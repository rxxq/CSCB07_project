package com.group_0471.flybook;

import backend.BackendControlPanel;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AdminBookActivity extends Activity {
/**
*this class controls what happens when admins try to book things
*/

	/**
	*iherits parent javadoc
	*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_book);
	}
	
	/**
	*inherits parent javadoc
	*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_book, menu);
		return true;
	}
	
	/**
	*inherits parent javadoc
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
	*controls what happens when "BOOK" button is pressed
	*@param view the current view
	*/	
	public void book(View view){
		/*get the master detail ID of flight*/
		String mdID = getIntent().getStringExtra("mdID");
		/*get the user to book for*/
		String user = ((EditText)findViewById(R.id.bookPath)).toString();
		ActivityVars.addToBooked(mdID, user);
	}
}
