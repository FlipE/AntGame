/**
 * 
 */
package cells;

import exceptions.CellOccupiedException;
import models.Ant;
import models.AntWorld;

/**
 * AntHill.java
 *
 * @author 	Chris B
 * @date	22 Mar 2013
 * @version	1.0
 */
public interface AntHill extends Cell {

	public Ant spawnAnt(AntWorld world) throws CellOccupiedException;
	public void depositFood();
	
}