package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.EstadoReunion;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

public class ReunionDTO {
    // *-- Atributos --* //
    private Long idReunion;

    private String calle;

    private LocalDateTime fecha;

    private EstadoReunion estado;


    // *-- Relaciones --* //
    private Anuncio anuncio;

    private Usuario vendedor;

    private Usuario comprador;

    private List<Revision> revisiones;

    private Cuestionario cuestionario;


    // *-- Constructores --* //
    public ReunionDTO() {}


    // *-- Getters y Setters --* //
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
        this.revisiones.add(revision);
    }

    public Cuestionario getCuestionario() {
        return cuestionario;
    }

    public void setCuestionario(Cuestionario cuestionario) {
        this.cuestionario = cuestionario;
    }

    // *-- Métodos --* //
    public void parse(Reunion reunion) {
        this.idReunion = reunion.getIdReunion();
        this.calle = reunion.getCalle();
        this.fecha = reunion.getFecha();
        this.estado = reunion.getEstado();

        this.anuncio = null;
        this.vendedor = null;
        this.comprador = null;
        this.revisiones = null;
        this.cuestionario = null;
    }
}
