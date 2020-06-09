package com.example.roombooking.Model;

import com.example.roombooking.Interface.IAllApiCalls;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientBaseUrl {

    public static final String BASE_URL = "http://192.168.1.104:8070/WebApplication1/webresources/generic/";

    private static RetrofitClientBaseUrl instance;
    private Retrofit retrofit;

    private RetrofitClientBaseUrl() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClientBaseUrl getInstance() {
        if (instance == null) {
            instance = new RetrofitClientBaseUrl();
        }
        return instance;
    }

    //Register new User
    public IAllApiCalls getRegisterUser() {
        return retrofit.create(IAllApiCalls.class);
    }

    //Login User with credential
    public IAllApiCalls getUserCredential() {
        return retrofit.create(IAllApiCalls.class);
    }


    // Send Email to another user
    public IAllApiCalls sendEmail() {
        return retrofit.create(IAllApiCalls.class);
    }

    //Receive Email
    public IAllApiCalls getEmail() {
        return retrofit.create(IAllApiCalls.class);
    }

    //Delete Email
    public IAllApiCalls deleteEmail() {
        return retrofit.create(IAllApiCalls.class);
    }

    //Update Email with reply back
    //Delete Email
    public IAllApiCalls replyEmail() {
        return retrofit.create(IAllApiCalls.class);
    }

    public IAllApiCalls roomBooking() {
        return retrofit.create(IAllApiCalls.class);
    }

    public IAllApiCalls requestsSend() {
        return retrofit.create(IAllApiCalls.class);
    }
}
