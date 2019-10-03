package com.opus_bd.pilot.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.opus_bd.pilot.Model.GeocodingLocation;
import com.opus_bd.pilot.Model.PilotCheckBodyM;
import com.opus_bd.pilot.Model.SalesModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.RetrofitService.ApiClient;
import com.opus_bd.pilot.Utils.SharedPrefManager;
import com.opus_bd.pilot.Utils.Utilities;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_SHORT;

public class CheckInActivity extends AppCompatActivity implements OnMapReadyCallback {
    private final int FINE_LOCATION_PERMISSION = 1;
    LocationRequest mLocationRequest;
    int LOCATION_INTERVAL = 30 * 1000; // 30 sec
    int FAST_INTERVAL = 10 * 1000; // 10 sec
    double latitude, getlat;
    double longitude, getlong;
    LocationCallback locationCallback;
    FusedLocationProviderClient fusedLocationProviderClient;
    boolean mRequestingLocationUpdates = false;
    private GoogleMap mMap;
    Marker friendMarker;
    private final int REQUEST_CHECK_SETTINGS = 10;
    @BindView(R.id.attendance_button)
    Button attendButton;
    @BindView(R.id.tvYourLocation)
    TextView tvYourLocation;
    String location, date,time,Check;
    String loc, shipName;
    int scheduleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            location = bundle.getString("Location");
            scheduleName = bundle.getInt("SCID");
            date = bundle.getString("Date");
            shipName = bundle.getString("ShipName");
            time = bundle.getString("Time");
            Check = bundle.getString("Check");
            Utilities.showLogcatMessage(" SCID  c" + scheduleName + location + date);

        }

        attendButton.setText(Check);
        GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation(location,
                getApplicationContext(), new GeocoderHandler());


        checkPermission();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(
                new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            updateLocationCamera();
                            String checkInLocation = getAddressFromLatLong(getApplicationContext(), latitude, longitude);
                            Log.d("tag", " Location" + checkInLocation);
                        }
                    }
                });

        fusedLocationProviderClient.getLastLocation().addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                }
        );
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                onLocationChanged(locationResult.getLastLocation());
            }
        };
        getPeriodicLocationUpdates();
    }

    public static String getAddressFromLatLong(Context context, double lat, double lng) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            return obj.getAddressLine(0);

        } catch (Exception ignored) {
        }
        return "";
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    getlat = bundle.getDouble("Lat");
                    getlong = bundle.getDouble("log");
                    break;
                default:
                    locationAddress = null;
            }
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        FINE_LOCATION_PERMISSION);
                return;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case FINE_LOCATION_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }

                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(
                            new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location != null) {
                                        updateLocationCamera();
                                      }
                                }
                            });

                    fusedLocationProviderClient.getLastLocation().addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            }
                    );

                    getPeriodicLocationUpdates();
                }
                break;
            }
            default:
                break;
        }
    }

    private void getPeriodicLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(LOCATION_INTERVAL);
        mLocationRequest.setFastestInterval(FAST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(CheckInActivity.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                return;
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, locationCallback,
                Looper.myLooper());
    }

    public void onLocationChanged(Location location) {

        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        String checkInLocation = getAddressFromLatLong(getApplicationContext(), latitude, longitude);
        //

        Log.d("tag", " Location1" + checkInLocation);
        tvYourLocation.setText(checkInLocation);
        loc = tvYourLocation.getText().toString();
        updateLocationCamera();
        //Utils.showLogMessage(msg);
    }

    private void updateLocationCamera() {
        LatLng coordinate = new LatLng(latitude, longitude);
        mMap.clear();
        friendMarker = mMap.addMarker(new MarkerOptions().position(coordinate).title("Your Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 12));
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (fusedLocationProviderClient != null) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates) {
            getPeriodicLocationUpdates();
        }
    }

    @OnClick(R.id.attendance_button)
    public void attendance_button() {
        Utilities.showLogcatMessage(" Button ");

        submitToServer();

    }

    private void submitToServer() {

        String token = SharedPrefManager.getInstance(CheckInActivity.this).getUser();
        int pilot = SharedPrefManager.getInstance(CheckInActivity.this).getID();
        PilotCheckBodyM body = new PilotCheckBodyM(pilot, scheduleName, Check, shipName, "CTG-Payra", date, time, loc);
        ApiClient.getApiInterface().postPilotCheckApi(token, body).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.body() != null) {
                    Toast.makeText(CheckInActivity.this, "" + response.body(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CheckInActivity.this, MainActivity.class));
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(CheckInActivity.this, "failed mkl", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean isValidated() {
        if (longitude == 0 || latitude == 0) {
            Toast.makeText(CheckInActivity.this,
                    "could not detect your location!", LENGTH_SHORT).show();
            return false;
        }
        // Checking if location is near office premises
        Location officeLocation = new Location("");
        officeLocation.setLatitude(getlat);
        officeLocation.setLongitude(getlong);

        Location myLocation = new Location("");
        myLocation.setLongitude(longitude);
        myLocation.setLatitude(latitude);

        float distanceInMeters = officeLocation.distanceTo(myLocation);
        Log.d("tag", "Validation" + distanceInMeters);
        if (distanceInMeters > 100) {
          /*  Toast(g, "You are not in office range! " +
                    "Please try again near office premises", Toast.LENGTH_SHORT, true).show();*/

            Toast.makeText(CheckInActivity.this, "You are out of range! " +
                    "Please try again near premises", LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng coordinate = new LatLng(latitude, longitude);
        friendMarker = mMap.addMarker(new MarkerOptions().position(coordinate).title("Your Location"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(false);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 14));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            SharedPrefManager.getInstance(this).clearToken();
            Toast.makeText(this, "Logged out successfully!!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
        if (id == R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
        if (id == R.id.pendingList) {
            Intent intent = new Intent(this, PendingSalesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
        if (id == R.id.salesList) {
            Intent intent = new Intent(this, SalesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}


