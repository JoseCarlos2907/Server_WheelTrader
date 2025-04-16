package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Valoracion;
import jakarta.persistence.*;

public class ValoracionDTO {
    // *-- Atributos --* //
    private Long idValoracion;

    private int valoracion;

    private String comentario;


    // *-- Relaciones --* //
    private UsuarioDTO usuarioEnvia;

    private UsuarioDTO usuarioRecibe;


    // *-- Constructores --* //
    public ValoracionDTO() {}


    // *-- Getters y Setters --* //
    public Long getIdValoracion() {
        return idValoracion;
    }

    public void setIdValoracion(Long idValoracion) {
        this.idValoracion = idValoracion;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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
    public void parse(Valoracion valoracion) {
        this.idValoracion = valoracion.getIdValoracion();
        this.valoracion = valoracion.getValoracion();
        this.comentario = valoracion.getComentario();

        this.usuarioEnvia = null;
        this.usuarioRecibe = null;
    }
}
