package cells;

import antgame.Config;
import models.Ant;
import util.Position;
import exceptions.AntNotFoundException;
import exceptions.CellOccupiedException;

/**
 * Cell.java
 *
 * @author 	Chris B
 * @date	17 Mar 2013
 * @version	1.0
 */
public class ClearCell implements Cell {

	private Position position;
	private Ant ant;
	private byte redTrail;
	private byte blackTrail;
	private int food;
	
	
	public ClearCell(int x, int y, int food) {
		this.position = new Position(x, y);
		this.ant = null;
		this.redTrail = 0;
		this.blackTrail = 0;
		this.food = food;
	}

	public boolean isOccupied() {
		return this.ant != null;
	}

	public Ant leave() throws AntNotFoundException {
		if(this.ant != null) {
			Ant a = this.ant;
			this.ant = null;
			return a;
		}
		else {
			throw new AntNotFoundException("There is no ant on this cell.");
		}
	}

	public void arrive(Ant a) throws CellOccupiedException {
		if(this.ant == null) {
			this.ant = a;
			this.ant.setPosition(this.position);
		}
		else {
			throw new CellOccupiedException("The cell is already occupied.");
		}
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @return the food
	 */
	public int numFood() {
		return food;
	}
	
	/**
	 * If there is food in the cell. remove 1 and return true, else return false.
	 * @return true if food is picked up, false otherwise.
	 */
	public boolean pickUpFood() {
		if(this.food > 0) {
			this.food -= 1;
			return true;
		}
		return false;
	}
	
	/**
	 * If there is less than 9 food in the cell. Add 1 and return true, else return false.
	 * @return true if the food is dropped off, false otherwise.
	 */
	public void dropFood() {
		this.food += 1;
	}

	/**
	 * Check to see if the given red marker type is set.
	 * 
	 * @param type the marker type
	 * @return true if the marker type is set
	 */
	public boolean senseRedTrail(int type) {
		// if the type is valid then check to see if the bit for that marker is set
		if(type >= 0 && type <= 5 ) {
			return (this.redTrail & (1 << (type+1))) != 0;
		}
		return false;
	}
	
	/**
	 * Check to see if the given black marker type is set.
	 * 
	 * @param type the marker type
	 * @return true if the marker type is set
	 */
	public boolean senseBlackTrail(int type) {
		// if the type is valid then check to see if the bit for that marker is set
		if(type >= 0 && type <= 5 ) {
			return (this.blackTrail & (1 << (type+1))) != 0;
		}
		return false;
	}
	
	public void setRedTrail(int type) {
		if(type >= 0 && type <= 5 ) {
			this.redTrail |= 1 << (type+1);
		}
	}
	
	public void setBlackTrail(int type) {
		if(type >= 0 && type <= 5 ) {
			this.blackTrail |= 1 << (type+1);
		}
	}
	
	public void clearRedTrail(int type) {
		if(type >= 0 && type <= 5 ) {
			this.redTrail &= ~(1 << (type+1));
		}
	}
	
	public void clearBlackTrail(int type) {
		if(type >= 0 && type <= 5 ) {
			this.blackTrail &= ~(1 << (type+1));
		}
	}

	public Ant getAnt() {
		return ant;
	}

	/**
	 * @param color
	 */
	public boolean senseFoeMarker(int color) {
		if(color == Config.RED_ANT) {
			return this.blackTrail > 0;
		}
		else if(color == Config.BLACK_ANT) {
			return this.redTrail > 0;
		}
		return false;
	}

	@Override
	public void killOccupyingAnt() throws AntNotFoundException {
		if(this.ant != null) {
			this.ant.kill();
			int color = this.ant.getColor();
			this.ant = null;
			this.food += Config.DEAD_ANT_FOOD_VALUE;
			
			System.out.println(((color == Config.BLACK_ANT) ? "black " : "red ") + "ant killed " + this.position);
		}
		else {
			throw new AntNotFoundException("There is no ant on this cell.");
		}
	}
}