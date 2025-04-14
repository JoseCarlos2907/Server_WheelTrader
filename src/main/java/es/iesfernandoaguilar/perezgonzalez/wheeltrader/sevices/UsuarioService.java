package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void save(Usuario usuario) {
        this.usuarioRepository.save(usuario);
    }

    public Usuario findById(long idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    public Optional<Usuario> iniciarSesion(String nombreUsuarioOCorreo) {
        return usuarioRepository.iniciarSesion(nombreUsuarioOCorreo);
    }

    public boolean existsUsuarioByDni(String dni) {
        return usuarioRepository.existsUsuarioByDni(dni);
    }

    public boolean existsUsuarioByNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsUsuarioByNombreUsuario(nombreUsuario);
    }

    public boolean existsUsuarioByCorreo(String correo) {
        return usuarioRepository.existsUsuarioByCorreo(correo);
    }

    public String getSaltUsuarioByCorreo(String correo) {
        return this.usuarioRepository.getSaltUsuarioByCorreo(correo);
    }

    public String getNombreCompletoByCorreo(String correo) {
        return this.usuarioRepository.getNombreCompletoByCorreo(correo);
    }

    @Transactional
    public void updateContraseniaUsuario(String contrasenia, String correo) {
        this.usuarioRepository.updateContraseniaUsuario(contrasenia, correo);
    }
}
