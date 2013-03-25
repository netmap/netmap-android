package edu.mit.csail.netmap.client;

import us.costan.chrome.ChromeView;
import android.os.Bundle;
import android.view.Window;
import android.annotation.SuppressLint;
import android.app.Activity;

public class WebActivity extends Activity {
  private ChromeView chromeView;
  
  @SuppressLint("SetJavaScriptEnabled")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_web);
    
    chromeView = (ChromeView)findViewById(R.id.gameUiView);
    chromeView.getSettings().setJavaScriptEnabled(true);
    chromeView.loadUrl("http://192.168.10.70:9200");
    // chromeView.loadUrl("file:///android_asset/index.html");
  }
}
