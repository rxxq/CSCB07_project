/**
 * 
 */
package backend;

import java.io.IOException;

/**
 * This is the class used by the frontend to access backend features.
 * @author Rex Xia
 *
 */
public abstract class BackendControlPanel {
    
    /* TODO define these paths
     * need to find out the proper place to put files in the Android file system
     */
    private static final String CLIENTDBPATH = null;

    private static final String AUTHDBPATH = null;

    private static final String FLIGHTDBPATH = null;

    /** Info for the current user **/
    private static ClientInfo currUser = null;
    
    /** Database of AuthorizationInfo **/
    private static AuthorizationDB dbAuth = new AuthorizationDB();
    /** Database of ClientInfo **/
    private static ClientDB dbClients = new ClientDB();
    /** Database of Flight **/
    private static FlightDB dbFlights = new FlightDB();
    
    /* TODO list:
     * [DONE]login/logout user
     * [DONE]inform frontend whether current user is admin
     * load/save data to files
     * edit client info (self and other)
     * edit flight info
     * add (book) an itinerary to a client
     * get booked itineraries
     */
    /*
     * TODO (design)
     * New CRC
     * Find out what kind of data format is needed for displaying stuff to the front end
     */
    
    /**
     * Loads all the data from file into the databases. This method must be run
     * when the app is started up.
     * @throws IOException if an error when loading up the data.
     * Should probably abort the app if this happens
     */
    public static void startUp() throws IOException {
        dbClients.load(CLIENTDBPATH);
        dbAuth.load(AUTHDBPATH);
        dbFlights.load(FLIGHTDBPATH);
    }
    
    /**
     * Saves all the data from the databases into csv files. This method must
     * be run before the app shuts down.
     */
    public static void shutDown() {
        //TODO
    }
    
    /**
     * Log in the user with the specified email (username) and password
     * @param email The email of the user to log in
     * @param password The password of the user to log in
     * @throws NoSuchUserException If the user cannot be found
     * @throws InvalidPasswordException If the password is incorrect
     */
    public static void login(String email, String password)
            throws NoSuchUserException, InvalidPasswordException {
        
        // Check if the email and password matches
        if(!dbAuth.checkPassword(email, password)) {
            throw new InvalidPasswordException(String.format(
                    "Invalid password for %s", email));
        }
        
        // If get to this point password was valid
        
        // Set current user
        currUser = dbClients.getClient(email);
    }
    /**
     * Log out the current logged in user. Does not save anything to file,
     * if trying to exit program, must call the appropriate methods to save
     * data first.
     */
    public static void logout(){
        // un-set current user
        currUser = null;
    }
    
    /**
     * Checks whether the current logged-in user is an admin
     * @return true if the current logged-in user is an admin
     */
    public static boolean isAdmin() {
        try {
            return dbAuth.isAdmin(currUser.getEmail());
        } catch (NoSuchUserException e) {
            /* This should never happen, since the current logged in user
             * must have been checked against dbAuth previously during login.
             */
            e.printStackTrace();
            return false;
        }
    }
    
}
