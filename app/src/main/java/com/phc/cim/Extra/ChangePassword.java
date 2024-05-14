package com.phc.cim.Extra;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.phc.cim.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ali.abdul on 4/4/2018.
 */

public class ChangePassword {
    String password;
    String email;
    Context context;
    String jsonStr;
    String MID;
    String MText;

    public ChangePassword(Context context, String email, String password){

        this.context=context;
        this.password=password;
        this.email=email;

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
            jsonStr=result;
            // Invokes the thread for parsing the JSON data
            parserTask.execute();

        }
    }
    private String getDirectionsUrl() {

        // Building the url to the web service
        String baseurl=context.getResources().getString(R.string.baseurl);
        String url=baseurl+"ChangeUserPassword?username="+email+"&userpassword="+password;
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

            if (result != null) {
                if (MID.equals("1")) {
         /*           AlertDialog alertDialog = new AlertDialog.Builder(
                            context).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Success");

                    // Setting Dialog Message
                    alertDialog.setMessage("Your Comments has been sent to PHC.");
                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.tick);

                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog closed*/
                            Toast.makeText(context, "Password changed", Toast.LENGTH_SHORT).show();
          /*              }
                    });

                    // Showing Alert Message
                    alertDialog.show();*/
                }
                // Updating parsed JSON data into ListView

            }
            else{
                Toast.makeText(context, "Password not changed please try again!", Toast.LENGTH_SHORT).show();
            }


        }

    }

}
