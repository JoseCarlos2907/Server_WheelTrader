package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Pago;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Venta;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class PagoDTO {
    // *-- Atributos --* //
    private Long idPago;

    private double cantidad;

    private LocalDateTime fechaPago;


    // *-- Relaciones --* //
    private VentaDTO venta;


    // *-- Constructores --* //
    public PagoDTO() {}


    // *-- Getters y Setters --* //
    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public VentaDTO getVenta() {
        return venta;
    }

    public void setVenta(VentaDTO venta) {
        this.venta = venta;
    }

    // *-- Métodos --* //
    public void parse(Pago pago){
        this.idPago = pago.getIdPago();
        this.cantidad = pago.getCantidad();
        this.fechaPago = pago.getFechaPago();

        this.venta = null;
    }
}
