package isep.project.care4old.extension;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import isep.project.care4old.R;


public class FragmentQuestion extends Fragment {

    private int value ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_questionnary, container, false);

        TextView Question = (TextView) v.findViewById(R.id.textQuestion);
        Question.setText(getArguments().getString("textQuestion"));

        TextView numero = (TextView) v.findViewById(R.id.numeroQuestion);
        numero.setText(getArguments().getString("numeroQuestion"));

        ListView mListHabillage = (ListView) v.findViewById(R.id.listQuestion);
        mListHabillage.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_single_choice, getArguments().getStringArray("listQuestion")));
        mListHabillage.setItemChecked(0, true);

        return v;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView lv = (ListView) getActivity().findViewById(R.id.listQuestion);
        value = lv.getCheckedItemPosition() ;
    }


    public int getValue(){
        return value ;
    }

    public static FragmentQuestion newInstance(String text, String text2, String[] textlist) {

        FragmentQuestion f = new FragmentQuestion();
        Bundle b = new Bundle();
        b.putString("textQuestion", text);
        b.putString("numeroQuestion", text2);
        b.putStringArray("listQuestion", textlist);

        f.setArguments(b);

        return f;
    }

}