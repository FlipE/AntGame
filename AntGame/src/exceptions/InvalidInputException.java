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
public class InvalidInputException extends Exception {

	private static final long serialVersionUID = 8322592249072086449L;

	/**
	 * @param message
	 */
	public InvalidInputException(String message) {
		super(message);
	}
	
}