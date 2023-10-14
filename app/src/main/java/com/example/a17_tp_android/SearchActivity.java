package com.example.a17_tp_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import modele.Evenement;

public class SearchActivity extends AppCompatActivity {
    private EditText searchEditText;
    private Button searchButton;
    private ListView resultList;
    private List<Evenement> searchResults;
    private ArrayAdapter<Evenement> adapter;

    private TextView tvNoResultsFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        resultList = findViewById(R.id.resultList);
        tvNoResultsFound = findViewById(R.id.tvNoResultsFound);

        searchResults = new ArrayList<>();

        // Initialisation de l'adapter
        adapter = new ArrayAdapter<Evenement>(this, R.layout.event_item_list_layout, searchResults){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.event_item_list_layout, parent, false);
                }
                TextView text1 = convertView.findViewById(R.id.lblNomActivite);
                TextView text2 = convertView.findViewById(R.id.lblDate);
                TextView text3 = convertView.findViewById(R.id.lblNombreParticipant);

                text1.setText(searchResults.get(position).getNom());
                text2.setText(searchResults.get(position).getDate() + ", " + searchResults.get(position).getAdresse());
                text3.setText(searchResults.get(position).getNombreParticipant() + " participant(s)");

                return convertView;
            }
        };
        resultList.setAdapter(adapter);

        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Evenement evenementClique = searchResults.get(position);

                // Lancer une nouvelle activité pour afficher les détails de l'événement
                Intent intent = new Intent(SearchActivity.this, DetailsEvenement.class);
                intent.putExtra("EVENEMENT_CLE", evenementClique.getCle());
                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(v -> {
            String searchTerm = searchEditText.getText().toString().trim();

            // Chercher dans la base de données Firebase
            DatabaseReference evenementsRef = FirebaseDatabase.getInstance().getReference("evenements");
            evenementsRef.orderByChild("nom").startAt(searchTerm).endAt(searchTerm + "\uf8ff").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    searchResults.clear();
                    tvNoResultsFound.setVisibility(View.GONE);

                    for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                        Evenement evenement = eventSnapshot.getValue(Evenement.class);
                        if (evenement != null) {
                            evenement.setCle(eventSnapshot.getKey());
                            searchResults.add(evenement);
                        }
                    }
                    if (searchResults.isEmpty()) {
                        tvNoResultsFound.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();// Affichez vos résultats ici, par exemple en utilisant un ArrayAdapter
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Gérer les erreurs ici
                }
            });
        });
    }
}