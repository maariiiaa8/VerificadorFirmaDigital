package cliente;

import util.Terminal;

import java.io.*;
import java.net.Socket;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class Cliente extends Terminal {
    public static void main(String[] args) throws Exception {
        String HOST = "localhost";
        int PUERTO = 5000;

        Terminal.cabecera("CLIENTE");
        Terminal.cmd("INICIALIZANDO PROTOCOLO DE COMUNICACIÓN SEGURA....");
        Terminal.cmd("ESTABLECIENDO CONEXIÓN CON " + HOST + ":" + PUERTO + "........");

        Socket socket = new Socket(HOST, PUERTO);
        Terminal.cmd("CONEXIÓN ESTABLECIDA............................OK");
        Terminal.separador();

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Los clientes se identifican usando su nombre.
        Scanner scanner = new Scanner(System.in);
        Terminal.cmd("INTRODUCE TU NOMBRE: ");
        String nombre = scanner.nextLine();
        out.println(nombre);
        Terminal.log("IDENTIFICADO COMO [" + nombre + "]");
        Terminal.log("ESPERANDO MENSAJE DEL SERVIDOR...");
        Terminal.separador();

        // Reciben la clave pública.
        String clavePublicaStr = in.readLine();
        byte[] clavePublicaBytes = Base64.getDecoder().decode(clavePublicaStr);
        PublicKey clavePublica = KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(clavePublicaBytes));
        Terminal.log("CLAVE PÚBLICA RECIBIDA.......................OK");

        // Reciben el mensaje y firma.
        String mensaje = in.readLine();
        Terminal.log("MENSAJE RECIBIDO: \"" + mensaje + "\"");

        String firmaStr = in.readLine();
        byte[] firmaBytes = Base64.getDecoder().decode(firmaStr);
        Terminal.log("FIRMA RECIBIDA...............................OK");

        // Verifican si la firma es válida o no.
        Terminal.cmd("VERIFICANDO FIRMA DIGITAL.........................");
        boolean valida = VerificadorFirma.verificarFirma(mensaje, firmaBytes, clavePublica);
        Terminal.separador();

        if (valida) {
            Terminal.log("*** FIRMA VÁLIDA ***");
        } else {
            Terminal.log("*** FIRMA INVÁLIDA ***");
        }
        Terminal.log("MENSAJE: \"" + mensaje + "\"");
        Terminal.separador();

        Terminal.cmd("CERRANDO CONEXIÓN...............................OK");
        socket.close();
    }
}