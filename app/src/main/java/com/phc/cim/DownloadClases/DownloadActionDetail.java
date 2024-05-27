package com.phc.cim.DownloadClases;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.phc.cim.Activities.Aqc.ActionActivity;
import com.phc.cim.Activities.Licensing.LicensingActionActivity;
import com.phc.cim.R;
import com.phc.cim.TabsActivities.DetailTabActivity;

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

public class DownloadActionDetail {
    Context context;
    ArrayList<HashMap<String, String>> mylist;
    ArrayList<String> mylist2;
    ArrayList<String> imagespath;
    String jsonStr = null;
    String jsonStr2 = null;
    String final_id;
    String email=null;
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
    String  PHCRegistrationNo;
    String PKID;
    String PicCount;
    String PlanID;
    String SubActionType;
    String UploadedBy;
    String VisitedDate;
    String isDeleted;
    String visited="1";
    String VisitedTime;
    double Lat;
    double Lng;
    int count=0;
    String RecordLockedForUpdate;
    String UserName,LastVisitedDate, HCEName, HCEAddress, District, SectorType, OrgType, HCSPType, HCSPName, HCSPSO ,HCSPCNIC ,HCSPContactNo ,RegStatus ,RegNum ,CouncilStatus ,CouncilName ,CouncilNum,RegType,index,total_beds;
       double latitude,longitude;
    public DownloadActionDetail(Context context, String HCEName, String final_id, String email, String password, String username, String isEdit , String index, int count){

        this.count=count;
        this.context=context;
        this.HCEName=HCEName;
        this.final_id=final_id;
        this.email=email;
        this.password=password;
        this.username=username;
        this.isEdit=isEdit;
        this.index=index;
//        this.MarkSurvCount= MarkSurvCount;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading Data, Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        String url = getDirectionsUrl(final_id);
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

    private String getDirectionsUrl(String final_id) {

        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String url =null;
        if(count==1) {
            url = baseurl + "GetAllUploadedActions?finalId=" + final_id;
        }
        else  {
            url = baseurl + "GetHCEDetail?ID=" + final_id;
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
// ...
                    mylist = new ArrayList<HashMap<String, String>>();
                    for (int i = 0; i < json.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = json.getJSONObject(i);
                        if(count==1) {
                            map.put("ActionType", e.getString("ActionType"));
                            map.put("Comments", e.getString("Comments"));
                            map.put("Council", e.getString("Council"));
                            map.put("CouncilNo", e.getString("CouncilNo"));
                            map.put("Date_Time", e.getString("Date_Time"));
                            map.put("FinalID", e.getString("FinalID"));
                            map.put("ImageUploadMode", e.getString("ImageUploadMode"));
                            map.put("Image_Path", e.getString("Image_Path"));
                            map.put("Lat", e.getString("Lat"));
                            map.put("Lng", e.getString("Lng"));
                            map.put("PHCRegistrationNo", e.getString("PHCRegistrationNo"));
                            map.put("PKID", e.getString("PKID"));
                            map.put("PicCount", e.getString("PicCount"));
                            map.put("PlanID", e.getString("PlanID"));
                            map.put("SubActionType", e.getString("SubActionType"));
                            map.put("UploadedBy", e.getString("UserName"));
                            map.put("VisitedDate", e.getString("VisitedDate"));
                            map.put("isDeleted", e.getString("isDeleted"));
                            map.put("isFIRSubmit", e.getString("isFIRSubmit"));
                            map.put("VisitedTime", e.getString("VisitedTime"));

                        }
                        else {
                            map.put("HCEName", e.getString("HCEName"));
                            map.put("HCEAddress", e.getString("HCEAddress"));
                            map.put("District", e.getString("District"));
                            map.put("SectorType", e.getString("SectorType"));
                            map.put("OrgType", e.getString("OrgType"));
                            map.put("HCSPType", e.getString("HCSPType"));
                            map.put("HCSPName", e.getString("HCSPName"));
                            map.put("HCSPSO", e.getString("HCSPSO"));
                            map.put("HCSPCNIC", e.getString("HCSPCNIC"));
                            map.put("HCSPContactNo", e.getString("HCSPContactNo"));
                            map.put("RegStatus", e.getString("RegStatus"));
                            map.put("RegNum", e.getString("RegNum"));
                            map.put("RegType", e.getString("RegType"));
                            map.put("RecordLockedForUpdate", e.getString("RecordLockedForUpdate"));
                            map.put("CouncilStatus", e.getString("CouncilStatus"));
                            map.put("CouncilName", e.getString("CouncilName"));
                            map.put("CouncilNum", e.getString("CouncilNum"));
                            map.put("total_beds", e.getString("total_beds"));
                            map.put("lat", e.getString("lat"));
                            map.put("lng", e.getString("lng"));
                            map.put("LastVisitedDate", e.getString("LastVisitedDate"));
                            map.put("UserName", e.getString("UserName"));
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
            if(result!=null && result.size()>0) {

                if(count==1){
                    for (int i = 0; i < 1; i++) {

                        ActionType = result.get(i).get("ActionType");
                        Comments = result.get(i).get("Comments");
                        Council = result.get(i).get("Council");
                        CouncilNo = result.get(i).get("CouncilNo");
                        Date_Time = result.get(i).get("Date_Time");
                        FinalID = result.get(i).get("FinalID");
                        ImageUploadMode = result.get(i).get("ImageUploadMode");
                        Image_Path = result.get(i).get("Image_Path");
                        isFIRSubmit = result.get(i).get("isFIRSubmit");
                        PHCRegistrationNo = result.get(i).get("PHCRegistrationNo");
                        PKID = result.get(i).get("PKID");
                        PicCount = result.get(i).get("PicCount");
                        PlanID = result.get(i).get("PlanID");
                        SubActionType = result.get(i).get("SubActionType");
                        UploadedBy = result.get(i).get("UploadedBy");
                        VisitedDate = result.get(i).get("VisitedDate");
                        VisitedTime= result.get(i).get("VisitedTime");
                        isDeleted = result.get(i).get("isDeleted");
                        Lat = Double.parseDouble(result.get(i).get("Lat"));
                        Lng = Double.parseDouble(result.get(i).get("Lng"));
                    }

                }
              else {
                    for (int i = 0; i < result.size(); i++) {

                        HCEName = result.get(i).get("HCEName");
                        HCEAddress = result.get(i).get("HCEAddress");
                        District = result.get(i).get("District");
                        SectorType = result.get(i).get("SectorType");
                        OrgType = result.get(i).get("OrgType");
                        HCSPType = result.get(i).get("HCSPType");
                        HCSPName = result.get(i).get("HCSPName");
                        HCSPSO = result.get(i).get("HCSPSO");
                        HCSPCNIC = result.get(i).get("HCSPCNIC");
                        HCSPContactNo = result.get(i).get("HCSPContactNo");
                        RegStatus = result.get(i).get("RegStatus");
                        RegNum = result.get(i).get("RegNum");
                        RegType = result.get(i).get("RegType");
                        total_beds = result.get(i).get("total_beds");
                        RecordLockedForUpdate = result.get(i).get("RecordLockedForUpdate");
                        CouncilStatus = result.get(i).get("CouncilStatus");
                        CouncilName = result.get(i).get("CouncilName");
                        CouncilNum = result.get(i).get("CouncilNum");
                        LastVisitedDate = result.get(i).get("LastVisitedDate");
                        UserName = result.get(i).get("UserName");
                        latitude = Double.parseDouble(result.get(i).get("lat"));
                        longitude = Double.parseDouble(result.get(i).get("lng"));

                    }
                }

                String url = getDirectionsUrl2(final_id);
                DownloadTask2 downloadTask = new DownloadTask2();
                //Start downloading json data from Google Directions API
                downloadTask.execute(url);

            }
            else {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                String url = getDirectionsUrl2(final_id);
                DownloadTask2 downloadTask = new DownloadTask2();
                //Start downloading json data from Google Directions API
                downloadTask.execute(url);
               // Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();

            }
        }

    }
    private class DownloadTask2 extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl2(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask2 parserTask = new ParserTask2();
            jsonStr2 = result;
            // Invokes the thread for parsing the JSON data
            parserTask.execute();

        }
    }

    private String getDirectionsUrl2(String final_id) {

        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String url = null;
        url = baseurl + "GetUploadImage?finalId=" + final_id;
        url = url.replaceAll(" ", "%20");

        return url;
    }

    private String downloadUrl2(String strUrl) throws IOException {


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

    private class ParserTask2 extends AsyncTask<Object, Object, ArrayList<String>>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog


        }

        @Override
        protected ArrayList<String> doInBackground(Object... arg0) {

            // Making a request to url and getting response

            if (jsonStr2 != null) {
                try {
                    JSONArray json = new JSONArray(jsonStr2);
// ...
                    mylist2 = new ArrayList<String>();
                    for (int i = 0; i < json.length(); i++) {
                        //ArrayList<String> map = new ArrayList<String>();
                        JSONObject e = json.getJSONObject(i);

                        //   map.put("FinalID", e.getString("FinalID"));
                        //  map.add(e.getString("ImagePath"));
                        // map.put("ImagePath", e.getString("ImagePath"));

                        mylist2.add(e.getString("ImagePath"));
                    }

                    // adding contact to contact list
                    //contactList.add(contact);

                } catch (final JSONException e) {

                    e.printStackTrace();


                }
            } else {
                Log.e("exception", "Couldn't get json from server.");
            }

            return mylist2;
        }

        @Override
        protected void onPostExecute(final ArrayList<String> result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (result != null) {

                imagespath=result;
            }
            SharedPreferences prefs = context.getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
            String RoleID = prefs.getString("RoleID", null); //0 is the default value.
                    //FinalID = result.get(i).get("FinalID");
                    //ImagePath = result.get(i).get("ImagePath");
            if(count==3) {


                if(RoleID.equals("1")) {
                    Intent firstpage = new Intent(context, ActionActivity.class);
                    firstpage.putExtra("HCEName", HCEName);
                    firstpage.putExtra("HCEAddress", HCEAddress);
                    firstpage.putExtra("District", District);
                    firstpage.putExtra("SectorType", SectorType);
                    firstpage.putExtra("OrgType", OrgType);
                    firstpage.putExtra("HCSPType", HCSPType);
                    firstpage.putExtra("HCSPName", HCSPName);
                    firstpage.putExtra("HCSPSO", HCSPSO);
                    firstpage.putExtra("HCSPCNIC", HCSPCNIC);
                    firstpage.putExtra("HCSPContactNo", HCSPContactNo);
                    firstpage.putExtra("RegStatus", RegStatus);
                    firstpage.putExtra("RegNum", RegNum);
                    firstpage.putExtra("RegType", RegType);
                    firstpage.putExtra("total_beds", total_beds);
                    firstpage.putExtra("RecordLockedForUpdate", RecordLockedForUpdate);
                    firstpage.putExtra("CouncilStatus", CouncilStatus);
                    firstpage.putExtra("CouncilName", CouncilName);
                    firstpage.putExtra("CouncilNum", CouncilNum);
                    firstpage.putExtra("final_id", final_id);
                    firstpage.putExtra("latitude", latitude);
                    firstpage.putExtra("longitude", longitude);
                    firstpage.putExtra("email", email);
                    firstpage.putExtra("Password", password);
                    firstpage.putExtra("username", username);
                    firstpage.putExtra("isEdit", isEdit);
                    firstpage.putExtra("index", index);
//                    firstpage.putExtra("MarkSurvCount", MarkSurvCount);
                    firstpage.putStringArrayListExtra("imageurls", imagespath);
                    context.startActivity(firstpage);
                }
                else if(RoleID.equals("2")) {
                    Intent firstpage = new Intent(context, LicensingActionActivity.class);
                    firstpage.putExtra("HCEName", HCEName);
                    firstpage.putExtra("HCEAddress", HCEAddress);
                    firstpage.putExtra("District", District);
                    firstpage.putExtra("SectorType", SectorType);
                    firstpage.putExtra("OrgType", OrgType);
                    firstpage.putExtra("HCSPType", HCSPType);
                    firstpage.putExtra("HCSPName", HCSPName);
                    firstpage.putExtra("HCSPSO", HCSPSO);
                    firstpage.putExtra("HCSPCNIC", HCSPCNIC);
                    firstpage.putExtra("HCSPContactNo", HCSPContactNo);
                    firstpage.putExtra("RegStatus", RegStatus);
                    firstpage.putExtra("RegNum", RegNum);
                    firstpage.putExtra("RegType", RegType);
                    firstpage.putExtra("total_beds", total_beds);
                    firstpage.putExtra("RecordLockedForUpdate", RecordLockedForUpdate);
                    firstpage.putExtra("CouncilStatus", CouncilStatus);
                    firstpage.putExtra("CouncilName", CouncilName);
                    firstpage.putExtra("CouncilNum", CouncilNum);
                    firstpage.putExtra("final_id", final_id);
                    firstpage.putExtra("latitude", latitude);
                    firstpage.putExtra("longitude", longitude);
                    firstpage.putExtra("email", email);
                    firstpage.putExtra("Password", password);
                    firstpage.putExtra("username", username);
                    firstpage.putExtra("isEdit", isEdit);
                    firstpage.putExtra("index", index);
//                    firstpage.putExtra("MarkSurvCount", MarkSurvCount);
                    firstpage.putStringArrayListExtra("imageurls", imagespath);
                    context.startActivity(firstpage);
                }
            }
                    if(count==1) {
                        if(RoleID.equals("1")) {
                            Intent firstpage = new Intent(context, ActionActivity.class);
                            firstpage.putExtra("HCEName", HCEName);
                            firstpage.putExtra("ActionType", ActionType);
                            firstpage.putExtra("Comments", Comments);
                            //  firstpage.putExtra("Date_Time", Date_Time);
                            firstpage.putExtra("final_id", final_id);
                            //firstpage.putExtra("ImageUploadMode", ImageUploadMode);
                            // firstpage.putExtra("Image_Path", Image_Path);
                            firstpage.putExtra("isFIRSubmit", isFIRSubmit);
                            //  firstpage.putExtra("PKID", PKID);
                            firstpage.putExtra("RegNum", PHCRegistrationNo);
                            // firstpage.putExtra("RegType", RegType);
                            //  firstpage.putExtra("PicCount", PicCount);
                            //  firstpage.putExtra("PlanID", PlanID);
                            firstpage.putExtra("SubActionType", SubActionType);
                            firstpage.putExtra("CouncilName", Council);
                            firstpage.putExtra("CouncilNum", CouncilNo);
                            firstpage.putExtra("UploadedBy", UploadedBy);
                            firstpage.putExtra("VisitedDate", VisitedDate);
                            firstpage.putExtra("VisitedTime", VisitedTime);
                            //  firstpage.putExtra("isDeleted", isDeleted);
                            // firstpage.putExtra("latitude", Lat);
                            // firstpage.putExtra("longitude", Lng);
                            firstpage.putExtra("visited", visited);
                            firstpage.putExtra("email", email);
                            firstpage.putExtra("Password", password);
                            firstpage.putExtra("username", username);
                            firstpage.putExtra("isEdit", isEdit);
                            firstpage.putExtra("index", index);
//                            firstpage.putExtra("MarkSurvCount", MarkSurvCount);
                            firstpage.putStringArrayListExtra("imageurls", imagespath);
                            context.startActivity(firstpage);
                        }
                        else if(RoleID.equals("2")) {
                            Intent firstpage = new Intent(context, LicensingActionActivity.class);
                            firstpage.putExtra("HCEName", HCEName);
                            firstpage.putExtra("ActionType", ActionType);
                            firstpage.putExtra("Comments", Comments);
                            //  firstpage.putExtra("Date_Time", Date_Time);
                            firstpage.putExtra("final_id", final_id);
                            //firstpage.putExtra("ImageUploadMode", ImageUploadMode);
                            // firstpage.putExtra("Image_Path", Image_Path);
                            firstpage.putExtra("isFIRSubmit", isFIRSubmit);
                            //  firstpage.putExtra("PKID", PKID);
                            firstpage.putExtra("RegNum", PHCRegistrationNo);
                            // firstpage.putExtra("RegType", RegType);
                            //  firstpage.putExtra("PicCount", PicCount);
                            //  firstpage.putExtra("PlanID", PlanID);
                            firstpage.putExtra("SubActionType", SubActionType);
                            firstpage.putExtra("CouncilName", Council);
                            firstpage.putExtra("CouncilNum", CouncilNo);
                            firstpage.putExtra("UploadedBy", UploadedBy);
                            firstpage.putExtra("VisitedDate", VisitedDate);
                            firstpage.putExtra("VisitedTime", VisitedTime);

                            //  firstpage.putExtra("isDeleted", isDeleted);
                            // firstpage.putExtra("latitude", Lat);
                            // firstpage.putExtra("longitude", Lng);
                            firstpage.putExtra("visited", visited);
                            firstpage.putExtra("email", email);
                            firstpage.putExtra("Password", password);
                            firstpage.putExtra("username", username);
                            firstpage.putExtra("isEdit", isEdit);
                            firstpage.putExtra("index", index);
                            firstpage.putStringArrayListExtra("imageurls", imagespath);
                            context.startActivity(firstpage);

                        }
                    }
                    if(count==2) {
                        Intent firstpage = new Intent(context, DetailTabActivity.class);
                        firstpage.putExtra("HCEName", HCEName);
                        firstpage.putExtra("HCEAddress", HCEAddress);
                        firstpage.putExtra("District", District);
                        firstpage.putExtra("SectorType", SectorType);
                        firstpage.putExtra("OrgType", OrgType);
                        firstpage.putExtra("HCSPType", HCSPType);
                        firstpage.putExtra("HCSPName", HCSPName);
                        firstpage.putExtra("HCSPSO", HCSPSO);
                        firstpage.putExtra("HCSPCNIC", HCSPCNIC);
                        firstpage.putExtra("HCSPContactNo", HCSPContactNo);
                        firstpage.putExtra("RegStatus", RegStatus);
                        firstpage.putExtra("RegNum", RegNum);
                        firstpage.putExtra("RegType", RegType);
                        firstpage.putExtra("total_beds", total_beds);
                        firstpage.putExtra("RecordLockedForUpdate", RecordLockedForUpdate);
                        firstpage.putExtra("CouncilStatus", CouncilStatus);
                        firstpage.putExtra("CouncilName", CouncilName);
                        firstpage.putExtra("CouncilNum", CouncilNum);
                        firstpage.putExtra("final_id", final_id);
                        firstpage.putExtra("latitude", latitude);
                        firstpage.putExtra("longitude", longitude);
                        firstpage.putExtra("email", email);
                        firstpage.putExtra("Password", password);
                        firstpage.putExtra("username", username);
                        firstpage.putExtra("isEdit", isEdit);
                        firstpage.putExtra("index", index);
                        firstpage.putExtra("UserName", UserName);
                        firstpage.putExtra("LastVisitedDate", LastVisitedDate);
//                        firstpage.putExtra("MarkSurvCount", MarkSurvCount);
                        firstpage.putStringArrayListExtra("imageurls", imagespath);
                        context.startActivity(firstpage);
                    }
        }
    }
}