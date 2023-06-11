package com.example.investopia;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class SecondFragment extends Fragment {
    String gstin;
    FirebaseDatabase db;
    public SecondFragment(String GSTIN, FirebaseDatabase fdb){
        gstin = GSTIN;
        db = fdb;
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second, container, false);

        ArrayList<String> pnames = new ArrayList<>();

        TextView name = view.findViewById(R.id.companyName);
        TextView des = view.findViewById(R.id.companyDes);
        ListView lv = view.findViewById(R.id.productList);

        DatabaseReference compRef = db.getReference("Company Details");
        Query checkUserDatabase = compRef.orderByKey().equalTo(gstin);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot snap : snapshot.getChildren()){
                        name.setText(snap.child("Company Name").getValue(String.class));
                        des.setText(snap.child("Description").getValue(String.class));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference = db.getReference("Products");
        Query checkDatabase = reference.orderByKey().equalTo(gstin);
        checkDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if((snapshot.exists())){

                    for(DataSnapshot snap : snapshot.getChildren()){
                        for (DataSnapshot snap1 : snap.getChildren()){
                            //des.setText(snap1.child("Product Name").getValue(String.class));
                            pnames.add(snap1.child("Product Name").getValue(String.class));
                        }
                    }
                    lv.setAdapter(new ArrayAdapter<>(view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, pnames));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}