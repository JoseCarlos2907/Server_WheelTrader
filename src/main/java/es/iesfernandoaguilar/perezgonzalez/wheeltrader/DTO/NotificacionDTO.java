package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.EstadoNotificacion;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.TipoNotificacion;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Notificacion;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;
import jakarta.persistence.*;

public class NotificacionDTO {
    // *-- Atributos --* //
    private Long idNotificacion;

    private String titulo;

    private String mensaje;

    private String estado;

    private String tipo;


    // *-- Relaciones --* //
    private UsuarioDTO usuarioEnvia;

    private UsuarioDTO usuarioRecibe;

    private AnuncioDTO anuncio;


    // *-- Constructores --* //
    public NotificacionDTO() {}


    // *-- Getters y Setters --* //
    public Long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public AnuncioDTO getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(AnuncioDTO anuncio) {
        this.anuncio = anuncio;
    }

    // *-- Métodos --* //
    public void parse(Notificacion notificacion) {
        this.idNotificacion = notificacion.getIdNotificacion();
        this.titulo = notificacion.getTitulo();
        this.mensaje = notificacion.getMensaje();
        this.estado = notificacion.getEstado().toString();
        this.tipo = notificacion.getTipo().toString();

        this.usuarioEnvia = null;
        this.usuarioRecibe = null;
    }
}
