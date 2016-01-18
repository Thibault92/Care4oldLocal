package isep.project.care4old.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import isep.project.care4old.R;
import isep.project.care4old.dao.UserDAO;
import isep.project.care4old.model.User;

public class AccountConnectionActivity extends Activity {

    public final static String CONNECTION_TO_FIRST = "isep.project.care4old.activity.first_to_connection";
    public final static String CONNECTION_TO_DOCTOR = "isep.project.care4old.activity.connection_to_doctor";
    public final static String CONNECTION_TO_PATIENT = "isep.project.care4old.activity.connection_to_patient";

    private UserDAO myUserDAO;

    private TextView pasInscrit = null;

    private EditText identifiant = null;
    private EditText password = null;

    private Button send = null;
    private Button raz = null;

    private ArrayList<EditText> myEditTexts = null;

    private View.OnClickListener toInscriptionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent secondActivity = new Intent(AccountConnectionActivity.this,CreateAccountFirstPartActivity.class);
            secondActivity.putExtra(CONNECTION_TO_FIRST, identifiant.getText().toString());
            startActivity(secondActivity);
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

                User newUser = new User();
                newUser.setPassword(password.getText().toString());
                newUser.setIdentifiant(identifiant.getText().toString());

                if(!myUserDAO.connectUser(newUser))
                    Toast.makeText(AccountConnectionActivity.this, "Echec de la connexion. Vérifiez vos informations.", Toast.LENGTH_LONG).show();
                else {
                    Toast.makeText(AccountConnectionActivity.this, "Connexion réussie!", Toast.LENGTH_LONG).show();
                    if(myUserDAO.getUserType(newUser.getIdentifiant()).equals("Un patient"))
                        sendData(PatientProfileActivity.class, CONNECTION_TO_PATIENT);
                    else
                        sendData(DoctorProfileActivity.class, CONNECTION_TO_DOCTOR);
                    clearEditTexts();
                }
            }
        }
    };

    public boolean checkAllEditTexts() {
        for (int i = 0; i < myEditTexts.size(); i++) {
            if(TextUtils.isEmpty(myEditTexts.get(i).getText())) {
                Toast.makeText(AccountConnectionActivity.this, "Le champ " + myEditTexts.get(i).getHint() + " n'est pas correctement rempli, veuillez vérifier vos informations.", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    public void sendData(Class destination, String key){
        Intent secondActivity = new Intent(AccountConnectionActivity.this, destination);
        secondActivity.putExtra(key, myUserDAO.getIdUser(identifiant.getText().toString()));
        startActivity(secondActivity);
    }

    public void clearEditTexts() {
        for(int i = 0; i < myEditTexts.size(); i++)
            myEditTexts.get(i).getText().clear();
    }

    public void getComponents() {
        pasInscrit = (TextView)findViewById(R.id.pasInscrit);

        identifiant = (EditText)findViewById(R.id.identifiantConnectionInput);
        password = (EditText)findViewById(R.id.passwordConnectionInput);

        send = (Button)findViewById(R.id.send);
        raz = (Button)findViewById(R.id.raz);
    }

    public void getListeners(){
        pasInscrit.setOnClickListener(toInscriptionListener);
        raz.setOnClickListener(razListener);
        send.setOnClickListener(sendListener);
    }

    public void createTableEditTexts(){
        myEditTexts = new ArrayList<>();
        myEditTexts.add(identifiant);
        myEditTexts.add(password);
    }

    public void getDataFromOtherActivities() {
        Intent intentReceived = getIntent();
        String identifierFromFirst = intentReceived.getStringExtra(CreateAccountFirstPartActivity.FIRST_TO_CONNECTION);
        String[] identifierFromThird = intentReceived.getStringArrayExtra(CreateAccountSecondPartActivity.SECOND_TO_CONNECTION);

        if(identifierFromFirst != null)
            identifiant.setText(identifierFromFirst);

        else if(identifierFromThird != null)
            identifiant.setText(identifierFromThird[0]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_connection);

        getComponents();
        createTableEditTexts();
        getListeners();
        getDataFromOtherActivities();

        myUserDAO = new UserDAO(this);
    }
}
