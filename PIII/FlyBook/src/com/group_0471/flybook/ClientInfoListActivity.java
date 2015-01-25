package com.group_0471.flybook;

import java.util.ArrayList;

import com.group_0471.flybook.client_pinfo_values.ClientPersonalInfoValues;

import backend.Itinerary;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * An activity representing a list of ClientInfo. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link ClientInfoDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ClientInfoListFragment} and the item details (if present) is a
 * {@link ClientInfoDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ClientInfoListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class ClientInfoListActivity extends FragmentActivity implements
		ClientInfoListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ClientPersonalInfoValues.refreshVal();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clientinfo_list);

		if (findViewById(R.id.clientinfo_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((ClientInfoListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.clientinfo_list))
					.setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}
	
	/**
	 * controls what happens when actionbar items are selected
	 *@param item the menu item that was selected
	 */
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

		}else if( id == R.id.logout){
			NavUtils.navigateUpTo(this, new Intent(this,
					MainActivity.class));
		}else if(id == R.id.shopping_cart){
			Intent i = new Intent(this, ShoppingCartActivity.class);
			startActivity(i);
		}else if(id == R.id.account_settings){
			Intent i = new Intent(this, ClientInfoListActivity.class);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Callback method from {@link ClientInfoListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(ClientInfoDetailFragment.ARG_ITEM_ID, id);
			ClientInfoDetailFragment fragment = new ClientInfoDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.clientinfo_detail_container, fragment)
					.commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this,
					ClientInfoDetailActivity.class);
			detailIntent.putExtra(ClientInfoDetailFragment.ARG_ITEM_ID, id);
			Log.i("bitch", id);
			ActivityVars.setChangeParam(id.split(":")[0]);
			Log.i("test", ActivityVars.getChangeParam());
			startActivity(detailIntent);
		}
	}
	
}
