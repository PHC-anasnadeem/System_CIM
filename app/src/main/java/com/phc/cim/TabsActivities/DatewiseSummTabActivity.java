package com.phc.cim.TabsActivities;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.phc.cim.DownloadClases.DownloadVisitsActivity;
import com.phc.cim.Fragments.CloSealDaywiseFragment;
import com.phc.cim.Fragments.CloseSealInspDaywiseFragment;
import com.phc.cim.Fragments.FunSealDaywiseFragment;
import com.phc.cim.Extra.HomeFragment;
import com.phc.cim.Fragments.L_ANoticeDaywiseFragment;
import com.phc.cim.Fragments.NonRegisterHCEDaywiseFragment;
import com.phc.cim.Fragments.NotSealDaywiseFragment;
import com.phc.cim.Extra.NotificationFragment;
import com.phc.cim.Extra.PhotosFragment;
import com.phc.cim.Extra.SettingFragment;
import com.phc.cim.Extra.VideosFragment;
import com.phc.cim.Activities.Common.AboutusActivity;
import com.phc.cim.Activities.Common.ChangePasswordActivity;
import com.phc.cim.Fragments.SealTemperedDaywiseFragment;
import com.phc.cim.Fragments.VisitOnComplaintDaywiseFragment;
import com.phc.cim.Others.Logout;
import com.phc.cim.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DatewiseSummTabActivity extends AppCompatActivity {

    private TabLayout tabLayout;
//    private ViewPager viewPager;
    private ViewPager2 viewPager;
    Bundle bundle;
    String dataType;
    String registrationType;
    String orgType;
    String REGfilterstatus;
    String TehsilText;
    String distancetext;
    String searchbytext;
    String BfromText = "";
    String BtoText = "";
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
    String NotSealedID, NotSealed, NotSealedcount, CloseSealedID, CloseSealed, ClosedSealedcount, PlanID, index, team, totalvisits, totalfir, startdat, enddat, FunctionalSealedID, FunctionalSealed, FunctionalSealedcount;
    String appURI = "";
    String time1;
    String password;
    String isEdit;
    String Vistdate,TotalImages;
    String username, District, PlanCode,reportlevel;
    Button btn_detail;
    Spinner date_spinner;
    TextView visiteddate, teamtext, totalvisittext, planidtext;
    Long startdatmiles,enddatemiles;

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
        setContentView(R.layout.daywise_tabs);
        context = this;

        visiteddate = (TextView) findViewById(R.id.visiteddate);
        teamtext = (TextView) findViewById(R.id.teamtext);
        totalvisittext = (TextView) findViewById(R.id.totalvisittext);
        planidtext = (TextView) findViewById(R.id.planidtext);
        btn_detail = (Button) findViewById(R.id.btn_detail);
        date_spinner = (Spinner) findViewById(R.id.date_spinner);
        bundle = new Bundle();
        Intent intent;
        intent = getIntent();
        PlanID = (String) intent.getSerializableExtra("PlanID");
        index = (String) intent.getSerializableExtra("index");
        team = (String) intent.getSerializableExtra("team");
        email = (String) intent.getSerializableExtra("email");
        password = (String) intent.getSerializableExtra("password");
        username = (String) intent.getSerializableExtra("username");
        isEdit = (String) intent.getSerializableExtra("isEdit");
        totalvisits = (String) intent.getSerializableExtra("totalvisits");
        totalfir = (String) intent.getSerializableExtra("totalfir");
        startdat = (String) intent.getSerializableExtra("startdat");
        enddat = (String) intent.getSerializableExtra("enddate");
        District = (String) intent.getSerializableExtra("District");
        PlanCode = (String) intent.getSerializableExtra("PlanCode");
        startdatmiles = (Long) intent.getSerializableExtra("timeInMillis");
        enddatemiles = (Long) intent.getSerializableExtra("timeInMillis2");
        TotalImages =(String) intent.getSerializableExtra("TotalImages");
        reportlevel=(String) intent.getSerializableExtra("reportlevel");
        indtabresult = (ArrayList<HashMap<String, String>>) intent.getSerializableExtra("result");


        visiteddate.setText(startdat + " - " + enddat);
        teamtext.setText("Team: " + team);
        totalvisittext.setText("Visits: " + totalvisits + " - FIR: " + totalfir + " - Images: " + TotalImages);

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
       if(reportlevel.equals("Managment")){
           activityTitles =PlanCode;
           planidtext.setText("District: "+District);
       }
       else {
           activityTitles = getResources().getString(R.string.nav_visit_title);
           planidtext.setText(District+" - "+PlanCode);
       }


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
        //---------------------------------Date Spinner----------------------------------
        Date stdate = new Date(startdatmiles);
        Date endate = new Date(enddatemiles);
        ArrayAdapter<String> dateadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getDatesBetween(stdate,endate)) {
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the spinner collapsed item (non-popup item) as a text view
                TextView tv = (TextView) super.getView(position, convertView, parent);

                // Set the text color of spinner item
                tv.setTextColor(Color.WHITE);

                // Return the view
                return tv;
            }


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
                TextView tv = (TextView) super.getDropDownView(position,convertView,parent);

                // Set the text color of drop down items
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tv.setTextColor(Color.BLACK);
                }else {
                    tv.setTextColor(Color.WHITE);
                }

                if (position == 0) {
                    // Set the hint text color gray
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        tv.setTextColor(Color.BLACK);
                    }else {
                        tv.setTextColor(Color.WHITE);
                    }
                } else {
                    //convertView.setBackgroundColor(Color.WHITE);
                    //((TextView) parent.getChildAt(position)).setTextColor(Color.WHITE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        tv.setTextColor(Color.BLACK);
                    }else {
                        tv.setTextColor(Color.WHITE);
                    }
                }
                return tv;
            }
        };
        // Set the dropdown view resource for the date adapter
        dateadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the popup background resource for the spinner if the SDK version is Jelly Bean or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            date_spinner.setPopupBackgroundResource(R.drawable.spinner);
        }

        // Set the adapter for the date spinner
        date_spinner.setAdapter(dateadapter);

        // Set an item selected listener for the date spinner
        date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Check if the view is not null and is an instance of TextView before setting the text color
                if (view != null && view instanceof TextView) {
                    ((TextView) view).setTextColor(Color.WHITE);
                }

                // Get the selected visit date
                Vistdate = parent.getItemAtPosition(position).toString();

                // Format the visit date if it is not null and not equal to "All"
                if (Vistdate != null && !Vistdate.equals("All")) {
                    DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                    Date date2 = null;
                    try {
                        date2 = formatter.parse(Vistdate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // Reformat the date to the desired format
                    SimpleDateFormat newFormat = new SimpleDateFormat("M/d/yyyy", Locale.getDefault());
                    if (date2 != null) {
                        Vistdate = newFormat.format(date2);
                    }
                }

                // Put relevant data into the bundle
                bundle.putSerializable("indtabresult", indtabresult);
                bundle.putString("email", email);
                bundle.putString("password", password);
                bundle.putString("username", username);
                bundle.putString("isEdit", isEdit);
                bundle.putString("Vistdate", Vistdate);

                // Initialize TabLayout
                tabLayout = findViewById(R.id.tabs);

                // Set up the ViewPager2
                viewPager = findViewById(R.id.viewpager);
                viewPager.setOffscreenPageLimit(4);
                setupViewPager(viewPager);


                // Style TabLayout
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#00a652"));
                tabLayout.setSelectedTabIndicatorHeight((int) (3 * getResources().getDisplayMetrics().density));
                tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#00a652"));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case where nothing is selected if needed
            }
        });


        btn_detail.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {

                DownloadVisitsActivity downloadVisitsActivity = new DownloadVisitsActivity(context, PlanID, email, password, username, isEdit, index, team, totalvisits, totalfir, startdat, enddat, District, PlanCode, Vistdate,indtabresult,TotalImages);

            }
        });


    }

    private void setupViewPager(ViewPager2 viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, bundle);
        adapter.addFragment(new FunSealDaywiseFragment(), "Functional Sealed");
        adapter.addFragment(new CloSealDaywiseFragment(), "Close Sealed");
        adapter.addFragment(new NotSealDaywiseFragment(), "Sealing Not Required");
        adapter.addFragment(new CloseSealInspDaywiseFragment(), "Close Sealed Inspection");
        adapter.addFragment(new L_ANoticeDaywiseFragment(), "L&A Notice");
        adapter.addFragment(new VisitOnComplaintDaywiseFragment(), "Visit On Complaint");
        adapter.addFragment(new SealTemperedDaywiseFragment(), "Seal -Tempered and Re-sealed");
        adapter.addFragment(new NonRegisterHCEDaywiseFragment(), "Non-Register HCE Notice Issued");

        viewPager.setAdapter(adapter);

        // Attach TabLayout with ViewPager2 and set tab titles
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(adapter.getFragmentTitle(position));
        });

        // Detach previous mediator if exists
        if (tabLayoutMediator != null) {
            tabLayoutMediator.detach();
        }

        // Attach the new mediator
        tabLayoutMediator.attach();

        viewPager.setCurrentItem(0);
    }


    class ViewPagerAdapter extends FragmentStateAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final Bundle fragmentBundle;

        public ViewPagerAdapter(@NonNull FragmentActivity activity, Bundle bundle) {
            super(activity);
            this.fragmentBundle = bundle;
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment = mFragmentList.get(position);
            fragment.setArguments(fragmentBundle);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return mFragmentList.size();
        }

        public String getFragmentTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public ArrayList<String> getDatesBetween(
            Date startDate, Date endDate) {

        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);
        ArrayList<String> counciltypelist = new ArrayList<String>();
        counciltypelist.add("All");
        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String finalString = "";
            if (result != null)
                finalString = newFormat.format(result);
            counciltypelist.add(finalString);
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        counciltypelist.add(enddat);
        return counciltypelist;
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
}
