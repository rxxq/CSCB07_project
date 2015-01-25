package com.group_0471.flybook;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AdminBook_Itin extends Activity {
/**
*class to control behavior of aministrators that book items
*/
	/**
	*inherits parent doc
	*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_book__itin);
	}

	/**
	*inherits parent doc
	*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_book__itin, menu);
		return true;
	}

	/**
	*inherits parent doc
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
	*inherits parent doc
	*/
	public void book(View view){
		/*get Master Detail ID*/
		String mdID = getIntent().getStringExtra("mdID");
		/*get the user to book for*/
		String user = ((EditText)findViewById(R.id.bookPathItin)).toString();
	}
}
