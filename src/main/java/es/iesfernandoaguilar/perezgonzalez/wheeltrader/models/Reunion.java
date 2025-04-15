package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.EstadoReunion;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reuniones")
public class Reunion {
    // *-- Atributos --* //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReunion;

    private String calle;

    private LocalDateTime fecha;

    private EstadoReunion estado;

    // *-- Relaciones --* //
    @ManyToOne
    @JoinColumn(name = "anuncio_id")
    private Anuncio anuncio;

    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor;

    @ManyToOne
    @JoinColumn(name = "comprador_id")
    private Usuario comprador;

    @OneToMany(mappedBy = "reunion")
    private List<Revision> revisiones;

    @OneToOne(mappedBy = "reunion")
    private Cuestionario cuestionario;

    // *-- Constructores --* //
    public Reunion() {}

    public Reunion(String calle, LocalDateTime fecha) {
        this.calle = calle;
        this.fecha = fecha;
        this.estado = EstadoReunion.PENDIENTE;

        this.revisiones = new ArrayList<>();
    }

    // *-- Getters, Setters --* //

    public Long getIdReunion() {
        return idReunion;
    }

    public void setIdReunion(Long idReunion) {
        this.idReunion = idReunion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public EstadoReunion getEstado() {
        return estado;
    }

    public void setEstado(EstadoReunion estado) {
        this.estado = estado;
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

    public List<Revision> getRevisiones() {
        return revisiones;
    }

    public void setRevisiones(List<Revision> revisiones) {
        this.revisiones = revisiones;
    }

    public void addRevision(Revision revision) {
        if(!this.revisiones.contains(revision)) {
            this.revisiones.add(revision);
        }
        revision.setReunion(this);
    }

    public Cuestionario getCuestionario() {
        return cuestionario;
    }

    public void setCuestionario(Cuestionario cuestionario) {
        this.cuestionario = cuestionario;
        cuestionario.setReunion(this);
    }


    // *-- Métodos --* //
}
