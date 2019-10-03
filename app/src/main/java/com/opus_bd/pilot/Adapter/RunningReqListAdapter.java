package com.opus_bd.pilot.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.opus_bd.pilot.Activity.RequisitorActivity.CouponBuyActivity;
import com.opus_bd.pilot.Activity.RequisitorActivity.RequisatorHomeActivity;
import com.opus_bd.pilot.Model.PilotCheckBodyM;
import com.opus_bd.pilot.Model.RequisationListModel;
import com.opus_bd.pilot.Model.TokenBuyModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.RetrofitService.APIClientInterface;
import com.opus_bd.pilot.RetrofitService.RetrofitService;
import com.opus_bd.pilot.Utils.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RunningReqListAdapter extends RecyclerView.Adapter<RunningReqListAdapter.ItemViewHolder> {
    private final Context context;
    private List<RequisationListModel> items;
    public RunningReqListAdapter(List<RequisationListModel> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_req_list, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        RequisationListModel item = items.get(position);
        holder.set(item);
    }

    @Override
    public int getItemCount() {
        if (items == null) {
            return 0;
        }
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvshipNo)
        TextView tvshipNo;
        @BindView(R.id.tvtripStartDate)
        TextView tvtripStartDate;
        @BindView(R.id.tvstartPort)
        TextView tvstartPort; @BindView(R.id.tvendPort)
        TextView tvendPort;
        @BindView(R.id.btnCancle)
        Button btnCancle;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void set(final RequisationListModel item) {
            //getSiteName(item.getSiteId());
            tvshipNo.setText(item.getShipNo());
            tvtripStartDate.setText(item.getReq().getTripStartDate());
            tvstartPort.setText(item.getReq().getStartPort());
            tvendPort.setText(item.getReq().getEndPort());

           btnCancle.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Utilities.showLogcatMessage("item.getReqID() "+item.getReqID());
                   submitToServer(item.getReqID());
               }
           });

        }
    }

    private void submitToServer(int id) {
        Utilities.showLogcatMessage(" Button ");
        RetrofitService retrofitService = APIClientInterface.getClient().create(RetrofitService.class);

        Call<String> registrationRequest = retrofitService.CancelledRequisition(id);
        registrationRequest.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Utilities.showLogcatMessage(" response.body() " + response.body());
                if (response.body() != null) {
                    Toast.makeText(context, "" + response.body(), Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, RequisatorHomeActivity.class));
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "failed response.body()", Toast.LENGTH_SHORT).show();
                Utilities.showLogcatMessage("failed response.body()");

            }
        });
    }

   /* public void getSiteName(int id) {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(context).getUser();
        Call<SiteModel> getSiteName = retrofitService.getSiteName(token, id);
        getSiteName.enqueue(new Callback<SiteModel>() {
            @Override
            public void onResponse(Call<SiteModel> call, @NonNull Response<SiteModel> response) {
                tvPendingSalesSite.setText(response.body().getSiteName());

                Utilities.showLogcatMessage(" Site Name : " + tvPendingSalesSite.getText());
            }

            @Override
            public void onFailure(Call<SiteModel> call, Throwable t) {
                Utilities.showLogcatMessage("error " + t.toString());
            }
        });
    }*/
}
