package backend;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Required class for marking.
 * @author Rex Xia
 *
 */
public class Driver {
	
	/** Database of ClientInfo **/
    protected static ClientDB dbClients = new ClientDB();
    /** Database of Flight **/
    protected static FlightDB dbFlights = new FlightDB();

    
	/**
	 * Uploads the clients to the database
	 * @param path
	 */
	public static void uploadClientInfo(String path){
		try {
            dbClients.load(path);
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
            dbFlights.load(path);
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
            return dbClients.getClient(email).toCSV();
        } catch (NoSuchUserException e) {
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
	            dbFlights.getAllFlights(), 
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
		Search itineraries = new Search(dbFlights.getAllFlights(), date, origin, destination);
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
		Search itineraries = new Search(dbFlights.getAllFlights(), date, origin, destination);
		return Search.toCSV(itineraries.sortByCost(itineraries.getItineraries()));
	}
	/**
	 * Gets all itineraries that match the criteria and sorts by Time
	 * @param date
	 * @param origin
	 * @param destination
	 * @return itineraries
	 */
	public static String getItinerariesSortedByTime(String date, String origin, String destination){
		Search itineraries = new Search(dbFlights.getAllFlights(), date, origin, destination);
		return Search.toCSV(itineraries.sortByTime());
	}
	
}
