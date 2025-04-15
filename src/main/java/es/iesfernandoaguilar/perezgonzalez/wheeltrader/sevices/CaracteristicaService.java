package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Anuncio;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Caracteristica;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.AnuncioRepository;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.CaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaracteristicaService {
    @Autowired
    private CaracteristicaRepository caracteristicaRepository;

    public void save(Caracteristica caracteristica) {
        this.caracteristicaRepository.save(caracteristica);
    }
}
