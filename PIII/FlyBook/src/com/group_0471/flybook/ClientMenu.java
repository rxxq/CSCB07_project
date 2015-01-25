package com.group_0471.flybook;

import backend.BackendControlPanel;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.app.ActionBar;

public class ClientMenu extends ActionBarActivity {
/**
*controls client menu acitvity
*/

	/**Initially created items in view
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_menu);
	}

	/**Inflates Action Bar
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.client_menu, menu);
		return true;
	}

	/**
	 * Controls what happens when Action Bar items are selected
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*get id of pressed item*/
		int id = item.getItemId();

		/*unimplemented, just return true for now*/
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.logout){
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

	/**
	 * controls action if searchItineraries is press
	 * @param view current view -> ClientMenu
	 */
	public void searchItineraries(View view){
		/*switches to Itinerary Activity*/
		Intent intent = new Intent(this, ItinListActivity.class);
		startActivity(intent);
	}

	/**
	 * controls action if searchFlights is pressed
	 * @param view current view -> ClientMenu
	 */
	public void searchFlights(View view) {
		/*switches to Itinerary Activity*/
		Intent intent = new Intent(this, flightListActivity.class);
		startActivity(intent);
	}
	
	/**
	 * redirects user to ItineraryInterface if 
	 * "Search avail. Itineraries" pressed
	 * @param view current view -> ClientMenu
	 */
	public void searchItin(View view) {
		/*switches to Itinerary Activity*/
		Intent intent = new Intent(this, ItineraryInterface.class);
		startActivity(intent);
	}
}
