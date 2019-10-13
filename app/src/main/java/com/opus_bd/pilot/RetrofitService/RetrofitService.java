package com.opus_bd.pilot.RetrofitService;

import com.opus_bd.pilot.Model.Organization.BoatStatusForORG;
import com.opus_bd.pilot.Model.Organization.TokenforOrgModel;
import com.opus_bd.pilot.Model.OrganizationModel;
import com.opus_bd.pilot.Model.PilotCheckBodyM;
import com.opus_bd.pilot.Model.PortModel;
import com.opus_bd.pilot.Model.RequisationListModel;
import com.opus_bd.pilot.Model.RequisationPostModel;
import com.opus_bd.pilot.Model.Organization.ScheduleAssignForORG;
import com.opus_bd.pilot.Model.ScheduleByDateModel;
import com.opus_bd.pilot.Model.ScheduleByIDModel.ScheduleByIDModel;
import com.opus_bd.pilot.Model.SuperVisor.RequisitionCancleModel;
import com.opus_bd.pilot.Model.TokenBuyModel;
import com.opus_bd.pilot.Model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface RetrofitService {


    @POST("global/api/AppsLoginPost")
    Call<UserModel> login(@Body UserModel userModel);

    @GET("global/api/GETScheduleBypilotid/{id}")
    Call<List<ScheduleByIDModel>> GETScheduleByPilotID(@Header("Authorization") String token, @Path("id") int id);

    @GET("global/api/GetPortListApi")
    Call<List<PortModel>> GetPortListApi();

    @GET("global/api/GetTokenInfoApi/{orgid}")
    Call<List<OrganizationModel>> GETOrganizationModelByOrgID(@Header("Authorization") String token, @Path("orgid") int id);


    @GET("global/api/GetRequisitionInfoApi/{orgid}")
    Call<List<RequisationListModel>> GETRequisationListModelByOrgID(@Path("orgid") int id);

    @GET("global/api/GetScheduleAssignForCheckINOrg/{orgId}")
    Call<List<ScheduleAssignForORG>> GetScheduleAssignForCheckINOrg(@Path("orgId") int id);


    @GET("global/api/GetBalanceTokenOrg/{orgid}")
    Call<List<TokenforOrgModel>> GetBalanceTokenOrg(@Path("orgid") int id);


    @GET("global/api/GetShipStatusOrg/{orgId}")
    Call<List<BoatStatusForORG>> GetShipStatusOrg(@Path("orgId") int id);


    @POST("global/api/GetRequisitionSave")
    Call<String> GetRequisitionSave(@Body RequisationPostModel requisationPostModel);

    @POST("global/api/GetTokenSave")
    Call<String> GetTokenSave(@Body TokenBuyModel tokenBuyModel);


    @POST("global/api/CancelledRequisition/{masterId}")
    Call<String> CancelledRequisition(@Path("masterId") int id);

    @GET("api/PilotCheckApi/{id}")
    Call<List<PilotCheckBodyM>> getCheckIn(@Header("Authorization") String token, @Path("id") int id);

    @GET("global/api/GetAspNetUsersDataByApi/{scheduleID}")
    Call<ScheduleByDateModel> gETScheduleByshipName(@Header("Authorization") String token, @Path("scheduleID") int id);

    @POST("global/api/PilotCheckApi")
    Call<String> postPilotCheckApi(@Header("Authorization") String token, @Body PilotCheckBodyM pilotCheckIn);

    @GET("global/api/GETScheduleByscheduleDate/{scheduleDate}")
    Call<List<ScheduleByDateModel>> gETScheduleByscheduleDate(@Header("Authorization") String token, @Path("scheduleDate") String scheduleDate);

    @GET("global/api/GetAspNetUsersDataByApi/{userName}")
    Call<UserModel> getUser(@Header("Authorization") String token, @Path("email") String userName);

    @GET("global/api/GetAspNetUsersDataByApi/{userName}")
    Call<UserModel> getUserInfo(@Header("Authorization") String token, @Path("userName") String userName);


    //SuperVisor
    @GET("global/api/GetAllRequisitionDetailsPending")
    Call<List<RequisationListModel>> GetAllRequisitionDetailsPending();


    @GET("global/api/GetAllScheduleAssignLog/")
    Call<List<ScheduleByIDModel>> GetAllScheduleAssignLog();

    @GET("global/api/GetAllPilotCheckIncheckOut/")
    Call<List<PilotCheckBodyM>> GetAllPilotCheckIncheckOut();


    @GET("global/api/GetAllRequisitionDetailsForScheduleAdmin/")
    Call<List<RequisationListModel>> GetAllRequisitionDetailsForScheduleAdmin();//it has to be change


    @GET("global/api/GetAllRequisitionMasterCancelAdmin/")
    Call<List<RequisitionCancleModel>> GetAllRequisitionMasterCancelAdmin();


    @GET("global/api/GetAllRequisitionDetailsCompleteAdmin/")
    Call<List<RequisitionCancleModel>> GetAllRequisitionDetailsCompleteAdmin();//it has to be change


    @GET("global/api/GetBalanceTokenByAdmin/")
    Call<List<TokenforOrgModel>> GetBalanceTokenByAdmin();

    //admin


}


