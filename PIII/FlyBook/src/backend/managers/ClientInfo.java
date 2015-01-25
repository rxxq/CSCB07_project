/**
 * 
 */
package backend.managers;

/**
 * @author Brandon
 *
 */
public class ClientInfo implements DatabaseEntry<String> {

	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String creditCardNumber;
	private String expiryDate;
	
	/**
	 * Constructor for Personal Information of the client.
	 * 
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param address
	 * @param creditCardNumber
	 * @param expiryDate
	 */
	public ClientInfo(String firstName, String lastName, String email, String address, String creditCardNumber, String expiryDate) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.creditCardNumber = creditCardNumber;
		this.expiryDate = expiryDate;
	}
	
	/**
	 * Make a new ClientInfo with the same information as oldClient
	 * @param oldClient The ClientInfo to make a copy of
	 */
	public ClientInfo(ClientInfo oldClient){
		this.firstName = oldClient.getFirstName();
		this.lastName = oldClient.getLastName();
		this.email = oldClient.getEmail();
		this.address = oldClient.getAddress();
		this.creditCardNumber = oldClient.getCreditCardNumber();
		this.expiryDate = oldClient.getExpiryDate();
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

	/**
	 * Returns the email of this ClientInfo as a unique key
	 * @return the email of this ClientInfo
	 */
	@Override
	public String getKey() {
		return getEmail();
	}

    /**
     * Returns this ClientInfo in csv format (same format as handout)
     * @return The string representation of this ClientInfo (csv format)
     */
    public String toCSV() {
        return (lastName + ","
                + firstName + ","
                + email + ","
                + address + ","
                + creditCardNumber + ","
                + expiryDate);
    }
	
}
