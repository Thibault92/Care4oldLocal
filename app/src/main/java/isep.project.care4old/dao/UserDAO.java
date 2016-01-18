package isep.project.care4old.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import isep.project.care4old.database.DatabaseHandler;
import isep.project.care4old.database.MyDatabase;
import isep.project.care4old.model.User;

public class UserDAO implements MyDatabase {

    protected DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
    protected SQLiteDatabase myDatabase;

    public UserDAO() {

    }

    public UserDAO(Context context) {
        DatabaseHandler myDatabaseHandler = new DatabaseHandler(context);
        myDatabase = myDatabaseHandler.getWritableDatabase();
    }

    public void addUser(User newUser) {

        String birthday = dateFormat.format(newUser.getBirthday());

        ContentValues myUserSet = new ContentValues();

        myUserSet.put(USER_ID_DOCTOR, newUser.getIdDoctorLinked());
        myUserSet.put(USER_FAMILYNAME, newUser.getFamilyName());
        myUserSet.put(USER_FIRSTNAME, newUser.getFirstName());
        myUserSet.put(USER_IDENTIFIANT, newUser.getIdentifiant());
        myUserSet.put(USER_PASSWORD, newUser.getPassword());
        myUserSet.put(USER_BIRTHDAY, birthday);
        myUserSet.put(USER_ADDRESS, newUser.getAddress());
        myUserSet.put(USER_ZIP, newUser.getZip());
        myUserSet.put(USER_CITY, newUser.getCity());
        myUserSet.put(USER_HOMEPHONE, newUser.getHomePhone());
        myUserSet.put(USER_MOBILEPHONE, newUser.getMobilePhone());
        myUserSet.put(USER_EMERGENCYPHONE, newUser.getEmergencyPhone());
        myUserSet.put(USER_GENDER, newUser.getGender());
        myUserSet.put(USER_STATUS, newUser.getStatus());
        myUserSet.put(USER_RESIDENCY, newUser.getResidency());
        myUserSet.put(USER_IS_FINANCIAL_HELP, newUser.getFinancialHelp());
        myUserSet.put(USER_IS_HOME_HELP, newUser.getHomeHelp());
        myUserSet.put(USER_TYPE, newUser.getUserType());

        myDatabase.insert(USER_TABLE_NAME, null, myUserSet);
    }

    public boolean connectUser(User userToConnect) {
        Cursor c = myDatabase.rawQuery("SELECT " + USER_KEY + " FROM " + USER_TABLE_NAME + " WHERE " +
                                        USER_IDENTIFIANT + " = ? AND " + USER_PASSWORD + " = ?",
                                        new String[]{userToConnect.getIdentifiant(), userToConnect.getPassword()});
        c.moveToFirst();
        c.close();
        return c.getCount() > 0;
    }

    public boolean checkUser(String userToCheck) {
        Cursor c = myDatabase.rawQuery("SELECT " + USER_KEY + " FROM " + USER_TABLE_NAME + " WHERE " +
                                        USER_IDENTIFIANT + " = ?",
                                        new String[]{userToCheck});
        c.moveToFirst();
        c.close();
        return c.getCount() > 0;
    }

    public String getUserType(String identifiant) {
        Cursor c = myDatabase.rawQuery("SELECT " + USER_TYPE + " FROM " + USER_TABLE_NAME + " WHERE " +
                        USER_IDENTIFIANT + " = ?",
                new String[]{identifiant});
        c.moveToFirst();
        String userType = c.getString(0);
        c.close();
        return userType;
    }

    public int getIdUser(String userToGet) {
        Cursor c = myDatabase.rawQuery("SELECT " + USER_KEY + " FROM " + USER_TABLE_NAME + " WHERE " +
                        USER_IDENTIFIANT + " = ?",
                new String[]{userToGet});
        c.moveToFirst();
        int idUser = c.getInt(0);
        c.close();
        return idUser;
    }

    public String getNameDoctor(int idDoctor) {
        String namesDoctor ;

        Cursor c = myDatabase.rawQuery("SELECT "+USER_FIRSTNAME+", "+USER_FAMILYNAME+" FROM "+USER_TABLE_NAME+" WHERE " +
                        USER_KEY+ " = ?", new String[]{String.valueOf(idDoctor)});
        c.moveToFirst();
            namesDoctor = "Dr. "+c.getString(0)+" "+c.getString(1) ;
        c.close();

        return namesDoctor;
    }

    public User getUserToProfile(int userToGet) {

        User getUser = new User();

        Cursor c = myDatabase.rawQuery("SELECT "+USER_ID_DOCTOR+", "+USER_FIRSTNAME+", "+USER_FAMILYNAME+", "+USER_GENDER+
                        ", "+USER_BIRTHDAY+", "+USER_ADDRESS+", "+USER_ZIP+", "+USER_CITY+", "+USER_MOBILEPHONE+", "+USER_HOMEPHONE+
                        ", "+USER_EMERGENCYPHONE+", "+USER_RESIDENCY+", "+USER_IS_HOME_HELP+", "+USER_IS_FINANCIAL_HELP+
                        " FROM " + USER_TABLE_NAME + " WHERE " +USER_KEY+ " = ?", new String[]{String.valueOf(userToGet)});

        c.moveToFirst();
                getUser.setIdDoctorLinked(c.getInt(0));
                getUser.setFirstName(c.getString(1));
                getUser.setFamilyName(c.getString(2));
                getUser.setGender(c.getString(3));
        try {
            Date date = dateFormat.parse(c.getString(4)) ;
            getUser.setBirthday(date);
        } catch (ParseException e) {
            Log.v("LOG DATE",e.getMessage());
        }
                getUser.setAddress(c.getString(5));
                getUser.setZip(Integer.valueOf(c.getString(6)));
                getUser.setCity(c.getString(7));
                getUser.setMobilePhone(c.getString(8));
                getUser.setHomePhone(c.getString(9));
                getUser.setEmergencyPhone(c.getString(10));
                getUser.setResidency(c.getString(11));
                getUser.setHomeHelp(c.getString(12));
                getUser.setFinancialHelp(c.getString(13));

        c.close();

        return getUser;
    }

    public void close() {
        myDatabase.close();
    }
}
