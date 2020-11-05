import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;

public class ClienteMulticast
{
    //recibes mensaje -> socket tipo MulticastSocket, longitud del mensaje (numero de bytes)
  static byte[] recibe_mensaje(MulticastSocket socket,int longitud) throws IOException
  {
    byte[] buffer = new byte[longitud];
    DatagramPacket paquete = new DatagramPacket(buffer,buffer.length);
    socket.receive(paquete);
    return buffer;
  }

  public static void main(String[] args) throws Exception
  {
    System.setProperty("java.net.preferIPv4Stack", "true");
    InetAddress grupo = InetAddress.getByName("230.0.0.0");//Obtenemos el grupo
    MulticastSocket socket = new MulticastSocket(50000);//Obtenemos un cocket asocaido al puerto N
    socket.joinGroup(grupo);//Para que el cleinte pueda recibir los mensajes enviados al grupo X unimos el sovket al grupo X

    System.out.println("Esperando datagrama...");

    /* recibe una string */
    byte[] a = recibe_mensaje(socket,4);
    System.out.println(new String(a,"UTF-8"));

    /* recibe 5 doubles */
    byte[] buffer = recibe_mensaje(socket,5*8);
    ByteBuffer b = ByteBuffer.wrap(buffer);

    for (int i = 0; i < 5; i++)
      System.out.println(b.getDouble());

    socket.leaveGroup(grupo);// el socket abando el grupo
    socket.close();
  }
}