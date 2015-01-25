package backend.managers;

import java.io.IOException;
import java.util.Collection;

// NOTE: Automarker did not compile with this import
//import android.view.animation.BounceInterpolator;
import backend.exceptions.AlreadyExistingEntryException;
import backend.exceptions.InvalidCSVFormatException;
import backend.exceptions.NoSuchEntryException;

/**
 * 
 */

/**
 * A client manager. The key is the user's email, the values are ClientInfo.
 * Stores all the ClientInfo for all the Clients.
 * Can upload a csv file to load info.
 * Can output a csv file to save info.
 * This class is a singleton.
 * @author group
 *
 */
public class ClientManager extends Manager<String, ClientInfo> {
	
    /**
     * The actual singleton instance. We create this as soon as the app
     * starts up because we will need this for sure, so we might as well
     * create it.
     */
    private static final ClientManager instance = new ClientManager();
    
    private final int LNAMEINDEX = 0;
    private final int FNAMEINDEX = 1;
    private final int EMAILINDEX = 2;
    private final int ADDRESSINDEX = 3;
    private final int CREDCARDINDEX = 4;
    private final int EXPDATEINDEX = 5;
    /** The name of this database for exception messages **/
    private final String DBNAME = "ClientDB";
    
    /**
     * Retrieve the one and only instance of ClientDB
     * @return The only instance of ClientDB
     */
    public static ClientManager getInstance() {
        return instance;
    }

    /**
     * Creates a new empty ClientDB
     */
    private ClientManager(){
    	super();
    }
    
//	/**
//	 * Creates a new ClientDB by loading the data from the specified path
//	 * @param path The path of the csv file to load data from.
//	 * The file must be formatted according to the specifications in the
//	 * Phase 2 handout.
//	 * @throws IOException Error in reading the csv file
//	 */
//	private ClientDB(String path) throws IOException{
//        // First make an empty instance and set it to be the only instance
//        this();
//        // Load data into it
//        load(path);
//	}
	
	/**
	 * Returns a ClientInfo given it's email
	 * @param email The email of the ClientInfo to return
	 * @return The ClientInfo requested (a copy of the version in the manager)
	 * @throws NoSuchEntryException if the user does not exist in this ClientDB
	 */
	public ClientInfo getClient(String email) throws NoSuchEntryException{
		
	    // Get the ClientInfo and return a copy of it.
		return new ClientInfo(getEntry(email));

	}
	
	/**
	 * Adds a new client to the database
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param address
	 * @param creditCardNumber
	 * @param expiryDate
	 * @throws AlreadyExistingEntryException If another user already exists
	 * with the same email.
	 */
	public void addNewClient(String firstName, String lastName, String email,
			String address, String creditCardNumber, String expiryDate)
					throws AlreadyExistingEntryException{
		
	    // Create a new client info for the new user and add it to the Database
	    ClientInfo newClient = new ClientInfo(firstName, lastName, email, 
	            address, creditCardNumber, expiryDate);
	    addNewEntry(newClient.getKey(), newClient);
	}
	
	/**
	 * Update an already existing client
	 * @param key
	 * @param client
	 * @throws NoSuchEntryException if no user with that key exists
	 */
	public void setClient(String key, ClientInfo client) throws NoSuchEntryException{
		setEntry(key, new ClientInfo(client));
	}
	
	/* (non-Javadoc)
	 * @see backend.Database#parseLine(java.lang.String)
	 */
	@Override
    public ClientInfo parseLine(String line)
	        throws InvalidCSVFormatException {
		
		// Split the line into comma-delimited tokens
        // (the -1 argument keeps empty strings between commas)
		String [] tokens = line.split(",",-1);
		
		// Create a new ClientInfo with the required information
		ClientInfo newClient;
        try {
            newClient = new ClientInfo(
            		tokens[FNAMEINDEX],
            		tokens[LNAMEINDEX],
            		tokens[EMAILINDEX],
            		tokens[ADDRESSINDEX],
            		tokens[CREDCARDINDEX],
            		tokens[EXPDATEINDEX]);
        } catch (ArrayIndexOutOfBoundsException e) {
            // If the the indices are out of bounds the csv line is probably
            // not formatted correctly
            throw new InvalidCSVFormatException(DBNAME);
        }
		
		// Return the new ClientInfo
		return newClient;
	}
	
	/**
     * Creates a default clientInfo entry for any clients that don't
     * have an authorizationInfo entry yet
     * @param clientEmails A Collection of emails of clients to make 
     * default entries for
	 */
	public void generateDefaultClientInfos(Collection <String> emails) {
        for(String email: emails) {
            try {
                // Add a new client with an email and blank info
                addNewClient("", "", email, "", "", "");
            }
            catch(AlreadyExistingEntryException e) {
                // Do nothing if an entry already exists
            }
        }
	}


}
