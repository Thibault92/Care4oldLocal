package isep.project.care4old.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import isep.project.care4old.R;
import isep.project.care4old.dao.MedicalCheckDAO;
import isep.project.care4old.extension.custom_adapter.MedicalCheckListAdapter;

public class MedicalCheckActivity extends AppCompatActivity {

    public final static String MEDCHECK_TO_MEDCHECKCREATION = "care4old.activity.MEDCHECK_TO_MEDCHECKCREATION";
    public final static String MEDCHECK_TO_PROFILEPATIENT = "care4old.activity.MEDCHECK_TO_PROFILEPATIENT ";

    private MedicalCheckDAO myMedCheckDAO;
    private ListView listOfMedicalChecks = null;
    private Button add, back;
    private int myPatientId;
    private int myDoctorId;

    private View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent secondActivity = new Intent(MedicalCheckActivity.this, MedicalCheckCreationActivity.class);
            secondActivity.putExtra(MEDCHECK_TO_MEDCHECKCREATION, new int[]{myPatientId, myDoctorId});
            startActivity(secondActivity);
        }
    };

    private View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent secondActivity = new Intent(MedicalCheckActivity.this, PatientProfileActivity.class);
            secondActivity.putExtra(MEDCHECK_TO_PROFILEPATIENT , new int[]{myPatientId, myDoctorId});
            startActivity(secondActivity);
        }
    };

    public void getComponents(){
        add = (Button)findViewById(R.id.add_medcheck);
        back = (Button)findViewById(R.id.retour_profile);
        listOfMedicalChecks = (ListView)findViewById(R.id.list_of_medchecks);
    }

    private void getListeners(){
        back.setOnClickListener(backListener);
        add.setOnClickListener(addListener);
    }

    public void getDataFromOtherActivities() {
        Intent intentReceived = getIntent();

        int[] myPatientIdFromPatientProfile = intentReceived.getIntArrayExtra(PatientProfileActivity.PROFILE_PATIENT_TO_MEDICAL_CHECK);
        int[] myPatientIdFromMedCheckCreation = intentReceived.getIntArrayExtra(MedicalCheckCreationActivity.MEDCHECKCREATION_TO_MEDCHECK);
        int[] myPatientIdFromConsultMedCheck = intentReceived.getIntArrayExtra(ConsultMedicalCheckActivity.MEDCONSULT_TO_MEDCHECK);

        if(myPatientIdFromMedCheckCreation != null) {
            myPatientId = myPatientIdFromMedCheckCreation[0];
            myDoctorId = myPatientIdFromMedCheckCreation[1];
        }
        else if(myPatientIdFromPatientProfile != null){
            myPatientId = myPatientIdFromPatientProfile[0];
            myDoctorId = myPatientIdFromPatientProfile[1];
        }
        else if(myPatientIdFromConsultMedCheck != null){
            myPatientId = myPatientIdFromConsultMedCheck[0];
            myDoctorId = myPatientIdFromConsultMedCheck[1];
        }
    }

    public void createMedCheckList(){
        MedicalCheckListAdapter myMedChecksAdapter = new MedicalCheckListAdapter(myPatientId, myDoctorId, myMedCheckDAO.getMedicalChecks(myPatientId), this);
        listOfMedicalChecks.setAdapter(myMedChecksAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_check);

        myMedCheckDAO = new MedicalCheckDAO(this);

        getComponents();
        getListeners();
        getDataFromOtherActivities();
        createMedCheckList();
    }
}

