package com.phc.cim.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.phc.cim.DownloadClases.DownloadHCEDetail;
import com.phc.cim.DataElements.ActionType;
import com.phc.cim.DataElements.subActionType;
import com.phc.cim.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ClosedSealedAdapter extends BaseAdapter {
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
    String actionid;
    int count = 1;

    public ClosedSealedAdapter(Context context, ArrayList<HashMap<String, String>> arrayList, String actionid,String email, String password, String username, String isEdit) {
        this.arrayList = arrayList;
        this.context = context;
        this.actionid=actionid;
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
        TextView hcenametext;
        TextView districttext;
        TextView Addresstext;
        TextView visitby;
        TextView visitdate;
        TextView transectiondate;
        TextView firtext;
        TextView totalpic;
        TextView actiontext;
        TextView subaction;
        TextView comnttext;
        String isFIRSubmit;
        String visitactionid;



        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.visit_sumlist_items, null);
        }
        hceNo = (TextView) convertView.findViewById(R.id.hce_no);
        hcenametext = (TextView) convertView.findViewById(R.id.hcenametext);
        districttext = (TextView) convertView.findViewById(R.id.districttext);
        Addresstext = (TextView) convertView.findViewById(R.id.Addresstext);
        visitby = (TextView) convertView.findViewById(R.id.visitby);
        visitdate = (TextView) convertView.findViewById(R.id.visitdate);
        transectiondate = (TextView) convertView.findViewById(R.id.transectiondate);
       // actiontext= (TextView) convertView.findViewById(R.id.actiontext);
        subaction= (TextView) convertView.findViewById(R.id.subaction);
        totalpic = (TextView) convertView.findViewById(R.id.totalpic);
        firtext= (TextView) convertView.findViewById(R.id.firtext);
        comnttext= (TextView) convertView.findViewById(R.id.comnttext);


            hceNo.setText(arrayList.get(position).get("index"));
            hcenametext.setText(arrayList.get(position).get("Name"));
            Addresstext.setText("Address: " + arrayList.get(position).get("Address"));
            comnttext.setText("Comments: " + arrayList.get(position).get("Comments"));
            districttext.setText("District:  " + arrayList.get(position).get("district"));

            String ackwardDate = arrayList.get(position).get("Date_Time");
            String ackwardRipOff = ackwardDate.replace("/Date(", "").replace("+0500", "").replace(")/", "");
            Long timeInMillis = Long.valueOf(ackwardRipOff);
            Date d = new Date(timeInMillis);
            visitdate.setText("Updated Date:  " + new SimpleDateFormat("dd-MMM-yyyy h:mm:ss").format(d).toString());

            String start_dt = arrayList.get(position).get("VisitedDate");
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date date = null;
            try {
                date = (Date) formatter.parse(start_dt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
        String finalString="";
        if(date!=null)
             finalString = newFormat.format(date);
            transectiondate.setText("Visit Date:  " + finalString);

            visitby.setText("Updated by:  " + arrayList.get(position).get("UserName"));
           // actiontext.setText("Action:  " + arrayList.get(position).get("Action"));
            subaction.setText("Quack Type:  " + arrayList.get(position).get("SubAction"));
            isFIRSubmit = arrayList.get(position).get("isFIRSubmit");
            if (isFIRSubmit.equals("1")) {
                firtext.setText("FIR Registered:  Yes");
            } else if (isFIRSubmit.equals("0")) {
                firtext.setText("FIR Registered:  No");
            }
            totalpic.setText("Total Images Uploaded:  " + arrayList.get(position).get("TotalImages"));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 2;
                DownloadHCEDetail downloadHceDetail = new DownloadHCEDetail(context, arrayList.get(position).get("Name"), arrayList.get(position).get("FinalID"), email, password, username, isEdit, arrayList.get(position).get("index"), count,"");
            }
        });

        return convertView;
    }
}