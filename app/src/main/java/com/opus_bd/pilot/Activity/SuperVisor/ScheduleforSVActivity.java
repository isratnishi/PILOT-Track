package com.opus_bd.pilot.Activity.SuperVisor;

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
import com.opus_bd.pilot.Adapter.PendingCheckinListAdapter;
import com.opus_bd.pilot.Adapter.RunningScheduleListAdapter;
import com.opus_bd.pilot.Model.ScheduleByIDModel.ScheduleByIDModel;
import com.opus_bd.pilot.Model.UserModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.RetrofitService.APIClientInterface;
import com.opus_bd.pilot.RetrofitService.RetrofitService;
import com.opus_bd.pilot.Utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleforSVActivity extends AppCompatActivity {
    @BindView(R.id.rvPendingList)
    RecyclerView rvPendingList;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    RunningScheduleListAdapter pendingListAdapter;
    private ArrayList<ScheduleByIDModel> locationNameArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedulefor_sv);
        ButterKnife.bind(this);
        Gson gson = new Gson();
        String token = SharedPrefManager.getInstance(this).getUser();
        UserModel obj = gson.fromJson(token, UserModel.class);
        String email = obj.getUserName();

        getAllList();
        intRecyclerView();

    }

    public void intRecyclerView() {
        pendingListAdapter = new RunningScheduleListAdapter(locationNameArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPendingList.setLayoutManager(layoutManager);
        rvPendingList.setAdapter(pendingListAdapter);
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
                        locationNameArrayList.clear();
                        locationNameArrayList.addAll(response.body());
                        pendingListAdapter.notifyDataSetChanged();

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
