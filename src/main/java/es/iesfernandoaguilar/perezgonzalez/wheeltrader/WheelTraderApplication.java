package es.iesfernandoaguilar.perezgonzalez.wheeltrader;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.UsuarioDTO;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.*;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.UsuarioRepository;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices.*;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.SecureUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;

@SpringBootApplication
public class WheelTraderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WheelTraderApplication.class, args);
    }

    // Prueba para registrar un usuario
/*    @Bean
    CommandLineRunner commandLineRunner(UsuarioRepository usuarioRepository) {
        return args -> {
            byte[] salt = SecureUtils.getSalt();
//
//            Usuario usuario = new Usuario("joseca","perez","12345678A","joseca",SecureUtils.generate512("joseca", salt),"correo@a.com","correopp@a.com","ACTIVO",Base64.getEncoder().encodeToString(salt),false);

            Usuario usuario1 = new Usuario("joseca","perez","12345678A","c/Mi Casa n9", "joseca","68b84cd44b24aca6d7ca776cf79fb80a7d2f579a941811aab2761642a70724ba5d2fc02c64e4b234614d7de43980306570b194430634aa1e3741044623e7c83e","correo@a.com","correopp@a.com", "5ZGUVfUls/mtpeKsW2P0iQ==",true);
            Usuario usuario2 = new Usuario("prueba","crr1","22345678A","c/Mi Casa n9", "pruebacrr1", SecureUtils.generate512("Prueba123", salt),"prueba1portalgp@gmail.com","correo.pp@a.com", Base64.getEncoder().encodeToString(salt),false);

            usuarioRepository.save(usuario1);
            usuarioRepository.save(usuario2);
        };
    }*/

    // Prueba para guardar anuncios junto a sus valores, caracteristicas y tipo de vehiculo
    /*@Bean
    CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {
            // Creo la característica Puertas
            Caracteristica caracteristicaPuertas = new Caracteristica("Puertas", "5", "2");

            // Creo el tipo Coche
            TipoVehiculo tipoVehiculoCoche = new TipoVehiculo("Coche");

            // Creo la relación tipoVehiculo_Caracteristica
            TipoVehiculo_Caracteristica relacionCochePuertas = new TipoVehiculo_Caracteristica(true);
            relacionCochePuertas.setTipoVehiculo(tipoVehiculoCoche);
            relacionCochePuertas.setCaracteristica(caracteristicaPuertas);

            // Creo el valor de característica y asigno la propia caracteristica
            ValorCaracteristica valorPuertas = new ValorCaracteristica("5");
            valorPuertas.setCaracteristica(caracteristicaPuertas);

            // Y finalmente creo el anuncio y lo relaciono con el tipo y su caracteristica
            Anuncio anuncio = new Anuncio(LocalDateTime.now(), "Coche de ejemplo", 9999.0, "Cádiz", "Medina-Sidonia", "1234ABC", "1234567890123");
            anuncio.setTipoVehiculo(tipoVehiculoCoche);
            anuncio.addValoresCaracteristica(valorPuertas);

            // Los save
            // Caracteristica, TipoVehiculo, TipoVehiculo_Caracteristica, ValorCaracteristica y Anuncio
            CaracteristicaService caracteristicaService = context.getBean(CaracteristicaService.class);
            TipoVehiculoService tipoVehiculoService = context.getBean(TipoVehiculoService.class);
            TipoVehiculoCaracteristicaService tipoVehiculoCaracteristicaService = context.getBean(TipoVehiculoCaracteristicaService.class);
            ValorCaracteristicaService valorCaracteristicaService = context.getBean(ValorCaracteristicaService.class);
            AnuncioService anuncioService = context.getBean(AnuncioService.class);
            
            caracteristicaService.save(caracteristicaPuertas);
            tipoVehiculoService.save(tipoVehiculoCoche);
            tipoVehiculoCaracteristicaService.save(relacionCochePuertas);
            anuncioService.save(anuncio);
            valorCaracteristicaService.save(valorPuertas);
        };
    }*/

    // Publicar un anuncio (Doy por hecho de que ya está creado el objeto)
    /*@Bean
    CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {
            UsuarioService usuarioService = context.getBean(UsuarioService.class);
            AnuncioService anuncioService = context.getBean(AnuncioService.class);

            Usuario usuario = usuarioService.findById(1);
            Anuncio anuncio = anuncioService.findById(1);

            usuario.setAnunciosPublicados(new ArrayList<>());
            usuario.addAnuncioPublicado(anuncio);

            anuncioService.save(anuncio);
            usuarioService.save(usuario);
        };
    }*/

    @Bean
    CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {
            UsuarioService usuarioService = context.getBean(UsuarioService.class);

            Usuario usuario = usuarioService.findById(1);

            ObjectMapper mapper = new ObjectMapper();

            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.parse(usuario);

            System.out.println(mapper.writeValueAsString(usuarioDTO));
        };
    }
}
