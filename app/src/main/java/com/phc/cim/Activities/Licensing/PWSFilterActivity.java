package com.phc.cim.Activities.Licensing;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import com.phc.cim.Activities.Common.AboutusActivity;
import com.phc.cim.Activities.Common.ChangePasswordActivity;
import com.phc.cim.Activities.Common.DesealListing;
import com.phc.cim.Activities.Common.FilterActivity;
import com.phc.cim.Activities.Common.HearingStatusActivity;
import com.phc.cim.Activities.Common.IndReportingActivity;
import com.phc.cim.Activities.Common.QuackActivity;
import com.phc.cim.Activities.Common.RegistrationStatus;
import com.phc.cim.Activities.Common.ReportQuackActivity;
import com.phc.cim.Activities.Inspection.InspectionFilterActivity;
import com.phc.cim.Activities.Inspection.InspectionVisitsActivity;
import com.phc.cim.DataElements.ActionType;
import com.phc.cim.DataElements.CouncilType;
import com.phc.cim.DataElements.Distance;
import com.phc.cim.DataElements.PWSDistrict;
import com.phc.cim.DataElements.PWSOrgType;
import com.phc.cim.DataElements.RegStatus;
import com.phc.cim.DataElements.SectorType;
import com.phc.cim.DataElements.Tehsil;
import com.phc.cim.DataElements.UpdateStatL1;
import com.phc.cim.DataElements.UpdateStatL2;
import com.phc.cim.DataElements.subActionType;
import com.phc.cim.DownloadClases.DownloadPWSData;
import com.phc.cim.Extra.HomeFragment;
import com.phc.cim.Extra.NotificationFragment;
import com.phc.cim.Extra.PhotosFragment;
import com.phc.cim.Extra.SettingFragment;
import com.phc.cim.Extra.VideosFragment;
import com.phc.cim.Managers.DataManager;
import com.phc.cim.Managers.DatabaseManager;
import com.phc.cim.Others.CurrentLocation;
import com.phc.cim.Others.Logout;
import com.phc.cim.R;
import com.phc.cim.TabsActivities.DashboardTabs;

import java.util.ArrayList;
import java.util.HashMap;

public class PWSFilterActivity extends AppCompatActivity {
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
    LinearLayout Action_layout;
    CurrentLocation gps;
    double cur_latitude;
    double cur_longitude;
    String searchbytext = "";
    String distancetext = "";
    String sectortypetext = "";
    String counciltypetext = "";
    String hceTypetext = "";
    String hcestatusText = "";
    String hcestatusID = "";
    String districtText = "";
    String tehsilText = "";
    String BfromText = "";
    String BtoText = "";
    String email = "";
    private Boolean userchangesec = true;
    String lastvisitedText = "";
    String lastvisitedID;
    String actionText = "";
    String hcenameText = "";
    String plancodeText = "";
    String RegnoText = "";
    String cnicText = "";
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
    EditText cnicEdit;
    EditText finalidEdit;

    Context context;
    int count;
    DataManager dataManager;
    DatabaseManager databaseManager;
    ArrayList<subActionType> subactionType = null;
    ArrayList<subActionType> subactionTypeselected = null;
    String subactionTypeText;
    String subactionTypeID;
    String subactiontseltext;
    ArrayList<PWSDistrict> districts;
    ArrayList<Tehsil> tehsils;
    ArrayList<SectorType> sectorTypes;
    ArrayList<CouncilType> councilTypes;
    ArrayList<PWSOrgType> orgTypes;
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
    private int versionCode = 0;
    String Roleid;
    String appURI = "";
    String time1;
    Switch simpleSwitch1;
    String password;
    String isEdit;
    String isStat;
    String UserID;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pws_activity_filter);
        context = this;
        //ImageView imageView = (ImageView) findViewById(R.id.splashImage);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gps = new CurrentLocation(context);
        mHandler = new Handler();
        TextView t2 = (TextView) findViewById(R.id.text2);
        //Linkify.addLinks(t2, Linkify.ALL);
        //t2.setMovementMethod(LinkMovementMethod.getInstance());
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        //fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        //imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getString(R.string.nav_item_filter_titles);
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        isStat = prefs.getString("isStat", null);//"No name defined" is the default value.
        email = prefs.getString("email", null);//"No name defined" is the default value.
        password = prefs.getString("password", null);//"No name defined" is the default value.
        username = prefs.getString("username", null);//"No name defined" is the default value.
        isEdit = prefs.getString("isEdit", null);//"No name defined" is the default value.
        Roleid = prefs.getString("RoleID", null); //0 is the default value.
        UserID = prefs.getString("UserID", null); //0 is the default value.
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
        dataManager = new DataManager(context);
        databaseManager = new DatabaseManager(context);
        bed_from = (EditText) findViewById(R.id.bed_from);
        bed_to = (EditText) findViewById(R.id.bed_to);
        hcenameEdit = (EditText) findViewById(R.id.hce_name);
        RegnoEdit = (EditText) findViewById(R.id.reg_no);
        cnicEdit = (EditText) findViewById(R.id.cnicEdit);
        hcetypespinner = (Spinner) findViewById(R.id.HCE_Typespinner);
        district_spinner = (Spinner) findViewById(R.id.districtSpinner);


       /* sectortypespinner.setSelection(0);
        regnoLayout = (LinearLayout) findViewById(R.id.reg_noL);
       hcenameLayout = (LinearLayout) findViewById(R.id.hce_nameL);
       // plancodelayout  = (LinearLayout) findViewById(R.id.plancodelayout);
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
       // lastvisit_layout = (LinearLayout) findViewById(R.id.lastvisit_layout);
        finalidlayout = (LinearLayout) findViewById(R.id.finalidlayout);
      Action_layout = (LinearLayout) findViewById(R.id.Action_layout);*/


        if (Roleid.equals("1")) {
            navigationView.getMenu().findItem(R.id.nav_actiondesc).setVisible(true);
            if (isStat.equals("true")) {
                navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(true);


            } else {
                navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(false);
            }
        } else {
            navigationView.getMenu().findItem(R.id.nav_actiondesc).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(false);
        }
       /* districtLayout.setVisibility(View.GONE);
        TehsilLayout.setVisibility(View.GONE);
        distanceLayout.setVisibility(View.GONE);
        Loc_layout.setVisibility(View.GONE);
        regnoLayout.setVisibility(View.GONE);
        hcenameLayout.setVisibility(View.GONE);*/


/*        errortextlayout.setVisibility(View.GONE);
        hidelayout.setVisibility(View.GONE);
        districtLayout.setVisibility(View.GONE);
        TehsilLayout.setVisibility(View.GONE);
        sectortypelayout.setVisibility(View.GONE);
        hcetypelayout.setVisibility(View.GONE);
        Council_Typelayout.setVisibility(View.GONE);
        subvisit_layout.setVisibility(View.GONE);
        regnoLayout.setVisibility(View.GONE);
        hcenameLayout.setVisibility(View.GONE);
//        plancodelayout.setVisibility(View.GONE);
        bed_layout.setVisibility(View.GONE);
      //  lastvisit_layout.setVisibility(View.GONE);
        finalidlayout.setVisibility(View.GONE);

       Action_layout.setVisibility(View.GONE);*/
     /*   if (simpleSwitch1.isChecked()) {
            distanceLayout.setVisibility(View.VISIBLE);
            regnoLayout.setVisibility(View.VISIBLE);
            hcenameLayout.setVisibility(View.VISIBLE);
            Council_Typelayout.setVisibility(View.VISIBLE);
            bed_layout.setVisibility(View.VISIBLE);
            lastvisit_layout.setVisibility(View.VISIBLE);
            Action_layout.setVisibility(View.VISIBLE);
        }
        else {*/

        //}
        Button btn_reset = (Button) findViewById(R.id.btn_reset);

        btn_reset.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                hcetypespinner.setSelection(0);
                district_spinner.setSelection(0);

                // plancodeEdit.setText("");
                bed_to.setText("");
                bed_from.setText("");
                hcenameEdit.setText("");
                RegnoEdit.setText("");
                cnicEdit.setText("");
                // errortextlayout.setVisibility(View.GONE);
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

/*

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
       */
/* String[] counciltype =
                {"Select Council Type", "NCH", "NCT","PMDC", "PNC"};*//*


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
*/


        orgTypes = databaseManager.getPWSorgType();
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

        String Division = "Please Select";
        districts = databaseManager.getPWSDistrict();

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
                //getTehsil();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button btn_find = (Button) findViewById(R.id.btn_find);

        btn_find.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                int Btonumber = 0;
                int Bfromnumber = 0;
                int count = 0;
                BfromText = bed_from.getText().toString();
                BtoText = bed_to.getText().toString();
                RegnoText = RegnoEdit.getText().toString();
                hcenameText = hcenameEdit.getText().toString();
                cnicText = cnicEdit.getText().toString();

                if (!BfromText.equals("")) {
                    Bfromnumber = Integer.parseInt(BfromText);
                }
                if (!BtoText.equals("")) {
                    Btonumber = Integer.parseInt(BtoText);
                }


                if (Btonumber < Bfromnumber) {
                    Toast.makeText(context, "Bed Strength: From should always be less than to", Toast.LENGTH_SHORT).show();
                    count = 1;
                }
                if (count < 1) {
                    if (hceTypetext.equals("Please Select")) {
                        hceTypetext = "";
                    }
                    if (districtText.equals("Please Select")) {
                        districtText = "";
                    } else {
                        DownloadPWSData downloadPWSData = new DownloadPWSData(context, hceTypetext, districtText, BfromText, BtoText, RegnoText, hcenameText, cnicText);
 /*
        Intent firstpage = new Intent(context, PWSDetailListActivity.class);
        firstpage.putExtra("hceTypetext",hceTypetext);
        firstpage.putExtra("districtText",districtText);
        firstpage.putExtra("BfromText",BfromText);
        firstpage.putExtra("BtoText",BtoText);
        firstpage.putExtra("RegnoText",RegnoText);
        firstpage.putExtra("hcenameText",hcenameText);
        firstpage.putExtra("cnicText",cnicText);
        context.startActivity(firstpage);*/
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
        int i = 0;
        for (PWSOrgType orgType : orgTypes) {
            String orgTypess = orgType.getOrgType();
            if ((!orgTypess.equals("null")) && (!orgTypess.equals("")))
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
        for (PWSDistrict district : districts) {
            String districtLists = district.getDistrict();
            if ((!districtLists.equals("null")) && (!districtLists.equals("")) && (!districtLists.equals("-Please Select -")) && (!districtLists.equals("-Please Select-")))
                districtList.add(districtLists);
            i++;
        }
        return districtList;
    }

    private ArrayList<String> getactionTypes() {

        ArrayList<String> actiontypelist = new ArrayList<String>();

        int i = 0;
        for (ActionType actionType : actionTypeList) {
            String actiontypelists = actionType.getActionType();
            actiontypelist.add(actiontypelists);
            i++;
        }
        actiontypelist.add("Not Visited");
        return actiontypelist;
    }

    private ArrayList<String> getsubactionTypes() {
        subactionType = dataManager.getsubActionstype(lastvisitedID, Roleid);
        ArrayList<String> subactiontypelist = new ArrayList<String>();
        int i = 0;
        for (subActionType subActionType : subactionType) {
            String subactiontype = subActionType.getSubactionType();
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
                subactionTypeID = subactionType.get(position).getSubActionType_Id();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return subactiontypelist;
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

    private ArrayList<String> getDistance() {

        ArrayList<String> distancelist = new ArrayList<String>();
        int i = 0;
        for (Distance distance : distances) {
            String distLists = distance.getDistance();
            distancelist.add(distLists);
            i++;
        }
        return distancelist;
    }

    private ArrayList<String> getRegStatus() {

        ArrayList<String> regstatuslist = new ArrayList<String>();
        int i = 0;
        for (RegStatus regStatus : regStatuses) {
            String regstatLists = regStatus.getRegStatus();
            regstatuslist.add(regstatLists);
            i++;
        }
        return regstatuslist;
    }

    private ArrayList<String> getTehsil() {

        tehsils = dataManager.getTehsilList(districtText);
        ArrayList<String> TehsilList = new ArrayList<String>();
        int i = 0;
        for (Tehsil tehsil : tehsils) {
            String Tehsillists = tehsil.getTehsil();
            if (!Tehsillists.equals("null"))
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

    /*
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
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

        // Check if the username matches
        if (username.equals("Faizan Niazi") || username.equals("Ali Abdul Mateen") || username.equals("Sami Ullah Khan")) {
            menu.findItem(R.id.nav_registration).setVisible(true); // Show the item
        } else {
            menu.findItem(R.id.nav_registration).setVisible(false); // Hide the item
        }

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        if (Roleid.equals("3")) {
                            startActivity(new Intent(context, InspectionFilterActivity.class));

                        } else {
                            startActivity(new Intent(context, FilterActivity.class).putExtra("email", email).putExtra("password", password).putExtra("username", username).putExtra("isEdit", isEdit));

                        }
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_reportquack:
                        startActivity(new Intent(context, ReportQuackActivity.class).putExtra("email", email).putExtra("password", password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_quack:
                        startActivity(new Intent(context, QuackActivity.class).putExtra("email", email).putExtra("password", password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_actionsummary:
                        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                        String isStat = prefs.getString("isStat", null);//"No name defined" is the default value.
                        String UserID = prefs.getString("UserID", null); //0 is the default value.
                        if (isStat.equals("true")) {
                            startActivity(new Intent(context, DashboardTabs.class).putExtra("email", email).putExtra("password", password).putExtra("username", username).putExtra("isEdit", isEdit));
                            drawer.closeDrawers();
                        } else {
                            Toast.makeText(context, "You are not authorised!", Toast.LENGTH_SHORT).show();
                        }
                        return true;


                    case R.id.nav_actiondesc:
                        if (Roleid.equals("3")) {
                            startActivity(new Intent(context, InspectionVisitsActivity.class));

                        } else {
                            startActivity(new Intent(context, IndReportingActivity.class).putExtra("email", email).putExtra("password", password).putExtra("username", username).putExtra("isEdit", isEdit));

                        }
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
                    case R.id.nav_registration:
                        startActivity(new Intent(context, RegistrationStatus.class));
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
        return true;
    }
}
