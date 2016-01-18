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
import isep.project.care4old.dao.UserDAO;
import isep.project.care4old.model.User;

public class CreateAccountPatientActivity extends Activity {

    public final static String DOCTOR_LINK_TO_CONNECTION = "isep.project.care4old.inscription.doctor_link_to_connection";
    public final static String DOCTOR_LINK_TO_THIRD = "isep.project.care4old.inscription.doctor_link_to_third";

    private EditText myDoctor = null;

    private Button previous = null;
    private Button send = null;
    private Button raz = null;

    private ArrayList<EditText> myEditTexts = null;

    private String[] myUser = null;
    private UserDAO myUserDAO;

    private View.OnClickListener previousListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            updateMyUser();
            sendData(CreateAccountThirdPartActivity.class, DOCTOR_LINK_TO_THIRD);
        }
    };

    private View.OnClickListener razListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            clearEditTexts();
        }
    };

    private View.OnClickListener sendListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            if(checkAllEditTexts()) {
                if(checkPatterns()) {
                    if(checkDoctorExists()) {
                        updateMyUser();
                        addUserIntoDatabase();
                        sendData(AccountConnectionActivity.class, DOCTOR_LINK_TO_CONNECTION);
                    }
                }
            }
        }
    };

    public boolean checkAllEditTexts() {
        for (int i = 0; i < myEditTexts.size(); i++) {
            if(TextUtils.isEmpty(myEditTexts.get(i).getText())) {
                Toast.makeText(CreateAccountPatientActivity.this, "Le champ " + myEditTexts.get(i).getHint() + " n'est pas correctement rempli, veuillez vérifier vos informations.", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    public boolean checkPatterns() {
        return checkEmailPattern(myDoctor.getText().toString());
    }

    public boolean checkPattern(String regex, String stringToMatch) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(stringToMatch);
        return matcher.matches();
    }

    public boolean checkEmailPattern(String email) {
        if(!checkPattern("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", email)) {
            Toast.makeText(CreateAccountPatientActivity.this, "Retapez une adresse mail valide.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean checkDoctorExists () {
        if(!myUserDAO.checkUser(myDoctor.getText().toString())) {
            Toast.makeText(CreateAccountPatientActivity.this, "Cette adresse n'existe pas, veuillez en saisir une autre.", Toast.LENGTH_LONG).show();
            myDoctor.getText().clear();
            return false;
        }
        return true;
    }

    public void updateMyUser() {
        myUser[17] = String.valueOf(myUserDAO.getIdUser(myDoctor.getText().toString()));
    }

    public void addUserIntoDatabase () {
        User newUser = new User();
        newUser.setAttributes(myUser);
        myUserDAO.addUser(newUser);
    }

    public void sendData(Class destination, String key){
        Intent secondActivity = new Intent(CreateAccountPatientActivity.this, destination);
        secondActivity.putExtra(key, myUser);
        startActivity(secondActivity);
    }

    public void clearEditTexts() {
        for(int i = 0; i < myEditTexts.size(); i++)
            myEditTexts.get(i).getText().clear();
    }

    public void getComponents() {
        myDoctor = (EditText)findViewById(R.id.emailMedecinInput);

        previous = (Button)findViewById(R.id.retourCreation4);
        send = (Button)findViewById(R.id.nextCreation4);
        raz = (Button)findViewById(R.id.raz);
    }

    public void createTableEditTexts(){
        myEditTexts = new ArrayList<>();
        myEditTexts.add(myDoctor);
    }

    public void getListeners(){
        previous.setOnClickListener(previousListener);
        raz.setOnClickListener(razListener);
        send.setOnClickListener(sendListener);
    }

    public void getDataFromOtherActivities() {
        myUser = new String[18];
        String[] userAttributes = getIntent().getStringArrayExtra(CreateAccountThirdPartActivity.THIRD_TO_DOCTOR_LINK);
        if(userAttributes != null) {
            for(int i = 0; i < myUser.length; i++)
                myUser[i] = userAttributes[i];
            if(userAttributes[17] != null)
                myDoctor.setText(userAttributes[17]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_patient);

        myUserDAO = new UserDAO(this);

        getComponents();
        createTableEditTexts();
        getListeners();
        getDataFromOtherActivities();


    }
}
