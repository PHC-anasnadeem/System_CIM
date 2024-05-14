package com.phc.cim.Extra;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.HashMap;


public class RegistrationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    View objView;
    Spinner hcsptypespinner;
    Spinner sectortypespinner;
    String jsonStr=null;
    ArrayList<HashMap<String, String>> mylist;
    ProgressDialog pDialog;
    String HCE_Name;
    String  HCE_Address;
    String HCSP_Name;
    String HCSP_CNIC;
    String HCSP_Contact;
    EditText HCEName;
    EditText HCEAddress;

    EditText HCSPName;
    EditText CNIC;
    EditText Contact;
    String sectortype;
    String hcspType;

    String MID;
    String MText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       objView= inflater.inflate(R.layout.fragment_registration, container, false);

        hcsptypespinner = (Spinner) objView.findViewById(R.id.HCSP_Typespinner);
        String [] hcsptype =
                {"Service Provider Type","Owner","Incharge","Manager"};

        ArrayAdapter<String> hcsptypeadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,hcsptype){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                if(position == 0){
                    // Set the hint text color gray
                    //tv.setTextColor(Color.GRAY);
                }
                else {
                   // tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        hcsptypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hcsptypespinner.setAdapter(hcsptypeadapter);
        hcsptypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                    hcspType=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sectortypespinner = (Spinner) objView.findViewById(R.id.Sector_Type);
        String [] countype =
                {"Sector Type","Public","Private"};

        ArrayAdapter<String> countypeadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item,countype){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);

                if(position == 0){
                    // Set the hint text color gray
                    //tv.setTextColor(Color.GRAY);
                }
                else {
                    // tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        countypeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sectortypespinner.setAdapter(countypeadapter);
        sectortypespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                sectortype = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //SectorType    =   (TextView)findViewById( R.id.content );
        HCEName      =   (EditText) objView.findViewById(R.id.hce_name);
        HCEAddress     = (EditText) objView.findViewById(R.id.Address);
        HCSPName      =   (EditText) objView.findViewById(R.id.HCSP_Name);
        CNIC      =    (EditText) objView.findViewById(R.id.CNIC);
        Contact       =   (EditText) objView.findViewById(R.id.Mobile);


        Button saveme=(Button) objView.findViewById(R.id.btn_signup);

        saveme.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v)
            {
                try{

                    HCE_Name = HCEName.getText().toString();
                    HCE_Address   = HCEAddress.getText().toString();
                    HCSP_Name   = HCSPName.getText().toString();
                    HCSP_CNIC   = CNIC.getText().toString();
                    HCSP_Contact   = Contact.getText().toString();
                    // CALL GetText method to make post method call
                    int count=0;
                    if (sectortype.equals("Sector Type")) {
                        setSpinnerError(sectortypespinner,("Sector can't be empty"));
                        count++;
                    }
                    if (hcspType.equals("Service Provider Type")) {
                        setSpinnerError(hcsptypespinner,("HCSP can't be empty"));
                        count++;
                    }
                    if(TextUtils.isEmpty(HCE_Name)) {
                       HCEName.setError("Please Enter District Name");
                        count++;
                        //return;
                    }
                   if(TextUtils.isEmpty(HCE_Address)) {
                        HCEAddress.setError("Please Enter District Address");
                        count++;
                        //return;
                    }
                    if(TextUtils.isEmpty(HCSP_Name)) {
                        HCSPName.setError("Please Enter HCSP Name");
                        count++;
                        //return;
                    }
                  if(TextUtils.isEmpty(HCSP_CNIC)) {
                        CNIC.setError("Please Enter HCSP CNIC");
                        count++;
                       // return;
                    }
                     if(TextUtils.isEmpty(HCSP_Contact)) {
                        Contact.setError("Please Enter HCSP Contact");
                        count++;
                      //  return;
                    }


                   if(count<1){
                       pDialog = new ProgressDialog(getContext());
                       pDialog.setMessage("Please wait...");
                       pDialog.setCancelable(false);
                       pDialog.show();
                        String url = getDirectionsUrl(getContext());
                        DownloadTask downloadTask = new DownloadTask();

                        //Start downloading json data from Google Directions API
                        downloadTask.execute(url);

                    }
                    else {
                       Toast.makeText(getContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();

                   }
                }
                catch(Exception ex)
                {
                    // getContext().setText(" url exeption! " );
                }
            }
        });




       return objView;
    }
    private void setSpinnerError(Spinner spinner, String error){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

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
            jsonStr=result;
            // Invokes the thread for parsing the JSON data
            parserTask.execute();

        }
    }
    private String getDirectionsUrl(Context context) {

        // Building the url to the web service
        String baseurl=context.getResources().getString(R.string.baseurl);

        String url=baseurl+"HCERegister?sectortype=" + sectortype + "&DeviceToken=&hcename="+HCE_Name+"&hceaddress="+ HCE_Address+"&designation="+hcspType+"&hcspname="+HCSP_Name+"&cnic="+HCSP_CNIC+"&mobileno="+HCSP_Contact;
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
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            getContext()).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Success");

                    // Setting Dialog Message
                    alertDialog.setMessage("Your request has been submitted to PHC. A representative will contact you shortly to schedule your visit. We look forward to seeing you!");
                    // Setting Icon to Dialog
                    alertDialog.setIcon(R.drawable.tick);

                    // Setting OK Button
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog closed
                            Toast.makeText(getContext(), "Thanks You!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();
                }
                // Updating parsed JSON data into ListView

            }
            else{
                Toast.makeText(getContext(), "Network eror", Toast.LENGTH_SHORT).show();
        }


        }

    }

}
