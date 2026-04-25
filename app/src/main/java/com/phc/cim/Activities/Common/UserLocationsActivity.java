package com.phc.cim.Activities.Common;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.phc.cim.DataElements.UserLocation;
import com.phc.cim.Others.Logout;
import com.phc.cim.R;
import com.phc.cim.Activities.Licensing.PWSFilterActivity;
import com.phc.cim.TabsActivities.DashboardTabs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.cardview.widget.CardView;

public class UserLocationsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<UserLocation> userLocations = new ArrayList<>();
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private CardView loadingCard;
    private FloatingActionButton fabRefresh;
    private Context context;
    private String email, username, password, isEdit, Roleid;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private Handler handler = new Handler();
    private Runnable fetchRunnable;
    private Map<String, Marker> userMarkers = new HashMap<>();

    private boolean isFirstLoad = true;
    private boolean isTaskRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_locations);
        context = this;

        SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        email = prefs.getString("email", null);
        username = prefs.getString("username", null);
        password = prefs.getString("password", null);
        isEdit = prefs.getString("isEdit", null);
        Roleid = prefs.getString("RoleID", null);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("User Movements");
        }

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        loadingCard = findViewById(R.id.loading_card);
        fabRefresh = findViewById(R.id.fab_refresh);

        View navHeader = navigationView.getHeaderView(0);
        TextView txtName = navHeader.findViewById(R.id.name);
        TextView txtWebsite = navHeader.findViewById(R.id.website);
        txtName.setText(username);
        txtWebsite.setText(email);

        setUpNavigationView();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Manual refresh shows loader
                fetchData(true);
            }
        });

        // Initial load shows loader
        fetchData(true);
    }

    private void fetchData(boolean showLoader) {
        if (isTaskRunning) return;
        String baseurl = context.getResources().getString(R.string.baseurl);
        new FetchUserLocationsTask(showLoader).execute(baseurl + "GetUpdateLocation");
    }

    // Overload for backward compatibility or auto-refresh (default false loader)
    private void fetchData() {
        fetchData(false);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    if(Roleid != null && (Roleid.equals("3") || Roleid.equals("5") || Roleid.equals("6"))){
                       startActivity(new Intent(context, com.phc.cim.Activities.Inspection.InspectionFilterActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
                    } else {
                       startActivity(new Intent(context, FilterActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
                    }
                } else if (id == R.id.nav_quack) {
                    startActivity(new Intent(context, QuackActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));

                } else if (id == R.id.nav_actionsummary) {
                    SharedPreferences prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
                    String isStat = prefs.getString("isStat", null);
                    if(isStat != null && isStat.equals("true")) {
                        startActivity(new Intent(context, DashboardTabs.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
                    } else {
                        Toast.makeText(context, "You are not authorised!", Toast.LENGTH_SHORT).show();
                    }

                } else if (id == R.id.nav_actiondesc) {
                    if(Roleid != null && Roleid.equals("3")){
                        startActivity(new Intent(context, com.phc.cim.Activities.Inspection.InspectionVisitsActivity.class));
                    } else {
                        startActivity(new Intent(context, IndReportingActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));
                    }

                } else if (id == R.id.nav_pwssearch) {
                    startActivity(new Intent(context, PWSFilterActivity.class));

                } else if (id == R.id.nav_list) {
                    startActivity(new Intent(context, DesealListing.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));

                } else if (id == R.id.nav_hearing) {
                    startActivity(new Intent(context, HearingStatusActivity.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));

                } else if (id == R.id.nav_registration) {
                    startActivity(new Intent(context, RegistrationStatus.class).putExtra("email",email).putExtra("password",password).putExtra("username", username).putExtra("isEdit", isEdit));

                } else if (id == R.id.nav_resetPassword) {
                    startActivity(new Intent(context, ChangePasswordActivity.class).putExtra("email",email).putExtra("password",password));

                } else if (id == R.id.nav_about_us) {
                    startActivity(new Intent(context, AboutusActivity.class));

                } else if (id == R.id.nav_user_locations) {
                     // Already here
                     drawer.closeDrawer(GravityCompat.START);
                     return true;

                } else if (id == R.id.nav_Logout) {
                    showLogoutDialog();
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure to exit CIM?")
                .setTitle("Exit")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(context, Logout.class));
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        enableMyLocation();
        updateMapMarkers();
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            }
        }
    }

    private void updateMapMarkers() {
        if (mMap == null || userLocations.isEmpty()) return;

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        boolean hasPoints = false;

        for (UserLocation loc : userLocations) {
            double lat = loc.getLatitudeDouble();
            double lon = loc.getLongitudeDouble();
            if (lat == 0.0 && lon == 0.0) continue;

            LatLng targetPosition = new LatLng(lat, lon);
            String snippetText = "Last Updated: " + formatDateTime(loc.getLastUpdated());

            Marker marker = userMarkers.get(loc.getUsername());
            if (marker == null) {
                // First time: create marker
                marker = mMap.addMarker(new MarkerOptions()
                        .position(targetPosition)
                        .title(loc.getUsername())
                        .snippet(snippetText));
                userMarkers.put(loc.getUsername(), marker);
            } else {
                // Animate existing marker to new position
                animateMarker(marker, targetPosition);
                marker.setSnippet(snippetText);
                if (marker.isInfoWindowShown()) {
                    marker.showInfoWindow();
                }
            }

            builder.include(targetPosition);
            hasPoints = true;
        }

        // Only move camera on first successful load to avoid disrupting user interaction
        if (hasPoints && isFirstLoad) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
            isFirstLoad = false;
        }
    }
    
    private void animateMarker(final Marker marker, final LatLng toPosition) {
        final Handler handler = new Handler();
        final long start = android.os.SystemClock.uptimeMillis();
        final LatLng startLatLng = marker.getPosition();
        final long duration = 1000; // 1 second animation

        final android.view.animation.Interpolator interpolator = new android.view.animation.LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = android.os.SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                double lng = t * toPosition.longitude + (1 - t) * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t) * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    marker.setPosition(toPosition);
                }
            }
        });
    }
    
    private String formatDateTime(String dateStr) {
        if (dateStr == null || !dateStr.contains("Date")) return dateStr;
        try {
            Pattern pattern = Pattern.compile("/Date\\((\\d+)([+-]\\d+)?\\)/");
            Matcher matcher = pattern.matcher(dateStr);
            if (matcher.find()) {
                long millis = Long.parseLong(matcher.group(1));
                Date date = new Date(millis);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault());
                return sdf.format(date);
            }
        } catch (Exception e) {
            Log.e("UserLocations", "Error parsing date: " + dateStr, e);
        }
        return dateStr;
    }

    private class FetchUserLocationsTask extends AsyncTask<String, Void, String> {
        
        private boolean showLoading;
        
        public FetchUserLocationsTask(boolean showLoading) {
            this.showLoading = showLoading;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isTaskRunning = true;
            if (showLoading && loadingCard != null) {
                loadingCard.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                // Set timeouts to avoid hanging tasks
                conn.setConnectTimeout(5000); 
                conn.setReadTimeout(5000);
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                return sb.toString();
            } catch (Exception e) {
                Log.e("UserLocations", "Error fetching data", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            isTaskRunning = false;
            if (loadingCard != null) {
                loadingCard.setVisibility(View.GONE);
            }
            if (result != null) {
                try {
                    JSONArray array = new JSONArray(result);
                    // Don't clear list completely, update it to keep state if possible, 
                    // but simple clear/add is fine for now as we map by username in updateMapMarkers
                    userLocations.clear(); 
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        UserLocation loc = new UserLocation();
                        loc.setUsername(obj.optString("Username"));
                        loc.setLatitude(obj.optString("Latitude"));
                        loc.setLongitude(obj.optString("Longitude"));
                        loc.setLastUpdated(obj.optString("LastUpdated"));
                        loc.setSuccess(obj.optBoolean("Success"));
                        userLocations.add(loc);
                    }
                    updateMapMarkers();
                } catch (Exception e) {
                    Log.e("UserLocations", "Error parsing result", e);
                    // Suppress toast on auto-refresh to avoid annoyance
                    if (showLoading) { 
                        Toast.makeText(context, "Error parsing server data", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                 if (showLoading) {
                    Toast.makeText(context, "Failed to fetch user locations", Toast.LENGTH_SHORT).show();
                 }
            }
        }
        
        @Override
        protected void onCancelled() {
            super.onCancelled();
            isTaskRunning = false;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        startAutoRefresh();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAutoRefresh();
    }

    private void startAutoRefresh() {
        fetchRunnable = new Runnable() {
            @Override
            public void run() {
                fetchData(false); // Silent refresh
                handler.postDelayed(this, 3000); // 3 seconds interval for near real-time
            }
        };
        handler.postDelayed(fetchRunnable, 3000);
    }

    private void stopAutoRefresh() {
        if (fetchRunnable != null) {
            handler.removeCallbacks(fetchRunnable);
        }
    }

}
