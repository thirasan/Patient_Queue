package com.example.thirasan.patient_queue;

/**
 * Created by thirasan on 6/10/2017 AD.
 */

public class Patient {

    private String firstName;
    private String lastName;
    private String[] queue;

    public Patient(){

    }

    public Patient(String firstName, String lastName ,String[] queue){
        this.firstName = firstName;
        this.lastName = lastName;

        this.queue = new String[queue.length];
        for(int i = 0; i < queue.length ; i++){
            this.queue[i] = queue[i];
        }
    }
}
