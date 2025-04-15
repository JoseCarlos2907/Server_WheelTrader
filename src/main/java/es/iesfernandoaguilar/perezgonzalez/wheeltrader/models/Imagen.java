package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import jakarta.persistence.*;

@Entity
@Table(name = "imagenes")
public class Imagen {
    // *-- Atributos --* //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImagen;

    private String imgBase64;

    // *-- Relaciones --* //
    @ManyToOne
    @JoinColumn(name = "anuncio_id")
    private Anuncio anuncio;

    // *-- Constructores --* //
    public Imagen() {}

    public Imagen(String imgBase64) {
        this.imgBase64 = imgBase64;
    }

    // *-- Getters, Setters --* //

    public Long getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Long idImagen) {
        this.idImagen = idImagen;
    }

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }


    // *-- Métodos --* //
}
