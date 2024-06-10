package com.phc.cim.TabsActivities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.OpenableColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.cim.Activities.Aqc.ActionHistoryActivity;
import com.phc.cim.Activities.Common.DesealListing;
import com.phc.cim.Activities.Common.FilterActivity;
import com.phc.cim.Activities.Common.HearingStatusActivity;
import com.phc.cim.Activities.Common.IndReportingActivity;
import com.phc.cim.Activities.Common.QuackActivity;
import com.phc.cim.Activities.Common.ReportQuackActivity;
import com.phc.cim.Activities.Common.RouteMapsActivity;
import com.phc.cim.Activities.Licensing.PWSFilterActivity;
import com.phc.cim.Fragments.DetailViewFragment;
import com.phc.cim.Extra.HomeFragment;
import com.phc.cim.Extra.NotificationFragment;
import com.phc.cim.Fragments.PWSDetailFragment;
import com.phc.cim.Extra.PhotosFragment;
import com.phc.cim.Extra.SettingFragment;
import com.phc.cim.Extra.VideosFragment;
import com.phc.cim.Activities.Common.AboutusActivity;
import com.phc.cim.Activities.Common.ChangePasswordActivity;
import com.phc.cim.Others.Logout;
import com.phc.cim.R;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPSClient;
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
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class DetailTabActivity extends AppCompatActivity {


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
    };
    private RelativeLayout photos_layout;
    private static Bitmap scaledphoto=null;
    private String filePath=null;
    private Uri filePathURI;
    String directoryPath=null;
    int PICK_IMAGE_REQUEST = 111;
    private Uri u=null;
    private Boolean picTaken=false;
    private Boolean picreceved=false;
    private Boolean picUploaded=false;
    public  static final int RequestPermissionCode  = 1 ;
    private Uri fileUri;
    private Boolean picAttachement=false;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<String> _images;
    String jsonStr2;
    Bundle bundle;
    String MID2;
    String MText2;
    String ftpbaseurl, ftpUser,ftpPas;
    String hce_nameText="";
    String AddressText="";
    String HCSP_nameText="";
    String HCSP_SOText="";
    String CNIC_Text="";
    String HCSP_ContactText="";
    String Reg_NoText="";
    String coun_NoText="";
    String  final_id="";
    String districtText="";
    String sectortypetext="";
    String hceTypetext="";
    String HCSPTypeText="";
    String RegstatusText="";
    String counStatusText="";
    String counciltypetext="";
    String email=null;
    String password;
    String isEdit;
    String username;
    String RecordLockedForUpdate="";
    String total_beds="";
    String index;
    int count=0;
    Context context;
    public static final int MEDIA_TYPE_IMAGE = 1;

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
    double latitude;
    double longitude;

    String appURI = "";
    String time1;
    String RegType="";
    private DownloadManager downloadManager;
    private long downloadReference;
    int MY_REQUEST_CODE=5;
    ProgressDialog pDialog;
    String imagepath,UserName,LastVisitedDate,isStat,RoleID;
    String imageNAme=null;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;
    //private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    //private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";
    public static int navItemIndex = 0;
    private ArrayList<String> imageurls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_view);
        context=this;
        Intent intent;
        intent = getIntent();
        pDialog = new ProgressDialog(context);

 BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
       // BottomNavigationViewHelper.removeShiftMode(navigation);


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
        UserName= (String) intent.getSerializableExtra("UserName");
        LastVisitedDate = (String) intent.getSerializableExtra("LastVisitedDate");
        imageurls = (ArrayList<String>) getIntent().getSerializableExtra("imageurls");

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        isStat = prefs.getString("isStat", null);//"No name defined" is the default value.
        RoleID = prefs.getString("RoleID", null); //0 is the default value.


        bundle = new Bundle();
        bundle.putString("HCEName", hce_nameText);
        bundle.putString("HCEAddress", AddressText);
        bundle.putString("District", districtText);
        bundle.putString("SectorType", sectortypetext);
        bundle.putString("OrgType", hceTypetext);
        bundle.putString("HCSPType", HCSPTypeText);
        bundle.putString("HCSPName", HCSP_nameText);
        bundle.putString("HCSPSO", HCSP_SOText);
        bundle.putString("HCSPCNIC", CNIC_Text);
        bundle.putString("HCSPContactNo", HCSP_ContactText);
        bundle.putString("RegStatus", RegstatusText);
        bundle.putString("RegNum", Reg_NoText);
        bundle.putString("CouncilStatus", counStatusText);
        bundle.putString("CouncilName", counciltypetext);
        bundle.putString("CouncilNum", coun_NoText);
        bundle.putString("final_id", final_id);
        bundle.putString("email", email);
        bundle.putString("password", password);
        bundle.putString("username", username);
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        bundle.putString("index", index);
        bundle.putString("isEdit", isEdit);
        bundle.putString("RecordLockedForUpdate", RecordLockedForUpdate);
        bundle.putString("total_beds", total_beds);
        bundle.putString("RegType", RegType);
        bundle.putString("UserName", UserName);
        bundle.putString("LastVisitedDate", LastVisitedDate);
        bundle.putStringArrayList("imageurls", imageurls);


        toolbar = (Toolbar) findViewById(R.id.toolbar1);
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
        //imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getString(R.string.detail_view);
        ftpbaseurl=context.getResources().getString(R.string.ftpbaseurl);
        ftpUser=context.getResources().getString(R.string.ftpUser);
        ftpPas=context.getResources().getString(R.string.ftpPass);
        loadNavHeader();
        checkPermission();
        EnableRuntimePermission();
        verifyStoragePermissions(this);
        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        if(RoleID.equals("1")) {
            navigationView.getMenu().findItem(R.id.nav_actiondesc).setVisible(true);
            if (isStat.equals("true")) {
                navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(true);


            } else {
                navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(false);
            }
        }
        else {
            navigationView.getMenu().findItem(R.id.nav_actiondesc).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(false);
        }
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#00a652"));
        tabLayout.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#00a652"));
    /*    View root = tabLayout.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(Color.parseColor("#727272"));
            drawable.setSize(2, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }*/
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),bundle);
        adapter.addFragment(new DetailViewFragment(), "Census");
        adapter.addFragment(new PWSDetailFragment(), "PWS");


       // adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final Bundle fragmentBundle;

        public ViewPagerAdapter(FragmentManager manager, Bundle bundle) {
            super(manager);
            fragmentBundle=bundle;
        }

        @Override
        public Fragment getItem(int position) {
            //final MapFragment f = new MapFragment();
            //f.setArguments(this.fragmentBundle);
            mFragmentList.get(position).setArguments(fragmentBundle);
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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
                    case R.id.nav_reportquack:
                        startActivity(new Intent(context, ReportQuackActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_quack:
                        startActivity(new Intent(context, QuackActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_actionsummary:
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
                 /*   case R.id.nav_settings:
                        startActivity(new Intent(context, String.class));
                        drawer.closeDrawers();
                        return true;*/
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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
 /*       if (shouldLoadHomeFragOnBackPress) {
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

        // show menu only when home fragment is selected
    /*    if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
            getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        }*/
        return true;
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 1: {
                if (requestCode == MY_REQUEST_CODE) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // Now user should be able to use camera
                    }
                    else {
                        // Your app will not have this permission. Turn off all functions
                        // that require this permission or it will force close like your
                        // original question
                    }
                }


            }
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
         /*       case R.id.navigation_home:
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
                    if(latitude!=0 && longitude!=0){

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
                        routintent.putExtra("sectortype", sectortypetext);
                        routintent.putExtra("council", counciltypetext);
                        routintent.putExtra("HCEType", hceTypetext);
                        routintent.putExtra("Beds", total_beds);
                        routintent.putExtra("HCSPname",HCSP_nameText);
                        routintent.putExtra("HCSPSO",HCSP_SOText);
                        context.startActivity(routintent);
                    }
                    return true;
                case R.id.navigation_notifications:
                   // if(isEdit.equals("true") && (RecordLockedForUpdate.equals("No") || RecordLockedForUpdate.equals("null"))) {
                    if(isEdit.equals("true")) {
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

                        firstpage.putExtra("latitude", latitude);
                        firstpage.putExtra("longitude", longitude);
                        firstpage.putExtra("UserName", UserName);
                        firstpage.putExtra("LastVisitedDate", LastVisitedDate);
                        context.startActivity(firstpage);
                    }
                    else if(isEdit.equals("false")){
                        Toast.makeText(context, "You are not authorized to edit the information", Toast.LENGTH_SHORT).show();
                    }
                /*    else if(isEdit.equals("true") && RecordLockedForUpdate.equals("Yes")){

                        Toast.makeText(context, "HCE information is under review and is locked for Edit", Toast.LENGTH_SHORT).show();
                    }*/

                    return true;

                case R.id.navigation_attachement:
                    count=1;
                    requestRuntimePermission();
                    Intent attachintent = new Intent();
                    attachintent.setType("image/*");
                    attachintent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(Intent.createChooser(attachintent, "Select Image"), PICK_IMAGE_REQUEST);

                    return true;
                case R.id.navigation_camera:
                    if(Build.VERSION.SDK_INT>=24){
                        try{
                            Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                            m.invoke(null);
                        }catch(Exception e){
                            e.printStackTrace();
                        }

                    }
                    requestRuntimePermission();
                    count=2;
                    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
                    String currentDateandTime = sdf.format(new Date());
                    String pictureName=final_id+"_"+currentDateandTime;//here you can get picture name from user. I supposed Test name
                    Intent intentcamera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    File photo = new File(context.getExternalFilesDir(null),  pictureName+".jpg");//save picture (.jpg) on SD Card
                    u= Uri.fromFile(photo);
                    intentcamera.putExtra(MediaStore.EXTRA_OUTPUT,u);
                    filePath = photo.getAbsolutePath();
                    startActivityForResult(intentcamera, REQUEST_CODE);

                    return true;
                case R.id.navigation_history:
                startActivity(new Intent(context, ActionHistoryActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit).putExtra("index", index).putExtra("hce_nameText",hce_nameText ).putExtra("final_id",final_id ));
                    return true;
            }
            return false;
        }


    };
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(count==2) {
            try {

               // context.getContentResolver().notifyChange(u, null);
               // ContentResolver cr = context.getContentResolver();
              //  Bitmap bm = android.provider.MediaStore.Images.Media.getBitmap(cr, u);
//ImageView to set the picture taken from camera.
                //   mImageView.setImageBitmap(bm);
                picAttachement=false;
                picTaken = true; //to ensure picture is taken
                pDialog = new ProgressDialog(context);
                pDialog.setMessage("Uploading image, Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
                upLoadPicture();
            } catch (Exception e) {

                e.printStackTrace();

            }
        }
        else if(count==1) {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
               // fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
               //filePath= fileUri.getPath();
                filePathURI =data.getData();


               filePath= getRealPathFromUri(context ,filePathURI);

             //  String fileName=   filePath.substring(filePath.indexOf("0"));;
             //   filePath=context.getExternalFilesDir(null) + "/" +fileName;
                //filePath=   getRealPathFromURI_API19(context ,filePathURI);
                try {
                    picAttachement=true;
                    picTaken = false;
                    pDialog = new ProgressDialog(context);
                    pDialog.setMessage("Uploading image, Please wait...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    //getting image from gallery
                    //Bitmap bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(filePath));
                    upLoadPicture();
                    //Setting image to ImageView
                    //  mImageView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

/*        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // successfully captured the image
                // launching upload activity
                launchUploadActivity(true);


            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled Image capture
                Toast.makeText(getContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to capture image
                Toast.makeText(getContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                // video successfully recorded
                // launching upload activity
                launchUploadActivity(false);

            } else if (resultCode == RESULT_CANCELED) {

                // user cancelled recording
                Toast.makeText(getContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();

            } else {
                // failed to record video
                Toast.makeText(getContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }*/
    }
    public void upLoadPicture() {

        if (picTaken) {
            RetrieveFeedTask downloadTask = new RetrieveFeedTask();
            downloadTask.execute();
       /*     final ProgressDialog pd = ProgressDialog.show(context, "Please wait", "Uploading Picture ...", true);
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


                        try {
                            client.setFileType(FTP.BINARY_FILE_TYPE);
                            // buffIn = new BufferedInputStream(new FileInputStream(file));
                            fis = new FileInputStream(file);
                            client.enterLocalPassiveMode();
                            client.makeDirectory("HCEImages/" + final_id); //I want to upload picture in MyPictures directory/folder. you can use your own.
                            client.makeDirectory("HCEImages/" + final_id + "/" + username);
                            client.makeDirectory("HCEImages/" + final_id + "/" + username + "/Camera");
                            directoryPath="HCEImages/" + final_id + "/" + username + "/Camera";
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
                            imageNAme=final_id + "_" + currentDateandTime+extension;
                            imagepath = final_id + "/" + username + "/Camera/" + imageNAme;

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
                                String url = getDirectionsUrl2(context);
                                DownloadTask2 downloadTask = new DownloadTask2();
                                //Start downloading json data from Google Directions API
                                downloadTask.execute(url);
                            }
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

                }

            });
            thread.start();
            pd.dismiss();*/

        }
        else
        {
            // Toast.makeText(context, "Please Take Picture First than Upload.", Toast.LENGTH_LONG).show();

        }

        if (picAttachement) {
            RetrieveFeedTask downloadTask = new RetrieveFeedTask();
            downloadTask.execute();
         /*   final ProgressDialog pd = ProgressDialog.show(context, "Please wait", "Uploading Picture ...", true);
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    String extension = filePath.substring(filePath.lastIndexOf("."));
                    File file = new File(filePath);
                    FileInputStream fis = null;
                    // BufferedInputStream buffIn = null;

             *//*       try {
                        picUploaded=false;
                        imagepath = null;
                        imageNAme=null;
                        directoryPath=null;
                        picreceved=true;
                        FTPSClient client = new FTPSClient();
                        client.setNeedClientAuth(true);
                        TrustManager[] trustManager = new TrustManager[] { new X509TrustManager() {
                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }

                            @Override
                            public void checkClientTrusted(X509Certificate[] certs, String authType) {
                            }

                            @Override
                            public void checkServerTrusted(X509Certificate[] certs, String authType) {
                            }
                        } };

                        client.setTrustManager(trustManager[0]);
                        InputStream  caFileInputStream = context.getResources().openRawResource(R.raw.ali);

                        // We're going to put our certificates in a Keystore

                        KeyStore keyStore = KeyStore.getInstance("PKCS12");
                        keyStore.load(caFileInputStream,"123456789".toCharArray());
                        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                        kmf.init(keyStore, "123456789".toCharArray());
                        // kmf.init(null, null);
                        KeyManager km = kmf.getKeyManagers()[0];
                        client.setKeyManager(km);
                        client.setBufferSize(1024 * 1024);
                        client.setConnectTimeout(100000);
                        SSLContext sc = SSLContext.getInstance("TLS");
                        sc.init(kmf.getKeyManagers(), trustManager, new java.security.SecureRandom());
                        //   client.setSocketFactory(sc.getSocketFactory());
                   {
                            client.execPBSZ(0);
                            client.execPROT("P");

                        }
                        try {
                            client.setFileType(FTP.BINARY_FILE_TYPE);
                            // buffIn = new BufferedInputStream(new FileInputStream(file));
                            fis = new FileInputStream(file);
                            client.enterLocalPassiveMode();
                            client.makeDirectory("HCEImages/" + final_id); //I want to upload picture in MyPictures directory/folder. you can use your own.
                            client.makeDirectory("HCEImages/" + final_id+"/"+username);
                            client.makeDirectory("HCEImages/" + final_id+"/"+username+"/Attachments");
                            directoryPath="HCEImages/" + final_id+"/"+username+"/Attachments";
                        } catch (Exception e) {
                            if (pDialog.isShowing())
                                pDialog.dismiss();
                            picreceved=false;
                            Toast.makeText(context, "Picture not found! Please try again", Toast.LENGTH_SHORT).show();
                        }

                        if(picreceved) {
                            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
                            String currentDateandTime = sdf.format(new Date());
                            imageNAme=final_id + "_" + currentDateandTime+extension;
                            imagepath = final_id +"/"+username+"/Attachments/" + imageNAme;
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
                            int  returnCode = client.getReplyCode();
                            if (inputStream == null || returnCode == 550) {
                                picUploaded=false;
                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                Toast.makeText(context, "Picture not uploaded! Please try again", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String url = getDirectionsUrl2(context);
                                DownloadTask2 downloadTask = new DownloadTask2();
                                //Start downloading json data from Google Directions API
                                downloadTask.execute(url);
                            }
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
                } catch (Exception e) {
                    e.printStackTrace();
                }*//*
                    if(picUploaded) {

                       *//*
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
                        }*//*
                    }
                }

            });
            thread.start();
            pd.dismiss();
*/
        }
        else
        {
            //   Toast.makeText(context, "Please Take Picture First than Upload.", Toast.LENGTH_LONG).show();

        }
    }
    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {

        private Exception exception;

        protected Void doInBackground(String... urls) {
            String extension = filePath.substring(filePath.lastIndexOf("."));
            File file = new File(filePath);
            InputStream fis = null;
            FTPSClient client = new FTPSClient("TLS", true);
            if (picTaken) {

            try {
                picUploaded=false;
                imagepath = null;
                imageNAme=null;
                directoryPath=null;
                picreceved = true;
                try {
                    TrustManager[] trustManager = new TrustManager[]{new X509TrustManager() {
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }};

                    client.setTrustManager(trustManager[0]);
                    KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                    kmf.init(null, null);
                    KeyManager km = kmf.getKeyManagers()[0];
                    client.setKeyManager(km);
                    client.setBufferSize(1024 * 1024);
                    client.setConnectTimeout(100000);
                    client.connect(InetAddress.getByName(ftpbaseurl), 990);
                    client.setSoTimeout(100000);

                    if (client.login(ftpUser, ftpPas)) {
                        client.execPBSZ(0);
                        client.execPROT("P");
                    }
                }
                     catch (SocketException e) {
                        Log.e("APPTAG", e.getStackTrace().toString());
                    } catch (UnknownHostException e) {
                        Log.e("APPTAG", e.getStackTrace().toString());
                    } catch (IOException e) {
                        Log.e("APPTAG", e.getStackTrace().toString());
                    } catch (Exception e) {
                        Log.e("APPTAG", e.getStackTrace().toString());
                    }
                try {
                    client.setFileType(FTP.BINARY_FILE_TYPE);
                    // buffIn = new BufferedInputStream(new FileInputStream(file));

                    fis = new FileInputStream(file);

                    client.enterLocalPassiveMode();
                    client.makeDirectory("HCEImages/" + final_id); //I want to upload picture in MyPictures directory/folder. you can use your own.
                    client.makeDirectory("HCEImages/" + final_id + "/" + username);
                    client.makeDirectory("HCEImages/" + final_id + "/" + username + "/Camera");
                    directoryPath="HCEImages/" + final_id + "/" + username + "/Camera";
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
                    imageNAme=final_id + "_" + currentDateandTime+extension;
                    imagepath = final_id + "/" + username + "/Camera/" + imageNAme;

                    try
                    {
                        if (!client.storeFile("HCEImages/" + imagepath, fis))//this is actual file uploading on FtpServer in specified directory/folder
                        {
                            if (pDialog.isShowing())
                                pDialog.dismiss();
                         //   Toast.makeText(context, "Unable to write file on server", Toast.LENGTH_SHORT).show();
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
                        String url3 = getDirectionsUrl2(context);
                        DownloadTask2 downloadTask = new DownloadTask2();
                        //Start downloading json data from Google Directions API
                        downloadTask.execute(url3);
                    }
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

            } catch (Exception e) {
                e.printStackTrace();
            }

            }


            if (picAttachement) {
                try {
                   // String dstPath = context.getExternalFilesDir(null) + File.separator;
                    //File dst = new File(dstPath);

                //  File newFile=  exportFile(file, dst);
                    picUploaded = false;
                    imagepath = null;
                    imageNAme = null;
                    directoryPath = null;
                    picreceved = true;
                    try {
                        TrustManager[] trustManager = new TrustManager[]{new X509TrustManager() {
                            @Override
                            public X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }

                            @Override
                            public void checkClientTrusted(X509Certificate[] certs, String authType) {
                            }

                            @Override
                            public void checkServerTrusted(X509Certificate[] certs, String authType) {
                            }
                        }};

                        client.setTrustManager(trustManager[0]);
                        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                        kmf.init(null, null);
                        KeyManager km = kmf.getKeyManagers()[0];
                        client.setKeyManager(km);
                        client.setBufferSize(1024 * 1024);
                        client.setConnectTimeout(100000);
                        client.connect(InetAddress.getByName(ftpbaseurl), 990);
                        client.setSoTimeout(100000);

                        if (client.login(ftpUser, ftpPas)) {
                            client.execPBSZ(0);
                            client.execPROT("P");
                        }
                    } catch (SocketException e) {
                        Log.e("APPTAG", e.getStackTrace().toString());
                    } catch (UnknownHostException e) {
                        Log.e("APPTAG", e.getStackTrace().toString());
                    } catch (IOException e) {
                        Log.e("APPTAG", e.getStackTrace().toString());
                    } catch (Exception e) {
                        Log.e("APPTAG", e.getStackTrace().toString());
                    }
                    try {
                        client.setFileType(FTP.BINARY_FILE_TYPE);
                        // buffIn = new BufferedInputStream(new FileInputStream(file));
                        Uri mUri = Uri.fromFile(file);
                        fis= getContentResolver().openInputStream(mUri);
                      //  fis = new FileInputStream(newFile);
                         client.enterLocalPassiveMode();
                        client.makeDirectory("HCEImages/" + final_id); //I want to upload picture in MyPictures directory/folder. you can use your own.
                        client.makeDirectory("HCEImages/" + final_id + "/" + username);
                        client.makeDirectory("HCEImages/" + final_id + "/" + username + "/Attachments");
                        directoryPath = "HCEImages/" + final_id + "/" + username + "/Attachments";
                    } catch (Exception e) {
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        picreceved = false;
                        throw new Exception(e);
                       // Toast.makeText(context, "Picture not found! Please try again", Toast.LENGTH_SHORT).show();
                    }

                    if (picreceved) {
                        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
                        String currentDateandTime = sdf.format(new Date());
                        imageNAme = final_id + "_" + currentDateandTime + extension;
                        imagepath = final_id + "/" + username + "/Attachments/" + imageNAme;
                        try {


                            if (!client.storeFile("HCEImages/" + imagepath, fis))//this is actual file uploading on FtpServer in specified directory/folder
                            {
                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                picreceved = false;
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
                            String url2 = getDirectionsUrl2(context);
                            DownloadTask2 downloadTask = new DownloadTask2();
                            //Start downloading json data from Google Directions API
                            downloadTask.execute(url2);
                        }
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
            }



            return null;
        }

        protected void onPostExecute(String feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }
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


    /**
     * Creating file uri to store image/video
     */
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };

            cursor = context.getApplicationContext().getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
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
            jsonStr2=result;
            // Invokes the thread for parsing the JSON data
            parserTask.execute();

        }
    }
    private String getDirectionsUrl2(Context context) {

        // Building the url to the web service
        String baseurl=context.getResources().getString(R.string.baseurl);
        String token=context.getResources().getString(R.string.token);
        String url=baseurl+"UploadHCEImage?strToken="+token+"&FinalID="+final_id+"&ImagePath="+imagepath+"&emailAddress="+email+"&visitedDate=&RoleID="+RoleID;
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
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
                                );
       }
    }


}
