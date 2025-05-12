package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.Filtros;

public class FiltroReportesDTO {
    private int pagina;
    private int cantidadPorPagina;
    private String tipoFiltro;

    public FiltroReportesDTO() {}

    public FiltroReportesDTO(String cadena, int pagina, int cantidadPorPagina, String tipoFiltro) {
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

    public String getTipoFiltro() {
        return tipoFiltro;
    }

    public void setTipoFiltro(String tipoFiltro) {
        this.tipoFiltro = tipoFiltro;
    }
}
