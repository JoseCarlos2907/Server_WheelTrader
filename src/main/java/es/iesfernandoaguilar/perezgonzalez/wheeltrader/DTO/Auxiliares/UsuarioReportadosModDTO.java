package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.Auxiliares;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.UsuarioDTO;

public class UsuarioReportadosModDTO {
    private UsuarioDTO usuario;
    private long cantReportes;

    public UsuarioReportadosModDTO() {}

    public UsuarioReportadosModDTO(UsuarioDTO usuario, long cantReportes) {
        this.usuario = usuario;
        this.cantReportes = cantReportes;
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
}