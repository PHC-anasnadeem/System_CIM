package com.phc.cim.Activities.Licensing;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
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
import com.phc.cim.Activities.Common.HearingStatusActivity;
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
import java.util.Calendar;
import java.util.HashMap;

import butterknife.ButterKnife;


public class PWSDetailListActivity extends AppCompatActivity {

    CurrentLocation gps;
    Context context;
    ListView listView;
    ProgressDialog pDialog;
    double cur_latitude;
    double cur_longitude;
    ArrayList<HashMap<String, String>> indtabresult;
    ArrayList<HashMap<String, String>> mylist;
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
    double des_lat;
    double des_lng;
    int currentPage = 1;
    DataManager dataManager;
    String MID;
    String MText;
    String jsonStr;
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
    String password = "";
    String isEdit = "";
    String username = "";
    String isStat;
    String UserID;
    String RoleID;
    String recID;
    String infoVisitDatetext = "";
    String sectortypetext = "";
    String hceTypetext = "";
    String infoVisitBytext = "";
    String BasicInfoVisitedTime = "";
    String LoctionVisitedBy = "";
    String LoctionVisitedDate = "";
    String LoctionVisitedTime = "";
    String UserName = "";
    String BasicInfoVisitedUserName = "";
    String LocationVisitedUserName = "";
    String currlocText = "";
    String currlocID = "-1";
    String total_beds = "";
    double latitude;
    double longitude;
    String email = "";
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
    private View navFooter;
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
    String districtText, BfromText, BtoText, RegnoText, hcenameText, CNICtext;
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
        setContentView(R.layout.activity_pws_list);
        context = this;
        pDialog = new ProgressDialog(context);

        gps = new CurrentLocation(context);
        Intent intent;
        intent = getIntent();

        hceTypetext = (String) intent.getSerializableExtra("hceTypetext");
        districtText = (String) intent.getSerializableExtra("districtText");
        BfromText = (String) intent.getSerializableExtra("BfromText");
        BtoText = (String) intent.getSerializableExtra("BtoText");
        RegnoText = (String) intent.getSerializableExtra("RegnoText");
        hcenameText = (String) intent.getSerializableExtra("hcenameText");
        CNICtext = (String) intent.getSerializableExtra("cnicText");
        indtabresult = (ArrayList<HashMap<String, String>>) intent.getSerializableExtra("result");
        TotalRecord = Integer.parseInt(indtabresult.get(0).get("TotalRec"));
        PageSize = Integer.parseInt(indtabresult.get(0).get("PageSize"));
        int total = (int) Math.ceil((double) TotalRecord / PageSize);
        listView = (ListView) findViewById(R.id.list);
        footerView = ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loading_view, null, false);
        if (currentPage < total) {
            this.listView.addFooterView(footerView);
        }
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

        activityTitles = getResources().getString(R.string.nav_item_HCElist_titles);
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

          /*  pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading Data, Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
            String url = getDirectionsUrl();
            DownloadTask downloadTask = new DownloadTask();
            //Start downloading json data from Google Directions API
            downloadTask.execute(url);*/

        index = PageSize;
        pwsListAdapter = new PWSListAdapter(context, indtabresult);
        listView.setAdapter(pwsListAdapter);
        pwsListAdapter.notifyDataSetChanged();


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                //what is the bottom item that is visible
                int lastInScreen = firstVisibleItem + visibleItemCount;
                //is the bottom item visible & not loading more already? Load more!
                if ((lastInScreen == totalItemCount) && !(loadingMore)) {

                    //start a new thread for loading the items in the list

                    int total = (int) Math.ceil((double) TotalRecord / PageSize);
                    if (currentPage < total) {

                        String url = getDirectionsUrl();
                        DownloadTask downloadTask = new DownloadTask();
                        //Start downloading json data from Google Directions API
                        downloadTask.execute(url);
                    }
                }

            }

        });
        if (gps.canGetLocation()) {

            cur_latitude = gps.getLatitude();
            cur_longitude = gps.getLongitude();
            // latlangListener.onlatlang(cur_latitude, cur_longitude);
            if (cur_latitude != 0.0 && cur_longitude != 0.0) {
            } else {

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
        getSupportActionBar().setTitle(activityTitles + " (" + TotalRecord + ")");
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
        loadingMore = true;
        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String token = context.getResources().getString(R.string.token);
        String url = null;
        url = baseurl + "Get_HCEDetailForMobile?strToken=" + token + "&HCEName=" + hcenameText + "&District=" + districtText + "&BedsFrom=" + BfromText + "&BedsTo=" + BtoText + "&orgType=" + hceTypetext + "&RegNum=" + RegnoText + "&CNIC=" + CNICtext + "&CurrentPage=" + currentPage;


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
                        index = index + 1;
                        map.put("index", String.valueOf(index));
                        map.put("Address", e.getString("Address"));
                        map.put("CNIC", e.getString("CNIC"));
                        map.put("CouncilName", e.getString("CouncilName"));
                        map.put("CouncilNo", e.getString("CouncilNo"));
                        map.put("District", e.getString("District"));
                        map.put("FeeReceived", e.getString("FeeReceived"));
                        map.put("HCEName", e.getString("HCEName"));
                        map.put("HCEType", e.getString("HCEType"));
                        map.put("Hcp_recID", e.getString("Hcp_recID"));
                        map.put("Mobile", e.getString("Mobile"));
                        map.put("PHCDeRegDate", e.getString("PHCDeRegDate"));
                        map.put("PHCDeRegNo", e.getString("PHCDeRegNo"));
                        map.put("PHCPLDate", e.getString("PHCPLDate"));
                        map.put("PHCPLNo", e.getString("PHCPLNo"));
                        map.put("PHCRLDate", e.getString("PHCRLDate"));
                        map.put("PHCRLNo", e.getString("PHCRLNo"));
                        map.put("PHCRegDate", e.getString("PHCRegDate"));
                        map.put("PHCRegNo", e.getString("PHCRegNo"));
                        map.put("Reason", e.getString("Reason"));
                        map.put("RegistrationCertificateIssued", e.getString("RegistrationCertificateIssued"));
                        map.put("SPName", e.getString("SPName"));
                        map.put("SPType", e.getString("SPType"));
                        map.put("SectorType", e.getString("SectorType"));
                        map.put("bedStrength", e.getString("bedStrength"));
                        map.put("TotalRec", e.getString("TotalRec"));
                        map.put("PageSize", e.getString("PageSize"));
                        indtabresult.add(map);
                    }

                    // adding contact to contact list
                    //contactList.add(contact);

                } catch (final JSONException e) {

                    e.printStackTrace();


                }
            } else {
                Log.e("exception", "Couldn't get json from server.");
            }

            return indtabresult;
        }

        @Override
        protected void onPostExecute(final ArrayList<HashMap<String, String>> result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (result != null && result.size() > 0) {
                TotalRecord = Integer.parseInt(result.get(0).get("TotalRec"));
                PageSize = Integer.parseInt(result.get(0).get("PageSize"));
                currentPage = currentPage + 1;
                pwsListAdapter.notifyDataSetChanged();
                loadingMore = false;
                //  indtabresult.;


                /*Intent firstpage = new Intent(context, PWSDetailListActivity.class);
                firstpage.putExtra("result",result);
                context.startActivity(firstpage);*/
            } else {
                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();

            }
        }

    }
}
