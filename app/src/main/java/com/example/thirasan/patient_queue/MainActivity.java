package com.example.thirasan.patient_queue;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements AppView {

    private Presenter presenter;
    private LinearLayout add;
    private RelativeLayout home;

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
        this.add = (LinearLayout) findViewById(R.id.add);
        this.home = (RelativeLayout) findViewById(R.id.home);
    }

    @Override
    public void update() {

    }

    public void addPatient(View view) {
        this.home.setVisibility(View.GONE);
        this.add.setVisibility(View.VISIBLE);
    }

    public void add(View view) {
        EditText firstName = (EditText) findViewById(R.id.firstName);
        EditText lastName = (EditText) findViewById(R.id.lastName);
        presenter.addPatient(firstName.toString(),lastName.toString());
    }
}