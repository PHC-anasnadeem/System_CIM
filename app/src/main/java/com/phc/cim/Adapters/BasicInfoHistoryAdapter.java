package com.phc.cim.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.phc.cim.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class BasicInfoHistoryAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String, String>> arrayList;

    int count = 0;

    public BasicInfoHistoryAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {
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

        TextView updatedby;
        TextView hcename;
        TextView hceaddress;
        TextView hcetype;
        TextView sectortype;
        TextView sptype;
        TextView spname;
        TextView spso;
        TextView spcnic,spmobile,regno,counclname,counclno,comment,corloc,currloc,latitude,longitude,updateddate;


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.basicinfo_historylist_items, null);
        }
        updatedby = (TextView) convertView.findViewById(R.id.updatedby);
        updateddate = (TextView) convertView.findViewById(R.id.updateddate);
        hcename = (TextView) convertView.findViewById(R.id.hcename);
        hceaddress = (TextView) convertView.findViewById(R.id.hceaddress);
        hcetype = (TextView) convertView.findViewById(R.id.hcetype);
        sectortype = (TextView) convertView.findViewById(R.id.sectortype);
        sptype = (TextView) convertView.findViewById(R.id.sptype);
        spname = (TextView) convertView.findViewById(R.id.spname);
        spso= (TextView) convertView.findViewById(R.id.spso);
        spcnic= (TextView) convertView.findViewById(R.id.spcnic);
        spmobile= (TextView) convertView.findViewById(R.id.spmobile);
        regno= (TextView) convertView.findViewById(R.id.regno);
        counclname= (TextView) convertView.findViewById(R.id.counclname);
        counclno= (TextView) convertView.findViewById(R.id.counclno);
        comment= (TextView) convertView.findViewById(R.id.comment);
        corloc= (TextView) convertView.findViewById(R.id.corloc);
        currloc= (TextView) convertView.findViewById(R.id.currloc);
        latitude= (TextView) convertView.findViewById(R.id.latitude);
        longitude= (TextView) convertView.findViewById(R.id.longitude);






        String ackwardDate = arrayList.get(position).get("LastVisitedDate");
        if(!ackwardDate.equals("Null") || !ackwardDate.equals("null") || !ackwardDate.equals("")) {
            Calendar calendar = Calendar.getInstance();
            String ackwardRipOff = ackwardDate.replace("/Date(", "").replace("+0500", "").replace(")/", "");
            Long timeInMillis = Long.valueOf(ackwardRipOff);
            Date d = new Date(timeInMillis);
            calendar.setTimeInMillis(timeInMillis);
            updateddate.setText("Updated Dated:  " + new SimpleDateFormat("dd-MMM-yyyy h:mm:ss").format(calendar.getTime()).toString());
        }
        else {
            updateddate.setText("Updated Dated:  ");
        }
        updatedby.setText("Updated by:  "+arrayList.get(position).get("UserName"));
        hcename.setText("HCE Name:  "+arrayList.get(position).get("HCEName"));
        hceaddress.setText("  Address:  "+arrayList.get(position).get("HCEAddress"));
        hcetype.setText("  HCE Type:  "+arrayList.get(position).get("OrgType"));
        sectortype.setText("  Sector Type:  "+arrayList.get(position).get("SectorType"));
        spname.setText("Service Provider Name:  "+arrayList.get(position).get("HCSPName"));
        sptype.setText("  SP Designation:  "+arrayList.get(position).get("HCSPType"));
        spso.setText("  SP Father Name:  "+arrayList.get(position).get("HCSPSO"));
        spcnic.setText("  SP CNIC:  "+arrayList.get(position).get("HCSPCNIC"));
        spmobile.setText("  SP Contact No:  "+arrayList.get(position).get("HCSPContactNo"));
        regno.setText("PHC Registration No:  "+arrayList.get(position).get("RegNum"));
        counclname.setText("Council Name:  "+arrayList.get(position).get("CouncilName"));
        counclno.setText("Council No:  "+arrayList.get(position).get("CouncilNum"));
        comment.setText("Comments:  "+arrayList.get(position).get("RemarksByPHCSurveyor"));

        String corlocID=arrayList.get(position).get("CorrectLoc");
        if(corlocID.equals("1")){
            corloc.setText("Outlet location is correct:  Yes");
        }
        else {
            corloc.setText("Outlet location is correct:  No");
        }

        String currlocID=arrayList.get(position).get("CurrentLoc");
        if(currlocID.equals("1")){
            currloc.setText("Current location is outlet location:  Yes");
        }
        else {
            currloc.setText("Current location is outlet location:  No");
        }
        latitude.setText("latitude:  "+arrayList.get(position).get("lat"));
        longitude.setText("longitude:  "+arrayList.get(position).get("lng"));



        return convertView;
    }

}