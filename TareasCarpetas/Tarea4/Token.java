import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;


class Token{
  static DataInputStream entrada;
  static DataOutputStream salida;
  static boolean primera_vez = true;
  static String ip;
  static long token = 0;
  static int nodo;

  static class Worker extends Thread{
    public void run()
    {
        try {
            System.out.println("[NODE] "+ nodo + "watting token");
            ServerSocket servidor = new ServerSocket(50000);
            Socket conexion = servidor.accept();
            entrada = new DataInputStream(conexion.getInputStream());
            System.out.println("[NODE] connected");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
  }

    public static void main(String[] args) throws Exception{
        if (args.length != 2){
        System.err.println("Se debe pasar como parametros el numero de nodo y la IP del siguiente nodo");
        System.exit(1);
        }

        nodo = Integer.valueOf(args[0]);  // el primer parametro es el numero de nodo
        ip = args[1];  // el segundo parametro es la IP del siguiente nodo en el anillo

        Worker w = new Worker();
        w.start();
        Socket conexion;
        System.out.println("[NODE] "+nodo + " connecting to "+ ip + " at [50000]");
        for(;;){
            try {
                conexion = new Socket(ip,50000);
                break;
            } catch (Exception e) {
                Thread.sleep(500);
            }
        }
        System.out.println("[NODE] "+nodo + " connected to "+ ip + " at [50000]");
        salida = new DataOutputStream(conexion.getOutputStream());
        w.join();


        while (true){
            if (nodo == 0 && primera_vez){
                primera_vez = false;
            }
            else {
                if (primera_vez){
                    primera_vez = false;
                }
                token = entrada.readLong();
            }
            token++;
            System.out.println("Nodo : "+nodo+"  Token: "+token);
            salida.writeLong(token);
            salida.flush();
        }
    }
}