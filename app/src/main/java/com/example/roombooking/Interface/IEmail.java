package com.example.roombooking.Interface;

import com.example.roombooking.ViewModel.Get.EmailPojo;
import com.example.roombooking.ViewModel.Post.SendEmail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IEmail {

    @GET("SendInboxMessage&{title}&{message}&{time}&{name}&{email_send_by}&{email_send_to}")
    Call<SendEmail> sendEmail(
            @Path(value = "title") String title,
            @Path(value = "message") String message,
            @Path(value = "time") String time,
            @Path(value = "name") String name,
            @Path(value = "email_send_by") String senderName,
            @Path(value = "email_send_to") String receiverName

    );

    @GET("emailreceiveby&{email}")
    Call<EmailPojo> getEmail(
            @Path(value = "email") String emailReceivedBy
    );
}
