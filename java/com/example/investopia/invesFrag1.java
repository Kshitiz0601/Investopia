package com.example.investopia;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import java.io.*;
import java.util.HashMap;

import androidx.fragment.app.Fragment;

public class invesFrag1 extends Fragment {
    FirebaseDatabase firebaseDatabase;
    ArrayList<String> al = new ArrayList<>();

    ArrayList<ArrayList<String>> details = new ArrayList<>();
    String pan;
    public invesFrag1(FirebaseDatabase fDatabase,String pnn){
        // require a empty public constructor
        firebaseDatabase = fDatabase;
        pan = pnn;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_inves_frag1, container, false);
        final String[] linkedIn = new String[1];
        AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
        dialog.setTitle("Product Details");

        final String[] gstin = new String[1];
        EditText editText = view.findViewById(R.id.search);
        ListView lv = view.findViewById(R.id.productList);
//        lv.setCacheColorHint(R.color.black);
        Button btn = view.findViewById(R.id.search_button);
        DatabaseReference prodRef = firebaseDatabase.getReference("Products");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> names = new ArrayList<>();
                prodRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren() ){
                            for (DataSnapshot snapshot2 : snapshot1.getChildren()){
                                if(snapshot2.child("Domain").getValue(String.class).equals(editText.getText().toString())){
                                    names.add(snapshot2.child("Product Name").getValue(String.class));
                                    gstin[0] = snapshot1.getKey();
                                    ArrayList<String> det = new ArrayList<>();
                                    firebaseDatabase.getReference("Company Details").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot5) {
                                            det.add(String.valueOf(snapshot5.child(gstin[0]).child("Company Name").getValue(String.class)));
                                            linkedIn[0] = snapshot5.child(gstin[0]).child("LinkedIn").getValue(String.class);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    det.add(snapshot2.child("Description").getValue(String.class));
                                    det.add(snapshot2.child("GDrive Link").getValue(String.class));
                                    det.add(snapshot2.child("Estimated Cost").getValue(String.class));
                                    det.add(snapshot2.getKey());
                                    details.add(det);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        
                    }
                });
                lv.setAdapter(new ArrayAdapter<>(view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, names));
            }
        });
        final String[] value_to_add = new String[1];

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.setMessage("Company Name: " + details.get(i).get(4) + "\n" + "Description: "
                        + details.get(i).get(0) + "\n" + "Google Drive Link: "
                        + details.get(i).get(1) + "\n" + "Estimated Cost: " +details.get(i).get(2));
                value_to_add[0] = details.get(i).get(3);
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        dialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                value_to_add[0] = gstin[0] + "-" + value_to_add[0];
                al.add(value_to_add[0]);
                HashMap<String,Object> map = new HashMap<>();
                map.put("products",al);
                firebaseDatabase.getReference().child("Favourite Products").child(pan).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(view.getContext(), "Product added to Favourites", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Failed to Add", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.setNegativeButton("LinkedIn", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(linkedIn[0])));
            }
        });

        return view;
    }
}