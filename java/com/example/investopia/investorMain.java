package com.example.investopia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class investorMain extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_main);
        Intent i = getIntent();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        String pan  = i.getStringExtra("pan");
        invesFrag1 frag1 =  new invesFrag1(FirebaseDatabase.getInstance(),pan);
        invesFrag2 frag2 =  new invesFrag2(pan,FirebaseDatabase.getInstance());
        invesFrag3 frag3 =  new invesFrag3(pan);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.person:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.flFragment, frag1)
                                .commit();
                        return true;

                    case R.id.home:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.flFragment, frag2)
                                .commit();
                        return true;

                    case R.id.settings:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.flFragment, frag3)
                                .commit();
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.person);
    }
}