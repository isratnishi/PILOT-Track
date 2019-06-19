package com.opus_bd.pilot.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.opus_bd.pilot.Model.MessageResponse;
import com.opus_bd.pilot.Model.PilotCheckIn;
import com.opus_bd.pilot.Model.ProductModel;
import com.opus_bd.pilot.Model.SalesModel;
import com.opus_bd.pilot.Model.SiteModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.RetrofitService.RetrofitClientInstance;
import com.opus_bd.pilot.RetrofitService.RetrofitService;
import com.opus_bd.pilot.Utils.SharedPrefManager;
import com.opus_bd.pilot.Utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewEntryActivity extends AppCompatActivity {
    @BindView(R.id.btnCheckIn)
    Button btnCheckIn;
    @BindView(R.id.tvYourLocation)
    TextView tvYourLocation;
    @BindView(R.id.tvProduct)
    TextView tvProduct;
    @BindView(R.id.tvSalesTarget)
    TextView tvSalesTarget;
    @BindView(R.id.tvSiteName)
    TextView tvSiteName;
    @BindView(R.id.etTarget)
    EditText etTarget;

    SalesModel model = new SalesModel();

    String location;
    PilotCheckIn pilotCheckIn=new PilotCheckIn();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            location = bundle.getString("Location");
            Utilities.showLogcatMessage(location);

        }

        Gson gson = new Gson();
        String visit = SharedPrefManager.getInstance(NewEntryActivity.this).getVisit();
        model = gson.fromJson(visit, SalesModel.class);

        tvYourLocation.setText(location);
        getSiteName(model.getSiteId());
        getProductName(model.getProductId());
        tvSalesTarget.setText(model.getTarget());

    }

    private void submitToServer() {
//
        //Utilities.showLogcatMessage(tvYourLocation.getText().toString());
       // pilotCheckIn.setLocation(loc);
        pilotCheckIn.setCheckType("CheckIN");
        pilotCheckIn.setPilotID(SharedPrefManager.getInstance(NewEntryActivity.this).getID());
       // pilotCheckIn.setScheduleID(site);
       // pilotCheckIn.setEntryDate(date);
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(NewEntryActivity.this).getUser();
        if (token != null) {

            Call<MessageResponse> saveVisit = retrofitService.postPilotCheckApi(token, pilotCheckIn);

            saveVisit.enqueue(new Callback<MessageResponse>() {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                    if (response.body() != null) {
                       // Toast.makeText(CheckInActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                       // startActivity(new Intent(CheckInActivity.this, MainActivity.class));

                    }
                }

                @Override
                public void onFailure(Call<MessageResponse> call, Throwable t) {
                    //showProgressBar(false);
                    //Toast.makeText(CheckInActivity.this, "Fail to connect ", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Not registered! Please sign in to continue", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void deleteSaleVisit(int id) {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(NewEntryActivity.this).getUser();
        Call<MessageResponse> deleteSaleVisit = retrofitService.deleteSaleVisit(token, id);

        deleteSaleVisit.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                if (response.body() != null) {
                    Toast.makeText(NewEntryActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                //showProgressBar(false);
                Toast.makeText(NewEntryActivity.this, "Fail to connect ", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getProductName(int id) {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();
        Call<ProductModel> registrationRequest = retrofitService.getProductName(token, id);
        registrationRequest.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, @NonNull Response<ProductModel> response) {
                tvProduct.setText(response.body().getProductName());
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Utilities.showLogcatMessage("error " + t.toString());
            }
        });
    }

    public void getSiteName(int id) {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();
        Call<SiteModel> registrationRequest = retrofitService.getSiteName(token, id);
        registrationRequest.enqueue(new Callback<SiteModel>() {
            @Override
            public void onResponse(Call<SiteModel> call, @NonNull Response<SiteModel> response) {
                tvSiteName.setText(response.body().getSiteName());
            }

            @Override
            public void onFailure(Call<SiteModel> call, Throwable t) {
                Utilities.showLogcatMessage("error " + t.toString());
            }
        });
    }

    @OnClick(R.id.btnCheckIn)
    public void btnCheckIn() {
        submitToServer();
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

