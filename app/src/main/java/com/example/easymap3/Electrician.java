package com.example.easymap3;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Electrician implements Serializable{

    private static final long serialVersionUID=1L;

    @SerializedName("id")
    private int id;
    @SerializedName("type")
    private String type;
    @SerializedName("available")
    private String available;
    @SerializedName("experience")
    private String experience;
    @SerializedName("details")
    private String details;
    @SerializedName("charge")
    private String charge;
    @SerializedName("division")
    private String division;
    @SerializedName("subDivision")
    private String subDivision;
    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("longitude")
    private Double longitude;
    @SerializedName("userId")
    private int userId;
    @SerializedName("requestUserId")
    private String requestUserId;

    public String getAcceptedId() {
        return acceptedId;
    }

    public void setAcceptedId(String acceptedId) {
        this.acceptedId = acceptedId;
    }

    @SerializedName("acceptedId")
    private String acceptedId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getSubDivision() {
        return subDivision;
    }

    public void setSubDivision(String subDivision) {
        this.subDivision = subDivision;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRequestUserId() {
        return requestUserId;
    }

    public void setRequestUserId(String requestUserId) {
        this.requestUserId = requestUserId;
    }

}
