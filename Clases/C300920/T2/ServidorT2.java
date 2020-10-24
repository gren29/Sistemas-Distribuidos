import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.nio.ByteBuffer;
class Servidor{
    //lee del DataInputStream todos los bytes requeridos

        static void read(DataInputStream f,byte[] b,int posicion,int longitud) throws Exception
        {
            while (longitud > 0)
            {
                int n = f.read(b,posicion,longitud);
                posicion += n;
                longitud -= n;
            }
        }

    public static void main(String[] args)throws Exception{
        ServerSocket servidor = new ServerSocket(50001);

        Socket conexion = servidor.accept();
        
        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());

        //recibe 5 numeros punto floatante
        long start = System.currentTimeMillis();
        byte[] a = new byte[1000000*8];
        read(entrada,a,0,1000000*8);
        ByteBuffer b = ByteBuffer.wrap(a);
        for(int i = 0; i < 1000000;i++){
            System.out.println(b.getDouble());
        }
        long end = System.currentTimeMillis();
        System.out.println("Tiempo : " + ((end - start) ));

        salida.close();
        entrada.close();
        conexion.close();

    }
}