package com.opus_bd.salestracking.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.opus_bd.salestracking.Activity.CheckInActivity;
import com.opus_bd.salestracking.Model.ProductModel;
import com.opus_bd.salestracking.Model.SalesModel;
import com.opus_bd.salestracking.Model.SiteModel;
import com.opus_bd.salestracking.R;
import com.opus_bd.salestracking.RetrofitService.RetrofitClientInstance;
import com.opus_bd.salestracking.RetrofitService.RetrofitService;
import com.opus_bd.salestracking.Utils.SharedPrefManager;
import com.opus_bd.salestracking.Utils.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingListAdapter extends RecyclerView.Adapter<PendingListAdapter.ItemViewHolder> {
    private final Context context;
    private List<SalesModel> items;
    private boolean visibleProfit = false;
    TextView tvPendingSalesSite;
    public void setVisibleProfit(boolean visibleProfit) {
        this.visibleProfit = visibleProfit;
    }

    public PendingListAdapter(List<SalesModel> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pending_list, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        SalesModel item = items.get(position);
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

        @BindView(R.id.tvPendingSalesLocation)
        TextView tvPendingSalesLocation;
        @BindView(R.id.tvPendingSalesTraget)
        TextView tvPendingSalesTraget;
        @BindView(R.id.btnCheckIn)
        Button btnCheckIn;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvPendingSalesSite = (TextView) itemView.findViewById(R.id.tvPendingSalesSite);
        }

        public void set(final SalesModel item) {
            getSiteName(item.getSiteId());
            tvPendingSalesLocation.setText(item.getLocation());
            tvPendingSalesTraget.setText(item.getTarget());
            btnCheckIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPrefManager.getInstance(context).saveVisit(item);

                    Intent intent = new Intent(context, CheckInActivity.class);
                    intent.putExtra("Location", tvPendingSalesLocation.getText());
                    context.startActivity(intent);
                }
            });


        }
    }

    public void getSiteName(int id) {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(context).getUser();
        Call<SiteModel> registrationRequest = retrofitService.getSiteName(token, id);
        registrationRequest.enqueue(new Callback<SiteModel>() {
            @Override
            public void onResponse(Call<SiteModel> call, @NonNull Response<SiteModel> response) {
                tvPendingSalesSite.setText(response.body().getSiteName());
            }

            @Override
            public void onFailure(Call<SiteModel> call, Throwable t) {
                Utilities.showLogcatMessage("error " + t.toString());
            }
        });
    }
}
