package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Venta;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService {
    @Autowired
    private VentaRepository ventaRepository;

    public void save(Venta venta) {
        this.ventaRepository.save(venta);
    }
}
