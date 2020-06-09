package com.example.roombooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class PendingRequestDetails extends AppCompatActivity {

    TextView tvFacilityName, tvRoomType, tvEquipment, tvPersons, tvPurpose, tvExtraNotes, tvDate, tvStartTime, tvEndTime;
    Toolbar toolbar;

    private String name, roomtype,equipment,noOfPersons, purpose, extranotes, date, startTime, endTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_request_details);

        SharedPreferences pendingRequestDetail = getApplication().getSharedPreferences("pendingRequestDetail", MODE_PRIVATE);
        name = pendingRequestDetail.getString("facilityName","" );
        roomtype = pendingRequestDetail.getString("roomType","" );
        equipment = pendingRequestDetail.getString("equipments","" );
        noOfPersons = pendingRequestDetail.getString("gathering","" );
        purpose = pendingRequestDetail.getString("purpose","" );
        extranotes = pendingRequestDetail.getString("extraNotes","" );
        date = pendingRequestDetail.getString("date","" );
        startTime = pendingRequestDetail.getString("startTime","" );
        endTime = pendingRequestDetail.getString("endTime","" );

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pending Request Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvFacilityName = findViewById(R.id.tvFacilityName);
        tvRoomType = findViewById(R.id.tvRoomType);
        tvEquipment = findViewById(R.id.tvEquipment);
        tvPersons = findViewById(R.id.tvPersons);
        tvPurpose = findViewById(R.id.tvPurpose);
        tvExtraNotes = findViewById(R.id.tvExtraNotes);
        tvDate = findViewById(R.id.tvDate);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);



        tvFacilityName.setText(name);
        tvRoomType.setText("Room Type: " + roomtype);
        tvEquipment.setText("Equipments: " +equipment);
        tvPersons.setText("Gathering: " +noOfPersons);
        tvPurpose.setText("Purpose: " +purpose);
        tvExtraNotes.setText("Extra Notes: " +extranotes);
        tvDate.setText("Booking Date: " +date);
        tvStartTime.setText("Start Time: " +startTime);
        tvEndTime.setText("End Time: " +endTime);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) ;
        finish();
        return super.onOptionsItemSelected(item);
    }

}
