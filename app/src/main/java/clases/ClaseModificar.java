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
public class ClaseModificar extends AsyncTask<String,String,String> {
    URL URLCONNECTION ;
    String urlString="http://10.40.80.214/servicios/index.php?opc=3";
    Activity activity;
    JSONObject jsonObject;
    public ClaseModificar(Activity ctx){
        activity=ctx;

    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String id = params[0];
            String usuario = params[1];
            String clave = params[2];
            URLCONNECTION= new URL(urlString+"&idusuario="+id+"&usuario="+usuario+"&clave="+clave+"");
            HttpURLConnection urlConnection = (HttpURLConnection) URLCONNECTION.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            jsonObject= new JSONObject(responseStrBuilder.toString());
            Log.d("CONNECTEDSERVICES", jsonObject.toString());

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
            if (jsonObject.getBoolean("value")){
                Toast.makeText(activity.getApplicationContext(), "Actualizo Correctamente!", Toast.LENGTH_SHORT).show();
                new ClaseListar(activity).execute();
            }else{
                Toast.makeText(activity.getApplicationContext(), "No Actualizo, cambiar usuario!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        super.onPostExecute(s);
    }


}
