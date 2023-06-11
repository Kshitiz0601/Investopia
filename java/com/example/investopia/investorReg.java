package com.example.investopia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class investorReg extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_reg);
        EditText name = findViewById(R.id.name);
        EditText pan = findViewById(R.id.panno);
        EditText email = findViewById(R.id.email);
        EditText pass = findViewById(R.id.pass);
        EditText repass = findViewById(R.id.reenter);

        Button submit = findViewById(R.id.submit);

        TextView login = findViewById(R.id.alreadyUser);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(investorReg.this,InvestorLogin.class));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((pass.getText().toString()).equals(repass.getText().toString()) && Pattern.compile("^(.+)@(.+)$").matcher(email.getText().toString()).matches() && Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}").matcher(pan.getText().toString()).matches()){

                    Intent i = new Intent(investorReg.this,investorAdditionalInfo.class);
                    i.putExtra("name",name.getText().toString());
                    i.putExtra("pan",pan.getText().toString());
                    i.putExtra("email",email.getText().toString());
                    i.putExtra("pass",pass.getText().toString());
                    startActivity(i);
                }
                else{
                    Toast.makeText(investorReg.this,"Please Enter Valid Details",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}