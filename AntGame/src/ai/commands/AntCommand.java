/**
 * 
 */
package ai.commands;

import models.Ant;
import exceptions.ReceiverNotSetException;

/**
 * AntCommand.java
 *
 * @date	28 Mar 2013
 * @version	1.0
 */
public interface AntCommand {

	/**
	 * Set the receiver that the command should be executed on.
	 * 
	 * @param receiver the receiver of the command
	 */
	public void setReceiver(Ant receiver);
	
	/**
	 * Reset receiver to null
	 */
	public void resetReceiver();
	
	/**
	 * Execute a command on the receiver. the receiver must first be set
	 * @throws ReceiverNotSetException if the receiver is not set
	 * @throws Exception
	 * 
	 * @return the next command index to execute
	 */
	public int execute() throws ReceiverNotSetException, Exception;
}