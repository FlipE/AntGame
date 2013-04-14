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
public class SenseMark extends AbstractAntCommand {

	private int direction;
	private int trueState;
	private int falseState;
	private int type;
	
	/**
	 * @param direction
	 * @param trueState
	 * @param falseState
	 * @param markType
	 */
	public SenseMark(int direction, int trueState, int falseState, int markType) {
		super();
		this.direction = direction;
		this.trueState = trueState;
		this.falseState = falseState;
		this.type = markType;
	}

	/* (non-Javadoc)
	 * @see ai.commands.AbstractAntCommand#execute()
	 */
	@Override
	public int execute() throws ReceiverNotSetException, Exception {
		return super.receiver.senseMark(direction, trueState, falseState, type);
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append("Sense");
		s.append(" ");
		s.append(this.direction);
		s.append(" ");
		s.append(this.trueState);
		s.append(" ");
		s.append(this.falseState);
		s.append(" ");
		s.append("marker");
		s.append(" ");
		s.append(this.type);
		
		return s.toString();
	}
	
}
