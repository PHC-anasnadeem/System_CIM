package com.phc.cim.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.phc.cim.TabsActivities.EditTabActivity;
import com.phc.cim.Activities.Common.RouteMapsActivity;
import com.phc.cim.DataElements.CouncilType;
import com.phc.cim.DataElements.District;
import com.phc.cim.DataElements.OrgType;
import com.phc.cim.DataElements.SectorType;
import com.phc.cim.DataElements.UpdateStatL1;
import com.phc.cim.DataElements.UpdateStatL2;
import com.phc.cim.Managers.DataManager;
import com.phc.cim.Others.CurrentLocation;
import com.phc.cim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class PWSDetailFragment extends Fragment {

    double des_lat;
    double des_lng;
    DataManager dataManager;
    String MID;
    String MText;
    String jsonStr;
    String password;
    String isEdit;
    String username;
    CurrentLocation gps;
    EditText hce_nameEdit;
    EditText AddressEdit;
    EditText HCSP_nameEdit;
    EditText HCSP_SOEdit;
    EditText CNIC_Edit;
    EditText HCSP_ContactEdit;
    EditText Reg_NoEdit;
    EditText coun_NoEdit;

    TextInputLayout hce_namelayout;
    TextInputLayout Addresslayout;
    TextInputLayout HCSP_namelayout;
    TextInputLayout HCSP_SOlayout;
    TextInputLayout CNIC_layout;
    TextInputLayout HCSP_Contactlayout;
    TextInputLayout district_layout;
    TextInputLayout sectortypelayout;
    TextInputLayout hcetypelayout;
    TextInputLayout HCSP_layout;
    TextInputLayout regStatus_layout;
    TextInputLayout counStatus_layout;
    TextInputLayout regNoInput;
    TextInputLayout counTypeInput;
    TextInputLayout counNoInput;


    ArrayList<HashMap<String, String>> mylist;


    EditText district_spinner;
    EditText sectortypespinner;
    EditText hcetypespinner;
    EditText HCSP_spinner;
    EditText regStatus_spinner;
    EditText counStatus_spinner;
    EditText counciltypespiner;


    Spinner updatestatusspinner;
    Spinner substatusspinner;

    String hce_nameText="";
    String AddressText="";
    String HCSP_nameText="";
    String HCSP_SOText="";
    String CNIC_Text="";
    String HCSP_ContactText="";
    String Reg_NoText="";
    String coun_NoText="";
    String comnt="";
    String  final_id="";
    String RegType="";

    String districtText="";
    String sectortypetext="";
    String hceTypetext="";
    String HCSPTypeText="";
    String RegstatusText="";
    String counStatusText="";
    String counciltypetext="";
    String updatestatustext="";
    String substatustext="";
    String counStatusID="";
    String RegstatusID="";
    double latitude;
    double longitude;
    String email= "";
    double cur_latitude;
    double cur_longitude;
    ImageView imageView;
    TextView textView;

    ArrayList<District> districts;
    ArrayList<SectorType> sectorTypes;
    ArrayList<CouncilType> councilTypes;
    ArrayList<OrgType> orgTypes;
    ArrayList<UpdateStatL1> updateStatL1s;
    ArrayList<UpdateStatL2> updateStatL2s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View obj_view = inflater.inflate(R.layout.pws_detail_view, container,false);


        dataManager = new DataManager(getContext());
        gps = new CurrentLocation(getContext());
        hce_nameEdit = (EditText) obj_view.findViewById(R.id.hce_name);
        AddressEdit = (EditText) obj_view.findViewById(R.id.Address);
        HCSP_nameEdit = (EditText) obj_view.findViewById(R.id.HCSP_Name);
        HCSP_SOEdit = (EditText) obj_view.findViewById(R.id.HCSP_SO);
        CNIC_Edit = (EditText) obj_view.findViewById(R.id.CNIC);
        HCSP_ContactEdit = (EditText) obj_view.findViewById(R.id.Mobile);
        Reg_NoEdit = (EditText) obj_view.findViewById(R.id.reg_no);
        coun_NoEdit = (EditText) obj_view.findViewById(R.id.council_no);
        district_spinner = (EditText) obj_view.findViewById(R.id.district);
        sectortypespinner = (EditText) obj_view.findViewById(R.id.Sector_Type);
        hcetypespinner = (EditText) obj_view.findViewById(R.id.hcetype);
        HCSP_spinner = (EditText) obj_view.findViewById(R.id.HCSPType);
        regStatus_spinner = (EditText) obj_view.findViewById(R.id.registration);
        counStatus_spinner = (EditText) obj_view.findViewById(R.id.council_reg);
        counciltypespiner = (EditText) obj_view.findViewById(R.id.counType);
        //coments = (EditText) findViewById(R.id.comments);

        hce_nameEdit.setEnabled(false);
        AddressEdit.setEnabled(false);
        HCSP_nameEdit.setEnabled(false);
        HCSP_SOEdit.setEnabled(false);
        CNIC_Edit.setEnabled(false);
        HCSP_ContactEdit.setEnabled(false);
        Reg_NoEdit.setEnabled(false);
        coun_NoEdit.setEnabled(false);
         district_spinner.setEnabled(false);
         sectortypespinner.setEnabled(false);
         hcetypespinner.setEnabled(false);
         HCSP_spinner.setEnabled(false);
         regStatus_spinner.setEnabled(false);
         counStatus_spinner.setEnabled(false);
         counciltypespiner.setEnabled(false);

        // imageView =(ImageView) obj_view.findViewById(R.id.logo);
        textView= (TextView)  obj_view.findViewById(R.id.text_view);
        hce_namelayout = (TextInputLayout) obj_view.findViewById(R.id.hce_name_layout);
        Addresslayout = (TextInputLayout) obj_view.findViewById(R.id.Address_layout);
        HCSP_namelayout = (TextInputLayout) obj_view.findViewById(R.id.HCSP_Name_layout);
        HCSP_SOlayout = (TextInputLayout) obj_view.findViewById(R.id.HCSP_SO_layout);
        CNIC_layout = (TextInputLayout) obj_view.findViewById(R.id.CNIC_layout);
        HCSP_Contactlayout = (TextInputLayout) obj_view.findViewById(R.id.Mobile_layout);
        district_layout = (TextInputLayout) obj_view.findViewById(R.id.district_layout);
        sectortypelayout = (TextInputLayout) obj_view.findViewById(R.id.Sector_Type_layout);
        hcetypelayout = (TextInputLayout) obj_view.findViewById(R.id.hcetype_layout);
        HCSP_layout = (TextInputLayout) obj_view.findViewById(R.id.HCSPType_layout);
        regStatus_layout = (TextInputLayout) obj_view.findViewById(R.id.registration_layout);
        counStatus_layout = (TextInputLayout) obj_view.findViewById(R.id.council_reg_layout);
        regNoInput = (TextInputLayout) obj_view.findViewById(R.id.reg);
        counTypeInput= (TextInputLayout) obj_view.findViewById(R.id.counclType);
        counNoInput = (TextInputLayout) obj_view.findViewById(R.id.council);
        //coments.setEnabled(false);
/*

        district_spinner = (Spinner) obj_view.findViewById(R.id.district);
        sectortypespinner = (Spinner) obj_view.findViewById(R.id.Sector_Type);
        hcetypespinner = (Spinner) obj_view.findViewById(R.id.hcetypespinner);
        HCSP_spinner = (Spinner) obj_view.findViewById(R.id.HCSP_Typespinner);
        regStatus_spinner = (Spinner) obj_view.findViewById(R.id.registration_spinner);
        counStatus_spinner = (Spinner) obj_view.findViewById(R.id.council_spinner);
        counciltypespiner = (Spinner) obj_view.findViewById(R.id.counType_spinner);
*/
     //   BottomNavigationView navigation = (BottomNavigationView) obj_view.findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // updatestatusspinner = (Spinner) findViewById(R.id.updstatus_spinner);
        // substatusspinner = (Spinner) findViewById(R.id.substatus_spinner);
        //imageView.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        hce_namelayout.setVisibility(View.GONE);
        Addresslayout.setVisibility(View.GONE);
        HCSP_namelayout.setVisibility(View.GONE);
        HCSP_SOlayout.setVisibility(View.GONE);
        CNIC_layout.setVisibility(View.GONE);
        HCSP_Contactlayout.setVisibility(View.GONE);
        district_layout.setVisibility(View.GONE);
        sectortypelayout.setVisibility(View.GONE);
        hcetypelayout.setVisibility(View.GONE);
        HCSP_layout.setVisibility(View.GONE);
        regStatus_layout.setVisibility(View.GONE);
        counStatus_layout.setVisibility(View.GONE);
        regNoInput.setVisibility(View.GONE);
        counTypeInput.setVisibility(View.GONE);
        counNoInput.setVisibility(View.GONE);
        final Bundle args = getArguments();
        final_id = args.getString("final_id");
        email = args.getString("email");
        regNoInput.setVisibility(View.GONE);
        counTypeInput.setVisibility(View.GONE);
        counNoInput.setVisibility(View.GONE);

        String url = getDirectionsUrl();
        DownloadTask downloadTask = new DownloadTask();
        //Start downloading json data from Google Directions API
        downloadTask.execute(url);


       /* hce_nameEdit.setText(hce_nameText);
        AddressEdit.setText(AddressText);
        HCSP_nameEdit.setText(HCSP_nameText);
        HCSP_SOEdit.setText(HCSP_SOText);
        CNIC_Edit.setText(CNIC_Text);
        HCSP_ContactEdit.setText(HCSP_ContactText);
        Reg_NoEdit.setText(Reg_NoText);
        coun_NoEdit.setText(coun_NoText);*/


        // substatusInput.setVisibility(View.GONE);

        //-------------------------Current Location----------------------------
        if (gps.canGetLocation()) {
            cur_latitude = gps.getLatitude();
            cur_longitude = gps.getLongitude();
            // latlangListener.onlatlang(cur_latitude, cur_longitude);
            if (cur_latitude != 0.0 && cur_longitude != 0.0) {

            }
            else {
                cur_latitude = gps.getLatitude();
                cur_longitude = gps.getLongitude();


            }
        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            gps.showSettingsAlert();
        }

/*
        Button EditButton = (Button) findViewById(R.id.EditButton);
        EditButton.setOnClickListener(new Button.OnClickListener() {

                                          public void onClick(View v) {
                                              Intent firstpage = new Intent(context, CommentsActivity.class);
                                              firstpage.putExtra("HCEName", hce_nameText);
                                              firstpage.putExtra("HCEAddress", AddressText);
                                              firstpage.putExtra("District", districtText);
                                              firstpage.putExtra("SectorType", sectortypetext);
                                              firstpage.putExtra("OrgType", hceTypetext);
                                              firstpage.putExtra("HCSPType", HCSPTypeText);
                                              firstpage.putExtra("HCSPName", HCSP_nameText);
                                              firstpage.putExtra("HCSPSO", HCSP_SOText);
                                              firstpage.putExtra("HCSPCNIC", CNIC_Text);
                                              firstpage.putExtra("HCSPContactNo", HCSP_ContactText);
                                              firstpage.putExtra("RegStatus", RegstatusText);
                                              firstpage.putExtra("RegNum", Reg_NoText);
                                              firstpage.putExtra("CouncilStatus", counStatusText);
                                              firstpage.putExtra("CouncilName", counciltypetext);
                                              firstpage.putExtra("CouncilNum", coun_NoText);
                                              firstpage.putExtra("final_id", final_id);
                                              firstpage.putExtra("email", email);
                                              context.startActivity(firstpage);
                                          }
                                      });
        Button Draw_route = (Button) findViewById(R.id.Draw_route);
        Draw_route.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {

              *//*  Intent firstpage = new Intent(context, RouteMapsActivity.class);
                firstpage.putExtra("des_latitude", marker_latitude);
                firstpage.putExtra("des_longitude", marker_longitude);
                firstpage.putExtra("status", status);
                firstpage.putExtra("markerstatus", marker_status);
                firstpage.putExtra("name", marker_name);
                firstpage.putExtra("address", marker_address);
                firstpage.putExtra("mobile", mobile_number);
                context.startActivity(firstpage);*//*
            }
        });*/
        //---------------------------------District Spinner--------------------------------------------
      /*  districts= dataManager.getDistrictList();

        ArrayAdapter<String> district_spinneradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getDistrict()) {
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
        district_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        district_spinner.setAdapter(district_spinneradapter);
        if (districtText != null) {
            int spinnerPosition = district_spinneradapter.getPosition(districtText);
            district_spinner.setSelection(spinnerPosition);
        }
        district_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                districtText = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---------------------------------Sector Type Spinner--------------------------------------------

        sectorTypes= dataManager.getSectorTypes();
        ArrayAdapter<String> sector_spinneradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getsectorTypes()) {
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
        sector_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sectortypespinner.setAdapter(sector_spinneradapter);
        if (sectortypetext != null) {
            int spinnerPosition = sector_spinneradapter.getPosition(sectortypetext);
            sectortypespinner.setSelection(spinnerPosition);
        }
        sectortypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                sectortypetext = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---------------------------------HCE Type Spinner--------------------------------------------
        orgTypes= dataManager.getOrgTypesList();

        ArrayAdapter<String> hcetype_spinneradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getOrgTypes()) {
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
        hcetype_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hcetypespinner.setAdapter(hcetype_spinneradapter);
        if (hceTypetext != null) {
            int spinnerPosition = hcetype_spinneradapter.getPosition(hceTypetext);
            hcetypespinner.setSelection(spinnerPosition);
        }
        hcetypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                hceTypetext = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---------------------------------HCSP Type Spinner--------------------------------------------
        String[] HCSP_Type=
                {"Please Select", "Owner", "Incharge", "Manager"};
        ArrayAdapter<String> HCSP_spinneradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, HCSP_Type) {
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
        HCSP_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        HCSP_spinner.setAdapter(HCSP_spinneradapter);
        if (HCSPTypeText != null) {
            int spinnerPosition = HCSP_spinneradapter.getPosition(HCSPTypeText);
            HCSP_spinner.setSelection(spinnerPosition);
        }
        HCSP_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                HCSPTypeText = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---------------------------------regStatus Spinner--------------------------------------------

        String[] regStatus =
                {"Please Select", "Yes", "No"};
        ArrayAdapter<String> regStatus_spinneradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, regStatus) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
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
        regStatus_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regStatus_spinner.setAdapter(regStatus_spinneradapter);
        if (RegstatusText != null) {
            int spinnerPosition = regStatus_spinneradapter.getPosition(RegstatusText);
            regStatus_spinner.setSelection(spinnerPosition);
        }
        regStatus_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                RegstatusText = parent.getItemAtPosition(position).toString();
                if(RegstatusText=="Yes"){
                    regNoInput.setVisibility(View.VISIBLE);
                }
                else {
                    regNoInput.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---------------------------------counStatus Spinner--------------------------------------------
        final String[] counStatus =
                {"Please Select", "Yes", "No"};

        ArrayAdapter<String> counStatus_spinneradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, counStatus) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
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
        counStatus_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        counStatus_spinner.setAdapter(counStatus_spinneradapter);
        if (counStatusText != null) {
            int spinnerPosition = counStatus_spinneradapter.getPosition(counStatusText);
            counStatus_spinner.setSelection(spinnerPosition);
        }
        counStatus_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                counStatusText = parent.getItemAtPosition(position).toString();
                if(counStatusText=="Yes"){
                    counTypeInput.setVisibility(View.VISIBLE);
                    counNoInput.setVisibility(View.VISIBLE);
                }
                else {
                    counTypeInput.setVisibility(View.GONE);
                    counNoInput.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---------------------------------counciltype Spinner--------------------------------------------

        councilTypes= dataManager.getCouncilTypes();
        ArrayAdapter<String> counciltype_spinneradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getCouncilTypes()) {
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
        counciltype_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        counciltypespiner.setAdapter(counciltype_spinneradapter);
        if (counciltypetext != null) {
            int spinnerPosition = counciltype_spinneradapter.getPosition(counciltypetext);
            counciltypespiner.setSelection(spinnerPosition);
        }
        counciltypespiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

                counciltypetext = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
/*
        //---------------------------------Updated Status Spinner--------------------------------------------

        updateStatL1s= dataManager.getUpdateStatus();
        ArrayAdapter<String> updatedstatusadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getUpdateStatus()) {
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
        updatedstatusadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        updatestatusspinner.setAdapter(updatedstatusadapter);
        updatestatusspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

                updatestatustext = parent.getItemAtPosition(position).toString();
                if(updatestatustext.equals("Closed")){
                    substatusInput.setVisibility(View.VISIBLE);
                }
                else {
                    substatusInput.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---------------------------------Sub Status Spinner--------------------------------------------

        updateStatL2s= dataManager.getSubStatus();
        ArrayAdapter<String> substatusadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getSubStatus()) {
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
        substatusadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        substatusspinner.setAdapter(substatusadapter);
        substatusspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

                substatustext = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

/*
        Button markasdone = (Button) findViewById(R.id.btn_submit);
        markasdone.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {

                hce_nameText = hce_nameEdit.getText().toString();
                AddressText = AddressEdit.getText().toString();
                HCSP_nameText = HCSP_nameEdit.getText().toString();
                HCSP_SOText = HCSP_SOEdit.getText().toString();
                CNIC_Text=CNIC_Edit.getText().toString();
                HCSP_ContactText = HCSP_ContactEdit.getText().toString();
                Reg_NoText = Reg_NoEdit.getText().toString();
                coun_NoText = coun_NoEdit.getText().toString();

                int count=0;
                comnt = coments.getText().toString();


                if (comnt.equals("")) {
                    Toast.makeText(context, "Please Enter comments then submit ", Toast.LENGTH_SHORT).show();
                    count++;
                }
                if(counStatusText=="Yes" && updatestatustext.equals("Mark as Quack")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            context).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Eror");

                    // Setting Dialog Message
                    alertDialog.setMessage("Please select registration with council as No");
                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.eroricon);

                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog closed
                            //Toast.makeText(context, "Thanks You!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();

                    count++;
                }
                if(RegstatusText=="No" && updatestatustext.equals("Already Registered With PHC")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            context).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Eror");

                    // Setting Dialog Message
                    alertDialog.setMessage("Please select registration with PHC as Yes and enter registration no, if available");
                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.eroricon);

                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog closed
                            //Toast.makeText(context, "Thanks You!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                    count++;
                }
                if (count<1) {
                    if(counStatusText=="No")  {
                        coun_NoText = "";
                        counciltypetext = "";
                        counStatusID="0";

                    }
                    if(RegstatusText=="No") {
                        Reg_NoText = "";
                        RegstatusID="0";
                    }
                    if(counStatusText=="Yes")  {

                        counStatusID="1";

                    }
                    if(RegstatusText=="Yes") {
                        RegstatusID="1";
                    }
                    if(districtText.equals("Please Select")){
                        districtText="";
                    }
                    if(sectortypetext.equals("Please Select")){
                        sectortypetext="";
                    }
                    if(hceTypetext.equals("Please Select")){
                        hceTypetext="";
                    }
                    if(counciltypetext.equals("Please Select")){
                        counciltypetext="";
                    }
                    if(HCSPTypeText.equals("Please Select")){
                        HCSPTypeText="";
                    }
                    if(RegstatusText.equals("Please Select")){
                        RegstatusText="";
                    }
                    if(counStatusText.equals("Please Select")){
                        counStatusText="";
                    }
                    if(updatestatustext.equals("Please Select")){
                        updatestatustext="";
                    }
                    if(substatustext.equals("Please Select")){
                        substatustext="";
                    }
                             String url = getDirectionsUrl(context);
                              DownloadTask downloadTask = new DownloadTask();
                            //Start downloading json data from Google Directions API
                              downloadTask.execute(url);
                        }


            }
        });*/
return obj_view;

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
         /*       case R.id.navigation_home:
                    Intent filteintent = new Intent(getContext(), FilterActivity.class);
                    filteintent.putExtra("email", email);
                    getContext().startActivity(filteintent);
               *//*     IntentIntegrator intentIntegrator=new IntentIntegrator(activity);
                    intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                    intentIntegrator.setPrompt("scan");
                    intentIntegrator.setCameraId(0);
                    intentIntegrator.setBeepEnabled(false);
                    intentIntegrator.setBarcodeImageEnabled(false);
                    intentIntegrator.initiateScan();*//*
                    // firstpage.putExtra("stop",result);
                    // firstpage.putExtra("des_latitude",getDes_latitude());
                    // firstpage.putExtra("des_longitude",getDes_longitude());


                    //mapsActivity.startActivity(firstpage);

                    //mTextMessage.setText(R.string.title_home);
                    return true;*/
                case R.id.navigation_dashboard:
                    Intent routintent = new Intent(getContext(), RouteMapsActivity.class);
                    routintent.putExtra("des_latitude", latitude);
                    routintent.putExtra("des_longitude", longitude);
                    routintent.putExtra("RegType", RegType);
                    //  routintent.putExtra("markerstatus", marker_status);
                    routintent.putExtra("name", hce_nameText);
                    routintent.putExtra("address", AddressText);
                    routintent.putExtra("mobile", HCSP_ContactText);
                    getContext().startActivity(routintent);

                    return true;
                case R.id.navigation_notifications:
                    Intent firstpage = new Intent(getContext(), EditTabActivity.class);
                    firstpage.putExtra("HCEName", hce_nameText);
                    firstpage.putExtra("HCEAddress", AddressText);
                    firstpage.putExtra("District", districtText);
                    firstpage.putExtra("SectorType", sectortypetext);
                    firstpage.putExtra("OrgType", hceTypetext);
                    firstpage.putExtra("HCSPType", HCSPTypeText);
                    firstpage.putExtra("HCSPName", HCSP_nameText);
                    firstpage.putExtra("HCSPSO", HCSP_SOText);
                    firstpage.putExtra("HCSPCNIC", CNIC_Text);
                    firstpage.putExtra("HCSPContactNo", HCSP_ContactText);
                    firstpage.putExtra("RegStatus", RegstatusText);
                    firstpage.putExtra("RegNum", Reg_NoText);
                    firstpage.putExtra("CouncilStatus", counStatusText);
                    firstpage.putExtra("CouncilName", counciltypetext);
                    firstpage.putExtra("CouncilNum", coun_NoText);
                    firstpage.putExtra("final_id", final_id);
                    firstpage.putExtra("email", email);
                    getContext().startActivity(firstpage);

                    return true;
            }
            return false;
        }


    };

    private ArrayList<String> getOrgTypes() {

        ArrayList<String> orgtypelist = new ArrayList<String>();
        int i=0;
        for (OrgType orgType : orgTypes) {
            String orgTypess= orgType.getOrgType();
            orgtypelist.add(orgTypess);
            i++;
        }
        return orgtypelist;
    }
    private ArrayList<String> getCouncilTypes() {

        ArrayList<String> counciltypelist = new ArrayList<String>();
        int i=0;
        for (CouncilType councilType : councilTypes) {
            String counciltypelists= councilType.getCouncilType();
            counciltypelist.add(counciltypelists);
            i++;
        }
        return counciltypelist;
    }
    private ArrayList<String> getsectorTypes() {

        ArrayList<String> sectorTypeList = new ArrayList<String>();
        int i=0;
        for (SectorType sectorType : sectorTypes) {
            String sectortypelists= sectorType.getSectorType();
            sectorTypeList.add(sectortypelists);
            i++;
        }
        return sectorTypeList;
    }
    private ArrayList<String> getDistrict() {

        ArrayList<String> districtList = new ArrayList<String>();
        int i=0;
        for (District district : districts) {
            String districtLists= district.getDistrict();
            districtList.add(districtLists);
            i++;
        }
        return districtList;
    }

    private ArrayList<String> getUpdateStatus() {

        ArrayList<String> updatestatuslist = new ArrayList<String>();
        int i=0;
        for (UpdateStatL1 updateStatL1 : updateStatL1s) {
            String updatedStatL1ss= updateStatL1.getUpdatedStatL1();
            updatestatuslist.add(updatedStatL1ss);
            i++;
        }
        return updatestatuslist;
    }
    private ArrayList<String> getSubStatus() {

        ArrayList<String> substatuslist = new ArrayList<String>();
        int i=0;
        for (UpdateStatL2 updateStatL2 : updateStatL2s) {
            String substatLists= updateStatL2.getUpdatedStatL2();
            substatuslist.add(substatLists);
            i++;
        }
        return substatuslist;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();
            jsonStr=result;
            // Invokes the thread for parsing the JSON data
            parserTask.execute();

        }
    }
    private String getDirectionsUrl() {

        // Building the url to the web service
        String baseurl=getContext().getResources().getString(R.string.baseurl);
        String url=baseurl+"GetHCEPHCDetail?ID="+final_id;
            url = url.replaceAll(" ", "%20");

        return url;
    }
    private String downloadUrl(String strUrl) throws IOException {


        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();
            int statusCode = urlConnection.getResponseCode();


            if (statusCode >= 200 && statusCode < 400) {
                // Create an InputStream in order to extract the response object
                iStream = urlConnection.getInputStream();
            } else {
                iStream = urlConnection.getErrorStream();
            }
            //iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    private class ParserTask extends AsyncTask<Object, Object, ArrayList<HashMap<String, String>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog


        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Object... arg0) {

            // Making a request to url and getting response

            if (jsonStr != null) {
                try {
                    JSONArray json = new JSONArray(jsonStr);
// ...
                    mylist = new ArrayList<HashMap<String, String>>();
                    for (int i = 0; i < json.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = json.getJSONObject(i);

                        map.put("HCEName", e.getString("HCEName"));
                        map.put("HCEAddress", e.getString("HCEAddress"));
                        map.put("District", e.getString("District"));
                        map.put("SectorType", e.getString("SectorType"));
                        map.put("OrgType", e.getString("OrgType"));
                        map.put("HCSPType", e.getString("HCSPType"));
                        map.put("HCSPName", e.getString("HCSPName"));
                        map.put("HCSPSO", e.getString("HCSPSO"));
                        map.put("HCSPCNIC", e.getString("HCSPCNIC"));
                        map.put("HCSPContactNo", e.getString("HCSPContactNo"));
                        map.put("RegStatus", e.getString("RegStatus"));
                        map.put("RegNum", e.getString("RegNum"));
                        map.put("RegType", e.getString("RegType"));
                        map.put("CouncilStatus", e.getString("CouncilStatus"));
                        map.put("CouncilName", e.getString("CouncilName"));
                        map.put("CouncilNum", e.getString("CouncilNum"));
                        map.put("lat", e.getString("lat"));
                        map.put("lng", e.getString("lng"));
                        mylist.add(map);
                    }

                    // adding contact to contact list
                    //contactList.add(contact);

                } catch (final JSONException e) {

                    e.printStackTrace();


                }
            } else {
                Log.e("exception", "Couldn't get json from server.");
            }

            return mylist;
        }

        @Override
        protected void onPostExecute(final ArrayList<HashMap<String, String>> result) {
            super.onPostExecute(result);
            if(result!=null && result.size()>0) {
//                imageView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                hce_namelayout.setVisibility(View.VISIBLE);
                Addresslayout.setVisibility(View.VISIBLE);
                HCSP_namelayout.setVisibility(View.VISIBLE);
                HCSP_SOlayout.setVisibility(View.VISIBLE);
                CNIC_layout.setVisibility(View.VISIBLE);
                HCSP_Contactlayout.setVisibility(View.VISIBLE);
                district_layout.setVisibility(View.VISIBLE);
                sectortypelayout.setVisibility(View.VISIBLE);
                hcetypelayout.setVisibility(View.VISIBLE);
                HCSP_layout.setVisibility(View.VISIBLE);
                regStatus_layout.setVisibility(View.VISIBLE);
                counStatus_layout.setVisibility(View.VISIBLE);
                regNoInput.setVisibility(View.VISIBLE);
                counTypeInput.setVisibility(View.VISIBLE);
                counNoInput.setVisibility(View.VISIBLE);
                for (int i = 0; i < result.size(); i++) {

                    hce_nameText = result.get(i).get("HCEName");
                    AddressText = result.get(i).get("HCEAddress");
                    districtText = result.get(i).get("District");
                    sectortypetext = result.get(i).get("SectorType");
                    hceTypetext = result.get(i).get("OrgType");
                    HCSPTypeText = result.get(i).get("HCSPType");
                    HCSP_nameText = result.get(i).get("HCSPName");
                    HCSP_SOText = result.get(i).get("HCSPSO");
                    CNIC_Text = result.get(i).get("HCSPCNIC");
                    HCSP_ContactText = result.get(i).get("HCSPContactNo");
                    RegstatusText = result.get(i).get("RegStatus");
                    Reg_NoText = result.get(i).get("RegNum");
                    RegType = result.get(i).get("RegType");
                    counStatusText = result.get(i).get("CouncilStatus");
                    counciltypetext = result.get(i).get("CouncilName");
                    coun_NoText = result.get(i).get("CouncilNum");
                    latitude= Double.parseDouble(result.get(i).get("lat"));
                    longitude= Double.parseDouble(result.get(i).get("lng"));

                    hce_nameEdit.setText(hce_nameText);
                    AddressEdit.setText(AddressText);
                    HCSP_nameEdit.setText(HCSP_nameText);
                    HCSP_SOEdit.setText(HCSP_SOText);
                    CNIC_Edit.setText(CNIC_Text);
                    HCSP_ContactEdit.setText(HCSP_ContactText);
                    Reg_NoEdit.setText(Reg_NoText);
                    coun_NoEdit.setText(coun_NoText);
                    district_spinner.setText(districtText);
                    sectortypespinner.setText(sectortypetext);
                    hcetypespinner.setText(hceTypetext);
                    HCSP_spinner.setText(HCSPTypeText);
                    regStatus_spinner.setText(RegstatusText);
                    counStatus_spinner.setText(counStatusText);
                    counciltypespiner.setText(counciltypetext);

                    if(counStatusText=="Yes"){
                        counTypeInput.setVisibility(View.VISIBLE);
                        counNoInput.setVisibility(View.VISIBLE);
                    }
                    else {
                        counTypeInput.setVisibility(View.GONE);
                        counNoInput.setVisibility(View.GONE);
                    }
                    if(RegstatusText=="Yes"){
                        regNoInput.setVisibility(View.VISIBLE);
                    }
                    else {
                        regNoInput.setVisibility(View.GONE);
                    }

                }
            }


        }

    }

}
