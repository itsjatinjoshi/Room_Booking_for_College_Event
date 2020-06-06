package com.example.roombooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.roombooking.Model.RetrofitClientBaseUrl;
import com.example.roombooking.ViewModel.Post.SendEmail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForwardEmail extends AppCompatActivity {
    private static final String TAG = "ForwardEmail";
    private String emailTitle, Message, firstName, senderEmail;
    Button btnCancel, btnSend;
    EditText etMessage, etForwardEmailTo;
    TextView tvSenderName, tvSenderEmail, tvSubject;
    Toolbar toolbar;
    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forward_email);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Forward");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences sharedpref1 = getApplication().getSharedPreferences("userName", MODE_PRIVATE);
        firstName = sharedpref1.getString("firstName", ""); //
        senderEmail = sharedpref1.getString("email", "");

        SharedPreferences sharedpref = getApplication().getSharedPreferences("replyEmail", MODE_PRIVATE);
        emailTitle = sharedpref.getString("emailTitle", "");
        Message = sharedpref.getString("Message", "");

        btnCancel = findViewById(R.id.btnCancel);
        btnSend = findViewById(R.id.btnSend);
        etMessage = findViewById(R.id.etMessage);
        etForwardEmailTo = findViewById(R.id.etForwardEmailTo);
        tvSenderName = findViewById(R.id.tvSenderName);
        tvSenderEmail = findViewById(R.id.tvSenderEmail);
        tvSubject = findViewById(R.id.tvSubject);

        tvSenderName.setText(firstName);
        tvSenderEmail.setText(senderEmail);
        tvSubject.setText("Subject: "+emailTitle);
        etMessage.setText(Message);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent readEmailActivity = new Intent(ForwardEmail.this, ReadEmail.class);
                startActivity(readEmailActivity);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forwardToNewUser();
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) ;
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void forwardToNewUser() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String timeDate = dateFormat.format(date);
        System.out.println("Converted String: " + timeDate);
        if (etForwardEmailTo.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter the email", Toast.LENGTH_SHORT).show();
        } else if (!etForwardEmailTo.getText().toString().matches(String.valueOf(EMAIL_ADDRESS_PATTERN))) {
            Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
        } else {
            String emailForwardTo = etForwardEmailTo.getText().toString().trim();

            Call<SendEmail> call = RetrofitClientBaseUrl.getInstance().sendEmail().sendEmail(emailTitle, Message, timeDate, firstName, senderEmail, emailForwardTo);
            call.enqueue(new Callback<SendEmail>() {
                @Override
                public void onResponse(Call<SendEmail> call, Response<SendEmail> response) {
                    if (response.body().getStatus().equals("Success")) {
                        Toast.makeText(ForwardEmail.this, "Message send Successfully", Toast.LENGTH_LONG).show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(ForwardEmail.this, InboxEmails.class);
                                startActivity(intent);
                            }
                        }, 2000);
                        // clearAllFields(true);
                    } else {
                        Toast.makeText(ForwardEmail.this, "Sending failed...", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Sending failed: " + response.body().getStatus());
                    }
                }

                @Override
                public void onFailure(Call<SendEmail> call, Throwable t) {

                }
            });
        }


    }
}
