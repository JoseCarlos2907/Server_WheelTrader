package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Caracteristica;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.TipoVehiculo;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.TipoVehiculo_Caracteristica;
import jakarta.persistence.*;

public class TipoVehiculo_CaracteristicaDTO {
    // *-- Atributos --* //
    private Long idTipoVehiculo_Caracteristica;

    private boolean obligatorio;


    // *-- Relaciones --* //
    private TipoVehiculoDTO tipoVehiculo;

    private CaracteristicaDTO caracteristica;


    // *-- Constructores --* //
    public TipoVehiculo_CaracteristicaDTO() {}


    // *-- Getters y Setters --* //
    public Long getIdTipoVehiculo_Caracteristica() {
        return idTipoVehiculo_Caracteristica;
    }

    public void setIdTipoVehiculo_Caracteristica(Long idTipoVehiculo_Caracteristica) {
        this.idTipoVehiculo_Caracteristica = idTipoVehiculo_Caracteristica;
    }

    public boolean isObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public TipoVehiculoDTO getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculoDTO tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public CaracteristicaDTO getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(CaracteristicaDTO caracteristica) {
        this.caracteristica = caracteristica;
    }

    // *-- Métodos --* //
    public void parse(TipoVehiculo_Caracteristica tipoVehiculo_caracteristica){
        this.idTipoVehiculo_Caracteristica = tipoVehiculo_caracteristica.getIdTipoVehiculo_Caracteristica();
        this.obligatorio = tipoVehiculo_caracteristica.isObligatorio();

        this.tipoVehiculo = null;
        this.caracteristica = null;
    }
}
