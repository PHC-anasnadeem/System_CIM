package com.phc.cim.Adapters;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.phc.cim.DataElements.ActionType;
import com.phc.cim.DataElements.subActionType;
import com.phc.cim.DownloadClases.DownloadSealingsubActivity;
import com.phc.cim.R;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class Datewise_SummAdapter extends BaseAdapter {
    Context context;
    String email;
    ArrayList<HashMap<String, String>> arrayList;
    String password;
    String isEdit;
    String username;
    String Hcename;
    String RoleID;
    String strtdatetext;
    String enddatetext;
    String actiontseltext,  actionTypeID, subactionTypeID ,subactiontseltext,PlanTitle;
    ArrayList<ActionType> actionTypeselected = null;
    ArrayList<subActionType> subactionTypeselected=null;
    int count = 1;

    public Datewise_SummAdapter(Context context, ArrayList<HashMap<String, String>> arrayList , String email, String password, String username, String isEdit, String PlanTitle) {
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
        TextView hcenametext;
        TextView quacktypetext;
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
            convertView = inflater.inflate(R.layout.datwise_sum_listitem, null);
        }
       // hceNo = (TextView) convertView.findViewById(R.id.hce_no);
        quacktypetext = (TextView) convertView.findViewById(R.id.quacktypetext);



           // hceNo.setText(arrayList.get(position).get("index"));
        quacktypetext.setText(arrayList.get(position).get("CategoryDesc")+":  " + arrayList.get(position).get("TotalSealed"));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = context.getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                String isStat = prefs.getString("isStat", null);//"No name defined" is the default value.
                if (isStat.equals("true")) {
                    DownloadSealingsubActivity downloadSealingsubActivity=new DownloadSealingsubActivity(context,PlanTitle,arrayList.get(position).get("ID"),arrayList.get(position).get("PKID"),arrayList.get(position).get("CategoryDesc"),email,password,username,isEdit);
                }
               }
        });
        return convertView;
    }
}