package es.iesfernandoaguilar.perezgonzalez.wheeltrader.handlers;

import es.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Mensaje;
import es.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Serializador;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
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

            Mensaje msg = new Mensaje();
            msg.setTipo("BIENVENIDO");
            this.dos.writeUTF(Serializador.codificarMensaje(msg));

            while (true) {
                String linea = this.dis.readUTF();
                Mensaje msgUsuario = Serializador.decodificarMensaje(linea);

                switch (msgUsuario.getTipo()){
                    case "":
                        break;
                }
            }

        } catch (EOFException e) {
            System.out.println("Se cerró el flujo con el puerto " + cliente.getPort());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
