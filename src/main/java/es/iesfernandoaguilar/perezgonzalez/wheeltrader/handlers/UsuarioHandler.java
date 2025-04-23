package es.iesfernandoaguilar.perezgonzalez.wheeltrader.handlers;

import ch.qos.logback.core.joran.spi.NoAutoStartUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.*;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.Filtros.*;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.TipoDatoCaracteristica;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.*;
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

public class UsuarioHandler implements Runnable {

    private Socket cliente;
    private ApplicationContext context;

    private DataOutputStream dos;
    private DataInputStream dis;

    public UsuarioHandler(Socket cliente, ApplicationContext context) {
        this.cliente = cliente;
        this.context = context;
    }

    @Override
    public void run() {
        CaracteristicaService caracteristicaService = context.getBean(CaracteristicaService.class);
        ImagenService imagenService = context.getBean(ImagenService.class);
        TipoVehiculoService tipoVehiculoService = context.getBean(TipoVehiculoService.class);
        ValorCaracteristicaService VCService = context.getBean(ValorCaracteristicaService.class);
        AnuncioService anuncioService = context.getBean(AnuncioService.class);
        UsuarioService usuarioService = context.getBean(UsuarioService.class);
        ObjectMapper mapper = new ObjectMapper();

        boolean cierraSesion = false;
        try {
            this.dis = new DataInputStream(cliente.getInputStream());
            this.dos = new DataOutputStream(cliente.getOutputStream());

            Mensaje msg = new Mensaje();
            msg.setTipo("BIENVENIDO");
            this.dos.writeUTF(Serializador.codificarMensaje(msg));

            while (!cierraSesion) {
                String linea = this.dis.readUTF();
                Mensaje msgUsuario = Serializador.decodificarMensaje(linea);

                Mensaje msgRespuesta;
                switch (msgUsuario.getTipo()){
                    case "COMPROBAR_DATOS_VEHICULO":
                        System.out.println("COMPROBAR_DATOS_VEHICULO");
                        List<ValorCaracteristicaDTO> valoresDTO = mapper.readValue(msgUsuario.getParams().get(0), new TypeReference<List<ValorCaracteristicaDTO>>(){});

                        boolean resultado = comprobarDatosVehiculo(caracteristicaService, valoresDTO);

                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("DATOS_VALIDOS");
                        msgRespuesta.addParam(resultado ? "si" : "no");

                        this.dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        break;

                    case "PUBLICAR_ANUNCIO":
                        System.out.println("PUBLICAR_ANUNCIO");
                        List<byte[]> imagenes = new ArrayList<>();
                        AnuncioDTO anuncioDTO = mapper.readValue(msgUsuario.getParams().get(0), AnuncioDTO.class);

                        // Miro la cantidad de imagenes
                        int cantImagenes = Integer.parseInt(msgUsuario.getParams().get(1));
                        for (int i = 0; i < cantImagenes; i++) {
                            // Por cada imagen recojo la cantidad de bytes de cada una con los bytes de cada una
                            int cantBytesImg = this.dis.readInt();
                            byte[] bytesImagen = new byte[cantBytesImg];
                            this.dis.readFully(bytesImagen);
                            imagenes.add(bytesImagen);
                        }

                        publicarAnuncio(anuncioDTO, imagenes, caracteristicaService, imagenService, tipoVehiculoService, VCService, anuncioService, usuarioService);

                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("ANUNCIO_PUBLICADO");

                        this.dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        break;


                    case "OBTENER_ANUNCIOS":
                        System.out.println("OBTENER_ANUNCIOS");
                        List<byte[]> imagenesListaAnuncios = new ArrayList<>();
                        List<AnuncioDTO> anuncios = obtenerAnuncios(mapper, anuncioService, VCService, imagenService, imagenesListaAnuncios, msgUsuario.getParams().get(0), msgUsuario.getParams().get(1));

                        // Convierto los anuncios parseados a JSON para pasarselo a la aplicación
                        String anunciosJSON = mapper.writeValueAsString(anuncios);
                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("ENVIA_ANUNCIOS");
                        msgRespuesta.addParam(anunciosJSON);

                        this.dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        this.dos.flush();

                        this.dos.writeInt(imagenesListaAnuncios.size());
                        this.dos.flush();

                        System.out.println("Cantidad de imagenes: " + imagenesListaAnuncios.size());
                        for (byte[] imagen : imagenesListaAnuncios) {
                            System.out.println("Longitud de la imagen: " + imagen.length);
                            this.dos.writeInt(imagen.length);
                            this.dos.flush();

                            this.dos.write(imagen);
                            this.dos.flush();
                        }
                        break;
                }
            }

        } catch (EOFException e) {
            System.out.println("Se cerró el flujo con el puerto " + cliente.getPort());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean comprobarDatosVehiculo(CaracteristicaService caracteristicaService, List<ValorCaracteristicaDTO> valoresCaracteristicas) {
        boolean sonValidos = true;
        for (ValorCaracteristicaDTO valorDTO : valoresCaracteristicas){
            Caracteristica caracteristica = caracteristicaService.findByNombreWithTipoVehiculoCaracteristicas(valorDTO.getNombreCaracteristica());
            TipoVehiculo_Caracteristica valor = caracteristica.getTiposVehiculoCaracteristica().getFirst();

            // System.out.println(valorDTO.getNombreCaracteristica());

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
            List<byte[]> imagenes,
            CaracteristicaService caracteristicaService,
            ImagenService imagenService,
            TipoVehiculoService tipoVehiculoService,
            ValorCaracteristicaService VCService,
            AnuncioService anuncioService,
            UsuarioService usuarioService
    ) {
        // A partir de aquí se mapean los datos a mano
        Usuario usuario = usuarioService.findByNombreUsuarioWithAnunciosPublicados(anuncioDTO.getVendedor().getNombreUsuario());

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
        TipoVehiculo tipoCoche = tipoVehiculoService.findByTipoWithAnuncios(anuncioDTO.getTipoVehiculo());
        anuncio.setTipoVehiculo(tipoCoche);

        // Parseo cada valor característica y la asocio al anuncio parseado directamente
        for(ValorCaracteristicaDTO valorDTO: anuncioDTO.getValoresCaracteristicas()){
            Caracteristica caracteristica = caracteristicaService.findByNombreWithValoresCaracteristicas(valorDTO.getNombreCaracteristica());
            ValorCaracteristica valorCaracteristica = new ValorCaracteristica(valorDTO.getValor());
            valorCaracteristica.setCaracteristica(caracteristica);
            anuncio.addValoresCaracteristica(valorCaracteristica);
        }

        // Guardo primero el anuncio
        anuncioService.save(anuncio);

        // Después guardo los valores de las características con la relación de este anuncio,
        // ya que si lo hago antes de guardar el anuncio no me encuentra el anuncio en la relación
        for(ValorCaracteristica valorCaracteristica : anuncio.getValoresCaracteristicas()){
            VCService.save(valorCaracteristica);
        }

        // Parseo las imágenes y las añado al anuncio parseado
        for(byte[] img : imagenes){
            Imagen imagen = new Imagen();
            imagen.setImagen(img);
            anuncio.addImagen(imagen);
            imagenService.save(imagen);
        }

        usuarioService.save(usuario);
    }

    public List<AnuncioDTO> obtenerAnuncios(ObjectMapper mapper, AnuncioService anuncioService, ValorCaracteristicaService VCService, ImagenService imagenService, List<byte[]> imagenes, String filtroJSON, String tipoFiltro) throws JsonProcessingException {
        List<Anuncio> anunciosEncontrados = new ArrayList<>();
        List<AnuncioDTO> anunciosParseados = new ArrayList<>();
        switch (tipoFiltro){
            case "Todo":
                // Recojo el JSON del filtro del protocolo
                FiltroTodoDTO filtroTodoDTO = mapper.readValue(filtroJSON, FiltroTodoDTO.class);

                // Creo un objeto paginable con los parametros del filtro
                Pageable pageableTodo = PageRequest.of(filtroTodoDTO.getPagina(), filtroTodoDTO.getCantidadPorPagina());

                // Busco los anuncios desde el servicio según el tipo
                anunciosEncontrados = anuncioService.findAll(
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

                anunciosEncontrados = anuncioService.findCoches(
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
                        pageableCoche
                );
                break;

            case "Moto":
                FiltroMotoDTO filtroMotoDTO = mapper.readValue(filtroJSON, FiltroMotoDTO.class);

                Pageable pageableMoto = PageRequest.of(filtroMotoDTO.getPagina(), filtroMotoDTO.getCantidadPorPagina());

                anunciosEncontrados = anuncioService.findMotos(
                        filtroMotoDTO.getMarca(),
                        filtroMotoDTO.getModelo(),
                        filtroMotoDTO.getCantMarchas(),
                        filtroMotoDTO.getKmMinimo(),
                        filtroMotoDTO.getKmMaximo(),
                        filtroMotoDTO.getProvincia(),
                        filtroMotoDTO.getCiudad(),
                        filtroMotoDTO.getCvMinimo(),
                        filtroMotoDTO.getCvMaximo(),
                        filtroMotoDTO.getAnioMinimo(),
                        filtroMotoDTO.getAnioMaximo(),
                        filtroMotoDTO.getTipoCombustible(),
                        pageableMoto
                );
                break;

            case "Camioneta":
                FiltroCamionetaDTO filtroCamionetaDTO = mapper.readValue(filtroJSON, FiltroCamionetaDTO.class);

                Pageable pageableCamioneta = PageRequest.of(filtroCamionetaDTO.getPagina(), filtroCamionetaDTO.getCantidadPorPagina());

                anunciosEncontrados = anuncioService.findCamionetas(
                        filtroCamionetaDTO.getMarca(),
                        filtroCamionetaDTO.getModelo(),
                        filtroCamionetaDTO.getAnioMinimo(),
                        filtroCamionetaDTO.getAnioMaximo(),
                        filtroCamionetaDTO.getKmMinimo(),
                        filtroCamionetaDTO.getKmMaximo(),
                        filtroCamionetaDTO.getTipoCombustible(),
                        filtroCamionetaDTO.getCvMinimo(),
                        filtroCamionetaDTO.getCvMaximo(),
                        filtroCamionetaDTO.getCantMarchas(),
                        filtroCamionetaDTO.getnPuertas(),
                        filtroCamionetaDTO.getProvincia(),
                        filtroCamionetaDTO.getCiudad(),
                        filtroCamionetaDTO.getTraccion(),
                        pageableCamioneta
                );
                break;

            case "Camion":
                FiltroCamionDTO filtroCamionDTO = mapper.readValue(filtroJSON, FiltroCamionDTO.class);

                Pageable pageableCamion = PageRequest.of(filtroCamionDTO.getPagina(), filtroCamionDTO.getCantidadPorPagina());

                anunciosEncontrados = anuncioService.findCamiones(
                        filtroCamionDTO.getMarca(),
                        filtroCamionDTO.getModelo(),
                        filtroCamionDTO.getAnioMinimo(),
                        filtroCamionDTO.getAnioMaximo(),
                        filtroCamionDTO.getKmMinimo(),
                        filtroCamionDTO.getKmMaximo(),
                        filtroCamionDTO.getTipoCombustible(),
                        filtroCamionDTO.getCvMinimo(),
                        filtroCamionDTO.getCvMaximo(),
                        filtroCamionDTO.getCantMarchas(),
                        filtroCamionDTO.getProvincia(),
                        filtroCamionDTO.getCiudad(),
                        pageableCamion
                );
                break;

            case "Maquinaria":
                FiltroMaquinariaDTO filtroMaquinariaDTO  = mapper.readValue(filtroJSON, FiltroMaquinariaDTO.class);

                Pageable pageableMaquinaria = PageRequest.of(filtroMaquinariaDTO.getPagina(), filtroMaquinariaDTO.getCantidadPorPagina());

                anunciosEncontrados = anuncioService.findMaquinaria(
                        filtroMaquinariaDTO.getMarca(),
                        filtroMaquinariaDTO.getModelo(),
                        filtroMaquinariaDTO.getAnioMinimo(),
                        filtroMaquinariaDTO.getAnioMaximo(),
                        filtroMaquinariaDTO.getTipoCombustible(),
                        filtroMaquinariaDTO.getProvincia(),
                        filtroMaquinariaDTO.getCiudad(),
                        pageableMaquinaria
                );
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

            List<ValorCaracteristica> valoresCaracteristicas = VCService.findByIdAnuncio(anuncio.getIdAnuncio());

            // Datos de la relación con ValorCaracteristica
            for (ValorCaracteristica valorCaracteristica : valoresCaracteristicas) {
                ValorCaracteristicaDTO vcDTO = new ValorCaracteristicaDTO();
                vcDTO.parse(valorCaracteristica);
                anuncioDTOEncontrado.getValoresCaracteristicas().add(vcDTO);
            }

            // Datos de la relación con Usuario
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.parse(anuncio.getVendedor());
            anuncioDTOEncontrado.setVendedor(usuarioDTO);

            // Datos de la relación con TipoVehiculo
            anuncioDTOEncontrado.setTipoVehiculo(anuncio.getTipoVehiculo().getTipo());

            // Datos de la relación con Imagen
            List<Imagen> imgs = imagenService.findByIdAnuncio(anuncio.getIdAnuncio());
            imagenes.add(imgs.getFirst().getImagen());

            anunciosParseados.add(anuncioDTOEncontrado);
        }

        return anunciosParseados;
    }
}
