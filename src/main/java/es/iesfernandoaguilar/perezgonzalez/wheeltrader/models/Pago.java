package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pago {
    // *-- Atributos --* //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    private double cantidad;

    private LocalDateTime fechaPago;

    // *-- Relaciones --* //
    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    // *-- Constructores --* //
    public Pago() {}

    public Pago(double cantidad, LocalDateTime fechaPago, Venta venta) {
        this.cantidad = cantidad;
        this.fechaPago = fechaPago;
        this.venta = venta;
    }

    // *-- Getters, Setters --* //

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

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }


    // *-- Métodos --* //
}
