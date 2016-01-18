package isep.project.care4old.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import isep.project.care4old.database.DatabaseHandler;
import isep.project.care4old.database.MyDatabase;
import isep.project.care4old.model.Hospitalization;

public class HospDAO implements MyDatabase {

    protected SQLiteDatabase myDatabase;
    protected DatabaseHandler myDatabaseHandler;

    public HospDAO(Context context) {
        myDatabaseHandler = new DatabaseHandler(context);
        myDatabase = myDatabaseHandler.getWritableDatabase();
    }

    public void close() {
        myDatabase.close();
    }

    public void addHosp(Hospitalization hosp) {
        ContentValues myHospSet = new ContentValues();
        myHospSet.put(HOSPITALIZATION_ID_USER, hosp.getIdPatient());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        myHospSet.put(HOSPITALIZATION_START, dateFormat.format(hosp.getHospStart()));
        myHospSet.put(HOSPITALIZATION_END, dateFormat.format(hosp.getHospEnd()));
        myHospSet.put(HOSPITALIZATION_MOTIF, hosp.getMotif());

        myDatabase.insert(HOSPITALIZATION_TABLE_NAME, null, myHospSet);
    }

    public boolean checkHosp(String hospToCheck) {
        Cursor c = myDatabase.rawQuery("SELECT " + HOSPITALIZATION_KEY + " FROM " + HOSPITALIZATION_TABLE_NAME + " WHERE " +
                        HOSPITALIZATION_START + " = ?",
                new String[]{hospToCheck});
        c.moveToFirst();
        c.close();
        return c.getCount() > 0;
    }

    public ArrayList<String> getHosps(int idPatient) {
        ArrayList<String> myHosps = new ArrayList<>();
        Cursor c = myDatabase.rawQuery("SELECT " + HOSPITALIZATION_START + ", " + HOSPITALIZATION_END + ", " + HOSPITALIZATION_MOTIF + " FROM " + HOSPITALIZATION_TABLE_NAME + " WHERE " +
                HOSPITALIZATION_ID_USER + " = ? ORDER BY "+HOSPITALIZATION_END+" DESC ", new String[]{String.valueOf(idPatient)});
        while(c.moveToNext())
            myHosps.add(c.getString(0) + " - " + c.getString(1) + " - " + c.getString(2));
        c.close();

        return myHosps;
    }

    public String[] getLastHosp(int idPatient) {
        String[] lastResults = new String[3];

        Cursor c = myDatabase.rawQuery("SELECT " + HOSPITALIZATION_START + ", " + HOSPITALIZATION_END + ", " + HOSPITALIZATION_MOTIF + " FROM " + HOSPITALIZATION_TABLE_NAME + " WHERE " +
                HOSPITALIZATION_ID_USER + " = ? ORDER BY "+HOSPITALIZATION_END+" DESC LIMIT 1", new String[]{String.valueOf(idPatient)});

        if(c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < lastResults.length; i++) {
                lastResults[i] = c.getString(i);
            }
        }
        c.close();

        return lastResults;
    }
}