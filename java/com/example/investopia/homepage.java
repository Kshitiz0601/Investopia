package com.example.investopia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Button companybtn = findViewById(R.id.companybtn);
        Button investorbtn = findViewById(R.id.investorbtn);

        companybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homepage.this,companyReg.class);
                startActivity(intent);
            }
        });

        investorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homepage.this,investorReg.class);
                startActivity(intent);
            }
        });
    }
}