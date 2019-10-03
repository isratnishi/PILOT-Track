package com.opus_bd.pilot.Activity.RequisitorActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.opus_bd.pilot.Activity.LoginActivity;
import com.opus_bd.pilot.Model.OrganizationModel;
import com.opus_bd.pilot.Model.RequisationPostModel;
import com.opus_bd.pilot.Model.TokenBuyModel;
import com.opus_bd.pilot.Model.UserModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.RetrofitService.APIClientInterface;
import com.opus_bd.pilot.RetrofitService.RetrofitService;
import com.opus_bd.pilot.Utils.SharedPrefManager;
import com.opus_bd.pilot.Utils.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CouponBuyActivity extends AppCompatActivity {

    @BindView(R.id.tvOrganizationName)
    TextView tvOrganizationName;
    @BindView(R.id.tvCouponLeft)
    TextView tvCouponLeft;
    @BindView(R.id.etNoofCoupon)
    EditText etNoofCoupon; @BindView(R.id.etpaymentRef)
    EditText etpaymentRef;
    @BindView(R.id.tvCouponUnitPrice)
    TextView tvCouponUnitPrice;
    @BindView(R.id.tvTotalPrice)
    TextView tvTotalPrice;

    int orgid, Total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_buy);
        ButterKnife.bind(this);
        Gson gson = new Gson();
        String token = SharedPrefManager.getInstance(this).getUser();
        UserModel obj = gson.fromJson(token, UserModel.class);
        orgid = obj.getOrgID();
        getAllList(orgid);
        etNoofCoupon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int coupunno=Integer.parseInt(etNoofCoupon.getText().toString());
                int total =coupunno*400;
                tvTotalPrice.setText(String.valueOf(total));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void getAllList(int id) {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<OrganizationModel>> registrationRequest = retrofitService.GETOrganizationModelByOrgID(token, id);
            registrationRequest.enqueue(new Callback<List<OrganizationModel>>() {
                @Override
                public void onResponse(Call<List<OrganizationModel>> call, @NonNull Response<List<OrganizationModel>> response) {
                    if (response.body() != null) {
                        Utilities.showLogcatMessage("response " + response.body().get(0).getNoOfTokenbalance());
                       tvOrganizationName.setText(response.body().get(0).getOrganizationName());
                       tvCouponLeft.setText(String.valueOf(response.body().get(0).getNoOfTokenbalance()));
                    }
                }

                @Override
                public void onFailure(Call<List<OrganizationModel>> call, Throwable t) {
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


    @OnClick(R.id.btnCheckIn)
    public void btnCheckIn() {


        submitToServer();

    }

    private void submitToServer() {
        Utilities.showLogcatMessage(" Button ");
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        TokenBuyModel tokenBuyModel = new TokenBuyModel(orgid,Integer.parseInt(etNoofCoupon.getText().toString()),
                etpaymentRef.getText().toString(),
                400,  Integer.parseInt(tvTotalPrice.getText().toString()));

        Utilities.showLogcatMessage(" requisationPostModel " + tokenBuyModel.toString());

        Call<String> registrationRequest = retrofitService.GetTokenSave(tokenBuyModel);
        registrationRequest.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Utilities.showLogcatMessage(" response.body() " + response.body());
                if (response.body() != null) {
                    Toast.makeText(CouponBuyActivity.this, "" + response.body(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CouponBuyActivity.this, RequisatorHomeActivity.class));
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(CouponBuyActivity.this, "failed response.body()", Toast.LENGTH_SHORT).show();
                Utilities.showLogcatMessage("failed response.body()");

            }
        });
    }
}
