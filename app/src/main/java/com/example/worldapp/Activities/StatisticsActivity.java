package com.example.worldapp.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.worldapp.BaseClasses.BaseAppCompat;
import com.example.worldapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StatisticsActivity extends BaseAppCompat {

    PieChart mTourChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics2);
        initializeViews();
        super.SetToolbarTitle("Statistics");
        setupTourChart();
    }

    private void setupTourChart()
    {
        mTourChart.setUsePercentValues(true);
        mTourChart.getDescription().setEnabled(false);
        mTourChart.setExtraOffsets(5,10,5,5);
        mTourChart.setDragDecelerationFrictionCoef(0.95f);
        mTourChart.setDrawHoleEnabled(true);
        mTourChart.setHoleColor(Color.WHITE);
        mTourChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(34f, "Europe"));
        yValues.add(new PieEntry(23f, "USA"));
        yValues.add(new PieEntry(20f, "China"));
        yValues.add(new PieEntry(13f, "Bangalore"));

        PieDataSet dataSet = new PieDataSet(yValues, "Countries");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.YELLOW);

        mTourChart.setData(pieData);
        mTourChart.setCenterText("Top tours");
    }

    private void initializeViews()
    {
        mTourChart = findViewById(R.id.chart1);
    }
}
