package edu.mit.csail.netmap.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import edu.mit.csail.netmap.sensors.Config;
import edu.mit.csail.netmap.sensors.EventClient;
import edu.mit.csail.netmap.sensors.Location;
import edu.mit.csail.netmap.sensors.MeasureCallback;
import edu.mit.csail.netmap.sensors.Recorder;
import edu.mit.csail.netmap.sensors.Sensors;
import us.costan.chrome.ChromeJavascriptInterface;
import us.costan.chrome.ChromeView;
import us.costan.chrome.ChromeCookieManager;

/** The bindings available as the "NetMap.Pil" object. */
public class JsBindings implements EventClient {
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
    Location.on();
  }
  
  @ChromeJavascriptInterface
  public void locationOff() {
    Location.off();
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
    Sensors.measureAsync(measurements, new MeasureCallback() {
      @Override
      public void done(final String digest) {
        activity.runOnUiThread(new Runnable() {
          public void run() {
            webView.loadUrl("javascript:_pil_cb." + callbackName +
                            "(\"" + digest + "\")");
          }
        });        
      }
    });
  }
  
  @ChromeJavascriptInterface
  public void uploadReadingPack(final String callbackName) {
    // TODO: uploadPack
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
    // TODO: setBackend
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
  
  /** Called by GameActivity to load saveCookie's cookies into the WebView. **/
  public void loadCookies() {
    ChromeCookieManager cookies = ChromeCookieManager.getInstance();
    String origin = preferences.getString("origin", "http://netmap.pwnb.us");
    String cookie = preferences.getString("cookies", ""); 
    cookies.setCookie(origin, cookie);
  }
  
  @Override
  public void onLocation() {
    activity.runOnUiThread(new Runnable() {
      public void run() {
        webView.loadUrl("javascript:_pil_ev.location()");
      }
    });
  }
}
