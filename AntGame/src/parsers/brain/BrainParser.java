package parsers.brain;

import java.util.List;

import ai.AntBrain;
import ai.commands.AntCommand;
import ai.commands.DropFood;
import ai.commands.Flip;
import ai.commands.Mark;
import ai.commands.Move;
import ai.commands.PickUpFood;
import ai.commands.Sense;
import ai.commands.SenseMark;
import ai.commands.Turn;
import ai.commands.Unmark;
import antgame.Config;
import exceptions.InvalidInputException;

public class BrainParser {

	public AntBrain check(List<Token> list) throws InvalidInputException {
		AntBrain brain = new AntBrain();
		int i = 0;
		while (i < list.size()) {
			Token token = list.get(i);
			String tokenType = token.toString().toLowerCase();			
			
			if (tokenType.equals("drop")) {
				Token nextState = list.get(++i);
				brain.addCommand(this.dropFood(nextState));
			}

			else if (tokenType.equals("turn")) {
				Token direction = list.get(++i);
				Token nextState = list.get(++i);
				brain.addCommand(this.turn(direction, nextState));
			}
			
			else if (tokenType.equals("move")) {
				Token successState = list.get(++i);
				Token failState = list.get(++i);
				brain.addCommand(this.move(successState, failState));
			}
			
			else if (tokenType.equals("pickup")) {
				Token successState = list.get(++i);
				Token failState = list.get(++i);
				brain.addCommand(this.pickup(successState, failState));
			}
			
			else if (tokenType.equals("flip")) {
				Token num = list.get(++i);
				Token zeroState = list.get(++i);
				Token otherState = list.get(++i);
				brain.addCommand(this.flip(num, zeroState, otherState));
			}
			
			else if (tokenType.equals("mark")) {
				Token type = list.get(++i);
				Token nextState = list.get(++i);
				brain.addCommand(this.mark(type, nextState));
			}
			
			else if (tokenType.equals("unmark")) {
				Token type = list.get(++i);
				Token nextState = list.get(++i);
				brain.addCommand(this.unmark(type, nextState));
			}
			
			else if (tokenType.equals("sense")) {
				Token direction = list.get(++i);
				Token trueState = list.get(++i);
				Token falseState = list.get(++i);
				Token condition = list.get(++i);
				
				// this deviates from the usual format because sense mark has an extra argument 'type'
				if(condition.toString().toLowerCase().equals("marker")) {
					Token type = list.get(++i);
					brain.addCommand(this.senseMark(direction, trueState, falseState, type));
				}
				else {
					brain.addCommand(this.sense(direction, trueState, falseState, condition));
				}
			}
			
			else if(tokenType.equals(";")) {
				// do nothing with comments
			}
			
			else {
				// TODO want this to throw error but also want to handle comments need to change lexer slighly
				//throw new InvalidInputException("unrecognised input: " + tokenType);
			}
			
			i++;
		}
		return brain;
	}
	
	/**
	 * Given tokens representing a direction, true state, false state and mark type, 
	 * returns a {@link ai.commands.SenseMark} ant command instance.
	 * 
	 * @param direction a token representing a direction
	 * @param trueState a token representing the transition if the condition holds
	 * @param falseState a token representing the transition if the condition does not hold
	 * @param type the type of mark to look for
	 * @return
	 * @throws InvalidInputException
	 */
	private AntCommand senseMark(Token direction, Token trueState, Token falseState, Token type) throws InvalidInputException {
		return new SenseMark(this.parseSenseDirection(direction), this.parseState(trueState), this.parseState(falseState), this.parseMarkType(type));
	}
	
	/**
	 * Given tokens representing a direction, true state, false state and condition, 
	 * returns a {@link ai.commands.Sense} ant command instance.
	 * 
	 * @param direction a token representing a direction
	 * @param trueState a token representing the transition if the condition holds
	 * @param falseState a token representing the transition if the condition does not hold
	 * @param condition the condition to sense for
	 * @return
	 * @throws InvalidInputException
	 */
	private AntCommand sense(Token direction, Token trueState, Token falseState, Token condition) throws InvalidInputException {
		return new Sense(this.parseSenseDirection(direction), this.parseState(trueState), this.parseState(falseState), this.parseCondition(condition));
	}

	/**
	 * Given tokens representing a mark type and a next state, returns a {@link ai.commands.Unmark}
	 * ant command instance.
	 * 
	 * @param type a token representing a mark type
	 * @param nextState a token representing the next state
	 * @return a new Unmark instance
	 * @throws InvalidInputException
	 */
	private AntCommand unmark(Token type, Token nextState) throws InvalidInputException {
		return new Unmark(this.parseMarkType(type), this.parseState(nextState));
	}
	
	/**
	 * Given tokens representing a mark type and a next state, returns a {@link ai.commands.Mark}
	 * ant command instance.
	 * 
	 * @param type a token representing a mark type
	 * @param nextState a token representing the next state
	 * @return a new Mark instance
	 * @throws InvalidInputException
	 */
	private AntCommand mark(Token type, Token nextState) throws InvalidInputException {
		return new Mark(this.parseMarkType(type), this.parseState(nextState));
	}

	/**
	 * Given tokens representing a number, a state for if the  state and fail state, returns a {@link ai.commands.PickUpFood}
	 * ant command instance.
	 * 
	 * @param successState a token representing the transition on success
	 * @param failState a token representing the transition on failure
	 * @return a new PickUpFood instance
	 * @throws InvalidInputException
	 */
	private AntCommand flip(Token num, Token zeroState, Token otherState) throws InvalidInputException {
		return new Flip(this.parseNum(num), this.parseState(zeroState), this.parseState(otherState));
	}

	/**
	 * Given tokens representing a success state and fail state, returns a {@link ai.commands.PickUpFood}
	 * ant command instance.
	 * 
	 * @param successState a token representing the transition on success
	 * @param failState a token representing the transition on failure
	 * @return a new PickUpFood instance
	 * @throws InvalidInputException
	 */
	private AntCommand pickup(Token successState, Token failState) throws InvalidInputException {
		return new PickUpFood(this.parseState(successState), this.parseState(failState));
	}
	
	/**
	 * Given tokens representing a success state and fail state, returns a {@link ai.commands.Move}
	 * ant command instance.
	 * 
	 * @param successState a token representing the transition on success
	 * @param failState a token representing the transition on failure
	 * @return a new Move instance
	 * @throws InvalidInputException
	 */
	private AntCommand move(Token successState, Token failState) throws InvalidInputException {
		return new Move(this.parseState(successState), this.parseState(failState));
	}

	/**
	 * Given tokens representing a direction and next state, returns a {@link ai.commands.Turn}
	 * ant command instance.
	 * 
	 * @param direction
	 * @param nextState
	 * @return
	 * @throws InvalidInputException
	 */
	private AntCommand turn(Token direction, Token nextState) throws InvalidInputException {
		return new Turn(this.parseTurnDirection(direction), this.parseState(nextState));
	}

	/**
	 * Given a token representing the next state argument returns a {@link ai.commands.DropFood}
	 * ant command instance.
	 * 
	 * @param nextState the argument for the DropFood ant command represents the next state the ant should transition to
	 * @return a new DropFood instance
	 * @throws InvalidInputException
	 */
	private AntCommand dropFood(Token nextState) throws InvalidInputException {
		return new DropFood(parseState(nextState));	
	}

	/**
	 * Parse a token to make sure it contains a valid state. A valid state
	 * is a number 0 or emore and less than 10,000.
	 * 
	 * @param s token representing a state
	 * @return	an integer representation of the state
	 * @throws InvalidInputException
	 */
	private int parseState(Token s) throws InvalidInputException {
		try {
			int state = Integer.parseInt(s.toString());
			if (state >= 0 && state <= 9999) {
				return state;
			}
			else {
				throw new InvalidInputException("The next state must be 0 or more and less than 10,000. " + state + " is invalid.");
			}
		}
		catch(NumberFormatException e) {
			throw new InvalidInputException("The next state must be a number 0 or more and less than 10,000.");
		}
	}
	
	/**
	 * Given a token parse it to retrieve an int 0-5
	 * 
	 * @param t token representing a mark type
	 * @return the int representation of the mark
	 * @throws InvalidInputException
	 */
	private int parseMarkType(Token t) throws InvalidInputException {
		try {
			int state = Integer.parseInt(t.toString());
			if (state >= 0 && state <= 5) {
				return state;
			}
			else {
				throw new InvalidInputException("The mark type must be 0 or more and less than or equal to 5. " + state + " is invalid.");
			}
		}
		catch(NumberFormatException e) {
			throw new InvalidInputException("The mark type must be 0 or more and less than or equal to 5.");
		}
	}
	
	/**
	 * Given a token representing a number returns an integer.
	 * 
	 * @param number token representing a number
	 * @return a number
	 * @throws InvalidInputException
	 */
	private int parseNum(Token number) throws InvalidInputException {
		try {
			int num = Integer.parseInt(number.toString());
			return num;
		}
		catch(NumberFormatException e) {
			throw new InvalidInputException("Must be a number.");
		}
	}
	
	/**
	 * Given a token representing a direction returns the number representing that
	 * direction; {@link left#antgame.Config} or {@link right#antgame.Config}
	 * 
	 * @param direction
	 * @return the number representation of the direction
	 * @throws InvalidInputException
	 */
	private int parseTurnDirection(Token direction) throws InvalidInputException {
		String lr = direction.toString().toLowerCase();

		if (lr.equals("left")) {
			return Config.TURN_LEFT;
		}
		else if(lr.equals("right")) {
			return Config.TURN_RIGHT;
		}
		else {
			throw new InvalidInputException("Direction must be left or right. " + lr + " is invalid.");
		}
	}
	
	/**
	 * Given a token representing a direction returns the number representing that
	 * direction:
	 * <ul>
	 * 	 <li>{@link here#antgame.Config.SENSE_HERE}</li>
	 *   <li>{@link ahead#antgame.Config.SENSE_AHEAD}</li>
	 *   <li>{@link leftahead#antgame.Config.SENSE_LEFTAHEAD}</li>
	 *   <li>{@link rightahead#antgame.Config.SENSE_RIGHTAHEAD}</li>
	 * </ul>
	 * 
	 * @param direction a token representing a direction to sense
	 * @return the number representation of the direction
	 * @throws InvalidInputException
	 */
	private int parseSenseDirection(Token direction) throws InvalidInputException {
		String dir = direction.toString().toLowerCase();
		if (dir.equals("here")) {
			return Config.SENSE_HERE;
		}
		else if(dir.equals("ahead")) {
			return Config.SENSE_AHEAD;
		}
		else if(dir.equals("leftahead")) {
			return Config.SENSE_LEFTAHEAD;
		}
		else if(dir.equals("rightahead")) {
			return Config.SENSE_RIGHTAHEAD;
		}
		else {
			throw new InvalidInputException("Direction must be here, ahead, leftahead or rightahead. " + dir + " is invalid.");
		}
	}
	
	/**
	 * Given a token representing a condition to sense for, returns the number representing that
	 * condition:
	 * <ul>
	 * 	 <li>{@link friend#antgame.Config.SENSE_FRIEND}</li>
	 *   <li>{@link foe#antgame.Config.SENSE_FOE}</li>
	 *   <li>{@link friendwithfood#antgame.Config.SENSE_FRIENDWITHFOOD}</li>
	 *   <li>{@link foewithfood#antgame.Config.SENSE_FOEWITHFOOD}</li>
	 *   <li>{@link food#antgame.Config.SENSE_FOOD}</li>
	 *   <li>{@link rock#antgame.Config.SENSE_ROCK}</li>
	 *   <li>{@link foemarker#antgame.Config.SENSE_FOEMARKER}</li>
	 *   <li>{@link foehome#antgame.Config.SENSE_FOEHOME}</li>
	 *   <li>{@link home#antgame.Config.SENSE_HOME}</li>
	 * </ul>
	 * 
	 * @param condition
	 * @return
	 * @throws InvalidInputException
	 */
	private int parseCondition(Token condition) throws InvalidInputException {
		String c = condition.toString().toLowerCase();
		if (c.equals("friend")) {
			return Config.SENSE_FRIEND;
		}
		else if(c.equals("foe")) {
			return Config.SENSE_FOE;
		}
		else if(c.equals("friendwithfood")) {
			return Config.SENSE_FRIENDWITHFOOD;
		}
		else if(c.equals("foewithfood")) {
			return Config.SENSE_FOEWITHFOOD;
		}
		else if(c.equals("food")) {
			return Config.SENSE_FOOD;
		}
		else if(c.equals("rock")) {
			return Config.SENSE_ROCK;
		}
		else if(c.equals("foemarker")) {
			return Config.SENSE_FOEMARKER;
		}
		else if(c.equals("foehome")) {
			return Config.SENSE_FOEHOME;
		}
		else if(c.equals("home")) {
			return Config.SENSE_HOME;
		}
		else {
			throw new InvalidInputException("Unknown Condition: " + c);
		}
	}
}