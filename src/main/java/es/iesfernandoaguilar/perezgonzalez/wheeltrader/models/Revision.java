package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.EstadoRevision;
import jakarta.persistence.*;

@Entity
@Table(name = "revisiones")
public class Revision {
    // *-- Atributos --* //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRevision;

    private String imagenBase64;

    private String asunto;

    private String descripcion;

    private EstadoRevision estado;

    // *-- Relaciones --* //
    @ManyToOne
    @JoinColumn(name = "reunion_id")
    private Reunion reunion;

    // *-- Constructores --* //
    public Revision(){}

    public Revision(String asunto, String descripcion) {
        this.asunto = asunto;
        this.descripcion = descripcion;
        this.estado = EstadoRevision.PENDIENTE;
    }

    // *-- Getters, Setters --* //

    public Long getIdRevision() {
        return idRevision;
    }

    public void setIdRevision(Long idRevision) {
        this.idRevision = idRevision;
    }

    public String getImagenBase64() {
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoRevision getEstado() {
        return estado;
    }

    public void setEstado(EstadoRevision estado) {
        this.estado = estado;
    }

    public Reunion getReunion() {
        return reunion;
    }

    public void setReunion(Reunion reunion) {
        this.reunion = reunion;
    }

    // *-- Métodos --* //
}
