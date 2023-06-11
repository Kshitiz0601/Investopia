package com.example.investopia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class companyAdditonalInfo extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_additonal_info);

        firebaseDatabase = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        String cname = intent.getStringExtra("cname");
        String oname = intent.getStringExtra("oname");
        String gstin = intent.getStringExtra("gstin");
        String email = intent.getStringExtra("email");
        String pass = intent.getStringExtra("pass");

        EditText descp = findViewById(R.id.descp);
        EditText linkedin = findViewById(R.id.linkden);

        Button reg = findViewById(R.id.regbtn);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> map = new HashMap<>();
                map.put("Email",email);
                map.put("Company Name",cname);
                map.put("Owner Name",oname);
//                map.put("GST Number",gstin);
                map.put("Password",pass);
                map.put("Description",descp.getText().toString());
                map.put("LinkedIn",linkedin.getText().toString());


                firebaseDatabase.getReference().child("Company Details").child(gstin).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(companyAdditonalInfo.this,"Registration Successful",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(companyAdditonalInfo.this,companyLogin.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(companyAdditonalInfo.this,"Registration Unsuccessful, Please try again",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}