package servidor;

import java.io.PrintWriter;
import java.net.Socket;

public class GestorClientes {
    private final String nombre;
    private final Socket socket;
    private final PrintWriter out;

    public GestorClientes(String nombre, Socket socket, PrintWriter out) {
        this.nombre = nombre;
        this.socket = socket;
        this.out = out;
    }

    public String getNombre() {
        return nombre;
    }

    public Socket getSocket() {
        return socket;
    }

    public PrintWriter getOut() {
        return out;
    }
}