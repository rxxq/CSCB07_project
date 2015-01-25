package com.group_0471.flybook;

import backend.BackendControlPanel;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AdminMenu extends ActionBarActivity{
	/**
	 * @auth Daniel 
	 * Administrator Menu
	 */

	/*spinner resources*/
	Spinner spinner_1;
	Spinner spinner_2;


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_menu);

		/*intialize spinner & define array adapter, 
		  which gets vals from strings.xml array*/
		spinner_1 = (Spinner) findViewById(R.id.adminMenu);
		spinner_2 = (Spinner) findViewById(R.id.regMenu);


		ArrayAdapter<CharSequence> adapter_1 = ArrayAdapter.createFromResource(this, 
				R.array.admin_spinner_1,
				android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> adapter_2 = ArrayAdapter.createFromResource(this,
				R.array.admin_spinner_2, 
				android.R.layout.simple_spinner_item);

		/*specify layout of spinner (default layout)*/
		adapter_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		/*apply adapters to spinner*/
		spinner_1.setAdapter(adapter_1);
		spinner_2.setAdapter(adapter_2);

		/*sets actionlistener for items in spinner_1*/
		spinner_1.setOnItemSelectedListener(new OnItemSelectedListener() {

			/*control what happens to spinner_1 (Admin Menu) when its items are selected*/
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				Intent i = new Intent();
				if(arg0.getItemAtPosition(arg2).toString().equals("View/Edit Client Info.")){
					//i.setClass(AdminMenu.this, AdminEditInfoListActivity.class);
					//startActivity(i);
				}else if(arg0.getItemAtPosition(arg2).toString().equals("Create Admin Accounts")){
					i.setClass(AdminMenu.this, RegisterUser.class);
					startActivity(i);
				}else if(arg0.getItemAtPosition(arg2).toString().equals("Edit Admin Accounts")){
					i.setClass(AdminMenu.this, flightListActivity.class);
					startActivity(i);
				}else if(arg0.getItemAtPosition(arg2).toString().equals("Upload CSV File")){
					i.setClass(AdminMenu.this, CSVUploadActivity.class);
					startActivity(i);
				}
			}
				/*basically does nothing if nothing is selected*/
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					/*leave blank - should do nothing!*/
				}
			});

		/*sets action listener for items in spinner_2*/
		spinner_2.setOnItemSelectedListener(new OnItemSelectedListener() {

			/*controls what happens when items in spinner_2 are selected*/
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Intent i = new Intent();
				if(arg0.getItemAtPosition(arg2).toString().equals("Search Avail. Itineraries")){
					i.setClass(AdminMenu.this, ItineraryInterface.class);
					startActivity(i);
				}else if(arg0.getItemAtPosition(arg2).toString().equals("Search Flight Manifest")){
					i.setClass(AdminMenu.this, FlightSearch.class);
					startActivity(i);
				}else if(arg0.getItemAtPosition(arg2).toString().equals("Edit Billing Info")){
					i.setClass(AdminMenu.this, RegisterUser.class);
					startActivity(i);
				}


			}

			/*does nothing if nothing is selected*/
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				/*should be blank!*/
			}
		});
		}

		/**Inflates Action Bar, see admin_menu.xml in res/menu
		 * {@inheritDoc}
		 */
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.admin_menu, menu);
			return true;
		}

		/**controls what happens when menu items selected
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
			}else if (id == R.id.logout){
				/* redirect user to main menu & log them out */
				Intent i = new Intent(this, MainActivity.class);
				BackendControlPanel.getInstance(null).logout();
				startActivity(i);
			}else if (id == R.id.account_settings){ 
				/* redirect user to account settings page */
				Intent i = new Intent(this, EditClient.class);
				startActivity(i);
			}else if(id == R.id.shopping_cart){
				/* redirect user to Shopping Cart page */
				Intent i = new Intent(this, ShoppingCartActivity.class);
				startActivity(i);
			}
			/*default option*/
			return super.onOptionsItemSelected(item);
		}
	}


