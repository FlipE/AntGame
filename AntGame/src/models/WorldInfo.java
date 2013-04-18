/**
 * 
 */
package models;

import java.util.ArrayList;
import java.util.List;

import listeners.WorldInfoListener;
import listeners.WorldLoaderListener;
import cells.Cell;
import fileio.WorldLoader;

/**
 * WorldInfo.java
 *
 * @date	4 Apr 2013
 * @version	1.0
 */
public class WorldInfo implements WorldLoaderListener {

	/** Cells which represent the world */
	private Cell[][] world;
	
	/** List of listeners observing the world info */
	private List<WorldInfoListener> listeners;

	/** the location of the world in the filesystem */
	private String worldPath;
	
	/** the random number generator seed */
	private int seed;
	
	public WorldInfo() {
		super();
		this.listeners = new ArrayList<WorldInfoListener>();
		this.seed = 12345;
	}
	
	/**
	 * Set the random number seed for this world
	 * @param seed the random number seed
	 */
	public void setSeed(int seed) {
		this.seed = seed;
	}
	
	/**
	 * Get the random number seed for this world
	 * @return the random number seed
	 */
	public int getSeed() {
		return this.seed;
	}
	
	/**
	 * The cells which make up this world
	 * @return the cells which make up the world
	 */
	public Cell[][] getWorld() {
		return this.world;
	}
	
	/**
	 * given a path creates a world loader thread and
	 * attempts to load the world
	 * 
	 * @param worldPath the path to the world file
	 */
	public void loadWorld(String worldPath) {
		// set the world path to the new one given
		this.worldPath = worldPath;
		
		// create a new world loader thread
		WorldLoader loader = new WorldLoader(worldPath);
		Thread t = new Thread(loader);
		
		// set this info as brain loader listener
		loader.addListener(this);
		
		// start the thread
		t.start();
	}
	
	/**
	 * Register a listener with this object
	 * @param l
	 */
	public void addListener(WorldInfoListener l) {
		this.listeners.add(l);
	}
	
	/**
	 * remove a listener from this object
	 * @param l
	 */
	public void removeListener(WorldInfoListener l) {
		this.listeners.remove(l);
	}

	/**
	 * When notified by the world loader of a failure
	 * notify all listeners.
	 */
	@Override
	public void worldLoadFailed(String message) {
		this.notifyUpdateWorldPath(message, false);
	}

	/**
	 * When notified by the world loader of a completion
	 * notify all listeners. and set the world
	 */
	@Override
	public void worldLoadComplete(Cell[][] world) {
		this.world = world;
		this.notifyUpdateWorldPath(this.worldPath, true);
	}
	
	/**
	 * Notify all listeners to update their world paths with
	 * the given message and whether or not the path was valid.
	 * 
	 * This can be used to update the view of a succesful load
	 * or display an error when something goes wrong.
	 * 
	 * @param message the message to pass to all listeners
	 * @param isValid whether or not the load was successful
	 */
	private void notifyUpdateWorldPath(String message, boolean isValid) {
		for(WorldInfoListener l : this.listeners) {
			l.updateWorldPath(message, isValid);
		}
	}
}