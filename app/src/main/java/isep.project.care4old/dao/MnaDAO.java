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
import isep.project.care4old.model.Mna;

public class MnaDAO implements MyDatabase{

    private SQLiteDatabase db;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public MnaDAO(Context context) {
        DatabaseHandler dbHelper = new DatabaseHandler(context);
        db = dbHelper.getWritableDatabase();
    }


    public void ajouter(Mna m) {

        Date date = new Date();
        String current = dateFormat.format(date); //2014/08/06

        ContentValues value = new ContentValues();
        value.put(MNA_ID, m.getId());
        value.put(MNA_DATE, current);
        value.put(MNA_APPETITE, m.getAppetite());
        value.put(MNA_LOOSE, m.getLoose_weight());
        value.put(MNA_MOTRICITY, m.getMotricity());
        value.put(MNA_ACUTE, m.getAcute());
        value.put(MNA_NEURO, m.getNeuro());
        value.put(MNA_BMI, m.getBmi());
        value.put(MNA_TOTAL, m.getTotal());

        db.insert(MNA_TABLE_NAME, null, value);
    }

    public ArrayList<Mna> getMnaTestById(int id) {

        ArrayList<Mna> listMnas = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + MNA_TABLE_NAME + " WHERE " + MNA_ID + " = ? ORDER BY "+MNA_DATE+" DESC ", new String[]{String.valueOf(id)});

        while(c.moveToNext()){

            Mna mna = new Mna(id);

            mna.setId(c.getInt(0));
            try {
                Date date = dateFormat.parse(c.getString(1)) ;
                mna.setDate(date);
            } catch (ParseException e) {
                Log.v("LOG DATE", e.getMessage());
            }
            mna.setAppetite(c.getInt(2));
            mna.setLoose_weight(c.getInt(3));
            mna.setMotricity(c.getInt(4));
            mna.setAcute(c.getInt(5));
            mna.setNeuro(c.getInt(6));
            mna.setBmi(c.getInt(7));
            mna.setTotal(c.getInt(8));

            listMnas.add(mna);
        }

        c.close();

        return listMnas ;
    }

    public Mna getResultMna(int id,String date){

        Mna mna = new Mna(id);

        Cursor c = db.rawQuery("SELECT * FROM " + MNA_TABLE_NAME +
                " WHERE " + MNA_ID + " = ? AND " + MNA_DATE + " = ?", new String[]{String.valueOf(id), date});

        c.moveToFirst();

        try {
            Date dateMna = dateFormat.parse(c.getString(1)) ;
            mna.setDate(dateMna);
        } catch (ParseException e) {
            Log.v("LOG DATE", e.getMessage());
        }

        mna.setAppetite(c.getInt(2));
        mna.setLoose_weight(c.getInt(3));
        mna.setMotricity(c.getInt(4));
        mna.setAcute(c.getInt(5));
        mna.setNeuro(c.getInt(6));
        mna.setBmi(c.getInt(7));
        mna.setTotal(c.getInt(8));

        c.close();

        return mna ;
    }

    public void close() {
        db.close();
    }
}

