package com.example.thirasan.patient_queue;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity{

    private PatientDB mHelper;
    private CategoryDB cHelper;

    private List<String> patients;

    ListView patientList;

    public static String TABLE_NAME = "";
    private String id = "";

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

    private void initViewHolders() {
        Intent intent = getIntent();
        TABLE_NAME = intent.getStringExtra("table");
        this.id = intent.getStringExtra("id");

        this.mHelper = new PatientDB(this);
        this.cHelper = new CategoryDB(this);

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

    @Override
    public void onResume(){
        super.onResume();

        this.patients = mHelper.getPatientList();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, patients);

        patientList.setAdapter(adapter);
    }

    public void addPatient(View view) {
        Intent addPatients = new Intent(MainActivity.this, AddPatientActivity.class);

        startActivity(addPatients);
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    public void dropTable(View view) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.delete_table_title));
        builder.setMessage(getString(R.string.delete_teble_message));

        builder.setPositiveButton(getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cHelper.deleteCategory(id);
                        mHelper.dropTable();
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
}