package ntk.android.hackathon2015;

import android.os.AsyncTask;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.temboo.Library.Google.DistanceMatrix.DrivingDistanceMatrix;
import com.temboo.core.TembooException;

/**
 * Created by PhucNgo on 11/7/15.
 */
public class BackgroundGetDistanceOutput extends AsyncTask<Void, Void, String[]> {
    public BackgroundGetDistanceOutput() {

    }

    @Override
    protected String[] doInBackground(Void... params) {
        DrivingDistanceMatrix drivingDistanceMatrixChoreo = BackgroundGetDistanceInput.getDrivingDistanceMatrixChoreo();
        DrivingDistanceMatrix.DrivingDistanceMatrixInputSet drivingDistanceMatrixInputSet = BackgroundGetDistanceInput.getDrivingDistanceMatrixInputs();

        String[] array = new String[3];
        try {
            // Execute Choreo
            DrivingDistanceMatrix.DrivingDistanceMatrixResultSet drivingDistanceMatrixResults = drivingDistanceMatrixChoreo.execute(drivingDistanceMatrixInputSet);

            String dur = drivingDistanceMatrixResults.get_Duration();

            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(drivingDistanceMatrixResults.get_Response());
            JsonObject rootObj = root.getAsJsonObject();

            String orig = rootObj.get("origin_addresses").getAsJsonArray().get(0).getAsString();
            String dest = rootObj.get("destination_addresses").getAsJsonArray().get(0).getAsString();

            array[0] = dur;
            array[1] = orig;
            array[2] = dest;

            return array;

        } catch (TembooException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String[] result) {

    }
}
