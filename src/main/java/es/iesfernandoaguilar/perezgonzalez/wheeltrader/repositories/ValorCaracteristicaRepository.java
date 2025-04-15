package es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.ValorCaracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValorCaracteristicaRepository extends JpaRepository<ValorCaracteristica, Long> {
}
