package com.example.roombooking.ViewModel.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmailDetails {

    @SerializedName("ID")
    @Expose
    private Integer emailId;

    @SerializedName("Email Title")
    @Expose
    private String emailTitle;

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("Time")
    @Expose
    private String dateTime;

    @SerializedName("Sender Name")
    @Expose
    private String senderName;

    @SerializedName("Email Send By")
    @Expose
    private String emailSendBy;

    public EmailDetails(Integer emailId, String emailTitle, String message, String dateTime, String senderName, String emailSendBy) {
        this.emailId = emailId;
        this.emailTitle = emailTitle;
        this.message = message;
        this.dateTime = dateTime;
        this.senderName = senderName;
        this.emailSendBy = emailSendBy;
    }

    public Integer getEmailId() {
        return emailId;
    }

    public String getEmailTitle() {
        return emailTitle;
    }

    public String getMessage() {
        return message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getEmailSendBy() {
        return emailSendBy;
    }

}
