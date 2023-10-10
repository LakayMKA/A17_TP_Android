package com.example.a17_tp_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import modele.Evenement;

public class ListEvenementsActivity extends AppCompatActivity {
    private List<Evenement> evenementList;
    private ListView listView;
    private ArrayAdapter adapter;
    private FirebaseDatabase evenementDatabase;
    private DatabaseReference evenementDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste);

        // Initialisation de la base de données
        evenementDatabase = FirebaseDatabase.getInstance("https://a17-tp-android-default-rtdb.firebaseio.com/");
        evenementDatabaseReference = evenementDatabase.getReference("evenements");
        listView = findViewById(R.id.listView);

        // Rafraichissement de la liste des événements
        rafraichirListeListener(evenementDatabaseReference);


    }

    private void rafraichirListeListener(DatabaseReference evenementReference) {
        evenementReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                evenementList = new ArrayList<>();

                Iterable<DataSnapshot> listing = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = listing.iterator();
                while (iterator.hasNext()) {
                    Evenement value = iterator.next().getValue(Evenement.class);
                    evenementList.add(value);
                }

                adapter = setupAdapter(evenementList);
                listView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Traitement en cas d'erreur
            }
        });
    }
    private ArrayAdapter setupAdapter(final List<Evenement> evenementList) {
        return new ArrayAdapter<Evenement>(this, R.layout.event_item_list_layout, evenementList){
            public View getView(int position, View convertView, ViewGroup parent){
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.event_item_list_layout, parent, false);
                }
                TextView text1 = convertView.findViewById(R.id.lblNomActivite);
                TextView text2 = convertView.findViewById(R.id.lblDate);
                TextView text3 = convertView.findViewById(R.id.lblNombreParticipant);

                text1.setText(evenementList.get(position).getNom());
                text2.setText(evenementList.get(position).getDate() + ", " + evenementList.get(position).getAdresse());
                text3.setText(evenementList.get(position).getNombreParticipant()  + " participant(s)");

                return convertView;
            }
        };
    }

}