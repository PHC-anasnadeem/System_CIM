package com.phc.cim.Adapters;


import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.phc.cim.Fragments.NotRegClusterListFragment;
import com.phc.cim.R;

import java.util.ArrayList;
import java.util.HashMap;

public class NotRegClusterAdapter extends BaseAdapter {
    Context context;
    String email;
    ArrayList<HashMap<String, String>> arrayList;
    String password;
    String isEdit;
    String username;
    ViewPager viewPager;
    NotRegClusterListFragment.OnFragmentInteractionListener mListener;
    public NotRegClusterAdapter(Context context, ArrayList<HashMap<String, String>> arrayList, String email, String password, String username, String isEdit, ViewPager viewPager, NotRegClusterListFragment.OnFragmentInteractionListener mListener) {
        this.arrayList = arrayList;
        this.context = context;
        this.email = email;
        this.password = password;
        this.username = username;
        this.isEdit = isEdit;
        this.viewPager= viewPager;
        this.mListener=mListener;

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


        TextView startdate;
        TextView enddate;
        TextView teame;


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.quack_cluster_listitems, null);
        }

        startdate = (TextView) convertView.findViewById(R.id.startdate);
        enddate = (TextView) convertView.findViewById(R.id.enddate);
        teame = (TextView) convertView.findViewById(R.id.teame);


        startdate.setText("Cluster No: "+arrayList.get(position).get("ClusterNo"));
        teame.setText("Total Not Registered: "+arrayList.get(position).get("total_unregistered")+"\n"+"Total Not Visited: "+arrayList.get(position).get("Not_visited")+"\n"+"Total Visited: " +arrayList.get(position).get("Total_Visited"));
        enddate.setText("\t\t\t\t"+arrayList.get(position).get("Registration_not_required")+" Registration not required \n"+"\t\t\t\t"+arrayList.get(position).get("Found_Ligitimate")+" Found Ligitimate \n"+"\t\t\t\t"+arrayList.get(position).get("Sealing_not_req")+" Sealing not required \n"+"\t\t\t\t"+arrayList.get(position).get("Other")+" Other");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
                mListener.onFragmentInteraction(arrayList.get(position).get("lat"), arrayList.get(position).get("lng"));
            }
        });
        return convertView;
    }
}