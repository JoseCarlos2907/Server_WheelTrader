package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.EstadoNotificacion;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.TipoNotificacion;
import jakarta.persistence.*;

@Entity
@Table(name = "notificaciones")
public class Notificacion {
    // *-- Atributos --* //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotificacion;

    private String titulo;

    private String mensaje;

    private EstadoNotificacion estado;

    private TipoNotificacion tipo;

    // *-- Relaciones --* //
    @ManyToOne
    @JoinColumn(name = "usuario_envia_id")
    private Usuario usuarioEnvia;

    @ManyToOne
    @JoinColumn(name = "usuario_recibe_id")
    private Usuario usuarioRecibe;

    @ManyToOne
    @JoinColumn(name = "anuncio_id")
    private Anuncio anuncio;

    // *-- Constructores --* //
    public Notificacion() {}

    public Notificacion(String titulo, String mensaje) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.estado = EstadoNotificacion.NO_LEIDO;
        this.tipo = null;
    }

    // *-- Getters, Setters --* //

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
}
