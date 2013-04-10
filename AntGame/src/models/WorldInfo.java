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
 * @author 	Chris B
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
	
	public WorldInfo() {
		super();
		this.listeners = new ArrayList<WorldInfoListener>();
	}
	
	public Cell[][] getWorld() {
		return this.world;
	}
	
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
	
	public void addListener(WorldInfoListener l) {
		this.listeners.add(l);
	}
	
	public void removeListener(WorldInfoListener l) {
		this.listeners.remove(l);
	}

	@Override
	public void worldLoadFailed(String message) {
		this.notifyUpdateWorldPath(message, false);
	}

	@Override
	public void worldLoadComplete(Cell[][] world) {
		this.world = world;
		this.notifyUpdateWorldPath(this.worldPath, true);
	}
	
	private void notifyUpdateWorldPath(String message, boolean isValid) {
		for(WorldInfoListener l : this.listeners) {
			l.updateWorldPath(message, isValid);
		}
	}
}