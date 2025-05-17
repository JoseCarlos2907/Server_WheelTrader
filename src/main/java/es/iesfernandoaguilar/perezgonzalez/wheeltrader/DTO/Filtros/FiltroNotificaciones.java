package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO.Filtros;

public class FiltroNotificaciones {
    private long idUsuario;
    private int pagina;
    private int cantidadPorPagina;
    private String tipoFiltro;

    public FiltroNotificaciones() {}

    public FiltroNotificaciones(long idUsuario, int pagina, int cantidadPorPagina, String tipoFiltro) {
        this.idUsuario = idUsuario;
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

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoFiltro() {
        return tipoFiltro;
    }

    public void setTipoFiltro(String tipoFiltro) {
        this.tipoFiltro = tipoFiltro;
    }
}
