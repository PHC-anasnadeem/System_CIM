package com.phc.cim.DownloadClases;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.phc.cim.Activities.Common.SealingSubTypesActivity;
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

public class DownloadSealingsubActivity {
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
    int FuncselTotal=0;
    int ClosselTotal=0;
    int NotselTotal=0;
    String RecordLockedForUpdate;
    String PlanTitle,ActionID,  SubActionname  , FunctionalSealedID , SubCatID,FunctionalSealed, FunctionalSealedcount, NotSealed, NotSealedcount, CloseSealed, ClosedSealedcount,team,totalvisits,totalfir,startdat,enddate, District,PlanCode,selVistdate,TotalImages;
    double latitude, longitude;
    ArrayList<HashMap<String, String>> indtabresult;
    public DownloadSealingsubActivity(Context context, String PlanTitle,String SubCatID,String ActionID,String SubActionname, String email, String password, String username, String isEdit) {


        this.context = context;
        this.PlanTitle = PlanTitle;
        this.email = email;
        this.password = password;
        this.username = username;
        this.isEdit = isEdit;
        this.SubCatID = SubCatID;
        this.ActionID=ActionID;
        this.SubActionname=SubActionname;
        this.totalfir=totalfir;
        this.startdat=startdat;
        this.enddate=enddate;
        this.District=District;
        this.PlanCode=PlanCode;
      //  this.selVistdate=Vistdate;
        this.indtabresult=indtabresult;
        this.TotalImages=TotalImages;
        SharedPreferences prefs = context.getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String isStat = prefs.getString("isStat", null);//"No name defined" is the default value.
        RoleID = prefs.getString("RoleID", null); //0 is the default value.
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading Data, Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
/*        for (int i=0;i<indtabresult.size();i++){

            if(indtabresult.get(i).get("PKID").equals("1") && (indtabresult.get(i).get("VisitDate").equals(Vistdate) ||indtabresult.get(i).get("VisitDate").equals("null"))) {

                FunctionalSealedID = indtabresult.get(i).get("PKID");
                FunctionalSealed = indtabresult.get(i).get("TypeDesc");
                FuncselTotal = FuncselTotal + Integer.parseInt(indtabresult.get(i).get("TotalSealed"));
            }
            if(indtabresult.get(i).get("PKID").equals("3") && (indtabresult.get(i).get("VisitDate").equals(Vistdate) || indtabresult.get(i).get("VisitDate").equals("null"))) {
                CloseSealedID = indtabresult.get(i).get("PKID");
                CloseSealed = indtabresult.get(i).get("TypeDesc");
                ClosselTotal = ClosselTotal + Integer.parseInt(indtabresult.get(i).get("TotalSealed"));
            }
            if(indtabresult.get(i).get("PKID").equals("2") && (indtabresult.get(i).get("VisitDate").equals(Vistdate) || indtabresult.get(i).get("VisitDate").equals("null"))) {

                NotSealedID = indtabresult.get(i).get("PKID");
                NotSealed = indtabresult.get(i).get("TypeDesc");
                NotselTotal = NotselTotal + Integer.parseInt(indtabresult.get(i).get("TotalSealed"));
            }
        }
        FunctionalSealedcount = String.valueOf(FuncselTotal);
        NotSealedcount= String.valueOf(NotselTotal);
        ClosedSealedcount = String.valueOf(ClosselTotal);
        DateFormat formatter;
        if (Vistdate != null && Vistdate!="All") {
            formatter = new SimpleDateFormat("M/d/yyyy");
            Date date2 = null;
            try {
                date2 = (Date) formatter.parse(Vistdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yy");
            if (date2 != null)
                Vistdate = newFormat.format(date2);

        }*/
        String url = getDirectionsUrl2();
        DownloadTask2 downloadTask = new DownloadTask2();
        //Start downloading json data from Google Directions API
        downloadTask.execute(url);

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
/*        if(Vistdate.equals("All")){
            url = baseurl + "GetAllSealedPlanActionTypeWise?strToken=" + token + "&planID=" + PlanID+"&RoleID="+RoleID;
        }
        else {*/
            url = baseurl + "GetAllStatForPlanTitle_SubCat?strToken="+token+"&PlanTitle="+PlanTitle+"&SubCatID="+SubCatID;
       // }

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

    private class ParserTask2 extends AsyncTask<Object, Object, ArrayList<HashMap<String, String>>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog


        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Object... arg0) {

            // Making a request to url and getting response

            if (jsonStr2 != null) {
                try {
                    JSONArray json = new JSONArray(jsonStr2);
// ...
                    mylist2 = new ArrayList<HashMap<String, String>>();
                    for (int i = 0; i < json.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = json.getJSONObject(i);
                        map.put("index", String.valueOf(i+1));
                        map.put("Action" , e.getString("Action"));
                        map.put("ActionType" , e.getString("ActionType"));
                        map.put("Address" , e.getString("Address"));
                        map.put("Comments" , e.getString("Comments"));
                        map.put("Date_Time" , e.getString("Date_Time"));
                        map.put("FinalID" , e.getString("FinalID"));
                        map.put("Name" , e.getString("Name"));
                        map.put("SubAction" , e.getString("SubAction"));
                        map.put("TotalImages" , e.getString("TotalImages"));
                        map.put("UserName" , e.getString("UserName"));
                        map.put("VisitedDate" , e.getString("VisitedDate"));
                        map.put("district" , e.getString("district"));
                        map.put("isFIRSubmit" , e.getString("isFIRSubmit"));
                        map.put("sector_type" , e.getString("sector_type"));
                        mylist2.add(map);
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
        protected void onPostExecute(final ArrayList<HashMap<String, String>> result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (result != null && result.size() > 0) {
                Intent firstpage = new Intent(context, SealingSubTypesActivity.class);

                firstpage.putExtra("email", email);
                firstpage.putExtra("Password", password);
                firstpage.putExtra("username", username);
                firstpage.putExtra("isEdit", isEdit);
                firstpage.putExtra("PlanTitle",PlanTitle);
                firstpage.putExtra("ActionID",ActionID);
                firstpage.putExtra("SubActionname",SubActionname);
                firstpage.putExtra("result",result);
                context.startActivity(firstpage);

            } else {


                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();

            }
        }

    }



/*
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
        url = baseurl + "GetAllSealedCountPlanWise?strToken=" + token + "&planID=" + PlanID;

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

                        map.put("PKID", e.getString("PKID"));
                        map.put("TotalSealed", e.getString("TotalSealed"));
                        map.put("TypeDesc", e.getString("TypeDesc"));
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

            if (result != null && result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {

                    PKID = result.get(i).get("PKID");
                    if(PKID.equals("1")) {
                        FunctionalSealedID = result.get(i).get("PKID");
                        FunctionalSealed = result.get(i).get("TypeDesc");
                        FunctionalSealedcount = result.get(i).get("TotalSealed");
                    }
                    else  if(PKID.equals("2")){
                        NotSealedID = result.get(i).get("PKID");
                        NotSealed = result.get(i).get("TypeDesc");
                        NotSealedcount = result.get(i).get("TotalSealed");
                    }
                    else  if(PKID.equals("3")){
                        CloseSealedID = result.get(i).get("PKID");
                        CloseSealed = result.get(i).get("TypeDesc");
                        ClosedSealedcount = result.get(i).get("TotalSealed");
                    }
                }
                String url = getDirectionsUrl2();
                DownloadTask2 downloadTask = new DownloadTask2();
                //Start downloading json data from Google Directions API
                downloadTask.execute(url);

            } else {
                if (pDialog.isShowing())
                    pDialog.dismiss();

                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();

            }
        }

    }*/

}