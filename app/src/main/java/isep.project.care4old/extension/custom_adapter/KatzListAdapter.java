package isep.project.care4old.extension.custom_adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import isep.project.care4old.R;
import isep.project.care4old.activity.ConsultKatzActivity;
import isep.project.care4old.database.MyDatabase;
import isep.project.care4old.model.Katz;


public class KatzListAdapter extends BaseAdapter implements MyDatabase {

    public final static String KATZ_TO_KATZ = "isep.project.care4old.KATZ_TO_KATZ" ;

    private ArrayList<Katz> listOfTest = new ArrayList<>();
    private Context context;
    private int idUser;
    private int idDoctor;

    protected DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);

    public KatzListAdapter(ArrayList<Katz> listOfTest, Context context, int idUser,int idDoctor) {
        this.listOfTest = listOfTest;
        this.context = context;
        this.idUser = idUser;
        this.idDoctor = idDoctor ;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return listOfTest.size();
    }

    @Override
    public Object getItem(int pos) {
        return listOfTest.get(pos);
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
            view = inflater.inflate(R.layout.custom_tests_list_layout,parent, false);
        }

        TextView nortonTotal = (TextView)view.findViewById(R.id.testTotal);
        nortonTotal.setText(String.valueOf(listOfTest.get(position).getTotal()));

        TextView nortonDate = (TextView)view.findViewById(R.id.testDate);
        String dateN  = dateFormat.format(listOfTest.get(position).getDate());
        nortonDate.setText(dateN);

        Button detail = (Button)view.findViewById(R.id.testDetail);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(getContext(), ConsultKatzActivity.class);

                String[] extra = {String.valueOf(idUser),dateFormat.format(listOfTest.get(position).getDate()),String.valueOf(idDoctor)};

                nextActivity.putExtra(KATZ_TO_KATZ,extra) ;
                getContext().startActivity(nextActivity);
                notifyDataSetChanged();
            }
        });


        return view;
    }
}
