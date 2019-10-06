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
import com.opus_bd.pilot.Model.PilotCheckBodyM;
import com.opus_bd.pilot.Model.ScheduleByIDModel.ScheduleByIDModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.Utils.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RunningCheckinListAdapter extends RecyclerView.Adapter<RunningCheckinListAdapter.ItemViewHolder> {
    private final Context context;
    private List<PilotCheckBodyM> items;

    public RunningCheckinListAdapter(List<PilotCheckBodyM> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_running_list, parent, false);
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
            //    Utilities.showLogcatMessage(" Check in");
            tvEntryDate.setText(item.getEntryDate());
            tvEntryTime.setText(item.getEntryTime());
            tvBitName.setText(item.getBeatName());
            tvShipName.setText(item.getShipName());
            tvCheckType.setText(item.getCheckType());
            if (item.getCheckType().equals("Check Out")) {
                btnCheckIn.setVisibility(View.GONE);
            } else {
                btnCheckIn.setText("Check Out");
                btnCheckIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, CheckInActivity.class);
                        intent.putExtra("Location", item.getLocation());
                        intent.putExtra("SCID", item.getScheduleID());
                        Utilities.showLogcatMessage("SCID" + item.getScheduleID());
                        intent.putExtra("ShipName", item.getShipName());
                        intent.putExtra("Check", "Check Out");
                        intent.putExtra("beatName", item.getBeatName());
                        context.startActivity(intent);
                    }
                });
            }


        }
    }

}
