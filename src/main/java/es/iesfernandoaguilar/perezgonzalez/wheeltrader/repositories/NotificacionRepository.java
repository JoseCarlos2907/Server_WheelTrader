package es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.EstadoNotificacion;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.TipoNotificacion;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Notificacion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    @Modifying
    @Query(value = "insert into notificaciones (estado, mensaje, tipo, titulo, usuario_envia_id, usuario_recibe_id, anuncio_id) " +
            "values (:estado, :mensaje, :tipo, :titulo, :idUsuarioEnvia, :idUsuarioRecibe, :idAnuncio)",
    nativeQuery = true)
    void crearNotificacion(
            @Param("idUsuarioEnvia") long idUsuarioEnvia,
            @Param("idAnuncio") long idAnuncio,
            @Param("idUsuarioRecibe") long idUsuarioRecibe,
            @Param("estado") EstadoNotificacion estado,
            @Param("titulo") String titulo,
            @Param("mensaje") String mensaje,
            @Param("tipo") TipoNotificacion tipo
    );

    @Query("select n from Notificacion n left join fetch n.usuarioRecibe u left join n.usuarioEnvia where u.idUsuario = ?1 order by n.idNotificacion desc")
    List<Notificacion> obtenerNotificacionesByIdUsuario(long idUsuario, Pageable pageable);

    @Modifying
    @Query(value = "update notificaciones set estado = ?2 where id_notificacion = ?1", nativeQuery = true)
    void actualizarEstadoNotificacion(long idNotificacion, EstadoNotificacion estado);

    @Query("select n from Notificacion n left join fetch n.anuncio where n.idNotificacion = ?1")
    Notificacion findByIdNotificacionWithAnuncio(long idNotificacion);
}
