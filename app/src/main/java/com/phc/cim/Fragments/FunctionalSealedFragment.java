package com.phc.cim.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.phc.cim.Adapters.FunctionalSealedAdapter;
import com.phc.cim.R;

import java.util.ArrayList;
import java.util.HashMap;



public class FunctionalSealedFragment extends Fragment {

    String jsonStr = null;
    ListView listView;
    ArrayList<HashMap<String, String>> indtabresult;
    ArrayList<HashMap<String, String>> functionsealedlist;
    String FunctionalSealedID;
    String email,password,username,isEdit;
    ProgressDialog pDialog;
    int count=1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.visit_summarylist, container, false);

        pDialog = new ProgressDialog(getActivity());

        listView = (ListView) rootView.findViewById(R.id.list);
        final Bundle args = getArguments();
        indtabresult= (ArrayList<HashMap<String, String>>) args.getSerializable("indtabresult");
        FunctionalSealedID= args.getString("FunctionalSealedID");
        email= args.getString("email");
        password= args.getString("password");
        username= args.getString("username");
        isEdit= args.getString("isEdit");
        functionsealedlist=new ArrayList<HashMap<String, String>>();
        if(indtabresult!=null) {
            for (int i = 0; i < indtabresult.size(); i++) {
                if (indtabresult.get(i).get("ActionType").equals(FunctionalSealedID)) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("index", String.valueOf(count));
                    map.put("Action", indtabresult.get(i).get("Action"));
                    map.put("ActionType", indtabresult.get(i).get("ActionType"));
                    map.put("Address", indtabresult.get(i).get("Address"));
                    map.put("Comments", indtabresult.get(i).get("Comments"));
                    map.put("Date_Time", indtabresult.get(i).get("Date_Time"));
                    map.put("FinalID", indtabresult.get(i).get("FinalID"));
                    map.put("Name", indtabresult.get(i).get("Name"));
                    map.put("SubAction", indtabresult.get(i).get("SubAction"));
                    map.put("TotalImages", indtabresult.get(i).get("TotalImages"));
                    map.put("UserName", indtabresult.get(i).get("UserName"));
                    map.put("VisitedDate", indtabresult.get(i).get("VisitedDate"));
                    map.put("district", indtabresult.get(i).get("district"));
                    map.put("isFIRSubmit", indtabresult.get(i).get("isFIRSubmit"));
                    map.put("sector_type", indtabresult.get(i).get("sector_type"));
                    functionsealedlist.add(map);
                    count++;
                }
            }
        }
        FunctionalSealedAdapter functionalSealedAdapter =new FunctionalSealedAdapter(getContext(),functionsealedlist,email,password,username,isEdit);
        listView.setAdapter(functionalSealedAdapter);
        functionalSealedAdapter.notifyDataSetChanged();

        return rootView;
    }

}
