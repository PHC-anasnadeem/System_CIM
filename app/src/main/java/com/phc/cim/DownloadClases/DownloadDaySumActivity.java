package com.phc.cim.DownloadClases;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.phc.cim.TabsActivities.DatewiseSummTabActivity;
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

import static android.content.Context.MODE_PRIVATE;

public class DownloadDaySumActivity {
    Context context;
    ArrayList<HashMap<String, String>> mylist;
    ArrayList<HashMap<String, String>> mylist2;
    ArrayList<String> imagespath;
    String jsonStr = null;
    String jsonStr2 = null;
    String final_id;
    String email = null;
    String password;
    String isEdit;
    String username;
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
    String PlanID,CloseSealedID,  NotSealedID  , FunctionalSealedID , index,FunctionalSealed, FunctionalSealedcount, NotSealed, NotSealedcount, CloseSealed, ClosedSealedcount,team,totalvisits,totalfir,startdat,enddate, District,PlanCode,TotalImages,reportlevel;
    double latitude, longitude;
    Long timeInMillis;
    Long timeInMillis2;
    public DownloadDaySumActivity(Context context, String PlanID, String email, String password, String username, String isEdit, String index, String team, String totalvisits, String totalfir, String startdat, String enddate, String District, String PlanCode,Long timeInMillis,Long timeInMillis2, String TotalImages ,String  reportlevel) {


        this.context = context;
        this.PlanID = PlanID;
        this.email = email;
        this.password = password;
        this.username = username;
        this.isEdit = isEdit;
        this.index = index;
        this.team=team;
        this.totalvisits=totalvisits;
        this.totalfir=totalfir;
        this.startdat=startdat;
        this.enddate=enddate;
        this.District=District;
        this.PlanCode=PlanCode;
        this.timeInMillis=timeInMillis;
        this.timeInMillis2=timeInMillis2;
        this.TotalImages=TotalImages;
        this.reportlevel=reportlevel;
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
        url = baseurl + "GetAllSealedCountPlanActionTypeWiseWithDate?strToken=" + token + "&planID=" + PlanID+"&RoleID="+RoleID;

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

                        map.put("CategoryDesc", e.getString("CategoryDesc"));
                        map.put("ID", e.getString("ID"));
                        map.put("PKID", e.getString("PKID"));
                        map.put("TotalSealed", e.getString("TotalSealed"));
                        map.put("TypeDesc", e.getString("TypeDesc"));
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

                    Intent firstpage = new Intent(context, DatewiseSummTabActivity.class);
                    firstpage.putExtra("email", email);
                    firstpage.putExtra("Password", password);
                    firstpage.putExtra("username", username);
                    firstpage.putExtra("isEdit", isEdit);
                    firstpage.putExtra("PlanID",PlanID);
                    firstpage.putExtra("index",index );
                    firstpage.putExtra("team",team);
                    firstpage.putExtra("totalvisits",totalvisits);
                    firstpage.putExtra("totalfir",totalfir );
                    firstpage.putExtra("startdat",startdat);
                    firstpage.putExtra("enddate",enddate);
                    firstpage.putExtra("District",District);
                    firstpage.putExtra("PlanCode",PlanCode);
                    firstpage.putExtra("timeInMillis",timeInMillis);
                    firstpage.putExtra("timeInMillis2",timeInMillis2);
                    firstpage.putExtra("TotalImages",TotalImages);
                    firstpage.putExtra("reportlevel",reportlevel);
                    firstpage.putExtra("result",result);
                    context.startActivity(firstpage);
            } else {
                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();

            }
        }

    }
}