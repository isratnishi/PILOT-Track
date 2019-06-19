package com.opus_bd.pilot.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.opus_bd.pilot.Adapter.CheckinListAdapter;
import com.opus_bd.pilot.Adapter.PendingCheckinListAdapter;
import com.opus_bd.pilot.Model.CheckinModel;
import com.opus_bd.pilot.Model.ScheduleModel;
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

public class RouteActivity extends AppCompatActivity {
    @BindView(R.id.rvPendingList)
    RecyclerView rvPendingList;
    CheckinListAdapter pendingListAdapter;
    private ArrayList<CheckinModel> locationNameArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        ButterKnife.bind(this);


        intRecyclerView();
        getAllList(2);
    }

    public void intRecyclerView() {
        pendingListAdapter = new CheckinListAdapter(locationNameArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPendingList.setLayoutManager(layoutManager);
        rvPendingList.setAdapter(pendingListAdapter);
    }

    public void getAllList(int id) {

        Utilities.showLogcatMessage(" RESPONCE 1!");
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();
        Utilities.showLogcatMessage(" RESPONCE 2!");
        if (token != null) {

            Utilities.showLogcatMessage(" RESPONCE 3!");
            Call<List<CheckinModel>> registrationRequest = retrofitService.getCheckIn(token, id);
            Utilities.showLogcatMessage(" RESPONCE 4!");
            registrationRequest.enqueue(new Callback<List<CheckinModel>>() {
                @Override
                public void onResponse(Call<List<CheckinModel>> call, @NonNull Response<List<CheckinModel>> response) {

                    try {
                        if (response.body() != null) {
                            Utilities.showLogcatMessage(" RESPONCE !");
                            locationNameArrayList.clear();
                            locationNameArrayList.addAll(response.body());
                            pendingListAdapter.notifyDataSetChanged();
                        }
                        else Utilities.showLogcatMessage(" RESPONCE null");
                    }
                    catch (Exception e){

                        Utilities.showLogcatMessage(" RESPONCE null"+e);
                    }


                }

                @Override
                public void onFailure(Call<List<CheckinModel>> call, Throwable t) {
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
