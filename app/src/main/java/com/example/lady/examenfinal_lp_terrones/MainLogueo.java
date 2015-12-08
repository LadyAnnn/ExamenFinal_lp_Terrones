package com.example.lady.examenfinal_lp_terrones;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import clases.ClaseLogeo;

public class MainLogueo extends AppCompatActivity implements View.OnClickListener {
    EditText editTextUsuario;
    EditText editTextClave;
    Button buttonIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_logueo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editTextUsuario = (EditText)findViewById(R.id.editUsuario);
        editTextClave = (EditText)findViewById(R.id.editClave);
        buttonIngresar = (Button)findViewById(R.id.buttonIngresar);
        buttonIngresar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonIngresar:
                String sUsuario = editTextUsuario.getText().toString();
                String sClave = editTextClave.getText().toString();
                new ClaseLogeo(this).execute(sUsuario,sClave);
                break;
        }
    }
}
