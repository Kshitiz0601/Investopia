package com.example.investopia;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirstFragment extends Fragment {
    ListView languageLV;
    ArrayList<String> lngList;
    DatabaseReference databaseReference;
    public FirstFragment(DatabaseReference reference){
        databaseReference = reference;
        // require a empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_first, container, false);


        ArrayList<ArrayList<String>> details = new ArrayList<>();

        AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
        dialog.setTitle("Investor Details");

        EditText editText = view.findViewById(R.id.search);
        ListView lv = view.findViewById(R.id.investorsList);
        Button btn = view.findViewById(R.id.search_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> names = new ArrayList<>();

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String st = "SomeValue\n";
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            for(DataSnapshot snapshot2 : snapshot1.child("Interests").getChildren()){
                                if(snapshot2.getValue(String.class).equals(editText.getText().toString())){
                                    names.add(snapshot1.child("Name").getValue(String.class));
                                    ArrayList<String> det = new ArrayList<>(2);
                                    det.add(snapshot1.child("Name").getValue(String.class));
                                    det.add(snapshot1.child("Email").getValue(String.class));
                                    details.add(det);
                                        break;
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

        final String[] emailval = new String[1];

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.setMessage("Name: " + details.get(i).get(0) + "\n" + "Email: " + details.get(i).get(1));
                AlertDialog alertDialog = dialog.create();
                emailval[0] = details.get(i).get(1);
                alertDialog.show();
            }
        });

        dialog.setPositiveButton("Email", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              Intent intent = new Intent(view.getContext(),email.class);
              intent.putExtra("email", emailval[0]);
              startActivity(intent);
            }
        });

        return view;
    }
}