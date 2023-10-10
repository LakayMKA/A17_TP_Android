package com.example.a17_tp_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

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
                // Lancer l'activité CreationEvenement
                Intent intent = new Intent(MainActivity.this, CreationEvenement.class);
                startActivity(intent);
            }
        });

        btnListActivites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lancer l'activité qui affiche la liste des événements (que vous devez créer si elle n'existe pas encore)
                Intent intent = new Intent(MainActivity.this, ListEvenementsActivity.class);  // Assurez-vous de remplacer par le nom de votre activité de liste.
                startActivity(intent);
            }
        });

    }
}