package ntk.android.hackathon2015;

import android.os.AsyncTask;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.temboo.Library.Google.DistanceMatrix.DrivingDistanceMatrix;
import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by PhucNgo on 11/7/15.
 */
public class BackgroundGetDistanceInput extends AsyncTask<Void, Void, String> {
    private static DrivingDistanceMatrix drivingDistanceMatrixChoreo;
    private static DrivingDistanceMatrix.DrivingDistanceMatrixInputSet drivingDistanceMatrixInputs;
    public BackgroundGetDistanceInput() {

    }

    @Override
    protected String doInBackground(Void... params) {
        String[] array = new String[2];
        array[0] = "Philadelphia";
        array[1] = "New York";

        // Instantiate the Choreo, using a previously instantiated TembooSession object, eg:
        TembooSession session = null;
        try {
            session = new TembooSession("ptn32", "Project", "3a50a4154fb844b7afcedc1ce01a48f6");

            drivingDistanceMatrixChoreo = new DrivingDistanceMatrix(session);

            // Get an InputSet object for the choreo
            drivingDistanceMatrixInputs = drivingDistanceMatrixChoreo.newInputSet();

            // Set inputs
            drivingDistanceMatrixInputs.set_Destinations(array[0]); // address, city, state zip
            drivingDistanceMatrixInputs.set_Origins(array[1]); // current location in lat long

            return null;

        } catch (TembooException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        BackgroundGetDistanceOutput backgroundGetDistanceOutput = new BackgroundGetDistanceOutput();
        backgroundGetDistanceOutput.execute();
    }

    public static DrivingDistanceMatrix getDrivingDistanceMatrixChoreo() {
        return drivingDistanceMatrixChoreo;
    }

    public static DrivingDistanceMatrix.DrivingDistanceMatrixInputSet getDrivingDistanceMatrixInputs() {
        return drivingDistanceMatrixInputs;
    }
}
