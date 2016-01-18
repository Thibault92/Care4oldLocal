package isep.project.care4old.extension.custom_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import isep.project.care4old.R;


public class PatientsListAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<String> myPatients = new ArrayList<>();
    private Context context;


    public PatientsListAdapter(ArrayList<String> listOfPatients, Context context) {
        this.myPatients = listOfPatients;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return myPatients.size();
    }

    @Override
    public Object getItem(int pos) {
        return myPatients.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_patients_list_layout, parent, false);
        }

        TextView patientsListText = (TextView)view.findViewById(R.id.list_patient_string);
        patientsListText.setText(myPatients.get(position));

        return view;
    }
}