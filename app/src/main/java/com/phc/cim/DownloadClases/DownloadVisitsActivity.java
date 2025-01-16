package com.phc.cim.DownloadClases;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.phc.cim.TabsActivities.ReportingTabActivity;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class DownloadVisitsActivity {
    Context context;

    ArrayList<HashMap<String, String>> mylist2;


    String jsonStr2 = null;

    String email = null;
    String password;
    String isEdit;
    String username;
    ProgressDialog pDialog;

    String RoleID;
    int count = 0;
    int FuncselTotal=0;
    int ClosselTotal=0;
    int NotselTotal=0;
    int ClosInspTotal=0;
    int L_ATotal=0;
    int visitOnComplaintTotal=0;
    int SealTemperedTotal=0;
    int NonRegisterHCETotal=0;

    String PlanID,CloseSealedID,  NotSealedID  , FunctionalSealedID , index,FunctionalSealed, FunctionalSealedcount, NotSealed, NotSealedcount, CloseSealed, ClosedSealedcount, CloseSealedInspectioncount, L_Acount, VisitOnComplaintcount, SealTemperedcount, NonRegisterHCEcount, team,totalvisits,totalfir,startdat,enddate, District,PlanCode,selVistdate,TotalImages, CloseSealedInspectionID, CloseSealedInspection, L_A_ID, L_A,
    visitOnComplaintID, visitOnComplaint, SealTemperedID, SealTempered, NonRegisterHCEID, NonRegisterHCE ;

    ArrayList<HashMap<String, String>> indtabresult;
    public DownloadVisitsActivity(Context context, String PlanID, String email, String password, String username, String isEdit, String index,String team,String totalvisits,String totalfir,String startdat,String enddate,String District, String PlanCode,String Vistdate,ArrayList<HashMap<String, String>> indtabresult, String TotalImages) {


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
        this.selVistdate=Vistdate;
        this.indtabresult=indtabresult;
        this.TotalImages=TotalImages;
        SharedPreferences prefs = context.getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String isStat = prefs.getString("isStat", null);//"No name defined" is the default value.
        RoleID = prefs.getString("RoleID", null); //0 is the default value.
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading Data, Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();
        for (int i=0;i<indtabresult.size();i++){

            if(Objects.equals(indtabresult.get(i).get("PKID"), "1") && (indtabresult.get(i).get("VisitDate").equals(Vistdate) ||indtabresult.get(i).get("VisitDate").equals("null"))) {

                FunctionalSealedID = indtabresult.get(i).get("PKID");
                FunctionalSealed = indtabresult.get(i).get("TypeDesc");
                FuncselTotal = FuncselTotal + Integer.parseInt(Objects.requireNonNull(indtabresult.get(i).get("TotalSealed")));
            }
            if(Objects.equals(indtabresult.get(i).get("PKID"), "3") && (indtabresult.get(i).get("VisitDate").equals(Vistdate) || indtabresult.get(i).get("VisitDate").equals("null"))) {
                CloseSealedID = indtabresult.get(i).get("PKID");
                CloseSealed = indtabresult.get(i).get("TypeDesc");
                ClosselTotal = ClosselTotal + Integer.parseInt(Objects.requireNonNull(indtabresult.get(i).get("TotalSealed")));
            }
            if(Objects.equals(indtabresult.get(i).get("PKID"), "2") && (indtabresult.get(i).get("VisitDate").equals(Vistdate) || indtabresult.get(i).get("VisitDate").equals("null"))) {

                NotSealedID = indtabresult.get(i).get("PKID");
                NotSealed = indtabresult.get(i).get("TypeDesc");
                NotselTotal = NotselTotal + Integer.parseInt(Objects.requireNonNull(indtabresult.get(i).get("TotalSealed")));
            }
            if(indtabresult.get(i).get("PKID").equals("9") && (indtabresult.get(i).get("VisitDate").equals(Vistdate) || indtabresult.get(i).get("VisitDate").equals("null"))) {

                CloseSealedInspectionID = indtabresult.get(i).get("PKID");
                CloseSealedInspection = indtabresult.get(i).get("TypeDesc");
                ClosInspTotal = ClosInspTotal + Integer.parseInt(indtabresult.get(i).get("TotalSealed"));
            }
            if(indtabresult.get(i).get("PKID").equals("10") && (indtabresult.get(i).get("VisitDate").equals(Vistdate) || indtabresult.get(i).get("VisitDate").equals("null"))) {

                L_A_ID = indtabresult.get(i).get("PKID");
                L_A = indtabresult.get(i).get("TypeDesc");
                L_ATotal = L_ATotal + Integer.parseInt(indtabresult.get(i).get("TotalSealed"));
            }
            if(indtabresult.get(i).get("PKID").equals("11") && (indtabresult.get(i).get("VisitDate").equals(Vistdate) || indtabresult.get(i).get("VisitDate").equals("null"))) {

                visitOnComplaintID = indtabresult.get(i).get("PKID");
                visitOnComplaint = indtabresult.get(i).get("TypeDesc");
                visitOnComplaintTotal = visitOnComplaintTotal + Integer.parseInt(indtabresult.get(i).get("TotalSealed"));
            }
            if(indtabresult.get(i).get("PKID").equals("12") && (indtabresult.get(i).get("VisitDate").equals(Vistdate) || indtabresult.get(i).get("VisitDate").equals("null"))) {

                SealTemperedID = indtabresult.get(i).get("PKID");
                SealTempered = indtabresult.get(i).get("TypeDesc");
                SealTemperedTotal = SealTemperedTotal + Integer.parseInt(indtabresult.get(i).get("TotalSealed"));
            }
            if(indtabresult.get(i).get("PKID").equals("13") && (indtabresult.get(i).get("VisitDate").equals(Vistdate) || indtabresult.get(i).get("VisitDate").equals("null"))) {

                NonRegisterHCEID = indtabresult.get(i).get("PKID");
                NonRegisterHCE = indtabresult.get(i).get("TypeDesc");
                NonRegisterHCETotal = NonRegisterHCETotal + Integer.parseInt(indtabresult.get(i).get("TotalSealed"));
            }
        }

        FunctionalSealedcount = String.valueOf(FuncselTotal);
        NotSealedcount= String.valueOf(NotselTotal);
        ClosedSealedcount = String.valueOf(ClosselTotal);
        CloseSealedInspectioncount = String.valueOf(ClosInspTotal);
        L_Acount = String.valueOf(L_ATotal);
        VisitOnComplaintcount = String.valueOf(visitOnComplaintTotal);
        SealTemperedcount = String.valueOf(SealTemperedTotal);
        NonRegisterHCEcount = String.valueOf(NonRegisterHCETotal);

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

        }
        String url = getDirectionsUrl2(Vistdate);
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

    private String getDirectionsUrl2(String Vistdate) {

        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String token = context.getResources().getString(R.string.token);
        String url = null;
        if(Vistdate.equals("All")){
            url = baseurl + "GetAllSealedPlanActionTypeWise?strToken=" + token + "&planID=" + PlanID+"&RoleID="+RoleID;
        }
        else {
            url = baseurl + "GetAllSealedPlanActionTypeDateWise?strToken=" + token + "&planID=" + PlanID + "&VisitedDate=" + Vistdate+"&RoleID="+RoleID;
        }

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
                Intent firstpage = new Intent(context, ReportingTabActivity.class);
                firstpage.putExtra("FunctionalSealedID", FunctionalSealedID);
                firstpage.putExtra("FunctionalSealed",FunctionalSealed);
                firstpage.putExtra("FunctionalSealedcount",FunctionalSealedcount);
                firstpage.putExtra("NotSealedID", NotSealedID);
                firstpage.putExtra("NotSealed", NotSealed);
                firstpage.putExtra("NotSealedcount", NotSealedcount);
                firstpage.putExtra("CloseSealedID", CloseSealedID);
                firstpage.putExtra("CloseSealed", CloseSealed);
                firstpage.putExtra("ClosedSealedcount", ClosedSealedcount);
                firstpage.putExtra("CloseSealedInspectionID", CloseSealedInspectionID);
                firstpage.putExtra("CloseSealedInspection", CloseSealedInspection);
                firstpage.putExtra("CloseSealedInspectioncount", CloseSealedInspectioncount);
                firstpage.putExtra("L_A_ID", L_A_ID);
                firstpage.putExtra("L_A", L_A);
                firstpage.putExtra("L_Acount", L_Acount);
                firstpage.putExtra("visitOnComplaintID", visitOnComplaintID);
                firstpage.putExtra("visitOnComplaint", visitOnComplaint);
                firstpage.putExtra("VisitOnComplaintcount", VisitOnComplaintcount);
                firstpage.putExtra("SealTemperedID", SealTemperedID);
                firstpage.putExtra("SealTempered", SealTempered);
                firstpage.putExtra("SealTemperedcount", SealTemperedcount);
                firstpage.putExtra("NonRegisterHCEID", NonRegisterHCEID);
                firstpage.putExtra("NonRegisterHCE", NonRegisterHCE);
                firstpage.putExtra("NonRegisterHCEcount", NonRegisterHCEcount);
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
                firstpage.putExtra("TotalImages",TotalImages);
                firstpage.putExtra("selVistdate",selVistdate);
                firstpage.putExtra("result",result);
                context.startActivity(firstpage);

            } else {


                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();

            }
        }

    }

}