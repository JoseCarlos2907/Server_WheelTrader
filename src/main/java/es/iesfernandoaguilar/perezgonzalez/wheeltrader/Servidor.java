package es.iesfernandoaguilar.perezgonzalez.wheeltrader;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.handlers.InicioDeSesionHandler;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.handlers.UsuarioHandler;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class Servidor implements Runnable {
    private ServerSocket server;
    private Properties serverProperties;
    private ExecutorService executor;
    private boolean parar;
    private ConcurrentHashMap<Long, UsuarioHandler> usuariosHandlers;

    final String correoApp = "wheeltraderapp@gmail.com"; // Este es el correo de Gmail
    final String contraseniaApp = "hrwd pvon rhrz yzoj"; // Esta es la contraseña de aplicación de la cuenta de Gmail
    private Properties correoProperties;

    public Servidor() {
        this.serverProperties = new Properties();
        this.parar = false;

        // Creo las configuraciones para conectar la aplicación al servidor smtp
        this.correoProperties = new Properties();
        this.correoProperties.put("mail.smtp.host", "smtp.gmail.com"); // Especifico el servidor host de correos
        this.correoProperties.put("mail.smtp.port", "587");    // Especifico el puerto
        this.correoProperties.put("mail.smtp.auth", "true");   // Especifico que hay que autentificar
        this.correoProperties.put("mail.smtp.starttls.enable", "true");    // Especifico que sea segura la conexión

        try {
            this.serverProperties.load(new FileInputStream("src/main/resources/conf.properties"));

            this.server = new ServerSocket(Integer.parseInt(serverProperties.getProperty("PORT")));

            this.executor = Executors.newCachedThreadPool();

            this.usuariosHandlers = new ConcurrentHashMap<>();

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

    public void usuarioIniciaSesion(Long usuarioId, Socket socket) {
        UsuarioHandler uHandler = new UsuarioHandler(socket);
        this.usuariosHandlers.put(usuarioId, uHandler);
        this.executor.submit(uHandler);
    }

    public void listen(ApplicationContext context) {
        while (!parar){
            try {
                Socket socket = this.server.accept();

                this.executor.submit(new InicioDeSesionHandler(socket, context, this));

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

            this.executor.close();
        } catch (IOException e) {
            System.out.println("Error al cerrar el servidor: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Error al cerrar el servidor: " + e.getMessage());
        }
    }

    public void enviarCorreoRegistro(String correo, String nombreCompleto){
        String asunto = "Registro exitoso en WheelTrader";

        String texto = "Hola %s,%n%n" +
                "¡Gracias por registrarte en WheelTrader! Esperamos que disfrutes de la experiencia y que encuentres lo que buscas.%n%n"+
                "¡Bienvenido a WheelTrader!%n%n"+
                "Saludos,%n"+
                "El equipo de WheelTrader%n";
        String contenido = String.format(texto, nombreCompleto);

        this.enviarCorreo(asunto, contenido, correo);

        System.out.println("Correo registro enviado con éxito.");
    }

    public void enviarCorreoRecuperarContrasenia(String correo, String nombreCompleto, String codigo){
        String asunto = "Recuperación de Contraseña en WheelTrader";

        String texto = "Hola %s,%n%n" +
                "Hemos recibido una solicitud para restablecer la contraseña de tu cuenta.%n"+
                "Tu código de recuperación es:%n"+
                "%s%n%n" +
                "Saludos,%n"+
                "El equipo de WheelTrader%n";
        String contenido = String.format(texto, nombreCompleto, codigo);

        this.enviarCorreo(asunto, contenido, correo);

        System.out.println("Correo recuperación contraseña enviado con éxito.");
    }

    // Este código se repite cada vez que se envía un correo, entonces lo tengo en una función aparte para no repetir código
    private void enviarCorreo(String asunto, String contenido, String correo){
        // Con esto compruebo las credenciales del correo de la aplicación y creo una sesión para posteriormente crear un mensaje
        Session session = Session.getInstance(this.correoProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correoApp, contraseniaApp);
            }
        });

        try {
            // Creo el mensaje
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(correoApp));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo));
            msg.setSubject(asunto);
            msg.setText(contenido);


            // Envío el correo
            Transport.send(msg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) {
        SpringApplication.run(Servidor.class, args);
    }
}
