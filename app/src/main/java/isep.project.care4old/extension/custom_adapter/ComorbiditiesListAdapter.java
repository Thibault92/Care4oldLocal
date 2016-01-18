package isep.project.care4old.extension.custom_adapter;

import android.content.Context;
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
import isep.project.care4old.database.MyDatabase;
import isep.project.care4old.model.Comorbidities;


public class ComorbiditiesListAdapter extends BaseAdapter implements MyDatabase {

    private ArrayList<Comorbidities> listOfComorbidities = new ArrayList<>();
    private Context context ;

    protected DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);


    public ComorbiditiesListAdapter(ArrayList<Comorbidities> listOfComorbidities,Context context) {
        this.listOfComorbidities = listOfComorbidities;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return listOfComorbidities.size();
    }

    @Override
    public Object getItem(int pos) {
        return listOfComorbidities.get(pos);
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
            view = inflater.inflate(R.layout.custom_comorbidities_list_layout,parent, false);
        }

        TextView comorbiditiesPathology = (TextView)view.findViewById(R.id.comorbiditiesPathology);
        comorbiditiesPathology.setText(String.valueOf(listOfComorbidities.get(position).getPathology()));

        TextView comorbiditiesDate = (TextView)view.findViewById(R.id.comorbiditiesDate);
        String dateN  = dateFormat.format(listOfComorbidities.get(position).getDate());
        comorbiditiesDate.setText(dateN);

        return view;
    }
}