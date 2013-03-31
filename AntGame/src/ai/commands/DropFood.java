/**
 * 
 */
package ai.commands;

import exceptions.ReceiverNotSetException;

/**
 * Sense.java
 *
 * @author 	Chris B
 * @date	28 Mar 2013
 * @version	1.0
 */
public class DropFood extends AbstractAntCommand {

	private int nextState;
	
	/**
	 * @param nextState
	 */
	public DropFood(int nextState) {
		super();
		this.nextState = nextState;
	}

	/* (non-Javadoc)
	 * @see ai.commands.AbstractAntCommand#execute()
	 */
	@Override
	public int execute() throws ReceiverNotSetException {
		return 0;
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append(super.toString());
		s.append(" ");
		s.append(this.nextState);
		
		return s.toString();
	}
	
}
