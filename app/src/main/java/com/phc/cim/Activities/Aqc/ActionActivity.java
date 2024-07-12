package com.phc.cim.Activities.Aqc;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.phc.cim.Activities.Common.AboutusActivity;
import com.phc.cim.Activities.Common.ChangePasswordActivity;
import com.phc.cim.Activities.Common.DesealListing;
import com.phc.cim.Activities.Common.FilterActivity;
import com.phc.cim.Activities.Common.HearingStatusActivity;
import com.phc.cim.Activities.Common.QuackActivity;
import com.phc.cim.Activities.GalleryActivity;
import com.phc.cim.Activities.Common.IndReportingActivity;
import com.phc.cim.Activities.Common.ReportQuackActivity;
import com.phc.cim.Activities.Licensing.PWSFilterActivity;
import com.phc.cim.DataElements.ActionType;
import com.phc.cim.DataElements.CouncilType;
import com.phc.cim.DataElements.PracticingType;
import com.phc.cim.DataElements.subActionType;
import com.phc.cim.Extra.HomeFragment;
import com.phc.cim.Extra.NotificationFragment;
import com.phc.cim.Extra.PhotosFragment;
import com.phc.cim.Extra.SettingFragment;
import com.phc.cim.Extra.VideosFragment;
import com.phc.cim.Managers.DataManager;
import com.phc.cim.Managers.DatabaseManager;
import com.phc.cim.Others.Config;
import com.phc.cim.Others.CurrentLocation;
import com.phc.cim.Others.Logout;
import com.phc.cim.R;
import com.phc.cim.TabsActivities.DashboardTabs;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPSClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;
import static com.phc.cim.TabsActivities.DetailTabActivity.MEDIA_TYPE_IMAGE;
import static com.squareup.okhttp.internal.Internal.instance;


public class ActionActivity extends AppCompatActivity {


    CurrentLocation gps;
    double cur_latitude;


    double cur_longitude;

    TextInputLayout regNoInput;
    TextInputLayout counTypeInput;
    TextInputLayout counNoInput;
    TextInputLayout Reg_No_lay;
    TextInputLayout fir_layout;
    private Boolean picTaken = false;
    private Boolean picreceved = false;
    private Boolean picUploaded = false;
    TimePickerDialog timePickerDialog;
    int currentHour;
    int currentMinute;
    String amPm;
    private Boolean picAttachement = false;
    Context context;
    int count = 0;
    int backcount = 0;
    private String filePath = null;
    String directoryPath = null;
    private Uri filePathURI;
    String visitDate;
    String LoctionVisitedTime = "";
    String actionTypeText;
    String actionTypeID;
    String subactionTypeText;
    String subactionTypeID;
    int selectedSubactionTypeID;
    String firtext;
    String imageNAme = null;
    String imagepath;
    String firID = "0";
    String subactiontseltext;
    String actiontseltext;
    Spinner actionType_spinner;
    Spinner subactionType_spinner, practicingType_spinner;
    Spinner counciltypespiner;
    Spinner fir_spinner;
    ArrayList<ActionType> actionTypeList = null;
    ArrayList<subActionType> subactionType = null;
    ArrayList<ActionType> actionTypeselected = null;
    ArrayList<subActionType> subactionTypeselected = null;
    ArrayList<CouncilType> councilTypes;
    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_MOVIES = "videos";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;
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
    private Uri u = null;
    String appURI = "";
    String time1;
    String MID;
    String MText;
    String MID2;
    String MText2;
    String ftpbaseurl, ftpUser, ftpPas;
    String hce_nameText = "";

    String Reg_NoText = "";
    String coun_NoText = "";
    String final_id = "";
    String visited;
    String UploadedBy;

    String counciltypetext = "";
    String counciltypeID = "";
    String email = null;
    String password;
    String isEdit;
    String username;
    String VisitedTime = "";


    String index;
    String firactionbit;
    String jsonStr0;
    String VisitedDate;
    String isFIRSubmit;
    EditText coun_NoEdit;
    EditText hce_nameEdit;
    EditText Reg_no_edit;
    EditText loctimePickerEdit;
    DataManager dataManager;
    TextView totalpics;
    TextView newquacktext;
    TextView vistedtext;
    int countPictures = 0;
    private DownloadManager downloadManager;
    private long downloadReference;
    int MY_REQUEST_CODE = 5;
    private static final int RequestPermissionCode = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private ArrayList<String> _images;

    public static int navItemIndex = -1;

    ArrayList<HashMap<String, String>> mylist;
    ProgressDialog pDialog;
    String jsonStr;
    String jsonStr2;

    EditText txtDateTime;
    Calendar myCalendar;
    EditText comments;
    TextView errortext;
    String commentText, UserID;
    int actionposition;
    int subactionposition;
    int firposition;
    int councilposition;
    Bundle saveinstance;
    String roleid;

    ArrayAdapter<String> actionadapter;
    int MarkSurvCount = 0;
    private RequestQueue requestQueue;
    String selectedSubactionTypeIDString;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_action);
        context = this;


        pDialog = new ProgressDialog(context);
        actionType_spinner = (Spinner) findViewById(R.id.actionType_spinner);
        subactionType_spinner = (Spinner) findViewById(R.id.subactionType_spinner);
        practicingType_spinner = (Spinner) findViewById(R.id.practicingType_spinner);
        counciltypespiner = (Spinner) findViewById(R.id.counType_spinner);
        fir_spinner = (Spinner) findViewById(R.id.fir_spinner);
        txtDateTime = (EditText) findViewById(R.id.txtDateTime);
        loctimePickerEdit = (EditText) findViewById(R.id.loctimePicker);

        hce_nameEdit = (EditText) findViewById(R.id.hce_name);
        coun_NoEdit = (EditText) findViewById(R.id.council_no);
        Reg_no_edit = (EditText) findViewById(R.id.Reg_no_edit);
        totalpics = (TextView) findViewById(R.id.totalpics);
        newquacktext = (TextView) findViewById(R.id.newquacktext);
        vistedtext = (TextView) findViewById(R.id.vistedtext);
        errortext = (TextView) findViewById(R.id.errortext);

        comments = (EditText) findViewById(R.id.comments);
        newquacktext.setVisibility(View.GONE);
        dataManager = new DataManager(context);
        myCalendar = Calendar.getInstance(TimeZone.getDefault());
        if (comments.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        vistedtext.setVisibility(View.GONE);
        Intent intent = getIntent();
        errortext.setVisibility(View.GONE);

        VisitedDate = (String) intent.getSerializableExtra("VisitedDate");
        actionTypeID = (String) intent.getSerializableExtra("ActionType");
        subactionTypeID = (String) intent.getSerializableExtra("SubActionType");
        isFIRSubmit = (String) intent.getSerializableExtra("isFIRSubmit");
        commentText = (String) intent.getSerializableExtra("Comments");
        final_id = (String) intent.getSerializableExtra("final_id");
        hce_nameText = (String) intent.getSerializableExtra("HCEName");
        Reg_NoText = (String) intent.getSerializableExtra("RegNum");
        counciltypetext = (String) intent.getSerializableExtra("CouncilName");
        coun_NoText = (String) intent.getSerializableExtra("CouncilNum");
        email = (String) intent.getSerializableExtra("email");
        password = (String) intent.getSerializableExtra("password");
        username = (String) intent.getSerializableExtra("username");
        isEdit = (String) intent.getSerializableExtra("isEdit");
        index = (String) intent.getSerializableExtra("index");
        UploadedBy = (String) intent.getSerializableExtra("UploadedBy");
        visited = (String) intent.getSerializableExtra("visited");
        VisitedTime = (String) intent.getSerializableExtra("VisitedTime");
//        MarkSurvCount = (String) intent.getSerializableExtra("MarkSurvCount");
        _images = (ArrayList<String>) getIntent().getSerializableExtra("imageurls");

        // Initialize the RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Make the API call
        callApi();

        if (commentText != null) {
            comments.setText(commentText);
        }
        if (isFIRSubmit != null) {
            if (isFIRSubmit.equals("1")) {
                firtext = "Yes";
            }
            if (isFIRSubmit.equals("0")) {
                firtext = "No";
            }

        }
        if (visited != null && visited.equals("1")) {
            vistedtext.setVisibility(View.VISIBLE);
            vistedtext.setText("Last visited by: " + UploadedBy);
        }
        if (VisitedTime != null)
            loctimePickerEdit.setText(VisitedTime);

        if (index.equals("0")) {
            newquacktext.setVisibility(View.VISIBLE);
        }
        regNoInput = (TextInputLayout) findViewById(R.id.reg);
        counTypeInput = (TextInputLayout) findViewById(R.id.counclType);
        counNoInput = (TextInputLayout) findViewById(R.id.council);
        fir_layout = (TextInputLayout) findViewById(R.id.fir_layout);
        Reg_No_lay = (TextInputLayout) findViewById(R.id.Reg_No_lay);


        hce_nameEdit.setEnabled(false);
        if (visited != null && visited.equals("1")) {
            hce_nameEdit.setText(index + ". " + hce_nameText);
        } else {
            hce_nameEdit.setText(index + ". " + final_id + " - " + hce_nameText);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gps = new CurrentLocation(context);
        mHandler = new Handler();
        TextView t2 = (TextView) findViewById(R.id.text2);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);


        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        regNoInput.setVisibility(View.GONE);
        counTypeInput.setVisibility(View.GONE);
        counNoInput.setVisibility(View.GONE);
        Reg_No_lay.setVisibility(View.GONE);
        fir_layout.setVisibility(View.GONE);

        // load toolbar titles from string resources
        activityTitles = getResources().getString(R.string.nav_item_action_titles);
        ftpbaseurl = context.getResources().getString(R.string.ftpbaseurl);
        ftpUser = context.getResources().getString(R.string.ftpUser);
        ftpPas = context.getResources().getString(R.string.ftpPass);
        loadNavHeader();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, 42);
        }
        checkPermission();
        EnableRuntimePermission();
        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }

        if (coun_NoText != null) {
            coun_NoEdit.setText(coun_NoText);
        }
        if (Reg_NoText != null) {
            Reg_no_edit.setText(Reg_NoText);
        }


        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        roleid = prefs.getString("RoleID", null);//"No name defined" is the default value.
        UserID = prefs.getString("UserID", null);//"No name defined" is the default value.
        String isStat = prefs.getString("isStat", null);
        if (roleid.equals("1")) {
            navigationView.getMenu().findItem(R.id.nav_actiondesc).setVisible(true);
            if (isStat.equals("true")) {
                navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(true);


            } else {
                navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(false);
            }
        } else {
            navigationView.getMenu().findItem(R.id.nav_actiondesc).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_actionsummary).setVisible(false);
        }
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub


                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (VisitedDate != null && VisitedDate != "0") {
            String start_dt = VisitedDate;
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            Date date2 = null;
            try {
                date2 = (Date) formatter.parse(start_dt);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yy");
            String finalString = "";
            if (date2 != null)
                finalString = newFormat.format(date2);
            txtDateTime.setText(finalString);
        }
        txtDateTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        // initialize all your visual fields
        if (savedInstanceState != null) {
            actionType_spinner.setSelection(savedInstanceState.getInt("actionType_spinner", 0));
            // do this for each of your text views
        }
        loctimePickerEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            hourOfDay = hourOfDay - 12;
                            if (hourOfDay == 00) {
                                hourOfDay = 12;
                            }
                            amPm = "PM";
                        } else {
                            if (hourOfDay == 00) {
                                hourOfDay = 12;
                            }
                            amPm = "AM";
                        }
                        loctimePickerEdit.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });
        actionTypeList = dataManager.getActionstype(roleid);
        actionadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getactionTypes()) {
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

                if (position == 0) {
                    // Set the hint text color gray
                    //tv.setTextColor(Color.GRAY);
                } else {
                    // tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        actionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionType_spinner.setAdapter(actionadapter);
        if (actionTypeID != null) {
            getactionsel();
            int spinnerPosition = actionadapter.getPosition(actiontseltext);
            actionType_spinner.setSelection(spinnerPosition);
        }
        actionType_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                actionType_spinner.setSelection(position);
                actionTypeText = parent.getItemAtPosition(position).toString();
                actionTypeID = actionTypeList.get(position).getActionType_Id();
                firactionbit = actionTypeList.get(position).getFIRAssociated();
                if (firactionbit.equals("1")) {
                    fir_layout.setVisibility(View.VISIBLE);
                } else {
                    fir_layout.setVisibility(View.GONE);
                }
                getsubactionTypes(MarkSurvCount);
//                getsubactionTypes();
                getPracticingTypes();
                if (comments.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //---------------------------------counciltype Spinner--------------------------------------------

        councilTypes = dataManager.getCouncilTypes();
        ArrayAdapter<String> counciltype_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getCouncilTypes()) {
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

                if (position == 0) {
                    // Set the hint text color gray
                    //tv.setTextColor(Color.GRAY);
                } else {
                    // tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        counciltype_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        counciltypespiner.setAdapter(counciltype_spinneradapter);
        if (counciltypetext != null) {
            int spinnerPosition = counciltype_spinneradapter.getPosition(counciltypetext);
            counciltypespiner.setSelection(spinnerPosition);
        }
        counciltypespiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                councilposition = position;
                counciltypetext = parent.getItemAtPosition(position).toString();
                counciltypeID = councilTypes.get(position).getSectorType_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //---------------------------------fir_spinner Spinner--------------------------------------------

        String FIRsubmitted[] = {"Please Select", "Yes", "No"};
        //councilTypes= dataManager.getCouncilTypes();
        ArrayAdapter<String> fir_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, FIRsubmitted) {
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

                if (position == 0) {
                    // Set the hint text color gray
                    //tv.setTextColor(Color.GRAY);
                } else {
                    // tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        fir_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fir_spinner.setAdapter(fir_spinneradapter);

        if (firtext != null) {
            int spinnerPosition = fir_spinneradapter.getPosition(firtext);
            fir_spinner.setSelection(spinnerPosition);
        }
        fir_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

                firposition = position;
                firtext = parent.getItemAtPosition(position).toString();
                if (firtext.equals("Yes")) {
                    firID = "1";
                } else if (firtext.equals("No")) {
                    firID = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (gps.canGetLocation()) {
         /*   pDialog.setMessage("Please wait....");
            pDialog.setCancelable(false);
            pDialog.show();*/
            cur_latitude = gps.getLatitude();
            cur_longitude = gps.getLongitude();
            // latlangListener.onlatlang(cur_latitude, cur_longitude);
            if (cur_latitude != 0.0 && cur_longitude != 0.0) {

            } else {
                cur_latitude = gps.getLatitude();
                cur_longitude = gps.getLongitude();
            }
        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            gps.showSettingsAlert();


        }
        Button btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                int errorcount = 0;
                coun_NoText = coun_NoEdit.getText().toString();
                Reg_NoText = Reg_no_edit.getText().toString();

                visitDate = txtDateTime.getText().toString();
                LoctionVisitedTime = loctimePickerEdit.getText().toString();
                commentText = comments.getText().toString();
                if (visitDate.equals("")) {
                    txtDateTime.setError("Please select date");
                    errortext.setText("* Required fields are missing");
                    errorcount++;
                }
                if (LoctionVisitedTime.equals("")) {
                    loctimePickerEdit.setError("Please select time");
                    errortext.setText("* Required fields are missing");
                    errorcount++;

                }
                if (actionTypeID.equals("0")) {
                    setSpinnerError(actionType_spinner, ("Please select Action type"));
                    errortext.setText("* Required fields are missing");
                    errorcount++;
                }
                if (subactionTypeID.equals("0")) {
                    setSpinnerError(subactionType_spinner, ("Please select Quack type"));
                    errortext.setText("* Required fields are missing");
                    errorcount++;
                }
                else if (subactionTypeID.equals("9")) {
                    if ((counciltypeID.equals("0") && !coun_NoText.equals("")) || (!counciltypeID.equals("0") && coun_NoText.equals("")) || (counciltypeID.equals("0") && coun_NoText.equals(""))) {
                        errortext.setText("Please enter council type and council number both");
                        errorcount++;
                    }
                }
                if (errorcount < 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Do you want to save the changes?")
                            .setTitle("Save")
                            .setCancelable(false)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    pDialog.setMessage("Loading Data, Please wait...");
                                    pDialog.setCancelable(false);
                                    pDialog.show();

                                    if (counciltypeID.equals("0")) {
                                        counciltypetext = "";
                                    }
                                    String url = getDirectionsUrl0(context);
                                    DownloadTask0 downloadTask = new DownloadTask0();
                                    //Start downloading json data from Google Directions API
                                    downloadTask.execute(url);


                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    errortext.setVisibility(View.VISIBLE);
                }
            }
        });
//        Button camera = (Button) findViewById(R.id.take_pic);
//
//        camera.setOnClickListener(new Button.OnClickListener() {
//
//
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= 24) {
//                    try {
//                        Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
//                        m.invoke(null);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//                count = 2;
//                requestRuntimePermission();
//                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
//                String currentDateandTime = sdf.format(new Date());
//                String pictureName = final_id + "_" + currentDateandTime;//here you can get picture name from user. I supposed Test name
//                Intent intentcamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                File photo = new File(context.getExternalFilesDir(null), pictureName + ".jpg");//save picture (.jpg) on SD Card
//                u = Uri.fromFile(photo);
//                intentcamera.putExtra(MediaStore.EXTRA_OUTPUT, u);
//                intentcamera.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                filePath = photo.getAbsolutePath();
//                startActivityForResult(intentcamera, REQUEST_CODE);
//
//            }
//        });

        Button camera = findViewById(R.id.take_pic);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRuntimePermission();
            }
        });

        Button attachment = (Button) findViewById(R.id.attach_pic);

        attachment.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {

                count = 1;
                requestRuntimePermission();
                Intent attachintent = new Intent();
                attachintent.setType("image/*");
                attachintent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(attachintent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });
        Button gallery_button = (Button) findViewById(R.id.gallery_but);

        gallery_button.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                startGalleryActivity();
            }
        });
    }



    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        txtDateTime.setText(sdf.format(myCalendar.getTime()));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("actionType_spinner", actionType_spinner.getSelectedItemPosition());
        // do this for each or your Spinner
        // You might consider using Bundle.putStringArray() instead
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        savedInstanceState.getBoolean("actionType_spinner");

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//
//            case 1: {
//                if (requestCode == MY_REQUEST_CODE) {
//                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        // Now user should be able to use camera
//                    } else {
//                        // Your app will not have this permission. Turn off all functions
//                        // that require this permission or it will force close like your
//                        // original question
//                    }
//                }
//
//
//            }
//
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera and storage permissions are required to take photos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        count = 2;
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
        String currentDateandTime = sdf.format(new Date());
        String pictureName = final_id + "_" + currentDateandTime;
        Intent intentcamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(getExternalFilesDir(null), pictureName + ".jpg");
        u = Uri.fromFile(photo);
        intentcamera.putExtra(MediaStore.EXTRA_OUTPUT, u);
        intentcamera.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        filePath = photo.getAbsolutePath();
        startActivityForResult(intentcamera, REQUEST_CODE);
    }

    public void requestRuntimePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissionsNeeded = new ArrayList<>();

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(Manifest.permission.CAMERA);
            }

            if (!permissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        permissionsNeeded.toArray(new String[0]), 1);
            }
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        requestRuntimePermission();
        return Uri.fromFile(getOutputMediaFile(type));
    }

    // @RequiresApi(api = Build.VERSION_CODES.M)

    // public void requestRuntimePermission() {
    ////        if (Build.VERSION.SDK_INT >= 23) {
    ////
    ////            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    ////                    != PackageManager.PERMISSION_GRANTED) {
    ////                ActivityCompat.requestPermissions(this,
    ////                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    ////            }
    ////        }
    ////    }
//


    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        MY_REQUEST_CODE);
            }
        }


    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Config.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("", "Oops! Failed create "
                        + Config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    private void setSpinnerError(Spinner spinner, String error) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            //spinner.performClick(); // to open the spinner list if error is found.

        }
    }

    private void loadNavHeader() {
        // name, website

        txtName.setText(username);
        txtWebsite.setText(email);

    }


    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigatiokn menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            // toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        //toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }


    private HomeFragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
             /*   Intent i = new Intent(context, Login_Activity.class);
                startActivity(i);*/
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                // photos
                PhotosFragment photosFragment = new PhotosFragment();
                return photosFragment;
            case 2:
                // movies fragment
                VideosFragment videosFragment = new VideosFragment();
                return videosFragment;
            case 3:
                // notifications fragment
                NotificationFragment notificationsFragment = new NotificationFragment();
                return notificationsFragment;

            case 4:
                // settings fragment
                SettingFragment settingsFragment = new SettingFragment();
                return settingsFragment;
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(false);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_home:
                        startActivity(new Intent(context, FilterActivity.class).putExtra("email", email).putExtra("password", password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_reportquack:
                        startActivity(new Intent(context, ReportQuackActivity.class).putExtra("email", email).putExtra("password", password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_quack:
                        startActivity(new Intent(context, QuackActivity.class).putExtra("email", email).putExtra("password", password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_actiondesc:
                        startActivity(new Intent(context, IndReportingActivity.class).putExtra("email", email).putExtra("password", password).putExtra("username", username).putExtra("isEdit", isEdit));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_actionsummary:
                        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                        String isStat = prefs.getString("isStat", null);//"No name defined" is the default value.
                        String UserID = prefs.getString("UserID", null); //0 is the default value.
                        if (isStat.equals("true")) {
                            startActivity(new Intent(context, DashboardTabs.class).putExtra("email", email).putExtra("password", password).putExtra("username", username).putExtra("isEdit", isEdit));
                            drawer.closeDrawers();
                        } else {
                            Toast.makeText(context, "You are not authorised!", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    case R.id.nav_pwssearch:
                        startActivity(new Intent(context, PWSFilterActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_list:
                        startActivity(new Intent(context, DesealListing.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_hearing:
                        startActivity(new Intent(context, HearingStatusActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_resetPassword:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(context, ChangePasswordActivity.class).putExtra("email", email).putExtra("password", password));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        startActivity(new Intent(context, AboutusActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_Logout:
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure to exit CIM?")
                                .setTitle("Exit")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // launch new intent instead of loading fragment
                                        startActivity(new Intent(context, Logout.class));
                                        drawer.closeDrawers();
                                    }
                                })
                                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        return true;
                    //default:
                    //   navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (backcount == 1) {
            super.onBackPressed();
            return;
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Do you really want to navigate away from this screen? Changes made will not be effective")
                    .setTitle("Warning")
                    .setCancelable(false)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            backcount = 1;
                            onBackPressed();
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private ArrayList<String> getactionTypes() {

        ArrayList<String> actiontypelist = new ArrayList<String>();
        int i = 0;
        for (ActionType actionType : actionTypeList) {
            String actiontypelists = actionType.getActionType();
            actiontypelist.add(actiontypelists);
            i++;
        }
        return actiontypelist;
    }

    private ArrayList<String> getCouncilTypes() {

        ArrayList<String> counciltypelist = new ArrayList<String>();
        int i = 0;
        for (CouncilType councilType : councilTypes) {
            String counciltypelists = councilType.getCouncilType();
            counciltypelist.add(counciltypelists);
            i++;
        }
        return counciltypelist;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onResume();
        if (count == 2) {
            try {


                picTaken = true; //to ensure picture is taken
                picAttachement = false;
                pDialog = new ProgressDialog(context);
                pDialog.setMessage("Uploading image, Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
                upLoadPicture();
            } catch (Exception e) {

                e.printStackTrace();

            }
        } else if (count == 1) {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                // fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                //filePath= fileUri.getPath();
                filePathURI = data.getData();

                filePath = getRealPathFromUri(context, filePathURI);

                try {
                    picAttachement = true;
                    picTaken = false;
                    //getting image from gallery
                    //Bitmap bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(filePath));
                    pDialog = new ProgressDialog(context);
                    pDialog.setMessage("Uploading image, Please wait...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    upLoadPicture();
                    //Setting image to ImageView
                    //  mImageView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void upLoadPicture() {

        if (picTaken) {

            final ProgressDialog pd = ProgressDialog.show(context, "Please wait", "Uploading Picture ...", true);
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {

                    String extension = filePath.substring(filePath.lastIndexOf("."));
                    File file = new File(filePath);
                    FileInputStream fis = null;
                    // BufferedInputStream buffIn = null;
                    FTPSClient client = new FTPSClient("TLS", true);
                    try {
                        picUploaded = false;
                        imagepath = null;
                        imageNAme = null;
                        directoryPath = null;
                        picreceved = true;
                        try {
                            TrustManager[] trustManager = new TrustManager[]{new X509TrustManager() {
                                @Override
                                public X509Certificate[] getAcceptedIssuers() {
                                    return null;
                                }

                                @Override
                                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                                }

                                @Override
                                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                                }
                            }};

                            client.setTrustManager(trustManager[0]);
                            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                            kmf.init(null, null);
                            KeyManager km = kmf.getKeyManagers()[0];
                            client.setKeyManager(km);
                            client.setBufferSize(1024 * 1024);
                            client.setConnectTimeout(100000);
                            client.connect(InetAddress.getByName(ftpbaseurl), 990);
                            client.setSoTimeout(100000);

                            if (client.login(ftpUser, ftpPas)) {
                                client.execPBSZ(0);
                                client.execPROT("P");
                            }
                        } catch (SocketException e) {
                            Log.e("APPTAG", e.getStackTrace().toString());
                        } catch (UnknownHostException e) {
                            Log.e("APPTAG", e.getStackTrace().toString());
                        } catch (IOException e) {
                            Log.e("APPTAG", e.getStackTrace().toString());
                        } catch (Exception e) {
                            Log.e("APPTAG", e.getStackTrace().toString());
                        }

                        try {
                            client.setFileType(FTP.BINARY_FILE_TYPE);
                            // buffIn = new BufferedInputStream(new FileInputStream(file));
                            fis = new FileInputStream(file);
                            client.enterLocalPassiveMode();
                            client.makeDirectory("HCEImages/" + final_id); //I want to upload picture in MyPictures directory/folder. you can use your own.
                            client.makeDirectory("HCEImages/" + final_id + "/" + username);
                            client.makeDirectory("HCEImages/" + final_id + "/" + username + "/Camera");
                            directoryPath = "HCEImages/" + final_id + "/" + username + "/Camera";
                            //client.sendCommand("OPTS UTF8 ON");

                        } catch (Exception e) {
                            if (pDialog.isShowing())
                                pDialog.dismiss();
                            picreceved = false;
                            Toast.makeText(context, "Picture not found! Please try again", Toast.LENGTH_SHORT).show();
                            //client.makeDirectory("HCEImages/" + final_id); //I want to upload picture in MyPictures directory/folder. you can use your own.
                            //client.makeDirectory("HCEImages/" + final_id+"/Camera");

                        }

                        if (picreceved) {
                            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
                            String currentDateandTime = sdf.format(new Date());
                            imageNAme = final_id + "_" + currentDateandTime + extension;
                            imagepath = final_id + "/" + username + "/Camera/" + imageNAme;

                            try {
                                if (!client.storeFile("HCEImages/" + imagepath, fis))//this is actual file uploading on FtpServer in specified directory/folder
                                {
                                    throw new Exception("Unable to write file to FTP server");
                                }
                                //Make sure to always close the inputStream
                            } finally {
                                fis.close();
                            }
                            InputStream inputStream = client.retrieveFileStream("/" + directoryPath + "/" + imageNAme);
                            int returnCode = client.getReplyCode();
                            if (inputStream == null || returnCode == 550) {
                                picUploaded = false;
                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                Toast.makeText(context, "Picture not uploaded! Please try again", Toast.LENGTH_SHORT).show();
                            } else {
                                String url = getDirectionsUrl2(context);
                                DownloadTask2 downloadTask = new DownloadTask2();
                                //Start downloading json data from Google Directions API
                                downloadTask.execute(url);
                                countPictures++;
                            }
                            totalpics.setText("Total pictures uploaded: " + countPictures);
                        }

                        try {

                            //10 seconds to log off.  Also 10 seconds to disconnect.
                            client.setSoTimeout(10000);
                            client.logout();

                            //depending on the state of the server the .logout() may throw an exception,
                            //we want to ensure complete disconnect.
                        } catch (Exception innerException) {

                            //You potentially just want to log that there was a logout exception.

                        } finally {
                            //Make sure to always disconnect.  If not, there is a chance you will leave hanging sockects
                            client.disconnect();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (picUploaded) {


                    }
                }


            });
            thread.start();
            pd.dismiss();


        } else {
            // Toast.makeText(context, "Please Take Picture First than Upload.", Toast.LENGTH_LONG).show();

        }

        if (picAttachement) {

            final ProgressDialog pd = ProgressDialog.show(context, "Please wait", "Uploading Picture ...", true);
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {


                    String extension = filePath.substring(filePath.lastIndexOf("."));
                    File file = new File(filePath);
                    InputStream fis = null;
                    // BufferedInputStream buffIn = null;
                    FTPSClient client = new FTPSClient("TLS", true);
                    try {
                        picUploaded = false;
                        imagepath = null;
                        imageNAme = null;
                        directoryPath = null;
                        picreceved = true;
                        try {
                            TrustManager[] trustManager = new TrustManager[]{new X509TrustManager() {
                                @Override
                                public X509Certificate[] getAcceptedIssuers() {
                                    return null;
                                }

                                @Override
                                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                                }

                                @Override
                                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                                }
                            }};

                            client.setTrustManager(trustManager[0]);
                            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                            kmf.init(null, null);
                            KeyManager km = kmf.getKeyManagers()[0];
                            client.setKeyManager(km);
                            client.setBufferSize(1024 * 1024);
                            client.setConnectTimeout(100000);
                            client.connect(InetAddress.getByName(ftpbaseurl), 990);
                            client.setSoTimeout(100000);

                            if (client.login(ftpUser, ftpPas)) {
                                client.execPBSZ(0);
                                client.execPROT("P");
                            }
                        } catch (SocketException e) {
                            Log.e("APPTAG", e.getStackTrace().toString());
                        } catch (UnknownHostException e) {
                            Log.e("APPTAG", e.getStackTrace().toString());
                        } catch (IOException e) {
                            Log.e("APPTAG", e.getStackTrace().toString());
                        } catch (Exception e) {
                            Log.e("APPTAG", e.getStackTrace().toString());
                        }
                        try {
                            client.setFileType(FTP.BINARY_FILE_TYPE);
                            // buffIn = new BufferedInputStream(new FileInputStream(file));
                            Uri mUri = Uri.fromFile(file);
                            fis = getContentResolver().openInputStream(mUri);
                            client.enterLocalPassiveMode();
                            client.makeDirectory("HCEImages/" + final_id); //I want to upload picture in MyPictures directory/folder. you can use your own.
                            client.makeDirectory("HCEImages/" + final_id + "/" + username);
                            client.makeDirectory("HCEImages/" + final_id + "/" + username + "/Attachments");
                            directoryPath = "HCEImages/" + final_id + "/" + username + "/Attachments";
                        } catch (Exception e) {
                            if (pDialog.isShowing())
                                pDialog.dismiss();
                            picreceved = false;
                            Toast.makeText(context, "Picture not found! Please try again", Toast.LENGTH_SHORT).show();
                        }

                        if (picreceved) {
                            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
                            String currentDateandTime = sdf.format(new Date());
                            imageNAme = final_id + "_" + currentDateandTime + extension;
                            imagepath = final_id + "/" + username + "/Attachments/" + imageNAme;
                            try {
                                if (!client.storeFile("HCEImages/" + imagepath, fis))//this is actual file uploading on FtpServer in specified directory/folder
                                {
                                    throw new Exception("Unable to write file to FTP server");
                                }
                                //Make sure to always close the inputStream
                            } finally {
                                fis.close();
                            }
                            InputStream inputStream = client.retrieveFileStream("/" + directoryPath + "/" + imageNAme);
                            int returnCode = client.getReplyCode();
                            if (inputStream == null || returnCode == 550) {
                                picUploaded = false;
                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                Toast.makeText(context, "Picture not uploaded! Please try again", Toast.LENGTH_SHORT).show();
                            } else {
                                String url = getDirectionsUrl2(context);
                                DownloadTask2 downloadTask = new DownloadTask2();
                                //Start downloading json data from Google Directions API
                                downloadTask.execute(url);
                                countPictures++;
                            }
                            totalpics.setText("Total pictures uploaded: " + countPictures);
                        }
                        try {

                            //10 seconds to log off.  Also 10 seconds to disconnect.
                            client.setSoTimeout(10000);
                            client.logout();

                            //depending on the state of the server the .logout() may throw an exception,
                            //we want to ensure complete disconnect.
                        } catch (Exception innerException) {

                            //You potentially just want to log that there was a logout exception.

                        } finally {
                            //Make sure to always disconnect.  If not, there is a chance you will leave hanging sockects
                            client.disconnect();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (picUploaded) {


                    }
                }

            });
            thread.start();
            pd.dismiss();


        } else {
            //   Toast.makeText(context, "Please Take Picture First than Upload.", Toast.LENGTH_LONG).show();

        }
    }

    /**
     * Creating file uri to store image/video
     */
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private String getactionsel() {
        DatabaseManager databaseManager = new DatabaseManager(context);
        actionTypeselected = databaseManager.getActionselected(actionTypeID);
        if (actionTypeselected.size() > 0) {
            actiontseltext = actionTypeselected.get(0).getActionType();
        }
        return actiontseltext;
    }

    private String getsubactionsel() {
        DatabaseManager databaseManager = new DatabaseManager(context);
        subactionTypeselected = databaseManager.getsubActionselected(subactionTypeID);
        if (subactionTypeselected.size() > 0) {
            subactiontseltext = subactionTypeselected.get(0).getSubactionType();
        }
        return subactiontseltext;
    }



    private ArrayList<String> getsubactionTypes(int MarkSurvCount) {
        subactionType = dataManager.getsubActionstype(actionTypeID, roleid);
        ArrayList<String> subactiontypelist = new ArrayList<String>();

        // Populate the subactiontypelist and filter based on MarkSurvCount
        for (subActionType subActionType : subactionType) {
            String subactiontype = subActionType.getSubactionType();
            String subactionTypeID = subActionType.getSubActionType_Id(); // Assuming this method exists to get the ID
            if (MarkSurvCount <= 3 || !subactionTypeID.equals("16")) {
                subactiontypelist.add(subactiontype);
            }
        }

        ArrayAdapter<String> subactiontype_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, subactiontypelist) {
            @Override
            public boolean isEnabled(int position) {
                return true; // All items are enabled
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }
        };

        subactiontype_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subactionType_spinner.setAdapter(subactiontype_spinneradapter);

        if (subactionTypeID != null) {
            getsubactionsel();
            int spinnerPosition = subactiontype_spinneradapter.getPosition(subactiontseltext);
            subactionType_spinner.setSelection(spinnerPosition);
        }

        subactionType_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subactionTypeText = parent.getItemAtPosition(position).toString();
                subactionTypeID = subactionType.get(position).getSubActionType_Id();

                if (MarkSurvCount > 3 && subactionTypeID.equals("16")) {
                    // Do not allow selection of "16" if MarkSurvCount > 3
                    Toast.makeText(context, "Selection not allowed", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (subactionTypeID.equals("9")) {
                    counTypeInput.setVisibility(View.VISIBLE);
                    counNoInput.setVisibility(View.VISIBLE);
                    Reg_No_lay.setVisibility(View.VISIBLE);
                    if (comments.requestFocus()) {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    }
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                } else {
                    counTypeInput.setVisibility(View.GONE);
                    counNoInput.setVisibility(View.GONE);
                    Reg_No_lay.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        return subactiontypelist;
    }

    private ArrayList<PracticingType> getPracticingTypes() {
        ArrayList<PracticingType> subactiontypelist = new ArrayList<>();

        // Add hardcoded values with String ids
        subactiontypelist.add(new PracticingType("0", "Please Select"));
        subactiontypelist.add(new PracticingType("1", "Allopathy"));
        subactiontypelist.add(new PracticingType("2", "Dentistry"));
        subactiontypelist.add(new PracticingType("3", "Tibb"));
        subactiontypelist.add(new PracticingType("4", "Pharmacy"));
        subactiontypelist.add(new PracticingType("5", "Laboratory"));
        subactiontypelist.add(new PracticingType("6", "Dispensary"));
        subactiontypelist.add(new PracticingType("7", "Medical Store"));
        subactiontypelist.add(new PracticingType("8", "Gyne"));
        subactiontypelist.add(new PracticingType("9", "Hadi Jore"));
        subactiontypelist.add(new PracticingType("10", "Maternity home"));
        subactiontypelist.add(new PracticingType("11", "Homeopathic"));
        subactiontypelist.add(new PracticingType("12", "Other"));
        subactiontypelist.add(new PracticingType("13", "Pathology Lab"));
        subactiontypelist.add(new PracticingType("14", "Imaging Lab"));
        subactiontypelist.add(new PracticingType("15", "Pathology+Imaging Lab"));
        subactiontypelist.add(new PracticingType("16", "Collection Center"));
        subactiontypelist.add(new PracticingType("17", "Matab/Dawakhna"));
        subactiontypelist.add(new PracticingType("18", "Acupuncture"));
        subactiontypelist.add(new PracticingType("19", "Hair Transplant"));
        subactiontypelist.add(new PracticingType("20", "Cosmetic Surgery Clinic"));
        subactiontypelist.add(new PracticingType("21", "Addiction Treatment Center"));
        subactiontypelist.add(new PracticingType("22", "Dialysis Clinic"));

        ArrayAdapter<PracticingType> subactiontype_spinneradapter = new ArrayAdapter<PracticingType>(context, android.R.layout.simple_spinner_item, subactiontypelist) {
            @Override
            public boolean isEnabled(int position) {
                return true; // All items are enabled
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                return view;
            }
        };

        subactiontype_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        practicingType_spinner.setAdapter(subactiontype_spinneradapter);

        practicingType_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PracticingType selectedType = (PracticingType) parent.getItemAtPosition(position);
                subactionTypeText = selectedType.getDescription();
                String selectedSubactionTypeIDString = selectedType.getId();

                if ("0".equals(selectedSubactionTypeIDString)) {
                    // Handle "Please Select" case by setting subactionTypeText to null
                    subactionTypeText = null;
                } else {
                    subactionTypeText = selectedType.getDescription();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Set initial selection to "Please Select"
        practicingType_spinner.setSelection(0);

        return subactiontypelist;
    }







    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {

            // Toast.makeText(EditHCEFragment.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(this, new String[]{

                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    public void startGalleryActivity() {
    /*    ArrayList<String> images = new ArrayList<String>();
        images.add("http://sourcey.com/images/stock/salvador-dali-metamorphosis-of-narcissus.jpg");
        images.add("http://sourcey.com/images/stock/salvador-dali-the-dream.jpg");
        images.add("http://202.166.167.123:8081/phc_images/78_2015_08_05_10_41_03_.jpg");
        images.add("http://sourcey.com/images/stock/simpsons-persistance-of-memory.jpg");
        images.add("http://sourcey.com/images/stock/salvador-dali-the-great-masturbator.jpg");*/
        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putStringArrayListExtra(GalleryActivity.EXTRA_NAME, _images);
        startActivity(intent);
    }

    private class DownloadTask0 extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl0(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask0 parserTask = new ParserTask0();
            jsonStr0 = result;
            // Invokes the thread for parsing the JSON data
            parserTask.execute();

        }
    }

    private String getDirectionsUrl0(Context context) {

        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String token = context.getResources().getString(R.string.token);
//        SaveHCEImage
        String url = baseurl + "GetPlanID?strToken=" + token + "&FinalID=" + final_id + "&ImagePath=&ImageUploadMode=Camera&Lat=" + cur_latitude + "&Lng=" + cur_longitude + "&UploadedBy=&emailAddress=" + email + "&ActionType=" + actionTypeID + "&ActionSubType=" + subactionTypeID + "&PicCount=" + countPictures + "&Council=" + counciltypetext + "&CouncilNo=" + coun_NoText + "&isFIR=" + firID + "&phcRegNo=" + Reg_NoText + "&visitedDate=" + visitDate + "&comments=" + commentText + "&QuackCategory=&QuackSubCategory=&RoleID=" + roleid;
        url = url.replaceAll(" ", "%20");
        return url;
    }

    private String downloadUrl0(String strUrl) throws IOException {


        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {

            //Post Request
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            int statusCode = urlConnection.getResponseCode();


            if (statusCode >= 200 && statusCode < 400) {
                // Create an InputStream in order to extract the response object
                iStream = urlConnection.getInputStream();
            } else {
                iStream = urlConnection.getErrorStream();
            }


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

    private class ParserTask0 extends AsyncTask<Object, Object, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog


        }

        @Override
        protected String doInBackground(Object... arg0) {

            // Making a request to url and getting response

            if (jsonStr0 != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr0);

                    MID = jsonObj.getString("MID");
                    MText = jsonObj.getString("MText");
                    return MID;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("exception", "Couldn't get json from server.");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                if (MID.equals("1") && !UserID.equals("5")) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            context).create();

                    // Setting Dialog Title
                    //alertDialog.setTitle("Success");

                    // Setting Dialog Message
                    alertDialog.setMessage("Please review visit date as it is outside your assigned plans");
                    // Setting Icon to Dialog
                    //  alertDialog.setIcon(R.drawable.tick);

                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog closed
                            //finish();
                            //  Toast.makeText(context, "Thanks You!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                } else if (MID.equals("0")) {
                    Toast.makeText(context, "Service is not responding", Toast.LENGTH_SHORT).show();
                } else {

                    String url = getDirectionsUrl(context);
                    DownloadTask downloadTask = new DownloadTask();
                    //Start downloading json data from Google Directions API
                    downloadTask.execute(url);

                }
                // Updating parsed JSON data into ListView

            } else {
                Toast.makeText(context, "Server is not responding. Please try again", Toast.LENGTH_SHORT).show();
            }


        }

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

    private String getDirectionsUrl(Context context) {

        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String token = context.getResources().getString(R.string.token);
        String url = baseurl + "SaveHCEImage?strToken=" + token + "&FinalID=" + final_id + "&ImagePath=&ImageUploadMode=Camera&Lat=" + cur_latitude + "&Lng=" + cur_longitude + "&UploadedBy=&emailAddress=" + email + "&ActionType=" + actionTypeID + "&ActionSubType=" + subactionTypeID + "&PicCount=" + countPictures + "&Council=" + counciltypetext + "&CouncilNo=" + coun_NoText + "&isFIR=" + firID + "&phcRegNo=" + Reg_NoText + "&visitedDate=" + visitDate + "&comments=" + commentText + "&QuackCategory=&QuackSubCategory=&RoleID=" + roleid + "&VisitedTime=" + LoctionVisitedTime;
        url = url.replaceAll(" ", "%20");
        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {


        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {

            //Post Request
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            int statusCode = urlConnection.getResponseCode();


            if (statusCode >= 200 && statusCode < 400) {
                // Create an InputStream in order to extract the response object
                iStream = urlConnection.getInputStream();
            } else {
                iStream = urlConnection.getErrorStream();
            }


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
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    MID = jsonObj.getString("MID");
                    MText = jsonObj.getString("MText");
                    return MID;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("exception", "Couldn't get json from server.");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (result != null) {
                if (MID.equals("1")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            context).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Success");

                    // Setting Dialog Message
                    if (actionTypeID.equals("1") || actionTypeID.equals("3")) {
                        alertDialog.setMessage("Action against '" + hce_nameText + "' is recorded, please make sure to write its Reference ID '" + final_id + "' on seal report");
                    } else {
                        alertDialog.setMessage("Action against " + hce_nameText + " is recorded");
                    }

                    // Setting Icon to Dialog
                    //  alertDialog.setIcon(R.drawable.tick);
                    //

                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog closed

                            finish();

                            //  Toast.makeText(context, "Thanks You!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                } else {


                    Toast.makeText(context, "Update not submitted! Please Verify" + result, Toast.LENGTH_SHORT).show();

                }
                // Updating parsed JSON data into ListView

            } else {
                Toast.makeText(context, "Server is not responding. Please try again", Toast.LENGTH_SHORT).show();
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

    private String getDirectionsUrl2(Context context) {

        // Building the url to the web service
        String baseurl = context.getResources().getString(R.string.baseurl);
        String token = context.getResources().getString(R.string.token);
        String url = baseurl + "UploadHCEImage?strToken=" + token + "&FinalID=" + final_id + "&ImagePath=" + imagepath + "&emailAddress=" + email + "&visitedDate=&RoleID=" + roleid;
        url = url.replaceAll(" ", "%20");
        return url;

    }

    private String downloadUrl2(String strUrl) throws IOException {


        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {

            //Post Request
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            int statusCode = urlConnection.getResponseCode();


            if (statusCode >= 200 && statusCode < 400) {
                // Create an InputStream in order to extract the response object
                iStream = urlConnection.getInputStream();
            } else {
                iStream = urlConnection.getErrorStream();
            }


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

    private class ParserTask2 extends AsyncTask<Object, Object, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog


        }

        @Override
        protected String doInBackground(Object... arg0) {

            // Making a request to url and getting response

            if (jsonStr2 != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr2);

                    MID2 = jsonObj.getString("MID");
                    MText2 = jsonObj.getString("MText");
                    return MID2;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("exception", "Couldn't get json from server.");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (comments.requestFocus()) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            if (result != null) {
                if (MID2.equals("1")) {
                    Toast.makeText(context, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                    String imagebasepath = context.getResources().getString(R.string.Imagesbaseurl);
                    _images.add(imagebasepath + imagepath);
                    imagepath = null;

                } else {
                    Toast.makeText(context, "Update not submitted! Please Verify", Toast.LENGTH_SHORT).show();

                }
                // Updating parsed JSON data into ListView

            } else {
                Toast.makeText(context, "Server is not responding. Please try again", Toast.LENGTH_SHORT).show();
            }


        }

    }
    private void callApi() {
        // Construct the API URL with the final_id parameter
        String url = "https://census.phc.org.pk:51599/api/Allocation/GetSurveillanceCount?FinalID=" + final_id;

        // Show progress dialog
        pDialog.setMessage("Loading...");
        pDialog.show();

        // Create the JsonObjectRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Hide progress dialog
                        pDialog.dismiss();

                        try {
                            // Parse response JSON
                            JSONArray surveillanceCounts = response.getJSONArray("SurveillanceCount");
                            if (surveillanceCounts.length() > 0) {
                                JSONObject surveillanceCountObject = surveillanceCounts.getJSONObject(0);
                                MarkSurvCount = surveillanceCountObject.getInt("MarkForSurveillanceCount");
                            } else {
                                Toast.makeText(context, "Surveillance count data is not available", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Hide progress dialog
                        pDialog.dismiss();

                        // Handle the error
                        Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }



}
