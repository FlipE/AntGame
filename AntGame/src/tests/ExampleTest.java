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
			
			int seed = 12345;
			
			AntBrain redBrain = AntBrainLoader.load("sample.brain");
			AntBrain blackBrain = AntBrainLoader.load("sample.brain");
		
			Cell[][] cells = SimpleWorldLoader.load("tiny1.world");
			AntWorld world = new AntWorld(cells, redBrain, blackBrain);
			
			StringBuffer log = new StringBuffer();
			
			log.append("random seed: " + seed + "\r\n");
			for (int i=0; i<1000; i++){
				log.append("\r\nAfter round "+i+"...\r\n");
				log.append(world.printWorld());
				world.update();
			}
			
			// save the log
			File f = new File ("ourLog.txt");
			BufferedWriter fw = Files.newBufferedWriter(f.toPath(), Charset.forName("UTF-8"), StandardOpenOption.CREATE);
			
			String content = log.toString();
			String[] lines = content.split("\r\n|\n|\r");
			
			for(String line : lines) {
				fw.write(line, 0, line.length());
				fw.newLine();
			}
			
			fw.close();
			
			// echo out the log
			System.out.print(content);
		}
		catch (InvalidWorldException e) {
			e.printStackTrace();
		}
		catch (SyntacticallyInvalidInputException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
