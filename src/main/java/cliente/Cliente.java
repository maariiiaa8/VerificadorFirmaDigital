package cliente;

import util.Terminal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Cliente {
    public static void main(String[] args) throws Exception{
        String HOST = "localhost";
        int PUERTO = 5000;

        Terminal.cabecera("CLIENTE 76");
        Terminal.cmd("INICIALIZANDO PROTOCOLO DE COMUNICACIÓN SEGURA....");
        Terminal.cmd("ESTABLECIENDO CONEXIÓN CON " + HOST + ": " + PUERTO + "........");

        Socket socket = new Socket(HOST, PUERTO);
        Terminal.cmd("CONEXIÓN ESTABLECIDA............................OK");
        Terminal.separador();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String clavePublica = in.readLine();
        byte[] clavePublicaBytes = Base64.getDecoder().decode(clavePublica);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey clavepublica = keyFactory.generatePublic(new X509EncodedKeySpec(clavePublicaBytes));
        Terminal.log("CLAVE PÚBLICA RECIBIDA.......................OK");

        String mensaje = in.readLine();
        Terminal.log("MENSAJE RECIBIDO: \"" + mensaje + "\"");


        String firma = in.readLine();
        byte[] firmaBytes = Base64.getDecoder().decode(firma);
        Terminal.log("FIRMA RECIBIDA...............................OK");

        Terminal.cmd("VERIFICANDO FIRMA DIGITAL.........................");
        boolean firmaValida = VerificadorFirma.verificarFirma(mensaje, firmaBytes, clavepublica);

        Terminal.separador();

        if(firmaValida){
            Terminal.log("*** FIRMA VÁLIDA ***");
        } else  {
            Terminal.log("*** FIRMA INVÁLIDA ***");
        }

        Terminal.log("MENSAJE : \"" + mensaje + "\"");
        Terminal.separador();

        Terminal.cmd("CERRANDO CONEXIÓN...............................OK");
        socket.close();
    }

}
