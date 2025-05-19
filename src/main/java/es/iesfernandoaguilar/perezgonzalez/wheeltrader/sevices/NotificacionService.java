package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.EstadoNotificacion;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.TipoNotificacion;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Notificacion;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.NotificacionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {
    @Autowired
    private NotificacionRepository notificacionRepository;

    public void save(Notificacion notificacion) {
        this.notificacionRepository.save(notificacion);
    }

    @Transactional
    public void crearNotificacion(long idUsuarioEnvia, long idAnuncio, long idUsuarioRecibe, EstadoNotificacion estado, String titulo, String mensaje, TipoNotificacion tipo){
        this.notificacionRepository.crearNotificacion(idUsuarioEnvia, idAnuncio, idUsuarioRecibe, estado, titulo, mensaje, tipo);
    }

    public List<Notificacion> obtenerNotificacionesByIdUsuario(long idUsuario, Pageable pageable){
        return this.notificacionRepository.obtenerNotificacionesByIdUsuario(idUsuario, pageable);
    }

    @Transactional
    public void actualizarEstadoNotificacion(long idNotificacion, EstadoNotificacion estado) {
        this.notificacionRepository.actualizarEstadoNotificacion(idNotificacion, estado);
    }
}
