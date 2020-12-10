import java.io.BufferedReader;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import com.google.gson.reflect.TypeToken;

public class Cliente{
    static String dirIP;
    public static void main(String[] args)throws Exception{
        
        Scanner entradaEscaner = new Scanner (System.in); 
        char opcion, repetir='s';
        System.out.print("Ingresa la IP: ");
        dirIP = entradaEscaner.nextLine (); 
        System.out.println("DIreccion IP : "+dirIP);
        System.out.println("\n");

        while(true){

            System.out.println("a. Alta usuario");
            System.out.println("b. Consulta usuario");
            System.out.println("c. Borra usuario");
            System.out.println("d. Borra todos los usuarios");
            System.out.println("e. Salir\n\n");
            System.out.print("Opcion: ");
            opcion = entradaEscaner.next().charAt(0); 

            if (opcion =='a') {
                altaUsuario();
            } else if (opcion == 'b') {
                consultaUsuario(); 
            } else if (opcion == 'c') {
                borrarUsuario(); 
            } else if (opcion == 'd') {
                borrarUsuarios();
            } else if (opcion == 'e') {
                System.exit(0);
            } else {
                System.out.println("opncion invalida");
            }
        }
    }

    public static void altaUsuario() throws Exception{
        Scanner input = new Scanner (System.in);
        System.out.println("ALTA USUARIO");
        System.out.println("Email : ");
        String email = input.nextLine();
        System.out.println("Nombre : ");
        String nombre = input.nextLine();
        System.out.println("Apellido paterno : ");
        String apellidoPaterno = input.nextLine();
        System.out.println("Apellido materno : ");
        String apellidoMaterno = input.nextLine();
        System.out.println("Fecha nacimiento (YYYY-MM-DD) :");
        String date = input.nextLine();
        System.out.println("Telefono : ");
        String telefono = input.nextLine();
        System.out.println("Genermo (M/F) : ");
        String genero = input.nextLine();

        Usuario usuario = new Usuario(email,nombre,apellidoPaterno,apellidoMaterno,date,telefono,genero);
        String j = HTTPConexion.serializeJson(usuario);
        String respuesta = HTTPConexion.opciones("alta","usuario",j,dirIP);
        if(respuesta.equals(""))System.out.println("RES [OK]");
    }

    public static void consultaUsuario( )throws Exception{
        Scanner input = new Scanner (System.in);
        System.out.println("COSNULTA");
        System.out.println("Email : ");
        String email = input.nextLine();
        
        String respuesta = HTTPConexion.opciones("consulta","email",email,dirIP);
        Usuario usuario = HTTPConexion.deserializeJson(respuesta,new TypeToken<Usuario>(){}.getType());
        usuario.mostrar();
    }

    public static void borrarUsuario( )throws Exception{
        Scanner input = new Scanner (System.in);
        System.out.println("BORRAR USUARIO");
        System.out.println("Email : ");
        String email = input.nextLine();

        String respuesta = HTTPConexion.opciones("borra","email",email,dirIP);
        if(respuesta.equals(""))System.out.println("RES [OK]");
    }

    public static void borrarUsuarios( )throws Exception{
        Scanner input = new Scanner (System.in);
        System.out.println("BORRAR USUARIOS");

        String respuesta = HTTPConexion.opciones("borra_usuarios","","",dirIP);
        if(respuesta.equals(""))System.out.println("RES [OK]");
    }
}