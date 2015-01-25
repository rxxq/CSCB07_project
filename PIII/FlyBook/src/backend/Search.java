package backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

// NOTE: Automarker did not compile with this import
//import android.util.Log;
import backend.managers.FlightInfo;



public class Search {
/**
 * @auth Daniel
 */ 

/* @flights a ArrayList of flights
 * @itineraries list of itineraries
 * @origin the origin for all itineraries
 * @dest the destination for all itineraries
 */
	private ArrayList<FlightInfo> flights;
	private ArrayList<ArrayList<FlightInfo>> itineraries = new ArrayList<ArrayList<FlightInfo>>();
	private String origin;
	private String dest;
	private String Date;
	/**defualt constructor
	 * @param flights an arraylist of flights to convert to itineraries
	 * @param origin origin for all itineraries that will be generated
	 * @param dest final destination for all itineraries
	 * @param date date of intial flights in itineraries 
	 */
	public Search(ArrayList<FlightInfo> flights, String date, String origin, String dest){
		/* straightforward intialization*/
		this.flights = flights;
		this.origin = origin;
		this.dest = dest;
		this.Date = date;
		/* set the itineraries, start by iterating through each flight in flight list */
		for(FlightInfo flight: this.flights){
			/* for each flight @ origin create a list of visited nodes with flight as the intial node (i.e. root) 
			 * then call setItineraries to get remaining nodes, if they exist */
		    //Log.d("searchFlights", "origin match: " + flight.getOrigin().equals(this.origin));
		    //Log.d("searchFlights", "departure time match: " + flight.getDepartureDate().equals(this.Date));
		    //Log.d("searchFlights", flight.getDepartureDate() + "|" + this.Date);
			if(flight.getOrigin().equals(this.origin) && flight.getDepartureDate().equals(this.Date)){ 
                //Log.d("searchFlights", "origin and date equals");
			    ArrayList<FlightInfo> visited = new ArrayList<FlightInfo>();
				visited.add(flight);
				setItineraries(flight, visited);
			}//end if
		}//end loop
		
		//Log.d("searchFlights", "itineraries: " + itineraries.toString());
	}
	

	/**
	 * this method generates a ArrayList of valid itineraries and adds to global value
	 * itineraries accordingly
	 * @param flight a node in itinerary
	 * @param visited a list of visited nodes
	 */
	@SuppressWarnings("unchecked")
	private void setItineraries(FlightInfo flight, ArrayList<FlightInfo> visited) {
        //Log.d("searchFlights", "setItineraries flight: " + flight.toString());
        //Log.d("searchFlights", "setItineraries visited: " + visited.toString());
        
	    /* if we are @ destination add of list visited nodes to itineraries, 
		 * (i.e. the path we took is our itinerary to add 
		 * 										to our list of itineraries) */
		if(flight.getDestination().equals(this.dest)){ 
			itineraries.add(visited);
			//Log.d("searchFlights", "setItineraries at destination");
		}else{
			/*otherwise for each flight in flights that we havent visted that has a 
			 * origin at our current location (i.e. flight.destination) check if
			 * its a valid node, (i.e. if its departure time is 6 hours or less away) */
			for(FlightInfo other: this.flights){
				if( (!visited.contains(other)) &&
						(other.getOrigin().equals(flight.getDestination()))){
				    //Log.d("searchFlights", "setItineraries connecting but not yet valid");
					if(validNode(flight, other)){
				           //Log.d("searchFlights", "setItineraries valid node");
						/* if 6 hour rule holds, mark this flight as visited and recursivley move on until
						 * (a) we run out of nodes or (b) we arrive at a node that gets us to destination 
						 * we use temp_vis so the intial visted remains unchanged, otherwise we would not
						 * be able to explore all itineraries at flight*/
						ArrayList<FlightInfo> temp_vis = (ArrayList<FlightInfo>) visited.clone();
						temp_vis.add(other);
						setItineraries(other, temp_vis);
					} //end if
				} // end if
			} // end loop
		} // end else
	}

	/**
	 * checks if two nodes are 6 hours apart, takes into account leap years & etc.
	 * @param fl
	 * @param flight
	 * @return
	 */
	private boolean validNode(FlightInfo flight_1, FlightInfo flight_2) {
		/* initialize new simple date format in handout format for Phase 2
		 *  && get dates from two flights by calling their appropriate getters */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date_1 = flight_1.getArrivalDateTime();
		String date_2 = flight_2.getDepartureDateTime();
		
		/*we will want to make two date objects date to read and conver flight_1's  & flight_2's 
		 * date, both set to null because we need to  catch ParseExceptions when initialzing dates */
		Date date = null;
		Date other = null;

		/* intialize empty calendars to store dates of flight_1 and flight_2*/
		Calendar cal_1 = new GregorianCalendar();
		Calendar cal_2 = new GregorianCalendar();
	
		try {
			/* straight forward intialization, see java.util.Calendar @ Java.util.Date*/
			date = sdf.parse(date_1);
			other = sdf.parse(date_2);
			cal_1.setTime(date);
			cal_2.setTime(other);
		} catch (ParseException e) { //catch thrown exception by Date
			e.printStackTrace();
		}
		//Log.d("searchFlights", "validNode: date: " + date.toString());
		//Log.d("searchFlights", "validNode: other: " + other.toString());
        //Log.d("searchFlights", "validNode: cal_1: " + cal_1.toString());
        //Log.d("searchFlights", "validNode: cal_2: " + cal_2.toString());
		
		/* convert calendar dates to milliseconds and subtract them, then reconveert to hours,
		 * if the difference is less than or equal to 6 hours then flights are valid nodes */
		return (Math.abs(cal_1.getTimeInMillis() - cal_2.getTimeInMillis())) /(3600000.00) <= 6;
	}
	
	/**
	 * this method gets all possible itineraries generated by search
	 * @return itineraries
	 */
	public ArrayList<ArrayList<FlightInfo>> getItineraries(){
		return this.itineraries;
	}
	
	/**
     * this method gets all possible itineraries generated by search
     * @return itineraries
     */
    public List<Itinerary> getRealItineraries(){
        
        return Search.convertToRealItineraries(this.itineraries);
    }
	
    /**
     * Converters a list of pseudo-itineraries to a list of real Itineraries
     * @param pseudoItineraries
     * @return A list of real Itineraries
     */
    public static List<Itinerary> convertToRealItineraries(
            ArrayList<ArrayList<FlightInfo>> pseudoItineraries){
        
        ArrayList<Itinerary> result = new ArrayList<Itinerary>();
        
        // Go through each pseudo-itinerary and convert to a real Itinerary
        for(List<FlightInfo> pseudoItin : pseudoItineraries) {
            result.add(new Itinerary(pseudoItin));
        }
        return result;
    }
    
	
	/**
     * this method gets all possible itineraries generated by search that
     * do not have more Flights than specified by maxFlights
     * @param maxFlights The upper limit of Flights in the itineraries
     * returned by this method
     * @return itineraries
     */
    public List<Itinerary> getRealItineraries(int maxFlights){
        
        // A new list of itineraries to return
        ArrayList<Itinerary> result = new ArrayList<>();
        
        // Add each itinerary to the result if it has less than maxFlights
        for(ArrayList<FlightInfo> pseudoItin: itineraries) {
            if(pseudoItin.size() <= maxFlights) {
                // Convert the pseudoItin to a real itinerary before adding it
                result.add(new Itinerary(pseudoItin));
            }
        }
        
        return result;
    }
    
    /**
     * this method gets all possible itineraries generated by search that
     * do not have more Flights than specified by maxFlights
     * @param maxFlights The upper limit of Flights in the itineraries
     * returned by this method
     * @return itineraries
     */
    public ArrayList<ArrayList<FlightInfo>> getItineraries(int maxFlights){
        
        // A new list of itineraries to return
        ArrayList<ArrayList<FlightInfo>> result = new ArrayList<>();
        
        // Add each itinerary to the result if it has less than maxFlights
        for(ArrayList<FlightInfo> itin: itineraries) {
            if(itin.size() <= maxFlights) {
                result.add((ArrayList<FlightInfo>)itin.clone());
            }
        }
        
        return result;
    }
	
	/**
	 * see return statement
	 * @return list of itineraries containing individual itinerary's from specified 
	 * global orgin and destination
	 * note: later on this may also return null if no itineraries are available
	 */
	public String toString(){
		return itineraries.toString();
	}
	
	/**
	 * @return a String in CSV format (as specified in the handout) that
     * contains a list of all itineraries found by this Search
	 */
	public String toCSV() {
	    return toCSV(getItineraries());
	    
	}
	
    /**
     * Returns String in CSV format (as specified in the handout) that
     * contains a list of itineraries found by this Search.
     * There is a constraint on the number of Flights in each itinerary
     * @param maxFlights The upper limit of Flights in the itineraries
     * @return a String in CSV format (as specified in the handout) that
     * contains a list of itineraries found by this Search
     */
	public String toCSV(int maxFlights) {
        return toCSV(getItineraries(maxFlights));
	    
	}
	
	/**
	 * Does all the work in generating a string in CSV format
	 * (as specified in the handout) that contains a list of itineraries
	 * @param itineraries The list of itineraries to generate the string from
	 * @return a String in CSV format
	 */
	public static String toCSV(ArrayList<ArrayList<FlightInfo>> itineraries) {
	    
	    // The string to return
	    String result = new String();
	    
	    // Convert each (pseudo)itinerary to CSV format
	    for(ArrayList<FlightInfo> pseudoItinerary: itineraries) {
	        result += Itinerary.toCSV(pseudoItinerary) + "\n";
	    }
	    
        return result;
	    
	}

    /**
     * Does all the work in generating a string in CSV format
     * (as specified in the handout) that contains a list of itineraries
     * @param itineraries The list of itineraries to generate the string from
     * @return a String in CSV format
     */
    public static String toCSV(List<Itinerary> itineraries) {
        
        // The string to return
        String result = new String();
        
        // Convert each itinerary to CSV format
        for(Itinerary itin: itineraries) {
            result += itin.toCSV() + "\n";
        }
        
        return result;
        
    }	
	
//	public ArrayList<ArrayList<Flight>> byCost(ArrayList<ArrayList<Flight>> itineraries){
//		ArrayList<ArrayList<Flight>> sorted = 
//				new ArrayList<ArrayList<Flight>>();
//		ArrayList<int> costs = new ArrayList<int>();
//		for(ArrayList<Flight> itinerary: itineraries){
//			Itinerary temp = new Itinerary(itinerary);
//			costs.(temp.getTotalCost());
//		}
//		costs = costs.sort();
//		
//		return sorted;
//	}
	
	
	/**
	 * @return A list of intineraries sorted in non-descending order
	 * by total time
	 */
	public List<Itinerary> sortByTime(){
		ArrayList<ArrayList<FlightInfo>> sorted = new ArrayList<>();
		
		// Convert a list of list of flights to
		// a list of Itineraries (instances of the actual class)
		ArrayList<Itinerary> realItineraries = new ArrayList<>();
		for(ArrayList<FlightInfo> itin: itineraries){
			realItineraries.add(new Itinerary(itin));
		}
		
		// Do the sort and return
		sortByTime(realItineraries);
		
		for(int i = 0; i < realItineraries.size(); i++){
			sorted.add(realItineraries.get(i).getFlights());
		}
		return convertToRealItineraries(sorted);
		
	}
	
	/**
	 * Mutate a list of itineraries so that it is sorted in non-descending order
	 * by total time. Implemented with quick sort
	 * @param itins A list of itineraries to sort
	 * @return 
	 */
	private void sortByTime(List<Itinerary> itins){
		
		// Base case: only one or zero element
		if(itins.size() < 1){
			return;
		}
		
		// Base case: only two elements
		if(itins.size() == 2){
			// If elements are not in order, swap them
			if(itins.get(0).getTime() > itins.get(1).getTime()){
				Itinerary temp = itins.get(1);
				itins.set(1, itins.get(0));
				itins.set(0, temp);
			}
			return;
		}
		
		// Pick a pivot
		int pivot = 0;
		// Index of the element to compare with pivot
		// Starts at the end of the array
		int index = itins.size() - 1;
		// Index moves to the front of the array each time (it decrements)
		int direction = -1;
		
		// Swap elements around so left side is lower than pivot, 
		// and right side is more than pivot
		while(index != pivot){
			// pivot is left of index and yet pivot is greater than index 
			if(pivot < index && 
					itins.get(pivot).getTime() > itins.get(index).getTime()){
				// Swap pivot and index (and their elements);
				Itinerary temp = itins.get(index);
				itins.set(index, itins.get(pivot));
				itins.set(pivot, temp);
				
				int tempInd = index;
				index = pivot;
				pivot = tempInd;
				
				// Change the direction
				direction *= -1;
			}
			// pivot is right of index and yet pivot is less than index 
			if(pivot > index &&
					itins.get(pivot).getTime() < itins.get(index).getTime()){
				// Swap pivot and index (and their elements);
				Itinerary temp = itins.get(index);
				itins.set(index, itins.get(pivot));
				itins.set(pivot, temp);
				
				int tempInd = index;
				index = pivot;
				pivot = tempInd;
				
				// Change the direction
				direction *= -1;
			}
			// increment/decrement index based on the current direction
			index += direction;
		}
		
		// sort the left side and right side of pivot
		sortByTime(itins.subList(0, pivot));
		sortByTime(itins.subList(pivot + 1, itins.size()));
	}
	
	public List<Itinerary> sortByCost() {
		ArrayList<Itinerary> unsorted = new ArrayList<Itinerary>();
		ArrayList<ArrayList<FlightInfo>> sorted = new ArrayList<ArrayList<FlightInfo>>();
		for (ArrayList<FlightInfo> itin: itineraries) {
			Itinerary temp = new Itinerary(itin);
			unsorted.add(temp);
		}

		for (int i = 0; i < unsorted.size(); i ++) {
			for(int j = 0; j < unsorted.size(); j++){
				if(unsorted.get(i).getTotalCost() < unsorted.get(j).getTotalCost()){
					Collections.swap(unsorted, i, j);
				}
			}

		}

		for(int i = 0; i < unsorted.size(); i++){
			sorted.add(unsorted.get(i).getFlights());
		}

		return convertToRealItineraries(sorted);
	}
}
