package testing;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;


public class ParserTesting {

	@Test
	public void test() throws Exception {
		File f = new File("AntWorld.world");
		assertTrue(parsers.world.WorldParser.Parse(f)!= null);
	}
	
	
//	@Test
//	public void testAllsamples() {
//		for (int i = 0; i<10; i++){
//			File f = new File("worlds/sample"+i+".world");
//			assertTrue(WorldParser.Parse(f));
//		}
//	}
}
