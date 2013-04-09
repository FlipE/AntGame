/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.List;

import listeners.AntBrainLoaderListener;
import listeners.PlayerInfoListener;
import ai.AntBrain;
import fileio.AntBrainLoader;

/**
 * PlayerInfo.java
 *
 * @author 	Chris B
 * @date	2 Apr 2013
 * @version	1.0
 */
public class PlayerInfo implements AntBrainLoaderListener {

	private List<PlayerInfoListener> listeners;
	private AntBrain brain;
	private String brainPath;
	private String name;
	private int id;
	
	/**
	 * @param name
	 */
	public PlayerInfo(String name, int id) {
		super();
		this.name = name;
		this.id = id;
		this.listeners = new ArrayList<PlayerInfoListener>();
	}

	/**
	 * @return the brain
	 */
	public AntBrain getBrain() {
		return brain;
	}

	/**
	 * @param brain the brain to set
	 */
	public void setBrain(AntBrain brain) {
		this.brain = brain;
	}

	/**
	 * @return the brainPath
	 */
	public String getBrainPath() {
		return brainPath;
	}

	/**
	 * @param brainPath the brainPath to set
	 */
	public void setBrainPath(String brainPath) {
		this.brainPath = brainPath;
	}

	public void loadBrain(String brainPath) {
		// set the brain path to the new one given
		this.brainPath = brainPath;
		
		// set the brain state to validating
		// update listeners with brain state
		//this.notifyUpdateBrainPath("Validating...");
		
		// create a new brain loader thread
		AntBrainLoader loader = new AntBrainLoader(brainPath);
		Thread t = new Thread(loader);
		
		// set this info as brain loader listener
		loader.addListener(this);
		
		// start the thread
		t.start();
	}
	
	@Override
	public void brainLoadFailed(String message) {
		this.notifyUpdateBrainPath(message, false);
	}
	
	@Override
	public void brainLoadComplete(AntBrain brain) {
		this.brain = brain;
		this.notifyUpdateBrainPath(this.brainPath, true);
	}
	
	/**
	 * @param brainPath
	 */
	private void notifyUpdateBrainPath(String brainPath, boolean isValid) {
		for(PlayerInfoListener l : this.listeners) {
			l.updateBrainPath(brainPath, isValid);
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	public void addListener(PlayerInfoListener l) {
		this.listeners.add(l);
	}
	
	public void removeListener(PlayerInfoListener l) {
		this.listeners.remove(l);
	}
}