/**
 * 
 */
package backend.testing;

import java.util.ArrayList;

import backend.*;
import backend.managers.FlightInfo;

/**
 * @author Rex Xia
 *
 */
public class DriverTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
    	System.out.println("Test upload and get Clients");
        Driver.uploadClientInfo("./src/backend/testing/test_data/test_users");
        System.out.println(Driver.getClient("Email1"));
        System.out.println(Driver.getClient("Email2"));
        System.out.println(Driver.getClient("Email3"));
        
        Driver.uploadFlightInfo("./src/backend/testing/test_data/test_flights");
        System.out.println(Driver.getItineraries("1999-01-01", "Origin", "Destination"));
        
        FlightInfo MalaysianAirlines = new FlightInfo("1", "2014-10-06 16:27", "2014-10-06 20:27", "AC", 
    			"Toronto", "Montreal", 1000000000, 1);
        FlightInfo f2 = new FlightInfo("5", "2014-10-06 15:27", "2014-10-06 20:27", "AC", 
    			"Toronto", "Montreal", 100000000, 2);
        FlightInfo f3 = new FlightInfo("2", "2014-10-06 14:27", "2014-10-06 20:27", "AC", 
    			"Toronto", "Montreal", 10000000, 3);
        FlightInfo f4 = new FlightInfo("4", "2014-10-06 13:27", "2014-10-06 20:27", "AC", 
    			"Toronto", "Montreal", 1000000, 4);
        FlightInfo f5 = new FlightInfo("3", "2014-10-06 12:27", "2014-10-06 20:27", "AC", 
    			"Toronto", "Montreal", 100000, 5);
        
        ArrayList<FlightInfo> flights = new ArrayList<FlightInfo>();
        flights.add(f2);
        flights.add(f3);
        flights.add(f4);
        flights.add(f5);
        flights.add(MalaysianAirlines);
        Search s = new Search(flights, "2014-10-06", "Toronto", "Montreal");
        System.out.println((s.sortByTime()));

    }

}
