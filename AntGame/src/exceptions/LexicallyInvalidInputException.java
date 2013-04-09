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
public class LexicallyInvalidInputException extends Exception {

	private static final long serialVersionUID = 8322592249072086449L;

	/**
	 * @param message
	 */
	public LexicallyInvalidInputException(String message) {
		super(message);
	}
	
}