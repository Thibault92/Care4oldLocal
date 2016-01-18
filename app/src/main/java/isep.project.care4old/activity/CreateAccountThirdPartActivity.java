package isep.project.care4old.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import isep.project.care4old.R;


public class CreateAccountThirdPartActivity extends Activity {

    public final static String THIRD_TO_SECOND = "isep.project.care4old.inscription.third_to_second";
    public final static String THIRD_TO_DOCTOR_LINK = "isep.project.care4old.inscription.third_to_doctor_link";

    private RadioGroup gender = null;
    private RadioGroup status = null;
    private RadioGroup residency = null;
    private RadioGroup financialHelp = null;
    private RadioGroup homeHelp = null;

    private Button previous = null;
    private Button next = null;

    private String[] myUser = null;

    private View.OnClickListener previousListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            updateMyUserFromThis();
            sendData(CreateAccountSecondPartActivity.class, THIRD_TO_SECOND);
        }
    };

    private View.OnClickListener nextListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            updateMyUserFromThis();
            sendData(CreateAccountPatientActivity.class, THIRD_TO_DOCTOR_LINK);

        }
    };

    public void updateMyUserFromThis() {
        myUser[11] = ((RadioButton)findViewById(gender.getCheckedRadioButtonId())).getText().toString();
        myUser[12] = ((RadioButton)findViewById(status.getCheckedRadioButtonId())).getText().toString();
        myUser[13] = ((RadioButton)findViewById(residency.getCheckedRadioButtonId())).getText().toString();
        myUser[14] = ((RadioButton)findViewById(financialHelp.getCheckedRadioButtonId())).getText().toString();
        myUser[15] = ((RadioButton)findViewById(homeHelp.getCheckedRadioButtonId())).getText().toString();
    }

    public void sendData(Class destination, String key){
        Intent secondActivity = new Intent(CreateAccountThirdPartActivity.this, destination);
        secondActivity.putExtra(key, myUser);
        startActivity(secondActivity);
    }

    public void getComponents() {
        gender = (RadioGroup)findViewById(R.id.genderChoice);
        status = (RadioGroup)findViewById(R.id.statusChoice);
        residency = (RadioGroup)findViewById(R.id.residencyChoice);
        financialHelp = (RadioGroup)findViewById(R.id.financialChoice);
        homeHelp = (RadioGroup)findViewById(R.id.domicileChoice);

        next = (Button)findViewById(R.id.nextCreation3);
        previous = (Button)findViewById(R.id.retourCreation3);
    }

    public void getListeners(){
        next.setOnClickListener(nextListener);
        previous.setOnClickListener(previousListener);
    }

    public void getDataFromOtherActivities() {
        myUser = new String[18];

        Intent intentReceived = getIntent();
        String[] userAttributesFromSecond = intentReceived.getStringArrayExtra(CreateAccountSecondPartActivity.SECOND_TO_THIRD);
        String[] userAttributesFromDoctorLink = intentReceived.getStringArrayExtra(CreateAccountPatientActivity.DOCTOR_LINK_TO_THIRD);

        if(userAttributesFromSecond != null)
            updateMyUserFromOtherActivity(userAttributesFromSecond);

        else if(userAttributesFromDoctorLink != null)
            updateMyUserFromOtherActivity(userAttributesFromDoctorLink);
    }

    public void updateMyUserFromOtherActivity(String[] userAttributes) {
        for(int i = 0; i < myUser.length; i++) {
            myUser[i] = userAttributes[i];
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_third_part);

        getComponents();
        getListeners();
        getDataFromOtherActivities();
    }
}
