package tests;

import static org.junit.Assert.*;

import models.AntWorld;

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
	
	@Test
	public void Turn0Move() throws Exception {
		AntBrain redBrain = AntBrainLoader.load("singleCommandBrain/turnLeft0move.brain");
		AntBrain blackBrain = AntBrainLoader.load("singleCommandBrain/no.brain");
		Cell[][] cells = SimpleWorldLoader.load("working worlds/singleAnt.world");
		AntWorld world = new AntWorld(cells, redBrain, blackBrain);
		
		assertTrue(world.getWorld()[5][4].isOccupied());
		world.update();	//turn
//		world.update();	//move
//		world.update();
		System.out.println(world.printWorld());
		//assertTrue(world.getWorld()[6][4].isOccupied());
	}
}
