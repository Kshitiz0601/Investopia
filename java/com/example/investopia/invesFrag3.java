package com.example.investopia;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class invesFrag3 extends Fragment {
    String pan;
    public invesFrag3(String pnnnn){
        pan = pnnnn;
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_inves_frag3, container, false);

        Button logout = view.findViewById(R.id.logOut);
        Button feedback = view.findViewById(R.id.Feedback);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), homepage.class));
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),feedback.class);
                i.putExtra("id",pan);
                startActivity(i);
            }
        });

        return view;
    }
}