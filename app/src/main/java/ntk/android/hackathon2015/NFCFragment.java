package ntk.android.hackathon2015;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

import java.beans.IndexedPropertyChangeEvent;

import ntk.android.hackathon2015.R;

public class NFCFragment extends Fragment {

    public NFCFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nfc, container, false);

        try {
            String tagResult = getArguments().getString("Main");

            String[] args = tagResult.split("&");

            ParseObject obj = new ParseObject("option");

            // position
            obj.put(args[0].split(":")[0], args[0].split(":")[1]);
            // salary
            obj.put(args[1].split(":")[0], args[1].split(":")[1]);
            // company
            obj.put(args[2].split(":")[0], args[2].split(":")[1]);
            // city
            obj.put(args[3].split(":")[0], args[3].split(":")[1]);
            // option stage
            obj.put(args[4].split(":")[0], args[4].split(":")[1]);
        }
        catch (Exception e) {

        }

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}