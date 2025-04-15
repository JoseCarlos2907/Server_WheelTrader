package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.TipoVehiculo_Caracteristica;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.TipoVehiculoCaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoVehiculoCaracteristicaService {
    @Autowired
    private TipoVehiculoCaracteristicaRepository tipoVehiculoCaracteristicaRepository;

    public void save(TipoVehiculo_Caracteristica tipoVehiculoCaracteristica) {
        this.tipoVehiculoCaracteristicaRepository.save(tipoVehiculoCaracteristica);
    }
}
