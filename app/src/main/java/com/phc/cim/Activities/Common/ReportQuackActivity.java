package com.phc.cim.Activities.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.phc.cim.Activities.Aqc.ActionActivity;
import com.phc.cim.DataElements.District;
import com.phc.cim.DataElements.Division;
import com.phc.cim.DataElements.SectorType;
import com.phc.cim.DataElements.Tehsil;
import com.phc.cim.Managers.DataManager;
import com.phc.cim.Others.CurrentLocation;
import com.phc.cim.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ReportQuackActivity extends AppCompatActivity {


    double des_lat;
    double des_lng;
    DataManager dataManager;
    static String MID;
    static String MText;
    static String jsonStr;
    static Context context;
    CurrentLocation gps;
    EditText hce_nameEdit;
    EditText AddressEdit;
    EditText HCSP_nameEdit;
    EditText HCSP_SOEdit;
    EditText CNIC_Edit;
    EditText HCSP_ContactEdit;
    EditText coments;
    int a = 0;

    Spinner division_spinner;
    Spinner district_spinner;
    Spinner tehsil_spinner;
    Spinner quackloc_spinner;


    static String hce_nameText = "";
    static String AddressText = "";
    static String HCSP_nameText = "";
    static String HCSP_SOText = "";
    static String CNIC_Text = "";
    static String HCSP_ContactText = "";

    String comnt = "";
    static String final_id = "";
    static String districtText = "";
    String quackloctext;
    String divisionText = "";
    String tehsilText = "";
    static String email = "";
    static String password;
    static String isEdit;
    static String username;
    TextView errortext;
    static ProgressDialog pDialog;
    static double cur_latitude;
    static double cur_longitude;
    float loc_accuracy;
    double user_latitude;
    double user_longitude;
    ImageButton refreshbtn;
    TextView accuracyTextView;
    ArrayList<Division> divisions;
    ArrayList<District> districts;
    ArrayList<SectorType> sectorTypes;
    ArrayList<Tehsil> tehsils;
    private static ArrayList<String> _images;
    String roleid;

    private TextInputEditText latitudeEditText;
    private TextInputEditText longitudeEditText;
    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newquack);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dataManager = new DataManager(context);
        gps = new CurrentLocation(this);
        //refreshbtn = (ImageButton) findViewById(R.id.btn_refresh);
        accuracyTextView = (TextView) findViewById(R.id.accuracylabel);
        hce_nameEdit = (EditText) findViewById(R.id.hce_name);
        AddressEdit = (EditText) findViewById(R.id.Address);
        HCSP_nameEdit = (EditText) findViewById(R.id.HCSP_Name);
        HCSP_SOEdit = (EditText) findViewById(R.id.HCSP_SO);
        CNIC_Edit = (EditText) findViewById(R.id.CNIC);
        HCSP_ContactEdit = (EditText) findViewById(R.id.Mobile);
        coments = (EditText) findViewById(R.id.comments);
        errortext = (TextView) findViewById(R.id.errortext);
        pDialog = new ProgressDialog(context);
        division_spinner = (Spinner) findViewById(R.id.division_spinner);
        district_spinner = (Spinner) findViewById(R.id.district);
        tehsil_spinner = (Spinner) findViewById(R.id.Tehsil_spinner);
        quackloc_spinner = (Spinner) findViewById(R.id.quackloc_spinner);
        latitudeEditText = findViewById(R.id.lat);
        longitudeEditText = findViewById(R.id.lng);


        errortext.setVisibility(View.GONE);
        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra("email");
        password = (String) intent.getSerializableExtra("password");
        username = (String) intent.getSerializableExtra("username");
        isEdit = (String) intent.getSerializableExtra("isEdit");
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        roleid = prefs.getString("RoleID", null);//"No name defined" is the default value.


        // Initialize pDialog in onCreate
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Submitting your update, Please wait...");
        pDialog.setCancelable(false);


        //-------------------------Current Location----------------------------

        if (gps.canGetLocation()) {

            cur_latitude = gps.getLatitude();
            cur_longitude = gps.getLongitude();
            loc_accuracy = gps.getAccuracy();
            accuracyTextView.setText("Location Accurate to " + loc_accuracy + " meters");
            accuracyTextView.setTextColor(Color.parseColor("#303F9F"));

            // latlangListener.onlatlang(cur_latitude, cur_longitude);
            if (cur_latitude != 0.0 && cur_longitude != 0.0) {


            } else {
                cur_latitude = gps.getLatitude();
                cur_longitude = gps.getLongitude();


            }
        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            gps.showSettingsAlert();
        }
        this.runThread();


        //---------------------------------Division Spinner--------------------------------------------

        divisions = dataManager.getDivision();

        ArrayAdapter<String> division_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getDivision()) {
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
        division_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        division_spinner.setAdapter(division_spinneradapter);
        division_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                divisionText = parent.getItemAtPosition(position).toString();
                getDistrict();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //---------------------------------Quack location Spinner--------------------------------------------

        String quackloc[] = {"Please Select", "Yes", "No"};
        ArrayAdapter<String> quackloc_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, quackloc) {
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
        quackloc_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quackloc_spinner.setAdapter(quackloc_adapter);
        quackloc_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                quackloctext = parent.getItemAtPosition(position).toString();
                // Check if the selected option is "No"
                if (quackloctext.equals("No")) {
                    // Show latitudeEditText and longitudeEditText
                    latitudeEditText.setVisibility(View.VISIBLE);
                    longitudeEditText.setVisibility(View.VISIBLE);
                } else {
                    // Hide latitudeEditText and longitudeEditText
                    latitudeEditText.setVisibility(View.GONE);
                    longitudeEditText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button markasdone = (Button) findViewById(R.id.btn_submit);
        markasdone.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                int count = 0;
                hce_nameText = hce_nameEdit.getText().toString();
                AddressText = AddressEdit.getText().toString();
                HCSP_nameText = HCSP_nameEdit.getText().toString();
                HCSP_SOText = HCSP_SOEdit.getText().toString();
                CNIC_Text = CNIC_Edit.getText().toString();
                HCSP_ContactText = HCSP_ContactEdit.getText().toString();
                comnt = coments.getText().toString();

                String latitudeStr = latitudeEditText.getText().toString();
                String longitudeStr = longitudeEditText.getText().toString();

                if (!latitudeStr.isEmpty() && !longitudeStr.isEmpty()) {
                    latitude = Double.parseDouble(latitudeStr);
                    longitude = Double.parseDouble(longitudeStr);

                    // Further processing with latitude and longitude
                } else {
                    latitude = cur_latitude;
                    longitude = cur_longitude;
                }

                _images = new ArrayList<String>();


                if (comnt.equals("")) {
                    coments.setError("Please Enter Comments");
                    count++;
                }
                if (hce_nameText.equals("")) {
                    hce_nameEdit.setError("Name can't be empty");
                    count++;
                }
                if (AddressText.equals("")) {
                    AddressEdit.setError("Address can't be empty");
                    count++;
                }
                if (divisionText.equals("Please Select")) {
                    setSpinnerError(division_spinner, ("Please select division"));
                    count++;
                }
                if (districtText.equals("Please Select")) {
                    setSpinnerError(district_spinner, ("Please select district"));
                    count++;
                }
                if (tehsilText.equals("Please Select")) {
                    setSpinnerError(tehsil_spinner, ("Please select tehsil"));
                    count++;
                }
                if (quackloctext.equals("Please Select")) {
                    setSpinnerError(quackloc_spinner, ("Please select Quack location"));
                    count++;
                }
                if (count > 0) {
                    errortext.setVisibility(View.VISIBLE);
                }
                //User Latitude & Longitude

                user_latitude = latitude;
                user_longitude = longitude;
                if (count < 1) {

                    if (quackloctext.equals("No")) {
                        latitude = cur_latitude;
                        longitude = cur_longitude;
                    }

                    if (CNIC_Text.equals("")) {
                        CNIC_Text = "00000-0000000-0";
                    }
                    if (HCSP_ContactText.equals("")) {
                        HCSP_ContactText = "00000000000";
                    }
                    if (divisionText.equals("Please Select")) {
                        divisionText = "";
                    }
                    if (districtText.equals("Please Select")) {
                        districtText = "";
                    }
                    if (tehsilText.equals("Please Select")) {
                        tehsilText = "";
                    }
                /*    if(sectortypetext.equals("Please Select")){
                        sectortypetext="";
                    }*/
//                    pDialog.setMessage("Submitting your update, Please wait...");
//                    pDialog.setCancelable(false);
                    pDialog.show();

                    String url = getDirectionsUrl(context);
                    DownloadTask downloadTask = new DownloadTask();
                    //Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                }


            }
        });

    }

    // int i=0;
    private void runThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();//Call looper.prepare()
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //i++;
                    gps = new CurrentLocation(context);
                    cur_latitude = gps.getLatitude();
                    cur_longitude = gps.getLongitude();
                    loc_accuracy = gps.getAccuracy();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            accuracyTextView.setText("Location Accurate to " + loc_accuracy + " meters ");
                            accuracyTextView.setTextColor(Color.parseColor("#303F9F"));
                        }
                    });


                }

            }
        }).start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setSpinnerError(Spinner spinner, String error) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            //spinner.performClick(); // to open the spinner list if error is found.

        }
    }

    private ArrayList<String> getsectorTypes() {

        ArrayList<String> sectorTypeList = new ArrayList<String>();
        int i = 0;
        for (SectorType sectorType : sectorTypes) {
            String sectortypelists = sectorType.getSectorType();
            sectorTypeList.add(sectortypelists);
            i++;
        }
        return sectorTypeList;
    }


//    private ArrayList<String> getDivision() {
//
//        ArrayList<String> divisionList = new ArrayList<String>();
//        int i=0;
//        for (Division division : divisions) {
//            String divisionLists= division.getDivision();
//            divisionList.add(divisionLists);
//            i++;
//        }
//        return divisionList;
//    }

    private ArrayList<String> getDivision() {

        ArrayList<String> divisionList = new ArrayList<String>();
        int i = 0;
        if (divisions != null && !divisions.isEmpty()) {
            for (Division division : divisions) {
                String divisionLists = division.getDivision();
                divisionList.add(divisionLists);
                i++;
            }
        }
//        else {
//            divisionList.add("Please Select Division");
//            divisionList.add("Bahawalpur");
//            divisionList.add("D.G Khan");
//            divisionList.add("Faisalabad");
//            divisionList.add("Gujranwala");
//            divisionList.add("Lahore");
//            divisionList.add("Multan");
//            divisionList.add("Sahiwal");
//            divisionList.add("Sargodha");
//            divisionList.add("Rawalpindi");
//            divisionList.add("Other");
//        }
        return divisionList;
    }

    private ArrayList<String> getDistrict() {
        districts = dataManager.getDistrictList(divisionText);
        ArrayList<String> districtList = new ArrayList<String>();
        int i = 0;
        for (District district : districts) {
            String districtLists = district.getDistrict();
            districtList.add(districtLists);
            i++;
        }

//    private ArrayList<String> getDistrict() {
//        districts= dataManager.getDistrictList(divisionText);
//        ArrayList<String> districtList = new ArrayList<String>();
//
//            if (districts != null && !districts.isEmpty()) {
//            switch (divisionText) {
//                case "Bahawalpur":
//                    districtList.add("Bahawalpur");
//                    districtList.add("Bahawalnagar");
//                    districtList.add("Rahim Yar Khan");
//                    break;
//
//                case "D.G Khan":
//                    districtList.add("Dera Ghazi Khan");
//                    districtList.add("Layyah");
//                    districtList.add("Muzaffargarh");
//                    districtList.add("Rajanpur");
//                    break;
//
//                case "Faisalabad":
//                    districtList.add("Faisalabad");
//                    districtList.add("Chiniot");
//                    districtList.add("Jhang");
//                    districtList.add("Toba Tek Singh");
//                    break;
//                case "Gujranwala":
//                    districtList.add("Gujranwala");
//                    districtList.add("Gujrat");
//                    districtList.add("Sialkot");
//                    districtList.add("Narowal");
//                    districtList.add("Hafizabad");
//                    break;
//                case "Lahore":
//                    districtList.add("Lahore");
//                    districtList.add("Kasur");
//                    districtList.add("Nankana Sahib");
//                    districtList.add("Sheikhupura");
//                    break;
//                case "Multan":
//                    districtList.add("Multan");
//                    districtList.add("Khanewal");
//                    districtList.add("Lodhran");
//                    districtList.add("Vehari");
//                    break;
//                case "Sahiwal":
//                    districtList.add("Sahiwal");
//                    districtList.add("Okara");
//                    districtList.add("Pakpattan");
//                    break;
//                case "Sargodha":
//                    districtList.add("Sargodha");
//                    districtList.add("Bhakkar");
//                    districtList.add("Khushab");
//                    districtList.add("Mianwali");
//                    break;
//                case "Rawalpindi":
//                    districtList.add("Rawalpindi");
//                    districtList.add("Jhelum");
//                    districtList.add("Chakwal");
//                    districtList.add("Attock");
//                    break;
//                case "Other":
//                    districtList.add("Other");
//                    break;
//
//                default:
//                    // If the selected division doesn't match any cases, add a default case
//                    districtList.add("Please Select Division First");
//                    break;
//            }
//        }else {
//                int i = 0;
//                for (District district : districts) {
//                    String districtLists = district.getDistrict();
//                    districtList.add(districtLists);
//                    i++;
//                }
//            }


        //---------------------------------District Spinner--------------------------------------------


        ArrayAdapter<String> district_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, districtList) {
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
        district_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                districtText = parent.getItemAtPosition(position).toString();
                getTehsil();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return districtList;
    }

    private ArrayList<String> getTehsil() {

        tehsils = dataManager.getTehsilList(districtText);
        ArrayList<String> TehsilList = new ArrayList<String>();
        int i = 0;
        for (Tehsil tehsil : tehsils) {
            String Tehsillists = tehsil.getTehsil();
            TehsilList.add(Tehsillists);
            i++;
        }
//
//    private ArrayList<String> getTehsil() {
//
//        tehsils = dataManager.getTehsilList(districtText);
//        ArrayList<String> TehsilList = new ArrayList<String>();
//
//        if (tehsils != null && !tehsils.isEmpty()) {
////             Hard-coded tehsil values based on the selected district
//            switch (districtText) {
//                case "Attock":
//                    TehsilList.add("Hazro");
//                    TehsilList.add("Pindi Gheb");
//                    TehsilList.add("Fateh Jang");
//                    TehsilList.add("Jand");
//                    TehsilList.add("Taxila");
//                    TehsilList.add("Hassan Abdal");
//                    TehsilList.add("Attock");
//                    break;
//                case "Bahawalnagar":
//                    TehsilList.add("Bahawalnagar");
//                    TehsilList.add("Chishtian");
//                    TehsilList.add("Fort Abbas");
//                    TehsilList.add("Haroonabad");
//                    TehsilList.add("Minchanabad");
//                    break;
//                case "Bahawalpur":
//                    TehsilList.add("Ahmedpur Sharqia");
//                    TehsilList.add("Bahawalpur");
//                    TehsilList.add("Hasilpur");
//                    TehsilList.add("Khairpur");
//                    TehsilList.add("Tamewali");
//                    TehsilList.add("Yazman");
//                    break;
//                case "Bhakkar":
//                    TehsilList.add("Bhakkar");
//                    TehsilList.add("Darya Khan");
//                    TehsilList.add("Kaloorkot");
//                    TehsilList.add("Bugallon");
//                    break;
//                case "Chakwal":
//                    TehsilList.add("Choa Saidan Shah");
//                    TehsilList.add("Talagang");
//                    TehsilList.add("Kallar Kahar");
//                    TehsilList.add("Chakwal");
//                    break;
//                case "Chiniot":
//                    TehsilList.add("Lalian");
//                    TehsilList.add("Chiniot");
//                    TehsilList.add("Bhowana");
//                    break;
//                case "Dera Ghazi Khan":
//                    TehsilList.add("Dera Ghazi Khan");
//                    TehsilList.add("Taunsa Sharif");
//                    break;
//                case "Faisalabad":
//                    TehsilList.add("Faisalabad Saddar");
//                    TehsilList.add("Chak Jhumra");
//                    TehsilList.add("Samundri");
//                    TehsilList.add("Jaranwala");
//                    TehsilList.add("Tandlianwala");
//                    break;
//                case "Gujranwala":
//                    TehsilList.add("Gujranwala");
//                    TehsilList.add("Kamoki");
//                    TehsilList.add("Nowshera");
//                    TehsilList.add("Virkan");
//                    TehsilList.add("Wazirabad");
//                    break;
//                case "Gujrat":
//                    TehsilList.add("Gujrat");
//                    TehsilList.add("Kharian");
//                    TehsilList.add("Sarai Alamgir");
//                    break;
//                case "Hafizabad":
//                    TehsilList.add("Hafizabad");
//                    TehsilList.add("Pindi Bhattian");
//                    break;
//                case "Jhang":
//                    TehsilList.add("18 Hazari");
//                    TehsilList.add("Jhang");
//                    TehsilList.add("Ahmad Pur Sial");
//                    TehsilList.add("Shorkot");
//                    break;
//                case "Jhelum":
//                    TehsilList.add("Sohawa");
//                    TehsilList.add("Pind Dadan Khan");
//                    TehsilList.add("Jhelum");
//                    TehsilList.add("Dina");
//                    break;
//                case "Kasur":
//                    TehsilList.add("Chunian");
//                    TehsilList.add("Kasur");
//                    TehsilList.add("Pattoki");
//                    break;
//                case "Khanewal":
//                    TehsilList.add("Khanewal");
//                    TehsilList.add("Mian Channu");
//                    TehsilList.add("Kabirwala");
//                    TehsilList.add("Jahania");
//                    break;
//                case "Khushab":
//                    TehsilList.add("Khushab");
//                    TehsilList.add("Noorpur");
//                    TehsilList.add("Thal");
//                    break;
//                case "Lahore":
//                    TehsilList.add("Lahore");
//                    TehsilList.add("Lahore Cantt");
//                    TehsilList.add("Lahore Saddar");
//                    TehsilList.add("ModelTown");
//                    TehsilList.add("Shalamar");
//                    break;
//                case "Layyah":
//                    TehsilList.add("Layyah");
//                    TehsilList.add("Chaubara");
//                    TehsilList.add("Karor");
//                    TehsilList.add("Chowk Azam");
//                    TehsilList.add("Fatehpur");
//                    break;
//                case "Lodhran":
//                    TehsilList.add("Dunyapur");
//                    TehsilList.add("Karor Pacca");
//                    TehsilList.add("Lodhran");
//                    break;
//                case "Mandi Bahauddin":
//                    TehsilList.add("Malakwal");
//                    TehsilList.add("Mandi Bahauddin");
//                    TehsilList.add("Westfalia");
//                    break;
//                case "Mianwali":
//                    TehsilList.add("Isakhel");
//                    TehsilList.add("Mianwali");
//                    TehsilList.add("Piplan");
//                    break;
//                case "Multan":
//                    TehsilList.add("Multan Sadar");
//                    TehsilList.add("Dunyapur");
//                    TehsilList.add("Shuja Abad");
//                    TehsilList.add("Jalal Pur Pirwala");
//                    TehsilList.add("Multan City");
//                    TehsilList.add("Lodhran");
//                    break;
//                case "Muzaffargarh":
//                    TehsilList.add("Alipur");
//                    TehsilList.add("Jatoi");
//                    TehsilList.add("Kot Addu");
//                    TehsilList.add("Muzaffargarh");
//                    break;
//                case "Narowal":
//                    TehsilList.add("Narowal");
//                    TehsilList.add("Shakargarh");
//                    TehsilList.add("Zafarwal");
//                    break;
//                case "Nankana Sahib":
//                    TehsilList.add("Shahkot");
//                    TehsilList.add("Sangla Hill");
//                    TehsilList.add("Nankana");
//                    break;
//                case "Okara":
//                    TehsilList.add("Okara");
//                    TehsilList.add("Renala Khurd");
//                    TehsilList.add("Depalpur");
//                    break;
//                case "Pakpattan":
//                    TehsilList.add("Pakpattan");
//                    TehsilList.add("Arifwala");
//                    break;
//                case "Rahim Yar Khan":
//                    TehsilList.add("Khanpur");
//                    TehsilList.add("Liaquatpur");
//                    TehsilList.add("Rahim Yar Khan");
//                    TehsilList.add("Sadiqabad");
//                    break;
//                case "Rajanpur":
//                    TehsilList.add("Jampur");
//                    TehsilList.add("Rajanpur");
//                    TehsilList.add("Rojhan");
//                    break;
//                case "Rawalpindi":
//                    TehsilList.add("Rawalpindi");
//                    TehsilList.add("Muree");
//                    TehsilList.add("NULL");
//                    TehsilList.add("Taxila");
//                    TehsilList.add("Kotli Sattian");
//                    TehsilList.add("Gujar Khan");
//                    TehsilList.add("Kallar Syedan");
//                    TehsilList.add("Kahuta");
//                    break;
//                case "Sahiwal":
//                    TehsilList.add("Chichawatni");
//                    TehsilList.add("Sahiwal");
//                    break;
//                case "Sargodha":
//                    TehsilList.add("Bhalwal");
//                    TehsilList.add("Sahiwal");
//                    TehsilList.add("Sargodha");
//                    TehsilList.add("Bhera");
//                    TehsilList.add("Kot Momin");
//                    TehsilList.add("Shahpur");
//                    TehsilList.add("Silanwali");
//                    break;
//                case "Sheikhupura":
//                    TehsilList.add("Sharaqpur");
//                    TehsilList.add("Muridke");
//                    TehsilList.add("Sheikhupura");
//                    TehsilList.add("Ferozwala");
//                    TehsilList.add("Safdarabad");
//                    break;
//                case "Sialkot":
//                    TehsilList.add("Daska");
//                    TehsilList.add("Pasrur");
//                    TehsilList.add("Sambrial");
//                    TehsilList.add("Sialkot");
//                    break;
//                case "Toba Tek Singh":
//                    TehsilList.add("Gojra");
//                    TehsilList.add("Kamalia");
//                    TehsilList.add("Toba Tek Singh");
//                    break;
//                case "Vehari":
//                    TehsilList.add("Mailsi");
//                    TehsilList.add("Vehari");
//                    TehsilList.add("Burewala");
//                    break;
//                case "Other":
//                    TehsilList.add("Other Tehsil");
//                    break;
//
//                default:
//                    // Default case or handling for unsupported districts
//                    TehsilList.add("Please Select District First");
//                    break;
//            }
//        } else {
//            int i = 0;
//            for (Tehsil tehsil : tehsils) {
//                String Tehsillists = tehsil.getTehsil();
//                TehsilList.add(Tehsillists);
//                i++;
//            }
//        }


        //---------------------------------Tehsil Spinner--------------------------------------------

        ArrayAdapter<String> tehsil_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, TehsilList) {
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
        tehsil_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tehsil_spinner.setAdapter(tehsil_spinneradapter);
        tehsil_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                tehsilText = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return TehsilList;
    }

    class DownloadTask extends AsyncTask<String, Void, String> {


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
            jsonStr = result;
            // Invokes the thread for parsing the JSON data
            parserTask.execute();

        }
    }

    private String getDirectionsUrl(Context context) {

        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String token = context.getResources().getString(R.string.token);
        String url = baseurl + "ReportQuack?strToken=" + token + "&HCEName=" + hce_nameText + "&HCEAddress=" + AddressText + "&Division=" + divisionText + "&District=" + districtText + "&Tehsil=" + tehsilText + "&SectorType=Private&HCSPName=" + HCSP_nameText + "&HCSP_SO=" + HCSP_SOText + "&HCSP_CNIC=" + CNIC_Text + "&HCSPContactNo=" + HCSP_ContactText + "&lat=" + cur_latitude + "&lng=" + cur_longitude + "&emailAddress=" + email + "&Comments=" + comnt + "&Userlat=" + user_latitude + "&Userlng=" + user_longitude + "&QuackCategory=&QuackSubCategory=&RoleID=" + roleid;
        url = url.replaceAll(" ", "%20");
        url = url.replaceAll("#", "%23");
        url = url.replaceAll(",", "%2C");

        return url;
    }

    private static String downloadUrl(String strUrl) throws IOException {


        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {

            //Post Request
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            int statusCode = urlConnection.getResponseCode();


            if (statusCode >= 200 && statusCode < 400) {
                // Create an InputStream in order to extract the response object
                iStream = urlConnection.getInputStream();
            } else {
                iStream = urlConnection.getErrorStream();
            }


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

    class ParserTask extends AsyncTask<Object, Object, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog


        }

        @Override
        protected String doInBackground(Object... arg0) {

            // Making a request to url and getting response

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    MID = jsonObj.getString("MID");
                    MText = jsonObj.getString("MText");
                    return MID;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("exception", "Couldn't get json from server.");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (result != null) {
                if (MID.equals("0")) {
                    Toast.makeText(context, "Record not saved! Please try again", Toast.LENGTH_SHORT).show();
                } else {
                    // Show MID value as a Toast message
                    Toast.makeText(context, "MID: " + MID, Toast.LENGTH_SHORT).show();

                    final_id = MID;
                    Intent firstpage = new Intent(context, ActionActivity.class);
                    firstpage.putExtra("HCEName", hce_nameText);
                    firstpage.putExtra("HCEAddress", AddressText);
                    firstpage.putExtra("District", districtText);
                    firstpage.putExtra("OrgType", "");
                    firstpage.putExtra("HCSPType", "");
                    firstpage.putExtra("HCSPName", HCSP_nameText);
                    firstpage.putExtra("HCSPSO", HCSP_SOText);
                    firstpage.putExtra("HCSPCNIC", CNIC_Text);
                    firstpage.putExtra("HCSPContactNo", HCSP_ContactText);
                    firstpage.putExtra("RegStatus", "");
                    firstpage.putExtra("RegNum", "");
                    firstpage.putExtra("RegType", "");
                    firstpage.putExtra("total_beds", "");
                    firstpage.putExtra("RecordLockedForUpdate", "");
                    firstpage.putExtra("CouncilStatus", "");
                    firstpage.putExtra("CouncilName", "");
                    firstpage.putExtra("CouncilNum", "");
                    firstpage.putExtra("final_id", final_id);
                    firstpage.putExtra("latitude", cur_latitude);
                    firstpage.putExtra("longitude", cur_longitude);
                    firstpage.putExtra("email", email);
                    firstpage.putExtra("Password", password);
                    firstpage.putExtra("username", username);
                    firstpage.putExtra("isEdit", isEdit);
                    firstpage.putExtra("index", "0");
                    firstpage.putStringArrayListExtra("imageurls", _images);
                    finish();
                    context.startActivity(firstpage);
                }

                // Showing Alert Message
                //alertDialog.show();
            }
            // Updating parsed JSON data into ListView


            else {
                Toast.makeText(context, "Server error! Please try again", Toast.LENGTH_SHORT).show();
            }


        }

    }

}
