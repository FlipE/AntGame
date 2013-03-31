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
public class Turn extends AbstractAntCommand {

	private int direction;
	private int nextState;
	
	/**
	 * @param direction
	 * @param nextState
	 */
	public Turn(int direction, int nextState) {
		super();
		this.direction = direction;
		this.nextState = nextState;
	}

	/* (non-Javadoc)
	 * @see ai.commands.AbstractAntCommand#execute()
	 */
	@Override
	public int execute() throws ReceiverNotSetException {
		return super.receiver.turn(direction, nextState);
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append(super.toString());
		s.append(" ");
		s.append(this.direction);
		s.append(" ");
		s.append(this.nextState);
		
		return s.toString();
	}
	
}