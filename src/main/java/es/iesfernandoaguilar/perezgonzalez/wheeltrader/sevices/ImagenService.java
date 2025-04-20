package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Imagen;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.ImagenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagenService {
    @Autowired
    private ImagenRepository imagenRepository;

    public void save(Imagen imagen){
        this.imagenRepository.save(imagen);
    }

    @Transactional
    public List<Imagen> findByIdAnuncio(long idAnuncio){
        return this.imagenRepository.findByIdAnuncio(idAnuncio);
    }
}
