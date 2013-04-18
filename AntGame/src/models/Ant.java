package models;

import util.Position;
import ai.AntBrain;
import ai.commands.AntCommand;
import antgame.Config;
import exceptions.ReceiverNotSetException;

/**
 * Ant.java
 *
 * @date	17 Mar 2013
 * @version	1.0
 */
public class Ant implements Model {

	private static int nextID;
	
	private static int id() {
		return Ant.nextID++;
	}
	
	private final int id = Ant.id();
	private AntWorld world;
	private int color;
	private int resting;
	private int state;
	private boolean hasFood;
	private int direction;
	private Position position;
	private AntBrain brain;
	private int lastUpdated;
	private boolean isDead;
	public Ant old;
	
	/**
	 * 
	 */
	public Ant(AntWorld world, Position position, int color) {
		this.world = world;
		this.position = new Position(position);
		this.color = color;
		if(color == Config.BLACK_ANT) {
			this.brain = world.getBlackBrain();
		}
		else {
			this.brain = world.getRedBrain();
		}
		this.state = 0;
		this.lastUpdated = -1;
		this.isDead = false;
		this.old = null;
	}
	public Object clone(){
		Ant a = new Ant(world, position, color);
		a.state = this.state;
		a.resting = this.resting;
		a.hasFood = this.hasFood;
		a.brain = this.brain;
		a.isDead = this.isDead;
		return a;
	}
	
	/* (non-Javadoc)
	 * @see models.Model#update(float)
	 */
	@Override
	public void update() {
		
		if (lastUpdated > -1){		//from the second round and after, store the previous state for debugging
			old = (Ant) this.clone();
		}
		
		// reduce the resting time
		if(this.resting > 0) {
			this.resting -= 1;
		}
		
		// set the last updated to the current round
		this.lastUpdated = this.world.getRoundNum();
		
		//System.out.println("updating ant " + this.id);
		try {
			AntCommand command =  brain.getCommand(state);
			command.setReceiver(this);
			this.state = command.execute();
			command.resetReceiver();
		}
		catch (IndexOutOfBoundsException e) {
			//TODO log this, it should never happen because the brain was verified by the parser
			e.printStackTrace();
		}
		catch (ReceiverNotSetException e) {
			// log this, it should never happen because we have set the receiver. But if
			// the command implementation is wrong then it will break here.
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 
	 * @param senseDirection
	 * @param trueState
	 * @param falseState
	 * @param condition
	 * @return
	 */
	public int sense(int senseDirection, int trueState, int falseState, int condition) {
		
		// get the location to sense
		Position p = this.parseSenseDirection(senseDirection);
		int x = p.getX();
		int y = p.getY();
		
		// check for one of the possible conditions
		if(condition == Config.SENSE_FRIEND) {
			if(this.world.senseFriend(x, y, this.color)) {
				return trueState;
			}
		}
		else if(condition == Config.SENSE_FOE) {
			if(this.world.senseFoe(x, y, this.color)) {
				return trueState;
			}
		}
		else if(condition == Config.SENSE_FRIENDWITHFOOD) {
			if(this.world.senseFriendWithFood(x, y, this.color)) {
				return trueState;
			}
		}
		else if(condition == Config.SENSE_FOEWITHFOOD) {
			if(this.world.senseFoeWithFood(x, y, this.color)) {
				return trueState;
			}
		}
		else if(condition == Config.SENSE_FOOD) {
			if(this.world.senseFood(x, y)) {
				return trueState;
			}
		}
		else if(condition == Config.SENSE_ROCK) {
			if(this.world.senseRock(x, y)) {
				return trueState;
			}
		}
		else if(condition == Config.SENSE_FOEMARKER) {
			if(this.world.senseFoeMarker(x, y, this.color)) {
				return trueState;
			}
		}
		else if(condition == Config.SENSE_HOME) {
			if(this.world.senseHome(x, y, this.color)) {
				return trueState;
			}
		}
		else if(condition == Config.SENSE_FOEHOME) {
			if(this.world.senseFoeHome(x, y, this.color)) {
				return trueState;
			}
		}		
		return falseState;
	}
	
	/**
	 * 
	 * @param senseDirection
	 * @param trueState
	 * @param falseState
	 * @param type
	 * @return
	 */
	public int senseMark(int senseDirection, int trueState, int falseState, int type) {
		
		Position p = this.parseSenseDirection(senseDirection);
		
		// check the marker type in the cell for this ant's color
		if(this.world.senseMarker(p.getX(), p.getY(), type, this.color)) {
			return trueState;
		}
		else {
			return falseState;
		}		
	}
	
	/**
	 * @param senseDirection
	 * @return
	 */
	private Position parseSenseDirection(int senseDirection) {
		Position p = new Position();
		
		// get the location to sense
		if(senseDirection == Config.SENSE_HERE) {
			// position to sense is ant's current position
			p.set(this.position);
		}
		else if(senseDirection == Config.SENSE_AHEAD) {
			// the position 1 square in the direction the ant is facing
			p.set(this.position.getPositionInDirection(this.direction));
		}
		else if(senseDirection == Config.SENSE_LEFTAHEAD) {
			// the position 1 square ahead and to the left of the ant
			int leftDirection = this.turnLeft(this.direction);
			p.set(this.position.getPositionInDirection(leftDirection));
		}
		else if(senseDirection == Config.SENSE_RIGHTAHEAD) {
			// the position 1 square ahead and to the right of the ant
			int rightDirection = this.turnRight(this.direction);
			p.set(this.position.getPositionInDirection(rightDirection));
		}
		else {
			// throw an error?
		}
		return p;
	}

	/**
	 * Add the given mark type to the current position and transition 
	 * to the given state.
	 * 
	 * @param type
	 * @param nextState
	 * @return
	 */
	public int mark(int type, int nextState) {
		this.world.mark(this.position.getX(), this.position.getY(), type, this.color);	
		return nextState;
	}
	
	/**
	 * Remove the given mark type to the current position and transition 
	 * to the given state.
	 * 
	 * @param type
	 * @param nextState
	 * @return
	 */
	public int unmark(int type, int nextState) {
		this.world.unmark(this.position.getX(), this.position.getY(), type, this.color);
		return nextState;
	}
	
	/**
	 * 
	 * @param successState
	 * @param failState
	 * @return
	 */
	public int pickUpFood(int successState, int failState) {
		
		if(!this.hasFood) {
			if(this.world.pickUpFood(this.position.getX(), this.position.getY())) {
				this.hasFood = true;
				return successState;
			}
		}		
		return failState;
	}
	
	/**
	 * 
	 * @param nextState
	 * @return
	 */
	public int dropFood(int nextState) {
		if(this.hasFood) {
			this.world.dropFood(this.position.getX(), this.position.getY());
			this.hasFood = false;			
		}		
		return nextState;
	}
	
	/**
	 * 
	 * @param direction
	 * @param nextState
	 * @return
	 * @throws Exception 
	 */
	public int turn(int direction, int nextState) throws Exception {
		
		if(direction == Config.TURN_LEFT) {
			this.direction = turnLeft(this.direction);
		}
		else if(direction == Config.TURN_RIGHT) {
			this.direction = turnRight(this.direction);
		}
		else {
			throw new Exception("unknown direction: " + direction);
		}
		
		return nextState;
	}

	/**
	 * @return
	 */
	private int turnRight(int direction) {
		return (direction + 1) % 6;
	}

	/**
	 * @return
	 */
	private int turnLeft(int direction) {
		return (direction + 5) % 6;
	}
	
	/**
	 * 
	 * @param successState
	 * @param failState
	 * @return
	 */
	public int move(int successState, int failState) {
		
		if (resting <= 0) {
			// get the position this ant wants to move to
			Position destination = this.position.getPositionInDirection(this.direction);
			
			// try to move in that direction
			if(this.world.move(this.position.getX(), this.position.getY(), destination.getX(), destination.getY())) {				
				this.resting = Config.MOVEMENT_RESTING_PERIOD;
				return successState;
			}
		}
		return failState;
	}
	
	/**
	 * 
	 * @param num
	 * @param zeroState
	 * @param otherState
	 * @return
	 */
	public int flip(int num, int zeroState, int otherState) {
		int n = this.world.getInt(num);	
		return (n == 0) ? zeroState : otherState;
	}

	/**
	 * Get this ant's colour this should be one of either
	 * {@link red#Config.RED_ANT} or {@link black#Config.BLACK_ANT}.
	 * 
	 * @return either {@link red#Config.RED_ANT} or {@link black#Config.BLACK_ANT}.
	 */
	public int getColor() {
		return this.color;
	}

	/**
	 * Check to see if this ant is carrying any food.
	 * 
	 * @return true if this ant has food, false otherwise.
	 */
	public boolean hasFood() {
		return this.hasFood;
	}

	/**
	 * Get the number of rounds this ant has left to rest before it can move again.
	 * 
	 * @return the number of rounds this ant has left to rest before it can move again.
	 */
	public int getResting() {
		return resting;
	}

	/**
	 * Get this ant's current state
	 * 
	 * @return the ant's current state
	 */
	public int getState() {
		return state;
	}

	/**
	 * Get the ant's current direction.
	 * 
	 * @return the ant's current direction.
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * Get the ant's current position.
	 * 
	 * @return the ant's current position.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * update this ant's position to the new given position.
	 * 
	 * @param position the new position of this ant
	 */
	public void setPosition(Position position) {
		this.position.set(position);
	}

	/**
	 * @return the lastUpdated
	 */
	public int getLastupdated() {
		return this.lastUpdated;
	}

	public void kill() {
		this.isDead = true;
	}
	
}