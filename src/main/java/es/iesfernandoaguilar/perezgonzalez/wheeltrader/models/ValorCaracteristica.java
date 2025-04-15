package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import jakarta.persistence.*;

@Entity
@Table(name = "valorescaracteristicas")
public class ValorCaracteristica {
    // *-- Atributos --* //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idValorCaracteristica;

    private String valor;

    // *-- Relaciones --* //
    // Lo asigno en Anuncio
    @ManyToOne
    @JoinColumn(name = "anuncio_id")
    private Anuncio anuncio;

    // Lo asigno aquí
    @ManyToOne
    @JoinColumn(name = "caracteristica_id")
    private Caracteristica caracteristica;

    // *-- Constructores --* //
    public ValorCaracteristica() {}
    public ValorCaracteristica(String valor) {
        this.valor = valor;
    }

    // *-- Getters, Setters --* //
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

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public Caracteristica getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(Caracteristica caracteristica) {
        this.caracteristica = caracteristica;
        caracteristica.addValorCaracteristica(this);
    }


    // *-- Métodos --* //
}
