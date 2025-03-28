package es.iesfernandoaguilar.perezgonzalez.wheeltrader.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.UsuarioRepository;
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

public class InicioDeSesionHandler implements Runnable {
    private Socket socket;
    private UsuarioRepository usuarioRepository;
    private ApplicationContext context;

    public InicioDeSesionHandler(Socket socket, ApplicationContext context) {
        this.socket = socket;
        this.context = context;
    }

    @Override
    public void run() {
        this.usuarioRepository = context.getBean(UsuarioRepository.class);

        DataInputStream dis = null;
        DataOutputStream dos = null;

        boolean iniciaSesion = false;

        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            Optional<Usuario> usuario = null;
            while (!iniciaSesion) {
                String linea = dis.readUTF();
                Mensaje msgUsuario = Serializador.decodificarMensaje(linea);

                Mensaje msgRespuesta;
                switch (msgUsuario.getTipo()) {
                    case "OBTENER_SALT":
                        //System.out.println("OBTENER_SALT");
                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("ENVIA_SALT");
                        usuario = this.usuarioRepository.iniciarSesion(msgUsuario.getParams().get(0));
                        msgRespuesta.addParam(usuario.isPresent() ? usuario.get().getSalt(): "nada");

                        System.out.println(msgUsuario.getParams().get(0));
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
                            msgRespuesta.addParam(mapper.writeValueAsString(usuario.get()));
                        } else {
                            msgRespuesta.addParam("no");
                        }
                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        break;

                    case "COMPROBAR_DNI":
                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("DNI_EXISTE");

                        if(this.usuarioRepository.existsUsuarioByDni(msgUsuario.getParams().get(0))){
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
                            this.usuarioRepository.existsUsuarioByNombreUsuario(msgUsuario.getParams().get(0)) ||
                            this.usuarioRepository.existsUsuarioByCorreo(msgUsuario.getParams().get(1))
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

                        this.usuarioRepository.save(usuarioRegistrar);

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        break;
                }
            }
        } catch (EOFException e) {
            System.out.println("Se cerro el flujo de inicio de sesión");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
