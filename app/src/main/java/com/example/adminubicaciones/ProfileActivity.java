package com.example.adminubicaciones;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;

    private Button mButtonLogout;

    private EditText mTextTitulo;
    private EditText mTextMensaje;

    private Button mButtonCancelar;
    private Button mButtonAgregar;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(this);

        mButtonLogout = (Button)findViewById(R.id.button_logout);
        mButtonLogout.setOnClickListener(this);

        mTextTitulo = (EditText)findViewById(R.id.edittext_titulo_record);
        mTextMensaje = (EditText)findViewById(R.id.edittext_mensaje_record);

        mButtonAgregar = (Button)findViewById(R.id.button_agregar);
        mButtonCancelar = (Button)findViewById(R.id.button_cancel);

        mButtonAgregar.setOnClickListener(this);
        mButtonCancelar.setOnClickListener(this);
    }

    private void logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this,LoginActivity.class));
    }

    private void agregarRecord(){
        //agregar recordatorio
        final String titulo = mTextTitulo.getText().toString().trim();
        final String mensaje = mTextMensaje.getText().toString().trim();

        //Checar si los campos estan vacios
        if(TextUtils.isEmpty(titulo)){
            Toast.makeText(this,"Introduce un titulo", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(mensaje)){
            Toast.makeText(this,"Introduce un mensaje", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registrando usuario...");
        progressDialog.show();

        //Guardar recordatorio en la base de datos
        DatabaseReference myRef = database.getReference(firebaseAuth.getCurrentUser().getUid());
        myRef.child("Recordatorios").child(titulo).setValue(mensaje);
        finish();
        startActivity(new Intent(this,DashboardActivity.class));
    }

    @Override
    public void onClick(View v) {
        if(v == mButtonLogout){
            logout();
        }

        if(v == mButtonCancelar){
            finish();
            startActivity(new Intent(this,DashboardActivity.class));
        }

        if(v == mButtonAgregar){
            agregarRecord();
        }
    }
}
