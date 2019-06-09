package com.opus_bd.salestracking.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.opus_bd.salestracking.Activity.LoginActivity;
import com.opus_bd.salestracking.Activity.NewEntryActivity;
import com.opus_bd.salestracking.Activity.SalesActivity;
import com.opus_bd.salestracking.Model.MessageEvent;
import com.opus_bd.salestracking.Model.MessageResponse;
import com.opus_bd.salestracking.Model.ProductModel;
import com.opus_bd.salestracking.Model.SalesModel;
import com.opus_bd.salestracking.R;
import com.opus_bd.salestracking.RetrofitService.RetrofitClientInstance;
import com.opus_bd.salestracking.RetrofitService.RetrofitService;
import com.opus_bd.salestracking.Utils.SharedPrefManager;
import com.opus_bd.salestracking.Utils.Utilities;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewHolder> {
    private Context context;
    private List<SalesModel> salesModelList;


    TextView tvSalesProduct;

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

        @BindView(R.id.tvSalesTarget)
        TextView tvSalesTarget;
        @BindView(R.id.tvLocation)
        TextView tvLocation;
        @BindView(R.id.tvSalesDelete)
        TextView tvSalesDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            tvSalesProduct = (TextView) itemView.findViewById(R.id.tvSalesProduct);
        }

        public void set(final SalesModel item) {
            getProductName(item.getProductId());
            tvSalesTarget.setText(item.getTargetmeet());
            tvLocation.setText(item.getLocation());
            tvSalesDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlert(item.getId());
                }
            });
        }
    }


    public void getProductName(int id) {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(context).getUser();
        Call<ProductModel> registrationRequest = retrofitService.getProductName(token, id);
        registrationRequest.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, @NonNull Response<ProductModel> response) {
                tvSalesProduct.setText(response.body().getProductName());
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Utilities.showLogcatMessage("error " + t.toString());
            }
        });
    }


    public void showAlert(final int id) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Are you sure?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteSaleVisit(id);
                        EventBus.getDefault().post(new MessageEvent(true));
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                EventBus.getDefault().post(new MessageEvent(true));
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteSaleVisit(int id) {
        RetrofitService retrofitService = RetrofitClientInstance.getRetrofitInstance().create(RetrofitService.class);
        String token = SharedPrefManager.getInstance(context).getUser();
        Call<MessageResponse> deleteSale = retrofitService.deleteSale(token, id);

        deleteSale.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

                if (response.body() != null) {
                    Toast.makeText(context, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new MessageEvent(true));
                    Intent intent = new Intent(context, SalesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                //showProgressBar(false);
                // Toast.makeText(context, "Fail to connect ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}