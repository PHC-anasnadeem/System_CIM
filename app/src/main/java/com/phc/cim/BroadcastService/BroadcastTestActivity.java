package com.phc.cim.BroadcastService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.phc.cim.R;



public class BroadcastTestActivity extends AppCompatActivity {

    private static final String TAG = "BroadcastTest";
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_test);

        intent = new Intent(this, BroadCastService.class);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        startService(intent);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(broadcastReceiver, filter);
        //registerReceiver(broadcastReceiver, new IntentFilter(BroadCastService.BROADCAST_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        stopService(intent);
    }

    private void updateUI(Intent intent) {
        String counter = intent.getStringExtra("counter");
        String time = intent.getStringExtra("time");
        Log.d(TAG, counter);
        Log.d(TAG, time);

        TextView txtDateTime = (TextView) findViewById(R.id.txtDateTime);
        TextView txtCounter = (TextView) findViewById(R.id.txtCounter);
        txtDateTime.setText(time);
        txtCounter.setText(counter);
    }
}
