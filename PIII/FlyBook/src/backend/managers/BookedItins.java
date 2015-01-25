/**
 * 
 */
package backend.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import backend.Itinerary;

/**
 * @author Rex Xia
 *
 */
public class BookedItins implements DatabaseEntry<String> {
    
    /** The email of the user that this BookedItins belongs to **/
    private String email;
    /** A list of all the {@link Itinerary}s that a user has booked **/
    private List<Itinerary> booked;
    
    /**
     * Create a new BookedItins entry given the email and a list of 
     * {@link Itinerary}s
     * @param email The email of the user that this BookedItins belongs to
     * @param bookedItins A list of all the {@link Itinerary}s that a user has
     * booked
     */
    public BookedItins(String email, List<Itinerary> bookedItins) {
        this.email = email;
        this.booked = new ArrayList<>(bookedItins);
    }
    
    /**
     * Create a new Booked Itins for a user with no Itineraries in it
     * @param email The email of the user that this BookedItins belongs to
     */
    public BookedItins(String email) {
        this.email = email;
        this.booked = new ArrayList<>();
    }
    
    /**
     * Add an itinerary to this BookedItins
     * @param itin the itinerary to add
     */
    public void addItin(Itinerary itin) {
        // Optional TODO: make a copy constructor for Itinerary for good
        // encapsulation
        booked.add(itin);
    }
    
    /**
     * @return The email of the client that these BookedItins belong to
     * as a unique key.
     */
    @Override
    public String getKey() {
        return email;
    }

    /**
     * Return these BookedItins as a CSV line in this format:
     * <br>"email,Itinerary,Itinerary,Itinerary"
     * <br>"email,Flight:Flight:Flight,Flight:Flight:Flight" 
     * <br>"email,a|b|c:a|b|c:a|b|c,a|b|c:a|b|c:a|b|c,a|b|c:a|b|c:a|b|c"
     * <br>a,b,c represent the parameters for FlightInfo, like flightNum,
     * departure date, etc.
     */
    @Override
    public String toCSV() {
        
        // Start with email
        String result = email + ",";
        
        // For each itinerary
        for(Itinerary itin : booked) {
            String strItin;
            strItin = itin.toCSV(false);
            // replace commas (separating flight parameters) with '|'
            strItin.replace(',', '|');
            // replace new lines (separating flights) with ':'
            strItin.replace('\n', ':');
            
            
//            // For each flight
//            for(FlightInfo flight : itin.getFlights()) {
//                // Get the csv of the flight and convert the commas to |
//                result += flight.toCSV().replace(',', '|');
//                // Add a colon to separate the flights
//                result += ":";
//            }
            
            // Remove the last colon
            result = result.substring(0, result.length()-1);
            // Add a comma to separate the itineraries
            result += ",";
        }
        
        // remove the last comma and return
        return result.substring(0, result.length()-1);
    }

    /**
     * @return A list of booked itineraries
     */
    public List<Itinerary> getBooked() {
        return new ArrayList<Itinerary>(booked);
    }

}
