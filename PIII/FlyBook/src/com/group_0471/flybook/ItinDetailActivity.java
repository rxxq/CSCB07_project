package com.group_0471.flybook;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * An activity representing a single Itin detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link ItinListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link ItinDetailFragment}.
 */
public class ItinDetailActivity extends Activity implements OnClickListener {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_itin_detail);
		
		/*book button*/
		Button button = (Button)findViewById(R.id.itButton);
		button.setOnClickListener(this);
		
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

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
			arguments.putString(ItinDetailFragment.ARG_ITEM_ID, getIntent()
					.getStringExtra(ItinDetailFragment.ARG_ITEM_ID));
			ItinDetailFragment fragment = new ItinDetailFragment();
			fragment.setArguments(arguments);
			getFragmentManager().beginTransaction()
					.add(R.id.itin_detail_container, fragment).commit();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			navigateUpTo(new Intent(this, ItinListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**this is for bookin itineraries (when button is clicked)
	 * {@inheritDoc} 
	 */
	@Override
	public void onClick(View view) {
		if(view.getId() == R.id.itButton){
			if(ActivityVars.getType().equals("admin")){ 
			/*if user is an admin go to the admin view of itinerary booking*/
				Intent i = new Intent(this, AdminBook_Itin.class); 
				i.putExtra(getIntent()
						.getStringExtra(ItinDetailFragment.ARG_ITEM_ID),
						ActivityVars.getCurrUser());
				startActivity(i);
			}else{ //if user is a client simply book 
				ActivityVars.addToBookedItin(getIntent()
						.getStringExtra(ItinDetailFragment.ARG_ITEM_ID),
						ActivityVars.getCurrUser());
			}
		}
	}
}
