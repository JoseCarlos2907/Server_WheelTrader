package es.iesfernandoaguilar.perezgonzalez.wheeltrader.sevices;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Venta;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaService {
    @Autowired
    private VentaRepository ventaRepository;

    public void save(Venta venta) {
        this.ventaRepository.save(venta);
    }

    public void crearVenta(LocalDateTime fechaFinGarantia, Long idAnuncio, Long idComprador, Long idVendedor){
        this.ventaRepository.crearVenta(fechaFinGarantia, idAnuncio, idComprador, idVendedor);
    }

    public Venta findVentaWithPagosByIdAnuncio(Long idAnuncio){
        return this.ventaRepository.findVentaWithPagosByIdAnuncio(idAnuncio);
    }

    public List<Venta> findVentasByNombreUsuario(String nombreUsuario, Pageable pageable){
        return this.ventaRepository.findVentasByNombreUsuario(nombreUsuario, pageable);
    }
}
