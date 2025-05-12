package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Reporte;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;

    public void save(Reporte reporte) {
        this.reporteRepository.save(reporte);
    }


    public List<Reporte> findUltimosReportes(Pageable pageable) {
        return this.reporteRepository.findUltimosReportes(pageable);
    }
}
