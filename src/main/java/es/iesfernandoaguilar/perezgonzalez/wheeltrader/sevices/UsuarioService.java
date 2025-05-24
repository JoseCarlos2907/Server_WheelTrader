package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.Auxiliares.UsuarioReportadosModDTO;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.EstadoUsuario;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Auxiliares.UsuarioReportadosMod;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Notificacion;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void save(Usuario usuario) {
        this.usuarioRepository.save(usuario);
    }

    public void update(Usuario usuario) {
        this.usuarioRepository.save(usuario);
    }

    public Usuario findById(long idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    public Usuario findByNombreUsuarioWithAnunciosPublicados(String nombreUsuario) {
        return this.usuarioRepository.findByNombreUsuarioWithAnunciosPublicados(nombreUsuario);
    }

    public Usuario findByNombreUsuarioWithAnunciosGuardados(String nombreUsuario) {
        return this.usuarioRepository.findByNombreUsuarioWithAnunciosGuardados(nombreUsuario);
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

    public Optional<Usuario> findUsuarioQueHaGuardadoAnuncio(long idUsuario, long idAnuncio) {
        return this.usuarioRepository.findUsuarioQueHaGuardadoAnuncio(idUsuario, idAnuncio);
    }

    public List<UsuarioReportadosMod> findUsuariosReportadosMod(String cadena, Pageable pageable){
        return this.usuarioRepository.findUsuariosReportadosMod(cadena, pageable);
    }

    public String findCorreoPPByIdUsuario(long idUsuario){
        return this.usuarioRepository.findCorreoPPByIdUsuario(idUsuario);
    }

    @Transactional
    public void actualizarEstadoUsuario(long idUsuario, EstadoUsuario estado){
        this.usuarioRepository.actualizarEstadoUsuario(idUsuario, estado);
    }

    @Transactional
    public void actualizarContraseniaUsuario(String nombreUsuario, String contrasena){
        this.usuarioRepository.actualizarContrasenia(nombreUsuario, contrasena);
    }
}
