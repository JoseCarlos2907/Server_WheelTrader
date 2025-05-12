package es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Reporte;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    @Query("select r from Reporte r left join fetch r.usuarioEnvia left join fetch r.usuarioRecibe order by r.idReporte desc")
    List<Reporte> findUltimosReportes(Pageable pageable);
}
