package es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.TipoVehiculo_Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoVehiculoCaracteristicaRepository extends JpaRepository<TipoVehiculo_Caracteristica, Long> {
}
