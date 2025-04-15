package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Pago;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    public void save(Pago pago) {
        this.pagoRepository.save(pago);
    }
}
