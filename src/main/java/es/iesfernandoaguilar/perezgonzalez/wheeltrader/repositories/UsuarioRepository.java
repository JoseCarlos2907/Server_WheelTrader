package es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findById(long idUsuario);

    @Query("SELECT u from Usuario u where u.nombreUsuario = ?1 OR u.correo = ?1")
    Optional<Usuario> iniciarSesion(String nombreUsuarioOCorreo);

    boolean existsUsuarioByDni(String dni);

    boolean existsUsuarioByNombreUsuario(String nombreUsuario);

    boolean existsUsuarioByCorreo(String correo);

    @Query("SELECT u.salt from Usuario u where u.correo = ?1")
    String getSaltUsuarioByCorreo(String correo);

    @Query("Update Usuario u set u.contrasenia = ?1 where u.correo = ?2")
    void updateContraseniaUsuario(String cotrasenia, String correo);
}
