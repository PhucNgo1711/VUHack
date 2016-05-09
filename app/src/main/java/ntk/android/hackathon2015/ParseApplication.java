package ntk.android.hackathon2015;

import android.app.Application;

import com.parse.Parse;

//import com.parse.ParseCrashReporting;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Add your initialization code here
        Parse.initialize(this, "Cata7RXGKm4YnXBarHBRDvuUC89CgWHJEy6B7Ijs", "mhrKnwluWJgvVhV69EUx0OUGLEjEmPnP4MR27NPp");
    }
}
