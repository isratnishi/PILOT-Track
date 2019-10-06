package com.opus_bd.pilot.Activity.PilotActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.opus_bd.pilot.Activity.LoginActivity;
import com.opus_bd.pilot.Activity.NewCheckINActivity;
import com.opus_bd.pilot.Activity.PendingSalesActivity;
import com.opus_bd.pilot.Activity.RequisitorActivity.RequisationEntryActivity;
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
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tvUserName)
    TextView tvUserName;  @BindView(R.id.tvCompleteTrip)
    TextView tvCompleteTrip;
    private ArrayList<PilotCheckBodyM> locationNameArrayList = new ArrayList<>();
    int ongoging =0;
    int Cancle =0;
    int Complete =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Gson gson = new Gson();
        String token = SharedPrefManager.getInstance(this).getUser();
        UserModel obj = gson.fromJson(token, UserModel.class);

        String email = obj.getUserName();
        int id=obj.getPilotID();
        getAllList(id);
        tvUserName.setText("Welcome\n"+email);
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
                            Utilities.showLogcatMessage(" !  ");
                            for(int i=0;i<response.body().size();i++){
                               if(response.body().get(i).getCheckType().equals("Check In")){
                                    ongoging=ongoging+1;
                                   Utilities.showLogcatMessage(  "ongoging  "+ongoging);

                                } else if(response.body().get(i).getCheckType().equals("Check Out")){

                                    Complete=Complete+1;
                                    Utilities.showLogcatMessage(  "Check Out  "+Complete);
                                }
                               else {

                                   Cancle=Cancle+1;
                                   Utilities.showLogcatMessage(  "Cancle Out  "+Cancle);
                               }
                            }

                            tvCompleteTrip.setText("CompleteTrip\n"+Complete);
                        }

                    }
                    catch (Exception e){
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
    @OnClick(R.id.cvCheckIN)
    public void tvPendingSales() {

        Intent intent = new Intent(MainActivity.this, PendingSalesActivity.class);
        startActivity(intent);
    }
  @OnClick(R.id.cvCheckOUT)
    public void cvCheckOUT() {

        Intent intent = new Intent(MainActivity.this, PendingSalesActivity.class);
        startActivity(intent);
    }



    @OnClick(R.id.cvRoute)
    public void tvRoute() {

        Intent intent = new Intent(MainActivity.this, RouteActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cvNewEntry)
    public void fabNewEntry() {

        Intent intent = new Intent(MainActivity.this, NewCheckINActivity.class);
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
        if (id == R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        if (id == R.id.pendingList) {
            Intent intent = new Intent(this, PendingSalesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        if (id == R.id.newEntry) {
            Intent intent = new Intent(this, RequisationEntryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        if (id == R.id.salesList) {
            Intent intent = new Intent(MainActivity.this, RouteActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
