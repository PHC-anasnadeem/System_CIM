package com.phc.cim.Extra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.phc.cim.R;

public class FTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_t_p);
        Button btn_find=(Button) findViewById(R.id.btn_find);

        btn_find.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v)
            {
                RetrieveFeedTask downloadTask = new RetrieveFeedTask();
                downloadTask.execute();
            }
        });

    }
    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {

        private Exception exception;

        protected Void doInBackground(String... urls) {



            return null;
        }

        protected void onPostExecute(String feed) {
            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }
}