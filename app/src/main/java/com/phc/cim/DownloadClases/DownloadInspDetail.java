package com.phc.cim.DownloadClases;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.phc.cim.Activities.Inspection.InspectionDetailActivity;
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

public class DownloadInspDetail {
String jsonStr;
String jsonStr1;
String jsonStr2;
Context context;
String PHC_RegID;
ProgressDialog pDialog;
ArrayList<HashMap<String, String>> BasicInfoDetail;
ArrayList<HashMap<String, String>> LocationDetail;
ArrayList<HashMap<String, String>> mylist;
    ArrayList<HashMap<String, String>> mylist1;
ArrayList<String> mylist2;
ArrayList<String> imagespath;
    public DownloadInspDetail(Context context, String PHC_RegID){

        this.context=context;
        this.PHC_RegID=PHC_RegID;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait your data is loading...");
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
      url = baseurl + "GetHCEBasicInfo?strToken="+token+"&RegistrationNo="+PHC_RegID;
      ;

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

                    map.put("Address", e.getString("Address"));
                    map.put("BasicInfoMode", e.getString("BasicInfoMode"));
                    map.put("BasicInfoRoleID", e.getString("BasicInfoRoleID"));
                    map.put("BasicInfoVisitedBy", e.getString("BasicInfoVisitedBy"));
                    map.put("BasicInfoVisitedDate", e.getString("BasicInfoVisitedDate"));
                    map.put("BasicInfoVisitedTime", e.getString("BasicInfoVisitedTime"));
                    map.put("BasicInfoVisitedUserName", e.getString("BasicInfoVisitedUserName"));
                    map.put("FinalId", e.getString("FinalId"));
                    map.put("MobileNumber", e.getString("MobileNumber"));
                    map.put("PersonName", e.getString("PersonName"));
                    map.put("bedStrength", e.getString("bedStrength"));
                    map.put("dataType", e.getString("dataType"));
                    map.put("orgName", e.getString("orgName"));
                    map.put("orgType", e.getString("orgType"));
                    map.put("phcRegistrationNumber", e.getString("phcRegistrationNumber"));
                    map.put("recID", e.getString("recID"));
                    map.put("Services_Names", e.getString("Services_Names"));
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

        if (result != null && result.size() > 0) {
            BasicInfoDetail=result;
            String url = getDirectionsUrl1();
            DownloadTask1 downloadTask = new DownloadTask1();
            //Start downloading json data from Google Directions API
            downloadTask.execute(url);
        } else if(result != null && result.size()<1) {
            if (pDialog.isShowing())
                pDialog.dismiss();
            Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();

        }
        else if(result==null){
            if (pDialog.isShowing())
                pDialog.dismiss();
            AlertDialog alertDialog = new AlertDialog.Builder(
                    context).create();

            // Setting Dialog Title
            alertDialog.setTitle("Server Connection Error");

            // Setting Dialog Message
            alertDialog.setMessage("Please check your internet connection or verify server response");
            // Setting Icon to Dialog
            // alertDialog.setIcon(R.drawable.eroricon);

            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    //Toast.makeText(context, "Thanks You!", Toast.LENGTH_SHORT).show();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
        else{
            if (pDialog.isShowing())
                pDialog.dismiss();
            Toast.makeText(context, "Server not responding! Please try again", Toast.LENGTH_SHORT).show();

        }
    }

}

    private class DownloadTask1 extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl1(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask1 parserTask1 = new ParserTask1();
            jsonStr1 = result;
            // Invokes the thread for parsing the JSON data
            parserTask1.execute();

        }
    }

    private String getDirectionsUrl1() {

        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String token = context.getResources().getString(R.string.token);
        String url = null;
        url = baseurl + "GetHCELocation?strToken="+token+"&RegistrationNo="+PHC_RegID;
        ;

        url = url.replaceAll(" ", "%20");

        return url;
    }

    private String downloadUrl1(String strUrl) throws IOException {


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

    private class ParserTask1 extends AsyncTask<Object, Object, ArrayList<HashMap<String, String>>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog


        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Object... arg0) {

            // Making a request to url and getting response

            if (jsonStr1 != null) {
                try {
                    JSONArray json = new JSONArray(jsonStr1);
// ...
                 mylist1 = new ArrayList<HashMap<String, String>>();
                    for (int i = 0; i < json.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = json.getJSONObject(i);

                        map.put("Lat", e.getString("Lat"));
                        map.put("Lng", e.getString("Lng"));
                        map.put("LocationMode", e.getString("LocationMode"));
                        map.put("LocationRoleID", e.getString("LocationRoleID"));
                        map.put("LocationVisitedUserName", e.getString("LocationVisitedUserName"));
                        map.put("LoctionVisitedBy", e.getString("LoctionVisitedBy"));
                        map.put("LoctionVisitedDate", e.getString("LoctionVisitedDate"));
                        map.put("LoctionVisitedTime", e.getString("LoctionVisitedTime"));
                        mylist1.add(map);
                    }

                    // adding contact to contact list
                    //contactList.add(contact);

                } catch (final JSONException e) {

                    e.printStackTrace();


                }
            } else {
                Log.e("exception", "Couldn't get json from server.");
            }

            return mylist1;
        }

        @Override
        protected void onPostExecute(final ArrayList<HashMap<String, String>> result) {
            super.onPostExecute(result);


                LocationDetail=result;
                String url = getDirectionsUrl2();
                DownloadTask2 downloadTask = new DownloadTask2();
                //Start downloading json data from Google Directions API
                downloadTask.execute(url);

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

    private String getDirectionsUrl2() {

        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String token = context.getResources().getString(R.string.token);
        String url = null;
        url = baseurl + "GetInspectionImages?strToken="+token+"&RegNum=" + PHC_RegID;
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
                imagespath=result;
                Intent firstpage = new Intent(context, InspectionDetailActivity.class);
                firstpage.putExtra("BasicInfoDetail",BasicInfoDetail);
                firstpage.putExtra("LocationDetail",LocationDetail);
                firstpage.putStringArrayListExtra("imageurls", imagespath);
                context.startActivity(firstpage);
        }
    }
}