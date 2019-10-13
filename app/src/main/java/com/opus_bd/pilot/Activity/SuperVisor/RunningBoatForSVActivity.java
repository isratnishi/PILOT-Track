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
import com.opus_bd.pilot.Adapter.CheckinListAdapter;
import com.opus_bd.pilot.Model.PilotCheckBodyM;
import com.opus_bd.pilot.Model.UserModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.RetrofitService.RetrofitClientInstance;
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

public class RunningBoatForSVActivity extends AppCompatActivity {
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.rvPendingList)
    RecyclerView rvPendingList;
    CheckinListAdapter pendingListAdapter;
    private ArrayList<PilotCheckBodyM> locationNameArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_boat_for_sv);
        ButterKnife.bind(this);

        Gson gson = new Gson();
        String token = SharedPrefManager.getInstance(this).getUser();
        UserModel obj = gson.fromJson(token, UserModel.class);
        String email = obj.getUserName();

        tvUserName.setText(email);
        intRecyclerView();
        getAllList();
    }

    public void intRecyclerView() {
        pendingListAdapter = new CheckinListAdapter(locationNameArrayList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rvPendingList.setLayoutManager(layoutManager);
        rvPendingList.setAdapter(pendingListAdapter);
    }

    public void getAllList() {

        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();
        if (token != null) {
            Call<List<PilotCheckBodyM>> registrationRequest = retrofitService.GetAllPilotCheckIncheckOut();
            registrationRequest.enqueue(new Callback<List<PilotCheckBodyM>>() {
                @Override
                public void onResponse(Call<List<PilotCheckBodyM>> call, @NonNull Response<List<PilotCheckBodyM>> response) {

                    try {
                        if (response.body() != null) {
                            locationNameArrayList.clear();
                            locationNameArrayList.addAll(response.body());
                            pendingListAdapter.notifyDataSetChanged();
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
