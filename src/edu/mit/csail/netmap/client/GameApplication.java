package edu.mit.csail.netmap.client;

import edu.mit.csail.netmap.sensors.Sensors;
import us.costan.chrome.ChromeView;
import android.app.Application;

/** Initializes native components when the user starts the application. */
public class GameApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        ChromeView.initialize(this);
        Sensors.initialize(this);
    }
}