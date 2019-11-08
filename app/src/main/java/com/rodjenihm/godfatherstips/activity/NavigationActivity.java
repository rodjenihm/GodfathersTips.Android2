package com.rodjenihm.godfatherstips.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.rodjenihm.godfatherstips.R;

public class NavigationActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        findViewById(R.id.button).setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(this, AuthActivity.class));
            finish();
        });
    }
}
