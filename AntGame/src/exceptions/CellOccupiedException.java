/**
 * 
 */
package exceptions;

/**
 * CellOccupiedException.java
 *
 * @author 	Chris B
 * @date	22 Mar 2013
 * @version	1.0
 */
public class CellOccupiedException extends Exception {

	private static final long serialVersionUID = -7009110369840097116L;

	/**
	 * @param message
	 */
	public CellOccupiedException(String message) {
		super(message);
	}
	
}