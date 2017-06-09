package com.example.thirasan.patient_queue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements AppView {

    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewHolders();

        if(presenter == null) {
            presenter = new Presenter(this);
        }
    }

    private void initViewHolders() {

    }

    @Override
    public void update() {

    }
}