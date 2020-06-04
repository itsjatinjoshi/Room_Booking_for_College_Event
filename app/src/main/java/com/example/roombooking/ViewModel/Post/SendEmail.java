package com.example.roombooking.ViewModel.Post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendEmail {

    @SerializedName("Status")
    @Expose
    private String status;

    @SerializedName("Timestamp")
    @Expose
    private String time;

    @SerializedName("Message")
    @Expose
    private String message;


    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }
}
