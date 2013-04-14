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
public class Flip extends AbstractAntCommand {

	private int num;
	private int zeroState;
	private int otherState;
	
	/**
	 * @param num
	 * @param zeroState
	 * @param otherState
	 */
	public Flip(int num, int zeroState, int otherState) {
		super();
		this.num = num;
		this.zeroState = zeroState;
		this.otherState = otherState;
	}


	/* (non-Javadoc)
	 * @see ai.commands.AbstractAntCommand#execute()
	 */
	@Override
	public int execute() throws ReceiverNotSetException, Exception {
		return super.receiver.flip(num, zeroState, otherState);
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append(super.toString());
		s.append(" ");
		s.append(this.num);
		s.append(" ");
		s.append(this.zeroState);
		s.append(" ");
		s.append(this.otherState);
		
		return s.toString();
	}	
}