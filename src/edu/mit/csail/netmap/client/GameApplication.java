package edu.mit.csail.netmap.client;

import us.costan.chrome.ChromeView;
import android.app.Application;

public class GameApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        ChromeView.initialize(this);
    }
}