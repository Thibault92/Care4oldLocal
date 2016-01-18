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
import isep.project.care4old.dao.KatzDAO;
import isep.project.care4old.extension.custom_adapter.KatzListAdapter;
import isep.project.care4old.model.Katz;

public class ConsultKatzActivity extends AppCompatActivity {

    private int myUserId ;
    private int idDoctor ;
    private String dateKatzTest ;

    private KatzDAO katzDAO ;

    private Button backProfile ;
    private TextView hygiene,dressing,bathroom,incontinence,mobility,lunch,total,date ;
    private ListView listekatzTests ;

    protected DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public final static String KATZ_TO_PROFILE_PATIENT = "isep.project.care4Old.KATZ_TO_PROFILE" ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_katz);

        katzDAO = new KatzDAO(this) ;

        getDataFromOtherActivities();
        getComponents();
        displayTextInfos();
        createkatzList();
        getListener();

    }

    private void getDataFromOtherActivities() {

        Intent intentReceived = getIntent();

        String[] extraFromKatz = intentReceived.getStringArrayExtra(KatzListAdapter.KATZ_TO_KATZ);
        String[] extraFromProfile = intentReceived.getStringArrayExtra(PatientProfileActivity.PROFILE_PATIENT_TO_KATZ);

        if(extraFromProfile!=null && extraFromProfile.length>0) {
            myUserId = Integer.valueOf(extraFromProfile[0]);
            dateKatzTest = extraFromProfile[1] ;
            idDoctor =  Integer.valueOf(extraFromProfile[2]) ;
        }
        else if(extraFromKatz!=null && extraFromKatz.length>0) {
            myUserId =  Integer.valueOf(extraFromKatz[0]) ;
            dateKatzTest = extraFromKatz[1] ;
            idDoctor =  Integer.valueOf(extraFromKatz[2]) ;
        }

    }

    private void getComponents(){
        hygiene = (TextView)findViewById(R.id.KatzHygiene);
        dressing = (TextView)findViewById(R.id.KatzDressing);
        bathroom = (TextView)findViewById(R.id.KatzBathroom) ;
        mobility = (TextView)findViewById(R.id.KatzMobility);
        incontinence = (TextView)findViewById(R.id.KatzIncontinence);
        lunch = (TextView)findViewById(R.id.KatzLunch);
        total = (TextView)findViewById(R.id.KatzTotal);
        date = (TextView)findViewById(R.id.KatzDate);
        backProfile = (Button)findViewById(R.id.KatzBackProfilePatient);
        listekatzTests = (ListView)findViewById(R.id.listeOfKatz);
    }


    private void displayTextInfos(){

        Katz katz = katzDAO.getResultKatz(myUserId,dateKatzTest);

        hygiene.setText(String.valueOf(katz.getHygiene()));
        dressing.setText(String.valueOf(katz.getDressing()));
        bathroom.setText(String.valueOf(katz.getBathroom()));
        mobility.setText(String.valueOf(katz.getLocomotion()));
        incontinence.setText(String.valueOf(katz.getContinence()));
        lunch.setText(String.valueOf(katz.getLunch()));

        String totalGrade = String.valueOf(katz.getTotal())+" / 6 " ;
        total.setText(totalGrade);

        String dateN  = dateFormat.format(katz.getDate());
        date.setText(dateN);
    }
    
    private void createkatzList(){
        KatzListAdapter adapter = new KatzListAdapter(katzDAO.getKatzTestById(myUserId),this, myUserId,idDoctor);
        listekatzTests.setAdapter(adapter);
    }


    private void getListener(){
        backProfile.setOnClickListener(toProfilePatientListener);
    }

    private View.OnClickListener toProfilePatientListener= new View.OnClickListener() {
        @Override
        public void onClick(View v){
                Intent nextActivity = new Intent(ConsultKatzActivity.this, PatientProfileActivity.class);
                int [] extra = {myUserId,idDoctor} ;
                nextActivity.putExtra(KATZ_TO_PROFILE_PATIENT, extra);
                startActivity(nextActivity);
        }

    };
}
