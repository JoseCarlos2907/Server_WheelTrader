package es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
