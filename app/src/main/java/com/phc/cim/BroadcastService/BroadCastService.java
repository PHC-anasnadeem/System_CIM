package com.phc.cim.BroadcastService;


import android.app.Service;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.phc.cim.Others.CurrentLocation;
import com.phc.cim.ParcelableModel.CarParcelable;

import java.util.ArrayList;
import java.util.Locale;


public class BroadCastService extends Service {


    private static final String TAG = "BroadcastService";
    public static final String BROADCAST_ACTION = "com.phc.phc";
    private final Handler handler = new Handler();
    Intent intent;
    CurrentLocation gps;
    double cur_latitude;
    double cur_longitude;
    String cityName = null;
    private static double changePositionBy = 0.00005;

    @Override
    public void onCreate() {
        super.onCreate();

        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000); // 1 second

        return super.onStartCommand(intent, flags, startId);
    }


    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            broadcastUpdateInfo();
            handler.postDelayed(this, 10000); // broadcast in every 10 seconds
        }
    };

    //call your API in this method
    private void broadcastUpdateInfo() {

            gps = new CurrentLocation(this);
            if (gps.canGetLocation()) {

                cur_latitude = gps.getLatitude();
                cur_longitude = gps.getLongitude();
                if (cur_latitude != 0.0 && cur_longitude != 0.0) {

                    Geocoder gcd = new Geocoder(getBaseContext(),
                            Locale.getDefault());
                 /*   List<Address> addresses;
                    try {
                     *//*   addresses = gcd.getFromLocation(cur_latitude, cur_longitude, 1);
                        if (addresses.size() > 0)
                            System.out.println(addresses.get(0).getLocality());
                        cityName = addresses.get(0).getLocality();*//*
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            }




        Log.d(TAG, "entered DisplayLoggingInfo");

        //increment position(lat,lng) by 0.000005
      //  changePositionBy = changePositionBy + changePositionBy;

        ArrayList<CarParcelable> carParcelableList = new ArrayList<CarParcelable>();

        //Here I have incremented lat and long by 0.00005 in every 10 seconds. In real scenario you
        //should fetch current location data from your web service.
     /*   for(ApiResponse.PlaceMarks car : MainActivityPresenter.sCarList){
            carParcelableList.add(new CarParcelable(car.getCoordiatesList().get(0)+changePositionBy,
                    car.getCoordiatesList().get(1)+changePositionBy, car.getCarName(), car.getAddress()));
        }*/
        carParcelableList.add(new CarParcelable(cur_latitude,cur_longitude, "Current Location", "current"));
        intent.putExtra("carList", carParcelableList);
        sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
    }
}
