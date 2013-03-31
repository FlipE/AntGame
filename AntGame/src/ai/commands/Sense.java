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
public class Sense extends AbstractAntCommand {

	private int direction;
	private int trueState;
	private int falseState;
	private int condition;
	private int markType;
	
	/**
	 * @param direction
	 * @param trueState
	 * @param falseState
	 * @param condition
	 */
	public Sense(int direction, int trueState, int falseState, int condition) {
		super();
		this.direction = direction;
		this.trueState = trueState;
		this.falseState = falseState;
		this.condition = condition;
	}

	/* (non-Javadoc)
	 * @see ai.commands.AbstractAntCommand#execute()
	 */
	@Override
	public int execute() throws ReceiverNotSetException {
		return super.receiver.sense(direction, trueState, falseState, condition);
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append(super.toString());
		s.append(" ");
		s.append(this.direction);
		s.append(" ");
		s.append(this.trueState);
		s.append(" ");
		s.append(this.falseState);
		s.append(" ");
		s.append(this.condition);
		
		return s.toString();
	}
	
}
