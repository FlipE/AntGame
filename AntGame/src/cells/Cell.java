/**
 * 
 */
package cells;

import models.Ant;
import exceptions.AntNotFoundException;
import exceptions.CellOccupiedException;

/**
 * Cell.java
 *
 * @author 	Chris B
 * @date	22 Mar 2013
 * @version	1.0
 */
public interface Cell {

	public boolean isOccupied();
	public Ant leave() throws AntNotFoundException;
	public void arrive(Ant a) throws CellOccupiedException;
	
}