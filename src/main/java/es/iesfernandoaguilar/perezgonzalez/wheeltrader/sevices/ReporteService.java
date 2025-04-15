package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Reporte;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;

    public void save(Reporte reporte) {
        this.reporteRepository.save(reporte);
    }
}
