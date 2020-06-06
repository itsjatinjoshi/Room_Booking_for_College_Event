package com.example.roombooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roombooking.Model.RetrofitClientBaseUrl;
import com.example.roombooking.ViewModel.Post.RegisterNewUser;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUser extends AppCompatActivity {
    private static final String LOG_TAG = "RegisterUser";
    TextView tvLogin, facilityId, etFirstName, etLastName, etUsername, etEmail, etPassword, etConfirmPassword, etPhone;
    Spinner spinnerDepartment;
    Button btnRegister;
    int id;
    long phone;
    String fname, lname, username, password, email, department;
    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    String MobilePattern = "[0-9]{10}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        tvLogin = findViewById(R.id.tvLogin);
        facilityId = findViewById(R.id.facilityId);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etPhone = findViewById(R.id.etPhone);
        spinnerDepartment = findViewById(R.id.spinnerDepartment);
        btnRegister = findViewById(R.id.btnRegister);

        String[] departmentOptions = {"Please choose Option", "Student", "Teacher", "Management", "Others"};

        ArrayAdapter<String> departmentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, departmentOptions);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //todo: change the dropdown icon of spinner later
        spinnerDepartment.setAdapter(departmentAdapter);

        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(RegisterUser.this, LoginUser.class);
                startActivity(loginActivity);
            }
        });

        registerUser();
        clearFields(false);
    }


    private void registerUser() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidations();

                Log.d(LOG_TAG, "REGISTER SUCCESSFUL !!!  ");

            }
        });
    }


    private void checkValidations() {

        if (facilityId.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter the ID", Toast.LENGTH_SHORT).show();
        } else if (etFirstName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter the First Name", Toast.LENGTH_SHORT).show();
        } else if (etLastName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter the Last Name", Toast.LENGTH_SHORT).show();
        } else if (etUsername.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter the Username", Toast.LENGTH_SHORT).show();
        } else if (etEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter the Email", Toast.LENGTH_SHORT).show();
        } else if (!etEmail.getText().toString().matches(String.valueOf(EMAIL_ADDRESS_PATTERN))) {
            Toast.makeText(this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
        } else if (etPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter the Password", Toast.LENGTH_SHORT).show();
        } else if (etPassword.getText().toString().length() < 8) {
            Toast.makeText(this, "Password more than 8 characters", Toast.LENGTH_SHORT).show();
        } else if (etConfirmPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter the Confirm Password", Toast.LENGTH_SHORT).show();
        } else if (!(etPassword.getText().toString()).equals(etConfirmPassword.getText().toString())) {
            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show();
        } else if (etPhone.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter the Phone Number", Toast.LENGTH_SHORT).show();
        } else if (!etPhone.getText().toString().matches(MobilePattern)) {
            Toast.makeText(this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
        } else if (spinnerDepartment.getSelectedItem().toString().trim().equals("Please choose Option")) {
            Toast.makeText(this, "Please Choose Department", Toast.LENGTH_SHORT).show();
        } else {
            registerUserRetrofit();
        }

    }


    private void registerUserRetrofit() {
        Log.d(LOG_TAG, "Retrofit Method Starts: ");
        id = Integer.parseInt(facilityId.getText().toString());
        fname = etFirstName.getText().toString();
        lname = etLastName.getText().toString();
        username = etUsername.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
        try {
            phone = Long.parseLong(etPhone.getText().toString());
        } catch (NumberFormatException nm) {
            nm.printStackTrace();
        }

        department = spinnerDepartment.getSelectedItem().toString();

        Call<RegisterNewUser> call = RetrofitClientBaseUrl.getInstance().getRegisterUser().registerUser(
                id, fname, lname, username, password, email, (int) phone, department);

        call.enqueue(new Callback<RegisterNewUser>() {
            @Override
            public void onResponse(Call<RegisterNewUser> call, Response<RegisterNewUser> response) {
                Log.d(LOG_TAG, "Retrofit CallBack Starts: ");

                if (response.body().getStatus().equals("Success")) {
                    Toast.makeText(RegisterUser.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                    clearFields(true);

                    Log.d(LOG_TAG, "RESPONSE " + response.message());
                    Log.d(LOG_TAG, "RESPONSE SUCCESSFUL: " + response.isSuccessful());
                } else if (response.body().getStatus().equals("Failed")) {
                    Log.d(LOG_TAG, "Retrofit JSON NOT MATCHED : ");
                    Toast.makeText(RegisterUser.this, "Registered Failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterUser.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<RegisterNewUser> call, Throwable t) {
                Toast.makeText(RegisterUser.this, "Register Failure", Toast.LENGTH_SHORT).show();
                Log.d(LOG_TAG, "onFailure: " + t.getMessage());
            }
        });
        Log.d(LOG_TAG, "Retrofit CallBack Finish: ");
    }


    private void clearFields(boolean isSuccessful) {
        Log.d(LOG_TAG, "clearFields: Called");
        if (isSuccessful) {
            facilityId.setText("");
            etFirstName.setText("");
            etLastName.setText("");
            etUsername.setText("");
            etEmail.setText("");
            etPassword.setText("");
            etConfirmPassword.setText("");
            etPhone.setText("");
            spinnerDepartment.setSelection(0);
        } else {
            Log.d(LOG_TAG, "NOT CLEARED ");
        }


    }
}
