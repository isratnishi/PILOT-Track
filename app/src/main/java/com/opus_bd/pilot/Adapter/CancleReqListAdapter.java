package com.opus_bd.pilot.Adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.opus_bd.pilot.Model.RequisationListModel;
import com.opus_bd.pilot.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CancleReqListAdapter extends RecyclerView.Adapter<CancleReqListAdapter.ItemViewHolder> {
    private final Context context;
    private List<RequisationListModel> items;
    public CancleReqListAdapter(List<RequisationListModel> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_canreq_list, parent, false);
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


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void set(final RequisationListModel item) {
            tvshipNo.setText(item.getShipNo());
            tvtripStartDate.setText(item.getReq().getTripStartDate());
            tvstartPort.setText(item.getReq().getStartPort());
            tvendPort.setText(item.getReq().getEndPort());
        }
    }

}
