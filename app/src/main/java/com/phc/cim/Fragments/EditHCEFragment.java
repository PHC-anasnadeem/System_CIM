package com.phc.cim.Fragments;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.phc.cim.Adapters.MyCustomPagerAdapter;
import com.phc.cim.DataElements.Action;
import com.phc.cim.DataElements.CouncilType;
import com.phc.cim.DataElements.District;
import com.phc.cim.DataElements.OrgType;
import com.phc.cim.DataElements.SectorType;
import com.phc.cim.DataElements.UpdateStatL1;
import com.phc.cim.DataElements.UpdateStatL2;
import com.phc.cim.Managers.DataManager;


import com.phc.cim.Others.CurrentLocation;

import com.phc.cim.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class EditHCEFragment extends Fragment {


    DataManager dataManager;
    String MID;
    String MText;
    String jsonStr;

    CurrentLocation gps;
    EditText hce_nameEdit;
    EditText AddressEdit;
    EditText HCSP_nameEdit;
    EditText HCSP_SOEdit;
    EditText CNIC_Edit;
    EditText HCSP_ContactEdit;
    EditText Reg_NoEdit;
    EditText coun_NoEdit;
    EditText coments;
    TextInputLayout regNoInput;
    TextInputLayout counTypeInput;
    TextInputLayout counNoInput;
    TextInputLayout substatusInput;
    Spinner district_spinner;
    Spinner sectortypespinner;
    Spinner hcetypespinner;
    Spinner HCSP_spinner;
    Spinner regStatus_spinner;
    Spinner counStatus_spinner;
    Spinner counciltypespiner;
    Spinner quackloc_spinner;
    Spinner currloc_spinner;
    String currloc_text;
    String currloc_ID;
    String hce_nameText="";
    String AddressText="";
    String HCSP_nameText="";
    String HCSP_SOText="";
    String CNIC_Text="";
    String HCSP_ContactText="";
    String Reg_NoText="";
    String coun_NoText="";
    String comnt="";
    String  final_id="";
    String districtText="";
    String sectortypetext="";
    String hceTypetext="";
    String HCSPTypeText="";
    String RegstatusText="";
    String counStatusText="";
    String counciltypetext="";
    String counStatusID="";
    String RegstatusID="";
    String email= "";
    String password;
    String isEdit;
    String username;
    String quackloctext;
    String quacklocID;
    String distCurrPrevInMeters;
    double cur_latitude;
    double cur_longitude;
    ProgressDialog pDialog;
    ArrayList<District> districts;
    ArrayList<SectorType> sectorTypes;
    ArrayList<CouncilType> councilTypes;
    ArrayList<OrgType> orgTypes;
    ArrayList<UpdateStatL1> updateStatL1s;
    ArrayList<UpdateStatL2> updateStatL2s;
    ArrayList<Action> actions;
    String RoleID,UserID,isStat;
    TextView vistedtext;
    // Camera activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 70;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public  static final int RequestPermissionCode  = 1 ;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
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
    String date1="";
    String date2="";
    String appURI = "";
    String time1;
    MyCustomPagerAdapter myCustomPagerAdapter;
    String index;
    String UserName,LastVisitedDate;
    private ArrayList<String> imageurls;
    double latitude;
    double longitude;
    int countt;
    int backcount=0;
    private DownloadManager downloadManager;
    private long downloadReference;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;
    //private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    //private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";
    public static int navItemIndex = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View obj_view = inflater.inflate(R.layout.activity_comments, container,false);

        pDialog = new ProgressDialog(getContext());
        dataManager = new DataManager(getContext());
        gps = new CurrentLocation(getContext());
        hce_nameEdit = (EditText) obj_view.findViewById(R.id.hce_name);
        AddressEdit = (EditText) obj_view.findViewById(R.id.Address);
        HCSP_nameEdit = (EditText) obj_view.findViewById(R.id.HCSP_Name);
        HCSP_SOEdit = (EditText) obj_view.findViewById(R.id.HCSP_SO);
        CNIC_Edit = (EditText) obj_view.findViewById(R.id.CNIC);
        HCSP_ContactEdit = (EditText) obj_view.findViewById(R.id.Mobile);
        Reg_NoEdit = (EditText) obj_view.findViewById(R.id.reg_no);
        coun_NoEdit = (EditText) obj_view.findViewById(R.id.council_no);
        coments = (EditText) obj_view.findViewById(R.id.comments);
        quackloc_spinner = (Spinner) obj_view.findViewById(R.id.quackloc_spinner);
        currloc_spinner = (Spinner) obj_view.findViewById(R.id.curloc_spinner);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // district_spinner = (Spinner) findViewById(R.id.district);
        sectortypespinner = (Spinner) obj_view.findViewById(R.id.Sector_Type);
        hcetypespinner = (Spinner) obj_view.findViewById(R.id.hcetypespinner);
        HCSP_spinner = (Spinner) obj_view.findViewById(R.id.HCSP_Typespinner);
        regStatus_spinner = (Spinner) obj_view.findViewById(R.id.registration_spinner);
        counStatus_spinner = (Spinner) obj_view.findViewById(R.id.council_spinner);
        counciltypespiner = (Spinner) obj_view.findViewById(R.id.counType_spinner);

        regNoInput = (TextInputLayout) obj_view.findViewById(R.id.reg);
        counTypeInput= (TextInputLayout) obj_view.findViewById(R.id.counclType);
        counNoInput = (TextInputLayout) obj_view.findViewById(R.id.council);
        substatusInput = (TextInputLayout) obj_view.findViewById(R.id.substatus);
        vistedtext = (TextView) obj_view.findViewById(R.id.vistedtext);

        final Bundle args = getArguments();
        hce_nameText = args.getString("HCEName");
        AddressText = args.getString("HCEAddress");
        districtText = args.getString("District");
        sectortypetext = args.getString("SectorType");
        hceTypetext = args.getString("OrgType");
        HCSPTypeText = args.getString("HCSPType");
        HCSP_nameText = args.getString("HCSPName");
        HCSP_SOText = args.getString("HCSPSO");
        CNIC_Text = args.getString("HCSPCNIC");
        HCSP_ContactText = args.getString("HCSPContactNo");
        RegstatusText = args.getString("RegStatus");
        Reg_NoText =args.getString("RegNum");
        counStatusText = args.getString("CouncilStatus");
        counciltypetext = args.getString("CouncilName");
        coun_NoText = args.getString("CouncilNum");
        final_id = args.getString("final_id");
        UserName= args.getString("UserName");
        LastVisitedDate = args.getString("LastVisitedDate");
        latitude = args.getDouble("latitude");
        longitude = args.getDouble("longitude");



        SharedPreferences prefs = getContext().getSharedPreferences("MyPrefsFile", MODE_PRIVATE);

        isStat = prefs.getString("isStat", null);//"No name defined" is the default value.
        email = prefs.getString("email", null);//"No name defined" is the default value.
        password = prefs.getString("password", null);//"No name defined" is the default value.
        username = prefs.getString("username", null);//"No name defined" is the default value.
        isEdit = prefs.getString("isEdit", null);//"No name defined" is the default value.
        RoleID = prefs.getString("RoleID", null); //0 is the default value.
        UserID= prefs.getString("UserID", null); //0 is the default value.


        hce_nameEdit.setText(hce_nameText);
        AddressEdit.setText(AddressText);
        HCSP_nameEdit.setText(HCSP_nameText);
        HCSP_SOEdit.setText(HCSP_SOText);
        CNIC_Edit.setText(CNIC_Text);
        HCSP_ContactEdit.setText(HCSP_ContactText);
        Reg_NoEdit.setText(Reg_NoText);
        coun_NoEdit.setText(coun_NoText);

        regNoInput.setVisibility(View.GONE);
        counTypeInput.setVisibility(View.GONE);
        counNoInput.setVisibility(View.GONE);

        if(LastVisitedDate.equals("null")) {
            vistedtext.setText("Last updated by: "+UserName);

        }
        else {
            String ackwardDate = LastVisitedDate;
            Calendar calendar = Calendar.getInstance();
            String ackwardRipOff = ackwardDate.replace("/Date(", "").replace("+0500", "").replace(")/", "");
            Long timeInMillis = Long.valueOf(ackwardRipOff);
            calendar.setTimeInMillis(timeInMillis);
            String date = new SimpleDateFormat("dd-MMM-yyyy").format(calendar.getTime()).toString();
            vistedtext.setText("Last updated by: "+UserName +", Dated: "+ date);
        }
        //-------------------------Current Location----------------------------
        if (gps.canGetLocation()) {
            cur_latitude = gps.getLatitude();
            cur_longitude = gps.getLongitude();
            // latlangListener.onlatlang(cur_latitude, cur_longitude);
            if (cur_latitude != 0.0 && cur_longitude != 0.0) {

            }
            else {
                cur_latitude = gps.getLatitude();
                cur_longitude = gps.getLongitude();


            }
        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            gps.showSettingsAlert();
        }
        //---------------------------------you are at outlet location Spinner--------------------------------------------
        String quackloc[]={"Please Select","Yes","No"};
        ArrayAdapter<String> quackloc_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, quackloc) {
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
        quackloc_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quackloc_spinner.setAdapter(quackloc_adapter);
        quackloc_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                quackloctext = parent.getItemAtPosition(position).toString();
                if(quackloctext.equals("Yes")){
                    quacklocID="1";
                }
                else if(quackloctext.equals("No")){
                    quacklocID="0";
                }
                else {
                    quacklocID="-1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //---------------------------------current loc is correct Spinner--------------------------------------------
        String currloc[]={"Please Select","Yes","No"};
        ArrayAdapter<String> currloc_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, currloc) {
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
        currloc_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currloc_spinner.setAdapter(currloc_adapter);
        currloc_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                currloc_text = parent.getItemAtPosition(position).toString();
                if(currloc_text.equals("Yes")){
                    currloc_ID="1";
                }
                else if(currloc_text.equals("No")){
                    currloc_ID="0";
                }
                else {
                    currloc_ID="-1";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //---------------------------------District Spinner--------------------------------------------
/*        String Division="Please Select";
        districts= dataManager.getDistrictList(Division);

        ArrayAdapter<String> district_spinneradapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getDistrict()) {
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
        district_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        district_spinner.setAdapter(district_spinneradapter);
        if (districtText != null) {
            int spinnerPosition = district_spinneradapter.getPosition(districtText);
            district_spinner.setSelection(spinnerPosition);
        }
        district_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                districtText = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        //---------------------------------Sector Type Spinner--------------------------------------------

        sectorTypes= dataManager.getSectorTypes();
        ArrayAdapter<String> sector_spinneradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getsectorTypes()) {
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
        sector_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sectortypespinner.setAdapter(sector_spinneradapter);
        if (sectortypetext != null) {
            int spinnerPosition = sector_spinneradapter.getPosition(sectortypetext);
            sectortypespinner.setSelection(spinnerPosition);
        }
        sectortypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                sectortypetext = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---------------------------------HCE Type Spinner--------------------------------------------
        orgTypes= dataManager.getOrgTypesList();

        ArrayAdapter<String> hcetype_spinneradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getOrgTypes()) {
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
        hcetype_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hcetypespinner.setAdapter(hcetype_spinneradapter);
        if (hceTypetext != null) {
            int spinnerPosition = hcetype_spinneradapter.getPosition(hceTypetext);
            hcetypespinner.setSelection(spinnerPosition);
        }
        hcetypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                hceTypetext = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---------------------------------HCSP Type Spinner--------------------------------------------
        String[] HCSP_Type=
                {"Please Select", "Owner", "Incharge", "Manager"};
        ArrayAdapter<String> HCSP_spinneradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, HCSP_Type) {
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
        HCSP_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        HCSP_spinner.setAdapter(HCSP_spinneradapter);
        if (HCSPTypeText != null) {
            int spinnerPosition = HCSP_spinneradapter.getPosition(HCSPTypeText);
            HCSP_spinner.setSelection(spinnerPosition);
        }
        HCSP_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                HCSPTypeText = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---------------------------------regStatus Spinner--------------------------------------------

        String[] regStatus =
                {"Please Select", "Yes", "No"};
        ArrayAdapter<String> regStatus_spinneradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, regStatus) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
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
        regStatus_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regStatus_spinner.setAdapter(regStatus_spinneradapter);
        if (RegstatusText != null) {
            int spinnerPosition = regStatus_spinneradapter.getPosition(RegstatusText);
            regStatus_spinner.setSelection(spinnerPosition);
        }
        regStatus_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                RegstatusText = parent.getItemAtPosition(position).toString();
                if(RegstatusText.equals("Yes")){
                    regNoInput.setVisibility(View.VISIBLE);
                }
                else {
                    regNoInput.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---------------------------------counStatus Spinner--------------------------------------------
        final String[] counStatus =
                {"Please Select", "Yes", "No"};

        ArrayAdapter<String> counStatus_spinneradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, counStatus) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
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
        counStatus_spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        counStatus_spinner.setAdapter(counStatus_spinneradapter);
        if (counStatusText != null) {
            int spinnerPosition = counStatus_spinneradapter.getPosition(counStatusText);
            counStatus_spinner.setSelection(spinnerPosition);
        }
        counStatus_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                counStatusText = parent.getItemAtPosition(position).toString();
                if(counStatusText.equals("Yes")){
                    counTypeInput.setVisibility(View.VISIBLE);
                    counNoInput.setVisibility(View.VISIBLE);
                }
                else {
                    counTypeInput.setVisibility(View.GONE);
                    counNoInput.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //---------------------------------counciltype Spinner--------------------------------------------

        councilTypes= dataManager.getCouncilTypes();
        ArrayAdapter<String> counciltype_spinneradapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getCouncilTypes()) {
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
                //  ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);

                counciltypetext = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button markasdone = (Button) obj_view.findViewById(R.id.btn_submit);
        markasdone.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                Location loc1 = new Location("");
                loc1.setLatitude(latitude);
                loc1.setLongitude(longitude);

                Location loc2 = new Location("");
                loc2.setLatitude(cur_latitude);
                loc2.setLongitude(cur_longitude);
                distCurrPrevInMeters = String.valueOf(loc1.distanceTo(loc2));



                hce_nameText = hce_nameEdit.getText().toString();
                AddressText = AddressEdit.getText().toString();
                HCSP_nameText = HCSP_nameEdit.getText().toString();
                HCSP_SOText = HCSP_SOEdit.getText().toString();
                CNIC_Text=CNIC_Edit.getText().toString();
                HCSP_ContactText = HCSP_ContactEdit.getText().toString();
                Reg_NoText = Reg_NoEdit.getText().toString();
                coun_NoText = coun_NoEdit.getText().toString();
//                noticeno_text = noticeno_Edit.getText().toString();
                int count=0;
                comnt = coments.getText().toString();

                if(hce_nameText.equals("")){
                    hce_nameEdit.setError("Please enter name");
                    count++;
                }
                if(AddressText.equals("")){
                    AddressEdit.setError("Please enter address ");
                    count++;
                }
                if(HCSP_nameText.equals("")){
                    HCSP_nameEdit.setError("Please enter name");
                    count++;
                }
              /*  if (districtText.equals("Please Select")) {
                    setSpinnerError(district_spinner,("Please select district"));
                    count++;
                }*/
            /*    if (sectortypetext.equals("Please Select")) {
                    setSpinnerError(sectortypespinner,("Please select sector type"));
                    count++;
                }
                if (hceTypetext.equals("Please Select")) {
                    setSpinnerError(hcetypespinner,("Please select HCE type"));
                    count++;
                }*/
                if (quackloctext.equals("Please Select")) {
                    setSpinnerError(quackloc_spinner,("Please select"));
                    count++;
                }
                if (currloc_text.equals("Please Select")) {
                    setSpinnerError(currloc_spinner,("Please select"));
                    count++;
                }

             /*   if (comnt.equals("")) {
                    coments.setError("Please write your comments");
                    count++;
                }*/
                if(count<1) {

           /*          AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                     builder1.setMessage("Your slected location is "+distanceInMeters+" away from previous location./n Are you sure you want to proceed?")
                             .setTitle("Alert")
                             .setCancelable(false)
                             .setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int id) {


                                 }
                             })
                             .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int id) {
                                     dialog.cancel();

                                 }
                             });
                     AlertDialog alert = builder1.create();
                     alert.show();*/

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Do you want to save the changes?")
                            .setTitle("Save")
                            .setCancelable(false)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    if(counStatusText.equals("No"))  {
                                        coun_NoText = "";
                                        counciltypetext = "";
                                        counStatusID="0";

                                    }
                                    if(RegstatusText.equals("No")) {
                                        Reg_NoText = "";
                                        RegstatusID="0";
                                    }
                                    if(counStatusText.equals("Yes"))  {

                                        counStatusID="1";

                                    }
                                    if(RegstatusText.equals("Yes")) {
                                        RegstatusID="1";
                                    }
                                    if(districtText.equals("Please Select")){
                                        districtText="";
                                    }
                                    if(sectortypetext.equals("Please Select")){
                                        sectortypetext="";
                                    }
                                    if(hceTypetext.equals("Please Select")){
                                        hceTypetext="";
                                    }
                                    if(counciltypetext.equals("Please Select")){
                                        counciltypetext="";
                                    }
                                    if(HCSPTypeText.equals("Please Select")){
                                        HCSPTypeText="";
                                    }
                                    if(RegstatusText.equals("Please Select")){
                                        RegstatusText="";
                                    }
                                    if(counStatusText.equals("Please Select")){
                                        counStatusText="";
                                    }

                                    pDialog.setMessage("Submitting your update, Please wait...");
                                    pDialog.setCancelable(false);
                                    pDialog.show();
                                    String url = getDirectionsUrl(getContext());
                                    DownloadTask downloadTask = new DownloadTask();
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

                }


            }
        });
        Button canceldone = (Button) obj_view.findViewById(R.id.btn_cancel);
        canceldone.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {

                getActivity().onBackPressed();
            }
        });

return obj_view;

    }




    private void setSpinnerError(Spinner spinner, String error){
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
    private ArrayList<String> getOrgTypes() {

        ArrayList<String> orgtypelist = new ArrayList<String>();
        int i=0;
        for (OrgType orgType : orgTypes) {
            String orgTypess= orgType.getOrgType();
            orgtypelist.add(orgTypess);
            i++;
        }
        return orgtypelist;
    }
    private ArrayList<String> getCouncilTypes() {

        ArrayList<String> counciltypelist = new ArrayList<String>();
        int i=0;
        for (CouncilType councilType : councilTypes) {
            String counciltypelists= councilType.getCouncilType();
            counciltypelist.add(counciltypelists);
            i++;
        }
        return counciltypelist;
    }
    private ArrayList<String> getsectorTypes() {

        ArrayList<String> sectorTypeList = new ArrayList<String>();
        int i=0;
        for (SectorType sectorType : sectorTypes) {
            String sectortypelists= sectorType.getSectorType();
            sectorTypeList.add(sectortypelists);
            i++;
        }
        return sectorTypeList;
    }
    private ArrayList<String> getDistrict() {

        ArrayList<String> districtList = new ArrayList<String>();
        int i=0;
        for (District district : districts) {
            String districtLists= district.getDistrict();
            districtList.add(districtLists);
            i++;
        }
        return districtList;
    }

    private ArrayList<String> getUpdateStatus() {

        ArrayList<String> updatestatuslist = new ArrayList<String>();
        int i=0;
        for (UpdateStatL1 updateStatL1 : updateStatL1s) {
            String updatedStatL1ss= updateStatL1.getUpdatedStatL1();
            updatestatuslist.add(updatedStatL1ss);
            i++;
        }
        return updatestatuslist;
    }
    private ArrayList<String> getSubStatus() {

        ArrayList<String> substatuslist = new ArrayList<String>();
        int i=0;
        for (UpdateStatL2 updateStatL2 : updateStatL2s) {
            String substatLists= updateStatL2.getUpdatedStatL2();
            substatuslist.add(substatLists);
            i++;
        }
        return substatuslist;
    }
    private ArrayList<String> getActions() {

        ArrayList<String> actionslist = new ArrayList<String>();
        int i=0;
        for (Action action : actions) {
            String actionLists= action.getAction();
            actionslist.add(actionLists);
            i++;
        }
        return actionslist;
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
            jsonStr=result;
            // Invokes the thread for parsing the JSON data
            parserTask.execute();

        }
    }
    private String getDirectionsUrl(Context context) {

        // Building the url to the web service
        String baseurl=context.getResources().getString(R.string.baseurl);
        String token=context.getResources().getString(R.string.token);
        String url=baseurl+"UpdateHCEBasicInfo?strToken="+token+"&HCEName="+hce_nameText+"&HCEAddress="+AddressText+"&District="+districtText+"&SectorType="+sectortypetext +
                "&OrgType"+hceTypetext+"&HCSPType="+HCSPTypeText+"&HCSPName="+HCSP_nameText+"&HCSP_SO="+HCSP_SOText+"&HCSP_CNIC="+CNIC_Text+"&HCSPContactNo="+HCSP_ContactText+"&RegistrationNo="+Reg_NoText+
                "&RegistrationStatus="+RegstatusID+"&CouncilStatus="+counStatusID+"&CouncilNo="+coun_NoText+"&CouncilName="+counciltypetext+"&UpdateStatus=&UpdateSubStatus=&lat="+latitude+"&lng="+longitude+"&emailAddress=" +email+
                "&Comments="+comnt+"&final_id="+final_id+"&NoticeIssued=0&NoticeNo=&UpdateStatusID=0&UpdateSubStatusID=0&ActionID=0&RoleID=" +RoleID+
                "&UserLat="+cur_latitude+"&UserLng="+cur_longitude+"&CorrectLoc="+currloc_ID+"&CurrentLoc="+quacklocID+"&DistanceDiff=" +distCurrPrevInMeters;


        url = url.replaceAll(" ", "%20");
        url = url.replaceAll("#", "%23");
        url = url.replaceAll(",", "%2C");

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {


        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {

            //Post Request
            java.net.URL url = new URL(strUrl);
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

            }
            else {
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
                    //Toast.makeText(context, "Information update saved successfully", Toast.LENGTH_SHORT).show();
                    //finish();
                    //DownloadActionDetail downloadActionDetail = new DownloadActionDetail(context,hce_nameText, final_id, email, password, username, isEdit, index, countt);

                   AlertDialog alertDialog = new AlertDialog.Builder(
                            getContext()).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Success");

                    // Setting Dialog Message
                    alertDialog.setMessage("Your Comments has been submitted for detail review.");
                    // Setting Icon to Dialog
                    //  alertDialog.setIcon(R.drawable.tick);

                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
                            // Write your code here to execute after dialog closed
                            //    Toast.makeText(getContext(), "Thanks You!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                }
            }
            else{
                Toast.makeText(getContext(), "Server is not responding. Please try again", Toast.LENGTH_SHORT).show();
            }


        }

    }



}
