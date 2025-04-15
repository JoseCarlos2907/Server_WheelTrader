package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Notificacion;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {
    @Autowired
    private NotificacionRepository notificacionRepository;

    public void save(Notificacion notificacion) {
        this.notificacionRepository.save(notificacion);
    }
}
