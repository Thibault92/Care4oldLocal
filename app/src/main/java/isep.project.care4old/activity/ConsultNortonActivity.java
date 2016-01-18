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
import isep.project.care4old.dao.NortonDAO;
import isep.project.care4old.extension.custom_adapter.NortonListAdapter;
import isep.project.care4old.model.Norton;

public class ConsultNortonActivity extends AppCompatActivity {

    private int myUserId ;
    private String dateNortonTest ;
    private int idDoctor ;

    private NortonDAO nortonDAO ;

    private Button backProfile ;
    private TextView global,agility,psychic,incontinence,mobility,total,date ;
    private ListView listeNortonTests ;

    protected DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public final static String NORTON_TO_PROFILE_PATIENT = "isep.project.care4Old.NORTON_TO_PROFILE" ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_norton);

        nortonDAO = new NortonDAO(this) ;

        getDataFromOtherActivities();
        getComponents();
        displayTextInfos();
        createNortonList();
        getListener();

    }

    private void getDataFromOtherActivities() {

        Intent intentReceived = getIntent();

        String[] extraFromNorton = intentReceived.getStringArrayExtra(NortonListAdapter.NORTON_TO_NORTON);
        String[] extraFromProfile = intentReceived.getStringArrayExtra(PatientProfileActivity.PROFILE_PATIENT_TO_NORTON);

        if(extraFromProfile!=null && extraFromProfile.length>0) {
            myUserId = Integer.valueOf(extraFromProfile[0]);
            dateNortonTest = extraFromProfile[1] ;
            idDoctor =  Integer.valueOf(extraFromProfile[2]) ;
        }
        else if(extraFromNorton!=null && extraFromNorton.length>0) {
            myUserId =  Integer.valueOf(extraFromNorton[0]) ;
            dateNortonTest = extraFromNorton[1] ;
            idDoctor =  Integer.valueOf(extraFromNorton[2]) ;
        }

    }

    private void getComponents(){
        global = (TextView)findViewById(R.id.nortonGlobal);
        agility = (TextView)findViewById(R.id.nortonAgility);
        psychic = (TextView)findViewById(R.id.nortonPsychic) ;
        incontinence = (TextView)findViewById(R.id.nortonIncontinence);
        mobility = (TextView)findViewById(R.id.nortonMobility);
        total = (TextView)findViewById(R.id.nortonTotal);
        date = (TextView)findViewById(R.id.nortonDate);
        backProfile = (Button)findViewById(R.id.nortonBackProfilePatient);
        listeNortonTests = (ListView)findViewById(R.id.listeOfNorton);
    }


    private void displayTextInfos(){

        Norton norton = nortonDAO.getResultNorton(myUserId,dateNortonTest);

        global.setText(String.valueOf(norton.getGlobal()));
        agility.setText(String.valueOf(norton.getAgility()));
        psychic.setText(String.valueOf(norton.getPsychic()));
        incontinence.setText(String.valueOf(norton.getIncontinence()));
        mobility.setText(String.valueOf(norton.getMobility()));

        String totalGrade = String.valueOf(norton.getTotal())+" / 20 " ;
        total.setText(totalGrade);

        String dateN  = dateFormat.format(norton.getDate());
        date.setText(dateN);
    }
    
    private void createNortonList(){
        NortonListAdapter adapter = new NortonListAdapter(nortonDAO.getNortonTestById(myUserId),this, myUserId,idDoctor);
        listeNortonTests.setAdapter(adapter);
    }


    private void getListener(){
        backProfile.setOnClickListener(toProfilePatientListener);
    }

    private View.OnClickListener toProfilePatientListener= new View.OnClickListener() {
        @Override
        public void onClick(View v){
                Intent nextActivity = new Intent(ConsultNortonActivity.this, PatientProfileActivity.class);
                int [] extra = {myUserId,idDoctor} ;
                nextActivity.putExtra(NORTON_TO_PROFILE_PATIENT, extra);
                startActivity(nextActivity);
        }

    };
}
