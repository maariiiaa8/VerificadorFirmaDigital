package cliente;

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

        Socket socket = new Socket(HOST, PUERTO);
        System.out.println("Conectando...");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String clavePublica = in.readLine();
        byte[] clavePublicaBytes = Base64.getDecoder().decode(clavePublica);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey clavepublica = keyFactory.generatePublic(new X509EncodedKeySpec(clavePublicaBytes));
        System.out.println("Clave publica recibida.");

        String mensaje = in.readLine();
        System.out.println("Mensaje : " + mensaje + " recibida.");

        String firma = in.readLine();
        byte[] firmaBytes = Base64.getDecoder().decode(firma);
        System.out.println("Firma: " + firma + " recibida.");

        boolean firmaValida = VerificadorFirma.verificarFirma(mensaje, firmaBytes, clavepublica);

        System.out.println("=== RESULTADO ===");
        System.out.println("Mensaje: " + mensaje);
        System.out.println("Firma: " + firma + " recibida.");
        if(firmaValida){
            System.out.println("Firma valida.");
        } else{
            System.out.println("Firma invalida.");
        }

        socket.close();
        System.out.println("Conexión cerrada.");



    }

}
