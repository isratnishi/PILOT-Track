package com.opus_bd.pilot.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.opus_bd.pilot.Adapter.PendingCheckinListAdapter;
import com.opus_bd.pilot.Model.ScheduleModel;
import com.opus_bd.pilot.Model.UserInfo;
import com.opus_bd.pilot.Model.UserModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.RetrofitService.APIClientInterface;
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

public class PendingSalesActivity extends AppCompatActivity {
    @BindView(R.id.rvPendingList)
    RecyclerView rvPendingList;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    PendingCheckinListAdapter pendingListAdapter;
    private ArrayList<ScheduleModel> locationNameArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_sales);
        ButterKnife.bind(this);
        Gson gson = new Gson();
        String token = SharedPrefManager.getInstance(this).getUser();
        UserModel obj = gson.fromJson(token, UserModel.class);
        String email = obj.getName();
        getUser(email);
        getAllList(2);
        intRecyclerView();

    }

    public void intRecyclerView() {
        pendingListAdapter = new PendingCheckinListAdapter(locationNameArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPendingList.setLayoutManager(layoutManager);
        rvPendingList.setAdapter(pendingListAdapter);
    }

    public void getUser(String userName) {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();
        Call<UserInfo> registrationRequest = retrofitService.getUserInfo(token, userName);
        registrationRequest.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, @NonNull Response<UserInfo> response) {

                try {
                    if(response.body()!=null)
                    {
                        Utilities.showLogcatMessage(" Email " + response.body().getEmail());
                        Utilities.showLogcatMessage(" Id" + response.body().getId());

                        int id = response.body().getPilotID();
                      // getAllList(id);
                        tvUserName.setText(response.body().getUserName());
                    }

                    else Utilities.showLogcatMessage(" Responce Null");
                }
                catch (Exception e){}

            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Utilities.showLogcatMessage("error " + t.toString());
            }
        });
    }

    public void getAllList(int id) {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<ScheduleModel>> registrationRequest = retrofitService.getvisitList(token, id);
            registrationRequest.enqueue(new Callback<List<ScheduleModel>>() {
                @Override
                public void onResponse(Call<List<ScheduleModel>> call, @NonNull Response<List<ScheduleModel>> response) {
                    if (response.body() != null) {
                        locationNameArrayList.clear();
                        locationNameArrayList.addAll(response.body());
                        pendingListAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<ScheduleModel>> call, Throwable t) {
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
