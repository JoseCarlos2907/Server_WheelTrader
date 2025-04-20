package es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.ValorCaracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValorCaracteristicaRepository extends JpaRepository<ValorCaracteristica, Long> {
    @Query("SELECT vc FROM ValorCaracteristica vc LEFT JOIN FETCH vc.caracteristica WHERE vc.anuncio.idAnuncio = ?1")
    List<ValorCaracteristica> findByIdAnuncio(long idAnuncio);
}
