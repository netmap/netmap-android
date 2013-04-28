package edu.mit.csail.netmap.sensors;

import android.app.Application;
import android.content.Context;

/**
 * Entry point to the sensors package.
 */
public final class Sensors {
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
        
    // Config goes first, so every other module can use it right away.
    Config.initialize(context);
    // Recorder goes right after config, so the actual sensors can use it.
    Recorder.initialize(context);
    
    // Location goes before location sensors.
    Location.initialize(context);
    // Sensors get initialized here.
    Gps.initialize(context);
  }
  private static boolean initialized = false;
  
  /**
   * Collect the sensor reading data that will be stored in the database. 
   * 
   * @param jsonData {@link StringBuffer} that receives the reading data,
   *     formatted as a JSON string
   */
  public static void readSensors(StringBuffer jsonData) {
    jsonData.append("{");
    Config.getJsonFragment(jsonData);
    jsonData.append(",\"location\":");
    Location.getJson(jsonData);
    jsonData.append(",\"gps\":");
    Gps.getJson(jsonData);
    jsonData.append("}");
  }
}
