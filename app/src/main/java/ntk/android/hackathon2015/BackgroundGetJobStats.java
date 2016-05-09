package ntk.android.hackathon2015;

import android.os.AsyncTask;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by PhucNgo on 11/7/15.
 */
public class BackgroundGetJobStats extends AsyncTask<Void, Void, String> {
    public BackgroundGetJobStats() {

    }

    @Override
    protected String doInBackground(Void... params) {
        String result = "";

        String query = "SIG";
        try {
            String connString = "http://api.glassdoor.com/api/api.htm?t.p=47552&t.k=iBClhSCAavG&userip=153.104.42.118&useragent=&format=json&v=1&action=employers&q=" + query;

            URL url = new URL(connString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();

            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(new InputStreamReader((InputStream) conn.getContent()));
            JsonObject rootObj = root.getAsJsonObject();

            JsonArray employers = rootObj.get("response").getAsJsonObject().get("employers").getAsJsonArray();
            JsonObject firstResult = employers.get(0).getAsJsonObject();

            result = "name: " + firstResult.get("name") + "&"
                    + "logo: " + firstResult.get("squareLogo") + "&"
                    + "overall ratings:" + firstResult.get("overallRatings") + "&"
                    + "culture and values rating: " + firstResult.get("culturalAndValuesRating") + "&"
                    + "senior leadership rating: " + firstResult.get("seniorLeadershipRating") + "&"
                    + "compensation and benefits rating: " + firstResult.get("compensationAndBenefitsRating") + "&"
                    + "career opportunities rating: " + firstResult.get("careerOpportunitiesRating") + "&"
                    + "work life balance rating: " + firstResult.get("workLifeBalanceRating");


        } catch (Exception e) {
            String msg = e.getMessage();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {

    }

}
