package edu.mit.csail.netmap.client;

import edu.mit.csail.netmap.sensors.Config;
import edu.mit.csail.netmap.sensors.Gps;
import edu.mit.csail.netmap.sensors.Recorder;
import edu.mit.csail.netmap.sensors.Sensors;
import us.costan.chrome.ChromeJavascriptInterface;

/** The bindings available as the "NetMap.Pil" object. */
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
  public String gpsInfoJson() {
    StringBuffer buffer = new StringBuffer();
    Gps.getJson(buffer);
    return buffer.toString();
  }
  
  @ChromeJavascriptInterface
  public void readSensor() {
    StringBuffer jsonData = new StringBuffer(); 
    Sensors.readSensors(jsonData);
    Recorder.storeReading(jsonData.toString());
  }
  
  @ChromeJavascriptInterface
  public void uploadReadingPack() {
    Recorder.uploadReadingPack();
  }
  
  @ChromeJavascriptInterface
  public void setReadingsUploadBackend(String url, String cookie,
      String csrfToken) {
    Config.setReadingsUploadBackend(url, cookie, csrfToken);
  }
}
