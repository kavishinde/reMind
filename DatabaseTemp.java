package com.example.remind;

public class DatabaseTemp {
    private String batch;
    private boolean tbooked;
    private String teacher;


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
    public DatabaseTemp(boolean tbooked, String teacher, String batch)
    {
        this.tbooked=tbooked;
        this.batch=batch;
        this.teacher=teacher;

    }
}
