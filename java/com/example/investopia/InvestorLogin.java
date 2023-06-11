package com.example.investopia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class InvestorLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_login);


        Button login = findViewById(R.id.submit);
        EditText username = findViewById(R.id.panno);
        EditText Password = findViewById(R.id.pass);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String userUsername = username.getText().toString();
                final String userPassword = Password.getText().toString();
                DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("Investor Details");
                Query checkUserDatabase = reference.orderByKey().equalTo(userUsername);
                checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            username.setError(null);
                            String passwordFromDB = snapshot.child(userUsername).child("Password").getValue(String.class);
                            if(Objects.equals(passwordFromDB,userPassword)){
                                username.setError(null);
                                Intent intent = new Intent(InvestorLogin.this,investorMain.class);
                                intent.putExtra("pan",userUsername);
                                startActivity(intent);
                            }
                            else{
                                Password.setError("Invalid Password");
                                Password.requestFocus();
                            }
                        }
                        else{
                            username.setError("User Doesn't Exists");
                            username.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}