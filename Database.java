package com.example.remind;

public class Database {
    private boolean booked;
    private String batch;
    private boolean tbooked;
    private String teacher;

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public boolean isTbooked() {
        return tbooked;
    }

    public void setTbooked(boolean tbooked) {
        this.tbooked = tbooked;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Database(boolean booked, String teacher, boolean tbooked, String batch)
    {
        this.booked=booked;
        this.tbooked=tbooked;
        this.batch=batch;
        this.teacher=teacher;

    }


}

