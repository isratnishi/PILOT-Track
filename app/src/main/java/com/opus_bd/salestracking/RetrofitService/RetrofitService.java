package com.opus_bd.salestracking.RetrofitService;


import com.opus_bd.salestracking.Model.UserModel;
import com.opus_bd.salestracking.Model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface RetrofitService {
    @POST("api/logincheck")
    Call<UserResponse> login(@Body UserModel userModel);
}
