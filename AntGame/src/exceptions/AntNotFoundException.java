/**
 * 
 */
package exceptions;

/**
 * AntNotFoundException.java
 *
 * @author 	Chris B
 * @date	22 Mar 2013
 * @version	1.0
 */
public class AntNotFoundException extends Exception {

	private static final long serialVersionUID = 2402924796105825778L;

	/**
	 * @param message
	 */
	public AntNotFoundException(String message) {
		super(message);
	}
	
}