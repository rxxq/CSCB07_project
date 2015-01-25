package backend.testing;

import java.io.IOException;

import backend.BackendControlPanel;
import backend.exceptions.AlreadyExistingEntryException;
import backend.exceptions.InvalidPasswordException;
import backend.exceptions.NoSuchEntryException;

/**
 * Note: this test will no longer work since I Androidified the file loading
 * operations in BackendControlPanel.
 * @author Rex Xia
 *
 */
public class BackendCPTest {

/*
	public static void main(String[] args) {
	    
	    BackendControlPanel backendCP = BackendControlPanel.getInstance();
	    
		try {
            backendCP.addNewUser(
                    "John", "Smith", "john@smith.com", "address", "1235",
                    "expiryDate", true, "password");
        } catch (AlreadyExistingEntryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		try {
            backendCP.login("john@smith.com", "password");
        } catch (NoSuchEntryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidPasswordException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		// Should print true
		System.out.println(backendCP.isAdmin());
		
		backendCP.logout();
		
		// Test adding a second user
	      try {
	            backendCP.addNewUser(
	                    "Martha", "Jones", "martha@jones.com", "address", "1235",
	                    "expiryDate", false, "passwordMartha");
	        } catch (AlreadyExistingEntryException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        try {
	            backendCP.login("martha@jones.com", "passwordMartha");
	        } catch (NoSuchEntryException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (InvalidPasswordException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	        // Should print false
	        System.out.println(backendCP.isAdmin());
	        
	        backendCP.logout();

	}
*/
}
