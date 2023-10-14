package com.example.a17_tp_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import modele.Evenement;

public class ProfileActivity extends MenuActivity {
    private TextView userName, userEmail;
    private ListView eventsCreatedList, eventsParticipatingList;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private List<Evenement> eventsParticipating = new ArrayList<>();

    private ArrayAdapter<Evenement> setupAdapter(final List<Evenement> eventsList) {
        return new ArrayAdapter<Evenement>(this, R.layout.event_item_list_layout, eventsList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.event_item_list_layout, parent, false);
                }
                TextView text1 = convertView.findViewById(R.id.lblNomActivite);
                TextView text2 = convertView.findViewById(R.id.lblDate);
                TextView text3 = convertView.findViewById(R.id.lblNombreParticipant);

                text1.setText(eventsList.get(position).getNom());
                text2.setText(eventsList.get(position).getDate() + ", " + eventsList.get(position).getAdresse());
                text3.setText(eventsList.get(position).getNombreParticipant() + " participant(s)");

                return convertView;
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userEmail = findViewById(R.id.userEmail);
        eventsParticipatingList = findViewById(R.id.eventsParticipatingList);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            userEmail.setText(user.getEmail());



            // Récupération des événements auxquels l'utilisateur participe
            mDatabase.child("users").child(user.getUid()).child("eventsAttending").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    eventsParticipating.clear();
                    for (DataSnapshot eventKeySnapshot : dataSnapshot.getChildren()) {
                        String eventKey = eventKeySnapshot.getValue(String.class);
                        mDatabase.child("evenements").child(eventKey).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot eventSnapshot) {
                                Evenement evenement = eventSnapshot.getValue(Evenement.class);

                                if (evenement != null) {
                                    evenement.setCle(eventSnapshot.getKey());
                                    eventsParticipating.add(evenement);
                                }
                                Log.d("FirebaseDebug", "Event participation fetched: " + evenement.getNom());
                                ArrayAdapter<Evenement> adapterEventsParticipating = setupAdapter(eventsParticipating);
                                eventsParticipatingList.setAdapter(adapterEventsParticipating);
                                adapterEventsParticipating.notifyDataSetChanged();

                            }



                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Log.e("FirebaseDebug", "Error fetching event details: " + databaseError.getMessage());
                                                     }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("FirebaseDebug", "Error fetching events attending: " + databaseError.getMessage());
                }
            });
            eventsParticipatingList.setOnItemClickListener((parent, view, position, id) -> {
                Evenement clickedEvent = eventsParticipating.get(position);
                Intent intent = new Intent(ProfileActivity.this, DetailsEvenement.class);
                intent.putExtra("EVENEMENT_CLE", clickedEvent.getCle());
                startActivity(intent);
            });
        }
    }
}