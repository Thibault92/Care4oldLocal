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
import isep.project.care4old.model.Katz;

public class KatzDAO implements MyDatabase{

    private SQLiteDatabase db;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public KatzDAO(Context context) {
        DatabaseHandler dbHelper = new DatabaseHandler(context);
        db = dbHelper.getWritableDatabase();
    }

    public void ajouter(Katz k) {

        Date date = new Date();
        String current = dateFormat.format(date);

        ContentValues value = new ContentValues();
        value.put(KATZ_ID, k.getId());
        value.put(KATZ_DATE, current);
        value.put(KATZ_HYGIENE, k.getHygiene());
        value.put(KATZ_DRESSING, k.getDressing());
        value.put(KATZ_BATHROOM, k.getBathroom());
        value.put(KATZ_LOCOMOTION, k.getLocomotion());
        value.put(KATZ_CONTINENCE, k.getContinence());
        value.put(KATZ_LUNCH, k.getLunch());
        value.put(KATZ_TOTAL, k.getTotal());
        db.insert(KATZ_TABLE_NAME, null, value);

    }

    public ArrayList<Katz> getKatzTestById(int id) {

        ArrayList<Katz> listKatz = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + KATZ_TABLE_NAME+ " WHERE " + KATZ_ID + " = ? ORDER BY "+KATZ_DATE+" DESC ", new String[]{String.valueOf(id)});

        while(c.moveToNext()){

            Katz katz = new Katz(id);

            katz.setId(c.getInt(0));
            try {
                Date date = dateFormat.parse(c.getString(1)) ;
                katz.setDate(date);
            } catch (ParseException e) {
                Log.v("LOG DATE", e.getMessage());
            }
            katz.setHygiene(c.getInt(2));
            katz.setDressing(c.getInt(3));
            katz.setBathroom(c.getInt(4));
            katz.setLocomotion(c.getInt(5));
            katz.setContinence(c.getInt(6));
            katz.setLunch(c.getInt(7));
            katz.setTotal(c.getInt(8));

            listKatz.add(katz);
        }

        c.close();

        return listKatz ;
    }

    public Katz getResultKatz(int id,String date){

        Katz katz = new Katz(id);

        Cursor c = db.rawQuery("SELECT * FROM " + KATZ_TABLE_NAME +
                " WHERE " + KATZ_ID + " = ? AND " + KATZ_DATE + " = ?", new String[]{String.valueOf(id), date});

        c.moveToFirst();

        try {
            Date datekatz = dateFormat.parse(c.getString(1)) ;
            katz.setDate(datekatz);
        } catch (ParseException e) {
            Log.v("LOG DATE", e.getMessage());
        }

        katz.setHygiene(c.getInt(2));
        katz.setDressing(c.getInt(3));
        katz.setBathroom(c.getInt(4));
        katz.setLocomotion(c.getInt(5));
        katz.setContinence(c.getInt(6));
        katz.setLunch(c.getInt(7));
        katz.setTotal(c.getInt(8));

        c.close();

        return katz ;
    }

    public void close() {
        db.close();
    }
}

