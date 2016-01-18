package isep.project.care4old.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import isep.project.care4old.R;
import isep.project.care4old.dao.HospDAO;
import isep.project.care4old.model.Hospitalization;


public class HospitalizationCreationActivity extends AppCompatActivity {

    public final static String HOSPCREATION_TO_HOSPVIEW = "locatemyshop.shopCreation_to_shopHandler";

    private Calendar hospStartDate = Calendar.getInstance();
    private Calendar hospEndDate = Calendar.getInstance();
    private String hospStartDisplay;
    private String hospEndDisplay;

    private EditText motif = null;

    private TextView hospStart = null;
    private TextView hospEnd = null;

    private int switchButton;
    private Button hospStartButton = null;
    private Button hospEndButton = null;
    private Button previous = null;
    private Button send = null;
    private Button raz = null;

    private ArrayList<EditText> myEditTexts = null;

    private HospDAO myHospDAO = null;
    private Hospitalization myHosp = null;
    private int idPatient, idDoctor;

    private DatePickerDialog.OnDateSetListener hospDatePickerListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day){
            if(switchButton == 1){
                hospStartDate.set(year, month, day);
                hospStartDisplay = formDigits(hospStartDate.get(Calendar.DAY_OF_MONTH)) + "/" + formDigits(hospStartDate.get(Calendar.MONTH) + 1) + "/" + hospStartDate.get(Calendar.YEAR);
                hospStart.setText(hospStartDisplay);
            }

            else if(switchButton == 2){
                hospEndDate.set(year, month, day);
                hospEndDisplay = formDigits(hospEndDate.get(Calendar.DAY_OF_MONTH)) + "/" + formDigits(hospEndDate.get(Calendar.MONTH) + 1) + "/" + hospEndDate.get(Calendar.YEAR);
                hospEnd.setText(hospEndDisplay);
            }
        }
    };

    private String formDigits(int number){
        if(number <= 9) return "0" + number;
        else return number + "";
    }

    public Date convertStringToDate (String stringDate) {
        DateFormat formattedBirthday = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        Date birthdayConverted = null;
        try {
            birthdayConverted = formattedBirthday.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return birthdayConverted;
    }

    private View.OnClickListener hospListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int hospYear = 2016;
            int hospMonth = 1;
            int hospDay = 18;
            if(v.getId() == R.id.hospStartButton)
                switchButton = 1;
            else if(v.getId() == R.id.hospEndButton)
                switchButton = 2;

            DatePickerDialog birthdayPicker = new DatePickerDialog(HospitalizationCreationActivity.this, hospDatePickerListener, hospYear, hospMonth, hospDay);
            birthdayPicker.show();
        }
    };

    private View.OnClickListener razListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            clearEditTexts();
        }
    };

    private View.OnClickListener previousListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent secondActivity = new Intent(HospitalizationCreationActivity.this, HospitalizationActivity.class);
            secondActivity.putExtra(HOSPCREATION_TO_HOSPVIEW, new int[]{idPatient, idDoctor});
            startActivity(secondActivity);
        }
    };

    private View.OnClickListener sendListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            if(checkAllEditTexts()) {
                if(checkHospDoesNotExists())
                    goToChosenActivity(HospitalizationActivity.class, HOSPCREATION_TO_HOSPVIEW);
            }
        }
    };

    public boolean checkAllEditTexts() {
        for (int i = 0; i < myEditTexts.size(); i++) {
            if(TextUtils.isEmpty(myEditTexts.get(i).getText())) {
                Toast.makeText(HospitalizationCreationActivity.this, "Le champ " + myEditTexts.get(i).getHint() + " n'est pas correctement rempli, veuillez vérifier vos informations.", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    public boolean checkHospDoesNotExists () {
        if(myHospDAO.checkHosp(hospStart.getText().toString())) {
            Toast.makeText(HospitalizationCreationActivity.this, "Cette hospitalisation existe déjà, veuillez en saisir une autre.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void goToChosenActivity(Class destination, String key) {
        updateMyShopFromThis();
        sendData(destination, key);
        clearEditTexts();
    }

    public void updateMyShopFromThis() {
        myHosp.setIdPatient(idPatient);
        myHosp.setHospStart(convertStringToDate(hospStartDisplay));
        myHosp.setHospEnd(convertStringToDate(hospEndDisplay));
        myHosp.setMotif(motif.getText().toString());
    }

    public void sendData(Class destination, String key){
        myHospDAO.addHosp(myHosp);
        Intent secondActivity = new Intent(HospitalizationCreationActivity.this, destination);
        secondActivity.putExtra(key, new int[]{idPatient, idDoctor});
        startActivity(secondActivity);
    }

    public void clearEditTexts() {
        for(int i = 0; i < myEditTexts.size(); i++)
            myEditTexts.get(i).getText().clear();
    }

    public void getComponents() {
        motif = (EditText)findViewById(R.id.motifInput);

        hospStart = (TextView)findViewById(R.id.hospStartText);
        hospEnd = (TextView)findViewById(R.id.hospEndText);

        hospStartButton = (Button)findViewById(R.id.hospStartButton);
        hospEndButton = (Button)findViewById(R.id.hospEndButton);
        send = (Button)findViewById(R.id.send_hosps);
        previous = (Button)findViewById(R.id.retour_hosps);
        raz = (Button)findViewById(R.id.raz);
    }

    public void createTableEditTexts(){
        myEditTexts = new ArrayList<>();
        myEditTexts.add(motif);
    }

    public void getListeners(){
        hospStartButton.setOnClickListener(hospListener);
        hospEndButton.setOnClickListener(hospListener);
        raz.setOnClickListener(razListener);
        send.setOnClickListener(sendListener);
        previous.setOnClickListener(previousListener);
    }

    public void getDataFromOtherActivities() {
        myHosp = new Hospitalization();
        Intent intentReceived = getIntent();

        int[] idsFromHosp = intentReceived.getIntArrayExtra(HospitalizationActivity.HOSPVIEW_TO_HOSPCREATION);

        if(idsFromHosp != null) {
            idPatient = idsFromHosp[0];
            idDoctor = idsFromHosp[1];
        }

        myHosp.setIdPatient(idPatient);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitalization_creation);

        myHospDAO = new HospDAO(this);

        getComponents();
        createTableEditTexts();
        getListeners();
        getDataFromOtherActivities();
    }
}
