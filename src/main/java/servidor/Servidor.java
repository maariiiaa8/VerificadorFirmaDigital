package servidor;

import util.Terminal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Servidor extends Terminal {

    private static final BlockingQueue<GestorClientes> clientes = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws Exception {
        int PUERTO = 5000;

        Terminal.cabecera("SERVER " + PUERTO);
        Terminal.cmd("INICIALIZANDO PROTOCOLO DE COMUNICACIÓN SEGURA...");
        Terminal.cmd("CARGANDO MÓDULOS DE CIFRADO....................OK");

        ServerSocket servidor = new ServerSocket(PUERTO);
        Terminal.so("ESPERANDO CONEXIÓN EN EL PUERTO " + PUERTO + "................\nTERMINAL LISTA");
        Scanner scanner = new Scanner(System.in);

        Thread hiloDeClientesThread = new Thread(() -> {
            while (true) {
                try {
                    GestorClientes cliente = clientes.take();
                    Terminal.log("CLIENTE EN COLA: [" + cliente.getNombre() + "] - INTRODUCE MENSAJE:");

                    String mensaje = scanner.nextLine();

                    despacharMensaje(cliente, mensaje);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        hiloDeClientesThread.setDaemon(true);
        hiloDeClientesThread.start();

        while (true) {
            Socket socket = servidor.accept();
            new Thread(() -> {
                try {
                    identificarCliente(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static void identificarCliente(Socket socket) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String nombre = in.readLine();
        Terminal.log("CLIENTE IDENTIFICADO: [" + nombre + "] desde " + socket.getInetAddress());
        Terminal.log("CLIENTE [" + nombre + "] AÑADIDO A LA COLA - ESPERANDO MENSAJE...");

        clientes.put(new GestorClientes(nombre, socket, out));
    }

    private static void despacharMensaje(GestorClientes cliente, String mensaje) throws Exception {
        GestorFirma gestor = new GestorFirma();
        PrintWriter out = cliente.getOut();

        String clavePublica = Base64.getEncoder().encodeToString(gestor.getClavePublica().getEncoded());
        out.println(clavePublica);
        Terminal.log("CLAVE PÚBLICA ENVIADA A [" + cliente.getNombre() + "]......OK");

        byte[] firmaBytes = gestor.getFirma(mensaje);
        String firma64 = Base64.getEncoder().encodeToString(firmaBytes);
        out.println(mensaje);
        out.println(firma64);
        Terminal.log("MENSAJE Y FIRMA ENVIADOS A [" + cliente.getNombre() + "]....OK");

        cliente.getSocket().close();
        Terminal.log("CONEXIÓN CERRADA CON [" + cliente.getNombre() + "]");
    }
}