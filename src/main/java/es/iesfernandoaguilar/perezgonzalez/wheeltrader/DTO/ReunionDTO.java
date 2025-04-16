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
    private AnuncioDTO anuncio;

    private UsuarioDTO vendedor;

    private UsuarioDTO comprador;

    private List<RevisionDTO> revisiones;

    private CuestionarioDTO cuestionario;


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

    public List<RevisionDTO> getRevisiones() {
        return revisiones;
    }

    public void setRevisiones(List<RevisionDTO> revisiones) {
        this.revisiones = revisiones;
    }

    public void addRevision(RevisionDTO revision) {
        this.revisiones.add(revision);
    }

    public CuestionarioDTO getCuestionario() {
        return cuestionario;
    }

    public void setCuestionario(CuestionarioDTO cuestionario) {
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
