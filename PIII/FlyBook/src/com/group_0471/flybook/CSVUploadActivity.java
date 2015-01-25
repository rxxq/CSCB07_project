package com.group_0471.flybook;

import backend.BackendControlPanel;
import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class CSVUploadActivity extends Activity {
/**
*controls CSV upload acivity for admins
*/

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_csv_uploader);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_menu, menu);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
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
	 * controls what happens if admin chooses to upload flights by CSV
	 */
	public void uploadFlightsCSV(View view){
		/*get path & call backend method to save*/
	    String path = ((EditText)findViewById(R.id.csvPath)).getText().toString();
		try{
			BackendControlPanel.getInstance(null).importFlightInfo(path);
		}catch (Exception e){
		    e.printStackTrace();
			((EditText)findViewById(R.id.csvPath)).setError("Invalid FilePath!");
		}
		
	}

	/**
	 * controls what happens if admin chooses to upload clients by CSV
	 */	
	public void uploadClientsCSV(View view){
		/*get path & call backend method to save*/
		String path = ((EditText)findViewById(R.id.csvPath)).getText().toString();
		try{
			BackendControlPanel.getInstance(null).importClientInfo(path);
		}catch (Exception e){
		    e.printStackTrace();
			((EditText)findViewById(R.id.csvPath)).setError("Invalid FilePath!");
		}
	}
	
}
