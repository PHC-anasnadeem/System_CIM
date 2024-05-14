package com.phc.cim.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.phc.cim.DownloadClases.DownloadDaySumActivity;
import com.phc.cim.DataElements.ActionType;
import com.phc.cim.DataElements.subActionType;
import com.phc.cim.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class IndReportingAdapter extends BaseAdapter {
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

    public IndReportingAdapter(Context context, ArrayList<HashMap<String, String>> arrayList, String Hcename, String email, String password, String username, String isEdit , String index) {
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
        TextView totalvisits;
        TextView firtext;
        TextView totalpic;
        TextView Usernametext;
        String isFIRSubmit;




        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.ind_reportinglist_items, null);
        }
        hceNo = (TextView) convertView.findViewById(R.id.hce_no);
        planidtext = (TextView) convertView.findViewById(R.id.planidtext);
        districttext = (TextView) convertView.findViewById(R.id.districttext);
        startdate = (TextView) convertView.findViewById(R.id.startdate);
        enddate = (TextView) convertView.findViewById(R.id.enddate);
        teame = (TextView) convertView.findViewById(R.id.teame);
        totalvisits = (TextView) convertView.findViewById(R.id.totalvisits);
        firtext= (TextView) convertView.findViewById(R.id.firtext);
        totalpic= (TextView) convertView.findViewById(R.id.totalpic);
      //  Usernametext= (TextView) convertView.findViewById(R.id.Usernametext);



        hceNo.setText(arrayList.get(position).get("index"));
        planidtext.setText(arrayList.get(position).get("PlanCode"));
        districttext.setText("District:  " +arrayList.get(position).get("District"));
        String ackwardDate = arrayList.get(position).get("PlanStartDate");
        String ackwardRipOff = ackwardDate.replace("/Date(", "").replace("+0500", "").replace(")/", "");
        Long timeInMillis = Long.valueOf(ackwardRipOff);
        Date d= new Date(timeInMillis);
        startdate.setText("Start Date:  " +new SimpleDateFormat("dd-MMM-yyyy").format(d).toString());

        String ackwardDate2 = arrayList.get(position).get("PlanEndDate");
        String ackwardRipOff2 = ackwardDate2.replace("/Date(", "").replace("+0500", "").replace(")/", "");
        Long timeInMillis2 = Long.valueOf(ackwardRipOff2);
        Date d2= new Date(timeInMillis2);
        enddate.setText("End Date:  " +new SimpleDateFormat("dd-MMM-yyyy").format(d2).toString());

        teame.setText("Team:  " +arrayList.get(position).get("Team"));
        totalvisits.setText("Total Visits:  " +arrayList.get(position).get("TotalVisits"));
        firtext.setText("Total FIR:  " +arrayList.get(position).get("TotalFIR"));
        totalpic.setText("Total Images Uploaded:  " +arrayList.get(position).get("TotalImages"));


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ackwardDate = arrayList.get(position).get("PlanStartDate");
                String ackwardRipOff = ackwardDate.replace("/Date(", "").replace("+0500", "").replace(")/", "");
                Long timeInMillis = Long.valueOf(ackwardRipOff);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timeInMillis);
                Date d= new Date(timeInMillis);
                strtdatetext=new SimpleDateFormat("dd-MMM-yyyy").format(calendar.getTime()).toString();

                String ackwardDate2 = arrayList.get(position).get("PlanEndDate");
                String ackwardRipOff2 = ackwardDate2.replace("/Date(", "").replace("+0500", "").replace(")/", "");
                Long timeInMillis2 = Long.valueOf(ackwardRipOff2);
                calendar.setTimeInMillis(timeInMillis2);
                Date d2= new Date(timeInMillis2);
                enddatetext=new SimpleDateFormat("dd-MMM-yyyy").format(calendar.getTime()).toString();
                String reportlevel="Field";
               // DownloadVisitsActivity downloadVisitsActivity=new DownloadVisitsActivity(context,arrayList.get(position).get("PKID"),email,password,username,isEdit,arrayList.get(position).get("index"),arrayList.get(position).get("Team"),arrayList.get(position).get("TotalVisits"),arrayList.get(position).get("TotalFIR"),strtdatetext,enddatetext,arrayList.get(position).get("District"),arrayList.get(position).get("PlanCode"));
                DownloadDaySumActivity downloadDaySumActivity=new DownloadDaySumActivity(context,arrayList.get(position).get("PKID"),email,password,username,isEdit,arrayList.get(position).get("index"),arrayList.get(position).get("Team"),arrayList.get(position).get("TotalVisits"),arrayList.get(position).get("TotalFIR"),strtdatetext,enddatetext,arrayList.get(position).get("District"),arrayList.get(position).get("PlanCode"),timeInMillis,timeInMillis2,arrayList.get(position).get("TotalImages"),reportlevel);

            }
        });
        return convertView;
    }
}