package es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Reporte;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    @Query("select r from Reporte r left join fetch r.usuarioEnvia left join fetch r.usuarioRecibe order by r.idReporte desc")
    List<Reporte> findUltimosReportes(Pageable pageable);

    @Modifying
    @Query(value = "insert into reportes(explicacion, motivo, usuario_envia_id, usuario_recibe_id) values(:explicacion, :motivo, :idUsuarioReporta, :idUsuarioReportado);", nativeQuery = true)
    void reportarUsuario(
            @Param("idUsuarioReporta") Long idUsuarioReporta,
            @Param("idUsuarioReportado") Long idUsuarioReportado,
            @Param("explicacion") String explicacion,
            @Param("motivo") String motivo
    );

    @Query("select r from Reporte r where r.usuarioEnvia.idUsuario = ?1 and r.usuarioRecibe.idUsuario = ?2")
    Reporte findByIdReportaAndReportado(Long idReporta, Long idReportado);
}
