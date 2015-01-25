package backend;

/**
 * @author Brandon, Rex
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client extends User implements Saveable {
    
    private final int LNAMEINDEX = 0;
    private final int FNAMEINDEX = 1;
    private final int EMAILINDEX = 2;
    private final int ADDRESSINDEX = 3;
    private final int CREDCARDINDEX = 4;
    private final int EXPDATEINDEX = 5;
	
	private ArrayList<Itinerary> bookedItineraries = new ArrayList<Itinerary>();
	private ArrayList<Search> recentSearches = new ArrayList<Search>();
	private String firstName;
	private String lastName;
	private String address;
	private String creditCardNumber;
	private String expiryDate;
	
	// TODO another constructor that can load from file
	
	public Client(String firstName, String lastName, String email, String address, String creditCardNumber, String expiryDate) {
	    this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.creditCardNumber = creditCardNumber;
		this.expiryDate = expiryDate;
	}
	
	/**
	 * This method stores the clients 5 most recent searches. A neat little
	 * feature.
	 * 
	 * @param flights
	 * @param origin
	 * @param dest
	 */
	public void newSearch(ArrayList<Flight> flights, String date, String origin, String dest) {
		// If there are 5 recent searches remove the earliest search and then
		// append the current search.
		if (recentSearches.size() == 5) {
			recentSearches.remove(0);
			recentSearches.add(new Search(flights, date, origin, dest));
		}
		else {
			recentSearches.add(new Search(flights, date, origin, dest));
		}
	}
	
	/**
	 * Adds an itinerary to a clients list of booked itineraries 
	 * 
	 * @param itin an itinerary
	 */
	public void book(Itinerary itin) {
		bookedItineraries.add(itin);
	}
	
	/**
	 * Returns all of the clients booked itineraries
	 * 
	 * @return bookedItineraries a list of a clients booked itineraries
	 */
	public ArrayList<Itinerary> viewBooked() {
		return bookedItineraries;
	}
	
	/**
	 * Loads client-specific data from a user data text file.
	 * Assumes that reading starts at line 3 of the file (where client-specific
	 * data starts)
	 * @param reader A BufferedReader for the file to load from
	 */
	public void load(BufferedReader reader) {
		/* Read line 3 from file.
		 * This should contain a comma-separated list of:
		 * LastName,FirstNames,Email,Address,CreditCardNumber,ExpiryDate
		 */
		String line = reader.readLine();
		
		// Split into tokens delimited by tokens
		List<String> tokens = Arrays.asList(line.split(","));
		
		// Initialize fields with the appropriate values
		lastName = tokens.get(LNAMEINDEX);
		firstName = tokens.get(FNAMEINDEX);
		email = tokens.get(EMAILINDEX);
		address = tokens.get(ADDRESSINDEX);
		creditCardNumber = tokens.get(CREDCARDINDEX);
		expiryDate = tokens.get(EXPDATEINDEX);
		
		// TODO load booked flights
	}

	@Override
	public void save(BufferedWriter writer) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Gets the address of the client
	 * 
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Gets the credit Card Number of the client
	 * 
	 * @return creditCardNumber
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	
	/**
	 * Gets the email of the client
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Gets the expiry date of the clients credit card
	 * 
	 * @return expiryDate
	 */
	public String getExpiryDate() {
		return expiryDate;
	}
	
	/**
	 * Gets the first name of the client
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Gets the last name of the client
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Sets a new address for the client.
	 * 
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Sets a new credit card number for the client.
	 * 
	 * @param creditCardNumber
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	
	/**
	 * Sets a new email for the client.
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Sets a new credit card expiry date for the client.
	 * 
	 * @param expiryDate
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	/**
	 * Sets a new first name for the client.
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Sets a new last name for the client.
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	

}
