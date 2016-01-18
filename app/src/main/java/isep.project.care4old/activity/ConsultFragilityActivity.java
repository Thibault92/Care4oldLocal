package isep.project.care4old.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import isep.project.care4old.R;
import isep.project.care4old.dao.FragilityDAO;
import isep.project.care4old.extension.custom_adapter.FragilityListAdapter;
import isep.project.care4old.model.Fragility;

public class ConsultFragilityActivity extends AppCompatActivity {

    private int myUserId ;
    private String dateFragilityTest ;
    private int idDoctor ;

    private FragilityDAO FragilityDAO ;

    private Button backProfile ;
    private TextView home,drugs,mood,perception,fall,responsability,illness,mobility,continence,feed,cognitive,total,date ;
    private ListView listeFragilityTests ;

    protected DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public final static String FRAGILITY_TO_PROFILE_PATIENT = "isep.project.care4Old.FRAGILITY_TO_PROFILE" ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_fragility);

        FragilityDAO = new FragilityDAO(this) ;

        getDataFromOtherActivities();
        getComponents();
        displayTextInfos();
        createFragilityList();
        getListener();

    }

    private void getDataFromOtherActivities() {

        Intent intentReceived = getIntent();

        String[] extraFromFragility = intentReceived.getStringArrayExtra(FragilityListAdapter.FRAGILITY_TO_FRAGILITY);
        String[] extraFromProfile = intentReceived.getStringArrayExtra(PatientProfileActivity.PROFILE_PATIENT_TO_FRAGILITY);

        if(extraFromProfile!=null && extraFromProfile.length>0) {
            myUserId = Integer.valueOf(extraFromProfile[0]);
            dateFragilityTest = extraFromProfile[1] ;
            idDoctor =  Integer.valueOf(extraFromProfile[2]) ;
        }
        else if(extraFromFragility!=null && extraFromFragility.length>0) {
            myUserId =  Integer.valueOf(extraFromFragility[0]) ;
            dateFragilityTest = extraFromFragility[1] ;
            idDoctor =  Integer.valueOf(extraFromFragility[2]);
        }

    }

    private void getComponents(){
        home = (TextView)findViewById(R.id.FragilityHome);
        drugs = (TextView)findViewById(R.id.FragilityDrugs);
        mood = (TextView)findViewById(R.id.FragilityMood) ;
        perception = (TextView)findViewById(R.id.FragilityPerception);
        fall = (TextView)findViewById(R.id.FragilityFall);
        responsability = (TextView)findViewById(R.id.FragilityResponsability);
        illness = (TextView)findViewById(R.id.FragilityIllness);
        mobility = (TextView)findViewById(R.id.FragilityMobility);
        continence = (TextView)findViewById(R.id.FragilityContinence);
        feed = (TextView)findViewById(R.id.FragilityFeed);
        cognitive = (TextView)findViewById(R.id.FragilityCognitive);
        total = (TextView)findViewById(R.id.FragilityTotal);
        date = (TextView)findViewById(R.id.FragilityDate);
        backProfile = (Button)findViewById(R.id.FragilityBackProfilePatient);
        listeFragilityTests = (ListView)findViewById(R.id.listeOfFragility);
    }


    private void displayTextInfos(){

        Fragility Fragility = FragilityDAO.getResultFragility(myUserId, dateFragilityTest);

        home.setText(String.valueOf(Fragility.getHome()));
        drugs.setText(String.valueOf(Fragility.getDrugs()));
        mood.setText(String.valueOf(Fragility.getMood()));
        perception.setText(String.valueOf(Fragility.getPerception()));
        fall.setText(String.valueOf(Fragility.getFall()));
        responsability.setText(String.valueOf(Fragility.getResponsability()));
        illness.setText(String.valueOf(Fragility.getIllness()));
        mobility.setText(String.valueOf(Fragility.getMobility()));
        continence.setText(String.valueOf(Fragility.getContinence()));
        feed.setText(String.valueOf(Fragility.getFeed()));
        cognitive.setText(String.valueOf(Fragility.getCognitive()));

        String totalGrade = String.valueOf(Fragility.getTotal())+" / 26 " ;
        total.setText(totalGrade);

        String dateN  = dateFormat.format(Fragility.getDate());
        date.setText(dateN);
    }
    
    private void createFragilityList(){
        FragilityListAdapter adapter = new FragilityListAdapter(FragilityDAO.getFragilityTestById(myUserId),this, myUserId,idDoctor);
        listeFragilityTests.setAdapter(adapter);
    }


    private void getListener(){
        backProfile.setOnClickListener(toProfilePatientListener);
    }

    private View.OnClickListener toProfilePatientListener= new View.OnClickListener() {
        @Override
        public void onClick(View v){
                Intent nextActivity = new Intent(ConsultFragilityActivity.this, PatientProfileActivity.class);
                int [] extra = {myUserId,idDoctor} ;
                nextActivity.putExtra(FRAGILITY_TO_PROFILE_PATIENT, extra);
                startActivity(nextActivity);
        }

    };
}
