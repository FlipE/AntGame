/**
 * 
 */
package ai;

import ai.commands.AntCommand;

import com.badlogic.gdx.utils.Array;

/**
 * AntBrain.java
 *
 * @date	22 Mar 2013
 * @version	1.0
 */
public class AntBrain {
	
	private final Array<AntCommand> commands = new Array<AntCommand>();
		
	/**
	 * 
	 * @param command
	 */
	public void addCommand(AntCommand command) {
		this.commands.add(command);
	}
	
	/**
	 * Given a command index returns the command at that index
	 * 
	 * 
	 * @param index the command index
	 * @return the command at the given index
	 * @throws IndexOutOfBoundsException
	 */
	public AntCommand getCommand(int index) throws IndexOutOfBoundsException {
		return this.commands.get(index);
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		for(AntCommand c : this.commands) {
			s.append(c.toString());
			s.append("\n");
		}
		
		return s.toString();
	}
}