package com.example.dfma_app_656995;

public class HealthHelper {

    private String Tag, Condition, DoctorName, DoctorContact, FarmerContact;
            //Repeat, DateDue, DateReminder, Time;

    public HealthHelper() {
    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getDoctorContact() {
        return DoctorContact;
    }

    public void setDoctorContact(String doctorContact) {
        DoctorContact = doctorContact;
    }

    public String getFarmerContact() {
        return FarmerContact;
    }

    public void setFarmerContact(String farmerContact) {
        FarmerContact = farmerContact;
    }
}
