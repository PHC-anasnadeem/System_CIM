package com.phc.cim.Adapters;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.phc.cim.DownloadClases.DownloadHCEDetail;
import com.phc.cim.Activities.Common.RouteMapsActivity;
import com.phc.cim.R;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class HCEListAdapter extends BaseAdapter {
    Context context;
    String email;
    ArrayList<HashMap<String, String>> arrayList;
    String password;
    String isEdit;
    String username;
    int count = 0;

    public HCEListAdapter(Context context, ArrayList<HashMap<String, String>> arrayList, String email, String password, String username, String isEdit) {
        this.arrayList = arrayList;
        this.context = context;
        this.email = email;
        this.password = password;
        this.username = username;
        this.isEdit = isEdit;


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
            convertView = inflater.inflate(R.layout.activity_hcelist_items, null);
        }
        hceNo = (TextView) convertView.findViewById(R.id.hce_no);
        hcename = (TextView) convertView.findViewById(R.id.hce_name);
        mobile = (TextView) convertView.findViewById(R.id.calltext);
        hcetypetext = (TextView) convertView.findViewById(R.id.hcetypetext);
        Status = (TextView) convertView.findViewById(R.id.regstatustext);
        address = (TextView) convertView.findViewById(R.id.addresstext);
        image_view = (ImageView) convertView.findViewById(R.id.addressicon);
        action_image = (Button) convertView.findViewById(R.id.actionicon);
        detail_button = (Button) convertView.findViewById(R.id.detail_button);
        mobile_image = (ImageView) convertView.findViewById(R.id.callicon);
        hcspname = (TextView) convertView.findViewById(R.id.hcspname);

        hceNo.setText(arrayList.get(position).get("index"));
        mobile.setText(arrayList.get(position).get("MobileNumber"));
        hcspname.setText(arrayList.get(position).get("hcsp_name")+" - "+arrayList.get(position).get("hcsp_sodowo"));
        hcename.setText(arrayList.get(position).get("name") + " (" + arrayList.get(position).get("sector_type") + ")");
        address.setText(arrayList.get(position).get("address"));
        VisitStatus = arrayList.get(position).get("VisitStatus");
        ActionType = arrayList.get(position).get("ActionType");
        // Status.setText(arrayList.get(position).get("RegType"));

/*       if (arrayList.get(position).get("status").equals("Reg/PL/RL") || arrayList.get(position).get("status").equals("Reg/PL")){
           hcetypetext.setText(arrayList.get(position).get("HCE_Cat_Type") + " (" + arrayList.get(position).get("total_beds") + " beds)" + " - " + arrayList.get(position).get("councils"));
           Status.setText(arrayList.get(position).get("RegType"));
           image_view.setImageResource(R.drawable.marker_green);
       }
       else if (arrayList.get(position).get("status").equals("Reg")){
           hcetypetext.setText(arrayList.get(position).get("HCE_Cat_Type") + " (" + arrayList.get(position).get("total_beds") + " beds)" + " - " + arrayList.get(position).get("councils"));
           Status.setText(arrayList.get(position).get("RegType"));
           image_view.setImageResource(R.drawable.marker_yellow);
       }
       else if(arrayList.get(position).get("is_reg_with_phc").equals("0") && (arrayList.get(position).get("is_reg_council").equals("1") || arrayList.get(position).get("is_reg_council").equals("2"))){
           hcetypetext.setText(arrayList.get(position).get("HCE_Cat_Type") + " (" + arrayList.get(position).get("total_beds") + " beds)" + " - " + arrayList.get(position).get("councils"));
           Status.setText(arrayList.get(position).get("RegType"));
           image_view.setImageResource(R.drawable.marker_blue);

       }
       else if( arrayList.get(position).get("is_reg_council").equals("0")){
           hcetypetext.setText(arrayList.get(position).get("HCE_Cat_Type") + " (" + arrayList.get(position).get("total_beds") + " beds)");
           Status.setText(arrayList.get(position).get("RegType")+" (Not registered with any federal council)");
           image_view.setImageResource(R.drawable.marker_red);
       }*/

        if (arrayList.get(position).get("RegType").equals("Provisional License") || arrayList.get(position).get("RegType").equals("Regular License")) {
            if (VisitStatus.equals("1") && (ActionType.equals("1") || ActionType.equals("3"))) {
                image_view.setImageResource(R.drawable.marker_sealed_green);
            } else if (VisitStatus.equals("1") && ActionType.equals("2")) {
                image_view.setImageResource(R.drawable.marker_visited_green);
            } else {
                image_view.setImageResource(R.drawable.marker_green);
            }
            hcetypetext.setText(arrayList.get(position).get("HCE_Cat_Type") + " (" + arrayList.get(position).get("total_beds") + " beds)" + " - " + arrayList.get(position).get("councils"));
            Status.setText(arrayList.get(position).get("RegType"));
        } else if (arrayList.get(position).get("RegType").equals("Registered")) {
            if (VisitStatus.equals("1") && (ActionType.equals("1") || ActionType.equals("3"))) {
                image_view.setImageResource(R.drawable.marker_sealed_yellow);
            } else if (VisitStatus.equals("1") && ActionType.equals("2")) {
                image_view.setImageResource(R.drawable.marker_visited_yellow);
            } else {
                image_view.setImageResource(R.drawable.marker_yellow);
            }
            hcetypetext.setText(arrayList.get(position).get("HCE_Cat_Type") + " (" + arrayList.get(position).get("total_beds") + " beds)" + " - " + arrayList.get(position).get("councils"));
            Status.setText(arrayList.get(position).get("RegType"));
        } else if (arrayList.get(position).get("RegType").equals("Not Registered with PHC")) {
            if (VisitStatus.equals("1") && (ActionType.equals("1") || ActionType.equals("3"))) {
                image_view.setImageResource(R.drawable.marker_sealed_blue);
            } else if (VisitStatus.equals("1") && ActionType.equals("2")) {
                image_view.setImageResource(R.drawable.marker_visited_blue);
            } else {
                image_view.setImageResource(R.drawable.marker_blue);
            }

            hcetypetext.setText(arrayList.get(position).get("HCE_Cat_Type") + " (" + arrayList.get(position).get("total_beds") + " beds)" + " - " + arrayList.get(position).get("councils"));
            Status.setText(arrayList.get(position).get("RegType"));

        } else if (arrayList.get(position).get("RegType").equals("Quack")) {
            if (VisitStatus.equals("1") && (ActionType.equals("1") || ActionType.equals("3"))) {
                image_view.setImageResource(R.drawable.marker_sealed_red);
            } else if (VisitStatus.equals("1") && ActionType.equals("2")) {
                image_view.setImageResource(R.drawable.marker_visited_red);
            } else {
                image_view.setImageResource(R.drawable.marker_red);
            }
            hcetypetext.setText(arrayList.get(position).get("HCE_Cat_Type") + " (" + arrayList.get(position).get("total_beds") + " beds)"+" - "+arrayList.get(position).get("QuackCategory"));
            Status.setText(arrayList.get(position).get("RegType") + " (Not registered with any federal council)");

        }
//        else if (arrayList.get(position).get("RegType").equals("Quack") || ("RegType").equals("Not Registered with PHC")) {
//                image_view.setImageResource(R.drawable.purple_icon);
//
//            hcetypetext.setText(arrayList.get(position).get("HCE_Cat_Type") + " (" + arrayList.get(position).get("total_beds") + " beds)"+" - "+arrayList.get(position).get("QuackCategory"));
//            Status.setText(arrayList.get(position).get("RegType"));
//        }

       image_view.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {

               if(Double.parseDouble(arrayList.get(position).get("latitude"))!=0 && Double.parseDouble(arrayList.get(position).get("longitude"))!=0){


                   Intent firstpage = new Intent(context, RouteMapsActivity.class);
                   firstpage.putExtra("des_latitude", Double.parseDouble(arrayList.get(position).get("latitude")));
                   firstpage.putExtra("des_longitude", Double.parseDouble(arrayList.get(position).get("longitude")));
                   firstpage.putExtra("RegType", arrayList.get(position).get("RegType"));
                   firstpage.putExtra("name", arrayList.get(position).get("name"));
                   firstpage.putExtra("address", arrayList.get(position).get("address"));
                   firstpage.putExtra("mobile_number", arrayList.get(position).get("MobileNumber"));
                   firstpage.putExtra("email", email);
                   firstpage.putExtra("Password", password);
                   firstpage.putExtra("username", username);
                   firstpage.putExtra("isEdit", isEdit);
                   firstpage.putExtra("sectortype", arrayList.get(position).get("sector_type"));
                   firstpage.putExtra("council", arrayList.get(position).get("councils"));
                   firstpage.putExtra("HCEType", arrayList.get(position).get("HCE_Cat_Type"));
                   firstpage.putExtra("Beds", arrayList.get(position).get("total_beds"));
                   firstpage.putExtra("HCSPname", arrayList.get(position).get("hcsp_name"));
                   firstpage.putExtra("HCSPSO",arrayList.get(position).get("hcsp_sodowo"));
                   context.startActivity(firstpage);
               }
           }
       });
       action_image.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               SharedPreferences prefs = context.getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
               String roleid = prefs.getString("RoleID", null);//"No name defined" is the default value.

               String index=arrayList.get(position).get("index").toString();
               if(VisitStatus.equals("1") && arrayList.get(position).get("RoleID").equals(roleid)) {

                   AlertDialog.Builder builder = new AlertDialog.Builder(context);
                   builder.setMessage("Action for this record is already recorded:")
                           .setCancelable(true)
                           .setPositiveButton("Update Action", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                                   // launch new intent instead of loading fragment
                                   String index=arrayList.get(position).get("index").toString();
                                   count=1;
                                   DownloadHCEDetail downloadHceDetail = new DownloadHCEDetail(context,arrayList.get(position).get("name"), arrayList.get(position).get("final_id"), email, password, username, isEdit, index, count,arrayList.get(position).get("RegType"));

                               }
                           })
                           .setNegativeButton("Upload Image", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                                   String index=arrayList.get(position).get("index").toString();
                                   count=2;

                                   DownloadHCEDetail downloadHceDetail =new DownloadHCEDetail(context,arrayList.get(position).get("name"),arrayList.get(position).get("final_id"),email,password,username,isEdit,index,count,arrayList.get(position).get("RegType"));

                               }
                           });
                   AlertDialog alert = builder.create();
                   alert.show();
                        }
                       /* else if(VisitStatus.equals("1") && !arrayList.get(position).get("RoleID").equals(roleid)){
                   AlertDialog alertDialog = new AlertDialog.Builder(
                           context).create();

                   // Setting Dialog Title
                  // alertDialog.setTitle("Success");

                   // Setting Dialog Message
                   alertDialog.setMessage("Action for this record is already recorded by another team");
                   // Setting Icon to Dialog
                   //  alertDialog.setIcon(R.drawable.tick);

                   // Setting OK Button
                   alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {

                           // Write your code here to execute after dialog closed
                           //    Toast.makeText(getContext(), "Thanks You!", Toast.LENGTH_SHORT).show();
                       }
                   });

                   // Showing Alert Message
                   alertDialog.show();
               }*/
               else {
                   count=3;

                   DownloadHCEDetail downloadHceDetail = new DownloadHCEDetail(context,arrayList.get(position).get("name"), arrayList.get(position).get("final_id"), email, password, username, isEdit, index, count,arrayList.get(position).get("RegType"));
               }
           /*    Intent firstpage = new Intent(context, ActionActivity.class);
               firstpage.putExtra("des_latitude", Double.parseDouble(arrayList.get(position).get("latitude")));
               firstpage.putExtra("des_longitude", Double.parseDouble(arrayList.get(position).get("longitude")));
               firstpage.putExtra("RegType", arrayList.get(position).get("RegType"));
               firstpage.putExtra("name", arrayList.get(position).get("name"));
               firstpage.putExtra("address", arrayList.get(position).get("address"));
               firstpage.putExtra("mobile", arrayList.get(position).get("MobileNumber"));
               firstpage.putExtra("Password", password);
               firstpage.putExtra("username", username);
               firstpage.putExtra("isEdit", isEdit);
               context.startActivity(firstpage);*/
           }
       });
       mobile_image.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {

               if (arrayList.get(position).get("MobileNumber") != null) {

                   Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + arrayList.get(position).get("MobileNumber")));
                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   context.startActivity(intent);
               }
           }
       });
       detail_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String index=arrayList.get(position).get("index").toString();
                count=2;

                DownloadHCEDetail downloadHceDetail =new DownloadHCEDetail(context,arrayList.get(position).get("name"),arrayList.get(position).get("final_id"),email,password,username,isEdit,index,count,arrayList.get(position).get("RegType"));



            }
        });

        return convertView;
    }
}