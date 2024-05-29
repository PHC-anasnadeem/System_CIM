package com.phc.cim.DownloadClases;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.phc.cim.Activities.Common.UpdateBasicInfoActivity;
import com.phc.cim.TabsActivities.DetailTabActivity;
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

public class DownloadHCEDetail {
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
    String UserName,LastVisitedDate,MarkSurvCount, HCEName, HCEAddress, District, SectorType, OrgType, HCSPType, HCSPName, HCSPSO ,HCSPCNIC ,HCSPContactNo ,RegStatus ,RegNum ,CouncilStatus ,CouncilName ,CouncilNum,RegType,index,total_beds,RegTypebit;
       double latitude,longitude;
    public DownloadHCEDetail(Context context, String HCEName, String final_id, String email, String password, String username, String isEdit , String index, int count,String RegTypebit){

        this.count=count;
        this.context=context;
        this.HCEName=HCEName;
        this.final_id=final_id;
        this.email=email;
        this.password=password;
        this.username=username;
        this.isEdit=isEdit;
        this.index=index;
        this.RegTypebit=RegTypebit;
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

            url = baseurl + "GetHCEDetail?ID=" + final_id;

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
//                            map.put("MarkSurvCount", e.getString("MarkSurvCount"));

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

                    if(count==1 || count==3) {
                        Intent firstpage = new Intent(context, UpdateBasicInfoActivity.class);
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
                        firstpage.putExtra("count", count);
                        firstpage.putExtra("UserName", UserName);
                        firstpage.putExtra("LastVisitedDate", LastVisitedDate);
//                        firstpage.putExtra("MarkSurvCount", MarkSurvCount);
                        firstpage.putStringArrayListExtra("imageurls", imagespath);
                        context.startActivity(firstpage);
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