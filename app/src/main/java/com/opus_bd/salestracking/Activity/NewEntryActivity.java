package com.opus_bd.salestracking.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.opus_bd.salestracking.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewEntryActivity extends AppCompatActivity {
    @BindView(R.id.btnCheckIn)
    Button btnCheckIn;
    @BindView(R.id.tvYourLocation)
    TextView tvYourLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            String message = bundle.getString("message");
            tvYourLocation.setText(message);
        } else tvYourLocation.setText(" ");
    }

    @OnClick(R.id.btnCheckIn)
    public void btnCheckIn() {
        startActivity(new Intent(NewEntryActivity.this, CheckInActivity.class));
    }
}
