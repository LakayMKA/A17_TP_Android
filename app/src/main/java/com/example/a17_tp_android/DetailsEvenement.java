package com.example.a17_tp_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import modele.Evenement;

public class DetailsEvenement extends AppCompatActivity {
    // Ajouter des références pour les vues
    private TextView lblNomActiviteDetail;
    private TextView lblDateDetail;
    private TextView lblAdresseDetail;
    private TextView lblTypeDetail;
    private TextView lblDescriptionDetail;
    private TextView lblNombreParticipantDetail;
    private Button btnParticiper;

    private String eventKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_evenement);

        // Initialisation des vues
        lblNomActiviteDetail = findViewById(R.id.lblNomActiviteDetail);
        lblDateDetail = findViewById(R.id.lblDateDetail);
        lblAdresseDetail = findViewById(R.id.lblAdresseDetail);
        lblTypeDetail = findViewById(R.id.lblTypeDetail);
        lblDescriptionDetail = findViewById(R.id.lblDescriptionDetail);
        lblNombreParticipantDetail = findViewById(R.id.lblNombreParticipantDetail);
        btnParticiper = findViewById(R.id.btnParticiper);

        eventKey = getIntent().getStringExtra("EVENEMENT_CLE");
        // Interroger Firebase
        DatabaseReference evenementReference = FirebaseDatabase.getInstance().getReference("evenements").child(eventKey);
        evenementReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Evenement evenement = snapshot.getValue(Evenement.class);
                // Remplir les vues avec les données de l'événement
                lblNomActiviteDetail.setText(evenement.getNom());
                lblDateDetail.setText("Date: " + evenement.getDate());
                lblAdresseDetail.setText(evenement.getAdresse());
                lblTypeDetail.setText(evenement.getType());
                lblDescriptionDetail.setText(evenement.getDescription());
                lblNombreParticipantDetail.setText(String.valueOf(evenement.getNombreParticipant()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnParticiper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("evenements").child(eventKey);

                // Vérifier si l'utilisateur participe déjà à cet événement
                userRef.child("eventsAttending").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean alreadyParticipating = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (snapshot.getValue().equals(eventKey)) {
                                alreadyParticipating = true;
                                break;
                            }
                        }

                        if (alreadyParticipating) {
                            Toast.makeText(DetailsEvenement.this, "Vous participez déjà à cet événement!", Toast.LENGTH_SHORT).show();
                        } else {
                // Augmenter le nombre de participants de l'événement
                eventRef.child("nombreParticipant").runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        Integer curr = mutableData.getValue(Integer.class);
                        if (curr == null) {
                            mutableData.setValue(1);
                        } else {
                            mutableData.setValue(curr + 1);
                        }
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        if (committed) {
                            // Ajouter l'ID de l'événement à la liste des événements auxquels l'utilisateur participe
                            userRef.child("eventsAttending").push().setValue(eventKey);
                            Toast.makeText(DetailsEvenement.this, "Vous etes ajouté(e) dans la liste des participants!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        //Gerer erreurs
                    }
                });
            }
        });
    }
}
