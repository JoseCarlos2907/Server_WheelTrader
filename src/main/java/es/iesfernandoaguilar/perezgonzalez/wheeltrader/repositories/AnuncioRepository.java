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
    List<Anuncio> findAll(@Param("tiposVehiculo") List<String> tiposVehiculo,
                               @Param("anioMinimo") int anioMinimo,
                               @Param("anioMaximo") int anioMaximo,
                               @Param("marca") String marca,
                               @Param("modelo") String modelo,
                               @Param("provincia") String provincia,
                               @Param("ciudad") String ciudad,
                               @Param("precioMinimo") double precioMinimo,
                               @Param("precioMaximo") double precioMaximo,
                               Pageable pageable
    );
}
