package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Auxiliares;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.UsuarioDTO;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;

public class UsuarioReportadosMod {
    private Usuario usuario;
    private long cantReportes;
    private double mediaValoraciones;

    public UsuarioReportadosMod() {}

    public UsuarioReportadosMod(Usuario usuario, long cantReportes, double mediaValoraciones) {
        this.usuario = usuario;
        this.cantReportes = cantReportes;
        this.mediaValoraciones = mediaValoraciones;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public long getCantReportes() {
        return cantReportes;
    }

    public void setCantReportes(long cantReportes) {
        this.cantReportes = cantReportes;
    }

    public double getMediaValoraciones() {
        return mediaValoraciones;
    }

    public void setMediaValoraciones(double mediaValoraciones) {
        this.mediaValoraciones = mediaValoraciones;
    }
}
