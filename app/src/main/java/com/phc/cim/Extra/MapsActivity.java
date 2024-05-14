package com.phc.cim.Extra;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.phc.cim.Activities.Common.FilterActivity;
import com.phc.cim.Activities.Common.RouteMapsActivity;
import com.phc.cim.BroadcastService.BroadCastService;
import com.phc.cim.DataElements.District;
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

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.YELLOW;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    Context context;
    GoogleMap mMap;
    CurrentLocation gps;
    PolylineOptions lineOptions = null;
    double cur_latitude;
    double cur_longitude;
    BitmapDescriptor Reg_icon;
    BitmapDescriptor PL_icon;
    BitmapDescriptor unReg_icon;
    BitmapDescriptor quack_icon;
    LatLng current;
    LatLng animateCurrent;
    ArrayList<District> hces;
    float mOrientation;
    String Status_db;
    String flag;
    String is_reg_with_PHC;
    String is_reg_council;
    String unRegName;
    String unRegAddress;
    private TextView mTextMessage;
    private TextView mname;
    private TextView maddress;
    String HCE_name = null;
    Button button;
    Marker current_icon;
    Button call_button;
    Button comments;
    Button filterButton;
    String curr = null;
    FrameLayout FrameLayout;
    String jsonStr = null;
    ProgressDialog pDialog;
    String name;
    String status;
    String district;
    double marker_latitude;
    double marker_longitude;
    String marker_address;
    String marker_name;
    String marker_status;
    String mobile_number;
    String final_id;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String cityName = null;
    SearchView searchView;
    EditText searchPlate;
    Toolbar mActionBarToolbar;
    private ArrayList<CarParcelable> mCarParcelableList;
    private ArrayList<CarParcelable> mCarParcelableListCurrentLation;
    private ArrayList<CarParcelable> mCarParcelableListLastLocation;
    ArrayList<HashMap<String, String>> mylist;
    private Intent mServiceIntent;
    String email;
    int count=0;
    int searchCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

       // setContentView(R.layout.activity_maps);
        button = (Button) findViewById(R.id.route);
        call_button = (Button) findViewById(R.id.call);
        //filterButton = (Button) findViewById(R.id.filter);
        comments = (Button) findViewById(R.id.Comments);
        Reg_icon = BitmapDescriptorFactory.fromResource(R.drawable.map_yellow_icon);
        PL_icon = BitmapDescriptorFactory.fromResource(R.drawable.map_green_icon);
        unReg_icon = BitmapDescriptorFactory.fromResource(R.drawable.blue_icon);
        quack_icon = BitmapDescriptorFactory.fromResource(R.drawable.red_icon);
        context = this;
        pDialog = new ProgressDialog(this);
        mTextMessage = (TextView) findViewById(R.id.Reg_text);
        mname = (TextView) findViewById(R.id.Name);
        maddress = (TextView) findViewById(R.id.addrs);

        //FrameLayout = (FrameLayout) findViewById(R.id.mapframe);
        //FrameLayout.setVisibility(View.INVISIBLE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        email= (String) intent.getSerializableExtra("email");
    /*    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_location);

/*        if (searchItem != null) {
            searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {


                    return false;
                }
            });

            searchPlate = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchPlate.setHint("HCE name");


            View searchPlateView = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
            searchPlateView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));

            // use this method for search process



            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // use this method when query submitted
                    searchCount=1;
                    String url = getsearchUrl(query);
                    DownloadTask downloadTask = new DownloadTask();
                    //Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                    return true;

                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // use this method for auto complete search process
                    if(!TextUtils.isEmpty(newText)) {
                        *//*list.setVisibility(View.VISIBLE);
                        stops = dataManager.getStopsArray();

                        stopListAdapter = new StopListAdapter(context, stops);
                        list.setAdapter(stopListAdapter);
                        stopListAdapter.getFilter().filter(newText);*//*

                    }

                    else {

                        // list.setVisibility(View.GONE);
                    }
                    return false;
                }

            });

            SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        }*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gps = new CurrentLocation(this);
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
                    Toast.makeText(getApplicationContext(), "You need to grant permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        gps = new CurrentLocation(this);
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

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

            Geocoder gcd = new Geocoder(getBaseContext(),
                    Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(cur_latitude, cur_longitude, 1);
                if (addresses.size() > 0)
                    System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            } catch (IOException e) {
                e.printStackTrace();
            }
            current = new LatLng(cur_latitude, cur_longitude);
            mMap.setMyLocationEnabled(true);
            //current_icon = mMap.addMarker(new MarkerOptions().position(current).title("Current Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pickup)).rotation(mOrientation).snippet("current"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 14));
            // DataManager dataManager = new DataManager(this);
            // hces = dataManager.getHCEArray();

       /*     pDialog.setMessage("Please wait!....");
            pDialog.setCancelable(false);
            pDialog.show();
            String url = getDirectionsUrl();
            DownloadTask downloadTask = new DownloadTask();
            //Start downloading json data from Google Directions API
            downloadTask.execute(url);*/
        } else {
            // Toast.makeText(getApplicationContext(), "Please Refresh the page", Toast.LENGTH_SHORT).show();
        }


        //cur_latitude = gps.getLatitude();
        //cur_longitude = gps.getLongitude();

        // Add a marker in Sydney and move the camera

    }

    /**
     * Initialize the sensor manager.
     */
    private void setupSensorManager() {
        SensorManager mSensorManager = (SensorManager) this
                .getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL);

        Log.d("message", "SensorManager setup");
    }

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


    private void addMarker() {




            if (current_icon != null) {
                current_icon.remove();
            }
            String lat = Double.toString(cur_latitude);
            //Log.d(TAG, lat);

            LatLng carLoc = null, newCarLoc = null;
            //mCarParcelableListLastLocation = mCarParcelableListCurrentLation;
            if (mCarParcelableListLastLocation == null) {
                mCarParcelableListLastLocation = mCarParcelableListCurrentLation;
            }
            //Initially add markers for all cars to the map
            if (mCarParcelableListCurrentLation == null) {
                mCarParcelableListLastLocation = mCarParcelableList;
                // for(CarParcelable car : mCarParcelableList) {
                carLoc = new LatLng(cur_latitude, cur_longitude);

                current_icon = mMap.addMarker(new MarkerOptions()
                        .position(carLoc)
                        .title("Current Location")
                        .snippet("current")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pickup)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(carLoc, 14));

                //}
            } else { // then update each car's position by moving the marker smoothly from previous
                // location to the current location
                for (int i = 0; i < mCarParcelableListCurrentLation.size(); i++) {

                    carLoc = new LatLng(mCarParcelableListLastLocation.get(i).mLat, mCarParcelableListLastLocation.get(i).mLon);
                    newCarLoc = new LatLng(mCarParcelableListCurrentLation.get(i).mLat, mCarParcelableListCurrentLation.get(i).mLon);
                    cur_latitude = mCarParcelableListCurrentLation.get(i).mLat;
                    cur_longitude = mCarParcelableListCurrentLation.get(i).mLon;
                    animateMarker(i, carLoc, newCarLoc, false);
                }
                mCarParcelableListLastLocation = mCarParcelableListCurrentLation;
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
        animateCurrent=toPosition;


/*
        current_icon = mMap.addMarker(new MarkerOptions()
                .position(startPosition)
                .title(mCarParcelableListCurrentLation.get(position).mCarName)
                .snippet(mCarParcelableListCurrentLation.get(position).mAddress)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pointer)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPosition, 14));*/
       /* if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);*/
 /*       final Handler handler = new Handler();
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
                animateCurrent = new LatLng(lat, lng);
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
        if (cur_latitude != 0.0 && cur_longitude != 0.0 && mCarParcelableListCurrentLation != mCarParcelableListLastLocation) {
            // pDialog = new ProgressDialog(this);
            if(count==0){
                pDialog.setMessage("Loading Data, Please wait …..");
                pDialog.setCancelable(false);
                pDialog.show();
            }
            String url = getDirectionsUrl();
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(url);
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

        // Building the url to the web service
         String baseurl = context.getResources().getString(R.string.baseurl);

        String url = baseurl+"GetNearestLocations?Latitude=" + cur_latitude + "&Longitude=" + cur_longitude;
        url = url.replaceAll(" ", "%20");

        return url;
    }
    private String getsearchUrl(String searchtext) {

        // Building the url to the web service
         String baseurl = context.getResources().getString(R.string.baseurl);
        String token = context.getResources().getString(R.string.token);

        String url = baseurl+"Get_HCE_RegistrationNo_NameWise?strToken="+token+"&RegNum=&HCEName="+searchtext;
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

                        if(searchCount==1){
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
                            map.put("hcsp_cnic", e.getString("hcsp_cnic"));
                            map.put("hcsp_name", e.getString("hcsp_name"));
                            map.put("hcsp_sodowo", e.getString("hcsp_sodowo"));
                            map.put("is_reg_council", e.getString("is_reg_council"));
                            map.put("is_reg_with_phc", e.getString("is_reg_with_phc"));
                            map.put("lat", e.getString("lat"));
                            map.put("lng", e.getString("lng"));
                            map.put("phcRegistrationNumber", e.getString("phcRegistrationNumber"));
                            map.put("sector_type", e.getString("sector_type"));
                            map.put("tehsil", e.getString("tehsil"));
                            map.put("total_beds", e.getString("total_beds"));
                            map.put("uu_db_id", e.getString("uu_db_id"));
                        }
                        else {
                            map.put("id", String.valueOf(i));
                            map.put("latitude", e.getString("lat"));
                            map.put("longitude", e.getString("lng"));
                            map.put("address", e.getString("Address"));
                            map.put("name", e.getString("orgName"));
                            map.put("district", e.getString("CensusDistrict"));
                            map.put("status", e.getString("PhcLicenseType"));
                            map.put("MobileNumber", e.getString("MobileNumber"));
                            map.put("flag", e.getString("flag"));
                            map.put("hce_name", e.getString("hce_name"));
                            map.put("postal_address", e.getString("postal_address"));
                            map.put("dataType", e.getString("dataType"));
                            map.put("registrationType", e.getString("registrationType"));
                            map.put("orgType", e.getString("orgType"));
                            map.put("final_id", e.getString("final_id"));
                        }

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

            if (pDialog.isShowing())
                pDialog.dismiss();

            mMap.clear();
           /* current_icon = mMap.addMarker(new MarkerOptions()
                    .position(animateCurrent)
                    .title("Current Location")
                    .snippet("current")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pointer)));*/
            if(count==0){
                         if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(animateCurrent, 14));
                count=1;
            }


            if(result!=null) {
                for (int i = 0; i < result.size(); i++) {

                    double latitude = Double.parseDouble(result.get(i).get("lat"));
                    double longitude = Double.parseDouble(result.get(i).get("lng"));
                    name = result.get(i).get("Name");
                    Status_db = result.get(i).get("PhcLicenseType");
                    is_reg_with_PHC= result.get(i).get("is_reg_with_phc");
                    is_reg_council= result.get(i).get("is_reg_council");


                    //id = Float.parseFloat(hces.get(i).getHCE_id());

                    if (Status_db.equals("Reg")) {

                        LatLng latLng = new LatLng(latitude, longitude);

                        mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(Reg_icon).snippet(Status_db));

                    } else if (Status_db.equals("Reg/PL") || Status_db.equals("Reg/PL/RL")) {
                        LatLng latLng = new LatLng(latitude, longitude);

                        mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(PL_icon).snippet(Status_db));


                    }
                    else if (is_reg_with_PHC.equals("0") && is_reg_council.equals("1")) {
                        LatLng latLng = new LatLng(latitude, longitude);

                        mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(unReg_icon).snippet("UnRegistered"));


                    }
                    else if (is_reg_council.equals("0")) {
                        LatLng latLng = new LatLng(latitude, longitude);

                        mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(quack_icon).snippet("Quack"));


                    }

                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                        @Override
                        public boolean onMarkerClick(final Marker arg0) {
                            arg0.showInfoWindow();
                            if (arg0.getSnippet().equals("Reg")) {
                                for (int i = 0; i < result.size(); i++) {
                                    double _latitude = Double.parseDouble(result.get(i).get("lat"));
                                    double _longitude = Double.parseDouble(result.get(i).get("lng"));
                                    LatLng latLng = new LatLng(_latitude, _longitude);
                                    if (arg0.getPosition().equals(latLng)) {
                                        marker_latitude = _latitude;
                                        marker_longitude = _longitude;
                                        marker_address = result.get(i).get("Address");
                                        marker_name = result.get(i).get("Name");
                                        marker_status = result.get(i).get("is_reg_with_phc");
                                        mobile_number = result.get(i).get("hce_mobile");
                                        district = result.get(i).get("district");
                                        maddress.setText(marker_address);
                                        mname.setText(marker_name);
                                        final_id = result.get(i).get("final_id");
                                       /* SpannableString spanString = new SpannableString(marker_address);
                                        spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
                                        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
                                        spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);*/
                                        mname.setVisibility(View.VISIBLE);
                                        maddress.setVisibility(View.VISIBLE);
                                    }
                                }
                              //  FrameLayout.setVisibility(View.VISIBLE);
                                mname.setText(arg0.getTitle());
                                curr = "Reg";
                                status = "Registered but not yet Licensed";
                                mTextMessage.setText(status);
                                mTextMessage.setTextColor(YELLOW);

                            } else if (arg0.getSnippet().equals("current")) {
                                curr = "curent";
                                mTextMessage.setText("Current Location");
                                status = "Current Location";
                                maddress.setVisibility(View.GONE);
                                mname.setVisibility(View.GONE);
                            } else if (arg0.getSnippet().equals("Reg/PL") || arg0.getSnippet().equals("Reg/PL/RL")) {
                                for (int i = 0; i < result.size(); i++) {
                                    double _latitude = Double.parseDouble(result.get(i).get("lat"));
                                    double _longitude = Double.parseDouble(result.get(i).get("lng"));
                                    LatLng latLng = new LatLng(_latitude, _longitude);
                                    if (arg0.getPosition().equals(latLng)) {

                                        marker_latitude = _latitude;
                                        marker_longitude = _longitude;
                                        marker_address = result.get(i).get("Address");
                                        marker_name = result.get(i).get("Name");
                                        marker_status = result.get(i).get("is_reg_with_phc");
                                        mobile_number = result.get(i).get("hce_mobile");
                                        district = result.get(i).get("district");
                                        final_id = result.get(i).get("final_id");
                                        maddress.setText(marker_address);
                                        mname.setText(marker_name);
                                        mname.setVisibility(View.VISIBLE);
                                        maddress.setVisibility(View.VISIBLE);
                                    }
                                }
                              //  FrameLayout.setVisibility(View.VISIBLE);
                                curr = "Reg";
                                status = "PHC Provisional Licensed";
                                mTextMessage.setText(status);
                                mTextMessage.setTextColor(GREEN);
                            }
                                else if (arg0.getSnippet().equals("UnRegistered")) {
                                    for (int i = 0; i < result.size(); i++) {
                                        double _latitude = Double.parseDouble(result.get(i).get("lat"));
                                        double _longitude = Double.parseDouble(result.get(i).get("lng"));
                                        LatLng latLng = new LatLng(_latitude, _longitude);
                                        if (arg0.getPosition().equals(latLng)) {

                                            marker_latitude = _latitude;
                                            marker_longitude = _longitude;
                                            marker_address = result.get(i).get("Address");
                                            marker_name = result.get(i).get("Name");
                                            mobile_number = result.get(i).get("hce_mobile");
                                            marker_status = "UnRegistered";
                                            district = result.get(i).get("district");
                                            final_id = result.get(i).get("final_id");
                                            maddress.setText(marker_address);
                                            mname.setText(marker_name);
                                            mname.setVisibility(View.VISIBLE);
                                            maddress.setVisibility(View.VISIBLE);
                                        }
                                    }
                              //  FrameLayout.setVisibility(View.VISIBLE);
                                curr = "unReg";
                                status = "UnRegistered";
                                mTextMessage.setText(status);
                                mTextMessage.setTextColor(BLUE);

                            }
                            else if (arg0.getSnippet().equals("Quack")) {
                                for (int i = 0; i < result.size(); i++) {
                                    double _latitude = Double.parseDouble(result.get(i).get("lat"));
                                    double _longitude = Double.parseDouble(result.get(i).get("lng"));
                                    LatLng latLng = new LatLng(_latitude, _longitude);
                                    if (arg0.getPosition().equals(latLng)) {

                                        marker_latitude = _latitude;
                                        marker_longitude = _longitude;
                                        marker_address = result.get(i).get("Address");
                                        marker_name = result.get(i).get("Name");
                                        mobile_number = result.get(i).get("hce_mobile");
                                        marker_status = "Quack";
                                        district = result.get(i).get("district");
                                        final_id = result.get(i).get("final_id");
                                        maddress.setText(marker_address);
                                        mname.setText(marker_name);
                                        mname.setVisibility(View.VISIBLE);
                                        maddress.setVisibility(View.VISIBLE);
                                    }
                                }
                              //  FrameLayout.setVisibility(View.VISIBLE);
                                curr = "Quack";
                                status = "Quack";
                                mTextMessage.setText(status);
                                mTextMessage.setTextColor(BLUE);

                            }
                            return true;
                        }

                    });

                }
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (curr != null) {
                            if (curr.equals("curent")) {
                                Toast.makeText(getApplicationContext(), "Please Select HCE", Toast.LENGTH_SHORT).show();
                            } else {
                                ArrayList<CarParcelable> carParcelableList = new ArrayList<CarParcelable>();


                                    carParcelableList.add(new CarParcelable(cur_latitude,
                                            cur_longitude, "Current Location", cityName));


                                Intent firstpage = new Intent(context, RouteMapsActivity.class);
                                firstpage.putParcelableArrayListExtra("carList", carParcelableList);
                                firstpage.putExtra("des_latitude", marker_latitude);
                                firstpage.putExtra("des_longitude", marker_longitude);
                                firstpage.putExtra("status", status);
                                firstpage.putExtra("markerstatus", marker_status);
                                firstpage.putExtra("name", marker_name);
                                firstpage.putExtra("address", marker_address);
                                firstpage.putExtra("mobile", mobile_number);
                                context.startActivity(firstpage);

                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Please Select HCE", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                call_button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (curr != null) {
                            if (curr.equals("curent")) {
                                Toast.makeText(getApplicationContext(), "Please Select HCE", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getApplicationContext(), "Please Select HCE", Toast.LENGTH_SHORT).show();
                        }

                    }

                });
                filterButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent firstpage = new Intent(context, FilterActivity.class);
                        firstpage.putExtra("email", email);
                        context.startActivity(firstpage);

                    }
                });
                comments.setOnClickListener(new View.OnClickListener() {


                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {

                       /* Mac_Address mac_address=new Mac_Address();
                        String macAddress=mac_address.getMacAddress(context);
                        Toast.makeText(getApplicationContext(), "Mac Address"+macAddress, Toast.LENGTH_SHORT).show();*/
                        if (curr != null) {
                            if (curr.equals("curent")) {
                                Toast.makeText(getApplicationContext(), "Please Select HCE", Toast.LENGTH_SHORT).show();
                            } else {

                               // DownloadHCEDetail hceDetailActivity=new DownloadHCEDetail(context,final_id,email,);
                            /*    Intent firstpage = new Intent(context, ReportQuackActivity.class);

                                firstpage.putExtra("final_id", final_id);
                                firstpage.putExtra("des_latitude", marker_latitude);
                                firstpage.putExtra("des_longitude", marker_longitude);
                                firstpage.putExtra("status", status);
                                firstpage.putExtra("markerstatus", marker_status);
                                firstpage.putExtra("name", marker_name);
                                firstpage.putExtra("address", marker_address);
                                firstpage.putExtra("mobile", mobile_number);
                                firstpage.putExtra("district", district);
                                context.startActivity(firstpage);*/
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Please Select HCE", Toast.LENGTH_SHORT).show();
                        }

                    }

                });
            }
            else {
                Toast.makeText(getApplicationContext(), "Network eror", Toast.LENGTH_SHORT).show();
            }
        }

    }
//-------------------------------------Tabs View-------------------------------------------->>>>>>>>>>>>>
  /*  private void setupViewPager(ViewPager viewPager) {
        MapsActivity.ViewPagerAdapter adapter = new MapsActivity.ViewPagerAdapter(getSupportFragmentManager());
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
}