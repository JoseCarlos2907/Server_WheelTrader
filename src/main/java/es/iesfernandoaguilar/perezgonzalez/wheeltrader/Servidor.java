package es.iesfernandoaguilar.perezgonzalez.wheeltrader;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.handlers.InicioDeSesionHandler;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.UsuarioRepository;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.SecureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class Servidor {
    private ServerSocket server;
    private Properties serverProperties;
    private ExecutorService executor;

    public Servidor() {
        this.serverProperties = new Properties();
        try {
            this.serverProperties.load(new FileInputStream("src/main/resources/conf.properties"));

            this.server = new ServerSocket(Integer.parseInt(serverProperties.getProperty("PORT")));

            this.executor = Executors.newCachedThreadPool();

            System.out.println("Servidor escuchando en el puerto " + serverProperties.getProperty("PORT"));
        } catch (IOException e) {
            e.printStackTrace();
            //System.err.println(e.getMessage());
        }
    }

    @Bean
    CommandLineRunner commandLineRunner(UsuarioRepository usuarioRepository) {
        return args -> {
            /*Servidor s = new Servidor(usuarioRepository);
            s.listen();*/

            System.out.println(usuarioRepository == null);
            System.out.println(usuarioRepository.findByNombreUsuario("joseca").get().getSalt());

            this.listen(usuarioRepository);
        };
    }

    public void listen(UsuarioRepository usuarioRepository) {
        while (true){
            try {
                Socket socket = this.server.accept();

                this.executor.submit(new InicioDeSesionHandler(socket, usuarioRepository));

                System.out.println("Usuario conectado: " + socket.getPort());
            } catch (IOException e) {
                System.err.println(e.getMessage());
                break;
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Servidor.class, args);
    }
}
