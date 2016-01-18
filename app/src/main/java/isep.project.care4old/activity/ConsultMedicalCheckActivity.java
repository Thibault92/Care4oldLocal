package isep.project.care4old.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

import isep.project.care4old.R;
import isep.project.care4old.dao.MedicalCheckDAO;
import isep.project.care4old.extension.custom_adapter.MedicalCheckListAdapter;


public class ConsultMedicalCheckActivity extends AppCompatActivity {

    public final static String MEDCONSULT_TO_MEDCHECK = "isep.project.care4Old.CONSULT_MEDCHECK_TO_MEDCHECK";

    private int idPatient, idDoctor, idMedCheck;
    private MedicalCheckDAO medCheckDAO;

    private Button previous;

    private TextView date, taille, imc, poids, mna, albuminemie, crp, vitamine, diagnostic;

    private void getDataFromOtherActivities() {
        Intent intentReceived = getIntent();
        int[] ids = intentReceived.getIntArrayExtra(MedicalCheckListAdapter.MEDADAPTER_TO_MEDCONSULT);
        idPatient = ids[0];
        idDoctor = ids[1];
        idMedCheck = ids[2];
    }

    private void displayResults(){
        String[] medCheck = medCheckDAO.getMedicalCheck(idMedCheck);
        date.setText(medCheck[0]);
        taille.setText(medCheck[1]);
        poids.setText(medCheck[2]);

        DecimalFormat df = new DecimalFormat("0.00");
        double tailleImc = Math.pow(Double.parseDouble(medCheck[1])/100,2) ;
        double poidsImc = Double.parseDouble(medCheck[2]) ;
        double imcMath = poidsImc / tailleImc ;

        imc.setText(df.format(imcMath));
        mna.setText(medCheck[3]);
        albuminemie.setText(medCheck[4]);
        crp.setText(medCheck[5]);
        vitamine.setText(medCheck[6]);
        diagnostic.setText(medCheck[7]);
    }

    private void getComponents(){
        date = (TextView)findViewById(R.id.date_medcheck_infos);
        taille = (TextView)findViewById(R.id.tailleInfos);
        poids = (TextView)findViewById(R.id.poidsInfos) ;
        imc = (TextView)findViewById(R.id.imcInfos);
        mna = (TextView)findViewById(R.id.mnaInfos);
        albuminemie = (TextView)findViewById(R.id.albuminemieInfos);
        crp = (TextView)findViewById(R.id.crpInfos) ;
        vitamine = (TextView)findViewById(R.id.vitamineInfos);
        diagnostic = (TextView)findViewById(R.id.diagnosticInfos);

        previous = (Button)findViewById(R.id.retour_consultation);
    }

    private void getListener(){
        previous.setOnClickListener(previousListener);
    }


    private View.OnClickListener previousListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent secondActivity = new Intent(ConsultMedicalCheckActivity.this, MedicalCheckActivity.class);
            secondActivity.putExtra(MEDCONSULT_TO_MEDCHECK, new int[] {idPatient, idDoctor});
            startActivity(secondActivity);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_medical_check);

        medCheckDAO = new MedicalCheckDAO(this) ;

        getDataFromOtherActivities();
        getComponents();
        displayResults();
        getListener();
    }
}

