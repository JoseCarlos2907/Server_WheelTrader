package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.EstadoUsuario;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.enums.RolUsuario;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario {
    // *-- Atributos --* //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String nombre;

    private String apellidos;

    @Column(unique = true, nullable = false)
    private String dni;

    private String direccion;

    @Column(unique = true, nullable = false)
    private String nombreUsuario;

    private String contrasenia;

    @Column(unique = true, nullable = false)
    private String correo;

    private String correoPP;

    private RolUsuario rol;

    private EstadoUsuario estado;

    @Column(nullable = false)
    private String salt;

    // *-- Relaciones --* //
    @OneToMany(mappedBy = "usuarioEnvia")
    private List<Reporte> reportesEnviados;

    @OneToMany(mappedBy = "usuarioRecibe")
    private List<Reporte> reportesRecibidos;

    @OneToMany(mappedBy = "usuarioEnvia")
    private List<Notificacion> notificacionesEnviadas;

    @OneToMany(mappedBy = "usuarioRecibe")
    private List<Notificacion> notificacionesRecibidas;

    @OneToMany(mappedBy = "usuarioEnvia")
    private List<Valoracion> valoracionesEnviadas;

    @OneToMany(mappedBy = "usuarioRecibe")
    private List<Valoracion> valoracionesRecibidas;

    @ManyToMany
    @JoinTable(
            name = "anuncios_guardados",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "anuncio_id")
    )
    private Set<Anuncio> anunciosGuardados;

    @OneToMany(mappedBy = "vendedor")
    private List<Anuncio> anunciosPublicados;

    @OneToMany(mappedBy = "vendedor")
    private List<Venta> ventas;

    @OneToMany(mappedBy = "comprador")
    private List<Venta> compras;

    @OneToMany(mappedBy = "comprador")
    private List<Reunion> reunionesRecibidas;

    @OneToMany(mappedBy = "vendedor")
    private List<Reunion> reunionesOfrecidas;


    // *-- Constructores --* //
    public Usuario() {}

    public Usuario(String nombre, String apellidos, String dni, String direccion, String nombreUsuario, String contrasenia, String correo, String correoPP, String salt, boolean moderador) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.direccion = direccion;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.correo = correo;
        this.correoPP = correoPP;
        this.rol = moderador? RolUsuario.MODERADOR : RolUsuario.USUARIO;
        this.estado = EstadoUsuario.ACTIVO;
        this.salt = salt;

        this.reportesEnviados = new ArrayList<>();
        this.reportesRecibidos = new ArrayList<>();
        this.notificacionesEnviadas = new ArrayList<>();
        this.notificacionesRecibidas = new ArrayList<>();
        this.valoracionesEnviadas = new ArrayList<>();
        this.valoracionesRecibidas = new ArrayList<>();
    }

    // *-- Getters, Setters --* //
    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long id) {
        this.idUsuario = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuaroio) {
        this.nombreUsuario = nombreUsuaroio;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public boolean esModerador() {
        return rol == RolUsuario.MODERADOR;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public boolean estaActivo() {
        return estado == EstadoUsuario.ACTIVO;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Reporte> getReportesEnviados() {
        return this.reportesEnviados;
    }

    public void setReportesEnviados(List<Reporte> reportesEnviados) {
        this.reportesEnviados = reportesEnviados;
    }

    public void addReporteToReportesEnviados(Reporte reporte) {
        if (!this.reportesEnviados.contains(reporte)) {
            this.reportesEnviados.add(reporte);
        }
        reporte.setUsuarioEnvia(this);
    }

    public List<Reporte> getReportesRecibidos() {
        return this.reportesRecibidos;
    }

    public void setReportesRecibidos(List<Reporte> reportesRecibidos) {
        this.reportesRecibidos = reportesRecibidos;
    }

    public void addReporteToReportesRecibidos(Reporte reporte) {
        if (!this.reportesRecibidos.contains(reporte)) {
            this.reportesRecibidos.add(reporte);
        }
        reporte.setUsuarioRecibe(this);
    }

    public List<Notificacion> getNotificacionesEnviadas() {
        return this.notificacionesEnviadas;
    }

    public void setNotificacionesEnviadas(List<Notificacion> notificacionesEnviadas) {
        this.notificacionesEnviadas = notificacionesEnviadas;
    }

    public void addNotificacionToEnviadas(Notificacion notificacion) {
        if (!this.notificacionesEnviadas.contains(notificacion)) {
            this.notificacionesEnviadas.add(notificacion);
        }
        notificacion.setUsuarioEnvia(this);
    }

    public List<Notificacion> getNotificacionesRecibidas() {
        return this.notificacionesRecibidas;
    }

    public void setNotificacionesRecibidas(List<Notificacion> notificacionesRecibidas) {
        this.notificacionesRecibidas = notificacionesRecibidas;
    }

    public void addNotificacionToRecibidas(Notificacion notificacion) {
        if (!this.notificacionesRecibidas.contains(notificacion)) {
            this.notificacionesRecibidas.add(notificacion);
        }
        notificacion.setUsuarioRecibe(this);
    }

    public List<Valoracion> getValoracionesEnviadas() {
        return this.valoracionesEnviadas;
    }

    public void setValoracionesEnviadas(List<Valoracion> valoracionesEnviadas) {
        this.valoracionesEnviadas = valoracionesEnviadas;
    }

    public void addValoracionToEnviadas(Valoracion valoracion) {
        if (!this.valoracionesEnviadas.contains(valoracion)) {
            this.valoracionesEnviadas.add(valoracion);
        }
        valoracion.setUsuarioEnvia(this);
    }

    public List<Valoracion> getValoracionesRecibidas() {
        return this.valoracionesRecibidas;
    }

    public void setValoracionesRecibidas(List<Valoracion> valoracionesRecibidas) {
        this.valoracionesRecibidas = valoracionesRecibidas;
    }

    public void addValoracionToRecibidas(Valoracion valoracion) {
        if (!this.valoracionesRecibidas.contains(valoracion)) {
            this.valoracionesRecibidas.add(valoracion);
        }
        valoracion.setUsuarioRecibe(this);
    }

    public Set<Anuncio> getAnunciosGuardados() {
        return this.anunciosGuardados;
    }

    public void setAnunciosGuardados(Set<Anuncio> anunciosGuardados) {
        this.anunciosGuardados = anunciosGuardados;
    }

    public void addAnuncioGuardado(Anuncio anuncio) {
        if (!this.anunciosGuardados.contains(anuncio)) {
            this.anunciosGuardados.add(anuncio);
        }
        anuncio.addUsuariosGuardan(this);
    }

    public List<Anuncio> getAnunciosPublicados(){
        return this.anunciosPublicados;
    }

    public void setAnunciosPublicados(List<Anuncio> anunciosPublicados){
        this.anunciosPublicados = anunciosPublicados;
    }

    public void addAnuncioPublicado(Anuncio anuncio) {
        if (!this.anunciosPublicados.contains(anuncio)) {
            this.anunciosPublicados.add(anuncio);
        }
        anuncio.setVendedor(this);
    }

    public List<Venta> getVentas(){
        return this.ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }

    public void addVenta(Venta venta) {
        if (!this.ventas.contains(venta)) {
            this.ventas.add(venta);
        }
        venta.setVendedor(this);
    }

    public List<Venta> getCompras(){
        return this.compras;
    }

    public void setCompras(List<Venta> compras) {
        this.compras = compras;
    }

    public void addCompra(Venta compra) {
        if (!this.compras.contains(compra)) {
            this.compras.add(compra);
        }
        compra.setComprador(this);
    }

    public List<Reunion> getReunionesRecibidas(){
        return this.reunionesRecibidas;
    }

    public void setReunionesRecibidas(List<Reunion> reunionesRecibidas) {
        this.reunionesRecibidas = reunionesRecibidas;
    }

    public void addReunionRecibida(Reunion reunion) {
        if (!this.reunionesRecibidas.contains(reunion)) {
            this.reunionesRecibidas.add(reunion);
        }
        reunion.setVendedor(this);
    }

    public List<Reunion> getReunionesOfrecidas(){
        return this.reunionesOfrecidas;
    }

    public void setReunionesOfrecidas(List<Reunion> reunionesOfrecidas) {
        this.reunionesOfrecidas = reunionesOfrecidas;
    }

    public void addReunionOfrecida(Reunion reunion) {
        if (!this.reunionesOfrecidas.contains(reunion)) {
            this.reunionesOfrecidas.add(reunion);
        }
        reunion.setComprador(this);
    }

    // *-- Métodos --* //
    // Este métoodo transforma un usuario de la interfaz a uno de la BD, la diferencia es que al crear una instancia de este es que se genera el id automáticamente,
    // por eso creo uno nuevo en este métoodo, porque el que viene no puede generarlo y viene con un -1
    public void parseUsuario(Usuario usuario2) {
        this.setNombre(usuario2.getNombre());
        this.setApellidos(usuario2.getApellidos());
        this.setDni(usuario2.getDni());
        this.setDireccion(usuario2.getDireccion());
        this.setNombreUsuario(usuario2.getNombreUsuario());
        this.setContrasenia(usuario2.getContrasenia());
        this.setCorreo(usuario2.getCorreo());
        this.setCorreoPP(usuario2.getCorreoPP());
        this.setRol(usuario2.esModerador() ? RolUsuario.MODERADOR: RolUsuario.USUARIO);
        this.setEstado(usuario2.estaActivo()? EstadoUsuario.ACTIVO: EstadoUsuario.BANEADO);
        this.setSalt(usuario2.getSalt());
    }
}
