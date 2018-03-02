package com.example.tigani.travelcard.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tigani on 11/22/2017.
 */

public interface ApiRequestedAdmin
{
    @GET("/ICards/GetAdmin.php")
    Call<AdminsResponse> getRequestedAdmins(@Query("phone") String phone) ;
}
