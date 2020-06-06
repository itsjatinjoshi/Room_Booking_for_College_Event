package com.example.roombooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roombooking.Model.RetrofitClientBaseUrl;
import com.example.roombooking.ViewModel.Post.SendEmail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReplyEmail extends AppCompatActivity {
    private static final String TAG = "ReplyOrForwardEmail";
    int id;
    private String emailTitle, Message, Time, name, receiverEmail, firstName, senderEmail;
    TextView tvSenderName, tvSenderEmail, tvSubject;
    EditText etMessage;
    Button btnCancel, btnSend;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_email);

        SharedPreferences sharedpref = getApplication().getSharedPreferences("replyEmail", MODE_PRIVATE);
        id = sharedpref.getInt("id", 0); //
        emailTitle = sharedpref.getString("emailTitle", "");
        Message = sharedpref.getString("Message", "");
        Time = sharedpref.getString("Time", "");
        name = sharedpref.getString("name", "");
        receiverEmail = sharedpref.getString("sendBy", "");

        Log.d(TAG, "RECEIVER EMAIL" + receiverEmail);

        SharedPreferences sharedpref1 = getApplication().getSharedPreferences("userName", MODE_PRIVATE);
        firstName = sharedpref1.getString("firstName", ""); //
        senderEmail = sharedpref1.getString("email", "");
        Log.d(TAG, "FIRST NAME : " + firstName);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tvSenderName = findViewById(R.id.tvSenderName);
        tvSenderEmail = findViewById(R.id.tvSenderEmail);
        tvSubject = findViewById(R.id.tvSubject);
        etMessage = findViewById(R.id.etMessage);
        btnCancel = findViewById(R.id.btnCancel);
        btnSend = findViewById(R.id.btnSend);

        tvSenderName.setText(name);
        tvSenderEmail.setText(receiverEmail);
        etMessage.setText(Message);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent readEmailActivity = new Intent(ReplyEmail.this, ReadEmail.class);
                startActivity(readEmailActivity);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmailWithReply();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) ;
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void updateEmailWithReply() {
        String modifyMessage = etMessage.getText().toString();
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String timeDate = dateFormat.format(date);
        System.out.println("Converted String: " + timeDate);


        Call<SendEmail> call = RetrofitClientBaseUrl.getInstance().replyEmail()
                .updateEmail(emailTitle, modifyMessage, timeDate, firstName, senderEmail, receiverEmail, id);
        call.enqueue(new Callback<SendEmail>() {
            @Override
            public void onResponse(Call<SendEmail> call, Response<SendEmail> response) {
                if (response.body().getStatus().equals("Success")) {
                    Toast.makeText(ReplyEmail.this, "Message send Successfully", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse: " + "Message send");
                }
            }

            @Override
            public void onFailure(Call<SendEmail> call, Throwable t) {
                t.printStackTrace();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

}

//todo : handler.. after send email to go back to detail message
//todo: try to figure how how to keep the email inside the list view, because its update email address, in my point of view create a new table and save email inside , but will do it later