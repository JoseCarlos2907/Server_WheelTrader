package es.iesfernandoaguilar.perezgonzalez.wheeltrader.handlers;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Mensaje;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Serializador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class UsuarioHandler implements Runnable {

    private Socket cliente;

    private DataOutputStream dos;
    private DataInputStream dis;

    public UsuarioHandler(Socket cliente) {
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try {
            this.dis = new DataInputStream(cliente.getInputStream());
            this.dos = new DataOutputStream(cliente.getOutputStream());

            while (true) {
                String linea = this.dis.readUTF();
                Mensaje msgUsuario = Serializador.decodificarMensaje(linea);

                switch (msgUsuario.getTipo()){
                    case "":
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
