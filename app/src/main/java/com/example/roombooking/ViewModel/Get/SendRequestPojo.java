package com.example.roombooking.ViewModel.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SendRequestPojo {
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("DETAILS")
    @Expose
    private List<Details> Details = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public List<Details> getDetails() {
        return Details;
    }

    public void setDetails(List<Details> Details) {
        this.Details = Details;
    }
}
