package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.EstadoRevision;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Reunion;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Revision;
import jakarta.persistence.*;

public class RevisionDTO {
    // *-- Atributos --* //
    private Long idRevision;

    private byte[] imagen;

    private String asunto;

    private String descripcion;

    private String estado;


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

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
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
        this.imagen = revision.getImagen();
        this.asunto = revision.getAsunto();
        this.descripcion = revision.getDescripcion();
        this.estado = revision.getEstado().toString();

        this.reunion = null;
    }
}
