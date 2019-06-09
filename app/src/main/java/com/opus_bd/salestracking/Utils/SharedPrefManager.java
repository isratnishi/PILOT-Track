package com.opus_bd.salestracking.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.opus_bd.salestracking.Model.SalesModel;
import com.opus_bd.salestracking.Model.UserModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPrefManager {
    public static final String SHARED_PREF_NAME = "SalesTrack";
    private static SharedPrefManager mInstance;
    private Context mCtx;
    private static final String KEY_ORDERS = "orders";
    public static final String BEARER = "Bearer ";
    public static final String KEY_State = "state";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_VISIT = "visit";
    private static final String KEY_ID = "id";

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void saveUser(UserModel userModel) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userModel);
        editor.putString(KEY_TOKEN, json);
        editor.apply();
    }

    public void clearToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(KEY_TOKEN).apply();
    }
    public void saveVisit(SalesModel salesModel) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(salesModel);
        editor.putString(KEY_VISIT, json);
        editor.apply();
    }

    public void saveID(int id) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, id);
        editor.apply();
    }

    public int getID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_ID, 0);
    }

    public String getVisit() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_VISIT, null);
    }

    public void clearID() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(KEY_TOKEN).apply();
    }

    public String getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TOKEN, null);
    }


    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TOKEN, null) != null;
    }

   /* public void logout(Context context) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void saveSalesToCart(ArrayList<SaleModel> saleModels) {
        Gson gson = new Gson();
        String tempOrders = gson.toJson(saleModels);

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ORDERS, tempOrders);
        editor.apply();
    }

    public ArrayList<SaleModel> getSavedSales() {
        List<SaleModel> tempOrders = new ArrayList<>();

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);

        String stringOrders = sharedPreferences.getString(KEY_ORDERS, null);
        try {
            Gson gson = new Gson();
            SaleModel[] checkOutModels = gson.fromJson(stringOrders,
                    SaleModel[].class);

            tempOrders = Arrays.asList(checkOutModels);
            tempOrders = new ArrayList<>(tempOrders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ArrayList<SaleModel>) tempOrders;
    }*/

    public void clearSales() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(KEY_ORDERS).apply();
    }
}