package com.opus_bd.pilot.Activity.RequisitorActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opus_bd.pilot.Activity.LoginActivity;
import com.opus_bd.pilot.Model.OrganizationModel;
import com.opus_bd.pilot.Model.PortModel;
import com.opus_bd.pilot.Model.RequisationPostModel;
import com.opus_bd.pilot.Model.Ship;
import com.opus_bd.pilot.Model.UserModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.RetrofitService.APIClientInterface;
import com.opus_bd.pilot.RetrofitService.RetrofitService;
import com.opus_bd.pilot.Utils.SharedPrefManager;
import com.opus_bd.pilot.Utils.Utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequisationEntryActivity extends AppCompatActivity {

    @BindView(R.id.tvOrganization)
    TextView tvOrganization;
    @BindView(R.id.tvRequzationDate)
    TextView tvRequzationDate;
    @BindView(R.id.tvSelectDate)
    TextView tvSelectDate; @BindView(R.id.tvSelectTime)
    TextView tvSelectTime;
    @BindView(R.id.ship_name_spinner)
    Spinner ship_name_spinner;
    @BindView(R.id.start_port_spinner)
    Spinner start_port_spinner;
    @BindView(R.id.end_port_spinner)
    Spinner end_port_spinner;
    Context context = this;

    @BindView(R.id.from_day_button)
    Button fromDateButton;
    @BindView(R.id.from_year_month_button)
    Button fromYearMonthMonthButton;@BindView(R.id.from_Time_layout)
    ImageView from_Time_layout;
    /*@BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserName)
    TextView tvUserName;*/
    ArrayList<Ship> shipmodel = new ArrayList<>();
    ArrayList<PortModel> portmodel = new ArrayList<>();

    public int SELECTED_SHIP_ID;
    public int SELECTED_START_PORT_ID;
    public int SELECTED_END_PORT_ID;
    int mYear, mMonth, mDay;

    Calendar receiverDateCalender = Calendar.getInstance();
    String datefromate, timeFormate;
    int orgid;
    private Gson gson1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisation_entry);
        ButterKnife.bind(this);
        Gson gson = new Gson();
        String token = SharedPrefManager.getInstance(this).getUser();
        UserModel obj = gson.fromJson(token, UserModel.class);
        orgid = obj.getOrgID();
        getAllList(orgid);
        getAllPortList();


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

        from_Time_layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(RequisationEntryActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tvSelectTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }

    private void initializeGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson1 = gsonBuilder.create();
    }

    private void changeFromDate() {
        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(this,
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

                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                        String[] dateValues = formatter.format(receiverDateCalender.getTime()).split("/");
                        /*i{f (dateValues.length >= 3) */
                        fromDateButton.setText(dateValues[2]);
                        fromYearMonthMonthButton.setText(dateValues[1] + "\n" + dateValues[0]);
                        datefromate = dateValues[0] + "/" + dateValues[1] + "/" + dateValues[2];
                        Utilities.showLogcatMessage(" datefromate" + datefromate);
                        tvSelectDate.setText(datefromate);
                          /*  Utilities.showLogcatMessage(" time " + dateValues[3] + "" + dateValues[4] + "" + dateValues[5]);
                            timeFormate = dateValues[3] + ":" + dateValues[4];*/

/*
                        }*/

                    }
                }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    private void initializeFromDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String[] dateValues = formatter.format(receiverDateCalender.getTime()).split("/");
        /* if (dateValues.length >= 3) {*/
        timeFormate = dateValues[0] + "/" + dateValues[1] + "/" + dateValues[2];
        tvRequzationDate.setText(timeFormate);
        fromDateButton.setText(dateValues[0]);
        fromYearMonthMonthButton.setText(dateValues[1] + "\n" + dateValues[2]);
        datefromate = dateValues[0] + "/" + dateValues[1] + "/" + dateValues[2];
        Utilities.showLogcatMessage(" Date " + datefromate);
            /*Utilities.showLogcatMessage(" time " + dateValues[3] + "" + dateValues[4] + "" + dateValues[5]);
            timeFormate = dateValues[3] + ":" + dateValues[4];
*/

    }

    public void getAllList(int id) {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<OrganizationModel>> registrationRequest = retrofitService.GETOrganizationModelByOrgID(token, id);
            registrationRequest.enqueue(new Callback<List<OrganizationModel>>() {
                @Override
                public void onResponse(Call<List<OrganizationModel>> call, @NonNull Response<List<OrganizationModel>> response) {
                    if (response.body() != null) {
                        Utilities.showLogcatMessage("response " + response.body().get(0).getNoOfTokenbalance());
                        tvOrganization.setText(response.body().get(0).getOrganizationName());
                        shipmodel.addAll(response.body().get(0).getShips());
                        addShipNameSpinnerData(shipmodel);
                    }
                }

                @Override
                public void onFailure(Call<List<OrganizationModel>> call, Throwable t) {
                    Utilities.showLogcatMessage("error " + t.toString());
                }
            });
        } else {
            Toast.makeText(this, "Not registered! Please sign in to continue", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void addShipNameSpinnerData(final List<Ship> body) {
        List<String> shipList = new ArrayList<>();
        shipList.add("Select Ship");
        for (int i = 0; i < body.size(); i++) {
            shipList.add(body.get(i).getShipName() + "  " + body.get(i).getId());

        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, shipList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ship_name_spinner.setAdapter(dataAdapter);
        ship_name_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    SELECTED_SHIP_ID = body.get(i - 1).getId();
                    Utilities.showLogcatMessage("SELECTED_SHIP_ID " + SELECTED_SHIP_ID);
                } else {
                    SELECTED_SHIP_ID = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    public void getAllPortList() {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<PortModel>> registrationRequest = retrofitService.GetPortListApi();
            registrationRequest.enqueue(new Callback<List<PortModel>>() {
                @Override
                public void onResponse(Call<List<PortModel>> call, @NonNull Response<List<PortModel>> response) {
                    if (response.body() != null) {

                        portmodel.addAll(response.body());
                        addPortNameSpinnerData(portmodel);
                    }
                }

                @Override
                public void onFailure(Call<List<PortModel>> call, Throwable t) {
                    Utilities.showLogcatMessage("error " + t.toString());
                }
            });
        } else {
            Toast.makeText(this, "Not registered! Please sign in to continue", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void addPortNameSpinnerData(final List<PortModel> body) {
        List<String> shipList = new ArrayList<>();
        shipList.add("Select Port");
        for (int i = 0; i < body.size(); i++) {
            shipList.add(body.get(i).getPortName() + "  " + body.get(i).getId());

        }


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, shipList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        start_port_spinner.setAdapter(dataAdapter);
        start_port_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    SELECTED_START_PORT_ID = body.get(i - 1).getId();
                    Utilities.showLogcatMessage("SELECTED_START_PORT_ID " + SELECTED_START_PORT_ID);

                } else {
                    SELECTED_START_PORT_ID = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        end_port_spinner.setAdapter(dataAdapter);
        end_port_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    SELECTED_END_PORT_ID = body.get(i - 1).getId();
                    Utilities.showLogcatMessage("SELECTED_END_PORT_ID " + SELECTED_END_PORT_ID);
                } else {
                    SELECTED_END_PORT_ID = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @OnClick(R.id.attendance_button)
    public void attendance_button() {


        submitToServer();

    }

    private void submitToServer() {
        Utilities.showLogcatMessage(" Button ");
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        RequisationPostModel requisationPostModel = new RequisationPostModel(orgid, timeFormate, SELECTED_START_PORT_ID,
                SELECTED_END_PORT_ID, datefromate, tvSelectTime.getText().toString(), SELECTED_SHIP_ID);

        Utilities.showLogcatMessage(" requisationPostModel " + requisationPostModel.toString());
        /*ApiClient.getApiInterface().GetRequisitionSave(requisationPostModel).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Utilities.showLogcatMessage(" Responce " + response.body());
                if (response.body() != null) {
                    Toast.makeText(RequisationEntryActivity.this, "" + response.body(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RequisationEntryActivity.this, MainActivity.class));
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RequisationEntryActivity.this, "failed Responce", Toast.LENGTH_SHORT).show();

            }
        });*/
        Call<String> registrationRequest = retrofitService.GetRequisitionSave(requisationPostModel);
        registrationRequest.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Utilities.showLogcatMessage(" response.body() " + response.body());
                if (response.body() != null) {
                    Toast.makeText(RequisationEntryActivity.this, "" + response.body(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RequisationEntryActivity.this, RequisatorHomeActivity.class));
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RequisationEntryActivity.this, "failed response.body()", Toast.LENGTH_SHORT).show();
                Utilities.showLogcatMessage("failed response.body()");

            }
        });
    }
/*   private void submitToServer() {
        Utilities.showLogcatMessage(" Button ");
        String token = SharedPrefManager.getInstance(RequisationEntryActivity.this).getUser();
        int pilot = SharedPrefManager.getInstance(RequisationEntryActivity.this).getID();
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        RequisationPostModel requisationPostModel = new RequisationPostModel(orgid, timeFormate, SELECTED_START_PORT_ID,
                SELECTED_END_PORT_ID, datefromate, "3:45", SELECTED_SHIP_ID);

        Utilities.showLogcatMessage(" requisationPostModel " + requisationPostModel.toString());
        Call<MessageResponse> registrationRequest = retrofitService.GetRequisitionSave(requisationPostModel);
        registrationRequest.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                Utilities.showLogcatMessage(" response.body() " + response.body());
                if (response.body() != null) {
                    Toast.makeText(RequisationEntryActivity.this, "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RequisationEntryActivity.this, MainActivity.class));
                }

            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(RequisationEntryActivity.this, "failed response.body()", Toast.LENGTH_SHORT).show();
                Utilities.showLogcatMessage("failed response.body()");

            }
        });
    }*/


}
