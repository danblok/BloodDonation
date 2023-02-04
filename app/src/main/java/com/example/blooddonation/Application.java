package com.example.blooddonation;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Application {

    private int id;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date due;
    private User user;


    public Application(int id, Date due) {
        this.id = id;
        this.due = due;
    }
}
