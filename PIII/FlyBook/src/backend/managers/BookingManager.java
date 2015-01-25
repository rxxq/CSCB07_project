/**
 * 
 */
package backend.managers;

import java.util.ArrayList;
import java.util.List;

import backend.Itinerary;
import backend.exceptions.AlreadyExistingEntryException;
import backend.exceptions.InvalidCSVFormatException;
import backend.exceptions.NoSuchEntryException;

/**
 * A database that stores the itineraries that each user has booked.
 * It is a singleton.
 * @author Rex Xia
 *
 */
public class BookingManager extends Manager<String, BookedItins> {
    /**
     * The actual singleton instance. We create this as soon as the app
     * starts up because we will need this for sure, so we might as well
     * create it.
     */
    private static final BookingManager instance = new BookingManager();
    
    /** The name of this database for exception messages **/
    private final String DBNAME = "BookingsDB";
    
    /**
     * Retrieve the one and only instance of BookingsDB
     * @return The only instance of BookingsDB
     */
    public static BookingManager getInstance() {
        return instance;
    }
    
    /** 
     * This is a singleton, so the constructor is private
     */
    private BookingManager() {
        super();
    }
    
    
    /* (non-Javadoc)
     * @see backend.Database#parseLine(java.lang.String)
     */
    @Override
    public BookedItins parseLine(String line) throws InvalidCSVFormatException {
        
        try {
            // Split the line by commas
            // (the -1 argument keeps empty strings between commas)
            String[] tokens = line.split(",",-1);
            
            // The initial token is the email
            String email = tokens[0];
            
            // The rest of the tokens represent Itineraries
            // Create a list of Itineraries
            ArrayList <Itinerary> itins = new ArrayList<>();
            // For each Itinerary string
            String strItin;
            for(int i = 1; i < tokens.length; i++) {
                strItin = tokens[i];
                
                // Parse a list of flights
                ArrayList<FlightInfo> flights = new ArrayList<>();
                
                // Each itinerary string consists of FlightInfo separated by ':'            
                String[] flightStrs = strItin.split(":");
                for(String csvFlight : flightStrs) {
                    // replace the "|" between the parameters with commas
                    // to get the properly formatted csv for a flight
                    csvFlight = csvFlight.replace('|', ',');
                    // Create a new FlightInfo from the csv and add to flights
                    flights.add(new FlightInfo(csvFlight));
                }
                
                // Create a new Itinerary from the list of flights and add to
                // the list of Itineraries
                itins.add(new Itinerary(flights));
            }
            
            // Create and return a new BookedItins from the email and the 
            // list of Itineraries
            return new BookedItins(email, itins);
        } 
        catch (ArrayIndexOutOfBoundsException e) {
            // If at any point we get array out of bounds, then the CSV is
            // probably incorrectly formatted
            throw new InvalidCSVFormatException(DBNAME);
        }
    }
    
    /**
     * Return a list of Itineraries booked by a user
     * @param email The email of the user to retrieve bookings for
     * @return A (copy of) the list of Itineraries booked by 
     * the user specified by the email
     * @throws NoSuchEntryException If the email does not exist in this
     * manager.
     */
    public List<Itinerary> getBookedItins(String email)
            throws NoSuchEntryException{
        // return a copy
        return new ArrayList<>(getEntry(email).getBooked());
        
    }
    
    /**
     * Add an itinerary to a users' booked itineraries list. If the user did
     * not previously have a record in this BookingManager, a record will
     * be created for them
     * @param email The email of the user to add the itinerary for.
     * @param itin The itinerary to add.
     */
    public void bookItin(String email, Itinerary itin) {
        // Check if a record exists for this user
        BookedItins record;
        try {
            record = getEntry(email);
            // If the record exists, add the itin to it and re-add it to the
            // dataTable
            record.addItin(itin);
            setEntry(email, record);
        }
        catch(NoSuchEntryException e) {
            // If the record does not exist,
            // make a new record to add to this BookingManager
            record = new BookedItins(email);
            record.addItin(itin);
            try {addNewEntry(email, record);}
            catch (AlreadyExistingEntryException e1) {
                // should never happen since we already know the entry does
                // not exist
                e1.printStackTrace();
            }
        }
    }
}
