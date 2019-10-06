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
import com.opus_bd.pilot.Model.ScheduleByIDModel.ScheduleByIDModel;
import com.opus_bd.pilot.Model.ScheduleModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.Utils.SharedPrefManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PendingCheckinListAdapter extends RecyclerView.Adapter<PendingCheckinListAdapter.ItemViewHolder> {
    private final Context context;
    private List<ScheduleByIDModel> items;
    public PendingCheckinListAdapter(List<ScheduleByIDModel> items, Context context) {
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
        ScheduleByIDModel item = items.get(position);
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

        @BindView(R.id.tvScheduleDate)
        TextView tvScheduleDate; @BindView(R.id.tvScheduleNo)
        TextView tvScheduleNo;
        @BindView(R.id.tvGroupName)
        TextView tvGroupName;
        @BindView(R.id.tvShipName)
        TextView tvShipName;
        @BindView(R.id.btnCheckIn)
        Button btnCheckIn;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void set(final ScheduleByIDModel item) {
            tvScheduleDate.setText(item.getSchedule().getScheduleDate());
            tvScheduleNo.setText(item.getSchedule().getScheduleNo());
            tvGroupName.setText(item.getSchedule().getGroupName());
            tvShipName.setText(item.getShipName());
            btnCheckIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //SharedPrefManager.getInstance(context).saveVisit(item);

                    Intent intent = new Intent(context, CheckInActivity.class);
                    intent.putExtra("Location", tvScheduleNo.getText());
                    context.startActivity(intent);
                }
            });


        }
    }

}
