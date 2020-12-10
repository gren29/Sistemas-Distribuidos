import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HTTPConexion{
    public static <T> String serializeJson(T object) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(object);
    }
    public static <T> T deserializeJson(String json, Type t) {
        Gson gson = new Gson();
        return gson.fromJson(json, t);
    }
    
    public static String opciones(String operacion, String parametro, String json, String ip)
            throws MalformedURLException, IOException, RuntimeException {
        URL url = new URL("http://" + ip + ":8080/Servicio/rest/ws/" + operacion);
        HttpURLConnection conexcion = (HttpURLConnection)url.openConnection();
        
        conexcion.setDoOutput(true);
        conexcion.setRequestMethod("POST");
        conexcion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        String parametros = parametro + "=" + URLEncoder.encode(json, "UTF-8");

        OutputStream os = conexcion.getOutputStream();

        os.write(parametros.getBytes());
        os.flush();
        if (conexcion.getResponseCode() != HttpURLConnection.HTTP_OK){
            throw new RuntimeException("Codigo de error HTTP: " + conexcion.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(conexcion.getInputStream()));
        String respuesta;
        String aux = "";
        while ((respuesta = br.readLine()) != null){
            aux += respuesta;
        }
        conexcion.disconnect();
        return aux;
    }
}