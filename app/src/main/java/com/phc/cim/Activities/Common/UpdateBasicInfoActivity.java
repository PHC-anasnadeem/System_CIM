package com.phc.cim.Activities.Common;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.cim.Activities.Licensing.PWSFilterActivity;
import com.phc.cim.Adapters.MyCustomPagerAdapter;
import com.phc.cim.DataElements.Action;
import com.phc.cim.DataElements.CouncilType;
import com.phc.cim.DataElements.District;
import com.phc.cim.DataElements.OrgType;
import com.phc.cim.DataElements.SectorType;
import com.phc.cim.DataElements.UpdateStatL1;
import com.phc.cim.DataElements.UpdateStatL2;
import com.phc.cim.DownloadClases.DownloadActionDetail;
import com.phc.cim.Extra.HomeFragment;
import com.phc.cim.Extra.NotificationFragment;
import com.phc.cim.Extra.PhotosFragment;
import com.phc.cim.Extra.SettingFragment;
import com.phc.cim.Extra.VideosFragment;
import com.phc.cim.Managers.DataManager;
import com.phc.cim.Others.CurrentLocation;
import com.phc.cim.Others.Logout;
import com.phc.cim.R;
import com.phc.cim.TabsActivities.DashboardTabs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class UpdateBasicInfoActivity extends AppCompatActivity {

    Context context;
    DataManager dataManager;
    String MID;
    String MText;
    String jsonStr;

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
    Spinner district_spinner;
    Spinner sectortypespinner;
    Spinner hcetypespinner;
    Spinner HCSP_spinner;
    Spinner regStatus_spinner;
    Spinner counStatus_spinner;
    Spinner counciltypespiner;
    Spinner quackloc_spinner;
    Spinner currloc_spinner;
    String currloc_text;
    String currloc_ID;
    String hce_nameText = "";
    String AddressText = "";
    String HCSP_nameText = "";
    String HCSP_SOText = "";
    String CNIC_Text = "";
    String HCSP_ContactText = "";
    String Reg_NoText = "";
    String coun_NoText = "";
    String comnt = "";
    String final_id = "";
    String districtText = "";
    String sectortypetext = "";
    String hceTypetext = "";
    String HCSPTypeText = "";
    String RegstatusText = "";
    String counStatusText = "";
    String counciltypetext = "";
    String counStatusID = "";
    String RegstatusID = "";
    String email = "";
    String password;
    String isEdit;
    String username;
    String quackloctext;
    String quacklocID;
    String distCurrPrevInMeters;
    double cur_latitude;
    double cur_longitude;
    ProgressDialog pDialog;
    ArrayList<District> districts;
    ArrayList<SectorType> sectorTypes;
    ArrayList<CouncilType> councilTypes;
    ArrayList<OrgType> orgTypes;

    String latLngInput; //Add latLngInput
    EditText editTextLatLng;
    ArrayList<UpdateStatL1> updateStatL1s;
    ArrayList<UpdateStatL2> updateStatL2s;
    ArrayList<Action> actions;
    String RoleID, UserID, isStat;
    TextView vistedtext, errortext;

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
    String date1 = "";
    String date2 = "";
    String appURI = "";
    String time1;
    MyCustomPagerAdapter myCustomPagerAdapter;
    String index;
    String UserName, LastVisitedDate,MarkSurvCount;
    private ArrayList<String> imageurls;
    double latitude;
    double longitude;
    int countt;
    int backcount = 0;
    private DownloadManager downloadManager;
    private long downloadReference;
    private static int ACTIVITY_TIME_OUT = 5000;
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
        setContentView(R.layout.activity_basicinfo);
        context = this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHandler = new Handler();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
  /*      BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/
        // load toolbar titles from string resources
        pDialog = new ProgressDialog(context);
        dataManager = new DataManager(context);
        gps = new CurrentLocation(context);
        hce_nameEdit = (EditText) findViewById(R.id.hce_name);
        AddressEdit = (EditText) findViewById(R.id.Address);
        HCSP_nameEdit = (EditText) findViewById(R.id.HCSP_Name);
        HCSP_SOEdit = (EditText) findViewById(R.id.HCSP_SO);
        CNIC_Edit = (EditText) findViewById(R.id.CNIC);
        HCSP_ContactEdit = (EditText) findViewById(R.id.Mobile);
        Reg_NoEdit = (EditText) findViewById(R.id.reg_no);
        coun_NoEdit = (EditText) findViewById(R.id.council_no);
        coments = (EditText) findViewById(R.id.comments);
        quackloc_spinner = (Spinner) findViewById(R.id.quackloc_spinner);
        currloc_spinner = (Spinner) findViewById(R.id.curloc_spinner);
        editTextLatLng = (EditText) findViewById(R.id.editTextLatLng); //ADD editTextLatLng

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // district_spinner = (Spinner) findViewById(R.id.district);
        sectortypespinner = (Spinner) findViewById(R.id.Sector_Type);
        hcetypespinner = (Spinner) findViewById(R.id.hcetypespinner);
        HCSP_spinner = (Spinner) findViewById(R.id.HCSP_Typespinner);
        regStatus_spinner = (Spinner) findViewById(R.id.registration_spinner);
        counStatus_spinner = (Spinner) findViewById(R.id.council_spinner);
        counciltypespiner = (Spinner) findViewById(R.id.counType_spinner);

        regNoInput = (TextInputLayout) findViewById(R.id.reg);
        counTypeInput = (TextInputLayout) findViewById(R.id.counclType);
        counNoInput = (TextInputLayout) findViewById(R.id.council);
        substatusInput = (TextInputLayout) findViewById(R.id.substatus);
        vistedtext = (TextView) findViewById(R.id.vistedtext);
        errortext = (TextView) findViewById(R.id.errortext);
        errortext.setVisibility(View.GONE);

        Intent intent;
        intent = getIntent();
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
        final_id = (String) intent.getSerializableExtra("final_id");
        index = (String) intent.getSerializableExtra("index");
        UserName = (String) intent.getSerializableExtra("UserName");
        LastVisitedDate = (String) intent.getSerializableExtra("LastVisitedDate");
//        MarkSurvCount = (String) intent.getSerializableExtra("MarkSurvCount");
        imageurls = (ArrayList<String>) getIntent().getSerializableExtra("imageurls");
        latitude = (double) intent.getSerializableExtra("latitude");
        longitude = (double) intent.getSerializableExtra("longitude");
        countt = (int) intent.getSerializableExtra("count");


        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);

        isStat = prefs.getString("isStat", null);//"No name defined" is the default value.
        email = prefs.getString("email", null);//"No name defined" is the default value.
        password = prefs.getString("password", null);//"No name defined" is the default value.
        username = prefs.getString("username", null);//"No name defined" is the default value.
        isEdit = prefs.getString("isEdit", null);//"No name defined" is the default value.
        RoleID = prefs.getString("RoleID", null); //0 is the default value.
        UserID = prefs.getString("UserID", null); //0 is the default value.
        activityTitles = getResources().getString(R.string.nav_item_BasicInfo_titles);
        if (RoleID.equals("1") || RoleID.equals("3")) {
            navigationView.getMenu().findItem(R.id.nav_actiondesc).setVisible(true);
            if (isStat.equals("true") && RoleID.equals("1")) {
                navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(true);


            } else {
                navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(false);
            }
        } else {
            navigationView.getMenu().findItem(R.id.nav_actiondesc).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(false);
        }

        loadNavHeader();
        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
        hce_nameEdit.setText(hce_nameText);
        AddressEdit.setText(AddressText);
        HCSP_nameEdit.setText(HCSP_nameText);
        HCSP_SOEdit.setText(HCSP_SOText);
        CNIC_Edit.setText(CNIC_Text);
        HCSP_ContactEdit.setText(HCSP_ContactText);
        Reg_NoEdit.setText(Reg_NoText);
        coun_NoEdit.setText(coun_NoText);

        regNoInput.setVisibility(View.GONE);
        counTypeInput.setVisibility(View.GONE);
        counNoInput.setVisibility(View.GONE);

        if (LastVisitedDate != null && LastVisitedDate.equals("null")) {
            vistedtext.setText("Last updated by: " + UserName);



    } else if (LastVisitedDate != null) {
            String ackwardDate = LastVisitedDate;
            Calendar calendar = Calendar.getInstance();
            String ackwardRipOff = ackwardDate.replace("/Date(", "").replace("+0500", "").replace(")/", "");
            Long timeInMillis = Long.valueOf(ackwardRipOff);
            calendar.setTimeInMillis(timeInMillis);
            String date = new SimpleDateFormat("dd-MMM-yyyy").format(calendar.getTime()).toString();
            vistedtext.setText("Last updated by: " + UserName + ", Dated: " + date);
        }
        //-------------------------Current Location----------------------------
        if (gps.canGetLocation()) {
            cur_latitude = gps.getLatitude();
            cur_longitude = gps.getLongitude();
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
        //---------------------------------you are at outlet location Spinner--------------------------------------------
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
        quackloc_spinner.setAdapter(quackloc_adapter);
        quackloc_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quackloctext = parent.getItemAtPosition(position).toString();
                if (quackloctext.equals("Yes")) {
                    quacklocID = "1";
                    editTextLatLng.setVisibility(View.GONE); // Hide the EditText
                } else if (quackloctext.equals("No")) {
                    quacklocID = "0";
                    editTextLatLng.setVisibility(View.VISIBLE); // Show the EditText

                    // Retrieve GPS location
                    if (gps.canGetLocation()) {
                        cur_latitude = gps.getLatitude();
                        cur_longitude = gps.getLongitude();
                        if (cur_latitude != 0.0 && cur_longitude != 0.0) {
                            // Update the EditText with the retrieved latitude and longitude
                            editTextLatLng.setText(cur_latitude + ", " + cur_longitude);
                        } else {
                            // Handle case where location retrieval was unsuccessful
                        }
                    } else {
                        // GPS or network is not enabled. Show settings alert.
                        gps.showSettingsAlert();
                    }

                } else {
                    quacklocID = "-1";
                    editTextLatLng.setVisibility(View.GONE); // Show the EditText
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle when nothing is selected
            }
        });
        //---------------------------------current loc is correct Spinner--------------------------------------------
        String currloc[] = {"Please Select", "Yes", "No"};
        ArrayAdapter<String> currloc_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, currloc) {
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
        currloc_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currloc_spinner.setAdapter(currloc_adapter);
        currloc_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                currloc_text = parent.getItemAtPosition(position).toString();
                if (currloc_text.equals("Yes")) {
                    currloc_ID = "1";
                } else if (currloc_text.equals("No")) {
                    currloc_ID = "0";
                } else {
                    currloc_ID = "-1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //---------------------------------District Spinner--------------------------------------------
/*        String Division="Please Select";
        districts= dataManager.getDistrictList(Division);

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
        });*/

        //---------------------------------Sector Type Spinner--------------------------------------------

        sectorTypes = dataManager.getSectorTypes();
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
        orgTypes = dataManager.getOrgTypesList();

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
        String[] HCSP_Type =
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
                if (RegstatusText.equals("Yes")) {
                    regNoInput.setVisibility(View.VISIBLE);
                } else {
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
                if (counStatusText.equals("Yes")) {
                    counTypeInput.setVisibility(View.VISIBLE);
                    counNoInput.setVisibility(View.VISIBLE);
                } else {
                    counTypeInput.setVisibility(View.GONE);
                    counNoInput.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---------------------------------counciltype Spinner--------------------------------------------

        councilTypes = dataManager.getCouncilTypes();
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
        });

        Button markasdone = (Button) findViewById(R.id.btn_submit);
        markasdone.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Location loc1 = new Location("");
                loc1.setLatitude(latitude);
                loc1.setLongitude(longitude);

                Location loc2 = new Location("");
                loc2.setLatitude(cur_latitude);
                loc2.setLongitude(cur_longitude);
                distCurrPrevInMeters = String.valueOf(loc1.distanceTo(loc2));

                latLngInput = editTextLatLng.getText().toString(); // Add latLngInput
                hce_nameText = hce_nameEdit.getText().toString();
                AddressText = AddressEdit.getText().toString();
                HCSP_nameText = HCSP_nameEdit.getText().toString();
                HCSP_SOText = HCSP_SOEdit.getText().toString();
                CNIC_Text = CNIC_Edit.getText().toString();
                HCSP_ContactText = HCSP_ContactEdit.getText().toString();
                Reg_NoText = Reg_NoEdit.getText().toString();
                coun_NoText = coun_NoEdit.getText().toString();

                int count = 0;
                comnt = coments.getText().toString();

                if (hce_nameText.isEmpty()) {
                    hce_nameEdit.setError("Please enter name");
                    count++;
                }
                if (AddressText.isEmpty()) {
                    AddressEdit.setError("Please enter address");
                    count++;
                }
                if (HCSP_nameText.isEmpty()) {
                    HCSP_nameEdit.setError("Please enter name");
                    count++;
                }
                if ("Please Select".equals(quackloctext)) {
                    setSpinnerError(quackloc_spinner, "Please select");
                    count++;
                }
                if ("Please Select".equals(currloc_text)) {
                    setSpinnerError(currloc_spinner, "Please select");
                    count++;
                }

                // Check if the AddressText is empty when "No" is selected
                if ("0".equals(quacklocID) && AddressText.isEmpty()) {
                    editTextLatLng.setError("Please enter address"); // Show error message
                    count++;
                }

                if (count > 0) {
                    errortext.setVisibility(View.VISIBLE);
                } else {
                    errortext.setVisibility(View.GONE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to save the changes?")
                            .setTitle("Save")
                            .setCancelable(false)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if ("No".equals(counStatusText)) {
                                        coun_NoText = "";
                                        counciltypetext = "";
                                        counStatusID = "0";
                                    }
                                    if ("No".equals(RegstatusText)) {
                                        Reg_NoText = "";
                                        RegstatusID = "0";
                                    }
                                    if ("Yes".equals(counStatusText)) {
                                        counStatusID = "1";
                                    }
                                    if ("Yes".equals(RegstatusText)) {
                                        RegstatusID = "1";
                                    }

                                    if ("Please Select".equals(districtText)) {
                                        districtText = "";
                                    }
                                    if ("Please Select".equals(sectortypetext)) {
                                        sectortypetext = "";
                                    }
                                    if ("Please Select".equals(hceTypetext)) {
                                        hceTypetext = "";
                                    }
                                    if ("Please Select".equals(counciltypetext)) {
                                        counciltypetext = "";
                                    }
                                    if ("Please Select".equals(HCSPTypeText)) {
                                        HCSPTypeText = "";
                                    }
                                    if ("Please Select".equals(RegstatusText)) {
                                        RegstatusText = "";
                                    }
                                    if ("Please Select".equals(counStatusText)) {
                                        counStatusText = "";
                                    }

                                    pDialog.setMessage("Submitting your update, Please wait...");
                                    pDialog.setCancelable(false);
                                    pDialog.show();
                                    String url = getDirectionsUrl(context);
                                    DownloadTask downloadTask = new DownloadTask();
                                    // Start downloading json data from Google Directions API
                                    downloadTask.execute(url);
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

//        markasdone.setOnClickListener(new Button.OnClickListener() {
//
//            public void onClick(View v) {
//                Location loc1 = new Location("");
//                loc1.setLatitude(latitude);
//                loc1.setLongitude(longitude);
//
//                Location loc2 = new Location("");
//                loc2.setLatitude(cur_latitude);
//                loc2.setLongitude(cur_longitude);
//                distCurrPrevInMeters = String.valueOf(loc1.distanceTo(loc2));
//
//
//                latLngInput = editTextLatLng.getText().toString(); //Add latLngInput
//                hce_nameText = hce_nameEdit.getText().toString();
//                AddressText = AddressEdit.getText().toString();
//                HCSP_nameText = HCSP_nameEdit.getText().toString();
//                HCSP_SOText = HCSP_SOEdit.getText().toString();
//                CNIC_Text = CNIC_Edit.getText().toString();
//                HCSP_ContactText = HCSP_ContactEdit.getText().toString();
//                Reg_NoText = Reg_NoEdit.getText().toString();
//                coun_NoText = coun_NoEdit.getText().toString();
//
//                int count = 0;
//                comnt = coments.getText().toString();
//                if (hce_nameText.equals("")) {
//                    hce_nameEdit.setError("Please enter name");
//                    count++;
//                }
//                if (AddressText.equals("")) {
//                    AddressEdit.setError("Please enter address ");
//                    count++;
//                }
//                if (HCSP_nameText.equals("")) {
//                    HCSP_nameEdit.setError("Please enter name");
//                    count++;
//                }
//                if (quackloctext.equals("Please Select")) {
//                    setSpinnerError(quackloc_spinner, ("Please select"));
//                    count++;
//                }
//                if (currloc_text.equals("Please Select")) {
//                    setSpinnerError(currloc_spinner, ("Please select"));
//                    count++;
//                }
//
//                // Check if the AddressText is empty when "No" is selected
//                if (quacklocID.equals("0") && AddressText.isEmpty()) {
//                    editTextLatLng.setError("Please enter address"); // Show error message
//                    count++;
//                }
//
//                if (count > 0) {
//                    errortext.setVisibility(View.VISIBLE);
//                }
//                if (count < 1) {
//                    errortext.setVisibility(View.GONE);
//           /*          AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
//                     builder1.setMessage("Your slected location is "+distanceInMeters+" away from previous location./n Are you sure you want to proceed?")
//                             .setTitle("Alert")
//                             .setCancelable(false)
//                             .setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
//                                 public void onClick(DialogInterface dialog, int id) {
//
//
//                                 }
//                             })
//                             .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                 public void onClick(DialogInterface dialog, int id) {
//                                     dialog.cancel();
//
//                                 }
//                             });
//                     AlertDialog alert = builder1.create();
//                     alert.show();*/
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setMessage("Do you want to save the changes?")
//                            .setTitle("Save")
//                            .setCancelable(false)
//                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//
//                                    if (counStatusText.equals("No")) {
//                                        coun_NoText = "";
//                                        counciltypetext = "";
//                                        counStatusID = "0";
//
//                                    }
//                                    if (RegstatusText.equals("No")) {
//                                        Reg_NoText = "";
//                                        RegstatusID = "0";
//                                    }
//                                    if (counStatusText.equals("Yes")) {
//
//                                        counStatusID = "1";
//
//                                    }
//                                    if (RegstatusText.equals("Yes")) {
//                                        RegstatusID = "1";
//                                    }
//                                    if (districtText.equals("Please Select")) {
//                                        districtText = "";
//                                    }
//                                    if (sectortypetext.equals("Please Select")) {
//                                        sectortypetext = "";
//                                    }
//                                    if (hceTypetext.equals("Please Select")) {
//                                        hceTypetext = "";
//                                    }
//                                    if (counciltypetext.equals("Please Select")) {
//                                        counciltypetext = "";
//                                    }
//                                    if (HCSPTypeText.equals("Please Select")) {
//                                        HCSPTypeText = "";
//                                    }
//                                    if (RegstatusText.equals("Please Select")) {
//                                        RegstatusText = "";
//                                    }
//                                    if (counStatusText.equals("Please Select")) {
//                                        counStatusText = "";
//                                    }
//
//                                    pDialog.setMessage("Submitting your update, Please wait...");
//                                    pDialog.setCancelable(false);
//                                    pDialog.show();
//                                    String url = getDirectionsUrl(context);
//                                    DownloadTask downloadTask = new DownloadTask();
//                                    //Start downloading json data from Google Directions API
//                                    downloadTask.execute(url);
//                                }
//                            })
//                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alert = builder.create();
//                    alert.show();
//
//                }
//
//
//            }
//        });
        Button canceldone = (Button) findViewById(R.id.btn_cancel);
        canceldone.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {

                onBackPressed();
            }
        });


    }

    private void loadNavHeader() {
        // name, website

        txtName.setText(username);
        txtWebsite.setText(email);

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
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
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
                        startActivity(new Intent(context, FilterActivity.class).putExtra("email", email).putExtra("password", password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_reportquack:
                        startActivity(new Intent(context, ReportQuackActivity.class).putExtra("email", email).putExtra("password", password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_quack:
                        startActivity(new Intent(context, QuackActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_actionsummary:
                        if (isStat.equals("true")) {
                            startActivity(new Intent(context, DashboardTabs.class).putExtra("email", email).putExtra("password", password).putExtra("username", username).putExtra("isEdit", isEdit));
                            drawer.closeDrawers();
                        } else {
                            Toast.makeText(context, "You are not authorised!", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    case R.id.nav_actiondesc:
                        startActivity(new Intent(context, IndReportingActivity.class).putExtra("email", email).putExtra("password", password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_resetPassword:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(context, ChangePasswordActivity.class).putExtra("email", email).putExtra("password", password));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_pwssearch:
                        startActivity(new Intent(context, PWSFilterActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_list:
                        startActivity(new Intent(context, DesealListing.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_hearing:
                        startActivity(new Intent(context, HearingStatusActivity.class));
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
                    //     navItemIndex = 0;
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

        if (backcount == 1) {
            super.onBackPressed();
            return;
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Do you really want to navigate away from this screen? Changes made will not be effective")
                    .setTitle("Warning")
                    .setCancelable(false)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            backcount = 1;
                            onBackPressed();
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();


        }

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

    private ArrayList<String> getOrgTypes() {

        ArrayList<String> orgtypelist = new ArrayList<String>();
        int i = 0;
        for (OrgType orgType : orgTypes) {
            String orgTypess = orgType.getOrgType();
            orgtypelist.add(orgTypess);
            i++;
        }
        return orgtypelist;
    }

    private ArrayList<String> getCouncilTypes() {

        ArrayList<String> counciltypelist = new ArrayList<String>();
        int i = 0;
        for (CouncilType councilType : councilTypes) {
            String counciltypelists = councilType.getCouncilType();
            counciltypelist.add(counciltypelists);
            i++;
        }
        return counciltypelist;
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

    private ArrayList<String> getDistrict() {

        ArrayList<String> districtList = new ArrayList<String>();
        int i = 0;
        for (District district : districts) {
            String districtLists = district.getDistrict();
            districtList.add(districtLists);
            i++;
        }
        return districtList;
    }

    private ArrayList<String> getUpdateStatus() {

        ArrayList<String> updatestatuslist = new ArrayList<String>();
        int i = 0;
        for (UpdateStatL1 updateStatL1 : updateStatL1s) {
            String updatedStatL1ss = updateStatL1.getUpdatedStatL1();
            updatestatuslist.add(updatedStatL1ss);
            i++;
        }
        return updatestatuslist;
    }

    private ArrayList<String> getSubStatus() {

        ArrayList<String> substatuslist = new ArrayList<String>();
        int i = 0;
        for (UpdateStatL2 updateStatL2 : updateStatL2s) {
            String substatLists = updateStatL2.getUpdatedStatL2();
            substatuslist.add(substatLists);
            i++;
        }
        return substatuslist;
    }

    private ArrayList<String> getActions() {

        ArrayList<String> actionslist = new ArrayList<String>();
        int i = 0;
        for (Action action : actions) {
            String actionLists = action.getAction();
            actionslist.add(actionLists);
            i++;
        }
        return actionslist;
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
            jsonStr = result;
            // Invokes the thread for parsing the JSON data
            parserTask.execute();

        }
    }

    private String getDirectionsUrl(Context context) {

        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String token = context.getResources().getString(R.string.token);
        String url = baseurl + "UpdateHCEBasicInfo?strToken=" + token + "&HCEName=" + hce_nameText + "&HCEAddress=" + AddressText + "&District=" + districtText + "&SectorType=" + sectortypetext +
                "&OrgType" + hceTypetext + "&HCSPType=" + HCSPTypeText + "&HCSPName=" + HCSP_nameText + "&HCSP_SO=" + HCSP_SOText + "&HCSP_CNIC=" + CNIC_Text + "&HCSPContactNo=" + HCSP_ContactText + "&RegistrationNo=" + Reg_NoText +
                "&RegistrationStatus=" + RegstatusID + "&CouncilStatus=" + counStatusID + "&CouncilNo=" + coun_NoText + "&CouncilName=" + counciltypetext + "&UpdateStatus=&UpdateSubStatus=&lat=" + latitude + "&lng=" + longitude + "&emailAddress=" + email +
                "&Comments=" + comnt + "&final_id=" + final_id + "&NoticeIssued=0&NoticeNo=&UpdateStatusID=0&UpdateSubStatusID=0&ActionID=0&RoleID=" + RoleID +
                "&UserLat=" + cur_latitude + "&UserLng=" + cur_longitude + "&CorrectLoc=" + currloc_ID + "&CurrentLoc=" + quacklocID + "&DistanceDiff=" + distCurrPrevInMeters;


        url = url.replaceAll(" ", "%20");
        url = url.replaceAll("#", "%23");
        url = url.replaceAll(",", "%2C");

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {


        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {

            //Post Request
            java.net.URL url = new URL(strUrl);
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
                if (MID.equals("1")) {
                    Toast.makeText(context, "Information update saved successfully", Toast.LENGTH_SHORT).show();

                    DownloadActionDetail downloadActionDetail = new DownloadActionDetail(context, hce_nameText, final_id, email, password, username, isEdit, index, countt);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            // Start your app main activity
                            finish();
                        }

                    }, ACTIVITY_TIME_OUT);


                } else {
                    Toast.makeText(context, "Data is not updated properly.(MID=0). Please contact ICT team", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Server is not responding. Please try again", Toast.LENGTH_SHORT).show();
            }


        }

    }


}
