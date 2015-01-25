/**
 * 
 */
package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;

/**
 * An object with data that can be loaded from and saved to text files
 * @author Rex Xia
 *
 */

public interface Saveable {
    /**
     * Load data from the default file
     * @throws UserFileException If somehow unable to read user file
     */
    public void load() throws UserFileException;
    
    /** 
     * Load data from the specified filename
     * @param filename The name of the file to load data from
     * @throws UserFileException If somehow unable to read user file
     */
    public void load(String filename) throws UserFileException;
    
    /** 
     * Load data from the BufferedReader specified
     * @param reader The reader to load data from
     * @throws UserFileException If somehow unable to read user file
     */
    public void load(BufferedReader reader) throws UserFileException;
    
    /**
     * @return True if this Saveable has unsaved data
     * @throws UserFileException If somehow unable to write to user file
     */
    public boolean isUnsaved() throws UserFileException;
    
    /**
     * Save data to the default file
     * @throws UserFileException If somehow unable to write to user file
     */
    public void save() throws UserFileException;
    
    /**
     * Save data to the specified filename
     * @param filename The name of the file to save data to
     * @throws UserFileException If somehow unable to write to user file
     */
    public void save(String filename) throws UserFileException;
    
    /**
     * Save data to the specified BufferedWriter
     * @param writer The writer to write data to
     * @throws UserFileException If somehow unable to write to user file
     */
    public void save(BufferedWriter writer) throws UserFileException;
}
