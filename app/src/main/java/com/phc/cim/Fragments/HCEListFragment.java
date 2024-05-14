package com.phc.cim.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.phc.cim.Adapters.HCEListAdapter;
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



public class HCEListFragment extends Fragment {
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
    String finalidText;
    String Cnic;
    String Phone;
    String dataType;
    String registrationType;
    String orgType;
    String REGfilterstatus;
    String districtText;
    String TehsilText;
    String distancetext;
    String subactionTypeID;
    String BfromText="";
    String BtoText="";
    String email=null;
    String password;
    String isEdit;
    String username;
    String lastvisitedText;
    String RegnoText;
    String hcenameText;
    String QuackType;
    BitmapDescriptor Reg_icon;
    BitmapDescriptor PL_icon;
    BitmapDescriptor unReg_icon;
    BitmapDescriptor quack_icon;
    int count=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_hce_list, container, false);

        pDialog = new ProgressDialog(getActivity());

        //context=this;
        listView = (ListView) rootView.findViewById(R.id.list);
        gps = new CurrentLocation(getContext());
        final Bundle args = getArguments();
        dataType= args.getString("dataType");
        registrationType= args.getString("registrationType");
        orgType= (String) args.getString("orgType");
        REGfilterstatus= args.getString("hcestatus");
        districtText= args.getString("districtText");
        TehsilText= args.getString("tehsilText");
        subactionTypeID= args.getString("subactionTypeID");
        distancetext= args.getString("distancetext");
        BfromText= args.getString("BfromText");
        BtoText= args.getString("BtoText");
        lastvisitedText = args.getString("lastvisitedText");
        RegnoText = args.getString("RegnoText");
        hcenameText = args.getString("hcenameText");
        email= args.getString("email");
        password= args.getString("password");
        username= args.getString("username");
        isEdit= args.getString("isEdit");
        finalidText= args.getString("finalidText");
        QuackType= args.getString("QuackType");
        Cnic= args.getString("Cnic");
        Phone= args.getString("Phone");
        if (gps.canGetLocation()) {
         /*   pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();*/
            cur_latitude = gps.getLatitude();
            cur_longitude = gps.getLongitude();
            // latlangListener.onlatlang(cur_latitude, cur_longitude);
            if (cur_latitude != 0.0 && cur_longitude != 0.0) {
                pDialog.setMessage("Loading Data, Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
                String url = getDirectionsUrl();
                DownloadTask downloadTask = new DownloadTask();
                //Start downloading json data from Google Directions API
                downloadTask.execute(url);
            }
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
        return rootView;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gps = new CurrentLocation(getContext());
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
                    Toast.makeText(getContext(), "You need to grant permission", Toast.LENGTH_SHORT).show();
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
        String baseurl = getContext().getResources().getString(R.string.baseurl);

        String token= getContext().getResources().getString(R.string.token);

        url = baseurl + "GetHCEs?strToken="+token+"&District=" + districtText + "&Tehsil=" + TehsilText + "&DataType=" + dataType + "&orgType=" + orgType + "&Councile=" + registrationType + "&Status=" + REGfilterstatus + "&Category=&From=" + BfromText + "&To=" + BtoText+"&Lvs=&RegNum="+RegnoText+"&HCEName="+hcenameText+"&Latitude="+cur_latitude+"&Longitude="+cur_longitude+"&Distance="+distancetext+"&finalid="+finalidText+"&ActionType="+lastvisitedText+"&QuackCategory="+QuackType+"&QuackSubCategory=&SubActionType="+subactionTypeID+"&Cnic="+Cnic+"&Phone="+Phone;
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
                    for (int i = 0; i <json.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = json.getJSONObject(i);


                        map.put("index", String.valueOf(i+1));
                        map.put("latitude", e.getString("lat"));
                        map.put("longitude", e.getString("lng"));
                        map.put("address", e.getString("Address"));
                        map.put("name", e.getString("Name"));
                        map.put("status", e.getString("PhcLicenseType"));
                        map.put("MobileNumber", e.getString("ContactNo"));
                        map.put("RegType", e.getString("RegType"));
                        map.put("final_id", e.getString("final_id"));
                        map.put("is_reg_council", e.getString("is_reg_council"));
                        map.put("total_beds", e.getString("total_beds"));
                        map.put("councils", e.getString("councils"));
                        map.put("HCE_Cat_Type", e.getString("HCE_Cat_Type"));
                        map.put("sector_type", e.getString("sector_type"));
                        map.put("is_reg_with_phc", e.getString("is_reg_with_phc"));
                        map.put("ActionType", e.getString("ActionType"));
                        map.put("VisitStatus", e.getString("VisitStatus"));
                        map.put("QuackCategory", e.getString("QuackCategory"));
                        map.put("hcsp_name", e.getString("hcsp_name"));
                        map.put("hcsp_sodowo", e.getString("hcsp_sodowo"));
                        map.put("RoleID", e.getString("RoleID"));
                        map.put("district", e.getString("district"));
                        map.put("hcsp_cnic", e.getString("hcsp_cnic"));   //Add here
                        map.put("hcsp_phone", e.getString("hcsp_phone"));



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
            if (count == 0) {
                count = 1;
            }


            if (result != null) {
                if (result.size() > 0) {


                    HCEListAdapter adapter = new HCEListAdapter(getContext(), result, email, password, username, isEdit);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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


                } else {
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                }
            }
            else {
             //   Toast.makeText(getContext(), "Internet Connection Error: Please try again.", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
