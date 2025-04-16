package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Anuncio;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Pago;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Venta;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

public class VentaDTO {
    // *-- Atributos --* //
    private Long idVenta;

    private LocalDateTime fechaFinGarantia;

    // *-- Relaciones --* //
    private Anuncio anuncio;

    private Usuario vendedor;

    private Usuario comprador;

    private List<Pago> pagos;

    // *-- Constructores --* //
    public VentaDTO() {}


    // *-- Getters y Setters --* //
    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDateTime getFechaFinGarantia() {
        return fechaFinGarantia;
    }

    public void setFechaFinGarantia(LocalDateTime fechaFinGarantia) {
        this.fechaFinGarantia = fechaFinGarantia;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public void setComprador(Usuario comprador) {
        this.comprador = comprador;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public void addPago(Pago pago) {
        this.pagos.add(pago);
    }


    // *-- Métodos --* //
    public void parse(Venta venta) {
        this.idVenta = venta.getIdVenta();
        this.fechaFinGarantia = venta.getFechaFinGarantia();

        this.anuncio = null;
        this.vendedor = null;
        this.comprador = null;
        this.pagos = null;
    }
}
