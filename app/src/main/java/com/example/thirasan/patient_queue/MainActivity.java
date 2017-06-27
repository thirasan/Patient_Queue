package com.example.thirasan.patient_queue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AppView {

    private Presenter presenter;

    private DBHelper mHelper;
    private List<String> patients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewHolders();

        if(presenter == null) {
            presenter = new Presenter(this);
        }

        ListView patientList = (ListView)findViewById(R.id.patientList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, patients);

        patientList.setAdapter(adapter);
    }

    private void initViewHolders() {
        this.mHelper = new DBHelper(this);
        this.patients = mHelper.getPatientList();
    }

    @Override
    public void update() {

    }

    public void addPatient(View view) {
        Intent addPatients = new Intent(MainActivity.this, AddPatientActivity.class);

        startActivity(addPatients);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }
}