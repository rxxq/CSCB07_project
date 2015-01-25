/**
 * 
 */
package backend.managers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

// NOTE: Automarker did not compile with this import
//import android.util.Log;
import backend.exceptions.AlreadyExistingEntryException;
import backend.exceptions.InvalidCSVFormatException;
import backend.exceptions.NoSuchEntryException;

/**
 * A manager (not an actual SQL database, it's actually a manager)
 * that can read data from a csv file and load it into memory.
 * This is a singleton.
 * @author xiaxiuqi
 *
 */
public abstract class Manager<K, V extends DatabaseEntry<K>>{
    
    /** The tag used for file read/write logcat messages **/
    public static final String FILEIOTAG = "Flybook_File_IO";
    
    /** 
     * The dataTable is where all the data resides.
     * It is a map of keys to values (DatabaseEntries).
     */
	protected HashMap<K, V> dataTable;
	
	/**
	 * Create a new empty Database.
	 */
	protected Manager(){
		dataTable = new HashMap<K,V>();
	}
	
	/**
	 * Reads data from a csv file and loads it into this Database.
	 * Also closes the file before returning.
	 * @param inputFile The csv file to read data from
	 * @throws IOException Error in file operations
	 */
	public void load(File inputFile)
	        throws IOException{
		
	    // Wrap the file in a reader
	    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
	    
		// Read one line at a time
	    for(String line = reader.readLine();
	            line != null; line = reader.readLine()) {
	        
	        // Call parseLine on the line to get a data entry
	        // (eg: ClientInfo or Flight)
	        V newEntry;
            try {
                newEntry = parseLine(line);
            } catch (InvalidCSVFormatException e) {
                // If the CSV format is invalid, print a message to logcat
                //Log.e(FILEIOTAG, e.getMessage());
                // then skip this line
                continue;
            }
	        		
    		// Call getKey on the value
    		// Put the key, value into the dataTable
            try {
                addNewEntry(newEntry.getKey(), newEntry);
            } catch (AlreadyExistingEntryException e) {
                // If already exists, skip this entry but print an error message
                //Log.e(FILEIOTAG, "Error while loading file: "+ e.getMessage());
                continue;
            }
	    }

	    // Close the file
	    reader.close();
	}
	
	/**
     * Reads data from a csv file and loads it into this Database.
     * Also closes the file before returning.
     * @param path The path of the csv file to read data from
     * @throws IOException Error in file operations
	 */
	public void load(String path)
	        throws IOException{
	    load(new File(path));
	}
	
	/**
	 * Parses a line from the csv file and return a value
	 * (eg ClientInfo or a FlightInfo or a AuthorizationInfo)
	 * @param line A line from the csv file
	 * @return A value to be added to the Database
	 */
	public abstract V parseLine(String line)
	        throws InvalidCSVFormatException;
	
	/**
	 * Writes data from this Database to a csv file.
	 * Also closes the file before returning.
	 * @param outputFile The file to write to
	 * @throws IOException Error in file operations
	 */
	public void save(File outputFile) throws IOException{
	    // Wrap the file in a writer
	    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
	    
	    String line;
	    for (V entry: dataTable.values()) {
	        // Get each entry, convert to a csv line (call toCSV on entry)
	        line = entry.toCSV();
	        
	        // Add a newline at the end of each line and write it
	        writer.write(line + "\n");
	    }
	    
	    // Close file
	    writer.close();
	}
	
	/**
	 * Safely adds a new entry. Ensures that a new entry cannot be added if
	 * another entry with the same the key already exists in this Database.
	 * @param key
	 * @param newEntry
	 * @throws AlreadyExistingEntryException If an entry already exists with
	 * the same key in this Database.
	 */
	protected void addNewEntry(K key, V newEntry) throws AlreadyExistingEntryException {
	    // First check if there is already an entry with the same key
        try{
            getEntry(key);
        }
        catch (NoSuchEntryException e){
            // If the entry did not already exist we can add it
            dataTable.put(key, newEntry);
            return;
        }
        // If user did already exist throw an exception
        throw new AlreadyExistingEntryException(String.format(
                "Another entry with key:"
                + " %s already exists in this %s!", key.toString(),
                this.getClass().getName()));

	}
	/**
	 * Updates an existing entry. Ensures that an entry with that key
	 * already exists in this database
	 * @param key
	 * @param updatedEntry
	 * @throws NoSuchEntryException If an entry does not exist
	 * with that key
	 */
	protected void setEntry(K key, V updatedEntry) throws NoSuchEntryException{
		// First check if there is already n entry with the same key
		try{
			getEntry(key);
		}
		catch (NoSuchEntryException e){
			// If the user does not exist throw an exception
			throw new NoSuchEntryException(String.format(
	                "No entry with key:"
	                + " %s exists in %s!", key.toString(),
	                this.getClass().getName()));
		}
		// If the user exist update their new info
		dataTable.put(key, updatedEntry);
	}
	/**
	 * Safely gets the entry corresponding to the key. Ensures that an
	 * exception is raised if the key does not exist in this Database.
	 * @param key
	 * @return The entry. Note that no copy is made, the original reference
	 * to the entry is returned
	 * @throws NoSuchEntryException if the entry does not exist in this Database
	 */
	protected V getEntry(K key) throws NoSuchEntryException {
	    // Check to make sure that the entry can be found
        if(!dataTable.containsKey(key)) {
            throw new NoSuchEntryException(String.format("The entry with key:"
                    + " %s could not be found"
                    + " in this %s", key.toString(),
                    this.getClass().getName()));
        }
        
        // Return the entry 
        // No copying, the original is returned because we don't know if V
        // has a copy constructor
        // The specific subclass of Manager should make a copy and return it
        return dataTable.get(key);
	}
	
	/**
	 * Deletes all the data in this Database.
	 */
	public void clearData() {
	    dataTable.clear();
	}
	
//	/**
//	 * Remove a key-value pair from this Manager
//	 * @param key The key of data to remove
//	 * @return The value that was removed from this Manager
//	 */
//	public V removeAndReturn(K key) {
//	    return dataTable.remove(key);
//	}
	
	/**
	 * Change the key associated with a particular record
	 * Eg: change a user's email
	 * @param oldKey
	 * @param newKey
	 * @throws AlreadyExistingEntryException if the new key already exists in
	 * this Manager.
	 */
	public void changeKey(K oldKey, K newKey) throws AlreadyExistingEntryException {
	    // First make sure the newKey is not already taken
	    try {
            getEntry(newKey);
        } catch (NoSuchEntryException e) {
            // If its not taken we can allow this operation
            // Remove the data from the old key and re-add it to under a new key
            dataTable.put(newKey, dataTable.remove(oldKey));
            return;
        }
	    // else, throw an exception
	    throw new AlreadyExistingEntryException(String.format("Cannot change " +
	    		"%s to %s because the key %s already exists!", 
	    		oldKey, newKey));
	}
	
	/**
	 * Return a set of all the keys in this Database.
	 * The set returned is a copy, so modifications to it does not affect the
	 * Database
	 * @return A copy of the set of all keys in this Database
	 */
	public Set<K> getKeys() {
        return new TreeSet<K>(dataTable.keySet());
	}
}
