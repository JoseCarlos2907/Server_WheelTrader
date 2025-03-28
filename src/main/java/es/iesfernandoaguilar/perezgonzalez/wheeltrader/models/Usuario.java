package es.iesfernandoaguilar.perezgonzalez.wheeltrader.models;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    private String rol;

    private String estado;

    @Column(unique = true, nullable = false)
    private String salt;

    public Usuario() {}

    public Usuario(String nombre, String apellidos, String dni, String nombreUsuario, String contrasenia, String correo, String correoPP, String estado, String salt, boolean moderador) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.correo = correo;
        this.correoPP = correoPP;
        this.rol = moderador? "MODERADOR" : "USUARIO";
        this.estado = estado;
        this.salt = salt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

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
        this.setRol(usuario2.getRol());
        this.setEstado(usuario2.getEstado());
        this.setSalt(usuario2.getSalt());
    }
}
