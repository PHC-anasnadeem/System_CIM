package com.phc.cim.Fragments;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.RequiresApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.cim.Adapters.HigherPlanSummAdapter;
import com.phc.cim.DataElements.ActionType;
import com.phc.cim.DataElements.CouncilType;
import com.phc.cim.DataElements.subActionType;
import com.phc.cim.Managers.DataManager;
import com.phc.cim.Others.CurrentLocation;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class HigherPlanSummFragment extends Fragment {

    CurrentLocation gps;
    double cur_latitude;
    double cur_longitude;

    TextInputLayout regNoInput;
    TextInputLayout counTypeInput;
    TextInputLayout counNoInput;
    TextInputLayout Reg_No_lay;
    TextInputLayout fir_layout;
    private Boolean picTaken=false;
    private Boolean picreceved=true;
    private Boolean picAttachement=false;

    int count=0;
    int backcount=0;
    private String filePath=null;
    private Uri filePathURI;
    String visitDate;
    String actionTypeText;
    String actionTypeID;
    String subactionTypeText;
    String subactionTypeID;
    String firtext;
    String firID="0";
    String subactiontseltext;
    String actiontseltext;
    Spinner actionType_spinner;
    Spinner subactionType_spinner;
    Spinner counciltypespiner;
    Spinner fir_spinner;
    ArrayList<ActionType> actionTypeList = null;
    ArrayList<subActionType> subactionType = null;
    ArrayList<ActionType> actionTypeselected = null;
    ArrayList<subActionType> subactionTypeselected=null;
    ArrayList<CouncilType> councilTypes;
    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_MOVIES = "videos";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;
    // toolbar titles respected to selected nav menu item
    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private static final String LOG_TAG = "AppUpgrade";
    private String activityTitles;
    private int versionCode = 0;
    int PICK_IMAGE_REQUEST = 111;
    private Uri u=null;
    String appURI = "";
    String time1;
    String MID;
    String MText;
    String MID2;
    String MText2;
    Switch simpleSwitch1;
    String hce_nameText="";
    //  String AddressText="";
    // String HCSP_nameText="";
    // String HCSP_SOText="";
    //  String CNIC_Text="";
    //   String HCSP_ContactText="";
    String Reg_NoText="";
    String coun_NoText="";
    String  final_id="";
    String visited;
    String UploadedBy;
    //  String districtText="";
    //String sectortypetext="";
    //  String hceTypetext="";
//    String HCSPTypeText="";
    //   String RegstatusText="";
//    String counStatusText="";
    String counciltypetext="";
    String counciltypeID="";
    //    String RegType="";
    String email=null;
    String password;
    String isEdit;
    String username;
    //    String RecordLockedForUpdate="";
//    String total_beds="";
    String index;
    String PlanTitle;

    String  VisitedDate;
    String isFIRSubmit;
    // double latitude;
    //  double longitude;
    EditText coun_NoEdit;
    EditText hce_nameEdit;
    EditText Reg_no_edit;
    DataManager dataManager;
    TextView totalpics;
    TextView newquacktext;
    TextView vistedtext;
    int countPictures=0;
    private DownloadManager downloadManager;
    private long downloadReference;
    int MY_REQUEST_CODE=5;
    private ArrayList<String> _images;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;
    //private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    //private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";
    public static int navItemIndex = -1;

    ArrayList<HashMap<String, String>> mylist;
    ProgressDialog pDialog;
    String jsonStr;
    String jsonStr2;
    String imagepath,PlanStartDate,PlanEndDate;
    EditText txtDateTime;
    Calendar myCalendar;
    EditText comments;
    String commentText;
    Spinner date_spinner;
    int actionposition;
    int subactionposition;
    int firposition;
    int councilposition;
    Bundle saveinstance;
    String strtdatetext, enddatetext,Vistdate;
    ListView listView;
    TextView hce_name;
    String RoleID;
    ArrayAdapter<String> actionadapter;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.allteam_vists_list, container, false);


        pDialog = new ProgressDialog(getContext());
        listView = (ListView) rootView.findViewById(R.id.list);
       // hce_name = (TextView) rootView.findViewById(R.id.hce_name);
        date_spinner = (Spinner)  rootView.findViewById(R.id.date_spinner);

        final Bundle args = getArguments();

        email= args.getString("email");
        password = args.getString("password");
        username = args.getString("username");
        isEdit = args.getString("isEdit");
        PlanTitle = args.getString("PlanTitle");
        PlanStartDate= args.getString("PlanStartDate");
        PlanEndDate= args.getString("PlanEndDate");
        gps = new CurrentLocation(getContext());
        TextView t2 = (TextView) rootView.findViewById(R.id.text2);
        SharedPreferences prefs = getContext().getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String isStat = prefs.getString("isStat", null);//"No name defined" is the default value.
        RoleID = prefs.getString("RoleID", null); //0 is the default value.

        String ackwardDate = PlanStartDate;
        String ackwardRipOff = ackwardDate.replace("/Date(", "").replace("+0500", "").replace(")/", "");
        Long timeInMillis = Long.valueOf(ackwardRipOff);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        Date d= new Date(timeInMillis);
        strtdatetext=new SimpleDateFormat("dd-MMM-yyyy").format(calendar.getTime()).toString();

        String ackwardDate2 = PlanEndDate;
        String ackwardRipOff2 = ackwardDate2.replace("/Date(", "").replace("+0500", "").replace(")/", "");
        Long timeInMillis2 = Long.valueOf(ackwardRipOff2);
        calendar.setTimeInMillis(timeInMillis2);
        Date d2= new Date(timeInMillis2);
        enddatetext=new SimpleDateFormat("dd-MMM-yyyy").format(calendar.getTime()).toString();
        //hce_name.setText(PlanTitle);
        Date stdate = new Date(timeInMillis);
        Date endate = new Date(timeInMillis2);
        ArrayAdapter<String> dateadapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getDatesBetween(stdate,endate)) {
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the spinner collapsed item (non-popup item) as a text view
                TextView tv = (TextView) super.getView(position, convertView, parent);

                // Set the text color of spinner item
                //  tv.setTextColor(Color.WHITE);

                // Return the view
                return tv;
            }


            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return true;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) super.getDropDownView(position,convertView,parent);

                // Set the text color of drop down items
           /*     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    tv.setTextColor(Color.BLACK);
                }else {
                    tv.setTextColor(Color.WHITE);
                }
*/
                if (position == 0) {
                    // Set the hint text color gray
                /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        tv.setTextColor(Color.BLACK);
                    }else {
                        tv.setTextColor(Color.WHITE);
                    }*/
                } else {
                    //convertView.setBackgroundColor(Color.WHITE);
                    //((TextView) parent.getChildAt(position)).setTextColor(Color.WHITE);
                /*    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        tv.setTextColor(Color.BLACK);
                    }else {
                        tv.setTextColor(Color.WHITE);
                    }*/
                }
                return tv;
            }
        };
        dateadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
   /*     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            date_spinner.setPopupBackgroundResource(R.drawable.spinner);
        }*/
        date_spinner.setAdapter(dateadapter);
        date_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                Vistdate = parent.getItemAtPosition(position).toString();
                DateFormat formatter;
                if (Vistdate != null && Vistdate!="All") {
                    formatter = new SimpleDateFormat("dd-MMM-yyyy");
                    Date date2 = null;
                    try {
                        date2 = (Date) formatter.parse(Vistdate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    SimpleDateFormat newFormat = new SimpleDateFormat("M/d/yyyy");
                    if (date2 != null)
                        Vistdate = newFormat.format(date2);

                }
                pDialog.setMessage("Loading Data, Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
                String url = getDirectionsUrl(Vistdate);
                DownloadTask downloadTask = new DownloadTask();
                //Start downloading json data from Google Directions API
                downloadTask.execute(url);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return rootView;
    }

    public ArrayList<String> getDatesBetween(
            Date startDate, Date endDate) {

        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);
        ArrayList<String> counciltypelist = new ArrayList<String>();
        counciltypelist.add("All");
        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-yyyy");
            String finalString = "";
            if (result != null)
                finalString = newFormat.format(result);
            counciltypelist.add(finalString);
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        counciltypelist.add(enddatetext);
        return counciltypelist;
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

    private String getDirectionsUrl(String vsdate) {

        // Building the url to the web service
        String baseurl = getContext().getResources().getString(R.string.baseurl);
        String token= getContext().getResources().getString(R.string.token);
        String url =null;
        if(vsdate.equals("All")) {
            url = baseurl + "GetLatestVisitsPlanTitleWise?strToken=" + token + "&strDataTitle=" + PlanTitle+"&RoleID="+RoleID;
        }
        else {
            url = baseurl + "GetLatestVisitsPlanTitle_DateWise?strToken=" + token + "&strDataTitle="+PlanTitle+"&VisitDate="+vsdate+"&RoleID="+RoleID;
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
                        map.put("index", String.valueOf(i+1));
                        map.put("ClosedSealedTotal", e.getString("ClosedSealedTotal"));
                        map.put("District", e.getString("District"));
                        map.put("DistrictID", e.getString("DistrictID"));
                        map.put("FunctionalSealedTotal", e.getString("FunctionalSealedTotal"));
                        map.put("NotSealedTotal", e.getString("NotSealedTotal"));
                        map.put("PlanID", e.getString("PlanID"));
                        map.put("Team", e.getString("Team"));
                        map.put("TeamNo", e.getString("TeamNo"));
                        map.put("TotalFIR", e.getString("TotalFIR"));
                        map.put("TotalImages", e.getString("TotalImages"));
                        map.put("TotalVisits", e.getString("TotalVisits"));
                        map.put("PlanStartDate", e.getString("PlanStartDate"));
                        map.put("PlanEndDate", e.getString("PlanEndDate"));
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
            if(result!=null && result.size()>0) {
                HigherPlanSummAdapter adapter = new HigherPlanSummAdapter(getContext(), result,hce_nameText, email, password, username, isEdit,PlanTitle);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
            else {

                Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();

            }
        }

    }

}
