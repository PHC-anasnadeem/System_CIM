package com.phc.cim.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.phc.cim.DataElements.ActionType;
import com.phc.cim.DataElements.subActionType;
import com.phc.cim.Managers.DatabaseManager;
import com.phc.cim.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class ActionHistoryAdapter extends BaseAdapter {
    Context context;
    String email;
    ArrayList<HashMap<String, String>> arrayList;
    String password;
    String isEdit;
    String username;
    String Hcename;
    String index;
    String actiontseltext,  actionTypeID, subactionTypeID ,subactiontseltext;
    ArrayList<ActionType> actionTypeselected = null;
    ArrayList<subActionType> subactionTypeselected=null;
    int count = 0;

    public ActionHistoryAdapter(Context context, ArrayList<HashMap<String, String>> arrayList,String Hcename, String email, String password, String username, String isEdit , String index) {
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
        TextView updateddate;
        TextView visiteddate;
        TextView visitedby;
        TextView actiontype;
        TextView subaction;
        TextView firtext;
        TextView comtext;
        TextView counciltext,councilnotext,regnotext,visitedtime;
        String isFIRSubmit;



        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.action_historylist_items, null);
        }
        hceNo = (TextView) convertView.findViewById(R.id.hce_no);
        updateddate = (TextView) convertView.findViewById(R.id.updateddate);
        visiteddate = (TextView) convertView.findViewById(R.id.visiteddate);
        visitedtime = (TextView) convertView.findViewById(R.id.visitedtime);

        visitedby = (TextView) convertView.findViewById(R.id.visitedby);
        actiontype = (TextView) convertView.findViewById(R.id.actiontype);
        subaction = (TextView) convertView.findViewById(R.id.subaction);
        firtext= (TextView) convertView.findViewById(R.id.firtext);
        comtext= (TextView) convertView.findViewById(R.id.comtext);
        counciltext= (TextView) convertView.findViewById(R.id.counciltext);
        councilnotext= (TextView) convertView.findViewById(R.id.councilnotext);
        regnotext= (TextView) convertView.findViewById(R.id.regnotext);
        actionTypeID= arrayList.get(position).get("ActionType");
        subactionTypeID = arrayList.get(position).get("SubActionType");
        visitedtime.setText("Visit Time:  "+arrayList.get(position).get("VisitedTime"));
        counciltext.setText("Council Name:  "+arrayList.get(position).get("Council"));
        councilnotext.setText("Council No:  "+arrayList.get(position).get("CouncilNo"));
        regnotext.setText("PHC Registeration No:  "+arrayList.get(position).get("PHCRegistrationNo"));
        getactionsel();
        getsubactionsel();
        hceNo.setText(arrayList.get(position).get("index"));
        String ackwardDate = arrayList.get(position).get("Date_Time");
        Calendar calendar = Calendar.getInstance();
        String ackwardRipOff = ackwardDate.replace("/Date(", "").replace("+0500", "").replace(")/", "");
        Long timeInMillis = Long.valueOf(ackwardRipOff);
        Date d= new Date(timeInMillis);
       calendar.setTimeInMillis(timeInMillis);
        updateddate.setText(" Dated:  " +new SimpleDateFormat("dd-MMM-yyyy h:mm:ss").format(calendar.getTime()).toString());
        comtext.setText("Comments:  "+arrayList.get(position).get("Comments"));
        isFIRSubmit =arrayList.get(position).get("isFIRSubmit");
        if(isFIRSubmit.equals("1")) {
            firtext.setText("FIR submitted:  Yes");
        }
        else if(isFIRSubmit.equals("0")){
            firtext.setText("FIR submitted:  No");
        }


        String start_dt = arrayList.get(position).get("VisitedDate");
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        try {
            date = (Date)formatter.parse(start_dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
        String finalString = "";
        if(date!=null)
            finalString = newFormat.format(date);

        visiteddate.setText("Visited date:  "+finalString);
        visitedby.setText("Visited by:  "+arrayList.get(position).get("UserName"));
        actiontype.setText("Action Type:  "+actiontseltext);
        subaction.setText("Quack Type:  "+subactiontseltext);




        return convertView;
    }
    private String getactionsel() {
        DatabaseManager databaseManager = new DatabaseManager(context);
        actionTypeselected = databaseManager.getActionselected(actionTypeID);
        if(actionTypeselected.size()>0) {
            actiontseltext = actionTypeselected.get(0).getActionType();
        }
        return actiontseltext;
    }
    private String getsubactionsel() {
        DatabaseManager databaseManager = new DatabaseManager(context);
        subactionTypeselected = databaseManager.getsubActionselected(subactionTypeID);
        if(subactionTypeselected.size()>0) {
            subactiontseltext = subactionTypeselected.get(0).getSubactionType();
        }
        return subactiontseltext;
    }
}