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
import isep.project.care4old.dao.LawtonDAO;
import isep.project.care4old.extension.custom_adapter.LawtonListAdapter;
import isep.project.care4old.model.Lawton;

public class ConsultLawtonActivity extends AppCompatActivity {

    private int myUserId ;
    private String dateLawtonTest ;
    private int idDoctor ;

    private LawtonDAO lawtonDAO ;

    private Button backProfile ;
    private TextView phone,growshop,cook,clean,laundry,transport,drugs,money,total,date ;
    private ListView listeLawtonTests ;

    protected DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public final static String LAWTON_TO_PROFILE_PATIENT = "isep.project.care4Old.LAWTON_TO_PROFILE" ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_lawton);

        lawtonDAO = new LawtonDAO(this) ;

        getDataFromOtherActivities();
        getComponents();
        displayTextInfos();
        createLawtonList();
        getListener();

    }

    private void getDataFromOtherActivities() {

        Intent intentReceived = getIntent();

        String[] extraFromLawton = intentReceived.getStringArrayExtra(LawtonListAdapter.LAWTON_TO_LAWTON);
        String[] extraFromProfile = intentReceived.getStringArrayExtra(PatientProfileActivity.PROFILE_PATIENT_TO_LAWTON);

        if(extraFromProfile!=null && extraFromProfile.length>0) {
            myUserId = Integer.valueOf(extraFromProfile[0]);
            dateLawtonTest = extraFromProfile[1] ;
            idDoctor =  Integer.valueOf(extraFromProfile[2]) ;
        }
        else if(extraFromLawton!=null && extraFromLawton.length>0) {
            myUserId =  Integer.valueOf(extraFromLawton[0]) ;
            dateLawtonTest = extraFromLawton[1] ;
            idDoctor =  Integer.valueOf(extraFromLawton[2]) ;
        }

    }

    private void getComponents(){
        phone = (TextView)findViewById(R.id.LawtonPhone);
        growshop = (TextView)findViewById(R.id.LawtonGrowshop);
        cook = (TextView)findViewById(R.id.LawtonCook) ;
        clean = (TextView)findViewById(R.id.LawtonClean);
        laundry = (TextView)findViewById(R.id.LawtonLaundry);
        transport = (TextView)findViewById(R.id.LawtonTransport);
        drugs = (TextView)findViewById(R.id.LawtonDrugs);
        money = (TextView)findViewById(R.id.LawtonMoney);
        total = (TextView)findViewById(R.id.LawtonTotal);
        date = (TextView)findViewById(R.id.LawtonDate);
        backProfile = (Button)findViewById(R.id.LawtonBackProfilePatient);
        listeLawtonTests = (ListView)findViewById(R.id.listeOfLawton);
    }


    private void displayTextInfos(){

        Lawton lawton = lawtonDAO.getResultLawton(myUserId, dateLawtonTest);

        phone.setText(String.valueOf(lawton.getPhone()));
        growshop.setText(String.valueOf(lawton.getGrowshop()));
        cook.setText(String.valueOf(lawton.getCook()));
        clean.setText(String.valueOf(lawton.getClean()));
        laundry.setText(String.valueOf(lawton.getLaundry()));
        transport.setText(String.valueOf(lawton.getTransport()));
        drugs.setText(String.valueOf(lawton.getDrugs()));
        money.setText(String.valueOf(lawton.getMoney()));

        String totalGrade = String.valueOf(lawton.getTotal())+" / 8 " ;
        total.setText(totalGrade);

        String dateN  = dateFormat.format(lawton.getDate());
        date.setText(dateN);
    }
    
    private void createLawtonList(){
        LawtonListAdapter adapter = new LawtonListAdapter(lawtonDAO.getLawtonTestById(myUserId),this, myUserId,idDoctor);
        listeLawtonTests.setAdapter(adapter);
    }


    private void getListener(){
        backProfile.setOnClickListener(toProfilePatientListener);
    }

    private View.OnClickListener toProfilePatientListener= new View.OnClickListener() {
        @Override
        public void onClick(View v){
                Intent nextActivity = new Intent(ConsultLawtonActivity.this, PatientProfileActivity.class);
                int [] extra = {myUserId,idDoctor} ;
                nextActivity.putExtra(LAWTON_TO_PROFILE_PATIENT, extra);
                startActivity(nextActivity);
        }

    };
}
