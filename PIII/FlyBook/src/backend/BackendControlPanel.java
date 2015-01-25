/**
 * 
 */
package backend;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.renderscript.Type.CubemapFace;
import android.util.Log;
import backend.exceptions.AlreadyExistingEntryException;
import backend.exceptions.InvalidPasswordException;
import backend.exceptions.NoPermissionException;
import backend.exceptions.NoSuchEntryException;
import backend.managers.AuthorizationInfo;
import backend.managers.AuthorizationManager;
import backend.managers.BookedItins;
import backend.managers.BookingManager;
import backend.managers.ClientManager;
import backend.managers.ClientInfo;
import backend.managers.Manager;
import backend.managers.FlightManager;
import backend.managers.FlightInfo;

/**
 * This is the class used by the frontend to access backend features.
 * This is also responsible for managing file access for the backend databases.
 * This is a singleton.
 * 
 * @author Rex Xia
 *
 */
public class BackendControlPanel {

	//***TODO***: double-check to make sure data is saved (use saveAllData)
	//Whenever something is changed in the managers.

	// These files are all located in this app's internal storages space
	/** The name of the file storing client info **/
	private static final String CLIENTFILENAME = "clients.csv";
	/** The name of the file storing authentication info **/
	private static final String AUTHFILENAME = "passwords.txt";//"authentication.csv";
	/** The name of the file storing flight info **/
	private static final String FLIGHTFILENAME = "flights.csv";
	/** The name of the file storing bookings info **/
	private static final String BOOKINGFILENAME = "bookings.csv";

	/** 
	 * The actual instance of BackendControlPanel.
	 * It is not created right away since we need to get the application
	 * context first in order to load data. (see getInstance)
	 */
	private static BackendControlPanel instance = null;

	/** 
	 * A stored reference to the application context so the backend can
	 * access the file system.
	 */
	private Context appContext;

	/** Email of the the current user **/
	private String currUserEmail;

	/** manager of AuthorizationInfo **/
	private AuthorizationManager mAuthDB;
	/** manager of ClientInfo **/
	private ClientManager mClientDB;
	/** manager of FlightInfo **/
	private FlightManager mFlightDB; 
	/** manager of BookingsInfo **/
	private BookingManager mBookDB;

	/**
	 * Retrieve the BackendControlPanel singleton instance
	 * @param context The application context. This is used so the backend can
	 * access the file system. If the BackendControlPanel singleton has
	 * already been retrieved previously, this parameter can be null.
	 * @return The BackendControlPanel singleton instance
	 */
	public static BackendControlPanel getInstance(Context context) {

		/* If this is the first time BackendControlPanel is requested,
		 * we need to create the instance and load in all the csv files
		 * into the backend managers
		 */
		if(instance == null) {
			instance = new BackendControlPanel(context);
		}

		return instance;
	}

	/**
	 * 
	 * @param context The application context. It is used to access the file
	 * system
	 */
	private BackendControlPanel(Context context){

		// No user is logged in at the beginning.
		currUserEmail = null;

		/* We want to make sure this is an application context instead of 
		 * an activity context because the BackedControlPanel will probably
		 * outlive the activity
		 */
		appContext = context.getApplicationContext();

		// Get all the manager singletons
		mAuthDB = AuthorizationManager.getInstance();
		mClientDB = ClientManager.getInstance();
		mFlightDB = FlightManager.getInstance();
		mBookDB = BookingManager.getInstance();

		// load all the data from file
		loadAllData();
	}

	/**
	 * Loads all the data from file into the managers.
	 * If data could not be loaded for some reason (eg: data files
	 * do no exist or could not be found), the managers will be created with
	 * no data. A warning message will be written to logcat.
	 */
	public void loadAllData() {
		// Open the directory of this app's internal storage
		File dir = appContext.getFilesDir();

		// Call load on all the databases
		boolean error = false;
		try {mClientDB.load(new File(dir, CLIENTFILENAME));}
		catch (IOException e) {
			Log.e(Manager.FILEIOTAG, "Unable to load ClientDB! It will " +
					"start out empty.");
			error = true;
		}
		if(!error) {
			Log.i(Manager.FILEIOTAG, "ClientDB loaded!");
			error = false;
		}

		try {mAuthDB.load(new File(dir, AUTHFILENAME));}
		catch (IOException e) {
			Log.e(Manager.FILEIOTAG, "Unable to load AuthorizationDB! " +
					"It will " +
					"start out empty.");
			error = true;
		}
		if(!error) {
			Log.i(Manager.FILEIOTAG, "AuthorizationDB loaded!");
			error = false;
		}
		
        // If a client has a clientInfo but not AuthInfo, or vice versa,
        // Make a default entry for it
        mAuthDB.generateDefaultPasswords(mClientDB.getKeys());
        mClientDB.generateDefaultClientInfos(mAuthDB.getKeys());

		try {mFlightDB.load(new File(dir, FLIGHTFILENAME));}
		catch (IOException e) {
			Log.e(Manager.FILEIOTAG, "Unable to load FlightDB! It will " +
					"start out empty.");
			error = true;
		}
		if(!error) {
			Log.i(Manager.FILEIOTAG, "FlightDB loaded!");
			error = false;
		}

		try {mBookDB.load(new File(dir, BOOKINGFILENAME));}
		catch (IOException e) {
			Log.e(Manager.FILEIOTAG, "Unable to load BookingDB! It will " +
					"start out empty.");
			error = true;
		}
		if(!error) {
			Log.i(Manager.FILEIOTAG, "BookingDB loaded!");
			error = false;
		}
		//        try {
		//            mAuthDB.load(new File(dir, AUTHFILENAME));
		//            mClientDB.load(new File(dir, CLIENTFILENAME));
		//            mFlightDB.load(new File(dir, FLIGHTFILENAME));
		//            mBookDB.load(new File(dir, BOOKINGFILENAME));
		//        }catch (IOException | AlreadyExistingEntryException e) {
		//            e.printStackTrace();
		//            
		//            /* If any of the above can't be loaded for any reason,
		//             * we make sure all the databases are cleared to prevent partial
		//             * entries that don't make sense.
		//             * Eg: a user has an entry in AuthDB but not in ClientDB
		//             */
		//            mAuthDB.clearData();
		//            mClientDB.clearData();
		//            mFlightDB.clearData();
		//            
		//            // Print an error message to logcat
		//            Log.e(Database.FILEIOTAG, "Error during loading of files into " +
		//                    "databases. databases will start empty.");
		//            return;
		//        }
		//        
		//        // Print an info message to logcat if file reading succeeded
		//        Log.i(Database.FILEIOTAG, "Loaded data into databases");
	}

	/**
	 * Saves all the data from the databases into csv files. This method must
	 * be run before the app shuts down.
	 * @param method The name of the method calling saveAllData. This will be 
	 * appended to the start of log messages saying if the save worked or not.
	 */
	public void saveAllData(String method) {
		// Add a colon to the message
		method += ": ";

		// Get the directory of this app's internal storage
		File dir = appContext.getFilesDir();

		// Call save on the databases
		boolean error = false;
		try {mClientDB.save(new File(dir, CLIENTFILENAME));}
		catch (IOException e) {
			Log.e(Manager.FILEIOTAG, method + "Unable to save ClientDB!");
			error = true;
		}
		if(!error) {
			Log.i(Manager.FILEIOTAG, method + "ClientDB saved!");
			error = false;
		}

		try{mAuthDB.save(new File(dir, AUTHFILENAME));}
		catch (IOException e) {
			Log.e(Manager.FILEIOTAG, method + "Unable to save AuthorizationDB!");
			error = true;
		}
		if(!error) {
			Log.i(Manager.FILEIOTAG, method + "AuthorizationDB saved!");
			error = false;
		}

		try{mFlightDB.save(new File(dir, FLIGHTFILENAME));}
		catch (IOException e) {
			Log.e(Manager.FILEIOTAG, method + "Unable to save FlightDB!");
			error = true;
		}
		if(!error) {
			Log.i(Manager.FILEIOTAG, method + "FlightDB saved!");
			error = false;
		}

		try{mBookDB.save(new File(dir, BOOKINGFILENAME));}
		catch (IOException e) {
			Log.e(Manager.FILEIOTAG, method + "Unable to save BookingDB!");
			error = true;
		}
		if(!error) {
			Log.i(Manager.FILEIOTAG, method + "BookingDB saved!");
			error = false;
		}
	}

	/**
	 * Reads a csv file formatted according to the handout and imports
	 * the client data in it.
	 * @param path Path of the csv file to import from. This path is
	 * relative to this app's internal storage space
	 * @throws IOException Error in file operations
	 */
	public void importClientInfo(String path) throws IOException{
        // Open the directory of this app's internal storage
        File dir = appContext.getFilesDir();
	    
		mClientDB.load(new File(dir,path));

		// Make a AuthorizationInfo for the clients imported
		// and have the password default to the default password
		mAuthDB.generateDefaultPasswords(mClientDB.getKeys());
		//^ inefficient way to do it. If have time will make sure that only
		//  newly added emails are passed in

		saveAllData("importClientInfo");
	}

	/**
	 * Reads a csv file formatted according to the handout and imports the
	 * flight data in it
	 * @param path Path of the csv file to import from. This path is
     * relative to this app's internal storage space
	 * @throws IOException Error in file operations
	 */
	public void importFlightInfo(String path) throws IOException {
	    // Open the directory of this app's internal storage
        File dir = appContext.getFilesDir();
        
        mFlightDB.load(new File(dir,path));

		saveAllData("importFlightInfo");
	}

	/**
	 * Log in the user with the specified email (username) and password
	 * @param email The email of the user to log in
	 * @param password The password of the user to log in
	 * @throws NoSuchEntryException If the user cannot be found
	 * @throws InvalidPasswordException If the password is incorrect
	 */
	public void login(String email, String password)
			throws NoSuchEntryException, InvalidPasswordException {

		// Check if the email and password matches
		if(!mAuthDB.checkPassword(email, password)) {
			throw new InvalidPasswordException(String.format(
					"Invalid password for %s", email));
		}

		// If get to this point password was valid

		// Set current user
		currUserEmail = mClientDB.getClient(email).getEmail();
	}
	/**
	 * Log out the current logged in user. Also saves all data
	 */
	public void logout(){
		// un-set current user
		currUserEmail = null;

		saveAllData("logout");
	}

	/**
	 * Checks whether the current logged-in user is an admin
	 * @return true if the current logged-in user is an admin
	 */
	public boolean isAdmin() {
		try {
			return mAuthDB.isAdmin(currUserEmail);
		} catch (NoSuchEntryException e) {
			/* This should never happen, since the current logged in user
			 * must have been checked against dbAuth previously during login.
			 */
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Adds a new user to the backend databases
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param address
	 * @param creditCardNumber
	 * @param expiryDate
	 * @param isAdmin
	 * @param password
	 * @throws AlreadyExistingEntryException
	 */
	public void addNewUser(String firstName, String lastName, 
			String email, String address, String creditCardNumber, 
			String expiryDate, boolean isAdmin, String password) 
					throws AlreadyExistingEntryException{

		mClientDB.addNewClient(firstName, lastName, email, address, 
				creditCardNumber, expiryDate);
		mAuthDB.addNewUser(email, password, isAdmin);

		saveAllData("addNewUser");
	}

	/**
	 * Changes the password
	 * @param email
	 * @param oldPass
	 * @param newPass
	 * @throws NoSuchEntryException 
	 * @throws InvalidPasswordException 
	 * @throws NoPermissionException 
	 */
	public void changePassword(String email, String oldPass, String newPass)
			throws NoSuchEntryException, InvalidPasswordException, NoPermissionException {
		checkPermissionClient(email, "changePassword");
		mAuthDB.changePassword(email, oldPass, newPass);

		saveAllData("changePassword");
	}

	/**
	 * @return the current logged-in user's email
	 */
	public String getCurrUserEmail() {
		return currUserEmail;
	}

	/**
	 * (Temp method)(Please use searchFlights instead)
	 * Returns a list of FlightInfos (Not an itenerary, just a list of flights)
	 * @return a list of FlightInfo
	 */
	@Deprecated
	public static ArrayList<FlightInfo> getFlightInfo() {
		FlightInfo f2 = new FlightInfo("5", "2014-10-06 15:27", "2014-10-06 20:27", "AC", 
				"Toronto", "Venice", 100000000, 1);
		FlightInfo f3 = new FlightInfo("2", "2014-10-06 14:27", "2014-10-06 20:27", "AC", 
				"Los Angeles", "Toronto", 10000000, 2);
		FlightInfo f4 = new FlightInfo("4", "2014-10-06 13:27", "2014-10-06 20:27", "AC", 
				"Montreal", "London", 1000000, 3);
		FlightInfo f5 = new FlightInfo("3", "2014-10-06 12:27", "2014-10-06 20:27", "AC", 
				"Toronto", "New York City", 100000, 4);

		ArrayList<FlightInfo> flights = new ArrayList<FlightInfo>();
		flights.add(f2);
		flights.add(f3);
		flights.add(f4);
		flights.add(f5);
		return flights;
	}

	/**
	 * Return a list of flights meeting certain search parameters
	 * @param date
	 * @param origin
	 * @param destination
	 * @return a list of flights. This is not organized into an itinerary.
	 */
	public List<FlightInfo> searchFlights(String date,
			String origin, String destination){
	    
	    Log.d("searchFlights", String.format("params: %s\n%s\n%s", date, origin, destination));

	    Log.d("searchFlights", "getAllFlights: " + mFlightDB.getAllFlights().toString());
	    Log.d("searchFlights", "secondFlight: " + mFlightDB.getAllFlights().get(1).toCSV());
	    
		// Create a new search
		Search searchResults = new Search(
				// Retrieve flights from FlightDB
				mFlightDB.getAllFlights(),
				// Pass in the search parameters
				date, origin, destination);
		
		Log.d("searchFlights", "searchResults: " + searchResults.toCSV());

		// For all the itineraries with only one flight in them,
		// extract the Flight and put it in a list
		ArrayList<FlightInfo> singleFlights = new ArrayList<>();
		for(Itinerary itin: searchResults.getRealItineraries(1)) {
			singleFlights.add(itin.getFlights().get(0));
		}
		
		Log.d("searchFlights", "singleFlights: " + singleFlights);

		//Log.i("search(back)", singleFlights);
		return singleFlights;
	}

	/**
	 * 
	 * @param FlightID
	 * @return
	 * @throws NoSuchEntryException 
	 */
	public FlightInfo getFlightByID(String FlightID) throws NoSuchEntryException {
		FlightInfo temp = null;
		for(FlightInfo flight : mFlightDB.getAllFlights()){
			if(flight.MasterDetailID() == FlightID){
				temp = flight;
			}
		}
		if(temp == null){
			throw new NoSuchEntryException();
		}
		else{
			return temp;
		}
	}

	/**
	 * Updates a flights info
	 * @param updatedFlight
	 * @throws NoSuchEntryException 
	 * @throws NoPermissionException 
	 */
	public void setFlightInfo(FlightInfo updatedFlight)
			throws NoSuchEntryException, NoPermissionException {
		// check that user is an admin
		checkPermissionIsAdmin("setFlightInfo");
		mFlightDB.setFlight(updatedFlight.getFlightID(), updatedFlight);

		saveAllData("setFlightInfo");
	}

	/**
	 * Search for a list of Itineraries based on the parameters and return
	 * them sorted by cost
	 * @param date
	 * @param origin
	 * @param destination
	 * @return a list of Itineraries sorted by cost
	 */
	public List<Itinerary> searchItinerariesSortedByCost(String date,
			String origin, String destination) {
		Search results = new Search(mFlightDB.getAllFlights(), 
				date, origin, destination);
		return results.sortByCost();
	}

	/**
	 * Search for a list of Itineraries based on the parameters and return
	 * them sorted by duration
	 * @param date
	 * @param origin
	 * @param destination
	 * @return a list of Itineraries sorted by duration
	 */
	public List<Itinerary> searchItinerariesSortedByTime(String date,
			String origin, String destination) {

		Search results = new Search(mFlightDB.getAllFlights(), 
				date, origin, destination);
		return results.sortByTime();
	}

	/**
	 * (Temp method) (please use getAllClientInfo() instead)
	 * Return all the ClientInfos
	 * @return
	 */
	@Deprecated
	public static List<ClientInfo> getClientInfo(){
		ArrayList<ClientInfo> info = new ArrayList<ClientInfo>();
		info.add(new ClientInfo("THAT", "GUY", 
				"THATGUY@WHOISHE.?", "DONT KNOW!", "N/A", "N/a"));
		return info;
	}

	/**
	 * Return the ClientInfo of all the users in the database.
	 * Only Admins have permission to do this.
	 * @return a list of 
	 * @throws NoPermissionException If the current logged in user is not
	 * an admin.
	 */
	public List<ClientInfo> getAllClientInfo() throws NoPermissionException{

		// Check permission (no permission if not admin)
		checkPermissionIsAdmin("getAllClientInfo");

		// Get all clients
		ArrayList<ClientInfo> result = new ArrayList<>();
		// Loop through all the emails and add its ClientInfo to the results
		for(String email : mClientDB.getKeys()) {
			try {
				result.add(mClientDB.getClient(email));
			} catch (NoSuchEntryException e) {
				// Should never happen, since we got the emails from the
				// database to begin with
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * Get the ClientInfo of a specific client
	 * @param email The email of the ClientInfo requested
	 * @return The ClientInfo corresponding to the email
	 * @throws NoPermissionException If the current logged in user is not
	 * an admin and tried to access someone else's client info.
	 * @throws NoSuchEntryException if the client does not exist in the
	 * client database.
	 */
	public ClientInfo getClientInfo(String email) 
			throws NoPermissionException, NoSuchEntryException{

		// Check if user has permission.
		checkPermissionClient(email, "getClientInfo");

		// Get the ClientInfo and return
		return mClientDB.getClient(email);
	}

	/**
	 * Updates a client info
	 * @param email The email of the client to change info for. Note: If the
	 * the user wants to change their email, this parameter should be the old
	 * email
	 * @param newInfo The new value of the ClientInfo
	 * @throws NoSuchEntryException 
	 * @throws NoPermissionException 
	 * @throws AlreadyExistingEntryException  If the email is changed, and
	 * another user already has the same email as the new email
	 */
	public void setClientInfo(String email, ClientInfo newInfo)
			throws NoSuchEntryException, NoPermissionException, AlreadyExistingEntryException {
		// check if user has permission to change the client's info
		checkPermissionClient(email, "setClientInfo");

		// Handle the case where the email changes
		// Need to change the email in many different managers
		if(!email.equals(newInfo.getEmail())) {
			changeEmail(email, newInfo.getEmail());
		}

		// Set the info under the new email
		mClientDB.setClient(newInfo.getEmail(), newInfo);

		saveAllData("setClientInfo");
	}

	/**
	 * 
	 * @param oldEmail
	 * @param newEmail
	 * @throws AlreadyExistingEntryException If another client already has
	 * the same email as the newEmail
	 */
	private void changeEmail(String oldEmail, String newEmail) throws AlreadyExistingEntryException {

		// Change the email in all managers that use it as a key
		mClientDB.changeKey(oldEmail, newEmail);
		mAuthDB.changeKey(oldEmail, newEmail);
		mBookDB.changeKey(oldEmail, newEmail);
	}

	/**
	 * Check if the current user has permission to access data for a specific
	 * client. The current user has permission if they are an admin or
	 * if they are accessing their own info.
	 * @param email The email of the client to be accessed
	 * @param methodName Name of the method, to put in the error message
	 * @throws NoPermissionException If the current user has no permission to
	 * access the client's info
	 */
	private void checkPermissionClient(String email, String methodName)
			throws NoPermissionException {
		if(!isAdmin() && !currUserEmail.equals(email)) {
			throw new NoPermissionException(currUserEmail, methodName);
		}
	}

	/**
	 * Check if the current user has permission to access admin-only features
	 * @param methodName Name of the method, to put in the error message
	 * @throws NoPermissionException If the current user is not an admin
	 */
	private void checkPermissionIsAdmin(String methodName)
			throws NoPermissionException {
		if(!isAdmin()) {
			throw new NoPermissionException(currUserEmail, methodName);
		}
	}

	/**
	 * Return the booked Itineraries of the current logged in user
	 * @return
	 */
	public List<Itinerary> getBookedItins(){
		try {
			return getBookedItins(currUserEmail);
		} catch (NoPermissionException | NoSuchEntryException e) {
			// Should never happen since current user can access own inf
			// and current user does exist in the database
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Return the booked Itineraries of a specific user
	 * @param email The email of the user to get Itineraries from
	 * @return A list of Itineraries that the user has booked
	 * @throws NoPermissionException if the current logged in user is not
	 * an admin and tried to access someone else's booked Intineraries.
	 */
	public List<Itinerary> getBookedItins(String email) 
			throws NoPermissionException, NoSuchEntryException{
		// Check if the user has permission
		checkPermissionClient(email, "getBookedItins");

		// Retrieve the booked Itineraries and return
		return mBookDB.getBookedItins(email);

	}

	/**
	 * Add an Itinerary to a user's booked itineraries list
	 * @param email
	 * @throws NoPermissionException if the current logged-in user is not an
	 * admin and tried to book an itin for someone else.
	 */
	public void bookItin(String email, Itinerary itin)
	        throws NoPermissionException {
		final String METHODNAME = "bookItin";
	    
	    // Check if the user has permission
	    checkPermissionClient(email, METHODNAME);
	    
	    // Book the itin
	    mBookDB.bookItin(email, itin);
	    
	    // save the data
		saveAllData(METHODNAME);
	}

}
