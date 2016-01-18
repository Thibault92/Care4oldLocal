package isep.project.care4old.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import isep.project.care4old.R;
import isep.project.care4old.dao.ComorbiditiesDAO;
import isep.project.care4old.extension.custom_adapter.ComorbiditiesListAdapter;
import isep.project.care4old.model.Comorbidities;

public class ComorbiditiesActivity extends AppCompatActivity {

    private final static int stackComorbidities = R.array.comorbidities_array;

    private ComorbiditiesDAO comorbiditiesDao ;
    private int myUserId ;
    private int idDoctor ;

    private AutoCompleteTextView searchBarComorbidties ;
    private ListView listOfComorbidities ;
    private Button addComorbidities,comorbiditiesBackProfile ;

    public final static String COMORBIDITIES_TO_PROFILE_PATIENT = "isep.project.care4Old.COMORBIDITIES_TO_PROFILE" ;
    public final static String COMORBIDITIES_TO_COMORBIDITIES = "isep.project.care4Old.COMORBIDITIES_TO_COMORBIDITIES" ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comorbidities);

        comorbiditiesDao = new ComorbiditiesDAO(this);

        getDataFromOtherActivities();
        getComponents();
        getListener();
        createArticlesWishlist();

    }

    private void getDataFromOtherActivities() {

        Intent intentReceived = getIntent();

        int[] myUserIdFromProfile = intentReceived.getIntArrayExtra(PatientProfileActivity.PROFILE_PATIENT_TO_COMORBIDITIES);
        int[] myUserIdFromComorbidities = intentReceived.getIntArrayExtra(ComorbiditiesActivity.COMORBIDITIES_TO_COMORBIDITIES);

        if(myUserIdFromProfile!=null && myUserIdFromProfile.length>0) {
            myUserId = myUserIdFromProfile[0];
            idDoctor = myUserIdFromProfile[1];
        }else if(myUserIdFromComorbidities!=null && myUserIdFromComorbidities.length>0) {
            myUserId = myUserIdFromComorbidities[0];
            idDoctor = myUserIdFromComorbidities[1];
        }

    }

    private void getComponents(){
        searchBarComorbidties = (AutoCompleteTextView) findViewById(R.id.searchBarComorbidties);
        addComorbidities = (Button) findViewById(R.id.addComorbidities);
        comorbiditiesBackProfile = (Button) findViewById(R.id.comorbiditiesBackProfile);
        listOfComorbidities = (ListView) findViewById(R.id.listOfComorbidities);
    }

    private View.OnClickListener toBackProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Intent nextActivity = new Intent(ComorbiditiesActivity.this, PatientProfileActivity.class);
            int [] extra = {myUserId,idDoctor} ;
            nextActivity.putExtra(COMORBIDITIES_TO_PROFILE_PATIENT, extra);
            startActivity(nextActivity);
        }
    };

    private View.OnClickListener toAddComorbiditiesListener = new View.OnClickListener() {

        @Override
        public void onClick(View v){

            if(checkAutoCompletion()) {
                if (comorbiditiesDao.checkComorbidities(myUserId, searchBarComorbidties.getText().toString())) {
                    comorbiditiesDao.ajouter(new Comorbidities(myUserId, searchBarComorbidties.getText().toString()));
                    Intent nextActivity = new Intent(ComorbiditiesActivity.this, ComorbiditiesActivity.class);
                    int [] extra = {myUserId,idDoctor} ;
                    nextActivity.putExtra(COMORBIDITIES_TO_COMORBIDITIES, extra);
                    startActivity(nextActivity);
                } else {
                    Toast.makeText(ComorbiditiesActivity.this, "Cette comorbidites existe déjà pour aujourd'hui", Toast.LENGTH_LONG).show();
                    searchBarComorbidties.getText().clear();
                }
            }
            else{
                Toast.makeText(ComorbiditiesActivity.this, "Entrez une Comorbidities présente dans la liste", Toast.LENGTH_LONG).show();
                searchBarComorbidties.getText().clear();
            }

        }
    };


    private boolean checkAutoCompletion(){

        for(String test : getResources().getStringArray(stackComorbidities)){
            if(test.equals(searchBarComorbidties.getText().toString()))
                return true;
        }
        return false ;
    }

    private void getListener(){
        addComorbidities.setOnClickListener(toAddComorbiditiesListener);
        comorbiditiesBackProfile.setOnClickListener(toBackProfileListener);
    }

    private void createArticlesWishlist(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,getResources().getStringArray(stackComorbidities));
        searchBarComorbidties.setAdapter(adapter);

        ComorbiditiesListAdapter adapter2 = new ComorbiditiesListAdapter (comorbiditiesDao.getComorbiditiesTestById(myUserId),this);
        listOfComorbidities.setAdapter(adapter2);

    }
}
