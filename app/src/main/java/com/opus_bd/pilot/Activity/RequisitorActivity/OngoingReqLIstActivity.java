package com.opus_bd.pilot.Activity.RequisitorActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.opus_bd.pilot.Activity.LoginActivity;
import com.opus_bd.pilot.Adapter.RunningReqListAdapter;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OngoingReqLIstActivity extends AppCompatActivity {
    @BindView(R.id.rvlist)
    RecyclerView rvlist;
    int orgid;
    RunningReqListAdapter runningReqListAdapter;
    private ArrayList<RequisationListModel> requisationListModels = new ArrayList<>();
    private ArrayList<RequisationListModel> requisationListModel = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing_req_list);
        ButterKnife.bind(this);
        Gson gson = new Gson();
        String token = SharedPrefManager.getInstance(this).getUser();
        UserModel obj = gson.fromJson(token, UserModel.class);
        orgid = obj.getOrgID();
        intRecyclerView();
       /* tvUserName.setText(obj.getUserName());
        getAllList(orgid);*/
        getAllReqList(orgid);
    }
    public void intRecyclerView() {
        runningReqListAdapter = new RunningReqListAdapter(requisationListModels, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvlist.setLayoutManager(layoutManager);
        rvlist.setAdapter(runningReqListAdapter);
    }

    public void getAllReqList(int id) {
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(this).getUser();

        if (token != null) {
            Call<List<RequisationListModel>> registrationRequest = retrofitService.GETRequisationListModelByOrgID( id);
            registrationRequest.enqueue(new Callback<List<RequisationListModel>>() {
                @Override
                public void onResponse(Call<List<RequisationListModel>> call, @NonNull Response<List<RequisationListModel>> response) {
                    if (response.body() != null) {
                        requisationListModels.clear();
                        for(int i=0;i<response.body().size();i++){
                            Utilities.showLogcatMessage("response " + response.body().size());
                            if(response.body().get(i).getReq().getRequisitionStatusId()==1){

                                requisationListModels.add(response.body().get(i));
                            }
                        }
                        runningReqListAdapter.notifyDataSetChanged();
                        Utilities.showLogcatMessage(" runningReqListAdapter.getItemCount() "+runningReqListAdapter.getItemCount());

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
}
