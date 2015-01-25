package com.group_0471.flybook.itinerary_values;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backend.BackendControlPanel;
import backend.Itinerary;
import backend.managers.FlightInfo;

import com.group_0471.flybook.ActivityVars;
import com.group_0471.flybook.flight_values.FlightListContent.DummyItem;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ItinValues {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

	static {
		List<Itinerary> test = null;
		if(ActivityVars.getSortType().equals("date")){
		test = BackendControlPanel.getInstance(null).searchItinerariesSortedByTime(
				ActivityVars.getItinDate(), 
				ActivityVars.getItinOrigin(), ActivityVars.getItinDest());
		}else{
			test = BackendControlPanel.getInstance(null).searchItinerariesSortedByCost(
					ActivityVars.getItinDate(), 
					ActivityVars.getItinOrigin(), ActivityVars.getItinDest());
		}
		for(Itinerary itin: test){
			addItem(new DummyItem(itin.MasterDetailID(), itin.MasterDetail()));
			ActivityVars.addToCurrItin((itin));
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
