package com.example.roombooking.ViewModel.Post;

import com.example.roombooking.ViewModel.Get.LoginCredentials;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("Status")
    @Expose
    private String status;

    @SerializedName("Timestamp")
    @Expose
    private Integer timestamp;

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("UserDetail")
    @Expose
    private LoginCredentials userDetail;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginCredentials getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(LoginCredentials userDetail) {
        this.userDetail = userDetail;
    }

}
