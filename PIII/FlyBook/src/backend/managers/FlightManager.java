/**
 * 
 */
package backend.managers;

import java.io.IOException;
import java.util.ArrayList;

import backend.exceptions.InvalidCSVFormatException;
import backend.exceptions.NoSuchEntryException;

/**
 * A manager of Flights.
 * Stores Flights.
 * Can upload Flights from csv (same format as handout).
 * Can also output a csv file (same format as handout).
 * This class is a singleton.
 * @author xiaxiuqi
 *
 */
public class FlightManager extends Manager<String, FlightInfo> {
	
    /**
     * The actual singleton instance. We create this as soon as the app
     * starts up because we will need this for sure, so we might as well
     * create it.
     */
    private static final FlightManager instance = new FlightManager();
    
//	private static int NUMBERINDEX = 0;
//	private static int DEPARTINDEX = 1;
//	private static int ARRIVEINDEX = 2;
//	private static int AIRLINEINDEX = 3;
//	private static int ORIGININDEX = 4;
//	private static int DESTINDEX = 5;
//	private static int PRICEINDEX = 6;
    /** The name of this database for exception messages **/
    private final String DBNAME = "FlightDB";
	
    /**
     * Retrieve the one and only instance of FlightDB
     * @return The only instance of FlightDB
     */
    public static FlightManager getInstance() {
        return instance;
    }

    /**
     * Creates a new empty FlightDB
     */
    private FlightManager() {
        super();     
    }

//    /**
//	 * Creates a new flightDB by loading the data from the specified path
//	 * @param path The path of the csv file to load data from.
//	 * The file must be formatted according to the specifications in the
//	 * Phase 2 handout.
//	 * @throws IOException Error in reading the CSV file
//	 */
//	private FlightDB(String path) throws IOException{
//        // First make an empty instance and set it to be the only instance
//        this();
//        // Load data into it
//        load(path);
//	}
	
	/* (non-Javadoc)
	 * @see backend.Database#parseLine(java.lang.String)
	 */
	@Override
    public FlightInfo parseLine(String line)
	        throws InvalidCSVFormatException {
	    
//		// Split the line into comma-delimited tokens
//		String [] tokens = line.split(",");
//		
//		// Create a new Flight with the required information
//		FlightInfo newFlight;
//        try {
//            newFlight = new FlightInfo(
//            		tokens[NUMBERINDEX],
//            		tokens[DEPARTINDEX],
//            		tokens[ARRIVEINDEX],
//            		tokens[AIRLINEINDEX],
//            		tokens[ORIGININDEX],
//            		tokens[DESTINDEX],
//            		Integer.parseInt(tokens[PRICEINDEX]));
//        } catch (ArrayIndexOutOfBoundsException e) {
//            // If the the indices are out of bounds the csv line is probably
//            // not formatted correctly
//            throw new InvalidCSVFormatException(DBNAME);
//        }
		
	    /*
	     * Design note: For FlightDB, I put the parsing code in the constructor
	     * for FlightInfo so that BookingsDB can also access the same parsing
	     * code. This is a compromise solution betwen two options:
	     * 1) Put all the parsing code in all the DatabaseEntries
	     *     Problem: There is no way to specify a constructor in interfaces
	     *         If we made it a method instead, we would need an instance
	     *         of a DatabaseEntry just to be able to call the parseLine
	     *         method from it. It's also not possible to make an interface
	     *         specify a static method
	     * 2) Put all the parsing code in all the subclasses of Database
	     *     Problem: One database cannot access the parsing method in another
	     *         because it would require an instance of the other database.
	     *         it's also not possible to make an abstract static method
	     *         in the Database parent class
	     */
		try {
		    return new FlightInfo(line);
		}
		catch(InvalidCSVFormatException e) {
		    // re-throw the exception with the name of this database
		    throw new InvalidCSVFormatException(DBNAME);
		}
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
	
	/**
	 * Updates a flights info
	 * @param key
	 * @param updatedEntry
	 * @throws NoSuchEntryException if no flight with that key exists
	 */
	public void setFlight(String key, FlightInfo updatedEntry) throws NoSuchEntryException{
		setEntry(key, new FlightInfo(updatedEntry));
	}
}
