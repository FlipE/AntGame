package listeners;

import cells.Cell;

public interface WorldLoaderListener {

	public void worldLoadFailed(String message);
	public void worldLoadComplete(Cell[][] world);
	
}