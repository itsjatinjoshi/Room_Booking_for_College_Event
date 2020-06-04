package com.example.roombooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.roombooking.Model.EmailAdapter;
import com.example.roombooking.Model.RetrofitClientBaseUrl;
import com.example.roombooking.ViewModel.Get.EmailDetails;
import com.example.roombooking.ViewModel.Get.EmailPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InboxEmails extends AppCompatActivity {
    private static final String TAG = "InboxEmails";
    Toolbar toolbar;
    RecyclerView recyclerEmail;
    EmailAdapter mAdapter;
    ArrayList<EmailDetails> emailArray = new ArrayList<>();
    String email;
    TextView tvNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: " + "CHECK POINT1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_emails);
        toolbar = findViewById(R.id.toolbar);
        tvNotification = findViewById(R.id.tvNotification);
        tvNotification.setText("No New Email...");
        toolbar.setTitle("Inbox");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences sharedpref1 = getApplication().getSharedPreferences("inbox", MODE_PRIVATE);
        email = sharedpref1.getString("email", "");
        Log.d(TAG, "onCreate: getEmail : " + email);
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

        Call<EmailPojo> call = RetrofitClientBaseUrl.getInstance().getEmail().getEmail(email);
        call.enqueue(new Callback<EmailPojo>() {
            @Override
            public void onResponse(Call<EmailPojo> call, Response<EmailPojo> response) {

                Log.d(TAG, "onResponse code : " + response.code());
                Log.d(TAG, "onResponse message : " + response.message());
                Log.d(TAG, "onResponse raw : " + response.raw());
                EmailPojo attributes = response.body();
                List<EmailDetails> list = response.body().getEmailDetail();
                try {
                    postData(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (response.body().getStatus().equals("Success")) {

                    for (int i = 0; i < list.size(); i++) {
                        int id = list.get(i).getEmailId();
                        String email = list.get(i).getEmailTitle();
                        String Message = list.get(i).getMessage();
                        String Time = list.get(i).getDateTime();
                        String name = list.get(i).getSenderName();
                        String sendBy = list.get(i).getEmailSendBy();
                        System.out.println(id + "\n"
                                + email + "\n" +
                                Message + "\n" +
                                Time + "\n" +
                                name + "\n" +
                                sendBy);
                    }
                }
                tvNotification.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<EmailPojo> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                t.printStackTrace();
                tvNotification.setVisibility(View.VISIBLE);
            }
        });
    }

    public void postData(List<EmailDetails> postList) throws JSONException {
        // Log.d(TAG, ": " +"IS IT CALLING");
        Log.d(TAG, "postData : " + "CHECK POINT1");
        final ArrayList<EmailDetails> posts1 = (ArrayList<EmailDetails>) postList;
        recyclerEmail = findViewById(R.id.recyclerEmail);
        recyclerEmail.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new EmailAdapter(posts1, getApplicationContext());
        Log.d(TAG, "postData: " + mAdapter.toString());
        recyclerEmail.setAdapter(mAdapter);

        Log.d(TAG, "ADAPTER: " + mAdapter.getItemCount());
        // mAdapter.setOnItemClickListener(onItemClickPoke);


    }

}
