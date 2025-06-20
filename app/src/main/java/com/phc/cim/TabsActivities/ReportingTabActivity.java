package com.phc.cim.TabsActivities;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayoutMediator;
import com.phc.cim.Activities.Common.DesealListing;
import com.phc.cim.Activities.Common.FilterActivity;
import com.phc.cim.Activities.Common.HearingStatusActivity;
import com.phc.cim.Activities.Common.IndReportingActivity;
import com.phc.cim.Activities.Common.QuackActivity;
import com.phc.cim.Activities.Common.RegistrationStatus;
import com.phc.cim.Activities.Common.ReportQuackActivity;
import com.phc.cim.Activities.Licensing.PWSFilterActivity;
import com.phc.cim.Fragments.CloseSealInspDaywiseFragment;
import com.phc.cim.Fragments.CloseSealedInspectionFragment;
import com.phc.cim.Fragments.ClosedSealedFragment;
import com.phc.cim.Fragments.FunctionalSealedFragment;
import com.phc.cim.Extra.HomeFragment;
import com.phc.cim.Fragments.L_AFragment;
import com.phc.cim.Fragments.NonRegisterHCEFragment;
import com.phc.cim.Fragments.NotSealedFragment;
import com.phc.cim.Extra.NotificationFragment;
import com.phc.cim.Extra.PhotosFragment;
import com.phc.cim.Extra.SettingFragment;
import com.phc.cim.Extra.VideosFragment;
import com.phc.cim.Activities.Common.AboutusActivity;
import com.phc.cim.Activities.Common.ChangePasswordActivity;
import com.phc.cim.Fragments.SealTemperedFragment;
import com.phc.cim.Fragments.visitOnComplaint;
import com.phc.cim.Others.Logout;
import com.phc.cim.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ReportingTabActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    Bundle bundle;
    String dataType;
    String registrationType;
    String orgType;
    String REGfilterstatus;
    String districtText;
    String TehsilText;
    String distancetext;
    String searchbytext;
    String BfromText="";
    String BtoText="";
    String email;
    String lastvisitedText;
    String RegnoText;
    String hcenameText;
    String finalidText;
    Context context;
    ArrayList<HashMap<String, String>> indtabresult;
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
    String NotSealedID,NotSealed,NotSealedcount,CloseSealedID,CloseSealed,ClosedSealedcount,PlanID,index,team,totalvisits,totalfir,startdat,enddate,FunctionalSealedID,FunctionalSealed,FunctionalSealedcount,CloseSealedInspectionID,CloseSealedInspection,CloseSealedInspectioncount,
    L_A_ID, L_A, L_Acount, visitOnComplaintID, visitOnComplaint, VisitOnComplaintcount, SealTemperedID, SealTempered, SealTemperedcount, NonRegisterHCEID, NonRegisterHCE, NonRegisterHCEcount;
    String appURI = "";
    String time1;
    String password;
    String isEdit;
    String username,TotalImages,District,PlanCode,selVistdate;
    TextView visiteddate,teamtext,totalvisittext,planidtext,selvisittext;
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
        setContentView(R.layout.reporting_tabs);
        context=this;

        visiteddate = (TextView) findViewById(R.id.visiteddate);
        teamtext= (TextView) findViewById(R.id.teamtext);
        totalvisittext  = (TextView) findViewById(R.id.totalvisittext);
        planidtext= (TextView) findViewById(R.id.planidtext);
        selvisittext= (TextView) findViewById(R.id.selvisitdate);
        Intent intent;
        intent = getIntent();
        FunctionalSealedID= (String) intent.getSerializableExtra("FunctionalSealedID");
        FunctionalSealed= (String) intent.getSerializableExtra("FunctionalSealed");
        FunctionalSealedcount= (String) intent.getSerializableExtra("FunctionalSealedcount");
        NotSealedID= (String) intent.getSerializableExtra("NotSealedID");
        NotSealed= (String) intent.getSerializableExtra("NotSealed");
        NotSealedcount= (String) intent.getSerializableExtra("NotSealedcount");
        CloseSealedID= (String) intent.getSerializableExtra("CloseSealedID");
        CloseSealed= (String) intent.getSerializableExtra("CloseSealed");
        ClosedSealedcount= (String) intent.getSerializableExtra("ClosedSealedcount");
        CloseSealedInspectionID= (String) intent.getSerializableExtra("CloseSealedInspectionID");
        CloseSealedInspection= (String) intent.getSerializableExtra("CloseSealedInspection");
        CloseSealedInspectioncount= (String) intent.getSerializableExtra("CloseSealedInspectioncount");
        L_A_ID= (String) intent.getSerializableExtra("L_A_ID");
        L_A= (String) intent.getSerializableExtra("L_A");
        L_Acount= (String) intent.getSerializableExtra("L_Acount");
        visitOnComplaintID= (String) intent.getSerializableExtra("visitOnComplaintID");
        visitOnComplaint= (String) intent.getSerializableExtra("visitOnComplaint");
        VisitOnComplaintcount= (String) intent.getSerializableExtra("VisitOnComplaintcount");
        SealTemperedID= (String) intent.getSerializableExtra("SealTemperedID");
        SealTempered= (String) intent.getSerializableExtra("SealTempered");
        SealTemperedcount= (String) intent.getSerializableExtra("SealTemperedcount");
        NonRegisterHCEID= (String) intent.getSerializableExtra("NonRegisterHCEID");
        NonRegisterHCE= (String) intent.getSerializableExtra("NonRegisterHCE");
        NonRegisterHCEcount= (String) intent.getSerializableExtra("NonRegisterHCEcount");
        PlanID= (String) intent.getSerializableExtra("PlanID");
        index= (String) intent.getSerializableExtra("index");
        team= (String) intent.getSerializableExtra("team");
        email= (String) intent.getSerializableExtra("email");
        password = (String) intent.getSerializableExtra("password");
        username = (String) intent.getSerializableExtra("username");
        isEdit = (String) intent.getSerializableExtra("isEdit");
        totalvisits = (String) intent.getSerializableExtra("totalvisits");
        totalfir = (String) intent.getSerializableExtra("totalfir");
        startdat = (String) intent.getSerializableExtra("startdat");
        enddate = (String) intent.getSerializableExtra("enddate");
        District = (String) intent.getSerializableExtra("District");
        PlanCode = (String) intent.getSerializableExtra("PlanCode");
        TotalImages=(String) intent.getSerializableExtra("TotalImages");
        selVistdate = (String) intent.getSerializableExtra("selVistdate");
        indtabresult = (ArrayList<HashMap<String, String>>)intent.getSerializableExtra("result");

        planidtext.setText(District+" - "+PlanCode);
        visiteddate.setText(startdat+" - "+enddate);
        teamtext.setText("Team: "+team);
        totalvisittext.setText("Visits: "+totalvisits+" - FIR: "+totalfir+" - Images: "+TotalImages);
        if(selVistdate.equals("All")){
            selvisittext.setText("Visit Date: " + startdat+" - "+enddate);
        }
        else {
            if (selVistdate != null && selVistdate!="All") {
                DateFormat formatter;
                formatter = new SimpleDateFormat("M/d/yyyy");
                Date date2 = null;
                try {
                    date2 = (Date) formatter.parse(selVistdate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat newFormat = new SimpleDateFormat("dd/MMM/yyyy");
                if (date2 != null)
                    selVistdate = newFormat.format(date2);
                selvisittext.setText("Visit Date: " + selVistdate);

            }

        }
        bundle = new Bundle();
        bundle.putSerializable("indtabresult",indtabresult);
        bundle.putString("FunctionalSealedID",FunctionalSealedID);
        bundle.putString("NotSealedID",NotSealedID);
        bundle.putString("CloseSealedID",CloseSealedID);
        bundle.putString("CloseSealedInspectionID",CloseSealedInspectionID);
        bundle.putString("L_A_ID",L_A_ID);
        bundle.putString("visitOnComplaintID",visitOnComplaintID);
        bundle.putString("SealTemperedID",SealTemperedID);
        bundle.putString("NonRegisterHCEID",NonRegisterHCEID);

        bundle.putString("email",email);
        bundle.putString("password",password);
        bundle.putString("username",username);
        bundle.putString("isEdit",isEdit);

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
       // imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getString(R.string.nav_item_Visit_Summary);

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
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // TabLayout and ViewPager2 initialization
        tabLayout = findViewById(R.id.tabs);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);  // Limit to keep previous and next pages in memory
        setupViewPager(viewPager);  // Initialize the adapter here

        // TabLayout customization
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#00a652"));
        tabLayout.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#00a652"));

    }

    private void setupViewPager(ViewPager2 viewPager) {
        // Adapter ko create karna aur fragments ko add karna
        ReportingViewPagerAdapter adapter = new ReportingViewPagerAdapter(this, bundle);
        adapter.addFragment(new FunctionalSealedFragment(), FunctionalSealed + " (" + FunctionalSealedcount + ")");
        adapter.addFragment(new ClosedSealedFragment(), CloseSealed + " (" + ClosedSealedcount + ")");
        adapter.addFragment(new NotSealedFragment(), NotSealed + " (" + NotSealedcount + ")");
        adapter.addFragment(new CloseSealedInspectionFragment(), CloseSealedInspection + " (" + CloseSealedInspectioncount + ")");
        adapter.addFragment(new L_AFragment(), L_A + " (" + L_Acount + ")");
        adapter.addFragment(new visitOnComplaint(), visitOnComplaint + " (" + VisitOnComplaintcount + ")");
        adapter.addFragment(new SealTemperedFragment(), SealTempered + " (" + SealTemperedcount + ")");
        adapter.addFragment(new NonRegisterHCEFragment(), NonRegisterHCE + " (" + NonRegisterHCEcount + ")");

        // ViewPager2 ko adapter assign karna
        viewPager.setAdapter(adapter);

        // Attach TabLayout with ViewPager2 and set tab titles
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(adapter.getPageTitle(position));
        });

        // Detach previous mediator if exists
        if (tabLayoutMediator != null) {
            tabLayoutMediator.detach();
        }

        // Attach the new mediator
        tabLayoutMediator.attach();

        viewPager.setCurrentItem(0); // Default page
    }

    class ReportingViewPagerAdapter extends FragmentStateAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final Bundle fragmentBundle;

        public ReportingViewPagerAdapter(FragmentActivity activity, Bundle bundle) {
            super(activity);  // Passing the activity to the FragmentStateAdapter
            this.fragmentBundle = bundle;
        }

        @Override
        public Fragment createFragment(int position) {
            Fragment fragment = mFragmentList.get(position);
            fragment.setArguments(fragmentBundle); // Set the arguments (bundle) to the fragment
            return fragment;
        }

        @Override
        public int getItemCount() {
            return mFragmentList.size(); // Number of fragments in the list
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title); // Add fragment and its title
        }

        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position); // Return the title for each fragment
        }
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

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }
}
