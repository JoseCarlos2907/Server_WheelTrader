package es.iesfernandoaguilar.perezgonzalez.wheeltrader.repositories;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Venta;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    @Query(value = "insert into ventas(fecha_fin_garantia, anuncio_id, comprador_id, vendedor_id) values(:fechaFinGarantia, :idAnuncio, :idComprador, :idVendedor);", nativeQuery = true)
    void crearVenta(
            @Param("fechaFinGarantia") LocalDateTime fechaFinGarantia,
            @Param("idAnuncio") Long idAnuncio,
            @Param("idComprador") Long idComprador,
            @Param("idVendedor") Long idVendedor
    );

    @Query("select v from Venta v left join fetch v.pagos where v.anuncio.idAnuncio = ?1")
    Venta findVentaWithPagosByIdAnuncio(Long idAnuncio);

    @Query("""
        select v from Venta v 
        left join fetch v.anuncio
        left join fetch v.anuncio.valoresCaracteristicas vcs
        left join fetch vcs.caracteristica
        left join fetch v.anuncio.vendedor 
        where v.comprador.nombreUsuario = ?1""")
    List<Venta> findVentasByNombreUsuario(String nombreUsuario, Pageable pageable);
}
