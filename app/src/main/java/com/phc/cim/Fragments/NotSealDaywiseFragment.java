package com.phc.cim.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.phc.cim.Adapters.Datewise_SummAdapter;
import com.phc.cim.R;

import java.util.ArrayList;
import java.util.HashMap;


public class NotSealDaywiseFragment extends Fragment {
    String jsonStr = null;
    ListView listView;
    ArrayList<HashMap<String, String>> indtabresult;
    ArrayList<HashMap<String, String>> notsealedlist;
    int count=1;
    String Vistdate;
    ProgressDialog pDialog;
    String email,password,username,isEdit,PlanTitle;
    TextView totaltext;
    int Total=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.datewise_summlist, container, false);

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
        notsealedlist=new ArrayList<HashMap<String, String>>();
        for (int i=0;i<indtabresult.size();i++){

            if(indtabresult.get(i).get("PKID").equals("2") && indtabresult.get(i).get("VisitDate").equals(Vistdate)) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("index", String.valueOf(count));
                map.put("CategoryDesc", indtabresult.get(i).get("CategoryDesc"));
                map.put("ID",indtabresult.get(i).get("ID"));
                map.put("PKID", indtabresult.get(i).get("PKID"));
                map.put("TotalSealed", indtabresult.get(i).get("TotalSealed"));
                map.put("TypeDesc", indtabresult.get(i).get("TypeDesc"));
                map.put("VisitDate", indtabresult.get(i).get("VisitDate"));
                Total= Total + Integer.parseInt(indtabresult.get(i).get("TotalSealed"));
                notsealedlist.add(map);
                count++;
            }
        }
        totaltext.setText("Sealing Not Required: " +Total);
        Total=0;
        Datewise_SummAdapter datewise_summAdapter =new Datewise_SummAdapter(getContext(),notsealedlist,email,password,username,isEdit,PlanTitle);
        listView.setAdapter(datewise_summAdapter);
        datewise_summAdapter.notifyDataSetChanged();
        return rootView;
    }

}
