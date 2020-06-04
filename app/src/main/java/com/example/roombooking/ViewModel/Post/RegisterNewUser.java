package com.example.roombooking.ViewModel.Post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterNewUser {
    @SerializedName("Status")
    @Expose
    private String status;

    @SerializedName("f_name")
    @Expose
    private String f_name;

    @SerializedName("Timestamp")
    @Expose
    private String time;

    @SerializedName("Message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public String getF_name() {
        return f_name;
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }
}

