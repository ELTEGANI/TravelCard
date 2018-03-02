package com.example.tigani.travelcard.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tigani on 11/24/2017.
 */

public interface ApiUpdateCardInfo
{
    @GET("ICards/UpdateCardInfo.php")
    Call<UpdateCardInfoResponse> UpdateCard(@Query("id") String id,
                                            @Query("name") String name,
                                            @Query("destination") String destination,
                                            @Query("passno") String passno,
                                            @Query("issuedate") String issuedate,
                                            @Query("status") String status,
                                            @Query("holderphone") String holderphone);
}
