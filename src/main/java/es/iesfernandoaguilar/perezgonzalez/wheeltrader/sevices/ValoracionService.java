package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Valoracion;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.ValoracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValoracionService {
    @Autowired
    private ValoracionRepository valoracionRepository;

    public void save(Valoracion valoracion) {
        this.valoracionRepository.save(valoracion);
    }
}
