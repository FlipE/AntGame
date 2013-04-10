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
public class Unmark extends AbstractAntCommand {

	private int type;
	private int nextState;
	
	/**
	 * @param type
	 * @param nextState
	 */
	public Unmark(int type, int nextState) {
		super();
		this.type = type;
		this.nextState = nextState;
	}

	/* (non-Javadoc)
	 * @see ai.commands.AbstractAntCommand#execute()
	 */
	@Override
	public int execute() throws ReceiverNotSetException {
		return super.receiver.unmark(type, nextState);
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append(super.toString());
		s.append(" ");
		s.append(this.type);
		s.append(" ");
		s.append(this.nextState);
		
		return s.toString();
	}
	
}