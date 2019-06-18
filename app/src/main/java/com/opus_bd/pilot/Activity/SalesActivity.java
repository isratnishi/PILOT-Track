package com.opus_bd.pilot.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.opus_bd.pilot.Adapter.SalesAdapter;
import com.opus_bd.pilot.Model.MessageEvent;
import com.opus_bd.pilot.Model.SalesModel;
import com.opus_bd.pilot.Model.UserModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.RetrofitService.RetrofitClientInstance;
import com.opus_bd.pilot.RetrofitService.RetrofitService;
import com.opus_bd.pilot.Utils.SharedPrefManager;
import com.opus_bd.pilot.Utils.Utilities;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesActivity extends AppCompatActivity {

    @BindView(R.id.rvSalesList)
    RecyclerView rvSalesList;
    SalesAdapter salesAdapter;
    private ArrayList<SalesModel> locationNameArrayList = new ArrayList<>();

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        ButterKnife.bind(this);
        Gson gson = new Gson();
        String token = SharedPrefManager.getInstance(this).getUser();
        UserModel obj = gson.fromJson(token, UserModel.class);
        String email = obj.getEmail();
        getUser(email);
        intRecyclerView();

    }

    public void intRecyclerView() {
        salesAdapter = new SalesAdapter(locationNameArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvSalesList.setLayoutManager(layoutManager);
        rvSalesList.setAdapter(salesAdapter);
    }

    public void getUser(String email) {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();
        Call<UserModel> registrationRequest = retrofitService.getUser(token, email);
        registrationRequest.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, @NonNull Response<UserModel> response) {
                int id = response.body().getId();
                SharedPrefManager.getInstance(SalesActivity.this).saveID(id);
                getSaleVisit(id);


            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Utilities.showLogcatMessage("error " + t.toString());
            }
        });
    }

    public void getSaleVisit(int id) {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<SalesModel>> registrationRequest = retrofitService.getSaleVisit(token, id);
            registrationRequest.enqueue(new Callback<List<SalesModel>>() {
                @Override
                public void onResponse(Call<List<SalesModel>> call, @NonNull Response<List<SalesModel>> response) {
                    if (response.body() != null) {
                        locationNameArrayList.clear();
                        locationNameArrayList.addAll(response.body());
                        salesAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<SalesModel>> call, Throwable t) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Utilities.showLogcatMessage(" Event Bus");
        if (event.isUpdate()) {
            int i = SharedPrefManager.getInstance(this).getID();

            Utilities.showLogcatMessage(" Event Bus i " + i);
            getSaleVisit(i);
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
