package isep.project.care4old.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import isep.project.care4old.R;
import isep.project.care4old.dao.ComorbiditiesDAO;
import isep.project.care4old.dao.HospDAO;
import isep.project.care4old.dao.MedicalCheckDAO;
import isep.project.care4old.dao.ResultTestDAO;
import isep.project.care4old.dao.UserDAO;
import isep.project.care4old.model.User;


public class PatientProfileActivity extends AppCompatActivity {

    private int myUserId ;
    private int idDoctor=0 ;

    private ResultTestDAO resultTestDAO ;
    private UserDAO userDAO ;
    private ComorbiditiesDAO comorbiditiesDAO ;
    private MedicalCheckDAO medicalCheckDAO ;
    private HospDAO hospDAO ;

    private Button toTest,toMedicalCheck,toComorbidities,toHospitalisation,toDoctor,deconnexion ;

    private TextView norton,lawton,katz,mna,fragility,date ;
    private TextView doctor,firstname,lastname,gender,birthday;
    private TextView adress,phones,emergencyPhone;
    private TextView residency,financialHelp,homeHelp;

    private TextView nortonClick,lawtonClick, katzClick, mnaClick, fragilityClick ;

    private TextView height,weight,bmi,vitamine,crp,albumin,dateConsultation ;
    private TextView dateComorbidities,pathologyComorbidties ;
    private TextView dateStartHospilisation, dateEndHospilisation, motifHospitalisation ;

    private String resultTestUser[] ;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public final static String PROFILE_PATIENT_TO_PATIENT_QUESTIONNARY = "isep.project.care4Old.PROFILE_TO_QUESTIONNARY_PATIENT";
    public final static String PROFILE_PATIENT_TO_NORTON = "isep.project.care4Old.PROFILE_TO_NORTON" ;
    public final static String PROFILE_PATIENT_TO_FRAGILITY = "isep.project.care4Old.PROFILE_TO_FRAGILITY" ;
    public final static String PROFILE_PATIENT_TO_MNA = "isep.project.care4Old.PROFILE_TO_MNA" ;
    public final static String PROFILE_PATIENT_TO_LAWTON = "isep.project.care4Old.PROFILE_TO_LAWTON" ;
    public final static String PROFILE_PATIENT_TO_KATZ = "isep.project.care4Old.PROFILE_TO_KATZ" ;
    public final static String PROFILE_PATIENT_TO_COMORBIDITIES = "isep.project.care4Old.PROFILE_TO_COMORBIDITIES" ;
    public final static String PROFILE_PATIENT_TO_MEDICAL_CHECK = "isep.project.care4Old.PROFILE_PATIENT_TO_MEDICAL_CHECKS" ;
    public final static String PROFILE_PATIENT_TO_HOSPITALIZATION = "isep.project.care4Old.PROFILE_PATIENT_TO_HOSPITALIZATION" ;
    public final static String PROFILE_PATIENT_TO_DOCTOR = "isep.project.care4Old.PROFILE_PATIENT_TO_DOCTOR" ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        resultTestDAO = new ResultTestDAO(this) ;
        userDAO = new UserDAO(this) ;
        comorbiditiesDAO = new ComorbiditiesDAO(this);
        medicalCheckDAO = new MedicalCheckDAO(this) ;
        hospDAO = new HospDAO(this);

        getDataFromOtherActivities();
        getComponents();
        displayTextInfos();
        getListener();

    }

    private void getDataFromOtherActivities() {

        Intent intentReceived = getIntent();

        int idFromConnection = intentReceived.getIntExtra(AccountConnectionActivity.CONNECTION_TO_PATIENT, -1);
        int[] idFromQuestionnary = intentReceived.getIntArrayExtra(PatientQuestionnaryActivity.PATIENT_QUESTIONNARY_TO_PROFILE_PATIENT) ;
        int[] idFromNorton = intentReceived.getIntArrayExtra(ConsultNortonActivity.NORTON_TO_PROFILE_PATIENT);
        int[] idFromKatz = intentReceived.getIntArrayExtra(ConsultKatzActivity.KATZ_TO_PROFILE_PATIENT);
        int[] idFromLawton = intentReceived.getIntArrayExtra(ConsultLawtonActivity.LAWTON_TO_PROFILE_PATIENT);
        int[] idFromMna = intentReceived.getIntArrayExtra(ConsultMnaActivity.MNA_TO_PROFILE_PATIENT);
        int[] idFromFragility = intentReceived.getIntArrayExtra(ConsultFragilityActivity.FRAGILITY_TO_PROFILE_PATIENT);
        int[] idFromProfileDoctor = intentReceived.getIntArrayExtra(DoctorProfileActivity.PROFILE_DOCTOR_TO_PATIENT_PROFILE);
        int[] idFromComorbidities = intentReceived.getIntArrayExtra(ComorbiditiesActivity.COMORBIDITIES_TO_PROFILE_PATIENT);
        int[] idFromHospitalization = intentReceived.getIntArrayExtra(HospitalizationActivity.HOSPVIEW_TO_PATIENTPROFILE);
        int[] idFromMedicalCheck = intentReceived.getIntArrayExtra(MedicalCheckActivity.MEDCHECK_TO_PROFILEPATIENT);

        if(idFromConnection > 0){
            myUserId = idFromConnection;
            idDoctor = 0 ;
        }else if(idFromQuestionnary!=null && idFromQuestionnary.length>0) {
            myUserId = idFromQuestionnary[0];
            idDoctor = idFromQuestionnary[1];
        }else if(idFromNorton!=null && idFromNorton.length>0) {
            myUserId = idFromNorton[0];
            idDoctor = idFromNorton[1];
        }else if(idFromKatz!=null && idFromKatz.length>0) {
            myUserId = idFromKatz[0];
            idDoctor = idFromKatz[1];
        }else if(idFromLawton!=null && idFromLawton.length>0) {
            myUserId = idFromLawton[0];
            idDoctor = idFromLawton[1];
        }else if(idFromMna!=null && idFromMna.length>0) {
            myUserId = idFromMna[0];
            idDoctor = idFromMna[1];
        }else if(idFromFragility!=null && idFromFragility.length>0) {
            myUserId = idFromFragility[0];
            idDoctor = idFromFragility[1];
        }else if(idFromProfileDoctor!=null && idFromProfileDoctor.length>0) {
            myUserId = idFromProfileDoctor[0];
            idDoctor = idFromProfileDoctor[1];
        }else if(idFromComorbidities!=null && idFromComorbidities.length>0) {
            myUserId = idFromComorbidities[0];
            idDoctor = idFromComorbidities[1];
        }else if(idFromHospitalization!=null && idFromHospitalization.length>0) {
            myUserId = idFromHospitalization[0];
            idDoctor = idFromHospitalization[1];
        }else if(idFromMedicalCheck!=null && idFromMedicalCheck.length>0) {
            myUserId = idFromMedicalCheck[0];
            idDoctor = idFromMedicalCheck[1];
        }
        
    }

    private void getComponents(){
        doctor = (TextView)findViewById(R.id.profileDoctor);
        firstname = (TextView)findViewById(R.id.profileFirstName);
        lastname = (TextView)findViewById(R.id.profileLastName) ;
        gender = (TextView)findViewById(R.id.profileGender);
        birthday = (TextView)findViewById(R.id.profileBirthday);
        adress = (TextView)findViewById(R.id.profileAdress) ;
        phones = (TextView)findViewById(R.id.profilePhones);
        emergencyPhone = (TextView)findViewById(R.id.profilePhoneEmergency) ;
        residency = (TextView)findViewById(R.id.profileResidence) ;
        financialHelp = (TextView)findViewById(R.id.profileFinance) ;
        homeHelp = (TextView)findViewById(R.id.profileHome);
        katz = (TextView)findViewById(R.id.Resultat_Katz);
        lawton = (TextView)findViewById(R.id.Resultat_Lawton);
        mna = (TextView)findViewById(R.id.Resultat_MNA);
        norton = (TextView)findViewById(R.id.Resultat_Norton);
        fragility = (TextView)findViewById(R.id.Resultat_Fragility);
        date = (TextView)findViewById(R.id.Resultat_Date);
        toTest = (Button)findViewById(R.id.Test_Page);
        katzClick = (TextView)findViewById(R.id.profileKatz);
        lawtonClick = (TextView)findViewById(R.id.profileLawton);
        mnaClick = (TextView)findViewById(R.id.profileMna);
        nortonClick = (TextView)findViewById(R.id.profileNorton);
        fragilityClick = (TextView)findViewById(R.id.profileFragility);
        height = (TextView)findViewById(R.id.profileHeight);
        weight = (TextView)findViewById(R.id.profileWeight);
        bmi = (TextView)findViewById(R.id.profileBMI);
        vitamine = (TextView)findViewById(R.id.profileVitamine);
        crp = (TextView)findViewById(R.id.profileCRP);
        albumin = (TextView)findViewById(R.id.profileAlbumin);
        dateConsultation = (TextView)findViewById(R.id.profileDateConsultation);
        dateComorbidities = (TextView)findViewById(R.id.profileDateComorbidities);
        pathologyComorbidties = (TextView)findViewById(R.id.profilePathologyComorbidities);
        dateStartHospilisation = (TextView)findViewById(R.id.profileDateStartHospitalisation);
        dateEndHospilisation = (TextView)findViewById(R.id.profileDateEndHospitalisation);
        motifHospitalisation = (TextView)findViewById(R.id.profileMotifHospitalisation) ;
        toMedicalCheck = (Button)findViewById(R.id.profileToMedicalCheck);
        toComorbidities = (Button)findViewById(R.id.profileToComorbidities);
        toHospitalisation = (Button)findViewById(R.id.profileToHospitalisation);
        toDoctor = (Button)findViewById(R.id.profileToDoctor);
        deconnexion = (Button)findViewById(R.id.profileDeconnexion);

        if(idDoctor==0){
            toMedicalCheck.setVisibility(View.GONE);
            toComorbidities.setVisibility(View.GONE);
            toHospitalisation.setVisibility(View.GONE);
            toDoctor.setVisibility(View.GONE);
        }else{
            deconnexion.setVisibility(View.GONE);
        }
    }

    private void displayTextInfos(){
        displayTextInfosPersonals();
        displayTextTest();
        displayTextInfosMedical();
        displayTextComorbidities();
        displayTextHospitalisation();
    }


    private void displayTextInfosPersonals(){
        User infoUser = userDAO.getUserToProfile(myUserId);

        doctor.setText(userDAO.getNameDoctor(infoUser.getIdDoctorLinked()));

        firstname.setText(infoUser.getFirstName());
        lastname.setText(infoUser.getFamilyName());
        gender.setText(infoUser.getGender().substring(3));


        String birthdayUser = dateFormat.format(infoUser.getBirthday());
        birthday.setText(birthdayUser);

        String fullAdress = infoUser.getAddress()+", "+infoUser.getZip()+" "+infoUser.getCity() ;
        adress.setText(fullAdress);

        String fullPhones = infoUser.getMobilePhone()+" / "+infoUser.getHomePhone() ;
        phones.setText(fullPhones);
        emergencyPhone.setText(infoUser.getEmergencyPhone());

        residency.setText(infoUser.getResidency());
        financialHelp.setText(infoUser.getFinancialHelp());
        homeHelp.setText(infoUser.getHomeHelp());
    }

    private void displayTextTest(){
        resultTestUser = resultTestDAO.getLastResultTestTotal(myUserId);

        if(resultTestUser[1]!= null && !resultTestUser[1].isEmpty())
            getCommentsTest();

        norton.setText(resultTestUser[0]);
        lawton.setText(resultTestUser[1]);
        fragility.setText(resultTestUser[2]);
        mna.setText(resultTestUser[3]);
        katz.setText(resultTestUser[4]);
        date.setText(resultTestUser[5]);
    }

    private void getCommentsTest(){

        // Norton
        if(Integer.valueOf(resultTestUser[0])>14)
            resultTestUser[0] = resultTestUser[0]+" - risque minimum";
        else
            resultTestUser[0] = resultTestUser[0]+" - risque élevé";

        // Lawton
        if(Integer.valueOf(resultTestUser[1])>4)
            resultTestUser[1] = resultTestUser[1]+" - comportement autonome";
        else
            resultTestUser[1] = resultTestUser[1]+" - comportement dépendant";

        // Fragility
        if(Integer.valueOf(resultTestUser[2])<=8)
            resultTestUser[2] = resultTestUser[2]+" - personne peu fragile" ;
        else if(Integer.valueOf(resultTestUser[2])>8 && Integer.valueOf(resultTestUser[1])<=11)
            resultTestUser[2] = resultTestUser[2]+" - personne fragile" ;
        else
            resultTestUser[2] = resultTestUser[2]+" - personne très fragile" ;

        // MNA
        if(Integer.valueOf(resultTestUser[3])<=7)
            resultTestUser[3] = resultTestUser[3]+" - dénutrition avérée" ;
        else if(Integer.valueOf(resultTestUser[3])>7 && Integer.valueOf(resultTestUser[2])<=11)
            resultTestUser[3] = resultTestUser[3]+" - risque de dénutrition" ;
        else
            resultTestUser[3] = resultTestUser[3]+" - état nutritionnel normal" ;


        // Katz
        if(Double.valueOf(resultTestUser[4])>=3)
            resultTestUser[4] = resultTestUser[4]+" - autonome";
        else
            resultTestUser[4] = resultTestUser[4]+" - dépendant";
    }


    private void displayTextInfosMedical(){

        String[] infosMedical = medicalCheckDAO.getLastMedicalCheck(myUserId);

        if(infosMedical[0]!= null && !infosMedical[0].isEmpty()) {
            dateConsultation.setText(infosMedical[0]);
            height.setText(infosMedical[1]);
            weight.setText(infosMedical[2]);
            albumin.setText(infosMedical[3]);
            crp.setText(infosMedical[4]);
            vitamine.setText(infosMedical[5]);

            DecimalFormat df = new DecimalFormat("0.00");
            double tailleImc = Math.pow(Double.parseDouble(infosMedical[1])/100,2) ;
            double poidsImc = Double.parseDouble(infosMedical[2]) ;
            double imcMath = poidsImc / tailleImc ;
            bmi.setText(df.format(imcMath));
        }
    }

    private void displayTextComorbidities(){

        String[] lastComorbiditie = comorbiditiesDAO.getLastComorbiditie(myUserId);

        if(lastComorbiditie[0]!= null && !lastComorbiditie[0].isEmpty()) {
            pathologyComorbidties.setText(lastComorbiditie[0]);
            dateComorbidities.setText(lastComorbiditie[1]);
        }
    }

    private void displayTextHospitalisation(){

        String[] lastResultsHosp = hospDAO.getLastHosp(myUserId);

        if(lastResultsHosp[0]!= null && !lastResultsHosp[0].isEmpty()) {
            String startHosp = lastResultsHosp[0].substring(0,lastResultsHosp[0].length()-5)+" - ";
            dateStartHospilisation.setText(startHosp);
            dateEndHospilisation.setText(lastResultsHosp[1]);
            motifHospitalisation.setText(lastResultsHosp[2]);
        }
    }

    private void getListener(){
        toTest.setOnClickListener(toPageTestListener);
        katzClick.setOnClickListener(toKatzListener);
        nortonClick.setOnClickListener(toNortonListener);
        fragilityClick.setOnClickListener(toFragilityListener);
        mnaClick.setOnClickListener(toMnaListener);
        lawtonClick.setOnClickListener(toLawtonListener);
        toMedicalCheck.setOnClickListener(toMedicalCheckListener);
        toComorbidities.setOnClickListener(toComorbiditiesListener);
        toHospitalisation.setOnClickListener(toHospitalisationListener);
        toDoctor.setOnClickListener(toDoctorListener);
        deconnexion.setOnClickListener(deconnexionListener);
    }

    private View.OnClickListener toNortonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            if(resultTestUser[0]== null || resultTestUser[0].isEmpty()){
                Toast.makeText(PatientProfileActivity.this, "Vous n'avez encore aucun test Norton pour les consulter.", Toast.LENGTH_LONG).show() ;
            }else {
                String extra[] = {String.valueOf(myUserId),resultTestUser[5],String.valueOf(idDoctor)};

                Intent nextActivity = new Intent(PatientProfileActivity.this, ConsultNortonActivity.class);
                nextActivity.putExtra(PROFILE_PATIENT_TO_NORTON, extra);
                startActivity(nextActivity);
            }
        }
    };

    private View.OnClickListener toFragilityListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            if(resultTestUser[1]== null || resultTestUser[1].isEmpty()){
                Toast.makeText(PatientProfileActivity.this, "Vous n'avez encore aucun test Fragility pour les consulter.", Toast.LENGTH_LONG).show() ;
            }else {
                String extra[] = {String.valueOf(myUserId),resultTestUser[5],String.valueOf(idDoctor)};

                Intent nextActivity = new Intent(PatientProfileActivity.this, ConsultFragilityActivity.class);
                nextActivity.putExtra(PROFILE_PATIENT_TO_FRAGILITY, extra);
                startActivity(nextActivity);
            }
        }
    };

    private View.OnClickListener toMnaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            if(resultTestUser[1]== null || resultTestUser[1].isEmpty()){
                Toast.makeText(PatientProfileActivity.this, "Vous n'avez encore aucun test MNA pour les consulter.", Toast.LENGTH_LONG).show() ;
            }else {
                String extra[] = {String.valueOf(myUserId),resultTestUser[5],String.valueOf(idDoctor)};

                Intent nextActivity = new Intent(PatientProfileActivity.this, ConsultMnaActivity.class);
                nextActivity.putExtra(PROFILE_PATIENT_TO_MNA, extra);
                startActivity(nextActivity);
            }
        }
    };

    private View.OnClickListener toLawtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            if(resultTestUser[3]== null || resultTestUser[3].isEmpty()){
                Toast.makeText(PatientProfileActivity.this, "Vous n'avez encore aucun test Lawton pour les consulter.", Toast.LENGTH_LONG).show() ;
            }else {
                String extra[] = {String.valueOf(myUserId),resultTestUser[5],String.valueOf(idDoctor)};

                Intent nextActivity = new Intent(PatientProfileActivity.this, ConsultLawtonActivity.class);
                nextActivity.putExtra(PROFILE_PATIENT_TO_LAWTON, extra);
                startActivity(nextActivity);
            }
        }
    };

    private View.OnClickListener toKatzListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            if(resultTestUser[4]== null || resultTestUser[4].isEmpty()){
                Toast.makeText(PatientProfileActivity.this, "Vous n'avez encore aucun test Katz pour les consulter.", Toast.LENGTH_LONG).show() ;
            }else {
                String extra[] = {String.valueOf(myUserId),resultTestUser[5],String.valueOf(idDoctor)};

                Intent nextActivity = new Intent(PatientProfileActivity.this, ConsultKatzActivity.class);
                nextActivity.putExtra(PROFILE_PATIENT_TO_KATZ, extra);
                startActivity(nextActivity);
            }
        }
    };

    private View.OnClickListener toPageTestListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            Date date = new Date();
            String current = dateFormat.format(date);

            if(current.equals(resultTestUser[5])){
                Toast.makeText(PatientProfileActivity.this, "Vous avez déjà répondu à un test aujourd'hui, un seul test par jour est autorisé.", Toast.LENGTH_LONG).show() ;
            }else {
                Intent nextActivity = new Intent(PatientProfileActivity.this, PatientQuestionnaryActivity.class);
                int [] extra = {myUserId,idDoctor} ;
                nextActivity.putExtra(PROFILE_PATIENT_TO_PATIENT_QUESTIONNARY, extra);
                startActivity(nextActivity);
            }
        }
    };

    private View.OnClickListener toMedicalCheckListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            Intent nextActivity = new Intent(PatientProfileActivity.this, MedicalCheckActivity.class);
            int [] extra = {myUserId,idDoctor} ;
            nextActivity.putExtra(PROFILE_PATIENT_TO_MEDICAL_CHECK, extra);
            startActivity(nextActivity);
        }
    };

    private View.OnClickListener toComorbiditiesListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            Intent nextActivity = new Intent(PatientProfileActivity.this, ComorbiditiesActivity.class);
            int [] extra = {myUserId,idDoctor} ;
            nextActivity.putExtra(PROFILE_PATIENT_TO_COMORBIDITIES, extra);
            startActivity(nextActivity);
        }
    };

    private View.OnClickListener toHospitalisationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            Intent nextActivity = new Intent(PatientProfileActivity.this, HospitalizationActivity.class);
            int [] extra = {myUserId,idDoctor} ;
            nextActivity.putExtra(PROFILE_PATIENT_TO_HOSPITALIZATION, extra);
            startActivity(nextActivity);
        }
    };

    private View.OnClickListener toDoctorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            Intent nextActivity = new Intent(PatientProfileActivity.this, DoctorProfileActivity.class);
            nextActivity.putExtra(PROFILE_PATIENT_TO_DOCTOR, idDoctor);
            startActivity(nextActivity);
        }
    };

    private View.OnClickListener deconnexionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent nextActivity = new Intent(PatientProfileActivity.this, AccountConnectionActivity.class);
            startActivity(nextActivity);
        }
    };
}
