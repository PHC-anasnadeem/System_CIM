package com.phc.cim.Fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.phc.cim.R;

import java.util.ArrayList;
import java.util.List;


public class OverAllSldRslttFragment extends Fragment {
    PieChart pieChart;
    int count=10;
    String email,password,username,isEdit;
    String FIRs,Total,PKID;
    TextView text;
    float Functional_Sealed,Sealing_Not_Required,Closed_Sealed;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dashboard, container, false);

        pieChart = (PieChart) rootView.findViewById(R.id.chart);
        text= (TextView) rootView.findViewById(R.id.text);

        final Bundle args = getArguments();
        PKID = args.getString("PKID");
        Closed_Sealed = Float.parseFloat(args.getString("Closed_Sealed"));
        FIRs = args.getString("FIRs");
        Functional_Sealed = Float.parseFloat(args.getString("Functional_Sealed"));
        Sealing_Not_Required = Float.parseFloat(args.getString("Sealing_Not_Required"));
        Total = args.getString("Total");
        email = args.getString("email");
        password = args.getString("password");
        username = args.getString("username");
        isEdit = args.getString("isEdit");
        text.setText("Overall Visits Summary ("+Total+")");
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
        colors.add(ContextCompat.getColor(getContext(), R.color.red));
        colors.add(ContextCompat.getColor(getContext(), R.color.green));
        colors.add(ContextCompat.getColor(getContext(), R.color.lightblue));


        // Now adding array of data to the entery set

        List<Entry> entries = new ArrayList<>();
        //  entries.add(new Entry(8f, 0));
        entries.add(new Entry(Functional_Sealed, 1));
        entries.add(new Entry(Sealing_Not_Required, 2));
        entries.add(new Entry(Closed_Sealed, 3));

        //  entries.add(new Entry(23f, 4));
        // entries.add(new Entry(17f, 5));


        PieDataSet set = new PieDataSet(entries, "");
        ArrayList<String> xVals = new ArrayList<String>();

        String Func_Sealed= String.valueOf(Functional_Sealed);
        String not_Sealed= String.valueOf(Sealing_Not_Required);
        String close_Sealed= String.valueOf(Closed_Sealed);
        Func_Sealed= Func_Sealed.replace(".0", "");
        not_Sealed= not_Sealed.replace(".0", "");
        close_Sealed= close_Sealed.replace(".0", "");
        xVals.add("Functional Sealed (" +Func_Sealed+")" );
        xVals.add("Sealing Not Required ("+not_Sealed+")");
        xVals.add("Close Sealed ("+close_Sealed+")");
        //xVals.add("May");
        //xVals.add("June");

        PieData data = new PieData(xVals,set);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
        pieChart.setData(data);
        pieChart.setDrawSliceText(false);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(255);
        set.setSliceSpace(2f);
        // colors according to the dataset
        set.setColors(colors);
        set.setValueTextSize(15f);
        set.setValueTextColor(ContextCompat.getColor(getContext(), R.color.backgroundcolor));

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


        return rootView;
    }

    // If we need to display center text with textStyle
    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("TOTAL\n "+Total);
        s.setSpan(new RelativeSizeSpan(2f), 11, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), 11, s.length(), 0);
        return s;
    }

}
