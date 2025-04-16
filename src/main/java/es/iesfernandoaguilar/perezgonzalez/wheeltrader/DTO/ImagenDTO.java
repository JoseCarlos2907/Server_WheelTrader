package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Anuncio;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Imagen;
import jakarta.persistence.*;

public class ImagenDTO {
    // *-- Atributos --* //
    private Long idImagen;

    private String imgBase64;


    // *-- Relaciones --* //
    private Anuncio anuncio;


    // *-- Constructores --* //
    public ImagenDTO() {}


    // *-- Getters y Setters --* //
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
    public void parse(Imagen imagen){
        this.idImagen = imagen.getIdImagen();
        this.imgBase64 = imagen.getImgBase64();

        this.anuncio = null;
    }
}
