package servidor;

import java.security.*;

public class GestorFirma {

    private PublicKey clavePublica;
    private PrivateKey clavePrivada;

    public GestorFirma() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); // Usamos el algoritmo RSA.
        keyGen.initialize(2048); // La clave tendrá un tamaño de 2048 bits.
        KeyPair keyPair = keyGen.generateKeyPair(); // Generador de las claves privada y pública.
        clavePublica = keyPair.getPublic();
        clavePrivada = keyPair.getPrivate();

    }

    public PublicKey getClavePublica() {
        return clavePublica;
    }

    public void setClavePublica(PublicKey clavePublica) {
        this.clavePublica = clavePublica;
    }

    public PrivateKey getClavePrivada() {
        return clavePrivada;
    }

    public void setClavePrivada(PrivateKey clavePrivada) {
        this.clavePrivada = clavePrivada;
    }

    public byte[] getFirma(String mensaje) throws Exception {
        Signature firma = Signature.getInstance("SHA256withRSA");
        firma.initSign(clavePrivada);
        firma.update(mensaje.getBytes());
        return firma.sign(); // Genera la firma
    }
}
