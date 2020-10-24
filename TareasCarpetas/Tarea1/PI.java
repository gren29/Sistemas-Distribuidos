import java.net.Socket;
import java.net.ServerSocket;
import java.nio.ByteBuffer; 
import java.lang.Thread;
import java.io.DataOutputStream; 
import java.io.DataInputStream;

class PI {
  static Object lock = new Object(); 
  static double pi = 0;
  static class Worker extends Thread{
  Socket conexion; 
  Worker(Socket conexion) {
    this.conexion = conexion; 
  }
  public void run(){
    try{
      DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
      DataInputStream  entrada = new DataInputStream(conexion.getInputStream());
      double x = entrada.readDouble();
      synchronized(lock){ 
        pi += x;
      }

      salida.close(); 
      entrada.close(); 
      conexion.close();

    }catch(Exception e){ 
      System.err.println(e.getMessage());
    } 
  }
}

public static void main(String[] args) throws Exception{
  if (args.length != 1){
    System.err.println("Uso:"); 
    System.err.println("java PI <nodo>"); 
    System.exit(0);
  }
  int nodo = Integer.valueOf(args[0]); 
  if (nodo == 0){
    System.out.println("Server ...");
    ServerSocket servidor = new ServerSocket(50000);
    Worker w[] = new Worker[3];
    int i = 0;
    while(i < 3){
        Socket conexion = servidor.accept();
        w[i]= new Worker(conexion); 
        w[i].start();
        i++;
        System.out.println("[Server] Node : "+ i);
      }
    double suma = 0;
    i = 0;
    while(i <10000000){
      suma += 4.0/(8*i+1);
      i++; 
    }
    synchronized(lock){ 
      pi += suma;
    }
    i = 0;
    while(i<3){
      w[i].join(); 
      i++;
    }
    System.out.println("PI : "+pi);
  }
  else{
    Socket conexion = null; 
    for(;;)
    try{
      conexion = new Socket("localhost",50000);
      break; 
    }
    catch (Exception e){ Thread.sleep(100);
    }
    DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
    DataInputStream entrada = new DataInputStream(conexion.getInputStream());
    double suma = 0;
    int i = 0;
    while(i < 10000000){
      suma += 4.0/(8*i+(2*(nodo-1)+3));
      i++; 
    }
      suma = (nodo%2 == 0) ? suma : (-suma); 
      salida.writeDouble(suma); 
      salida.close();
      entrada.close();
      conexion.close();
    }
  }
}