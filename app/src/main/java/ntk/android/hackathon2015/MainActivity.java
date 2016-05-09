package ntk.android.hackathon2015;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    boolean mReadMode = false;
    private NfcAdapter mNfcAdapter;
    private PendingIntent mNfcPendingIntent;
    private String tagResult = "";
    Fragment nfcFragment = null;

    public Runner runner = new Runner(this);
    public Runner getRunner(){
        return this.runner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            // Call the Parse log out method
            ParseUser.logOut();
            // Start and intent for the dispatch activity
            Intent intent = new Intent(MainActivity.this, DispatchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }

//        if(id == R.id.action_search){
//            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new JobOptionsFragment();
                title = getString(R.string.title_joboptions);
                break;
            case 2:
                fragment = new PreferencesFragment();
                title = getString(R.string.title_preferences);
                break;
            case 3:
                mNfcAdapter = NfcAdapter.getDefaultAdapter(MainActivity.this);
                mNfcPendingIntent = PendingIntent.getActivity(MainActivity.this, 0,
                        new Intent(MainActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

                enableTagReadMode();

                new AlertDialog.Builder(MainActivity.this).setTitle("Touch tag to read")
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                disableTagReadMode();
                            }

                        }).create().show();

                fragment = new NFCFragment();
                nfcFragment = fragment;

                title = getString(R.string.title_nfc);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    private void enableTagReadMode() {
        mReadMode = true;
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter[] mReadTagFilters = new IntentFilter[] { tagDetected };
        mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mReadTagFilters, null);
    }

    private void disableTagReadMode() {
        mReadMode = false;
        mNfcAdapter.disableForegroundDispatch(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onNewIntent(Intent intent) {
        // Tag writing mode
        if (mReadMode && NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            Ndef ndef = Ndef.get(detectedTag);
            if (ndef == null) {
                // NDEF is not supported by this Tag.
                Toast.makeText(this, "NULL", Toast.LENGTH_LONG).show();
            }

            Parcelable[] parcelables = intent.getParcelableArrayExtra(mNfcAdapter.EXTRA_NDEF_MESSAGES);

            if (parcelables != null && parcelables.length > 0) {
                try {
                    readText((NdefMessage) parcelables[0]);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void readText(NdefMessage ndefMessage) throws UnsupportedEncodingException {
        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if (ndefRecords != null && ndefRecords.length > 0) {
            NdefRecord ndefRecord = ndefRecords[0];

            String tagContent = getTextFromNdefRecord(ndefRecord);

            //String tagContent= new String (ndefRecords[0].getPayload());
            //String tagContent = ndefRecords[0].toString();

            //Toast.makeText(this, tagContent, Toast.LENGTH_LONG).show();

            parse(tagContent);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getTextFromNdefRecord(NdefRecord ndefRecord)
    {
        byte[] payload = ndefRecord.getPayload();

        try {
            String result = new String(payload);
            return result;
        }
        catch (Exception e) {
            String tmp = e.getMessage();
            return null;
        }
    }

    public void parse(String tagContent) {
        StringBuilder sb = new StringBuilder(tagContent);
        sb.deleteCharAt(0);
        sb.deleteCharAt(1);
        sb.deleteCharAt(0);
        String content = sb.toString();

        JsonParser parser = new JsonParser();
        JsonElement root = parser.parse(content);
        JsonObject rootObj = root.getAsJsonObject();

         tagResult = "Position: " + rootObj.get("p").getAsString() + "&"
                + "Salary: " + rootObj.get("s").getAsString() + "&"
                + "Company: " + rootObj.get("c").getAsString() + "&"
                + "City: " + rootObj.get("city").getAsString() + "&"
                + "Option Stage: " + rootObj.get("optionStage").getAsString();


//        Bundle bundle = new Bundle();
//        bundle.putString("Main", tagResult);
//        try {
//            nfcFragment.setArguments(bundle);
//        }
//        catch (Exception e) {
//            String er = e.getMessage();
//        }

        String[] args = tagResult.split("&");

        ParseObject obj = new ParseObject("option");

        String position = args[0].split(":")[0];
        String positionVal = args[0].split(":")[1];

        String salary = args[1].split(":")[0];
        String salaryVal = args[1].split(":")[1];

        String company = args[2].split(":")[0];
        String companyVal = args[2].split(":")[1];

        String city = args[3].split(":")[0];
        String cityVal = args[3].split(":")[1];

        String optionStage = args[4].split(":")[0];
        String optionStageVal = args[4].split(":")[1];

        obj.put(position, positionVal);
        obj.put(salary, salaryVal);
        obj.put(company, companyVal);
        obj.put(city, cityVal);
        obj.put(optionStage, optionStageVal);

        obj.saveInBackground();

        Toast.makeText(this, "Job Info Saved", Toast.LENGTH_LONG).show();

    }
}