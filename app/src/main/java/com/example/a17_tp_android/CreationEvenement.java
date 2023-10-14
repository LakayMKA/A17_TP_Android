package com.example.a17_tp_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import modele.Evenement;

public class CreationEvenement extends MenuActivity {

    // Déclaration des champs EditText
    private EditText txtNom, txtAdresse, txtDate, txtDescription, txtType, txtNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_evenement);


        // Obtenez une instance de la base de données avec l'URL
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://a17-tp-android-default-rtdb.firebaseio.com/");

        DatabaseReference eventsRef = database.getReference("evenements");

        // Initialisation des champs EditText
        txtNom = findViewById(R.id.txtNom);
        txtAdresse = findViewById(R.id.txtAdresse);
        txtDate = findViewById(R.id.txtDate);
        txtDescription = findViewById(R.id.txtDescription);
        txtType = findViewById(R.id.txtType);
        txtNombre = findViewById(R.id.txtNombre);

        Button btnCreerEvent = findViewById(R.id.btnCreerEvent);

        btnCreerEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ajouterEvenement(eventsRef);
            }

            private void ajouterEvenement(DatabaseReference evenementReference) {
                String nom = txtNom.getText().toString();
                String adresse = txtAdresse.getText().toString();
                String date = txtDate.getText().toString();
                String description = txtDescription.getText().toString();
                String type = txtType.getText().toString();
                int nombreParticipants = Integer.parseInt(txtNombre.getText().toString());

                Evenement newEvent = new Evenement(nom, adresse, date, description, type, nombreParticipants);

                String key = evenementReference.push().getKey(); // Génère un identifiant unique pour le nouvel événement
                evenementReference.child(key).setValue(newEvent);

                Toast.makeText(CreationEvenement.this, "Événement ajouté avec succès!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreationEvenement.this, ListEvenementsActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}