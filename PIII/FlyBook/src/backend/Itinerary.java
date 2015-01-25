package backend;

import java.util.ArrayList;
import java.util.List;

import backend.managers.FlightInfo;

public class Itinerary {
	//initialize the field variables
	private String totalTime = "00:00";
	private int time;
	private String origin;
	private String destination;
	private int totalCost = 0;
	private ArrayList<FlightInfo> flights = new ArrayList<FlightInfo>();
	
	/**
	 * Creates a new empty itinerary
	 */
	public Itinerary(){
		
	}
	
	/**
	 * Convert an existing pseudo-itinerary to a new real Itinerary
	 * @param pseudoItin
	 */
	public Itinerary(List<FlightInfo> pseudoItin){
		
		for(FlightInfo flight: pseudoItin){
				appendFlight(flight);
		}
		
	}
	
	/**
	 * Adds the flight to the end of the itinerary and updates the variables about the itinerary
	 * @param flight
	 */
	public void appendFlight(FlightInfo flight){
		//check if it is the first flight in the itinerary
		if(flights.size() == 0){
			this.origin = flight.getOrigin();
			totalTime = flight.getTime();
		}
		else{
			totalTimeWithLayover(flight);
		}
		//change the destination to the destination of the last flight in the itinerary
		this.destination = flight.getDestination();
		this.totalCost += flight.getCost();
		generateTime();
		flights.add(flight);
		
	}
	
	/**
	 * Calculate the total time of the itinerary and updates the field variable
	 * @param flight
	 */
	public void totalTimeWithLayover(FlightInfo flight){
		//get the total flight time of the itinerary and the time of the flight being added
		String flight1 = flight.getTime();
		String total = totalTime;
		String layover = LayoverTime(flight);
		//add up all the hours of the previous flight and flight being added
		int hours = Integer.parseInt(flight1.split(":")[0]);
		hours += Integer.parseInt(total.split(":")[0]);
		hours += Integer.parseInt(layover.split(":")[0]);
		//add up all the minutes of the previous flight and flight being added
		int mins = Integer.parseInt(flight1.split(":")[1]);
		mins += Integer.parseInt(total.split(":")[1]);
		mins += Integer.parseInt(layover.split(":")[1]);
		//convert minutes to hours and update the minutes and hours
		if(mins > 60){
			hours += Math.floor(mins/60);
			mins = mins%60;
		}
		//make the hours and minutes 2 digits
		String totalHours = (hours < 10 ? "0" : "") + hours;
		String totalMins = (mins < 10 ? "0" : "") + mins;
		this.totalTime = totalHours + ":" + totalMins;
	}
	
	/**
	 * Returns the integer of the total itinerary flight time in minutes
	 * @return time
	 */
	public int getTime(){
		return this.time;
	}
	
	/**
	 * Convert the string of the total time to the total time in minutes
	 */
	public void generateTime(){
		int hours = Integer.parseInt(this.totalTime.split(":")[0]);
		int mins = Integer.parseInt(this.totalTime.split(":")[1]);
		this.time = ((hours * 60) + mins);
	}
	
	/**
	 * Calculate the layover time of the newest flight
	 * @param flight
	 * @return layover time
	 */
	public String LayoverTime(FlightInfo flight){
		//get the hour time of the arrival time of the last flight of the itinerary and the departure time of the new flight
		int hours;
		int hours1 = Integer.parseInt(this.flights.get(flights.size() - 1).getArrivalTime().split(":")[0]);
		int hours2 = Integer.parseInt(flight.getDepartTime().split(":")[0]);
		//check if the layover is into a new day
		if(hours2 < hours1){
			hours = (24 - hours1) + hours2;
		}
		else{
			hours = hours2 - hours1;
		}
		//get the minute time of the arrival time of the last flight of the itinerary and the departure time of the new flight
		int mins;
		int mins1 = Integer.parseInt(this.flights.get(flights.size() - 1).getArrivalTime().split(":")[1]);
		int mins2 = Integer.parseInt(flight.getDepartTime().split(":")[1]);
		//check if the layover is into the next hour
		if(mins2 < mins1){
			mins = (60 - mins1) + mins2;
			hours -= 1;
		}
		else{
			mins = mins2 - mins1;
		}
		//make the hours and minutes 2 digits
		String hour = (hours < 10 ? "0" : "") + hours;
		String min = (mins < 10 ? "0" : "") + mins;
		return (hour + ":" + min);
	}
	
	/**
	 * Gets the arraylist of the flights in order
	 * @return the arraylist of the flights
	 */
	public ArrayList<FlightInfo> getFlights() {
		return flights;
	}

	/**
	 * Gets the origin of the itinerary
	 * @return origin location
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Gets the destination of the itinerary
	 * @return destination location
	 */
	public String getDestination() {
		return this.destination;
	}
	
	/**
	 * Gets the total cost of the itinerary
	 * @return cost
	 */
	public int getTotalCost(){
		return this.totalCost;
	}

	/**
	 * Makes a string representation of the itinerary using the flight ids
	 * @return allFlights
	 */
	public String toString(){
		String allFlights;
		//check if the itinerary is not empty
		if(flights.size() > 0){
			allFlights = flights.get(0).toString();
			for(int i = 1; i < flights.size(); i++)
			allFlights += " then you get on " + flights.get(i).toString();
		}
		else{
			allFlights = "No flights";
		}
		return allFlights;
	}
	
	/**
     * Returns a string representation of this Itinerary in the
     * format specified on the handout, including the total cost and total time
     * @return the string representation
     */
	public String toCSV() {
	    return toCSV(true);
	}
	
	/**
	 * Returns a string representation of this Itinerary in the
	 * format specified on the handout,
	 * optionally including the total cost and total time
	 * @param getCostTime If false, the string returned will not include
	 * the total cost and total duration
	 * @return the string representation
	 */
	public String toCSV(boolean getCostTime) {
	    
	    // Build a string with one Flight on each line
	    String result = new String();
	    
	    for(FlightInfo f: flights) {
	        result += f.toCSV();
	        result += "\n";
	    }
	    
	    if(getCostTime) {
    	    // Total price on its own line
    	    result += Integer.toString(getTotalCost()) + "\n";
    	    
    	    // Total duration on its own line (format HH:MM)
    	    result += totalTime + "\n";
	    }
	    
	    return result;
	}
	
	/**
	 * Temporary method to convert an ArrayList of Flights to a string in the
	 * csv format specified in the assignment handout.
	 * <b>We need to eventually convert all ArrayLists of Flights in the code
	 * to be actual Itineraries.</b>
	 * @param pseudoItinerary An ArrayList of Flights that represents an
	 * itinerary but is not actually an instance of Itinerary
	 * @return the string representation
	 */
	public static String toCSV(ArrayList<FlightInfo> pseudoItinerary) {

	    // Create an Itinerary instance using the Flights in pseudoItinerary
	    Itinerary realItinerary = new Itinerary();
	    for(FlightInfo f: pseudoItinerary) {
	        realItinerary.appendFlight(f);
	    }
	    
	    // Use the toCSV of the real Itinerary
	    return realItinerary.toCSV();
	}
	
	/**
	 * used to display itinerary detail in First Pane in Shopping Cart & Search Itineraries
	 * @return formatted string "origin → destination
	 * 							#ofConnections connection(s)"
	 */
	public String MasterDetailID() {
        return origin + " → " + destination +"\n(" + (flights.size() -1) + " Connection(s))" ;
    }
	
	/**
	 * used to display itinerary details in Shopping Cart & Search Itinerary Layouts
	 * @return formatted string like in FlightInfo + ↓ after each flight in itinerary + totalCost
	 */
	public String MasterDetail(){
		String res = "";
		for(FlightInfo flight: flights){
			res += "Flight number: "+ flight.getFlightNum() + "\n"
	                + "Depart date: "+ flight.getDepartureDateTime() + "\n"
	                + "Arrival date: "+ flight.getArrivalDateTime() +"\n"
	                + "Airline: "+ flight.getAirline() + "\n"
	                + "Origin: "+ flight.getOrigin() + "\n"
	                + "Destination: "+ destination + "\n"
	                + "(CAD) $"+ Integer.toString(flight.getCost())+ "\n";
	                
			if(flight.getFlightNum() != (flights.get(flights.size()-1).getFlightNum())){
				res += "\t\t\t↓";
			}
			res+= "\n";
			}
		
		res += "Total Cost: $"+ getTotalCost();
		return res;
	}
   
}
