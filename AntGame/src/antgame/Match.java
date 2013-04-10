/**
 * 
 */
package antgame;

import java.util.ArrayList;
import java.util.List;
import cells.Cell;
import listeners.MatchListener;
import models.AntWorld;

/**
 * A match runs a single match
 * 
 * Match.java
 *
 * @author 	Chris B
 * @date	6 Apr 2013
 * @version	1.0
 */
public class Match {

	/** list of listeners which need to be informed of updates */
	private List<MatchListener> listeners;
	
	/** the winner is the player with the highest score once the match has finished */
	private int winner;
	
	/** The world is the simulation which will be run */
	private AntWorld world;
	
	/** the name of this match */
	String name;
	
	/** once the match is finished this is set to true */
	private boolean isFinished;
	
	/** random number seed */
	int seed;
	
	/** the speed at which the simulation should be run. This effects the number of rounds calculated per frame */
	int matchSpeed;
	
	/**
	 * 
	 * @param world
	 */
	public Match(AntWorld world, String name) {
		this.world = world;
		this.name = name;
		this.isFinished = false;
		this.listeners = new ArrayList<MatchListener>();
		this.matchSpeed = 10000;
	}

	public void update() {
		if(world.getRoundNum() < Config.NUM_ROUNDS_PER_MATCH) {
			for(int i = 0 ; i < this.matchSpeed; i += 1) {
				world.update();
			}
		}
		else {
			this.winner = world.getWinner();
			this.isFinished = true;
			this.notifyMatchFinished();
		}		
	}
	
	private void notifyMatchFinished() {
		for(MatchListener l : this.listeners) {
			l.isFinished();
		}
	}

	public void addListener(MatchListener l) {
		this.listeners.add(l);
	}
	
	public void removeListener(MatchListener l) {
		this.listeners.remove(l);
	}

	public Cell[][] getCells() {
		return this.world.getCells();
	}
	
	public void setSpeed(int speed) {
		this.matchSpeed = speed;
	}
}