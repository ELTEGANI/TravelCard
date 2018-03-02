package com.example.tigani.travelcard.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tigani on 11/22/2017.
 */

public interface ApiAddCheckAdmin
{
    @GET("/ICards/AddCheckAdmin.php")
    Call<AddCheckAdminPojo> getResponse(@Query("name") String name,
                                         @Query("checkadminphone") String checkadminphone,
                                         @Query("adminphone") String adminphone,
                                         @Query("status") String status);

}
