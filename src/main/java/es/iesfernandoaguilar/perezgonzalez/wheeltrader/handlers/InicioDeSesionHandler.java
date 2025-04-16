package es.iesfernandoaguilar.perezgonzalez.wheeltrader.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.UsuarioDTO;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.Servidor;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.UsuarioRepository;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices.UsuarioService;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Mensaje;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Serializador;
import org.springframework.context.ApplicationContext;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;

public class InicioDeSesionHandler implements Runnable {
    private Socket socket;
    private Servidor server;
    private UsuarioService usuarioService;
    private ApplicationContext context;

    public InicioDeSesionHandler(Socket socket, ApplicationContext context, Servidor server) {
        this.socket = socket;
        this.context = context;
        this.server = server;
    }

    @Override
    public void run() {
        this.usuarioService = context.getBean(UsuarioService.class);

        DataInputStream dis = null;
        DataOutputStream dos = null;

        boolean iniciaSesion = false;

        Optional<Usuario> usuario = null;
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            Random rnd = new Random();
            String codigoGenerado = "0000";
            String correoRecuperarContrasenia = "";
            while (!iniciaSesion) {
                String linea = dis.readUTF();
                Mensaje msgUsuario = Serializador.decodificarMensaje(linea);

                Mensaje msgRespuesta;
                switch (msgUsuario.getTipo()) {
                    case "OBTENER_SALT":
                        //System.out.println("OBTENER_SALT");
                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("ENVIA_SALT");
                        usuario = this.usuarioService.iniciarSesion(msgUsuario.getParams().get(0));
                        msgRespuesta.addParam(usuario.isPresent() ? usuario.get().getSalt(): "nada");

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        break;
                    case "INICIAR_SESION":
                        //System.out.println("INICIAR_SESION");

                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("INICIA_SESION");

                        if (usuario.isPresent() && (usuario.get().getNombreUsuario().equals(msgUsuario.getParams().get(0)) || usuario.get().getCorreo().equals(msgUsuario.getParams().get(0))) && usuario.get().getContrasenia().equals(msgUsuario.getParams().get(1))) {
                            iniciaSesion = true;

                            msgRespuesta.addParam("si");
                            // Esto convierte el objeto Usuario a una cadena con formato JSON
                            ObjectMapper mapper = new ObjectMapper();

                            // Preparo un usuario sin el hash de la contraseña y sin el salt para la sesión
                            UsuarioDTO usuarioPreparado = new UsuarioDTO();
                            usuarioPreparado.parse(usuario.get());
                            usuarioPreparado.setContrasenia("");
                            usuarioPreparado.setSalt("");

                            msgRespuesta.addParam(mapper.writeValueAsString(usuarioPreparado));
                        } else {
                            msgRespuesta.addParam("no");
                        }
                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        break;

                    case "COMPROBAR_DNI":
                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("DNI_EXISTE");

                        if(this.usuarioService.existsUsuarioByDni(msgUsuario.getParams().get(0))){
                            msgRespuesta.addParam("si");
                        }else{
                            msgRespuesta.addParam("no");
                        }

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));

                        break;

                    case "COMPROBAR_NOMUSU_CORREO":
                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("USUARIO_EXISTE");

                        if(
                            this.usuarioService.existsUsuarioByNombreUsuario(msgUsuario.getParams().get(0)) ||
                            this.usuarioService.existsUsuarioByCorreo(msgUsuario.getParams().get(1))
                        ){
                            msgRespuesta.addParam("si");
                        }else{
                            msgRespuesta.addParam("no");
                        }

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        break;

                    case "REGISTRAR_USUARIO":
                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("USUARIO_REGISTRADO");

                        ObjectMapper mapper = new ObjectMapper();
                        Usuario usuarioMapped = mapper.readValue(msgUsuario.getParams().get(0), Usuario.class);

                        Usuario usuarioRegistrar = new Usuario();
                        usuarioRegistrar.parseUsuario(usuarioMapped);

                        this.usuarioService.save(usuarioRegistrar);

                        this.server.enviarCorreoRegistro(usuarioRegistrar.getCorreo(), usuarioRegistrar.getNombre() + " " + usuarioRegistrar.getApellidos());

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        break;

                    case "RECUPERAR_CONTRASENIA":
                        msgRespuesta = new Mensaje();

                        if(this.usuarioService.existsUsuarioByCorreo(msgUsuario.getParams().get(0))){
                            msgRespuesta.setTipo("CODIGO_ENVIADO");
                            correoRecuperarContrasenia = msgUsuario.getParams().get(0);
                            codigoGenerado = String.format("%04d", rnd.nextInt(9998)+1);
                            this.server.enviarCorreoRecuperarContrasenia(
                                    correoRecuperarContrasenia,
                                    this.usuarioService.getNombreCompletoByCorreo(correoRecuperarContrasenia),
                                    codigoGenerado
                            );
                        }else{
                            msgRespuesta.setTipo("CORREO_NO_EXISTE");
                        }

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        break;

                    case "INTENTA_CODIGO":
                        msgRespuesta = new Mensaje();
                        if(codigoGenerado.equals(msgUsuario.getParams().get(0))){
                            msgRespuesta.setTipo("CODIGO_CORRECTO");
                            msgRespuesta.addParam(this.usuarioService.getSaltUsuarioByCorreo(correoRecuperarContrasenia));
                            codigoGenerado = "0000";
                        }else{
                            msgRespuesta.setTipo("CODIGO_INCORRECTO");
                        }

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        break;

                    case "REINICIAR_CONTRASENIA":
                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("CONTRASENIA_REGISTRADA");

                        this.usuarioService.updateContraseniaUsuario(msgUsuario.getParams().get(0), correoRecuperarContrasenia);

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        break;
                }
            }
        } catch (EOFException e) {
            System.out.println("Se cerró el flujo de inicio de sesión");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        if(iniciaSesion){
            this.server.usuarioIniciaSesion(usuario.get().getIdUsuario(), this.socket);
        }
    }
}
