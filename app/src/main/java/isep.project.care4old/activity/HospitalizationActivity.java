package isep.project.care4old.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import isep.project.care4old.R;
import isep.project.care4old.dao.HospDAO;
import isep.project.care4old.extension.custom_adapter.HospitalizationListAdapter;


public class HospitalizationActivity extends AppCompatActivity {

    public final static String HOSPVIEW_TO_HOSPCREATION = "care4old.HOSPVIEW_TO_HOSPCREATION";
    public final static String HOSPVIEW_TO_PATIENTPROFILE = "care4old.HOSPVIEW_TO_PATIENTPROFILE";

    private HospDAO myHospDAO;
    private ListView listOfHosps = null;
    private Button add = null;
    private Button previous = null;
    private int idPatient, idDoctor;

    private View.OnClickListener previousListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            Intent secondActivity = new Intent(HospitalizationActivity.this, PatientProfileActivity.class);
            secondActivity.putExtra(HOSPVIEW_TO_PATIENTPROFILE, new int[]{idPatient, idDoctor});
            startActivity(secondActivity);
        }
    };

    private View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent secondActivity = new Intent(HospitalizationActivity.this, HospitalizationCreationActivity.class);
            secondActivity.putExtra(HOSPVIEW_TO_HOSPCREATION, new int[]{idPatient, idDoctor});
            startActivity(secondActivity);
        }
    };

    public void getComponents(){
        previous = (Button)findViewById(R.id.hosp_retour_profile);
        add = (Button)findViewById(R.id.add_hosp);
        listOfHosps = (ListView)findViewById(R.id.list_of_hosps);
    }

    private void getListeners(){
        previous.setOnClickListener(previousListener);
        add.setOnClickListener(addListener);
    }

    public void getDataFromOtherActivities() {
        Intent intentReceived = getIntent();

        int[] idsFromPatientProfile = intentReceived.getIntArrayExtra(PatientProfileActivity.PROFILE_PATIENT_TO_HOSPITALIZATION);
        int[] idsFromHospCreation = intentReceived.getIntArrayExtra(HospitalizationCreationActivity.HOSPCREATION_TO_HOSPVIEW);

        if(idsFromHospCreation != null) {
            idPatient = idsFromHospCreation[0];
            idDoctor = idsFromHospCreation[1];
        }
        else {
            idPatient = idsFromPatientProfile[0];
            idDoctor = idsFromPatientProfile[1];
        }
    }

    public void createArticleList(){
        HospitalizationListAdapter myHospsAdapter = new HospitalizationListAdapter(myHospDAO.getHosps(idPatient), this);
        listOfHosps.setAdapter(myHospsAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitalization);

        myHospDAO = new HospDAO(this);

        getComponents();
        getListeners();
        getDataFromOtherActivities();
        createArticleList();
    }
}

