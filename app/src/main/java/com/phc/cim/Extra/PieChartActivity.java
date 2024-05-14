package com.phc.cim.Extra;


import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.phc.cim.DataElements.PieEntry;
import com.phc.cim.R;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends AppCompatActivity {


    PieChart pieChart;
    PieChart pieChart1;
    PieChart pieChart2;
    PieChart pieChart3;
    int count=10;

    ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        pieChart = (PieChart) findViewById(R.id.chart);
        pieChart1 = (PieChart) findViewById(R.id.chart1);
        pieChart2 = (PieChart) findViewById(R.id.chart2);
        pieChart3 = (PieChart) findViewById(R.id.chart3);

        // array of images

        ArrayList<Drawable> array_image = new ArrayList<Drawable>();
    /*    array_image.add(ContextCompat.getDrawable(this, R.drawable.creditcard));
        array_image.add(ContextCompat.getDrawable(this, R.drawable.dollar));
        array_image.add(ContextCompat.getDrawable(this, R.drawable.favourite));
        array_image.add(ContextCompat.getDrawable(this, R.drawable.plane));
        array_image.add(ContextCompat.getDrawable(this, R.drawable.others));*/
        // array of graph percentage value
        ArrayList<String> array_percent = new ArrayList<String>();
        array_percent.add(String.valueOf(15) + "%");
        array_percent.add(String.valueOf(10) + "%");
        array_percent.add(String.valueOf(25) + "%");
        array_percent.add(String.valueOf(20) + "%");
        array_percent.add(String.valueOf(30) + "%");

        // array of graph drawing size according to design
        ArrayList<Float> array_drawGraph = new ArrayList<Float>();
        array_drawGraph.add((float) 15);
        array_drawGraph.add((float) 10);
        array_drawGraph.add((float) 25);
        array_drawGraph.add((float) 20);
        array_drawGraph.add((float) 30);
        // array of graph different colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
       // colors.add(ContextCompat.getColor(this, R.color.colorGreen));
       // colors.add(ContextCompat.getColor(this, R.color.colorAccent));
        colors.add(ContextCompat.getColor(this, R.color.yellow));
        colors.add(ContextCompat.getColor(this, R.color.colorAccent));
        colors.add(ContextCompat.getColor(this, R.color.deeppurple));


        // Now adding array of data to the entery set

        List<Entry> entries = new ArrayList<>();
      //  entries.add(new Entry(8f, 0));
        entries.add(new Entry(3000f, 1));
        entries.add(new Entry(3000f, 2));
        entries.add(new Entry(3000f, 3));

      //  entries.add(new Entry(23f, 4));
       // entries.add(new Entry(17f, 5));


      PieDataSet set = new PieDataSet(entries, "");
        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("Functional Sealed");
        xVals.add("Sealing Not Required");
        xVals.add("Close Sealed");
        //xVals.add("May");
        //xVals.add("June");

        PieData data = new PieData(xVals,set);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
        pieChart.setData(data);
        pieChart.setDrawSliceText(false);
        // colors according to the dataset
        set.setColors(colors);
        // adding legends to the desigred positions
        Legend l = pieChart.getLegend();
        l.setTextSize(14f);
        l.setTextColor(Color.BLACK);
        l.setEnabled(true);

        l.setWordWrapEnabled(true);
        // calling method to set center text
        pieChart.setCenterText(generateCenterSpannableText());
        // if no need to add description
        pieChart.setDescription("");
        pieChart.animateY(5000);
        pieChart.setCenterTextColor(Color.BLACK);
    }

// If we need to display center text with textStyle
        private SpannableString generateCenterSpannableText() {
            SpannableString s = new SpannableString("TOTAL\n 9000");
            s.setSpan(new RelativeSizeSpan(2f), 11, s.length(), 0);
            s.setSpan(new StyleSpan(Typeface.BOLD), 11, s.length(), 0);
            return s;
        }


}
