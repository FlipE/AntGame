/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.List;

import listeners.WorldInfoListener;
import cells.Cell;
import fileio.AntBrainLoader;

/**
 * WorldInfo.java
 *
 * @author 	Chris B
 * @date	4 Apr 2013
 * @version	1.0
 */
public class WorldInfo {

	/** Cells which represent the world */
	private Cell[][] world;
	
	/** List of listeners observing the world info */
	private List<WorldInfoListener> listeners;

	/** the location of the world in the filesystem */
	private String worldPath;
	
	public WorldInfo() {
		super();
		this.listeners = new ArrayList<WorldInfoListener>();
	}
	
	public void setWorld(Cell[][] world) {
		this.world = world;
	}
	
	public Cell[][] getWorld() {
		return this.world;
	}
	
	public void loadWorld(String worldPath) {
		// set the brain path to the new one given
		this.worldPath = worldPath;
		
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
	
	public void addListener(WorldInfoListener l) {
		this.listeners.add(l);
	}
	
	public void removeListener(WorldInfoListener l) {
		this.listeners.remove(l);
	}
}