package isep.project.care4old.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import isep.project.care4old.database.DatabaseHandler;
import isep.project.care4old.database.MyDatabase;
import isep.project.care4old.model.Comorbidities;

public class ComorbiditiesDAO implements MyDatabase {

    private SQLiteDatabase db;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public ComorbiditiesDAO(Context context) {
        DatabaseHandler dbHelper = new DatabaseHandler(context);
        db = dbHelper.getWritableDatabase();
    }

    public void ajouter(Comorbidities c) {

        Date date = new Date();
        String current = dateFormat.format(date);

        ContentValues value = new ContentValues();
        value.put(COMORBIDITIES_ID, c.getId());
        value.put(COMORBIDITIES_DATE, current);
        value.put(COMORBIDITIES_PATHOLOGY, c.getPathology());

        db.insert(COMORBIDITIES_TABLE_NAME, null, value);
    }

    public ArrayList<Comorbidities> getComorbiditiesTestById(int id) {

        ArrayList<Comorbidities> listComorbidities = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + COMORBIDITIES_TABLE_NAME + " WHERE " + COMORBIDITIES_ID + " = ? ORDER BY " + COMORBIDITIES_DATE + " DESC ", new String[]{String.valueOf(id)});

        while(c.moveToNext()){

            Comorbidities comorbidities = new Comorbidities(id);

            comorbidities.setId(c.getInt(0));
            try {
                Date date = dateFormat.parse(c.getString(1)) ;
                comorbidities.setDate(date);
            } catch (ParseException e) {
                Log.v("LOG DATE", e.getMessage());
            }
            comorbidities.setPathology(c.getString(2));

            listComorbidities.add(comorbidities);
        }

        c.close();

        return listComorbidities ;
    }

    public String[] getLastComorbiditie(int id){

        String[] lastComorbitie = new String[2] ;

        Cursor c = db.rawQuery("SELECT "+COMORBIDITIES_PATHOLOGY+", "+COMORBIDITIES_DATE+" FROM " + COMORBIDITIES_TABLE_NAME +
                " WHERE " + COMORBIDITIES_ID + " = ? ORDER BY "+COMORBIDITIES_DATE+" DESC LIMIT 1", new String[]{String.valueOf(id)});

        if(c.getCount() > 0) {
            c.moveToFirst();
            lastComorbitie[0]=c.getString(0);
            lastComorbitie[1]=c.getString(1);
        }
        c.close();

        return lastComorbitie ;
    }

    public boolean checkComorbidities(int id, String pathology) {

        Date date = new Date();
        String current = dateFormat.format(date);

        Cursor c = db.rawQuery("SELECT "+ COMORBIDITIES_ID+ " FROM " + COMORBIDITIES_TABLE_NAME + " WHERE " +
                COMORBIDITIES_ID+ " = ? AND " +COMORBIDITIES_PATHOLOGY+ " = ? AND "+COMORBIDITIES_DATE+ " = ? ", new String[]{String.valueOf(id),pathology,current});

        c.moveToFirst();

        c.close();

        return !(c.getCount() > 0);
    }


    public void close() {
        db.close();
    }
}