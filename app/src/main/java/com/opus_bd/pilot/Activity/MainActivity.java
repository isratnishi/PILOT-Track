package com.opus_bd.pilot.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.opus_bd.pilot.Model.UserModel;
import com.opus_bd.pilot.R;
import com.opus_bd.pilot.Utils.Constants;
import com.opus_bd.pilot.Utils.SharedPrefManager;
import com.opus_bd.pilot.Utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.fabNewEntry)
    FloatingActionButton fabNewEntry;
    String userName;

    //UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Bundle bundle = getIntent().getExtras();
        Gson gson = new Gson();
        String token = SharedPrefManager.getInstance(this).getUser();
        UserModel obj = gson.fromJson(token, UserModel.class);
        String email = obj.getName();
        tvUserName.setText(email);

        Utilities.showLogcatMessage(" Email " + email);

      /*  if (bundle != null) {
            userName = bundle.getString(Constants.USER_NAME);
            tvUserName.setText(userName);

        }*/
    /*    Gson gson = new Gson(); String json = SharedPrefManager.getInstance(this).getUser();


        userModel = gson.fromJson(json, UserModel.class);
       tvUserName.setText(userModel.getEmail());*/
    }

    @OnClick(R.id.tvCheckIN)
    public void tvPendingSales() {

        Intent intent = new Intent(MainActivity.this, PendingSalesActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tvCheckOUT)
    public void tvSales() {

        Intent intent = new Intent(MainActivity.this, SalesActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tvRoute)
    public void tvRoute() {

        Intent intent = new Intent(MainActivity.this, RouteActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.fabNewEntry)
    public void fabNewEntry() {

        Intent intent = new Intent(MainActivity.this, NewCheckINActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            SharedPrefManager.getInstance(this).clearToken();
            Toast.makeText(this, "Logged out successfully!!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
        if (id == R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
        if (id == R.id.pendingList) {
            Intent intent = new Intent(this, PendingSalesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
        if (id == R.id.salesList) {
            Intent intent = new Intent(this, SalesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
