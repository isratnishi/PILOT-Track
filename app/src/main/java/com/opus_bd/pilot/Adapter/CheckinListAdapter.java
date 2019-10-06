package com.opus_bd.pilot.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.opus_bd.pilot.Activity.CheckInActivity;
import com.opus_bd.pilot.Model.CheckinModel;
import com.opus_bd.pilot.Model.PilotCheckBodyM;
import com.opus_bd.pilot.Model.ScheduleModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.Utils.SharedPrefManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckinListAdapter extends RecyclerView.Adapter<CheckinListAdapter.ItemViewHolder> {
    private final Context context;
    private List<PilotCheckBodyM> items;

    public CheckinListAdapter(List<PilotCheckBodyM> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_route_list, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        PilotCheckBodyM item = items.get(position);

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

        @BindView(R.id.tvEntryDate)
        TextView tvEntryDate;
        @BindView(R.id.tvEntryTime)
        TextView tvEntryTime;
        @BindView(R.id.tvShipName)
        TextView tvShipName;
        @BindView(R.id.tvBitName)
        TextView tvBitName;
        @BindView(R.id.tvLocation)
        TextView tvLocation;
        @BindView(R.id.tvCheckType)
        TextView tvCheckType;
        @BindView(R.id.btnCheckIn)
        Button btnCheckIn;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void set(final PilotCheckBodyM item) {
            tvEntryDate.setText(item.getEntryDate());
            tvEntryTime.setText(item.getEntryTime());
            tvBitName.setText(item.getBeatName());
            tvShipName.setText(item.getShipName());
            tvLocation.setText(item.getLocation());
            tvCheckType.setText(item.getCheckType());
            btnCheckIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //SharedPrefManager.getInstance(context).saveVisit(item);

              /*      Intent intent = new Intent(context, CheckInActivity.class);
                    intent.putExtra("Location", tvScheduleNo.getText());
                    context.startActivity(intent);*/
                }
            });


        }
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
