package com.phc.cim.Extra;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.phc.cim.DataElements.UpdateStatL1;
import com.phc.cim.DataElements.UpdateStatL2;
import com.phc.cim.Managers.DataManager;
import com.phc.cim.R;

import java.util.ArrayList;


public class ActionFragment extends Fragment {



    int count=0;
    String jsonStr;
    String actionTypeText;
    String actionTypeID;
    String subactionTypeText;
    String subactionTypeID;
    DataManager dataManager;
    Spinner actionType_spinner;
    Spinner subactionType_spinner;
    ArrayList<UpdateStatL1> actionTypeList = null;
    ArrayList<UpdateStatL2> subactionType = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View obj_view = inflater.inflate(R.layout.fragment_action, container, false);

        actionType_spinner = (Spinner) obj_view.findViewById(R.id.actionType_spinner);
        subactionType_spinner = (Spinner) obj_view.findViewById(R.id.subactionType_spinner);

        // districts= dataManager.getDistrictList();

        ArrayAdapter<String> actiontype_spinneradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getactionTypes()) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return true;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                if (position == 0) {
                    // Set the hint text color gray
                    //tv.setTextColor(Color.GRAY);
                } else {
                    // tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };



        actiontype_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionType_spinner.setAdapter(actiontype_spinneradapter);
        if (actionTypeText != null) {
            int spinnerPosition = actiontype_spinneradapter.getPosition(actionTypeText);
            actionType_spinner.setSelection(spinnerPosition);
        }
        actionType_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                actionTypeText = parent.getItemAtPosition(position).toString();
                actionTypeID= actionTypeList.get(position).getUpdatedStatL1_Id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


return  obj_view;
    }

/*    private ArrayList<String> getsectorTypes() {

        ArrayList<String> sectorTypeList = new ArrayList<String>();
        int i=0;
        for (SectorType sectorType : sectorTypes) {
            String sectortypelists= sectorType.getSectorType();
            sectorTypeList.add(sectortypelists);
            i++;
        }
        return sectorTypeList;
    }*/


    private ArrayList<String> getactionTypes() {

        ArrayList<String> actiontypelist = new ArrayList<String>();
        int i=0;
        for (UpdateStatL1 updateStatL1 : actionTypeList) {
            String counciltypelists= updateStatL1.getUpdatedStatL1();
            actiontypelist.add(counciltypelists);
            i++;
        }
        return actiontypelist;
    }
    private ArrayList<String> getsubactionTypes() {
       // subactionType= dataManager.getTehsilList(districtText);
        ArrayList<String> subactiontypelist = new ArrayList<String>();
        int i=0;
        for (UpdateStatL2 updateStatL2 : subactionType) {
            String subactiontype= updateStatL2.getUpdatedStatL2();
            subactiontypelist.add(subactiontype);
            i++;
        }
        ArrayAdapter<String> subactiontype_spinneradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, subactiontypelist) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return true;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                if (position == 0) {
                    // Set the hint text color gray
                    //tv.setTextColor(Color.GRAY);
                } else {
                    // tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        subactiontype_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subactionType_spinner.setAdapter(subactiontype_spinneradapter);
        if (subactionTypeText != null) {
            int spinnerPosition = subactiontype_spinneradapter.getPosition(subactionTypeText);
            subactionType_spinner.setSelection(spinnerPosition);
        }
        subactionType_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                subactionTypeText = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return subactiontypelist;
    }
}
