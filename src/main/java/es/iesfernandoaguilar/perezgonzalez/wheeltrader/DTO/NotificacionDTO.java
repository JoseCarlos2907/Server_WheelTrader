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

    private EstadoNotificacion estado;

    private TipoNotificacion tipo;


    // *-- Relaciones --* //
    private Usuario usuarioEnvia;

    private Usuario usuarioRecibe;


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

    public EstadoNotificacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoNotificacion estado) {
        this.estado = estado;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoNotificacion tipo) {
        this.tipo = tipo;
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
    public void parse(Notificacion notificacion) {
        this.idNotificacion = notificacion.getIdNotificacion();
        this.titulo = notificacion.getTitulo();
        this.mensaje = notificacion.getMensaje();
        this.estado = notificacion.getEstado();
        this.tipo = notificacion.getTipo();

        this.usuarioEnvia = null;
        this.usuarioRecibe = null;
    }
}
