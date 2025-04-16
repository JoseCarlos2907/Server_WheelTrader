package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import jakarta.persistence.*;

@Entity
@Table(name = "reportes")
public class Reporte {
    // *-- Atributos --* //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReporte;

    private String motivo;

    private String explicacion;

    // *-- Relaciones --* //
    @ManyToOne
    @JoinColumn(name = "usuario_envia_id")
    private Usuario usuarioEnvia;

    @ManyToOne
    @JoinColumn(name = "usuario_recibe_id")
    private Usuario usuarioRecibe;

    // *-- Constructores --* //
    public Reporte() {}

    public Reporte(String motivo, String explicacion) {
        this.motivo = motivo;
        this.explicacion = explicacion;
    }

    // *-- Getters, Setters --* //
    public Long getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Long idReporte) {
        this.idReporte = idReporte;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getExplicacion() {
        return explicacion;
    }

    public void setExplicacion(String explicacion) {
        this.explicacion = explicacion;
    }

    public Usuario getUsuarioEnvia() {
        return usuarioEnvia;
    }

    public void setUsuarioEnvia(Usuario usuarioEnvia) {
        this.usuarioEnvia = usuarioEnvia;
    }

    public Usuario getUsuarioRecibe() {
        return usuarioRecibe;
    }

    public void setUsuarioRecibe(Usuario usuarioRecibe) {
        this.usuarioRecibe = usuarioRecibe;
    }

    // *-- Métodos --* //


}
