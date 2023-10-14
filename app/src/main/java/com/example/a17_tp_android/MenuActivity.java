package com.example.a17_tp_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.bouton_menu_layout);  // Utilisez la mise en page de base

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });
    }

    @Override
    public void setContentView(int layoutResID) {
        FrameLayout activityContent = (FrameLayout) findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContent, true);
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.menu_profil) {
                    // Intent pour le profil
                    return true;
                } else if (id == R.id.menu_accueil) {
                    Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                } else if (id == R.id.menu_deconnexion) {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signOut();
                    Intent intent = new Intent(MenuActivity.this, Connexion_compte.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.menu_recherche) {
                    // Action pour la recherche
                    return true;
                }

                return false;
            }
        });
        popup.inflate(R.menu.menu);
        popup.show();
    }
}
