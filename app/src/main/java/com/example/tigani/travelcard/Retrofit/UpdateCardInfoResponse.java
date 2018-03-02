package com.example.tigani.travelcard.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tigani on 11/24/2017.
 */

public class UpdateCardInfoResponse
{
    @SerializedName("error")
    @Expose
    private boolean error;

    public boolean isError()
    {
        return error;
    }

    public void setError(boolean error)
    {
        this.error = error;
    }
}
