package com.group_0471.flybook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * An activity representing a single flight detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link flightListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link flightDetailFragment}.
 */
public class flightDetailActivity extends ActionBarActivity implements OnClickListener{

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flight_detail);
		
		Button button = (Button)findViewById(R.id.button1);
		button.setOnClickListener(this);
		// Show the Up button in the action bar.
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(flightDetailFragment.ARG_ITEM_ID, getIntent()
					.getStringExtra(flightDetailFragment.ARG_ITEM_ID));
			flightDetailFragment fragment = new flightDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
			.add(R.id.flight_detail_container, fragment).commit();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.client_menu, menu);
		return true; //items not implemented in this menu
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpTo(this, new Intent(this,
					flightListActivity.class));
			return true;

		}else if( id == R.id.logout){ //if user wants to log out
			NavUtils.navigateUpTo(this, new Intent(this,
					MainActivity.class));
		}else if(id == R.id.shopping_cart){ //if user wants to view cart
			Intent i = new Intent(this, ShoppingCartActivity.class);
			startActivity(i);
		}else if(id == R.id.account_settings){ //edit and view acc settings
			Intent i = new Intent(this, ClientInfoListActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * what happens when user wants to book a flight (onClick for book button)
	 *@param view the current view
	 */
	public void onClick(View view){
		if(view.getId() == R.id.button1){
			if(ActivityVars.getType().equals("admin")){
				Intent i = new Intent(this, AdminBookActivity.class);
				i.putExtra("mdID", getIntent()
						.getStringExtra(flightDetailFragment.ARG_ITEM_ID));
				startActivity(i);
			}else{
				ActivityVars.addToBooked(getIntent()
						.getStringExtra(flightDetailFragment.ARG_ITEM_ID),
						ActivityVars.getCurrUser());
			}
		}
	}
}
