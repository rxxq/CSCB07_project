package com.group_0471.flybook.client_pinfo_values;

import java.util.ArrayList;
import com.group_0471.flybook.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import backend.*;
import backend.exceptions.NoPermissionException;
import backend.exceptions.NoSuchEntryException;
import backend.managers.ClientInfo;
/**
 * Helper class for providing content for UserInfo Values
 */
public class ClientPersonalInfoValues {

	/**
	 * An array of PersonalInfo items.
	 */
	public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();
	
	/**
	 * add items from DB to be viewed
	 */
	static {
		ClientInfo info = null;
		info = ActivityVars.getUser();
		
			addItem(new DummyItem("Email: "+ info.getEmail(),""));
			addItem(new DummyItem("First Name: "+ info.getFirstName(), ""));
			addItem(new DummyItem("Last Name: "+ info.getLastName(), ""));
			addItem(new DummyItem("Address: "+ info.getAddress(),""));
			addItem(new DummyItem("CC #: "+ info.getCreditCardNumber(),""));
			addItem(new DummyItem("CC Exp. Date: "+ info.getExpiryDate(),""));
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

	public static void refreshVal() {
		// TODO Auto-generated method stub
		new ClientPersonalInfoValues();
	}
}
