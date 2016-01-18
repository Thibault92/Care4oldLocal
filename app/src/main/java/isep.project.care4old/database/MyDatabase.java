package isep.project.care4old.database;


public interface MyDatabase {

    String DATABASE_NAME = "database.db" ;
    int DATABASE_VERSION = 15 ;

    String KATZ_TABLE_NAME = "katz" ;
    String KATZ_ID = "id" ;
    String KATZ_DATE = "date" ;
    String KATZ_HYGIENE = "hygiene" ;
    String KATZ_DRESSING = "dressing" ;
    String KATZ_BATHROOM = "bathroom" ;
    String KATZ_LOCOMOTION = "locomotion" ;
    String KATZ_CONTINENCE = "continence" ;
    String KATZ_LUNCH = "lunch" ;
    String KATZ_TOTAL = "total" ;

    String FRAGILITY_TABLE_NAME = "fragility" ;
    String FRAGILITY_ID = "id" ;
    String FRAGILITY_DATE = "date" ;
    String FRAGILITY_HOME = "home" ;
    String FRAGILITY_DRUGS = "drugs" ;
    String FRAGILITY_MOOD = "mood" ;
    String FRAGILITY_PERCEPTION = "perception" ;
    String FRAGILITY_FALL = "fall" ;
    String FRAGILITY_RESPONSABILITY = "responsability" ;
    String FRAGILITY_ILLNESS = "illness" ;
    String FRAGILITY_MOBILITY = "mobility" ;
    String FRAGILITY_CONTINENCE = "continence" ;
    String FRAGILITY_FEED = "feed" ;
    String FRAGILITY_COGNITIVE = "cognitive" ;
    String FRAGILITY_TOTAL = "total" ;

    String LAWTON_TABLE_NAME = "lawton" ;
    String LAWTON_ID= "id" ;
    String LAWTON_DATE = "date" ;
    String LAWTON_PHONE = "phone" ;
    String LAWTON_GROWSHOP = "growshop" ;
    String LAWTON_COOK = "cook" ;
    String LAWTON_CLEAN = "clean" ;
    String LAWTON_LAUNDRY = "laundry" ;
    String LAWTON_TRANSPORT = "transport" ;
    String LAWTON_DRUGS = "drugs" ;
    String LAWTON_MONEY = "money" ;
    String LAWTON_TOTAL = "total" ;

    String MNA_TABLE_NAME = "mna" ;
    String MNA_ID = "id" ;
    String MNA_DATE = "date" ;
    String MNA_APPETITE = "appetite" ;
    String MNA_LOOSE = "loose_weight" ;
    String MNA_MOTRICITY = "motricity" ;
    String MNA_ACUTE = "acute" ;
    String MNA_NEURO = "neuro" ;
    String MNA_BMI = "bmi" ;
    String MNA_TOTAL = "total" ;

    String NORTON_TABLE_NAME = "norton" ;
    String NORTON_ID = "id" ;
    String NORTON_DATE = "date" ;
    String NORTON_GLOBAL = "global" ;
    String NORTON_AGILITY = "agility" ;
    String NORTON_PSYCHIC = "psychic" ;
    String NORTON_INCONTINENCE= "incontinence" ;
    String NORTON_MOBILITY = "mobility" ;
    String NORTON_TOTAL = "total" ;

    String USER_TABLE_NAME = "personals";
    String USER_KEY = "id";
    String USER_ID_DOCTOR = "id_doctor";
    String USER_FAMILYNAME = "name";
    String USER_FIRSTNAME = "first_name";
    String USER_IDENTIFIANT = "identifiant";
    String USER_PASSWORD = "password";
    String USER_BIRTHDAY = "birthday";
    String USER_ADDRESS = "address";
    String USER_ZIP = "zip";
    String USER_CITY = "city";
    String USER_HOMEPHONE = "phone";
    String USER_MOBILEPHONE = "mobile";
    String USER_EMERGENCYPHONE = "emergency";
    String USER_GENDER = "gender";
    String USER_STATUS = "status";
    String USER_RESIDENCY = "residency";
    String USER_IS_FINANCIAL_HELP = "is_financial_help";
    String USER_IS_HOME_HELP = "us_home_help";
    String USER_TYPE = "type";

    String RESULT_TEST_TABLE_NAME = "result" ;
    String RESULT_TEST_ID = "id" ;
    String RESULT_TEST_DATE = "date" ;
    String RESULT_NORTON_TOTAL = "norton_total" ;
    String RESULT_KATZ_TOTAL = "katz_total" ;
    String RESULT_MNA_TOTAL = "mna_total" ;
    String RESULT_LAWTON_TOTAL = "lawton_total" ;
    String RESULT_FRAGILITY_TOTAL = "fragility_total" ;

    String COMORBIDITIES_TABLE_NAME = "comorbidities" ;
    String COMORBIDITIES_ID = "id" ;
    String COMORBIDITIES_DATE = "date" ;
    String COMORBIDITIES_PATHOLOGY = "pathology" ;

    String HOSPITALIZATION_TABLE_NAME = "hospitalization";
    String HOSPITALIZATION_KEY = "id";
    String HOSPITALIZATION_ID_USER = "id_patient";
    String HOSPITALIZATION_START = "start";
    String HOSPITALIZATION_END = "end";
    String HOSPITALIZATION_MOTIF = "reason";

    String MEDICAL_CHECK_TABLE_NAME = "medical_check";
    String MEDICAL_CHECK_KEY = "id";
    String MEDICAL_CHECK_ID_USER = "id_patient";
    String MEDICAL_CHECK_DATE  = "date";
    String MEDICAL_CHECK_HEIGHT = "taille";
    String MEDICAL_CHECK_WEIGHT = "poids";
    String MEDICAL_CHECK_MNA = "mna";
    String MEDICAL_CHECK_ALBUMINEMIE = "albuminemie";
    String MEDICAL_CHECK_CRP = "crp";
    String MEDICAL_CHECK_VITAMINE = "vitamined";
    String MEDICAL_CHECK_DIAGNOSTIC = "diagnostic";

}
