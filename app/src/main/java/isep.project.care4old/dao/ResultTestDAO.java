package isep.project.care4old.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import isep.project.care4old.database.DatabaseHandler;
import isep.project.care4old.database.MyDatabase;
import isep.project.care4old.model.ResultTest;


public class ResultTestDAO implements MyDatabase{

    private SQLiteDatabase db;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public ResultTestDAO(Context context) {
        DatabaseHandler dbHelper = new DatabaseHandler(context);
        db = dbHelper.getWritableDatabase();
    }

    public void ajouter(ResultTest r) {

        Date date = new Date();
        String current = dateFormat.format(date);

        ContentValues value = new ContentValues();
        value.put(RESULT_TEST_ID, r.getIdUser());
        value.put(RESULT_TEST_DATE, current);
        value.put(RESULT_NORTON_TOTAL, r.getNorton().getTotal());
        value.put(RESULT_KATZ_TOTAL, r.getKatz().getTotal());
        value.put(RESULT_MNA_TOTAL, r.getMna().getTotal());
        value.put(RESULT_LAWTON_TOTAL, r.getLawton().getTotal());
        value.put(RESULT_FRAGILITY_TOTAL, r.getFragility().getTotal());

        db.insert(RESULT_TEST_TABLE_NAME, null, value);
    }

    public String[] getLastResultTestTotal(int id){

        String[] lastResults = new String[6];

        Cursor c = db.rawQuery("SELECT "+RESULT_NORTON_TOTAL+", "+RESULT_LAWTON_TOTAL+", "+RESULT_FRAGILITY_TOTAL+
                ", "+RESULT_MNA_TOTAL+", "+RESULT_KATZ_TOTAL+", "+RESULT_TEST_DATE+" FROM "+RESULT_TEST_TABLE_NAME+
                " WHERE "+RESULT_TEST_ID+" = ? ORDER BY "+RESULT_TEST_DATE+" DESC LIMIT 1", new String[]{String.valueOf(id)});

        if(c.getCount() > 0) {
            c.moveToFirst();
            for (int i = 0; i < lastResults.length; i++) {
                lastResults[i] = c.getString(i);
            }
        }

        c.close();

        return lastResults ;
    }


    public void close() {
        db.close();
    }
}

