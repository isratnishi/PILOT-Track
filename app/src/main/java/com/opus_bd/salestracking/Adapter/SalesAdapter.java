package com.opus_bd.salestracking.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.opus_bd.salestracking.Model.SalesModel;
import com.opus_bd.salestracking.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewHolder> {
    private Context context;
    private List<SalesModel> salesModelList;

    @NonNull
    @Override
    public SalesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sales_list, parent, false);
        return new SalesAdapter.ViewHolder(v);
    }

    public SalesAdapter(List<SalesModel> items, Context context) {
        this.salesModelList = items;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull SalesAdapter.ViewHolder holder, int i) {
        SalesModel item = salesModelList.get(i);
        holder.set(item);
    }

    @Override
    public int getItemCount() {
        if (salesModelList == null) {
            return 0;
        }
        return salesModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvSalesProduct)
        TextView tvSalesProduct;
        @BindView(R.id.tvSalesTarget)
        TextView tvSalesTarget;
        @BindView(R.id.tvLocation)
        TextView tvLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void set(final SalesModel item) {
            tvSalesProduct.setText(String.valueOf(item.getProductId()));
            tvSalesTarget.setText(item.getTargetmeet());
            tvLocation.setText(item.getLocation());
        }
    }
}