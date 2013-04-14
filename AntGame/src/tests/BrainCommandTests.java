package tests;

import static org.junit.Assert.*;

import models.AntWorld;

import org.junit.Ignore;
import org.junit.Test;

import util.Position;

import ai.AntBrain;
import cells.Cell;
import cells.ClearCell;
import exceptions.SyntacticallyInvalidInputException;
import fileio.AntBrainLoader;
import fileio.SimpleWorldLoader;

public class BrainCommandTests {

	@Test
	public void TurnRight() throws Exception {
		AntBrain redBrain = AntBrainLoader.load("singleCommandBrain/turnRight.brain");
		AntBrain blackBrain = AntBrainLoader.load("singleCommandBrain/no.brain");
		Cell[][] cells = SimpleWorldLoader.load("working worlds/singleAnt.world");
		AntWorld world = new AntWorld(cells, redBrain, blackBrain);
		
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 0);
		world.update();
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 1);
		world.update();
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 2);
		world.update();
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 3);
		world.update();
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 4);
		world.update();
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 5);
	}
	
	@Test
	public void TurnLeft() throws Exception {
		AntBrain redBrain = AntBrainLoader.load("singleCommandBrain/turnLeft.brain");
		AntBrain blackBrain = AntBrainLoader.load("singleCommandBrain/no.brain");
		Cell[][] cells = SimpleWorldLoader.load("working worlds/singleAnt.world");
		AntWorld world = new AntWorld(cells, redBrain, blackBrain);
		
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 0);
		world.update();
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 5);
		world.update();
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 4);
		world.update();
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 3);
		world.update();
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 2);
		world.update();
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 1);
	}
	
	@Test
	public void TurnLeftNRight() throws Exception {
		AntBrain redBrain = AntBrainLoader.load("singleCommandBrain/turnLeftRight.brain");
		AntBrain blackBrain = AntBrainLoader.load("singleCommandBrain/no.brain");
		Cell[][] cells = SimpleWorldLoader.load("working worlds/singleAnt.world");
		AntWorld world = new AntWorld(cells, redBrain, blackBrain);
		
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 0);
		world.update();	//turn Left
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 5);
		world.update();	//turn right
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 0);
	}
	
	@Test
	public void Move() throws Exception {
		AntBrain redBrain = AntBrainLoader.load("singleCommandBrain/move.brain");
		AntBrain blackBrain = AntBrainLoader.load("singleCommandBrain/no.brain");
		Cell[][] cells = SimpleWorldLoader.load("working worlds/singleAnt.world");
		AntWorld world = new AntWorld(cells, redBrain, blackBrain);
		
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getPosition().getX() == 5);
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getPosition().getY() == 4);
		world.update();
		assertTrue(((ClearCell)world.getWorld()[6][4]).getAnt().getPosition().getX() == 6);
		assertTrue(((ClearCell)world.getWorld()[6][4]).getAnt().getPosition().getY() == 4);
	}
	
	@Test	//checks resting decreases on each turn until 0
	public void MoveNRest() throws Exception {
		AntBrain redBrain = AntBrainLoader.load("singleCommandBrain/move.brain");
		AntBrain blackBrain = AntBrainLoader.load("singleCommandBrain/no.brain");
		Cell[][] cells = SimpleWorldLoader.load("working worlds/singleAnt.world");
		AntWorld world = new AntWorld(cells, redBrain, blackBrain);
		
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getPosition().getX() == 5);
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getPosition().getY() == 4);
		world.update();		//move
		assertTrue(((ClearCell)world.getWorld()[6][4]).getAnt().getPosition().getX() == 6);
		assertTrue(((ClearCell)world.getWorld()[6][4]).getAnt().getPosition().getY() == 4);
		for (int i = 13; i > 0; i--){
			assertTrue(((ClearCell)world.getWorld()[6][4]).getAnt().getResting() == i);
			world.update();	//rest
		}
		world.update();		// on this round move command is executed again
		assertTrue(((ClearCell)world.getWorld()[7][4]).getAnt().getPosition().getX() == 7);
		assertTrue(((ClearCell)world.getWorld()[7][4]).getAnt().getPosition().getY() == 4);
	}
	
	@Test
	public void TurnLeft0move() throws Exception {
		AntBrain redBrain = AntBrainLoader.load("singleCommandBrain/turnLeft0move.brain");
		AntBrain blackBrain = AntBrainLoader.load("singleCommandBrain/no.brain");
		Cell[][] cells = SimpleWorldLoader.load("working worlds/singleAnt.world");
		AntWorld world = new AntWorld(cells, redBrain, blackBrain);
		
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 0);
		world.update();	//turn Left
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 5);
		world.update();	//move
		assertTrue(world.getWorld()[5][3].isOccupied());	//successfully moved
		assertTrue(((ClearCell)world.getWorld()[5][3]).getAnt().getDirection() == 5);	//direction has not changed
	}
	
	@Test
	public void TurnLeft1move() throws Exception {
		AntBrain redBrain = AntBrainLoader.load("singleCommandBrain/turnLeft1move.brain");
		AntBrain blackBrain = AntBrainLoader.load("singleCommandBrain/no.brain");
		Cell[][] cells = SimpleWorldLoader.load("working worlds/singleAnt.world");
		AntWorld world = new AntWorld(cells, redBrain, blackBrain);
		
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 0);
		world.update();	//turn Left
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 4);
		world.update();	//move
		assertTrue(world.getWorld()[4][3].isOccupied());	//successfully moved
		assertTrue(((ClearCell)world.getWorld()[4][3]).getAnt().getDirection() == 4);	//direction has not changed
	}
	
	@Test
	public void TurnLeft2move() throws Exception {
		AntBrain redBrain = AntBrainLoader.load("singleCommandBrain/turnLeft2move.brain");
		AntBrain blackBrain = AntBrainLoader.load("singleCommandBrain/no.brain");
		Cell[][] cells = SimpleWorldLoader.load("working worlds/singleAnt.world");
		AntWorld world = new AntWorld(cells, redBrain, blackBrain);
		
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 0);
		world.update();	//turn Left
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 3);
		world.update();	//move
		assertTrue(world.getWorld()[4][4].isOccupied());	//successfully moved
		assertTrue(((ClearCell)world.getWorld()[4][4]).getAnt().getDirection() == 3);	//direction has not changed
	}
	
	@Test
	public void TurnLeft3move() throws Exception {
		AntBrain redBrain = AntBrainLoader.load("singleCommandBrain/turnLeft3move.brain");
		AntBrain blackBrain = AntBrainLoader.load("singleCommandBrain/no.brain");
		Cell[][] cells = SimpleWorldLoader.load("working worlds/singleAnt.world");
		AntWorld world = new AntWorld(cells, redBrain, blackBrain);
		
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 0);
		world.update();	//turn Left
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 2);
		world.update();	//move
		assertTrue(world.getWorld()[4][5].isOccupied());	//successfully moved
		assertTrue(((ClearCell)world.getWorld()[4][5]).getAnt().getDirection() == 2);	//direction has not changed
	}
	
	@Test
	public void TurnLeft4move() throws Exception {
		AntBrain redBrain = AntBrainLoader.load("singleCommandBrain/turnLeft3move.brain");
		AntBrain blackBrain = AntBrainLoader.load("singleCommandBrain/no.brain");
		Cell[][] cells = SimpleWorldLoader.load("working worlds/singleAnt.world");
		AntWorld world = new AntWorld(cells, redBrain, blackBrain);
		
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 0);
		world.update();	//turn Left
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 1);
		world.update();	//move
		assertTrue(world.getWorld()[5][5].isOccupied());	//successfully moved
		assertTrue(((ClearCell)world.getWorld()[5][5]).getAnt().getDirection() == 1);	//direction has not changed
	}
	
	@Test
	public void TurnLeft5move() throws Exception {
		AntBrain redBrain = AntBrainLoader.load("singleCommandBrain/turnLeft3move.brain");
		AntBrain blackBrain = AntBrainLoader.load("singleCommandBrain/no.brain");
		Cell[][] cells = SimpleWorldLoader.load("working worlds/singleAnt.world");
		AntWorld world = new AntWorld(cells, redBrain, blackBrain);
		
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 0);
		world.update();	//turn Left
		assertTrue(((ClearCell)world.getWorld()[5][4]).getAnt().getDirection() == 0);	//full 360
		world.update();	//move
		assertTrue(world.getWorld()[6][4].isOccupied());	//successfully moved
		assertTrue(((ClearCell)world.getWorld()[6][4]).getAnt().getDirection() == 0);	//direction has not changed
	}
	
	@Ignore
	public void sense() throws Exception {
		AntBrain redBrain = AntBrainLoader.load("singleCommandBrain/turnLeft0move.brain");
		AntBrain blackBrain = AntBrainLoader.load("singleCommandBrain/no.brain");
		Cell[][] cells = SimpleWorldLoader.load("working worlds/singleAnt.world");
		AntWorld world = new AntWorld(cells, redBrain, blackBrain);
	}
}
