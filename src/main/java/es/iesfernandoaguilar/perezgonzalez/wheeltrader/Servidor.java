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
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Servidor implements Runnable {
    private ServerSocket server;
    private Properties serverProperties;
    private ExecutorService executor;
    private boolean parar;

    public Servidor() {
        this.serverProperties = new Properties();
        this.parar = false;
        try {
            this.serverProperties.load(new FileInputStream("src/main/resources/conf.properties"));

            this.server = new ServerSocket(Integer.parseInt(serverProperties.getProperty("PORT")));

            this.executor = Executors.newCachedThreadPool();

            System.out.println("Servidor escuchando en el puerto " + serverProperties.getProperty("PORT") + " y en la IP 192.168.1.66");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Bean
    CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {
            new Thread(this).start();
            this.listen(context);
        };
    }


    public void listen(ApplicationContext context) {
        while (!parar){
            try {
                Socket socket = this.server.accept();

                this.executor.submit(new InicioDeSesionHandler(socket, context));

                System.out.println("Usuario conectado: " + socket.getPort());
            } catch (IOException e) {
                System.err.println(e.getMessage());
                break;
            }
        }
        System.out.println("Servidor apagado.");
    }

    private void stop() {
        System.out.println("Apagando el servidor...");
        parar = true;
        try {
            if (server != null && !server.isClosed()) {
                server.close();
            }

            this.executor.shutdown();

            this.executor.awaitTermination(3, TimeUnit.SECONDS);
        } catch (IOException e) {
            System.out.println("Error al cerrar el servidor: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Error al cerrar el servidor: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Servidor.class, args);
    }

    @Override
    public void run() {
        try(Scanner sc = new Scanner(System.in)) {

            System.out.println("Escriba 'S' para detener el servidor.");
            while (true) {
                String input = sc.next();
                if (input.equalsIgnoreCase("S")) {
                    break;
                }
            }

            this.stop();
            System.out.println("Servidor detenido correctamente.");
        }
    }
}
