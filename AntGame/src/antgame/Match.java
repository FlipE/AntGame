/**
 * 
 */
package antgame;

import java.util.ArrayList;
import java.util.List;

import listeners.MatchListener;
import models.AntWorld;
import models.PlayerInfo;
import cells.Cell;

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

	/** the two players fighting in this match */
	private PlayerInfo redPlayer;
	private PlayerInfo blackPlayer;
	
	/**
	 * 
	 * @param world
	 */
	public Match(AntWorld world, PlayerInfo redPlayer, PlayerInfo blackPlayer, String name) {
		this.world = world;
		this.redPlayer = redPlayer;
		this.blackPlayer = blackPlayer;
		this.name = name;
		this.isFinished = false;
		this.listeners = new ArrayList<MatchListener>();
		this.matchSpeed = 100;
	}

	public void update() {
		if(world.getRoundNum() < Config.NUM_ROUNDS_PER_MATCH) {
			for(int i = 0 ; i < this.matchSpeed; i += 1) {
				world.update();
			}
		}
		else {
			this.winner = this.getWinner(world.getRedScore(), world.getBlackScore());
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
	
	/**
	 * Returns the id of the player with the highest score or
	 * {@link antgame.Config.DRAW} if the game is a draw.
	 * 
	 * @param redScore
	 * @param blackScore
	 * @return the id of the player with the highest score or {@link antgame.Config.DRAW} if the game is a draw.
	 */
	public int getWinner(int redScore, int blackScore) {
		int winner = Config.DRAW;
		if(blackScore > redScore) {
			winner = blackPlayer.getId();
		}
		else if(blackScore < redScore) {
			winner = redPlayer.getId();
		}		
		return winner;
	}
	
	/**
	 * Return the current round number.
	 * 
	 * @return the current round number.
	 */
	public int getRoundNum() {
		return this.world.getRoundNum();
	}
}