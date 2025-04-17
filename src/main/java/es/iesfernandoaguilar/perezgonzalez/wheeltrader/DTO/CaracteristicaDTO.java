package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.TipoDatoCaracteristica;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Caracteristica;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.TipoVehiculo_Caracteristica;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.ValorCaracteristica;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

public class CaracteristicaDTO {
    // *-- Atributos --* //
    private Long idCaracteristica;

    private String nombre;

    private String tipo_dato;

    private int valorMax;

    private int valorMin;

    private String opciones;

    // *-- Relaciones --* //
    private List<TipoVehiculo_CaracteristicaDTO> tiposVehiculoCaracteristica;

    private List<ValorCaracteristicaDTO> valoresCaracteristicas;


    // *-- Constructores --* //
    public CaracteristicaDTO() {}


    // *-- Getters y Setters --* //
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

    public String getTipo_dato() {
        return tipo_dato;
    }

    public void setTipo_dato(String tipo_dato) {
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

    public List<TipoVehiculo_CaracteristicaDTO> getTiposVehiculoCaracteristica() {
        return tiposVehiculoCaracteristica;
    }

    public void setTiposVehiculoCaracteristica(List<TipoVehiculo_CaracteristicaDTO> tiposVehiculoCaracteristica) {
        this.tiposVehiculoCaracteristica = tiposVehiculoCaracteristica;
    }

    public void addTipoVehiculoCaracteristica(TipoVehiculo_CaracteristicaDTO caracteristica) {
        this.tiposVehiculoCaracteristica.add(caracteristica);
    }

    public List<ValorCaracteristicaDTO> getValoresCaracteristicas() {
        return valoresCaracteristicas;
    }

    public void setValoresCaracteristicas(List<ValorCaracteristicaDTO> valoresCaracteristicas) {
        this.valoresCaracteristicas = valoresCaracteristicas;
    }

    public void addValorCaracteristica(ValorCaracteristicaDTO caracteristica) {
        this.valoresCaracteristicas.add(caracteristica);
    }


    // *-- Métodos --* //
    public void parse(Caracteristica caracteristica) {
        this.idCaracteristica = caracteristica.getIdCaracteristica();
        this.nombre = caracteristica.getNombre();
        this.valorMax = caracteristica.getValorMax();
        this.valorMin = caracteristica.getValorMin();

        this.tiposVehiculoCaracteristica = null;
        this.valoresCaracteristicas = null;
    }
}
