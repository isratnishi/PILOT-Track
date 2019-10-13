package com.opus_bd.pilot.Activity.PilotActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.opus_bd.pilot.Activity.LoginActivity;
import com.opus_bd.pilot.Adapter.RunningCheckinListAdapter;
import com.opus_bd.pilot.Model.GFG;
import com.opus_bd.pilot.Model.PilotCheckBodyM;
import com.opus_bd.pilot.Model.UserModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.RetrofitService.RetrofitClientInstance;
import com.opus_bd.pilot.RetrofitService.RetrofitService;
import com.opus_bd.pilot.Utils.SharedPrefManager;
import com.opus_bd.pilot.Utils.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.ContactsContract.*;

public class CompleteListActivity extends AppCompatActivity {

    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.rvPendingList)
    RecyclerView rvPendingList;

    RunningCheckinListAdapter pendingListAdapter;
    private ArrayList<PilotCheckBodyM> locationNameArrayList = new ArrayList<>();
    private ArrayList<PilotCheckBodyM> ScheduleNameArrayList = new ArrayList<>();
    private ArrayList<Integer> ScheduleIDArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        ButterKnife.bind(this);

        Gson gson = new Gson();
        String token = SharedPrefManager.getInstance(this).getUser();
        UserModel obj = gson.fromJson(token, UserModel.class);
        String email = obj.getUserName();
        int id = obj.getPilotID();
        tvUserName.setText(email);
        intRecyclerView();
        getAllList(id);

    }

    public void intRecyclerView() {
        pendingListAdapter = new RunningCheckinListAdapter(locationNameArrayList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(false);
        layoutManager.setStackFromEnd(true);
        rvPendingList.setLayoutManager(layoutManager);
        rvPendingList.setAdapter(pendingListAdapter);
    }

    public void getAllList(int id) {

        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();
        if (token != null) {
            Call<List<PilotCheckBodyM>> registrationRequest = retrofitService.getCheckIn(token, id);
            registrationRequest.enqueue(new Callback<List<PilotCheckBodyM>>() {
                @Override
                public void onResponse(Call<List<PilotCheckBodyM>> call, @NonNull Response<List<PilotCheckBodyM>> response) {

                    try {
                        if (response.body() != null) {
                            locationNameArrayList.clear();
                            for (int i = 0; i < response.body().size(); i++) {

                                try {
                                    if (response.body().get(i).getCheckType().equals("Check Out")) {
                                        //  locationNameArrayList.add(response.body().get(i));
                                        if (!ScheduleIDArrayList.contains(response.body().get(i).getScheduleID())) {
                                            ScheduleIDArrayList.add(response.body().get(i).getScheduleID());
                                            ScheduleNameArrayList.add(response.body().get(i));
                                        }

                                    }
                                } catch (Exception e) {
                                }

                            }
                            // Utilities.showLogcatMessage(" ScheduleNameArrayList "+ScheduleNameArrayList.size());


                            list();
                            pendingListAdapter.notifyDataSetChanged();
                        }

                    } catch (Exception e) {
                    }


                }

                @Override
                public void onFailure(Call<List<PilotCheckBodyM>> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(this, "Not registered! Please sign in to continue", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void list() {

        Utilities.showLogcatMessage(" ScheduleNameArrayList " + ScheduleNameArrayList.size());
        for (int i = 0; i < ScheduleNameArrayList.size(); i++) {
            Utilities.showLogcatMessage(" ScheduleNameArrayList " + ScheduleNameArrayList.get(i).getScheduleID());


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
