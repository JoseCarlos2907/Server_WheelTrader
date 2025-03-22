package es.iesfernandoaguilar.perezgonzalez.wheeltrader;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.UsuarioRepository;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.SecureUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Base64;

@SpringBootApplication
public class WheelTraderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WheelTraderApplication.class, args);
    }

    // Prueba para registrar un usuario
    /*@Bean
    CommandLineRunner commandLineRunner(UsuarioRepository usuarioRepository) {
        return args -> {
            byte[] salt = SecureUtils.getSalt();

            Usuario usuario = new Usuario("joseca","perez","12345678A","joseca",SecureUtils.generate512("joseca", salt),"correo@a.com","correopp@a.com","ACTIVO",Base64.getEncoder().encodeToString(salt),false);

            usuarioRepository.save(usuario);
        };
    }*/
}
