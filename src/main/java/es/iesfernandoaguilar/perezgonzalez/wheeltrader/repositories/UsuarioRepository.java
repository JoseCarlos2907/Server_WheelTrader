package es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.Auxiliares.UsuarioReportadosModDTO;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.EstadoUsuario;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Auxiliares.UsuarioReportadosMod;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Notificacion;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findById(long idUsuario);

    @Query("select u from Usuario u where u.nombreUsuario = ?1 OR u.correo = ?1")
    @Transactional(readOnly = true)
    Optional<Usuario> iniciarSesion(String nombreUsuarioOCorreo);

    boolean existsUsuarioByDni(String dni);

    boolean existsUsuarioByNombreUsuario(String nombreUsuario);

    boolean existsUsuarioByCorreo(String correo);

    @Query("select u.salt from Usuario u where u.correo = ?1")
    String getSaltUsuarioByCorreo(String correo);

    @Query("select CONCAT(u.nombre, ' ' ,u.apellidos) as nombreCompleto from Usuario u where u.correo = ?1")
    String getNombreCompletoByCorreo(String correo);

    @Modifying
    @Query("update Usuario u set u.contrasenia = ?1 where u.correo = ?2")
    void updateContraseniaUsuario(String cotrasenia, String correo);

    @Query("select u from Usuario u left join fetch u.anunciosPublicados where u.nombreUsuario = ?1")
    Usuario findByNombreUsuarioWithAnunciosPublicados(String nombreUsuario);

    @Query("select u from Usuario u left join fetch u.anunciosGuardados where u.nombreUsuario = ?1")
    Usuario findByNombreUsuarioWithAnunciosGuardados(String nombreUsuario);

    @Query("""
        select u
        from Usuario u
        join u.anunciosGuardados a
        where u.idUsuario = ?1 and a.idAnuncio = ?2
    """)
    Optional<Usuario> findUsuarioQueHaGuardadoAnuncio(Long idUsuario, Long idAnuncio);

    @Query(value = "select new es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Auxiliares.UsuarioReportadosMod(" +
            "u, " +
            "count(distinct r.idReporte), " +
            "coalesce(avg(v.valoracion), 0)) " +
            "FROM Usuario u " +
            "left join u.reportesRecibidos r " +
            "left join u.valoracionesRecibidas v " +
            "where (:cadena is null or u.nombreUsuario like %:cadena%) " +
            "group by u " +
            "order by count(distinct r.idReporte) desc, coalesce(avg(v.valoracion), 0) desc")
    List<UsuarioReportadosMod> findUsuariosReportadosMod(@Param("cadena") String cadena, Pageable pageable);

    @Query("select u.correoPP from Usuario u where u.idUsuario = ?1")
    String findCorreoPPByIdUsuario(long idUsuario);

    @Modifying
    @Query(value = "update usuarios set estado = ?2 where id_usuario = ?1", nativeQuery = true)
    void actualizarEstadoUsuario(long idUsuario, EstadoUsuario estado);

    @Modifying
    @Query(value = "update usuarios set contrasenia = ?2 where nombre_usuario = ?1", nativeQuery = true)
    void actualizarContrasenia(String nombreUsuario, String contrasenia);
}
