package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Anuncio;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.AnuncioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
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
                marca,
                modelo,
                anioMinimo,
                anioMaximo,
                precioMinimo,
                precioMaximo,
                provincia,
                ciudad,
                tiposVehiculo,
                pageable
        );
    }

    @Transactional
    public List<Anuncio> findCoches(String marca, String modelo, int cantMarchas, int kmMinimo, int kmMaximo, int nPuertas, String provincia, String ciudad, int cvMinimo, int cvMaximo, int anioMinimo, int anioMaximo, String tipoCombustible, String transmision, Pageable pageable){
        return this.anuncioRepository.findCoches(
                marca,
                modelo,
                cantMarchas,
                kmMinimo,
                kmMaximo,
                nPuertas,
                provincia,
                ciudad,
                cvMinimo,
                cvMaximo,
                anioMinimo,
                anioMaximo,
                tipoCombustible,
                transmision,
                pageable
        );
    }

    @Transactional
    public List<Anuncio> findMotos(String marca, String modelo, int cantMarchas, int kmMinimo, int kmMaximo, String provincia, String ciudad, int cvMinimo, int cvMaximo, int anioMinimo, int anioMaximo, String tipoCombustible, Pageable pageable){
        return this.anuncioRepository.findMotos(
                marca,
                modelo,
                cantMarchas,
                kmMinimo,
                kmMaximo,
                provincia,
                ciudad,
                cvMinimo,
                cvMaximo,
                anioMinimo,
                anioMaximo,
                tipoCombustible,
                pageable
        );
    }

    @Transactional
    public List<Anuncio> findCamionetas(String marca, String modelo, int anioMinimo, int anioMaximo, int kmMinimo, int kmMaximo, String tipoCombustible, int cvMinimo, int cvMaximo, int cantMarchas, int nPuertas, String provincia, String ciudad, String traccion, Pageable pageable){
        return this.anuncioRepository.findCamionetas(
                marca,
                modelo,
                anioMinimo,
                anioMaximo,
                kmMinimo,
                kmMaximo,
                tipoCombustible,
                cvMinimo,
                cvMaximo,
                cantMarchas,
                nPuertas,
                provincia,
                ciudad,
                traccion,
                pageable
        );
    }

    @Transactional
    public List<Anuncio> findCamiones(String marca, String modelo, int anioMinimo, int anioMaximo, int kmMinimo, int kmMaximo, String tipoCombustible, int cvMinimo, int cvMaximo, int cantMarchas, String provincia, String ciudad, Pageable pageable){
        return this.anuncioRepository.findCamiones(
                marca,
                modelo,
                anioMinimo,
                anioMaximo,
                kmMinimo,
                kmMaximo,
                tipoCombustible,
                cvMinimo,
                cvMaximo,
                cantMarchas,
                provincia,
                ciudad,
                pageable
        );
    }

    @Transactional
    public List<Anuncio> findMaquinaria(String marca, String modelo, int anioMinimo, int anioMaximo, String tipoCombustible, String provincia, String ciudad, Pageable pageable){
        return this.anuncioRepository.findMaquinarias(
                marca,
                modelo,
                anioMinimo,
                anioMaximo,
                tipoCombustible,
                provincia,
                ciudad,
                pageable
        );
    }
}
