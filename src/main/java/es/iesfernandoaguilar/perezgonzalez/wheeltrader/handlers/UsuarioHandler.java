package es.iesfernandoaguilar.perezgonzalez.wheeltrader.handlers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.AnuncioDTO;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.ImagenDTO;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.ValorCaracteristicaDTO;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.TipoDatoCaracteristica;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.*;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices.*;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Mensaje;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Serializador;
import org.springframework.context.ApplicationContext;

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

        // Parseo las imágenes y las añado al anuncio parseado
        for(byte[] img : imagenes){
            Imagen imagen = new Imagen();
            imagen.setImagen(img);
            imagenService.save(imagen);
            anuncio.addImagen(imagen);
        }

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

        usuarioService.save(usuario);
    }
}
