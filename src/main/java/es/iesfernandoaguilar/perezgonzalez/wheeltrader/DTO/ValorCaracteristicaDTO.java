package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Anuncio;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Caracteristica;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.ValorCaracteristica;
import jakarta.persistence.*;

public class ValorCaracteristicaDTO {
    // *-- Atributos --* //
    private Long idValorCaracteristica;

    private String valor;


    // *-- Relaciones --* //
    private AnuncioDTO anuncio;

    private String nombreCaracteristica;


    // *-- Constructores --* //
    public ValorCaracteristicaDTO() {}

    public ValorCaracteristicaDTO(String valor){
        this.valor = valor;
    }

    // *-- Getters y Setters --* //
    public Long getIdValorCaracteristica() {
        return idValorCaracteristica;
    }

    public void setIdValorCaracteristica(Long idValorCaracteristica) {
        this.idValorCaracteristica = idValorCaracteristica;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public AnuncioDTO getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(AnuncioDTO anuncio) {
        this.anuncio = anuncio;
    }

    public String getNombreCaracteristica() {
        return nombreCaracteristica;
    }

    public void setNombreCaracteristica(String nombreCaracteristica) {
        this.nombreCaracteristica = nombreCaracteristica;
    }

    // *-- Métodos --* //
    public void parse(ValorCaracteristica valorCaracteristica) {
        this.idValorCaracteristica = valorCaracteristica.getIdValorCaracteristica();
        this.valor = valorCaracteristica.getValor();

        this.anuncio = null;
        this.nombreCaracteristica = valorCaracteristica.getCaracteristica().getNombre();
    }
}
