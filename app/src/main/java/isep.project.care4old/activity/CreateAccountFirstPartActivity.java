package isep.project.care4old.activity ;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import isep.project.care4old.R;
import isep.project.care4old.dao.UserDAO;

public class CreateAccountFirstPartActivity extends Activity {

    public final static String FIRST_TO_SECOND = "isep.project.care4old.inscription.first_to_second";
    public final static String FIRST_TO_CONNECTION = "isep.project.care4old.inscription.first_to_connection";

    private TextView dejaInscrit = null;

    private EditText identifiant = null;
    private EditText password = null;
    private EditText confirmationPassword = null;

    private Button next = null;
    private Button raz = null;

    private ArrayList<EditText> myEditTexts = null;

    private String[] myUser = null;
    private UserDAO myUserDAO;

    private View.OnClickListener toConnectionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent secondActivity = new Intent(CreateAccountFirstPartActivity.this, AccountConnectionActivity.class);
            secondActivity.putExtra(FIRST_TO_CONNECTION, identifiant.getText().toString());
            startActivity(secondActivity);
        }
    };

    private View.OnClickListener razListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            clearEditTexts();
        }
    };

    private View.OnClickListener nextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            if(checkAllEditTexts()) {
                if(checkPatterns()) {
                    if(checkUserDoesNotExists() && isPasswordsMatched()) {
                        goToChosenActivity(CreateAccountSecondPartActivity.class, FIRST_TO_SECOND);
                    }
                }
            }
        }
    };

    public boolean checkAllEditTexts() {
        for (int i = 0; i < myEditTexts.size(); i++) {
            if(TextUtils.isEmpty(myEditTexts.get(i).getText())) {
                Toast.makeText(CreateAccountFirstPartActivity.this, "Le champ " + myEditTexts.get(i).getHint() + " n'est pas correctement rempli, veuillez vérifier vos informations.", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    public boolean checkPatterns() {
        return checkEmailPattern(identifiant.getText().toString()) && checkPasswordPattern(password.getText().toString());
    }

    public boolean checkPattern(String regex, String stringToMatch) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(stringToMatch);
        return matcher.matches();
    }

    public boolean checkEmailPattern(String email) {
        if(!checkPattern("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", email)) {
            Toast.makeText(CreateAccountFirstPartActivity.this, "Retapez une adresse mail valide.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean checkPasswordPattern(String motDePasse) {
        if(!checkPattern("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$", motDePasse)) {
            Toast.makeText(CreateAccountFirstPartActivity.this, "Votre mot de passe doit contenir au moins 6 caractères dont une minuscule, une majuscule, un chiffre, sans espace.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean checkUserDoesNotExists () {
        if(myUserDAO.checkUser(identifiant.getText().toString())) {
            Toast.makeText(CreateAccountFirstPartActivity.this, "Cette adresse existe déjà, veuillez en saisir une autre.", Toast.LENGTH_LONG).show();
            identifiant.getText().clear();
            return false;
        }
        return true;
    }

    public boolean isPasswordsMatched() {
        if (!password.getText().toString().equals(confirmationPassword.getText().toString())) {
            Toast.makeText(CreateAccountFirstPartActivity.this, "Les 2 mots de passe rentrés ne correspondent pas, veuillez vérifier vos informations.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void goToChosenActivity(Class destination, String key) {
        updateMyUserFromThis();
        sendData(destination, key);
        clearEditTexts();
    }

    public void updateMyUserFromThis() {
        myUser[0] = identifiant.getText().toString();
        myUser[1] = password.getText().toString();
    }

    public void sendData(Class destination, String key){
        Intent secondActivity = new Intent(CreateAccountFirstPartActivity.this, destination);
        secondActivity.putExtra(key, myUser);
        startActivity(secondActivity);
    }

    public void clearEditTexts() {
        for(int i = 0; i < myEditTexts.size(); i++)
            myEditTexts.get(i).getText().clear();
    }

    public void getComponents() {
        dejaInscrit = (TextView)findViewById(R.id.dejaInscrit);

        identifiant = (EditText)findViewById(R.id.identifiantInscriptionInput);
        password = (EditText)findViewById(R.id.passwordInscriptionInput);
        confirmationPassword = (EditText)findViewById(R.id.confirmationPasswordInput);

        next = (Button)findViewById(R.id.nextCreation1);
        raz = (Button)findViewById(R.id.raz);
    }

    public void createTableEditTexts(){
        myEditTexts = new ArrayList<>();
        myEditTexts.add(identifiant);
        myEditTexts.add(password);
        myEditTexts.add(confirmationPassword);
    }

    public void getListeners(){
        dejaInscrit.setOnClickListener(toConnectionListener);
        raz.setOnClickListener(razListener);
        next.setOnClickListener(nextListener);
    }

    public void getDataFromOtherActivities() {
        myUser = new String[18];

        Intent intentReceived = getIntent();
        String identifierFromConnection = intentReceived.getStringExtra(AccountConnectionActivity.CONNECTION_TO_FIRST);
        String[] userAttributes = intentReceived.getStringArrayExtra(CreateAccountSecondPartActivity.SECOND_TO_FIRST);

        if(identifierFromConnection != null)
            identifiant.setText(identifierFromConnection);

        else if(userAttributes != null)
            updateMyUserFromOtherActivity(userAttributes);
    }

    public void updateMyUserFromOtherActivity(String[] userAttributes) {
        for(int i = 0; i < myUser.length; i++) {
            myUser[i] = userAttributes[i];
            if(i < 2)
                myEditTexts.get(i).setText(userAttributes[i]);
        }
        myEditTexts.get(2).setText(userAttributes[1]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_first_part);

        getComponents();
        createTableEditTexts();
        getListeners();
        getDataFromOtherActivities();

        myUserDAO = new UserDAO(this);
    }
}
