package com.group_0471.flybook;

import java.util.ArrayList;

import backend.Itinerary;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
*used for viewing booked items
*/
public class ShoppingCartActivity extends ActionBarActivity {
	/*this method may be deleted and changed to a MasterDetailFlow - Daniel*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping_cart);
		/*add a linear layout and add the text & other items needed*/
		LinearLayout lView = new LinearLayout(this);
		ArrayList<Itinerary> curr = new ArrayList<Itinerary>();
	    TextView myText = new TextView(this);
	    String userFlights = "";
	    for(Itinerary itin: ActivityVars.getBooked()){
	    	curr.add(itin);
	    }
	    try {
	    	for(Itinerary itin: curr){
	    		userFlights += itin.MasterDetailID() + "\n";
		    }
	    	myText.setText(userFlights);
	    	lView.addView(myText);
	    	
	    }
	    
	    catch (Exception e){
	    	
	    }
	    

	    setContentView(lView);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopping_cart, menu);
		return true;
	}

	/**
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
	
	
}
