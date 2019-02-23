package com.example.worldapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.worldapp.Adapters.MyHomesListingsAdapter;
import com.example.worldapp.Models.HomeDetailsModel;
import com.example.worldapp.R;

import java.util.ArrayList;

public class ListingsActivity extends AppCompatActivity {

    ArrayList<HomeDetailsModel> homesHomeDetailsModelsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings);
        RecyclerView rvListedHomesAdaptor = (RecyclerView) findViewById(R.id.rv_listed_homes);
        homesHomeDetailsModelsList = HomeDetailsModel.createHomeList(7);
        MyHomesListingsAdapter adapter = new MyHomesListingsAdapter(homesHomeDetailsModelsList);
        rvListedHomesAdaptor.setAdapter(adapter);
        rvListedHomesAdaptor.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
