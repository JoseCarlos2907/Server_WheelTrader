package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta {
    // *-- Atributos --* //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    private LocalDateTime fechaFinGarantia;

    // *-- Relaciones --* //
    @OneToOne
    @JoinColumn(name = "anuncio_id")
    private Anuncio anuncio;

    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor;

    @ManyToOne
    @JoinColumn(name = "comprador_id")
    private Usuario comprador;

    @OneToMany(mappedBy = "venta")
    private List<Pago> pagos;

    // *-- Constructores --* //
    public Venta() {}

    public Venta(LocalDateTime fechaFinGarantia) {
        this.fechaFinGarantia = fechaFinGarantia;

        this.pagos = new ArrayList<>();
    }

    // *-- Getters, Setters --* //

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
        if(!this.pagos.contains(pago)){
            this.pagos.add(pago);
        }
        pago.setVenta(this);
    }


    // *-- Métodos --* //
}
