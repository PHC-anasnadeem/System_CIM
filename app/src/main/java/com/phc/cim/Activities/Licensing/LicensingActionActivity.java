package com.phc.cim.Activities.Licensing;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.phc.cim.Activities.Common.AboutusActivity;
import com.phc.cim.Activities.Common.ChangePasswordActivity;
import com.phc.cim.Activities.Common.DesealListing;
import com.phc.cim.Activities.Common.FilterActivity;
import com.phc.cim.Activities.Common.HearingStatusActivity;
import com.phc.cim.Activities.Common.QuackActivity;
import com.phc.cim.Activities.GalleryActivity;
import com.phc.cim.Activities.Common.IndReportingActivity;
import com.phc.cim.Activities.Common.ReportQuackActivity;
import com.phc.cim.DataElements.ActionType;
import com.phc.cim.DataElements.CouncilType;
import com.phc.cim.DataElements.subActionType;
import com.phc.cim.Extra.HomeFragment;
import com.phc.cim.Extra.NotificationFragment;
import com.phc.cim.Extra.PhotosFragment;
import com.phc.cim.Extra.SettingFragment;
import com.phc.cim.Extra.VideosFragment;
import com.phc.cim.Managers.DataManager;
import com.phc.cim.Managers.DatabaseManager;
import com.phc.cim.Others.Config;
import com.phc.cim.Others.CurrentLocation;
import com.phc.cim.Others.Logout;
import com.phc.cim.R;
import com.phc.cim.TabsActivities.DashboardTabs;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;
import static com.phc.cim.Fragments.EditHCEFragment.MEDIA_TYPE_VIDEO;
import static com.phc.cim.Fragments.EditHCEFragment.RequestPermissionCode;
import static com.phc.cim.TabsActivities.DetailTabActivity.MEDIA_TYPE_IMAGE;

public class LicensingActionActivity extends AppCompatActivity {


    CurrentLocation gps;
    double cur_latitude;
    double cur_longitude;

    TextInputLayout regNoInput;
    TextInputLayout counTypeInput;
    TextInputLayout counNoInput;
    TextInputLayout Reg_No_lay;
    TextInputLayout fir_layout;
    private Boolean picTaken = false;
    private Boolean picreceved = false;
    private Boolean picUploaded = false;

    private Boolean picAttachement = false;
    Context context;
    int count = 0;
    int backcount = 0;
    private String filePath = null;
    String directoryPath = null;
    private Uri filePathURI;
    String visitDate;
    String LoctionVisitedTime = "";
    String actionTypeText;
    String actionTypeID;
    String subactionTypeText;
    String subactionTypeID;
    String firtext;
    String imageNAme = null;
    String firID = "0";
    String subactiontseltext;
    String actiontseltext;
    Spinner actionType_spinner;
    Spinner subactionType_spinner;
    Spinner counciltypespiner;
    Spinner fir_spinner;
    String roleid;
    ArrayList<ActionType> actionTypeList = null;
    ArrayList<subActionType> subactionType = null;
    ArrayList<ActionType> actionTypeselected = null;
    ArrayList<subActionType> subactionTypeselected = null;
    ArrayList<CouncilType> councilTypes;
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
    int PICK_IMAGE_REQUEST = 111;
    private Uri u = null;
    String appURI = "";
    String time1;
    String MID;
    String MText;
    String MID2;
    String MText2;
    Switch simpleSwitch1;
    String hce_nameText = "";
    String Reg_NoText = "";
    String coun_NoText = "";
    String final_id = "";
    String visited;
    String UploadedBy;
    TimePickerDialog timePickerDialog;
    int currentHour;
    int currentMinute;
    String amPm;
    //  String districtText="";
    //String sectortypetext="";
    //  String hceTypetext="";
//    String HCSPTypeText="";
    //   String RegstatusText="";
//    String counStatusText="";
    String counciltypetext = "";
    String counciltypeID = "";
    //    String RegType="";
    String email = null;
    String password;
    String isEdit;
    String username;
    //    String RecordLockedForUpdate="";
//    String total_beds="";
    String index;
    String firactionbit;
    String ftpbaseurl;
    String VisitedDate;
    String isFIRSubmit;
    // double latitude;
    //  double longitude;
    EditText coun_NoEdit;
    EditText hce_nameEdit;
    EditText Reg_no_edit;
    DataManager dataManager;
    TextView totalpics;
    TextView newquacktext;
    TextView vistedtext;
    int countPictures = 0;
    private DownloadManager downloadManager;
    private long downloadReference;
    int MY_REQUEST_CODE = 5;
    private ArrayList<String> _images;
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
    String jsonStr2, jsonStr0;
    String imagepath;
    String VisitedTime;
    EditText txtDateTime;
    Calendar myCalendar;
    EditText loctimePickerEdit;
    EditText comments;
    TextView errortext;
    String commentText, UserID;
    int actionposition;
    int subactionposition;
    int firposition;
    int councilposition;
    Bundle saveinstance;

    ArrayAdapter<String> actionadapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.licensing_action);
        context = this;


        pDialog = new ProgressDialog(context);
        actionType_spinner = (Spinner) findViewById(R.id.actionType_spinner);
        subactionType_spinner = (Spinner) findViewById(R.id.subactionType_spinner);
        counciltypespiner = (Spinner) findViewById(R.id.counType_spinner);
        //fir_spinner = (Spinner) findViewById(R.id.fir_spinner);
        txtDateTime = (EditText) findViewById(R.id.txtDateTime);
        loctimePickerEdit = (EditText) findViewById(R.id.loctimePicker);

        hce_nameEdit = (EditText) findViewById(R.id.hce_name);
        coun_NoEdit = (EditText) findViewById(R.id.council_no);
        Reg_no_edit = (EditText) findViewById(R.id.Reg_no_edit);
        totalpics = (TextView) findViewById(R.id.totalpics);
        newquacktext = (TextView) findViewById(R.id.newquacktext);
        vistedtext = (TextView) findViewById(R.id.vistedtext);
        errortext = (TextView) findViewById(R.id.errortext);

        comments = (EditText) findViewById(R.id.comments);
        newquacktext.setVisibility(View.GONE);
        dataManager = new DataManager(context);
        myCalendar = Calendar.getInstance(TimeZone.getDefault());
        if (comments.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        comments.setFocusableInTouchMode(true);
        comments.setFocusable(true);
        vistedtext.setVisibility(View.GONE);
        Intent intent = getIntent();
        errortext.setVisibility(View.GONE);
        // AddressText = (String) intent.getSerializableExtra("HCEAddress");
        //  districtText = (String) intent.getSerializableExtra("District");
        //sectortypetext = (String) intent.getSerializableExtra("SectorType");
        //   hceTypetext = (String) intent.getSerializableExtra("OrgType");
        //  HCSPTypeText = (String) intent.getSerializableExtra("HCSPType");
        //   HCSP_nameText = (String) intent.getSerializableExtra("HCSPName");
        //   HCSP_SOText = (String) intent.getSerializableExtra("HCSPSO");
        //    CNIC_Text = (String) intent.getSerializableExtra("HCSPCNIC");
        //   HCSP_ContactText = (String) intent.getSerializableExtra("HCSPContactNo");
        //   RegstatusText = (String) intent.getSerializableExtra("RegStatus");

        //   counStatusText = (String) intent.getSerializableExtra("CouncilStatus");

        //   RegType = (String) intent.getSerializableExtra("RegType");
        //  total_beds = (String) intent.getSerializableExtra("total_beds");
        //  RecordLockedForUpdate = (String) intent.getSerializableExtra("RecordLockedForUpdate");

        //  latitude = (double) intent.getSerializableExtra("latitude");
        //  longitude = (double) intent.getSerializableExtra("longitude");
        VisitedDate = (String) intent.getSerializableExtra("VisitedDate");
        actionTypeID = (String) intent.getSerializableExtra("ActionType");
        subactionTypeID = (String) intent.getSerializableExtra("SubActionType");
        isFIRSubmit = (String) intent.getSerializableExtra("isFIRSubmit");
        commentText = (String) intent.getSerializableExtra("Comments");
        final_id = (String) intent.getSerializableExtra("final_id");
        hce_nameText = (String) intent.getSerializableExtra("HCEName");
        Reg_NoText = (String) intent.getSerializableExtra("RegNum");
        counciltypetext = (String) intent.getSerializableExtra("CouncilName");
        coun_NoText = (String) intent.getSerializableExtra("CouncilNum");
        email = (String) intent.getSerializableExtra("email");
        password = (String) intent.getSerializableExtra("password");
        username = (String) intent.getSerializableExtra("username");
        isEdit = (String) intent.getSerializableExtra("isEdit");
        index = (String) intent.getSerializableExtra("index");
        UploadedBy = (String) intent.getSerializableExtra("UploadedBy");
        visited = (String) intent.getSerializableExtra("visited");
        VisitedTime = (String) intent.getSerializableExtra("VisitedTime");
        _images = (ArrayList<String>) getIntent().getSerializableExtra("imageurls");

        if (commentText != null) {
            comments.setText(commentText);
        }
    /*    if(isFIRSubmit!=null){
            if(isFIRSubmit.equals("1")){
                firtext="Yes";
            }
            if(isFIRSubmit.equals("0")){
                firtext="No";
            }

        }*/
        if (visited != null && visited.equals("1")) {
            vistedtext.setVisibility(View.VISIBLE);
            vistedtext.setText("Last visited by: " + UploadedBy);
        }
        if (VisitedTime != null)
            loctimePickerEdit.setText(VisitedTime);
        if (index.equals("0")) {
            newquacktext.setVisibility(View.VISIBLE);
        }
        // regNoInput = (TextInputLayout) findViewById(R.id.reg);
        counTypeInput = (TextInputLayout) findViewById(R.id.counclType);
        counNoInput = (TextInputLayout) findViewById(R.id.council);
        // fir_layout= (TextInputLayout) findViewById(R.id.fir_layout);
        Reg_No_lay = (TextInputLayout) findViewById(R.id.Reg_No_lay);


        hce_nameEdit.setEnabled(false);
        if (visited != null && visited.equals("1")) {
            hce_nameEdit.setText(index + ". " + hce_nameText);
        } else {
            hce_nameEdit.setText(index + ". " + final_id + " - " + hce_nameText);
        }
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
        //regNoInput.setVisibility(View.GONE);
        //counTypeInput.setVisibility(View.GONE);
        //counNoInput.setVisibility(View.GONE);
        //Reg_No_lay.setVisibility(View.GONE);
        //fir_layout.setVisibility(View.GONE);
  /*      BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/
        // load toolbar titles from string resources
        activityTitles = getResources().getString(R.string.nav_item_action_titles);
        ftpbaseurl = context.getResources().getString(R.string.ftpbaseurl);
        loadNavHeader();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, 42);
        }
        checkPermission();
        EnableRuntimePermission();
        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        if (coun_NoText != null) {
            coun_NoEdit.setText(coun_NoText);
        }
        if (Reg_NoText != null) {
            Reg_no_edit.setText(Reg_NoText);
        }

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        roleid = prefs.getString("RoleID", null);//"No name defined" is the default value.
        UserID = prefs.getString("UserID", null);//"No name defined" is the default value.
        String isStat = prefs.getString("isStat", null);//"No name defined" is the default value.
        if (roleid.equals("1")) {
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

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub


                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (VisitedDate != null && VisitedDate != "0") {
            String start_dt = VisitedDate;
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date date2 = null;
            try {
                date2 = (Date) formatter.parse(start_dt);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yy");
            String finalString = "";
            if (date2 != null)
                finalString = newFormat.format(date2);
            txtDateTime.setText(finalString);
        }
       /* else {
            txtDateTime.setText(sdf.format(myCalendar.getTime()));
        }*/

        txtDateTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        // initialize all your visual fields
        if (savedInstanceState != null) {
            actionType_spinner.setSelection(savedInstanceState.getInt("actionType_spinner", 0));
            // do this for each of your text views
        }
        loctimePickerEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            hourOfDay = hourOfDay - 12;
                            if (hourOfDay == 00) {
                                hourOfDay = 12;
                            }
                            amPm = "PM";
                        } else {
                            if (hourOfDay == 00) {
                                hourOfDay = 12;
                            }
                            amPm = "AM";
                        }
                        loctimePickerEdit.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });
        actionTypeList = dataManager.getActionstype(roleid);
        actionadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getactionTypes()) {
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
        actionType_spinner.setAdapter(actionadapter);
        if (actionTypeID != null) {
            getactionsel();
            int spinnerPosition = actionadapter.getPosition(actiontseltext);
            actionType_spinner.setSelection(spinnerPosition);
        }
        actionType_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

                actionType_spinner.setSelection(position);
                actionTypeText = parent.getItemAtPosition(position).toString();
                actionTypeID = actionTypeList.get(position).getActionType_Id();
                //firactionbit=actionTypeList.get(position).getFIRAssociated();
               /* if(firactionbit.equals("1")) {
                    fir_layout.setVisibility(View.VISIBLE);
                }
                else {
                    fir_layout.setVisibility(View.GONE);
                }*/
                getsubactionTypes();
                if (comments.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

                councilposition = position;
                counciltypetext = parent.getItemAtPosition(position).toString();
                counciltypeID = councilTypes.get(position).getSectorType_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //---------------------------------fir_spinner Spinner--------------------------------------------

    /*    String FIRsubmitted[]= {"Please Select","Yes","No"};
        //councilTypes= dataManager.getCouncilTypes();
        ArrayAdapter<String> fir_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, FIRsubmitted) {
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
        fir_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fir_spinner.setAdapter(fir_spinneradapter);

        if (firtext != null) {
            int spinnerPosition = fir_spinneradapter.getPosition(firtext);
            fir_spinner.setSelection(spinnerPosition);
        }
        fir_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

                 firposition=position;
                firtext = parent.getItemAtPosition(position).toString();
                if(firtext.equals("Yes")) {
                    firID = "1";
                }
                else if(firtext.equals("No")){
                    firID = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
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
        Button btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                int errorcount = 0;
                coun_NoText = coun_NoEdit.getText().toString();
                Reg_NoText = Reg_no_edit.getText().toString();
           /*     String day = "Day = " + simpleDatePicker.getDayOfMonth();
                String month = "Month = " + (simpleDatePicker.getMonth() + 1);
                String year = "Year = " + simpleDatePicker.getYear();*/

                visitDate = txtDateTime.getText().toString();
                LoctionVisitedTime = loctimePickerEdit.getText().toString();
                commentText = comments.getText().toString();
                if (visitDate.equals("")) {
                    txtDateTime.setError("Please select date");
                    errortext.setText("* Required fields are missing");
                    errorcount++;
                }
                if (LoctionVisitedTime.equals("")) {
                    loctimePickerEdit.setError("Please select time");
                    errortext.setText("* Required fields are missing");
                    errorcount++;

                }
                if (actionTypeID.equals("0")) {
                    setSpinnerError(actionType_spinner, ("Please select Action"));
                    errortext.setText("* Required fields are missing");
                    errorcount++;
                }
                if (subactionTypeID.equals("0")) {
                    setSpinnerError(subactionType_spinner, ("Please select type"));
                    errortext.setText("* Required fields are missing");
                    errorcount++;
                }
           /*     else if(subactionTypeID.equals("9")) {
                       if((counciltypeID.equals("0") && !coun_NoText.equals("")) || (!counciltypeID.equals("0") && coun_NoText.equals("")) || (counciltypeID.equals("0") && coun_NoText.equals(""))){
                           errortext.setText("Please enter council type and council number both");
                           errorcount++;
                       }
                }*/
                if (errorcount < 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to save the changes?")
                            .setTitle("Save")
                            .setCancelable(false)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    pDialog.setMessage("Loading Data, Please wait...");
                                    pDialog.setCancelable(false);
                                    pDialog.show();

                                    if (counciltypeID.equals("0")) {
                                        counciltypetext = "";
                                    }
                                    String url = getDirectionsUrl0(context);
                                    DownloadTask0 downloadTask = new DownloadTask0();
                                    //Start downloading json data from Google Directions API
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
                } else {
                    errortext.setVisibility(View.VISIBLE);
                }
            }
        });
        Button camera = (Button) findViewById(R.id.take_pic);

        camera.setOnClickListener(new Button.OnClickListener() {


            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 24) {
                    try {
                        Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                        m.invoke(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                count = 2;
                requestRuntimePermission();
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
                String currentDateandTime = sdf.format(new Date());
                String pictureName = final_id + "_" + currentDateandTime;//here you can get picture name from user. I supposed Test name
                Intent intentcamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photo = new File(Environment.getExternalStorageDirectory(), pictureName + ".jpg");//save picture (.jpg) on SD Card
                u = Uri.fromFile(photo);
                intentcamera.putExtra(MediaStore.EXTRA_OUTPUT, u);
                intentcamera.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                filePath = photo.getAbsolutePath();
                startActivityForResult(intentcamera, REQUEST_CODE);

            }
        });
        Button attachment = (Button) findViewById(R.id.attach_pic);

        attachment.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {

                count = 1;
                requestRuntimePermission();
                Intent attachintent = new Intent();
                attachintent.setType("image/*");
                attachintent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(attachintent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });
        Button gallery_button = (Button) findViewById(R.id.gallery_but);

        gallery_button.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                startGalleryActivity();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txtDateTime.setText(sdf.format(myCalendar.getTime()));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("actionType_spinner", actionType_spinner.getSelectedItemPosition());
        // do this for each or your Spinner
        // You might consider using Bundle.putStringArray() instead
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        savedInstanceState.getBoolean("actionType_spinner");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 1: {
                if (requestCode == MY_REQUEST_CODE) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // Now user should be able to use camera
                    } else {
                        // Your app will not have this permission. Turn off all functions
                        // that require this permission or it will force close like your
                        // original question
                    }
                }
  /*              if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //do here
                } else {
                    Toast.makeText(this, "The app was not allowed to read your store.", Toast.LENGTH_LONG).show();
                }
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

                return;*/

            }

        }
    }

    public Uri getOutputMediaFileUri(int type) {
        requestRuntimePermission();
        return Uri.fromFile(getOutputMediaFile(type));
    }

    // @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestRuntimePermission() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        MY_REQUEST_CODE);
            }
        }


    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Config.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("", "Oops! Failed create "
                        + Config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    /*
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
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
                        startActivity(new Intent(context, QuackActivity.class).putExtra("email", email).putExtra("password", password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_actiondesc:
                        startActivity(new Intent(context, IndReportingActivity.class).putExtra("email", email).putExtra("password", password).putExtra("username", username).putExtra("isEdit", isEdit));
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
                    /*     case R.id.nav_photos:
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
                        startActivity(new Intent(context, ChangePasswordActivity.class).putExtra("email", email).putExtra("password", password));
                        drawer.closeDrawers();
         /*               AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setTitle("Change Password");
                        final EditText oldPass = new EditText(context);
                        final EditText newPass = new EditText(context);
                        final EditText confirmPass = new EditText(context);


                        oldPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        confirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

                        oldPass.setHint("Old Password");
                        newPass.setHint("New Password");
                        confirmPass.setHint("Confirm Password");
                        LinearLayout ll=new LinearLayout(context);
                        ll.setOrientation(LinearLayout.VERTICAL);
                        ll.addView(oldPass);
                        ll.addView(newPass);
                        ll.addView(confirmPass);
                        alertDialog.setView(ll);
                        alertDialog.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                      String oldpas=  oldPass.getText().toString();
                                        String newPas = newPass.getText().toString();
                                        String confirmPas =confirmPass.getText().toString();
                                        if(newPas.equals(confirmPas) && oldpas.equals(password)){
                                            ChangePassword changePassword=new ChangePassword(context,email,newPas);
                                            dialog.cancel();
                                        }
                                        else {
                                            Toast.makeText(context, "Password not matched", Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });
                        alertDialog.setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = alertDialog.create();
                        alert11.show();*/
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

     /*   // show menu only when home fragment is selected
        if (navItemIndex == 0) {
           // getMenuInflater().inflate(R.menu.menu_main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
            getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        }*/
        return true;
    }

    private ArrayList<String> getactionTypes() {

        ArrayList<String> actiontypelist = new ArrayList<String>();
        int i = 0;
        for (ActionType actionType : actionTypeList) {
            String actiontypelists = actionType.getActionType();
            actiontypelist.add(actiontypelists);
            i++;
        }
        return actiontypelist;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onResume();
        if (count == 2) {
            try {

                // context.getContentResolver().notifyChange(u, null);
                // ContentResolver cr = context.getContentResolver();
                // Bitmap bm = android.provider.MediaStore.Images.Media.getBitmap(cr, u);
//ImageView to set the picture taken from camera.
                //   mImageView.setImageBitmap(bm);
                picTaken = true; //to ensure picture is taken
                picAttachement = false;
                pDialog.setMessage("Uploading image, Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
                upLoadPicture();
            } catch (Exception e) {

                e.printStackTrace();

            }
        } else if (count == 1) {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                // fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                //filePath= fileUri.getPath();
                filePathURI = data.getData();

                filePath = getRealPathFromUri(context, filePathURI);

                try {
                    picAttachement = true;
                    picTaken = false;
                    //getting image from gallery
                    //Bitmap bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(filePath));
                    pDialog.setMessage("Uploading image, Please wait...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    upLoadPicture();
                    //Setting image to ImageView
                    //  mImageView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void upLoadPicture() {

        if (picTaken) {

            final ProgressDialog pd = ProgressDialog.show(context, "Please wait", "Uploading Picture ...", true);
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    String extension = filePath.substring(filePath.lastIndexOf("."));
                    File file = new File(filePath);
                    FileInputStream fis = null;
                    // BufferedInputStream buffIn = null;

                    try {
                        picUploaded = false;
                        imagepath = null;
                        imageNAme = null;
                        directoryPath = null;
                        picreceved = true;
                        FTPClient client = new FTPClient();

                        client.connect(ftpbaseurl);
                        client.login("mobile", "Document2"); //this is the login credentials of your ftpserver. Ensure to use valid username and password otherwise it throws exception

                        try {
                            client.setFileType(FTP.BINARY_FILE_TYPE);
                            // buffIn = new BufferedInputStream(new FileInputStream(file));
                            fis = new FileInputStream(file);
                            client.enterLocalPassiveMode();
                            client.makeDirectory("HCEImages/" + final_id); //I want to upload picture in MyPictures directory/folder. you can use your own.
                            client.makeDirectory("HCEImages/" + final_id + "/" + username);
                            client.makeDirectory("HCEImages/" + final_id + "/" + username + "/Camera");
                            directoryPath = "HCEImages/" + final_id + "/" + username + "/Camera";
                            //client.sendCommand("OPTS UTF8 ON");

                        } catch (Exception e) {
                            if (pDialog.isShowing())
                                pDialog.dismiss();
                            picreceved = false;
                            Toast.makeText(context, "Picture not found! Please try again", Toast.LENGTH_SHORT).show();
                            //client.makeDirectory("HCEImages/" + final_id); //I want to upload picture in MyPictures directory/folder. you can use your own.
                            //client.makeDirectory("HCEImages/" + final_id+"/Camera");

                        }

                        if (picreceved) {
                            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
                            String currentDateandTime = sdf.format(new Date());
                            imageNAme = final_id + "_" + currentDateandTime + extension;
                            imagepath = final_id + "/" + username + "/Camera/" + imageNAme;

                            try {
                                if (!client.storeFile("HCEImages/" + imagepath, fis))//this is actual file uploading on FtpServer in specified directory/folder
                                {
                                    throw new Exception("Unable to write file to FTP server");
                                }
                                //Make sure to always close the inputStream
                            } finally {
                                fis.close();
                            }
                            InputStream inputStream = client.retrieveFileStream("/" + directoryPath + "/" + imageNAme);
                            int returnCode = client.getReplyCode();
                            if (inputStream == null || returnCode == 550) {
                                picUploaded = false;
                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                Toast.makeText(context, "Picture not uploaded! Please try again", Toast.LENGTH_SHORT).show();
                            } else {
                                picUploaded = true;
                                countPictures++;
                            }
                            totalpics.setText("Total pictures uploaded: " + countPictures);
                        }

                        try {

                            //10 seconds to log off.  Also 10 seconds to disconnect.
                            client.setSoTimeout(10000);
                            client.logout();

                            //depending on the state of the server the .logout() may throw an exception,
                            //we want to ensure complete disconnect.
                        } catch (Exception innerException) {

                            //You potentially just want to log that there was a logout exception.

                        } finally {
                            //Make sure to always disconnect.  If not, there is a chance you will leave hanging sockects
                            client.disconnect();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (picUploaded) {
                        String url = getDirectionsUrl2(context);
                        DownloadTask2 downloadTask = new DownloadTask2();
                        //Start downloading json data from Google Directions API
                        downloadTask.execute(url);
             /*
                        String hostname = "202.142.147.36";
                        String username = "mobile";
                        String password = "Document2";
                        String dirPath = directoryPath;
                        String filePath ="/"+directoryPath+"/"+imageNAme;
                        FTPCheckFileExists ftpApp = new FTPCheckFileExists();

                        try {
                            ftpApp.connect(hostname, username, password);

                            boolean exist = ftpApp.checkDirectoryExists(dirPath);
                            System.out.println("Is directory " + dirPath + " exists? " + exist);

                            exist = ftpApp.checkFileExists(filePath);
                            System.out.println("Is file " + filePath + " exists? " + exist);
                            if(exist){
                                String url = getDirectionsUrl2(context);
                                DownloadTask2 downloadTask = new DownloadTask2();
                                //Start downloading json data from Google Directions API
                                downloadTask.execute(url);
                            }
                            else {
                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                Toast.makeText(context, "Picture not uploaded! Please Verify", Toast.LENGTH_SHORT).show();
                            }

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } finally {
                            try {
                                ftpApp.logout();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }*/

                    }
                }


            });
            thread.start();
            pd.dismiss();


        } else {
            // Toast.makeText(context, "Please Take Picture First than Upload.", Toast.LENGTH_LONG).show();

        }

        if (picAttachement) {

            final ProgressDialog pd = ProgressDialog.show(context, "Please wait", "Uploading Picture ...", true);
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {


                    String extension = filePath.substring(filePath.lastIndexOf("."));
                    File file = new File(filePath);
                    FileInputStream fis = null;
                    // BufferedInputStream buffIn = null;

                    try {
                        picUploaded = false;
                        imagepath = null;
                        imageNAme = null;
                        directoryPath = null;
                        picreceved = true;
                        FTPClient client = new FTPClient();

                        client.connect(ftpbaseurl);
                        client.login("mobile", "Document2"); //this is the login credentials of your ftpserver. Ensure to use valid username and password otherwise it throws exception

                        try {
                            client.setFileType(FTP.BINARY_FILE_TYPE);
                            fis = new FileInputStream(file);
                            client.enterLocalPassiveMode();
                            client.makeDirectory("HCEImages/" + final_id); //I want to upload picture in MyPictures directory/folder. you can use your own.
                            client.makeDirectory("HCEImages/" + final_id + "/" + username);
                            client.makeDirectory("HCEImages/" + final_id + "/" + username + "/Attachments");
                            directoryPath = "HCEImages/" + final_id + "/" + username + "/Attachments";
                        } catch (Exception e) {
                            if (pDialog.isShowing())
                                pDialog.dismiss();
                            picreceved = false;
                            Toast.makeText(context, "Picture not found! Please try again", Toast.LENGTH_SHORT).show();
                        }

                        if (picreceved) {
                            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
                            String currentDateandTime = sdf.format(new Date());
                            imageNAme = final_id + "_" + currentDateandTime + extension;
                            imagepath = final_id + "/" + username + "/Attachments/" + imageNAme;
                            try {
                                if (!client.storeFile("HCEImages/" + imagepath, fis))//this is actual file uploading on FtpServer in specified directory/folder
                                {
                                    throw new Exception("Unable to write file to FTP server");
                                }
                                //Make sure to always close the inputStream
                            } finally {
                                fis.close();
                            }
                            InputStream inputStream = client.retrieveFileStream("/" + directoryPath + "/" + imageNAme);
                            int returnCode = client.getReplyCode();
                            if (inputStream == null || returnCode == 550) {
                                picUploaded = false;
                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                Toast.makeText(context, "Picture not uploaded! Please try again", Toast.LENGTH_SHORT).show();
                            } else {
                                picUploaded = true;
                                countPictures++;
                            }
                            totalpics.setText("Total pictures uploaded: " + countPictures);
                        }
                        try {

                            //10 seconds to log off.  Also 10 seconds to disconnect.
                            client.setSoTimeout(10000);
                            client.logout();

                            //depending on the state of the server the .logout() may throw an exception,
                            //we want to ensure complete disconnect.
                        } catch (Exception innerException) {

                            //You potentially just want to log that there was a logout exception.

                        } finally {
                            //Make sure to always disconnect.  If not, there is a chance you will leave hanging sockects
                            client.disconnect();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (picUploaded) {
                        String url = getDirectionsUrl2(context);
                        DownloadTask2 downloadTask = new DownloadTask2();
                        //Start downloading json data from Google Directions API
                        downloadTask.execute(url);
                       /*
                        String hostname = "202.142.147.36";
                        String username = "mobile";
                        String password = "Document2";
                        String dirPath = directoryPath;
                        String filePath = "/"+directoryPath+"/"+imageNAme;;

                        FTPCheckFileExists ftpApp = new FTPCheckFileExists();

                        try {
                            ftpApp.connect(hostname, username, password);

                            boolean exist = ftpApp.checkDirectoryExists(dirPath);
                            System.out.println("Is directory " + dirPath + " exists? " + exist);

                            exist = ftpApp.checkFileExists(filePath);
                            System.out.println("Is file " + filePath + " exists? " + exist);
                            if(exist){
                                String url = getDirectionsUrl2(context);
                                DownloadTask2 downloadTask = new DownloadTask2();
                                //Start downloading json data from Google Directions API
                                downloadTask.execute(url);
                            }
                            else {
                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                Toast.makeText(context, "Picture not uploaded! Please Verify", Toast.LENGTH_SHORT).show();
                            }

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } finally {
                            try {
                                ftpApp.logout();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }*/
                    }
                }

            });
            thread.start();
            pd.dismiss();


        } else {
            //   Toast.makeText(context, "Please Take Picture First than Upload.", Toast.LENGTH_LONG).show();

        }
    }

    /**
     * Creating file uri to store image/video
     */
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private String getactionsel() {
        DatabaseManager databaseManager = new DatabaseManager(context);
        actionTypeselected = databaseManager.getActionselected(actionTypeID);
        if (actionTypeselected.size() > 0) {
            actiontseltext = actionTypeselected.get(0).getActionType();
        }
        return actiontseltext;
    }

    private String getsubactionsel() {
        DatabaseManager databaseManager = new DatabaseManager(context);
        subactionTypeselected = databaseManager.getsubActionselected(subactionTypeID);
        if (subactionTypeselected.size() > 0) {
            subactiontseltext = subactionTypeselected.get(0).getSubactionType();
        }
        return subactiontseltext;
    }

    private ArrayList<String> getsubactionTypes() {
        subactionType = dataManager.getsubActionstype(actionTypeID, "2");
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
        if (subactionTypeID != null) {
            getsubactionsel();
            int spinnerPosition = subactiontype_spinneradapter.getPosition(subactiontseltext);
            subactionType_spinner.setSelection(spinnerPosition);
        }
        subactionType_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                //subactionposition=position;
                subactionTypeText = parent.getItemAtPosition(position).toString();
                subactionTypeID = subactionType.get(position).getSubActionType_Id();
           /*     if(subactionTypeID.equals("9")) {
                    counTypeInput.setVisibility(View.VISIBLE);
                    counNoInput.setVisibility(View.VISIBLE);
                    Reg_No_lay.setVisibility(View.VISIBLE);
                    if(comments.requestFocus()) {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    }
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                }
                else {

                    counTypeInput.setVisibility(View.GONE);
                    counNoInput.setVisibility(View.GONE);
                    Reg_No_lay.setVisibility(View.GONE);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (comments.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        return subactiontypelist;
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {

            // Toast.makeText(EditHCEFragment.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(this, new String[]{

                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    public void startGalleryActivity() {
    /*    ArrayList<String> images = new ArrayList<String>();
        images.add("http://sourcey.com/images/stock/salvador-dali-metamorphosis-of-narcissus.jpg");
        images.add("http://sourcey.com/images/stock/salvador-dali-the-dream.jpg");
        images.add("http://202.166.167.123:8081/phc_images/78_2015_08_05_10_41_03_.jpg");
        images.add("http://sourcey.com/images/stock/simpsons-persistance-of-memory.jpg");
        images.add("http://sourcey.com/images/stock/salvador-dali-the-great-masturbator.jpg");*/
        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putStringArrayListExtra(GalleryActivity.EXTRA_NAME, _images);
        startActivity(intent);
    }

    private class DownloadTask0 extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl0(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask0 parserTask = new ParserTask0();
            jsonStr0 = result;
            // Invokes the thread for parsing the JSON data
            parserTask.execute();

        }
    }

    private String getDirectionsUrl0(Context context) {

        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String token = context.getResources().getString(R.string.token);
        String url = baseurl + "GetPlanID?strToken=" + token + "&FinalID=" + final_id + "&ImagePath=&ImageUploadMode=Camera&Lat=" + cur_latitude + "&Lng=" + cur_longitude + "&UploadedBy=&emailAddress=" + email + "&ActionType=" + actionTypeID + "&ActionSubType=" + subactionTypeID + "&PicCount=" + countPictures + "&Council=" + counciltypetext + "&CouncilNo=" + coun_NoText + "&isFIR=" + firID + "&phcRegNo=" + Reg_NoText + "&visitedDate=" + visitDate + "&comments=" + commentText + "&QuackCategory=&QuackSubCategory=&RoleID=" + roleid;
        url = url.replaceAll(" ", "%20");
        return url;
    }

    private String downloadUrl0(String strUrl) throws IOException {


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

    private class ParserTask0 extends AsyncTask<Object, Object, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog


        }

        @Override
        protected String doInBackground(Object... arg0) {

            // Making a request to url and getting response

            if (jsonStr0 != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr0);

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

            if (result != null) {
                if (MID.equals("1") && !UserID.equals("5")) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            context).create();

                    // Setting Dialog Title
                    //alertDialog.setTitle("Success");

                    // Setting Dialog Message
                    alertDialog.setMessage("Please review visit date as it is outside your assigned plans");
                    // Setting Icon to Dialog
                    //  alertDialog.setIcon(R.drawable.tick);

                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog closed
                            //finish();
                            //  Toast.makeText(context, "Thanks You!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                } else if (MID.equals("0")) {
                    Toast.makeText(context, "Service is not responding", Toast.LENGTH_SHORT).show();
                } else {

                    String url = getDirectionsUrl(context);
                    DownloadTask downloadTask = new DownloadTask();
                    //Start downloading json data from Google Directions API
                    downloadTask.execute(url);

                }
                // Updating parsed JSON data into ListView

            } else {
                Toast.makeText(context, "Server is not responding. Please try again", Toast.LENGTH_SHORT).show();
            }


        }

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
        String url = baseurl + "SaveHCEImage?strToken=" + token + "&FinalID=" + final_id + "&ImagePath=&ImageUploadMode=Camera&Lat=" + cur_latitude + "&Lng=" + cur_longitude + "&UploadedBy=&emailAddress=" + email + "&ActionType=" + actionTypeID + "&ActionSubType=" + subactionTypeID + "&PicCount=" + countPictures + "&Council=" + counciltypetext + "&CouncilNo=" + coun_NoText + "&isFIR=" + firID + "&phcRegNo=" + Reg_NoText + "&visitedDate=" + visitDate + "&comments=" + commentText + "&QuackCategory=&QuackSubCategory=&RoleID=" + roleid + "&VisitedTime=" + LoctionVisitedTime;
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
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            context).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Success");

                    // Setting Dialog Message
                    alertDialog.setMessage("Your Action has been submitted for detail review.");
                    // Setting Icon to Dialog
                    //  alertDialog.setIcon(R.drawable.tick);

                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog closed
                            finish();
                            //  Toast.makeText(context, "Thanks You!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                } else {


                    Toast.makeText(context, "Update not submitted! Please Verify", Toast.LENGTH_SHORT).show();

                }
                // Updating parsed JSON data into ListView

            } else {
                Toast.makeText(context, "Server is not responding. Please try again", Toast.LENGTH_SHORT).show();
            }


        }

    }


    private class DownloadTask2 extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl2(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask2 parserTask = new ParserTask2();
            jsonStr2 = result;
            // Invokes the thread for parsing the JSON data
            parserTask.execute();

        }
    }

    private String getDirectionsUrl2(Context context) {

        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String token = context.getResources().getString(R.string.token);
        String url = baseurl + "UploadHCEImage?strToken=" + token + "&FinalID=" + final_id + "&ImagePath=" + imagepath + "&emailAddress=" + email + "&visitedDate=&RoleID=" + roleid;
        url = url.replaceAll(" ", "%20");
        return url;

    }

    private String downloadUrl2(String strUrl) throws IOException {


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

    private class ParserTask2 extends AsyncTask<Object, Object, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog


        }

        @Override
        protected String doInBackground(Object... arg0) {

            // Making a request to url and getting response

            if (jsonStr2 != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr2);

                    MID2 = jsonObj.getString("MID");
                    MText2 = jsonObj.getString("MText");
                    return MID2;
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
            if (comments.requestFocus()) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            if (result != null) {
                if (MID2.equals("1")) {
                    Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    String imagebaseurl = context.getResources().getString(R.string.Imagesbaseurl);
                    ;
                    _images.add(imagebaseurl + imagepath);
                    imagepath = null;

                } else {
                    Toast.makeText(context, "Update not submitted! Please Verify", Toast.LENGTH_SHORT).show();

                }
                // Updating parsed JSON data into ListView

            } else {
                Toast.makeText(context, "Server is not responding. Please try again", Toast.LENGTH_SHORT).show();
            }


        }

    }
}
