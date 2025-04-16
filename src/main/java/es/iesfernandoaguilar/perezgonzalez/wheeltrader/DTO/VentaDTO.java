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
    private AnuncioDTO anuncio;

    private UsuarioDTO vendedor;

    private UsuarioDTO comprador;

    private List<PagoDTO> pagos;

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

    public AnuncioDTO getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(AnuncioDTO anuncio) {
        this.anuncio = anuncio;
    }

    public UsuarioDTO getVendedor() {
        return vendedor;
    }

    public void setVendedor(UsuarioDTO vendedor) {
        this.vendedor = vendedor;
    }

    public UsuarioDTO getComprador() {
        return comprador;
    }

    public void setComprador(UsuarioDTO comprador) {
        this.comprador = comprador;
    }

    public List<PagoDTO> getPagos() {
        return pagos;
    }

    public void setPagos(List<PagoDTO> pagos) {
        this.pagos = pagos;
    }

    public void addPago(PagoDTO pago) {
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
