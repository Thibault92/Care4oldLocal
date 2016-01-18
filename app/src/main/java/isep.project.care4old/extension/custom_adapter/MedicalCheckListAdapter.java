package isep.project.care4old.extension.custom_adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import isep.project.care4old.R;
import isep.project.care4old.activity.ConsultMedicalCheckActivity;
import isep.project.care4old.database.MyDatabase;
import isep.project.care4old.model.MedicalCheck;

public class MedicalCheckListAdapter extends BaseAdapter implements MyDatabase {

    public static final String MEDADAPTER_TO_MEDCONSULT = "isep.project.care4Old.MEDADAPTER_TO_MEDCONSULT";

    private ArrayList<MedicalCheck> medicalChecklist = new ArrayList<>();
    private Context context;

    private int idPatient, idDoctor;

    public MedicalCheckListAdapter(int idPatient, int idDoctor, ArrayList<MedicalCheck> medicalChecklist, Context context) {
        this.medicalChecklist = medicalChecklist;
        this.context = context;
        this.idPatient = idPatient;
        this.idDoctor = idDoctor;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return medicalChecklist.size();
    }

    @Override
    public Object getItem(int pos) {
        return medicalChecklist.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_medcheck_list_layout, parent, false);
        }

        TextView listItemText = (TextView)view.findViewById(R.id.medcheck_item_list);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

        String infosMedical = dateFormat.format(medicalChecklist.get(position).getDateConsultation())+" - "+medicalChecklist.get(position).getDiagnostic() ;

        listItemText.setText(infosMedical);
        listItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondActivity = new Intent(getContext(), ConsultMedicalCheckActivity.class);
                secondActivity.putExtra(MEDADAPTER_TO_MEDCONSULT, new int[]{idPatient, idDoctor, medicalChecklist.get(position).getIdMedcheck()});
                getContext().startActivity(secondActivity);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}

