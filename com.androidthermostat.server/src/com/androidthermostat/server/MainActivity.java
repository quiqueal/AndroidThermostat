package com.androidthermostat.server;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import com.androidthermostat.server.data.Conditions;
import com.androidthermostat.server.data.Schedules;
import com.androidthermostat.server.data.Settings;
import com.androidthermostat.server.utils.FurnaceController;
import com.androidthermostat.server.utils.MulticastListener;
import com.androidthermostat.server.utils.Utils;
import com.androidthermostat.server.utils.WebServer;


public class MainActivity extends Activity {

	
	Handler refreshHandler;
	TextView debugText;


	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        debugText  = (TextView) findViewById(R.id.debugText);
        refreshHandler= new Handler();
        refreshHandler.postDelayed(refreshRunnable, 1000);
     
        Intent i = new Intent(this, MainService.class);
        startService(i);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        

        
	}
	
	
	
	
	public void updateScreen()
	{
		FurnaceController fc =  FurnaceController.getCurrent();
		String output = "Fan: " + String.valueOf(fc.fanOn) + "\n";
		output += "Cool: " + String.valueOf(fc.coolOn) + "\n";
		output += "Heat: " + String.valueOf(fc.heatOn) + "\n";
		output += "Temp: " + Conditions.getCurrent().insideTempRaw + "\n";
		
		debugText.setText(output + Utils.debugText);
	}
	
	private Runnable refreshRunnable = new Runnable() {
	   public void run() {
		   updateScreen();
		   refreshHandler.postDelayed(this, 1000);
	    }
	};
	
}
