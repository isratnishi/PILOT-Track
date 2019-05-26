package com.opus_bd.salestracking.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.opus_bd.salestracking.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tvPendingSales)
    public void tvPendingSales() {

        Intent intent = new Intent(MainActivity.this, PendingSalesActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tvSales)
    public void tvSales() {

        Intent intent = new Intent(MainActivity.this, NewEntryActivity.class);
        startActivity(intent);
    }
}
