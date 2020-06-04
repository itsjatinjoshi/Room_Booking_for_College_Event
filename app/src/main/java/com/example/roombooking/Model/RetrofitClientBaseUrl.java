package com.example.roombooking.Model;

import com.example.roombooking.Interface.IEmail;
import com.example.roombooking.Interface.ILogin;
import com.example.roombooking.Interface.IRegister;

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

    public static synchronized RetrofitClientBaseUrl getInstance(){
        if(instance == null){
            instance = new RetrofitClientBaseUrl();
        }
        return instance;
    }

    public IRegister getRegisterUser(){
        return retrofit.create(IRegister.class);
    }

    public ILogin getUserCredential(){
        return retrofit.create(ILogin.class);
    }

    public IEmail sendEmail(){
        return retrofit.create(IEmail.class);
    }

    public IEmail getEmail(){
        return retrofit.create(IEmail.class);
    }
}
