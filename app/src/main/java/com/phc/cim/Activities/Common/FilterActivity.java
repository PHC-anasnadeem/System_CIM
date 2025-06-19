package com.phc.cim.Activities.Common;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.cim.Activities.Licensing.PWSFilterActivity;
import com.phc.cim.Activities.Notification.NotificationManager;
import com.phc.cim.Activities.Notification.NotificationApiClient;
import com.phc.cim.Activities.Notification.NotificationModel;
import com.phc.cim.Activities.Notification.NotificationReceiver;
import com.phc.cim.DataElements.ActionType;
import com.phc.cim.DataElements.CouncilType;
import com.phc.cim.DataElements.Distance;
import com.phc.cim.DataElements.District;
import com.phc.cim.DataElements.OrgType;
import com.phc.cim.DataElements.RegStatus;
import com.phc.cim.DataElements.SectorType;
import com.phc.cim.DataElements.Tehsil;
import com.phc.cim.DataElements.UpdateStatL1;
import com.phc.cim.DataElements.UpdateStatL2;
import com.phc.cim.DataElements.subActionType;
import com.phc.cim.DownloadClases.DownloadQuackClusterActivity;
import com.phc.cim.Extra.HomeFragment;
import com.phc.cim.Extra.NotificationFragment;
import com.phc.cim.Extra.PhotosFragment;
import com.phc.cim.Extra.SettingFragment;
import com.phc.cim.Extra.VideosFragment;
import com.phc.cim.Fragments.MapFragment;
import com.phc.cim.Managers.DataManager;
import com.phc.cim.Others.CurrentLocation;
import com.phc.cim.Others.Logout;
import com.phc.cim.R;
import com.phc.cim.TabsActivities.DashboardTabs;
import com.phc.cim.TabsActivities.SummMapListTabs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FilterActivity extends AppCompatActivity implements NotificationReceiver.NotificationCountListener {
    Spinner sectortypespinner;
    Spinner hcetypespinner;
    Spinner counciltypespiner;
    Spinner status_spinner;
    Spinner district_spinner;
    Spinner tehsil_spinner;
    Spinner searchbyspinner;
    Spinner distancespinner;
    Spinner lastvisitedspinner;
    Spinner actionspinner;
    Spinner subactionType_spinner;

    LinearLayout distanceLayout;
    LinearLayout districtLayout;
    LinearLayout TehsilLayout;
    LinearLayout Loc_layout;
    LinearLayout sectortypelayout;
    LinearLayout Council_Typelayout;
    LinearLayout HCSstatus_layout;
    LinearLayout hcetypelayout;
    LinearLayout bed_layout;
    LinearLayout lastvisit_layout;
    LinearLayout finalidlayout;
    LinearLayout cniclayout;
    LinearLayout phonelayout;
    LinearLayout Action_layout;
    CurrentLocation gps;
    double cur_latitude;
    double cur_longitude;
    String searchbytext="";
    String distancetext="";
    String sectortypetext="";
    String counciltypetext="";
    String hceTypetext="";
    String hcestatusText="";
    String hcestatusID="";
    String districtText="";
    String tehsilText="";
    String BfromText="";
    String BtoText="";
    String email="";
    private Boolean userchangesec=true;
    String lastvisitedText="";
    String lastvisitedID;
    String actionText="";
    String hcenameText="";
    String plancodeText="";
    String RegnoText="";
    String finalidText="";
    String Cnic="";
    String Phone="";
    LinearLayout regnoLayout;
    LinearLayout hcenameLayout;
    LinearLayout showlayout;
    LinearLayout hidelayout;
    LinearLayout subvisit_layout;
    LinearLayout errortextlayout;
    EditText bed_from;
    EditText bed_to;
    EditText hcenameEdit;
    EditText RegnoEdit;
    EditText plancodeEdit;
    EditText finalidEdit;
    EditText cnicEdit;
    EditText phoneNoEdit;

    Context context;
    int count;
    DataManager dataManager;
    ArrayList<subActionType> subactionType = null;
    ArrayList<subActionType> subactionTypeselected=null;
    String subactionTypeText;
    String subactionTypeID;
    String subactiontseltext;
    ArrayList<District> districts;
    ArrayList<Tehsil> tehsils;
    ArrayList<SectorType> sectorTypes;
    ArrayList<CouncilType> councilTypes;
    ArrayList<OrgType> orgTypes;
    ArrayList<String> districtTehsil;
    ArrayList<UpdateStatL1> updateStatL1s;
    ArrayList<UpdateStatL2> updateStatL2s;
    ArrayList<Distance> distances;
    ArrayList<RegStatus> regStatuses;
    ArrayList<ActionType> actionTypeList = null;
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
    String versionName = "";
    int versionCode = -1;
    String Roleid;
    String appURI = "";
    String time1;
    Switch simpleSwitch1;
    String password;
    String isEdit;
    String username;
    private DownloadManager downloadManager;
    private long downloadReference;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;
    //private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    //private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";
    public static int navItemIndex = -1;

    ArrayList<HashMap<String, String>> mylist;
    ProgressDialog pDialog;
    String jsonStr;
    ArrayAdapter<String> sectortypeadapter;
    private ImageView notificationIcon;
    private TextView notificationCount;
    private int notificationCounter = 0;
    private static final int REQUEST_CODE_INSTALL_PERMISSION = 1001;

    // Notification manager
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        context=this;
        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra("email");
        password = (String) intent.getSerializableExtra("password");
        username = (String) intent.getSerializableExtra("username");
        isEdit = (String) intent.getSerializableExtra("isEdit");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gps = new CurrentLocation(context);
        mHandler = new Handler();
//        TextView t2 = (TextView) findViewById(R.id.text2);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);

        // Find notification views
//        notificationIcon = findViewById(R.id.notificationIcon);
//        notificationCount = findViewById(R.id.notificationCount);

        int notificationCount = getIntent().getIntExtra("notificationCount", 0);

        // Set click listener for notification icon
//        notificationIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent notifyIntent = new Intent(FilterActivity.this, NotificationActivity.class);
//                startActivity(notifyIntent);
////                Toast.makeText(FilterActivity.this, "Notifications clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//        incrementNotificationCount();





    // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getString(R.string.nav_item_filter_titles);

        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
        dataManager = new DataManager(context);

        bed_from = (EditText) findViewById(R.id.bed_from);
        bed_to = (EditText) findViewById(R.id.bed_to);
        hcenameEdit = (EditText) findViewById(R.id.hce_name);
        RegnoEdit = (EditText) findViewById(R.id.reg_no);
        finalidEdit = (EditText) findViewById(R.id.finalidEdit);
        cnicEdit = (EditText) findViewById(R.id.cnicEdit);
        phoneNoEdit = (EditText) findViewById(R.id.phoneNoEdit);

        actionspinner= (Spinner) findViewById(R.id.action_spinner);
        subactionType_spinner = (Spinner) findViewById(R.id.subvisit_spinner);
        sectortypespinner = (Spinner) findViewById(R.id.Sector_Type);
        lastvisitedspinner = (Spinner) findViewById(R.id.lastvisit_spinner);
        distancespinner = (Spinner) findViewById(R.id.distanceSpinner);
        status_spinner = (Spinner) findViewById(R.id.HCSstatus_spinner);
        counciltypespiner = (Spinner) findViewById(R.id.Council_Typespinner);
        hcetypespinner = (Spinner) findViewById(R.id.HCE_Typespinner);
        district_spinner = (Spinner) findViewById(R.id.districtSpinner);
        tehsil_spinner = (Spinner) findViewById(R.id.tehsilSpinner);

        sectortypespinner.setSelection(0);

        regnoLayout = (LinearLayout) findViewById(R.id.reg_noL);
        hcenameLayout = (LinearLayout) findViewById(R.id.hce_nameL);
        errortextlayout= (LinearLayout) findViewById(R.id.errortextlayout);
        distanceLayout = (LinearLayout) findViewById(R.id.distanceid);
        districtLayout = (LinearLayout) findViewById(R.id.districtid);
        TehsilLayout = (LinearLayout) findViewById(R.id.tehsilid);
        showlayout = (LinearLayout) findViewById(R.id.showlayout);
        hidelayout = (LinearLayout) findViewById(R.id.hidelayout);
        subvisit_layout = (LinearLayout) findViewById(R.id.subvisit_layout);

        sectortypelayout = (LinearLayout) findViewById(R.id.sectortypelayout);
        Council_Typelayout = (LinearLayout) findViewById(R.id.Council_Typelayout);
        HCSstatus_layout = (LinearLayout) findViewById(R.id.HCSstatus_layout);
        hcetypelayout = (LinearLayout) findViewById(R.id.hcetypelayout);
        bed_layout = (LinearLayout) findViewById(R.id.bed_layout);
        finalidlayout = (LinearLayout) findViewById(R.id.finalidlayout);
        cniclayout = (LinearLayout) findViewById(R.id.cniclayout);
        phonelayout = (LinearLayout) findViewById(R.id.phonelayout);
        Action_layout = (LinearLayout) findViewById(R.id.Action_layout);

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String isStat = prefs.getString("isStat", null);//"No name defined" is the default value.
        Roleid = prefs.getString("RoleID", null); //0 is the default value.

        if(Roleid.equals("1")) {
            navigationView.getMenu().findItem(R.id.nav_actiondesc).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_hearing).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_list).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_registration).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_quack).setVisible(true);
            if (isStat.equals("true")) {
                navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(true);


            } else {
                navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(false);
            }
        }
        else {
            navigationView.getMenu().findItem(R.id.nav_actiondesc).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_hearing).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_list).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_registration).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_quack).setVisible(false);
        }



        errortextlayout.setVisibility(View.GONE);
        hidelayout.setVisibility(View.GONE);
        districtLayout.setVisibility(View.GONE);
        TehsilLayout.setVisibility(View.GONE);
        sectortypelayout.setVisibility(View.GONE);
        hcetypelayout.setVisibility(View.GONE);
        Council_Typelayout.setVisibility(View.GONE);
        subvisit_layout.setVisibility(View.GONE);
        regnoLayout.setVisibility(View.GONE);
        hcenameLayout.setVisibility(View.GONE);
        bed_layout.setVisibility(View.GONE);
        finalidlayout.setVisibility(View.GONE);
        cniclayout.setVisibility(View.GONE);
        phonelayout.setVisibility(View.GONE);
        Action_layout.setVisibility(View.GONE);

        Button btn_reset=(Button) findViewById(R.id.btn_reset);

        btn_reset.setOnClickListener(new Button.OnClickListener() {

                                        public void onClick(View v) {
                                            sectortypespinner.setSelection(0);
                                            lastvisitedspinner.setSelection(0);
                                            distancespinner.setSelection(0);
                                            status_spinner.setSelection(0);
                                            counciltypespiner.setSelection(0);
                                            hcetypespinner.setSelection(0);
                                            district_spinner.setSelection(0);
                                            tehsil_spinner.setSelection(0);
                                            actionspinner.setSelection(0);
                                            bed_to.setText("");
                                            bed_from.setText("");
                                            hcenameEdit.setText("");
                                            RegnoEdit.setText("");
                                            finalidEdit.setText("");
                                            cnicEdit.setText("");
                                            phoneNoEdit.setText("");
                                            errortextlayout.setVisibility(View.GONE);
                                        }
                                    });

        showlayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                   // if (simpleSwitch1.isChecked()) {
                                    districtLayout.setVisibility(View.VISIBLE);
                                    TehsilLayout.setVisibility(View.VISIBLE);
                                    sectortypelayout.setVisibility(View.VISIBLE);
                                    hcetypelayout.setVisibility(View.VISIBLE);
                                    Council_Typelayout.setVisibility(View.VISIBLE);
                                    hidelayout.setVisibility(View.VISIBLE);
                                    showlayout.setVisibility(View.GONE);
                                    regnoLayout.setVisibility(View.VISIBLE);
                                    hcenameLayout.setVisibility(View.VISIBLE);
                                    bed_layout.setVisibility(View.VISIBLE);
                                    finalidlayout.setVisibility(View.VISIBLE);
                                    cniclayout.setVisibility(View.VISIBLE);
                                    phonelayout.setVisibility(View.VISIBLE);

           /*     }
                else {
                    distanceLayout.setVisibility(View.GONE);
                    regnoLayout.setVisibility(View.GONE);
                    hcenameLayout.setVisibility(View.GONE);
                    Council_Typelayout.setVisibility(View.GONE);
                    bed_layout.setVisibility(View.GONE);
                    lastvisit_layout.setVisibility(View.GONE);
                    Action_layout.setVisibility(View.GONE);
                }*/
            }
        });

        hidelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // if (simpleSwitch1.isChecked()) {
                districtLayout.setVisibility(View.GONE);
                TehsilLayout.setVisibility(View.GONE);
                sectortypelayout.setVisibility(View.GONE);
                hcetypelayout.setVisibility(View.GONE);
                Council_Typelayout.setVisibility(View.GONE);
                hidelayout.setVisibility(View.GONE);
                showlayout.setVisibility(View.VISIBLE);
               // distanceLayout.setVisibility(View.GONE);
                regnoLayout.setVisibility(View.GONE);
                hcenameLayout.setVisibility(View.GONE);
               // plancodelayout.setVisibility(View.GONE);
                bed_layout.setVisibility(View.GONE);
              //  lastvisit_layout.setVisibility(View.GONE);
                finalidlayout.setVisibility(View.GONE);
                cniclayout.setVisibility(View.GONE);
                phonelayout.setVisibility(View.GONE);

           /*     }
                else {
                    distanceLayout.setVisibility(View.GONE);
                    regnoLayout.setVisibility(View.GONE);
                    hcenameLayout.setVisibility(View.GONE);
                    Council_Typelayout.setVisibility(View.GONE);
                    bed_layout.setVisibility(View.GONE);
                    lastvisit_layout.setVisibility(View.GONE);
                    Action_layout.setVisibility(View.GONE);
                }*/
            }
        });
        if (gps.canGetLocation()) {
         /*   pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();*/
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
    //---------------------------------Search By Spinner--------------------------------------------
/*
        String[] Searchby=
                {"Please Select","Distance", "District","PHC Registration No","HCE Name"};
        ArrayAdapter<String> searchbyadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, Searchby) {
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
        searchbyadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchbyspinner.setAdapter(searchbyadapter);
        searchbyspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                searchbytext = parent.getItemAtPosition(position).toString();
                if(searchbytext=="Distance"){
                    districtLayout.setVisibility(View.GONE);
                    TehsilLayout.setVisibility(View.GONE);
                    distanceLayout.setVisibility(View.VISIBLE);
                    Loc_layout.setVisibility(View.VISIBLE);
                    regnoLayout.setVisibility(View.GONE);
                    hcenameLayout.setVisibility(View.GONE);
                    sectortypelayout.setVisibility(View.VISIBLE);
                    Council_Typelayout.setVisibility(View.VISIBLE);
                    HCSstatus_layout.setVisibility(View.VISIBLE);
                    hcetypelayout.setVisibility(View.VISIBLE);
                    bed_layout.setVisibility(View.VISIBLE);
                }
                else if(searchbytext=="District"){
                    districtLayout.setVisibility(View.VISIBLE);
                    TehsilLayout.setVisibility(View.VISIBLE);
                    distanceLayout.setVisibility(View.GONE);
                    Loc_layout.setVisibility(View.GONE);
                    regnoLayout.setVisibility(View.GONE);
                    hcenameLayout.setVisibility(View.GONE);
                    sectortypelayout.setVisibility(View.VISIBLE);
                    Council_Typelayout.setVisibility(View.VISIBLE);
                    HCSstatus_layout.setVisibility(View.VISIBLE);
                    hcetypelayout.setVisibility(View.VISIBLE);
                    bed_layout.setVisibility(View.VISIBLE);
                }
                else if(searchbytext=="PHC Registration No") {
                    regnoLayout.setVisibility(View.VISIBLE);
                    hcenameLayout.setVisibility(View.GONE);
                    districtLayout.setVisibility(View.GONE);
                    TehsilLayout.setVisibility(View.GONE);
                    distanceLayout.setVisibility(View.GONE);
                    Loc_layout.setVisibility(View.GONE);
                    sectortypelayout.setVisibility(View.GONE);
                    Council_Typelayout.setVisibility(View.GONE);
                    HCSstatus_layout.setVisibility(View.GONE);
                    hcetypelayout.setVisibility(View.GONE);
                    bed_layout.setVisibility(View.GONE);
                }
                else if(searchbytext=="HCE Name") {
                    regnoLayout.setVisibility(View.GONE);
                    hcenameLayout.setVisibility(View.VISIBLE);
                    districtLayout.setVisibility(View.GONE);
                    TehsilLayout.setVisibility(View.GONE);
                    distanceLayout.setVisibility(View.GONE);
                    Loc_layout.setVisibility(View.GONE);
                     sectortypelayout.setVisibility(View.GONE);
                     Council_Typelayout.setVisibility(View.GONE);
                     HCSstatus_layout.setVisibility(View.GONE);
                     hcetypelayout.setVisibility(View.GONE);
                     bed_layout.setVisibility(View.GONE);
                }
                if(searchbytext=="Please Select"){
                    districtLayout.setVisibility(View.GONE);
                    TehsilLayout.setVisibility(View.GONE);
                    distanceLayout.setVisibility(View.GONE);
                    Loc_layout.setVisibility(View.GONE);
                    regnoLayout.setVisibility(View.GONE);
                    hcenameLayout.setVisibility(View.GONE);
                    sectortypelayout.setVisibility(View.VISIBLE);
                    Council_Typelayout.setVisibility(View.VISIBLE);
                    HCSstatus_layout.setVisibility(View.VISIBLE);
                    hcetypelayout.setVisibility(View.VISIBLE);
                    bed_layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

*/

        //---------------------------------Search By Spinner--------------------------------------------
        distances= dataManager.getdistance();
        ArrayAdapter<String> distanceadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getDistance()) {
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
        distanceadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distancespinner.setAdapter(distanceadapter);
        distancespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                distancetext = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sectorTypes= dataManager.getSectorTypes();
         sectortypeadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getsectorTypes()) {
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
        sectortypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sectortypespinner.setAdapter(sectortypeadapter);
        sectortypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
               // ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                sectortypetext = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        councilTypes= dataManager.getCouncilTypes();
       /* String[] counciltype =
                {"Select Council Type", "NCH", "NCT","PMDC", "PNC"};*/

        ArrayAdapter<String> counciltypeadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getCouncilTypes()) {
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
        counciltypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        counciltypespiner.setAdapter(counciltypeadapter);
        counciltypespiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
               // ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                counciltypetext = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        regStatuses=dataManager.getRegStatus();
        ArrayAdapter<String> status_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getRegStatus()) {
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
        status_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status_spinner.setAdapter(status_spinneradapter);
        status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
              //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                hcestatusText = parent.getItemAtPosition(position).toString();
                hcestatusID= regStatuses.get(position).getRegStatus_id();
                if(hcestatusID.equals("5")) {
                    Action_layout.setVisibility(View.VISIBLE);
                }
                else {
                    Action_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




       // getPackages();
       /* String[] hcsptype =
                {"Select District Type",
                        "Acupuncturist",
                        "Addiction Treatment Center",
                        "Advance Imaging Lab",
                        "BHU",
                        "BHU,Basic Health Unit jabbi",
                        "CMW Clinic",
                        "Collection Center",
                        "Collection Centre",
                        "Cosmetic Surgery Clinic",
                        "Dental Hyginist",
                        "Dentist",
                        "DHQ",
                        "DHQ/Teaching",
                        "Dialysis Center",
                        "Dispensary",
                        "FHC",
                        "FP Clinic",
                        "GP",
                        "GP,single man clinic",
                        "GRD",
                        "Gyne Clinic",
                        "Hair Transplant",
                        "Hakim",
                        "Homeopath",
                        "Homeopath,HOMEOPATH",
                        "Homeopath,homeopathic clinic",
                        "Homeopath,Homoeopathic Clinic",
                        "Homeopath,optometry refrectionist",
                        "Homeopathic clinic",
                        "Hospital",
                        "Hospital,Rehabilitation_ Physiotherapy Dept.",
                        "Imaging Lab",
                        "Lab",
                        "LHV Clinic",
                        "Main Lab",
                        "Maternity Home",
                        "MCHC",
                        "Midwifery Clinic",
                        "Mobile Health Clinic",
                        "Multiple Specialty",
                        "Multiple Specialty,Psychotharepies and Homeopathy",
                        "Non-Teaching",
                        "Nursing Home",
                        "Pathalogy + Advance Imaging Lab",
                        "Pathalogy + Imaging Lab",
                        "Pathalogy Lab",
                        "PESSI",
                        "Physiotherapist",
                        "Poly Clinic",
                        "PWD",
                        "RHC",
                        "Single Speciality",
                        "Single Specialty",
                        "SSD",
                        "SSEC",
                        "SSMC",
                        "Teaching",
                        "THQ",
                        "Wapda"

                };*/

        orgTypes= dataManager.getOrgTypesList();
        ArrayAdapter<String> hcetypeadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getOrgTypes()) {
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

        hcetypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hcetypespinner.setAdapter(hcetypeadapter);
        hcetypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                hceTypetext = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


////////////////////////////////////////////////district////////////////////////////////////////

        String Division="Please Select";
        districts= dataManager.getDistrictList(Division);

        ArrayAdapter<String> districtadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getDistrict()) {
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
        districtadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        district_spinner.setAdapter(districtadapter);
        district_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                districtText = parent.getItemAtPosition(position).toString();
                getTehsil();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////////////////////////////////////////////////Last visited status////////////////////////////////////////


        actionTypeList= dataManager.getActionstype(Roleid);
        ArrayAdapter<String> lastvisitadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getactionTypes()) {
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
        lastvisitadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lastvisitedspinner.setAdapter(lastvisitadapter);
        lastvisitedspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                lastvisitedText = parent.getItemAtPosition(position).toString();

              if(lastvisitedText.equals("Not Visited")){
                  lastvisitedID="-1";
                  subactionTypeID="0";
                  subvisit_layout.setVisibility(View.GONE);
              }
              else {
                  lastvisitedID=actionTypeList.get(position).getActionType_Id();
                  if(lastvisitedID.equals("0")){
                      subvisit_layout.setVisibility(View.GONE);
                  }
                  else {
                      subvisit_layout.setVisibility(View.VISIBLE);
                      getsubactionTypes();
                  }


              }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ////////////////////////////////////////////////Action////////////////////////////////////////

        updateStatL1s= dataManager.getUpdateStatus();

        ArrayAdapter<String> actionadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getUpdateStatus()) {
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
        actionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionspinner.setAdapter(actionadapter);
        actionspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                actionText = parent.getItemAtPosition(position).toString();
               // actionID=updateStatL1s.get(position).getUpdatedStatL1_Id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Button btn_find=(Button) findViewById(R.id.btn_find);

        btn_find.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v)
            {   int Btonumber=0;
                int Bfromnumber = 0;
                int count=0;
                BfromText = bed_from.getText().toString();
                BtoText = bed_to.getText().toString();
                RegnoText=RegnoEdit.getText().toString();
                hcenameText=hcenameEdit.getText().toString();
                finalidText = finalidEdit.getText().toString();
                Cnic = cnicEdit.getText().toString();
                Phone = phoneNoEdit.getText().toString();

             if(finalidText.equals("") && hcenameText.equals("") && Cnic.equals("") && Phone.equals("") && RegnoText.equals("")) {
                 if ((districtText.equals("Please Select") || districtText.equals("")) && (tehsilText.equals("Please Select") || tehsilText.equals("")) && (distancetext.equals("Please Select") || distancetext.equals(""))) {
                     errortextlayout.setVisibility(View.VISIBLE);
                     count++;
                 }
             }

                if(!BfromText.equals("")) {
                     Bfromnumber = Integer.parseInt(BfromText);
                }
                if(!BtoText.equals("")) {
                     Btonumber = Integer.parseInt(BtoText);
                }


                if(Btonumber < Bfromnumber){
                    Toast.makeText(context, "Bed Strength: From should always be less than to", Toast.LENGTH_SHORT).show();
                    count=1;
                }
                if(count<1) {
                    if(hcestatusID.equals("5")){

                        sectortypetext="Private";
                    }
                    if (sectortypetext.equals("Please Select")) {
                        sectortypetext = "";
                    }
                    if (counciltypetext.equals("Please Select")) {
                        counciltypetext = "";
                    }
                    if (hceTypetext.equals("Please Select")) {
                        hceTypetext = "";
                    }
                    if (hcestatusText.equals("Please Select")) {
                        hcestatusText = "";
                    }
                    if (distancetext.equals("Please Select")) {
                        distancetext = "";
                    }
                    if (districtText.equals("Please Select")) {
                        districtText = "";
                    }
                    if (tehsilText.equals("Please Select")) {
                        tehsilText = "";
                    }
                    if(hcestatusID.equals("0")){
                        hcestatusID="";
                    }
                    if (actionText.equals("Please Select")) {
                        actionText = "";
                    }

                if((!districtText.equals("") || !tehsilText.equals(""))&& (hcestatusID.equals("4")|| hcestatusID.equals("5")) ) {
                    if(tehsilText.equals("")){
                        Toast.makeText(context, "Please select tehsil", Toast.LENGTH_SHORT).show();

                    }
                    else if(districtText.equals("")){
                        Toast.makeText(context, "Please select district", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        DownloadQuackClusterActivity downloadQuackClusterActivity = new DownloadQuackClusterActivity(context, sectortypetext, counciltypetext, hceTypetext, hcestatusID, districtText, tehsilText, distancetext, BfromText, BtoText, lastvisitedID,subactionTypeID, RegnoText, hcenameText, email, password, username, isEdit, finalidText, actionText,Cnic,Phone);
                    }
                }
    else {
        Intent firstpage = new Intent(context, SummMapListTabs.class);
        firstpage.putExtra("dataType", sectortypetext);
        firstpage.putExtra("registrationType", counciltypetext);
        firstpage.putExtra("orgType", hceTypetext);
        firstpage.putExtra("hcestatus", hcestatusID);
        firstpage.putExtra("districtText", districtText);
        firstpage.putExtra("tehsilText", tehsilText);
        firstpage.putExtra("distancetext", distancetext);
        firstpage.putExtra("BfromText", BfromText);
        firstpage.putExtra("BtoText", BtoText);
        firstpage.putExtra("lastvisitedText", lastvisitedID);
        firstpage.putExtra("subactionTypeID", subactionTypeID);
        firstpage.putExtra("RegnoText", RegnoText);
        firstpage.putExtra("hcenameText", hcenameText);
        firstpage.putExtra("email", email);
        firstpage.putExtra("Password", password);
        firstpage.putExtra("username", username);
        firstpage.putExtra("isEdit", isEdit);
        firstpage.putExtra("finalidText", finalidText);
        firstpage.putExtra("QuackType", actionText);
        firstpage.putExtra("Cnic",Cnic);
        firstpage.putExtra("Phone", Phone);
        context.startActivity(firstpage);
            }
         }
            }
        });

        // Initialize notification manager
        notificationManager = NotificationManager.getInstance(this);
        
        // Start notification service
        notificationManager.startNotificationService();
        
        // Register for notification count updates
        notificationManager.registerNotificationCountListener(this);
        
        // Fetch notifications initially
        fetchNotifications();
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        // Register for notification count updates
        if (notificationManager != null) {
            notificationManager.registerNotificationCountListener(this);
        }
        
        // Fetch notifications
        fetchNotifications();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        
        // Unregister notification count listener
        if (notificationManager != null) {
            notificationManager.unregisterNotificationCountListener();
        }
    }
    
    /**
     * Fetch notifications from the API
     */
    private void fetchNotifications() {
        if (notificationManager != null) {
            notificationManager.fetchNotifications(new NotificationApiClient.NotificationResponseListener() {
                @Override
                public void onResponse(List<NotificationModel> notifications) {
                    // Update notification count
                    updateNotificationCount(notifications.size());
                }
                
                @Override
                public void onError(String error) {
                    Log.e("FilterActivity", "Error fetching notifications: " + error);
                }
            });
        }
    }
    
    /**
     * Handle notification count updates from the broadcast receiver
     */
    @Override
    public void onNotificationCountUpdated(int count) {
        updateNotificationCount(count);
    }
    
    /**
     * Update the notification count badge
     */
    private void updateNotificationCount(int count) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (notificationCount != null) {
                    if (count > 0) {
                        notificationCount.setVisibility(View.VISIBLE);
                        notificationCount.setText(String.valueOf(count));
                    } else {
                        notificationCount.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gps = new CurrentLocation(context);
                    // Check if GPS enabled
                    if (gps.canGetLocation()) {

                        cur_latitude = gps.getLatitude();
                        cur_longitude = gps.getLongitude();
                        // latlangListener.onlatlang(cur_latitude, cur_longitude);

                    } else {
                        // Can't get location.
                        // GPS or network is not enabled.
                        // Ask user to enable GPS/network in settings.
                        gps.showSettingsAlert();
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(context, "You need to grant permission", Toast.LENGTH_SHORT).show();
                }
                return;

            }
        }
    }
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

    private ArrayList<String> getactionTypes() {

        ArrayList<String> actiontypelist = new ArrayList<String>();

        int i=0;
        for (ActionType actionType : actionTypeList) {
            String actiontypelists= actionType.getActionType();
            actiontypelist.add(actiontypelists);
            i++;
        }
        actiontypelist.add("Not Visited");
        return actiontypelist;
    }
    private ArrayList<String> getsubactionTypes() {
        subactionType= dataManager.getsubActionstype(lastvisitedID,Roleid);
        ArrayList<String> subactiontypelist = new ArrayList<String>();
        int i=0;
        for (subActionType subActionType : subactionType) {
            String subactiontype= subActionType.getSubactionType();
            subactiontypelist.add(subactiontype);
            i++;
        }

        ArrayAdapter<String> subactiontype_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, subactiontypelist) {
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
        subactionType_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                //subactionposition=position;
                subactionTypeText = parent.getItemAtPosition(position).toString();
                subactionTypeID=subactionType.get(position).getSubActionType_Id();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return subactiontypelist;
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
    private ArrayList<String> getDistance() {

        ArrayList<String> distancelist = new ArrayList<String>();
        int i=0;
        for (Distance distance : distances) {
            String distLists= distance.getDistance();
            distancelist.add(distLists);
            i++;
        }
        return distancelist;
    }
    private ArrayList<String> getRegStatus() {

        ArrayList<String> regstatuslist = new ArrayList<String>();
        int i=0;
        for (RegStatus regStatus : regStatuses) {
            String regstatLists= regStatus.getRegStatus();
            regstatuslist.add(regstatLists);
            i++;
        }
        return regstatuslist;
    }
    private ArrayList<String> getTehsil() {

        tehsils= dataManager.getTehsilList(districtText);
        ArrayList<String> TehsilList = new ArrayList<String>();
        int i=0;
        for (Tehsil tehsil : tehsils) {
            String Tehsillists= tehsil.getTehsil();
            if(!Tehsillists.equals("null"))
            TehsilList.add(Tehsillists);
            i++;
        }
        ArrayAdapter<String> tehsiladapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, TehsilList) {
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
        tehsiladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tehsil_spinner.setAdapter(tehsiladapter);
        tehsil_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                tehsilText = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return TehsilList;
    }
    private void setSpinnerError(Spinner spinner, String error){
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

    private void loadNavHeader() {

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

        Menu menu = navigationView.getMenu();

        // Check if the username matches Anas Nadeem or Faizan Niazi or Sami Ullah Khan
        if (username.equals("Faizan Niazi") || username.equals("Anas Nadeem") || username.equals("Sami Ullah Khan")) {
            menu.findItem(R.id.nav_registration).setVisible(true); // Show the item
        } else {
            menu.findItem(R.id.nav_registration).setVisible(false); // Hide the item
        }


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
//                    case R.id.nav_reportquack:
//                        startActivity(new Intent(context, ReportQuackActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
//                        drawer.closeDrawers();
//                        return true;
                    case R.id.nav_quack:
                        startActivity(new Intent(context, QuackActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;

                    case R.id.nav_actionsummary:
                        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                        String isStat = prefs.getString("isStat", null);//"No name defined" is the default value.
                        String UserID = prefs.getString("UserID", null); //0 is the default value.
                        if(isStat.equals("true")) {
                            startActivity(new Intent(context, DashboardTabs.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
                            drawer.closeDrawers();
                        }
                        else {
                            Toast.makeText(context, "You are not authorised!", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    case R.id.nav_actiondesc:
                        startActivity(new Intent(context, IndReportingActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_pwssearch:
                        startActivity(new Intent(context, PWSFilterActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_list:
                        startActivity(new Intent(context, DesealListing.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_hearing:
                        startActivity(new Intent(context, HearingStatusActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_registration:
                        startActivity(new Intent(context, RegistrationStatus.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
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
                    case R.id.nav_update:
                        // Show a dialog asking the user if they want to update
                        showUpdateDialog(); // Show the confirmation dialog
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


                    //default:
                     //   navItemIndex = 0;
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

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        // Get notification menu item
        final MenuItem menuItem = menu.findItem(R.id.action_notification);
        
        // Get notification icon view
        View actionView = menuItem.getActionView();
        notificationIcon = actionView.findViewById(R.id.notification_icon);
        notificationCount = actionView.findViewById(R.id.notification_count);
        
        // Set click listener for notification icon
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open notification list activity
                Intent intent = new Intent(FilterActivity.this, com.phc.cim.Activities.Notification.NotificationListActivity.class);
                startActivity(intent);
            }
        });
        
        return true;
    }

//-------------------------DownloadInspDetail Task------------------------------------------------

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

    private String getDirectionsUrl() {
        String url=null;
        String baseurl = context.getResources().getString(R.string.baseurl);

        url = baseurl + "GetTotalHCE?District="+districtText+"&Tehsil="+tehsilText+"&DataType="+sectortypetext+"&orgType="+hceTypetext+"&Councile="+counciltypetext+"&Status="+hcestatusText+"&Category=&From="+BfromText+"&To="+BtoText+"&Lvs="+lastvisitedText+"&RegNum="+RegnoText+"&HCEName="+hcenameText+"&Latitude="+cur_latitude+"&Longitude="+cur_longitude+"&Distance="+distancetext+"&Cnic="+Cnic+"&Phone="+Phone;
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

                    mylist = new ArrayList<HashMap<String, String>>();
                    for (int i = 0; i < json.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = json.getJSONObject(i);


                        map.put("latitude", e.getString("lat"));
                        map.put("longitude", e.getString("lng"));
                        map.put("address", e.getString("Address"));
                        map.put("name", e.getString("Name"));
                        map.put("status", e.getString("PhcLicenseType"));
                        map.put("MobileNumber", e.getString("ContactNo"));
                        map.put("final_id", e.getString("final_id"));
                        map.put("is_reg_council", e.getString("is_reg_council"));
                        map.put("is_reg_with_phc", e.getString("is_reg_with_phc"));
                        map.put("hcsp_cnic", e.getString("hcsp_cnic"));
                        map.put("hcsp_phone", e.getString("hcsp_phone"));


                        mylist.add(map);
                    }

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

            if (pDialog.isShowing())
                pDialog.dismiss();
            if(count==0){
                count=1;
            }

            if(result.size()>0) {
            }
            else {
                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
            }
        }

    }

    // increment notification count
    private void incrementNotificationCount() {
        notificationCounter++;
        updateNotificationCount(notificationCounter);
    }

    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Update Available")
                .setMessage("A new version is available. Do you want to update now?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If user clicks Yes, start the update process
                        startUpdateProcess();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If user clicks No, do nothing (cancel the update)
                        dialog.dismiss();
                    }
                });

        // Show the dialog
        builder.create().show();
    }

    private void startUpdateProcess() {
        if (isAppInstalled(context, "com.phc.cim")) { // Replace with your app's package name
            Toast.makeText(context, "Please uninstall the existing app before updating.", Toast.LENGTH_SHORT).show();
            return;
        }

        String apkUrl = "https://os.phc.org.pk/images/apk/app-release.apk";

        String fileName = "app-release.apk";
        File downloadFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);

        new DownloadFileTask().execute(apkUrl, downloadFile.getAbsolutePath());
    }

    // Method to check if the app is installed
    private boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    // AsyncTask to download the APK file
    private class DownloadFileTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            String apkUrl = params[0]; // The URL to download APK from
            String filePath = params[1]; // Path to save the APK file

            try {
                // Connect to the server to fetch the APK file
                URL url = new URL(apkUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return null;
                }

                // Create an input stream to read the file
                InputStream input = connection.getInputStream();
                FileOutputStream output = new FileOutputStream(filePath);

                byte[] data = new byte[4096];
                int count;
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
                return filePath; // Return the path to the downloaded file
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String filePath) {
            if (filePath != null) {
                installAPK(filePath); // Call the install method after download completion
            } else {
                Toast.makeText(context, "Download failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to install the downloaded APK
    private void installAPK(String filePath) {
        File apkFile = new File(filePath);
        if (!apkFile.exists()) {
            Toast.makeText(context, "Downloaded file not found.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(context, getPackageName() + ".provider", apkFile);
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            Uri apkUri = Uri.fromFile(apkFile);
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
