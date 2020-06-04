package com.example.roombooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ComposeEmail extends AppCompatActivity {
    private static final String TAG = "ComposeEmail";
    Toolbar toolbar;
    EditText etToSend, etSubject, etMessage;
    Button btnSend;
    String firstName, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_email);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Compose New Email");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences sharedpref1 = getApplication().getSharedPreferences("emailKey", MODE_PRIVATE);
        firstName = sharedpref1.getString("firstName", ""); //
        email = sharedpref1.getString("email", "");

        Log.d(TAG, "Firstname : " + firstName);
        Log.d(TAG, "Email : " + email);

        etToSend = findViewById(R.id.etToSend);
        etSubject = findViewById(R.id.etSubject);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidations();
            }
        });
        clearAllFields(false);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (etToSend.getText().toString().isEmpty() &&
                    etSubject.getText().toString().isEmpty() &&
                    etMessage.getText().toString().isEmpty()) {
                finish();
            } else {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ComposeEmail.this);
                alertDialog.setTitle("Exit");
                alertDialog.setCancelable(true);
                alertDialog.setMessage("Are You Sure want to Cancel");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
                Log.d("COMPOSEMAIL", "EXIT FROM COMPOSE MAIL");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkValidations() {
        if (etToSend.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter sender address", Toast.LENGTH_LONG).show();
        } else if (etSubject.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Subject", Toast.LENGTH_LONG).show();
        } else if (etMessage.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Write description", Toast.LENGTH_LONG).show();
        } else {
            sendEmail();
        }
    }

    private void sendEmail() {
        String title = etSubject.getText().toString();
        String message = etMessage.getText().toString();
        String emailSendTo = etToSend.getText().toString();

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String timeDate = dateFormat.format(date);
        System.out.println("Converted String: " + timeDate);


        Call<SendEmail> call = RetrofitClientBaseUrl.getInstance().sendEmail().sendEmail(title, message, timeDate, firstName, email, emailSendTo);
        call.enqueue(new Callback<SendEmail>() {
            @Override
            public void onResponse(Call<SendEmail> call, Response<SendEmail> response) {
                if (response.body().getStatus().equals("Success")) {
                    Toast.makeText(ComposeEmail.this, "Message send Successfully", Toast.LENGTH_LONG).show();
                    clearAllFields(true);
                } else {
                    Toast.makeText(ComposeEmail.this, "Sending failed...", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Sending failed: " + response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<SendEmail> call, Throwable t) {

            }
        });
    }

    private void clearAllFields(boolean sendSuccessful) {
        if (sendSuccessful) {
            etSubject.setText("");
            etMessage.setText("");
            etToSend.setText("");
        } else {
            Log.d(TAG, "NOT CLEARED ");
        }
    }
}
//{title}&{message}&{time}&{name}&{email_send_by}&{email_send_to}