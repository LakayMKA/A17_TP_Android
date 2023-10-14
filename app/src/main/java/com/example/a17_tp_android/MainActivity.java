package com.example.a17_tp_android;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends MenuActivity {

    FirebaseAuth auth;
    Button btnListActivites, btnCreerActivite;

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        btnListActivites = findViewById(R.id.btnListActivites);
        btnCreerActivite = findViewById(R.id.btnCreerActivite);

        user = auth.getCurrentUser();
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Connexion_compte.class);
            startActivity(intent);
            finish();
        }


        btnCreerActivite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(MainActivity.this, CreationEvenement.class);
                startActivity(intent);
            }
        });

        btnListActivites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ListEvenementsActivity.class);
                startActivity(intent);
            }
        });

    }
}