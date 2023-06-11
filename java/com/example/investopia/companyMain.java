package com.example.investopia;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class companyMain extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_main);
        Intent i = getIntent();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Investor Details");
        FirstFragment firstFragment = new FirstFragment(reference);
        SecondFragment secondFragment = new SecondFragment(i.getStringExtra("GSTIN"), FirebaseDatabase.getInstance());
        ThirdFragment thirdFragment = new ThirdFragment(i.getStringExtra("GSTIN"));

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.person:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.flFragment, firstFragment)
                                .commit();
                        return true;

                    case R.id.home:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.flFragment, secondFragment)
                                .commit();
                        return true;

                    case R.id.settings:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.flFragment, thirdFragment)
                                .commit();
                        return true;
                }
                return false;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.person);
    }
}