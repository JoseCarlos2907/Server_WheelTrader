package es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Anuncio;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Imagen;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
    Anuncio findById(long id);

    @EntityGraph(attributePaths = {
            "vendedor",
            "usuariosGuardan",
            "tipoVehiculo"
    })
    @Query("SELECT a FROM Anuncio a " +
            "JOIN a.tipoVehiculo t " +
            "JOIN a.valoresCaracteristicas anio " +
            "LEFT JOIN a.valoresCaracteristicas marca " +
            "LEFT JOIN a.valoresCaracteristicas modelo " +
            "WHERE t.tipo IN :tiposVehiculo " +
            "AND anio.caracteristica.nombre LIKE 'Anio_%' AND CAST(anio.valor AS int) >= :anioMinimo AND CAST(anio.valor AS int) <= :anioMaximo " +
            "AND (LOWER(marca.valor) = LOWER(:marca) OR :marca IS NULL) " +
            "AND (LOWER(modelo.valor) = LOWER(:modelo) OR :modelo IS NULL) " +
            "AND (LOWER(a.provincia) = LOWER(:provincia) OR :provincia IS NULL) " +
            "AND (LOWER(a.ciudad) = LOWER(:ciudad) OR :ciudad IS NULL) " +
            "AND a.precio BETWEEN :precioMinimo AND :precioMaximo " +
            "ORDER BY a.fechaPublicacion DESC")
    List<Anuncio> findAll(
            @Param("marca") String marca,
            @Param("modelo") String modelo,
            @Param("anioMinimo") int anioMinimo,
            @Param("anioMaximo") int anioMaximo,
            @Param("precioMinimo") double precioMinimo,
            @Param("precioMaximo") double precioMaximo,
            @Param("provincia") String provincia,
            @Param("ciudad") String ciudad,
            @Param("tiposVehiculo") List<String> tiposVehiculo,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {
            "vendedor",
            "usuariosGuardan",
            "tipoVehiculo"
    })
    @Query("SELECT a FROM Anuncio a " +
            "JOIN a.tipoVehiculo t " +
            "JOIN a.valoresCaracteristicas anio " +
            "LEFT JOIN a.valoresCaracteristicas marca " +
            "LEFT JOIN a.valoresCaracteristicas modelo " +
            "LEFT JOIN a.valoresCaracteristicas km " +
            "LEFT JOIN a.valoresCaracteristicas marchas " +
            "LEFT JOIN a.valoresCaracteristicas puertas " +
            "LEFT JOIN a.valoresCaracteristicas cv " +
            "LEFT JOIN a.valoresCaracteristicas tc " +
            "LEFT JOIN a.valoresCaracteristicas transmision " +
            "WHERE t.tipo = 'Coche' " +
            "AND anio.caracteristica.nombre LIKE 'Anio_%' AND CAST(anio.valor AS int) >= :anioMinimo AND CAST(anio.valor AS int) <= :anioMaximo " +
            "AND km.caracteristica.nombre LIKE 'KM_%' AND CAST(km.valor AS int) >= :kmMinimo AND CAST(km.valor AS int) <= :kmMaximo " +
            "AND cv.caracteristica.nombre LIKE 'CV_%' AND CAST(cv.valor AS int) >= :cvMinimo AND CAST(cv.valor AS int) <= :cvMaximo " +
            "AND marchas.caracteristica.nombre LIKE 'Marchas_%' AND CAST(marchas.valor AS int) = :cantMarchas " +
            "AND puertas.caracteristica.nombre LIKE 'Puertas_%' AND CAST(puertas.valor AS int) = :nPuertas " +
            "AND (LOWER(marca.valor) = LOWER(:marca) OR :marca IS NULL) " +
            "AND (LOWER(modelo.valor) = LOWER(:modelo) OR :modelo IS NULL) " +
            "AND (LOWER(tc.valor) = LOWER(:tipoCombustible) OR :tipoCombustible IS NULL) " +
            "AND (LOWER(transmision.valor) = LOWER(:transmision) OR :transmision IS NULL) " +
            "AND (LOWER(a.provincia) = LOWER(:provincia) OR :provincia IS NULL) " +
            "AND (LOWER(a.ciudad) = LOWER(:ciudad) OR :ciudad IS NULL) " +
            "ORDER BY a.fechaPublicacion DESC")
    List<Anuncio> findCoches(
            @Param("marca") String marca,
            @Param("modelo") String modelo,
            @Param("cantMarchas") int cantMarchas,
            @Param("kmMinimo") int kmMinimo,
            @Param("kmMaximo") int kmMaximo,
            @Param("nPuertas") int nPuertas,
            @Param("provincia") String provincia,
            @Param("ciudad") String ciudad,
            @Param("cvMinimo") int cvMinimo,
            @Param("cvMaximo") int cvMaximo,
            @Param("anioMinimo") int anioMinimo,
            @Param("anioMaximo") int anioMaximo,
            @Param("tipoCombustible") String tipoCombustible,
            @Param("transmision") String transmision,
            Pageable pageable
    );


    @EntityGraph(attributePaths = {
            "vendedor",
            "usuariosGuardan",
            "tipoVehiculo"
    })
    @Query("SELECT a FROM Anuncio a " +
            "JOIN a.tipoVehiculo t " +
            "JOIN a.valoresCaracteristicas anio " +
            "LEFT JOIN a.valoresCaracteristicas marca " +
            "LEFT JOIN a.valoresCaracteristicas modelo " +
            "LEFT JOIN a.valoresCaracteristicas km " +
            "LEFT JOIN a.valoresCaracteristicas marchas " +
            "LEFT JOIN a.valoresCaracteristicas cilindrada " +
            "LEFT JOIN a.valoresCaracteristicas tc " +
            "WHERE t.tipo = 'Moto' " +
            "AND anio.caracteristica.nombre LIKE 'Anio_%' AND CAST(anio.valor AS int) >= :anioMinimo AND CAST(anio.valor AS int) <= :anioMaximo " +
            "AND km.caracteristica.nombre LIKE 'KM_%' AND CAST(km.valor AS int) >= :kmMinimo AND CAST(km.valor AS int) <= :kmMaximo " +
            "AND cilindrada.caracteristica.nombre LIKE 'CV_%' AND CAST(cilindrada.valor AS int) >= :cvMinimo AND CAST(cilindrada.valor AS int) <= :cvMaximo " +
            "AND marchas.caracteristica.nombre LIKE 'Marchas_%' AND CAST(marchas.valor AS int) = :cantMarchas " +
            "AND (LOWER(marca.valor) = LOWER(:marca) OR :marca IS NULL) " +
            "AND (LOWER(modelo.valor) = LOWER(:modelo) OR :modelo IS NULL) " +
            "AND (LOWER(tc.valor) = LOWER(:tipoCombustible) OR :tipoCombustible IS NULL) " +
            "AND (LOWER(a.provincia) = LOWER(:provincia) OR :provincia IS NULL) " +
            "AND (LOWER(a.ciudad) = LOWER(:ciudad) OR :ciudad IS NULL) " +
            "ORDER BY a.fechaPublicacion DESC")
    List<Anuncio> findMotos(
        @Param("marca") String marca,
        @Param("modelo") String modelo,
        @Param("cantMarchas") int cantMarchas,
        @Param("kmMinimo") int kmMinimo,
        @Param("kmMaximo") int kmMaximo,
        @Param("provincia") String provincia,
        @Param("ciudad") String ciudad,
        @Param("cvMinimo") int cvMinimo,
        @Param("cvMaximo") int cvMaximo,
        @Param("anioMinimo") int anioMinimo,
        @Param("anioMaximo") int anioMaximo,
        @Param("tipoCombustible") String tipoCombustible,
        Pageable pageable
    );


    @EntityGraph(attributePaths = {
            "vendedor",
            "usuariosGuardan",
            "tipoVehiculo"
    })
    @Query("SELECT a FROM Anuncio a " +
            "JOIN a.tipoVehiculo t " +
            "JOIN a.valoresCaracteristicas anio " +
            "LEFT JOIN a.valoresCaracteristicas marca " +
            "LEFT JOIN a.valoresCaracteristicas modelo " +
            "LEFT JOIN a.valoresCaracteristicas km " +
            "LEFT JOIN a.valoresCaracteristicas marchas " +
            "LEFT JOIN a.valoresCaracteristicas puertas " +
            "LEFT JOIN a.valoresCaracteristicas cv " +
            "LEFT JOIN a.valoresCaracteristicas tc " +
            "LEFT JOIN a.valoresCaracteristicas traccion " +
            "WHERE t.tipo = 'Camioneta' " +
            "AND anio.caracteristica.nombre LIKE 'Anio_%' AND CAST(anio.valor AS int) >= :anioMinimo AND CAST(anio.valor AS int) <= :anioMaximo " +
            "AND km.caracteristica.nombre LIKE 'KM_%' AND CAST(km.valor AS int) >= :kmMinimo AND CAST(km.valor AS int) <= :kmMaximo " +
            "AND cv.caracteristica.nombre LIKE 'CV_%' AND CAST(cv.valor AS int) >= :cvMinimo AND CAST(cv.valor AS int) <= :cvMaximo " +
            "AND marchas.caracteristica.nombre LIKE 'Marchas_%' AND CAST(marchas.valor AS int) = :cantMarchas " +
            "AND puertas.caracteristica.nombre LIKE 'Puertas_%' AND CAST(puertas.valor AS int) = :nPuertas " +
            "AND (LOWER(marca.valor) = LOWER(:marca) OR :marca IS NULL) " +
            "AND (LOWER(modelo.valor) = LOWER(:modelo) OR :modelo IS NULL) " +
            "AND (LOWER(tc.valor) = LOWER(:tipoCombustible) OR :tipoCombustible IS NULL) " +
            "AND (LOWER(traccion.valor) = LOWER(:traccion) OR :traccion IS NULL) " +
            "AND (LOWER(a.provincia) = LOWER(:provincia) OR :provincia IS NULL) " +
            "AND (LOWER(a.ciudad) = LOWER(:ciudad) OR :ciudad IS NULL) " +
            "ORDER BY a.fechaPublicacion DESC")
    List<Anuncio> findCamionetas(
            @Param("marca") String marca,
            @Param("modelo") String modelo,
            @Param("anioMinimo") int anioMinimo,
            @Param("anioMaximo") int anioMaximo,
            @Param("kmMinimo") int kmMinimo,
            @Param("kmMaximo") int kmMaximo,
            @Param("tipoCombustible") String tipoCombustible,
            @Param("cvMinimo") int cvMinimo,
            @Param("cvMaximo") int cvMaximo,
            @Param("cantMarchas") int cantMarchas,
            @Param("nPuertas") int nPuertas,
            @Param("provincia") String provincia,
            @Param("ciudad") String ciudad,
            @Param("traccion") String traccion,
            Pageable pageable
    );


    @EntityGraph(attributePaths = {
            "vendedor",
            "usuariosGuardan",
            "tipoVehiculo"
    })
    @Query("SELECT a FROM Anuncio a " +
            "JOIN a.tipoVehiculo t " +
            "JOIN a.valoresCaracteristicas anio " +
            "LEFT JOIN a.valoresCaracteristicas marca " +
            "LEFT JOIN a.valoresCaracteristicas modelo " +
            "LEFT JOIN a.valoresCaracteristicas km " +
            "LEFT JOIN a.valoresCaracteristicas marchas " +
            "LEFT JOIN a.valoresCaracteristicas cv " +
            "LEFT JOIN a.valoresCaracteristicas tc " +
            "WHERE t.tipo = 'Camion' " +
            "AND anio.caracteristica.nombre LIKE 'Anio_%' AND CAST(anio.valor AS int) >= :anioMinimo AND CAST(anio.valor AS int) <= :anioMaximo " +
            "AND km.caracteristica.nombre LIKE 'KM_%' AND CAST(km.valor AS int) >= :kmMinimo AND CAST(km.valor AS int) <= :kmMaximo " +
            "AND cv.caracteristica.nombre LIKE 'CV_%' AND CAST(cv.valor AS int) >= :cvMinimo AND CAST(cv.valor AS int) <= :cvMaximo " +
            "AND marchas.caracteristica.nombre LIKE 'Marchas_%' AND CAST(marchas.valor AS int) = :cantMarchas " +
            "AND (LOWER(marca.valor) = LOWER(:marca) OR :marca IS NULL) " +
            "AND (LOWER(modelo.valor) = LOWER(:modelo) OR :modelo IS NULL) " +
            "AND (LOWER(tc.valor) = LOWER(:tipoCombustible) OR :tipoCombustible IS NULL) " +
            "AND (LOWER(a.provincia) = LOWER(:provincia) OR :provincia IS NULL) " +
            "AND (LOWER(a.ciudad) = LOWER(:ciudad) OR :ciudad IS NULL) " +
            "ORDER BY a.fechaPublicacion DESC")
    List<Anuncio> findCamiones(
            @Param("marca") String marca,
            @Param("modelo") String modelo,
            @Param("anioMinimo") int anioMinimo,
            @Param("anioMaximo") int anioMaximo,
            @Param("kmMinimo") int kmMinimo,
            @Param("kmMaximo") int kmMaximo,
            @Param("tipoCombustible") String tipoCombustible,
            @Param("cvMinimo") int cvMinimo,
            @Param("cvMaximo") int cvMaximo,
            @Param("cantMarchas") int cantMarchas,
            @Param("provincia") String provincia,
            @Param("ciudad") String ciudad,
            Pageable pageable
    );


    @EntityGraph(attributePaths = {
            "vendedor",
            "usuariosGuardan",
            "tipoVehiculo"
    })
    @Query("SELECT a FROM Anuncio a " +
            "JOIN a.tipoVehiculo t " +
            "JOIN a.valoresCaracteristicas anio " +
            "LEFT JOIN a.valoresCaracteristicas marca " +
            "LEFT JOIN a.valoresCaracteristicas modelo " +
            "LEFT JOIN a.valoresCaracteristicas tc " +
            "WHERE t.tipo = 'Maquinaria' " +
            "AND anio.caracteristica.nombre LIKE 'Anio_%' AND CAST(anio.valor AS int) >= :anioMinimo AND CAST(anio.valor AS int) <= :anioMaximo " +
            "AND (LOWER(marca.valor) = LOWER(:marca) OR :marca IS NULL) " +
            "AND (LOWER(modelo.valor) = LOWER(:modelo) OR :modelo IS NULL) " +
            "AND (LOWER(tc.valor) = LOWER(:tipoCombustible) OR :tipoCombustible IS NULL) " +
            "AND (LOWER(a.provincia) = LOWER(:provincia) OR :provincia IS NULL) " +
            "AND (LOWER(a.ciudad) = LOWER(:ciudad) OR :ciudad IS NULL) " +
            "ORDER BY a.fechaPublicacion DESC")
    List<Anuncio> findMaquinarias(
            @Param("marca") String marca,
            @Param("modelo") String modelo,
            @Param("anioMinimo") int anioMinimo,
            @Param("anioMaximo") int anioMaximo,
            @Param("tipoCombustible") String tipoCombustible,
            @Param("provincia") String provincia,
            @Param("ciudad") String ciudad,
            Pageable pageable
    );
}
