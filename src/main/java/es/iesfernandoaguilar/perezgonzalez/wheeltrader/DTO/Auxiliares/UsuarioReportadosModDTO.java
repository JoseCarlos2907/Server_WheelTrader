package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.Auxiliares;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.UsuarioDTO;

public class UsuarioReportadosModDTO {
    private UsuarioDTO usuario;
    private long cantReportes;
    private double mediaValoraciones;

    public UsuarioReportadosModDTO() {}

    public UsuarioReportadosModDTO(UsuarioDTO usuario, long cantReportes, double mediaValoraciones) {
        this.usuario = usuario;
        this.cantReportes = cantReportes;
        this.mediaValoraciones = mediaValoraciones;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
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