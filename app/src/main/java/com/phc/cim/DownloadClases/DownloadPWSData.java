package com.phc.cim.DownloadClases;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.phc.cim.Activities.Licensing.PWSDetailListActivity;
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

public class DownloadPWSData {
    Context context;
    ArrayList<HashMap<String, String>> mylist;
    String jsonStr = null;
    ProgressDialog pDialog;
    String RoleID;
    int TotalRecord;
    int PageSize;
    int currentPage=1;
    String  hceTypetext, districtText, BfromText, BtoText, RegnoText, hcenameText,CNICtext;
    public DownloadPWSData(Context context, String hceTypetext, String districtText, String BfromText, String BtoText, String RegnoText, String hcenameText,String CNICtext) {

                this.context = context;
                this.hceTypetext=hceTypetext;
                this.districtText=districtText;
                this.BfromText=BfromText;
                this.BtoText=BtoText;
                this.RegnoText=RegnoText;
                this.hcenameText=hcenameText;
                this.CNICtext=CNICtext;

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
            url = baseurl + "Get_HCEDetailForMobile?strToken="+token+"&HCEName="+hcenameText+"&District="+districtText+"&BedsFrom="+BfromText+"&BedsTo="+BtoText+"&orgType="+hceTypetext+"&RegNum="+RegnoText+"&CNIC="+CNICtext+"&CurrentPage="+currentPage;



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

                            map.put("index", String.valueOf(i + 1));
                            map.put("Address", e.getString("Address"));
                            map.put("CNIC", e.getString("CNIC"));
                            map.put("CouncilName", e.getString("CouncilName"));
                            map.put("CouncilNo", e.getString("CouncilNo"));
                            map.put("District", e.getString("District"));
                            map.put("FeeReceived", e.getString("FeeReceived"));
                            map.put("HCEName", e.getString("HCEName"));
                            map.put("HCEType", e.getString("HCEType"));
                            map.put("Hcp_recID", e.getString("Hcp_recID"));
                            map.put("Mobile", e.getString("Mobile"));
                            map.put("PHCDeRegDate", e.getString("PHCDeRegDate"));
                            map.put("PHCDeRegNo", e.getString("PHCDeRegNo"));
                            map.put("PHCPLDate", e.getString("PHCPLDate"));
                            map.put("PHCPLNo", e.getString("PHCPLNo"));
                            map.put("PHCRLDate", e.getString("PHCRLDate"));
                            map.put("PHCRLNo", e.getString("PHCRLNo"));
                            map.put("PHCRegDate", e.getString("PHCRegDate"));
                            map.put("PHCRegNo", e.getString("PHCRegNo"));
                            map.put("Reason", e.getString("Reason"));
                            map.put("RegistrationCertificateIssued", e.getString("RegistrationCertificateIssued"));
                            map.put("SPName", e.getString("SPName"));
                            map.put("SPType", e.getString("SPType"));
                            map.put("SectorType", e.getString("SectorType"));
                            map.put("bedStrength", e.getString("bedStrength"));
                            map.put("TotalRec", e.getString("TotalRec"));
                            map.put("PageSize", e.getString("PageSize"));

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
                Intent firstpage = new Intent(context, PWSDetailListActivity.class);
                firstpage.putExtra("result",result);
                firstpage.putExtra("hceTypetext",hceTypetext);
                firstpage.putExtra("districtText",districtText);
                firstpage.putExtra("BfromText",BfromText);
                firstpage.putExtra("BtoText",BtoText);
                firstpage.putExtra("RegnoText",RegnoText);
                firstpage.putExtra("hcenameText",hcenameText);
                firstpage.putExtra("cnicText",CNICtext);
                context.startActivity(firstpage);
            } else {
                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();

            }
        }

    }
}