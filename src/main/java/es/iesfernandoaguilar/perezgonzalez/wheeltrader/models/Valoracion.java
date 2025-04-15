package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import jakarta.persistence.*;

@Entity
@Table(name = "valoraciones")
public class Valoracion {
    // *-- Atributos --* //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idValoracion;

    private int valoracion;

    private String comentario;

    // *-- Relaciones --* //
    @ManyToOne
    @JoinColumn(name = "usuario_envia_id")
    private Usuario usuarioEnvia;

    @ManyToOne
    @JoinColumn(name = "usuario_recibe_id")
    private Usuario usuarioRecibe;

    // *-- Constructores --* //
    public Valoracion() {}

    public Valoracion(int valoracion, String comentario) {
        this.valoracion = valoracion;
        this.comentario = comentario;
    }

    // *-- Getters, Setters --* //

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
