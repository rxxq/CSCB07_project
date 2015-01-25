package com.group_0471.flybook;

import java.io.IOException;

import backend.*;
import backend.exceptions.AlreadyExistingEntryException;
import backend.exceptions.InvalidPasswordException;
import backend.exceptions.NoSuchEntryException;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
*the registration class
*@auth Daniel 
*/
public class RegisterUser extends ActionBarActivity {

	/**
	 * initially created items in RegisterUser Activity
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_user);
	}

	/**Inflates ActionBar (see res/menu/register_user.xml for layout)
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_user, menu);
		return true;
	}

	/**
	 * Handles ActionBar items when clicked, currently not implemented
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) { // not implemented
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * handles actions after "submit" button is pressed in registerUser
	 * @param view current view (Activity Layout) -> RegisterUser
	 */
	public void registrationCompleted(View view){
		/*create intent to redirect to MainActivity on completion*/
		Intent i = new Intent(this, MainActivity.class);

		/* checks  if current user is admin, & changes intent and is_admin accordingly */
		boolean is_admin = false;
		if(ActivityVars.getType().equals("admin")){
			i = new Intent(this, AdminMenu.class);
			is_admin = true;
		}

		/* get user input from EditText's in RegisterUser */
		String email = ((EditText) findViewById(R.id.user_email)).getText().toString();
		String pw = ((EditText) findViewById(R.id.user_pw)).getText().toString();
		String first = ((EditText) findViewById(R.id.user_first)).getText().toString();
		String last = ((EditText) findViewById(R.id.user_last)).getText().toString();
		String address = ((EditText) findViewById(R.id.user_address)).getText().toString();
		String ccNum = ((EditText) findViewById(R.id.ccNum)).getText().toString();
		String ccExp = ((EditText) findViewById(R.id.cc_exp)).getText().toString();

		/*try to create new user*/
		try {
			BackendControlPanel.getInstance(null).addNewUser(
					first, last, email, address, ccNum,
					ccExp, is_admin, pw);
		} catch (AlreadyExistingEntryException e) {
			/*if user already exists, throw pop-up error in email EditText*/
			((EditText) findViewById(R.id.user_email)).setError("E-Mail already in use");
			return;
		}

		/*returns to LogIn screen if client, AdminMenu if admin*/
		startActivity(i);
	}
}
