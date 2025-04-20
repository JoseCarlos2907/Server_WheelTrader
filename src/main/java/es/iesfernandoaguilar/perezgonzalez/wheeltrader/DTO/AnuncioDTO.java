package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.EstadoAnuncio;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnuncioDTO {
    // *-- Atributos --* //
    private Long idAnuncio;

    private LocalDateTime fechaPublicacion;

    private LocalDateTime fechaExpiracion;

    private String descripcion;

    private double precio;

    private String estado;

    private String provincia;

    private String ciudad;

    private String matricula;

    private String numSerieBastidor;

    // *-- Relaciones --* //
    private UsuarioDTO vendedor;

    private Set<UsuarioDTO> usuariosGuardan;

    private String tipoVehiculo;

    private List<ImagenDTO> imagenes;

    private VentaDTO venta;

    private List<ReunionDTO> reuniones;

    private List<ValorCaracteristicaDTO> valoresCaracteristicas;


    // *-- Constructores --* //
    public AnuncioDTO() {}

    public AnuncioDTO(LocalDateTime fechaPublicacion, String descripcion, double precio, String provincia, String ciudad, String matricula, String numSerieBastidor) {
        this.fechaPublicacion = fechaPublicacion;
        this.fechaExpiracion = fechaPublicacion.plusMonths(3);
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = "EN_VENTA";
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.matricula = matricula;
        this.numSerieBastidor = numSerieBastidor;

        this.usuariosGuardan = new HashSet<>();
        this.imagenes = new ArrayList<>();
        this.reuniones = new ArrayList<>();
        this.valoresCaracteristicas = new ArrayList<>();
    }

    // *-- Getters y Setters --* //
    public Long getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(Long idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNumSerieBastidor() {
        return numSerieBastidor;
    }

    public void setNumSerieBastidor(String numSerieBastidor) {
        this.numSerieBastidor = numSerieBastidor;
    }

    public UsuarioDTO getVendedor() {
        return vendedor;
    }

    public void setVendedor(UsuarioDTO vendedor) {
        this.vendedor = vendedor;
    }

    public Set<UsuarioDTO> getUsuariosGuardan() {
        return usuariosGuardan;
    }

    public void setUsuariosGuardan(Set<UsuarioDTO> usuariosGuardan) {
        this.usuariosGuardan = usuariosGuardan;
    }

    public void addUsuarioGuarda(UsuarioDTO usuario) {
        this.usuariosGuardan.add(usuario);
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public List<ImagenDTO> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenDTO> imagenes) {
        this.imagenes = imagenes;
    }

    public void addImagen(ImagenDTO imagen) {
        this.imagenes.add(imagen);
    }

    public VentaDTO getVenta() {
        return venta;
    }

    public void setVenta(VentaDTO venta) {
        this.venta = venta;
    }

    public List<ReunionDTO> getReuniones() {
        return reuniones;
    }

    public void setReuniones(List<ReunionDTO> reuniones) {
        this.reuniones = reuniones;
    }

    public void addReunion(ReunionDTO reunion) {
        this.reuniones.add(reunion);
    }

    public List<ValorCaracteristicaDTO> getValoresCaracteristicas() {
        return valoresCaracteristicas;
    }

    public void setValoresCaracteristicas(List<ValorCaracteristicaDTO> valoresCaracteristicas) {
        this.valoresCaracteristicas = valoresCaracteristicas;
    }

    public void addValorCaracteristica(ValorCaracteristicaDTO valorCaracteristica) {
        this.valoresCaracteristicas.add(valorCaracteristica);
    }

    // *-- Métodos --* //
    public void parse(Anuncio anuncio) {
        this.idAnuncio = anuncio.getIdAnuncio();
        this.fechaPublicacion = null;
        this.fechaExpiracion = null;
        this.descripcion = anuncio.getDescripcion();
        this.precio = anuncio.getPrecio();
        this.estado = anuncio.getEstado().toString();
        this.provincia = anuncio.getProvincia();
        this.ciudad = anuncio.getCiudad();

        this.vendedor = null;
        this.usuariosGuardan = new HashSet<>();
        this.tipoVehiculo = null;
        this.imagenes = new ArrayList<>();
        this.venta = null;
        this.reuniones = new ArrayList<>();
        this.valoresCaracteristicas = new ArrayList<>();
    }
}
