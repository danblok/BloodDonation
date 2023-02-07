package com.example.blooddonation;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Application implements Serializable {

    private String id;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date donationDate;
    private String userId;
    private String bloodComponent;
    private String donationType;
    private String donationPlace;
    private String status;

    public Application() {
    }

    public Application(String id, Date donationDate, String userId, String bloodComponent, String donationType, String donationPlace, String status) {
        this.id = id;
        this.donationDate = donationDate;
        this.userId = userId;
        this.bloodComponent = bloodComponent;
        this.donationType = donationType;
        this.donationPlace = donationPlace;
        this.status = status;
    }

    public Application(Date donationDate, String userId, String bloodComponent, String donationType, String donationPlace, String status) {
        this.donationDate = donationDate;
        this.userId = userId;
        this.bloodComponent = bloodComponent;
        this.donationType = donationType;
        this.donationPlace = donationPlace;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBloodComponent() {
        return bloodComponent;
    }

    public void setBloodComponent(String bloodComponent) {
        this.bloodComponent = bloodComponent;
    }

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
    }

    public String getDonationPlace() {
        return donationPlace;
    }

    public void setDonationPlace(String donationPlace) {
        this.donationPlace = donationPlace;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id='" + id + '\'' +
                ", donationDate=" + donationDate +
                ", userId='" + userId + '\'' +
                ", bloodComponent='" + bloodComponent + '\'' +
                ", donationType='" + donationType + '\'' +
                ", donationPlace='" + donationPlace + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
