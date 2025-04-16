package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Reporte;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;
import jakarta.persistence.*;

public class ReporteDTO {
    // *-- Atributos --* //
    private Long idReporte;

    private String motivo;

    private String explicacion;

    // *-- Relaciones --* //
    private UsuarioDTO usuarioEnvia;

    private UsuarioDTO usuarioRecibe;


    // *-- Constructores --* //
    public ReporteDTO() {}


    // *-- Getters y Setters --* //
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

    public UsuarioDTO getUsuarioEnvia() {
        return usuarioEnvia;
    }

    public void setUsuarioEnvia(UsuarioDTO usuarioEnvia) {
        this.usuarioEnvia = usuarioEnvia;
    }

    public UsuarioDTO getUsuarioRecibe() {
        return usuarioRecibe;
    }

    public void setUsuarioRecibe(UsuarioDTO usuarioRecibe) {
        this.usuarioRecibe = usuarioRecibe;
    }

    // *-- Métodos --* //
    public void parse(Reporte reporte) {
        this.idReporte = reporte.getIdReporte();
        this.motivo = reporte.getMotivo();

        this.usuarioEnvia = null;
        this.usuarioRecibe = null;
    }
}
