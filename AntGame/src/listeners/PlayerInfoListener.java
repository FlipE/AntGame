/**
 * 
 */
package listeners;

/**
 * PlayerInfoListener.java
 *
 * @author 	Chris B
 * @date	2 Apr 2013
 * @version	1.0
 */
public interface PlayerInfoListener {

	public void updateBrainPath(String path, boolean isValid);
	public void updateName(String name, boolean isValid);
	public void updateId(int id);
	
}