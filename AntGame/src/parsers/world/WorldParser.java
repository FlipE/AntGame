package parsers.world;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cells.*;


public class WorldParser {
	public static char[][] world;

	public static Cell[][] Parse(File worldFile) throws Exception{
		
		checkSyntax(worldFile);
		
		getWorldArray(worldFile);	
		int foodCount = 0;
		int rockCount = 0;
		int redHill = 0;
		int blackHill = 0;
		for (int i=1; i<149; i++){
			for (int j= 1; j<149; j++){
				if (world[i][j] == '5'){	
					if(!checkFoodBlob(i,j)){
						throw new Exception("invalid food shape");
					}
					foodCount++;
				}
				if (world[i][j] == '#'){
					rockCount++;
				}
				if (world[i][j] == '+'){
					redHill++;
					if(!checkAnthill(i,j,'+')){
						throw new Exception("invalid ant hill shape");
					}
				}
				if (world[i][j] == '-'){
					blackHill++;
					if(!checkAnthill(i,j,'-')){
						throw new Exception("invalid ant hill shape");
					}
				}
			}
		}
		if (foodCount != 11){
			throw new Exception("wrong number of food blobs: "+foodCount);
		}
		else if (rockCount != 14){
			throw new Exception("wrong number of rocks: "+rockCount);
		}
		else if (redHill != 1){
			throw new Exception("wrong number of redHills: "+redHill);
		}
		else if (blackHill != 1){
			throw new Exception("wrong number of blackHill: "+blackHill);
		}
		
		getWorldArray(worldFile); 	//reset world 
		return getCells(world);
	}
	/**
	 * Checks the correct layout of food blobs i.e: 5*5 cells
	 * of 5 units of food surrounded by empty space
	 * add functionality to detect overlapping objects
	 * @param row
	 * @param column
	 * @return false if badly formed
	 */
	public static boolean checkFoodBlob(int i, int j){
		for (int col=0; col<5; col++){
			for (int row=0; row<5; row++){				//check food is disposed in rectangle
				if (world[i+row][j+col] != '5'){
					return false;
				}
			}
		}
		for (int col=0; col<5; col++){
			for (int row=0; row<5; row++){				//remove food to avoid counting twice
				world[i+row][j+col] = ((char)'.');
			}
		}
		return true;
	}
	/**
	 * Checks the correct layout of ant hills  i.e:
	 * a hexagon with sides of length 7 and of cells type + or -
	 * if shape is valid the hill is removed to avoid recounting
	 * 
	 * @param row
	 * @param column
	 * @return	false if badly formed
	 */
	public static boolean checkAnthill(int i, int j, char antType){

		int lineLength = 6;
		int indent;

		if (i%2 != 0){										//first line of hexagon on even row
			for (int row = 0; row < 13; row++){
				indent = 0;									//indent is 0 for first 2 and last 2 rows
				if (row == 2 | row == 3){indent = -1;}
				else if (row == 4 | row == 5){indent = -2;}
				else if (row == 6){indent = -3;}
				else if (row == 7 | row == 8){indent = -2;}
				else if (row == 9 | row == 10){indent = -1;}

				if (row>6)	{lineLength--;}					//once at middle line length should be decremented
				else		{lineLength++;}
				
				for (int col = 0; col<lineLength; col++){
					
					if (antType == '.'){					//shape has been validated remove ant hill to avoid counting twice
						world[row+i][col+j+indent] = antType;
					}
					else if ( world[row+i][col+j+indent] != antType){
						return false; 						// wrong ant hill shape
					}
				}
			}
			if(antType == '.'){
				return true;								//ant hill conforms and has been erased
			}
			else
			return checkAnthill(i,j, '.'); 					//ant hill conforms erase ant hill
		}
		else{												//first line of hex is odd
			for (int row = 0; row < 13; row++){
				indent = 0;									//indent is 0 for first and last row
				if (row == 1 | row == 2){indent = -1;}
				else if (row == 3 | row == 4){indent = -2;}
				else if (row == 5 | row == 6 | row == 7){indent = -3;}
				else if (row == 8 | row == 9){indent = -2;}
				else if (row == 10 | row == 11){indent = -1;}

				if (row>6)	{lineLength--;}								//once at middle line length should be decremented
				else		{lineLength++;}
				
				for (int col = 0; col< lineLength; col++){		
					if (antType == '.'){								//shape has been validated remove ant hill to avoid counting twice
						world[row+i][col+j+indent] = antType;
					}
					else if ( world[row+i][col+j+indent] != antType){
						return false; 									// wrong ant hill shape
					}
				}
			}
			if(antType == '.'){
				return true;						//ant hill conforms and has been erased
			}
			else
			return checkAnthill(i,j, '.');			//erase ant hill
		}
	}
	/**
	 * print the array of chars debugging purposes
	 */
	public static void printWorld(){
		for (int i = 0; i<world.length;i++){
			System.out.print("\n");
			for (int j = 0; j<world.length; j++){
				System.out.print(world[i][j]);
			}
		}
	}
	
	/**
	 * converts the .world file into an array of characters
	 * @param worldFile
	 * @throws IOException
	 */
	public static void getWorldArray(File worldFile) throws IOException{
		world = new char[150][150];
		BufferedReader br = new BufferedReader(new FileReader(worldFile));
		br.readLine();
		br.readLine();					//ignore first 2 lines
		String line = br.readLine();
		int lineNum = 0;
		int pos;
		while (lineNum < 150){
			if (lineNum%2 == 0){
				pos = 0;
			}
			else{
				pos = 1;
			}
			for (int i = 0; i < 150; i++){
				world[lineNum][i] = line.charAt(pos);
				pos = pos+2;
			}
			line = br.readLine();
			lineNum++;
		}	
	}
	
	/**
	 * Check only valid characters are in the world file and check dimensions are correct
	 * @param worldFile
	 * @return
	 * @throws IOException
	 */
		public static void checkSyntax(File worldFile) throws Exception{
			boolean validWorld;
			BufferedReader br = new BufferedReader(new FileReader(worldFile));
			String s = br.readLine();
			String s1 = br.readLine();
			int width = Integer.parseInt(s);
			int height = Integer.parseInt(s1);			//get the height and width from first 2 lines
			if(height != width | width != 150){
				throw new Exception("height and width must be 150");
			}

			String bodyRegex ="( |#|\\s|5|\\.|\\+|-)";		//regular expressions for checking syntax
			String borderRegex = "( |#|\\s)";	

			Pattern borderPattern = Pattern.compile(borderRegex);			
			Pattern bodyPattern = Pattern.compile(bodyRegex);
			Matcher matchBorder;
			Matcher matchBody;

			String firstBorder = br.readLine();						//check first line of board
			
			
			for (int i = 0 ; i<firstBorder.length(); i++){
				matchBorder = borderPattern.matcher(firstBorder.substring(i, i+1));
				if (!matchBorder.matches()){ throw new Exception("unknown character in upper border");}
			}

			String bodyLine = br.readLine();
			int counter = 1;
			while (counter < (height-2)){			//check all the middle lines
				if (!(bodyLine.length() == 300 ||bodyLine.length() == 301)){ throw new Exception("wrong board length");}		
				for (int i = 0 ; i<firstBorder.length(); i++){
					matchBody = bodyPattern.matcher(bodyLine.substring(i, i+1));
					if (!matchBody.matches()){ throw new Exception("unknown character in upper border");}
				}
				bodyLine = br.readLine();
				counter++;
			}
		
		String lastBorder = br.readLine();
		if (lastBorder.length() != 301){ throw new Exception("wrong lower border length");}
		
		for (int i = 0 ; i<firstBorder.length(); i++){
			matchBorder = borderPattern.matcher(lastBorder.substring(i, i+1));
			if (!matchBorder.matches()){ throw new Exception("unknown character in lower border");}
		}
		
		if (br.readLine() != null){throw new Exception("extra board lines found");}
	}
	
	/**
	 * create cell objects to represent the world in the game.
	 * @throws Exception 
	 */
	public static Cell[][] getCells(char[][] world) throws Exception{
		Cell[][] finalWorld = new Cell[150][150];
		char cell;
		for (int i = 0; i<world.length; i++){
			for(int j = 0; j<world.length; j++){
				cell = world[i][j];
				if (cell == '.'){
					finalWorld[i][j] = new ClearCell(i,j,0);
				}
				else if (cell == '#'){
					finalWorld[i][j] = new RockyCell(i,j);
				}
				else if (cell == '+'){
					finalWorld[i][j] = new RedAntHill(i,j,0);
				}
				else if (cell == '-'){
					finalWorld[i][j] = new BlackAntHill(i,j,0);
				}
				else if (cell == '5'){
					finalWorld[i][j] = new ClearCell(i,j,5);
				}
				else{
					throw new Exception("unknown Cell");
				}
			}
		}
		return finalWorld;
	}
}

