package com.group_0471.flybook;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class FlightSearch extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flight_search);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.flight_search, menu);
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
	
	public void searchFlights(View view){
		/*convert Date */
		DatePicker dp = (DatePicker)findViewById(R.id.datePicker);
		int year = dp.getYear();
		int month = dp.getMonth();
		int day = dp.getDayOfMonth();
		
		/*convert time*/
		TimePicker tp = (TimePicker)findViewById(R.id.timePicker);
		int hour = tp.getCurrentHour();
		int minute = tp.getCurrentMinute();

		
		Log.i("ampm", new Integer(hour).toString());

		/*to calendar */
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(year, month, day, hour, minute);
	    
	    /*format date */
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    sdf.setCalendar(calendar);
	    
		ActivityVars.setFlightDate(sdf.format(calendar.getTime()));
		ActivityVars.setFlightOrigin(((EditText)findViewById(R.id.flight_origin)).getText().toString());
		ActivityVars.setFlightDest(((EditText)findViewById(R.id.flight_dest)).getText().toString());
		Intent i = new Intent(this, flightListActivity.class);
		Log.i("Date: ", sdf.format(calendar.getTime()));
		Log.i("Origin: ", ActivityVars.getFlightOrigin());
		Log.i("Dest: ", ActivityVars.getFlightDest());
		startActivity(i);
	}
}
