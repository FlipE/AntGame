package testing;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import generator.WorldGenerator;

import org.junit.Test;

public class WorldTesting {

	@Test
	public void test() {
		WorldGenerator wg = new WorldGenerator(11,20);
		File world = wg.getWorld();
	}
	

}
