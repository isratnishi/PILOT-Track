package com.opus_bd.pilot.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.opus_bd.pilot.Model.Organization.BoatStatusForORG;
import com.opus_bd.pilot.Model.Organization.ScheduleAssignForORG;
import com.opus_bd.pilot.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PendingScheduleAdapter extends RecyclerView.Adapter<PendingScheduleAdapter.ItemViewHolder> {
    private final Context context;
    private List<ScheduleAssignForORG> items;

    public PendingScheduleAdapter(List<ScheduleAssignForORG> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pending_schedule_list, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        ScheduleAssignForORG item = items.get(position);
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

        @BindView(R.id.tvreqNo)
        TextView tvreqNo;
        @BindView(R.id.tvscheduleNo)
        TextView tvscheduleNo;
        @BindView(R.id.tvScheduleDate)
        TextView tvScheduleDate;
        @BindView(R.id.tvpilotName)
        TextView tvpilotName;
        @BindView(R.id.tvShipName)
        TextView tvShipName;
        @BindView(R.id.tvgroupName)
        TextView tvgroupName;
        @BindView(R.id.tvstartPort)
        TextView tvstartPort;
        @BindView(R.id.tvendPort)
        TextView tvendPort;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void set(final ScheduleAssignForORG item) {
            tvreqNo.setText(item.getReqNo());
            tvscheduleNo.setText(item.getScheduleNo());
            tvScheduleDate.setText(item.getScheduledate());
            tvpilotName.setText(item.getPilotName());
            tvShipName.setText(item.getShipName());
            tvgroupName.setText(item.getGroupName());
            tvstartPort.setText(item.getStartPort());
            tvendPort.setText(item.getEndPort());


        }
    }


}
