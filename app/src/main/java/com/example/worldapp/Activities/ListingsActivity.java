package com.example.worldapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.worldapp.Adapters.MyHomesListingsAdapter;
import com.example.worldapp.Models.HomeDetailsModel;
import com.example.worldapp.R;

import java.util.ArrayList;

public class ListingsActivity extends AppCompatActivity {

    ArrayList<HomeDetailsModel> homesHomeDetailsModelsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_listings);
        RecyclerView rvListedHomesAdaptor = findViewById(R.id.rv_listed_homes);
        rvListedHomesAdaptor.setHasFixedSize(true);
        rvListedHomesAdaptor.setLayoutManager(new LinearLayoutManager(this));
    }

    public void AddNewHome(View view) {
        startActivity(new Intent(this, AddHome1Activity.class));
    }
}
