package com.opus_bd.salestracking.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.opus_bd.salestracking.Model.GeocodingLocation;
import com.opus_bd.salestracking.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewEntryActivity extends AppCompatActivity {
    @BindView(R.id.btnCheckIn)
    Button btnCheckIn;
    @BindView(R.id.tvYourLocation)
    TextView tvYourLocation;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.etProduct)
    EditText etProduct;
    @BindView(R.id.etSalesTarget)
    EditText etSalesTarget;
    @BindView(R.id.etTarget)
    EditText etTarget;
    double latitude, passLat, passLog;
    double longitude;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;

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


        String address = tvYourLocation.getText().toString();

        GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation(address,
                getApplicationContext(), new GeocoderHandler());


        // Opening sharedPreferences in Private mode.
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        // Opening sharedPreferences in edit mode using editor.
        editor = sharedPreferences.edit();
    }

    @OnClick(R.id.btnCheckIn)
    public void btnCheckIn() {
        Intent intent = new Intent(NewEntryActivity.this, CheckInActivity.class);
        //intent.putExtra("LatLong",tvLocation.getText().toString());

        intent.putExtra("Lat", latitude);
        intent.putExtra("log", longitude);
        startActivity(intent);
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    latitude = bundle.getDouble("Lat");
                    longitude = bundle.getDouble("log");
                    break;
                default:
                    locationAddress = null;
            }
            /*  tvLocation.setText(String.valueOf(latitude)+"   "+String.valueOf(longitude));*/
        }
    }

    public void StoreListToSharedPreferences() {

        editor.putString("ProductName", etProduct.getText().toString());
        editor.putString("SalesTarget", etSalesTarget.getText().toString());
        editor.putString("Target", etTarget.getText().toString());
        editor.putString("Location", tvYourLocation.getText().toString());

        editor.apply();


    }

    // Creating method to Show values from sharedPreferences.
    public void RetrieveListFromSharedPreferences() {

        tv1.setText(sharedPreferences.getString("ProductName", etProduct.getText().toString()));
        tv2.setText(sharedPreferences.getString("SalesTarget", etSalesTarget.getText().toString()));
        tv3.setText(sharedPreferences.getString("Target", etTarget.getText().toString()));
        tv4.setText(sharedPreferences.getString("Location", tvYourLocation.getText().toString()));

    }

    @OnClick(R.id.btnSubmit)
    public void btnSubmit(){
        StoreListToSharedPreferences();
        RetrieveListFromSharedPreferences();
    }
}

