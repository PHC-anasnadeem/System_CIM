package com.phc.cim.Activities.Licensing;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.cim.Activities.Common.AboutusActivity;
import com.phc.cim.Activities.Common.ChangePasswordActivity;
import com.phc.cim.Activities.Common.DesealListing;
import com.phc.cim.Activities.Common.FilterActivity;
import com.phc.cim.Activities.Common.IndReportingActivity;
import com.phc.cim.Activities.Common.QuackActivity;
import com.phc.cim.Activities.Common.ReportQuackActivity;
import com.phc.cim.Activities.Inspection.InspectionFilterActivity;
import com.phc.cim.Activities.Inspection.InspectionVisitsActivity;
import com.phc.cim.Adapters.PWSListAdapter;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.ButterKnife;

public class PWSDetailViewActivity extends AppCompatActivity {

    double des_lat;
    double des_lng;
    DataManager dataManager;
    CurrentLocation gps;
    EditText hce_nameEdit;
    EditText AddressEdit;
    EditText HCSP_nameEdit;
    EditText HCSP_SOEdit;
    EditText CNIC_Edit;
    EditText HCSP_ContactEdit;
    EditText Reg_NoEdit;
    EditText coun_NoEdit;
    EditText reg_dateEdit, pl_noEdit, rl_dateEdit, rl_noEdit, fee_rcvdEdit, regcertissuEdit, reasonEdit, total_bedsEdit, pl_dateEdit, dereg_noEdit, dereg_dateEdit;

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
    String RegType = "";

    String districtText = "";
    String sectortypetext = "";
    String hceTypetext = "";
    String HCSPTypeText = "";
    String RegstatusText = "";
    String counStatusText = "";
    String counciltypetext = "";
    String updatestatustext = "";
    String substatustext = "";
    String counStatusID = "";
    String RegstatusID = "";
    double latitude;
    double longitude;
    String email = "";
    double cur_latitude;
    double cur_longitude;
    ImageView imageView;
    TextView textView;
    Context context;
    ListView listView;
    ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> indtabresult;
    int PICK_IMAGE_REQUEST = 111;
    private Uri u = null;
    private Boolean picTaken = false;
    private Boolean picreceved = false;
    private Boolean picUploaded = false;
    private Boolean picAttachement = false;
    int count = 0;
    private String filePath = null;
    String directoryPath = null;
    private Uri filePathURI;
    int MY_REQUEST_CODE = 5;
    String imageNAme = null;
    int TotalRecord;
    int PageSize;
    String MID2, MText2, jsonStr2;
    //-----End Images----
    // ArrayList<String> mylist;
    int currentPage = 1;
    String MID;
    String MText;
    String jsonStr;
    EditText latEdit;
    EditText lngEdit;
    EditText coments;

    TextInputLayout substatusInput;
    EditText timePicker1;
    EditText infoVisitDate;
    EditText loctimePickerEdit;
    EditText locDateTimeEdit;
    EditText bedsEdit;
    String FeeReceived = "";
    String lngText = "";
    String password = "";
    String isEdit = "";
    String username = "";
    String isStat;
    String UserID;
    String RoleID;
    String HCEindex;
    String Hcp_recID = "";
    String PHCDeRegDate = "";
    String PHCDeRegNo = "";
    String Reason = "";
    String RegistrationCertificateIssued = "";
    String PHCPLDate = "";
    String PHCPLNo = "";
    String PHCRLDate = "";
    String PHCRLNo = "";
    String PHCRegDate = "";
    String currlocID = "-1";
    String total_beds = "";
    Button btn_info, btn_loc, btn_save, btn_cancel;
    LinearLayout layout_submit, errortextlayout, infolayout, loclayout, imageslayout;
    TextInputLayout loc_layout, reg_layout, Sector_layout, hce_name_layout, Address_layout, hcetype_layout, HCSP_Name_layout, Mobile_layout,
            beds_layout, txtDateTime_layout, timePicker1_layout, lat_layout, lng_layout, locDatelayout, loctimelayout;
    TextView indicatelabel;
    TextView locindlabel, latvisitinfo, latvisitloc;
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
    String date1 = "";
    String date2 = "";
    String appURI = "";
    String BfromText, BtoText, RegnoText, hcenameText, CNICtext;
    //ArrayAdapter adapter;
    PWSListAdapter pwsListAdapter;
    //MyCustomPagerAdapter myCustomPagerAdapter;
    int index;
    //it will tell us weather to load more items or not
    boolean loadingMore = false;
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
    View footerView;
    Calendar calendar;
    public static final String TAG = "GalleryActivity";
    ViewPager _pager;
    LinearLayout _thumbnails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pws_detailview_activity);
        context = this;


        dataManager = new DataManager(context);
        gps = new CurrentLocation(context);

        hce_nameEdit = (EditText) findViewById(R.id.hce_name);
        AddressEdit = (EditText) findViewById(R.id.Address);
        HCSP_nameEdit = (EditText) findViewById(R.id.HCSP_Name);
        CNIC_Edit = (EditText) findViewById(R.id.CNIC);
        HCSP_ContactEdit = (EditText) findViewById(R.id.Mobile);
        coun_NoEdit = (EditText) findViewById(R.id.council_no);
        district_spinner = (EditText) findViewById(R.id.district);
        sectortypespinner = (EditText) findViewById(R.id.Sector_Type);
        hcetypespinner = (EditText) findViewById(R.id.hcetype);
        HCSP_spinner = (EditText) findViewById(R.id.HCSPType);
        counciltypespiner = (EditText) findViewById(R.id.counType);
        Reg_NoEdit = (EditText) findViewById(R.id.reg_no);
        reg_dateEdit = (EditText) findViewById(R.id.reg_dateEdit);
        dereg_dateEdit = (EditText) findViewById(R.id.dereg_dateEdit);
        dereg_noEdit = (EditText) findViewById(R.id.dereg_noEdit);
        pl_dateEdit = (EditText) findViewById(R.id.pl_dateEdit);
        pl_noEdit = (EditText) findViewById(R.id.pl_noEdit);
        rl_dateEdit = (EditText) findViewById(R.id.rl_dateEdit);
        rl_noEdit = (EditText) findViewById(R.id.rl_noEdit);
        fee_rcvdEdit = (EditText) findViewById(R.id.fee_rcvdEdit);
        regcertissuEdit = (EditText) findViewById(R.id.regcertissuEdit);
        reasonEdit = (EditText) findViewById(R.id.reasonEdit);
        total_bedsEdit = (EditText) findViewById(R.id.total_bedsEdit);


        hce_nameEdit.setEnabled(false);
        AddressEdit.setEnabled(false);
        HCSP_nameEdit.setEnabled(false);
        CNIC_Edit.setEnabled(false);
        HCSP_ContactEdit.setEnabled(false);
        Reg_NoEdit.setEnabled(false);
        coun_NoEdit.setEnabled(false);
        district_spinner.setEnabled(false);
        sectortypespinner.setEnabled(false);
        hcetypespinner.setEnabled(false);
        HCSP_spinner.setEnabled(false);
        counciltypespiner.setEnabled(false);
        reg_dateEdit.setEnabled(false);
        dereg_dateEdit.setEnabled(false);
        dereg_noEdit.setEnabled(false);
        pl_dateEdit.setEnabled(false);
        pl_noEdit.setEnabled(false);
        rl_dateEdit.setEnabled(false);
        rl_noEdit.setEnabled(false);
        fee_rcvdEdit.setEnabled(false);
        regcertissuEdit.setEnabled(false);
        reasonEdit.setEnabled(false);
        total_bedsEdit.setEnabled(false);

        // imageView =(ImageView) obj_view.findViewById(R.id.logo);
        //textView = (TextView) findViewById(R.id.text_view);
        hce_namelayout = (TextInputLayout) findViewById(R.id.hce_name_layout);
        Addresslayout = (TextInputLayout) findViewById(R.id.Address_layout);
        HCSP_namelayout = (TextInputLayout) findViewById(R.id.HCSP_Name_layout);
        CNIC_layout = (TextInputLayout) findViewById(R.id.CNIC_layout);
        HCSP_Contactlayout = (TextInputLayout) findViewById(R.id.Mobile_layout);
        district_layout = (TextInputLayout) findViewById(R.id.district_layout);
        sectortypelayout = (TextInputLayout) findViewById(R.id.Sector_Type_layout);
        hcetypelayout = (TextInputLayout) findViewById(R.id.hcetype_layout);
        HCSP_layout = (TextInputLayout) findViewById(R.id.HCSPType_layout);
        counTypeInput = (TextInputLayout) findViewById(R.id.counclType);
        counNoInput = (TextInputLayout) findViewById(R.id.council);
        regNoInput = (TextInputLayout) findViewById(R.id.reg);

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
        UserID = prefs.getString("UserID", null); //0 is the default value.
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

        activityTitles = getResources().getString(R.string.nav_pwsview_titles);
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
        //textView.setVisibility(View.VISIBLE);
 /*       hce_namelayout.setVisibility(View.GONE);
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
        regNoInput.setVisibility(View.GONE);
        counTypeInput.setVisibility(View.GONE);
        counNoInput.setVisibility(View.GONE);*/
        Intent intent;
        intent = getIntent();

        HCEindex = (String) intent.getSerializableExtra("index");
        AddressText = (String) intent.getSerializableExtra("Address");
        CNIC_Text = (String) intent.getSerializableExtra("CNIC");
        counciltypetext = (String) intent.getSerializableExtra("CouncilName");
        coun_NoText = (String) intent.getSerializableExtra("CouncilNo");
        districtText = (String) intent.getSerializableExtra("District");
        hce_nameText = (String) intent.getSerializableExtra("HCEName");
        hceTypetext = (String) intent.getSerializableExtra("HCEType");
        HCSP_ContactText = (String) intent.getSerializableExtra("Mobile");
        HCSP_nameText = (String) intent.getSerializableExtra("SPName");
        HCSPTypeText = (String) intent.getSerializableExtra("SPType");
        sectortypetext = (String) intent.getSerializableExtra("SectorType");
        Reg_NoText = (String) intent.getSerializableExtra("PHCRegNo");
        PHCRegDate = (String) intent.getSerializableExtra("PHCRegDate");
        PHCDeRegDate = (String) intent.getSerializableExtra("PHCDeRegDate");
        PHCDeRegNo = (String) intent.getSerializableExtra("PHCDeRegNo");
        PHCPLDate = (String) intent.getSerializableExtra("PHCPLDate");
        PHCPLNo = (String) intent.getSerializableExtra("PHCPLNo");
        PHCRLDate = (String) intent.getSerializableExtra("PHCRLDate");
        PHCRLNo = (String) intent.getSerializableExtra("PHCRLNo");
        FeeReceived = (String) intent.getSerializableExtra("FeeReceived");
        Reason = (String) intent.getSerializableExtra("Reason");
        RegistrationCertificateIssued = (String) intent.getSerializableExtra("RegistrationCertificateIssued");
        total_beds = (String) intent.getSerializableExtra("bedStrength");
        Hcp_recID = (String) intent.getSerializableExtra("Hcp_recID");


        if (AddressText.equals("null")) {
            AddressText = "";
        }
        if (CNIC_Text.equals("null")) {
            CNIC_Text = "";
        }
        if (counciltypetext.equals("null")) {
            counciltypetext = "";
        }
        if (coun_NoText.equals("null")) {
            coun_NoText = "";
        }
        if (districtText.equals("null")) {
            districtText = "";
        }
        if (hce_nameText.equals("null")) {
            hce_nameText = "";
        }
        if (hceTypetext.equals("null")) {
            hceTypetext = "";
        }
        if (HCSP_ContactText.equals("null")) {
            HCSP_ContactText = "";
        }
        if (HCSP_nameText.equals("null")) {
            HCSP_nameText = "";
        }
        if (HCSPTypeText.equals("null")) {
            HCSPTypeText = "";
        }
        if (sectortypetext.equals("null")) {
            sectortypetext = "";
        }
        if (Reg_NoText.equals("null")) {
            Reg_NoText = "";
        }
        if (PHCRegDate.equals("null")) {
            PHCRegDate = "";
        }
        if (PHCDeRegDate.equals("null")) {
            PHCDeRegDate = "";
        }
        if (PHCDeRegNo.equals("null")) {
            PHCDeRegNo = "";
        }
        if (PHCPLDate.equals("null")) {
            PHCPLDate = "";
        }
        if (PHCPLNo.equals("null")) {
            PHCPLNo = "";
        }
        if (PHCRLDate.equals("null")) {
            PHCRLDate = "";
        }
        if (PHCRLNo.equals("null")) {
            PHCRLNo = "";
        }
        if (FeeReceived.equals("null")) {
            FeeReceived = "";
        }
        if (Reason.equals("null")) {
            Reason = "";
        }
        if (RegistrationCertificateIssued.equals("null")) {
            RegistrationCertificateIssued = "";
        }
        if (total_beds.equals("null")) {
            total_beds = "";
        }


        hce_nameEdit.setText(hce_nameText);
        AddressEdit.setText(AddressText);
        HCSP_nameEdit.setText(HCSP_nameText);
        CNIC_Edit.setText(CNIC_Text);
        HCSP_ContactEdit.setText(HCSP_ContactText);
        Reg_NoEdit.setText(Reg_NoText);
        coun_NoEdit.setText(coun_NoText);
        district_spinner.setText(districtText);
        sectortypespinner.setText(sectortypetext);
        hcetypespinner.setText(hceTypetext);
        HCSP_spinner.setText(HCSPTypeText);
        counciltypespiner.setText(counciltypetext);
        reg_dateEdit.setText(PHCRegDate);
        dereg_dateEdit.setText(PHCDeRegDate);
        dereg_noEdit.setText(PHCDeRegNo);
        pl_dateEdit.setText(PHCPLDate);
        pl_noEdit.setText(PHCPLNo);
        rl_dateEdit.setText(PHCRLDate);
        rl_noEdit.setText(PHCRLNo);
        fee_rcvdEdit.setText(FeeReceived);
        regcertissuEdit.setText(RegistrationCertificateIssued);
        reasonEdit.setText(Reason);
        total_bedsEdit.setText(total_beds);

        Button canceldone = (Button) findViewById(R.id.btn_cancel);
        canceldone.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {

                onBackPressed();
            }
        });





      /*  if(counStatusText=="Yes"){
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
        }*/

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
                        if (RoleID.equals("3")) {
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
                        if (RoleID.equals("3")) {
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
}
