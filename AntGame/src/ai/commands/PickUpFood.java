/**
 * 
 */
package ai.commands;

import exceptions.ReceiverNotSetException;

/**
 * Sense.java
 *
 * @date	28 Mar 2013
 * @version	1.0
 */
public class PickUpFood extends AbstractAntCommand {

	private int successState;
	private int failState;
	
	/**
	 * @param successState
	 * @param failState
	 */
	public PickUpFood(int successState, int failState) {
		super();
		this.successState = successState;
		this.failState = failState;
	}

	/* (non-Javadoc)
	 * @see ai.commands.AbstractAntCommand#execute()
	 */
	@Override
	public int execute() throws ReceiverNotSetException, Exception {
		return super.receiver.pickUpFood(successState, failState);
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append(super.toString());
		s.append(" ");
		s.append(this.successState);
		s.append(" ");
		s.append(this.failState);
		
		return s.toString();
	}
	
}