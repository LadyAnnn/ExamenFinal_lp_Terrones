package clases;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lady.examenfinal_lp_terrones.MainActivity;
import com.example.lady.examenfinal_lp_terrones.R;

import org.json.JSONArray;
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
import java.util.ArrayList;

import adaptador.AdaptadorCustom;

/**
 * Created by LADY on 08/12/2015.
 */
public class ClaseLogeo  extends AsyncTask<String,String,String> {
    URL URLCONNECTION ;
    String urlString="http://10.40.80.214/servicios/index.php?opc=5";
    Activity activity;
    JSONObject jsonExisteLista;

    public ClaseLogeo(Activity ctx){
        activity=ctx;

    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URLCONNECTION= new URL(urlString +"&usuario="+params[0]+"&clave="+params[1]+"");
            Log.d("PARAMETROS",""+params[0]+params[1]);
            HttpURLConnection urlConnection = (HttpURLConnection) URLCONNECTION.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            jsonExisteLista= new JSONObject(responseStrBuilder.toString());
            Log.d("CONNECTEDSERVICE", jsonExisteLista.toString());

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
            if(jsonExisteLista.getInt("value")>0){
                activity.startActivity(new Intent(activity.getApplicationContext(), MainActivity.class));
            }
            else{
                Toast.makeText(activity.getApplicationContext(),"Usuario no existe, por favor intentarlo de nuevo",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        super.onPostExecute(s);
    }
}