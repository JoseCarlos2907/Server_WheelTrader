package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "caracteristicas")
public class Caracteristica {
    // *-- Atributos --* //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCaracteristica;

    private String nombre;

    private String valorMax;

    private String valorMin;

    // *-- Relaciones --* //
    // Lo asigno en TipoVehiculo_Caracteristica
    @OneToMany(mappedBy = "caracteristica")
    private List<TipoVehiculo_Caracteristica> tiposVehiculoCaracteristica;

    // Lo asigno en ValorCaracteristica
    @OneToMany(mappedBy = "caracteristica")
    private List<ValorCaracteristica> valoresCaracteristicas;

    // *-- Constructores --* //
    public Caracteristica() {}

    public Caracteristica(String nombre, String valorMax, String valorMin) {
        this.nombre = nombre;
        this.valorMax = valorMax;
        this.valorMin = valorMin;

        this.tiposVehiculoCaracteristica = new ArrayList<>();
        this.valoresCaracteristicas = new ArrayList<>();
    }

    // *-- Getters, Setters --* //

    public Long getIdCaracteristica() {
        return idCaracteristica;
    }

    public void setIdCaracteristica(Long idCaracteristica) {
        this.idCaracteristica = idCaracteristica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValorMax() {
        return valorMax;
    }

    public void setValorMax(String valorMax) {
        this.valorMax = valorMax;
    }

    public String getValorMin() {
        return valorMin;
    }

    public void setValorMin(String valorMin) {
        this.valorMin = valorMin;
    }

    public List<TipoVehiculo_Caracteristica> getTiposVehiculoCaracteristica() {
        return tiposVehiculoCaracteristica;
    }

    public void setTiposVehiculoCaracteristica(List<TipoVehiculo_Caracteristica> tiposVehiculoCaracteristica) {
        this.tiposVehiculoCaracteristica = tiposVehiculoCaracteristica;
    }

    public void addTipoVehiculoCaracteristica(TipoVehiculo_Caracteristica tipoVehiculoCaracteristica) {
        if(!this.tiposVehiculoCaracteristica.contains(tipoVehiculoCaracteristica)) {
            this.tiposVehiculoCaracteristica.add(tipoVehiculoCaracteristica);
        }
    }

    public List<ValorCaracteristica> getValoresCaracteristicas() {
        return valoresCaracteristicas;
    }

    public void setValoresCaracteristicas(List<ValorCaracteristica> valoresCaracteristicas) {
        this.valoresCaracteristicas = valoresCaracteristicas;
    }

    public void addValorCaracteristica(ValorCaracteristica valor) {
        if(!this.valoresCaracteristicas.contains(valor)) {
            this.valoresCaracteristicas.add(valor);
        }
    }


    // *-- Métodos --* //
}
