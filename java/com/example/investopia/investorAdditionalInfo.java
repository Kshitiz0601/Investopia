package com.example.investopia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class investorAdditionalInfo extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_additional_info);

        ArrayList<String> al = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String pan = intent.getStringExtra("pan");
        String email = intent.getStringExtra("email");
        String pass = intent.getStringExtra("pass");

        CheckBox finance = findViewById(R.id.finance);
        CheckBox education = findViewById(R.id.education);
        CheckBox agriculture = findViewById(R.id.agro);
        CheckBox healthcare = findViewById(R.id.healthcare);
        CheckBox automotive = findViewById(R.id.automotive);
        CheckBox sports = findViewById(R.id.sports);

        finance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                al.add("Finance");
            }
        });

        education.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                al.add("Education");
            }
        });

        agriculture.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                al.add("Agriculture");
            }
        });

        healthcare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                al.add("Healthcare");
            }
        });

        automotive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                al.add("Automotive");
            }
        });

        sports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                al.add("Sports");
            }
        });


        Button reg = findViewById(R.id.reg);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("Name",name);
//                map.put("PAN number",pan);
                map.put("Email",email);
                map.put("Password",pass);
                map.put("Interests",al);


                firebaseDatabase.getReference().child("Investor Details").child(pan).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(investorAdditionalInfo.this,"Registration Successful",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(investorAdditionalInfo.this,InvestorLogin.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(investorAdditionalInfo.this,"Error Occurred, Please try again",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}