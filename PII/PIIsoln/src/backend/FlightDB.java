/**
 * 
 */
package backend;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A Database of Flights
 * Stores Flights
 * Can upload Flights from csv (same format as handout)
 * Can also output a csv file (same format as handout)
 * @author xiaxiuqi
 *
 */
public class FlightDB extends Database<String, FlightInfo> {
	
	private static int NUMBERINDEX = 0;
	private static int DEPARTINDEX = 1;
	private static int ARRIVEINDEX = 2;
	private static int AIRLINEINDEX = 3;
	private static int ORIGININDEX = 4;
	private static int DESTINDEX = 5;
	private static int PRICEINDEX = 6;
	/**
	 * Creates a new flightDB by loading the data from the specified path
	 * @param path The path of the csv file to load data from.
	 * The file must be formatted according to the specifications in the
	 * Phase 2 handout.
	 * @throws IOException Error in reading the CSV file
	 */
	public FlightDB(String path) throws IOException{
		super();
	    load(path);
	}
	
	/**
	 * Creates a new empty FlightDB
	 */
	public FlightDB() {
        super();
    }

    /* (non-Javadoc)
	 * @see backend.Database#parseLine(java.lang.String)
	 */
	@Override
	protected FlightInfo parseLine(String line) {
		// Split the line into comma-delimited tokens
		String [] tokens = line.split(",");
		
		// Create a new Flight with the required information
		FlightInfo newFlight = new FlightInfo(
				tokens[NUMBERINDEX],
				tokens[DEPARTINDEX],
				tokens[ARRIVEINDEX],
				tokens[AIRLINEINDEX],
				tokens[ORIGININDEX],
				tokens[DESTINDEX],
				Integer.parseInt(tokens[PRICEINDEX]));
		
		// Return the new ClientInfo
		return newFlight;
	}

	/**
	 * This method will be used by Daniel's Search
	 * to get data from this FlightDB as an ArrayList
	 * @return All the flights in the database as an ArrayList of Flights
	 */
	public ArrayList<FlightInfo> getAllFlights(){
		
	    // Add all the values in the records map to a new ArrayList
	    ArrayList<FlightInfo> allFlights = new ArrayList<FlightInfo>();
	    for(FlightInfo f: dataTable.values()) {
	        // Make a new copy of each flight as we add them to the list
	        allFlights.add(new FlightInfo(f));
	    }
	    
	    // Safe to return since everything is a copy, not the original
	    return allFlights;
	}

}
