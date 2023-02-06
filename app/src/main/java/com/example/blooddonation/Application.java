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
    private String city;
    private String donationPlace;

    public Application() {
    }

    public Application(String id, Date due, String userId, String bloodComponent, String donationType, String city, String donationPlace) {
        this.id = id;
        this.donationDate = due;
        this.userId = userId;
        this.bloodComponent = bloodComponent;
        this.donationType = donationType;
        this.city = city;
        this.donationPlace = donationPlace;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDonationPlace() {
        return donationPlace;
    }

    public void setDonationPlace(String donationPlace) {
        this.donationPlace = donationPlace;
    }
}
