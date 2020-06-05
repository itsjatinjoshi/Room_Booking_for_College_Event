package com.example.roombooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roombooking.Model.RetrofitClientBaseUrl;
import com.example.roombooking.Utils.PreferenceUtils;
import com.example.roombooking.ViewModel.Get.EmailDetails;
import com.example.roombooking.ViewModel.Get.EmailPojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadEmail extends AppCompatActivity {
    private static final String TAG = "ReadEmail";
    int id;
    String emailTitle, Message, Time, name, sendBy;

    TextView tvSenderName, tvSenderEmail, tvSubject, tvMessage;
    Button btnReply, btnForward;
    ImageView ivDelete;
    Toolbar toolbar;

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
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        tvSenderName = findViewById(R.id.tvSenderName);
        tvSenderEmail = findViewById(R.id.tvSenderEmail);
        tvSubject = findViewById(R.id.tvSubject);
        tvMessage = findViewById(R.id.tvMessage);
        btnReply = findViewById(R.id.btnReply);
        btnForward = findViewById(R.id.btnForward);
        ivDelete = findViewById(R.id.ivDelete);

        tvSenderName.setText(name);
        tvSenderEmail.setText("< " + sendBy + " >");
        tvSubject.setText("Subject: " + emailTitle);
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

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmail();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) ;
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void replyToSender() {

    }

    private void forwardToNewUser() {

    }

    private void deleteEmail() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ReadEmail.this);
        alertDialog.setTitle("Delete Email !!!");
        alertDialog.setCancelable(true);
        alertDialog.setMessage("Deleted email won't be undo");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Call<EmailPojo> call = RetrofitClientBaseUrl.getInstance().deleteEmail().deleteEmail(id);
                call.enqueue(new Callback<EmailPojo>() {
                    @Override
                    public void onResponse(Call<EmailPojo> call, Response<EmailPojo> response) {
                        int emailID = response.body().getEmailDetail().get(0).getEmailId();
                        if (response.body().getStatus().equals("Success")) {
                            Toast.makeText(ReadEmail.this, "Email Deleted Successfully", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "onResponse: " + emailID + " deleted");
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(ReadEmail.this, InboxEmails.class);
                                    startActivity(intent);
                                }
                            }, 3000);
                        } else {
                            Toast.makeText(ReadEmail.this, "Not deleted, Try again", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<EmailPojo> call, Throwable t) {
                        Toast.makeText(ReadEmail.this, "Not deleted, Try again", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        t.printStackTrace();
                    }
                });

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();


    }


}
//todo: set animation after delete the email