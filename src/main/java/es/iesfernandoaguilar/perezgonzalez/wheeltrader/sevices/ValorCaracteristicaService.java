package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.ValorCaracteristica;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.ValorCaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValorCaracteristicaService {
    @Autowired
    private ValorCaracteristicaRepository valorCaracteristicaRepository;

    public void save(ValorCaracteristica valorCaracteristica) {
        this.valorCaracteristicaRepository.save(valorCaracteristica);
    }
}
