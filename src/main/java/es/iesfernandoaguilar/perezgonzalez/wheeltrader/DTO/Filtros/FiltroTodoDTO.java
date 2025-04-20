package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.Filtros;

import java.util.ArrayList;
import java.util.List;

public class FiltroTodoDTO {
    private String marca;
    private String modelo;
    private int anioMinimo;
    private int anioMaximo;
    private String provincia;
    private String ciudad;
    private double precioMinimo;
    private double precioMaximo;
    private List<String> tiposVehiculo;
    private int pagina;
    private int cantidadPorPagina;
    private String tipoFiltro;

    public FiltroTodoDTO() {
        this.tiposVehiculo = new ArrayList<>();
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnioMinimo() {
        return anioMinimo;
    }

    public void setAnioMinimo(int anioMinimo) {
        this.anioMinimo = anioMinimo;
    }

    public int getAnioMaximo() {
        return anioMaximo;
    }

    public void setAnioMaximo(int anioMaximo) {
        this.anioMaximo = anioMaximo;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public double getPrecioMinimo() {
        return precioMinimo;
    }

    public void setPrecioMinimo(double precioMinimo) {
        this.precioMinimo = precioMinimo;
    }

    public double getPrecioMaximo() {
        return precioMaximo;
    }

    public void setPrecioMaximo(double precioMaximo) {
        this.precioMaximo = precioMaximo;
    }

    public List<String> getTiposVehiculo() {
        return tiposVehiculo;
    }

    public void setTiposVehiculo(List<String> tiposVehiculo) {
        this.tiposVehiculo = tiposVehiculo;
    }

    public void addTipoVehiculo(String tipoVehiculo) {
        this.tiposVehiculo.add(tipoVehiculo);
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getCantidadPorPagina() {
        return cantidadPorPagina;
    }

    public void setCantidadPorPagina(int cantidadPorPagina) {
        this.cantidadPorPagina = cantidadPorPagina;
    }

    public String getTipoFiltro() {
        return tipoFiltro;
    }

    public void setTipoFiltro(String tipoFiltro) {
        this.tipoFiltro = tipoFiltro;
    }
}
