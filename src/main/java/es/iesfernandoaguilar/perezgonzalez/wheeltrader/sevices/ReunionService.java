package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Reunion;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.ReunionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReunionService {
    @Autowired
    private ReunionRepository reunionRepository;

    public void save(Reunion reunion) {
        this.reunionRepository.save(reunion);
    }
}
