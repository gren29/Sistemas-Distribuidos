import java.rmi.Naming;

public class ServidorMulultiplicacion{
  public static void main(String[] args) throws Exception{
    String url = "rmi://localhost/prueba";
    ClaseMultiplicacion obj = new ClaseMultiplicacion();

    // registra la instancia en el rmiregistry
    Naming.rebind(url,obj);
  }
}