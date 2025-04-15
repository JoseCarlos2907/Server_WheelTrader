package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Cuestionario;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.CuestionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuestionarioService {
    @Autowired
    private CuestionarioRepository cuestionarioRepository;

    public void save(Cuestionario cuestionario) {
        this.cuestionarioRepository.save(cuestionario);
    }
}
