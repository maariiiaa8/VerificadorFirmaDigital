package cliente;

import java.security.PublicKey;
import java.security.Signature;

public class VerificadorFirma {
    public static boolean verificarFirma(String mensaje, byte[] firma, PublicKey clavePublica) throws Exception {
        Signature firmaSignature = Signature.getInstance("SHA256withRSA");
        firmaSignature.initVerify(clavePublica);
        firmaSignature.update(mensaje.getBytes());
        return firmaSignature.verify(firma);
    }
}
