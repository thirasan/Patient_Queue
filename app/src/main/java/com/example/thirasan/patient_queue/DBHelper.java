package com.example.thirasan.patient_queue;

/**
 * Created by thirasan on 6/10/2017 AD.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();

    private SQLiteDatabase sqLiteDatabase;

    public DBHelper(Context context) {
        super(context, Patient.DATABASE_NAME, null, Patient.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PATIENT_TABLE = String.format("CREATE TABLE %s " +
                        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                Patient.TABLE,
                Patient.Column.ID,
                Patient.Column.FIRST_NAME,
                Patient.Column.LAST_NAME,
                Patient.Column.IDENTIFIER,
                Patient.Column.QUEUE);

        Log.i(TAG, CREATE_PATIENT_TABLE);

        // create friend table
        db.execSQL(CREATE_PATIENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_FRIEND_TABLE = "DROP TABLE IF EXISTS " + Patient.TABLE;

        db.execSQL(DROP_FRIEND_TABLE);

        Log.i(TAG, "Upgrade Database from " + oldVersion + " to " + newVersion);

        onCreate(db);
    }

    public List<String> getPatientList() {
        List<String> patients = new ArrayList<String>();

        sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query
                (Patient.TABLE, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        while(!cursor.isAfterLast()) {

            patients.add(cursor.getLong(0) + " " +
                    cursor.getString(1) + " " +
                    cursor.getString(2));

            cursor.moveToNext();
        }

        sqLiteDatabase.close();

        return patients;
    }

    public void addPatient(Patient patient) {
        sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(Friend.Column.ID, friend.getId());
        values.put(Patient.Column.FIRST_NAME, patient.getFirstName());
        values.put(Patient.Column.LAST_NAME, patient.getLastName());
        values.put(Patient.Column.IDENTIFIER, patient.getIdentifier());
        values.put(Patient.Column.QUEUE, patient.getQueue());

        sqLiteDatabase.insert(Patient.TABLE, null, values);

        sqLiteDatabase.close();
    }

    public Patient getPatient(String id) {

        sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query( Patient.TABLE,
                null,
                Patient.Column.ID + " = ? ",
                new String[] { id },
                null,
                null,
                null,
                null);


        if (cursor != null) {
            cursor.moveToFirst();
        }

        Patient patient = new Patient(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));

        patient.setId((int) cursor.getLong(0));

        return patient;
    }
    public void alreadyInspect(String id,String queue){
        sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query( Patient.TABLE,
                null,
                Patient.Column.ID + " = ? ",
                new String[] { id },
                null,
                null,
                null,
                null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        String[] queues = cursor.getString(4).split(",");
        String newQueue = "";

        if(queues.length > 1) {
            for (int i = 0; i < queues.length; i++) {
                if (!queue.equals(queues[i])) {
                    newQueue += queues[i] + ",";
                }
            }
            newQueue = newQueue.substring(0, newQueue.length() - 1);
        }

        ContentValues values = new ContentValues();
        values.put(Patient.Column.ID, cursor.getLong(0));
        values.put(Patient.Column.FIRST_NAME, cursor.getString(1));
        values.put(Patient.Column.LAST_NAME, cursor.getString(2));
        values.put(Patient.Column.IDENTIFIER, cursor.getString(3));
        values.put(Patient.Column.QUEUE, newQueue);

        int row = sqLiteDatabase.update(Patient.TABLE,
                values,
                Patient.Column.ID + " = ? ",
                new String[] { String.valueOf(cursor.getLong(0)) });

        sqLiteDatabase.close();
    }

    public void updatePatient(Patient patient) {

        sqLiteDatabase  = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Patient.Column.ID, patient.getId());
        values.put(Patient.Column.FIRST_NAME, patient.getFirstName());
        values.put(Patient.Column.LAST_NAME, patient.getLastName());
        values.put(Patient.Column.IDENTIFIER, patient.getIdentifier());
        values.put(Patient.Column.QUEUE, patient.getQueue());

        int row = sqLiteDatabase.update(Patient.TABLE,
                values,
                Patient.Column.ID + " = ? ",
                new String[] { String.valueOf(patient.getId()) });

        sqLiteDatabase.close();
    }

    public void deletePatient(String id) {

        sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.delete(Patient.TABLE, Patient.Column.ID + " = " + id, null);

        sqLiteDatabase.close();
    }
}