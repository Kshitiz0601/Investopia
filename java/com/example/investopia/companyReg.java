package com.example.investopia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class companyReg extends AppCompatActivity {
    EditText cname, oname, gstin, email, pass, repass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_reg);
        cname = findViewById(R.id.name);
        oname = findViewById(R.id.oname);
        gstin = findViewById(R.id.gstin);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        repass = findViewById(R.id.reenter);

        Button submit = findViewById(R.id.submit);
        TextView login = findViewById(R.id.alreadyUser);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(companyReg.this,companyLogin.class));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((pass.getText().toString()).equals(repass.getText().toString()) && Pattern.compile("^(.+)@(.+)$").matcher(email.getText().toString()).matches() && Pattern.compile("^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$").matcher(gstin.getText().toString()).matches()){

                    Intent  i = new Intent(companyReg.this,companyAdditonalInfo.class);
                    i.putExtra("cname",cname.getText().toString());
                    i.putExtra("oname",oname.getText().toString());
                    i.putExtra("gstin",gstin.getText().toString());
                    i.putExtra("email",email.getText().toString());
                    i.putExtra("pass",pass.getText().toString());
                    startActivity(i);
                }
                else{
                    Toast.makeText(companyReg.this,"Please Enter Valid Details",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}


