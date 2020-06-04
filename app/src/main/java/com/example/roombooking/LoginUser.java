package com.example.roombooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roombooking.Model.RetrofitClientBaseUrl;
import com.example.roombooking.Utils.PreferenceUtils;
import com.example.roombooking.ViewModel.Post.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.roombooking.RegisterUser.EMAIL_ADDRESS_PATTERN;

public class LoginUser extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginUser";
    TextView tvRegister;
    EditText etEmail, etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        PreferenceUtils utils = new PreferenceUtils();

        if (PreferenceUtils.getUsername(this) != null) {
            Intent intent = new Intent(LoginUser.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        if (tvRegister.isPressed()) {
            Intent registerActivity = new Intent(this, RegisterUser.class);
            startActivity(registerActivity);
        }
        if (v == btnLogin) {
            if (etEmail.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter your email ID", Toast.LENGTH_SHORT).show();
            } else if (!EMAIL_ADDRESS_PATTERN.matcher(etEmail.getText().toString().trim()).matches()) {
                Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
            } else if (etPassword.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            } else if (etPassword.getText().toString().length() < 8) {
                Toast.makeText(this, "Enter Valid password", Toast.LENGTH_SHORT).show();
            } else {
                loginUser();
            }

        }
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();
        Log.d(TAG, "EMAIL : " + email + " PASSWORD : " + pass);
        Toast.makeText(this, "All Good", Toast.LENGTH_SHORT).show();

        Call<LoginResponse> call = RetrofitClientBaseUrl.getInstance().getUserCredential().credentials(email, pass);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().getStatus().equals("Success")) {
                    int id = response.body().getUserDetail().getFacilityId();
                    String firstName = response.body().getUserDetail().getFirstName();
                    String lastName = response.body().getUserDetail().getLastName();
                    String username = response.body().getUserDetail().getUsername();
                    String password = response.body().getUserDetail().getPassword();
                    String email = response.body().getUserDetail().getEmail();
                    int phoneNumber = response.body().getUserDetail().getPhoneNumber();
                    String department = response.body().getUserDetail().getDepartment();

                    Log.d(TAG, "USER DETAILS: "
                            + "\n" + id + " "
                            + "\n" + firstName + " "
                            + "\n" + lastName + " "
                            + "\n" + username + " "
                            + "\n" + password + " "
                            + "\n" + email + " "
                            + "\n" + phoneNumber + " "
                            + "\n" + department + " ");
                    PreferenceUtils.saveUsername(email, getApplicationContext());
                    PreferenceUtils.savePassword(password, getApplicationContext());
                    SharedPreferences sharedPref = getSharedPreferences("userName", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();

                    editor.putInt("id", id);
                    editor.putString("firstName", firstName);
                    editor.putString("lastName", lastName);
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.putString("email", email);
                    editor.putInt("phoneNumber", phoneNumber);
                    editor.putString("department", department);
                    editor.apply();
                    Intent homeIntent = new Intent(LoginUser.this, HomeActivity.class);
                    startActivity(homeIntent);
                } else {
                    Toast.makeText(LoginUser.this, "Invalid User", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "RESPONSE Invalid: " + response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(LoginUser.this, "Invalid User", Toast.LENGTH_SHORT).show();
            }
        });
    }


}


//        In this project,you will have to develop a mobile application of a booking room system
//        for the CEGEP’s Montreal campus.Teachers,pedagogical counsellors as well as other
//        CEGEP staff members required to book a room in the Montreal campus either for an
//        exam or a meeting.You will have to develop a real-time booking room system that takes
//        into account the available rooms(for meetings,exams,etc.),equipment in these rooms
//        (hardware and software),the number of persons who will be in the room,the booking
//        date,as well as the reason for booking a room.