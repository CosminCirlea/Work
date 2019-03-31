package com.example.worldapp.Fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.worldapp.Adapters.MyToursListingsAdapter;
import com.example.worldapp.Models.GuidedToursModel;
import com.example.worldapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentHome extends Fragment {
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<GuidedToursModel> list;
    MyToursListingsAdapter mTourAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = rootView.findViewById(R.id.rv_listed_homes);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));

        reference = FirebaseDatabase.getInstance().getReference().child("Tours");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    GuidedToursModel p = dataSnapshot1.getValue(GuidedToursModel.class);
                    list.add(p);
                }
                mTourAdapter = new MyToursListingsAdapter(list);
                recyclerView.setAdapter(mTourAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}
