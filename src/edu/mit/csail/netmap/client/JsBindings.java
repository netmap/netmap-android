package edu.mit.csail.netmap.client;

import edu.mit.csail.netmap.sensors.Gps;
import us.costan.chrome.ChromeJavascriptInterface;

/** The bindings available as the "NetMapBindings" object. */
public class JsBindings {
  @ChromeJavascriptInterface
  public void gpsStart() {
    Gps.start();
  }
  
  @ChromeJavascriptInterface
  public void gpsStop() {
    Gps.stop();
  }
  
  @ChromeJavascriptInterface
  public String getGpsTrace() {
    StringBuffer buffer = new StringBuffer();
    Gps.getJson(buffer);
    return buffer.toString();
  }
}
