package edu.mit.csail.netmap.sensors;

import java.net.URI;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/* Stores persistent configuration supplied by the game side. */
public class Config {
  /** Name used by the sensors preferences file. */
  private static final String PREFERENCES_NAME = "recorder";

  /** Persistent settings, such as the upload server URI. */
  private static SharedPreferences preferences_ = null;
  
  /** URI of the HTTP backend that receives sensor reading data. */
  private static URI uploadUri_ = null;
  
  /** Value of the HTTP Cookie header sent when uploading readings. */
  private static String uploadCookie_ = null;
  
  /** Value of the HTTP X-CSRF-Token header sent when uploading readings. */
  private static String uploadToken_ = null;

  
  /** The value of the 'uid' field in sensor reading data. */
  private static String uid_ = null;
  
  /** Called by {@link Sensors#initialize(android.content.Context)}. */
  public static final void initialize(Context context) {
    preferences_ = context.getSharedPreferences(
        PREFERENCES_NAME, Context.MODE_PRIVATE);
    uploadUri_ = URI.create(preferences_.getString("uploadUrl",
        "http://netmap.csail.mit.edu/net_readings/"));
    uploadCookie_ = preferences_.getString("uploadCookie", "");
    uploadToken_ = preferences_.getString("uploadToken", "");
  }
  
  /**
   * Sets the HTTP backend that receives the sensor readings.
   * 
   * The backend information will be persisted until a new backend is set by
   * calling this method.
   * 
   * @param url fully-qualified URL to the HTTP backkend that will receive
   *     readings as POST data
   * @param cookie the value for the HTTP Cookie header
   * @param csrfToken the value for the HTTP X-CSRF-Token header
   */
  public static final void setReadingsUploadBackend(String url, String cookie,
      String csrfToken) {
    // Check that the URL parses before making persistent changes.
    URI uploadUri = URI.create(url);
    
    Editor editor = preferences_.edit();
    editor.putString("uploadUrl", url);
    editor.putString("uploadCookie", cookie);
    editor.putString("uploadToken", csrfToken);
    editor.commit();
    
    uploadUri_ = uploadUri;
  }
  
  /**
   * The URL of the HTTP backend that receives the sensor readings.
   *
   * @return the URL of the HTTP backend that receives the sensor readings
   */
  public static final URI getReadingsUploadUri() {
    return uploadUri_;
  }
  
  /**
   * The value of the HTTP Cookie header sent when uploading sensor data.
   *
   * @return the value of the HTTP Cookie header sent when uploading sensor data
   */
  public static final String getReadingsUploadCookie() {
    return uploadCookie_;
  }

  /**
   * The value of the HTTP X-CSRF-Token header sent when uploading sensor data.
   *
   * @return the value of the HTTP X-CSRF-Token header sent when uploading
   *     sensor data
   */
  public static final String getReadingsUploadToken() {
    return uploadToken_;
  }


  /**
   * Sets the value of the "uid" field that will "stamp" all sensor readings.
   * 
   * This value will be persisted until a new value is set by calling this
   * method.
   * 
   * @param uid the value of the "uid" field in all future readings
   */
  public static final void setReadingsUid(String uid) {
    Editor editor = preferences_.edit();
    editor.putString("uid", uid);
    editor.commit();
    
    uid_ = uid;
  }
  
  /**
   * Writes a JSON representation of the sensor config to the given buffer.
   * 
   * @param jsonData a {@link StringBuffer} that receives a JSON representation
   *     of the GPS sensor data
   */
  public static final void getJson(StringBuffer buffer) {
    buffer.append("{\"uid\":\"");
    buffer.append(uid_);
    buffer.append(",\"timestamp\":");
    buffer.append(System.currentTimeMillis());
    buffer.append("}");
  }
}
