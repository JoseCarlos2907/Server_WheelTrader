package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.TipoDatoCaracteristica;
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

    private TipoDatoCaracteristica tipo_dato;

    private int valorMax;

    private int valorMin;

    private String opciones;

    // *-- Relaciones --* //
    // Lo asigno en TipoVehiculo_Caracteristica
    @OneToMany(mappedBy = "caracteristica")
    private List<TipoVehiculo_Caracteristica> tiposVehiculoCaracteristica;

    // Lo asigno en ValorCaracteristica
    @OneToMany(mappedBy = "caracteristica")
    private List<ValorCaracteristica> valoresCaracteristicas;

    // *-- Constructores --* //
    public Caracteristica() {}

    public Caracteristica(String nombre, TipoDatoCaracteristica tipo_dato, int valorMax, int valorMin, String opciones) {
        this.nombre = nombre;
        this.tipo_dato = tipo_dato;
        this.valorMax = valorMax;
        this.valorMin = valorMin;
        this.opciones = opciones;

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

    public TipoDatoCaracteristica getTipo_dato() {
        return tipo_dato;
    }

    public void setTipo_dato(TipoDatoCaracteristica tipo_dato) {
        this.tipo_dato = tipo_dato;
    }

    public int getValorMax() {
        return valorMax;
    }

    public void setValorMax(int valorMax) {
        this.valorMax = valorMax;
    }

    public int getValorMin() {
        return valorMin;
    }

    public void setValorMin(int valorMin) {
        this.valorMin = valorMin;
    }

    public String getOpciones() {
        return opciones;
    }

    public void setOpciones(String opciones) {
        this.opciones = opciones;
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
