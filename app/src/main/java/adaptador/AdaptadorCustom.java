package adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lady.examenfinal_lp_terrones.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by LADY on 03/12/2015.
 */
public class AdaptadorCustom extends ArrayAdapter<String> {
    List<String> itemsLista;
    Context CONTX;

    public AdaptadorCustom(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        itemsLista = objects;
        CONTX = context;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        try {
            JSONObject jsonObject = new JSONObject(itemsLista.get(position));


            if (row == null)
            {

                LayoutInflater vi = (LayoutInflater)CONTX.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = vi.inflate(R.layout.actividad_lista, null);
            }

            TextView tvUsuario = (TextView) row.findViewById (R.id.textUsuario);
            TextView tvClave = (TextView) row.findViewById (R.id.textClave);
            tvUsuario.setText("Usuario:  "+jsonObject.getString("usuario"));
            tvClave.setText("Clave:  "+jsonObject.getString("clave"));



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return row;
    }
}
