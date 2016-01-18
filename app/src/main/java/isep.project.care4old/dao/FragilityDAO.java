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
import isep.project.care4old.model.Fragility;

public class FragilityDAO implements MyDatabase {

    private SQLiteDatabase db;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public FragilityDAO(Context context) {
        DatabaseHandler dbHelper = new DatabaseHandler(context);
        db = dbHelper.getWritableDatabase();
    }

    public void ajouter(Fragility f) {

        Date date = new Date();
        String current = dateFormat.format(date);

        ContentValues value = new ContentValues();
        value.put(FRAGILITY_ID, f.getId());
        value.put(FRAGILITY_DATE, current);
        value.put(FRAGILITY_HOME, f.getHome());
        value.put(FRAGILITY_DRUGS, f.getDrugs());
        value.put(FRAGILITY_MOOD, f.getMood());
        value.put(FRAGILITY_PERCEPTION, f.getPerception());
        value.put(FRAGILITY_FALL, f.getFall());
        value.put(FRAGILITY_RESPONSABILITY, f.getResponsability());
        value.put(FRAGILITY_ILLNESS, f.getIllness());
        value.put(FRAGILITY_MOBILITY, f.getMobility());
        value.put(FRAGILITY_CONTINENCE, f.getContinence());
        value.put(FRAGILITY_FEED, f.getFeed());
        value.put(FRAGILITY_COGNITIVE, f.getCognitive());
        value.put(FRAGILITY_TOTAL, f.getTotal());

        db.insert(FRAGILITY_TABLE_NAME, null, value);
    }

    public ArrayList<Fragility> getFragilityTestById(int id) {

        ArrayList<Fragility> listFragility = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + FRAGILITY_TABLE_NAME+ " WHERE " + FRAGILITY_ID + " = ? ORDER BY "+FRAGILITY_DATE+" DESC ", new String[]{String.valueOf(id)});

        while(c.moveToNext()){

            Fragility fragility = new Fragility(id);

            fragility.setId(c.getInt(0));
            try {
                Date date = dateFormat.parse(c.getString(1)) ;
                fragility.setDate(date);
            } catch (ParseException e) {
                Log.v("LOG DATE", e.getMessage());
            }
            fragility.setHome(c.getInt(2));
            fragility.setDrugs(c.getInt(3));
            fragility.setMood(c.getInt(4));
            fragility.setPerception(c.getInt(5));
            fragility.setFall(c.getInt(6));
            fragility.setResponsability(c.getInt(7));
            fragility.setIllness(c.getInt(8));
            fragility.setMobility(c.getInt(9));
            fragility.setContinence(c.getInt(10));
            fragility.setFeed(c.getInt(11));
            fragility.setCognitive(c.getInt(12));
            fragility.setTotal(c.getInt(13));

            listFragility.add(fragility);
        }

        c.close();

        return listFragility ;
    }

    public Fragility getResultFragility(int id,String date){

        Fragility fragility = new Fragility(id);

        Cursor c = db.rawQuery("SELECT * FROM " + FRAGILITY_TABLE_NAME +
                " WHERE " + FRAGILITY_ID + " = ? AND " + FRAGILITY_DATE + " = ?", new String[]{String.valueOf(id), date});

        c.moveToFirst();

        try {
            Date dateFragility = dateFormat.parse(c.getString(1)) ;
            fragility.setDate(dateFragility);
        } catch (ParseException e) {
            Log.v("LOG DATE", e.getMessage());
        }

        fragility.setHome(c.getInt(2));
        fragility.setDrugs(c.getInt(3));
        fragility.setMood(c.getInt(4));
        fragility.setPerception(c.getInt(5));
        fragility.setFall(c.getInt(6));
        fragility.setResponsability(c.getInt(7));
        fragility.setIllness(c.getInt(8));
        fragility.setMobility(c.getInt(9));
        fragility.setContinence(c.getInt(10));
        fragility.setFeed(c.getInt(11));
        fragility.setCognitive(c.getInt(12));
        fragility.setTotal(c.getInt(13));

        c.close();

        return fragility ;
    }

    public void close() {
        db.close();
    }
}


