package isep.project.care4old.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;

import isep.project.care4old.R;
import isep.project.care4old.dao.DoctorDAO;
import isep.project.care4old.extension.custom_adapter.PatientsListAdapter;

public class DoctorProfileActivity extends AppCompatActivity {

    private DoctorDAO myDoctorDAO ;
    private int myDoctorId;

    private AutoCompleteTextView searchBar = null ;

    private ListView listOfPatients = null ;

    private Button findPatient,deconnexionDoctor ;

    public final static String PROFILE_DOCTOR_TO_PATIENT_PROFILE = "isep.project.care4Old.PROFILE_DOCTOR_TO_PROFILE_PATIENT";

    private View.OnClickListener findPatientListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            if(checkAutoCompletion()) {

                String myPatient = searchBar.getText().toString() ;
                String stringId = " 0";

                StringTokenizer stringTokenizer = new StringTokenizer(myPatient, "-");
                while (stringTokenizer.hasMoreElements()) {
                    stringId = stringTokenizer.nextElement().toString();
                }

                int userId = Integer.valueOf(stringId.substring(1));

                Intent nextActivity = new Intent(DoctorProfileActivity.this, PatientProfileActivity.class);
                int [] extra = {userId,myDoctorId} ;
                nextActivity.putExtra(PROFILE_DOCTOR_TO_PATIENT_PROFILE, extra);
                startActivity(nextActivity);
            }
            else{
                Toast.makeText(DoctorProfileActivity.this, "Entrez le nom d'un patient qui vous est associé", Toast.LENGTH_LONG).show();
                searchBar.getText().clear();
            }
        }
    };

    private View.OnClickListener deconnexionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent nextActivity = new Intent(DoctorProfileActivity.this, AccountConnectionActivity.class);
            startActivity(nextActivity);
        }
    };


    private void getComponents(){
        findPatient = (Button)findViewById(R.id.find_patient);
        deconnexionDoctor = (Button)findViewById(R.id.doctorDeconnexion);
        searchBar = (AutoCompleteTextView)findViewById(R.id.autocomplete_patient);
        listOfPatients = (ListView)findViewById(R.id.liste_de_patients);
    }

    private void getListeners(){
        deconnexionDoctor.setOnClickListener(deconnexionListener);
        findPatient.setOnClickListener(findPatientListener);
    }

    private void getDataFromOtherActivities() {
        Intent intentReceived = getIntent();

        int myUserIdFromConnection = intentReceived.getIntExtra(AccountConnectionActivity.CONNECTION_TO_DOCTOR, -1);
        int myUserIdFromPatient = intentReceived.getIntExtra(PatientProfileActivity.PROFILE_PATIENT_TO_DOCTOR, -1);

        if(myUserIdFromConnection > 0)
            myDoctorId = myUserIdFromConnection;
        else if(myUserIdFromPatient > 0)
            myDoctorId = myUserIdFromPatient;
    }

    private boolean checkAutoCompletion(){
        ArrayList<String> myPatients = myDoctorDAO.getPatients(myDoctorId);
        for(String patient : myPatients){
            if(patient.equals(searchBar.getText().toString()))
                return true;
        }
        return false ;
    }

    private void createPatientListe(){
        ArrayAdapter<String> searchBarAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myDoctorDAO.getPatients(myDoctorId));
        searchBar.setAdapter(searchBarAdapter);

        PatientsListAdapter patientsListAdapter = new PatientsListAdapter(myDoctorDAO.getPatients(myDoctorId), this);
        listOfPatients.setAdapter(patientsListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        myDoctorDAO = new DoctorDAO(this);

        getComponents();
        getListeners();
        getDataFromOtherActivities();
        createPatientListe();
    }
}

