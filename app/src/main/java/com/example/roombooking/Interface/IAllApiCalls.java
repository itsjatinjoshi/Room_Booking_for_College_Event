package com.example.roombooking.Interface;

import com.example.roombooking.ViewModel.Get.EmailPojo;
import com.example.roombooking.ViewModel.Post.LoginResponse;
import com.example.roombooking.ViewModel.Post.RegisterNewUser;
import com.example.roombooking.ViewModel.Post.SendEmail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IAllApiCalls {
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

    @GET("LoginUser&{username}&{facility_password}")
    Call<LoginResponse> credentials(
            @Path(value = "username") String email,
            @Path(value = "facility_password") String facility_password

    );

    @GET("RegisterFacility&{facility_id}&{f_name}&{l_name}&{username}&{facility_password}&{facility_email}&{facility_phone}&{facility_department}")
    Call<RegisterNewUser> registerUser(
            @Path(value = "facility_id") int facility_id,
            @Path(value = "f_name") String f_name,
            @Path(value = "l_name") String l_name,
            @Path(value = "username") String username,
            @Path(value = "facility_password") String facility_password,
            @Path(value = "facility_email") String facility_email,
            @Path(value = "facility_phone") int facility_phone,
            @Path(value = "facility_department") String facility_department

    );

    @GET("deleteEmail&{emailID}")
    Call<EmailPojo> deleteEmail(
            @Path(value = "emailID") int emailID
    );


    @GET("UpdatEmailMessage&{title}&{message}&{time}&{name}&{email_send_by}&{email_send_to}&{email_id}")
    Call<SendEmail> updateEmail(
            @Path(value = "title") String title,
            @Path(value = "message") String message,
            @Path(value = "time") String time,
            @Path(value = "name") String name,
            @Path(value = "email_send_by") String senderEmail,
            @Path(value = "email_send_to") String receiverEmail,
            @Path(value = "email_id") int emailId
    );

}
