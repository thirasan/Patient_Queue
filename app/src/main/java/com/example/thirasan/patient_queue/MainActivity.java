package com.example.thirasan.patient_queue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    private DBHelper mHelper;
    private List<String> patients;

    ListView patientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewHolders();

        this.patients = mHelper.getPatientList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, patients);

        patientList.setAdapter(adapter);
    }

    @Override
    public void onResume(){
        super.onResume();

        this.patients = mHelper.getPatientList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, patients);

        patientList.setAdapter(adapter);
    }

    private void initViewHolders() {
        this.mHelper = new DBHelper(this);
        this.patientList = (ListView)findViewById(R.id.patientList);

        patientList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent queue = new Intent(MainActivity.this, QueueActivity.class);

                String listName = patients.get(position);
                int index = listName.indexOf(" ");
                String columnId = listName.substring(0, index);

                queue.putExtra(Patient.Column.ID, columnId);
                startActivity(queue);
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
        });
    }

    public void addPatient(View view) {
        Intent addPatients = new Intent(MainActivity.this, AddPatientActivity.class);

        startActivity(addPatients);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }
}