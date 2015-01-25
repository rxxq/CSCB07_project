/**
 * 
 */
package backend.managers;

import java.util.Collection;
import backend.exceptions.AlreadyExistingEntryException;
import backend.exceptions.InvalidCSVFormatException;
import backend.exceptions.InvalidPasswordException;
import backend.exceptions.NoSuchEntryException;

/**
 * A Manager that stores AuthorizationInfo for users.
 * This class is a singleton.
 * @author Rex Xia
 *
 */
public class AuthorizationManager extends Manager<String, AuthorizationInfo> {
    
    /**
     * The actual singleton instance. We create this as soon as the app
     * starts up because we will need this for sure, so we might as well
     * create it.
     */
    private static final AuthorizationManager instance = new AuthorizationManager();

    private static final String DEFAULTPASS = "password";
    private final int EMAILINDEX = 0;
    private final int PASSWORDINDEX = 1;
    private final int ISADMININDEX = 2;
    /** The name of this database for exception messages **/
    private final String DBNAME = "AuthorizationDB";
    
    /**
     * Retrieve the one and only instance of AuthorizationDB
     * @return The only instance of AuthorizationDB
     */
    public static AuthorizationManager getInstance() {
        return instance;
    }

    /**
     * Create a new empty AuthorizationDB
     */
    private AuthorizationManager() {
        super();

    }
    
//    /**
//     * Creates a new AuthorizationDB by loading the data from the specified path
//     * @param path The path of the csv file to load data from.
//     * The file must be formatted like this: "email,password,isAdmin"
//     * @throws IOException Error in reading the csv file
//     */
//    private AuthorizationDB(String path) throws IOException{
//        // First make an empty instance and set it to be the only instance
//        this();
//        // Load data into it
//        load(path);
//    }

    /* (non-Javadoc)
     * @see backend.Database#parseLine(java.lang.String)
     */
    @Override
    public AuthorizationInfo parseLine(String line)
            throws InvalidCSVFormatException {
        // Split the line into comma-delimited tokens
        // (the -1 argument keeps empty strings between commas)
        String [] tokens = line.split(",", -1);
        
        // Create a new AuthorizationInfo with the required information
        AuthorizationInfo newAuth;
        try {
            newAuth = new AuthorizationInfo(
                    tokens[EMAILINDEX],
                    tokens[PASSWORDINDEX],
                    /* if the token contains the string "true" then it's 
                     * boolean value is true, else it is false */
                    tokens[ISADMININDEX].equals("true")?true:false
                            );
        } catch (ArrayIndexOutOfBoundsException e) {
            // If the the indices are out of bounds the csv line is probably
            // not formatted correctly
            throw new InvalidCSVFormatException(DBNAME);
        }
        
        // Return the new AuthorizationInfo
        return newAuth;

    }
    
    /**
     * Checks if the user identified by a particular email has admin privileges
     * @param email The email of the user to identify
     * @return true if the user is an admin
     * @throws NoSuchEntryException if the user does not exist in this
     * AuthorizationDB
     */
    public boolean isAdmin(String email) throws NoSuchEntryException {      
        return getEntry(email).isAdmin();
    }
    
    /**
     * Check the password of a user and return true if the password provided
     * matches the password stored in this AuthorizationDB for the user
     * @param email Email of the user to check password for
     * @param password Password that the user is attempting to use to login
     * @return true if the provided password is correct
     * @throws NoSuchEntryException if the user does not exist in this
     * AuthorizationDB
     */
    public boolean checkPassword(String email, String password)
            throws NoSuchEntryException {
        
        // Get this user's AuthorizationInfo
        AuthorizationInfo auth = getEntry(email);
        
        // Compare the password provided with the correct password
        // Return true if it's the same.
        return auth.getPassword().equals(password);
    }
    
    /**
     * Add a new user to the authorization database
     * @param email
     * @param password
     * @param isAdmin
     * @throws AlreadyExistingEntryException If another user with the same
     * email already exists
     */
    public void addNewUser(String email, String password, boolean isAdmin)
    		throws AlreadyExistingEntryException{
        
		// Make a new AuthorizationInfo
        AuthorizationInfo newAuth = new AuthorizationInfo(
                email, password, isAdmin);
        
        // Add it to the database
        addNewEntry(newAuth.getKey(), newAuth);
    	
    }
    
    /**
     * Change the password of an existing user
     * @param email
     * @param oldPassword
     * @param newPassword
     * @throws NoSuchEntryException if no user with that email exists
     * @throws InvalidPasswordException if the old password does not match
     */
    public void changePassword(String email, String oldPassword, String newPassword)
    		throws NoSuchEntryException, InvalidPasswordException{
    	// check if the old password is valid
    	if(checkPassword(email, oldPassword)){
    		AuthorizationInfo temp = new AuthorizationInfo(email, newPassword, isAdmin(email));
    		setEntry(email, temp);
    	}
    	else{
    		// throw an exception and don't change the old password to the new one
    		throw new InvalidPasswordException("Passwords do not match");
    	}
    }
    
    /**
     * Creates a default authorizationInfo entry for any clients that don't
     * have an authorizationInfo entry yet
     * @param clientEmails A Collection of emails of clients to make 
     * default entries for
     */
    public void generateDefaultPasswords(Collection<String> clientEmails) {
        for(String email: clientEmails) {
            try {
                addNewUser(email, DEFAULTPASS, false);
            }
            catch(AlreadyExistingEntryException e) {
                // Do nothing if an entry already exists
            }
        }
    }
}
