/**
 * 
 */
package backend.exceptions;

/**
 * This is the exception thrown when a non-admin tries to do something that
 * requires admin permission
 * @author Rex Xia
 *
 */
public class NoPermissionException extends Exception {

    /**
     * 
     */
    public NoPermissionException() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @param detailMessage
     */
    private NoPermissionException(String detailMessage) {
        super(detailMessage);
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Formats the detail message like this:
     * "[email] does not have permission to access [method]. Admin privileges
     * are required"
     * @param email The email of the user that tried to access the method
     * @param method the method accessed
     */
    public NoPermissionException(String email, String method) {
        this(String.format("%s does not have permission to access %s. " +
        		"Admin privileges are required", email, method));
    }

    /**
     * @param throwable
     */
    public NoPermissionException(Throwable throwable) {
        super(throwable);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param detailMessage
     * @param throwable
     */
    public NoPermissionException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        // TODO Auto-generated constructor stub
    }

}
