package com.phc.cim.Managers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


import com.phc.cim.DataElements.Action;
import com.phc.cim.DataElements.ActionType;
import com.phc.cim.DataElements.CouncilType;
import com.phc.cim.DataElements.Distance;
import com.phc.cim.DataElements.District;
import com.phc.cim.DataElements.Division;
import com.phc.cim.DataElements.OrgType;
import com.phc.cim.DataElements.PWSDistrict;
import com.phc.cim.DataElements.PWSOrgType;
import com.phc.cim.DataElements.RegStatus;
import com.phc.cim.DataElements.Role;
import com.phc.cim.DataElements.SectorType;
import com.phc.cim.DataElements.Tehsil;
import com.phc.cim.DataElements.UpdateStatL1;
import com.phc.cim.DataElements.UpdateStatL2;
import com.phc.cim.DataElements.subActionType;
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


public class WebApiManager {

    String Division_id=null;
    String division=null;
    String District_div=null;
    String District_id=null;
    String District = null;
    String Tehsil_id=null;
    String TDistrict=null;
    String Tehsil=null;
    String sectorType_id=null;
    String sectorType = null;
    String councilType_id=null;
    String councilType = null;
    String OrgType_Id=null;
    String OrgType = null;
    String updatedStatL1_Id=null;
    String updatedStatL1 = null;
    String updatedStatL2_Id=null;
    String updatedStatL2 = null;
    String action_Id=null;
    String action=null;
    String regStatus_Id=null;
    String reg_status=null;
    String Distance_Id=null;
    String Dictance=null;
    String PWS_District=null;
    String pwsDistrict_Id=null;
    String PWSorg=null;
    String PWSorgID=null;
    String actionTypeText;
    String actionTypeID;
    String subactionTypeText;
    String subactionTypeID;
    String subcatActionType_Id;
    String FIRAssociated;
    String CatDefinition;
    String RoleName;
    String Role_Id,CatStatusID , CatRoleID,SubRoleID, SubStatusID;
    int count=0;
    ProgressDialog pDialog;
    ArrayList<HashMap<String, String>> mylist;
    String jsonStr = null;
    DatabaseManager databaseManagerHelper;
    Context context;

    public WebApiManager(Context context, int count){

        this.context=context;
        this.count=count;
        databaseManagerHelper = new DatabaseManager(context);
     /*   pDialog = new ProgressDialog(context);
        pDialog.setMessage("Please wait your data is loading...");
        pDialog.setCancelable(false);
        pDialog.show();*/

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
            //JsonDataParsing();
            // Invokes the thread for parsing the JSON data
           parserTask.execute();

        }
    }

    private String getDirectionsUrl() {

        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String token =  context.getResources().getString(R.string.token);
        String url =null;
        if(count==0) {
             url = baseurl + "GetDistricts";

        }
        else if(count==1){
             url = baseurl + "GetAllTehsils";
        }
        else if(count==2){
             url = baseurl + "GetSectorType";
        }
        else if(count==3){
             url = baseurl + "GetCouncilType";
        }
        else if(count==4){
             url = baseurl + "GetOrgType";
        }
        else if(count==5){
            url = baseurl + "GetAllDistances";
        }
        else if(count==6){
            url = baseurl + "GetRegStatus";
        }

        else if(count==7) {

            url = baseurl + "GetCategoryType";
        }
        else if(count==8) {

            url = baseurl + "GetSubCategoryTypes";
        }
        else if(count==9) {

            url = baseurl + "GetDivisions";
        }
        else if(count==10){
            url = baseurl + "GetQuackCategory?strToken="+token;
        }
        else if(count==11){
            url = baseurl + "GetAllRoles?strToken="+token;
        }
        else if(count==12){
            url = baseurl + "GetRegStatusLevel2?ID=21";
        }
        else if(count==13){
            url = baseurl + "GetAllActions";
        }
        else if(count==14){
            url = baseurl + "GetPWSDistricts";
        }
        else if(count==15){
            url = baseurl + "GetPWSOrgType";
        }
       // url = url.replaceAll(" ", "%20");

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

    private class ParserTask extends AsyncTask<Object, Object, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog


        }

        @Override
        protected String doInBackground(Object... arg0) {

            // Making a request to url and getting response

            if (jsonStr != null) {

                try {
                    JSONArray json = new JSONArray(jsonStr);
                        databaseManagerHelper.delete(count);
                    //mylist = new ArrayList<HashMap<String, String>>();
                    for (int i = 0; i <json.length(); i++) {
                        // HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = json.getJSONObject(i);
                        if(count==0) {
                            District_id = String.valueOf(i);
                            District_div=e.getString("Division");
                            District = e.getString("District");
                            District district = new District(District_id,District_div,District);
                            databaseManagerHelper.saveDistrict(district);
                        }
                        else if(count==1){
                            Tehsil_id = String.valueOf(i);
                            TDistrict = e.getString("District");
                            Tehsil = e.getString("Tehsil");
                            Tehsil tehsil = new Tehsil(Tehsil_id,TDistrict,Tehsil);
                            databaseManagerHelper.saveTehsil(tehsil);
                        }
                        else if(count==2){
                            sectorType_id = String.valueOf(i);
                            sectorType = e.getString("Sector");
                            SectorType sectorTypes = new SectorType(sectorType_id,sectorType);
                            databaseManagerHelper.saveSectorType(sectorTypes);
                        }
                        else if(count==3){
                            councilType_id = String.valueOf(i);
                            councilType = e.getString("Council");
                            CouncilType council = new CouncilType(councilType_id,councilType);
                            databaseManagerHelper.saveCouncilType(council);
                        }
                        else if(count==4){
                            OrgType_Id = String.valueOf(i);
                            OrgType = e.getString("OrgTypeName");
                            OrgType orgType = new OrgType(OrgType_Id,OrgType);
                            databaseManagerHelper.saveOrgType(orgType);
                        }

                        else if(count==5){
                            Distance_Id = e.getString("ID");
                            Dictance = e.getString("DistanceDesc");
                            Distance distance = new Distance(Distance_Id,Dictance);
                            databaseManagerHelper.saveDistance(distance);
                        }
                        else if(count==6){
                            regStatus_Id = e.getString("ID");
                            reg_status = e.getString("StatusDesc");
                            RegStatus regStatus = new RegStatus(regStatus_Id,reg_status);
                            databaseManagerHelper.saveRegStatus(regStatus);
                        }

                        else if(count==7) {
                            actionTypeID = e.getString("PKID");
                            actionTypeText = e.getString("TypeDesc");
                            FIRAssociated = e.getString("FIRAssociated");
                            CatDefinition = e.getString("Definition");
                            CatRoleID = e.getString("RoleID");
                            CatStatusID = e.getString("StatusID");

                            ActionType actiontype = new ActionType(actionTypeID,actionTypeText,FIRAssociated,CatDefinition,CatRoleID,CatStatusID);
                            databaseManagerHelper.saveActiontype(actiontype);
                        }
                        else if(count==8) {

                            subactionTypeID = e.getString("ID");
                            subcatActionType_Id=e.getString("CategoryTypeID");
                            subactionTypeText = e.getString("StatusLevel2");
                            SubRoleID=e.getString("RoleID");
                            SubStatusID = e.getString("StatusID");
                            subActionType subactionstype = new subActionType(subactionTypeID,subcatActionType_Id,subactionTypeText,SubRoleID,SubStatusID);
                            databaseManagerHelper.savesubActiontype(subactionstype);
                        }
                        else if(count==9) {

                            Division_id = String.valueOf(i);
                            division = e.getString("Division");
                            Division division1 = new Division(Division_id,division);
                            databaseManagerHelper.savedivision(division1);
                        }

                        else if(count==10){
                            updatedStatL1_Id = String.valueOf(i);
                            updatedStatL1 = e.getString("QuackCatDesc");
                            UpdateStatL1 updateStatL1 = new UpdateStatL1(updatedStatL1_Id,updatedStatL1);
                            databaseManagerHelper.saveUpdateStatus(updateStatL1);
                        }
                        else if(count==11){
                            Role_Id = e.getString("PKID");
                            RoleName = e.getString("RoleName");
                            Role role = new Role(Role_Id,RoleName);
                            databaseManagerHelper.saveRole(role);
                        }
                        else if(count==12){
                            updatedStatL2_Id = e.getString("ID");
                            updatedStatL2 = e.getString("StatusLevel2");
                            UpdateStatL2 updateStatL2 = new UpdateStatL2(updatedStatL2_Id,updatedStatL2);
                            databaseManagerHelper.saveSubStatus(updateStatL2);
                        }
                        else if(count==13){
                            action_Id = e.getString("ID");
                            action = e.getString("ActionName");
                            Action actions = new Action(action_Id,action);
                            databaseManagerHelper.saveAction(actions);

                        }
                        else if(count==14){
                            pwsDistrict_Id = String.valueOf(i);
                            PWS_District = e.getString("District");
                            PWSDistrict pwsDistrict = new PWSDistrict(pwsDistrict_Id,PWS_District);
                            databaseManagerHelper.savePWSdistrict(pwsDistrict);

                        }
                        else if(count==15){
                            PWSorgID = String.valueOf(i);
                            PWSorg = e.getString("OrgTypeName");
                            PWSOrgType pwsOrgType = new PWSOrgType(PWSorgID,PWSorg);
                            databaseManagerHelper.savePWSOrg(pwsOrgType);

                        }
                    }


                } catch (final JSONException e) {

                    e.printStackTrace();


                }
            } else {
                Log.e("exception", "Couldn't get json from server.");
            }



            return null;
        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);


      /*      if (pDialog.isShowing())
                pDialog.dismiss();*/



        }

    }

}
