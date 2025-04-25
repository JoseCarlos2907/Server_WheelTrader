package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.TipoVehiculo;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.TipoVehiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoVehiculoService {
    @Autowired
    private TipoVehiculoRepository tipoVehiculoRepository;

    public void save(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculoRepository.save(tipoVehiculo);
    }

    @Transactional
    public TipoVehiculo findByTipoWithAnuncios(String tipo){
        return this.tipoVehiculoRepository.findByTipoWithAnuncios(tipo);
    }

    public TipoVehiculo findByIdAnuncio(long idAnuncio){
        return this.tipoVehiculoRepository.findByIdAnuncio(idAnuncio);
    }
}
