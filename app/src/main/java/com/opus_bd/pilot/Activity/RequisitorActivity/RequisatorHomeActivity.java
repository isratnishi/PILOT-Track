package com.opus_bd.pilot.Activity.RequisitorActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.opus_bd.pilot.Activity.LoginActivity;
import com.opus_bd.pilot.Activity.PilotActivity.MainActivity;
import com.opus_bd.pilot.Activity.PendingSalesActivity;
import com.opus_bd.pilot.Activity.PilotActivity.RouteActivity;
import com.opus_bd.pilot.Adapter.RunningReqListAdapter;
import com.opus_bd.pilot.Model.OrganizationModel;
import com.opus_bd.pilot.Model.RequisationListModel;
import com.opus_bd.pilot.Model.UserModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.RetrofitService.APIClientInterface;
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

public class RequisatorHomeActivity extends AppCompatActivity {

    @BindView(R.id.tvUserName)
    TextView tvUserName;

    @BindView(R.id.tvTotalCoupon)
    TextView tvTotalCoupon;

     @BindView(R.id.tvTotalRequisation)
    TextView tvTotalRequisation;

     @BindView(R.id.tvCancleRequisation)
    TextView tvCancleRequisation;

     @BindView(R.id.tvRunningRequisation)
    TextView tvRunningRequisation;

    @BindView(R.id.rvlist)
    RecyclerView rvlist;

    RunningReqListAdapter runningReqListAdapter;
    private ArrayList<RequisationListModel> requisationListModels = new ArrayList<>();

    @BindView(R.id.cvNewREq)
    CardView cvNewREq;
    int orgid;
    int ongoging =0;
    int Cancle =0;
    int Complete =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisator_home);
        ButterKnife.bind(this);
        Gson gson = new Gson();
        String token = SharedPrefManager.getInstance(this).getUser();
        UserModel obj = gson.fromJson(token, UserModel.class);
        orgid = obj.getOrgID();
        tvUserName.setText(obj.getUserName());
        intRecyclerView();
        getAllList(orgid);
        getAllReqList(orgid);
    }
    public void intRecyclerView() {
        runningReqListAdapter = new RunningReqListAdapter(requisationListModels, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvlist.setLayoutManager(layoutManager);
        rvlist.setAdapter(runningReqListAdapter);
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

                        tvTotalCoupon.setText(String.valueOf(response.body().get(0).getNoOfTokenbalance()));
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

    public void getAllReqList(int id) {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<RequisationListModel>> registrationRequest = retrofitService.GETRequisationListModelByOrgID( id);
            registrationRequest.enqueue(new Callback<List<RequisationListModel>>() {
                @Override
                public void onResponse(Call<List<RequisationListModel>> call, @NonNull Response<List<RequisationListModel>> response) {
                    if (response.body() != null ) {

                        for(int i=0;i<response.body().size();i++){
                            if (response.body().get(i).getReq().getRequisitionStatusId() == 1) {
                                ongoging=ongoging+1;

                            }

                             if(response.body().get(i).getReq().getRequisitionStatusId()==2){
                                Cancle=Cancle+1;

                            }

                            if(response.body().get(i).getReq().getRequisitionStatusId()==3){
                                Complete=Complete+1;

                            }
                        }

                        tvRunningRequisation.setText(String.valueOf(ongoging));
                        tvCancleRequisation.setText(String.valueOf(Cancle));
                        tvTotalRequisation.setText(String.valueOf(Complete));
                       // tvTotalCoupon.setText(String.valueOf(response.body().get(0).getNoOfTokenbalance()));
                        /*tvOrganization.setText(response.body().get(0).getOrganizationName());
                        shipmodel.addAll(response.body().get(0).getShips());
                        addShipNameSpinnerData(shipmodel);*/
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

    @OnClick(R.id.cvNewREq)
    public void cvNewREq() {

        Intent intent = new Intent(RequisatorHomeActivity.this, RequisationEntryActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cvBuyCoupon)
    public void cvBuyCoupon() {

        Intent intent = new Intent(RequisatorHomeActivity.this, CouponBuyActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cvRunningRequisation)
    public void cvRunningRequisation() {

        Intent intent = new Intent(RequisatorHomeActivity.this, OngoingReqLIstActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cvCancleRequisation)
    public void cvCancleRequisation() {

        Intent intent = new Intent(RequisatorHomeActivity.this, CancleReqActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cvCompleteRequisation)
    public void cvCompleteRequisation() {

        Intent intent = new Intent(RequisatorHomeActivity.this, CompleteTripActivity.class);
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
            Intent intent = new Intent(this, RouteActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
