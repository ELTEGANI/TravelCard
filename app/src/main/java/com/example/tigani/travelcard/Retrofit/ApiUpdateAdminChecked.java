package com.example.tigani.travelcard.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tigani on 11/27/2017.
 */

public interface ApiUpdateAdminChecked
{
    @GET("/ICards/UpdateCheckAdmin.php")
    Call<UpdateCheckAdminResponse> getResponse(@Query("id") String id,
                                               @Query("name") String name,
                                               @Query("checkadminphone") String checkadminphone,
                                               @Query("status") String status);
}
