package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.EstadoRevision;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Reunion;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Revision;
import jakarta.persistence.*;

public class RevisionDTO {
    // *-- Atributos --* //
    private Long idRevision;

    private String imagenBase64;

    private String asunto;

    private String descripcion;

    private EstadoRevision estado;


    // *-- Relaciones --* //
    private ReunionDTO reunion;


    // *-- Constructores --* //
    public RevisionDTO(){}


    // *-- Getters y Setters --* //
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

    public ReunionDTO getReunion() {
        return reunion;
    }

    public void setReunion(ReunionDTO reunion) {
        this.reunion = reunion;
    }

    // *-- Métodos --* //
    public void parse(Revision revision){
        this.idRevision = revision.getIdRevision();
        this.imagenBase64 = revision.getImagenBase64();
        this.asunto = revision.getAsunto();
        this.descripcion = revision.getDescripcion();
        this.estado = revision.getEstado();

        this.reunion = null;
    }
}
