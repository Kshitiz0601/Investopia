package com.example.investopia;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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


public class invesFrag2 extends Fragment {
    String pan;
    FirebaseDatabase db;
    public invesFrag2(String pann, FirebaseDatabase fdb){
        pan = pann;
        db = fdb;
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inves_frag2, container, false);

        ArrayList<ArrayList<String>> details = new ArrayList<>();
        ArrayList<String> pnames = new ArrayList<>();

        AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
        dialog.setTitle("Investor Details");

        TextView name = view.findViewById(R.id.investorName);
        ListView lv = view.findViewById(R.id.investorList);

        DatabaseReference compRef = db.getReference("Investor Details");
        Query checkUserDatabase = compRef.orderByKey().equalTo(pan);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot snap : snapshot.getChildren()){
                        name.setText(snap.child("Name").getValue(String.class));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference favProd = db.getReference("Favourite Products");
        Query checkDatabase = favProd.orderByKey().equalTo(pan);
        checkDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot snapshot1:snapshot.child(pan).child("products").getChildren()) {
                        String s = snapshot1.getValue(String.class);
                        String[] al = s.split("-");
                        String gstin = al[0];
                        String pid = al[1];
                        DatabaseReference prod = db.getReference("Products");
                        Query checkGSTIN = prod.orderByKey().equalTo(gstin);
                        checkGSTIN.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot psnapshot) {
                                if (psnapshot.exists()) {
                                        pnames.add(psnapshot.child(gstin).child(pid).child("Product Name").getValue(String.class));
                                        ArrayList<String> det = new ArrayList<>(4);
                                        det.add(psnapshot.child(gstin).child(pid).child("Product Name").getValue(String.class));
                                        det.add(psnapshot.child(gstin).child(pid).child("Description").getValue(String.class));
                                        det.add(psnapshot.child(gstin).child(pid).child("Estimated Cost").getValue(String.class));
                                        det.add(psnapshot.child(gstin).child(pid).child("GDrive Link").getValue(String.class));
                                        details.add(det);
                                }
                                lv.setAdapter(new ArrayAdapter<>(view.getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, pnames));
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.setMessage("Product Name: " + details.get(i).get(0) + "\n" + "Description: " + details.get(i).get(1) +"\n"+"Estimated Cost:"+details.get(i).get(2)+"\n"+"Google Drive Link:"+details.get(i).get(3));
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        return view;
    }
}