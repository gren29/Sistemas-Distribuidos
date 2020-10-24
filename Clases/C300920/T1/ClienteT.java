import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.nio.ByteBuffer;

class Cliente{
    // lee del DataInputStream todos los bytes requeridos
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
        Socket conexion = new Socket("localhost",50001);
        DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
        DataInputStream entrada = new DataInputStream(conexion.getInputStream());


        //envia un numero punto flotante
        long start = System.currentTimeMillis();
        double num = 1.0;
        for(int i = 0; i < 1000;i++,num++)
            salida.writeDouble(num);

        long end = System.currentTimeMillis();
        System.out.println("Tiempo : " + ((end - start) ));
        salida.close();
        entrada.close();
        conexion.close();
    }
}