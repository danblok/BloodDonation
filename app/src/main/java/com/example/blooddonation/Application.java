package com.example.blooddonation;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Application implements Serializable {

    private String id;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date due;
    private User user;


    public Application(String id, Date due) {
        this.id = id;
        this.due = due;
    }
}
