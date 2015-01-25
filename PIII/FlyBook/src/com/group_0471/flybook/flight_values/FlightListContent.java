package com.group_0471.flybook.flight_values;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.group_0471.flybook.ActivityVars;
import com.group_0471.flybook.itinerary_values.ItinValues.DummyItem;

import android.util.Log;
import backend.*;
import backend.managers.FlightInfo;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class FlightListContent {
    
	/**
	 * An array of sample (dummy) items.
	 */
	public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

	static {
		
		List<FlightInfo> flights = BackendControlPanel.getInstance(null).searchFlights(
				ActivityVars.getFlightDate(), ActivityVars.getFlightOrigin(), ActivityVars.getFlightDest());
		Log.i("test", new Integer (flights.size()).toString());
		for(FlightInfo flight: flights){
			addItem(new DummyItem(flight.MasterDetailID(), flight.toMasterDetail()));
			Log.i("xxx", flight.toMasterDetail());
		}
		

	}

	private static void addItem(DummyItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	/**
	 * A dummy item representing a piece of content.
	 */
	public static class DummyItem {
		public String id;
		public String content;

		public DummyItem(String id, String content) {
			this.id = id;
			this.content = content;
		}

		@Override
		public String toString() {
			return id;
		}
	}
}
