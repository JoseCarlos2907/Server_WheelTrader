package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

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

    private String valorMax;

    private String valorMin;

    // *-- Relaciones --* //
    private List<TipoVehiculo_Caracteristica> tiposVehiculoCaracteristica;

    private List<ValorCaracteristica> valoresCaracteristicas;


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

    public void addTipoVehiculoCaracteristica(TipoVehiculo_Caracteristica caracteristica) {
        this.tiposVehiculoCaracteristica.add(caracteristica);
    }

    public List<ValorCaracteristica> getValoresCaracteristicas() {
        return valoresCaracteristicas;
    }

    public void setValoresCaracteristicas(List<ValorCaracteristica> valoresCaracteristicas) {
        this.valoresCaracteristicas = valoresCaracteristicas;
    }

    public void addValorCaracteristica(ValorCaracteristica caracteristica) {
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
