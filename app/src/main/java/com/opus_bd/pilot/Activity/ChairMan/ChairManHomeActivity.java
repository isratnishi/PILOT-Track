package com.opus_bd.pilot.Activity.ChairMan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.opus_bd.pilot.Activity.LoginActivity;
import com.opus_bd.pilot.Activity.PilotActivity.MainActivity;
import com.opus_bd.pilot.Activity.RequisitorActivity.CompleteTripActivity;
import com.opus_bd.pilot.Activity.SuperVisor.PendingScheduleforSVActivity;
import com.opus_bd.pilot.Activity.SuperVisor.RunningBoatForSVActivity;
import com.opus_bd.pilot.Activity.SuperVisor.ScheduleforSVActivity;
import com.opus_bd.pilot.Activity.SuperVisor.SuperVisorActivity;
import com.opus_bd.pilot.Model.Organization.TokenforOrgModel;
import com.opus_bd.pilot.Model.PilotCheckBodyM;
import com.opus_bd.pilot.Model.RequisationListModel;
import com.opus_bd.pilot.Model.ScheduleByIDModel.ScheduleByIDModel;
import com.opus_bd.pilot.Model.SuperVisor.RequisitionCancleModel;
import com.opus_bd.pilot.Model.UserModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.RetrofitService.APIClientInterface;
import com.opus_bd.pilot.RetrofitService.RetrofitClientInstance;
import com.opus_bd.pilot.RetrofitService.RetrofitService;
import com.opus_bd.pilot.Utils.SharedPrefManager;
import com.opus_bd.pilot.Utils.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChairManHomeActivity extends AppCompatActivity {
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvRunningRequisation)
    TextView tvRunningRequisation;
    @BindView(R.id.tvCancleRequisation)
    TextView tvCancleRequisation;
    @BindView(R.id.tvTotalRequisation)
    TextView tvTotalRequisation;
    @BindView(R.id.tvPendingSchedule)
    TextView tvPendingSchedule;
    @BindView(R.id.tvRunningSchedule)
    TextView tvRunningSchedule;
    @BindView(R.id.tvRunningBoatStatus)
    TextView tvRunningBoatStatus;
    @BindView(R.id.tvTotalCoupon)
    TextView tvTotalCoupon;
    @BindView(R.id.tvCouponUsed)
    TextView tvCouponUsed;
    @BindView(R.id.tvCouponLeft)
    TextView tvCouponLeft;
    Boolean backPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chair_man_home);
        ButterKnife.bind(this);
        Gson gson = new Gson();
        String token = SharedPrefManager.getInstance(this).getUser();
        UserModel obj = gson.fromJson(token, UserModel.class);

        tvUserName.setText(obj.getUserName());
        getAllRunningRequisationList();
        getAllCancleRequisationList();
        getAllTotalRequisationList();
        getAllPendingScheduleList();
        getAllRunningScheduleList();
        getAllRunningBoatStatusList();
        getAllTokenList();
    }


    public void getAllRunningRequisationList() {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<RequisationListModel>> registrationRequest = retrofitService.GetAllRequisitionDetailsForScheduleAdmin();
            registrationRequest.enqueue(new Callback<List<RequisationListModel>>() {
                @Override
                public void onResponse(Call<List<RequisationListModel>> call, @NonNull Response<List<RequisationListModel>> response) {
                    if (response.body() != null) {
                        tvRunningRequisation.setText(String.valueOf(response.body().size()));
                    }
                }

                @Override
                public void onFailure(Call<List<RequisationListModel>> call, Throwable t) {
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

    public void getAllCancleRequisationList() {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<RequisitionCancleModel>> registrationRequest = retrofitService.GetAllRequisitionMasterCancelAdmin();
            registrationRequest.enqueue(new Callback<List<RequisitionCancleModel>>() {
                @Override
                public void onResponse(Call<List<RequisitionCancleModel>> call, @NonNull Response<List<RequisitionCancleModel>> response) {
                    if (response.body() != null) {
                        tvCancleRequisation.setText(String.valueOf(response.body().size()));
                    }
                }

                @Override
                public void onFailure(Call<List<RequisitionCancleModel>> call, Throwable t) {
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


    public void getAllTotalRequisationList() {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<RequisitionCancleModel>> registrationRequest = retrofitService.GetAllRequisitionDetailsCompleteAdmin();
            registrationRequest.enqueue(new Callback<List<RequisitionCancleModel>>() {
                @Override
                public void onResponse(Call<List<RequisitionCancleModel>> call, @NonNull Response<List<RequisitionCancleModel>> response) {
                    if (response.body() != null) {
                        tvTotalRequisation.setText(String.valueOf(response.body().size()));
                    }
                }

                @Override
                public void onFailure(Call<List<RequisitionCancleModel>> call, Throwable t) {
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


    public void getAllPendingScheduleList() {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<RequisationListModel>> registrationRequest = retrofitService.GetAllRequisitionDetailsPending();
            registrationRequest.enqueue(new Callback<List<RequisationListModel>>() {
                @Override
                public void onResponse(Call<List<RequisationListModel>> call, @NonNull Response<List<RequisationListModel>> response) {
                    if (response.body() != null) {
                        tvPendingSchedule.setText(String.valueOf(response.body().size()));
                    }
                }

                @Override
                public void onFailure(Call<List<RequisationListModel>> call, Throwable t) {
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

    public void getAllRunningScheduleList() {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<ScheduleByIDModel>> registrationRequest = retrofitService.GetAllScheduleAssignLog();
            registrationRequest.enqueue(new Callback<List<ScheduleByIDModel>>() {
                @Override
                public void onResponse(Call<List<ScheduleByIDModel>> call, @NonNull Response<List<ScheduleByIDModel>> response) {
                    if (response.body() != null) {
                        tvRunningSchedule.setText(String.valueOf(response.body().size()));
                    }
                }

                @Override
                public void onFailure(Call<List<ScheduleByIDModel>> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(this, "Not registered! Please sign in to continue", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void getAllRunningBoatStatusList() {

        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();
        if (token != null) {
            Call<List<PilotCheckBodyM>> registrationRequest = retrofitService.GetAllPilotCheckIncheckOut();
            registrationRequest.enqueue(new Callback<List<PilotCheckBodyM>>() {
                @Override
                public void onResponse(Call<List<PilotCheckBodyM>> call, @NonNull Response<List<PilotCheckBodyM>> response) {

                    try {
                        if (response.body() != null) {
                            tvRunningBoatStatus.setText(String.valueOf(response.body().size()));
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

    public void getAllTokenList() {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<TokenforOrgModel>> registrationRequest = retrofitService.GetBalanceTokenByAdmin();
            registrationRequest.enqueue(new Callback<List<TokenforOrgModel>>() {
                @Override
                public void onResponse(Call<List<TokenforOrgModel>> call, @NonNull Response<List<TokenforOrgModel>> response) {
                    if (response.body() != null) {
                        tvTotalCoupon.setText("" + response.body().get(0).getTotalPurchase());
                        tvCouponUsed.setText("" + response.body().get(0).getTotalUse());
                        tvCouponLeft.setText("" + response.body().get(0).getBalanceToken());

                    }
                }

                @Override
                public void onFailure(Call<List<TokenforOrgModel>> call, Throwable t) {
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

    @OnClick(R.id.cvPendingSchedule)
    public void cvPendingSchedule() {

        Intent intent = new Intent(ChairManHomeActivity.this, PendingScheduleforSVActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.cvRunningSchedule)
    public void cvRunningSchedule() {

        Intent intent = new Intent(ChairManHomeActivity.this, ScheduleforSVActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cvCompleteRequisation)
    public void cvCompleteRequisation() {

        Intent intent = new Intent(ChairManHomeActivity.this, CompleteTripActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cvRunningBoatStatus)
    public void cvRunningBoatStatus() {

        Intent intent = new Intent(ChairManHomeActivity.this, RunningBoatForSVActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cvCancleRequisation)
    public void cvCancleRequisation() {

        Intent intent = new Intent(ChairManHomeActivity.this, CancleRequizationforSVActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cvRunningRequisation)
    public void cvRunningRequisation() {

        Intent intent = new Intent(ChairManHomeActivity.this, PendingRequizationforSVActivity.class);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (backPressed) {
            super.onBackPressed();
        }

        // Toast.makeText(this,"Tap Again for Exit",Toast.LENGTH_SHORT).show();
        Toasty.info(this, "Tap Again for Exit").show();
        backPressed = true;
    }
}
