package edu.mit.csail.netmap.sensors;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

public final class WiFi {
  /** Entry point to Android's WiFi functionality. */
  private static WifiManager wifiManager;
  
  private static WifiReceiver wifiReceiver;
  
  private static WifiLock wifiLock;
  
  private static WifiInfo wifiInfo;
  private static List<ScanResult> scanResults;
  
  /** True when the WiFi is enabled by the user. */
  private static boolean enabled = false;
  
  /** True when the WiFi is powered up and reporting information. - not being used now*/
  private static boolean started = false;
  
  /** True when listening for WiFi updates. */
  private static boolean listening = false;
  
  private static Context context;
  
  /** Called by {@link Sensors#initialize(android.content.Context)}. */
  public static void initialize(Context context) {
    context = context;
    wifiManager = 
      (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
    wifiReceiver = new WifiReceiver();
    wifiLock = wifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL_HIGH_PERF, "WiFiSilentScanLock");
    wifiLock.acquire();
  }
  
  /**
   * Starts listening for location updates.
   * 
   * This should be called when your application / activity becomes active.
   */
  public static void start() {
    if (listening) return;
    context.registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)); 
    // We only get onProviderDisabled() when we start listening.
    enabled = isEnabled();
    wifiManager.startScan();
    listening = true;
  }

  /** 
   * Stops listening for location updates.
   * 
   * This should be called when your application / activity is no longer active.
   */
  public static void stop() {
    if (!listening) return;
    context.unregisterReceiver(wifiReceiver);
    wifiLock.release();
    listening = false;
  }
  
  /**
   * Checks if the user's preferences allow the use of GPS.
   * 
   * @return true if the user lets us use GPS
   */
  public static boolean isEnabled() {
    return wifiManager.isWifiEnabled();
  }
  
  /**
   * Writes a JSON representation of the GPS data to the given buffer.
   * 
   * @param buffer a {@link StringBuffer} that receives a JSON representation of
   *     the GPS sensor data
   */
  public static void getJson(StringBuffer buffer) {
    buffer.append("{\"enabled\":");
    if (enabled) { buffer.append("true"); } else { buffer.append("false"); }
    /**if (started) {
      buffer.append(",\"started\": true");
    }**/
    if (wifiInfo != null){
      buffer.append(",\"ssid\":");
      buffer.append(wifiInfo.getSSID());
      buffer.append(",\"bssid\":");
      buffer.append(wifiInfo.getBSSID());
      buffer.append(",\"rssi\":");
      buffer.append(wifiInfo.getRssi());
      buffer.append(",\"linkSpeed\":");
      buffer.append(wifiInfo.getLinkSpeed());   
    }
    
    if (scanResults != null) {
      buffer.append(",\"numOfAps\":");
      buffer.append(scanResults.size());
      buffer.append(",\"aps\":[");
      
      boolean firstElement = true;
      for (int i = 0; i < scanResults.size(); i++) {
        if (firstElement) {
          firstElement = false;
          buffer.append("{\"ssid\":");
        } else {
          buffer.append(",{\"ssid\":");
        }
        buffer.append(scanResults.get(i).SSID);
        buffer.append(",\"bssid\":");
        buffer.append(scanResults.get(i).BSSID);
        buffer.append(",\"frequency\":");
        buffer.append(scanResults.get(i).frequency);
        buffer.append(",\"level\":");
        buffer.append(scanResults.get(i).level);
        buffer.append("}");
      }
      buffer.append("]");
    }
    buffer.append("}");
  }
  private static class WifiReceiver extends BroadcastReceiver {
    public void onReceive(Context c, Intent intent) 
     {       
     wifiInfo = wifiManager.getConnectionInfo();
     scanResults = wifiManager.getScanResults();    
     }
  }
  
}
