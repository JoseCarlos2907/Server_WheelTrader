package es.iesfernandoaguilar.perezgonzalez.wheeltrader;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.handlers.InicioDeSesionHandler;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.UsuarioRepository;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.SecureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.util.Base64;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class Servidor {
    private ServerSocket server;
    private Properties serverProperties;
    private ExecutorService executor;
//    private boolean parar;

    public Servidor() {
        this.serverProperties = new Properties();
        try {
            this.serverProperties.load(new FileInputStream("src/main/resources/conf.properties"));

            this.server = new ServerSocket(Integer.parseInt(serverProperties.getProperty("PORT")));

            this.executor = Executors.newCachedThreadPool();

            System.out.println("Servidor escuchando en el puerto " + serverProperties.getProperty("PORT") + " y en la IP 192.168.1.66");
        } catch (IOException e) {
            e.printStackTrace();
            //System.err.println(e.getMessage());
        }
    }

    @Bean
    CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {

            this.listen(context);
//            Servidor servidor = null;
//            Thread serverThread = null;
//            try(Scanner sc = new Scanner(System.in)) {
//                servidor = new Servidor(usuarioRepository);
//                serverThread = new Thread(this);
//                serverThread.start();
//
//                System.out.println("Escriba 'S' para detener el servidor.");
//                while (true) {
//                    String input = sc.next();
//                    if (input.equalsIgnoreCase("S")) {
//                        break;
//                    }
//                }
//
//                servidor.stop();
//                serverThread.join(); // Espera a que el hilo del servidor termine
//                System.out.println("Servidor detenido correctamente.");
//            }catch (InterruptedException e) {
//                System.out.println("Interrumpido mientras se esperaba la finalización del servidor.");
//                Thread.currentThread().interrupt();
//            }
        };
    }


    public void listen(ApplicationContext context) {
        while (true){
            try {
                Socket socket = this.server.accept();

                this.executor.submit(new InicioDeSesionHandler(socket, context));

                System.out.println("Usuario conectado: " + socket.getPort());
            } catch (SocketTimeoutException ex) {
                continue;
            } catch (IOException e) {
                System.err.println(e.getMessage());
                break;
            }
        }
        System.out.println("Servidor apagado.");
    }

//    private void stop() {
//        System.out.println("Apagando el servidor...");
//        parar = true;
//        try {
//            if (server != null && !server.isClosed()) {
//                server.close();
//            }
//        } catch (IOException e) {
//            System.out.println("Error al cerrar el servidor: " + e.getMessage());
//        }
//    }

    public static void main(String[] args) {
        SpringApplication.run(Servidor.class, args);
    }
}
