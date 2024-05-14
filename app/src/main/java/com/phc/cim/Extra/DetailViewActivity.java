package com.phc.cim.Extra;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.cim.Activities.Common.FilterActivity;
import com.phc.cim.Activities.Common.RouteMapsActivity;
import com.phc.cim.DataElements.CouncilType;
import com.phc.cim.DataElements.District;
import com.phc.cim.DataElements.OrgType;
import com.phc.cim.DataElements.SectorType;
import com.phc.cim.DataElements.UpdateStatL1;
import com.phc.cim.DataElements.UpdateStatL2;
import com.phc.cim.Managers.DataManager;
import com.phc.cim.Activities.Common.AboutusActivity;
import com.phc.cim.Activities.Common.ChangePasswordActivity;
import com.phc.cim.Others.CurrentLocation;
import com.phc.cim.Others.Logout;
import com.phc.cim.R;
import com.phc.cim.TabsActivities.EditTabActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DetailViewActivity extends AppCompatActivity {


    double des_lat;
    double des_lng;
    DataManager dataManager;
    String MID;
    String MText;
    String jsonStr;
    Context context;
    CurrentLocation gps;
    EditText hce_nameEdit;
    EditText AddressEdit;
    EditText HCSP_nameEdit;
    EditText HCSP_SOEdit;
    EditText CNIC_Edit;
    EditText HCSP_ContactEdit;
    EditText Reg_NoEdit;
    EditText coun_NoEdit;
    EditText coments;

    TextInputLayout regNoInput;
    TextInputLayout counTypeInput;
    TextInputLayout counNoInput;
    TextInputLayout substatusInput;

    EditText district_spinner;
    EditText sectortypespinner;
    EditText hcetypespinner;
    EditText HCSP_spinner;
    EditText regStatus_spinner;
    EditText counStatus_spinner;
    EditText counciltypespiner;
    EditText updatestatusspinner;
    EditText substatusspinner;
    EditText bedsEdit;

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
    String password;
    String isEdit;
    String username;

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
    String RecordLockedForUpdate="";
    String total_beds="";
    double latitude;
    double longitude;
    String email= "";
    double cur_latitude;
    double cur_longitude;

    ArrayList<District> districts;
    ArrayList<SectorType> sectorTypes;
    ArrayList<CouncilType> councilTypes;
    ArrayList<OrgType> orgTypes;
    ArrayList<UpdateStatL1> updateStatL1s;
    ArrayList<UpdateStatL2> updateStatL2s;


    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_MOVIES = "videos";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;
    // toolbar titles respected to selected nav menu item
    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private static final String LOG_TAG = "AppUpgrade";
    private String activityTitles;
    private int versionCode = 0;

    String appURI = "";
    String time1;

    String index;
    private DownloadManager downloadManager;
    private long downloadReference;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;
    //private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    //private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";
    public static int navItemIndex = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        context = this;
        dataManager = new DataManager(context);
        gps = new CurrentLocation(this);
        hce_nameEdit = (EditText) findViewById(R.id.hce_name);
        AddressEdit = (EditText) findViewById(R.id.Address);
        HCSP_nameEdit = (EditText) findViewById(R.id.HCSP_Name);
        HCSP_SOEdit = (EditText) findViewById(R.id.HCSP_SO);
        CNIC_Edit = (EditText) findViewById(R.id.CNIC);
        HCSP_ContactEdit = (EditText) findViewById(R.id.Mobile);
        Reg_NoEdit = (EditText) findViewById(R.id.reg_no);
        coun_NoEdit = (EditText) findViewById(R.id.council_no);
        district_spinner = (EditText) findViewById(R.id.district);
        sectortypespinner = (EditText) findViewById(R.id.Sector_Type);
        hcetypespinner = (EditText) findViewById(R.id.hcetype);
        HCSP_spinner = (EditText) findViewById(R.id.HCSPType);
        bedsEdit = (EditText) findViewById(R.id.beds);
        regStatus_spinner = (EditText) findViewById(R.id.registration);
        counStatus_spinner = (EditText) findViewById(R.id.council_reg);
        counciltypespiner = (EditText) findViewById(R.id.counType);
        //coments = (EditText) findViewById(R.id.comments);
        regNoInput = (TextInputLayout) findViewById(R.id.reg);
        counTypeInput= (TextInputLayout) findViewById(R.id.counclType);
        counNoInput = (TextInputLayout) findViewById(R.id.council);
        substatusInput = (TextInputLayout) findViewById(R.id.substatus);


        hce_nameEdit.setEnabled(false);
        AddressEdit.setEnabled(false);
        HCSP_nameEdit.setEnabled(false);
        HCSP_SOEdit.setEnabled(false);
        CNIC_Edit.setEnabled(false);
        HCSP_ContactEdit.setEnabled(false);
        Reg_NoEdit.setEnabled(false);
        coun_NoEdit.setEnabled(false);
        bedsEdit.setEnabled(false);
        //coments.setEnabled(false);

   /*     district_spinner = (Spinner) findViewById(R.id.district);
        sectortypespinner = (Spinner) findViewById(R.id.Sector_Type);
        hcetypespinner = (Spinner) findViewById(R.id.hcetypespinner);
        HCSP_spinner = (Spinner) findViewById(R.id.HCSP_Typespinner);
        regStatus_spinner = (Spinner) findViewById(R.id.registration_spinner);
        counStatus_spinner = (Spinner) findViewById(R.id.council_spinner);
        counciltypespiner = (Spinner) findViewById(R.id.counType_spinner);*/

        district_spinner.setEnabled(false);
        sectortypespinner.setEnabled(false);
        hcetypespinner.setEnabled(false);
        HCSP_spinner.setEnabled(false);
        regStatus_spinner.setEnabled(false);
        counStatus_spinner.setEnabled(false);
        counciltypespiner.setEnabled(false);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        //fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        // imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getString(R.string.detail_view);

        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
       // updatestatusspinner = (Spinner) findViewById(R.id.updstatus_spinner);
       // substatusspinner = (Spinner) findViewById(R.id.substatus_spinner);


        Intent intent = getIntent();


        hce_nameText = (String) intent.getSerializableExtra("HCEName");
        AddressText = (String) intent.getSerializableExtra("HCEAddress");
        districtText = (String) intent.getSerializableExtra("District");
        sectortypetext = (String) intent.getSerializableExtra("SectorType");
        hceTypetext = (String) intent.getSerializableExtra("OrgType");
        HCSPTypeText = (String) intent.getSerializableExtra("HCSPType");
        HCSP_nameText = (String) intent.getSerializableExtra("HCSPName");
        HCSP_SOText = (String) intent.getSerializableExtra("HCSPSO");
        CNIC_Text = (String) intent.getSerializableExtra("HCSPCNIC");
        HCSP_ContactText = (String) intent.getSerializableExtra("HCSPContactNo");
        RegstatusText = (String) intent.getSerializableExtra("RegStatus");
        Reg_NoText = (String) intent.getSerializableExtra("RegNum");
        counStatusText = (String) intent.getSerializableExtra("CouncilStatus");
        counciltypetext = (String) intent.getSerializableExtra("CouncilName");
        coun_NoText = (String) intent.getSerializableExtra("CouncilNum");
        RegType = (String) intent.getSerializableExtra("RegType");
        total_beds = (String) intent.getSerializableExtra("total_beds");
        RecordLockedForUpdate = (String) intent.getSerializableExtra("RecordLockedForUpdate");
        final_id = (String) intent.getSerializableExtra("final_id");
        latitude = (double) intent.getSerializableExtra("latitude");
        longitude = (double) intent.getSerializableExtra("longitude");
        email = (String) intent.getSerializableExtra("email");
        password = (String) intent.getSerializableExtra("password");
        username = (String) intent.getSerializableExtra("username");
        isEdit = (String) intent.getSerializableExtra("isEdit");
        index = (String) intent.getSerializableExtra("index");

        // substatusInput.setVisibility(View.GONE);
        //hce_nameEdit.setHint(index+" HCE Name");
        hce_nameEdit.setText(index+". "+hce_nameText);
        AddressEdit.setText(AddressText);
        HCSP_nameEdit.setText(HCSP_nameText);
        HCSP_SOEdit.setText(HCSP_SOText);
        CNIC_Edit.setText(CNIC_Text);
        HCSP_ContactEdit.setText(HCSP_ContactText);
        Reg_NoEdit.setText(Reg_NoText);
        coun_NoEdit.setText(coun_NoText);
        bedsEdit.setText(total_beds);
        district_spinner.setText(districtText);
        sectortypespinner.setText(sectortypetext);
        hcetypespinner.setText(hceTypetext);
        HCSP_spinner.setText(HCSPTypeText);
        regStatus_spinner.setText(RegstatusText);
        counStatus_spinner.setText(counStatusText);
        counciltypespiner.setText(counciltypetext);

        regNoInput.setVisibility(View.GONE);
        counTypeInput.setVisibility(View.GONE);
        counNoInput.setVisibility(View.GONE);
        if(counStatusText.equals("Yes")){
            counTypeInput.setVisibility(View.VISIBLE);
            counNoInput.setVisibility(View.VISIBLE);
        }
        else {
            counTypeInput.setVisibility(View.GONE);
            counNoInput.setVisibility(View.GONE);
        }
        if(RegstatusText.equals("Yes")){
            regNoInput.setVisibility(View.VISIBLE);
        }
        else {
            regNoInput.setVisibility(View.GONE);
        }


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
                                              Intent firstpage = new Intent(context, ReportQuackActivity.class);
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
       /* districts= dataManager.getDistrictList();

        ArrayAdapter<String> district_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getDistrict()) {
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
        ArrayAdapter<String> sector_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getsectorTypes()) {
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

        ArrayAdapter<String> hcetype_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getOrgTypes()) {
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
        ArrayAdapter<String> HCSP_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, HCSP_Type) {
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
        ArrayAdapter<String> regStatus_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, regStatus) {
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

        ArrayAdapter<String> counStatus_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, counStatus) {
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
        ArrayAdapter<String> counciltype_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getCouncilTypes()) {
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
        });*/

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

    }
    private void loadNavHeader() {
        // name, website

        txtName.setText(username);
        txtWebsite.setText(email);

        // loading header background image
       /* Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // showing dot next to notifications label
        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);*/
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigatiokn menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            // toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
             /*   Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();*/
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        //toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }


    private HomeFragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
             /*   Intent i = new Intent(context, Login_Activity.class);
                startActivity(i);*/
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // photos
                PhotosFragment photosFragment = new PhotosFragment();
                return photosFragment;
            case 2:
                // movies fragment
                VideosFragment videosFragment = new VideosFragment();
                return videosFragment;
            case 3:
                // notifications fragment
                NotificationFragment notificationsFragment = new NotificationFragment();
                return notificationsFragment;

            case 4:
                // settings fragment
                SettingFragment settingsFragment = new SettingFragment();
                return settingsFragment;
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(false);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        startActivity(new Intent(context, FilterActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    /*  case R.id.nav_photos:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_PHOTOS;
                        break;
                    case R.id.nav_movies:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_MOVIES;
                        break;
                    case R.id.nav_notifications:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_NOTIFICATIONS;
                        break;*/
                /*    case R.id.nav_settings:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(context, Setting.class));
                        drawer.closeDrawers();
                        return true;*/
                    case R.id.nav_resetPassword:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(context, ChangePasswordActivity.class).putExtra("email",email).putExtra("password",password));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(context, AboutusActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_Logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure to exit CIM?")
                                .setTitle("Exit")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // launch new intent instead of loading fragment
                                        startActivity(new Intent(context, Logout.class));
                                        drawer.closeDrawers();
                                    }
                                })
                                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        return true;
                    // default:
                    // navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
     /*   if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }*/

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
/*
        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
            getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        }*/
        return true;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
           /*     case R.id.navigation_home:
                    Intent filteintent = new Intent(context, FilterActivity.class);
                    filteintent.putExtra("email", email);
                    filteintent.putExtra("Password", password);
                    filteintent.putExtra("username", username);
                    filteintent.putExtra("isEdit", isEdit);
                    context.startActivity(filteintent);
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
                    Intent routintent = new Intent(context, RouteMapsActivity.class);
                    routintent.putExtra("des_latitude", latitude);
                    routintent.putExtra("des_longitude", longitude);
                    routintent.putExtra("RegType", RegType);
                    routintent.putExtra("final_id", final_id);
                    routintent.putExtra("index", index);
                    routintent.putExtra("name", hce_nameText);
                    routintent.putExtra("address", AddressText);
                    routintent.putExtra("mobile", HCSP_ContactText);
                    routintent.putExtra("email", email);
                    routintent.putExtra("Password", password);
                    routintent.putExtra("username", username);
                    routintent.putExtra("isEdit", isEdit);
                    context.startActivity(routintent);

                    return true;
                case R.id.navigation_notifications:
                    if(isEdit.equals("true") && (RecordLockedForUpdate.equals("No") || RecordLockedForUpdate.equals("null"))) {
                        Intent firstpage = new Intent(context, EditTabActivity.class);
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
                        firstpage.putExtra("Password", password);
                        firstpage.putExtra("username", username);
                        firstpage.putExtra("isEdit", isEdit);
                        context.startActivity(firstpage);
                    }
                    else if(isEdit.equals("false")){
                        Toast.makeText(getApplicationContext(), "You are not authorized to edit the information", Toast.LENGTH_SHORT).show();
                    }
                    else if(isEdit.equals("true") && RecordLockedForUpdate.equals("Yes")){

                        Toast.makeText(getApplicationContext(), "HCE information is under review and is locked for Edit", Toast.LENGTH_SHORT).show();
                    }

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
    private String getDirectionsUrl(Context context) {

        // Building the url to the web service
        String baseurl=context.getResources().getString(R.string.baseurl);
        String url=baseurl+"AddHCEInfo_SendMail?HCEName="+hce_nameText+"&HCEAddress="+AddressText+"&District="+districtText+"&SectorType="+sectortypetext+"&OrgType="+hceTypetext+"&HCSPType="+HCSPTypeText+"&HCSPName="+HCSP_nameText+"&HCSP_SO="+HCSP_SOText+"&HCSP_CNIC="+CNIC_Text+"&HCSPContactNo="+HCSP_ContactText+"&RegistrationNo="+Reg_NoText+"&RegistrationStatus="+RegstatusID+"&CouncilStatus="+counStatusID+"&CouncilNo="+coun_NoText+"&CouncilName="+counciltypetext+"&UpdateStatus="+updatestatustext+"&UpdateSubStatus="+substatustext+"&lat="+cur_latitude+"&lng="+cur_longitude+"&emailAddress="+email+"&Comments="+comnt+"&final_id="+final_id;
          url = url.replaceAll(" ", "%20");

        return url;
    }
    private String downloadUrl(String strUrl) throws IOException {


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

    private class ParserTask extends AsyncTask<Object, Object, String> {

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

            }
            else {
                Log.e("exception", "Couldn't get json from server.");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                if (MID.equals("1")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            context).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Success");

                    // Setting Dialog Message
                    alertDialog.setMessage("Your Comments has been sent to PHC.");
                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.tick);

                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog closed
                            Toast.makeText(context, "Thanks You!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                }
                // Updating parsed JSON data into ListView

            }
            else{
                Toast.makeText(context, "Network eror", Toast.LENGTH_SHORT).show();
            }


        }

    }

}
