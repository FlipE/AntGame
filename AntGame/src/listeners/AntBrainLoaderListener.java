/**
 * 
 */
package listeners;

import ai.AntBrain;

/**
 * AntBrainLoaderListener.java
 *
 * @author 	Chris B
 * @date	2 Apr 2013
 * @version	1.0
 */
public interface AntBrainLoaderListener {

	public void brainLoadFailed(String message);
	public void brainLoadComplete(AntBrain brain);
	
}