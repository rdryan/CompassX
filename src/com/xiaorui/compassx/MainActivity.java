package com.xiaorui.compassx;

import com.samsung.android.sdk.remotesensor.Srs;
import com.samsung.android.sdk.remotesensor.SrsRemoteSensorManager;

import net.youmi.android.AdManager;
import net.youmi.android.diy.banner.DiyAdSize;
import net.youmi.android.diy.banner.DiyBanner;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MainActivity extends Activity implements SensorEventListener {
	private SensorManager sensorManager;
	private Sensor sensor;
	private Rose rose;
	private SharedPreferences prefs;
	private long waitTime = 2000;
	private long touchTime = 0;
	
	//use SAMSUNG SDK
	static SrsRemoteSensorManager		mServiceManager = null;
	Srs								remoteSensor = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//use SAMSUNG SDK, but it's useless
		remoteSensor = new Srs();

        prefs = getSharedPreferences("data", MODE_PRIVATE);
        
		// 初始化接口，应用启动的时候调用
		// 参数：appId, appSecret, 调试模式
		AdManager.getInstance(this).init("0a70120469c410e6", "bcac8802bd2f92d0", false);				
		RelativeLayout adLayout=(RelativeLayout)findViewById(R.id.AdLayout);
		DiyBanner banner = new DiyBanner(this, DiyAdSize.SIZE_MATCH_SCREENx32);
		adLayout.addView(banner);
        
		// Create new instance of custom Rose and set it on the screen
		LinearLayout ImgLayout=(LinearLayout)findViewById(R.id.ImgLayout);
	    rose = new Rose(this);
	    ImgLayout.addView(rose);
	    //setContentView(rose);
	    
	    // Get sensor and sensor manager
	    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	    sensorManager.registerListener(this, sensor,
	  	      SensorManager.SENSOR_DELAY_FASTEST );
	    
        boolean background = prefs.getBoolean("BackgroundColor", false);
        setBackgroundWhiteBlack(!background);	//the save data is the opposite
        
        boolean isfullscreen = prefs.getBoolean("IsFullScreen", false); 
        setFullScreenOnOff(!isfullscreen);		//the save data is the opposite
	    
	    Log.d("Compass", "onCreated");	
	}

	// Register to listen to sensors
	@Override
	public void onResume() {
	  super.onResume();
	  sensorManager.registerListener(this, sensor,
	      SensorManager.SENSOR_DELAY_FASTEST );
	}
	
	// Unregister the sensor listener
	@Override
	public void onPause() {
	  super.onPause();
	  sensorManager.unregisterListener(this);
	}
	
	// Ignore accuracy changes
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
	  
	// Listen to sensor and provide output
	public void onSensorChanged(SensorEvent event) {
	  int orientation = (int) event.values[0];
	  Log.d("Compass", "Got sensor event: " + event.values[0]);
	  rose.setDirection(-orientation);
	}
	  
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	  super.onConfigurationChanged(newConfig);
	}	  
	  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		if (item.getItemId() == R.id.action_about){	
			new AlertDialog.Builder(this)
			.setTitle("ABOUT")
			.setMessage("Compass V2.0\n" +
					"Copyright (c) 2014\n\n" +
					"rdryan@sina.com\n")
			.setPositiveButton("OK",null)
			.show();
		}
		else if (item.getItemId() == R.id.action_ground) {

	        boolean background = prefs.getBoolean("BackgroundColor", false);
	        setBackgroundWhiteBlack(background);
	        Editor mEditor = prefs.edit();
	        mEditor.putBoolean("BackgroundColor", !background);
	        mEditor.commit();
		}
		else if (item.getItemId() == R.id.action_screens) {
			
	        boolean isfullscreen = prefs.getBoolean("IsFullScreen", false); 
	        setFullScreenOnOff(isfullscreen);
	        Editor mEditor = prefs.edit();
	        mEditor.putBoolean("IsFullScreen", !isfullscreen);
	        mEditor.commit();
		}
		
		return true;
	}
	
	public void setBackgroundWhiteBlack(boolean background) {
        
        if (background) {
        	rose.setBackgroundColor(Color.WHITE);
        } else {
        	rose.setBackgroundColor(Color.BLACK);
        }
	}
	
	public void setFullScreenOnOff(boolean isfullscreen) {
		final WindowManager.LayoutParams attrs = getWindow().getAttributes();
        
        if (isfullscreen) {
			//Set full screen view
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			//requestWindowFeature(Window.FEATURE_NO_TITLE);
			
        } else {
			//Exit full screen view
			attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getWindow().setAttributes(attrs);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
	}
	
	@Override  
	public void onBackPressed() {  
	    long currentTime = System.currentTimeMillis();  
	    if((currentTime-touchTime) >= waitTime) {  
	        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();  
	        touchTime = currentTime;  
	    }else {  
	        super.onBackPressed();  
	    }  
	}  
}
