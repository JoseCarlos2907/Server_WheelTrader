package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tiposvehiculos_caracteristicas")
public class TipoVehiculo_Caracteristica {
    // *-- Atributos --* //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoVehiculo_Caracteristica;

    private boolean obligatorio;

    // *-- Relaciones --* //
    // Lo asigno aquí
    @ManyToOne
    @JoinColumn(name = "tipo_vehiculo_id")
    private TipoVehiculo tipoVehiculo;

    // Lo asigno aquí
    @ManyToOne
    @JoinColumn(name = "caracteristica_id")
    private Caracteristica caracteristica;

    // *-- Constructores --* //
    public TipoVehiculo_Caracteristica() {}

    public TipoVehiculo_Caracteristica(boolean obligatorio){
        this.obligatorio = obligatorio;
    }

    // *-- Getters, Setters --* //

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

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
        tipoVehiculo.addTipoVehiculoCaracteristica(this);
    }

    public Caracteristica getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(Caracteristica caracteristica) {
        this.caracteristica = caracteristica;
        caracteristica.addTipoVehiculoCaracteristica(this);
    }


    // *-- Métodos --* //
}
