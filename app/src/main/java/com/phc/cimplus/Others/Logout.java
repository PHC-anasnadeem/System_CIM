package com.phc.cimplus.Others;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.phc.cimplus.Activities.Common.Login_Activity;


public class Logout extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_comments);



        // Clear session data
        getSharedPreferences("MyPrefsFile", MODE_PRIVATE).edit().clear().commit();

        // Go to MainActivity (Splash) instead of Login to ensure lookups are loaded
        Intent intent = new Intent(this, com.phc.cimplus.Activities.Common.MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }
}
