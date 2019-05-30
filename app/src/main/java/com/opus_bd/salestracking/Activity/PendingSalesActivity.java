package com.opus_bd.salestracking.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.opus_bd.salestracking.Adapter.PendingListAdapter;
import com.opus_bd.salestracking.Model.SalesModel;
import com.opus_bd.salestracking.Model.UserModel;
import com.opus_bd.salestracking.R;
import com.opus_bd.salestracking.RetrofitService.RetrofitClientInstance;
import com.opus_bd.salestracking.RetrofitService.RetrofitService;
import com.opus_bd.salestracking.Utils.SharedPrefManager;
import com.opus_bd.salestracking.Utils.Utilities;

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
    PendingListAdapter pendingListAdapter;
    private ArrayList<SalesModel> locationNameArrayList = new ArrayList<>();
    int SPID;

    public void setSPID(int SPID) {
        this.SPID = SPID;
    }

    public int getSPID() {
        return SPID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_sales);
        ButterKnife.bind(this);
        Gson gson = new Gson();
        String token = SharedPrefManager.getInstance(this).getUser();
        UserModel obj = gson.fromJson(token, UserModel.class);
        String email = obj.getEmail();
        getUser(email);
        intRecyclerView();

    }

    public void intRecyclerView() {
        pendingListAdapter = new PendingListAdapter(locationNameArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPendingList.setLayoutManager(layoutManager);
        rvPendingList.setAdapter(pendingListAdapter);
    }

    public void getUser(String email) {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();
        Call<UserModel> registrationRequest = retrofitService.getUser(token, email);
        registrationRequest.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, @NonNull Response<UserModel> response) {

                int id = response.body().getId();
                getAllList(id);


            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Utilities.showLogcatMessage("error " + t.toString());
            }
        });
    }

    public void getAllList(int id) {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<SalesModel>> registrationRequest = retrofitService.getvisitList(token, id);
            registrationRequest.enqueue(new Callback<List<SalesModel>>() {
                @Override
                public void onResponse(Call<List<SalesModel>> call, @NonNull Response<List<SalesModel>> response) {
                    if (response.body() != null) {
                        locationNameArrayList.clear();
                        locationNameArrayList.addAll(response.body());
                        pendingListAdapter.notifyDataSetChanged();
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
}
