package com.gerardopoblete.hospitalapp.model.doctors;

public class Doctor {

    private String RUT;
    private String names;
    private String lastnames;
    private String speciality;

    public Doctor(String RUT, String names, String lastnames, String speciality) {
        this.RUT = RUT;
        this.names = names;
        this.lastnames = lastnames;
        this.speciality = speciality;
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

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
