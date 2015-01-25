package backend;

import java.io.IOException;
import java.util.ArrayList;

import backend.exceptions.AlreadyExistingEntryException;
import backend.exceptions.NoSuchEntryException;
import backend.managers.ClientManager;
import backend.managers.FlightManager;
import backend.managers.FlightInfo;

/**
 * Required class for marking.
 * @author Rex Xia
 *
 */
public class Driver {
	
	/** Database of ClientInfo **/
    protected static ClientManager mClientDB = ClientManager.getInstance();
    /** Database of Flight **/
    protected static FlightManager mFlightsDB = FlightManager.getInstance();

    
	/**
	 * Uploads the clients to the database
	 * @param path
	 */
	public static void uploadClientInfo(String path){
		try {
            mClientDB.load(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	/**
	 * Uploads the flights to the database
	 * @param path
	 */
	public static void uploadFlightInfo(String path){
		try {
            mFlightsDB.load(path);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	/**
	 * Gets a client from the database
	 * @param email
	 * @return client
	 */
	public static String getClient(String email){
		try {
            return mClientDB.getClient(email).toCSV();
        } catch (NoSuchEntryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
	}
	
	/**
	 * Gets all flights that match the criteria
	 * @param date
	 * @param origin
	 * @param destination
	 * @return flights
	 */
	public static String getFlights(String date, String origin, String destination){
	    
	    // Create a new search
	    Search searchResults = new Search(
	            // Retrieve flights from FlightDB
	            mFlightsDB.getAllFlights(), 
	            // Pass in the search parameters
	            date, origin, destination);
	    
	    // For all the itineraries with only one flight in them,
	    // extract the Flight and put it in a list
	    ArrayList<FlightInfo> singleFlights = new ArrayList<>();
	    for(ArrayList<FlightInfo> itin: searchResults.getItineraries(1)) {
	        singleFlights.add(itin.get(0));
	    }
	    
	    // Build a string consisting of each flight in CSV format
	    String flightCSV = new String();
	    for(FlightInfo f: singleFlights) {
	        flightCSV = f.toCSV() + "\n";
	    }
	    
	    return flightCSV;
	}
	
	/**
	 * Gets all itineraries that match the criteria
	 * @param date
	 * @param origin
	 * @param destination
	 * @return itineraries
	 */
	public static String getItineraries(String date, String origin, String destination){
		Search itineraries = new Search(mFlightsDB.getAllFlights(), date, origin, destination);
		return itineraries.toCSV();
	}
	
	/**
	 * Gets all itineraries that match the criteria and sorts by Cost
	 * @param date
	 * @param origin
	 * @param destination
	 * @return itineraries
	 */
	public static String getItinerariesSortedByCost(String date, String origin, String destination){
		Search itineraries = new Search(mFlightsDB.getAllFlights(), date, origin, destination);
		return Search.toCSV(itineraries.sortByCost());
	}
	/**
	 * Gets all itineraries that match the criteria and sorts by Time
	 * @param date
	 * @param origin
	 * @param destination
	 * @return itineraries
	 */
	public static String getItinerariesSortedByTime(String date, String origin, String destination){
		Search itineraries = new Search(mFlightsDB.getAllFlights(), date, origin, destination);
		return Search.toCSV(itineraries.sortByTime());
	}
	
}
