package com.phc.cim.Activities.Common;


import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.RequiresApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.phc.cim.Activities.Licensing.PWSFilterActivity;
import com.phc.cim.BroadcastService.BroadCastService;
import com.phc.cim.DownloadClases.DownloadHCEDetail;
import com.phc.cim.Extra.HomeFragment;
import com.phc.cim.Extra.NotificationFragment;
import com.phc.cim.Extra.PhotosFragment;
import com.phc.cim.Extra.SettingFragment;
import com.phc.cim.Extra.VideosFragment;
import com.phc.cim.Others.CurrentLocation;
import com.phc.cim.Others.DirectionDataParser;
import com.phc.cim.Others.Logout;
import com.phc.cim.ParcelableModel.CarParcelable;
import com.phc.cim.R;
import com.phc.cim.TabsActivities.DashboardTabs;

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
import java.util.Locale;

public class RouteMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    Context context;
    GoogleMap mMap;
    CurrentLocation gps;
    TextView mTextMessage;
    TextView mname;
    TextView maddress;
    PolylineOptions lineOptions = null;
    TextView hcetypetext;
    TextView hceNo;
    TextView mobile;
    double cur_latitude;
    double cur_longitude;
    double des_lat;
    double des_lng;
    LatLng current;
    BitmapDescriptor Reg_icon;
    BitmapDescriptor PL_icon;
    BitmapDescriptor unReg_icon;
    Marker current_icon;
    ArrayList<HashMap<String, String>> stop;
    ProgressDialog pDialog;
    String cityName = null;
    String distance;
    String time;
    TextView mdistance,hcspname;
    TextView mtime;
    Button call_button;
    String markerstatus;
    private Bundle mBundle;
    private String carName;
    private double mLat, mLon;
    private ArrayList<CarParcelable> mCarParcelableList;
    private ArrayList<CarParcelable> mCarParcelableListCurrentLation;
    private ArrayList<CarParcelable> mCarParcelableListLastLocation;
    private int mSelectedCar;
    String RegType;
    String name;
    String address;
    String mobile_number;
    String final_id;
    LatLng latLng;
    Button action_button;
    Button comments;
    private static final String TAG = "BroadcastTest";
    private Intent mServiceIntent;
    String email=null;
    String password;
    String isEdit;
    String username;
    String index;
    String sectortype;
    String council;
    String HCEType;
    String Beds,HCSPSO,HCSPname;
    String VisitStatus;
    float hue_green=149;
    float hue_yellow=57;
    float hue_blue=206;
    float hue_red=358;
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
    public static int navItemIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mTextMessage = (TextView) findViewById(R.id.Reg_text);
        mname = (TextView) findViewById(R.id.Name);
        maddress = (TextView) findViewById(R.id.addrs);
        mdistance = (TextView) findViewById(R.id.distance);
        action_button = (Button) findViewById(R.id.action);
        hcspname= (TextView) findViewById(R.id.hcspname);
     //   mtime = (TextView) findViewById(R.id.time);
       // filterButton = (Button) findViewById(R.id.filter);
       // filterButton.setVisibility(View.GONE);
        context = this;
        Intent intent = getIntent();
        des_lat= (double) intent.getSerializableExtra("des_latitude");
        des_lng= (double) intent.getSerializableExtra("des_longitude");
        markerstatus= (String) intent.getSerializableExtra("markerstatus");
        RegType= (String) intent.getSerializableExtra("RegType");
        name= (String) intent.getSerializableExtra("name");
        address= (String) intent.getSerializableExtra("address");
        final_id= (String) intent.getSerializableExtra("final_id");
        mobile_number=(String) intent.getSerializableExtra("mobile_number");
        email = (String) intent.getSerializableExtra("email");
        index = (String) intent.getSerializableExtra("index");
        password = (String) intent.getSerializableExtra("password");
        username = (String) intent.getSerializableExtra("username");
        isEdit = (String) intent.getSerializableExtra("isEdit");
        sectortype  = (String) intent.getSerializableExtra("sectortype");
        council = (String) intent.getSerializableExtra("council");
        HCEType = (String) intent.getSerializableExtra("HCEType");
        Beds = (String) intent.getSerializableExtra("Beds");
        VisitStatus = (String) intent.getSerializableExtra("VisitStatus");
        HCSPname = (String) intent.getSerializableExtra("HCSPname");
        HCSPSO = (String) intent.getSerializableExtra("HCSPSO");


        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        activityTitles = getResources().getString(R.string.nav_item_drawroute_titles);

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
        hceNo = (TextView) findViewById(R.id.hce_no);
        mobile = (TextView) findViewById(R.id.calltext);
        hcetypetext = (TextView) findViewById(R.id.hcetypetext);
        call_button = (Button) findViewById(R.id.call);
        comments = (Button) findViewById(R.id.Comments);
        Reg_icon = BitmapDescriptorFactory.fromResource(R.drawable.map_yellow_icon);
        PL_icon = BitmapDescriptorFactory.fromResource(R.drawable.map_green_icon);
        unReg_icon = BitmapDescriptorFactory.fromResource(R.drawable.blue_icon);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mCarParcelableList = getIntent().getParcelableArrayListExtra("carList");
        mMap = googleMap;
        gps = new CurrentLocation(this);

        // current_icon = mMap.addMarker(new MarkerOptions().icon());

        // check if GPS enabled
        if (gps.canGetLocation()) {

            cur_latitude = gps.getLatitude();
            cur_longitude = gps.getLongitude();
            if (cur_latitude != 0.0 && cur_longitude != 0.0) {

                Geocoder gcd = new Geocoder(getBaseContext(),
                        Locale.getDefault());
            /*    List<Address> addresses;
                try {
                    addresses = gcd.getFromLocation(cur_latitude, cur_longitude, 1);
                   *//* if (addresses.size() > 0)
                        System.out.println(addresses.get(0).getLocality());
                    ///cityName = addresses.get(0).getLocality();*//*
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
            // Add a marker in Sydney and move the camera
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            current = new LatLng(cur_latitude, cur_longitude);
           // current_icon=mMap.addMarker(new MarkerOptions().position(current).title("Current Location").icon( BitmapDescriptorFactory.fromResource(R.drawable.car)).snippet("current"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 14));

             latLng = new LatLng(des_lat, des_lng);

            if (RegType.equals("Registered")) {

                mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.defaultMarker(hue_yellow)));

            } else if(RegType.equals("Not Registered with PHC")) {
                mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.defaultMarker(hue_blue)));

            }
            else if(RegType.equals("Provisional License") || RegType.equals("Regular License")){
                mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.defaultMarker(hue_green)));
            }
            else if(RegType.equals("Quack")){
                mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.defaultMarker(hue_red)));

            }
            mname.setText(name+ "( "+sectortype+" )");
            hcetypetext.setText(HCEType + " (" + Beds + " beds)" + " - " + council);
            mTextMessage.setText(RegType);
            hceNo.setText(index);
            mobile.setText(mobile_number);
            hcspname.setText(HCSPname+" - "+HCSPSO);
            maddress.setText(address);
                    String url = getDirectionsUrl(des_lat,des_lng);
                    DownloadTask downloadTask = new DownloadTask();
                   downloadTask.execute(url);

            comments.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {


                            int count=2;
                            DownloadHCEDetail downloadHceDetail = new DownloadHCEDetail(context,name, final_id, email,password,username,isEdit,index,count,RegType);



                }

            });
            action_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (VisitStatus.equals("1")) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Action for this record is already recorded:")
                                .setCancelable(true)
                                .setPositiveButton("Update Action", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // launch new intent instead of loading fragment

                                        int count = 1;
                                        DownloadHCEDetail downloadHceDetail = new DownloadHCEDetail(context, name, final_id, email, password, username, isEdit, index, count,RegType);

                                    }
                                })
                                .setNegativeButton("Upload Image", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        int count = 2;

                                        DownloadHCEDetail downloadHceDetail = new DownloadHCEDetail(context, name, final_id, email, password, username, isEdit, index, count,RegType);

                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        int count = 3;
                        DownloadHCEDetail downloadHceDetail = new DownloadHCEDetail(context, name, final_id, email, password, username, isEdit, index, count,RegType);
                    }
                }
            });
            call_button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mobile_number != null) {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile_number));
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                         /*   //This code will make a call without user interaction and will also pick up the default phone dialler
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile_number));

                            // IF you wants the user to choose his dialler remove
                            intent.setClassName("com.android.phone", "com.android.phone.OutgoingCallBroadcaster");
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            context.startActivity(intent);*/
                            //If you want just to throw the number to the dialler and the user press the call button use this code

                        }

                    }

            });


        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    private void addMarker(){

       // mMap.clear();
        if(current_icon!=null){
        current_icon.remove();}
        String lat = Double.toString(cur_latitude);
        Log.d(TAG, lat);

        LatLng carLoc = null, newCarLoc = null;
        mCarParcelableListLastLocation = mCarParcelableListCurrentLation;

        //Initially add markers for all cars to the map
        if(mCarParcelableListCurrentLation==null){
            mCarParcelableListLastLocation = mCarParcelableList;
           // for(CarParcelable car : mCarParcelableList) {
                carLoc = new LatLng(cur_latitude, cur_longitude);

            current_icon = mMap.addMarker(new MarkerOptions()
                        .position(carLoc)
                        //.title(car.mCarName)
                        //.snippet(car.mAddress)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));


            //}
        }else{ // then update each car's position by moving the marker smoothly from previous
            // location to the current location
            for(int i=0; i<mCarParcelableListCurrentLation.size();i++) {
                carLoc = new LatLng(mCarParcelableListLastLocation.get(i).mLat, mCarParcelableListLastLocation.get(i).mLon);
                newCarLoc = new LatLng(mCarParcelableListCurrentLation.get(i).mLat, mCarParcelableListCurrentLation.get(i).mLon);
                cur_latitude= mCarParcelableListCurrentLation.get(i).mLat;
                cur_longitude=mCarParcelableListCurrentLation.get(i).mLon;
                animateMarker(i, carLoc, newCarLoc, false);
            }
            //set the the last known location of each car to the current location of each car
            //so we will get the updates of car's location then we will move the marker from
            //last known location to the current location again.



        }

    }


    //creating a broadcast receiver object
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };


    @Override
    public void onResume() {
        super.onResume();
        mServiceIntent = new Intent(this, BroadCastService.class);
        startService(mServiceIntent);//starting the service
        //IntentFilter filter = new IntentFilter();
        //filter.addAction(BroadCastService.BROADCAST_ACTION);
       // registerReceiver(broadcastReceiver, filter);
        registerReceiver(broadcastReceiver, new IntentFilter(BroadCastService.BROADCAST_ACTION));//register the broadcast receiver
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        stopService(mServiceIntent);
    }

    private void updateUI(Intent intent) {

        //"carList" parcelable object is Broadcast from BroadCastService class
        mCarParcelableListCurrentLation = intent.getParcelableArrayListExtra("carList");

        addMarker();

    }

    //This methos is used to move the marker of each car smoothly when there are any updates of their position
    public void animateMarker(final int position, final LatLng startPosition, final LatLng toPosition,
                              final boolean hideMarker) {


        /*  current_icon = mMap.addMarker(new MarkerOptions()
                .position(startPosition)
                .title(mCarParcelableListCurrentLation.get(position).mCarName)
                .snippet(mCarParcelableListCurrentLation.get(position).mAddress)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));*/
    /*    if (markerstatus.equals("Reg")) {

            mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(Reg_icon));
            mTextMessage.setText(status);
            mTextMessage.setTextColor(YELLOW);
        } else if(markerstatus.equals("UnRegistered")) {
            mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(unReg_icon));
            mTextMessage.setText(status);
            mTextMessage.setTextColor(BLUE);

        }
        else{

            mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(PL_icon));
            mTextMessage.setText(status);
            mTextMessage.setTextColor(GREEN);
        }*/

/*
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();

        final long duration = 1000;
        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startPosition.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startPosition.latitude;

                current_icon.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        current_icon.setVisible(false);
                    } else {
                        current_icon.setVisible(true);
                    }
                }
            }
        });*/
        String url = getDirectionsUrl(des_lat,des_lng);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
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
                    case R.id.nav_resetPassword:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(context, ChangePasswordActivity.class).putExtra("email",email).putExtra("password",password));
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
   /*     if (shouldLoadHomeFragOnBackPress) {
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

   /*     // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
            getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        }*/
        return true;
    }



















    //------------------------------------------DownloadInspDetail Task-------------------------------------------------------

    // Fetches data from url passed
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

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    private class ParserTask extends AsyncTask<Object, Object, List<List<HashMap<String, String>>>>{



        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(Object... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject((String) jsonData[0]);
                DirectionDataParser parser = new DirectionDataParser();

                routes = parser.parse(jObject);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return routes;

        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;

            if(result!=null) {
                mMap.clear();
                if (RegType.equals("Registered")) {

                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

                } else if(RegType.equals("Not Registered with PHC")) {
                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));


                }
                else if(RegType.equals("Provisional License") || RegType.equals("Regular License")){
                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                }
                else if(RegType.equals("Quack")){
                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }


                MarkerOptions markerOptions = new MarkerOptions();

                for (int i = 0; i < result.size(); i++) {

                    points = new ArrayList();
                    lineOptions = new PolylineOptions();
                    // distance=Double.parseDouble((String) points.get());;
                    // time;
                    List<HashMap<String, String>> path = result.get(i);
                    // HashMap dittime = path.get(0);

                    for (int j = 0; j < path.size(); j++) {
                        HashMap point = path.get(j);
                        if (j < 1) {
                            distance = (String) point.get("distance");
                            time = (String) point.get("time");
                            mdistance.setText(  distance + " km");
                          //  mtime.setText("Time: " + time + " min");
                        }
                        if (j > 0) {
                            double lat = Double.parseDouble((String) point.get("lat"));
                            double lng = Double.parseDouble((String) point.get("lng"));

                            LatLng position = new LatLng(lat, lng);

                            points.add(position);
                        }
                    }

                    lineOptions.addAll(points);
                    lineOptions.width(12);
                    if (RegType.equals("Registered")) {
                        lineOptions.color(Color.YELLOW);
                    } else if (RegType.equals("Not Registered with PHC")) {
                        lineOptions.color(Color.BLUE);
                    } else if(RegType.equals("Provisional License") || RegType.equals("Regular License")) {
                        lineOptions.color(Color.GREEN);
                    }
                 else if(RegType.equals("Quack")) {
                    lineOptions.color(Color.RED);
                }
                    lineOptions.geodesic(true);

                }

// Drawing polyline in the Google Map for the i-th route
//            pDialog.dismiss();
                if(lineOptions!=null)
                mMap.addPolyline(lineOptions);
            }
            else {
                Toast.makeText(context, "Check your internet connection and try again", Toast.LENGTH_SHORT).show();
            }

        }


    }

    private String getDirectionsUrl(double des_lat, double des_lng) {


                // Sensor enabled
                String sensor = "sensor=false";
                String mode = "mode=driving";

                String url="https://maps.googleapis.com/maps/api/directions/json?origin="+ cur_latitude + "," + cur_longitude +"&destination="+ des_lat +","+des_lng+ "&" + sensor + "&" + mode + "&key=AIzaSyBxDdbatLHZH5q77InR1Oua_bzNuXcZzEY";

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
}