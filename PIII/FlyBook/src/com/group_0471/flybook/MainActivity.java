package com.group_0471.flybook;

import backend.*;
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
*main activity for loggin in
*/
public class MainActivity extends ActionBarActivity {

	private BackendControlPanel backendCP;

	/**handles initially created items in MainActivity
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		backendCP = BackendControlPanel.getInstance(getApplicationContext());
		/*reset this field in case user uses "UP" or "HOME", this prevents
		 normal users from reaching AdminMenu if Admin forgets to log out! */
		ActivityVars.setType("");
	}

	/**handles actionbar on java side
	 * @see admin_menu.xml in res/menu
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * currently unimplemented (auto-generated)
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
	 * controls actions that occur after "register" button pressed
	 * @param view current activity(view) -> "MainActivity"
	 */
	public void registerUser(View view){
		/* send user to RegisterUser activity, and set current client
		 * type to "client" (for use in activities shared by
		 * admins and clients */
		Intent i = new Intent(this, RegisterUser.class);
		ActivityVars.setType("client");
		startActivity(i);
	}

	/**
	 * controls action that occur after "login" is button pressed
	 * @param view view current activity(view) -> "MainActivity"
	 */
	public void login(View view){
		/*intial intent is to send user to ClientMenu*/
		Intent i = new Intent(this, ClientMenu.class);

		/* get the Email and Password EditText's from Activity 
		   and convert to String */
		EditText textEmailAddress = (EditText) findViewById(R.id.editText1);
		EditText textPassword = (EditText) findViewById(R.id.editText2);

		String email = textEmailAddress.getText().toString();
		String pass = textPassword.getText().toString();

		/*for testing - HARDCODED DEFAULT ADMIN */
		if(email.equals("admin") && pass.equals("admin")){
			i = new Intent(this, AdminMenu.class);
			startActivity(i);
		}

		/*try to login to DB if failed catch the appropriate exceptions*/
		try {
			backendCP.login(email, pass);

		} catch (NoSuchEntryException e) {
			/*if user DNE, return the error in pop-up*/
			textEmailAddress.setError("Username Doesn't Exist");
			return;
		} catch (InvalidPasswordException e) {
			/*if password incorrect, return error in pop-up*/
			textPassword.setError("Incorrect Password");
			return;
		}

		/* this is for personal info activity */
		ActivityVars.setCurrUser(email);
		startActivity(i); // goes to Client Menu
	}
}
