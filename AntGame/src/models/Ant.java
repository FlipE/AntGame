package models;

import exceptions.ReceiverNotSetException;
import util.Position;
import ai.AntBrain;
import ai.commands.AntCommand;
import antgame.Config;

/**
 * Ant.java
 *
 * @author 	Chris B
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
	}
	
	/* (non-Javadoc)
	 * @see models.Model#update(float)
	 */
	@Override
	public void update() {
		
		// reduce the resting time
		if(this.resting > 0) {
			this.resting -= 1;
		}
		
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
	 */
	public int turn(int direction, int nextState) {
		
		if(direction == Config.TURN_LEFT) {
			this.direction = turnLeft(this.direction);
		}
		else if(direction == Config.TURN_RIGHT) {
			this.direction = turnRight(this.direction);
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
				this.position.set(destination);
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
	 * @return
	 */
	public int getColor() {
		return this.color;
	}

	/**
	 * @return
	 */
	public boolean hasFood() {
		return this.hasFood;
	}
	
	public String toString1() {
		int food = 0;
		if(hasFood()){food = 1;}
		return color +" ant";
	}
	public String toString2() {
		int food = 0;
		if(hasFood()){food = 1;}
		return "dir"+ direction+ ", food "+ food + ", state "+ state + ", resting " + resting;
		//red ant of id 28, dir 5, food 0, state 27, resting 0
	}
}