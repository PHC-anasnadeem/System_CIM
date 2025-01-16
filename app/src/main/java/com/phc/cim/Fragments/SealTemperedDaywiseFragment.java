package com.phc.cim.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.phc.cim.Adapters.Datewise_SummAdapter;
import com.phc.cim.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SealTemperedDaywiseFragment extends Fragment {
    ListView listView;
    ArrayList<HashMap<String, String>> indtabresult;
    ArrayList<HashMap<String, String>> sealTemperedlist;
    int count=1;
    String Vistdate;
    ProgressDialog pDialog;
    String email,password,username,isEdit,PlanTitle;
    TextView totaltext;
    int Total=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_visit_on_complaint_daywise, container, false);

        pDialog = new ProgressDialog(getActivity());
        totaltext = (TextView) rootView.findViewById(R.id.totaltext);
        listView = (ListView) rootView.findViewById(R.id.list);
        final Bundle args = getArguments();
        indtabresult= (ArrayList<HashMap<String, String>>) args.getSerializable("indtabresult");
        email= args.getString("email");
        password= args.getString("password");
        username= args.getString("username");
        isEdit= args.getString("isEdit");
        Vistdate=args.getString("Vistdate");
        PlanTitle=args.getString("PlanTitle");
        sealTemperedlist=new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < indtabresult.size(); i++) {
            String pkid = indtabresult.get(i).get("PKID");
            String visitDate = indtabresult.get(i).get("VisitDate");

            // Check if pkid and visitDate are not null before calling equals()
            if (pkid != null && visitDate != null && pkid.equals("12") && visitDate.equals(Vistdate)) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("index", String.valueOf(count));
                map.put("CategoryDesc", indtabresult.get(i).get("CategoryDesc"));
                map.put("ID", indtabresult.get(i).get("ID"));
                map.put("PKID", pkid);
                map.put("TotalSealed", indtabresult.get(i).get("TotalSealed"));
                map.put("TypeDesc", indtabresult.get(i).get("TypeDesc"));
                map.put("VisitDate", visitDate);
                Total = Total + Integer.parseInt(indtabresult.get(i).get("TotalSealed"));
                sealTemperedlist.add(map);
                count++;
            }
        }


        totaltext.setText("Seal -Tempered and Re-sealed: " +Total);
        Total=0;
        Datewise_SummAdapter datewise_summAdapter =new Datewise_SummAdapter(getContext(),sealTemperedlist,email,password,username,isEdit,PlanTitle);
        listView.setAdapter(datewise_summAdapter);
        datewise_summAdapter.notifyDataSetChanged();

        return rootView;
    }
}