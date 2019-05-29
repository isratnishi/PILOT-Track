package com.opus_bd.salestracking.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.opus_bd.salestracking.Adapter.PendingListAdapter;
import com.opus_bd.salestracking.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PendingSalesActivity extends AppCompatActivity {
    @BindView(R.id.rvPendingList)
    RecyclerView rvPendingList;
    PendingListAdapter pendingListAdapter;
    private ArrayList<String> locationNameArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_sales);
        ButterKnife.bind(this);
        AddData();
        intRecyclerView();

    }

    public void intRecyclerView() {
        pendingListAdapter = new PendingListAdapter(this, locationNameArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPendingList.setLayoutManager(layoutManager);
        rvPendingList.setAdapter(pendingListAdapter);
    }

    public void AddData() {
        String[] values = new String[]{"Opus Technology Ltd",
                "BASIS Institute of Technology & Management (BITM)", "dhaka City college","Savar Upazila"};


        for (int i = 0; i < values.length; ++i) {
            locationNameArrayList.add(values[i]);
        }

    }

}
