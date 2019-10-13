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
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.Utils.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RunningScheduleListAdapter extends RecyclerView.Adapter<RunningScheduleListAdapter.ItemViewHolder> {
    private final Context context;
    private List<ScheduleByIDModel> items;

    public RunningScheduleListAdapter(List<ScheduleByIDModel> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_running_schedule_list, parent, false);
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
        TextView tvScheduleDate;
        @BindView(R.id.tvScheduleNo)
        TextView tvScheduleNo;
        @BindView(R.id.tvGroupName)
        TextView tvGroupName;
        @BindView(R.id.tvShipName)
        TextView tvShipName;
        @BindView(R.id.tvCheckType)
        TextView tvCheckType;
        @BindView(R.id.tvPilotName)
        TextView tvPilotName;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void set(final ScheduleByIDModel item) {

            tvScheduleDate.setText(item.getSchedule().getScheduleDate());
            tvScheduleNo.setText(item.getSchedule().getScheduleNo());
            tvGroupName.setText(item.getSchedule().getGroupName());
            tvShipName.setText(item.getShipName());
            tvCheckType.setText(item.getPilot().getPilotType());
            tvPilotName.setText(item.getPilot().getPilotName());


        }
    }

}
