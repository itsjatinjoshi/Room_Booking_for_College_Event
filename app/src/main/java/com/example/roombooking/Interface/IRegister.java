package com.example.roombooking.Interface;

import com.example.roombooking.ViewModel.Post.RegisterNewUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IRegister {

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
}

//http://localhost:8070/WebApplication1/webresources/generic/RegisterFacility
// &1&f_name&l_name&username&facility_password&facility_email&12122&facility_department

//http://192.168.1.104:8070/WebApplication1/webresources/generic/RegisterFacility
// &63&Vvdbx&Vhdj&Sggs&gdhhd&hdhf&555446&Management