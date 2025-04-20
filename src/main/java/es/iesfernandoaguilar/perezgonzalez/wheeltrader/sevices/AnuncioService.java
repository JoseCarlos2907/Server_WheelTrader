package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Anuncio;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.AnuncioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Transactional
    public List<Anuncio> findAll(List<String> tiposVehiculo, int anioMinimo, int anioMaximo, String marca, String modelo, String provincia, String ciudad, double precioMinimo, double precioMaximo, Pageable pageable) {
        return this.anuncioRepository.findAll(
                tiposVehiculo,
                anioMinimo,
                anioMaximo,
                marca.isEmpty() ? null : marca,
                modelo.isEmpty() ? null : modelo,
                provincia.isEmpty() ? null : provincia,
                ciudad.isEmpty() ? null : ciudad,
                precioMinimo,
                precioMaximo,
                pageable
        );
    }
}
