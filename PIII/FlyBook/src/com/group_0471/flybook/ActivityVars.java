package com.group_0471.flybook;

import java.util.ArrayList;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import backend.*;
import backend.exceptions.AlreadyExistingEntryException;
import backend.exceptions.NoPermissionException;
import backend.exceptions.NoSuchEntryException;
import backend.managers.ClientInfo;
import backend.managers.FlightInfo;

public class ActivityVars extends Application{
/**
*@auth group_0371
*this class acts as a cache for the application
*/
	private static String type; // user type (admin/client)
	private static String curr_user; //current user email
	/*list of booked flights*/
	private static ArrayList<Itinerary> booked = 
			new ArrayList<Itinerary>(); 
	private static String changeParam; 
	private static String sortType;
	private static String itinOrigin;
	private static String itinDest;
	private static String itinDate;
	private static String itinCost;
	/*current itinerary being searched*/
	private static ArrayList<Itinerary> current_itin =
			new ArrayList<Itinerary>();
	/*recent searches, not implemented*/
	private static ArrayList<String> recentSearches = 
			new ArrayList<String>();
	private static String flightDate;
	private static String flightOrigin;
	private static String flightDest;
	private static String curr_client_admin; //curre client that admin wants to edit
	
	/**
	* parameter of user that we want to change - dont use
	*/
	@Deprecated
	public static void setChangeParam(String changeParam) {
		ActivityVars.changeParam = changeParam;
	}
	
	/**
	*@return the parameter to be changed
	*/
	@Deprecated
	public static String getChangeParam() {
		return changeParam;
	}
	
	/**
	*@return the email of current logged user
	*/
	public static String getCurrUser(){
		return curr_user;
	}

	/**
	*store the email of the currently logged in user
	*@param user the user email to store
	*/	
	public static void setCurrUser(String user){
		curr_user = user;
	}

	/**
	*@return a list of booked itineraries by current user
	*/
	public static ArrayList<Itinerary> getBooked() {
		return booked;
	}
	
	/**
	*adds the Itinerary itin to the list of booked items of the User user
	*@param user the email of a user we want to add booked items to
	*@param itin the itinerary we want to add
	*/
	public static void addToBooked(Itinerary itin, String user) {
		ActivityVars.booked.add(itin);
		/*get an instance of backend method bookItin and save booked stuff*/
		try {
			BackendControlPanel.getInstance(null).bookItin(user, itin);
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	* adds a flight to the User user's list of booked items
	*@param flight the flight to address
	*@param user the user we want to add a flight to (in their booked list)
	*/
	public static void addToBooked(FlightInfo flight, String user){
		Itinerary i = new Itinerary();
		i.appendFlight(flight);
		ActivityVars.booked.add(i);
	}
	
	/**
	*adds a flight to the User user's list of booked information where
	*@param stringExtra the ID (String) of the FlightInfo version of flight we want to add
	*@param user the email of the user we want to add flight to 
	*/
	public static void addToBooked(String stringExtra, String user) {
		FlightInfo curr = null;
		/*get the flight by id and then call the method to add it to user's booked*/
		try { //error should be impossible since only existing flights are displayed
			curr = BackendControlPanel.getInstance(null).getFlightByID(stringExtra);
		} catch (NoSuchEntryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addToBooked(curr, user);
	
	}
	
	/**
	*sets the type of the currently logged in user, either "admin" or "client"
	*@param user_type the type of user 
	*/
	public static void setType(String user_type) {
		type = user_type;
	}

	/**
	*gets the type of the currently logged in user either admin or client
	*@return "admin" or "client"
	*/
	public static String getType() {
		return type;
	}

	/**
	*a method to change information in a current user
	*@newInfo the information that needs to be  changed in current users information
	*/
	public static void changeInfo(String first, String last, String email, String address , String ccNum, String ccExp){
	    
	    BackendControlPanel backend = BackendControlPanel.getInstance(null);
	    final String TAG = "test change info";
	    
		//testing to see if it works for address
	    // if change param == "CC Exp Date"
 			try {
 				// set the clients address to newInfo
 				ClientInfo infoToChange = 
 				        backend.getClientInfo(curr_user);
 				infoToChange.setExpiryDate(ccExp);
 				backend.setClientInfo(
 				        backend.getCurrUserEmail(), infoToChange);
 			} catch (NoPermissionException | NoSuchEntryException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} catch (AlreadyExistingEntryException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             } 		
	    // if change param == "CC #"
 			try {
 				// set the clients address to newInfo
 				ClientInfo infoToChange = 
 				        backend.getClientInfo(curr_user);
 				infoToChange.setCreditCardNumber(ccNum);
 				backend.setClientInfo(
 				        backend.getCurrUserEmail(), infoToChange);
 			} catch (NoPermissionException | NoSuchEntryException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} catch (AlreadyExistingEntryException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
	    // if change param == "Last Name"
	 			try {
	 				// set the clients address to newInfo
	 				ClientInfo infoToChange = 
	 				        backend.getClientInfo(curr_user);
	 				infoToChange.setLastName(last);
	 				backend.setClientInfo(
	 				        backend.getCurrUserEmail(), infoToChange);
	 			} catch (NoPermissionException | NoSuchEntryException e) {
	 				// TODO Auto-generated catch block
	 				e.printStackTrace();
	 			} catch (AlreadyExistingEntryException e) {
	                 // TODO Auto-generated catch block
	                 e.printStackTrace();
	             }
	    // if change param == "First Name"
	 			try {
	 				// set the clients address to newInfo
	 				ClientInfo infoToChange = 
	 				        backend.getClientInfo(curr_user);
	 				infoToChange.setFirstName(first);
	 				backend.setClientInfo(
	 				        backend.getCurrUserEmail(), infoToChange);
	 			} catch (NoPermissionException | NoSuchEntryException e) {
	 				// TODO Auto-generated catch block
	 				e.printStackTrace();
	 			} catch (AlreadyExistingEntryException e) {
	                 // TODO Auto-generated catch block
	                 e.printStackTrace();
	             }
	    // if change param == "Email"
	 			try {
	 				// set the clients address to newInfo
	 				ClientInfo infoToChange = 
	 				        backend.getClientInfo(curr_user);
	 				infoToChange.setEmail(email);
	 				backend.setClientInfo(
	 				        backend.getCurrUserEmail(), infoToChange);
	 			} catch (NoPermissionException | NoSuchEntryException e) {
	 				// TODO Auto-generated catch block
	 				e.printStackTrace();
	 			} catch (AlreadyExistingEntryException e) {
	                 // TODO Auto-generated catch block
	                 e.printStackTrace();
	             }
		// if change param == "Address"
			try {
				// set the clients address to newInfo
				ClientInfo infoToChange = 
				        backend.getClientInfo(curr_user);
				infoToChange.setAddress(address);
				backend.setClientInfo(
				        backend.getCurrUserEmail(), infoToChange);
			} catch (NoPermissionException | NoSuchEntryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AlreadyExistingEntryException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
		try {
			// this is the users address
			Log.i(TAG, "clients address: " + 
			        backend.getClientInfo(curr_user).getAddress());
		} catch (NoPermissionException | NoSuchEntryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//this is what newInfo is

	}
	
	/**
	 * use for by searchItineraries to find sort Type "date" or "time"
	 * @param newSortType type of sort "by date" or "travel time"
	 */
	public static void setSortType(String newSortType) {
		sortType = newSortType;
	}
	
	/**
	 * see setSortType
	 * @return sort type "date" or "time"
	 */
	public static String getSortType(){
		return sortType;
	}
	
	/**
	*set date of current itinerary to be searched
	*@param new_date date to set
	*/
	public static void setItinDate(String new_date){
		itinDate = new_date;
	}
	
	/**
	*sets the origin of current itinerary to be searched
	*@param new_origin the origin to set
	*/	
	public static void setItinOrigin(String new_origin){
		itinOrigin = new_origin;
	}
	
	/**
	*sets the destination of current itinerary to be searched
	*@param new_dest the destination to set
	*/		
	public static void setItinDest(String new_dest){
		itinDest = new_dest;
	}
	
	/**
	*sets the cost of current itinerary to be searched
	*@param new_cost the cost to set
	*/		
	public static void setItinCost(String new_cost){
		itinCost = new_cost;
	}

	/**
	*gets the date of current itinerary to be searched
	*@return the date of itinerary searched
	*/	
	public static String getItinDate(){
		return itinDate;
	}

	/**
	*gets the origin of current itinerary to be searched
	*@return the origin of itinerary searched
	*/	
	public static String getItinOrigin(){
		return itinOrigin;
	}

	/**
	*gets the destination of current itinerary to be searched
	*@return the destination of itinerary searched
	*/	
	public static String getItinDest(){
		return itinDest;
	}

	/**
	*gets the cost of current itinerary to be searched
	*@return the cost of itinerary searched
	*/	
	public static String getItinCost(){
		return itinCost;
	}
	
	/**
	 * this is how we add booked itineraries from SearchItinerarys
	 * @param extra Master Detail ID
	 * @param user user to book for
	 */
	public static void addToBookedItin(String extra, String user){
		/*extra is itinerary Master Detail ID */
		Itinerary curr = null;
		/*get current itinerary list*/
		ArrayList<Itinerary> curr_itins = getCurrItin();
		for (Itinerary itin: curr_itins){
			if(itin.MasterDetailID().equals(extra)){
				curr = itin;
			}
		}
		addToBooked(curr, user);
 	}

 	/**
	*adds an itinarary to the lsit of currently booked itineraries
	*@param itin itinerary to add
	*/	
	public static void addToCurrItin(Itinerary itin){
		current_itin.add(itin);
	}

	/**
	*returns the current itinerary being searched for 
	*@return the current itinerary being searched
	*/	
	public static ArrayList<Itinerary> getCurrItin(){
		return current_itin;
	}
	
	/**
	*gets list of recent searches made by user_type (not implemented)
	*/
	@Deprecated
	public static ArrayList<String> getRecentSearches() {
		return recentSearches;
	}
	
	/**
	*sets an item to list of recently searched items 
	*@param recentSearched a list of sarch items to add (not implemented)
	*/
	@Deprecated
	public static void setRecentSearches(ArrayList<String> recentSearches) {
		ActivityVars.recentSearches = recentSearches;
	}
	
	/**
	* add item to list of recent searches
	*@param mySearch item to add
	*/
	@Deprecated
	public static void addToSearches(String mySearch) {
		ActivityVars.recentSearches.add(mySearch);
	}
	
	/**
	*set date of  flights to be searched (search flights activity)
	*@param date the date to search
	*/
	public static void setFlightDate(String date) {
		flightDate = date;
	}

	/**
	*set origin of  flights to be searched (search flights activity)
	*@param origin the origin to search
	*/
	public static void setFlightOrigin(String origin) {
		flightOrigin = origin;
	}

	/**
	*set destination of  flights to be searched (search flights activity)
	*@param dest the destination to search
	*/
	public static void setFlightDest(String dest) {
		flightDest = dest;
		
	}

	/**
	*get origin of  flights to be searched (search flights activity)
	*@return origin to search
	*/
	public static String getFlightOrigin() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	*get destination of  flights to be searched (search flights activity)
	*@return destination to search
	*/
	public static String getFlightDest() {
		// TODO Auto-generated method stub
		return flightDest;
	}

	/**
	*gets date of  flights to be searched (search flights activity)
	*@return  the date to search
	*/
	public static String getFlightDate() {
		// TODO Auto-generated method stub
		return flightDate;
	}
	
	/**
	*gets the current user in ClientInfo form for modification
	*@return the current user
	*/
	public static ClientInfo getUser(){
		ClientInfo user = null;
		try {
			if(type.equals("client")){
			user= BackendControlPanel.getInstance(null).getClientInfo(ActivityVars.getCurrUser());
			}else{
				user = BackendControlPanel.getInstance(null).getClientInfo(getAdminClientEmail());
			}
		} catch (NoPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchEntryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return user;
		}
	
	/**
	*get the email of a user the client wants to edit
	*@return the user to edit
	*/
	private static String getAdminClientEmail() {
		// TODO Auto-generated method stub
		return curr_client_admin;
	}
	
	/**
	*set the email of a user to edit (admin only)
	*@param username user to edit (email)
	*/
	static void setAdminClientEmail(String username) {
		// TODO Auto-generated method stub
		curr_client_admin = username;
	}
}
