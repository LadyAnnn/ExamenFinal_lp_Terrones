package clases;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by LADY on 03/12/2015.
 */
public class ClaseAgregar extends AsyncTask<String,String,String> {
    URL URLCONNECTION ;
    String urlString="http://10.40.80.214/servicios/index.php?opc=2";
    Activity activity;

    JSONObject jsonArrayLista;
    public ClaseAgregar(Activity ctx){
        activity=ctx;

    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String usuario = params[0];
            String clave = params[1];
            String nombre = params[2];
            String apellido=params[3];
            URLCONNECTION= new URL(urlString+"&usuario="+usuario+"&clave="+clave+"&nombre="+nombre+"&apellido="+apellido);
            HttpURLConnection urlConnection = (HttpURLConnection) URLCONNECTION.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();// peticion

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            jsonArrayLista= new JSONObject(responseStrBuilder.toString());
            Log.d("CONNECTEDSERVICE", jsonArrayLista.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("CONNECTEDSERVICE", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("CONNECTEDSERVICE", e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("CONNECTEDSERVICE", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {// clases asincronaz
        try {
            if (jsonArrayLista.getBoolean("value")){
                Toast.makeText(activity.getApplicationContext(), "Registro correctamente!", Toast.LENGTH_SHORT).show();
                new ClaseListar(activity).execute();//mostrar el dato nuevo
            }else{

                Toast.makeText(activity.getApplicationContext(), "Usuario ya existe!", Toast.LENGTH_SHORT).show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        super.onPostExecute(s);
    }


}
