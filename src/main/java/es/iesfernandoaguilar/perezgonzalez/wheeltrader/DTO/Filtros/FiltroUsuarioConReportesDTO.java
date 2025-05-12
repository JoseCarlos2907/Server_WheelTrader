package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.Filtros;

public class FiltroUsuarioConReportesDTO {
    private String cadena;
    private int pagina;
    private int cantidadPorPagina;
    private String tipoFiltro;

    public FiltroUsuarioConReportesDTO() {}

    public FiltroUsuarioConReportesDTO(String cadena, int pagina, int cantidadPorPagina, String tipoFiltro) {
        this.cadena = cadena;
        this.pagina = pagina;
        this.cantidadPorPagina = cantidadPorPagina;
        this.tipoFiltro = tipoFiltro;
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

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    public String getTipoFiltro() {
        return tipoFiltro;
    }

    public void setTipoFiltro(String tipoFiltro) {
        this.tipoFiltro = tipoFiltro;
    }
}
