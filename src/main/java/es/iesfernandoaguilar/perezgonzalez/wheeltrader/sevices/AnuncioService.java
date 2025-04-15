package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Anuncio;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.AnuncioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnuncioService {
    @Autowired
    private AnuncioRepository anuncioRepository;

    public void save(Anuncio anuncio) {
        this.anuncioRepository.save(anuncio);
    }

    public Anuncio findById(long id) {
        return this.anuncioRepository.findById(id);
    }
}
