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

import com.opus_bd.pilot.Activity.RequisitorActivity.RequisatorHomeActivity;
import com.opus_bd.pilot.Model.Organization.BoatStatusForORG;
import com.opus_bd.pilot.Model.RequisationListModel;
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

public class RunningBoatStatusListAdapter extends RecyclerView.Adapter<RunningBoatStatusListAdapter.ItemViewHolder> {
    private final Context context;
    private List<BoatStatusForORG> items;

    public RunningBoatStatusListAdapter(List<BoatStatusForORG> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_running_boat_list, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        BoatStatusForORG item = items.get(position);
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
        @BindView(R.id.tvbeatName)
        TextView tvbeatName;
        @BindView(R.id.tventryDate)
        TextView tventryDate;
        @BindView(R.id.tventryTime)
        TextView tventryTime;
        @BindView(R.id.tvLocationName)
        TextView tvLocationName;
        @BindView(R.id.tvCheckType)
        TextView tvCheckType;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void set(final BoatStatusForORG item) {
            tvreqNo.setText(item.getReqNo());
            tvscheduleNo.setText(item.getScheduleNo());
            tvScheduleDate.setText(item.getScheduledate());
            tvpilotName.setText(item.getPilotName());
            tvShipName.setText(item.getShipName());
            tvbeatName.setText(item.getBeatName());
            tventryDate.setText(item.getEntryDate());
            tventryTime.setText(item.getEntryTime());
            tvLocationName.setText(item.getLocation());
            tvCheckType.setText(item.getCheckType());


        }
    }


}
