/**
 * 
 */
package backend.managers;

/**
 * Stores a user's password and their permission level
 * (ie Whether the user is an admin)
 * @author Rex Xia
 *
 */
public class AuthorizationInfo implements DatabaseEntry<String> {
    
    /** The user's email **/
    private String email;
    /** The user's password **/
    private String password;
    /** true if the user is an admin **/
    private boolean isAdmin;
    
    /**
     * Creates a new AuthorizationInfo DatabaseEntry
     * @param email The email of the user that this AuthorizationInfo describes
     * @param password The password the user that this AuthorizationInfo
     * describes
     * @param isAdmin Whether the user that this AuthorizationInfo describes
     * has admin privileges
     */
    public AuthorizationInfo(String email, String password, 
            boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    /**
     * @return The email of the user that this AuthorizationInfo describes,
     * as an unique identifier for this AuthorizationInfo
     */
    @Override
    public String getKey() {
        // TODO Auto-generated method stub
        return email;
    }

    /**
     * Convert this AuthorizationInfo into a string in CSV format.
     * @return A string in this format: "email,password,isAdmin"
     */
    @Override
    public String toCSV() {
        // TODO Auto-generated method stub
        return String.format("%s,%s,%s", email, password, isAdmin);
    }

    /**
     * @return true if the user that this AuthorizationInfo describes
     * has admin privileges
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * @return the password of the user that this AuthorizationInfo describes
     */
    public String getPassword() {
        return password;
    }

}
