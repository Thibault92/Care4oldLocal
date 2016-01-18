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
import isep.project.care4old.dao.MnaDAO;
import isep.project.care4old.extension.custom_adapter.MnaListAdapter;
import isep.project.care4old.model.Mna;

public class ConsultMnaActivity extends AppCompatActivity {

    private int myUserId ;
    private String dateMnaTest ;
    private int idDoctor ;

    private MnaDAO mnaDAO ;

    private Button backProfile ;
    private TextView appetite,looseWeight,motricity,neuro,acute,bmi,total,date ;
    private ListView listeMnaTests ;

    protected DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public final static String MNA_TO_PROFILE_PATIENT = "isep.project.care4Old.MNA_TO_PROFILE" ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_mna);

        mnaDAO = new MnaDAO(this) ;

        getDataFromOtherActivities();
        getComponents();
        displayTextInfos();
        createMnaList();
        getListener();

    }

    private void getDataFromOtherActivities() {

        Intent intentReceived = getIntent();

        String[] extraFromMna = intentReceived.getStringArrayExtra(MnaListAdapter.MNA_TO_MNA);
        String[] extraFromProfile = intentReceived.getStringArrayExtra(PatientProfileActivity.PROFILE_PATIENT_TO_MNA);

        if(extraFromProfile!=null && extraFromProfile.length>0) {
            myUserId = Integer.valueOf(extraFromProfile[0]);
            dateMnaTest = extraFromProfile[1] ;
            idDoctor =  Integer.valueOf(extraFromProfile[2]) ;
        }
        else if(extraFromMna!=null && extraFromMna.length>0) {
            myUserId =  Integer.valueOf(extraFromMna[0]) ;
            dateMnaTest = extraFromMna[1] ;
            idDoctor =  Integer.valueOf(extraFromMna[2]) ;
        }

    }

    private void getComponents(){
        appetite = (TextView)findViewById(R.id.MnaAppetite);
        looseWeight = (TextView)findViewById(R.id.MnaLooseWeight);
        motricity = (TextView)findViewById(R.id.MnaMotricity) ;
        acute = (TextView)findViewById(R.id.MnaAcute);
        neuro = (TextView)findViewById(R.id.MnaNeuro);
        bmi = (TextView)findViewById(R.id.MnaBmi);
        total = (TextView)findViewById(R.id.MnaTotal);
        date = (TextView)findViewById(R.id.MnaDate);
        backProfile = (Button)findViewById(R.id.MnaBackProfilePatient);
        listeMnaTests = (ListView)findViewById(R.id.listeOfMna);
    }


    private void displayTextInfos(){

        Mna mna = mnaDAO.getResultMna(myUserId, dateMnaTest);

        appetite.setText(String.valueOf(mna.getAppetite()));
        looseWeight.setText(String.valueOf(mna.getLoose_weight()));
        motricity.setText(String.valueOf(mna.getMotricity()));
        acute.setText(String.valueOf(mna.getAcute()));
        neuro.setText(String.valueOf(mna.getNeuro()));
        bmi.setText(String.valueOf(mna.getBmi()));

        String totalGrade = String.valueOf(mna.getTotal())+" / 14 " ;
        total.setText(totalGrade);

        String dateN  = dateFormat.format(mna.getDate());
        date.setText(dateN);
    }
    
    private void createMnaList(){
        MnaListAdapter adapter = new MnaListAdapter(mnaDAO.getMnaTestById(myUserId),this, myUserId,idDoctor);
        listeMnaTests.setAdapter(adapter);
    }


    private void getListener(){
        backProfile.setOnClickListener(toProfilePatientListener);
    }

    private View.OnClickListener toProfilePatientListener= new View.OnClickListener() {
        @Override
        public void onClick(View v){
                Intent nextActivity = new Intent(ConsultMnaActivity.this, PatientProfileActivity.class);
                int [] extra = {myUserId,idDoctor} ;
                nextActivity.putExtra(MNA_TO_PROFILE_PATIENT, extra);
                startActivity(nextActivity);
        }

    };
}
