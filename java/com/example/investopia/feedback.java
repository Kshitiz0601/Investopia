package com.example.investopia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i =getIntent();
        String inp = i.getStringExtra("id");
        setContentView(R.layout.activity_feedback);
        EditText feedback = findViewById(R.id.feed);
        Button submit = findViewById(R.id.submit);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("Feedback");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                HashMap<String,String> map = new HashMap<>();
//                map.put("",feedback.getText().toString());
                reference.child(inp).child("").setValue(feedback.getText().toString());
                feedback.setText("");
                Toast.makeText(feedback.this, "Feedback Submitted", Toast.LENGTH_SHORT).show();

            }
        });
    }
}