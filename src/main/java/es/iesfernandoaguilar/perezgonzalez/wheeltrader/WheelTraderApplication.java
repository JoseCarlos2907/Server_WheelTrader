package es.iesfernandoaguilar.perezgonzalez.wheeltrader;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.Filtros.FiltroTodoDTO;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.*;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SpringBootApplication
public class WheelTraderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WheelTraderApplication.class, args);
    }

    // Prueba para registrar un usuario
    /*@Bean
    CommandLineRunner commandLineRunner(UsuarioRepository usuarioRepository) {
        return args -> {
            byte[] salt1 = SecureUtils.getSalt();
            byte[] salt2 = SecureUtils.getSalt();

            Usuario usuario1 = new Usuario("joseca","perez","12345678A","c/Mi Casa n9", "joseca", SecureUtils.generate512("joseca", salt1),"correo@a.com","correopp@a.com", Base64.getEncoder().encodeToString(salt1),true);
            Usuario usuario2 = new Usuario("prueba","crr1","22345678A","c/Mi Casa n9", "pruebacrr1", SecureUtils.generate512("Prueba123", salt2),"prueba1portalgp@gmail.com","correo.pp@a.com", Base64.getEncoder().encodeToString(salt2),false);

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
            Anuncio anuncio = new Anuncio("Coche de ejemplo", 9999.0, "Cádiz", "Medina-Sidonia", "1234ABC", "1234567890123");
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

    // Prueba de parseo de un usuario
    /*@Bean
    CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {
            UsuarioService usuarioService = context.getBean(UsuarioService.class);

            Usuario usuario = usuarioService.findById(1);

            ObjectMapper mapper = new ObjectMapper();

            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.parse(usuario);

            System.out.println(mapper.writeValueAsString(usuarioDTO));
        };
    }*/

    // Cargar los tipos de vehículos y características en la BD
    /*@Bean
    CommandLineRunner commandLineRunner(ApplicationContext context, TipoVehiculoCaracteristicaService tipoVehiculoCaracteristicaService) {
        return args -> {
            // Services
            TipoVehiculoService tipoVehiculoService = context.getBean(TipoVehiculoService.class);
            CaracteristicaService caracteristicaService = context.getBean(CaracteristicaService.class);
            TipoVehiculoCaracteristicaService tipoCaracteristicaService = context.getBean(TipoVehiculoCaracteristicaService.class);

            // Tipos de vehículos
            TipoVehiculo tipoVehiculoCoche = new TipoVehiculo("Coche");
            TipoVehiculo tipoVehiculoMoto = new TipoVehiculo("Moto");
            TipoVehiculo tipoVehiculoCamioneta = new TipoVehiculo("Camioneta");
            TipoVehiculo tipoVehiculoCamion = new TipoVehiculo("Camion");
            TipoVehiculo tipoVehiculoMaquinaria = new TipoVehiculo("Maquinaria");

            tipoVehiculoService.save(tipoVehiculoCoche);
            tipoVehiculoService.save(tipoVehiculoMoto);
            tipoVehiculoService.save(tipoVehiculoCamioneta);
            tipoVehiculoService.save(tipoVehiculoCamion);
            tipoVehiculoService.save(tipoVehiculoMaquinaria);


            // Características para el tipo "Coche"
            Caracteristica caracteristicaMarcaCoches = new Caracteristica("Marca_Coche", TipoDatoCaracteristica.TEXTO, 0, 0, null);
            Caracteristica caracteristicaModeloCoches = new Caracteristica("Modelo_Coche", TipoDatoCaracteristica.TEXTO, 0, 0, null);
            Caracteristica caracteristicaCVCoches = new Caracteristica("CV_Coche", TipoDatoCaracteristica.NUMERICO, 1500, 40, null);
            Caracteristica caracteristicaAnioCoches = new Caracteristica("Anio_Coche", TipoDatoCaracteristica.NUMERICO, 2025, 1950, null);
            Caracteristica caracteristicaKmCoches = new Caracteristica("KM_Coche", TipoDatoCaracteristica.NUMERICO, 2000000, 0, null);
            Caracteristica caracteristicaTipoCombustibleCoches = new Caracteristica("TipoCombustible_Coche", TipoDatoCaracteristica.TEXTO, 0, 0, "Gasolina,Electrico,Diesel,Hibrido");
            Caracteristica caracteristicaTransmisionCoches = new Caracteristica("Transmision_Coche", TipoDatoCaracteristica.TEXTO, 0, 0, "Manual,Automatica");
            Caracteristica caracteristicaMarchasCoches = new Caracteristica("Marchas_Coche", TipoDatoCaracteristica.NUMERICO, 10, 3, null);
            Caracteristica caracteristicaPuertasCoches = new Caracteristica("Puertas_Coche", TipoDatoCaracteristica.NUMERICO, 5,2, null);

            caracteristicaService.save(caracteristicaMarcaCoches);
            caracteristicaService.save(caracteristicaModeloCoches);
            caracteristicaService.save(caracteristicaCVCoches);
            caracteristicaService.save(caracteristicaAnioCoches);
            caracteristicaService.save(caracteristicaKmCoches);
            caracteristicaService.save(caracteristicaTipoCombustibleCoches);
            caracteristicaService.save(caracteristicaTransmisionCoches);
            caracteristicaService.save(caracteristicaMarchasCoches);
            caracteristicaService.save(caracteristicaPuertasCoches);


            // Características para el tipo "Moto"
            Caracteristica caracteristicaMarcaMotos = new Caracteristica("Marca_Moto", TipoDatoCaracteristica.TEXTO, 0, 0, null);
            Caracteristica caracteristicaModeloMotos = new Caracteristica("Modelo_Moto", TipoDatoCaracteristica.TEXTO, 0, 0, null);
            Caracteristica caracteristicaCilindradaMotos = new Caracteristica("Cilindrada_Moto", TipoDatoCaracteristica.NUMERICO, 2000, 50, null);
            Caracteristica caracteristicaAnioMotos = new Caracteristica("Anio_Moto", TipoDatoCaracteristica.NUMERICO, 2025, 1950, null);
            Caracteristica caracteristicaKmMotos = new Caracteristica("KM_Moto", TipoDatoCaracteristica.NUMERICO, 1000000, 0, null);
            Caracteristica caracteristicaTipoCombustibleMotos = new Caracteristica("TipoCombustible_Moto", TipoDatoCaracteristica.TEXTO, 0, 0, "Gasolina,Electrico");
            Caracteristica caracteristicaMarchasMotos = new Caracteristica("Marchas_Moto", TipoDatoCaracteristica.NUMERICO, 6, 1, null);

            caracteristicaService.save(caracteristicaMarcaMotos);
            caracteristicaService.save(caracteristicaModeloMotos);
            caracteristicaService.save(caracteristicaCilindradaMotos);
            caracteristicaService.save(caracteristicaAnioMotos);
            caracteristicaService.save(caracteristicaKmMotos);
            caracteristicaService.save(caracteristicaTipoCombustibleMotos);
            caracteristicaService.save(caracteristicaMarchasMotos);


            // Características para el tipo "Camioneta"
            Caracteristica caracteristicaMarcaCamionetas = new Caracteristica("Marca_Camioneta", TipoDatoCaracteristica.TEXTO, 0, 0, null);
            Caracteristica caracteristicaModeloCamionetas = new Caracteristica("Modelo_Camioneta", TipoDatoCaracteristica.TEXTO, 0, 0, null);
            Caracteristica caracteristicaTipoTraccionCamionetas = new Caracteristica("TipoTraccion_Camioneta", TipoDatoCaracteristica.TEXTO, 0, 0, "4x2,4x4");
            Caracteristica caracteristicaAnioCamionetas = new Caracteristica("Anio_Camioneta", TipoDatoCaracteristica.NUMERICO, 2025, 1950, null);
            Caracteristica caracteristicaKmCamionetas = new Caracteristica("KM_Camioneta", TipoDatoCaracteristica.NUMERICO, 1500000, 0, null);
            Caracteristica caracteristicaTipoCombustibleCamionetas = new Caracteristica("TipoCombustible_Camioneta", TipoDatoCaracteristica.TEXTO, 0, 0, "Gasoleo,Electrico");
            Caracteristica caracteristicaCargaKgCamionetas = new Caracteristica("CargaKg_Camioneta", TipoDatoCaracteristica.NUMERICO, 2000, 200, null);
            Caracteristica caracteristicaCVCamionetas = new Caracteristica("CV_Camioneta", TipoDatoCaracteristica.NUMERICO, 400, 50, null);
            Caracteristica caracteristicaMarchasCamionetas = new Caracteristica("Marchas_Camioneta", TipoDatoCaracteristica.NUMERICO, 10, 4, null);
            Caracteristica caracteristicaPuertasCamionetas = new Caracteristica("Puertas_Camioneta", TipoDatoCaracteristica.NUMERICO, 5, 2, null);

            caracteristicaService.save(caracteristicaMarcaCamionetas);
            caracteristicaService.save(caracteristicaModeloCamionetas);
            caracteristicaService.save(caracteristicaTipoTraccionCamionetas);
            caracteristicaService.save(caracteristicaAnioCamionetas);
            caracteristicaService.save(caracteristicaKmCamionetas);
            caracteristicaService.save(caracteristicaTipoCombustibleCamionetas);
            caracteristicaService.save(caracteristicaCargaKgCamionetas);
            caracteristicaService.save(caracteristicaCVCamionetas);
            caracteristicaService.save(caracteristicaMarchasCamionetas);
            caracteristicaService.save(caracteristicaPuertasCamionetas);

            // Características para el tipo "Camión"
            Caracteristica caracteristicaMarcaCamiones = new Caracteristica("Marca_Camion", TipoDatoCaracteristica.TEXTO, 0, 0, null);
            Caracteristica caracteristicaModeloCamiones = new Caracteristica("Modelo_Camion", TipoDatoCaracteristica.TEXTO, 0, 0, null);
            Caracteristica caracteristicaAnioCamiones = new Caracteristica("Anio_Camion", TipoDatoCaracteristica.NUMERICO, 2025, 1950, null);
            Caracteristica caracteristicaKmCamiones = new Caracteristica("KM_Camion", TipoDatoCaracteristica.NUMERICO, 2000000, 0, null);
            Caracteristica caracteristicaCargaKgCamiones = new Caracteristica("CargaKg_Camion", TipoDatoCaracteristica.NUMERICO, 40000, 1000, null);
            Caracteristica caracteristicaTipoCombustibleCamiones = new Caracteristica("TipoCombustible_Camion", TipoDatoCaracteristica.TEXTO, 0, 0, "Diesel,Electrico,GNL,Hidrogeno");
            Caracteristica caracteristicaCVCamiones = new Caracteristica("CV_Camion", TipoDatoCaracteristica.NUMERICO, 800, 100, null);
            Caracteristica caracteristicaMarchasCamiones = new Caracteristica("Marchas_Camion", TipoDatoCaracteristica.NUMERICO, 18, 5, null);

            caracteristicaService.save(caracteristicaMarcaCamiones);
            caracteristicaService.save(caracteristicaModeloCamiones);
            caracteristicaService.save(caracteristicaAnioCamiones);
            caracteristicaService.save(caracteristicaKmCamiones);
            caracteristicaService.save(caracteristicaCargaKgCamiones);
            caracteristicaService.save(caracteristicaTipoCombustibleCamiones);
            caracteristicaService.save(caracteristicaCVCamiones);
            caracteristicaService.save(caracteristicaMarchasCamiones);


            // Características para el tipo "Maquinaria"
            Caracteristica caracteristicaMarcaMaquinarias = new Caracteristica("Marca_Maquinaria", TipoDatoCaracteristica.TEXTO, 0, 0, null);
            Caracteristica caracteristicaModeloMaquinarias = new Caracteristica("Modelo_Maquinaria", TipoDatoCaracteristica.TEXTO, 0, 0, null);
            Caracteristica caracteristicaAnioMaquinarias = new Caracteristica("Anio_Maquinaria", TipoDatoCaracteristica.NUMERICO, 2025, 1950, null);
            Caracteristica caracteristicaTipoCombustibleMaquinarias = new Caracteristica("TipoCombustible_Maquinaria", TipoDatoCaracteristica.TEXTO, 0, 0, "Diesel,Gasolina,Electrico,GLP");

            caracteristicaService.save(caracteristicaMarcaMaquinarias);
            caracteristicaService.save(caracteristicaModeloMaquinarias);
            caracteristicaService.save(caracteristicaAnioMaquinarias);
            caracteristicaService.save(caracteristicaTipoCombustibleMaquinarias);


            // Relaciones características de "Coche"
            TipoVehiculo_Caracteristica relacionCoche_Marca = new TipoVehiculo_Caracteristica(true);
            relacionCoche_Marca.setTipoVehiculo(tipoVehiculoCoche);
            relacionCoche_Marca.setCaracteristica(caracteristicaMarcaCoches);
            tipoVehiculoCaracteristicaService.save(relacionCoche_Marca);

            TipoVehiculo_Caracteristica relacionCoche_Modelo = new TipoVehiculo_Caracteristica(true);
            relacionCoche_Modelo.setTipoVehiculo(tipoVehiculoCoche);
            relacionCoche_Modelo.setCaracteristica(caracteristicaModeloCoches);
            tipoVehiculoCaracteristicaService.save(relacionCoche_Modelo);

            TipoVehiculo_Caracteristica relacionCoche_CV = new TipoVehiculo_Caracteristica(true);
            relacionCoche_CV.setTipoVehiculo(tipoVehiculoCoche);
            relacionCoche_CV.setCaracteristica(caracteristicaCVCoches);
            tipoVehiculoCaracteristicaService.save(relacionCoche_CV);

            TipoVehiculo_Caracteristica relacionCoche_Anio = new TipoVehiculo_Caracteristica(true);
            relacionCoche_Anio.setTipoVehiculo(tipoVehiculoCoche);
            relacionCoche_Anio.setCaracteristica(caracteristicaAnioCoches);
            tipoVehiculoCaracteristicaService.save(relacionCoche_Anio);

            TipoVehiculo_Caracteristica relacionCoche_Km = new TipoVehiculo_Caracteristica(true);
            relacionCoche_Km.setTipoVehiculo(tipoVehiculoCoche);
            relacionCoche_Km.setCaracteristica(caracteristicaKmCoches);
            tipoVehiculoCaracteristicaService.save(relacionCoche_Km);

            TipoVehiculo_Caracteristica relacionCoche_TipoCombustible = new TipoVehiculo_Caracteristica(true);
            relacionCoche_TipoCombustible.setTipoVehiculo(tipoVehiculoCoche);
            relacionCoche_TipoCombustible.setCaracteristica(caracteristicaTipoCombustibleCoches);
            tipoVehiculoCaracteristicaService.save(relacionCoche_TipoCombustible);

            TipoVehiculo_Caracteristica relacionCoche_Transmision = new TipoVehiculo_Caracteristica(true);
            relacionCoche_Transmision.setTipoVehiculo(tipoVehiculoCoche);
            relacionCoche_Transmision.setCaracteristica(caracteristicaTransmisionCoches);
            tipoVehiculoCaracteristicaService.save(relacionCoche_Transmision);

            TipoVehiculo_Caracteristica relacionCoche_Marchas = new TipoVehiculo_Caracteristica(true);
            relacionCoche_Marchas.setTipoVehiculo(tipoVehiculoCoche);
            relacionCoche_Marchas.setCaracteristica(caracteristicaMarchasCoches);
            tipoVehiculoCaracteristicaService.save(relacionCoche_Marchas);

            TipoVehiculo_Caracteristica relacionCoche_Puertas = new TipoVehiculo_Caracteristica(false);
            relacionCoche_Puertas.setTipoVehiculo(tipoVehiculoCoche);
            relacionCoche_Puertas.setCaracteristica(caracteristicaPuertasCoches);
            tipoVehiculoCaracteristicaService.save(relacionCoche_Puertas);

            // Relaciones características de "Moto"
            TipoVehiculo_Caracteristica relacionMoto_Marca = new TipoVehiculo_Caracteristica(true);
            relacionMoto_Marca.setTipoVehiculo(tipoVehiculoMoto);
            relacionMoto_Marca.setCaracteristica(caracteristicaMarcaMotos);
            tipoVehiculoCaracteristicaService.save(relacionMoto_Marca);

            TipoVehiculo_Caracteristica relacionMoto_Modelo = new TipoVehiculo_Caracteristica(true);
            relacionMoto_Modelo.setTipoVehiculo(tipoVehiculoMoto);
            relacionMoto_Modelo.setCaracteristica(caracteristicaModeloMotos);
            tipoVehiculoCaracteristicaService.save(relacionMoto_Modelo);

            TipoVehiculo_Caracteristica relacionMoto_Cilindrada = new TipoVehiculo_Caracteristica(true);
            relacionMoto_Cilindrada.setTipoVehiculo(tipoVehiculoMoto);
            relacionMoto_Modelo.setCaracteristica(caracteristicaCilindradaMotos);
            tipoVehiculoCaracteristicaService.save(relacionMoto_Cilindrada);

            TipoVehiculo_Caracteristica relacionMoto_Anio = new TipoVehiculo_Caracteristica(true);
            relacionMoto_Anio.setTipoVehiculo(tipoVehiculoMoto);
            relacionMoto_Anio.setCaracteristica(caracteristicaAnioMotos);
            tipoVehiculoCaracteristicaService.save(relacionMoto_Anio);

            TipoVehiculo_Caracteristica relacionMoto_Km = new TipoVehiculo_Caracteristica(true);
            relacionMoto_Km.setTipoVehiculo(tipoVehiculoMoto);
            relacionMoto_Km.setCaracteristica(caracteristicaKmMotos);
            tipoVehiculoCaracteristicaService.save(relacionMoto_Km);

            TipoVehiculo_Caracteristica relacionMoto_TipoCombustible = new TipoVehiculo_Caracteristica(true);
            relacionMoto_TipoCombustible.setTipoVehiculo(tipoVehiculoMoto);
            relacionMoto_TipoCombustible.setCaracteristica(caracteristicaTipoCombustibleMotos);
            tipoVehiculoCaracteristicaService.save(relacionMoto_TipoCombustible);

            TipoVehiculo_Caracteristica relacionMoto_Marchas = new TipoVehiculo_Caracteristica(true);
            relacionMoto_Marchas.setTipoVehiculo(tipoVehiculoMoto);
            relacionMoto_Marchas.setCaracteristica(caracteristicaMarchasMotos);
            tipoVehiculoCaracteristicaService.save(relacionMoto_Marchas);

            // Relaciones características de "Camioneta"
            TipoVehiculo_Caracteristica relacionCamioneta_Marca = new TipoVehiculo_Caracteristica(true);
            relacionCamioneta_Marca.setTipoVehiculo(tipoVehiculoCamioneta);
            relacionCamioneta_Marca.setCaracteristica(caracteristicaMarcaCamionetas);
            tipoVehiculoCaracteristicaService.save(relacionCamioneta_Marca);

            TipoVehiculo_Caracteristica relacionCamioneta_Modelo = new TipoVehiculo_Caracteristica(true);
            relacionCamioneta_Modelo.setTipoVehiculo(tipoVehiculoCamioneta);
            relacionCamioneta_Modelo.setCaracteristica(caracteristicaModeloCamionetas);
            tipoVehiculoCaracteristicaService.save(relacionCamioneta_Modelo);

            TipoVehiculo_Caracteristica relacionCamioneta_TipoTraccion = new TipoVehiculo_Caracteristica(true);
            relacionCamioneta_TipoTraccion.setTipoVehiculo(tipoVehiculoCamioneta);
            relacionCamioneta_TipoTraccion.setCaracteristica(caracteristicaTipoTraccionCamionetas);
            tipoVehiculoCaracteristicaService.save(relacionCamioneta_TipoTraccion);

            TipoVehiculo_Caracteristica relacionCamioneta_Anio = new TipoVehiculo_Caracteristica(true);
            relacionCamioneta_Anio.setTipoVehiculo(tipoVehiculoCamioneta);
            relacionCamioneta_Anio.setCaracteristica(caracteristicaAnioCamionetas);
            tipoVehiculoCaracteristicaService.save(relacionCamioneta_Anio);

            TipoVehiculo_Caracteristica relacionCamioneta_Km = new TipoVehiculo_Caracteristica(true);
            relacionCamioneta_Km.setTipoVehiculo(tipoVehiculoCamioneta);
            relacionCamioneta_Km.setCaracteristica(caracteristicaKmCamionetas);
            tipoVehiculoCaracteristicaService.save(relacionCamioneta_Km);

            TipoVehiculo_Caracteristica relacionCamioneta_TipoCombustible = new TipoVehiculo_Caracteristica(true);
            relacionCamioneta_TipoCombustible.setTipoVehiculo(tipoVehiculoCamioneta);
            relacionCamioneta_TipoCombustible.setCaracteristica(caracteristicaTipoCombustibleCamionetas);
            tipoVehiculoCaracteristicaService.save(relacionCamioneta_TipoCombustible);

            TipoVehiculo_Caracteristica relacionCamioneta_CargaKg = new TipoVehiculo_Caracteristica(true);
            relacionCamioneta_CargaKg.setTipoVehiculo(tipoVehiculoCamioneta);
            relacionCamioneta_CargaKg.setCaracteristica(caracteristicaCargaKgCamionetas);
            tipoVehiculoCaracteristicaService.save(relacionCamioneta_CargaKg);

            TipoVehiculo_Caracteristica relacionCamioneta_CV = new TipoVehiculo_Caracteristica(true);
            relacionCamioneta_CV.setTipoVehiculo(tipoVehiculoCamioneta);
            relacionCamioneta_CV.setCaracteristica(caracteristicaCVCamionetas);
            tipoVehiculoCaracteristicaService.save(relacionCamioneta_CV);

            TipoVehiculo_Caracteristica relacionCamioneta_Marchas = new TipoVehiculo_Caracteristica(true);
            relacionCamioneta_Marchas.setTipoVehiculo(tipoVehiculoCamioneta);
            relacionCamioneta_Marchas.setCaracteristica(caracteristicaMarchasCamionetas);
            tipoVehiculoCaracteristicaService.save(relacionCamioneta_Marchas);

            TipoVehiculo_Caracteristica relacionCamioneta_Puertas = new TipoVehiculo_Caracteristica(false);
            relacionCamioneta_Puertas.setTipoVehiculo(tipoVehiculoCamioneta);
            relacionCamioneta_Puertas.setCaracteristica(caracteristicaPuertasCamionetas);
            tipoVehiculoCaracteristicaService.save(relacionCamioneta_Puertas);

            // Relaciones características de "Camión"
            TipoVehiculo_Caracteristica relacionCamion_Marca = new TipoVehiculo_Caracteristica(true);
            relacionCamion_Marca.setTipoVehiculo(tipoVehiculoCamion);
            relacionCamion_Marca.setCaracteristica(caracteristicaMarcaCamiones);
            tipoVehiculoCaracteristicaService.save(relacionCamion_Marca);

            TipoVehiculo_Caracteristica relacionCamion_Modelo = new TipoVehiculo_Caracteristica(true);
            relacionCamion_Modelo.setTipoVehiculo(tipoVehiculoCamion);
            relacionCamion_Modelo.setCaracteristica(caracteristicaModeloCamiones);
            tipoVehiculoCaracteristicaService.save(relacionCamion_Modelo);

            TipoVehiculo_Caracteristica relacionCamion_Anio = new TipoVehiculo_Caracteristica(true);
            relacionCamion_Anio.setTipoVehiculo(tipoVehiculoCamion);
            relacionCamion_Anio.setCaracteristica(caracteristicaAnioCamiones);
            tipoVehiculoCaracteristicaService.save(relacionCamion_Anio);

            TipoVehiculo_Caracteristica relacionCamion_Km = new TipoVehiculo_Caracteristica(true);
            relacionCamion_Km.setTipoVehiculo(tipoVehiculoCamion);
            relacionCamion_Km.setCaracteristica(caracteristicaKmCamiones);
            tipoVehiculoCaracteristicaService.save(relacionCamion_Km);

            TipoVehiculo_Caracteristica relacionCamion_CargaKg = new TipoVehiculo_Caracteristica(true);
            relacionCamion_CargaKg.setTipoVehiculo(tipoVehiculoCamion);
            relacionCamion_CargaKg.setCaracteristica(caracteristicaCargaKgCamiones);
            tipoVehiculoCaracteristicaService.save(relacionCamion_CargaKg);

            TipoVehiculo_Caracteristica relacionCamion_TipoCombustible = new TipoVehiculo_Caracteristica(true);
            relacionCamion_TipoCombustible.setTipoVehiculo(tipoVehiculoCamion);
            relacionCamion_TipoCombustible.setCaracteristica(caracteristicaTipoCombustibleCamiones);
            tipoVehiculoCaracteristicaService.save(relacionCamion_TipoCombustible);

            TipoVehiculo_Caracteristica relacionCamion_CV = new TipoVehiculo_Caracteristica(true);
            relacionCamion_CV.setTipoVehiculo(tipoVehiculoCamion);
            relacionCamion_CV.setCaracteristica(caracteristicaCVCamiones);
            tipoVehiculoCaracteristicaService.save(relacionCamion_CV);

            TipoVehiculo_Caracteristica relacionCamion_Marchas = new TipoVehiculo_Caracteristica(true);
            relacionCamion_Marchas.setTipoVehiculo(tipoVehiculoCamion);
            relacionCamion_Marchas.setCaracteristica(caracteristicaMarchasCamiones);
            tipoVehiculoCaracteristicaService.save(relacionCamion_Marchas);

            // Relaciones características de "Maquinaria"
            TipoVehiculo_Caracteristica relacionMaquinaria_Marca = new TipoVehiculo_Caracteristica(true);
            relacionMaquinaria_Marca.setTipoVehiculo(tipoVehiculoMaquinaria);
            relacionMaquinaria_Marca.setCaracteristica(caracteristicaMarcaMaquinarias);
            tipoVehiculoCaracteristicaService.save(relacionMaquinaria_Marca);

            TipoVehiculo_Caracteristica relacionMaquinaria_Modelo = new TipoVehiculo_Caracteristica(true);
            relacionMaquinaria_Modelo.setTipoVehiculo(tipoVehiculoMaquinaria);
            relacionMaquinaria_Modelo.setCaracteristica(caracteristicaModeloMaquinarias);
            tipoVehiculoCaracteristicaService.save(relacionMaquinaria_Modelo);

            TipoVehiculo_Caracteristica relacionMaquinaria_Anio = new TipoVehiculo_Caracteristica(true);
            relacionMaquinaria_Anio.setTipoVehiculo(tipoVehiculoMaquinaria);
            relacionMaquinaria_Anio.setCaracteristica(caracteristicaAnioMaquinarias);
            tipoVehiculoCaracteristicaService.save(relacionMaquinaria_Anio);

            TipoVehiculo_Caracteristica relacionMaquinaria_TipoCombustible = new TipoVehiculo_Caracteristica(true);
            relacionMaquinaria_TipoCombustible.setTipoVehiculo(tipoVehiculoMaquinaria);
            relacionMaquinaria_TipoCombustible.setCaracteristica(caracteristicaTipoCombustibleMaquinarias);
            tipoVehiculoCaracteristicaService.save(relacionMaquinaria_TipoCombustible);
        };
    }*/

    // Prueba de comprobaciones para una lista de valores
    /*@Bean
    CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {
            CaracteristicaService caracteristicaService = context.getBean(CaracteristicaService.class);

            AnuncioDTO anuncioDTO = new AnuncioDTO(LocalDateTime.now(), "Coche de ejemplo", 9999.0, "Cádiz", "Medina-Sidonia", "1234ABC", "1234567890123");
            anuncioDTO.setTipoVehiculo("Coche");

            ValorCaracteristicaDTO valorCaracteristicaDTO_Marca = new ValorCaracteristicaDTO("Opel");
            valorCaracteristicaDTO_Marca.setNombreCaracteristica("Marca_Coche");

            ValorCaracteristicaDTO valorCaracteristicaDTO_Modelo = new ValorCaracteristicaDTO("Astra");
            valorCaracteristicaDTO_Modelo.setNombreCaracteristica("Modelo_Coche");

            ValorCaracteristicaDTO valorCaracteristicaDTO_CV = new ValorCaracteristicaDTO("150");
            valorCaracteristicaDTO_CV.setNombreCaracteristica("CV_Coche");

            ValorCaracteristicaDTO valorCaracteristicaDTO_Anio = new ValorCaracteristicaDTO("2007");
            valorCaracteristicaDTO_Anio.setNombreCaracteristica("Anio_Coche");

            ValorCaracteristicaDTO valorCaracteristicaDTO_KM = new ValorCaracteristicaDTO("400000");
            valorCaracteristicaDTO_KM.setNombreCaracteristica("KM_Coche");

            ValorCaracteristicaDTO valorCaracteristicaDTO_TipoCombustible = new ValorCaracteristicaDTO("Diesel");
            valorCaracteristicaDTO_TipoCombustible.setNombreCaracteristica("TipoCombustible_Coche");

            ValorCaracteristicaDTO valorCaracteristicaDTO_Transmision = new ValorCaracteristicaDTO("Manual");
            valorCaracteristicaDTO_Transmision.setNombreCaracteristica("Transmision_Coche");

            ValorCaracteristicaDTO valorCaracteristicaDTO_Marchas = new ValorCaracteristicaDTO("5");
            valorCaracteristicaDTO_Marchas.setNombreCaracteristica("Marchas_Coche");

            ValorCaracteristicaDTO valorCaracteristicaDTO_Puertas = new ValorCaracteristicaDTO("3");
            valorCaracteristicaDTO_Puertas.setNombreCaracteristica("Puertas_Coche");

            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_Marca);
            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_Modelo);
            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_CV);
            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_Anio);
            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_KM);
            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_TipoCombustible);
            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_Transmision);
            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_Marchas);
            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_Puertas);


            boolean sonValidos = true;
            for (ValorCaracteristicaDTO valorDTO : anuncioDTO.getValoresCaracteristicas()){
                Caracteristica caracteristica = caracteristicaService.findByNombreWithTipoVehiculoCaracteristicas(valorDTO.getNombreCaracteristica());
                TipoVehiculo_Caracteristica valor = caracteristica.getTiposVehiculoCaracteristica().getFirst();

                System.out.println(valorDTO.getNombreCaracteristica());

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

            if (sonValidos) {
                System.out.println("Si");
            }else{
                System.out.println("No");
            }
        };
    }*/

    // Prueba de anuncio simulando que viene desde la interfaz
    /*@Bean
    CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {
            ImagenService imagenService = context.getBean(ImagenService.class);
            TipoVehiculoService tipoVehiculoService = context.getBean(TipoVehiculoService.class);
            ValorCaracteristicaService VCService = context.getBean(ValorCaracteristicaService.class);
            CaracteristicaService caracteristicaService = context.getBean(CaracteristicaService.class);
            AnuncioService anuncioService = context.getBean(AnuncioService.class);
            UsuarioService usuarioService = context.getBean(UsuarioService.class);

            // Se mapearía el objeto JSON en vez de crearlo aquí
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setNombreUsuario("joseca");

            ImagenDTO imagenDTO = new ImagenDTO(
                    "data:image/jpeg;base64,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"
            );

            AnuncioDTO anuncioDTO = new AnuncioDTO(LocalDateTime.now(), "Coche de ejemplo", 9999.0, "Cádiz", "Medina-Sidonia", "1234ABC", "1234567890123");
            anuncioDTO.setTipoVehiculo("Coche");
            anuncioDTO.addImagen(imagenDTO);

            ValorCaracteristicaDTO valorCaracteristicaDTO_Marca = new ValorCaracteristicaDTO("Opel");
            valorCaracteristicaDTO_Marca.setNombreCaracteristica("Marca_Coche");

            ValorCaracteristicaDTO valorCaracteristicaDTO_Modelo = new ValorCaracteristicaDTO("Astra");
            valorCaracteristicaDTO_Modelo.setNombreCaracteristica("Modelo_Coche");

            ValorCaracteristicaDTO valorCaracteristicaDTO_CV = new ValorCaracteristicaDTO("150");
            valorCaracteristicaDTO_CV.setNombreCaracteristica("CV_Coche");

            ValorCaracteristicaDTO valorCaracteristicaDTO_Anio = new ValorCaracteristicaDTO("2007");
            valorCaracteristicaDTO_Anio.setNombreCaracteristica("Anio_Coche");

            ValorCaracteristicaDTO valorCaracteristicaDTO_KM = new ValorCaracteristicaDTO("400000");
            valorCaracteristicaDTO_KM.setNombreCaracteristica("KM_Coche");

            ValorCaracteristicaDTO valorCaracteristicaDTO_TipoCombustible = new ValorCaracteristicaDTO("Diesel");
            valorCaracteristicaDTO_TipoCombustible.setNombreCaracteristica("TipoCombustible_Coche");

            ValorCaracteristicaDTO valorCaracteristicaDTO_Transmision = new ValorCaracteristicaDTO("Manual");
            valorCaracteristicaDTO_Transmision.setNombreCaracteristica("Transmision_Coche");

            ValorCaracteristicaDTO valorCaracteristicaDTO_Marchas = new ValorCaracteristicaDTO("5");
            valorCaracteristicaDTO_Marchas.setNombreCaracteristica("Marchas_Coche");

            ValorCaracteristicaDTO valorCaracteristicaDTO_Puertas = new ValorCaracteristicaDTO("3");
            valorCaracteristicaDTO_Puertas.setNombreCaracteristica("Puertas_Coche");

            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_Marca);
            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_Modelo);
            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_CV);
            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_Anio);
            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_KM);
            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_TipoCombustible);
            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_Transmision);
            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_Marchas);
            anuncioDTO.addValorCaracteristica(valorCaracteristicaDTO_Puertas);


            // A partir de aquí se mapean los datos a mano
            Usuario usuario = usuarioService.findByNombreUsuarioWithAnunciosPublicados(usuarioDTO.getNombreUsuario());

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
            for(ImagenDTO imgDTO : anuncioDTO.getImagenes()){
                Imagen imagen = new Imagen();
                imagen.setImgBase64(imgDTO.getImgBase64());
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
        };
    }*/

    // Prueba de búsqueda de todos los tipos de vehículos
    /*@Bean
    CommandLineRunner commandLineRunner(ApplicationContext context) {
        return args -> {
            AnuncioService anuncioService = context.getBean(AnuncioService.class);

            // Hago un filtro simulando que es el que viene de la aplicación
            FiltroTodoDTO filtroDTO = new FiltroTodoDTO();
            filtroDTO.setMarca("");
            filtroDTO.setModelo("Astra");
            filtroDTO.setAnioMaximo(2025);
            filtroDTO.setAnioMinimo(2003);
            filtroDTO.setProvincia("");
            filtroDTO.setCiudad("");
            filtroDTO.setPrecioMinimo(1200);
            filtroDTO.setPrecioMaximo(10000);
            filtroDTO.addTipoVehiculo("Coche");
            filtroDTO.addTipoVehiculo("Moto");
            filtroDTO.setPagina(0);
            filtroDTO.setCantidadPorPagina(10);

            Pageable pageable = PageRequest.of(filtroDTO.getPagina(), filtroDTO.getCantidadPorPagina());

            List<Anuncio> anuncios = anuncioService.findAll(
                    filtroDTO.getTiposVehiculo(),
                    filtroDTO.getAnioMinimo(),
                    filtroDTO.getAnioMaximo(),
                    filtroDTO.getMarca(),
                    filtroDTO.getModelo(),
                    filtroDTO.getProvincia(),
                    filtroDTO.getCiudad(),
                    filtroDTO.getPrecioMinimo(),
                    filtroDTO.getPrecioMaximo(),
                    pageable
            );

            for (Anuncio anuncio : anuncios) {
                System.out.println("------------------------");
                System.out.println("ID: " + anuncio.getIdAnuncio());
                System.out.println("Matricula: " + anuncio.getMatricula());
                System.out.println("Descripción: " + anuncio.getDescripcion());
            }
            System.out.println("------------------------");
        };
    }*/
}
