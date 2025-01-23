package com.phc.cim.Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.phc.cim.Others.CurrentLocation;
import com.phc.cim.ParcelableModel.CarParcelable;
import com.phc.cim.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class summaryFragment extends Fragment {
    float TotalRecords;
    float  TotalPL;
    float  TotalRL;
    float TotalReg;
    float  TotalUnregistered;
    float   TotalQuacks;
    float TotalRLPL;
    float FTotalRLPL;
    float FTotalReg;
    float  FTotalUnregistered;
    float   FTotalQuacks;
    float   FTotalDeseal;
    TextView totaltex;
    TextView PLRLTEXT;
    TextView RegText;
    TextView notReg;
    TextView quacktext;
    TextView desealtext;
    ImageView licenseicon;
    ImageView registeredicon;
    ImageView notregicon;
    ImageView quackicon;
    ImageView desealicon;
    LinearLayout errortextlayout;

    String finalidText;
    String Cnic;
    String Phone;
    String dataType;
    String registrationType;
    String orgType;
    String REGfilterstatus;
    String districtText;
    String TehsilText;
    String distancetext;
    String subactionTypeID;
    String BfromText="";
    String BtoText="";
    String email=null;
    String password;
    String isEdit;
    String username;
    String lastvisitedText;
    String RegnoText;
    String hcenameText;
    String QuackType;
    ProgressDialog pDialog;
    double cur_latitude;
    double cur_longitude;
    CurrentLocation gps;
    private ArrayList<CarParcelable> mCarParcelableListLastLocation;
    ArrayList<HashMap<String, String>> mylist;
    BarChart chart ;
    ArrayList<BarEntry> BARENTRY1;
    ArrayList<BarEntry> BARENTRY2;
    ArrayList<BarEntry> BARENTRY3;
    ArrayList<BarEntry> BARENTRY4;
    ArrayList<BarEntry> BARENTRY5;
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset1;
    BarDataSet Bardataset2;
    BarDataSet Bardataset3;
    BarDataSet Bardataset4;
    BarDataSet Bardataset5;
    BarData BARDATA ;
    String jsonStr = null;
    List<IBarDataSet> bars;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View objview= inflater.inflate(R.layout.fragment_summary, container, false);
        chart = (BarChart) objview.findViewById(R.id.chart1);

         totaltex= (TextView)  objview.findViewById(R.id.totaltext);
         PLRLTEXT= (TextView) objview.findViewById(R.id.licensetext);
         RegText= (TextView) objview.findViewById(R.id.regtext);
         notReg= (TextView) objview.findViewById(R.id.notregtext);
         quacktext= (TextView) objview.findViewById(R.id.quacktext);
//        desealtext= (TextView) objview.findViewById(R.id.desealtext);

        licenseicon= (ImageView) objview.findViewById(R.id.licenseicon);
        registeredicon= (ImageView) objview.findViewById(R.id.registeredicon);
        notregicon= (ImageView) objview.findViewById(R.id.notregicon);
        quackicon= (ImageView) objview.findViewById(R.id.quackicon);
//        desealicon= (ImageView) objview.findViewById(R.id.desealicon);
        errortextlayout = (LinearLayout) objview.findViewById(R.id.errortextlayout);

        pDialog = new ProgressDialog(getActivity());
        gps = new CurrentLocation(getContext());
        final Bundle args = getArguments();
        dataType= args.getString("dataType");
        registrationType= args.getString("registrationType");
        orgType= (String) args.getString("orgType");
        REGfilterstatus= args.getString("hcestatus");
        districtText= args.getString("districtText");
        TehsilText= args.getString("tehsilText");
        subactionTypeID= args.getString("subactionTypeID");
        distancetext= args.getString("distancetext");
        BfromText= args.getString("BfromText");
        BtoText= args.getString("BtoText");
        lastvisitedText = args.getString("lastvisitedText");
        RegnoText = args.getString("RegnoText");
        hcenameText = args.getString("hcenameText");
        email= args.getString("email");
        password= args.getString("password");
        username= args.getString("username");
        isEdit= args.getString("isEdit");
        finalidText= args.getString("finalidText");
        QuackType= args.getString("QuackType");
        Cnic= args.getString("Cnic");
        Phone= args.getString("Phone");

        licenseicon.setVisibility(View.GONE);
        registeredicon.setVisibility(View.GONE);
        notregicon.setVisibility(View.GONE);
        quackicon.setVisibility(View.GONE);
//        desealicon.setVisibility(View.GONE);
        errortextlayout.setVisibility(View.GONE);

        if (gps.canGetLocation()) {

            cur_latitude = gps.getLatitude();
            cur_longitude = gps.getLongitude();

            if (cur_latitude != 0.0 && cur_longitude != 0.0) {
                pDialog.setMessage("Loading Data, Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
                String url = getDirectionsUrl();
                DownloadTask downloadTask = new DownloadTask();
                //Start downloading json data from Google Directions API
                downloadTask.execute(url);
            }
            else {
                cur_latitude = gps.getLatitude();
                cur_longitude = gps.getLongitude();
                String url = getDirectionsUrl();
                DownloadTask downloadTask = new DownloadTask();
                //Start downloading json data from Google Directions API
                downloadTask.execute(url);
            }
        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            gps.showSettingsAlert();
        }


        BARENTRY1 = new ArrayList<>();
        BARENTRY2 = new ArrayList<>();
        BARENTRY3 = new ArrayList<>();
        BARENTRY4 = new ArrayList<>();
//        BARENTRY5 = new ArrayList<>();
        bars = new ArrayList<IBarDataSet>();

        BarEntryLabels = new ArrayList<String>();



        return objview;

    }

    public void AddValuesToBARENTRY(){


        BARENTRY1.add(new BarEntry(Math.round(FTotalRLPL), 0));
        BARENTRY2.add(new BarEntry(Math.round(FTotalReg), 1));
        BARENTRY3.add(new BarEntry(Math.round(FTotalUnregistered), 2));
        BARENTRY4.add(new BarEntry(Math.round(FTotalQuacks), 3));
//        BARENTRY5.add(new BarEntry(Math.round(FTotalDeseal), 4));

    }

    public void AddValuesToBarEntryLabels(){

        BarEntryLabels.add("");
        BarEntryLabels.add("");
        BarEntryLabels.add("");
        BarEntryLabels.add("");
//      BarEntryLabels.add("");

    }

//-------------------------DownloadInspDetail Task------------------------------------------------

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
        String url=null;
        String baseurl = getContext().getResources().getString(R.string.baseurl);

        String token= getContext().getResources().getString(R.string.token);

        url = baseurl + "GetPercentage?strToken="+token+"&District=" + districtText + "&Tehsil=" + TehsilText + "&DataType=" + dataType + "&orgType=" + orgType + "&Councile=" + registrationType + "&Status=" + REGfilterstatus + "&Category=&From=" + BfromText + "&To=" + BtoText+"&Lvs=&RegNum="+RegnoText+"&HCEName="+hcenameText+"&Latitude="+cur_latitude+"&Longitude="+cur_longitude+"&Distance="+distancetext+"&finalid="+finalidText+"&ActionType="+lastvisitedText+"&QuackCategory="+QuackType+"&QuackSubCategory=&SubActionType="+subactionTypeID;

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
                    for (int i = 0; i <json.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = json.getJSONObject(i);
                        map.put("index", String.valueOf(i));
                        map.put("TotalPL", e.getString("TotalPL"));
                        map.put("TotalQuacks", e.getString("TotalQuacks"));
                        map.put("TotalRL", e.getString("TotalRL"));
                        map.put("TotalRecords", e.getString("TotalRecords"));
                        map.put("TotalReg", e.getString("TotalReg"));
                        map.put("TotalUnregistered", e.getString("TotalUnregistered"));

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



            if (result != null) {
                if (result.size() > 0) {

                    for(int i = 0; i < result.size(); i++){
                        TotalRecords =  Float.parseFloat(result.get(i).get("TotalRecords"));
                        TotalPL =  Float.parseFloat(result.get(i).get("TotalPL"));
                        TotalRL =  Float.parseFloat(result.get(i).get("TotalRL"));
                        TotalReg =  Float.parseFloat(result.get(i).get("TotalReg"));
                        TotalUnregistered =  Float.parseFloat(result.get(i).get("TotalUnregistered"));
//                        TotalQuacks = (int) Float.parseFloat(result.get(i).get("TotalQuacks"));
                    }

                    TotalRLPL=TotalPL+TotalRL;


                     FTotalRLPL=(TotalRLPL/TotalRecords)*100;
                     FTotalReg=(TotalReg/TotalRecords)*100;
                      FTotalUnregistered=(TotalUnregistered/TotalRecords)*100;
                       FTotalQuacks=(TotalQuacks/TotalRecords)*100;
//                    FTotalDeseal=(TotalQuacks/TotalRecords)*100;

                    totaltex.setText("Total record found: "+((int) TotalRecords));
                    if(TotalRecords!=0) {

                        licenseicon.setVisibility(View.VISIBLE);
                        registeredicon.setVisibility(View.VISIBLE);
                        notregicon.setVisibility(View.VISIBLE);
                        quackicon.setVisibility(View.VISIBLE);
//                        desealicon.setVisibility(View.VISIBLE);
                        errortextlayout.setVisibility(View.VISIBLE);
                        PLRLTEXT.setText("Provisional/Regular License ("+((int) TotalRLPL)+" - "+Math.round(FTotalRLPL)+"% )");
                        RegText.setText("Registered with PHC ("+((int) TotalReg)+" - "+Math.round(FTotalReg)+"% )");
                        notReg.setText("Not Registered with PHC ("+((int) TotalUnregistered)+" - "+Math.round(FTotalUnregistered)+"% )");
                        quacktext.setText("Quack ("+((int) TotalQuacks)+" - "+Math.round(FTotalQuacks)+"% )");
//                        desealtext.setText("DeSeal ("+((int) TotalQuacks)+" - "+Math.round(FTotalDeseal)+"% )");
                        AddValuesToBARENTRY();

                        AddValuesToBarEntryLabels();

                        Bardataset1 = new BarDataSet(BARENTRY1, "PL/RL");
                        Bardataset1.setColor(0xFF00FF7F);
                        Bardataset1.setValueTextSize(10);
                        Bardataset1.setBarSpacePercent(-500f);
                        bars.add(Bardataset1);
                        Bardataset2 = new BarDataSet(BARENTRY2, "REG");
                        Bardataset2.setValueTextSize(10);
                        Bardataset2.setColor(Color.YELLOW);
                        Bardataset2.setBarSpacePercent(-285f);
                        bars.add(Bardataset2);
                        Bardataset3 = new BarDataSet(BARENTRY3, "Not REG");
                        Bardataset3.setValueTextSize(10);
                        Bardataset3.setColor(0xFF6495ED);
                        Bardataset3.setBarSpacePercent(-285f);
                        bars.add(Bardataset3);
                        Bardataset4 = new BarDataSet(BARENTRY4, "QUACK");
                        Bardataset4.setValueTextSize(10);
                        Bardataset4.setColor(Color.RED);
                        Bardataset4.setBarSpacePercent(-285f);
                        bars.add(Bardataset4);
//                        Bardataset5 = new BarDataSet(BARENTRY5, "DeSeal");
//                        Bardataset5.setValueTextSize(10);
//                        Bardataset5.setColor(0xFFA765E9);
//                        Bardataset5.setBarSpacePercent(-500f);
//                        bars.add(Bardataset5);

                        BARDATA = new BarData(BarEntryLabels, bars);
                        chart.getAxisRight().setDrawGridLines(false);
                        chart.getAxisLeft().setDrawGridLines(false);
                        chart.getXAxis().setDrawGridLines(false);
                        chart.setDescription("");
                        chart.getAxisRight().setAxisMaxValue(100f);
                        chart.getAxisRight().setAxisMinValue(0f);
                        chart.getAxisLeft().setAxisMaxValue(100f);
                        chart.getAxisLeft().setAxisMinValue(0f);
                        chart.getXAxis().setAvoidFirstLastClipping(true);
                        chart.setData(BARDATA);
                        chart.animateY(3000);
                    }
                } else {
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getContext(), "Internet Connection Error: Please try again.", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
