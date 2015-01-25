/**
 * 
 */
package backend;

import java.io.IOException;

/**
 * A Database that stores AuthorizationInfo for users
 * @author Rex Xia
 *
 */
public class AuthorizationDB extends Database<String, AuthorizationInfo> {
    
    private final int EMAILINDEX = 0;
    private final int PASSWORDINDEX = 1;
    private final int ISADMININDEX = 2;

    /**
     * Create a new empty AuthorizationDB
     */
    public AuthorizationDB() {
        super();
    }
    
    /**
     * Creates a new AuthorizationDB by loading the data from the specified path
     * @param path The path of the csv file to load data from.
     * The file must be formatted like this: "email,password,isAdmin"
     * @throws IOException Error in reading the csv file
     */
    public AuthorizationDB(String path) throws IOException{
        super();
        load(path);
    }

    /* (non-Javadoc)
     * @see backend.Database#parseLine(java.lang.String)
     */
    @Override
    protected AuthorizationInfo parseLine(String line) {
        // Split the line into comma-delimited tokens
        String [] tokens = line.split(",");
        
        // Create a new AuthorizationInfo with the required information
        AuthorizationInfo newAuth = new AuthorizationInfo(
                tokens[EMAILINDEX],
                tokens[PASSWORDINDEX],
                /* if the token contains the string "true" then it's 
                 * boolean value is true, else it is false */
                tokens[ISADMININDEX].equals("true")?true:false
                        );
        
        // Return the new AuthorizationInfo
        return newAuth;

    }
    
    /**
     * Checks if the user identified by a particular email has admin privileges
     * @param email The email of the user to identify
     * @return true if the user is an admin
     * @throws NoSuchUserException if the user does not exist in this
     * AuthorizationDB
     */
    public boolean isAdmin(String email) throws NoSuchUserException {
        // Check to make sure that the user can be found
        if(!dataTable.containsKey(email)) {
            throw new NoSuchUserException(String.format("The user with email:"
                    + " %s could not be found"
                    + " in the authorization database", email));
        }
        
        // Return a copy
        return dataTable.get(email).isAdmin();
    }
    
    /**
     * Check the password of a user and return true if the password provided
     * matches the password stored in this AuthorizationDB for the user
     * @param email Email of the user to check password for
     * @param password Password that the user is attempting to use to login
     * @return true if the provided password is correct
     * @throws NoSuchUserException if the user does not exist in this
     * AuthorizationDB
     */
    public boolean checkPassword(String email, String password)
            throws NoSuchUserException {
        // Check to make sure that the user can be found
        if(!dataTable.containsKey(email)) {
            throw new NoSuchUserException(String.format("The user with email:"
                    + " %s could not be found"
                    + " in the authorization database", email));
        }
        
        // Compare the password provided with the correct password
        // Return true if it's the same.
        return dataTable.get(email).getPassword().equals(password);
    }
}
