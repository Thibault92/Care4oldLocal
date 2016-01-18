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
import isep.project.care4old.model.Norton;

public class NortonDAO implements MyDatabase {

    private SQLiteDatabase db;

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public NortonDAO(Context context) {
        DatabaseHandler dbHelper = new DatabaseHandler(context);
        db = dbHelper.getWritableDatabase();
    }


    public void ajouter(Norton n) {

        Date date = new Date();
        String current = dateFormat.format(date);

        ContentValues value = new ContentValues();
        value.put(NORTON_ID, n.getId());
        value.put(NORTON_DATE, current);
        value.put(NORTON_GLOBAL, n.getGlobal());
        value.put(NORTON_AGILITY, n.getAgility());
        value.put(NORTON_PSYCHIC, n.getPsychic());
        value.put(NORTON_INCONTINENCE, n.getIncontinence());
        value.put(NORTON_MOBILITY, n.getMobility());
        value.put(NORTON_TOTAL, n.getTotal());

        db.insert(NORTON_TABLE_NAME, null, value);
    }

    public ArrayList<Norton> getNortonTestById(int id) {

        ArrayList<Norton> listNortons = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + NORTON_TABLE_NAME + " WHERE " + NORTON_ID + " = ? ORDER BY "+NORTON_DATE+" DESC ", new String[]{String.valueOf(id)});

        while(c.moveToNext()){

            Norton norton = new Norton(id);

            norton.setId(c.getInt(0));
            try {
                Date date = dateFormat.parse(c.getString(1)) ;
                norton.setDate(date);
            } catch (ParseException e) {
                Log.v("LOG DATE", e.getMessage());
            }
            norton.setGlobal(c.getInt(2));
            norton.setAgility(c.getInt(3));
            norton.setPsychic(c.getInt(4));
            norton.setIncontinence(c.getInt(5));
            norton.setMobility(c.getInt(6));
            norton.setTotal(c.getInt(7));

            listNortons.add(norton);
        }

        c.close();

        return listNortons ;
    }

    public Norton getResultNorton(int id,String date){

        Norton norton = new Norton(id);

        Cursor c = db.rawQuery("SELECT * FROM "+NORTON_TABLE_NAME+
                " WHERE "+NORTON_ID+" = ? AND "+NORTON_DATE+" = ?", new String[]{String.valueOf(id),date});

        c.moveToFirst();

        try {
            Date dateNorton = dateFormat.parse(c.getString(1)) ;
            norton.setDate(dateNorton);
        } catch (ParseException e) {
            Log.v("LOG DATE", e.getMessage());
        }

        norton.setGlobal(c.getInt(2));
        norton.setAgility(c.getInt(3));
        norton.setPsychic(c.getInt(4));
        norton.setIncontinence(c.getInt(5));
        norton.setMobility(c.getInt(6));
        norton.setTotal(c.getInt(7));

        c.close();

        return norton ;
    }


}

