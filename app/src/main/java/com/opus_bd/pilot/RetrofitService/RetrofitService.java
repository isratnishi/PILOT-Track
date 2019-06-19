package com.opus_bd.pilot.RetrofitService;


import com.opus_bd.pilot.Model.CheckinModel;
import com.opus_bd.pilot.Model.MessageResponse;
import com.opus_bd.pilot.Model.ProductModel;
import com.opus_bd.pilot.Model.SalesModel;
import com.opus_bd.pilot.Model.ScheduleByDateModel;
import com.opus_bd.pilot.Model.ScheduleModel;
import com.opus_bd.pilot.Model.SiteModel;
import com.opus_bd.pilot.Model.UserInfo;
import com.opus_bd.pilot.Model.UserModel;
import com.opus_bd.pilot.Model.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface RetrofitService {
  /*  @POST("api/AppsLoginPost")
    Call<UserModel> login(@Body UserModel userModel);*/

    @POST("api/AppsLoginPost")
    Call<UserResponse> login(@Body UserModel userModel);

    @GET("api/GETScheduleByPilotID/{id}")
    Call<List<ScheduleModel>> getvisitList(@Header("Authorization") String token, @Path("id") int id);

    @GET("api/PilotCheckApi/{id}")
    Call<List<CheckinModel>> getCheckIn(@Header("Authorization") String token, @Path("id") int id);

    @GET("api/GETScheduleByscheduleDate/{scheduleDate}")
    Call<List<ScheduleByDateModel>> gETScheduleByscheduleDate(@Header("Authorization") String token, @Path("scheduleDate") String scheduleDate);

    @GET("api/GetAspNetUsersDataByApi/{userName}")
    Call<UserModel> getUser(@Header("Authorization") String token, @Path("email") String userName);

    @GET("api/GetAspNetUsersDataByApi/{userName}")
    Call<UserInfo> getUserInfo(@Header("Authorization") String token, @Path("userName") String userName);

    @POST("api/saveVisit ")
    Call<MessageResponse> saveVisit(@Header("Authorization") String token, @Body SalesModel salesModel);

    @POST("api/deleteSaleVisit/{id} ")
    Call<MessageResponse> deleteSaleVisit(@Header("Authorization") String token, @Path("id") int id);

    @POST("api/deleteSale/{id} ")
    Call<MessageResponse> deleteSale(@Header("Authorization") String token, @Path("id") int id);

    @GET("api/getSaleVisit/{id}")
    Call<List<SalesModel>> getSaleVisit(@Header("Authorization") String token, @Path("id") int id);

    @GET("api/getSiteName/{id}")
    Call<SiteModel> getSiteName(@Header("Authorization") String token, @Path("id") int id);

    @GET("api/getProductName/{id}")
    Call<ProductModel> getProductName(@Header("Authorization") String token, @Path("id") int id);
}
