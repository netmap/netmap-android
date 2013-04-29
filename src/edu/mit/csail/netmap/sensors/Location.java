package edu.mit.csail.netmap.sensors;

import android.content.Context;

public final class Location {
  /** The most recent location passed to onLocationChanged. */
  private static android.location.Location lastLocation;
  
  /** Called by {@link Sensors#initialize(android.content.Context)}. */
  public static void initialize(Context context) {
    
  }
  
  /**
   * Writes a JSON representation of the location data to the given buffer.
   * 
   * @param buffer a {@link StringBuffer} that receives a JSON representation of
   *     the GPS sensor data
   */
  public static void getJson(StringBuffer buffer) {
    // Cache the location we'll report, to avoid race conditions.
    android.location.Location location = lastLocation; 
    
    if (location == null) {
      buffer.append("{}");
      return;
    }
    buffer.append("{\"latitude\":");
    buffer.append(location.getLatitude());
    buffer.append(",\"longitude\":");
    buffer.append(location.getLongitude());
    buffer.append(",\"provider\":\"");
    buffer.append(location.getProvider());
    buffer.append("\",\"timestamp\":");
    buffer.append(location.getTime());
    if (location.hasAccuracy()) {
      buffer.append(",\"accuracy\":");
      buffer.append(location.getAccuracy());
    }
    if (location.hasAltitude()) {
      buffer.append(",\"altitude\":");
      buffer.append(location.getAltitude());
    }
    if (location.hasBearing()) {
      // NOTE: calling this "heading" for consistency with HTML5 Geolocation
      buffer.append(",\"heading\":");
      buffer.append(location.getBearing());
    }
    if (location.hasSpeed()) {
      buffer.append(",\"speed\":");
      buffer.append(location.getSpeed());
    }
    buffer.append("}");
  }
  
  /**
   * Called by various sensors' listeners to report location updates.
   * 
   * @param location Android's most recent location estimate
   */
  static void onLocationChanged(android.location.Location location) {
    lastLocation = location;
    
    // TODO(pwnall): historic info
  }
}
