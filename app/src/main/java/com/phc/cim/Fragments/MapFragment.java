package com.phc.cim.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.maps.android.clustering.ClusterManager;
import com.phc.cim.Activities.Common.ReportQuackActivity;
import com.phc.cim.Activities.Common.RouteMapsActivity;
import com.phc.cim.DataElements.District;
import com.phc.cim.DataElements.MyItem;
import com.phc.cim.DownloadClases.DownloadHCEDetail;
import com.phc.cim.Others.CurrentLocation;
import com.phc.cim.ParcelableModel.CarParcelable;
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
import java.util.Locale;

/**
 * Created by Teespire on 3/21/2017.
 */

public class MapFragment extends Fragment {

    static Circle myCircle;
    private static List<Circle> circles = new ArrayList<>();
    private ClusterManager<MyItem> mClusterManager;
    //Context context;
    static GoogleMap mMap;
    CurrentLocation gps;
    PolylineOptions lineOptions = null;
    double cur_latitude;
    double cur_longitude;
    BitmapDescriptor Reg_icon;
    BitmapDescriptor PL_icon;
    BitmapDescriptor unReg_icon;
    BitmapDescriptor quack_icon;
    LatLng current;
    ArrayList<District> hces;
    float mOrientation;
    String Status_db = null;
    String flag;
    String sectortype;
    String unRegName;
    String unRegAddress;
    String VisitStatus;
    String ActionType;
    String council;
    String HCEType;
    String Beds;
    static LatLng latLngCirlceSelected;
    TextView hcetypetext;
    TextView hceNo;
    TextView Status;
    TextView mobile;
    TextView hcspname;
    private TextView mTextMessage;
    private TextView mname;
    private TextView maddress;
    String HCE_name = null;
    Button button;
    Button action_button;
    Button call_button;
    Button comments;
    Marker current_icon;
    LatLng animateCurrent;

    LatLng lt;
    Button filterButton;
    String curr = null;
    LinearLayout linearLayout;
    String jsonStr = null;
    ProgressDialog pDialog;
    String name;
    String status;
    String is_reg_with_PHC;
    String is_reg_council;
    String final_id;
    String hcsp_cnic;
    String hcsp_phone;
    String district;
    double marker_latitude;
    double marker_longitude;
    String marker_address;
    String marker_name;
    String marker_status;
    String mobile_number;
    String dataType;
    String registrationType;
    String orgType;
    String REGfilterstatus;
    String districtText;
    String TehsilText;
    String distancetext;
    String subactionTypeID;
    String BfromText = "";
    String BtoText = "";
    String email;
    String index;
    String finalidText;
    String QuackType;
    String password;
    String isEdit;
    String username;
    String lastvisitedText;
    String RegnoText;
    String hcenameText;
    String RegType;
    String Cnic = "";
    String Phone = "";
    SearchView searchView;
    EditText searchPlate;
    ViewPager viewPager;
    Toolbar mActionBarToolbar;
    ArrayList<HashMap<String, String>> mylist;
    private ArrayList<CarParcelable> mCarParcelableList;
    private ArrayList<CarParcelable> mCarParcelableListCurrentLation;
    private ArrayList<CarParcelable> mCarParcelableListLastLocation;
    private Intent mServiceIntent;
    float hue_green = 149;
    float hue_yellow = 57;
    float hue_blue = 206;
    float hue_red = 358;
    float hue_purple = 400;
    int blue_transp = 0x408cb4cc;
    int red_transp = 0x40ff0000;
    static int filcolor = 0x40ff0000;
    TextView startdate;
    TextView enddate;
    TextView teame;
    LinearLayout circlelayout;

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
    static ArrayList<HashMap<String, String>> indtabresult;
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

    String appURI = "";
    String time1;
    String HCSPname, HCSPSO;
    MapView mMapView;
    private DownloadManager downloadManager;
    private long downloadReference;
    public static int navItemIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.filter_activity_maps, container, false);
        // context = getContext();
        button = (Button) rootView.findViewById(R.id.route);
        action_button = (Button) rootView.findViewById(R.id.action);
        call_button = (Button) rootView.findViewById(R.id.call);
        // filterButton = (Button) findViewById(R.id.filter);
        comments = (Button) rootView.findViewById(R.id.Comments);
        hceNo = (TextView) rootView.findViewById(R.id.hce_no);
        mobile = (TextView) rootView.findViewById(R.id.calltext);
        hcetypetext = (TextView) rootView.findViewById(R.id.hcetypetext);
        // Status = (TextView) rootView.findViewById(R.id.regstatustext);
        pDialog = new ProgressDialog(getActivity());
        startdate = (TextView) rootView.findViewById(R.id.startdate);
        enddate = (TextView) rootView.findViewById(R.id.enddate);
        teame = (TextView) rootView.findViewById(R.id.teame);
        circlelayout = (LinearLayout) rootView.findViewById(R.id.circlelayout);
        circlelayout.setVisibility(View.GONE);

        mTextMessage = (TextView) rootView.findViewById(R.id.Reg_text);
        mname = (TextView) rootView.findViewById(R.id.Name);
        maddress = (TextView) rootView.findViewById(R.id.addrs);
        hcspname = (TextView) rootView.findViewById(R.id.hcspname);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.maplayout);
        linearLayout.setVisibility(View.GONE);
        mMapView = (MapView) rootView.findViewById(R.id.map);

        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                //mCarParcelableList = getIntent().getParcelableArrayListExtra("carList");
                mMap = googleMap;
                //  mClusterManager = new ClusterManager<MyItem>();
                gps = new CurrentLocation(getActivity());
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                    } else {
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
                            // return null;
                        }
                    }
                } else {
                    // Check if GPS enabled
                    if (gps.canGetLocation()) {
                        cur_latitude = gps.getLatitude();
                        cur_longitude = gps.getLongitude();
                        // latlangListener.onlatlang(cur_latitude, cur_longitude);
                    } else {
                        gps.showSettingsAlert();
                        //  return null;
                    }
                }

                //gps = new CurrentLocation(this);

                // check if GPS enabled
/*----
------to get City-Name from coordinates ------------- */
                if (cur_latitude != 0.0 && cur_longitude != 0.0) {
                    String cityName = null;
                    Geocoder gcd = new Geocoder(getActivity(),
                            Locale.getDefault());
                    List<Address> addresses;
                    try {
                        addresses = gcd.getFromLocation(cur_latitude, cur_longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mMap.setMyLocationEnabled(true);
                    current = new LatLng(cur_latitude, cur_longitude);
                    // current_icon= mMap.addMarker(new MarkerOptions().position(current).title("Current Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pickup)).rotation(mOrientation).snippet("current"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 11));
                    // DataManager dataManager = new DataManager(this);
                    // hces = dataManager.getHCEArray();

                    pDialog.setMessage("Loading Data, Please wait...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    final Bundle args = getArguments();

                    dataType = args.getString("dataType");
                    registrationType = args.getString("registrationType");
                    orgType = (String) args.getString("orgType");
                    REGfilterstatus = args.getString("hcestatus");
                    districtText = args.getString("districtText");
                    TehsilText = args.getString("tehsilText");
                    subactionTypeID = args.getString("subactionTypeID");
                    distancetext = args.getString("distancetext");
                    BfromText = args.getString("BfromText");
                    BtoText = args.getString("BtoText");
                    lastvisitedText = args.getString("lastvisitedText");
                    RegnoText = args.getString("RegnoText");
                    hcenameText = args.getString("hcenameText");
                    email = args.getString("email");
                    password = args.getString("password");
                    username = args.getString("username");
                    isEdit = args.getString("isEdit");
                    finalidText = args.getString("finalidText");
                    QuackType = args.getString("QuackType");
//                    Cnic= args.getString("Cnic");
//                    Phone= args.getString("Phone");
                    indtabresult = (ArrayList<HashMap<String, String>>) args.getSerializable("indtabresult");


                    if (REGfilterstatus.equals("4")) {
                        filcolor = blue_transp;
                    } else if (REGfilterstatus.equals("5")) {
                        filcolor = red_transp;
                    }



                    /*    Intent intent = getIntent();

                     */
                    String url = getDirectionsUrl();
                    DownloadTask downloadTask = new DownloadTask();
                    //Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                } else {
                    // Toast.makeText(getApplicationContext(), "Please Refresh the page", Toast.LENGTH_SHORT).show();
                }
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(LatLng arg0) {
                        // TODO Auto-generated method stub
                        Log.d("arg0", arg0.latitude + "-" + arg0.longitude);
                        linearLayout.setVisibility(View.GONE);
                        circlelayout.setVisibility(View.GONE);


                    }
                });

                mMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

                    @Override
                    public void onCircleClick(Circle circle) {
                        // TODO Auto-generated method stub
                        // Log.d("arg0", arg0.latitude + "-" + arg0.longitude);
                        linearLayout.setVisibility(View.GONE);
                        //circlelayout.setVisibility(View.GONE);

                        circlelayout.setVisibility(View.VISIBLE);
                        for (int i = 0; i < indtabresult.size(); i++) {
                            LatLng latLng = new LatLng(Double.parseDouble(indtabresult.get(i).get("lat")), Double.parseDouble(indtabresult.get(i).get("lng")));
                            if (latLng.equals(circle.getCenter())) {
                                startdate.setText("Cluster No: " + indtabresult.get(i).get("ClusterNo"));
                                teame.setText("Total Quacks: " + indtabresult.get(i).get("total_quacks") + "\n" + "Total Not Visited: " + indtabresult.get(i).get("Not_visited") + "\n" + "Total Visited: " + indtabresult.get(i).get("Total_Visited"));
                                enddate.setText("\t\t\t\t" + indtabresult.get(i).get("functional_sealed") + " Functonal sealed \n" + "\t\t\t\t" + indtabresult.get(i).get("Closed_sealed") + " Close sealed \n" + "\t\t\t\t" + indtabresult.get(i).get("Sealing_not_req") + " Sealing not required");
                            }
                        }


                    }
                });
                //User Can add manually Marker

//                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                    @Override
//                    public void onMapClick(LatLng latLng) {
//                        // Clear existing markers
//                        mMap.clear();
//
//                        // Add a marker at the clicked location with a title
//                        Marker selectedLocationMarker = mMap.addMarker(new MarkerOptions()
//                                .position(latLng)
//                                .title("Selected Location"));
//
//                        // Save the selected latitude and longitude
//                        double manuallySelectedLatitude = latLng.latitude;
//                        double manuallySelectedLongitude = latLng.longitude;
//
//                        // Set a marker click listener to open the ReportQuackActivity
//                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                            @Override
//                            public boolean onMarkerClick(Marker marker) {
//                                if (marker.equals(selectedLocationMarker)) {
//                                    // Open the ReportQuackActivity
//                                    openReportQuackActivity(manuallySelectedLatitude, manuallySelectedLongitude);
//                                    return true; // Consume the marker click event
//                                }
//                                return false; // Allow default marker click behavior for other markers
//                            }
//                        });
//                    }
//                });


                //cur_latitude = gps.getLatitude();
                //cur_longitude = gps.getLongitude();

                // Add a marker in Sydney and move the camera
            }
        });

        return rootView;
    }

    private void openReportQuackActivity(double manuallySelectedLatitude, double manuallySelectedLongitude) {
        Intent intent = new Intent(getContext(), ReportQuackActivity.class);
        intent.putExtra("latitude", manuallySelectedLatitude);
        intent.putExtra("longitude", manuallySelectedLongitude);
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gps = new CurrentLocation(getActivity());
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
                    Toast.makeText(getActivity(), "You need to grant permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    /**
     * Initialize the sensor manager.
     */
/*    private void setupSensorManager() {
        SensorManager mSensorManager = (SensorManager) this
                .getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL);

        Log.d("message", "SensorManager setup");
    }*/

    /**
     * The sensor event listener.
     */
    SensorEventListener mSensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            mOrientation = event.values[0];
            Log.d("message", "Phone Moved " + mOrientation);
            //draw(mOrientation);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };


    public static void onFragmentInteraction(String lat, String lng) {
        for (Circle circle : circles) {
            circle.remove();
        }
        circles.clear();
        latLngCirlceSelected = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
        for (int i = 0; i < indtabresult.size(); i++) {

            LatLng latLng = new LatLng(Double.parseDouble(indtabresult.get(i).get("lat")), Double.parseDouble(indtabresult.get(i).get("lng")));
            if (latLng.equals(latLngCirlceSelected)) {
                CircleOptions circleOptions = new CircleOptions()
                        .center(latLng)   //set center
                        .radius(Double.parseDouble(indtabresult.get(i).get("Radius")))   //set radius in meters
                        //.fillColor(Color.TRANSPARENT)  //default
                        .fillColor(filcolor)  //semi-transparent
                        .strokeColor(Color.BLUE)
                        .strokeWidth(5);
                myCircle = mMap.addCircle(circleOptions);
                myCircle.setClickable(true);
                circles.add(myCircle);
                myCircle.setTag("Cluster" + i);
            } else {
                CircleOptions circleOptions = new CircleOptions()
                        .center(latLng)   //set center
                        .radius(Double.parseDouble(indtabresult.get(i).get("Radius")))   //set radius in meters
                        //.fillColor(Color.TRANSPARENT)  //default
                        .fillColor(filcolor)  //semi-transparent
                        .strokeColor(Color.WHITE)
                        .strokeWidth(5);
                myCircle = mMap.addCircle(circleOptions);
                myCircle.setClickable(true);
                circles.add(myCircle);
                myCircle.setTag("Cluster" + i);
            }
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngCirlceSelected, 14));
    }

    private void setUpClusterer() {
        // Position the map.
        //  getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(getContext(), mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        //addItems();
    }

    private void addItems() {

        // Set some lat/lng coordinates to start with.
        double lat = 51.5145160;
        double lng = -0.1270060;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MyItem offsetItem = new MyItem(lat, lng);
            mClusterManager.addItem(offsetItem);
        }
    }


    //-------------------------DownloadInspDetail Task------------------------------------------------
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
        String url = null;
        String baseurl = getContext().getResources().getString(R.string.baseurl);
        String token = getContext().getResources().getString(R.string.token);
        url = baseurl + "GetHCEs?strToken=" + token + "&District=" + districtText + "&Tehsil=" + TehsilText + "&DataType=" + dataType + "&orgType=" + orgType + "&Councile=" + registrationType + "&Status=" + REGfilterstatus + "&Category=&From=" + BfromText + "&To=" + BtoText + "&Lvs=&RegNum=" + RegnoText + "&HCEName=" + hcenameText + "&Latitude=" + cur_latitude + "&Longitude=" + cur_longitude + "&Distance=" + distancetext + "&finalid=" + finalidText + "&ActionType=" + lastvisitedText + "&QuackCategory=" + QuackType + "&QuackSubCategory=&SubActionType=" + subactionTypeID;
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
                    mylist = new ArrayList<HashMap<String, String>>();
                    for (int i = 0; i < json.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = json.getJSONObject(i);
                        map.put("index", String.valueOf(i + 1));
                        map.put("Address", e.getString("Address"));
                        map.put("CensusPhcID", e.getString("CensusPhcID"));
                        map.put("CensusRegNum", e.getString("CensusRegNum"));
                        map.put("ContactNo", e.getString("ContactNo"));
                        map.put("CouncilNo", e.getString("CouncilNo"));
                        map.put("HCE_Cat_Type", e.getString("HCE_Cat_Type"));
                        map.put("ID_PK", e.getString("ID_PK"));
                        map.put("Name", e.getString("Name"));
                        map.put("PWDID", e.getString("PWDID"));
                        map.put("PhcLicenseType", e.getString("PhcLicenseType"));
                        map.put("councils", e.getString("councils"));
                        map.put("designation", e.getString("designation"));
                        map.put("district", e.getString("district"));
                        map.put("division", e.getString("division"));
                        map.put("final_id", e.getString("final_id"));
                        map.put("gender", e.getString("gender"));
                        map.put("hce_mobile", e.getString("hce_mobile"));
                        map.put("hcsp_name", e.getString("hcsp_name"));
                        map.put("hcsp_sodowo", e.getString("hcsp_sodowo"));
                        map.put("is_reg_council", e.getString("is_reg_council"));
                        map.put("is_reg_with_phc", e.getString("is_reg_with_phc"));
                        map.put("RegType", e.getString("RegType"));
                        map.put("lat", e.getString("lat"));
                        map.put("lng", e.getString("lng"));
                        map.put("phcRegistrationNumber", e.getString("phcRegistrationNumber"));
                        map.put("sector_type", e.getString("sector_type"));
                        map.put("tehsil", e.getString("tehsil"));
                        map.put("total_beds", e.getString("total_beds"));
                        map.put("uu_db_id", e.getString("uu_db_id"));
                        map.put("ActionType", e.getString("ActionType"));
                        map.put("VisitStatus", e.getString("VisitStatus"));
//                            map.put("hcsp_cnic", e.getString("hcsp_cnic"));
//                            map.put("hcsp_phone", e.getString("hcsp_phone"));

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

            if (result != null) {
                if (result.size() > 0) {
                    LatLng latLngzoom = null;

                    for (int i = 0; i < result.size(); i++) {
                        double latitude = 0;
                        double longitude = 0;
                        String lt = result.get(i).get("lat").toString();
                        String ln = result.get(i).get("lng").toString();
                        if (lt != "null") {
                            latitude = Double.parseDouble(lt);
                        }
                        if (ln != "null") {
                            longitude = Double.parseDouble(ln);
                        }


                        name = result.get(i).get("Name");
                        Status_db = result.get(i).get("PhcLicenseType");
                        is_reg_with_PHC = result.get(i).get("is_reg_with_phc");
                        is_reg_council = result.get(i).get("is_reg_council");
                        RegType = result.get(i).get("RegType");
                        VisitStatus = result.get(i).get("VisitStatus");
                        ActionType = result.get(i).get("ActionType");
                        if (latitude != 0 && longitude != 0) {
                            latLngzoom = new LatLng(latitude, longitude);
                        }


                        if (RegType.equals("Registered")) {

                            if (latitude != 0 && longitude != 0) {

                                LatLng latLng = new LatLng(latitude, longitude);

                                if (VisitStatus.equals("1") && (ActionType.equals("1") || ActionType.equals("3"))) {
                                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_sealed_yellow)).snippet(RegType));

                                } else if (VisitStatus.equals("1") && ActionType.equals("2")) {
                                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_visited_yellow)).snippet(RegType));

                                } else {

                                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.defaultMarker(hue_yellow)).snippet(RegType));
                                }
                            }

                        } else if (RegType.equals("Provisional License") || RegType.equals("Regular License")) {

                            if (latitude != 0 && longitude != 0) {


                                LatLng latLng = new LatLng(latitude, longitude);
                                if (VisitStatus.equals("1") && (ActionType.equals("1") || ActionType.equals("3"))) {
                                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_sealed_green)).snippet(RegType));

                                } else if (VisitStatus.equals("1") && ActionType.equals("2")) {
                                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_visited_green)).snippet(RegType));

                                } else {

                                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.defaultMarker(hue_green)).snippet(RegType));
                                }
                            }

                        } else if (RegType.equals("Not Registered with PHC")) {
                            if (latitude != 0 && longitude != 0) {

                                LatLng latLng = new LatLng(latitude, longitude);
                                if (VisitStatus.equals("1") && (ActionType.equals("1") || ActionType.equals("3"))) {
                                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_sealed_blue)).snippet(RegType));

                                } else if (VisitStatus.equals("1")) {
                                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_visited_blue)).snippet(RegType));

                                } else {
                                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.defaultMarker(hue_blue)).snippet(RegType));
                                }
                            }

                        } else if (RegType.equals("Quack")) {
                            if (latitude != 0 && longitude != 0) {

                                LatLng latLng = new LatLng(latitude, longitude);
                                if (VisitStatus.equals("1") && (ActionType.equals("1") || ActionType.equals("3"))) {
                                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_sealed_red)).snippet(RegType));

                                } else if (VisitStatus.equals("1") && ActionType.equals("2")) {
                                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_visited_red)).snippet(RegType));

                                } else {
                                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.defaultMarker(hue_red)).snippet(RegType));

                                }
                            }
//                        } else if (RegType.equals("Quack") || RegType.equals("Not Registered with PHC")) {
//                            if (latitude != 0 && longitude != 0) {
//
//                                LatLng latLng = new LatLng(latitude, longitude);
//                                if (VisitStatus.equals("1") && (ActionType.equals("2"))) {
//                                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_sealed_purple)).snippet(RegType));
//
//                                } else if (VisitStatus.equals("1") && ActionType.equals("2")) {
//                                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_visited_purple)).snippet(RegType));
//
//                                } else {
//                                    mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(BitmapDescriptorFactory.defaultMarker(hue_purple)).snippet(RegType));
//
//                                }
//                            }
                        }


                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                            @Override
                            public boolean onMarkerClick(final Marker arg0) {
                                arg0.showInfoWindow();
                                if (arg0.getSnippet() != null) {
                                    if (arg0.getSnippet().equals("Registered")) {
                                        for (int i = 0; i < result.size(); i++) {
                                            double _latitude = Double.parseDouble(result.get(i).get("lat"));
                                            double _longitude = Double.parseDouble(result.get(i).get("lng"));
                                            LatLng latLng = new LatLng(_latitude, _longitude);
                                            String title_name = result.get(i).get("Name");
                                            if (arg0.getTitle().equals(title_name)) {
                                                marker_latitude = _latitude;
                                                marker_longitude = _longitude;
                                                marker_address = result.get(i).get("Address");
                                                marker_name = result.get(i).get("Name");
                                                marker_status = result.get(i).get("RegType");
                                                status = marker_status;
                                                mobile_number = result.get(i).get("hce_mobile");
                                                district = result.get(i).get("district");
                                                index = result.get(i).get("index");
                                                VisitStatus = result.get(i).get("VisitStatus");
                                                sectortype = result.get(i).get("sector_type");
                                                council = result.get(i).get("councils");
                                                HCEType = result.get(i).get("HCE_Cat_Type");
                                                Beds = result.get(i).get("total_beds");
                                                HCSPname = result.get(i).get("hcsp_name");
                                                HCSPSO = result.get(i).get("hcsp_sodowo");
                                                mname.setText(marker_name + "( " + sectortype + " )");
                                                maddress.setText(marker_address);
                                                hcspname.setText(result.get(i).get("hcsp_name") + " - " + result.get(i).get("hcsp_sodowo"));
                                                hcetypetext.setText(result.get(i).get("HCE_Cat_Type") + " (" + result.get(i).get("total_beds") + " beds)" + " - " + result.get(i).get("councils"));
                                                mTextMessage.setText(marker_status);
                                                hceNo.setText(index);
                                                mobile.setText(mobile_number);
                                                final_id = result.get(i).get("final_id");
//                                                    hcsp_cnic = result.get(i).get("hcsp_cnic");
//                                                    hcsp_phone = result.get(i).get("hcsp_phone");

                                                mname.setVisibility(View.VISIBLE);
                                                maddress.setVisibility(View.VISIBLE);
                                            }
                                        }
                                        circlelayout.setVisibility(View.GONE);
                                        linearLayout.setVisibility(View.VISIBLE);
                                        mname.setText(arg0.getTitle());
                                        curr = "Reg";

                                    } else if (arg0.getSnippet().equals("current")) {
                                        curr = "curent";
                                        mTextMessage.setText("Current Location");
                                        status = "Current Location";
                                        maddress.setVisibility(View.GONE);
                                        mname.setVisibility(View.GONE);

                                    } else if (arg0.getSnippet().equals("Provisional License") || arg0.getSnippet().equals("Regular License")) {
                                        for (int i = 0; i < result.size(); i++) {
                                            double _latitude = Double.parseDouble(result.get(i).get("lat"));
                                            double _longitude = Double.parseDouble(result.get(i).get("lng"));
                                            LatLng latLng = new LatLng(_latitude, _longitude);
                                            String title_name = result.get(i).get("Name");
                                            if (arg0.getTitle().equals(title_name)) {

                                                marker_latitude = _latitude;
                                                marker_longitude = _longitude;
                                                marker_address = result.get(i).get("Address");
                                                marker_name = result.get(i).get("Name");
                                                marker_status = result.get(i).get("RegType");
                                                status = marker_status;
                                                mobile_number = result.get(i).get("hce_mobile");
                                                district = result.get(i).get("district");
                                                final_id = result.get(i).get("final_id");
                                                index = result.get(i).get("index");
                                                VisitStatus = result.get(i).get("VisitStatus");
                                                sectortype = result.get(i).get("sector_type");
                                                council = result.get(i).get("councils");
                                                HCEType = result.get(i).get("HCE_Cat_Type");
                                                Beds = result.get(i).get("total_beds");
                                                HCSPname = result.get(i).get("hcsp_name");
                                                HCSPSO = result.get(i).get("hcsp_sodowo");
                                                hcspname.setText(result.get(i).get("hcsp_name") + " - " + result.get(i).get("hcsp_sodowo"));
                                                mname.setText(marker_name + "( " + sectortype + " )");
                                                hcetypetext.setText(result.get(i).get("HCE_Cat_Type") + " (" + result.get(i).get("total_beds") + " beds)" + " - " + result.get(i).get("councils"));
                                                mTextMessage.setText(marker_status);
                                                maddress.setText(marker_address);
                                                mobile.setText(mobile_number);
                                                hceNo.setText(index);
                                                mname.setVisibility(View.VISIBLE);
                                                maddress.setVisibility(View.VISIBLE);
//                                                    hcsp_cnic = result.get(i).get("hcsp_cnic");
//                                                    hcsp_phone = result.get(i).get("hcsp_phone");
                                            }
                                        }
                                        circlelayout.setVisibility(View.GONE);
                                        linearLayout.setVisibility(View.VISIBLE);
                                        curr = "Reg";

                                    } else if (arg0.getSnippet().equals("Not Registered with PHC")) {
                                        for (int i = 0; i < result.size(); i++) {
                                            double _latitude = Double.parseDouble(result.get(i).get("lat"));
                                            double _longitude = Double.parseDouble(result.get(i).get("lng"));
                                            LatLng latLng = new LatLng(_latitude, _longitude);
                                            String title_name = result.get(i).get("Name");
                                            if (arg0.getTitle().equals(title_name)) {

                                                marker_latitude = _latitude;
                                                marker_longitude = _longitude;
                                                marker_address = result.get(i).get("Address");
                                                marker_name = result.get(i).get("Name");
                                                mobile_number = result.get(i).get("hce_mobile");
                                                marker_status = result.get(i).get("RegType");
                                                status = marker_status;
                                                district = result.get(i).get("district");
                                                final_id = result.get(i).get("final_id");
                                                index = result.get(i).get("index");
                                                council = result.get(i).get("councils");
                                                HCEType = result.get(i).get("HCE_Cat_Type");
                                                Beds = result.get(i).get("total_beds");
                                                sectortype = result.get(i).get("sector_type");
                                                HCSPname = result.get(i).get("hcsp_name");
                                                HCSPSO = result.get(i).get("hcsp_sodowo");
                                                hcspname.setText(result.get(i).get("hcsp_name") + " - " + result.get(i).get("hcsp_sodowo"));
                                                mname.setText(marker_name + "( " + sectortype + " )");
                                                hcetypetext.setText(result.get(i).get("HCE_Cat_Type") + " (" + result.get(i).get("total_beds") + " beds)" + " - " + result.get(i).get("councils"));
                                                mTextMessage.setText(marker_status);
                                                maddress.setText(marker_address);
                                                mobile.setText(mobile_number);
                                                VisitStatus = result.get(i).get("VisitStatus");
                                                hceNo.setText(index);
                                                mname.setVisibility(View.VISIBLE);
                                                maddress.setVisibility(View.VISIBLE);
//                                                    hcsp_cnic = result.get(i).get("hcsp_cnic");
//                                                    hcsp_phone = result.get(i).get("hcsp_phone");
                                            }
                                        }
                                        circlelayout.setVisibility(View.GONE);
                                        linearLayout.setVisibility(View.VISIBLE);
                                        curr = "unReg";


                                    } else if (arg0.getSnippet().equals("Quack")) {
                                        for (int i = 0; i < result.size(); i++) {
                                            double _latitude = Double.parseDouble(result.get(i).get("lat"));
                                            double _longitude = Double.parseDouble(result.get(i).get("lng"));
                                            LatLng latLng = new LatLng(_latitude, _longitude);
                                            String title_name = result.get(i).get("Name");
                                            if (arg0.getTitle().equals(title_name)) {

                                                marker_latitude = _latitude;
                                                marker_longitude = _longitude;
                                                marker_address = result.get(i).get("Address");
                                                marker_name = result.get(i).get("Name");
                                                mobile_number = result.get(i).get("hce_mobile");
                                                marker_status = result.get(i).get("RegType");
                                                status = marker_status;
                                                district = result.get(i).get("district");
                                                final_id = result.get(i).get("final_id");
                                                index = result.get(i).get("index");
                                                council = result.get(i).get("councils");
                                                HCEType = result.get(i).get("HCE_Cat_Type");
                                                Beds = result.get(i).get("total_beds");
                                                sectortype = result.get(i).get("sector_type");
                                                VisitStatus = result.get(i).get("VisitStatus");
                                                HCSPname = result.get(i).get("hcsp_name");
                                                HCSPSO = result.get(i).get("hcsp_sodowo");
                                                hcspname.setText(result.get(i).get("hcsp_name") + " - " + result.get(i).get("hcsp_sodowo"));
                                                mname.setText(marker_name + "( " + sectortype + " )");
                                                hcetypetext.setText(result.get(i).get("HCE_Cat_Type") + " (" + result.get(i).get("total_beds") + " beds)" + " - " + result.get(i).get("councils"));
                                                mTextMessage.setText(marker_status);
                                                hceNo.setText(index);
                                                mobile.setText(mobile_number);
                                                maddress.setText(marker_address);
                                                mname.setVisibility(View.VISIBLE);
                                                maddress.setVisibility(View.VISIBLE);
//                                                    hcsp_cnic = result.get(i).get("hcsp_cnic");
//                                                    hcsp_phone = result.get(i).get("hcsp_phone");
                                            }
                                        }
                                        circlelayout.setVisibility(View.GONE);
                                        linearLayout.setVisibility(View.VISIBLE);
                                        curr = "Quack";
                                        // mTextMessage.setText(status);
                                        //mTextMessage.setTextColor(RED);

                                    }
                                }
                                return true;

                            }

                        });

                    }


                    if (indtabresult != null) {

                        for (int i = 0; i < indtabresult.size(); i++) {

                            LatLng latLng = new LatLng(Double.parseDouble(indtabresult.get(i).get("lat")), Double.parseDouble(indtabresult.get(i).get("lng")));
                            CircleOptions circleOptions = new CircleOptions()
                                    .center(latLng)   //set center
                                    .radius(Double.parseDouble(indtabresult.get(i).get("Radius")))   //set radius in meters
                                    //.fillColor(Color.TRANSPARENT)  //default
                                    .fillColor(filcolor)  //semi-transparent
                                    .strokeColor(Color.WHITE)
                                    .strokeWidth(5);
                            myCircle = mMap.addCircle(circleOptions);
                            myCircle.setClickable(true);
                            circles.add(myCircle);
                            myCircle.setTag("Cluster" + i);
                        }
                    }


                    if (latLngzoom != null)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngzoom, 11));

                    action_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (VisitStatus.equals("1")) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("Action for this record is already recorded:")
                                        .setCancelable(true)
                                        .setPositiveButton("Update Action", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                // launch new intent instead of loading fragment

                                                int count = 1;
                                                DownloadHCEDetail downloadHceDetail = new DownloadHCEDetail(getContext(), marker_name, final_id, email, password, username, isEdit, index, count, marker_status);

                                            }
                                        })
                                        .setNegativeButton("Upload Image", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                int count = 2;

                                                DownloadHCEDetail downloadHceDetail = new DownloadHCEDetail(getContext(), marker_name, final_id, email, password, username, isEdit, index, count, marker_status);

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            } else {

                                int count = 3;
                                DownloadHCEDetail downloadHceDetail = new DownloadHCEDetail(getContext(), marker_name, final_id, email, password, username, isEdit, index, count, marker_status);
                            }
                        }
                    });

                    button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (curr != null) {
                                if (curr.equals("curent")) {
                                    Toast.makeText(getActivity(), "Please Select HCE", Toast.LENGTH_SHORT).show();
                                } else {

                                    Intent firstpage = new Intent(getContext(), RouteMapsActivity.class);
                                    firstpage.putExtra("des_latitude", marker_latitude);
                                    firstpage.putExtra("des_longitude", marker_longitude);
                                    firstpage.putExtra("RegType", marker_status);
                                    firstpage.putExtra("final_id", final_id);
                                    firstpage.putExtra("mobile_number", mobile_number);
                                    firstpage.putExtra("name", marker_name);
                                    firstpage.putExtra("index", index);
                                    firstpage.putExtra("address", marker_address);
                                    firstpage.putExtra("email", email);
                                    firstpage.putExtra("Password", password);
                                    firstpage.putExtra("username", username);
                                    firstpage.putExtra("isEdit", isEdit);
                                    firstpage.putExtra("sectortype", sectortype);
                                    firstpage.putExtra("council", council);
                                    firstpage.putExtra("HCEType", HCEType);
                                    firstpage.putExtra("Beds", Beds);
                                    firstpage.putExtra("VisitStatus", VisitStatus);
                                    firstpage.putExtra("HCSPname", HCSPname);
                                    firstpage.putExtra("HCSPSO", HCSPSO);
                                    getContext().startActivity(firstpage);

                                }

                            } else {
                                Toast.makeText(getActivity(), "Please Select HCE", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    call_button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (curr != null) {
                                if (curr.equals("curent")) {
                                    Toast.makeText(getActivity(), "Please Select HCE", Toast.LENGTH_SHORT).show();
                                } else if (mobile_number != null) {

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

                            } else {
                                Toast.makeText(getActivity(), "Please Select HCE", Toast.LENGTH_SHORT).show();
                            }

                        }

                    });
        /*        filterButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent firstpage = new Intent(context, FilterActivity.class);
                        firstpage.putExtra("des_latitude", marker_latitude);
                        firstpage.putExtra("des_longitude", marker_longitude);
                        firstpage.putExtra("status", status);
                        firstpage.putExtra("markerstatus", marker_status);
                        firstpage.putExtra("name", marker_name);
                        firstpage.putExtra("address", marker_address);
                        firstpage.putExtra("mobile", mobile_number);
                        context.startActivity(firstpage);

                    }
                });*/
                    comments.setOnClickListener(new View.OnClickListener() {

                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(View v) {
                            if (curr != null) {
                                if (curr.equals("curent")) {
                                    Toast.makeText(getActivity(), "Please Select HCE", Toast.LENGTH_SHORT).show();
                                } else {

                                    int count = 2;
                                    DownloadHCEDetail downloadHceDetail = new DownloadHCEDetail(getContext(), marker_name, final_id, email, password, username, isEdit, index, count, marker_status);
                            /*    Intent firstpage = new Intent(context, ReportQuackActivity.class);
                                firstpage.putExtra("des_latitude", marker_latitude);
                                firstpage.putExtra("des_longitude", marker_longitude);
                                firstpage.putExtra("status", status);
                                firstpage.putExtra("markerstatus", marker_status);
                                firstpage.putExtra("name", marker_name);
                                firstpage.putExtra("address", marker_address);
                                firstpage.putExtra("mobile", mobile_number);
                                context.startActivity(firstpage);*/
                                }

                            } else {
                                Toast.makeText(getActivity(), "Please Select HCE", Toast.LENGTH_SHORT).show();
                            }

                        }

                    });
                }
            } else {
                Toast.makeText(getActivity(), "No data found", Toast.LENGTH_SHORT).show();
            }
            if (pDialog.isShowing())
                pDialog.dismiss();
            // mMap.clear();
        }

    }
//-------------------------------------Tabs View-------------------------------------------->>>>>>>>>>>>>
/*    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LocateUsFragment(), "List View");
          adapter.addActivity(new MapsActivity(),"Map");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private final List<Activity> mActivityList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
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
        public void addActivity(Activity activity, String title) {
            mActivityList.add(activity);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }*/




/*
 private void loadNavHeader() {
        // name, website

        txtName.setText("Ali Awan");
        txtWebsite.setText("ali.abdul@phc.org,pk");

        // loading header background image
     */
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
        navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);*//*

    }

  */
/***
 * Returns respected fragment that user
 * selected from navigation menu
 *//*

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
                HomeFragment fragment = getHomeFragment();
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
           */
/*   Intent i = new Intent(context, Login_Activity.class);
                startActivity(i);*//*

                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // photos
                MapFragment photosFragment = new MapFragment();
                return photosFragment;
            case 2:
                // movies fragment
                HCEListFragment videosFragment = new HCEListFragment();
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
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
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
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_photos:
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
                        break;
                    case R.id.nav_settings:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_SETTINGS;
                        break;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(context, Aboutus.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(context, ChangePasswordActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
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
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
            getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        }
        return true;
    }

*/


}
