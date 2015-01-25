/**
 * 
 */
package backend.managers;

import backend.exceptions.InvalidCSVFormatException;

/**
 * @author Trevor
 *
 */
public class FlightInfo implements DatabaseEntry<String>{
	//Create fields for all of the flight information
	private String flightNum;
	private String departureDateTime;
	private String arrivalDateTime;
	private String airline;
	private String origin;
	private String destination;
	private int cost;
	private int numSeats;
	
	//Index of each element in the CSV format
	private static int NUMBERINDEX = 0;
	private static int DEPARTINDEX = 1;
	private static int ARRIVEINDEX = 2;
	private static int AIRLINEINDEX = 3;
	private static int ORIGININDEX = 4;
	private static int DESTINDEX = 5;
	private static int PRICEINDEX = 6;
	private static int NSEATSINDEX = 7;

	/**
	 * Constructor for Flight Information
	 * @param flightNum
	 * @param departure
	 * @param arrival
	 * @param airline
	 * @param origin
	 * @param dest
	 * @param numSeats
	 */
	public FlightInfo(String flightNum, String departure, String arrival, String airline, 
			String origin, String dest, int cost, int numSeats){
		//set the field variables
		this.flightNum = flightNum;
		this.departureDateTime = departure;
		this.arrivalDateTime = arrival;
		this.airline = airline;
		this.origin = origin;
		this.destination = dest;
		this.cost = cost;
		this.numSeats = numSeats;
	}
	
	/**
	 * Create a new Flight with the same information as oldFlight
	 * @param oldFlight The Flight to copy all the data from
	 */
	public FlightInfo(FlightInfo oldFlight) {
        this.flightNum = oldFlight.getFlightNum();
        this.departureDateTime = oldFlight.getDepartureDateTime();
        this.arrivalDateTime = oldFlight.getArrivalDateTime();
        this.airline = oldFlight.getAirline();
        this.origin = oldFlight.getOrigin();
        this.destination = oldFlight.getDestination();
        this.cost = oldFlight.getCost();
        this.numSeats = oldFlight.getNumSeats();
	}
	
	/**
	 * Create a new Flight by parsing a CSV line in the same format specified
	 * in the handout.
	 * @param csvLine The CSV line to get the parameters from.
	 * @throws InvalidCSVFormatException if the CSV line could not be read
	 */
	public FlightInfo(String csvLine) throws InvalidCSVFormatException {
	    // Split the line into comma-delimited tokens
        // (the -1 argument keeps empty strings between commas)
        String [] tokens = csvLine.split(",", -1);
        
        // Create a new Flight with the required information
        FlightInfo newFlight;
        try {
            flightNum = tokens[NUMBERINDEX];
            departureDateTime = tokens[DEPARTINDEX];
            arrivalDateTime = tokens[ARRIVEINDEX];
            airline = tokens[AIRLINEINDEX];
            origin = tokens[ORIGININDEX];
            destination = tokens[DESTINDEX];
            cost = Integer.parseInt(tokens[PRICEINDEX]);
            numSeats = Integer.parseInt(tokens[NSEATSINDEX]);
        } catch (ArrayIndexOutOfBoundsException e) {
            // If the the indices are out of bounds the csv line is probably
            // not formatted correctly
            throw new InvalidCSVFormatException();
        }
	}
	
	/**
	 * get the flight Number
	 * @return flightNum
	 */
	public String getFlightNum() {
		return flightNum;
	}
	
	/**
	 * set the flight Number
	 * @param flightNum
	 */
	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}
	
	/**
	 * Get the departure date and time of the flight
	 * @return departureDateTime
	 */
	public String getDepartureDateTime() {
		return departureDateTime;
	}
	
	/**
     * Get the departure date only (exclude time of day)
     * @return The date of the day that the flight departs on
     */
    public String getDepartureDate() {
        // Split by space, return only the zeroth (front) part
        return departureDateTime.split(" ")[0];
    }
	
	/**
	 * Set the departure date and time of the flight
	 * @param departureDateTime
	 */
	public void setDepartureDateTime(String departureDateTime) {
		this.departureDateTime = departureDateTime;
	}
	
	/**
	 * Get the arrival date and time of the flight
	 * @return arrival DateTime
	 */
	public String getArrivalDateTime() {
		return arrivalDateTime;
	}
	
	/**
	 * Set the arrival date and time of the flight
	 * @param arrivalDateTime
	 */
	public void setArrivalDateTime(String arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}
	
	/**
	 * Get the airline of the flight
	 * @return airline
	 */
	public String getAirline() {
		return airline;
	}
	
	/**
	 * Set the airline of the flight
	 * @param airline
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}
	
	/**
	 * Get the origin of the flight
	 * @return origin
	 */
	public String getOrigin() {
		return this.origin;
	}
	
	/**
	 * Set the origin of the flight
	 * @param origin
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	/**
	 * Get the destination of the flight
	 * @return destination
	 */
	public String getDestination() {
		return this.destination;
	}
	
	/**
	 * Set the destination of the flight
	 * @param destination
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	/**
	 * Gets the cost of the flight
	 * @return the cost of the flight
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Set the value of cost
	 * @param cost
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}
	/**
     * @return the numSeats
     */
    public int getNumSeats() {
        return numSeats;
    }

    /**
     * @param numSeats the numSeats to set
     */
    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    /**
	 * Gets the total time of the flight
	 * @return flight time in form HH:MM
	 */
	public String getTime(){
		//gets the arrival and departure time of the flight
		String departTime = getDepartureDateTime();
		String arrivalTime = getArrivalDateTime();
		//get the part of the arrival time and departure time that is the hours and converts them to ints
		int totalHours, totalMins;
		int arrivalHours = Integer.parseInt(arrivalTime.substring(arrivalTime.length()-5, arrivalTime.length()-3));
		int departHours = Integer.parseInt(departTime.substring(departTime.length()-5, departTime.length()-3));
		//check if the flights crosses into the next day
		if(arrivalHours < departHours){
			totalHours = (24 - departHours) + arrivalHours;
		}
		else{
			totalHours = arrivalHours - departHours;
		}
		//get the part of the arrival time and departure time that is the hours and converts them to ints
		int arrivalMins = Integer.parseInt(arrivalTime.substring(arrivalTime.length()-2, arrivalTime.length()));
		int departMins = Integer.parseInt(departTime.substring(departTime.length()-2, departTime.length()));
		//check if the minutes go into a new hours
		if(arrivalMins < departMins){
			totalMins = (60 - departMins) + arrivalMins;
			totalHours -= 1;
		}
		else{
			totalMins = arrivalMins - departMins;
		}
		//format the hours and minutes so they are always 2 digits
		String hour = (totalHours < 10 ? "0" : "") + totalHours;
		String min = (totalMins < 10 ? "0" : "") + totalMins;
		return (hour + ":" + min);
	}
	
	/**
	 * Gets the hours and minutes of the departure time
	 * @return the departure time in format HH:MM
	 */
	public String getDepartTime(){
		//take the part of the string after the space
		return getDepartureDateTime().split(" ")[1];
	}
	
	/**
	 * Gets the date of the departure
	 * @return the departure time in format YYYY-MM-DD
	 */
	public String getDepartDate(){
		//take the part of the string before the space
		return getDepartureDateTime().split(" ")[0];
	}
	
	/**
	 * Gets the hours and minutes of the arrival time
	 * @return the arrival time in the form HH:MM
	 */
	public String getArrivalTime(){
		return getArrivalDateTime().split(" ")[1];
	}
	/**
	 * Gets the flight ID in the form airline + flight number
	 * @return flightID
	 */
	public String getFlightID(){
		return getAirline() + getFlightNum();
	}
	
	/**
	 * Return the flight ID (airline+flight number) as a unique key to put into a database
	 * @return the flight ID
	 */
	@Override
	public String getKey() {
		return getFlightID();
	}
	
	
	public String toString() {
	    return getFlightID();
	}
	
	
	/**
	 * Returns this Flight in csv format (same format as handout)
	 * @return The csv representation of this Flight (one line)
	 */
	public String toCSV(){
		return (flightNum + ","
		        + departureDateTime + ","
		        + arrivalDateTime +","
		        + airline + ","
		        + origin + ","
		        + destination + ","
		        + Integer.toString(cost));
	}
	/**
	 * method that checks if flights are valid connections ie. if other flight leaves
	 * within 6 hours of this flight
	 * @param other other flight to check 
	 * @return True iff other flight is a valid connection in an itinerary
	 */
	public boolean validNode(FlightInfo other){
		boolean isValid = false;
		String[] flight = this.arrivalDateTime.split("- :");
		String[] otherFlight = other.departureDateTime.split("- :");

		boolean sameYear = flight[0] == otherFlight[0];
		boolean sameMonth = flight[1] == otherFlight[1];

		double flightTime = (Double.parseDouble(flight[2])*24) + //day * 24 hr
				Double.parseDouble(flight[3]) +                  //hrs stay same
				(Double.parseDouble(flight[4])/60);				// mins div 60
		double otherFlightTime = (Double.parseDouble(otherFlight[2]) * 24 ) + //day
				Double.parseDouble(otherFlight[3]) + 				//hrs
				(Double.parseDouble(otherFlight[4])/60);					//mins
		boolean sixHourDiff = Math.abs(flightTime - otherFlightTime) <= 6; //6 hour diff by subtraction

		if(sameYear && sameMonth && sixHourDiff){
			isValid = true;
		}
		//still need to consider bordercases ie if flight on dec31 and other flight leaves jan 01 
		// or 31st of any month and 1st of another month
		return isValid; 
	}
	
	public String MasterDetailID() {
        return flightNum + ": " + origin + " → " + destination;
    }
    
    public String toMasterDetail() {
        return ("Flight number: "+flightNum + "\n"
                + "Depart date: "+ departureDateTime + "\n"
                + "Arrival date: "+ arrivalDateTime +"\n"
                + "Airline: "+ airline + "\n"
                + "Origin: "+ origin + "\n"
                + "Destination: "+ destination + "\n"
                +"(CAD) $"+ Integer.toString(cost));
    }
}
