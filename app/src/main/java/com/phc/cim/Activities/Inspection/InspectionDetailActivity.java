package com.phc.cim.Activities.Inspection;

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
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.phc.cim.Activities.Common.AboutusActivity;
import com.phc.cim.Activities.Common.ChangePasswordActivity;
import com.phc.cim.Activities.Common.DesealListing;
import com.phc.cim.Activities.Common.HearingStatusActivity;
import com.phc.cim.Activities.Common.QuackActivity;
import com.phc.cim.Activities.GalleryActivity;
import com.phc.cim.Activities.Common.IndReportingActivity;
import com.phc.cim.Activities.Common.ReportQuackActivity;
import com.phc.cim.Activities.Licensing.PWSFilterActivity;
import com.phc.cim.Adapters.MyCustomPagerAdapter;
import com.phc.cim.DataElements.CouncilType;
import com.phc.cim.DataElements.District;
import com.phc.cim.DataElements.OrgType;
import com.phc.cim.DataElements.SectorType;
import com.phc.cim.DataElements.UpdateStatL1;
import com.phc.cim.DataElements.UpdateStatL2;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import butterknife.ButterKnife;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;


public class InspectionDetailActivity extends AppCompatActivity {
///--------Upload Images---
    int PICK_IMAGE_REQUEST = 111;
    private Uri u=null;
    private Boolean picTaken=false;
    private Boolean picreceved=false;
    private Boolean picUploaded=false;
    private Boolean picAttachement=false;
    int count=0;
    private String filePath=null;
    String directoryPath=null;
    private Uri filePathURI;
    int MY_REQUEST_CODE=5;
    String imageNAme=null;
    String imagepath;
    String MID2,MText2,jsonStr2;
    //-----End Images----
    ArrayList<String> mylist;
    double des_lat;
    double des_lng;
    DataManager dataManager;
    String MID;
    String MText;
    String jsonStr;
    CurrentLocation gps;
    EditText hce_nameEdit;
    EditText AddressEdit;
    EditText HCSP_nameEdit;
    EditText latEdit;
    EditText lngEdit;
    EditText HCSP_ContactEdit;
    EditText Reg_NoEdit;
    EditText coun_NoEdit;
    EditText coments;

    TextInputLayout regNoInput;
    TextInputLayout counTypeInput;
    TextInputLayout counNoInput;
    TextInputLayout substatusInput;
    EditText timePicker1;
    EditText infoVisitDate;
    EditText sectortypespinner;
    EditText hcetypespinner;

    EditText Email;

    EditText EmailSP;
    String ftpbaseurl;
    ProgressDialog pDialog;
    EditText loctimePickerEdit;
    EditText locDateTimeEdit;
    EditText bedsEdit;
    String hce_nameText = "";
    String AddressText = "";
    String HCSP_nameText = "";
    String latText = "";
    String lngText = "";
    String HCSP_ContactText = "";
    String Reg_NoText = "";
    String final_id = "";
    String RegType = "";
    String password="";
    String isEdit="";
    String username="";
    String isStat;
    String UserID;
    String RoleID;
    String recID;
    String HCEEmail;
    String HCEEmailSP;
    String infoVisitDatetext = "";
    String sectortypetext = "";
    String hceTypetext = "";
    String infoVisitBytext= "";
    String BasicInfoVisitedTime= "";
    String LoctionVisitedBy= "";
    String LoctionVisitedDate= "";
    String LoctionVisitedTime= "";
    String UserName = "";
    String BasicInfoVisitedUserName = "";
    String LocationVisitedUserName = "";
    String currlocText = "";
    String currlocID = "-1";
    String total_beds = "";
    double latitude;
    double longitude;
    String email = "";
    double cur_latitude;
    double cur_longitude;
    Context context;
    ArrayAdapter<String> currloc_adapter;
    Button btn_info, btn_loc, btn_save,btn_cancel;
    LinearLayout layout_submit,errortextlayout,infolayout,loclayout,imageslayout;
    TextInputLayout loc_layout,reg_layout,Sector_layout,hce_name_layout,Address_layout,hcetype_layout,HCSP_Name_layout,Mobile_layout,
            beds_layout,txtDateTime_layout,timePicker1_layout,lat_layout,lng_layout,locDatelayout,loctimelayout, Email_Layout,EmailSP_Layout;
    TextView indicatelabel;
    TextView locindlabel,latvisitinfo,latvisitloc;
    Spinner currloc_spinner;
    ArrayList<District> districts;
    ArrayList<SectorType> sectorTypes;
    ArrayList<CouncilType> councilTypes;
    ArrayList<OrgType> orgTypes;
    ArrayList<UpdateStatL1> updateStatL1s;
    ArrayList<UpdateStatL2> updateStatL2s;
    ArrayList<HashMap<String, String>> BasicInfoDetail;
    ArrayList<HashMap<String, String>> LocationDetail;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_MOVIES = "videos";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;
    private static final int RequestPermissionCode = 1;
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
    String date1="";
    String date2="";
    String appURI = "";
    String time1;
    MyCustomPagerAdapter myCustomPagerAdapter;
    int index;
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

    TimePickerDialog timePickerDialog;
    int currentHour;
    int currentMinute;
    String amPm;
    Calendar calendar;
    public static final String TAG = "GalleryActivity";
    public static final String EXTRA_NAME = "images";

    private ArrayList<String> _images;
    private GalleryPagerAdapter _adapter;

    ViewPager _pager;
    LinearLayout _thumbnails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //context=this;
        setContentView(R.layout.inspection_detail_view);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         calendar = Calendar.getInstance();
        context=this;
        index=0;
        ButterKnife.bind(this);
        _pager = (ViewPager) findViewById(R.id.pager);
        _thumbnails = (LinearLayout) findViewById(R.id.thumbnails);
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        isStat = prefs.getString("isStat", null);//"No name defined" is the default value.
        email = prefs.getString("email", null);//"No name defined" is the default value.
        password = prefs.getString("password", null);//"No name defined" is the default value.
        username = prefs.getString("username", null);//"No name defined" is the default value.
        isEdit = prefs.getString("isEdit", null);//"No name defined" is the default value.
        RoleID = prefs.getString("RoleID", null); //0 is the default value.
        UserID= prefs.getString("UserID", null); //0 is the default value.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gps = new CurrentLocation(context);
        mHandler = new Handler();
        TextView t2 = (TextView) findViewById(R.id.text2);
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

        activityTitles =getResources().getString(R.string.nav_item_Inspection_titles);
        ftpbaseurl=context.getResources().getString(R.string.ftpbaseurl);
        if(RoleID.equals("1") || RoleID.equals("3")) {
            navigationView.getMenu().findItem(R.id.nav_actiondesc).setVisible(true);
            if (isStat.equals("true")&& RoleID.equals("1")) {
                navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(true);


            } else {
                navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(false);
            }
        }
        else {
            navigationView.getMenu().findItem(R.id.nav_actiondesc).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(false);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, 42);
        }
        checkPermission();
        EnableRuntimePermission();
        loadNavHeader();
        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dataManager = new DataManager(this);
        gps = new CurrentLocation(this);
        //--------edittext---------
        Reg_NoEdit = (EditText) findViewById(R.id.reg_no);
        sectortypespinner = (EditText) findViewById(R.id.Sector_Type);
        hce_nameEdit = (EditText) findViewById(R.id.hce_name);
        AddressEdit = (EditText) findViewById(R.id.Address);
        hcetypespinner = (EditText) findViewById(R.id.hcetype);
        HCSP_nameEdit = (EditText) findViewById(R.id.HCSP_Name);
        HCSP_ContactEdit = (EditText) findViewById(R.id.Mobile);
        Email = (EditText) findViewById(R.id.Email);
        EmailSP= (EditText) findViewById(R.id.EmailSP);
        bedsEdit = (EditText) findViewById(R.id.beds);
        infoVisitDate = (EditText) findViewById(R.id.txtDateTime);
        timePicker1 = (EditText) findViewById(R.id.timePicker1);
        latEdit = (EditText) findViewById(R.id.latEdit);
        lngEdit = (EditText) findViewById(R.id.lngEdit);
        locDateTimeEdit = (EditText) findViewById(R.id.locDateTime);
        loctimePickerEdit = (EditText) findViewById(R.id.loctimePicker);
        indicatelabel = (TextView) findViewById(R.id.indicatelabel);
        locindlabel = (TextView) findViewById(R.id.locindlabel);

        latvisitloc = (TextView) findViewById(R.id.latvisitloc);
        latvisitinfo = (TextView) findViewById(R.id.latvisitinfo);

        currloc_spinner = (Spinner) findViewById(R.id.currloc_spinner);
        //-------buttons-----------------
        btn_info = (Button) findViewById(R.id.btn_info);
        btn_loc = (Button) findViewById(R.id.btn_loc);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_save = (Button) findViewById(R.id.btn_save);


        //--------------layouts-------
        errortextlayout= (LinearLayout) findViewById(R.id.errortextlayout);


        layout_submit = (LinearLayout) findViewById(R.id.layout_submit);
        infolayout = (LinearLayout) findViewById(R.id.infolayout);
        loclayout = (LinearLayout) findViewById(R.id.loclayout);
        imageslayout= (LinearLayout) findViewById(R.id.imageslayout);
        loc_layout = (TextInputLayout) findViewById(R.id.loc_layout);
        reg_layout = (TextInputLayout) findViewById(R.id.reg_layout);
        Sector_layout = (TextInputLayout) findViewById(R.id.Sector_layout);
        hce_name_layout = (TextInputLayout) findViewById(R.id.hce_name_layout);
        Address_layout = (TextInputLayout) findViewById(R.id.Address_layout);
        hcetype_layout = (TextInputLayout) findViewById(R.id.hcetype_layout);
        HCSP_Name_layout = (TextInputLayout) findViewById(R.id.HCSP_Name_layout);
        Mobile_layout = (TextInputLayout) findViewById(R.id.Mobile_layout);
        Email_Layout = (TextInputLayout) findViewById(R.id.Email_Layout);
        EmailSP_Layout = (TextInputLayout) findViewById(R.id.EmailSP_Layout);
        beds_layout = (TextInputLayout) findViewById(R.id.beds_layout);
        txtDateTime_layout = (TextInputLayout) findViewById(R.id.txtDateTime_layout);
        timePicker1_layout = (TextInputLayout) findViewById(R.id.timePicker1_layout);
        lat_layout = (TextInputLayout) findViewById(R.id.lat_layout);
        lng_layout = (TextInputLayout) findViewById(R.id.lng_layout);
        locDatelayout = (TextInputLayout) findViewById(R.id.locDatelayout);
        loctimelayout = (TextInputLayout) findViewById(R.id.loctimelayout);


        Intent intent = getIntent();
        BasicInfoDetail = (ArrayList<HashMap<String, String>>)intent.getSerializableExtra("BasicInfoDetail");
        LocationDetail = (ArrayList<HashMap<String, String>>)intent.getSerializableExtra("LocationDetail");
        _images = (ArrayList<String>) getIntent().getSerializableExtra("imageurls");
        if(!BasicInfoDetail.equals(null)&& !BasicInfoDetail.equals("null")&& !BasicInfoDetail.equals("") && BasicInfoDetail.size()>0) {

            hce_nameText = BasicInfoDetail.get(0).get("orgName");
            AddressText = BasicInfoDetail.get(0).get("Address");
            infoVisitDatetext = BasicInfoDetail.get(0).get("BasicInfoVisitedDate");
            infoVisitBytext = BasicInfoDetail.get(0).get("BasicInfoVisitedBy");
            BasicInfoVisitedTime = BasicInfoDetail.get(0).get("BasicInfoVisitedTime");
            BasicInfoVisitedUserName = BasicInfoDetail.get(0).get("BasicInfoVisitedUserName");
            sectortypetext = BasicInfoDetail.get(0).get("dataType");
            hceTypetext = BasicInfoDetail.get(0).get("orgType");
            HCSP_nameText = BasicInfoDetail.get(0).get("PersonName");
            HCSP_ContactText = BasicInfoDetail.get(0).get("MobileNumber");
            HCEEmail = BasicInfoDetail.get(0).get("HCEEmail"); //Add Email Portion
            HCEEmailSP = BasicInfoDetail.get(0).get("HCEEmailSP");
            Reg_NoText = BasicInfoDetail.get(0).get("phcRegistrationNumber");
            total_beds = BasicInfoDetail.get(0).get("bedStrength");
            final_id = BasicInfoDetail.get(0).get("FinalId");
            recID = BasicInfoDetail.get(0).get("recID");
        }
        if(!LocationDetail.equals(null)&& !LocationDetail.equals("null")&& !LocationDetail.equals("") && LocationDetail.size()>0){
            LoctionVisitedBy = LocationDetail.get(0).get("LoctionVisitedBy");
            LoctionVisitedDate = LocationDetail.get(0).get("LoctionVisitedDate");
            LoctionVisitedTime = LocationDetail.get(0).get("LoctionVisitedTime");
            LocationVisitedUserName = LocationDetail.get(0).get("LocationVisitedUserName");
            latText = LocationDetail.get(0).get("Lat");
            lngText = LocationDetail.get(0).get("Lng");
        }

        if(latText.equals("0.0")){
            latText.equals("");
        }
        if(lngText.equals("0.0")){
            lngText.equals("");
        }


        if(_images!=null) {
         //   Assert.assertNotNull(_images);
            _adapter = new GalleryPagerAdapter(context);
            _pager.setAdapter(_adapter);
            _adapter.notifyDataSetChanged();
            _pager.setOffscreenPageLimit(6);
        }
        hce_nameEdit.setText(hce_nameText);
        AddressEdit.setText(AddressText);
        HCSP_nameEdit.setText(HCSP_nameText);
        Email.setText(HCEEmail); //Add Email Set Here
        EmailSP.setText(HCEEmailSP);
        latEdit.setText(latText);
        lngEdit.setText(lngText);
        HCSP_ContactEdit.setText(HCSP_ContactText);
        Reg_NoEdit.setText(Reg_NoText);
        bedsEdit.setText(total_beds);
        sectortypespinner.setText(sectortypetext);
        hcetypespinner.setText(hceTypetext);
        timePicker1.setText(BasicInfoVisitedTime);
        loctimePickerEdit.setText(LoctionVisitedTime);
        if(infoVisitBytext.equals("0")){
            latvisitinfo.setVisibility(View.GONE);
        }
        else {
            latvisitinfo.setText("Last visited by: "+BasicInfoVisitedUserName);
        }
        if(LoctionVisitedBy.equals("0") || LoctionVisitedBy.equals("")){
            latvisitloc.setVisibility(View.GONE);
        }
        else {
            latvisitloc.setText("Last visited by: "+LocationVisitedUserName);
        }

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub


                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };
            String ackwardDate = infoVisitDatetext;
        if(!infoVisitDatetext.equals(null) && !infoVisitDatetext.equals("null") && !infoVisitDatetext.equals("")) {

            String ackwardRipOff = ackwardDate.replace("/Date(", "").replace("+0500", "").replace(")/", "");
            Long timeInMillis = Long.valueOf(ackwardRipOff);
            calendar.setTimeInMillis(timeInMillis);
            date1 = new SimpleDateFormat("dd/MM/yy").format(calendar.getTime()).toString();
            infoVisitDate.setText(date1);
        }
        infoVisitDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DatePickerDialog(context, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

            String ackwardDate2 = LoctionVisitedDate;
            if(!LoctionVisitedDate.equals(null) && !LoctionVisitedDate.equals("null") && !LoctionVisitedDate.equals("")) {
                String ackwardRipOff2 = ackwardDate2.replace("/Date(", "").replace("+0500", "").replace(")/", "");
                Long timeInMillis2 = Long.valueOf(ackwardRipOff2);
                calendar.setTimeInMillis(timeInMillis2);
                date2 = new SimpleDateFormat("dd/MM/yy").format(calendar.getTime()).toString();
                locDateTimeEdit.setText(date2);
            }
        locDateTimeEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DatePickerDialog(context, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        timePicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar   calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            hourOfDay = hourOfDay-12;
                            if(hourOfDay==00){
                                hourOfDay=12;
                            }
                            amPm = "PM";
                        } else {
                            if(hourOfDay==00){
                                hourOfDay=12;
                            }
                            amPm = "AM";
                        }
                        timePicker1.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });

        loctimePickerEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar   calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            hourOfDay = hourOfDay-12;
                            if(hourOfDay==00){
                                hourOfDay=12;
                            }
                            amPm = "PM";
                        } else {
                            if(hourOfDay==00){
                                hourOfDay=12;
                            }
                            amPm = "AM";
                        }
                        loctimePickerEdit.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });

        //---------------------------------Current location Spinner--------------------------------------------

        String currloc[]={"Please Select","Yes","No"};
         currloc_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, currloc) {
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
                currlocText = parent.getItemAtPosition(position).toString();
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
                    cur_latitude = gps.getLatitude();
                    cur_longitude = gps.getLongitude();
                }
                if(currlocText.equals("Yes")){
                    lat_layout.setVisibility(View.VISIBLE);
                    lng_layout.setVisibility(View.VISIBLE);
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
                        cur_latitude = gps.getLatitude();
                        cur_longitude = gps.getLongitude();
                    }
                    latEdit.setText(String.valueOf(cur_latitude));
                    lngEdit.setText(String.valueOf(cur_longitude));
                }
                else {
                    lat_layout.setVisibility(View.GONE);
                    lng_layout.setVisibility(View.GONE);
                    //latEdit.setText(latText);
                    //lngEdit.setText(lngText);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Reg_NoEdit.setEnabled(false);
        sectortypespinner.setEnabled(false);
        hce_nameEdit.setEnabled(false);
        AddressEdit.setEnabled(false);
        Email.setEnabled(false); //Add Email EditText False
        EmailSP.setEnabled(false);
        hcetypespinner.setEnabled(false);
        HCSP_nameEdit.setEnabled(false);
        HCSP_ContactEdit.setEnabled(false);
        bedsEdit.setEnabled(false);
        infoVisitDate.setEnabled(false);
        timePicker1.setEnabled(false);
        latEdit.setEnabled(false);
        lngEdit.setEnabled(false);
        locDateTimeEdit.setEnabled(false);
        loctimePickerEdit.setEnabled(false);
        layout_submit.setVisibility(View.GONE);
        indicatelabel.setVisibility(View.GONE);
        loc_layout.setVisibility(View.GONE);
        errortextlayout.setVisibility(View.GONE);
        locindlabel.setVisibility(View.GONE);
        Button camera = (Button) findViewById(R.id.btn_Camera);

        camera.setOnClickListener(new Button.OnClickListener() {


            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=24){
                    try{
                        Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                        m.invoke(null);
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
                count=2;
                requestRuntimePermission();
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
                String currentDateandTime = sdf.format(new Date());
                String pictureName=final_id+"_"+currentDateandTime;//here you can get picture name from user. I supposed Test name
                Intent intentcamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photo = new File(Environment.getExternalStorageDirectory(),  pictureName+".jpg");//save picture (.jpg) on SD Card
                u= Uri.fromFile(photo);
                intentcamera.putExtra(MediaStore.EXTRA_OUTPUT,u);
                intentcamera.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                filePath = photo.getAbsolutePath();
                startActivityForResult(intentcamera, REQUEST_CODE);

            }
        });
        Button attachment = (Button) findViewById(R.id.btn_Attach);

        attachment.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {

                count=1;
                requestRuntimePermission();
                Intent attachintent = new Intent();
                attachintent.setType("image/*");
                attachintent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(attachintent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index=1;
                btn_info.setVisibility(View.GONE);
                layout_submit.setVisibility(View.VISIBLE);
                indicatelabel.setVisibility(View.VISIBLE);
                loc_layout.setVisibility(View.GONE);
                loclayout.setVisibility(View.GONE);
                imageslayout.setVisibility(View.GONE);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                Reg_NoEdit.setEnabled(false);
                sectortypespinner.setEnabled(false);
                hce_nameEdit.setEnabled(false);
                AddressEdit.setEnabled(false);
                Email.setEnabled(true);  //Add Email
                EmailSP.setEnabled(true);
                hcetypespinner.setEnabled(false);
                HCSP_nameEdit.setEnabled(true);
                HCSP_ContactEdit.setEnabled(true);
                bedsEdit.setEnabled(true);
                infoVisitDate.setEnabled(true);
                timePicker1.setEnabled(true);
                latEdit.setEnabled(false);
                lngEdit.setEnabled(false);
                locDateTimeEdit.setEnabled(false);
                loctimePickerEdit.setEnabled(false);
                reg_layout.setVisibility(View.GONE);
                Sector_layout.setVisibility(View.GONE);
                hce_name_layout.setVisibility(View.GONE);
                Address_layout.setVisibility(View.GONE);
                hcetype_layout.setVisibility(View.GONE);
                lat_layout.setVisibility(View.GONE);
                lng_layout.setVisibility(View.GONE);
                locDatelayout.setVisibility(View.GONE);
                loctimelayout.setVisibility(View.GONE);




            }
        });
        btn_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index=2;
                btn_loc.setVisibility(View.GONE);
                layout_submit.setVisibility(View.VISIBLE);
                loc_layout.setVisibility(View.VISIBLE);
                locindlabel.setVisibility(View.VISIBLE);
                infolayout.setVisibility(View.GONE);
                imageslayout.setVisibility(View.GONE);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                Reg_NoEdit.setEnabled(false);
                sectortypespinner.setEnabled(false);
                hce_nameEdit.setEnabled(false);
                AddressEdit.setEnabled(false);
                Email.setEnabled(false);  //Add Email
                EmailSP.setEnabled(false);
                hcetypespinner.setEnabled(false);
                HCSP_nameEdit.setEnabled(false);
                HCSP_ContactEdit.setEnabled(false);
                bedsEdit.setEnabled(false);
                infoVisitDate.setEnabled(false);
                timePicker1.setEnabled(false);
                latEdit.setEnabled(true);
                lngEdit.setEnabled(true);
                locDateTimeEdit.setEnabled(true);
                loctimePickerEdit.setEnabled(true);

                reg_layout.setVisibility(View.GONE);
                Sector_layout.setVisibility(View.GONE);
                hce_name_layout.setVisibility(View.GONE);
                Address_layout.setVisibility(View.GONE);
                hcetype_layout.setVisibility(View.GONE);
                HCSP_Name_layout.setVisibility(View.GONE);
                Mobile_layout.setVisibility(View.GONE);
                beds_layout.setVisibility(View.GONE);
                txtDateTime_layout.setVisibility(View.GONE);
                timePicker1_layout.setVisibility(View.GONE);
                currlocText="Please Select";
                if (currlocText != null) {
                    int spinnerPosition = currloc_adapter.getPosition(currlocText);
                    currloc_spinner.setSelection(spinnerPosition);
                }
                if(currlocText.equals("Yes")) {
                    lat_layout.setVisibility(View.VISIBLE);
                    lng_layout.setVisibility(View.VISIBLE);
                }
                else {
                    lat_layout.setVisibility(View.GONE);
                    lng_layout.setVisibility(View.GONE);
                }


            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index=0;
                btn_loc.setVisibility(View.VISIBLE);
                btn_info.setVisibility(View.VISIBLE);
                layout_submit.setVisibility(View.GONE);
                indicatelabel.setVisibility(View.GONE);
                loc_layout.setVisibility(View.GONE);
                errortextlayout.setVisibility(View.GONE);
                locindlabel.setVisibility(View.GONE);
                infolayout.setVisibility(View.VISIBLE);
                loclayout.setVisibility(View.VISIBLE);
                imageslayout.setVisibility(View.VISIBLE);
                Reg_NoEdit.setEnabled(false);
                sectortypespinner.setEnabled(false);
                hce_nameEdit.setEnabled(false);
                AddressEdit.setEnabled(false);
                Email.setEnabled(false);  //Add Email
                EmailSP.setEnabled(false);
                hcetypespinner.setEnabled(false);
                HCSP_nameEdit.setEnabled(false);
                HCSP_ContactEdit.setEnabled(false);
                bedsEdit.setEnabled(false);
                infoVisitDate.setEnabled(false);
                timePicker1.setEnabled(false);
                latEdit.setEnabled(false);
                lngEdit.setEnabled(false);
                locDateTimeEdit.setEnabled(false);
                loctimePickerEdit.setEnabled(false);

                reg_layout.setVisibility(View.VISIBLE);
                Sector_layout.setVisibility(View.VISIBLE);
                hce_name_layout.setVisibility(View.VISIBLE);
                Address_layout.setVisibility(View.VISIBLE);
                hcetype_layout.setVisibility(View.VISIBLE);
                HCSP_Name_layout.setVisibility(View.VISIBLE);
                Mobile_layout.setVisibility(View.VISIBLE);
                beds_layout.setVisibility(View.VISIBLE);
                txtDateTime_layout.setVisibility(View.VISIBLE);
                timePicker1_layout.setVisibility(View.VISIBLE);
                lat_layout.setVisibility(View.VISIBLE);
                lng_layout.setVisibility(View.VISIBLE);
                locDatelayout.setVisibility(View.VISIBLE);
                loctimelayout.setVisibility(View.VISIBLE);

                HCSP_nameEdit.setText(HCSP_nameText);
                latEdit.setText(latText);
                lngEdit.setText(lngText);
                HCSP_ContactEdit.setText(HCSP_ContactText);
                bedsEdit.setText(total_beds);
                timePicker1.setText(BasicInfoVisitedTime);
                loctimePickerEdit.setText(LoctionVisitedTime);
                if(date2.equals("")){
                    locDateTimeEdit.setText(LoctionVisitedDate);
                }
                else {
                    locDateTimeEdit.setText(date2);
                }
                if(date1.equals("")){
                    infoVisitDate.setText(infoVisitDatetext);
                }
                else {
                    infoVisitDate.setText(date1);
                }

            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    int count=0;
                LoctionVisitedDate   = locDateTimeEdit.getText().toString();
                LoctionVisitedTime = loctimePickerEdit.getText().toString();
                HCSP_ContactText= HCSP_ContactEdit.getText().toString();
                HCSP_nameText=HCSP_nameEdit.getText().toString();
                HCEEmail= Email.getText().toString(); // Add Email
                HCEEmailSP= EmailSP.getText().toString();
                total_beds= bedsEdit.getText().toString();
                infoVisitDatetext=infoVisitDate.getText().toString();
                BasicInfoVisitedTime=  timePicker1.getText().toString();
                if(index==2) {
                    if(currlocText.equals("Yes")) {
                        currlocID = "1";
                        latText=latEdit.getText().toString();
                        lngText=lngEdit.getText().toString();
                        if (latText.equals("") || latText.equals("0.0")) {
                            errortextlayout.setVisibility(View.VISIBLE);
                            count++;

                        }
                        if (lngText.equals("") || lngText.equals("0.0")) {
                            errortextlayout.setVisibility(View.VISIBLE);
                            count++;

                        }
                    }
                    else if(currlocText.equals("No")) {
                        currlocID = "0";
                        latText="0.0";
                        lngText="0.0";
                    }
                    else {
                        currlocID = "-1";
                        errortextlayout.setVisibility(View.VISIBLE);
                        count++;
                    }


                    if (LoctionVisitedDate.equals("")) {
                        errortextlayout.setVisibility(View.VISIBLE);
                        count++;

                    }
                    if (LoctionVisitedTime.equals("")) {
                        errortextlayout.setVisibility(View.VISIBLE);
                        count++;

                    }
                }


                else if(index==1){
                    if (infoVisitDatetext.equals("")) {
                        errortextlayout.setVisibility(View.VISIBLE);
                        count++;

                    }
                    if (BasicInfoVisitedTime.equals("")) {
                        errortextlayout.setVisibility(View.VISIBLE);
                        count++;

                    }
                }
                if(count<1) {
                    pDialog = new ProgressDialog(context);
                    pDialog.setMessage("Saving Data, Please wait...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    String url = getDirectionsUrl(context);
                    DownloadTask downloadTask = new DownloadTask();
                    //Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                }

            }
        });
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





    }
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
if(index==1){
    infoVisitDate.setText(sdf.format(calendar.getTime()));
}
else if(index==2){
    locDateTimeEdit.setText(sdf.format(calendar.getTime()));
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
                        startActivity(new Intent(context, InspectionFilterActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_reportquack:
                        startActivity(new Intent(context, ReportQuackActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
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
                        if(RoleID.equals("3")){
                            startActivity(new Intent(context, InspectionVisitsActivity.class));

                        }
                        else {
                            startActivity(new Intent(context, IndReportingActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));

                        } drawer.closeDrawers();
                        return true;

                    case R.id.nav_resetPassword:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(context, ChangePasswordActivity.class).putExtra("email",email).putExtra("password",password));
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
        String token=context.getResources().getString(R.string.token);
        String url="";
        if(index==1) {
            url = baseurl + "SaveHCEBasicInfo?strToken="+token+"&dataType="+sectortypetext+"&orgName="+hce_nameText+"&orgType="+hceTypetext+"&PersonName="+HCSP_nameText+"&Address="+AddressText+"&MobileNumber="+HCSP_ContactText+"&bedStrength="+total_beds+"&VisitedBy="+UserID+"&VisitedDate="+infoVisitDatetext+"&VisitedTime="+BasicInfoVisitedTime+"&phcRegistrationNumber="+Reg_NoText+"&FinalId="+final_id+"&RoleID="+RoleID+"&Mode=Mobile&HCP_RecID="+recID+"&UserLat="+cur_latitude+"&UserLng="+cur_longitude;
        }
        else if(index==2) {

            url=baseurl+"SaveHCELocation?strToken="+token+"&VisitedBy="+UserID+"&VisitedDate="+LoctionVisitedDate+"&VisitedTime="+LoctionVisitedTime+"&phcRegistrationNumber="+Reg_NoText+"&lat="+latText+"&lng="+lngText+"&RoleID="+RoleID+"&Mode=Mobile&dataType="+sectortypetext+"&orgName="+hce_nameText+"&orgType="+hceTypetext+"&HCP_RecID="+recID+"&isCurrentLoc="+currlocID+"&UserLat="+cur_latitude+"&UserLng="+cur_longitude;
        }

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
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (result != null) {
                if (MID.equals("1")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            context).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Success");

                    // Setting Dialog Message
                    alertDialog.setMessage("Your Update has been submitted");
                    // Setting Icon to Dialog
                    //  alertDialog.setIcon(R.drawable.tick);

                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog closed
                            index=0;
                            btn_loc.setVisibility(View.VISIBLE);
                            btn_info.setVisibility(View.VISIBLE);
                            layout_submit.setVisibility(View.GONE);
                            indicatelabel.setVisibility(View.GONE);
                            loc_layout.setVisibility(View.GONE);
                            errortextlayout.setVisibility(View.GONE);
                            locindlabel.setVisibility(View.GONE);
                            infolayout.setVisibility(View.VISIBLE);
                            loclayout.setVisibility(View.VISIBLE);
                            imageslayout.setVisibility(View.VISIBLE);
                            Reg_NoEdit.setEnabled(false);
                            sectortypespinner.setEnabled(false);
                            hce_nameEdit.setEnabled(false);
                            AddressEdit.setEnabled(false);
                            hcetypespinner.setEnabled(false);
                            HCSP_nameEdit.setEnabled(false);
                            HCSP_ContactEdit.setEnabled(false);
                            bedsEdit.setEnabled(false);
                            infoVisitDate.setEnabled(false);
                            timePicker1.setEnabled(false);
                            latEdit.setEnabled(false);
                            lngEdit.setEnabled(false);
                            locDateTimeEdit.setEnabled(false);
                            loctimePickerEdit.setEnabled(false);

                            reg_layout.setVisibility(View.VISIBLE);
                            Sector_layout.setVisibility(View.VISIBLE);
                            hce_name_layout.setVisibility(View.VISIBLE);
                            Address_layout.setVisibility(View.VISIBLE);
                            hcetype_layout.setVisibility(View.VISIBLE);
                            HCSP_Name_layout.setVisibility(View.VISIBLE);
                            Mobile_layout.setVisibility(View.VISIBLE);
                            beds_layout.setVisibility(View.VISIBLE);
                            txtDateTime_layout.setVisibility(View.VISIBLE);
                            timePicker1_layout.setVisibility(View.VISIBLE);
                            lat_layout.setVisibility(View.VISIBLE);
                            lng_layout.setVisibility(View.VISIBLE);
                            locDatelayout.setVisibility(View.VISIBLE);
                            loctimelayout.setVisibility(View.VISIBLE);
                            //finish();
                            //  Toast.makeText(context, "Thanks You!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                }
                else {


                    Toast.makeText(context, "Update not submitted! Please Verify", Toast.LENGTH_SHORT).show();

                }
                // Updating parsed JSON data into ListView

            }
            else{
                Toast.makeText(context, "Server is not responding. Please try again", Toast.LENGTH_SHORT).show();
            }


        }

    }

    ////-----------------Upload Images------
    // @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestRuntimePermission() {
        if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    public void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        MY_REQUEST_CODE);
            }
        }


    }
    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA))
        {

            // Toast.makeText(EditHCEFragment.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(this,new String[]{

                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
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
                picAttachement=false;
                pDialog = new ProgressDialog(context);
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
                    picAttachement=true;
                    picTaken = false;
                    //getting image from gallery
                    //Bitmap bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(filePath));
                    pDialog = new ProgressDialog(context);
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
                        picUploaded=false;
                        imagepath = null;
                        imageNAme=null;
                        directoryPath=null;
                        picreceved = true;
                        FTPClient client = new FTPClient();

                        client.connect(ftpbaseurl);
                        client.login("mobile", "Document2"); //this is the login credentials of your ftpserver. Ensure to use valid username and password otherwise it throws exception

                        try {
                            client.setFileType(FTP.BINARY_FILE_TYPE);
                            // buffIn = new BufferedInputStream(new FileInputStream(file));
                            fis = new FileInputStream(file);
                            client.enterLocalPassiveMode();
                            client.makeDirectory("HCEImages/Inspection");
                            client.makeDirectory("HCEImages/Inspection/" + Reg_NoText); //I want to upload picture in MyPictures directory/folder. you can use your own.
                            client.makeDirectory("HCEImages/Inspection/" + Reg_NoText + "/" + username);
                            client.makeDirectory("HCEImages/Inspection/" + Reg_NoText + "/" + username + "/Camera");
                            directoryPath="HCEImages/Inspection/" + Reg_NoText + "/" + username + "/Camera";
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
                            imageNAme=Reg_NoText + "_" + currentDateandTime+extension;
                            imagepath = "Inspection/"+Reg_NoText + "/" + username + "/Camera/" + imageNAme;

                            try
                            {
                                if (!client.storeFile("HCEImages/" + imagepath, fis))//this is actual file uploading on FtpServer in specified directory/folder
                                {
                                    throw new Exception("Unable to write file to FTP server");
                                }
                                //Make sure to always close the inputStream
                            }
                            finally
                            {
                                fis.close();
                            }
                            InputStream inputStream = client.retrieveFileStream("/"+directoryPath+"/"+imageNAme);
                            int returnCode = client.getReplyCode();
                            if (inputStream == null || returnCode == 550) {
                                picUploaded=false;
                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                Toast.makeText(context, "Picture not uploaded! Please try again", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                picUploaded=true;
                                //countPictures++;
                                //Toast.makeText(context, "Picture uploaded!", Toast.LENGTH_SHORT).show();
                                String url = getDirectionsUrl2(context);
                                DownloadTask2 downloadTask = new DownloadTask2();
                                //Start downloading json data from Google Directions API
                                downloadTask.execute(url);
                            }
                            //totalpics.setText("Total pictures uploaded: " + countPictures);
                        }

                        try
                        {

                            //10 seconds to log off.  Also 10 seconds to disconnect.
                            client.setSoTimeout(10000);
                            client.logout();

                            //depending on the state of the server the .logout() may throw an exception,
                            //we want to ensure complete disconnect.
                        }
                        catch(Exception innerException)
                        {

                            //You potentially just want to log that there was a logout exception.

                        }
                        finally
                        {
                            //Make sure to always disconnect.  If not, there is a chance you will leave hanging sockects
                            client.disconnect();
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(picUploaded) {
                      // String url = getDirectionsUrl2(context);
                     //   DownloadTask2 downloadTask = new DownloadTask2();
                        //Start downloading json data from Google Directions API
                    //  downloadTask.execute(url);
                    }
                }


            });
            thread.start();
            pd.dismiss();


        }
        else
        {
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
                        picUploaded=false;
                        imagepath=null;
                        imageNAme=null;
                        directoryPath=null;
                        picreceved=true;
                        FTPClient client = new FTPClient();

                        client.connect(ftpbaseurl);
                        client.login("mobile", "Document2"); //this is the login credentials of your ftpserver. Ensure to use valid username and password otherwise it throws exception

                        try {
                            client.setFileType(FTP.BINARY_FILE_TYPE);
                            fis = new FileInputStream(file);
                            client.enterLocalPassiveMode();
                            client.makeDirectory("HCEImages/Inspection");
                            client.makeDirectory("HCEImages/Inspection/" + Reg_NoText); //I want to upload picture in MyPictures directory/folder. you can use your own.
                            client.makeDirectory("HCEImages/Inspection/" + Reg_NoText+"/"+username);
                            client.makeDirectory("HCEImages/Inspection/" + Reg_NoText+"/"+username+"/Attachments");
                            directoryPath="HCEImages/Inspection/" + Reg_NoText+"/"+username+"/Attachments";
                        } catch (Exception e) {
                            if (pDialog.isShowing())
                                pDialog.dismiss();
                            picreceved=false;
                            Toast.makeText(context, "Picture not found! Please try again", Toast.LENGTH_SHORT).show();
                        }

                        if(picreceved) {
                            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
                            String currentDateandTime = sdf.format(new Date());
                            imageNAme=Reg_NoText + "_" + currentDateandTime+extension;
                            imagepath ="Inspection/"+ Reg_NoText +"/"+username+"/Attachments/" + imageNAme;
                            try
                            {
                                if (!client.storeFile("HCEImages/" + imagepath, fis))//this is actual file uploading on FtpServer in specified directory/folder
                                {
                                    throw new Exception("Unable to write file to FTP server");
                                }
                                //Make sure to always close the inputStream
                            }
                            finally
                            {
                                fis.close();
                            }
                            InputStream inputStream = client.retrieveFileStream("/"+directoryPath+"/"+imageNAme);
                            int returnCode = client.getReplyCode();
                            if (inputStream == null || returnCode == 550) {
                                picUploaded=false;
                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                Toast.makeText(context, "Picture not uploaded! Please try again", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                picUploaded=true;
                               // countPictures++;
                              //  Toast.makeText(context, "Picture uploaded!", Toast.LENGTH_SHORT).show();
                                String url = getDirectionsUrl2(context);
                                DownloadTask2 downloadTask = new DownloadTask2();
                                //Start downloading json data from Google Directions API
                                downloadTask.execute(url);
                            }
                            //totalpics.setText("Total pictures uploaded: " + countPictures);
                        }
                        try
                        {

                            //10 seconds to log off.  Also 10 seconds to disconnect.
                            client.setSoTimeout(10000);
                            client.logout();

                            //depending on the state of the server the .logout() may throw an exception,
                            //we want to ensure complete disconnect.
                        }
                        catch(Exception innerException)
                        {

                            //You potentially just want to log that there was a logout exception.

                        }
                        finally
                        {
                            //Make sure to always disconnect.  If not, there is a chance you will leave hanging sockects
                            client.disconnect();
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(picUploaded) {
                        //String url = getDirectionsUrl2(context);
                     //DownloadTask2 downloadTask = new DownloadTask2();
                        //Start downloading json data from Google Directions API
                       //downloadTask.execute(url);
                    }
                }

            });
            thread.start();
            pd.dismiss();


        }
        else
        {
            //   Toast.makeText(context, "Please Take Picture First than Upload.", Toast.LENGTH_LONG).show();

        }
    }
    /**
     * Creating file uri to store image/video
     */
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
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
    //=====================Images End=======================
//-------------------------Gellery------------------------------
    public class GalleryPagerAdapter extends PagerAdapter {

        Context _context;
        LayoutInflater _inflater;

        public GalleryPagerAdapter(Context context) {
            _context = context;
            _inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return _images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = _inflater.inflate(R.layout.pager_gallery_item, container, false);
            container.addView(itemView);

            // Get the border size to show around each image
            int borderSize = _thumbnails.getPaddingTop();

            // Get the size of the actual thumbnail image
            int thumbnailSize = ((FrameLayout.LayoutParams)
                    _pager.getLayoutParams()).bottomMargin - (borderSize*2);

            // Set the thumbnail layout parameters. Adjust as required
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(thumbnailSize, thumbnailSize);
            params.setMargins(0, 0, borderSize, 0);

            // You could also set like so to remove borders
            //ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
            //        ViewGroup.LayoutParams.WRAP_CONTENT,
            //        ViewGroup.LayoutParams.WRAP_CONTENT);

            final ImageView thumbView = new ImageView(_context);
            thumbView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            thumbView.setLayoutParams(params);
            thumbView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Thumbnail clicked");

                    // Set the pager position when thumbnail clicked
                    _pager.setCurrentItem(position);
                }
            });
            _thumbnails.addView(thumbView);

            final SubsamplingScaleImageView imageView =
                    (SubsamplingScaleImageView) itemView.findViewById(R.id.image);

            // Asynchronously load the image and set the thumbnail and pager view
            Glide.with(_context)
                    .load(_images.get(position))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            imageView.setImage(ImageSource.bitmap(bitmap));
                            thumbView.setImageBitmap(bitmap);
                        }
                    });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(_context, GalleryActivity.class);
                    intent.putStringArrayListExtra(GalleryActivity.EXTRA_NAME, _images);
                    startActivity(intent);
                }
            });
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    //=====================Gallery End=======================



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
            jsonStr2=result;
            // Invokes the thread for parsing the JSON data
            parserTask.execute();

        }
    }
    private String getDirectionsUrl2(Context context) {

        // Building the url to the web service
        String baseurl=context.getResources().getString(R.string.baseurl);
        String token=context.getResources().getString(R.string.token);
        String url=baseurl+"SaveHCEImageByInspection?strToken="+token+"&FinalID="+final_id+"&RegNum="+Reg_NoText+"&ImagePath="+imagepath+"&Lat="+cur_latitude+"&Lng="+cur_longitude+"&emailAddress="+email+"&comments=testing&RoleID="+RoleID+"&HCP_RecID="+recID;
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

            }
            else {
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
                if (MID2.equals("1")) {
                    Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    String imagebaseurl=context.getResources().getString(R.string.Imagesbaseurl);
                    _images.add(imagebaseurl+imagepath);
                    _adapter.notifyDataSetChanged();
                    imagepath=null;

                }
                else {
                    Toast.makeText(context, "Update not submitted! Please Verify", Toast.LENGTH_SHORT).show();

                }
                // Updating parsed JSON data into ListView

            }
            else{
                Toast.makeText(context, "Server is not responding. Please try again", Toast.LENGTH_SHORT).show();
            }


        }

    }
}


