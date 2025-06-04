package es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.EstadoAnuncio;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Anuncio;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Imagen;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
    Anuncio findById(long id);

    @Query("select a from Anuncio a left join fetch a.usuariosGuardan where a.idAnuncio = ?1")
    Anuncio findByIdWithUsuariosGuardan(long idAnuncio);

    @Query("select a from Anuncio a left join fetch a.valoresCaracteristicas vc left join fetch vc.caracteristica where a.idAnuncio = ?1")
    Anuncio findByIdAnuncioWithValoresCaracteristicas(long idAnuncio);

    @EntityGraph(attributePaths = {
            "vendedor",
            "usuariosGuardan",
            "tipoVehiculo"
    })
    @Query("""
            select a from Anuncio a
            join a.tipoVehiculo t
            join a.valoresCaracteristicas anio
            left join a.valoresCaracteristicas marca
            left join a.valoresCaracteristicas modelo
            where t.tipo in :tiposVehiculo
            and anio.caracteristica.nombre like 'Anio_%' and cast(anio.valor as int) >= :anioMinimo and cast(anio.valor as int) <= :anioMaximo
            and (lower(marca.valor) = lower(:marca) or :marca is null)
            and (lower(modelo.valor) = lower(:modelo) or :modelo is null)
            and (lower(a.provincia) = lower(:provincia) or :provincia is null)
            and (lower(a.ciudad) = lower(:ciudad) or :ciudad is null)
            and a.precio between :precioMinimo and :precioMaximo
            order by a.fechaPublicacion desc
    """)
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

    // Mal
    @EntityGraph(attributePaths = {
            "vendedor",
            "usuariosGuardan",
            "tipoVehiculo"
    })
    @Query("""
        SELECT a FROM Anuncio a
        JOIN a.tipoVehiculo t
        JOIN a.valoresCaracteristicas anio ON anio.caracteristica.nombre LIKE 'Anio_Coche'
        LEFT JOIN a.valoresCaracteristicas marca
        LEFT JOIN a.valoresCaracteristicas modelo
        JOIN a.valoresCaracteristicas km ON km.caracteristica.nombre LIKE 'KM_Coche'
        JOIN a.valoresCaracteristicas marchas ON marchas.caracteristica.nombre LIKE 'Marchas_Coche'
        JOIN a.valoresCaracteristicas puertas ON puertas.caracteristica.nombre LIKE 'Puertas_Coche'
        JOIN a.valoresCaracteristicas cv ON cv.caracteristica.nombre LIKE 'CV_Coche'
        JOIN a.valoresCaracteristicas tc ON tc.caracteristica.nombre LIKE 'TipoCombustible_Coche'
        JOIN a.valoresCaracteristicas transmision ON transmision.caracteristica.nombre LIKE 'Transmision_Coche'
        WHERE t.tipo = 'Coche'
        AND CAST(anio.valor AS int) >= :anioMinimo AND CAST(anio.valor AS int) <= :anioMaximo
        AND CAST(km.valor AS int) >= :kmMinimo AND CAST(km.valor AS int) <= :kmMaximo
        AND CAST(cv.valor AS int) >= :cvMinimo AND CAST(cv.valor AS int) <= :cvMaximo
        AND (:cantMarchas = 0 OR CAST(marchas.valor AS int) = :cantMarchas)
        AND (:nPuertas = 0 OR CAST(puertas.valor AS int) = :nPuertas)
        AND (:marca IS NULL OR LOWER(marca.valor) = LOWER(:marca))
        AND (:modelo IS NULL OR LOWER(modelo.valor) = LOWER(:modelo))
        AND (:tipoCombustible IS NULL OR LOWER(tc.valor) = LOWER(:tipoCombustible))
        AND (:transmision IS NULL OR LOWER(transmision.valor) = LOWER(:transmision))
        AND (:provincia IS NULL OR LOWER(a.provincia) = LOWER(:provincia))
        AND (:ciudad IS NULL OR LOWER(a.ciudad) = LOWER(:ciudad))
        ORDER BY a.fechaPublicacion DESC
    """)
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

    @Query("select a from Anuncio a join a.usuariosGuardan u where u.nombreUsuario = ?1 order by a.fechaPublicacion desc")
    List<Anuncio> findAnunciosGuardadosByNombreUsuario(String nombreUsuario, Pageable pageable);

    @Query("select a from Anuncio a left join fetch a.vendedor v where v.nombreUsuario = ?1 order by a.fechaPublicacion desc")
    List<Anuncio> findAnunciosPublicadosByNombreUsuario(String nombreUsuario, Pageable pageable);

    @EntityGraph(attributePaths = {
            "vendedor",
            "usuariosGuardan",
            "tipoVehiculo"
    })
    @Query("""
            select a from Anuncio a
            join a.tipoVehiculo t
            join a.valoresCaracteristicas anio
            left join a.valoresCaracteristicas marca
            left join a.valoresCaracteristicas modelo
            where t.tipo in :tiposVehiculo
            and anio.caracteristica.nombre like 'Anio_%' and cast(anio.valor as int) >= :anioMinimo and cast(anio.valor as int) <= :anioMaximo
            and (concat(lower(marca.valor), ' ', lower(modelo.valor) ) like concat('%', lower(:cadena), '%') or :cadena is null)
            and (lower(a.provincia) = lower(:provincia) or :provincia is null)
            and (lower(a.ciudad) = lower(:ciudad) or :ciudad is null)
            and a.precio between :precioMinimo and :precioMaximo
            and a.estado < 2
            order by a.fechaPublicacion desc
    """)
    List<Anuncio> findAllByString(
            @Param("cadena") String cadena,
            @Param("anioMinimo") int anioMinimo,
            @Param("anioMaximo") int anioMaximo,
            @Param("precioMinimo") double precioMinimo,
            @Param("precioMaximo") double precioMaximo,
            @Param("provincia") String provincia,
            @Param("ciudad") String ciudad,
            @Param("tiposVehiculo") List<String> tiposVehiculo,
            Pageable pageable
    );

    @Modifying
    @Query(value = "update anuncios set estado = ?2 where id_anuncio = ?1", nativeQuery = true)
    void actualizarEstadoAnuncio(long idAnuncio, EstadoAnuncio estadoAnuncio);
}
