package com.example.thirasan.patient_queue;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by thirasan on 6/29/2017 AD.
 */

public class QueueActivity extends AppCompatActivity{

    PatientDB mHelper;

    private LinearLayout queue;
    private TextView firstName;
    private TextView lastName;
    private TextView identifier;
    private String id = "";

    private Button buttonEdit;
    private Button buttonDelete;

    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewHolders();

    }

    @Override
    public void onResume(){
        super.onResume();

        initViewHolders();
    }

    private void setQueue(final String[] queue){
        for(int i = 0 ; i < queue.length ; i++){
            Button button = new Button(this);
            button.setLayoutParams(new ActionBar.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            button.setText(queue[i]);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button)v;
                    String buttonText = b.getText().toString();

                    mHelper.alreadyInspect(id,buttonText);
                    v.setVisibility(View.GONE);
                }
            });

            this.queue.addView(button);
        }
    }

    private void initViewHolders() {
        mHelper = new PatientDB(this);

        setContentView(R.layout.activity_queue);

        Bundle bundle  = getIntent().getExtras();

        if (bundle != null) {
            id = bundle.getString(Patient.Column.ID);
        }

        this.firstName = (TextView) findViewById(R.id.detail_first_name);
        this.lastName = (TextView) findViewById(R.id.detail_last_name);
        this.identifier = (TextView) findViewById(R.id.detail_id);
        this.queue = (LinearLayout) findViewById(R.id.detail_queue);

        this.buttonEdit = (Button) findViewById(R.id.button_edit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(QueueActivity.this,
                        AddPatientActivity.class);

                updateIntent.putExtra(Patient.Column.ID, patient.getId());
                updateIntent.putExtra(Patient.Column.FIRST_NAME, patient.getFirstName());
                updateIntent.putExtra(Patient.Column.LAST_NAME, patient.getLastName());
                updateIntent.putExtra(Patient.Column.IDENTIFIER, patient.getIdentifier());
                updateIntent.putExtra(Patient.Column.QUEUE, patient.getQueue());

                startActivity(updateIntent);
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
        });

        this.buttonDelete = (Button) findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(QueueActivity.this);
                builder.setTitle(getString(R.string.alert_title));
                builder.setMessage(getString(R.string.alert_message));

                builder.setPositiveButton(getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mHelper.deletePatient(id);

                                Toast.makeText(getApplication(),
                                        "Deleted", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });

                builder.setNegativeButton(getString(android.R.string.cancel), null);

                builder.show();

            }
        });
        patient = mHelper.getPatient(id);

        setQueue(patient.getQueue().split(","));

        firstName.setText(patient.getFirstName());
        lastName.setText(patient.getLastName());
        identifier.setText(patient.getIdentifier());
    }
}
