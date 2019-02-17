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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mTextEmail;
    private EditText mTextPassword;
    private Button mButtonLogin;
    private TextView mTextViewRegister;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //Abre la aplicacion
            finish();
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        mTextEmail = (EditText)findViewById(R.id.edittext_email);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        mButtonLogin = (Button)findViewById(R.id.button_login);
        mTextViewRegister = (TextView)findViewById(R.id.textview_register);

        mButtonLogin.setOnClickListener(this);
        mTextViewRegister.setOnClickListener(this);
    }

    private void loginUser(){
        String email = mTextEmail.getText().toString().trim();
        String password = mTextPassword.getText().toString().trim();

        //Checar si los campos estan vacios
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Introduce un Correo", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Introduce la Contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Iniciando Sesión...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            //Abre aplicacion
                            finish();
                            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == mButtonLogin){
            loginUser();
        }
        if(v == mTextViewRegister){
            //Open register activity
            finish();
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}
