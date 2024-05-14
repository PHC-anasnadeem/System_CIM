package com.phc.cim.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.phc.cim.R;

import java.util.ArrayList;
import java.util.HashMap;

public class InspectionVisitsAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>> arrayList;

    int count = 0;

    public InspectionVisitsAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
        this.arrayList = arrayList;
        this.context = context;

    }

    @Override
    public int getCount() {

        if (arrayList == null) {
            return 0;
        } else {
            return arrayList.size();
        }//return arrayList.length;
    }

    @Override
    public Object getItem(int position) {

        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        TextView hceNo;
        TextView hcenametext;
        TextView Regtext;
        TextView Visitbytext;
        TextView visisdatetext;
        TextView visittimetext;
        TextView Basictexttext;
        TextView loctext;
        TextView locVisitbytext;
        TextView locvisisdatetext;
        TextView locvisittimetext;



        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.insplist_items, null);
        }
        hceNo = (TextView) convertView.findViewById(R.id.hce_no);
        hcenametext = (TextView) convertView.findViewById(R.id.hcenametext);
        Regtext = (TextView) convertView.findViewById(R.id.Regtext);
        Visitbytext = (TextView) convertView.findViewById(R.id.Visitbytext);
        visisdatetext = (TextView) convertView.findViewById(R.id.visisdatetext);
        visittimetext = (TextView) convertView.findViewById(R.id.visittimetext);

        Basictexttext = (TextView) convertView.findViewById(R.id.Basictexttext);
        loctext = (TextView) convertView.findViewById(R.id.loctext);
        locVisitbytext = (TextView) convertView.findViewById(R.id.locVisitbytext);
        locvisisdatetext = (TextView) convertView.findViewById(R.id.locvisisdatetext);
        locvisittimetext = (TextView) convertView.findViewById(R.id.locvisittimetext);

        hceNo.setText(arrayList.get(position).get("index"));
        hcenametext.setText(arrayList.get(position).get("HCEName"));
        Regtext.setText("PHC Registration No:  "+arrayList.get(position).get("RegNum"));

        Basictexttext.setText(arrayList.get(position).get("BasicInfoChange"));
        Visitbytext.setText("Visited By:  "+arrayList.get(position).get("VisitedBy"));
        visittimetext.setText("Visit Time:  "+arrayList.get(position).get("VisitedTime"));
        visisdatetext.setText("Visit Date:  "+arrayList.get(position).get("VisitedDate"));

        loctext.setText(arrayList.get(position).get("LocChange"));
        locVisitbytext.setText("Visited By:  "+arrayList.get(position).get("LocVisitedBy"));
        locvisittimetext.setText("Visit Time:  "+arrayList.get(position).get("LocVisitedTime"));
        locvisisdatetext.setText("Visit Date:  "+arrayList.get(position).get("LocVisitedDate"));

        return convertView;
    }
}