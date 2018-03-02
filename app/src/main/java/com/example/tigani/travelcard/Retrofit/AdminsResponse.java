package com.example.tigani.travelcard.Retrofit;

import com.example.tigani.travelcard.Admin;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tigani on 11/22/2017.
 */

public class AdminsResponse
{
    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("admins")
    @Expose
    private List<AdminPojo> admins = null;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<AdminPojo> getAdmins() {
        return admins;
    }

    public void setAdmins(List<AdminPojo> admins) {
        this.admins = admins;
    }
}
