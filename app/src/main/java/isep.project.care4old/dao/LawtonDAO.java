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
import isep.project.care4old.model.Lawton;

public class LawtonDAO implements MyDatabase{

    private SQLiteDatabase db;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public LawtonDAO(Context context) {
        DatabaseHandler dbHelper = new DatabaseHandler(context);
        db = dbHelper.getWritableDatabase();
    }

    public void ajouter(Lawton l) {

        Date date = new Date();
        String current = dateFormat.format(date);

        ContentValues value = new ContentValues();
        value.put(LAWTON_ID, l.getId());
        value.put(LAWTON_DATE, current);
        value.put(LAWTON_PHONE, l.getPhone());
        value.put(LAWTON_GROWSHOP, l.getGrowshop());
        value.put(LAWTON_COOK, l.getCook());
        value.put(LAWTON_CLEAN, l.getClean());
        value.put(LAWTON_LAUNDRY, l.getLaundry());
        value.put(LAWTON_TRANSPORT, l.getTransport());
        value.put(LAWTON_DRUGS, l.getDrugs());
        value.put(LAWTON_MONEY, l.getMoney());
        value.put(LAWTON_TOTAL, l.getTotal());

        db.insert(LAWTON_TABLE_NAME, null, value);
    }

    public ArrayList<Lawton> getLawtonTestById(int id) {

        ArrayList<Lawton> listLawton = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + LAWTON_TABLE_NAME+ " WHERE " + LAWTON_ID + " = ? ORDER BY "+LAWTON_DATE+" DESC ", new String[]{String.valueOf(id)});

        while(c.moveToNext()){

            Lawton lawton = new Lawton(id);

            lawton.setId(c.getInt(0));
            try {
                Date date = dateFormat.parse(c.getString(1)) ;
                lawton.setDate(date);
            } catch (ParseException e) {
                Log.v("LOG DATE", e.getMessage());
            }
            lawton.setPhone(c.getInt(2));
            lawton.setGrowshop(c.getInt(3));
            lawton.setCook(c.getInt(4));
            lawton.setClean(c.getInt(5));
            lawton.setLaundry(c.getInt(6));
            lawton.setTransport(c.getInt(7));
            lawton.setDrugs(c.getInt(8));
            lawton.setMoney(c.getInt(9));
            lawton.setTotal(c.getInt(10));

            listLawton.add(lawton);
        }

        c.close();

        return listLawton ;
    }

    public Lawton getResultLawton(int id,String date){

        Lawton lawton = new Lawton(id);

        Cursor c = db.rawQuery("SELECT * FROM " + LAWTON_TABLE_NAME +
                " WHERE " + LAWTON_ID + " = ? AND " + LAWTON_DATE + " = ?", new String[]{String.valueOf(id), date});

        c.moveToFirst();

        try {
            Date dateLawton = dateFormat.parse(c.getString(1)) ;
            lawton.setDate(dateLawton);
        } catch (ParseException e) {
            Log.v("LOG DATE", e.getMessage());
        }

        lawton.setPhone(c.getInt(2));
        lawton.setGrowshop(c.getInt(3));
        lawton.setCook(c.getInt(4));
        lawton.setClean(c.getInt(5));
        lawton.setLaundry(c.getInt(6));
        lawton.setTransport(c.getInt(7));
        lawton.setDrugs(c.getInt(8));
        lawton.setMoney(c.getInt(9));
        lawton.setTotal(c.getInt(10));

        c.close();

        return lawton ;
    }

    public void close() {
        db.close();
    }

}

