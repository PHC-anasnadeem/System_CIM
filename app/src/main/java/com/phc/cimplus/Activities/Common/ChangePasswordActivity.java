package com.phc.cimplus.Activities.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.phc.cimplus.Extra.MyLocationService;
import com.phc.cimplus.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;


public class ChangePasswordActivity extends AppCompatActivity {

    Context context;
    String oldpas;
    String newPas;
    String confirmPas;
    String password;
    String email;
    String jsonStr;
    String MID;
    String MText;
    EditText oldPass;
    EditText newPass;
    EditText confirmPass;
    ProgressDialog pDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        context = this;
        
        // Initialize and set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        oldPass = (EditText) findViewById(R.id.old_password);
        newPass = (EditText) findViewById(R.id.new_password);
        confirmPass = (EditText) findViewById(R.id.confirm_password);

        pDialog = new ProgressDialog(this);

        Intent intent = getIntent();

        email= (String) intent.getSerializableExtra("email");
        password= (String) intent.getSerializableExtra("password");


        Button markasdone = (Button) findViewById(R.id.pass_button);
        markasdone.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                oldpas=  oldPass.getText().toString();
                newPas = newPass.getText().toString();
                confirmPas =confirmPass.getText().toString();
              int count=0;
                    if(TextUtils.isEmpty(newPas) || newPas.length() < 6)
                    {
                        newPass.setError("You must have 6 characters in your password");
                        count++;
                        return;
                    }
                    if(TextUtils.isEmpty(confirmPas) || confirmPas.length() < 6)
                    {
                        confirmPass.setError("You must have 6 characters in your password");
                        count++;
                        return;
                    }
          /*      if(oldpas.equals(password))
                {
                }
                else {
                    oldPass.setError("Old password is not matched");
                    count++;

                }*/
                    if(count<1) {
                        if (newPas.equals(confirmPas)) {
                            pDialog.setMessage("Please wait....");
                            pDialog.setCancelable(false);
                            pDialog.show();
                            String url = getDirectionsUrl();
                            DownloadTask downloadTask = new DownloadTask();
                            //Start downloading json data from Google Directions API
                            downloadTask.execute(url);

                        } else {
                            Toast.makeText(context, "New password and confirm password is not matched", Toast.LENGTH_SHORT).show();
                        }
                    }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
    private String getDirectionsUrl() {

        // Building the url to the web service
        String baseurl=context.getResources().getString(R.string.baseurl);
        String url=baseurl+"ChangeUserPassword?username="+email+"&oldPassword="+oldpas+"&userpassword="+newPas;
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
                    // Password changed successfully on the server.
                    // 1. Inform the user.
                    Toast.makeText(context,
                            "Password changed successfully. Please log in again with your new password.",
                            Toast.LENGTH_LONG).show();

                    // 2. Clear the locally stored session so that this device
                    //    is also forced to re-authenticate with the new password.
                    //    This mirrors what the Logout activity does, ensuring no
                    //    stale credentials remain on any device.
                    context.getSharedPreferences("MyPrefsFile", Context.MODE_PRIVATE)
                            .edit().clear().apply();

                    // 3. Stop the background location service (tied to the old session).
                    try {
                        context.stopService(new Intent(context, MyLocationService.class));
                    } catch (Exception ignored) {}

                    // 4. Redirect to Login_Activity, clearing the entire back-stack
                    //    so the user cannot press Back to return to a protected screen.
                    Intent loginIntent = new Intent(context, Login_Activity.class);
                    loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(loginIntent);
                } else {
                    Toast.makeText(context, "Old password is not matched", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Internet Connection Error: Please try again.", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
