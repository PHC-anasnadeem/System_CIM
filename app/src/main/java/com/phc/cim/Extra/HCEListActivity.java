package com.phc.cim.Extra;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;


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

public class HCEListActivity extends AppCompatActivity {

    String StudentID;
    CurrentLocation gps;
    Context context;
    String jsonStr = null;
    ListView listView;
    ArrayList<HashMap<String, String>> contactList;
    private ArrayList<CarParcelable> mCarParcelableList;
    private ArrayList<CarParcelable> mCarParcelableListCurrentLation;
    private ArrayList<CarParcelable> mCarParcelableListLastLocation;
    ArrayList<HashMap<String, String>> mylist;
    private Intent mServiceIntent;
    ProgressDialog pDialog;
    double cur_latitude;
    double cur_longitude;

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

    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hce_list);
        pDialog = new ProgressDialog(this);

        context=this;
        listView = (ListView) findViewById(R.id.list);
        gps = new CurrentLocation(this);
        Intent intent = getIntent();
        dataType= (String) intent.getSerializableExtra("dataType");
        registrationType= (String) intent.getSerializableExtra("registrationType");
        orgType= (String) intent.getSerializableExtra("orgType");
        REGfilterstatus= (String) intent.getSerializableExtra("hcestatus");
        districtText= (String) intent.getSerializableExtra("districtText");
        TehsilText= (String) intent.getSerializableExtra("tehsilText");
        searchbytext= (String) intent.getSerializableExtra("searchbytext");
        distancetext= (String) intent.getSerializableExtra("distancetext");
        BfromText= (String) intent.getSerializableExtra("BfromText");
        BtoText= (String) intent.getSerializableExtra("BtoText");
        email= (String) intent.getSerializableExtra("email");
        if (gps.canGetLocation()) {
            pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();
            cur_latitude = gps.getLatitude();
            cur_longitude = gps.getLongitude();
            // latlangListener.onlatlang(cur_latitude, cur_longitude);
            if (cur_latitude != 0.0 && cur_longitude != 0.0){
                pDialog.setMessage("Please wait!....");
                pDialog.setCancelable(false);
                pDialog.show();
                String url = getDirectionsUrl();
                DownloadTask downloadTask = new DownloadTask();
                //Start downloading json data from Google Directions API
                downloadTask.execute(url);}
            else {
                cur_latitude = gps.getLatitude();
                cur_longitude = gps.getLongitude();
                String url = getDirectionsUrl();
                DownloadTask downloadTask = new DownloadTask();
                //Start downloading json data from Google Directions API
                downloadTask.execute(url);
            }
        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            gps.showSettingsAlert();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

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
/*    private void addMarker() {





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

        if (cur_latitude != 0.0 && cur_longitude != 0.0 && mCarParcelableListCurrentLation != mCarParcelableListLastLocation) {
            // pDialog = new ProgressDialog(this);
            if(count==0){
                pDialog.setMessage("Please wait!....");
                pDialog.setCancelable(false);
                pDialog.show();
            }
            String url = getDirectionsUrl();
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(url);
        }
    }*/


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
        String url=null;
        String baseurl = context.getResources().getString(R.string.baseurl);


       // if(searchbytext.equals("District") ) {
            url = baseurl + "GetHCEs?District=" + districtText + "&Tehsil=" + TehsilText + "&DataType=" + dataType + "&orgType=" + orgType + "&Councile=" + registrationType + "&Status=" + REGfilterstatus + "&Category=&From=" + BfromText + "&To=" + BtoText;
       // }
       // else if(searchbytext.equals("Distance")){
       //     url = baseurl+"SearchHCE?DataType=" + dataType + "&orgType=" + orgType + "&Councile=" + registrationType + "&Latitude=" + cur_latitude + "&Longitude=" + cur_longitude;

      //  }

    /*    if(REGfilterstatus.equals("Yes")) {
         url = "http://202.142.147.36:8098/PHCCensusData.svc/SearchHCE?DataType=" + dataType + "&orgType=" + orgType + "&Councile=" + registrationType + "&Latitude=" + cur_latitude + "&Longitude=" + cur_longitude;
            //url = url.replaceAll(" ", "%20");
        }
        if(REGfilterstatus.equals("No")) {
            url="http://202.142.147.36:8098/PHCCensusData.svc/SearchUnRegHCE?DataType=" + dataType + "&orgType=" + orgType + "&Councile=" + registrationType + "&Latitude=" + cur_latitude + "&Longitude=" + cur_longitude;
        }*/
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


                        map.put("latitude", e.getString("lat"));
                        map.put("longitude", e.getString("lng"));
                        map.put("address", e.getString("Address"));
                        map.put("name", e.getString("Name"));
                        map.put("status", e.getString("PhcLicenseType"));
                        map.put("MobileNumber", e.getString("ContactNo"));
                        map.put("final_id", e.getString("final_id"));


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
                if(count==0){
                    count=1;
                }




            if(result.size()>0) {
                //HCEListAdapter adapter = new HCEListAdapter(context, result , email);
                //listView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();
 /*               for (int i = 0; i < result.size(); i++) {

                    double latitude = Double.parseDouble(result.get(i).get("latitude"));
                    double longitude = Double.parseDouble(result.get(i).get("longitude"));
                    name = result.get(i).get("name");
                    Status_db = result.get(i).get("status");
                    flag= result.get(i).get("flag");
                    unRegName= result.get(i).get("hce_name");
                    unRegAddress= result.get(i).get("postal_address");

                    //id = Float.parseFloat(hces.get(i).getHCE_id());

                    if (Status_db.equals("Reg")) {

                        LatLng latLng = new LatLng(latitude, longitude);

                        mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(Reg_icon).snippet(Status_db));

                    } else if (Status_db.equals("Reg/PL") || Status_db.equals("Reg/PL/RL")) {
                        LatLng latLng = new LatLng(latitude, longitude);

                        mMap.addMarker(new MarkerOptions().position(latLng).title(name).icon(PL_icon).snippet(Status_db));


                    }
                    else if (flag.equals("3")) {
                        LatLng latLng = new LatLng(latitude, longitude);

                        mMap.addMarker(new MarkerOptions().position(latLng).title(unRegName).icon(unReg_icon).snippet("UnRegistered"));


                    }

                }*/



            }
            else {
                Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
            }
        }

    }


}
