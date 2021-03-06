/**
 * 
 */
package fileio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import cells.BlackAntHill;
import cells.Cell;
import cells.ClearCell;
import cells.RedAntHill;
import cells.RockyCell;
import exceptions.InvalidWorldException;

/**
 * loadWorld.java
 *
 * @author 	Chris B
 * @date	23 Mar 2013
 * @version	1.0
 */
public class SimpleWorldLoader {

	private SimpleWorldLoader() {	
	}
	
	public static Cell[][] load(String filepath) throws InvalidWorldException {
		
		//BufferedReader reader = new BufferedReader(new FileReader(fileHandle));
		//String fileContent = fileHandle.readString();
		try {
			File file = new File(filepath);
			String fileContent = new String(Files.readAllBytes(file.toPath()));
			String[] lines = fileContent.split("\r\n|\n");	//not only \n newline used
			String line = null;
			
			// the first line is the width
			//line = reader.readLine();
			line = lines[0];
			int width = Integer.parseInt(line.trim());
			
			// second line is the height
			//line = reader.readLine();
			line = lines[1];
			int height = Integer.parseInt(line.trim());
			
			// create the cells array which hold the world representation
			Cell[][] cells = new Cell[width][height];
			
			//for (int y = 0;(line = reader.readLine()) != null; y += 1) {
			for (int y = 0; y < height; y += 1) {
				
				line = lines[y + 2];
				
				if(line.startsWith(" ")){		//accounts for indents
					line = line.substring(1);
				}
								
				// split the string on white space char
				String[] parts = line.split("\\s");
				
				for(int x = 0; x < parts.length; x += 1) {
					
					Cell cell = null;
					
					if(parts[x].equals(".")) {
						cell = new ClearCell(x, y, 0);
					}
					else if(parts[x].equals("#")) {
						cell = new RockyCell(x, y);
					}
					else if(parts[x].equals("+")){
						cell = new RedAntHill(x, y, 0);
					}
					else if(parts[x].equals("-")){
						cell = new BlackAntHill(x, y, 0);
					}
					else if(parts[x].matches("\\d")) {
						int food = Integer.parseInt(parts[x]);
						cell = new ClearCell(x, y, food);
					}
					else {
						// TODO log the error unknown cell type
						System.out.println("unknown cell type");
					}
					cells[x][y] = cell;					
				}
			}		
			
			//reader.close();
			/*
			//System.out.println("world "+cells[0][0].toString());
			for(int i = 0; i < cells.length; i++){
				for (int j = 0; j < cells[0].length; j++){
					System.out.println("cell ("+i+","+j+"):" + cells[i][j].toString());
				}
			}*/
			
			return cells;
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new InvalidWorldException("Problem loading world");
		}
		
	}
}