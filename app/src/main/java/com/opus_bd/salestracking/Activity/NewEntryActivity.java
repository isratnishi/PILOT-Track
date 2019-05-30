package com.opus_bd.salestracking.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.opus_bd.salestracking.Model.GeocodingLocation;
import com.opus_bd.salestracking.Model.MessageResponse;
import com.opus_bd.salestracking.Model.SalesModel;
import com.opus_bd.salestracking.R;
import com.opus_bd.salestracking.RetrofitService.RetrofitClientInstance;
import com.opus_bd.salestracking.RetrofitService.RetrofitService;
import com.opus_bd.salestracking.Utils.SharedPrefManager;
import com.opus_bd.salestracking.Utils.Utilities;

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
    @BindView(R.id.etProduct)
    TextView etProduct;
    @BindView(R.id.etSalesTarget)
    TextView etSalesTarget;
    @BindView(R.id.etTarget)
    TextView etTarget;

    SalesModel model = new SalesModel();

    String location, target;
    int site, product, salesperson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            location = bundle.getString("message");
            product = bundle.getInt("productid");
            site = bundle.getInt("siteid");
            target = bundle.getString("target");
            salesperson = bundle.getInt("spid");

        }
        tvYourLocation.setText(location);
        etSalesTarget.setText(target);
        etProduct.setText(product);

    }

    private void submitToServer() {
        model.setSiteId(Integer.valueOf(site));
        model.setLocation(tvYourLocation.getText().toString());
        model.setTarget(target);
        model.setProductId(product);
        model.setTargetmeet(etTarget.getText().toString());
        model.setSalespersonId(salesperson);

        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(NewEntryActivity.this).getUser();
        if (token != null) {
            Call<MessageResponse> addCompany = retrofitService.saveVisit(token, model);
            addCompany.enqueue(new Callback<MessageResponse>() {
                @Override
                public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                    Utilities.showLogcatMessage("  " + response.body().getStatus());
                    // showProgressBar(false);
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
        } else {
            Toast.makeText(this, "Not registered! Please sign in to continue", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @OnClick(R.id.btnCheckIn)
    public void btnCheckIn() {
        submitToServer();
    }

}

