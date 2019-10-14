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
import android.view.Menu;
import android.view.MenuItem;
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

import static android.widget.Toast.LENGTH_SHORT;

public class NewCheckINActivity extends AppCompatActivity {
    @BindView(R.id.tvSelectDate)
    TextView tvSelectDate;
    @BindView(R.id.tvEndPort)
    TextView tvEndPort;
    @BindView(R.id.tvStartPort)
    TextView tvStartPort;
    @BindView(R.id.from_day_button)
    Button fromDateButton;
    @BindView(R.id.from_year_month_button)
    Button fromYearMonthMonthButton;
    @BindView(R.id.ship_name_spinner)
    Spinner ship_name_spinner;
    @BindView(R.id.schedule_name_spinner)
    Spinner schedule_name_spinner;
    @BindView(R.id.CheckType_spinner)
    Spinner CheckType_spinner;
    int mYear, mMonth, mDay;
    Context context = this;
    private Gson gson;
    String check;
    //   SessionManager sessionManager;
    Calendar receiverDateCalender = Calendar.getInstance();
    String datefromate, timeFormate;
    ArrayList<ScheduleByDateModel> shipmodel = new ArrayList<>();

    public String SELECTED_SHIP_ID;
    public int SELECTED_SCHEDULE_ID;


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

        CheckType_spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
              }

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

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd hh mm ss a", Locale.ENGLISH);
                        String[] dateValues = formatter.format(receiverDateCalender.getTime()).split(" ");
                        if (dateValues.length >= 3) {
                            fromDateButton.setText(dateValues[2]);
                            fromYearMonthMonthButton.setText(dateValues[1] + "\n" + dateValues[0]);
                            datefromate = dateValues[0] + "" + dateValues[1] + "" + dateValues[2];
                            Utilities.showLogcatMessage(" time " + dateValues[3] + "" + dateValues[4] + "" + dateValues[5]);
                            timeFormate = dateValues[3] + ":" + dateValues[4];
                            getScheduleAllList(datefromate);
                            getShipAllList(datefromate);

                        }

                    }
                }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    private void initializeFromDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd hh mm ss a", Locale.ENGLISH);
        String[] dateValues = formatter.format(receiverDateCalender.getTime()).split(" ");
        if (dateValues.length >= 3) {
            fromDateButton.setText(dateValues[2]);
            fromYearMonthMonthButton.setText(dateValues[1] + "\n" + dateValues[0]);
            datefromate = dateValues[1] + "/" + dateValues[2] + "/" + dateValues[0];
            timeFormate = dateValues[3] + ":" + dateValues[4];
            getScheduleAllList(datefromate);
            getShipAllList(datefromate);

        }
    }

    public void addShipNameSpinnerData(final List<ScheduleByDateModel> body) {
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
                                                    Utilities.showLogcatMessage(" "+response.body());
                                                    shipmodel.clear();
                                                    shipmodel.addAll(response.body());
                                                    addShipNameSpinnerData(response.body());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<ScheduleByDateModel>> call, Throwable t) {
                                                Utilities.showLogcatMessage("error " + t.toString());
                                            }
                                        }

            );
        } else {
            Toast.makeText(this, "Not registered! Please sign in to continue", LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

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
                                                else {

                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<ScheduleByDateModel>> call, Throwable t) {
                                            }
                                        }

            );
        } else {
            Toast.makeText(this, "Not registered! Please sign in to continue", LENGTH_SHORT).show();
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
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ScheduleByDateModel> call, Throwable t) {
                                            }
                                        }

            );
        } else {
            Toast.makeText(this, "Not registered! Please sign in to continue", LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
    private class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            check = parent.getItemAtPosition(position).toString();
            Toast.makeText(NewCheckINActivity.this, " c " + check, LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    @OnClick(R.id.save_button)
    public void save_button() {
        Intent intent = new Intent(this, CheckInActivity.class);
        intent.putExtra("Location", tvStartPort.getText());
        intent.putExtra("SCID", SELECTED_SCHEDULE_ID);
        intent.putExtra("ShipName", SELECTED_SHIP_ID);
        intent.putExtra("Date", datefromate);
        intent.putExtra("Time", timeFormate);
        intent.putExtra("Check", check);
        startActivity(intent);
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
        return super.onOptionsItemSelected(item);
    }


}
