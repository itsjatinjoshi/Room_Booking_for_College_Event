package com.example.roombooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.roombooking.Model.EmailAdapter;
import com.example.roombooking.Model.RetrofitClientBaseUrl;
import com.example.roombooking.Model.SendRequestAdapter;
import com.example.roombooking.ViewModel.Get.Details;
import com.example.roombooking.ViewModel.Get.EmailDetails;
import com.example.roombooking.ViewModel.Get.EmailPojo;
import com.example.roombooking.ViewModel.Get.SendRequestPojo;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingBookingRequests extends AppCompatActivity {
    private static final String TAG = "PendingBookingRequests";
    String firstName, lastName;
    int userId;
    Toolbar toolbar;
   private RecyclerView recyclerPendingRequests;
   SendRequestAdapter mAdapter;
    ArrayList<SendRequestPojo> sendRequestArray = new ArrayList<>();

    TextView tvNotification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_booking_requests);

        SharedPreferences sharedpref1 = getApplication().getSharedPreferences("pendingRequests", MODE_PRIVATE);
        userId = sharedpref1.getInt("id", 0);
        firstName = sharedpref1.getString("firstName", ""); //
        lastName = sharedpref1.getString("lastName", "");

        toolbar = findViewById(R.id.toolbar);
        tvNotification = findViewById(R.id.tvNotification);
        tvNotification.setText("No New Email...");
        toolbar.setTitle("Pending Requests");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        init();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) ;
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void init() {

        Log.d(TAG, "INIT : " + "CHECK POINT2");

        Call<SendRequestPojo> call = RetrofitClientBaseUrl.getInstance().requestsSend().pendingRequests(userId);
        call.enqueue(new Callback<SendRequestPojo>() {
            @Override
            public void onResponse(Call<SendRequestPojo> call, Response<SendRequestPojo> response) {

                Log.d(TAG, "onResponse code : " + response.code());
                Log.d(TAG, "onResponse message : " + response.message());
                Log.d(TAG, "onResponse raw : " + response.raw());
                SendRequestPojo sendRequestPojo = response.body();
                final List<Details> requestDetails = response.body().getDetails();
                try {
                    postData(requestDetails);


                   mAdapter.setItemClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
                            int position = viewHolder.getAdapterPosition();
                            String facilityName = requestDetails.get(position).getFACILITYNAME();
                            String roomType = requestDetails.get(position).getROOMTYPE();
                            String equipments = requestDetails.get(position).getEQUIPMENTS();
                            String gathering = requestDetails.get(position).getGATHERING();
                            String date = requestDetails.get(position).getVANUEDATE();
                            String purpose = requestDetails.get(position).getPURPOSEOFVANUE();
                            String extraNotes = requestDetails.get(position).getEXTRANOTES();
                            String startTime = requestDetails.get(position).getSTARTTIME();
                            String endTime = requestDetails.get(position).getENDTIME();

                            SharedPreferences sharedPref = getSharedPreferences("pendingRequestDetail", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();

                            String fullName = firstName + " " + lastName;

                            editor.putString("facilityName", fullName);
                            editor.putString("roomType", roomType);
                            editor.putString("equipments", equipments);
                            editor.putString("gathering", gathering);
                            editor.putString("date", date);
                            editor.putString("purpose", purpose);
                            editor.putString("extraNotes", extraNotes);
                            editor.putString("startTime", startTime);
                            editor.putString("endTime", endTime);
                            editor.apply();
                            Intent pendingRequestDetails = new Intent(PendingBookingRequests.this, PendingRequestDetails.class);
                            startActivity(pendingRequestDetails);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                tvNotification.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SendRequestPojo> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                t.printStackTrace();
                tvNotification.setVisibility(View.VISIBLE);
            }
        });
    }

    public void postData(List<Details> postList) throws JSONException {
        // Log.d(TAG, ": " +"IS IT CALLING");
        Log.d(TAG, "postData : " + "CHECK POINT1");
        final ArrayList<Details> posts1 = (ArrayList<Details>) postList;
        recyclerPendingRequests = findViewById(R.id.recyclerPendingRequests);
        recyclerPendingRequests.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new SendRequestAdapter(posts1, getApplicationContext());
        Log.d(TAG, "postData: " + mAdapter.toString());
        recyclerPendingRequests.setAdapter(mAdapter);

        Log.d(TAG, "ADAPTER: " + mAdapter.getItemCount());
        //   mAdapter.setItemOnClickListener(mOnClickListener);


    }

}
