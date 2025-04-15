package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tipos_vehiculos")
public class TipoVehiculo {
    // *-- Atributos --* //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoVehiculo;

    private String tipo;

    // *-- Relaciones --* //
    // Se asigna desde Anuncio
    @OneToMany(mappedBy = "tipoVehiculo")
    private List<Anuncio> anuncios;

    // Se asigna desde TipoVehiculo_Caracteristica
    @OneToMany(mappedBy = "tipoVehiculo")
    private List<TipoVehiculo_Caracteristica> tiposVehiculoCaracteristicas;

    // *-- Constructores --* //
    public TipoVehiculo() {}

    public TipoVehiculo(String tipo) {
        this.tipo = tipo;

        this.anuncios = new ArrayList<>();
        this.tiposVehiculoCaracteristicas = new ArrayList<>();
    }

    // *-- Getters, Setters --* //

    public Long getIdTipoVehiculo() {
        return idTipoVehiculo;
    }

    public void setIdTipoVehiculo(Long idTipoVehiculo) {
        this.idTipoVehiculo = idTipoVehiculo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Anuncio> getAnuncios() {
        return anuncios;
    }

    public void setAnuncios(List<Anuncio> anuncios) {
        this.anuncios = anuncios;
    }

    public void addAnuncio(Anuncio anuncio) {
        if(!this.anuncios.contains(anuncio)) {
            this.anuncios.add(anuncio);
        }
    }

    public List<TipoVehiculo_Caracteristica> getTipoVehiculoCaracteristicas() {
        return tiposVehiculoCaracteristicas;
    }

    public void setTipoVehiculoCaracteristicas(List<TipoVehiculo_Caracteristica> tipoVehiculoCaracteristicas) {
        this.tiposVehiculoCaracteristicas = tipoVehiculoCaracteristicas;
    }

    public void addTipoVehiculoCaracteristica(TipoVehiculo_Caracteristica tipoVehiculoCaracteristica) {
        if(!this.tiposVehiculoCaracteristicas.contains(tipoVehiculoCaracteristica)){
            this.tiposVehiculoCaracteristicas.add(tipoVehiculoCaracteristica);
        }
    }


    // *-- Métodos --* //
}
