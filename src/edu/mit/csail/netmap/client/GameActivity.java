package edu.mit.csail.netmap.client;

import us.costan.chrome.ChromeSettings;
import us.costan.chrome.ChromeView;
import android.os.Bundle;
import android.view.Window;
import android.annotation.SuppressLint;
import android.app.Activity;

/**
 * The game's main and only activity.
 * 
 * All the action happens inside the WebView.
 */
public class GameActivity extends Activity {
  /** */
  private ChromeView chromeView = null;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_web);
    
    chromeView = (ChromeView)findViewById(R.id.gameUiView);
    setupChromeView(chromeView);

    // TODO(pwnall): deploy a production server and use that URL
    // chromeView.loadUrl("http://192.168.10.73:9200");
    chromeView.loadUrl("http://192.168.10.73:9200/dev_mode");
  }
  
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    chromeView.saveState(outState);
  }
  
  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    chromeView.restoreState(savedInstanceState);
  }
  
  @Override
  protected void onPause() {
    chromeView.onPause();
    super.onPause();
  }
  
  @Override
  protected void onResume() {
    super.onResume();
    chromeView.onResume();
  }
  
  @SuppressLint("SetJavaScriptEnabled")
  /** Configure the Chrome view for usage as the application's window. */
  private void setupChromeView(ChromeView chromeView) {
    ChromeSettings settings = chromeView.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setAllowUniversalAccessFromFileURLs(true);
    settings.setMediaPlaybackRequiresUserGesture(false);
    settings.setBuiltInZoomControls(false);
    settings.setSupportZoom(false);
    settings.setUserAgentString(settings.getUserAgentString() + " NetMap/1.0");
    
    chromeView.addJavascriptInterface(new JsBindings(), "NetMap");
  }
  
  @Override
  /** Navigate the WebView's history when the user presses the Back key. */
  public void onBackPressed() {
    if (chromeView != null) {
      if (chromeView.canGoBack()) {
        chromeView.goBack();
      } else {
        super.onBackPressed();
      }
    } else {
      super.onBackPressed();      
    }
  }
}
