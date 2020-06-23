package com.desirestodesigns.ecommerceadmin.datamodel;

public class AdminRegistrationForm {
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String createdDate;
    private String userId;
    private String address;
    public AdminRegistrationForm(){

    }

    public AdminRegistrationForm(String firstName, String lastName, String mobileNumber, String createdDate, String userId, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.createdDate = createdDate;
        this.userId = userId;
        this.address = address;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
