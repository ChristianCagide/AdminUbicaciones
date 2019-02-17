package com.example.adminubicaciones;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mButtonAgreg;
    private Button mButtonLogout;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        mButtonLogout = (Button)findViewById(R.id.button_logout);
        mButtonLogout.setOnClickListener(this);

        mButtonAgreg = (Button)findViewById(R.id.button_agreg_record);
        mButtonAgreg.setOnClickListener(this);
    }

    private void logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this,LoginActivity.class));
    }

    @Override
    public void onClick(View v) {
        if(v == mButtonAgreg){
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
        }

        if(v == mButtonLogout){
            logout();
        }
    }
}
