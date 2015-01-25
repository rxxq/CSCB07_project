package com.group_0471.flybook;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class ItineraryInterface extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_itinerary_interface);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if(ActivityVars.getType().equals("admin")){
			getMenuInflater().inflate(R.menu.admin_menu, menu);
		}else{
			getMenuInflater().inflate(R.menu.client_menu, menu);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	

	/**
	 * directs user to search Itineraries based on parameters
	 * @param view
	 */
	public void sortByDate(View view){
		ActivityVars.setSortType("date");
		Intent i = new Intent(this, ItinListActivity.class);
		startActivity(i);
	}
	
	/**
	 * directs user to search Itineraries based on parameters
	 * @param view
	 */
	public void sortByTime(View view){
		/*convert Date */
		DatePicker dp = (DatePicker)findViewById(R.id.datePicker);
		int year = dp.getYear();
		int month = dp.getMonth();
		int day = dp.getDayOfMonth();
		
		/*convert time*/
		TimePicker tp = (TimePicker)findViewById(R.id.timePicker);
		int hour = tp.getCurrentHour();
		int minute = tp.getCurrentMinute();
	
		/*to calendar */
	    Calendar calendar = new GregorianCalendar();
	    calendar.set(year, month, day, hour, minute);
	    
	    /*format date */
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	    sdf.setCalendar(calendar);
	    
		ActivityVars.setSortType("time");
		ActivityVars.setItinDate(sdf.format(calendar.getTime()));
		ActivityVars.setItinOrigin(((EditText)findViewById(R.id.itin_origin)).toString());
		ActivityVars.setItinDest(((EditText)findViewById(R.id.itin_dest)).toString());
		Intent i = new Intent(this, ItinListActivity.class);
		startActivity(i);
	}
}
