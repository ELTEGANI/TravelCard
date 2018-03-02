package com.example.tigani.travelcard.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tigani on 11/13/2017.
 */

public class Card
{
    @SerializedName("card_id")
    @Expose
    private String cardId;
    @SerializedName("holder_phone")
    @Expose
    private String holder_phone;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("passport_no")
    @Expose
    private String passportNo;
    @SerializedName("issuedate")
    @Expose
    private String issuedate;
    @SerializedName("identification_code")
    @Expose
    private String identificationCode;
    @SerializedName("admin_id")
    @Expose
    private String adminId;
    @SerializedName("person_image")
    @Expose
    private String personImage;
    @SerializedName("create_at")
    @Expose
    private String createAt;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(String issuedate) {
        this.issuedate = issuedate;
    }

    public String getIdentificationCode() {
        return identificationCode;
    }

    public void setIdentificationCode(String identificationCode) {
        this.identificationCode = identificationCode;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getPersonImage() {
        return personImage;
    }

    public void setPersonImage(String personImage) {
        this.personImage = personImage;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
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
