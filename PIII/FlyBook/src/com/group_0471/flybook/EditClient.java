package com.group_0471.flybook;

import backend.BackendControlPanel;
import backend.exceptions.AlreadyExistingEntryException;
import backend.exceptions.NoPermissionException;
import backend.exceptions.NoSuchEntryException;
import backend.managers.ClientInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditClient extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_client);
		ClientInfo user = null;
		try {
			 user = BackendControlPanel.getInstance(null).getClientInfo(ActivityVars.getCurrUser());
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchEntryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		((EditText)findViewById(R.id.editText1)).setText(user.getFirstName());
		((EditText)findViewById(R.id.editText2)).setText(user.getLastName());
		((EditText)findViewById(R.id.editText3)).setText(user.getEmail());
		((EditText)findViewById(R.id.editText4)).setText(user.getAddress());
		((EditText)findViewById(R.id.editText5)).setText(user.getCreditCardNumber());
		((EditText)findViewById(R.id.editText6)).setText(user.getExpiryDate());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_client, menu);
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
	
	public void submit(View view) {
		
		String first = ((EditText) findViewById(R.id.editText1)).getText().toString();
		String last = ((EditText) findViewById(R.id.editText2)).getText().toString();
		String email = ((EditText) findViewById(R.id.editText3)).getText().toString();
		String address = ((EditText) findViewById(R.id.editText4)).getText().toString();
		String ccNum = ((EditText) findViewById(R.id.editText5)).getText().toString();
		String ccExp = ((EditText) findViewById(R.id.editText6)).getText().toString();
		
	    final String TAG = "test change info";
	    ActivityVars.changeInfo(first, last, email, address, ccNum, ccExp);
		NavUtils.navigateUpTo(this, new Intent(this,ClientMenu.class));					
	}
}
