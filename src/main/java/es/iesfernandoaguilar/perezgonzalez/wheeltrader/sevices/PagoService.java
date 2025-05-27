package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Pago;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    public void save(Pago pago) {
        this.pagoRepository.save(pago);
    }
}
