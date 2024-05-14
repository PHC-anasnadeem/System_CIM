package com.phc.cim.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.phc.cim.DataElements.ActionType;
import com.phc.cim.DataElements.subActionType;
import com.phc.cim.DownloadClases.DownloadDaySumActivity;
import com.phc.cim.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class HigherPlanSummAdapter extends BaseAdapter {
    Context context;
    String email;
    ArrayList<HashMap<String, String>> arrayList;
    String password;
    String isEdit;
    String username;
    String Hcename;
    String index;
    String strtdatetext;
    String enddatetext,PlanTitle;
    String actiontseltext,  actionTypeID, subactionTypeID ,subactiontseltext;
    ArrayList<ActionType> actionTypeselected = null;
    ArrayList<subActionType> subactionTypeselected=null;
    int count = 0;

    public HigherPlanSummAdapter(Context context, ArrayList<HashMap<String, String>> arrayList, String Hcename, String email, String password, String username, String isEdit, String PlanTitle) {
        this.arrayList = arrayList;
        this.context = context;
        this.email = email;
        this.password = password;
        this.username = username;
        this.isEdit = isEdit;
        this.Hcename=Hcename;
        this.PlanTitle=PlanTitle;


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
            convertView = inflater.inflate(R.layout.aqc_plansumm_items, null);
        }

        startdate = (TextView) convertView.findViewById(R.id.startdate);
        enddate = (TextView) convertView.findViewById(R.id.enddate);
        teame = (TextView) convertView.findViewById(R.id.teame);
      //  Usernametext= (TextView) convertView.findViewById(R.id.Usernametext);



        startdate.setText(arrayList.get(position).get("TeamNo")+" - "+ arrayList.get(position).get("Team") + " - " +arrayList.get(position).get("District"));
        teame.setText(arrayList.get(position).get("TotalVisits")+" Visits - " +arrayList.get(position).get("TotalFIR")+" FIR Registered - "+ arrayList.get(position).get("TotalImages") +" Images uploaded");
        enddate.setText(arrayList.get(position).get("FunctionalSealedTotal")+" Functonal sealed - "+arrayList.get(position).get("ClosedSealedTotal")+" Close sealed - "+arrayList.get(position).get("NotSealedTotal")+" Sealing not required");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stdate= arrayList.get(position).get("PlanStartDate");
                String endate= arrayList.get(position).get("PlanEndDate");
                Long timeInMillis=null;
                Long timeInMillis2=null;
                if(!stdate.equals("null")) {
                    String ackwardDate = arrayList.get(position).get("PlanStartDate");
                    String ackwardRipOff = ackwardDate.replace("/Date(", "").replace("+0500", "").replace(")/", "");
                     timeInMillis = Long.valueOf(ackwardRipOff);
                    Date d = new Date(timeInMillis);
                    strtdatetext = new SimpleDateFormat("dd-MMM-yyyy").format(d).toString();
                }
                if(!endate.equals("null")) {
                    String ackwardDate2 = arrayList.get(position).get("PlanEndDate");
                    String ackwardRipOff2 = ackwardDate2.replace("/Date(", "").replace("+0500", "").replace(")/", "");
                     timeInMillis2 = Long.valueOf(ackwardRipOff2);
                    Date d2 = new Date(timeInMillis2);
                    enddatetext = new SimpleDateFormat("dd-MMM-yyyy").format(d2).toString();
                }
                String plancode=PlanTitle+" "+arrayList.get(position).get("TeamNo");
                String reportlevel="Managment";
                // DownloadVisitsActivity downloadVisitsActivity=new DownloadVisitsActivity(context,arrayList.get(position).get("PKID"),email,password,username,isEdit,arrayList.get(position).get("index"),arrayList.get(position).get("Team"),arrayList.get(position).get("TotalVisits"),arrayList.get(position).get("TotalFIR"),strtdatetext,enddatetext,arrayList.get(position).get("District"),arrayList.get(position).get("PlanCode"));
                DownloadDaySumActivity downloadDaySumActivity=new DownloadDaySumActivity(context,arrayList.get(position).get("PlanID"),email,password,username,isEdit,arrayList.get(position).get("index"),arrayList.get(position).get("Team"),arrayList.get(position).get("TotalVisits"),arrayList.get(position).get("TotalFIR"),strtdatetext,enddatetext,arrayList.get(position).get("District"),plancode,timeInMillis,timeInMillis2,arrayList.get(position).get("TotalImages"),reportlevel);

            }
        });
        return convertView;
    }
}