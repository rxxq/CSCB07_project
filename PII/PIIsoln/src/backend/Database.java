/**
 * 
 */
package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * A Database (not an actual SQL database)
 * that can read data from a csv file and load it into memory.
 * @author xiaxiuqi
 *
 */
public abstract class Database<K, V extends DatabaseEntry<K>> {
	protected HashMap<K, V> dataTable;
	
	/**
	 * Create a new empty Database.
	 */
	public Database(){
		dataTable = new HashMap<K,V>();
	}
	
	/**
	 * Reads data from a csv file and loads it into this Database
	 * @param path The Path of the csv file
	 * @throws IOException Error in file operations
	 */
	public void load(String path) throws IOException{
		
	    // Open the file
	    BufferedReader reader = new BufferedReader(new FileReader(path));
	    
		// Read one line at a time
	    for(String line = reader.readLine();
	            line != null; line = reader.readLine()) {
	        
	        // Call parseLine on the line to get a V object
	        // (eg: ClientInfo or Flight)
	        V value = parseLine(line);
	        		
    		// Call getKey on the value
    		// Put the key, value into the dataTable
	        dataTable.put(value.getKey(), value);
	    }

	    // Close the file
	    reader.close();
	}
	
	/**
	 * Parses a line from the csv file and return a value
	 * (eg ClientInfo or a FlightInfo or a AuthorizationInfo)
	 * @param line A line from the csv file
	 * @return A value to be added to the Database
	 */
	protected abstract V parseLine(String line);
	
	/**
	 * Writes data from this Database to a csv file
	 * @param Path The Path to write to
	 * @throws IOException Error in file operations
	 */
	public void save(String path) throws IOException{
	    // Open file for writing
	    BufferedWriter writer = new BufferedWriter(new FileWriter(path));
	    
	    String line;
	    for (V value: dataTable.values()) {
	        // Get each value, convert to a csv line (call toCSV on value)
	        line = value.toCSV();
	        
	        // Write each line
	        writer.write(line);
	    }
	    
	    // Close file
	    writer.close();
	}
}
