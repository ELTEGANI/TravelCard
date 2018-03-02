package com.example.tigani.travelcard.Retrofit;

/**
 * Created by tigani on 11/13/2017.
 */

public class CardItems
{
    private String Id,name,destination,Passport,issuedate,personimage,idcode,status,holder_phone;


    public String getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(String issuedate) {
        this.issuedate = issuedate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPassport() {
        return Passport;
    }

    public void setPassport(String passport) {
        Passport = passport;
    }

    public String getPersonimage() {
        return personimage;
    }

    public void setPersonimage(String personimage) {
        this.personimage = personimage;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHolder_phone() {
        return holder_phone;
    }

    public void setHolder_phone(String holder_phone) {
        this.holder_phone = holder_phone;
    }
}
