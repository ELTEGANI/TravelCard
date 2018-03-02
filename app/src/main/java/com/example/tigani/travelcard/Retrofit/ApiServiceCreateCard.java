package com.example.tigani.travelcard.Retrofit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by altigani Gabir on 19/09/17.
 */

public interface ApiServiceCreateCard
{
    @Multipart
    @POST("/ICards/createTravelcard.php")
    Call<NewCardPojo> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name,
                                 @Part("identificationcode") RequestBody identificationcode,
                                 @Part("fullname") RequestBody fullname,
                                 @Part("destination") RequestBody destination,
                                 @Part("passport") RequestBody passport,
                                 @Part("issuedate") RequestBody issuedate,
                                 @Part("adminphone") RequestBody adminphone,
                                 @Part("holderphone") RequestBody holderphone);
}