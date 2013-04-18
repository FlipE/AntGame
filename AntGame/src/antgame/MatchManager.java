/**
 * 
 */
package antgame;

import models.AntWorld;
import models.PlayerInfo;
import models.WorldInfo;

import com.badlogic.gdx.utils.Array;

/**
 * MatchManager.java
 *
 * @date	6 Apr 2013
 * @version	1.0
 */
public class MatchManager {

	private Array<PlayerInfo> players;
	private Array<WorldInfo> worlds;
	private Array<Match> matchQueue;
	
	/** a pointer to the next match in the queue */
	private int matchIndex;
	
	/** Signleton class instance */
	private static MatchManager instance = new MatchManager();
	
	/**
	 * Get the singleton instance
	 * 
	 * @return the instance of MatchManager
	 */
	public static MatchManager getInstance() {
		return instance;
	}
	
	/**
	 * Private default constructor. This class is a singleton
	 */
	private MatchManager() {
		this.players = new Array<PlayerInfo>();
		this.worlds = new Array<WorldInfo>();
		this.matchQueue = new Array<Match>();
		this.matchIndex = 0;
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void match() throws Exception {
		
		if(players.size < 2) {
			System.out.println("not enough players");
			throw new Exception("There must be at least 2 players");
		}
		
		if(worlds.size < 1) {
			System.out.println("not enough worlds");
			throw new Exception("There must be at least 1 world");
		}	
			
		for(WorldInfo worldInfo : worlds) {			
			for(int i = 0; i < players.size - 1; i += 1) {
				for(int j = i + 1; j < players.size; j += 1) {
					// round 1
					AntWorld world1 = new AntWorld(worldInfo.getWorld(), players.get(i).getBrain(), players.get(j).getBrain(), worldInfo.getSeed());
					Match round1 = new Match(world1, players.get(i), players.get(j), "Round 1");
					
					// round 2
					//AntWorld world2 = new AntWorld(worldInfo.getWorld(), players.get(j), players.get(i));
					//Match round2 = new Match(world2, "Round 2");
					
					// add the rounds to the list of all matchQueue
					this.matchQueue.add(round1);
					//this.matchQueue.add(round2);
				}
			}			
		}
		
	}
	
	/**
	 * 
	 * @param player
	 */
	public void addPlayer(PlayerInfo player) {
		this.players.add(player);
	}
	
	/**
	 * 
	 * @param world
	 */
	public void addWorld(WorldInfo world) {
		this.worlds.add(world);
	}

	/**
	 * check to see if there is another match in the queue
	 * 
	 * @return true if there is another match, false otherwise
	 */
	public boolean hasNext() {
		return this.matchIndex < (this.matchQueue.size);
	}

	/**
	 * the next match if there is one. use hasNext to check
	 * 
	 * @return the next match if one exists
	 */
	public Match next() {
		return this.matchQueue.get(this.matchIndex++);
	}

	/**
	 * 
	 */
	public void reset() {
		this.players = new Array<PlayerInfo>();
		this.worlds = new Array<WorldInfo>();
		this.matchQueue = new Array<Match>();
		this.matchIndex = 0;
	}
	
}