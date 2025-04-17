package es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {
    @Query("select c from Caracteristica c left join fetch c.valoresCaracteristicas where c.nombre = ?1")
    Caracteristica findByNombreWithValoresCaracteristicas(String nombre);

    @Query("select c from Caracteristica c left join fetch c.tiposVehiculoCaracteristica where c.nombre = ?1")
    Caracteristica findByNombreWithTipoVehiculoCaracteristicas(String nombre);
}
