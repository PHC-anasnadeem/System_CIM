package com.phc.cim.Activities.Common;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.phc.cim.Activities.Inspection.InspectionFilterActivity;
import com.phc.cim.DataElements.Role;
import com.phc.cim.Managers.DataManager;
import com.phc.cim.R;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Login_Activity extends AppCompatActivity {




    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    String jsonStr = null;
    private ProgressDialog pDialog;
    private ListView lv;
    String email=null;
    String password;
    String isEdit;
    String username;
    String isStat,UserID;
    Spinner role_spinner;
    String roleText;
    String roleID;
    ArrayList<Role> roles;
    public static final String PREFS_NAME = "MyPrefsFile";
    ArrayList<HashMap<String, String>> mylist;
    Context context;
    Toolbar toolbar;
    int count=0;
    Set<String> emailset;
    DataManager dataManager;
    AutoCompleteTextView autocomplete;
    SharedPreferences.Editor emailEditor;
    ArrayList<HashMap<String, String>> contactList;
    String[] arr = { "Paries,France", "PA,United States","Parana,Brazil",
            "Padua,Italy", "Pasadena,CA,United States"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        context=this;

         emailEditor = getSharedPreferences("Emails", MODE_PRIVATE).edit();
        // Set up the login form.
       // mEmailView = (EditText) findViewById(R.id.email);
        autocomplete = (AutoCompleteTextView)
                findViewById(R.id.autoCompleteTextView1);


      this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mPasswordView = (EditText) findViewById(R.id.password);
        role_spinner= (Spinner) findViewById(R.id.roleSpinner);
        SharedPreferences prefs = getSharedPreferences("Emails", MODE_PRIVATE);
        emailset = prefs.getStringSet("emaillist", null);//"No name defined" is the default value.
        if(emailset!=null) {
            List<String> list = new ArrayList<String>(emailset);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this, android.R.layout.select_dialog_item, list);

            autocomplete.setThreshold(1);
            autocomplete.setAdapter(adapter);
        }
        dataManager=new DataManager(this);

        Button mEmailSignInButton = (Button) findViewById(R.id.login_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Store values at the time of the login attempt.
                int count=0;
             email = autocomplete.getText().toString();
               password = mPasswordView.getText().toString();

                if(TextUtils.isEmpty(email)) {
                    autocomplete.setError("Please enter email");
                    count++;
                    //return;
                }
                if(TextUtils.isEmpty(password)) {
                    mPasswordView.setError("Please enter password");
                    count++;
                    //return;
                }
                if(count<1) {
                    ArrayList<String> list = new ArrayList<String>();
                    list.add(email);
                    Set<String> set = new HashSet<String>();
                    set.addAll(list);
                    if(emailset!=null)
                    set.addAll(emailset);
                    emailEditor.putStringSet("emaillist", set);
                    emailEditor.apply();
                    mProgressView = findViewById(R.id.login_progress);
                    mProgressView.setVisibility(View.VISIBLE);
                    String url = getDirectionsUrl();
                    DownloadTask downloadTask = new DownloadTask();
                    //Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                }
            }
        });


////////////////////////////////////////////////district////////////////////////////////////////


        roles= dataManager.getRoles();
        ArrayAdapter<String> roleadapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getRoles()) {
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
        roleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role_spinner.setAdapter(roleadapter);
        role_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                roleText = parent.getItemAtPosition(position).toString();
                roleID=roles.get(position).getRole_Id();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
    private ArrayList<String> getRoles() {

        ArrayList<String> roleList = new ArrayList<String>();
        int i=0;
        for (Role role : roles) {
            String roleLists= role.getRoleName();
            roleList.add(roleLists);
            i++;
        }
        return roleList;
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
        String url=baseURL+"GetAccountInfo?username="+email+"&userpassword="+password+"&RoleID="+roleID;
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
                    JSONObject json = new JSONObject(jsonStr);
// ...
                    mylist = new ArrayList<HashMap<String, String>>();
                    for (int i = 0; i <json.length(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put("MID", json.getString("MID"));
                        map.put("MText", json.getString("MText"));
                        map.put("isEdit", json.getString("isEdit"));
                        map.put("isStat", json.getString("isStat"));
                        map.put("UserID", json.getString("UserID"));
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
            mProgressView.setVisibility( View.GONE);

            if(result!=null) {
                String status = null;
                for (int i = 0; i < result.size(); i++) {
                    status = result.get(i).get("MID");
                    isEdit = result.get(i).get("isEdit");
                    username= result.get(i).get("MText");
                    isStat= result.get(i).get("isStat");
                    UserID= result.get(i).get("UserID");
                }
                if (status.equals("1")) {
                   // count++;
                 /*   if(count<10) {
                        for(int i=0; i<10; i++) {

                            WebApiManager webApiManager = new WebApiManager(context, count);
                            count++;
                        }
                    }*/
                   // DataManager dataManager = new DataManager(context);
                    SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                    editor.clear().commit();
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.putString("username", username);
                    editor.putString("isEdit", isEdit);
                    editor.putString("isStat", isStat);
                    editor.putString("UserID", UserID);
                    editor.putString("RoleID",roleID);
                    editor.apply();
                    if (roleID.equals("3")){
                        Intent firstpage = new Intent(context, InspectionFilterActivity.class);
                        context.startActivity(firstpage);
                    }else {
                        Intent firstpage = new Intent(context, FilterActivity.class);
                        firstpage.putExtra("email", email);
                        firstpage.putExtra("password", password);
                        firstpage.putExtra("username", username);
                        firstpage.putExtra("isEdit", isEdit);
                        firstpage.putExtra("isStat", isStat);
                        firstpage.putExtra("UserID", UserID);
                        context.startActivity(firstpage);
                    }


                    // Intent firstpage = new Intent(context, .class);
                    //  firstpage.putExtra("StudentList",(Serializable) result);
                    //  context.startActivity(firstpage);
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            context).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Error");

                    // Setting Dialog Message
                    alertDialog.setMessage("Email or Password is incorrect, please try again");
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
            }
         else {
                Toast.makeText(context, "Server not responding! Please try again", Toast.LENGTH_SHORT).show();
            }

        }

    }


}










