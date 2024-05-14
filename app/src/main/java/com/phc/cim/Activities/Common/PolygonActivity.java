package com.phc.cim.Activities.Common;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.phc.cim.R;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class PolygonActivity extends Activity
        implements OnMapClickListener, OnMapLongClickListener, OnMarkerClickListener, OnMapReadyCallback {

    final int RQS_GooglePlayServices = 1;
    private GoogleMap myMap;

    Location myLocation;
    TextView tvLocInfo;

    boolean markerClicked;
    PolygonOptions polygonOptions;
    Polygon polygon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.polygon_activity);

        tvLocInfo = (TextView) findViewById(R.id.locinfo);

        FragmentManager myFragmentManager = getFragmentManager();
        MapFragment myMapFragment
                = (MapFragment) myFragmentManager.findFragmentById(R.id.map);
        myMapFragment.getMapAsync(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        markerClicked = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
 /*    case R.id.menu_legalnotices:
                String LicenseInfo = GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(
                        getApplicationContext());
                AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(MainActivity.this);
                LicenseDialog.setTitle("Legal Notices");
                LicenseDialog.setMessage(LicenseInfo);
                LicenseDialog.show();
                return true;*/

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        super.onResume();

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        if (resultCode == ConnectionResult.SUCCESS) {
            Toast.makeText(getApplicationContext(),
                    "isGooglePlayServicesAvailable SUCCESS",
                    Toast.LENGTH_LONG).show();
        } else {
            GooglePlayServicesUtil.getErrorDialog(resultCode, this, RQS_GooglePlayServices);
        }

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        myMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        myMap.setMyLocationEnabled(true);

        myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        myMap.setOnMapClickListener(this);
        myMap.setOnMapLongClickListener(this);
        myMap.setOnMarkerClickListener(this);
    }
    @Override
    public void onMapClick(LatLng point) {
        tvLocInfo.setText(point.toString());
        myMap.animateCamera(CameraUpdateFactory.newLatLng(point));

        markerClicked = false;
    }

    @Override
    public void onMapLongClick(LatLng point) {
        tvLocInfo.setText("New marker added@" + point.toString());
        myMap.addMarker(new MarkerOptions().position(point).title(point.toString()));

        markerClicked = false;
    }


    @Override
    public boolean onMarkerClick(Marker marker) {

        if(markerClicked){

            if(polygon != null){
                polygon.remove();
                polygon = null;
            }

            polygonOptions.add(marker.getPosition());
            polygonOptions.strokeColor(Color.RED);
            polygonOptions.fillColor(Color.BLUE);
            polygon = myMap.addPolygon(polygonOptions);
        }else{
            if(polygon != null){
                polygon.remove();
                polygon = null;
            }

            polygonOptions = new PolygonOptions().add(marker.getPosition());
            markerClicked = true;
        }

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
