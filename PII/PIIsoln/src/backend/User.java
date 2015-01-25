package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/** 
 * @author Daniel, Rex, Brandon, Trevor
 */
public abstract class User implements Saveable{
    
    /** Name of the folder that stores user data */
    public static final String USERDIR = "./userinfo/";
    /** String to indicate a Client */
    public static final String CLIENT = "client";
    /** String to indicate an Admin */
    public static final String ADMIN = "admin";

    
    /** True if there are unsaved changes to this User's data */
    protected boolean unsaved = false;
    /** This User's email address. Also serves as a username */
    protected String email;
    /** This User's password. Currently not actually used */
    protected String password = "";
    
    
    /**
     * Converts an email address into a filename for a user data file.
     * This is done by converting '@' to '_' and appending ".txt"
     * @param email The email address to be converted into a filename
     * @return The filename corresponding to the email
     */
	public static String emailToFilename (String email) {
	    return email.replace('@', '_') + ".txt";
	}

    /**
     * @return True if there are unsaved changes to this User's data
     */
    public boolean isUnsaved() {
        return unsaved;
    }
    
    /**
     * Load data from the default file for this User
     * (named after this user's email address)
     * @throws UserFileException If somehow unable to read user file
     */
    public void load() throws UserFileException {
        load(User.emailToFilename(email));
    }
	
    /** 
     * Load data from the specified filename
     * @param filename The name of the file to load data from
     * @throws UserFileException If somehow unable to read user file
     */
    public void load(String filename) throws UserFileException {
        BufferedReader reader;
        
        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            throw new UserFileException("Unable to open " + filename);
        }
        
        load(reader);
        
        try {
            reader.close();
        } catch (IOException e) {
            throw new UserFileException("Unable to close " + filename);
        }
    }
    
    /** 
     * Load data from the BufferedReader specified
     * @param reader The reader to load data from
     */
    public abstract void load (BufferedReader reader) throws UserFileException;

    /* (non-Javadoc)
     * @see backend.Saveable#save()
     */
    @Override
    public void save() throws UserFileException {
        save(User.emailToFilename(email));
    }

    /* (non-Javadoc)
     * @see backend.Saveable#save(java.lang.String)
     */
    @Override
    public void save(String filename) throws UserFileException {
        BufferedWriter writer;
        
        try {
            writer = new BufferedWriter(new FileWriter(filename));
        } catch (IOException e) {
            throw new UserFileException("Unable to write " + filename);
        }
        
        save(writer);
        
        try {
            writer.close();
        } catch (IOException e) {
            throw new UserFileException("Unable to write " + filename);
        }
    }

    /* (non-Javadoc)
     * @see backend.Saveable#save(java.io.BufferedWriter)
     */
    @Override
    public abstract void save(BufferedWriter writer) throws UserFileException;
    
    
	
}
