package tests;

import models.AntWorld;
import ai.AntBrain;
import cells.Cell;
import exceptions.InvalidWorldException;
import exceptions.SyntacticallyInvalidInputException;
import fileio.AntBrainLoader;
import fileio.SimpleWorldLoader;

/**
 * Test.java
 *
 * @author 	Chris B
 * @date	10 Apr 2013
 * @version	1.0
 */
public class ExampleTest {

	public static void main(String[] args) {
		try {
			AntBrain redBrain = AntBrainLoader.load("tinyBrain.brain");
			AntBrain blackBrain = AntBrainLoader.load("tinyBrain.brain");
		
			Cell[][] cells = SimpleWorldLoader.load("tiny.world");
			AntWorld world = new AntWorld(cells, redBrain, blackBrain);
			
			
			world.update();
			world.printWorld();
			//world.printWorld();
		}
		catch (InvalidWorldException e) {
			e.printStackTrace();
		}
		catch (SyntacticallyInvalidInputException e) {
			e.printStackTrace();
		}		
	}
}
