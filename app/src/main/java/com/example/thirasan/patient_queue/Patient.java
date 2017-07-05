package com.example.thirasan.patient_queue;

import android.provider.BaseColumns;

/**
 * Created by thirasan on 6/10/2017 AD.
 */

public class Patient {

    private String firstName;
    private String lastName;
    private String identifier;
    private String queue;
    private int id;

    public static final String DATABASE_NAME = "patients.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE = "patient";

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String IDENTIFIER = "identifier";
        public static final String QUEUE= "queue";
    }

    public Patient(String firstName, String lastName ,String identifier ,String queue){
        this.firstName = firstName;
        this.lastName = lastName;
        this.identifier = identifier;
        this.queue = queue;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getIdentifier(){
        return this.identifier;
    }

    public String getQueue(){ return this.queue; }

    public int getId() { return this.id; }

    public void setId(int id) {
        this.id = id;
    }
}
