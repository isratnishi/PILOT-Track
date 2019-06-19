package com.opus_bd.pilot.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opus_bd.pilot.Model.ProductModel;
import com.opus_bd.pilot.Model.ScheduleByDateModel;
import com.opus_bd.pilot.Model.ScheduleModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.RetrofitService.APIClientInterface;
import com.opus_bd.pilot.RetrofitService.RetrofitClientInstance;
import com.opus_bd.pilot.RetrofitService.RetrofitService;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewCheckINActivity extends AppCompatActivity {
    @BindView(R.id.tvSelectDate)
    TextView tvSelectDate;
    @BindView(R.id.from_day_button)
    Button fromDateButton;
    @BindView(R.id.from_year_month_button)
    Button fromYearMonthMonthButton;
    @BindView(R.id.ship_name_spinner)
    Spinner ship_name_spinner;
    int mYear, mMonth, mDay;
    Context context = this;
    private Gson gson;
    //   SessionManager sessionManager;
    Calendar receiverDateCalender = Calendar.getInstance();
    String datefromate;
    ArrayList<String> shipNames = new ArrayList<>();
    ArrayList<ScheduleByDateModel> shipmodel = new ArrayList<>();
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
            Utilities.showLogcatMessage(" date " + datefromate);
        }
    }

    public static void convert(String dateString) throws ParseException {


    }

    private void addShipNameSpinnerData() {
        String[] truckNumberArray = shipNames.toArray(new String[shipNames.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_layout, truckNumberArray);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ship_name_spinner.setAdapter(adapter);
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
                        addShipNameSpinnerData();
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

}
