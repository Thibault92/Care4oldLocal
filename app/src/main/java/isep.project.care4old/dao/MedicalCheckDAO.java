package isep.project.care4old.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import isep.project.care4old.database.DatabaseHandler;
import isep.project.care4old.database.MyDatabase;
import isep.project.care4old.model.MedicalCheck;

public class MedicalCheckDAO implements MyDatabase {

    protected SQLiteDatabase myDatabase;
    protected DatabaseHandler myDatabaseHandler;



    public MedicalCheckDAO(Context context) {
        myDatabaseHandler = new DatabaseHandler(context);
        myDatabase = myDatabaseHandler.getWritableDatabase();
    }

    public void close() {
        myDatabase.close();
    }

    public void addMedCheck(MedicalCheck medCheck) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

        ContentValues myMedCheckSet = new ContentValues();
        myMedCheckSet.put(MEDICAL_CHECK_ID_USER, medCheck.getIdPatient());
        myMedCheckSet.put(MEDICAL_CHECK_DATE, dateFormat.format(new Date()));
        myMedCheckSet.put(MEDICAL_CHECK_HEIGHT, medCheck.getHeight());
        myMedCheckSet.put(MEDICAL_CHECK_WEIGHT, medCheck.getWeight());
        myMedCheckSet.put(MEDICAL_CHECK_MNA, medCheck.getMna());
        myMedCheckSet.put(MEDICAL_CHECK_ALBUMINEMIE, medCheck.getAlbuminemie());
        myMedCheckSet.put(MEDICAL_CHECK_CRP, medCheck.getCrp());
        myMedCheckSet.put(MEDICAL_CHECK_VITAMINE, medCheck.getVitamine());
        myMedCheckSet.put(MEDICAL_CHECK_DIAGNOSTIC, medCheck.getDiagnostic());

        myDatabase.insert(MEDICAL_CHECK_TABLE_NAME, null, myMedCheckSet);
    }


    public ArrayList<MedicalCheck> getMedicalChecks(int idPatient) {
        ArrayList<MedicalCheck> myMedChecks = new ArrayList<>();

        Cursor c = myDatabase.rawQuery("SELECT " + MEDICAL_CHECK_KEY + ", " + MEDICAL_CHECK_DATE + ", " + MEDICAL_CHECK_DIAGNOSTIC + " FROM " + MEDICAL_CHECK_TABLE_NAME +
                " WHERE " + MEDICAL_CHECK_ID_USER + " = ? ORDER BY "+MEDICAL_CHECK_DATE+" DESC ", new String[]{String.valueOf(idPatient)});

        while(c.moveToNext()) {
            MedicalCheck medCheck = new MedicalCheck();
            medCheck.setIdMedcheck(Integer.parseInt(c.getString(0)));
            medCheck.setDateConsultation(convertStringToDate(c.getString(1)));
            medCheck.setDiagnostic(c.getString(2));

            myMedChecks.add(medCheck);
        }
        c.close();
        return myMedChecks ;
    }

    public Date convertStringToDate (String stringDate) {
        DateFormat formattedBirthday = new SimpleDateFormat("dd/MM/yyyy",Locale.FRANCE);
        Date birthdayConverted = null;
        try {
            birthdayConverted = formattedBirthday.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return birthdayConverted;
    }

    public String[] getMedicalCheck(int idMedCheck) {
        String[] lastResults = new String[8];

        Cursor c = myDatabase.rawQuery("SELECT " + MEDICAL_CHECK_DATE + ", " + MEDICAL_CHECK_HEIGHT + ", " + MEDICAL_CHECK_WEIGHT +
                ", " + MEDICAL_CHECK_MNA + ", " + MEDICAL_CHECK_ALBUMINEMIE + ", " + MEDICAL_CHECK_CRP + ", " + MEDICAL_CHECK_VITAMINE +
                ", " + MEDICAL_CHECK_DIAGNOSTIC + " FROM " + MEDICAL_CHECK_TABLE_NAME +" WHERE " + MEDICAL_CHECK_KEY + " = ? ", new String[]{String.valueOf(idMedCheck)});
        if(c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < lastResults.length; i++) {
                lastResults[i] = c.getString(i);
            }
        }
        c.close();
        return lastResults ;
    }

    public String[] getLastMedicalCheck(int idPatient) {
        String[] lastResults = new String[6];

        Cursor c = myDatabase.rawQuery("SELECT " + MEDICAL_CHECK_DATE + ", " + MEDICAL_CHECK_HEIGHT + ", " + MEDICAL_CHECK_WEIGHT +
                ", "+ MEDICAL_CHECK_ALBUMINEMIE + ", " + MEDICAL_CHECK_CRP + ", " + MEDICAL_CHECK_VITAMINE + " FROM " + MEDICAL_CHECK_TABLE_NAME +
                " WHERE " + MEDICAL_CHECK_ID_USER+ " = ? ORDER BY "+MEDICAL_CHECK_DATE+" DESC LIMIT 1", new String[]{String.valueOf(idPatient)});
        if(c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < lastResults.length; i++) {
                lastResults[i] = c.getString(i);
            }
        }
        c.close();
        return lastResults ;
    }
}