package com.opus_bd.salestracking.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.opus_bd.salestracking.Activity.CheckInActivity;
import com.opus_bd.salestracking.Model.SalesModel;
import com.opus_bd.salestracking.R;
import com.opus_bd.salestracking.Utils.SharedPrefManager;
import com.opus_bd.salestracking.Utils.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PendingListAdapter extends RecyclerView.Adapter<PendingListAdapter.ItemViewHolder> {
    private final Context context;
    private List<SalesModel> items;
    private boolean visibleProfit = false;

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

        @BindView(R.id.tvPendingSalesSite)
        TextView tvPendingSalesSite;
        @BindView(R.id.tvPendingSalesLocation)
        TextView tvPendingSalesLocation;
        @BindView(R.id.tvPendingSalesTraget)
        TextView tvPendingSalesTraget;
        @BindView(R.id.btnCheckIn)
        Button btnCheckIn;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void set(final SalesModel item) {
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
}
