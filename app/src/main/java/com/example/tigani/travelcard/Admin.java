package com.example.tigani.travelcard;

/**
 * Created by tigani on 11/22/2017.
 */

public class Admin
{
    private String Id,Name,Phone,Status,entrancecode;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getEntrancecode() {
        return entrancecode;
    }

    public void setEntrancecode(String entrancecode) {
        this.entrancecode = entrancecode;
    }
}
