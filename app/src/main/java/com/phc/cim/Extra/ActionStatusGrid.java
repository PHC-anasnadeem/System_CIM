package com.phc.cim.Extra;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.phc.cim.R;

public class ActionStatusGrid extends AppCompatActivity {

    private TableLayout mTableLayout;
    ProgressDialog mProgressBar;
    int count1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_statusdesc);

        mProgressBar = new ProgressDialog(this);

        // setup the table
        mTableLayout = (TableLayout) findViewById(R.id.actiontable);

        mTableLayout.setStretchAllColumns(true);


       // startLoadData();

        TableRow tr_head = new TableRow(this);
        tr_head.setId(12+count1);
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView label_title = new TextView(this);
        label_title.setId(20+count1);
        label_title.setText("Title");
        label_title.setTextColor(Color.WHITE);
        label_title.setPadding(5, 5, 5, 5);
        tr_head.addView(label_title);// add the column to the table row here

        TextView label_description = new TextView(this);
        label_description.setId(21+count1);// define id that must be unique
        label_description.setText("Description"); // set the text for the header
        label_description.setTextColor(Color.WHITE); // set the color
        label_description.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_description); // add the column to the table row here

        mTableLayout.addView(tr_head, new TableLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        Integer count=0;
        /*while () {*/
            String title = "closed";// get the first variable
            String description = "permanent closed";// get the second variable
// Create the table row
            TableRow tr = new TableRow(this);
            if(count%2!=0) tr.setBackgroundColor(Color.GRAY);
            tr.setId(100+count);
            tr.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

//Create two columns to add as table data
            // Create a TextView to add date
            TextView labelDATE = new TextView(this);
            labelDATE.setId(200+count);
            labelDATE.setText(title);
            labelDATE.setPadding(2, 0, 5, 0);
            labelDATE.setTextColor(Color.BLACK);
            tr.addView(labelDATE);
            TextView labelWEIGHT = new TextView(this);
            labelWEIGHT.setId(200+count);
            labelWEIGHT.setText(description);
            labelWEIGHT.setTextColor(Color.BLACK);
            tr.addView(labelWEIGHT);

// finally add this to the table row
            mTableLayout.addView(tr, new TableLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            count++;
        }
   // }

 /*   public void startLoadData() {

        mProgressBar.setCancelable(false);
        mProgressBar.setMessage("Fetching Invoices..");
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressBar.show();


    }*/





}