package com.example.roombooking.ViewModel.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginCredentials {

    @SerializedName("Facility Id")
    @Expose
    private Integer facilityId;

    @SerializedName("First Name")
    @Expose
    private String firstName;

    @SerializedName("Last Name")
    @Expose
    private String lastName;

    @SerializedName("Username")
    @Expose
    private String username;

    @SerializedName("Password")
    @Expose
    private String password;

    @SerializedName("Email")
    @Expose
    private String email;

    @SerializedName("Phone Number")
    @Expose
    private Integer phoneNumber;

    @SerializedName("Department")
    @Expose
    private String department;

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
