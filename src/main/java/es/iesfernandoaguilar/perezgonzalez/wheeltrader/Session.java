package es.iesfernandoaguilar.perezgonzalez.wheeltrader;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.models.Usuario;

// Esto va en la interfaz pero sirve para probar la rama
public class Session {
    private Usuario usuario;

    public Session() {
        this.usuario = null;
    }

    public void iniciarSession(Usuario usuario) {
        this.usuario = usuario;
    }

    public void cerrarSession() {
        this.usuario = null;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean haIniciadoSesion() {
        return this.usuario != null;
    }
}
