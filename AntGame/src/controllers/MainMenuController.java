/**
 * 
 */
package controllers;

import antgame.AntGame;
import antgame.Config;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * MainMenuController.java
 *
 * @author 	Chris B
 * @date	26 Mar 2013
 * @version	1.0
 */
public class MainMenuController extends InputListener {

	Actor actor;
	String actorName;
	AntGame game;
		
	/**
	 * @param game
	 */
	public MainMenuController(AntGame game) {
		super();
		this.game = game;
	}

	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {		
		return true;
	}

	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		
		actor = event.getTarget();
		actorName = actor.getName();
		
		if(actorName.equals(Config.MENU_ITEM_TWO_PLAYER_GAME)) {
			game.setTwoPlayerMenuScreen();
		}
		else if(actorName.equals(Config.MENU_ITEM_TOURNAMENT_GAME)) {
			//game.setTournamentMenuScreen();
			game.setTwoPlayerMenuScreen();
		}
		else if(actorName.equals(Config.MENU_ITEM_HELP)) {
			game.setHelpScreen();
		}
		else if(actorName.equals(Config.MENU_ITEM_EXIT)) {
			game.dispose();
		}
		else {
			// where on earth did you click?
		}		
	}	
}