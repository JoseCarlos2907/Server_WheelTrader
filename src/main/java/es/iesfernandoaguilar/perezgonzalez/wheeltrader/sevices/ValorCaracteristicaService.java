package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.ValorCaracteristica;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.ValorCaracteristicaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValorCaracteristicaService {
    @Autowired
    private ValorCaracteristicaRepository valorCaracteristicaRepository;

    public void save(ValorCaracteristica valorCaracteristica) {
        this.valorCaracteristicaRepository.save(valorCaracteristica);
    }

    @Transactional
    public List<ValorCaracteristica> findByIdAnuncio(long idAnuncio) {
        return this.valorCaracteristicaRepository.findByIdAnuncio(idAnuncio);
    }
}
