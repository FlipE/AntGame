package antgame;

public class Config {

	public static final int RED_ANT = 0;
	public static final int BLACK_ANT = 1;

	public static final int NUM_ROUNDS = 300000;
	public static final int MOVEMENT_RESTING_PERIOD = 14;
	
	public static final String MENU_ITEM_TWO_PLAYER_GAME = "two player game";
	public static final String MENU_ITEM_TOURNAMENT_GAME = "tournament game";
	public static final String MENU_ITEM_HELP = "help";
	public static final String MENU_ITEM_EXIT = "exit";
	
	public static final String BACK_TO_MENU = "back";
	
	public static final int TURN_LEFT = 0;
	public static final int TURN_RIGHT = 1;
	
	public static final int SENSE_HERE = 0;
	public static final int SENSE_AHEAD = 1;
	public static final int SENSE_LEFTAHEAD = 2;
	public static final int SENSE_RIGHTAHEAD = 3;
	
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
}