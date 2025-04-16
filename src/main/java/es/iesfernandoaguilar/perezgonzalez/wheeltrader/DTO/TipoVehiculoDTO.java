package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Anuncio;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.TipoVehiculo;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.TipoVehiculo_Caracteristica;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

public class TipoVehiculoDTO {
    // *-- Atributos --* //
    private Long idTipoVehiculo;

    private String tipo;


    // *-- Relaciones --* //
    private List<AnuncioDTO> anuncios;

    private List<TipoVehiculo_CaracteristicaDTO> tiposVehiculoCaracteristicas;


    // *-- Constructores --* //
    public TipoVehiculoDTO() {}


    // *-- Getters y Setters --* //
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

    public List<AnuncioDTO> getAnuncios() {
        return anuncios;
    }

    public void setAnuncios(List<AnuncioDTO> anuncios) {
        this.anuncios = anuncios;
    }

    public void addAnuncio(AnuncioDTO anuncio) {
        this.anuncios.add(anuncio);
    }

    public List<TipoVehiculo_CaracteristicaDTO> getTiposVehiculoCaracteristicas() {
        return tiposVehiculoCaracteristicas;
    }

    public void setTiposVehiculoCaracteristicas(List<TipoVehiculo_CaracteristicaDTO> tiposVehiculoCaracteristicas) {
        this.tiposVehiculoCaracteristicas = tiposVehiculoCaracteristicas;
    }

    public void addTipoVehiculoCaracteristica(TipoVehiculo_CaracteristicaDTO caracteristica) {
        this.tiposVehiculoCaracteristicas.add(caracteristica);
    }

    // *-- Métodos --* //
    public void parse(TipoVehiculo tipoVehiculo) {
        this.idTipoVehiculo = tipoVehiculo.getIdTipoVehiculo();
        this.tipo = tipoVehiculo.getTipo();

        this.anuncios = null;
        this.tiposVehiculoCaracteristicas = null;
    }
}
