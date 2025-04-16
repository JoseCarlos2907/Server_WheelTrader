package es.iesfernandoaguilar.perezgonzalez.wheeltrader.DTO;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.EstadoAnuncio;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class AnuncioDTO {
    // *-- Atributos --* //
    private Long idAnuncio;

    private LocalDateTime fechaPublicacion;

    private LocalDateTime fechaExpiracion;

    private String descripcion;

    private double precio;

    private EstadoAnuncio estado;

    private String provincia;

    private String ciudad;

    private String matricula;

    private String numSerieBastidor;

    // *-- Relaciones --* //
    private Usuario vendedor;

    private Set<Usuario> usuariosGuardan;

    private TipoVehiculo tipoVehiculo;

    private List<Imagen> imagenes;

    private Venta venta;

    private List<Reunion> reuniones;

    private List<ValorCaracteristica> valoresCaracteristicas;


    // *-- Constructores --* //
    public AnuncioDTO() {}


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

    public EstadoAnuncio getEstado() {
        return estado;
    }

    public void setEstado(EstadoAnuncio estado) {
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

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    public Set<Usuario> getUsuariosGuardan() {
        return usuariosGuardan;
    }

    public void setUsuariosGuardan(Set<Usuario> usuariosGuardan) {
        this.usuariosGuardan = usuariosGuardan;
    }

    public void addUsuarioGuarda(Usuario usuario) {
        this.usuariosGuardan.add(usuario);
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public void addImagen(Imagen imagen) {
        this.imagenes.add(imagen);
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public List<Reunion> getReuniones() {
        return reuniones;
    }

    public void setReuniones(List<Reunion> reuniones) {
        this.reuniones = reuniones;
    }

    public void addReunion(Reunion reunion) {
        this.reuniones.add(reunion);
    }

    public List<ValorCaracteristica> getValoresCaracteristicas() {
        return valoresCaracteristicas;
    }

    public void setValoresCaracteristicas(List<ValorCaracteristica> valoresCaracteristicas) {
        this.valoresCaracteristicas = valoresCaracteristicas;
    }

    public void addValorCaracteristica(ValorCaracteristica valorCaracteristica) {
        this.valoresCaracteristicas.add(valorCaracteristica);
    }

    // *-- Métodos --* //
    public void parse(Anuncio anuncio) {
        this.idAnuncio = anuncio.getIdAnuncio();
        this.fechaPublicacion = anuncio.getFechaPublicacion();
        this.fechaExpiracion = anuncio.getFechaExpiracion();
        this.descripcion = anuncio.getDescripcion();
        this.precio = anuncio.getPrecio();
        this.estado = anuncio.getEstado();
        this.provincia = anuncio.getProvincia();
        this.ciudad = anuncio.getCiudad();

        this.vendedor = null;
        this.usuariosGuardan = null;
        this.tipoVehiculo = null;
        this.imagenes = null;
        this.venta = null;
        this.reuniones = null;
        this.valoresCaracteristicas = null;
    }
}
