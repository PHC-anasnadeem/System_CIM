package com.phc.cim.Adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.phc.cim.Activities.Licensing.PWSDetailViewActivity;
import com.phc.cim.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PWSListAdapter extends ArrayAdapter {
    Context context;
    String email;
    ArrayList<HashMap<String, String>> arrayList;
    String password;
    String isEdit;
    String username;
    int count = 0;

    public PWSListAdapter(@NonNull Context context, @NonNull ArrayList<HashMap<String, String>> arrayList) {
        super(context, R.layout.pws_list_items, arrayList);
        this.arrayList = arrayList;
        this.context = context;
    }
/*
    public PWSListAdapter(Context context, ArrayList<HashMap<String, String>> arrayList) {


        this.arrayList = arrayList;
        this.context = context;

    }*/

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
        TextView hcename;
        TextView counciltext;
        TextView bedtext;
        TextView hcetypetext;
        TextView Status;
        TextView sectorType;
        TextView address;
        TextView mobile,hcspname;
        ImageView image_view;
        ImageView mobile_image;
        Button action_image;
        Button detail_button;
        final String VisitStatus;
        String ActionType;


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pws_list_items, null);
        }
        hceNo = (TextView) convertView.findViewById(R.id.hce_no);
        hcename = (TextView) convertView.findViewById(R.id.hce_name);
        mobile = (TextView) convertView.findViewById(R.id.calltext);
        hcetypetext = (TextView) convertView.findViewById(R.id.hcetypetext);
        //Status = (TextView) convertView.findViewById(R.id.regstatustext);
        address = (TextView) convertView.findViewById(R.id.addresstext);
      //  image_view = (ImageView) convertView.findViewById(R.id.addressicon);
        //action_image = (Button) convertView.findViewById(R.id.actionicon);
        detail_button = (Button) convertView.findViewById(R.id.detail_button);
        mobile_image = (ImageView) convertView.findViewById(R.id.callicon);
        hcspname = (TextView) convertView.findViewById(R.id.hcspname);

        hceNo.setText(arrayList.get(position).get("index"));
        mobile.setText(arrayList.get(position).get("Mobile"));
        //hcspname.setText(arrayList.get(position).get("SPName")+" - "+arrayList.get(position).get("hcsp_sodowo"));
        hcspname.setText(arrayList.get(position).get("SPName"));
        if(!arrayList.get(position).get("HCEName").equals("null")){
            hcename.setText(arrayList.get(position).get("HCEName") + " (" + arrayList.get(position).get("SectorType") + ")");
        }
        else {
            hcename.setText(arrayList.get(position).get("SectorType") );
        }

        address.setText(arrayList.get(position).get("Address"));
        hcetypetext.setText(arrayList.get(position).get("HCEType") + " (" + arrayList.get(position).get("bedStrength") + " beds)" + " - " + arrayList.get(position).get("CouncilName"));





       mobile_image.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {

               if (arrayList.get(position).get("Mobile") != null) {

                   Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + arrayList.get(position).get("Mobile")));
                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   context.startActivity(intent);
               }
           }
       });
       detail_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


        Intent firstpage = new Intent(context, PWSDetailViewActivity.class);
                firstpage.putExtra("index",arrayList.get(position).get("index"));
                firstpage.putExtra("Address",arrayList.get(position).get("Address"));
                firstpage.putExtra("CNIC",arrayList.get(position).get("CNIC"));
                firstpage.putExtra("CouncilName",arrayList.get(position).get("CouncilName"));
                firstpage.putExtra("CouncilNo",arrayList.get(position).get("CouncilNo"));
                firstpage.putExtra("District",arrayList.get(position).get("District"));
                firstpage.putExtra("FeeReceived",arrayList.get(position).get("FeeReceived"));
                firstpage.putExtra("HCEName",arrayList.get(position).get("HCEName"));
                firstpage.putExtra("HCEType",arrayList.get(position).get("HCEType"));
                firstpage.putExtra("Hcp_recID",arrayList.get(position).get("Hcp_recID"));
                firstpage.putExtra("Mobile",arrayList.get(position).get("Mobile"));
                firstpage.putExtra("PHCDeRegDate",arrayList.get(position).get("PHCDeRegDate"));
                firstpage.putExtra("PHCDeRegNo",arrayList.get(position).get("PHCDeRegNo"));
                firstpage.putExtra("PHCPLDate",arrayList.get(position).get("PHCPLDate"));
                firstpage.putExtra("PHCPLNo",arrayList.get(position).get("PHCPLNo"));
                firstpage.putExtra("PHCRLDate",arrayList.get(position).get("PHCRLDate"));
                firstpage.putExtra("PHCRLNo",arrayList.get(position).get("PHCRLNo"));
                firstpage.putExtra("PHCRegDate",arrayList.get(position).get("PHCRegDate"));
                firstpage.putExtra("PHCRegNo",arrayList.get(position).get("PHCRegNo"));
                firstpage.putExtra("Reason",arrayList.get(position).get("Reason"));
                firstpage.putExtra("RegistrationCertificateIssued",arrayList.get(position).get("RegistrationCertificateIssued"));
                firstpage.putExtra("SPName",arrayList.get(position).get("SPName"));
                firstpage.putExtra("SPType",arrayList.get(position).get("SPType"));
                firstpage.putExtra("SectorType",arrayList.get(position).get("SectorType"));
                firstpage.putExtra("bedStrength",arrayList.get(position).get("bedStrength"));
                firstpage.putExtra("TotalRec",arrayList.get(position).get("TotalRec"));
                firstpage.putExtra("PageSize",arrayList.get(position).get("PageSize"));
                context.startActivity(firstpage);



            }
        });

        return convertView;
    }
}