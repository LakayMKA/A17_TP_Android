package com.example.a17_tp_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Creation_compte extends AppCompatActivity {

    EditText txtCourrielCreation;
    EditText txtMotDepasseCreation;
    EditText txtNomUtilisateurCreation;
    Button btnCreer;

    TextView lblConnexion;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creation_compte);

        mAuth = FirebaseAuth.getInstance();

        txtCourrielCreation = findViewById(R.id.courrielCreation);
        txtMotDepasseCreation = findViewById(R.id.motDePasseCreation);
        txtNomUtilisateurCreation = findViewById(R.id.nomUtilisateurCreation);
        btnCreer = findViewById(R.id.btn_creation);

        progressBar = findViewById(R.id.progressBar1);

        lblConnexion = findViewById(R.id.seConnecter);

        lblConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Connexion_compte.class);
                startActivity(intent);
                finish();
            }
        });


        btnCreer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String courriel, motDePasse;
                courriel = String.valueOf(txtCourrielCreation.getText());
                motDePasse = String.valueOf(txtMotDepasseCreation.getText());

                if(TextUtils.isEmpty(courriel)){
                    Toast.makeText(Creation_compte.this,"Saisir votre courriel",
                            Toast.LENGTH_SHORT);
                    return;
                }

                if(TextUtils.isEmpty(motDePasse)){
                    Toast.makeText(Creation_compte.this,"Saisir votre mot de passe",
                            Toast.LENGTH_SHORT);
                    return;
                }

                mAuth.createUserWithEmailAndPassword(courriel, motDePasse)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {

                                    // Get user ID
                                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                    // Create user data
                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put("courriel", courriel);
                                    userData.put("eventsAttending", new ArrayList<>());

                                    // Add user data to the database
                                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
                                    userRef.child(userId).setValue(userData);

                                    Toast.makeText(Creation_compte.this, "Authentication réussie.",
                                            Toast.LENGTH_SHORT).show();

                                } else {

                                    Toast.makeText(Creation_compte.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });




            }
        });





    }
}