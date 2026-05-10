package util;

public class Terminal {

    public static synchronized void print(String txt) throws InterruptedException {
        for (char ch : txt.toCharArray()) {
            System.out.print(ch);
            Thread.sleep(18);
        }
        System.out.println();
    }
    public static void separador() throws InterruptedException {
        print("====================================================");
    }
    public static void log (String txt) throws InterruptedException {
        print("| >> " + txt);
    }
    public static void cmd(String txt) throws InterruptedException {
        print("> " + txt);
    }
    public static void cabecera(String nombreServer) throws InterruptedException {
        separador();
        print("   -SISTEMA OPERATIVO UNIFICADO INDUSTRIAS ROBCO-  ");
        print("        COPYRIGHT 2075-2077 INDUSTRIAS ROBCO       ");
        print("                   -" + nombreServer + "-             ");
        separador();
    }
    public static void so(String txt) throws InterruptedException {
        print("SO RobCo v.85");
        print("(C) 2076 RobCo");
        separador();
        print(txt);
        separador();
    }
}
