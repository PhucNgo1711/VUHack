package ntk.android.hackathon2015;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ntk on 11/8/2015.
 */
public class Runner {
    private MatchCalculator matchCalculator;
    private ParseUser user =  ParseUser.getCurrentUser();

    //private ParseObject userPreferences;
    public List<ParseObject> cities = new ArrayList<ParseObject>();
    public JobOptionsAdapter jobOptionsAdapter;
    public MainActivity mainActivity;

    public Runner(Activity activity){
        queryForUserPreferences();
        mainActivity = (MainActivity) activity;
        jobOptionsAdapter = new JobOptionsAdapter(mainActivity);
    }

    public void queryForUserPreferences(){
        //userPreferences = ParseUser.getCurrentUser().getParseObject("userPreferences");
    }

    public void calculateMatches(){
        List<ParseObject> jobOptions = new ArrayList<ParseObject>();
        for(int i=0; i<jobOptionsAdapter.getCount()-1; i++){
            ParseObject op = jobOptionsAdapter.getItem(i);
            int score = 10;
            //int score = MatchCalculator(userPreferences,op);
            op.put("score", score);
        }
        ParseObject.saveAllInBackground(jobOptions);
        jobOptionsAdapter.loadObjects();
    }
}
