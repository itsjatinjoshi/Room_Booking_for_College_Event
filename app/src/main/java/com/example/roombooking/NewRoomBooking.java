package com.example.roombooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.roombooking.Model.RetrofitClientBaseUrl;
import com.example.roombooking.Utils.PreferenceUtils;
import com.example.roombooking.ViewModel.Post.LoginResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewRoomBooking extends AppCompatActivity {
    private static final String TAG = "NewRoomBooking";
    Toolbar toolbar;
    ProgressBar pgBar;
    TextView tvMessage, tvFacilityName;
    EditText etEquipment, etAllEquipment, etPersons, etDate, etTimeFrom, etTimeUpto, etPurpose, etExtraNotes;
    Button btnAddEquipment, btnSendQuery;

    Spinner spinnerRoomType;
    String firstName, lastName;
    int userId;
    String[] roomOptions = {"Please Choose", "Conference Room", "Libraries", "lecture halls", " student centers", "residence halls", "park-like", "Others"};
    DatePickerDialog datePickerDialog;
    int addEquip = 0;
    String timeString;
    Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_room_booking);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("New Room Booking");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences sharedpref1 = getApplication().getSharedPreferences("bookRoomForActivity", MODE_PRIVATE);
        userId = sharedpref1.getInt("id", 0);
        firstName = sharedpref1.getString("firstName", ""); //
        lastName = sharedpref1.getString("lastName", "");

        Log.d(TAG, "USER DETAIL :  " + userId +
                "\n" + firstName +
                "\n" + lastName);

        pgBar = findViewById(R.id.pgBar);
        tvMessage = findViewById(R.id.tvMessage);

        tvFacilityName = findViewById(R.id.tvFacilityName);
        etEquipment = findViewById(R.id.etEquipment);
        etAllEquipment = findViewById(R.id.etAllEquipment);
        etPersons = findViewById(R.id.etPersons);
        etDate = findViewById(R.id.etDate);
        etTimeFrom = findViewById(R.id.etTimeFrom);
        etTimeUpto = findViewById(R.id.etTimeUpto);
        etPurpose = findViewById(R.id.etPurpose);
        etExtraNotes = findViewById(R.id.etExtraNotes);

        btnAddEquipment = findViewById(R.id.btnAddEquipment);
        btnSendQuery = findViewById(R.id.btnSendQuery);

        spinnerRoomType = findViewById(R.id.spinnerRoomType);

        pgBar.setVisibility(View.GONE);
        tvMessage.setVisibility(View.GONE);

        tvFacilityName.setText(firstName + " " + lastName);
        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, roomOptions);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerRoomType.setAdapter(departmentAdapter);

        spinnerRoomType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        addEquipments();

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate();
            }
        });

        etTimeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(NewRoomBooking.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int h, int min) {
                                cal.set(Calendar.HOUR_OF_DAY, h);
                                cal.set(Calendar.MINUTE, min);
                                DateFormat format = DateFormat.getTimeInstance();
                                timeString = format.format(cal.getTime());
                                etTimeFrom.setText(timeString);
                            }
                        }, cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        true)
                        .show();
            }
        });

        etTimeUpto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(NewRoomBooking.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int h, int min) {
                                cal.set(Calendar.HOUR_OF_DAY, h);
                                cal.set(Calendar.MINUTE, min);
                                DateFormat format = DateFormat.getTimeInstance();
                                timeString = format.format(cal.getTime());
                                etTimeUpto.setText(timeString);
                                Log.d(TAG, "TIME UP TO 1 :   " + timeString);
                            }
                        }, cal.get(Calendar.HOUR_OF_DAY),
                        cal.get(Calendar.MINUTE),
                        true)
                        .show();
            }
        });


        btnSendQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compareTime();
                checkValidations();
            }
        });
    }


    private void pickDate() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year

        try {
            c.setTime(new SimpleDateFormat("MMM").parse("July"));
            int mMonth = c.get(Calendar.MONTH) + 1;
            int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
            datePickerDialog = new DatePickerDialog(NewRoomBooking.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                            String dateString = format.format(calendar.getTime());

                            etDate.setText(dateString);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) ;
        finish();
        return super.onOptionsItemSelected(item);
    }

    void checkValidations() {

        if (spinnerRoomType.getSelectedItem().toString().trim().equals("Please Choose")) {
            Toast.makeText(this, "Choose Room Type", Toast.LENGTH_SHORT).show();
        } else if (etAllEquipment.getText().toString().isEmpty()) {
            Toast.makeText(this, "You have not selected any equipment for meeting", Toast.LENGTH_SHORT).show();
        } else if (etPersons.getText().toString().isEmpty()) {
            Toast.makeText(this, "No Of Persons", Toast.LENGTH_SHORT).show();
        } else if (etDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Select Date", Toast.LENGTH_SHORT).show();
        } else if (etTimeFrom.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Starting Time", Toast.LENGTH_SHORT).show();
        } else if (etTimeUpto.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Finish Time", Toast.LENGTH_SHORT).show();
        }
        else if(compareTime()){
            Toast.makeText(this, "Staring time not to be greater thn end time", Toast.LENGTH_LONG).show();
        }else if (etPurpose.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please specify the reason", Toast.LENGTH_SHORT).show();
        } else {
            allowToFacility();
        }
    }

    void addEquipments() {
        btnAddEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = "";
                String addIntoAllEquipment = etEquipment.getText().toString().trim();

                if (addEquip == 0) {
                    temp = addIntoAllEquipment;
                    etAllEquipment.setText(addIntoAllEquipment);
                    Log.d(TAG, "TEMP1 : " + temp);
                    Log.d(TAG, "ADD EQUIPMENT1 : " + addIntoAllEquipment);
                    etEquipment.setText("");
                } else if (addEquip > 0) {
                    temp += addIntoAllEquipment;
                    temp.concat(temp);
                    Log.d(TAG, "TEMP2 : " + temp);
                    Log.d(TAG, "ADD EQUIPMENT2 : " + addIntoAllEquipment);
                    etAllEquipment.setText(temp);
                    etEquipment.setText("");
                }
                addEquip++;
                Log.d(TAG, "ADD EQUIPMENTS: " + addEquip);

            }

        });
    }

    private boolean compareTime() {
        Log.d(TAG, "TIME CALLING ...");
        Log.d(TAG, "TIME CHECK..."+ etTimeFrom.getText().toString());
        try {
            Log.d(TAG, "TRY CALLING... " );

            String str = "08:03:10 pm";
            DateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
            Date startTime = (Date)formatter.parse(etTimeFrom.getText().toString());
            Date endTime = (Date)formatter.parse(etTimeUpto.getText().toString());


            Log.d(TAG, "START TIME: "+ startTime);
            Log.d(TAG, "END TIME: " + endTime);

            if (startTime.before(endTime)) {
              //  Toast.makeText(this, "Staring time not to be greater thn end time", Toast.LENGTH_LONG).show();

                return false;
            }
        } catch (NumberFormatException | java.text.ParseException e) {
            e.getLocalizedMessage();
        }
        return true;
    }

    private void allowToFacility() {
        Log.d(TAG, "ALL GOOD");
        Toast.makeText(this, "ALL GOOD", Toast.LENGTH_SHORT).show();
    }


}
