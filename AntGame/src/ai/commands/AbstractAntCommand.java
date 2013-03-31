/**
 * 
 */
package ai.commands;

import models.Ant;
import exceptions.ReceiverNotSetException;

/**
 * AbstractAntCommand.java
 *
 * @author 	Chris B
 * @date	28 Mar 2013
 * @version	1.0
 */
public abstract class AbstractAntCommand implements AntCommand {

	protected Ant receiver;
	
	/**
	 * Set the receiver that the command should be executed on.
	 * @param a
	 * @return 
	 */
	@Override
	public void setReceiver(Ant receiver) {
		this.receiver = receiver;
	}

	/**
	 * Reset receiver to null
	 */
	@Override
	public void resetReceiver() {
		this.receiver = null;
	}

	/**
	 * Execute a command on the receiver. the receiver must first be set
	 * @throws ReceiverNotSetException if the receiver is not set
	 * @return the next command index to execute
	 */
	@Override
	public abstract int execute() throws ReceiverNotSetException;
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
	
}