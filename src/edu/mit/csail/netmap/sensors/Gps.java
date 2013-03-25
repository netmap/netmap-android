package edu.mit.csail.netmap.sensors;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Information from the phone's GPS sensor.
 */
public class Gps {
  /** Entry point to Android's GPS functionality. */
  private static LocationManager locationManager;
  
  /** The instance passed to all requestLocationUpdates() calls. */
  private static LocationStatusListener locationListener;

  /** Current GPS status. */
  private static GpsStatus gpsStatus;
    
  /** Number of milliseconds between location updates. */
  private static final int PRECISION_TIME_MS = 100;
  
  /** Number of meters between location updates. */
  private static final int PRECISION_DISTANCE_M = 1;
  
  /** Called by sensors initialization. */
  public static void initialize(Context context) {
    locationManager =
        (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
    locationManager.addGpsStatusListener(new GpsStatusListener());
    locationManager.addNmeaListener(new NmeaStatusListener());
    
    gpsStatus = null;
    locationListener = new LocationStatusListener();
  }
  
  /** Performs one location acquisition. */
  public static void getLocation() {
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
        PRECISION_TIME_MS, PRECISION_DISTANCE_M, locationListener);
  }
  
  private static class GpsStatusListener implements GpsStatus.Listener {
    @Override
    public void onGpsStatusChanged(int event) {
      gpsStatus = locationManager.getGpsStatus(gpsStatus);
      
      switch (event) {
      case GpsStatus.GPS_EVENT_STARTED:
        // GPS subsystem started.
      case GpsStatus.GPS_EVENT_STOPPED:
        // GPS stopped.
      case GpsStatus.GPS_EVENT_FIRST_FIX:
        int timeToFirstFix = gpsStatus.getTimeToFirstFix();
        
      case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
        Iterable<GpsSatellite> satellites = gpsStatus.getSatellites();
        for (GpsSatellite satellite : satellites) {
          
        }
      }
    }
  }
  
  private static class NmeaStatusListener implements GpsStatus.NmeaListener {
    @Override
    public void onNmeaReceived(long timestamp, String nmea) {
      
    }
  }
  
  private static class LocationStatusListener implements LocationListener {
    @Override
    public void onLocationChanged(Location location) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void onProviderDisabled(String provider) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void onProviderEnabled(String provider) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
      // TODO Auto-generated method stub
      
    }
  }
}
