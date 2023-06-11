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
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddProduct extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Intent i = getIntent();
        firebaseDatabase = FirebaseDatabase.getInstance();

        String gstin = i.getStringExtra("gstin");
        EditText id  = findViewById(R.id.id);
        EditText name = findViewById(R.id.productName);
        EditText domain = findViewById(R.id.domain);
        EditText gdrive = findViewById(R.id.gdrive);
        EditText estimated = findViewById(R.id.estimatedCost);
        EditText desc = findViewById(R.id.description);
        Button submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> map = new HashMap<>();
                map.put("Product Name",name.getText().toString());
                map.put("Domain",domain.getText().toString());
                map.put("GDrive Link",gdrive.getText().toString());
                map.put("Estimated Cost",estimated.getText().toString());
                map.put("Description",desc.getText().toString());

                firebaseDatabase.getReference().child("Products").child(gstin).child(id.getText().toString()).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddProduct.this,"Product Added Successfully",Toast.LENGTH_LONG).show();
                        name.setText("");
                        domain.setText("");
                        gdrive.setText("");
                        estimated.setText("");
                        desc.setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddProduct.this,"Unsuccessful, Please try again",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
}