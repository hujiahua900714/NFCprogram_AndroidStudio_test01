package com.example.hcetest001;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

public class ChartActivity extends AppCompatActivity {

    //Button Bar declaration
    Button homeBtn, payBtn, chartBtn;
    PieChart pieChart;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        //Button Bar definition
        homeBtn = (Button) findViewById(R.id.homeBtn);
        payBtn = (Button) findViewById(R.id.payBtn);
        chartBtn = (Button) findViewById(R.id.chartBtn);

        pieChart = (PieChart) findViewById(R.id.pieChart);
        barChart = (BarChart) findViewById(R.id.barChart);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChartActivity.this, PayActivity.class);
                startActivity(intent);
            }
        });

        setData();
    }

    private void setData()
    {

        // Set the percentage of language used
        PieModel pie = new PieModel();
        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "R",
                        Integer.parseInt("40"),
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Python",
                        Integer.parseInt("20"),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "C++",
                        Integer.parseInt("20"),
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "Java",
                        Integer.parseInt("20"),
                        Color.parseColor("#29B6F6")));

        // To animate the pie chart
        pieChart.startAnimation();

        barChart.addBar(
                new BarModel(
                        "R",
                        Integer.parseInt("40"),
                        Color.parseColor("#FFA726")));
        barChart.addBar(
                new BarModel(
                        "Python",
                        Integer.parseInt("20"),
                        Color.parseColor("#66BB6A")));
        barChart.addBar(
                new BarModel(
                        "C++",
                        Integer.parseInt("20"),
                        Color.parseColor("#EF5350")));
        barChart.addBar(
                new BarModel(
                        "Java",
                        Integer.parseInt("20"),
                        Color.parseColor("#29B6F6")));

        // To animate the pie chart
        barChart.startAnimation();
    }
}