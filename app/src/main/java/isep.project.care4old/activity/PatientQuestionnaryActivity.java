package isep.project.care4old.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import isep.project.care4old.R;
import isep.project.care4old.dao.FragilityDAO;
import isep.project.care4old.dao.KatzDAO;
import isep.project.care4old.dao.LawtonDAO;
import isep.project.care4old.dao.MnaDAO;
import isep.project.care4old.dao.NortonDAO;
import isep.project.care4old.dao.ResultTestDAO;
import isep.project.care4old.extension.FragmentQuestion;
import isep.project.care4old.extension.Questionnary;
import isep.project.care4old.model.ResultTest;


public class PatientQuestionnaryActivity extends FragmentActivity {

    private int idUser ;
    private int idDoctor ;

    private ArrayList<FragmentQuestion> listeF = new ArrayList<>();
    private Button mSend = null, mBack=null;
    private ViewPager pager = null ;

    private ResultTest resultTestModel;

    private KatzDAO katzDAO;
    private LawtonDAO lawtonDAO;
    private MnaDAO mnaDAO;
    private NortonDAO nortonDAO;
    private FragilityDAO fragilityDAO;
    private ResultTestDAO resultTestDAO ;

    public final static String PATIENT_QUESTIONNARY_TO_PROFILE_PATIENT = "isep.project.care4Old.QUESTIONNARY_TO_PROFILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_questionnary);

        katzDAO = new KatzDAO(this);
        lawtonDAO = new LawtonDAO(this);
        mnaDAO = new MnaDAO(this);
        nortonDAO = new NortonDAO(this);
        fragilityDAO = new FragilityDAO(this) ;
        resultTestDAO = new ResultTestDAO(this) ;

        getDataFromOtherActivities();
        getComponents();
        getListener();
    }

    private void getDataFromOtherActivities() {

        Intent intentReceived = getIntent();

        int[] idFromProfilePatient = intentReceived.getIntArrayExtra(PatientProfileActivity.PROFILE_PATIENT_TO_PATIENT_QUESTIONNARY) ;

        if(idFromProfilePatient !=null && idFromProfilePatient .length>0) {
            idUser = idFromProfilePatient[0];
            idDoctor = idFromProfilePatient[1];
        }

    }

    private void getComponents(){
        pager = (ViewPager) findViewById(R.id.viewPager);
        mSend = (Button) findViewById(R.id.send);
        mBack = (Button) findViewById(R.id.back);
    }

    private View.OnClickListener toShowResultsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            Intent nextActivity = new Intent(PatientQuestionnaryActivity.this, PatientProfileActivity.class);

            int[] resultat = new int[20];

            for (int i = 2; i < listeF.size();i++) {
                FragmentQuestion f = listeF.get(i);
                resultat[i-2]=f.getValue();
            }

            if(idUser>0) {
                resultTestModel = new ResultTest(idUser);
                resultTestModel.processing(resultat);
                addDatabase();
            }
            int [] extra = {idUser,idDoctor} ;
            nextActivity.putExtra(PATIENT_QUESTIONNARY_TO_PROFILE_PATIENT, extra);
            startActivity(nextActivity);
        }
    };

    private View.OnClickListener toBackProfileListener = new View.OnClickListener() {
        @Override
        public void onClick(View v){

            Intent nextActivity = new Intent(PatientQuestionnaryActivity.this, PatientProfileActivity.class);
            int [] extra = {idUser,idDoctor} ;
            nextActivity.putExtra(PATIENT_QUESTIONNARY_TO_PROFILE_PATIENT,extra);
            startActivity(nextActivity);
        }
    };

    private void addDatabase(){
        katzDAO.ajouter(resultTestModel.getKatz());
        lawtonDAO.ajouter(resultTestModel.getLawton());
        mnaDAO.ajouter(resultTestModel.getMna());
        nortonDAO.ajouter(resultTestModel.getNorton());
        fragilityDAO.ajouter(resultTestModel.getFragility());
        resultTestDAO.ajouter(resultTestModel);
    }

    private void getListener(){
        mSend.setOnClickListener(toShowResultsListener);
        mBack.setOnClickListener(toBackProfileListener);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
    }

    private class MyPagerAdapter extends FragmentPagerAdapter implements Questionnary{

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public FragmentQuestion getItem(int pos) {
            switch(pos) {

                case 0:
                    FragmentQuestion f1 = FragmentQuestion.newInstance(Question1,pos+1+"/"+NB,Reponse1);
                    listeF.add(f1);
                    return f1;
                case 1:
                    FragmentQuestion f2 = FragmentQuestion.newInstance(Question2,pos+1+"/"+NB,Reponse2);
                    listeF.add(f2);
                    return f2;
                case 2:
                    FragmentQuestion f3 = FragmentQuestion.newInstance(Question3,pos+1+"/"+NB, Reponse3);
                    listeF.add(f3);
                    return f3;
                case 3:
                    FragmentQuestion f4 = FragmentQuestion.newInstance(Question4,pos+1+"/"+NB, Reponse4);
                    listeF.add(f4);
                    return f4;
                case 4:
                    FragmentQuestion f5 = FragmentQuestion.newInstance(Question5,pos+1+"/"+NB, Reponse5);
                    listeF.add(f5);
                    return f5;
                case 5:
                    FragmentQuestion f6 = FragmentQuestion.newInstance(Question6,pos+1+"/"+NB, Reponse6);
                    listeF.add(f6);
                    return f6 ;
                case 6:
                    FragmentQuestion f7 = FragmentQuestion.newInstance(Question7,pos+1+"/"+NB, Reponse7);
                    listeF.add(f7);
                    return f7 ;
                case 7:
                    FragmentQuestion f8 = FragmentQuestion.newInstance(Question8,pos+1+"/"+NB, Reponse8);
                    listeF.add(f8);
                    return f8 ;
                case 8:
                    FragmentQuestion f9 = FragmentQuestion.newInstance(Question9,pos+1+"/"+NB, Reponse9);
                    listeF.add(f9);
                    return f9 ;
                case 9:
                    FragmentQuestion f10 = FragmentQuestion.newInstance(Question10,pos+1+"/"+NB, Reponse10);
                    listeF.add(f10);
                    return f10 ;
                case 10:
                    FragmentQuestion f11 = FragmentQuestion.newInstance(Question11,pos+1+"/"+NB, Reponse11);
                    listeF.add(f11);
                    return f11 ;
                case 11:
                    FragmentQuestion f12 = FragmentQuestion.newInstance(Question12,pos+1+"/"+NB, Reponse12);
                    listeF.add(f12);
                    return f12 ;
                case 12:
                    FragmentQuestion f13 = FragmentQuestion.newInstance(Question13,pos+1+"/"+NB, Reponse13);
                    listeF.add(f13);
                    return f13 ;
                case 13:
                    FragmentQuestion f14 = FragmentQuestion.newInstance(Question14,pos+1+"/"+NB, Reponse14);
                    listeF.add(f14);
                    return f14 ;
                case 14:
                    FragmentQuestion f15 = FragmentQuestion.newInstance(Question15,pos+1+"/"+NB, Reponse15);
                    listeF.add(f15);
                    return f15 ;
                case 15:
                    FragmentQuestion f16 = FragmentQuestion.newInstance(Question16,pos+1+"/"+NB, Reponse16);
                    listeF.add(f16);
                    return f16 ;
                case 16:
                    FragmentQuestion f17 = FragmentQuestion.newInstance(Question17,pos+1+"/"+NB, Reponse17);
                    listeF.add(f17);
                    return f17 ;
                case 17:
                    FragmentQuestion f18 = FragmentQuestion.newInstance(Question18,pos+1+"/"+NB, Reponse18);
                    listeF.add(f18);
                    return f18 ;
                case 18:
                    FragmentQuestion f19 = FragmentQuestion.newInstance(Question19,pos+1+"/"+NB, Reponse19);
                    listeF.add(f19);
                    return f19 ;
                case 19:
                    FragmentQuestion f20 = FragmentQuestion.newInstance(Question20,pos+1+"/"+NB, Reponse20);
                    listeF.add(f20);
                    return f20 ;
                case 20:
                    FragmentQuestion f21 = FragmentQuestion.newInstance(Question21,pos+1+"/"+NB, Reponse21);
                    listeF.add(f21);
                    return f21 ;
                default:
                    return new FragmentQuestion();
            }
        }

        @Override
        public int getCount() {
            return 21;
        }
    }


}
