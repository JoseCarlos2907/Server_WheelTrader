package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import jakarta.persistence.*;

@Entity
@Table(name = "imagenes")
public class Imagen {
    // *-- Atributos --* //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImagen;

    @Lob
    private byte[] imagen;

    // *-- Relaciones --* //
    @ManyToOne
    @JoinColumn(name = "anuncio_id")
    private Anuncio anuncio;

    // *-- Constructores --* //
    public Imagen() {}

    public Imagen(byte[] imagen) {
        this.imagen = imagen;
    }

    // *-- Getters, Setters --* //

    public Long getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Long idImagen) {
        this.idImagen = idImagen;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }


    // *-- Métodos --* //
}
