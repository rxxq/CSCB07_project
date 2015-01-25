package backend;

import java.io.IOException;

/**
 * 
 */

/**
 * A Client Database. The key is the user's email, the values are ClientInfo.
 * Stores all the ClientInfo for all the Clients
 * Can upload a csv file to load info
 * Can output a csv file to save info
 * @author group
 *
 */
public class ClientDB extends Database<String, ClientInfo> {
	
    private final int LNAMEINDEX = 0;
    private final int FNAMEINDEX = 1;
    private final int EMAILINDEX = 2;
    private final int ADDRESSINDEX = 3;
    private final int CREDCARDINDEX = 4;
    private final int EXPDATEINDEX = 5;
    
    /**
     * Creates a new empty ClientDB
     */
    public ClientDB(){
    	super();
    }
    
	/**
	 * Creates a new ClientDB by loading the data from the specified path
	 * @param path The path of the csv file to load data from.
	 * The file must be formatted according to the specifications in the
	 * Phase 2 handout.
	 * @throws IOException Error in reading the csv file
	 */
	public ClientDB(String path) throws IOException{
	    super();
		load(path);
	}
	
	/**
	 * Returns a ClientInfo given it's email
	 * @param email The email of the ClientInfo to return
	 * @return The ClientInfo requested
	 * @throws NoSuchUserException if the user does not exist in this ClientDB
	 */
	public ClientInfo getClient(String email) throws NoSuchUserException{
		
	    // Check to make sure that the client can be found
	    if(!dataTable.containsKey(email)) {
            throw new NoSuchUserException(String.format("The user with email:"
                    + " %s could not be found"
                    + " in the client database", email));
	    }
	    
	    // Return a copy
		return new ClientInfo(dataTable.get(email));

	}

	/* (non-Javadoc)
	 * @see backend.Database#parseLine(java.lang.String)
	 */
	@Override
	protected ClientInfo parseLine(String line) {
		
		// Split the line into comma-delimited tokens
		String [] tokens = line.split(",");
		
		// Create a new ClientInfo with the required information
		ClientInfo newClient = new ClientInfo(
				tokens[FNAMEINDEX],
				tokens[LNAMEINDEX],
				tokens[EMAILINDEX],
				tokens[ADDRESSINDEX],
				tokens[CREDCARDINDEX],
				tokens[EXPDATEINDEX]);
		
		// Return the new ClientInfo
		return newClient;
	}


}
