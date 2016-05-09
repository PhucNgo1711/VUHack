package ntk.android.hackathon2015;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseQueryAdapter;

public class JobOptionsFragment extends Fragment {
    private ListView mListView;

    public JobOptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_joboptions, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        mListView = (ListView) getActivity().findViewById(R.id.jobOptionListView);
        GetOptionsList();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void GetOptionsList() {
        MainActivity m = (MainActivity) getActivity();
        mListView.setAdapter(m.getRunner().jobOptionsAdapter);
    }

//    public class CustomAdapter extends ParseQueryAdapter<ParseObject> {
//
//        public CustomAdapter(Context context) {
//            super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
//                public ParseQuery create() {
//                    ParseQuery<ParseObject> query = ParseUser.getCurrentUser().getRelation("options").getQuery();
//                    return query;
//                }
//            });
//        }
//
//        @Override
//        public View getItemView(ParseObject object, View v, ViewGroup parent) {
//            super.getItemView(object, v, parent);
//            ViewHolder holder;
//            if (v == null) {
//                holder =new ViewHolder();
//                v = View.inflate(getContext(), R.layout.custom_list_item1, null);
//                holder.TextView1 = (TextView) v.findViewById(R.id.text1);
//                holder.TextView2 = (TextView) v.findViewById(R.id.text2);
//                v.setTag(holder);
//            }else{
//                holder = (ViewHolder) v.getTag();
//            }
//
//            holder.TextView1.setText(object.getString("position"));
//            holder.TextView2.setText(object.getParseObject("company").getString("name"));
//            return v;
//        }
//
//        class ViewHolder{
//            TextView TextView1;
//            TextView TextView2;
//        }
//    }
}