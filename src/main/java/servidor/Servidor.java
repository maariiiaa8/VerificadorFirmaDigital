package servidor;

import util.Terminal;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;

public class Servidor {
    public static void main(String[] args) throws Exception {
        int PUERTO = 5000;

        Terminal.cabecera("SERVER " + PUERTO);
        Terminal.cmd("INICIALIZANDO PROTOCOLO DE COMUNICACIÓN SEGURA...");
        Terminal.cmd("CARGANDO MÓDULOS DE CIFRADO....................OK");
        System.out.println();

        ServerSocket servidor = new ServerSocket(PUERTO);
        Terminal.so("ESPERANDO CONEXIÓN EN EL PUERTO " +  PUERTO +"................\nTERMINAL LISTA");

        Socket cliente = servidor.accept();
        Terminal.log("CLIENTE CONECTADO " + cliente.getInetAddress());

        GestorFirma firma = new GestorFirma();
        Terminal.log("GENERANDO PAR DE CLAVES RSA..................OK");
        Terminal.log("CLAVE PÚBLICA TRANSMITIDA....................OK");

        PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);
        String clavePublica = Base64.getEncoder().encodeToString(firma.getClavePublica().getEncoded());
        out.println(clavePublica);
        out.flush();


        // Pedimos el mensaje que se quiera enviar firmado
        Scanner scanner = new Scanner(System.in);
        Terminal.separador();
        Terminal.cmd("INTRODUCE EL MENSAJE A FIRMAR: ");
        String mensaje = scanner.nextLine();

        // El mensaje se firma
        byte[] firmaBytes = firma.getFirma(mensaje);
        String firma64 = Base64.getEncoder().encodeToString(firmaBytes);
        Terminal.log("MENSAJE FIRMADO..............................OK");
        Terminal.log("MENSAJE TRANSMITIDO..........................OK");
        Terminal.log("FIRMA TRANSMITIDA............................OK");


        out.println(mensaje);
        out.println(firma64);

        Terminal.separador();
        Terminal.cmd("CERRANDO CONEXIÓN...............................OK");

        scanner.close();
        cliente.close();
        servidor.close();
    }
}
