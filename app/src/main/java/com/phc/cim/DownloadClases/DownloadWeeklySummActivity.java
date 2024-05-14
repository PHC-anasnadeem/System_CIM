package com.phc.cim.DownloadClases;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.phc.cim.R;
import com.phc.cim.TabsActivities.HigherPlanSumTab;

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

public class DownloadWeeklySummActivity {
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
    String email,  password,  username,  isEdit,  PlanTitle, PlanStartDate,  PlanEndDate,  FunctionalSealed,  CloseSealed,  NotSealed;
    public DownloadWeeklySummActivity(Context context,String email, String password, String username, String isEdit, String PlanTitle, String PlanStartDate, String PlanEndDate, String FunctionalSealed, String CloseSealed, String NotSealed) {


        this.context = context;
        this.email = email;
        this.password = password;
        this.username = username;
        this.isEdit = isEdit;
        this.PlanTitle = PlanTitle;
        this.PlanStartDate=PlanStartDate;
        this.PlanEndDate=PlanEndDate;
        this.FunctionalSealed=FunctionalSealed;
        this.CloseSealed=CloseSealed;
        this.NotSealed=NotSealed;
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
        url = baseurl + "GetAllStatForPlanTitle?strToken="+token+"&PlanTitle="+PlanTitle;

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

                        //map.put("CategoryDesc", e.getString("CategoryDesc"));
                        map.put("ID", e.getString("ID"));
                        map.put("PKID", e.getString("SType"));
                        map.put("TotalSealed", e.getString("Total"));
                        map.put("CategoryDesc", e.getString("TypeDesc"));
                       map.put("VisitDate", e.getString("VisitDate"));
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
            if (result != null && result.size() > 0) {

                Intent firstpage = new Intent(context, HigherPlanSumTab.class);
                firstpage.putExtra("email", email);
                firstpage.putExtra("Password", password);
                firstpage.putExtra("username", username);
                firstpage.putExtra("isEdit", isEdit);
                firstpage.putExtra("PlanTitle", PlanTitle);
                firstpage.putExtra("PlanStartDate", PlanStartDate);
                firstpage.putExtra("PlanEndDate", PlanEndDate);
                firstpage.putExtra("FunctionalSealed",FunctionalSealed);
                firstpage.putExtra("CloseSealed",CloseSealed);
                firstpage.putExtra("NotSealed",NotSealed);
                firstpage.putExtra("result",result);
                context.startActivity(firstpage);
            } else {
                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();

            }
        }

    }
}