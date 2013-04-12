package tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

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
			File f = new File ("ourLog.txt");
			BufferedWriter fw = Files.newBufferedWriter(f.toPath(), Charset.forName("UTF-8"), StandardOpenOption.CREATE);
			
			
			AntBrain redBrain = AntBrainLoader.load("sample.brain");
			AntBrain blackBrain = AntBrainLoader.load("sample.brain");
		
			Cell[][] cells = SimpleWorldLoader.load("tiny1.world");
			AntWorld world = new AntWorld(cells, redBrain, blackBrain);
			
			StringBuffer buff = new StringBuffer(0);
			
			buff.append("random seed: 12345\r\n");
			for (int i=0; i<10; i++){
				buff.append("\r\nAfter round "+i+"...\r\n");
				buff.append(world.printWorld());
				world.update();
			}
			fw.write(buff.toString(), 0, buff.toString().length());
			System.out.print(buff);
		}
		catch (InvalidWorldException e) {
			e.printStackTrace();
		}
		catch (SyntacticallyInvalidInputException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
