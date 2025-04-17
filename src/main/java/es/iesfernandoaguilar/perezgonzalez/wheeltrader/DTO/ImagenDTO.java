package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Anuncio;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Imagen;
import jakarta.persistence.*;

public class ImagenDTO {
    // *-- Atributos --* //
    private Long idImagen;

    private byte[] imagen;


    // *-- Relaciones --* //
    private AnuncioDTO anuncio;


    // *-- Constructores --* //
    public ImagenDTO() {}

    public ImagenDTO(byte[] imagen) {
        this.imagen = imagen;
    }

    // *-- Getters y Setters --* //
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

    public AnuncioDTO getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(AnuncioDTO anuncio) {
        this.anuncio = anuncio;
    }

    // *-- Métodos --* //
    public void parse(Imagen imagen){
        this.idImagen = imagen.getIdImagen();
        this.imagen = imagen.getImagen();

        this.anuncio = null;
    }
}
