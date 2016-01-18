package isep.project.care4old.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import isep.project.care4old.R;
import isep.project.care4old.dao.MedicalCheckDAO;
import isep.project.care4old.model.MedicalCheck;

public class MedicalCheckCreationActivity extends Activity {

    public final static String MEDCHECKCREATION_TO_MEDCHECK = "paul.care4old.medcheckcreation_to_medcheck";

    private MedicalCheckDAO myMedCheckDAO;
    private MedicalCheck myMedicalCheck;
    private int idPatient, idDoctor;

    private EditText taille = null;
    private EditText poids = null;
    private EditText mna = null;
    private EditText albuminemie = null;
    private EditText crp = null;
    private EditText vitamine = null;
    private EditText diagnostic = null;

    private ArrayList<EditText> myEditTexts = null;

    private Button previous = null;
    private Button send = null;
    private Button raz = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_check_creation);

        getComponents();
        createTableEditTexts();
        getListeners();
        getDataFromOtherActivities();

        myMedCheckDAO = new MedicalCheckDAO(this);
    }

    private View.OnClickListener razListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            clearEditTexts();
        }
    };

    private View.OnClickListener previousListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent secondActivity = new Intent(MedicalCheckCreationActivity.this, MedicalCheckActivity.class);
            secondActivity.putExtra(MEDCHECKCREATION_TO_MEDCHECK, new int[]{idPatient, idDoctor});
            startActivity(secondActivity);
        }
    };

    private View.OnClickListener sendListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            if(checkAllEditTexts()) {
                if(checkPatterns()) {
                    goToChosenActivity(MedicalCheckActivity.class, MEDCHECKCREATION_TO_MEDCHECK);
                }
            }
        }
    };

    public boolean checkAllEditTexts() {
        for (int i = 0; i < myEditTexts.size(); i++) {
            if(TextUtils.isEmpty(myEditTexts.get(i).getText())) {
                Toast.makeText(MedicalCheckCreationActivity.this, "Le champ " + myEditTexts.get(i).getHint() + " n'est pas correctement rempli, veuillez vérifier vos informations.", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    public boolean checkPatterns(){
        return checkZipPattern(taille.getText().toString()) &&
                checkZipPattern(poids.getText().toString()) &&
                checkZipPattern(crp.getText().toString()) &&
                checkZipPattern(albuminemie.getText().toString()) &&
                checkZipPattern(vitamine.getText().toString()) &&
                checkZipPattern(mna.getText().toString());
    }

    public boolean checkOnlyNumberPattern(String regex, String stringToMatch) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(stringToMatch);
        return matcher.matches();
    }

    public boolean checkZipPattern(String zip) {
        if(!checkOnlyNumberPattern("^[0-9]+(,[0-9]+)?$", zip)) {
            Toast.makeText(MedicalCheckCreationActivity.this, "Retapez des valeurs valides.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void goToChosenActivity(Class destination, String key) {
        updateMyMedcheckFromThis();
        sendData(destination, key);
        clearEditTexts();
    }

    public void updateMyMedcheckFromThis() {
        myMedicalCheck.setIdPatient(idPatient);
        myMedicalCheck.setHeight(Integer.parseInt(taille.getText().toString()));
        myMedicalCheck.setWeight(Integer.parseInt(poids.getText().toString()));
        myMedicalCheck.setMna(Integer.parseInt(mna.getText().toString()));
        myMedicalCheck.setAlbuminemie(Integer.parseInt(albuminemie.getText().toString()));
        myMedicalCheck.setCrp(Integer.parseInt(crp.getText().toString()));
        myMedicalCheck.setVitamine(Integer.parseInt(vitamine.getText().toString()));
        myMedicalCheck.setDiagnostic(diagnostic.getText().toString());
    }

    public void sendData(Class destination, String key){
        myMedCheckDAO.addMedCheck(myMedicalCheck);
        Intent secondActivity = new Intent(MedicalCheckCreationActivity.this, destination);
        secondActivity.putExtra(key, new int[] {idPatient, idDoctor});
        startActivity(secondActivity);
    }

    public void clearEditTexts() {
        for(int i = 0; i < myEditTexts.size(); i++)
            myEditTexts.get(i).getText().clear();
    }

    public void getComponents() {
        taille = (EditText)findViewById(R.id.tailleInput);
        poids = (EditText)findViewById(R.id.poidsInput);
        mna = (EditText)findViewById(R.id.mnaInput);
        albuminemie = (EditText)findViewById(R.id.albuminemieInput);
        crp = (EditText)findViewById(R.id.crpInput);
        vitamine = (EditText)findViewById(R.id.vitdInput);
        diagnostic = (EditText)findViewById(R.id.diagnosticInput);

        send = (Button)findViewById(R.id.sendMedCheck);
        previous = (Button)findViewById(R.id.retourMedCheck);
        raz = (Button)findViewById(R.id.raz);
    }

    public void createTableEditTexts(){
        myEditTexts = new ArrayList<>();
        myEditTexts.add(taille);
        myEditTexts.add(poids);
        myEditTexts.add(mna);
        myEditTexts.add(albuminemie);
        myEditTexts.add(crp);
        myEditTexts.add(vitamine);
        myEditTexts.add(diagnostic);
    }

    public void getListeners(){
        raz.setOnClickListener(razListener);
        send.setOnClickListener(sendListener);
        previous.setOnClickListener(previousListener);
    }

    public void getDataFromOtherActivities() {
        myMedicalCheck = new MedicalCheck();
        Intent intentReceived = getIntent();
        idPatient = intentReceived.getIntArrayExtra(MedicalCheckActivity.MEDCHECK_TO_MEDCHECKCREATION)[0];
        idDoctor = intentReceived.getIntArrayExtra(MedicalCheckActivity.MEDCHECK_TO_MEDCHECKCREATION)[1];
        myMedicalCheck.setIdPatient(idPatient);
    }
}