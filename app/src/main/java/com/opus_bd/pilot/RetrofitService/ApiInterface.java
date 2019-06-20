package com.opus_bd.pilot.RetrofitService;

import com.opus_bd.pilot.Model.PilotCheckBodyM;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by TAOHID on 1/21/2018.
 */

public interface ApiInterface {

    @POST("api/PilotCheckApi")
    Call<String> postPilotCheckApi(@Header("Authorization") String token, @Body PilotCheckBodyM pilotCheckIn);


}