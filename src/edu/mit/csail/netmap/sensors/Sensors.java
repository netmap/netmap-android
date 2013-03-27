package edu.mit.csail.netmap.sensors;

import android.app.Application;
import android.content.Context;

/**
 * Entry point to the sensors package.
 */
public class Sensors {
  /**
   * Sets up all the sensors.
   * 
   * This should be called once from {@link Application#onCreate()}.
   * 
   * @param context the application's Android context
   */
  public static void initialize(Context context) {
    if (initialized) return;
    initialized = true;
        
    Gps.initialize(context);
  }
  private static boolean initialized = false;
}
