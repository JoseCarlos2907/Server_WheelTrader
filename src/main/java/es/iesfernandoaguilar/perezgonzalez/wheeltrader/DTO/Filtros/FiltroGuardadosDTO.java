package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.Filtros;

public class FiltroGuardadosDTO {
    private String nombreUsuario;
    private int pagina;
    private int cantidadPorPagina;
    private String tipoFiltro;

    public FiltroGuardadosDTO() {}

    public FiltroGuardadosDTO(String nombreUsuario, int pagina, int cantidadPorPagina) {
        this.nombreUsuario = nombreUsuario;
        this.pagina = pagina;
        this.cantidadPorPagina = cantidadPorPagina;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
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
