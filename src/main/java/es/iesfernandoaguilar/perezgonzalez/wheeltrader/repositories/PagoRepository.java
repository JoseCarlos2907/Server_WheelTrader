package es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Pago;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    @Query("select p from Pago p left join p.venta v where v.comprador.nombreUsuario = ?1")
    List<Pago> findPagosByNombreUsuario(String nombreUsuario, Pageable pageable);
}
