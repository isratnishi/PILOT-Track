package com.opus_bd.pilot.Activity.ChairMan;

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
import com.opus_bd.pilot.Activity.LoginActivity;
import com.opus_bd.pilot.Adapter.CancleReqListAdapter;
import com.opus_bd.pilot.Adapter.CancleReqListforSVAdapter;
import com.opus_bd.pilot.Model.RequisationListModel;
import com.opus_bd.pilot.Model.SuperVisor.RequisitionCancleModel;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancleRequizationforSVActivity extends AppCompatActivity {
    @BindView(R.id.rvlist)
    RecyclerView rvlist;
    int orgid;
    CancleReqListforSVAdapter runningReqListAdapter;
    private ArrayList<RequisitionCancleModel> requisationListModels = new ArrayList<>();
    private ArrayList<RequisationListModel> requisationListModel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancle_requizationfor_sv);
        ButterKnife.bind(this);
        Gson gson = new Gson();
        String token = SharedPrefManager.getInstance(this).getUser();
        UserModel obj = gson.fromJson(token, UserModel.class);
        intRecyclerView();
       /* tvUserName.setText(obj.getUserName());
        getAllList(orgid);*/
        getAllReqList();
    }

    public void intRecyclerView() {
        runningReqListAdapter = new CancleReqListforSVAdapter(requisationListModels, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvlist.setLayoutManager(layoutManager);
        rvlist.setAdapter(runningReqListAdapter);
    }

    public void getAllReqList() {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<RequisitionCancleModel>> registrationRequest = retrofitService.GetAllRequisitionMasterCancelAdmin();
            registrationRequest.enqueue(new Callback<List<RequisitionCancleModel>>() {
                @Override
                public void onResponse(Call<List<RequisitionCancleModel>> call, @NonNull Response<List<RequisitionCancleModel>> response) {
                    if (response.body() != null) {
                        requisationListModels.clear();
                        requisationListModels.addAll(response.body());
                        runningReqListAdapter.notifyDataSetChanged();
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
