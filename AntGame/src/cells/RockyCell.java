/**
 * 
 */
package cells;

import util.Position;
import models.Ant;
import exceptions.AntNotFoundException;
import exceptions.CellOccupiedException;

/**
 * RockyCell.java
 *
 * @author 	Chris B
 * @date	22 Mar 2013
 * @version	1.0
 */
public class RockyCell implements Cell {

	private Position position;
	
	/**
	 * 
	 */
	public RockyCell(int x, int y) {
		super();
		this.position = new Position(x, y);
	}

	@Override
	public boolean isOccupied() {
		return false;
	}

	@Override
	public Ant leave() throws AntNotFoundException {
		throw new AntNotFoundException("Ants cannot occupy rocky cells.");
	}

	@Override
	public void arrive(Ant a) throws CellOccupiedException {
		throw new CellOccupiedException("Ants cannot occupy rocky cells.");
	}

}
