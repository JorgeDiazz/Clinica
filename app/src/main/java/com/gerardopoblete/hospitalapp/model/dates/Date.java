package com.gerardopoblete.hospitalapp.model.dates;

import java.util.Calendar;

public class Date {

    private long ID;
    private String doctorRUT;
    private String patientRUT;
    private Calendar dateAndTime;
    private String state;

    public Date(long ID, String doctorRUT, String patientRUT, Calendar dateAndTime, String state) {
        this.ID = ID;
        this.doctorRUT = doctorRUT;
        this.patientRUT = patientRUT;
        this.dateAndTime = dateAndTime;
        this.state = state;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getDoctorRUT() {
        return doctorRUT;
    }

    public void setDoctorRUT(String doctorRUT) {
        this.doctorRUT = doctorRUT;
    }

    public String getPatientRUT() {
        return patientRUT;
    }

    public void setPatientRUT(String patientRUT) {
        this.patientRUT = patientRUT;
    }

    public Calendar getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Calendar dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
