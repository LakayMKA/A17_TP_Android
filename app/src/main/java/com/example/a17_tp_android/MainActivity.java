package com.example.a17_tp_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtenez une instance de la base de données avec l'URL correcte
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://a17-tp-android-default-rtdb.firebaseio.com/");

        // Obtenez une référence à la "message" noeud
        DatabaseReference myRef = database.getReference("message");

        // Définir une valeur pour le noeud référencé
        myRef.setValue("Hello, World!");

        myRef.setValue("Hello, World!").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Firebase", "Données écrites avec succès !");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Firebase", "Erreur lors de l'écriture des données", e);
            }
        });
    }
}