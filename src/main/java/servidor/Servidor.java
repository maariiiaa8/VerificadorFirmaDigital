package servidor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;

public class Servidor {
    public static void main(String[] args) throws Exception {
        int PUERTO = 5000;
        ServerSocket servidor = new ServerSocket(PUERTO);
        System.out.println("Esperando al cliente...");

        Socket cliente = servidor.accept();
        System.out.println("¡Cliente " + cliente.getInetAddress() + " conectado!");


        GestorFirma firma = new GestorFirma();
        System.out.println("Se están generando las claves pública y privada...");
        System.out.println("Espere...");
        System.out.println("¡Claves generadas con éxito!");

        PrintWriter out = new PrintWriter(cliente.getOutputStream(), true);

        String clavePublica = Base64.getEncoder().encodeToString(firma.getClavePublica().getEncoded());
        out.println(clavePublica);
        System.out.println("¡Clave pública enviada al cliente!.");
        out.flush();


        // Pedimos el mensaje que se quiera enviar firmado
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduzca el mensaje a firmar:  ");
        String mensaje = scanner.nextLine();

        // El mensaje se firma
        byte[] firmaBytes = firma.getFirma(mensaje);
        String firma64 = Base64.getEncoder().encodeToString(firmaBytes);
        System.out.println("Se ha firmado el mensaje.");

        out.println(mensaje);
        System.out.println("Mensaje enviado al cliente.");

        out.println(firma64);
        System.out.println("Firma enviada al cliente.");

        scanner.close();
        cliente.close();
        servidor.close();
        System.out.println("Conexión cerrada.");

    }
}
