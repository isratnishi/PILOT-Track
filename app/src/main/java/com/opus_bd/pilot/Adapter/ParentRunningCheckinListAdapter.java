package com.opus_bd.pilot.Adapter;


import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.opus_bd.pilot.Activity.CheckInActivity;
import com.opus_bd.pilot.Model.Parent;
import com.opus_bd.pilot.Model.PilotCheckBodyM;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.Utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParentRunningCheckinListAdapter extends RecyclerView.Adapter<ParentRunningCheckinListAdapter.ItemViewHolder> {
    private final Context context;
    private List<Parent> items;
    RunningCheckinListAdapter pendingListAdapter;
    private ArrayList<PilotCheckBodyM> locationNameArrayList = new ArrayList<>();

    public ParentRunningCheckinListAdapter(Context context, List<Parent> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.iten_parent_running_list, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Parent item = items.get(position);
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

        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.rvlist)
        RecyclerView rvlist;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void set(final Parent item) {
            tvTitle.setText(String.valueOf(item.getTitleid()));
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            rvlist.setLayoutManager(mLayoutManager);
            rvlist.setAdapter(pendingListAdapter);

        }
    }

}
