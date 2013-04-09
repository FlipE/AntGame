package tests;

import static org.junit.Assert.*;


import generator.world.WorldGenerator;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

import parsers.world.WorldParser;



public class ParserTesting {

	@Ignore	//takes 1 min to run
	public void testLotsofWorlds() throws Exception {
		for (int i = 0; i<100;i++){
			System.out.println("Test number "+ i);
			WorldGenerator wg = new WorldGenerator(14,11);
			File world = wg.getWorld();
			assertTrue(parsers.world.WorldParser.Parse(world)!= null);
		}
	}
	@Test (expected = Exception.class)//world generator can make wrong number of rocks if the same position is randomly chosen twice
	public void testBrokenWorld() throws Exception{
		File f = new File ("brokenWorlds/AntWorld.World"); 
		assertTrue(parsers.world.WorldParser.Parse(f)!= null);
	}
	@Ignore		//each time generate world is called the old AntWorld.world is overwritten
	public void testOverwriteWorld() throws Exception{
		WorldGenerator wg = new WorldGenerator(14,11);
		File world = wg.getWorld();
		assertTrue(parsers.world.WorldParser.Parse(world)!= null);
	}
	@Test (expected = Exception.class)
	public void testBrokenWorld1() throws Exception{
		File f = new File ("brokenWorlds/holeInAntNest.World"); 
		assertTrue(parsers.world.WorldParser.Parse(f)!= null);
	}
	@Test (expected = Exception.class)
	public void testBrokenWorld2() throws Exception{
		File f = new File ("brokenWorlds/holeInAntNest.World"); 
		assertTrue(parsers.world.WorldParser.Parse(f)!= null);
	}
	@Test //(expected = Exception.class)
	public void testBrokenWorld3() throws Exception{
		File f = new File ("brokenWorlds/touchingRocks.World"); 
		assertTrue(parsers.world.WorldParser.Parse(f)!= null);
	}
	@Test (expected = Exception.class)
	public void testBrokenWorld4() throws Exception{
		File f = new File ("brokenWorlds/unknownChar.World"); 
		assertTrue(parsers.world.WorldParser.Parse(f)!= null);
	}
}
