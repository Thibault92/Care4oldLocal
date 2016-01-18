package isep.project.care4old.dao;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import isep.project.care4old.database.DatabaseHandler;
import isep.project.care4old.database.MyDatabase;

public class DoctorDAO extends UserDAO implements MyDatabase {

    public DoctorDAO(Context context) {
        DatabaseHandler myDatabaseHandler = new DatabaseHandler(context);
        myDatabase = myDatabaseHandler.getWritableDatabase();
    }

    public ArrayList<String> getPatients(int idDoctor) {
        ArrayList<String> myPatients = new ArrayList<>();
        Cursor c = myDatabase.rawQuery("SELECT " + USER_FAMILYNAME + ", " + USER_FIRSTNAME + ", " + USER_ZIP + ", "+USER_KEY+" FROM " + USER_TABLE_NAME + " WHERE " +
                USER_ID_DOCTOR + " = ?", new String[]{String.valueOf(idDoctor)});
        while(c.moveToNext())
            myPatients.add(c.getString(0) + " " + c.getString(1) + " " + c.getString(2)+ " - "+c.getInt(3));
        c.close();

        return myPatients;
    }
}
