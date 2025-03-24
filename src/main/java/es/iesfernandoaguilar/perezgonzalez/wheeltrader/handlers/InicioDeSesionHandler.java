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
        boolean peticionUsuarioJSON = false;

        // Usuario usuario = new Usuario("joseca", "a1889f685d85d43486198234645c3d06680156d285ec8f1fc511def9a578df29e3a505cbba5790d2b34228d1ac208db16b69dd0f1b370261417dbfcc7da0e4ab");

        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            while (!iniciaSesion || !peticionUsuarioJSON) {
                String linea = dis.readUTF();
                Mensaje msgUsuario = Serializador.decodificarMensaje(linea);

                Mensaje msgRespuesta;
                Optional<Usuario> usuario;
                switch (msgUsuario.getTipo()) {
                    case "OBTENER_SALT":
                        System.out.println("OBTENER_SALT");
                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("ENVIA_SALT");
                        usuario = this.usuarioRepository.findByNombreUsuario(msgUsuario.getParams().get(0));
                        msgRespuesta.addParam(usuario.isPresent() ? usuario.get().getSalt(): "nada");

                        dos.writeUTF(Serializador.codificarMensaje(msgRespuesta));
                        break;
                    case "INICIAR_SESION":
                        System.out.println("INICIAR_SESION");
                        msgRespuesta = new Mensaje();
                        msgRespuesta.setTipo("INICIA_SESION");

                        usuario = this.usuarioRepository.findByNombreUsuario(msgUsuario.getParams().get(0));

                        if (usuario.isPresent() && usuario.get().getNombreUsuario().equals(msgUsuario.getParams().get(0)) && usuario.get().getContrasenia().equals(msgUsuario.getParams().get(1))) {
                            iniciaSesion = true;

                            msgRespuesta.addParam("si");
                            // Eso convierte el objeto Usuario a una cadena con formato JSON
                            ObjectMapper mapper = new ObjectMapper();
                            msgRespuesta.addParam(mapper.writeValueAsString(usuario.get()));
                        } else {
                            msgRespuesta.addParam("no");
                        }
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
