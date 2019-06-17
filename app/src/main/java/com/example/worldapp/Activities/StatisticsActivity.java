package com.example.worldapp.Activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.os.Bundle;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.Helpers.FirebaseHelper;
import com.example.worldapp.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class StatisticsActivity extends BaseAppCompat {

    PieChart mTourChart, mAccommodationChart, mParkingsChart;
    ArrayList<PieEntry> mToursValues  = new ArrayList<>();
    ArrayList<PieEntry> mAccommodationValues= new ArrayList<>();
    ArrayList<PieEntry> mParkingsValues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics2);
        initializeViews();
        super.SetToolbarTitle("Statistics");
        setupTourChart();
        setupParkingsChart();
        setupAccommodationChart();
    }

    @Override
    public void onResume(){
        super.onResume();
        mTourChart.animateY(1000, Easing.EaseInOutCubic);
        mAccommodationChart.animateY(1000, Easing.EaseInOutCubic);
        mParkingsChart.animateY(1000, Easing.EaseInOutCubic);
    }
    private void setupTourChart()
    {
        mTourChart.setUsePercentValues(true);
        mTourChart.getDescription().setEnabled(false);
        mTourChart.setExtraOffsets(5,10,5,5);
        mTourChart.setDragDecelerationFrictionCoef(0.99f);
        mTourChart.setDrawHoleEnabled(true);
        mTourChart.setHoleColor(Color.WHITE);
        mTourChart.setTransparentCircleRadius(61f);

        FirebaseHelper.mStatisticsReference.child("Tours").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren())
                {
                    PieEntry entry = new PieEntry(Float.valueOf(data.getValue().toString()), data.getKey());
                    mToursValues.add(entry);
                }
                setToursData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setToursData()
    {
        PieDataSet dataSet = new PieDataSet(mToursValues, "Countries");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.YELLOW);

        mTourChart.setData(pieData);
        mTourChart.setCenterText("Top tours");
        mTourChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
    }

    private void setupAccommodationChart()
    {
        mAccommodationChart.setUsePercentValues(true);
        mAccommodationChart.getDescription().setEnabled(false);
        mAccommodationChart.setExtraOffsets(5,10,5,5);
        mAccommodationChart.setDragDecelerationFrictionCoef(0.99f);
        mAccommodationChart.setDrawHoleEnabled(true);
        mAccommodationChart.setHoleColor(Color.WHITE);
        mAccommodationChart.setTransparentCircleRadius(61f);

        FirebaseHelper.mStatisticsReference.child("Accommodation").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren())
                {
                    PieEntry entry = new PieEntry(Float.valueOf(data.getValue().toString()), data.getKey());
                    mAccommodationValues.add(entry);
                }
                setAccommodationData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setAccommodationData()
    {
        PieDataSet dataSet = new PieDataSet(mAccommodationValues, "Countries");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.YELLOW);

        mAccommodationChart.setData(pieData);
        mAccommodationChart.setCenterText("Top accommodation");
        mAccommodationChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
    }

    private void setupParkingsChart()
    {
        mParkingsChart.setUsePercentValues(true);
        mParkingsChart.getDescription().setEnabled(false);
        mParkingsChart.setExtraOffsets(5,10,5,5);
        mParkingsChart.setDrawHoleEnabled(true);
        mParkingsChart.setHoleColor(Color.WHITE);
        mParkingsChart.setTransparentCircleRadius(61f);

        FirebaseHelper.mStatisticsReference.child("Parkings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren())
                {
                    PieEntry entry = new PieEntry(Float.valueOf(data.getValue().toString()), data.getKey());
                    mParkingsValues.add(entry);
                }
                setParkingsData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setParkingsData()
    {
        PieDataSet dataSet = new PieDataSet(mParkingsValues, "Countries");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.YELLOW);

        mParkingsChart.setData(pieData);
        mParkingsChart.setCenterText("Top parkings");
        mParkingsChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);
    }

    private void initializeViews()
    {
        mTourChart = findViewById(R.id.chart_tours);
        mAccommodationChart = findViewById(R.id.chart_accommodation);
        mParkingsChart = findViewById(R.id.chart_parkings);
    }
}
