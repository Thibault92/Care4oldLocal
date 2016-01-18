package isep.project.care4old.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import isep.project.care4old.R;
import isep.project.care4old.dao.UserDAO;
import isep.project.care4old.model.User;

public class CreateAccountSecondPartActivity extends Activity {

    public final static String SECOND_TO_FIRST = "isep.project.care4old.inscription.second_to_first";
    public final static String SECOND_TO_THIRD = "isep.project.care4old.inscription.second_to_third";
    public final static String SECOND_TO_CONNECTION = "isep.project.care4old.inscription.second_to_connexion";

    private String[] myUser = null;
    private UserDAO myUserDAO;

    private Calendar birthdayUser = Calendar.getInstance();
    private String birthdayDisplay ;

    private EditText familyName = null;
    private EditText firstName = null;
    private EditText address = null;
    private EditText zip = null;
    private EditText city = null;
    private EditText homePhone = null;
    private EditText mobilePhone = null;
    private EditText emergencyPhone = null;

    private ArrayList<EditText> myEditTexts = null;

    private TextView birthday = null;

    private RadioGroup userType = null;

    private Button previous = null;
    private Button next = null;
    private Button raz = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_second_part);

        myUserDAO = new UserDAO(this);

        getComponents();
        createTableEditTexts();
        getListeners();
        getDataFromOtherActivities();


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
            if(checkAllEditTexts()) {
                if(checkPatterns()) {
                    updateMyUserFromThis();
                    goToChosenActivity(CreateAccountFirstPartActivity.class, SECOND_TO_FIRST);
                }
            }
        }
    };

    private View.OnClickListener nextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            if(checkAllEditTexts()) {
                if(checkPatterns()) {
                    updateMyUserFromThis();
                    if(myUser[16].equals("Un médecin"))
                        goDirectlyToConnection();
                    else
                        goToChosenActivity(CreateAccountThirdPartActivity.class, SECOND_TO_THIRD);
                }
            }
        }
    };

    private DatePickerDialog.OnDateSetListener birthdayPickerListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day){
            birthdayUser.set(year, month, day);
            birthdayDisplay = formDigits(birthdayUser.get(Calendar.DAY_OF_MONTH)) + "/" +
                    formDigits(birthdayUser.get(Calendar.MONTH) + 1) + "/" + birthdayUser.get(Calendar.YEAR);
            birthday.setText(birthdayDisplay);
        }
    };

    private View.OnClickListener birthdayListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int birthdayYear = 1940;
            int birthdayMonth = 6;
            int birthdayDay = 15;

            DatePickerDialog birthdayPicker = new DatePickerDialog(CreateAccountSecondPartActivity.this, birthdayPickerListener, birthdayYear, birthdayMonth, birthdayDay);
            birthdayPicker.show();
        }
    };

    private String formDigits(int number){
        return number<=9?"0"+number:String.valueOf(number);
    }

    public boolean checkAllEditTexts() {
        for (int i = 0; i < myEditTexts.size(); i++) {
            if(TextUtils.isEmpty(myEditTexts.get(i).getText())) {
                Toast.makeText(CreateAccountSecondPartActivity.this, "Le champ " + myEditTexts.get(i).getHint() + " n'est pas correctement rempli, veuillez vérifier vos informations.", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    public boolean checkPatterns(){
        return checkOnlyTextPattern(familyName.getText().toString()) &&
                checkOnlyTextPattern(firstName.getText().toString()) &&
                checkOnlyTextPattern(city.getText().toString()) &&
                checkZipPattern(zip.getText().toString()) &&
                checkHomePhonePattern(homePhone.getText().toString()) &&
                checkMobilePhonePattern(mobilePhone.getText().toString()) &&
                checkEmergencyPhone(emergencyPhone.getText().toString());
    }

    public boolean checkOnlyNumberPattern(String regex, String stringToMatch) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(stringToMatch);
        return matcher.matches();
    }

    public boolean checkOnlyTextPattern(String onlyTextFields) {
        if(!checkOnlyNumberPattern("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)", onlyTextFields)) {
            Toast.makeText(CreateAccountSecondPartActivity.this, "Les champs Nom, Prénom, Ville ne doivent contenir que des lettres.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean checkZipPattern(String zip) {
        if(!checkOnlyNumberPattern("\\d{5}", zip)) {
            Toast.makeText(CreateAccountSecondPartActivity.this, "Retapez un code postal valide.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean checkHomePhonePattern(String telMaison) {
        if(!checkOnlyNumberPattern("^((\\+|00)33\\s?|0)[1-5](\\s?\\d{2}){4}$", telMaison)) {
            Toast.makeText(CreateAccountSecondPartActivity.this, "Retapez un numéro fixe valide.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean checkMobilePhonePattern(String telMobile) {
        if(!checkOnlyNumberPattern("^((\\+|00)33\\s?|0)[679](\\s?\\d{2}){4}$", telMobile)) {
            Toast.makeText(CreateAccountSecondPartActivity.this, "Retapez un numéro de mobile valide.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean checkEmergencyPhone(String telUrgence) {
        if(!checkOnlyNumberPattern("^((\\+|00)33\\s?|0)[1-5](\\s?\\d{2}){4}$", telUrgence)) {
            if (!checkOnlyNumberPattern("^((\\+|00)33\\s?|0)[679](\\s?\\d{2}){4}$", telUrgence)) {
                Toast.makeText(CreateAccountSecondPartActivity.this, "Retapez un numéro de téléphone valide.", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    public void goToChosenActivity(Class destination, String key) {
        sendData(destination, key);
        clearEditTexts();
    }

    public void goDirectlyToConnection() {
        addUserIntoDatabase();
        sendData(AccountConnectionActivity.class, SECOND_TO_CONNECTION);
        clearEditTexts();
    }

    public void updateMyUserFromThis() {
        myUser[2] = familyName.getText().toString();
        myUser[3] = firstName.getText().toString();
        myUser[4] = birthdayDisplay ;
        myUser[5] = address.getText().toString();
        myUser[6] = "" + zip.getText().toString();
        myUser[7] = city.getText().toString();
        myUser[8] = homePhone.getText().toString();
        myUser[9] = mobilePhone.getText().toString();
        myUser[10] = emergencyPhone.getText().toString();

        myUser[16] = ((RadioButton)findViewById(userType.getCheckedRadioButtonId())).getText().toString();
    }

    public void addUserIntoDatabase () {
        User newUser = new User();
        newUser.setAttributes(myUser);
        myUserDAO.addUser(newUser);
    }

    public void sendData(Class destination, String key){
        Intent secondActivity = new Intent(CreateAccountSecondPartActivity.this, destination);
        secondActivity.putExtra(key, myUser);
        startActivity(secondActivity);
    }

    public void clearEditTexts() {
        for(int i = 0; i < myEditTexts.size(); i++)
            myEditTexts.get(i).getText().clear();
    }

    public void getComponents() {
        familyName = (EditText)findViewById(R.id.familyNameInput);
        firstName = (EditText)findViewById(R.id.firstNameInput);
        address = (EditText)findViewById(R.id.addressInput);
        zip = (EditText)findViewById(R.id.zipInput);
        city = (EditText)findViewById(R.id.cityInput);
        homePhone = (EditText)findViewById(R.id.homeTelInput);
        mobilePhone = (EditText)findViewById(R.id.mobileTelInput);
        emergencyPhone = (EditText)findViewById(R.id.emergencyTelInput);

        birthday = (TextView)findViewById(R.id.birthdayPicker);

        userType = (RadioGroup)findViewById(R.id.userTypeChoice);

        next = (Button)findViewById(R.id.nextCreation2);
        previous = (Button)findViewById(R.id.retourCreation2);
        raz = (Button)findViewById(R.id.raz);
    }

    public void createTableEditTexts(){
        myEditTexts = new ArrayList<>();
        myEditTexts.add(familyName);
        myEditTexts.add(firstName);
        myEditTexts.add(address);
        myEditTexts.add(zip);
        myEditTexts.add(city);
        myEditTexts.add(homePhone);
        myEditTexts.add(mobilePhone);
        myEditTexts.add(emergencyPhone);
    }

    public void getListeners(){
        birthday.setOnClickListener(birthdayListener);
        raz.setOnClickListener(razListener);
        next.setOnClickListener(nextListener);
        previous.setOnClickListener(previousListener);
    }

    public void getDataFromOtherActivities() {
        myUser = new String[18];

        Intent intentReceived = getIntent();
        String[] userAttributesFromFirst = intentReceived.getStringArrayExtra(CreateAccountFirstPartActivity.FIRST_TO_SECOND);
        String[] userAttributesFromThird = intentReceived.getStringArrayExtra(CreateAccountThirdPartActivity.THIRD_TO_SECOND);

        if(userAttributesFromFirst != null)
            updateMyUserFromOtherActivity(userAttributesFromFirst);

        else if(userAttributesFromThird != null)
            updateMyUserFromOtherActivity(userAttributesFromThird);
    }

    public void updateMyUserFromOtherActivity(String[] userAttributes) {
        for(int i = 0; i < myUser.length; i++) {
            myUser[i] = userAttributes[i];
            if(userAttributes[i] != null) {
                if(i == 2 || i == 3)
                    myEditTexts.get(i-2).setText(userAttributes[i]);
                if(i == 4)
                    birthday.setText(userAttributes[4]);
                if(i > 4 && i < 11)
                    myEditTexts.get(i-3).setText(userAttributes[i]);
            }
        }
    }
}