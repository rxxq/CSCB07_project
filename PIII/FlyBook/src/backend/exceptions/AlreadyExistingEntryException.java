/**
 * 
 */
package backend.exceptions;

/**
 * @author xiaxiuqi
 *
 */
public class AlreadyExistingEntryException extends Exception {

	/**
	 * 
	 */
	public AlreadyExistingEntryException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param detailMessage
	 */
	public AlreadyExistingEntryException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param throwable
	 */
	public AlreadyExistingEntryException(Throwable throwable) {
		super(throwable);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param detailMessage
	 * @param throwable
	 */
	public AlreadyExistingEntryException(String detailMessage,
			Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

}
