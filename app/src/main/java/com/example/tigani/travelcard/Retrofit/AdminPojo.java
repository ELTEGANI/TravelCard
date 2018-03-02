package com.example.tigani.travelcard.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tigani on 11/22/2017.
 */

public class AdminPojo
{
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("checkadminphone")
    @Expose
    private String checkadminphone;
    @SerializedName("adminphone")
    @Expose
    private String adminphone;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("entrance_code")
    @Expose
    private String entrance_code;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheckadminphone() {
        return checkadminphone;
    }

    public void setCheckadminphone(String checkadminphone) {
        this.checkadminphone = checkadminphone;
    }

    public String getAdminphone() {
        return adminphone;
    }

    public void setAdminphone(String adminphone) {
        this.adminphone = adminphone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEntrance_code() {
        return entrance_code;
    }

    public void setEntrance_code(String entrance_code) {
        this.entrance_code = entrance_code;
    }
}
