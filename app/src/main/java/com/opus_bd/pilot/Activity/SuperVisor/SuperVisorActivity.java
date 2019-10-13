package com.opus_bd.pilot.Activity.SuperVisor;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.opus_bd.pilot.Activity.LoginActivity;
import com.opus_bd.pilot.Activity.PilotActivity.MainActivity;
import com.opus_bd.pilot.Activity.RequisitorActivity.CancleReqActivity;
import com.opus_bd.pilot.Activity.RequisitorActivity.RequisatorHomeActivity;
import com.opus_bd.pilot.Model.PilotCheckBodyM;
import com.opus_bd.pilot.Model.RequisationListModel;
import com.opus_bd.pilot.Model.ScheduleByIDModel.ScheduleByIDModel;
import com.opus_bd.pilot.Model.UserModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.RetrofitService.APIClientInterface;
import com.opus_bd.pilot.RetrofitService.RetrofitClientInstance;
import com.opus_bd.pilot.RetrofitService.RetrofitService;
import com.opus_bd.pilot.Utils.SharedPrefManager;
import com.opus_bd.pilot.Utils.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuperVisorActivity extends AppCompatActivity {
    int orgid;
    @BindView(R.id.tvUserName)
    TextView tvUserName;


    @BindView(R.id.tvPendingSchedule)
    TextView tvPendingSchedule;
    @BindView(R.id.tvRunningSchedule)
    TextView tvRunningSchedule;
    @BindView(R.id.tvRunningBoatStatus)
    TextView tvRunningBoatStatus;
    Boolean backPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_visor);
        ButterKnife.bind(this);
        Gson gson = new Gson();
        String token = SharedPrefManager.getInstance(this).getUser();
        UserModel obj = gson.fromJson(token, UserModel.class);
        orgid = obj.getOrgID();
        tvUserName.setText(obj.getUserName());
        getAllReqList();
        getAllList();
        getAllBoatList();
    }

    public void getAllReqList() {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<RequisationListModel>> registrationRequest = retrofitService.GetAllRequisitionDetailsPending();
            registrationRequest.enqueue(new Callback<List<RequisationListModel>>() {
                @Override
                public void onResponse(Call<List<RequisationListModel>> call, @NonNull Response<List<RequisationListModel>> response) {
                    if (response.body() != null) {
                        tvPendingSchedule.setText(String.valueOf(response.body().size()));
                    }
                }

                @Override
                public void onFailure(Call<List<RequisationListModel>> call, Throwable t) {
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

    public void getAllList() {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<ScheduleByIDModel>> registrationRequest = retrofitService.GetAllScheduleAssignLog();
            registrationRequest.enqueue(new Callback<List<ScheduleByIDModel>>() {
                @Override
                public void onResponse(Call<List<ScheduleByIDModel>> call, @NonNull Response<List<ScheduleByIDModel>> response) {
                    if (response.body() != null) {
                        tvRunningSchedule.setText(String.valueOf(response.body().size()));
                    }
                }

                @Override
                public void onFailure(Call<List<ScheduleByIDModel>> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(this, "Not registered! Please sign in to continue", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void getAllBoatList() {

        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();
        if (token != null) {
            Call<List<PilotCheckBodyM>> registrationRequest = retrofitService.GetAllPilotCheckIncheckOut();
            registrationRequest.enqueue(new Callback<List<PilotCheckBodyM>>() {
                @Override
                public void onResponse(Call<List<PilotCheckBodyM>> call, @NonNull Response<List<PilotCheckBodyM>> response) {

                    try {
                        if (response.body() != null) {
                            tvRunningBoatStatus.setText(String.valueOf(response.body().size()));
                        }

                    } catch (Exception e) {
                    }


                }

                @Override
                public void onFailure(Call<List<PilotCheckBodyM>> call, Throwable t) {
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

    @OnClick(R.id.cvPendingSchedule)
    public void cvPendingSchedule() {

        Intent intent = new Intent(SuperVisorActivity.this, PendingScheduleforSVActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.cvRunningSchedule)
    public void cvRunningSchedule() {

        Intent intent = new Intent(SuperVisorActivity.this, ScheduleforSVActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cvRunningBoatStatus)
    public void cvRunningBoatStatus() {

        Intent intent = new Intent(SuperVisorActivity.this, RunningBoatForSVActivity.class);
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

    @Override
    public void onBackPressed() {
        if (backPressed) {
            super.onBackPressed();
        }

        // Toast.makeText(this,"Tap Again for Exit",Toast.LENGTH_SHORT).show();
        Toasty.info(this, "Tap Again for Exit").show();
        backPressed = true;
    }
}
