package es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {
}
