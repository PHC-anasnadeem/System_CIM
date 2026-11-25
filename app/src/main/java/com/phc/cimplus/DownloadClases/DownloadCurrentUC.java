package com.phc.cimplus.DownloadClases;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.phc.cimplus.DataElements.CurrentUC;
import com.phc.cimplus.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadCurrentUC {

    public interface OnUCDownloadedListener {
        void onUCDetected(CurrentUC currentUC);
    }

    private Context context;
    private double latitude;
    private double longitude;
    private OnUCDownloadedListener listener;

    public DownloadCurrentUC(Context context, double latitude, double longitude, OnUCDownloadedListener listener) {
        this.context = context;
        this.latitude = latitude;
        this.longitude = longitude;
        this.listener = listener;

        new FetchTask().execute();
    }

    private class FetchTask extends AsyncTask<Void, Void, CurrentUC> {

        @Override
        protected CurrentUC doInBackground(Void... voids) {
            if (latitude == 0.0 || longitude == 0.0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                String baseurl = context.getResources().getString(R.string.baseurl);
                String urlStr = baseurl + "GetUCByLocation?Latitude=" + latitude + "&Longitude=" + longitude;
                urlStr = urlStr.replaceAll(" ", "%20");

                URL url = new URL(urlStr);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(8000);
                urlConnection.setReadTimeout(8000);
                urlConnection.connect();

                int statusCode = urlConnection.getResponseCode();
                InputStream inputStream = (statusCode >= 200 && statusCode < 400)
                        ? urlConnection.getInputStream()
                        : urlConnection.getErrorStream();

                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String response = buffer.toString();
                Log.d("DownloadCurrentUC", "Response: " + response);

                if (!response.isEmpty() && !response.equals("null")) {
                    JSONObject obj = new JSONObject(response);
                    CurrentUC uc = new CurrentUC();
                    uc.setUC_Boundary_ID(obj.optInt("UC_Boundary_ID", 0));
                    uc.setUC_Code(obj.optInt("UC_Code", 0));
                    uc.setUC_NO(obj.optInt("UC_NO", 0));
                    uc.setUC_Name(obj.optString("UC_Name", ""));
                    uc.setUC_Key(obj.optString("UC_Key", ""));
                    uc.setType(obj.optString("Type", ""));
                    uc.setTehsil_N(obj.optString("Tehsil_N", ""));
                    uc.setDistrict_N(obj.optString("District_N", ""));
                    uc.setDivision_N(obj.optString("Division_N", ""));
                    uc.setDisplayName(obj.optString("DisplayName", ""));

                    if (uc.getUC_Boundary_ID() > 0 || !uc.getDisplayName().isEmpty()) {
                        return uc;
                    }
                }
            } catch (Exception e) {
                Log.e("DownloadCurrentUC", "Error fetching UC: " + e.getMessage());
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception ignored) {
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(CurrentUC currentUC) {
            super.onPostExecute(currentUC);
            if (listener != null) {
                listener.onUCDetected(currentUC);
            }
        }
    }
}

