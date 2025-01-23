package com.phc.cim.DownloadClases;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.phc.cim.R;
import com.phc.cim.TabsActivities.SummMapListTabs;

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

import static android.content.Context.MODE_PRIVATE;

public class DownloadQuackClusterActivity {
    Context context;
    ArrayList<HashMap<String, String>> mylist;
    ArrayList<HashMap<String, String>> mylist2;
    ArrayList<String> imagespath;
    String jsonStr = null;
    String jsonStr2 = null;
    String final_id;
    ProgressDialog pDialog;
    String ActionType;
    String Comments;
    String Council;
    String CouncilNo;
    String Date_Time;
    String FinalID;
    String ImageUploadMode;
    String Image_Path;
    String isFIRSubmit;
    String PHCRegistrationNo;
    String PKID;
    String PicCount;
    String SubActionType;
    String UploadedBy;
    String VisitedDate;
    String isDeleted;
    String visited = "1";
    double Lat;
    double Lng;
    String RoleID;
    int count = 0;
    String RecordLockedForUpdate;
    String sectortypetext, counciltypetext, hceTypetext, hcestatusID, districtText, tehsilText, distancetext, BfromText, BtoText, lastvisitedID, RegnoText, hcenameText, email, password, username, isEdit, finalidText, actionText,subactionTypeID,Cnic,Phone;
    public DownloadQuackClusterActivity(Context context,String sectortypetext,String counciltypetext,String hceTypetext,String hcestatusID,String districtText,String tehsilText,String distancetext,String BfromText,String BtoText,String lastvisitedID,String subactionTypeID,String RegnoText,String hcenameText,String email,String password,String username,String isEdit,String finalidText,String actionText,String Cnic, String Phone) {


                this.context = context;
                this.email = email;
                this.password = password;
                this.username = username;
                this.isEdit = isEdit;
                this.sectortypetext=sectortypetext;
                this.counciltypetext=counciltypetext;
                this.hceTypetext=hceTypetext;
                this.hcestatusID=hcestatusID;
                this.districtText=districtText;
                this.tehsilText=tehsilText;
                this.distancetext=distancetext;
                this.BfromText=BfromText;
                this.BtoText=BtoText;
                this.lastvisitedID=lastvisitedID;
                this.subactionTypeID=subactionTypeID;
                this.RegnoText=RegnoText;
                this.hcenameText=hcenameText;
                this.finalidText=finalidText;
                this.actionText=actionText;
                this.Cnic=Cnic;
                this.Phone=Phone;

        SharedPreferences prefs = context.getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String isStat = prefs.getString("isStat", null);//"No name defined" is the default value.
        RoleID = prefs.getString("RoleID", null); //0 is the default value.
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading Data, Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        String url = getDirectionsUrl();
        DownloadTask downloadTask = new DownloadTask();
        //Start downloading json data from Google Directions API
        downloadTask.execute(url);
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
        if(hcestatusID.equals("5")){
            url = baseurl + "GetAllQuackClusters?strToken="+token+"&RegistrationStatusID="+hcestatusID+"&District="+districtText+"&Tehsil="+tehsilText+"&Distance=0&ClusterType=0";

        }
        else if(hcestatusID.equals("4")){
            url = baseurl + "GetAllUnRegClusters?strToken="+token+"&District="+districtText+"&Tehsil="+tehsilText;
        }

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
                        if(hcestatusID.equals("5")) {
                            map.put("ClusterNo", String.valueOf(i + 1));
                            map.put("Closed_sealed", e.getString("Closed_sealed"));
                            map.put("FinalID", e.getString("FinalID"));
                            map.put("Not_visited", e.getString("Not_visited"));
                            map.put("Sealing_not_req", e.getString("Sealing_not_req"));
                            map.put("Total_Visited", e.getString("Total_Visited"));
                            map.put("functional_sealed", e.getString("functional_sealed"));
                            map.put("lat", e.getString("lat"));
                            map.put("lng", e.getString("lng"));
                            map.put("total_quacks", e.getString("total_quacks"));
                            map.put("Radius", e.getString("Radius"));
                        }
                        else  if(hcestatusID.equals("4")){
                            map.put("ClusterNo", String.valueOf(i + 1));
                            map.put("Found_Ligitimate", e.getString("Found_Ligitimate"));
                            map.put("FinalID", e.getString("FinalID"));
                            map.put("Not_visited", e.getString("Not_visited"));
                            map.put("Other", e.getString("Other"));
                            map.put("Total_Visited", e.getString("Total_Visited"));
                            map.put("Registration_not_required", e.getString("Registration_not_required"));
                            map.put("Sealing_not_req", e.getString("Sealing_not_req"));
                            map.put("lat", e.getString("lat"));
                            map.put("lng", e.getString("lng"));
                            map.put("total_unregistered", e.getString("total_unregistered"));
                            map.put("Radius", e.getString("Radius"));
                        }
                        mylist.add(map);
                    }


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
            if (result != null && result.size() > 0) {

                Intent firstpage = new Intent(context, SummMapListTabs.class);
                firstpage.putExtra("dataType", sectortypetext);
                firstpage.putExtra("registrationType", counciltypetext);
                firstpage.putExtra("orgType", hceTypetext);
                firstpage.putExtra("hcestatus", hcestatusID);
                firstpage.putExtra("districtText", districtText);
                firstpage.putExtra("tehsilText", tehsilText);
                firstpage.putExtra("distancetext", distancetext);
                firstpage.putExtra("BfromText", BfromText);
                firstpage.putExtra("BtoText", BtoText);
                firstpage.putExtra("lastvisitedText", lastvisitedID);
                firstpage.putExtra("subactionTypeID", subactionTypeID);
                firstpage.putExtra("RegnoText", RegnoText);
                firstpage.putExtra("hcenameText", hcenameText);
                firstpage.putExtra("email", email);
                firstpage.putExtra("Password", password);
                firstpage.putExtra("username", username);
                firstpage.putExtra("isEdit", isEdit);
                firstpage.putExtra("finalidText", finalidText);
                firstpage.putExtra("QuackType", actionText);
                firstpage.putExtra("result",result);
                firstpage.putExtra("Cnic",Cnic);
                firstpage.putExtra("Phone",Phone);
                context.startActivity(firstpage);
            } else {
                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();

            }
        }

    }
}