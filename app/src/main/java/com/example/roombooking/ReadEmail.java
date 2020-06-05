package com.example.roombooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReadEmail extends AppCompatActivity {
    int id;
    String emailTitle, Message, Time, name, sendBy;

    TextView tvSenderName, tvSenderEmail, tvSubject, tvMessage;
    Button btnReply, btnForward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_email);

        SharedPreferences sharedpref = getApplication().getSharedPreferences("OpenEmail", MODE_PRIVATE);
        id = sharedpref.getInt("id", 0); //
        emailTitle = sharedpref.getString("emailTitle", "");
        Message = sharedpref.getString("Message", "");
        Time = sharedpref.getString("Time", "");
        name = sharedpref.getString("name", "");
        sendBy = sharedpref.getString("sendBy", "");

        tvSenderName = findViewById(R.id.tvSenderName);
        tvSenderEmail = findViewById(R.id.tvSenderEmail);
        tvSubject = findViewById(R.id.tvSubject);
        tvMessage = findViewById(R.id.tvMessage);
        btnReply = findViewById(R.id.btnReply);
        btnForward = findViewById(R.id.btnForward);

        tvSenderName.setText(name);
        tvSenderEmail.setText("< "+sendBy+" >");
        tvSubject.setText("Subject: "+emailTitle);
        tvMessage.setText(Message);
        
        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyToSender();
            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forwardToNewUser();
            }
        });

    }

    private void replyToSender() {

    }
    private void forwardToNewUser() {

    }


}
