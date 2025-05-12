package es.iesfernandoaguilar.perezgonzalez.wheeltrader.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.*;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.Auxiliares.UsuarioReportadosModDTO;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.Filtros.*;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.TipoDatoCaracteristica;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.*;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Auxiliares.UsuarioReportadosMod;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices.*;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Mensaje;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Serializador;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioHandler implements Runnable {

    private Socket cliente;
    private ApplicationContext context;

    private CaracteristicaService caracteristicaService;
    ImagenService imagenService;
    TipoVehiculoService tipoVehiculoService;
    ValorCaracteristicaService VCService;
    AnuncioService anuncioService;
    UsuarioService usuarioService;
    ReporteService reporteService;

    public UsuarioHandler(Socket cliente, ApplicationContext context) {
        this.cliente = cliente;
        this.context = context;
    }

    @Override
    public void run() {
        this.caracteristicaService = context.getBean(CaracteristicaService.class);
        this.imagenService = context.getBean(ImagenService.class);
        this.tipoVehiculoService = context.getBean(TipoVehiculoService.class);
        this.VCService = context.getBean(ValorCaracteristicaService.class);
        this.anuncioService = context.getBean(AnuncioService.class);
        this.usuarioService = context.getBean(UsuarioService.class);
        this.reporteService = context.getBean(ReporteService.class);
        ObjectMapper mapper = new ObjectMapper();

        DataInputStream dis = null;
        DataOutputStream dos = null;

        boolean cierraSesion = false;
        try {
            dos = new DataOutputStream(cliente.getOutputStream());
            dis = new DataInputStream(cliente.getInputStream());

            while (!cierraSesion) {
                String linea = dis.readUTF();
                // System.out.println(linea);
                Mensaje msgUsuario = Serializador.decodificarMensaje(linea);

                Mensaje msgRespuesta;
                switch (msgUsuario.getTipo()){
                    case "COMPROBAR_DATOS_VEHICULO":
                        System.out.println("COMPROBAR_DATOS_VEHICULO");
                        List<ValorCaracteristicaDTO> valoresDTO = mapper.readValue(msgUsuario.getParams().get(0), new TypeReference<List<ValorCaracteristicaDTO>>(){});

                        boolean resultado = comprobarDatosVehiculo(valoresDTO);

                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("DATOS_VALIDOS");
                        msgRespuesta.addParam(resultado ? "si" : "no");

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        dos.flush();
                        break;

                    case "PUBLICAR_ANUNCIO":
                        System.out.println("PUBLICAR_ANUNCIO");
                        List<byte[]> imagenes = new ArrayList<>();
                        AnuncioDTO anuncioDTO = mapper.readValue(msgUsuario.getParams().get(0), AnuncioDTO.class);

                        // Miro la cantidad de imagenes
                        int cantImagenes = Integer.parseInt(msgUsuario.getParams().get(1));
                        for (int i = 0; i < cantImagenes; i++) {
                            // Por cada imagen recojo la cantidad de bytes de cada una con los bytes de cada una
                            int cantBytesImg = dis.readInt();
                            byte[] bytesImagen = new byte[cantBytesImg];
                            dis.readFully(bytesImagen);
                            imagenes.add(bytesImagen);
                        }

                        publicarAnuncio(anuncioDTO, imagenes);

                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("ANUNCIO_PUBLICADO");

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        dos.flush();
                        break;

                    case "OBTENER_ANUNCIOS":
                        // System.out.println("OBTENER_ANUNCIOS");
                        List<byte[]> imagenesListaAnuncios = new ArrayList<>();
                        List<AnuncioDTO> anuncios = obtenerAnuncios(mapper, imagenesListaAnuncios, msgUsuario.getParams().get(0), msgUsuario.getParams().get(1), Integer.parseInt(msgUsuario.getParams().get(3)));

                        // Convierto los anuncios parseados a JSON para pasarselo a la aplicación
                        String anunciosJSON = mapper.writeValueAsString(anuncios);
                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("ENVIA_ANUNCIOS");
                        msgRespuesta.addParam(msgUsuario.getParams().get(1));
                        msgRespuesta.addParam(anunciosJSON);
                        msgRespuesta.addParam(String.valueOf(imagenesListaAnuncios.size()));
                        msgRespuesta.addParam(msgUsuario.getParams().get(2));

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        dos.flush();

                        for (byte[] imagen : imagenesListaAnuncios) {
                            dos.writeInt(imagen.length);
                            dos.flush();

                            dos.write(imagen);
                            dos.flush();
                        }
                        break;

                    case "GUARDAR_ANUNCIO":

                        this.guardarAnuncio(Integer.parseInt(msgUsuario.getParams().getFirst()), msgUsuario.getParams().get(1));

                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("ANUNCIO_GUARDADO");

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        dos.flush();
                        break;

                    case "ELIMINAR_ANUNCIO_GUARDADOS":

                        this.eliminarAnuncioGuardado(Integer.parseInt(msgUsuario.getParams().getFirst()), msgUsuario.getParams().get(1));

                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("ANUNCIO_ELIMINADO_GUARDADOS");

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        dos.flush();
                        break;

                    case "OBTENER_IMAGENES":

                        List<byte[]> bytesImagenes = this.enviarImagenesAnuncio(Integer.parseInt(msgUsuario.getParams().getFirst()));

                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("ENVIA_IMAGENES");
                        msgRespuesta.addParam(String.valueOf(bytesImagenes.size()));

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        dos.flush();

                        for (byte[] imagen : bytesImagenes) {
                            dos.writeInt(imagen.length);
                            dos.flush();

                            dos.write(imagen);
                            dos.flush();
                        }

                        break;

                    case "OBTENER_REPORTES_MOD":
                        // System.out.println("OBTENER_REPORTES_MOD");
                        FiltroUsuarioConReportesDTO filtroUsuarioConReportesDTO = mapper.readValue(msgUsuario.getParams().get(0), FiltroUsuarioConReportesDTO.class);

                        Pageable pageableUsuarioConReportes = PageRequest.of(filtroUsuarioConReportesDTO.getPagina(), filtroUsuarioConReportesDTO.getCantidadPorPagina());

                        List<UsuarioReportadosMod> usuariosReportadosMod = this.usuarioService.findUsuariosReportadosMod(filtroUsuarioConReportesDTO.getCadena(), pageableUsuarioConReportes);

                        List<UsuarioReportadosModDTO> usuariosReportadosModDTO = new ArrayList<>();
                        for (UsuarioReportadosMod usuarioReportadosMod : usuariosReportadosMod) {
                            UsuarioReportadosModDTO usuarioReportadosModDTO = new UsuarioReportadosModDTO();

                            UsuarioDTO usuarioDTO = new UsuarioDTO();
                            usuarioDTO.parse(usuarioReportadosMod.getUsuario());
                            usuarioReportadosModDTO.setUsuario(usuarioDTO);

                            usuarioReportadosModDTO.setCantReportes(usuarioReportadosMod.getCantReportes());
                            usuarioReportadosModDTO.setMediaValoraciones(usuarioReportadosMod.getMediaValoraciones());

                            usuariosReportadosModDTO.add(usuarioReportadosModDTO);
                        }

                        String usuariosReportadosModJSON = mapper.writeValueAsString(usuariosReportadosModDTO);

                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("ENVIA_REPORTES_MOD");
                        msgRespuesta.addParam(usuariosReportadosModJSON);

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        dos.flush();

                        break;

                    case "OBTENER_ULTIMOS_REPORTES_MOD":
                        FiltroReportesDTO filtroReportesDTO = mapper.readValue(msgUsuario.getParams().get(0), FiltroReportesDTO.class);

                        Pageable pageableReportes = PageRequest.of(filtroReportesDTO.getPagina(), filtroReportesDTO.getCantidadPorPagina());

                        List<Reporte> reportes = this.reporteService.findUltimosReportes(pageableReportes);

                        List<ReporteDTO> reportesDTO = new ArrayList<>();
                        for (Reporte reporte : reportes) {
                            ReporteDTO reporteDTO = new ReporteDTO();
                            reporteDTO.parse(reporte);
                            reportesDTO.add(reporteDTO);

                            UsuarioDTO usuarioRecibeDTO = new UsuarioDTO();
                            usuarioRecibeDTO.parse(reporte.getUsuarioRecibe());
                            reporteDTO.setUsuarioRecibe(usuarioRecibeDTO);

                            UsuarioDTO usuarioEnviaDTO = new UsuarioDTO();
                            usuarioEnviaDTO.parse(reporte.getUsuarioEnvia());
                            reporteDTO.setUsuarioEnvia(usuarioEnviaDTO);
                        }

                        String reportesJSON = mapper.writeValueAsString(reportesDTO);

                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("ENVIA_ULTIMOS_REPORTES_MOD");
                        msgRespuesta.addParam(reportesJSON);

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        dos.flush();

                        break;
                }
            }

        } catch (EOFException e) {
            System.out.println("Se cerró el flujo con el puerto " + cliente.getPort());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean comprobarDatosVehiculo(List<ValorCaracteristicaDTO> valoresCaracteristicas) {
        boolean sonValidos = true;
        for (ValorCaracteristicaDTO valorDTO : valoresCaracteristicas){
            Caracteristica caracteristica = this.caracteristicaService.findByNombreWithTipoVehiculoCaracteristicas(valorDTO.getNombreCaracteristica());
            TipoVehiculo_Caracteristica valor = caracteristica.getTiposVehiculoCaracteristica().getFirst();

            System.out.println(valorDTO.getNombreCaracteristica());

            try {
                if (caracteristica.getTipo_dato().equals(TipoDatoCaracteristica.NUMERICO)) {
                    int valorInt = Integer.parseInt(valorDTO.getValor());
                    if (valorInt < caracteristica.getValorMin() || valorInt > caracteristica.getValorMax()) {
                        sonValidos = false;
                        break;
                    }
                } else if (caracteristica.getTipo_dato().equals(TipoDatoCaracteristica.TEXTO)) {
                    String valorStr = valorDTO.getValor();
                    if(caracteristica.getOpciones() != null && ((valor.isObligatorio() && !caracteristica.getOpciones().contains(valorStr)) || valorStr.isEmpty())){
                        sonValidos = false;
                        break;
                    }
                }
            } catch (Exception e) {
                sonValidos = false;
                break;
            }
        }

        return sonValidos;
    }

    public void publicarAnuncio(
            AnuncioDTO anuncioDTO,
            List<byte[]> imagenes
    ) {
        // A partir de aquí se mapean los datos a mano
        Usuario usuario = this.usuarioService.findByNombreUsuarioWithAnunciosPublicados(anuncioDTO.getVendedor().getNombreUsuario());

        // Parseo el anuncio
        Anuncio anuncio = new Anuncio(
                anuncioDTO.getDescripcion(),
                anuncioDTO.getPrecio(),
                anuncioDTO.getProvincia(),
                anuncioDTO.getCiudad(),
                anuncioDTO.getMatricula(),
                anuncioDTO.getNumSerieBastidor()
        );

        usuario.addAnuncioPublicado(anuncio);

        // Busco el tipo de vehículo por la cadena que le paso desde le lado del usuario
        TipoVehiculo tipoCoche = this.tipoVehiculoService.findByTipoWithAnuncios(anuncioDTO.getTipoVehiculo());
        anuncio.setTipoVehiculo(tipoCoche);

        // Parseo cada valor característica y la asocio al anuncio parseado directamente
        for(ValorCaracteristicaDTO valorDTO: anuncioDTO.getValoresCaracteristicas()){
            Caracteristica caracteristica = this.caracteristicaService.findByNombreWithValoresCaracteristicas(valorDTO.getNombreCaracteristica());
            ValorCaracteristica valorCaracteristica = new ValorCaracteristica(valorDTO.getValor());
            valorCaracteristica.setCaracteristica(caracteristica);
            anuncio.addValoresCaracteristica(valorCaracteristica);
        }

        // Guardo primero el anuncio
        this.anuncioService.save(anuncio);

        // Después guardo los valores de las características con la relación de este anuncio,
        // ya que si lo hago antes de guardar el anuncio no me encuentra el anuncio en la relación
        for(ValorCaracteristica valorCaracteristica : anuncio.getValoresCaracteristicas()){
            this.VCService.save(valorCaracteristica);
        }

        // Parseo las imágenes y las añado al anuncio parseado
        for(byte[] img : imagenes){
            Imagen imagen = new Imagen();
            imagen.setImagen(img);
            anuncio.addImagen(imagen);
            this.imagenService.save(imagen);
        }

        this.usuarioService.save(usuario);
    }

    public List<AnuncioDTO> obtenerAnuncios(ObjectMapper mapper, List<byte[]> imagenes, String filtroJSON, String tipoFiltro, long idUsuario) throws JsonProcessingException {
        List<Anuncio> anunciosEncontrados = new ArrayList<>();
        List<AnuncioDTO> anunciosParseados = new ArrayList<>();

        System.out.println(filtroJSON);

        switch (tipoFiltro){
            case "Moderador":
            case "Todo":
                // Recojo el JSON del filtro del protocolo
                FiltroTodoDTO filtroTodoDTO = mapper.readValue(filtroJSON, FiltroTodoDTO.class);

                // Creo un objeto paginable con los parametros del filtro
                Pageable pageableTodo = PageRequest.of(filtroTodoDTO.getPagina(), filtroTodoDTO.getCantidadPorPagina());

                // Busco los anuncios desde el servicio según el tipo
                anunciosEncontrados = this.anuncioService.findAll(
                        filtroTodoDTO.getTiposVehiculo(),
                        filtroTodoDTO.getAnioMinimo(),
                        filtroTodoDTO.getAnioMaximo(),
                        filtroTodoDTO.getMarca(),
                        filtroTodoDTO.getModelo(),
                        filtroTodoDTO.getProvincia(),
                        filtroTodoDTO.getCiudad(),
                        filtroTodoDTO.getPrecioMinimo(),
                        filtroTodoDTO.getPrecioMaximo(),
                        pageableTodo
                );
                break;

            case "Coche":
                FiltroCocheDTO filtroCocheDTO = mapper.readValue(filtroJSON, FiltroCocheDTO.class);

                Pageable pageableCoche = PageRequest.of(filtroCocheDTO.getPagina(), filtroCocheDTO.getCantidadPorPagina());

                anunciosEncontrados = this.anuncioService.findAunciosByTipo(
                        tipoFiltro,
                        filtroCocheDTO.getMarca(),
                        filtroCocheDTO.getModelo(),
                        filtroCocheDTO.getCantMarchas(),
                        filtroCocheDTO.getKmMinimo(),
                        filtroCocheDTO.getKmMaximo(),
                        filtroCocheDTO.getnPuertas(),
                        filtroCocheDTO.getProvincia(),
                        filtroCocheDTO.getCiudad(),
                        filtroCocheDTO.getCvMinimo(),
                        filtroCocheDTO.getCvMaximo(),
                        filtroCocheDTO.getAnioMinimo(),
                        filtroCocheDTO.getAnioMaximo(),
                        filtroCocheDTO.getTipoCombustible(),
                        filtroCocheDTO.getTransmision(),
                        null,
                        pageableCoche
                );
                break;

            case "Moto":
                FiltroMotoDTO filtroMotoDTO = mapper.readValue(filtroJSON, FiltroMotoDTO.class);

                Pageable pageableMoto = PageRequest.of(filtroMotoDTO.getPagina(), filtroMotoDTO.getCantidadPorPagina());

                anunciosEncontrados = this.anuncioService.findAunciosByTipo(
                        filtroMotoDTO.getTipoFiltro(),
                        filtroMotoDTO.getMarca(),
                        filtroMotoDTO.getModelo(),
                        filtroMotoDTO.getCantMarchas(),
                        filtroMotoDTO.getKmMinimo(),
                        filtroMotoDTO.getKmMaximo(),
                        null,
                        filtroMotoDTO.getProvincia(),
                        filtroMotoDTO.getCiudad(),
                        filtroMotoDTO.getCvMinimo(),
                        filtroMotoDTO.getCvMaximo(),
                        filtroMotoDTO.getAnioMinimo(),
                        filtroMotoDTO.getAnioMaximo(),
                        filtroMotoDTO.getTipoCombustible(),
                        null,
                        null,
                        pageableMoto
                );
                break;

            case "Camioneta":
                FiltroCamionetaDTO filtroCamionetaDTO = mapper.readValue(filtroJSON, FiltroCamionetaDTO.class);

                Pageable pageableCamioneta = PageRequest.of(filtroCamionetaDTO.getPagina(), filtroCamionetaDTO.getCantidadPorPagina());

                anunciosEncontrados = this.anuncioService.findAunciosByTipo(
                        filtroCamionetaDTO.getTipoFiltro(),
                        filtroCamionetaDTO.getMarca(),
                        filtroCamionetaDTO.getModelo(),
                        filtroCamionetaDTO.getCantMarchas(),
                        filtroCamionetaDTO.getKmMinimo(),
                        filtroCamionetaDTO.getKmMaximo(),
                        filtroCamionetaDTO.getnPuertas(),
                        filtroCamionetaDTO.getProvincia(),
                        filtroCamionetaDTO.getCiudad(),
                        filtroCamionetaDTO.getCvMinimo(),
                        filtroCamionetaDTO.getCvMaximo(),
                        filtroCamionetaDTO.getAnioMinimo(),
                        filtroCamionetaDTO.getAnioMaximo(),
                        filtroCamionetaDTO.getTipoCombustible(),
                        null,
                        filtroCamionetaDTO.getTraccion(),
                        pageableCamioneta
                );
                break;

            case "Camion":
                FiltroCamionDTO filtroCamionDTO = mapper.readValue(filtroJSON, FiltroCamionDTO.class);

                Pageable pageableCamion = PageRequest.of(filtroCamionDTO.getPagina(), filtroCamionDTO.getCantidadPorPagina());

                anunciosEncontrados = this.anuncioService.findAunciosByTipo(
                        filtroCamionDTO.getTipoFiltro(),
                        filtroCamionDTO.getMarca(),
                        filtroCamionDTO.getModelo(),
                        filtroCamionDTO.getCantMarchas(),
                        filtroCamionDTO.getKmMinimo(),
                        filtroCamionDTO.getKmMaximo(),
                        null,
                        filtroCamionDTO.getProvincia(),
                        filtroCamionDTO.getCiudad(),
                        filtroCamionDTO.getCvMinimo(),
                        filtroCamionDTO.getCvMaximo(),
                        filtroCamionDTO.getAnioMinimo(),
                        filtroCamionDTO.getAnioMaximo(),
                        filtroCamionDTO.getTipoCombustible(),
                        null,
                        null,
                        pageableCamion
                );
                break;

            case "Maquinaria":
                FiltroMaquinariaDTO filtroMaquinariaDTO  = mapper.readValue(filtroJSON, FiltroMaquinariaDTO.class);

                Pageable pageableMaquinaria = PageRequest.of(filtroMaquinariaDTO.getPagina(), filtroMaquinariaDTO.getCantidadPorPagina());

                anunciosEncontrados = this.anuncioService.findAunciosByTipo(
                        filtroMaquinariaDTO.getTipoFiltro(),
                        filtroMaquinariaDTO.getMarca(),
                        filtroMaquinariaDTO.getModelo(),
                        null,
                        null,
                        null,
                        null,
                        filtroMaquinariaDTO.getProvincia(),
                        filtroMaquinariaDTO.getCiudad(),
                        null,
                        null,
                        filtroMaquinariaDTO.getAnioMinimo(),
                        filtroMaquinariaDTO.getAnioMaximo(),
                        filtroMaquinariaDTO.getTipoCombustible(),
                        null,
                        null,
                        pageableMaquinaria
                );
                break;

            case "Guardados":

                FiltroGuardadosDTO filtroGuardadosDTO  = mapper.readValue(filtroJSON, FiltroGuardadosDTO.class);

                Pageable pageableGuardados = PageRequest.of(filtroGuardadosDTO.getPagina(), filtroGuardadosDTO.getCantidadPorPagina());

                anunciosEncontrados = this.anuncioService.findAnunciosGuardadosByNombreUsuario(filtroGuardadosDTO.getNombreUsuario(), pageableGuardados);

                break;

            case "Publicados":

                FiltroPublicadosDTO filtroPublicadosDTO = mapper.readValue(filtroJSON, FiltroPublicadosDTO.class);

                Pageable pageablePublicados = PageRequest.of(filtroPublicadosDTO.getPagina(), filtroPublicadosDTO.getCantidadPorPagina());

                anunciosEncontrados = this.anuncioService.findAnunciosPublicadosByNombreUsuario(filtroPublicadosDTO.getNombreUsuario(), pageablePublicados);

                break;
        }

        // Convierto los anuncios encontrados a un formato que permita la aplicación (sin relaciones)
        for (Anuncio anuncio : anunciosEncontrados) {
            AnuncioDTO anuncioDTOEncontrado = new AnuncioDTO();
            anuncioDTOEncontrado.parse(anuncio);

            // Datos propios de anuncio
            anuncioDTOEncontrado.setPrecio(anuncio.getPrecio());
            anuncioDTOEncontrado.setProvincia(anuncio.getProvincia());
            anuncioDTOEncontrado.setCiudad(anuncio.getCiudad());

            List<ValorCaracteristica> valoresCaracteristicas = this.VCService.findByIdAnuncio(anuncio.getIdAnuncio());

            // Datos de la relación con ValorCaracteristica
            for (ValorCaracteristica valorCaracteristica : valoresCaracteristicas) {
                ValorCaracteristicaDTO vcDTO = new ValorCaracteristicaDTO();
                vcDTO.parse(valorCaracteristica);
                anuncioDTOEncontrado.getValoresCaracteristicas().add(vcDTO);
            }

            // Datos de la relación con TipoVehiculo
            TipoVehiculo tipoVehiculo = this.tipoVehiculoService.findByIdAnuncio(anuncio.getIdAnuncio());
            TipoVehiculoDTO tipoVehiculoDTO = new TipoVehiculoDTO();
            tipoVehiculoDTO.parse(tipoVehiculo);
            anuncioDTOEncontrado.setTipoVehiculo(tipoVehiculo.getTipo());

            // Datos de la relación con Usuario
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.parse(anuncio.getVendedor());
            usuarioDTO.setContrasenia("");
            usuarioDTO.setSalt("");
            anuncioDTOEncontrado.setVendedor(usuarioDTO);

            // Datos de la relación con TipoVehiculo
            anuncioDTOEncontrado.setTipoVehiculo(anuncio.getTipoVehiculo().getTipo());

            // Datos de la relación con Imagen
            List<Imagen> imgs = imagenService.findByIdAnuncio(anuncio.getIdAnuncio());
            imagenes.add(imgs.getFirst().getImagen());

            Optional<Usuario> usuarioOpt = this.usuarioService.findUsuarioQueHaGuardadoAnuncio(idUsuario, anuncio.getIdAnuncio());
            if (usuarioOpt.isPresent()) {
                anuncioDTOEncontrado.guardar();
            }else{
                anuncioDTOEncontrado.eliminarGuardado();
            }

            anunciosParseados.add(anuncioDTOEncontrado);
        }

        return anunciosParseados;
    }

    public void guardarAnuncio(int idAnuncio, String nombreUsuario){
        Anuncio anuncio = this.anuncioService.findByIdWithUsuariosGuardan(idAnuncio);

        Usuario usuario = this.usuarioService.findByNombreUsuarioWithAnunciosGuardados(nombreUsuario);

        usuario.addAnuncioGuardado(anuncio);

        this.usuarioService.save(usuario);
    }

    public void eliminarAnuncioGuardado(int idAnuncio, String nombreUsuario){
        Anuncio anuncio = this.anuncioService.findByIdWithUsuariosGuardan(idAnuncio);

        Usuario usuario = this.usuarioService.findByNombreUsuarioWithAnunciosGuardados(nombreUsuario);

        usuario.eliminarAnuncioGuardado(anuncio);

        this.usuarioService.save(usuario);
    }

    public List<byte[]> enviarImagenesAnuncio(int idAnuncio){
        List<Imagen> imagenes = this.imagenService.findByIdAnuncio(idAnuncio);

        List<byte[]> bytesImagenes = new ArrayList<>();

        for (Imagen imagen : imagenes) {
            bytesImagenes.add(imagen.getImagen());
        }

        return bytesImagenes;
    }
}
