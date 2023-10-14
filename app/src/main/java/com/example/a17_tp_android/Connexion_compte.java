package com.example.a17_tp_android;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Connexion_compte extends MenuActivity {

    EditText txtCourrielConnexion;
    EditText txtMotDepasseConnexion;
    Button btnConnexion;
    TextView lblCrerCompte;

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
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
        setContentView(R.layout.connexion_compte);

        mAuth = FirebaseAuth.getInstance();

        txtCourrielConnexion = findViewById(R.id.courrielConnexion);
        txtMotDepasseConnexion = findViewById(R.id.motDePasseConnexion);
        btnConnexion = findViewById(R.id.btnConnexion);
        lblCrerCompte = findViewById(R.id.creerCompte);

        lblCrerCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Creation_compte.class);
                startActivity(intent);
                finish();
            }
        });

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String courriel, motDePasse;
                courriel = String.valueOf(txtCourrielConnexion.getText());
                motDePasse = String.valueOf(txtMotDepasseConnexion.getText());

                if(TextUtils.isEmpty(courriel)){
                    Toast.makeText(Connexion_compte.this,"Saisir votre courriel",
                            Toast.LENGTH_SHORT);
                    return;
                }

                if(TextUtils.isEmpty(motDePasse)){
                    Toast.makeText(Connexion_compte.this,"Saisir votre mot de passe",
                            Toast.LENGTH_SHORT);
                    return;
                }

                mAuth.signInWithEmailAndPassword(courriel, motDePasse)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(Connexion_compte.this, "Connexion réussie.",
                                            Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                    FirebaseUser user = mAuth.getCurrentUser();

                                } else {

                                    Toast.makeText(Connexion_compte.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });




    }
}