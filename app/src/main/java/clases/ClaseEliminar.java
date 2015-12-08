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
public class ClaseEliminar extends AsyncTask<String,String,String> {
    URL URLCONNECTION ;
    String urlString="http://10.40.80.214/servicios/index.php?opc=4";
    Activity activity;
    JSONObject jsonArrayLista;
    public ClaseEliminar(Activity ctx){
        activity=ctx;

    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String id = params[0];
            URLCONNECTION= new URL(urlString+"&idusuario="+id);
            HttpURLConnection urlConnection = (HttpURLConnection) URLCONNECTION.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();//peticiones

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            jsonArrayLista= new JSONObject(responseStrBuilder.toString());//confirma si no o no
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
    protected void onPostExecute(String s) {
        try {
            if (jsonArrayLista.getBoolean("value")){
                Toast.makeText(activity.getApplicationContext(), "Elimino Correctamente!", Toast.LENGTH_SHORT).show();
                new ClaseListar(activity).execute();//vuelve a listar
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        super.onPostExecute(s);
    }

}
