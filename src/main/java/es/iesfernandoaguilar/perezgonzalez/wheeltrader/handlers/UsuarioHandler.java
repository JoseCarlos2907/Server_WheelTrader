package es.iesfernandoaguilar.perezgonzalez.wheeltrader.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.*;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.Auxiliares.UsuarioReportadosModDTO;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.Filtros.*;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.*;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.*;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Auxiliares.UsuarioReportadosMod;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.paypal.PayPalClient;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.paypal.PayPalService;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices.*;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Mensaje;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Serializador;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    NotificacionService notificacionService;

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
        this.notificacionService = context.getBean(NotificacionService.class);
        ObjectMapper mapper = new ObjectMapper();

        DataInputStream dis = null;
        DataOutputStream dos = null;

        boolean cierraSesion = false;
        try {
            dos = new DataOutputStream(cliente.getOutputStream());
            dis = new DataInputStream(cliente.getInputStream());

            // Token para pagos en PayPal
            String tokenPP = PayPalClient.obtenerAccessToken();
            // El id del pago como comprador en PayPal
            String orderId = null;
            // El correo del vendedor al que le tengo que pasar el dinero
            String correoVendedor = null;
            HttpResponse<String> response = null;

            while (!cierraSesion) {
                String linea = dis.readUTF();
                System.out.println(linea);
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

                    case "BANEAR_USUARIO":
                        this.usuarioService.actualizarEstadoUsuario(Long.valueOf(msgUsuario.getParams().get(0)), EstadoUsuario.BANEADO);
                        break;

                    case "DESBANEAR_USUARIO":
                        this.usuarioService.actualizarEstadoUsuario(Long.valueOf(msgUsuario.getParams().get(0)), EstadoUsuario.ACTIVO);
                        break;

                    case "REPORTAR_USUARIO":
                        ReporteDTO reporteDTO = mapper.readValue(msgUsuario.getParams().get(0), ReporteDTO.class);

                        Reporte reporte = this.reporteService.findByIdReportaAndReportado(reporteDTO.getUsuarioEnvia().getIdUsuario(), reporteDTO.getUsuarioRecibe().getIdUsuario());

                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("REPORTE_REALIZADO");
                        if(reporte == null) {
                            this.reporteService.reportarUsuario(reporteDTO.getUsuarioEnvia().getIdUsuario(), reporteDTO.getUsuarioRecibe().getIdUsuario(), reporteDTO.getExplicacion(), reporteDTO.getMotivo());
                            msgRespuesta.addParam("si");
                        }else{
                            msgRespuesta.addParam("no");
                        }

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        dos.flush();

                        break;

                    case "OBTENER_NOTIFICACIONES":
                        FiltroNotificaciones filtroNotificaciones = mapper.readValue(msgUsuario.getParams().get(0), FiltroNotificaciones.class);

                        List<NotificacionDTO> notificaciones = obtenerNotificaciones(filtroNotificaciones);

                        String notificacionesJSON = mapper.writeValueAsString(notificaciones);

                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("ENVIA_NOTIFICACIONES");
                        msgRespuesta.addParam(notificacionesJSON);
                        msgRespuesta.addParam(msgUsuario.getParams().get(1));

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        dos.flush();

                        System.out.println("Se envia todo");
                        break;

                    case "OBTENER_PDF_ACUERDO":
                        byte[] bytesDocumentoGenerado = rellenarPlantilla(msgUsuario.getParams().get(2), Integer.parseInt(msgUsuario.getParams().get(1)), Integer.parseInt(msgUsuario.getParams().get(0)));

                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("ENVIA_PDF_ACUERDO");
                        msgRespuesta.addParam(String.valueOf(bytesDocumentoGenerado.length));

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        dos.flush();

                        dos.write(bytesDocumentoGenerado);
                        dos.flush();

                        break;

                    case "OBTENER_PDF_ACUERDO_VENDEDOR":

                        String idCompradorAcuerdo = msgUsuario.getParams().get(0);
                        String idAnuncioAcuerdo = msgUsuario.getParams().get(1);
                        byte[] pdfRelleno = Files.readAllBytes(Paths.get("acuerdos/acuerdo_" + idAnuncioAcuerdo + "-" + idCompradorAcuerdo + "/acuerdo_" + idAnuncioAcuerdo + "-" + idCompradorAcuerdo + ".pdf"));

                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("ENVIA_PDF_ACUERDO_VENDEDOR");
                        msgRespuesta.addParam(String.valueOf(pdfRelleno.length));

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        dos.flush();

                        dos.write(pdfRelleno);
                        dos.flush();

                        break;

                    case "COMPRADOR_OFRECE_COMPRA":
                        int longitudPDFOferta = Integer.valueOf(msgUsuario.getParams().get(0));
                        byte[] pdfOferta = new byte[longitudPDFOferta];
                        dis.readFully(pdfOferta);

                        llegaOfertaAnuncio(pdfOferta, Long.valueOf(msgUsuario.getParams().get(2)), Long.valueOf(msgUsuario.getParams().get(1)), Long.valueOf(msgUsuario.getParams().get(3)));

                        break;

                    case "VENDEDOR_CONFIRMA_COMPRA":
                        int longitudPDFConfirmado = Integer.valueOf(msgUsuario.getParams().get(0));
                        byte[] pdfConfirmado = new byte[longitudPDFConfirmado];
                        dis.readFully(pdfConfirmado);

                        confirmarCompra(pdfConfirmado, Long.valueOf(msgUsuario.getParams().get(2)), Long.valueOf(msgUsuario.getParams().get(1)), Long.valueOf(msgUsuario.getParams().get(3)), Long.valueOf(msgUsuario.getParams().get(4)));
                        break;

                    case "VENDEDOR_RECHAZA_COMPRA":
                        rechazarCompra(Long.valueOf(msgUsuario.getParams().get(1)), Long.valueOf(msgUsuario.getParams().get(0)), Long.valueOf(msgUsuario.getParams().get(2)), Long.valueOf(msgUsuario.getParams().get(3)));
                        break;

                    case "CAMBIAR_ESTADO_NOTIFICACION":

                        EstadoNotificacion estadoCambiado = null;
                        if("LEIDO".equals(msgUsuario.getParams().get(1))){
                            estadoCambiado = EstadoNotificacion.LEIDO;
                        }else{
                            estadoCambiado = EstadoNotificacion.NO_LEIDO;
                        }

                        this.notificacionService.actualizarEstadoNotificacion(Long.valueOf(msgUsuario.getParams().get(0)), estadoCambiado);
                        break;

                    case "USUARIO_PAGA":
                        String correoComprador = this.usuarioService.findCorreoPPByIdUsuario(Long.valueOf(msgUsuario.getParams().get(0)));
                        correoVendedor = this.usuarioService.findCorreoPPByIdUsuario(Long.valueOf(msgUsuario.getParams().get(1)));
                        Map<String, Object> mapa = PayPalService.realizarPagoABusiness(tokenPP, correoComprador, Double.valueOf(msgUsuario.getParams().get(2)));

                        response = (HttpResponse<String>) mapa.get("response");
                        orderId = (String) mapa.get("orderId");

                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("ENVIA_URL_PAGO");
                        msgRespuesta.addParam((String) mapa.get("url"));

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        dos.flush();

                        System.out.println("Pago creado");
                        break;

                    case "OBTENER_ESTADO_PAGO":
                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("ENVIA_ESTADO_PAGO");
                        try{
                            if(response.getStatus() == 201){
                                if(PayPalService.isOrderApproved(orderId, tokenPP)){
                                    System.out.println("Approved");
                                    String captureUrl = "https://api-m.sandbox.paypal.com/v2/checkout/orders/" + orderId + "/capture";

                                    HttpResponse<String> captureResponse = Unirest.post(captureUrl)
                                            .header("Authorization", "Bearer " + tokenPP)
                                            .header("Content-Type", "application/json")
                                            .body("{}")
                                            .asString();

                                    if(captureResponse.getStatus() == 201) {
                                        System.out.println("Pagado con exito");
                                        PayPalService.realizarPagoACliente(tokenPP, correoVendedor, Double.valueOf(msgUsuario.getParams().get(1)));

                                        this.notificacionService.actualizarEstadoNotificacion(Long.valueOf(msgUsuario.getParams().get(0)), EstadoNotificacion.RESPONDIDO);

                                        msgRespuesta.addParam("si");
                                    } else {
                                        System.out.println("Ha dado otro codigo al capturar");
                                        msgRespuesta.addParam("error");
                                    }
                                }else{
                                    System.out.println("No approved");
                                    msgRespuesta.addParam("no");
                                }
                            }else{
                                System.out.println("El pago principal ha venido mal");
                                msgRespuesta.addParam("error");
                            }
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                            msgRespuesta.addParam("error");
                        }

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        dos.flush();
                        break;
                }
            }

        } catch (EOFException e) {
            System.out.println("Se cerró el flujo con el puerto " + cliente.getPort());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
                FiltroPorNombreUsuarioDTO filtroPorNombreUsuarioDTO = mapper.readValue(filtroJSON, FiltroPorNombreUsuarioDTO.class);
                Pageable pageablePorNombreUsuario = PageRequest.of(filtroPorNombreUsuarioDTO.getPagina(), filtroPorNombreUsuarioDTO.getCantidadPorPagina());

                anunciosEncontrados = this.anuncioService.findAnunciosGuardadosByNombreUsuario(filtroPorNombreUsuarioDTO.getNombreUsuario(), pageablePorNombreUsuario);
                break;

            case "PerfilUsuario":
            case "Publicados":

                FiltroPorNombreUsuarioDTO filtroPublicadosDTO = mapper.readValue(filtroJSON, FiltroPorNombreUsuarioDTO.class);

                Pageable pageablePublicados = PageRequest.of(filtroPublicadosDTO.getPagina(), filtroPublicadosDTO.getCantidadPorPagina());

                anunciosEncontrados = this.anuncioService.findAnunciosPublicadosByNombreUsuario(filtroPublicadosDTO.getNombreUsuario(), pageablePublicados);

                break;

            case "BarraBusqueda":
            case "BarraBusquedaMod":
                FiltroBarraBusquedaDTO filtroBarraBusquedaDTO = mapper.readValue(filtroJSON, FiltroBarraBusquedaDTO.class);

                Pageable pageableBarraBusqueda = PageRequest.of(filtroBarraBusquedaDTO.getPagina(), filtroBarraBusquedaDTO.getCantidadPorPagina());

                anunciosEncontrados = this.anuncioService.findAllByString(
                        filtroBarraBusquedaDTO.getTiposVehiculo(),
                        filtroBarraBusquedaDTO.getAnioMinimo(),
                        filtroBarraBusquedaDTO.getAnioMaximo(),
                        filtroBarraBusquedaDTO.getCadena(),
                        filtroBarraBusquedaDTO.getProvincia(),
                        filtroBarraBusquedaDTO.getCiudad(),
                        filtroBarraBusquedaDTO.getPrecioMinimo(),
                        filtroBarraBusquedaDTO.getPrecioMaximo(),
                        pageableBarraBusqueda
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

    public byte[] rellenarPlantilla(String tipoVehiculo, int idAnuncio, int idUsuario) throws IOException, DocumentException {

        Anuncio anuncio = this.anuncioService.findByIdAnuncioWithValoresCaracteristicas(idAnuncio);

        Usuario usuario = this.usuarioService.findById(idUsuario);

        String marca = "";
        String modelo = "";
        String anio = "";
        String km = "";
        String cv = "";
        String tipoCombustible = "";
        String transmision = "";
        String numeroMarchas = "";
        String cantPuertas = "";
        String cargaMaxima = "";
        String tipoTraccion = "";

        for (ValorCaracteristica vc : anuncio.getValoresCaracteristicas()) {
            if(vc.getCaracteristica().getNombre().contains("Marca")){
                marca = vc.getValor();
            }else if(vc.getCaracteristica().getNombre().contains("Modelo")){
                modelo = vc.getValor();
            }else if(vc.getCaracteristica().getNombre().contains("Anio")){
                anio = vc.getValor();
            }else if(vc.getCaracteristica().getNombre().contains("KM")){
                km = vc.getValor();
            }else if(vc.getCaracteristica().getNombre().contains("CV") || vc.getCaracteristica().getNombre().contains("Cilindrada")){
                cv = vc.getValor();
            }else if(vc.getCaracteristica().getNombre().contains("TipoCombustible")){
                tipoCombustible = vc.getValor();
            }else if(vc.getCaracteristica().getNombre().contains("Transmision")){
                transmision = vc.getValor();
            }else if (vc.getCaracteristica().getNombre().contains("Marchas")){
                numeroMarchas = vc.getValor();
            }else if (vc.getCaracteristica().getNombre().contains("Puertas")){
                cantPuertas = vc.getValor();
            }else if (vc.getCaracteristica().getNombre().contains("CargaMaxima")){
                cargaMaxima = vc.getValor();
            }else if (vc.getCaracteristica().getNombre().contains("TipoTraccion")){
                tipoTraccion = vc.getValor();
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfReader reader = new PdfReader(getClass().getResourceAsStream("/plantillas/Acuerdo" + tipoVehiculo + ".pdf"));
        PdfStamper stamper = new PdfStamper(reader, baos);
        AcroFields documento = stamper.getAcroFields();

        LocalDateTime fecha = LocalDateTime.now();

        switch (tipoVehiculo){
            case "Coche":
                documento.setField("Km", km);
                documento.setField("Cv", cv);
                documento.setField("Tipo_Combustible", tipoCombustible);
                documento.setField("Transmision", transmision);
                documento.setField("NumeroMarchas", numeroMarchas);
                documento.setField("CantidadPuertas", cantPuertas);
                documento.setField("Matricula", anuncio.getMatricula());

                break;

            case "Moto":
                documento.setField("Km", km);
                documento.setField("Cilindrada", cv);
                documento.setField("Tipo_Combustible", tipoCombustible);
                documento.setField("NumeroMarchas", numeroMarchas);
                documento.setField("Matricula", anuncio.getMatricula());
                break;

            case "Camion":
                documento.setField("Km", km);
                documento.setField("Cv", cv);
                documento.setField("CargaMaxima", cargaMaxima);
                documento.setField("Tipo_Combustible", tipoCombustible);
                documento.setField("NumeroMarchas", numeroMarchas);
                documento.setField("Matricula", anuncio.getMatricula());

                break;

            case "Camioneta":
                documento.setField("Km", km);
                documento.setField("Cv", cv);
                documento.setField("CargaMaxima", cargaMaxima);
                documento.setField("Tipo_Combustible", tipoCombustible);
                documento.setField("TipoTraccion", tipoTraccion);
                documento.setField("NumeroMarchas", numeroMarchas);
                documento.setField("Matricula", anuncio.getMatricula());

                break;

            case "Maquinaria":
                documento.setField("Tipo_Combustible", tipoCombustible);

                break;
        }

        documento.setField("NumeroBastidor", anuncio.getNumSerieBastidor());
        documento.setField("Ciudad", anuncio.getCiudad());
        documento.setField("Fecha", fecha.getDayOfMonth() + "/" + fecha.getMonthValue() + "/" + fecha.getYear());
        documento.setField("Marca", marca);
        documento.setField("Modelo", modelo);
        documento.setField("Anio", anio);
        documento.setField("Precio", String.format("%.2f" , anuncio.getPrecio()));
        documento.setField("DireccionComprador", usuario.getDireccion());
        documento.setField("DNI_CompradorFirma", usuario.getDni());
        documento.setField("NombreCompleto_Comprador", usuario.getNombre() + " " + usuario.getApellidos());
        documento.setField("DNI_Comprador", usuario.getDni());

        stamper.close();
        reader.close();

        baos.flush();
        byte[] pdfBytes = baos.toByteArray();

        File carpeta = new File("acuerdos/acuerdo_" + idAnuncio + "-" + idUsuario);
        carpeta.mkdirs();

        File pdf = new File("acuerdos/acuerdo_" + idAnuncio + "-" + idUsuario + "/acuerdo_" + idAnuncio + "-" + idUsuario + ".pdf");
        pdf.createNewFile();

        try (FileOutputStream fos = new FileOutputStream(pdf)) {
            fos.write(pdfBytes);
        }

        baos.close();

        return pdfBytes;
    }

    public void llegaOfertaAnuncio(byte[] bytesPDF, long idAnuncio, long idUsuario, long idVendedor) throws IOException {
        Path directorio = Paths.get("acuerdos", "acuerdo_" + idAnuncio + "-" + idUsuario);
        Files.createDirectories(directorio);

        Path pdfPath = directorio.resolve("acuerdo_" + idAnuncio + "-" + idUsuario + ".pdf");

        Files.deleteIfExists(pdfPath);

        Files.write(pdfPath, bytesPDF, StandardOpenOption.CREATE);

        Anuncio anuncio = this.anuncioService.findByIdAnuncioWithValoresCaracteristicas(idAnuncio);
        Usuario usuario = this.usuarioService.findById(idUsuario);

        String marca = "";
        String modelo = "";
        for (ValorCaracteristica vc: anuncio.getValoresCaracteristicas()){
            if(vc.getCaracteristica().getNombre().contains("Marca")){
                marca = vc.getValor();
            }else if (vc.getCaracteristica().getNombre().contains("Modelo")){
                modelo = vc.getValor();
            }
        }

        this.notificacionService.crearNotificacion(
                idUsuario,
                idAnuncio,
                idVendedor,
                EstadoNotificacion.NO_LEIDO,
                "Oferta recibida",
                "Se ha recibido una oferta por parte del usuario " + usuario.getNombreUsuario() + " para tu anuncio sobre el vehículo " + marca + " " + modelo,
                TipoNotificacion.OFERTA_ANUNCIO
        );
    }

    public List<NotificacionDTO> obtenerNotificaciones(FiltroNotificaciones filtro){
        List<Notificacion> notificaciones = this.notificacionService.obtenerNotificacionesByIdUsuario(filtro.getIdUsuario());

        List<NotificacionDTO> notificacionesDTO = new ArrayList<>();
        for (Notificacion notificacion: notificaciones){
            NotificacionDTO notificacionDTO = new NotificacionDTO();
            notificacionDTO.parse(notificacion);

            AnuncioDTO anuncioDTO = new AnuncioDTO();
            anuncioDTO.parse(notificacion.getAnuncio());

            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.parse(notificacion.getUsuarioEnvia());

            notificacionDTO.setAnuncio(anuncioDTO);
            notificacionDTO.setUsuarioEnvia(usuarioDTO);

            notificacionesDTO.add(notificacionDTO);
        }

        return notificacionesDTO;
    }

    public void confirmarCompra(byte[] bytesPDF, long idAnuncio, long idUsuario, long idVendedor, long idNotificacion) throws IOException, DocumentException {
        Path pdfPath = Paths.get("acuerdos/acuerdo_" + idAnuncio + "-" + idUsuario + "/acuerdo_" + idAnuncio + "-" + idUsuario + ".pdf");

        Files.deleteIfExists(pdfPath);

        Files.write(pdfPath, bytesPDF, StandardOpenOption.CREATE);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfReader reader = new PdfReader(Files.newInputStream(pdfPath));
        PdfStamper stamper = new PdfStamper(reader, baos);
        AcroFields documento = stamper.getAcroFields();


        Anuncio anuncio = this.anuncioService.findByIdAnuncioWithValoresCaracteristicas(idAnuncio);
        Usuario vendedor = this.usuarioService.findById(idVendedor);

        documento.setField("NombreCompleto_Vendedor",vendedor.getNombre() + " " + vendedor.getApellidos());
        documento.setField("DNI_Vendedor",vendedor.getDni());
        documento.setField("DNI_VendedorFirma",vendedor.getDni());

        // Con esta linea se puede decir que no se pueda editar una vez se marca esta variable a true
        stamper.setFormFlattening(true);
        stamper.close();
        reader.close();

        baos.flush();
        Files.write(pdfPath, baos.toByteArray());
        baos.close();

        String marca = "";
        String modelo = "";
        for (ValorCaracteristica vc: anuncio.getValoresCaracteristicas()){
            if(vc.getCaracteristica().getNombre().contains("Marca")){
                marca = vc.getValor();
            }else if (vc.getCaracteristica().getNombre().contains("Modelo")){
                modelo = vc.getValor();
            }
        }

        this.notificacionService.crearNotificacion(
                idVendedor,
                idAnuncio,
                idUsuario,
                EstadoNotificacion.NO_LEIDO,
                "Oferta aceptada",
                "El usuario" + vendedor.getNombreUsuario() + " ha aceptado la oferta que hiciste sobre el vehículo " + marca + " " + modelo + ", en caso de que el otro usuario no quiera enviar el vehículo o reunirse contigo, hay una cláusula donde deberá entregarlo en menos de 15 días.",
                TipoNotificacion.OFERTA_ACEPTADA
        );

        this.notificacionService.actualizarEstadoNotificacion(idNotificacion, EstadoNotificacion.RESPONDIDO);
        this.anuncioService.actualizarEstadoAnuncio(idAnuncio, EstadoAnuncio.VENDIDO);
    }

    public void rechazarCompra(long idAnuncio, long idUsuario, long idVendedor, long idNotificacion) throws IOException {
        Path pathAcuerdo = Paths.get("acuerdos/acuerdo_" + idAnuncio + "-" + idUsuario + "/acuerdo_" + idAnuncio + "-" + idUsuario + ".pdf");
        Files.deleteIfExists(pathAcuerdo);
        Path pathCarpetaAcuerdo = Paths.get("acuerdos/acuerdo_" + idAnuncio + "-" + idUsuario);
        Files.deleteIfExists(pathCarpetaAcuerdo);

        Anuncio anuncio = this.anuncioService.findByIdAnuncioWithValoresCaracteristicas(idAnuncio);
        Usuario vendedor = this.usuarioService.findById(idVendedor);

        String marca = "";
        String modelo = "";
        for (ValorCaracteristica vc: anuncio.getValoresCaracteristicas()){
            if(vc.getCaracteristica().getNombre().contains("Marca")){
                marca = vc.getValor();
            }else if (vc.getCaracteristica().getNombre().contains("Modelo")){
                modelo = vc.getValor();
            }
        }

        this.notificacionService.crearNotificacion(
                idVendedor,
                idAnuncio,
                idUsuario,
                EstadoNotificacion.NO_LEIDO,
                "Oferta rechazada",
                "El usuario" + vendedor.getNombreUsuario() + " ha rechazado la oferta que hiciste sobre el vehículo " + marca + " " + modelo + ". El documento de acuerdo se ha borrado del servidor por seguridad",
                TipoNotificacion.OFERTA_RECHAZADA
        );

        this.notificacionService.actualizarEstadoNotificacion(idNotificacion, EstadoNotificacion.RESPONDIDO);
        this.anuncioService.actualizarEstadoAnuncio(idAnuncio, EstadoAnuncio.EN_VENTA);
    }
}
