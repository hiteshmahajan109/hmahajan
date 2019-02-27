package com.android15dev.studentdataapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetail {

    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("course")
    @Expose
    private String course;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    /**
     * No args constructor for use in serialization
     */
    public UserDetail() {
    }

    /**
     * @param course
     * @param lastName
     * @param phoneNumber
     * @param address
     * @param dob
     * @param firstName
     */
    public UserDetail(String firstName, String lastName, String dob, String course, String address, String phoneNumber) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.course = course;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

}