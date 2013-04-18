package antgame;

public class Config {

	// the colors ants can be
	public static final int RED_ANT = 0;
	public static final int BLACK_ANT = 1;

	// the number of rounds per match
	public static final int NUM_ROUNDS_PER_MATCH = 3000;
	
	// number of rounds an ant rests after moving
	public static final int MOVEMENT_RESTING_PERIOD = 14;
	
	// names of the menu links
	public static final String MENU_ITEM_TWO_PLAYER_GAME = "two player game";
	public static final String MENU_ITEM_TOURNAMENT_GAME = "tournament game";
	public static final String MENU_ITEM_HELP = "help";
	public static final String MENU_ITEM_EXIT = "exit";
	public static final String BACK_TO_MENU = "back";
	public static final String PLAY = "play";
	
	// directions an ant can turn
	public static final int TURN_LEFT = 0;
	public static final int TURN_RIGHT = 1;
	
	// places an ant can sense
	public static final int SENSE_HERE = 0;
	public static final int SENSE_AHEAD = 1;
	public static final int SENSE_LEFTAHEAD = 2;
	public static final int SENSE_RIGHTAHEAD = 3;
	
	// things an ant can sense for
	public static final int SENSE_FRIEND = 0;
	public static final int SENSE_FOE = 1;
	public static final int SENSE_FRIENDWITHFOOD = 2;
	public static final int SENSE_FOEWITHFOOD = 3;
	public static final int SENSE_FOOD = 4;
	public static final int SENSE_ROCK = 5;
	public static final int SENSE_FOEMARKER = 6;
	public static final int SENSE_HOME = 7;
	public static final int SENSE_FOEHOME = 8;
	// note, sense marker has been split out into a seperate function
	
	// file paths
	public static String currentBrainPath = System.getProperty("user.dir");
	public static String currentWorldPath = System.getProperty("user.dir");	
	
	// Brain file description and extension
	public static final String BRAIN_FILE_DESCRIPTION = "Ant Brain Files";
	public static final String BRAIN_FILE_EXTENSION = "brain";
	
	// World file description and extension
	public static final String WORLD_FILE_DESCRIPTION = "Ant World Files";
	public static final String WORLD_FILE_EXTENSION = "world";
	
	// the maximum length of the player's name
	public static final int MAX_NAME_LENGTH = 24;
	
	// the value which represents a match in which both teams score the same
	public static final int DRAW = -1;
	
	// the number of food an ant becomes when it is killed
	public static final int DEAD_ANT_FOOD_VALUE = 3;
	
}