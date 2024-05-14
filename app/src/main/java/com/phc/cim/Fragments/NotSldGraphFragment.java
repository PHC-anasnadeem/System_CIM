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
import java.util.HashMap;
import java.util.List;


public class NotSldGraphFragment extends Fragment {
    PieChart pieChart;
    int count=10;
    String email,password,username,isEdit,Sealing_Not_Required;
    ArrayList<HashMap<String, String>> notsellist;
    TextView text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dashboard, container, false);

        pieChart = (PieChart) rootView.findViewById(R.id.chart);
        text= (TextView) rootView.findViewById(R.id.text);

        final Bundle args = getArguments();
        notsellist= (ArrayList<HashMap<String, String>>) args.getSerializable("notsellist");
        email= args.getString("email");
        password= args.getString("password");
        username= args.getString("username");
        isEdit= args.getString("isEdit");
        Sealing_Not_Required = args.getString("Sealing_Not_Required");
        // array of images
        text.setText("Sealing Not Required ("+Sealing_Not_Required+")");
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
  /*      for (int i=0;i<funclist.size();i++) {
            // colors.add(ContextCompat.getColor(this, R.color.colorGreen));
            // colors.add(ContextCompat.getColor(this, R.color.colorAccent));
            colors.add(ContextCompat.getColor(getContext(), Integer.parseInt("#fff")));
            colors.add(ContextCompat.getColor(getContext(), R.color.colorAccent));
            colors.add(ContextCompat.getColor(getContext(), R.color.deeppurple));
        }*/
        // Now adding array of data to the entery set
        int c=ContextCompat.getColor(getContext(), R.color.green);
        for (int i=0;i<notsellist.size();i++)
        {

            if(i==0){
                colors.add(c);
            }
            else {
                c = getLighterShadeColor(c);
                colors.add(c);
            }
        }
        List<Entry> entries = new ArrayList<>();
        //  entries.add(new Entry(8f, 0));
        for (int i=0;i<notsellist.size();i++) {
            entries.add(new Entry(Integer.parseInt(notsellist.get(i).get("Total")),i));
        }

        //  entries.add(new Entry(23f, 4));
        // entries.add(new Entry(17f, 5));


        PieDataSet set = new PieDataSet(entries, "");
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i=0;i<notsellist.size();i++) {
            xVals.add(notsellist.get(i).get("TypeDesc")+" ("+notsellist.get(i).get("Total")+")");
        }
        //xVals.add("May");
        //xVals.add("June");

        PieData data = new PieData(xVals,set);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
        pieChart.setData(data);
        pieChart.setDrawSliceText(false);
        //set.setColors(ColorTemplate.COLORFUL_COLORS);
        // colors according to the dataset
        set.setColors(colors);
        set.setValueTextSize(15f);
        set.setValueTextColor(ContextCompat.getColor(getContext(), R.color.backgroundcolor));
        set.setSliceSpace(1f);
        // adding legends to the desigred positions
        Legend l = pieChart.getLegend();
        l.setTextSize(12f);
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
        SpannableString s = new SpannableString("TOTAL\n "+Sealing_Not_Required);
        s.setSpan(new RelativeSizeSpan(2f), 11, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.BOLD), 11, s.length(), 0);
        return s;
    }
    public int getDarkerShadeColor(int c){
        float[] hsv = new float[3];
        int color = c;
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.80f;
        color = Color.HSVToColor(hsv);
        return color;
    }
    public int getLighterShadeColor(int c){
        float[] hsv = new float[3];
        int color = c;
        for(int i=0;i<3; i++){
            Color.colorToHSV(color, hsv);
            //  hsv[2] *= 1.35f;
            hsv[2] = 1.0f - 0.8f * (1.0f - hsv[2]);
            //hsv[2] = 0.2f + 0.8f * hsv[2];
            color = Color.HSVToColor(hsv);
        }
        return color;
    }
}
