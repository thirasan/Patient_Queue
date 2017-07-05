package com.example.thirasan.patient_queue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by thirasan on 6/26/2017 AD.
 */

public class AddPatientActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText identifier;
    private Button buttonOK;

    private EditText q1;
    private EditText q2;
    private EditText q3;
    private EditText q4;
    private EditText q5;

    private DBHelper mHelper;

    private int ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpatient);

        initViewHolders();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            ID = bundle.getInt(Patient.Column.ID);
            String firstName = bundle.getString(Patient.Column.FIRST_NAME);
            String lastName = bundle.getString(Patient.Column.LAST_NAME);
            String identifier = bundle.getString(Patient.Column.IDENTIFIER);

            this.firstName.setText(firstName);
            this.lastName.setText(lastName);
            this.identifier.setText(identifier);
        }
    }

    private void initViewHolders() {
        this.mHelper = new DBHelper(this);
        this.firstName = (EditText) findViewById(R.id.firstName);
        this.lastName = (EditText) findViewById(R.id.lastName);
        this.identifier = (EditText) findViewById(R.id.identifier);

        this.q1 = (EditText) findViewById(R.id.q1);
        this.q2 = (EditText) findViewById(R.id.q2);
        this.q3 = (EditText) findViewById(R.id.q3);
        this.q4 = (EditText) findViewById(R.id.q4);
        this.q5 = (EditText) findViewById(R.id.q5);

        this.buttonOK = (Button) findViewById(R.id.ok);
        buttonOK.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(AddPatientActivity.this);
                builder.setTitle(getString(R.string.add_data_title));
                builder.setMessage(getString(R.string.add_data_message));

                builder.setPositiveButton(getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String queues = "";

                                if(!(q1.getText().toString()).equals("")){
                                    queues = queues + q1.getText().toString() + ",";
                                }
                                if(!(q2.getText().toString()).equals("")){
                                    queues = queues + q2.getText().toString() + ",";
                                }
                                if(!(q3.getText().toString()).equals("")){
                                    queues = queues + q3.getText().toString() + ",";
                                }
                                if(!(q4.getText().toString()).equals("")){
                                    queues = queues + q4.getText().toString() + ",";
                                }
                                if(!(q5.getText().toString()).equals("")){
                                    queues = queues + q5.getText().toString();
                                }

                                Patient patient = new Patient(firstName.getText().toString(),lastName.getText().toString(),identifier.getText().toString(),queues);

                                if (ID == -1) {
                                    mHelper.addPatient(patient);
                                } else {
                                    patient.setId(ID);
                                    mHelper.updatePatient(patient);
                                }
                                finish();
                            }
                        });

                builder.setNegativeButton(getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });


                builder.show();
            }
        });


    }

}
