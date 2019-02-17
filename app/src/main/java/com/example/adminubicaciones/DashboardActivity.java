package com.example.adminubicaciones;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mButtonAgreg;
    private Button mButtonLogout;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;

    private RecyclerView mRecyclerView;

    private ArrayList<String> mTitulos = new ArrayList<>();
    private ArrayList<String> mMensajes = new ArrayList<>();

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(firebaseAuth.getCurrentUser().getUid());

        initArrays();

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

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

    private void initArrays(){
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild("Recordatorios")) {
                    // run some code
                    myRef = database.getReference(myRef.getKey() + "/Recordatorios");
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot recordatorio : dataSnapshot.getChildren()){
                                mTitulos.add(recordatorio.getKey());
                                mMensajes.add(recordatorio.getValue().toString());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    finish();
                    startActivity(new Intent(DashboardActivity.this,ProfileActivity.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mTitulos, mMensajes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
