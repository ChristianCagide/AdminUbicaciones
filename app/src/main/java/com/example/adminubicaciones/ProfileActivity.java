package com.example.adminubicaciones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;

    private Button mButtonLogout;

    private EditText mEditTextTitulo;
    private EditText mEditTextMensaje;

    private Button mButtonCancelar;
    private Button mButtonAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        mButtonLogout = (Button)findViewById(R.id.button_logout);
        mButtonLogout.setOnClickListener(this);

        mEditTextTitulo = (EditText)findViewById(R.id.edittext_titulo_record);
        mEditTextMensaje = (EditText)findViewById(R.id.edittext_mensaje_record);

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
