package com.phc.cim.TabsActivities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.cim.Activities.Common.AboutusActivity;
import com.phc.cim.Activities.Common.ChangePasswordActivity;
import com.phc.cim.Activities.Common.DesealListing;
import com.phc.cim.Activities.Common.FilterActivity;
import com.phc.cim.Activities.Common.HearingStatusActivity;
import com.phc.cim.Activities.Common.HigherReportingActivity;
import com.phc.cim.Activities.Common.IndReportingActivity;
import com.phc.cim.Activities.Common.QuackActivity;
import com.phc.cim.Activities.Common.RegistrationStatus;
import com.phc.cim.Activities.Common.ReportQuackActivity;
import com.phc.cim.Activities.Licensing.PWSFilterActivity;
import com.phc.cim.Extra.HomeFragment;
import com.phc.cim.Extra.NotificationFragment;
import com.phc.cim.Extra.PhotosFragment;
import com.phc.cim.Extra.SettingFragment;
import com.phc.cim.Extra.VideosFragment;
import com.phc.cim.Fragments.ClosedSldGraphFragment;
import com.phc.cim.Fragments.FuncSldGraphFragment;
import com.phc.cim.Fragments.NotSldGraphFragment;
import com.phc.cim.Fragments.OverAllSldRslttFragment;
import com.phc.cim.Others.Logout;
import com.phc.cim.R;

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
import java.util.HashMap;
import java.util.List;

public class DashboardTabs extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Bundle bundle;
    Context context;

    ArrayList<HashMap<String, String>> mylist;
    ArrayList<HashMap<String, String>> funclist;
    ArrayList<HashMap<String, String>> closelist;
    ArrayList<HashMap<String, String>> notsellist;
    String jsonStr = null;
    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_MOVIES = "videos";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;
    private Handler mHandler;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private ImageView imgProfile;
    private String activityTitles;
    ProgressDialog pDialog;
    Button btn_detail,btn_next,btn_back;
    String email;
    String password;
    String isEdit;
    String username;
    String Closed_Sealed,FIRs,Functional_Sealed,Sealing_Not_Required,Total,PKID;

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
        setContentView(R.layout.dashboards_tabs);
        context=this;
        Intent intent;
        intent = getIntent();
        email = (String) intent.getSerializableExtra("email");
        password = (String) intent.getSerializableExtra("password");
        username = (String) intent.getSerializableExtra("username");
        isEdit = (String) intent.getSerializableExtra("isEdit");
        btn_detail = (Button) findViewById(R.id.btn_detail);
        btn_next = (Button) findViewById(R.id.rightbutton);
        btn_back = (Button) findViewById(R.id.leftbutton);
        btn_next.setVisibility(View.GONE);
        btn_back.setVisibility(View.GONE);
        bundle = new Bundle();
        bundle.putString("email",email);
        bundle.putString("Password", password);
        bundle.putString("username", username);
        bundle.putString("isEdit", isEdit);
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading Data, Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
      //  CollapsingToolbarLayout ctl = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
      //  ctl.setTitle("Best Coupons Deals");
        //ctl.setCollapsedTitleTextAppearance(R.style.coll_toolbar_title);
        //ctl.setExpandedTitleTextAppearance(R.style.exp_toolbar_title);
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
     activityTitles = getResources().getString(R.string.nav_dashboard);

        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String isStat = prefs.getString("isStat", null);//"No name defined" is the default value.
        String roleid = prefs.getString("RoleID", null); //0 is the default value.
        if(roleid.equals("1")) {
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
        btn_detail.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
             //onRightClick(v);
              startActivity(new Intent(context, HigherReportingActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
               drawer.closeDrawers();
            }
        });
        btn_next.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                onRightClick(v);
            }
        });
        btn_back.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
               onLeftClick(v);
            }
        });

        String url = getDirectionsUrl();
        DownloadTask downloadTask = new DownloadTask();
        //Start downloading json data from Google Directions API
        downloadTask.execute(url);

    }
    public void toggleArrowVisibility(int isAtZeroIndex) {
        if(isAtZeroIndex==3)
            btn_next.setVisibility(View.INVISIBLE);
        else
        btn_next.setVisibility(View.VISIBLE);
        if(isAtZeroIndex==0)
            btn_back.setVisibility(View.INVISIBLE);
        else
            btn_back.setVisibility(View.VISIBLE);

    }
    public void onRightClick(View view) {

        viewPager.arrowScroll(ViewPager.FOCUS_RIGHT);
        //toggleArrowVisibility(viewPager.getCurrentItem());
    }

    public void onLeftClick(View view) {

        viewPager.arrowScroll(ViewPager.FOCUS_LEFT);
        //toggleArrowVisibility(viewPager.getCurrentItem());
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),bundle);
        adapter.addFragment(new OverAllSldRslttFragment(), "");
        adapter.addFragment(new FuncSldGraphFragment(), "");
        adapter.addFragment(new ClosedSldGraphFragment(), "");
        adapter.addFragment(new NotSldGraphFragment(), "");


       // adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        toggleArrowVisibility(viewPager.getCurrentItem());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                // Check if this is the page you want.
                toggleArrowVisibility(position);
            }
        });

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

        Menu menu = navigationView.getMenu();

        // Check if the username matches
        if (username.equals("Faizan Niazi") || username.equals("Anas Nadeem") || username.equals("Sami Ullah Khan")) {
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

        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String token = context.getResources().getString(R.string.token);
        String url = null;
        url = baseurl + "GetAllSealedCount?strToken=" + token;

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

                        map.put("Closed_Sealed", e.getString("Closed_Sealed"));
                        map.put("FIRs", e.getString("FIRs"));
                        map.put("Functional_Sealed", e.getString("Functional_Sealed"));
                        map.put("Sealing_Not_Required", e.getString("Sealing_Not_Required"));
                        map.put("Total", e.getString("Total"));
                        map.put("PKID", e.getString("PKID"));

                        mylist.add(map);
                    }

                    // adding contact to contact list
                    //contactList.add(contact);

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

            if (result != null && result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {

                    PKID = result.get(i).get("PKID");
                    Closed_Sealed= result.get(i).get("Closed_Sealed");
                    FIRs= result.get(i).get("FIRs");
                    Functional_Sealed= result.get(i).get("Functional_Sealed");
                    Sealing_Not_Required= result.get(i).get("Sealing_Not_Required");
                    Total= result.get(i).get("Total");
                }
                bundle.putString("PKID",PKID);
                bundle.putString("Closed_Sealed", Closed_Sealed);
                bundle.putString("FIRs", FIRs);
                bundle.putString("Functional_Sealed", Functional_Sealed);
                bundle.putString("Sealing_Not_Required", Sealing_Not_Required);
                bundle.putString("Total", Total);
                String url = getDirectionsUrl2();
                DownloadTask2 downloadTask2 = new DownloadTask2();
                //Start downloading json data from Google Directions API
                downloadTask2.execute(url);
            } else {
                if (pDialog.isShowing())
                    pDialog.dismiss();

                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();

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

            ParserTask2 parserTask2 = new ParserTask2();
            jsonStr = result;
            // Invokes the thread for parsing the JSON data
            parserTask2.execute();

        }
    }

    private String getDirectionsUrl2() {

        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String token = context.getResources().getString(R.string.token);
        String url = null;
        url = baseurl + "GetAllCategoryWiseSealedCount?strToken=" + token;

        url = url.replaceAll(" ", "%20");

        return url;
    }

    private String downloadUrl2(String strUrl) throws IOException {


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

    private class ParserTask2 extends AsyncTask<Object, Object, ArrayList<HashMap<String, String>>> {


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

                    funclist = new ArrayList<HashMap<String, String>>();
                    closelist = new ArrayList<HashMap<String, String>>();
                    notsellist = new ArrayList<HashMap<String, String>>();
                    for (int i = 0; i < json.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = json.getJSONObject(i);

                        if(e.getString("SType").equals("1")) {
                            map.put("ID", e.getString("ID"));
                            map.put("SType", e.getString("SType"));
                            map.put("Total", e.getString("Total"));
                            map.put("TypeDesc", e.getString("TypeDesc"));
                            funclist.add(map);
                        }
                        if(e.getString("SType").equals("2")) {
                            map.put("ID", e.getString("ID"));
                            map.put("SType", e.getString("SType"));
                            map.put("Total", e.getString("Total"));
                            map.put("TypeDesc", e.getString("TypeDesc"));
                            notsellist.add(map);
                        }
                        if(e.getString("SType").equals("3")) {
                            map.put("ID", e.getString("ID"));
                            map.put("SType", e.getString("SType"));
                            map.put("Total", e.getString("Total"));
                            map.put("TypeDesc", e.getString("TypeDesc"));
                            closelist.add(map);
                        }
                    }

                    // adding contact to contact list
                    //contactList.add(contact);

                } catch (final JSONException e) {

                    e.printStackTrace();


                }
            } else {
                Log.e("exception", "Couldn't get json from server.");
            }

            return funclist;
        }

        @Override
        protected void onPostExecute(final ArrayList<HashMap<String, String>> result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (result != null && result.size() > 0) {
                btn_next.setVisibility(View.VISIBLE);
                btn_back.setVisibility(View.VISIBLE);
                bundle.putSerializable("funclist",funclist);
                bundle.putSerializable("notsellist",notsellist);
                bundle.putSerializable("closelist",closelist);

                viewPager = (ViewPager) findViewById(R.id.viewpager);

                viewPager.setOffscreenPageLimit(4);
                setupViewPager(viewPager);

               // tabLayout = (TabLayout) findViewById(R.id.tabs);
                //tabLayout.setupWithViewPager(viewPager);
              //  tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#00a652"));
               // tabLayout.setSelectedTabIndicatorHeight((int) (4 * getResources().getDisplayMetrics().density));
               // tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#00a652"));
            } else {

                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();

            }
        }

    }
}
