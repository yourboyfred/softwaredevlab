package com.example.omra;

import android.widget.EditText;
import android.widget.Spinner;

public class AppointmentDetails {
    private String date;
    private String time;
    private String contact;
    private String purpose;

    private String description;
    private String month;

    public AppointmentDetails() {
    }

    public AppointmentDetails(String description, String date, String month, String time, String contact, String purpose) {
        this.date = date;
        this.time = time;
        this.contact = contact;
        this.purpose = purpose;
        this.description = description;
       this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}