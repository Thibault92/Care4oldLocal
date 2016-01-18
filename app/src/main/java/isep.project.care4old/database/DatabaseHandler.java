package isep.project.care4old.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper implements MyDatabase {

    public DatabaseHandler(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String KATZ_TABLE_CREATE = "CREATE TABLE " + KATZ_TABLE_NAME + " (" +
            KATZ_ID + " INTEGER, " +
            KATZ_DATE + " DATE, " +
            KATZ_HYGIENE+ " REAL, " +
            KATZ_DRESSING+ " REAL, " +
            KATZ_BATHROOM+ " REAL, " +
            KATZ_LOCOMOTION+ " REAL, " +
            KATZ_CONTINENCE+ " REAL, " +
            KATZ_LUNCH+ " REAL, " +
            KATZ_TOTAL + " REAL);";

    public static final String KATZ_DROP_TABLE = "DROP TABLE IF EXISTS " + KATZ_TABLE_NAME + ";" ;


    public static final String FRAGILITY_TABLE_CREATE = "CREATE TABLE " + FRAGILITY_TABLE_NAME + " (" +
            FRAGILITY_ID + " INTEGER, " +
            FRAGILITY_DATE + " DATE, " +
            FRAGILITY_HOME+ " INTEGER, " +
            FRAGILITY_DRUGS+ " INTEGER, " +
            FRAGILITY_MOOD+ " INTEGER, " +
            FRAGILITY_PERCEPTION+ " INTEGER, " +
            FRAGILITY_FALL+ " INTEGER, " +
            FRAGILITY_RESPONSABILITY+ " INTEGER, " +
            FRAGILITY_ILLNESS+ " INTEGER, " +
            FRAGILITY_MOBILITY+ " INTEGER, " +
            FRAGILITY_CONTINENCE+ " INTEGER, " +
            FRAGILITY_FEED+ " INTEGER, " +
            FRAGILITY_COGNITIVE+ " INTEGER, " +
            FRAGILITY_TOTAL + " INTEGER);";

    public static final String FRAGILITY_DROP_TABLE = "DROP TABLE IF EXISTS " + FRAGILITY_TABLE_NAME + ";" ;

    public static final String LAWTON_TABLE_CREATE = "CREATE TABLE " + LAWTON_TABLE_NAME + " (" +
            LAWTON_ID + " INTEGER, " +
            LAWTON_DATE + " DATE, " +
            LAWTON_PHONE+ " INTEGER, " +
            LAWTON_GROWSHOP+ " INTEGER, " +
            LAWTON_COOK+ " INTEGER, " +
            LAWTON_CLEAN+ " INTEGER, " +
            LAWTON_LAUNDRY+ " INTEGER, " +
            LAWTON_TRANSPORT+ " INTEGER, " +
            LAWTON_DRUGS+ " INTEGER, " +
            LAWTON_MONEY+ " INTEGER, " +
            LAWTON_TOTAL + " INTEGER);";

    public static final String LAWTON_DROP_TABLE = "DROP TABLE IF EXISTS " + LAWTON_TABLE_NAME + ";" ;

    public static final String NORTON_TABLE_CREATE = "CREATE TABLE " + NORTON_TABLE_NAME + " (" +
            NORTON_ID + " INTEGER, " +
            NORTON_DATE + " DATE, " +
            NORTON_GLOBAL+ " INTEGER, " +
            NORTON_AGILITY+ " INTEGER, " +
            NORTON_PSYCHIC+ " INTEGER, " +
            NORTON_INCONTINENCE+ " INTEGER, " +
            NORTON_MOBILITY+ " INTEGER, " +
            NORTON_TOTAL + " INTEGER);";

    public static final String NORTON_DROP_TABLE = "DROP TABLE IF EXISTS " + NORTON_TABLE_NAME + ";" ;

    public static final String MNA_TABLE_CREATE = "CREATE TABLE " + MNA_TABLE_NAME + " (" +
            MNA_ID + " INTEGER, " +
            MNA_DATE + " DATE, " +
            MNA_APPETITE+ " INTEGER, " +
            MNA_LOOSE+ " INTEGER, " +
            MNA_MOTRICITY+ " INTEGER, " +
            MNA_ACUTE+ " INTEGER, " +
            MNA_NEURO+ " INTEGER, " +
            MNA_BMI+ " INTEGER, " +
            MNA_TOTAL + " INTEGER);";

    public static final String MNA_DROP_TABLE = "DROP TABLE IF EXISTS " + MNA_TABLE_NAME + ";" ;

    public static final String USER_TABLE_CREATE = "CREATE TABLE " + USER_TABLE_NAME + " (" +
            USER_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_ID_DOCTOR + " INTEGER, " +
            USER_FAMILYNAME + " TEXT, " +
            USER_FIRSTNAME + " TEXT, " +
            USER_IDENTIFIANT + " TEXT, " +
            USER_PASSWORD + " TEXT, " +
            USER_BIRTHDAY + " DATE, " +
            USER_ADDRESS + " TEXT, " +
            USER_ZIP + " INTEGER, " +
            USER_CITY + " TEXT, " +
            USER_HOMEPHONE + " TEXT, " +
            USER_MOBILEPHONE + " TEXT, " +
            USER_EMERGENCYPHONE + " TEXT, " +
            USER_GENDER + " TEXT, " +
            USER_STATUS + " TEXT, " +
            USER_RESIDENCY + " TEXT, " +
            USER_IS_FINANCIAL_HELP + " INTEGER, " +
            USER_IS_HOME_HELP + " INTEGER, " +
            USER_TYPE + " TEXT);";

    public static final String USER_TABLE_DROP =  "DROP TABLE IF EXISTS " +USER_TABLE_NAME + ";";

    public static final String RESULT_TEST_TABLE_CREATE = "CREATE TABLE " +RESULT_TEST_TABLE_NAME+ " (" +
            RESULT_TEST_ID + " INTEGER, " +
            RESULT_TEST_DATE + " DATE, " +
            RESULT_NORTON_TOTAL+ " INTEGER, " +
            RESULT_KATZ_TOTAL+ " INTEGER, " +
            RESULT_MNA_TOTAL+ " INTEGER, " +
            RESULT_LAWTON_TOTAL+ " INTEGER, " +
            RESULT_FRAGILITY_TOTAL+ " INTEGER);";

    public static final String RESULT_TEST_TABLE_DROP =  "DROP TABLE IF EXISTS " +RESULT_TEST_TABLE_NAME+ ";";

    public static final String HOSPITALIZATION_TABLE_CREATE = "CREATE TABLE " + HOSPITALIZATION_TABLE_NAME + " (" +
            HOSPITALIZATION_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            HOSPITALIZATION_ID_USER + " INTEGER, " +
            HOSPITALIZATION_START + " DATE, " +
            HOSPITALIZATION_END + " DATE, " +
            HOSPITALIZATION_MOTIF + " TEXT);";

    public static final String HOSPITALIZATION_TABLE_DROP =  "DROP TABLE IF EXISTS " + HOSPITALIZATION_TABLE_NAME + ";";

    public static final String MEDICAL_CHECK_TABLE_CREATE = "CREATE TABLE " + MEDICAL_CHECK_TABLE_NAME + " (" +
            MEDICAL_CHECK_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MEDICAL_CHECK_ID_USER + " INTEGER, " +
            MEDICAL_CHECK_DATE + " DATE, " +
            MEDICAL_CHECK_HEIGHT + " INTEGER, " +
            MEDICAL_CHECK_WEIGHT + " INTEGER, " +
            MEDICAL_CHECK_MNA + " INTEGER, " +
            MEDICAL_CHECK_ALBUMINEMIE + " INTEGER, " +
            MEDICAL_CHECK_CRP + " INTEGER, " +
            MEDICAL_CHECK_VITAMINE + " INTEGER, " +
            MEDICAL_CHECK_DIAGNOSTIC + " TEXT);";

    public static final String MEDICAL_CHECK_TABLE_DROP = "DROP TABLE IF EXISTS " + MEDICAL_CHECK_TABLE_NAME + ";";

    public static final String COMORBIDITIES_TABLE_CREATE = "CREATE TABLE " + COMORBIDITIES_TABLE_NAME + " (" +
            COMORBIDITIES_ID + " INTEGER, " +
            COMORBIDITIES_DATE + " DATE, " +
            COMORBIDITIES_PATHOLOGY + " TEXT);";

    public static final String COMORBIDITIES_TABLE_DROP = "DROP TABLE IF EXISTS " + COMORBIDITIES_TABLE_NAME + ";";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(KATZ_TABLE_CREATE);
        db.execSQL(FRAGILITY_TABLE_CREATE);
        db.execSQL(LAWTON_TABLE_CREATE);
        db.execSQL(NORTON_TABLE_CREATE);
        db.execSQL(MNA_TABLE_CREATE);
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(RESULT_TEST_TABLE_CREATE);
        db.execSQL(HOSPITALIZATION_TABLE_CREATE);
        db.execSQL(MEDICAL_CHECK_TABLE_CREATE);
        db.execSQL(COMORBIDITIES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL(KATZ_DROP_TABLE);
        db.execSQL(FRAGILITY_DROP_TABLE);
        db.execSQL(LAWTON_DROP_TABLE);
        db.execSQL(NORTON_DROP_TABLE);
        db.execSQL(MNA_DROP_TABLE);
        db.execSQL(USER_TABLE_DROP);
        db.execSQL(RESULT_TEST_TABLE_DROP);
        db.execSQL(HOSPITALIZATION_TABLE_DROP);
        db.execSQL(MEDICAL_CHECK_TABLE_DROP);
        db.execSQL(COMORBIDITIES_TABLE_DROP);

        onCreate(db);
    }

}



