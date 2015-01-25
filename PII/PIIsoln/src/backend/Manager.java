/**
 * 
 */
package backend;

import java.io.FileReader;
import java.io.BufferedReader;

/**
 * Responsibilities: 
 * <ul>
 *      <li>Read user data and use it to initialise Users</li>
 *      <li>Keep track of who's logged in</li>
 *      <li>Create a flight database based on information in a file</li>
 * </ul>
 * Collaborators:
 * <ul>
 *      <li>User (and its subclasses Client, Admin)</li>
 *      <li>FlightDB</li>
 * </ul>
 * @author group 0371
 *
 */
public class Manager {
    
    /** The current logged in user */
    private User currentUser = null;
    
    /**
     * Reads the file for one user based on the email address, determines if it
     * is a client or admin, create an instance of the appropriate User 
     * subclass (Client or Admin) based on information in the file, and set
     * this User as the current user.
     * Note: password-protection currently unimplemented.
     * @param email The email address of the user to log in
     * @param password The password of the user
     * @throws IOException Error in reading file, probably because the file is
     * not formatted correctly
     * (should eventually make our own custom exception for this)
     */
    public void loginUser(String email, String password) {
        
        // Generate the filename from the email address
        String filename = User.emailToFilename(email);
        
        // read the appropriate file
        BufferedReader reader = new BufferedReader(
                new FileReader(User.USERDIR + filename));
        
        // check password (not implemented yet, not required by handout)
        // for now, just read one line and ignore the result
        reader.readLine();
        
        // Find out the type of user and create the appropriate one
        String userType = reader.readLine();
        User newUser;
        switch (userType){
            case User.CLIENT:
                newUser = new Client(email, password, reader);
                break;
            case User.ADMIN:
                // ADMIN not implemented yet
                // newUser = new Admin(email, password, reader);
                break;
            default:
                // raise some sort of exception
        }
        
        reader.close();
        
        // If another user is currently logged in,
        // be sure to logout the other user first.
        if(currentUser != null) {
            logoutUser();
        }
        
        // Set the user to be the current user
        currentUser = newUser;        

    }
    
    /**
     * Saves the unsaved data of the current user and logout
     */
    public void logoutUser() {
        // TODO
    }
}
