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
                marca.isEmpty() ? null : marca,
                modelo.isEmpty() ? null : modelo,
                anioMinimo,
                anioMaximo,
                precioMinimo,
                precioMaximo,
                provincia.isEmpty() ? null : provincia,
                ciudad.isEmpty() ? null : ciudad,
                tiposVehiculo,
                pageable
        );
    }

    @Transactional
    public List<Anuncio> findCoches(String marca, String modelo, int cantMarchas, int kmMinimo, int kmMaximo, int nPuertas, String provincia, String ciudad, int cvMinimo, int cvMaximo, int anioMinimo, int anioMaximo, String tipoCombustible, String transmision, Pageable pageable){
        return this.anuncioRepository.findCoches(
                marca.isEmpty() ? null : marca,
                modelo.isEmpty() ? null : marca,
                cantMarchas,
                kmMinimo,
                kmMaximo,
                nPuertas,
                provincia.isEmpty() ? null : marca,
                ciudad.isEmpty() ? null : marca,
                cvMinimo,
                cvMaximo,
                anioMinimo,
                anioMaximo,
                tipoCombustible.isEmpty() ? null : marca,
                transmision.isEmpty() ? null : marca,
                pageable
        );
    }

    @Transactional
    public List<Anuncio> findMotos(String marca, String modelo, int cantMarchas, int kmMinimo, int kmMaximo, String provincia, String ciudad, int cvMinimo, int cvMaximo, int anioMinimo, int anioMaximo, String tipoCombustible, Pageable pageable){
        return this.anuncioRepository.findMotos(
                marca.isEmpty() ? null : marca,
                modelo.isEmpty() ? null : marca,
                cantMarchas,
                kmMinimo,
                kmMaximo,
                provincia.isEmpty() ? null : marca,
                ciudad.isEmpty() ? null : marca,
                cvMinimo,
                cvMaximo,
                anioMinimo,
                anioMaximo,
                tipoCombustible.isEmpty() ? null : marca,
                pageable
        );
    }

    @Transactional
    public List<Anuncio> findCamionetas(String marca, String modelo, int anioMinimo, int anioMaximo, int kmMinimo, int kmMaximo, String tipoCombustible, int cvMinimo, int cvMaximo, int cantMarchas, int nPuertas, String provincia, String ciudad, String traccion, Pageable pageable){
        return this.anuncioRepository.findCamionetas(
                marca.isEmpty() ? null : marca,
                modelo.isEmpty() ? null : marca,
                anioMinimo,
                anioMaximo,
                kmMinimo,
                kmMaximo,
                tipoCombustible.isEmpty() ? null : marca,
                cvMinimo,
                cvMaximo,
                cantMarchas,
                nPuertas,
                provincia.isEmpty() ? null : marca,
                ciudad.isEmpty() ? null : marca,
                traccion.isEmpty() ? null : marca,
                pageable
        );
    }

    @Transactional
    public List<Anuncio> findCamiones(String marca, String modelo, int anioMinimo, int anioMaximo, int kmMinimo, int kmMaximo, String tipoCombustible, int cvMinimo, int cvMaximo, int cantMarchas, String provincia, String ciudad, Pageable pageable){
        return this.anuncioRepository.findCamiones(
                marca.isEmpty() ? null : marca,
                modelo.isEmpty() ? null : marca,
                anioMinimo,
                anioMaximo,
                kmMinimo,
                kmMaximo,
                tipoCombustible.isEmpty() ? null : marca,
                cvMinimo,
                cvMaximo,
                cantMarchas,
                provincia.isEmpty() ? null : marca,
                ciudad.isEmpty() ? null : marca,
                pageable
        );
    }

    @Transactional
    public List<Anuncio> findMaquinaria(String marca, String modelo, int anioMinimo, int anioMaximo, String tipoCombustible, String provincia, String ciudad, Pageable pageable){
        return this.anuncioRepository.findMaquinarias(
                marca.isEmpty() ? null : marca,
                modelo.isEmpty() ? null : marca,
                anioMinimo,
                anioMaximo,
                tipoCombustible.isEmpty() ? null : marca,
                provincia.isEmpty() ? null : marca,
                ciudad.isEmpty() ? null : marca,
                pageable
        );
    }
}
