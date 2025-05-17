package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.EstadoAnuncio;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "anuncios")
public class Anuncio {
    // *-- Atributos --* //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnuncio;

    private LocalDateTime fechaPublicacion;

    private LocalDateTime fechaExpiracion;

    @Lob
    private String descripcion;

    private double precio;

    private EstadoAnuncio estado;

    private String provincia;

    private String ciudad;

    private String matricula;

    private String numSerieBastidor;

    // *-- Relaciones --* //
    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor;

    @ManyToMany(mappedBy = "anunciosGuardados")
    private Set<Usuario> usuariosGuardan;

    @ManyToOne
    @JoinColumn(name = "tipovehiculo_id")
    private TipoVehiculo tipoVehiculo;

    @OneToMany(mappedBy = "anuncio")
    private List<Imagen> imagenes;

    @OneToOne(mappedBy = "anuncio")
    private Venta venta;

    @OneToMany(mappedBy = "anuncio")
    private List<Reunion> reuniones;

    @OneToMany(mappedBy = "anuncio")
    private List<ValorCaracteristica> valoresCaracteristicas;

    @OneToMany(mappedBy = "anuncio")
    private List<Notificacion> notificaciones;

    // *-- Constructores --* //
    public Anuncio() {}

    public Anuncio(String descripcion, double precio, String provincia, String ciudad, String matricula, String numSerieBastidor) {
        this.fechaPublicacion = LocalDateTime.now();
        this.fechaExpiracion = fechaPublicacion.plusMonths(3);
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = EstadoAnuncio.EN_VENTA;
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.matricula = matricula;
        this.numSerieBastidor = numSerieBastidor;

        this.usuariosGuardan = new HashSet<>();
        this.imagenes = new ArrayList<>();
        this.reuniones = new ArrayList<>();
        this.valoresCaracteristicas = new ArrayList<>();
        this.notificaciones = new ArrayList<>();
    }
    // *-- Getters, Setters --* //

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

    public void addUsuariosGuardan(Usuario usuario) {
        if(!this.usuariosGuardan.contains(usuario)) {
            this.usuariosGuardan.add(usuario);
        }
    }

    public void eliminarUsuarioGuarda(Usuario usuario) {
        this.usuariosGuardan.remove(usuario);
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
        tipoVehiculo.addAnuncio(this);
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public void addImagen(Imagen imagen) {
        if(!this.imagenes.contains(imagen)) {
            this.imagenes.add(imagen);
        }
        imagen.setAnuncio(this);
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
        if(!this.reuniones.contains(reunion)) {
            this.reuniones.add(reunion);
        }
        reunion.setAnuncio(this);
    }

    public List<ValorCaracteristica> getValoresCaracteristicas() {
        return valoresCaracteristicas;
    }

    public void setValoresCaracteristicas(List<ValorCaracteristica> valoresCaracteristicas) {
        this.valoresCaracteristicas = valoresCaracteristicas;
    }

    public void addValoresCaracteristica(ValorCaracteristica valorCaracteristica) {
        if(!this.valoresCaracteristicas.contains(valorCaracteristica)) {
            this.valoresCaracteristicas.add(valorCaracteristica);
        }
        valorCaracteristica.setAnuncio(this);
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public void addNotificacion(Notificacion notificacion) {
        if(!this.notificaciones.contains(notificacion)) {
            this.notificaciones.add(notificacion);
        }
        notificacion.setAnuncio(this);
    }

    // *-- Métodos --* //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Anuncio anuncio = (Anuncio) o;
        return idAnuncio != null && idAnuncio.equals(anuncio.getIdAnuncio());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
