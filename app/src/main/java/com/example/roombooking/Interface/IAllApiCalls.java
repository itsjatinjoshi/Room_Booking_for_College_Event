package com.example.roombooking.Interface;

import com.example.roombooking.ViewModel.Get.EmailPojo;
import com.example.roombooking.ViewModel.Get.SendRequestPojo;
import com.example.roombooking.ViewModel.Post.LoginResponse;
import com.example.roombooking.ViewModel.Post.RegisterNewUser;
import com.example.roombooking.ViewModel.Post.RoomBookingRequest;
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

    @GET("RegisterForRoomBooking&{facility_id}&{facility_name}&{room_type}&{equipments}&{gathering}&{date}&{reason}&{extra_notes}&{start_time}&{end_time}")
    Call<RoomBookingRequest> sendRoomBookingRequest(
            @Path(value = "facility_id") int facility_id,
            @Path(value = "facility_name") String facility_name,
            @Path(value = "room_type") String room_type,
            @Path(value = "equipments") String equipments,
            @Path(value = "gathering") String gathering,
            @Path(value = "date") String date,
            @Path(value = "reason") String reason,
            @Path(value = "extra_notes") String extra_notes,
            @Path(value = "start_time") String start_time,
            @Path(value = "end_time") String end_time
    );

    @GET("SendBookingRequest&{facility_id}")
    Call<SendRequestPojo> pendingRequests(
            @Path(value = "facility_id") int facilityId
    );
}
