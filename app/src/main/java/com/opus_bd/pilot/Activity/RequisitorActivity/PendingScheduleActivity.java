package com.opus_bd.pilot.Activity.RequisitorActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.opus_bd.pilot.Activity.LoginActivity;
import com.opus_bd.pilot.Adapter.PendingScheduleAdapter;
import com.opus_bd.pilot.Adapter.RunningBoatStatusListAdapter;
import com.opus_bd.pilot.Model.Organization.BoatStatusForORG;
import com.opus_bd.pilot.Model.Organization.ScheduleAssignForORG;
import com.opus_bd.pilot.Model.UserModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.RetrofitService.APIClientInterface;
import com.opus_bd.pilot.RetrofitService.RetrofitService;
import com.opus_bd.pilot.Utils.SharedPrefManager;
import com.opus_bd.pilot.Utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingScheduleActivity extends AppCompatActivity {
    @BindView(R.id.tvPendingschedule)
    TextView tvPendingschedule;
    @BindView(R.id.rvPendingScheduleList)
    RecyclerView rvPendingScheduleList;
    int orgid;
    PendingScheduleAdapter pendingScheduleAdapter;
    private ArrayList<ScheduleAssignForORG> scheduleAssignForORGS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_schedule);
        ButterKnife.bind(this);
        Gson gson = new Gson();
        String token = SharedPrefManager.getInstance(this).getUser();
        UserModel obj = gson.fromJson(token, UserModel.class);
        orgid = obj.getOrgID();
        intRecyclerView();
        getAllScheduleList(orgid);
    }

    public void intRecyclerView() {
        pendingScheduleAdapter = new PendingScheduleAdapter(scheduleAssignForORGS, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPendingScheduleList.setLayoutManager(layoutManager);
        rvPendingScheduleList.setAdapter(pendingScheduleAdapter);
    }

    public void getAllScheduleList(int id) {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<ScheduleAssignForORG>> registrationRequest = retrofitService.GetScheduleAssignForCheckINOrg(id);
            registrationRequest.enqueue(new Callback<List<ScheduleAssignForORG>>() {
                @Override
                public void onResponse(Call<List<ScheduleAssignForORG>> call, @NonNull Response<List<ScheduleAssignForORG>> response) {
                    if (response.body() != null) {

                        tvPendingschedule.setText("" + response.body().size());
                        scheduleAssignForORGS.clear();
                        scheduleAssignForORGS.addAll(response.body());
                        pendingScheduleAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<ScheduleAssignForORG>> call, Throwable t) {
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
