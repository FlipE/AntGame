package tests;

import models.Ant;
import models.AntWorld;
import models.PlayerInfo;
import models.WorldInfo;

/**
 * Test.java
 *
 * @author 	Chris B
 * @date	10 Apr 2013
 * @version	1.0
 */
public class ExampleTest {

	public static void main(String[] args) {
		
		PlayerInfo redPlayer = new PlayerInfo("Red Team", 1);
		PlayerInfo blackPlayer = new PlayerInfo("Black Team", 2);
		
		redPlayer.loadBrain("tinyBrain.ant");
		blackPlayer.loadBrain("tinyBrain.ant");
		
		WorldInfo worldInfo = new WorldInfo();
		worldInfo.loadWorld("tiny.world");
		
		AntWorld world = new AntWorld(worldInfo.getWorld(), redPlayer, blackPlayer);
		
		
		
	}
	
}
