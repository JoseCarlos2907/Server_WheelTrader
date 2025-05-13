package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.Filtros;

import java.util.List;

public class FiltroBarraBusqueda {
    private String cadena;
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

    public FiltroBarraBusqueda() {
    }

    public FiltroBarraBusqueda(String cadena, int anioMinimo, int anioMaximo, String provincia, String ciudad, double precioMinimo, double precioMaximo, List<String> tiposVehiculo, int pagina, int cantidadPorPagina, String tipoFiltro) {
        this.cadena = cadena;
        this.anioMinimo = anioMinimo;
        this.anioMaximo = anioMaximo;
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.precioMinimo = precioMinimo;
        this.precioMaximo = precioMaximo;
        this.tiposVehiculo = tiposVehiculo;
        this.pagina = pagina;
        this.cantidadPorPagina = cantidadPorPagina;
        this.tipoFiltro = tipoFiltro;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
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
