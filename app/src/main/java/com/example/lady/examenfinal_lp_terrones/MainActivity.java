package com.example.lady.examenfinal_lp_terrones;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import clases.ClaseAgregar;
import clases.ClaseEliminar;
import clases.ClaseListar;
import clases.ClaseModificar;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        listView = (ListView) findViewById(R.id.listView);
        listarUsuarios();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void listarUsuarios() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Operaciones http
            new ClaseListar(this).execute();
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    String s = (String) parent.getAdapter().getItem(position);
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        eliminarUsuario(jsonObject.getString("idusuario"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("DATACLICK", s);
                    return false;
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String s = (String) parent.getAdapter().getItem(position);
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        actualizarUsuario(new String[]{jsonObject.getString("idusuario"), jsonObject.getString("usuario"), jsonObject.getString("clave")});
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("DATACLICK", s);

                }
            });

        } else {
            // Mostrar errores
        }

    }
    private void eliminarUsuario(final String idusuario) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar");
        builder.setMessage("¿Esta Seguro de Eliminar?");
        builder.setPositiveButton("Seguro", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new ClaseEliminar(activity).execute(idusuario);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


    private void actualizarUsuario(final String[] data) {
        final View layout_insert = View.inflate(this, R.layout.actividad_actualizar, null);
        final EditText editTextUsuario = (EditText) layout_insert.findViewById(R.id.editUsuario);
        final EditText editTextClave = (EditText) layout_insert.findViewById(R.id.editClave);
        editTextUsuario.setText(data[1]);
        editTextClave.setText(data[2]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Actualizar");
        builder.setView(layout_insert);
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String sUsuario = editTextUsuario.getText().toString();
                String sClave = editTextClave.getText().toString();
                Log.d("DATOS", data[0] + "-" + sClave);
                new ClaseModificar(activity).execute(data[0], sUsuario, sClave);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
    private void registrarUsuario() {
        final View layout_insert = View.inflate(this, R.layout.actividad_agregar, null);
        final EditText editTextUsuario = (EditText) layout_insert.findViewById(R.id.textUsuario);
        final EditText editTextClave = (EditText) layout_insert.findViewById(R.id.textClave);
        final EditText editTextNombre = (EditText) layout_insert.findViewById(R.id.textNombre);
        final EditText editTextApellido = (EditText) layout_insert.findViewById(R.id.textApellido);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registrar");
        builder.setView(layout_insert);
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String sUsuario = editTextUsuario.getText().toString();
                String sClave = editTextClave.getText().toString();
                String sNombre = editTextNombre.getText().toString();
                String sApellido = editTextApellido.getText().toString();
                Log.d("DATOS", sUsuario + "-" + sClave);
                new ClaseAgregar(activity).execute(sUsuario, sClave,sNombre,sApellido);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
}
