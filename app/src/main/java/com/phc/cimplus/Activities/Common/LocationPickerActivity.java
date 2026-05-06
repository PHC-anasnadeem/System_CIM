package com.phc.cimplus.Activities.Common;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.phc.cimplus.R;

public class LocationPickerActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng selectedLatLng;
    private TextView tvCoordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_picker);

        tvCoordinates = findViewById(R.id.tv_coordinates);
        Button btnConfirm = findViewById(R.id.btn_confirm);

        double initialLat = getIntent().getDoubleExtra("lat", 0.0);
        double initialLng = getIntent().getDoubleExtra("lng", 0.0);
        selectedLatLng = new LatLng(initialLat, initialLng);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnConfirm.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("lat", selectedLatLng.latitude);
            resultIntent.putExtra("lng", selectedLatLng.longitude);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLatLng, 15f));

        updateCoordinatesText(selectedLatLng);

        mMap.setOnCameraMoveListener(() -> {
            selectedLatLng = mMap.getCameraPosition().target;
            updateCoordinatesText(selectedLatLng);
        });
    }

    private void updateCoordinatesText(LatLng latLng) {
        tvCoordinates.setText(String.format("Lat: %.6f, Lng: %.6f", latLng.latitude, latLng.longitude));
    }
}
