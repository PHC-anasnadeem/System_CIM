package com.phc.cim.Activities.Common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.phc.cim.Managers.WebApiManager;
import com.phc.cim.R;
import com.trncic.library.DottedProgressBar;

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

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private static final String LOG_TAG = "AppUpgrade";
    private String[] activityTitles;
    private View mProgressView;
    String appURI = "";
    String time1;

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
    //private PrefManager prefManager;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_MOVIES = "videos";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    public static String CURRENT_TAG = TAG_HOME;
    String GeneralRemarks;
    String MobileAppVerCode;
    String MobileAppVerName;
    String OutDated;
    String PKID;
    String PortNo;
    String Remarks;
    String UploadDate;
    // toolbar titles respected to selected nav menu item
    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    ArrayList<HashMap<String, String>> mylist;
    private TextView mTextMessage;
    Context context;
    Activity activity;
    private Spinner spinner1, spinner2;
    private Button btnSubmit;
    private static int SPLASH_TIME_OUT = 2000;
    int count=0;
     String versionName = "";
     int versionCode = -1;
    String jsonStr = null;
    Button tryagain;
    DottedProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_main);
        context = this;

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity (in this case, Login_Activity)
//                startActivity(new Intent(MainActivity.this, Login_Activity.class));
//
//                // Close this activity
//                finish();
//            }
//        }, SPLASH_TIME_OUT);

        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            // Activity was brought to front and not created,
            // Thus finishing this will get us to the last viewed activity
            finish();
            return;
        }
        getVersionInfo();
        progressBar = (DottedProgressBar) findViewById(R.id.progress);
        progressBar.startProgress();
        mTextMessage = (TextView)  findViewById(R.id.connection);
        mTextMessage.setVisibility(View.GONE);
        tryagain = (Button) findViewById(R.id.pass_button);
        tryagain.setVisibility(View.GONE);
        tryagain.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                mTextMessage.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                tryagain.setVisibility(View.GONE);
                progressBar.startProgress();
                                              final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                              final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                                              boolean isConnected = networkInfo != null &&
                                                      networkInfo.isConnectedOrConnecting();

                                              if(isConnected)
                                              {
                                                  if(count<16) {
                                                      for(int i=0; i<16; i++) {

                                                          WebApiManager webApiManager = new WebApiManager(context, count);
                                                          count++;
                                                      }
                                                  }
                                                  String url = getDirectionsUrl();
                                                  DownloadTask downloadTask = new DownloadTask();
                                                  //Start downloading json data from Google Directions API
                                                  downloadTask.execute(url);
                                                 //  if(networkInfo.getType()== ConnectivityManager.TYPE_MOBILE)

                                              }

                                              else{
                                                  progressBar.stopProgress();
                                                  progressBar.setVisibility(View.GONE);
                                                  mTextMessage.setVisibility(View.VISIBLE);
                                                  tryagain.setVisibility(View.VISIBLE);
                                                 // Toast.makeText(context, "Internet connection unavailable", Toast.LENGTH_SHORT).show();

                                              }
                                          }
                                      });


                final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                boolean isConnected = networkInfo != null &&
                        networkInfo.isConnectedOrConnecting();

                if (isConnected) {

                    if (count < 16) {
                        for (int i = 0; i < 16; i++) {
                            synchronized (MainActivity.class) {
                                if (count < 16) {
                                    WebApiManager webApiManager = new WebApiManager(context, count);

                                    count++;
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                    String url = getDirectionsUrl();
                    DownloadTask downloadTask = new DownloadTask();
                    //Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                } else {
                    progressBar.stopProgress();
                    progressBar.setVisibility(View.GONE);
                    mTextMessage.setVisibility(View.VISIBLE);
                    tryagain.setVisibility(View.VISIBLE);
                    //Toast.makeText(context, "Internet connection unavailable", Toast.LENGTH_SHORT).show();

                }






    }
    private void getVersionInfo() {

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
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

    private String getDirectionsUrl() {
        String baseURL=  getString(R.string.baseurl);
        String url=baseURL+"GetMobileAppVer?appVer="+versionName;
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
                        map.put("GeneralRemarks", e.getString("GeneralRemarks"));
                        map.put("MobileAppVerCode", e.getString("MobileAppVerCode"));
                        map.put("MobileAppVerName", e.getString("MobileAppVerName"));
                        map.put("OutDated", e.getString("OutDated"));
                        map.put("PKID", e.getString("PKID"));
                        map.put("PortNo", e.getString("PortNo"));
                        map.put("Remarks", e.getString("Remarks"));
                        map.put("UploadDate", e.getString("UploadDate"));

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


            if(result!=null) {
                String status = null;
                for (int i = 0; i < result.size(); i++) {
                    GeneralRemarks = result.get(i).get("GeneralRemarks");
                    MobileAppVerCode = result.get(i).get("MobileAppVerCode");
                    MobileAppVerName= result.get(i).get("MobileAppVerName");
                    OutDated = result.get(i).get("OutDated");
                    PKID = result.get(i).get("PKID");
                    PortNo= result.get(i).get("PortNo");
                    Remarks = result.get(i).get("Remarks");
                    UploadDate = result.get(i).get("UploadDate");

                }
          /*      if(OutDated.equals("false")){
                 *//*   new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {*//*
                            // This method will be executed once the timer is over
                            // Start your app main activity
                            progressBar.stopProgress();
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this,
                                    Login_Activity.class);

                            MainActivity.this.startActivity(intent);
                         MainActivity.this.finish();
                            //  mProgressView.setVisibility( View.GONE);
                            // Intent i = new Intent(context, Login_Activity.class);
                            //  startActivity(i);

                            // close this activity
                           // finish();
                *//*        }
                    }, SPLASH_TIME_OUT);*//*
                }*/
                 if(OutDated.equals("true")) {
                    progressBar.stopProgress();
                     AlertDialog.Builder builder = new AlertDialog.Builder(context);
                     builder.setMessage(GeneralRemarks)
                             .setTitle("Version Update")
                             .setCancelable(false)
                             .setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int id) {
                                     Intent intent = new Intent(Intent.ACTION_VIEW);
                                     intent.setData(Uri.parse("market://details?id=com.phc.phc"));
                                     try{
                                         startActivity(intent);
                                         MainActivity.this.finish();
                                     }
                                     catch(Exception e){
                                         intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.phc.cim"));
                                         MainActivity.this.finish();
                                     }
                                 }
                             })
                             .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int id) {
                                     dialog.cancel();
                                     MainActivity.this.finish();
                                 }
                             });
                     AlertDialog alert = builder.create();
                     alert.show();
                }
                else if(OutDated.equals("false")) {
                    progressBar.stopProgress();
                    if (GeneralRemarks.equals("null")) {
                        Intent intent = new Intent();

                       intent.setClass(MainActivity.this,
                                Login_Activity.class);

                        MainActivity.this.startActivity(intent);
                        MainActivity.this.finish();
                        finish();
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(GeneralRemarks)
                                .setTitle("Version Update")
                                .setCancelable(false)
                                .setPositiveButton("Update Now", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse("market://details?id=com.phc.cim"));
                                        try{
                                            startActivity(intent);
                                            MainActivity.this.finish();
                                        }
                                        catch(Exception e){
                                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.phc.cim"));
                                            MainActivity.this.finish();
                                        }
                                    }
                                })
                                .setNegativeButton("Update Later", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        Intent intent = new Intent();
                                        intent.setClass(MainActivity.this,
                                                Login_Activity.class);

                                        MainActivity.this.startActivity(intent);
                                        MainActivity.this.finish();
                                        finish();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }




                }
                else {
              Toast.makeText(context, "Server not responding! Please try again", Toast.LENGTH_SHORT).show();
                progressBar.stopProgress();
                progressBar.setVisibility(View.GONE);
                tryagain.setVisibility(View.VISIBLE);
            }


        }

    }
}
    /* // mTextMessage = (TextView) findViewById(R.id.message);
      Button button= (Button) findViewById(R.id.Next);
      BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
      navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
      button.setOnClickListener(new View.OnClickListener() {

          @Override
          public void onClick(View v) {

              Intent loginactivity= new Intent(context,Login_Activity.class);
              //mTextMessage.setText(R.string.title_dashboard);
              context.startActivity(loginactivity);
               *//*       Vist_Memb_Fragment blankFragment = new Vist_Memb_Fragment();  //this is your new fragment.
                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        fragmentManager.beginTransaction().add(R.id.container, blankFragment).addToBackStack(null).commit();*//*
               // addItemsOnSpinner2();
             //   addListenerOnButton();
             //   addListenerOnSpinnerItemSelection();

            }
        });
    }
    // add items into spinner dynamically
    public void addItemsOnSpinner2() {

       // spinner2 = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("list 1");
        list.add("list 2");
        list.add("list 3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        //spinner1 = (Spinner) findViewById(R.id.spinner1);
       // spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

       // spinner1 = (Spinner) findViewById(R.id.spinner1);
      // spinner2 = (Spinner) findViewById(R.id.spinner);
       // btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(context,
                        "OnClickListener : " +
                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) +
                                "\nSpinner 2 : "+ String.valueOf(spinner2.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    IntentIntegrator intentIntegrator=new IntentIntegrator(activity);
                    intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                    intentIntegrator.setPrompt("scan");
                    intentIntegrator.setCameraId(0);
                    intentIntegrator.setBeepEnabled(false);
                    intentIntegrator.setBarcodeImageEnabled(false);
                    intentIntegrator.initiateScan();
                    // firstpage.putExtra("stop",result);
                    // firstpage.putExtra("des_latitude",getDes_latitude());
                    // firstpage.putExtra("des_longitude",getDes_longitude());


                    //mapsActivity.startActivity(firstpage);

                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    LocateUsFragment locateUsFragment = new LocateUsFragment();  //this is your new fragment.
                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction().add(R.id.container, locateUsFragment).addToBackStack(null).commit();
                    return true;
                case R.id.navigation_notifications:
                    FAQsFragment faqFragment = new FAQsFragment();  //this is your new fragment.
                    FragmentManager fragmentManager1 = ((FragmentActivity) context).getSupportFragmentManager();
                    fragmentManager1.beginTransaction().add(R.id.container, faqFragment).addToBackStack(null).commit();

                    return true;
            }
            return false;
        }


    };
*/

