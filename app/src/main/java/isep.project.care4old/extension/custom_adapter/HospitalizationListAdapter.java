package isep.project.care4old.extension.custom_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import isep.project.care4old.R;
import isep.project.care4old.database.MyDatabase;


public class HospitalizationListAdapter extends BaseAdapter implements MyDatabase {

    private ArrayList<String> listOfHosps = new ArrayList<>();
    private Context context;



    public HospitalizationListAdapter(ArrayList<String> listOfHosps, Context context) {
        this.listOfHosps = listOfHosps;
        this.context = context;

    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return listOfHosps.size();
    }

    @Override
    public Object getItem(int pos) {
        return listOfHosps.get(pos);
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
            view = inflater.inflate(R.layout.custom_hosplist_layout, parent, false);
        }

        TextView listItemText = (TextView)view.findViewById(R.id.hosp_item_list);
        listItemText.setText(listOfHosps.get(position));

        return view;
    }

}

