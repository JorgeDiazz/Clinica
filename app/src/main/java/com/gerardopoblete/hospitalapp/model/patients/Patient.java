package com.gerardopoblete.hospitalapp.model.patients;

public class Patient {

    private String RUT;
    private String names;
    private String lastnames;
    private String phone;
    private String email;
    private String address;

    public Patient(String RUT, String names, String lastnames, String phone, String email, String address) {
        this.RUT = RUT;
        this.names = names;
        this.lastnames = lastnames;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getRUT() {
        return RUT;
    }

    public void setRUT(String RUT) {
        this.RUT = RUT;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastnames() {
        return lastnames;
    }

    public void setLastnames(String lastnames) {
        this.lastnames = lastnames;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
