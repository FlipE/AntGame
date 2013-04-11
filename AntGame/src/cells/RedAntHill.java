/**
 * 
 */
package cells;

import exceptions.CellOccupiedException;
import antgame.Config;
import models.Ant;
import models.AntWorld;

/**
 * BlackAntHill.java
 *
 * @author 	Chris B
 * @date	22 Mar 2013
 * @version	1.0
 */
public class RedAntHill extends ClearCell implements AntHill {

	/**
	 * @param x
	 * @param y
	 * @param food
	 */
	public RedAntHill(int x, int y, int food) {
		super(x, y, food);
	}

	@Override
	public Ant spawnAnt(AntWorld world) throws CellOccupiedException {
		Ant ant = new Ant(world, super.getPosition(), Config.RED_ANT);
		super.arrive(ant);
		return ant;
	}

	/* (non-Javadoc)
	 * @see cells.AntHill#depositFood()
	 */
	@Override
	public void depositFood() {
	}
	
	@Override
	public String toString() {
		return "black hill;";
	}
}