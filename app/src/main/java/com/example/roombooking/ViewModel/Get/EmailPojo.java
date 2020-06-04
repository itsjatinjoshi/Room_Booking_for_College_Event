package com.example.roombooking.ViewModel.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmailPojo {
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("EmailDetail")
    @Expose
    private List<EmailDetails> emailDetail = null;

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

    public List<EmailDetails> getEmailDetail() {
        return emailDetail;
    }

    public void setEmailDetail(List<EmailDetails> emailDetail) {
        this.emailDetail = emailDetail;
    }
}
