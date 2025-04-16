package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.EstadoUsuario;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.RolUsuario;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.*;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

public class UsuarioDTO {
    // *-- Atributos --* //
    private Long idUsuario;

    private String nombre;

    private String apellidos;

    private String dni;

    private String direccion;

    private String nombreUsuario;

    private String contrasenia;

    private String correo;

    private String correoPP;

    private RolUsuario rol;

    private EstadoUsuario estado;

    private String salt;

    // *-- Relaciones --* //
    private List<ReporteDTO> reportesEnviados;

    private List<ReporteDTO> reportesRecibidos;

    private List<NotificacionDTO> notificacionesEnviadas;

    private List<NotificacionDTO> notificacionesRecibidas;

    private List<ValoracionDTO> valoracionesEnviadas;

    private List<ValoracionDTO> valoracionesRecibidas;

    private Set<AnuncioDTO> anunciosGuardados;

    private List<AnuncioDTO> anunciosPublicados;

    private List<VentaDTO> ventas;

    private List<VentaDTO> compras;

    private List<ReunionDTO> reunionesRecibidas;

    private List<ReunionDTO> reunionesOfrecidas;


    // *-- Constructores --* //
    public UsuarioDTO() {}


    // *-- Getters y Setters --* //
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCorreoPP() {
        return correoPP;
    }

    public void setCorreoPP(String correoPP) {
        this.correoPP = correoPP;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<ReporteDTO> getReportesEnviados() {
        return reportesEnviados;
    }

    public void setReportesEnviados(List<ReporteDTO> reportesEnviados) {
        this.reportesEnviados = reportesEnviados;
    }

    public void addReporteEnviado(ReporteDTO reporte) {
        this.reportesEnviados.add(reporte);
    }

    public List<ReporteDTO> getReportesRecibidos() {
        return reportesRecibidos;
    }

    public void setReportesRecibidos(List<ReporteDTO> reportesRecibidos) {
        this.reportesRecibidos = reportesRecibidos;
    }

    public void addReporteRecibido(ReporteDTO reporte) {
        this.reportesRecibidos.add(reporte);
    }

    public List<NotificacionDTO> getNotificacionesEnviadas() {
        return notificacionesEnviadas;
    }

    public void setNotificacionesEnviadas(List<NotificacionDTO> notificacionesEnviadas) {
        this.notificacionesEnviadas = notificacionesEnviadas;
    }

    public void addNotificacionEnviada(NotificacionDTO notificacion) {
        this.notificacionesEnviadas.add(notificacion);
    }

    public List<NotificacionDTO> getNotificacionesRecibidas() {
        return notificacionesRecibidas;
    }

    public void setNotificacionesRecibidas(List<NotificacionDTO> notificacionesRecibidas) {
        this.notificacionesRecibidas = notificacionesRecibidas;
    }

    public void addNotificacionRecibida(NotificacionDTO notificacion) {
        this.notificacionesRecibidas.add(notificacion);
    }

    public List<ValoracionDTO> getValoracionesEnviadas() {
        return valoracionesEnviadas;
    }

    public void setValoracionesEnviadas(List<ValoracionDTO> valoracionesEnviadas) {
        this.valoracionesEnviadas = valoracionesEnviadas;
    }

    public void addValoracionEnviada(ValoracionDTO valoracion) {
        this.valoracionesEnviadas.add(valoracion);
    }

    public List<ValoracionDTO> getValoracionesRecibidas() {
        return valoracionesRecibidas;
    }

    public void setValoracionesRecibidas(List<ValoracionDTO> valoracionesRecibidas) {
        this.valoracionesRecibidas = valoracionesRecibidas;
    }

    public void addValoracionRecibida(ValoracionDTO valoracion) {
        this.valoracionesRecibidas.add(valoracion);
    }

    public Set<AnuncioDTO> getAnunciosGuardados() {
        return anunciosGuardados;
    }

    public void setAnunciosGuardados(Set<AnuncioDTO> anunciosGuardados) {
        this.anunciosGuardados = anunciosGuardados;
    }

    public void addAnuncioGuardado(AnuncioDTO anuncio) {
        this.anunciosGuardados.add(anuncio);
    }

    public List<AnuncioDTO> getAnunciosPublicados() {
        return anunciosPublicados;
    }

    public void setAnunciosPublicados(List<AnuncioDTO> anunciosPublicados) {
        this.anunciosPublicados = anunciosPublicados;
    }

    public void addAnuncioPublicado(AnuncioDTO anuncio) {
        this.anunciosPublicados.add(anuncio);
    }

    public List<VentaDTO> getVentas() {
        return ventas;
    }

    public void setVentas(List<VentaDTO> ventas) {
        this.ventas = ventas;
    }

    public void addVenta(VentaDTO venta) {
        this.ventas.add(venta);
    }

    public List<VentaDTO> getCompras() {
        return compras;
    }

    public void setCompras(List<VentaDTO> compras) {
        this.compras = compras;
    }

    public void addCompra(VentaDTO venta) {
        this.compras.add(venta);
    }

    public List<ReunionDTO> getReunionesRecibidas() {
        return reunionesRecibidas;
    }

    public void setReunionesRecibidas(List<ReunionDTO> reunionesRecibidas) {
        this.reunionesRecibidas = reunionesRecibidas;
    }

    public void addReunionRecibida(ReunionDTO reunion) {
        this.reunionesRecibidas.add(reunion);
    }

    public List<ReunionDTO> getReunionesOfrecidas() {
        return reunionesOfrecidas;
    }

    public void setReunionesOfrecidas(List<ReunionDTO> reunionesOfrecidas) {
        this.reunionesOfrecidas = reunionesOfrecidas;
    }

    public void addReunionOfrecida(ReunionDTO reunion) {
        this.reunionesOfrecidas.add(reunion);
    }

    // *-- Métodos --* //
    public void parse(Usuario usuario) {
        this.idUsuario = usuario.getIdUsuario();
        this.nombre = usuario.getNombre();
        this.apellidos = usuario.getApellidos();
        this.dni = usuario.getDni();
        this.direccion = usuario.getDireccion();
        this.nombreUsuario = usuario.getNombreUsuario();
        this.contrasenia = usuario.getContrasenia();
        this.correo = usuario.getCorreo();
        this.correoPP = usuario.getCorreoPP();
        this.rol = usuario.esModerador() ? RolUsuario.MODERADOR : RolUsuario.USUARIO;
        this.estado = usuario.estaActivo() ? EstadoUsuario.ACTIVO : EstadoUsuario.BANEADO;
        this.salt = usuario.getSalt();

        this.reportesEnviados = null;
        this.reportesRecibidos = null;
        this.notificacionesEnviadas = null;
        this.notificacionesRecibidas = null;
        this.valoracionesEnviadas = null;
        this.valoracionesRecibidas = null;
        this.anunciosGuardados = null;
        this.anunciosPublicados = null;
        this.ventas = null;
        this.compras = null;
        this.reunionesRecibidas = null;
        this.reunionesOfrecidas = null;
    }
}
