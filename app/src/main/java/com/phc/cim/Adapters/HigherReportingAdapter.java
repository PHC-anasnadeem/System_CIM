package com.phc.cim.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.phc.cim.DownloadClases.DownloadWeeklySummActivity;
import com.phc.cim.DataElements.ActionType;
import com.phc.cim.DataElements.subActionType;
import com.phc.cim.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HigherReportingAdapter extends BaseAdapter {
    Context context;
    String email;
    ArrayList<HashMap<String, String>> arrayList;
    String password;
    String isEdit;
    String username;
    String Hcename;
    String index;
    String strtdatetext;
    String enddatetext;
    String actiontseltext,  actionTypeID, subactionTypeID ,subactiontseltext;
    ArrayList<ActionType> actionTypeselected = null;
    ArrayList<subActionType> subactionTypeselected=null;
    int count = 0;

    public HigherReportingAdapter(Context context, ArrayList<HashMap<String, String>> arrayList, String Hcename, String email, String password, String username, String isEdit , String index) {
        this.arrayList = arrayList;
        this.context = context;
        this.email = email;
        this.password = password;
        this.username = username;
        this.isEdit = isEdit;
        this.Hcename=Hcename;
        this.index=index;

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
        TextView planidtext;
        TextView districttext;
        TextView startdate;
        TextView enddate;
        TextView teame;
        TextView Usernametext;
        String isFIRSubmit;




        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.aqc_statslist_items, null);
        }
        hceNo = (TextView) convertView.findViewById(R.id.hce_no);
        planidtext = (TextView) convertView.findViewById(R.id.planidtext);
        districttext = (TextView) convertView.findViewById(R.id.districttext);
        startdate = (TextView) convertView.findViewById(R.id.startdate);
        enddate = (TextView) convertView.findViewById(R.id.enddate);
        teame = (TextView) convertView.findViewById(R.id.teame);
      //  Usernametext= (TextView) convertView.findViewById(R.id.Usernametext);



        hceNo.setText(arrayList.get(position).get("index"));
        planidtext.setText(arrayList.get(position).get("PlanTitle"));
        startdate.setText(arrayList.get(position).get("TeamCount")+" teams consisting of "+ arrayList.get(position).get("TeamMembersCount") + " members");
        districttext.setText(arrayList.get(position).get("DistrictCount") + " District included:" +arrayList.get(position).get("Districts") );
        teame.setText(arrayList.get(position).get("TotalVisits")+" Visits - " +arrayList.get(position).get("TotalFIRSubmit")+" FIR Registered - "+ arrayList.get(position).get("Totalimages") +" Images uploaded");
        enddate.setText(arrayList.get(position).get("FunctionalSealedTotal")+" Functonal sealed - "+arrayList.get(position).get("ClosedSealedTotal")+" Close sealed - "+arrayList.get(position).get("NotSealedTotal")+" Sealing not required");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DownloadWeeklySummActivity downloadWeeklySummActivity=new DownloadWeeklySummActivity(context,email,password,username,isEdit,arrayList.get(position).get("PlanTitle"),arrayList.get(position).get("PlanStartDate"),arrayList.get(position).get("PlanEndDate"),arrayList.get(position).get("FunctionalSealedTotal"),arrayList.get(position).get("ClosedSealedTotal"),arrayList.get(position).get("NotSealedTotal"));


            }
        });
        return convertView;
    }
}