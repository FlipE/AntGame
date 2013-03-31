/**
 * 
 */
package cells;

import exceptions.CellOccupiedException;
import models.Ant;
import models.AntWorld;
import antgame.Config;

/**
 * BlackAntHill.java
 *
 * @author 	Chris B
 * @date	22 Mar 2013
 * @version	1.0
 */
public class BlackAntHill extends ClearCell implements AntHill {

	/**
	 * @param x
	 * @param y
	 * @param food
	 */
	public BlackAntHill(int x, int y, int food) {
		super(x, y, food);
	}

	/**
	 * Create a new ant of the appropriate colour, setting as the 
	 * cells occupying ant. The ant is also returned to the calling method.
	 * 
	 * @return The {@link Ant#models.Ant} instance which is created
	 */
	public Ant spawnAnt(AntWorld world) throws CellOccupiedException {
		Ant ant = new Ant(world, super.getPosition(), Config.BLACK_ANT);
		super.arrive(ant);
		return ant;
	}

	/* (non-Javadoc)
	 * @see cells.AntHill#depositFood()
	 */
	@Override
	public void depositFood() {
	}
	
}