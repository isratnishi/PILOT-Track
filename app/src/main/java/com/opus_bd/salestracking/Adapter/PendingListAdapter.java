package com.opus_bd.salestracking.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.opus_bd.salestracking.Activity.CheckInActivity;
import com.opus_bd.salestracking.Activity.NewEntryActivity;
import com.opus_bd.salestracking.R;

import java.util.List;

public class PendingListAdapter extends RecyclerView.Adapter<PendingListAdapter.ViewHolder> {
    private final Context context1;
        private List<String> mData;
        private LayoutInflater mInflater;
        private ItemClickListener mClickListener;

        // data is passed into the constructor
        public PendingListAdapter( Context context, List<String> data) {
            this.context1 = context;
            this.mData = data;
        }


        @NonNull
        @Override
        public PendingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pending_list, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PendingListAdapter.ViewHolder viewHolder, int i) {
            String animal = mData.get(i);
            viewHolder.myTextView.setText(animal);
        }
        // inflates the row layout from xml when needed


        // total number of rows
        @Override
        public int getItemCount() {
            return mData.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView myTextView;

            ViewHolder(View itemView) {
                super(itemView);
                myTextView = itemView.findViewById(R.id.tvPendingSalesLocation);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context1, NewEntryActivity.class);
                        intent.putExtra("message", myTextView.getText());
                        context1.startActivity(intent);
                    }
                });
            }

            @Override
            public void onClick(View view) {
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

            }
        }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

        // parent activity will implement this method to respond to click events
        public interface ItemClickListener {
            void onItemClick(View view, int position);
        }
    }