package com.example.adminubicaciones;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mTextName;
    private EditText mTextEmail;
    private EditText mTextPassword;
    private EditText mTextPasswordConfirm;
    private Button mButtonCancel;
    private Button mButtonRegisterNow;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //Abre la aplicacion
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        mTextName = (EditText)findViewById(R.id.edittext_name);
        mTextEmail = (EditText)findViewById(R.id.edittext_username);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mTextPasswordConfirm = (EditText)findViewById(R.id.edittext_passwordconfirm);
        mButtonCancel = (Button) findViewById(R.id.button_cancel);
        mButtonRegisterNow = (Button) findViewById(R.id.button_register);

        mButtonRegisterNow.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);

    }

    private void registerUser(){
        String name = mTextName.getText().toString().trim();
        String email = mTextEmail.getText().toString().trim();
        String password = mTextPassword.getText().toString().trim();
        String password_confirm = mTextPasswordConfirm.getText().toString().trim();

        //Checar si los campos estan vacios
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Introduce un Nombre", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Introduce un Correo", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Introduce una contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password_confirm)){
            Toast.makeText(this,"Vuelva a introducir la contraseña", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password != password_confirm){
            //passwords no son iguales
            Toast.makeText(this,"Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registrando usuario...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //se registro de manera correcta el usuario
                            Toast.makeText(RegisterActivity.this,"Registrado correctamente", Toast.LENGTH_SHORT).show();
                            //Abre aplicacion
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this,"No se pudo registrar", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });


    }

    @Override
    public void onClick(View v) {
        if(v == mButtonRegisterNow){
            registerUser();
        }
        if(v == mButtonCancel){
            //Open login activity
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
