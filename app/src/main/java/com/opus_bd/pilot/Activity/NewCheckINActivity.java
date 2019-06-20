package com.opus_bd.pilot.Activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opus_bd.pilot.Model.GeocodingLocation;
import com.opus_bd.pilot.Model.ProductModel;
import com.opus_bd.pilot.Model.ScheduleByDateModel;
import com.opus_bd.pilot.Model.ScheduleModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.RetrofitService.APIClientInterface;
import com.opus_bd.pilot.RetrofitService.RetrofitClientInstance;
import com.opus_bd.pilot.RetrofitService.RetrofitService;
import com.opus_bd.pilot.Utils.Constants;
import com.opus_bd.pilot.Utils.SharedPrefManager;
import com.opus_bd.pilot.Utils.Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewCheckINActivity extends AppCompatActivity {
    @BindView(R.id.tvSelectDate)
    TextView tvSelectDate;
    @BindView(R.id.tvSHip)
    TextView tvSHip;
    @BindView(R.id.tvshed)
    TextView tvshed;
    @BindView(R.id.tvEndPort)
    TextView tvEndPort;
    @BindView(R.id.tvStartPort)
    TextView tvStartPort;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.from_day_button)
    Button fromDateButton;
    @BindView(R.id.from_year_month_button)
    Button fromYearMonthMonthButton;
    @BindView(R.id.ship_name_spinner)
    Spinner ship_name_spinner;
    @BindView(R.id.schedule_name_spinner)
    Spinner schedule_name_spinner;
    int mYear, mMonth, mDay;
    Context context = this;
    private Gson gson;
    //   SessionManager sessionManager;
    Calendar receiverDateCalender = Calendar.getInstance();
    String datefromate;
    ArrayList<String> shipNames = new ArrayList<>();
    ArrayList<ScheduleByDateModel> shipmodel = new ArrayList<>();
    ArrayList<String> scheduleNames = new ArrayList<>();
    ArrayList<ScheduleByDateModel> schedulemodel = new ArrayList<>();

    public String SELECTED_SHIP_ID;
    public int SELECTED_SCHEDULE_ID;
    String SELECTED_SCHEDULE_NAME;
    String location, target;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_check_in);

        ButterKnife.bind(this);

        mYear = receiverDateCalender.get(Calendar.YEAR);
        mMonth = receiverDateCalender.get(Calendar.MONTH);
        mDay = receiverDateCalender.get(Calendar.DAY_OF_MONTH);
        initializeGson();
        initializeFromDate();
        getShipAllList("20190618");
        getScheduleAllList("20190618");

        fromDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFromDate();
            }
        });
        fromYearMonthMonthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFromDate();
            }
        });
       /* GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation("Dhaka",
                getApplicationContext(), new GeocoderHandler());


        checkPermission();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(NewCheckINActivity.this);

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
        getPeriodicLocationUpdates();*/








/*Utilities.showLogcatMessage("   ship"+SELECTED_SHIP_ID);
Utilities.showLogcatMessage(" schedule"+SELECTED_SCHEDULE_ID);*/
    }

    /*  public static String getAddressFromLatLong(Context context, double lat, double lng) {
          Geocoder geocoder = new Geocoder(context, Locale.getDefault());
          try {
              List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
              Address obj = addresses.get(0);
              return obj.getAddressLine(0);

          } catch (Exception ignored) {
          }
          return "";
      }

      @Override
      public void onPointerCaptureChanged(boolean hasCapture) {

      }

      @Override
      public void onMapReady(GoogleMap googleMap) {

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
                        resolvable.startResolutionForResult(NewCheckINActivity.this,
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
        tvLocation.setText(checkInLocation);
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
    }*/
    private void initializeGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
    }

    private void changeFromDate() {
        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(NewCheckINActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker datepicker, int
                            selectedYear, int selectedMonth, int selectedDay) {
                        receiverDateCalender.set(Calendar.YEAR, selectedYear);
                        receiverDateCalender.set(Calendar.MONTH, selectedMonth);
                        receiverDateCalender.set(Calendar.DAY_OF_MONTH, selectedDay);

                        Calendar tempCalender = Calendar.getInstance();
                        receiverDateCalender.set(Calendar.HOUR, tempCalender.get(Calendar.HOUR));
                        receiverDateCalender.set(Calendar.MINUTE, tempCalender.get(Calendar.MINUTE));
                        receiverDateCalender.set(Calendar.SECOND, tempCalender.get(Calendar.SECOND));

                        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d yyyy", Locale.ENGLISH);
                        String[] dateValues = formatter.format(receiverDateCalender.getTime()).split(" ");
                        if (dateValues.length >= 3) {
                            fromDateButton.setText(dateValues[1]);
                            fromYearMonthMonthButton.setText(dateValues[0] + "\n" + dateValues[2]);


                        }

                        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy MM dd", Locale.ENGLISH);
                        String[] dateValues1 = formatter2.format(receiverDateCalender.getTime()).split(" ");
                        if (dateValues1.length >= 3) {

                            datefromate = dateValues[0] + "" + dateValues[1] + "" + dateValues[2];
                            Utilities.showLogcatMessage(" date " + dateValues[0] + "" + dateValues[1] + "" + dateValues[2]);
                        }
                    }
                }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    private void initializeFromDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d yyyy", Locale.ENGLISH);
        String[] dateValues = formatter.format(receiverDateCalender.getTime()).split(" ");
        if (dateValues.length >= 3) {
            fromDateButton.setText(dateValues[1]);
            fromYearMonthMonthButton.setText(dateValues[0] + "\n" + dateValues[2]);
        }
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy mm dd", Locale.ENGLISH);
        String[] dateValues1 = formatter2.format(receiverDateCalender.getTime()).split(" ");
        if (dateValues1.length >= 3) {

            datefromate = dateValues[0] + "" + dateValues[1] + "" + dateValues[2];

        }
    }

    public void addShipNameSpinnerData(final List<ScheduleByDateModel> body) {
        // String[] truckNumberArray = shipNames.toArray(new String[shipNames.size()]);
       //
        // Toast.makeText(context, "data size " + body.size(), Toast.LENGTH_SHORT).show();
        List<String> shipList = new ArrayList<>();
        shipList.add("Select Ship");
        for (int i = 0; i < body.size(); i++) {
            shipList.add(body.get(i).getShipName());

        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, shipList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ship_name_spinner.setAdapter(dataAdapter);
        ship_name_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    SELECTED_SHIP_ID = "" + body.get(i - 1).getShipName();
                 //   Toast.makeText(context, "selected ship " + SELECTED_SHIP_ID, Toast.LENGTH_SHORT).show();
                    tvStartPort.setText(body.get(i - 1).getStartPort());
                    tvEndPort.setText(body.get(i - 1).getEndPort());
                } else {
                    SELECTED_SHIP_ID = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void addScheduleNameSpinnerData(final List<ScheduleByDateModel> body) {
      //  Toast.makeText(context, "data size " + body.size(), Toast.LENGTH_SHORT).show();
        List<String> scheduleList = new ArrayList<>();
        scheduleList.add("Select Schedule");
        for (int i = 0; i < body.size(); i++) {
            scheduleList.add(body.get(i).getScheduleNo());
        }


        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, scheduleList);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schedule_name_spinner.setAdapter(dataAdapter2);
        schedule_name_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    SELECTED_SCHEDULE_ID = body.get(i - 1).getScheduleID();
                //    Toast.makeText(context, "selected Schedule " + SELECTED_SCHEDULE_ID, Toast.LENGTH_SHORT).show();
                    gETScheduleByshipName(SELECTED_SCHEDULE_ID);
                } else {
                    SELECTED_SCHEDULE_ID = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void getShipAllList(String date) {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<ScheduleByDateModel>> registrationRequest = retrofitService.gETScheduleByscheduleDate(token, date);
            registrationRequest.enqueue(new Callback<List<ScheduleByDateModel>>() {
                                            @Override
                                            public void onResponse(Call<List<ScheduleByDateModel>> call, @NonNull Response<List<ScheduleByDateModel>> response) {
                                                if (response.body() != null) {
                                                    shipmodel.clear();
                                                    shipmodel.addAll(response.body());
                                                    addShipNameSpinnerData(response.body());
                                                    //tvStartPort.setText(response.body().get(0).getStartPort());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<ScheduleByDateModel>> call, Throwable t) {
                                                Utilities.showLogcatMessage("error " + t.toString());
                                            }
                                        }

            );
        } else {
            Toast.makeText(this, "Not registered! Please sign in to continue", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

  /*  public void addScheduleNameSpinnerData(final List<ScheduleByDateModel> body) {
        // String[] truckNumberArray = shipNames.toArray(new String[shipNames.size()]);
        Toast.makeText(context, "data size " + body.size(), Toast.LENGTH_SHORT).show();
        List<String> scheduleList = new ArrayList<>();
        scheduleList.add("Select Schedule");
        for (int i = 0; i < body.size(); i++) {
            scheduleList.add(body.get(i).getScheduleNo());
        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, scheduleList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schedule_name_spinner.setAdapter(dataAdapter);
        schedule_name_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    SELECTED_SCHEDULE_ID = body.get(i - 1).getScheduleID();
                    Toast.makeText(context, "selected Schedule " + SELECTED_SCHEDULE_ID, Toast.LENGTH_SHORT).show();
                } else {
                    SELECTED_SCHEDULE_ID = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }*/

    public void getScheduleAllList(String date) {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<ScheduleByDateModel>> registrationRequest = retrofitService.gETScheduleByscheduleDate(token, date);
            registrationRequest.enqueue(new Callback<List<ScheduleByDateModel>>() {
                                            @Override
                                            public void onResponse(Call<List<ScheduleByDateModel>> call, @NonNull Response<List<ScheduleByDateModel>> response) {
                                                if (response.body() != null) {
                                                    shipmodel.clear();
                                                    shipmodel.addAll(response.body());
                                                    addScheduleNameSpinnerData(response.body());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<ScheduleByDateModel>> call, Throwable t) {
                                                Utilities.showLogcatMessage("error " + t.toString());
                                            }
                                        }

            );
        } else {
            Toast.makeText(this, "Not registered! Please sign in to continue", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void gETScheduleByshipName(int id) {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();


        if (token != null) {
            Call<ScheduleByDateModel> registrationRequest = retrofitService.gETScheduleByshipName(token, id);
            registrationRequest.enqueue(new Callback<ScheduleByDateModel>() {
                                            @Override
                                            public void onResponse(Call<ScheduleByDateModel> call, @NonNull Response<ScheduleByDateModel> response) {
                                                if (response.body() != null) {
                                                    tvStartPort.setText(response.body().getStartPort());
                                                    Utilities.showLogcatMessage(tvStartPort.getText().toString());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ScheduleByDateModel> call, Throwable t) {
                                                Utilities.showLogcatMessage("error " + t.toString());
                                            }
                                        }

            );
        } else {
            Toast.makeText(this, "Not registered! Please sign in to continue", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @OnClick(R.id.save_button)
    public void save_button() {
        Intent intent = new Intent(this, CheckInActivity.class);
        intent.putExtra("Location", tvStartPort.getText());
        intent.putExtra("SCID", SELECTED_SCHEDULE_ID);
        intent.putExtra("Date",datefromate);
        startActivity(intent);
    }

}
