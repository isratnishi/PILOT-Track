package com.opus_bd.pilot.Dialog;
import android.support.v4.app.DialogFragment;

public class MapDialog extends DialogFragment {
       /* private View rootView;
        private TextView tvYourLocation;
        private Fragment map;
        private Button attendance_button;

    public final static String EXTRA_MODEL = "id";
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
    int statusID;

    String location, target;
    int site, product, salesperson;
        public void setCustomerModel(CustomerModel customerModel) {
            this.customerModel = customerModel;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            initViews();
            final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setView(rootView)
                    .setCancelable(true)
                    .setPositiveButton("Add", null)
                    .setNegativeButton("Cancel", null)
                    .create();
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.setCancelable(true);
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    onDialogShow(alertDialog);
                }
            });
            return alertDialog;
        }

        private void initViews() {

            rootView = LayoutInflater.from(getContext())
                    .inflate(R.layout.activity_check_in, null, false);
            attendButton = rootView.findViewById(R.id.attendButton);
            etPhone = rootView.findViewById(R.id.etPhone);
            etEmail = rootView.findViewById(R.id.etEmail);
            etBalance = rootView.findViewById(R.id.etBalance);
            progressbar = rootView.findViewById(R.id.progressbar);
            rootLayout = rootView.findViewById(R.id.rootLayout);



        }

        private void onDialogShow(AlertDialog dialog) {
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    submitToServer();
                }
            });

            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }

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
                //   tvYourLocation.setText(" Location1"+checkInLocation);
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
        tvLocation.setText(String.valueOf(getlat) + " " + String.valueOf(getlong));
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
                                       *//* latitude = location.getLatitude();
                                        longitude = location.getLongitude();*//*
                                        updateLocationCamera();
                                        //  Log.d(Constants.LOGTAG, location.getLatitude() + " " + location.getLongitude());
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
        Intent intent = new Intent(, NewEntryActivity.class);
        intent.putExtra("Location", tvYourLocation.getText());
        startActivity(intent);

//
//        if (isValidated()) {
//            Utilities.showLogcatMessage(" Visit " + SharedPrefManager.getInstance(CheckInActivity.this).getVisit());
//            Intent intent = new Intent(CheckInActivity.this, NewEntryActivity.class);
//            intent.putExtra("Location",tvYourLocation.getText());
//            startActivity(intent);
//        }


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
          *//*  Toast(g, "You are not in office range! " +
                    "Please try again near office premises", Toast.LENGTH_SHORT, true).show();*//*

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

    }*/


}