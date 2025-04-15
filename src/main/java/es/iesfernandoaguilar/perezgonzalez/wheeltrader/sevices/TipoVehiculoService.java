package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.TipoVehiculo;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.TipoVehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoVehiculoService {
    @Autowired
    private TipoVehiculoRepository tipoVehiculoRepository;

    public void save(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculoRepository.save(tipoVehiculo);
    }
}
