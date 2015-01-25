/**
 * 
 */
package backend.exceptions;

/**
 * This is the Exception thrown when a CSV file does not have the correct
 * format for reading
 * @author Rex Xia
 *
 */
public class InvalidCSVFormatException extends Exception {

    /**
     * 
     */
    public InvalidCSVFormatException() {
        // TODO Auto-generated constructor stub
    }

//    /**
//     * @param detailMessage
//     */
//    private InvalidCSVFormatException(String detailMessage) {
//        super(detailMessage);
//        // TODO Auto-generated constructor stub
//    }
    
    /**
     * Set the detail message of this Exception according to this format:
     * "The csv file [csvFileName] is not correctly formatted
     * for [databaseName]"
     * @param csvFileName The name of the file causing the error
     * @param databaseName The name of the database that was trying to
     * read the file
     */
    public InvalidCSVFormatException(String databaseName) {
        super(String.format("A line in a CSV file is not correctly " +
        		"formatted for %s!", databaseName));
    }

    /**
     * @param throwable
     */
    public InvalidCSVFormatException(Throwable throwable) {
        super(throwable);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param detailMessage
     * @param throwable
     */
    public InvalidCSVFormatException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        // TODO Auto-generated constructor stub
    }

}
