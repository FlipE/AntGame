package fileio;

import java.util.ArrayList;
import java.util.List;

import listeners.WorldLoaderListener;
import cells.Cell;
import exceptions.InvalidWorldException;

public class WorldLoader implements Runnable {

	private List<WorldLoaderListener> listeners;
	private String filepath;
	
	public WorldLoader(String filepath) {
		this.filepath = filepath;
		this.listeners = new ArrayList<WorldLoaderListener>();
	}
	
	private Cell[][] load(String filepath) throws InvalidWorldException {
		return SimpleWorldLoader.load(filepath);
	}
	
	@Override
	public void run() {
		
		try {
			Cell[][] world = this.load(this.filepath);
			this.notifyWorldLoadComplete(world);
		}
		catch(InvalidWorldException e) {
			this.notifyWorldLoadFailed(e.getMessage());
		}	
		
	}
	
	private void notifyWorldLoadFailed(String message) {
		for(WorldLoaderListener l : this.listeners) {
			l.worldLoadFailed(message);
		}
	}
	
	private void notifyWorldLoadComplete(Cell[][] world) {
		for(WorldLoaderListener l : this.listeners) {
			l.worldLoadComplete(world);
		}
	}
	
	public void addListener(WorldLoaderListener l) {
		this.listeners.add(l);
	}
	
	public void removeListener(WorldLoaderListener l) {
		this.listeners.remove(l);
	}
}