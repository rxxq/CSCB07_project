/**
 * 
 */
package backend.managers;

/**
 * An interface for objects that can be put into a Database
 * @author xiaxiuqi
 *
 */
public interface DatabaseEntry<K> {
	
	/**
	 * @return A key that can be used as an unique identifier
	 * for this DatabaseEntry
	 */
	public K getKey();
	
	/**
	 * @return A one-line string in CSV format representing this DatabaseEntry
	 */
	public String toCSV();

}
