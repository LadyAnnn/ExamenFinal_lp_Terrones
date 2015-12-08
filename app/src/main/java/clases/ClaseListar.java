package clases;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lady.examenfinal_lp_terrones.R;

import org.json.JSONArray;
import org.json.JSONException;

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
 * Created by LADY on 03/12/2015.
 */
public class ClaseListar extends AsyncTask<String,String,String> {
    URL URLCONNECTION ;
    String urlString="http://10.40.80.214/servicios/index.php?opc=1";
    Activity activity;
    JSONArray jsonArrayLista;

    public ClaseListar(Activity ctx){
        activity=ctx;

    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URLCONNECTION= new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) URLCONNECTION.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            jsonArrayLista= new JSONArray(responseStrBuilder.toString());
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
        Toast.makeText(activity.getApplicationContext(), "Listando...", Toast.LENGTH_SHORT).show();
        ArrayList<String> jsonObj = new ArrayList<>();
        for (int i = 0; i < jsonArrayLista.length(); i++) {
            try {
                jsonObj.add(jsonArrayLista.getJSONObject(i).toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        AdaptadorCustom arrayAdapterCustom = new AdaptadorCustom(activity.getApplicationContext(),0,jsonObj);
        ListView listView = (ListView)activity.findViewById(R.id.listView);
        listView.setAdapter(arrayAdapterCustom);
        super.onPostExecute(s);
    }
}