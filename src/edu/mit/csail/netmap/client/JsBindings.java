package edu.mit.csail.netmap.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import edu.mit.csail.netmap.sensors.Config;
import edu.mit.csail.netmap.sensors.Gps;
import edu.mit.csail.netmap.sensors.Location;
import edu.mit.csail.netmap.sensors.Recorder;
import edu.mit.csail.netmap.sensors.Sensors;
import us.costan.chrome.ChromeJavascriptInterface;
import us.costan.chrome.ChromeView;
import us.costan.chrome.ChromeCookieManager;

/** The bindings available as the "NetMap.Pil" object. */
public class JsBindings {
  /** The WebView using these bindings. */
  private final ChromeView webView;
  /** The activity that contains the WebView using these bindings. */
  private final GameActivity activity;
  
  private final SharedPreferences preferences;
  
  public JsBindings(ChromeView webView_, GameActivity activity_) {
    webView = webView_;
    activity = activity_;
    preferences = activity.getSharedPreferences("webview_session",
        Context.MODE_PRIVATE);
  }
  
  @ChromeJavascriptInterface
  public void locationOn() {
    Gps.start();
  }
  
  @ChromeJavascriptInterface
  public void locationOff() {
    Gps.stop();
  }
  
  @ChromeJavascriptInterface
  public String locationJson() {
    StringBuffer buffer = new StringBuffer();
    Location.getJson(buffer);
    return buffer.toString();
  }
  
  @ChromeJavascriptInterface
  public void startReading(final String measurements,
                           final String callbackName) {
    StringBuffer jsonData = new StringBuffer(); 
    Sensors.readSensors(jsonData);
    final String digest = Recorder.storeReading(jsonData.toString());
    
    activity.runOnUiThread(new Runnable() {
      public void run() {
        webView.loadUrl("javascript:_pil_cb." + callbackName +
                        "(\"" + digest + "\")");
      }
    });
  }
  
  @ChromeJavascriptInterface
  public void uploadReadingPack(final String callbackName) {
    boolean done = Recorder.uploadReadingPack();
    final String doneString = done ? "true" : "false";
    activity.runOnUiThread(new Runnable() {
      public void run() {
        webView.loadUrl("javascript:_pil_cb." + callbackName +
                        "(" + doneString + ")");
      }
    });
  }
  
  @ChromeJavascriptInterface
  public void setReadingsUploadBackend(String url, String uid) {
    Config.setReadingsUploadBackend(url, uid);
  }
  
  @ChromeJavascriptInterface
  public void saveCookies(String origin) {
    ChromeCookieManager cookies = ChromeCookieManager.getInstance();
    String cookie = cookies.getCookie(origin);
    
    Editor editor = preferences.edit();
    editor.putString("origin", origin);
    editor.putString("cookies", cookie);
    editor.commit();    
  }
  
  public void loadCookies() {
    ChromeCookieManager cookies = ChromeCookieManager.getInstance();
    String origin = preferences.getString("origin", "http://netmap.pwnb.us");
    String cookie = preferences.getString("cookies", ""); 
    cookies.setCookie(origin, cookie);
  }
}
