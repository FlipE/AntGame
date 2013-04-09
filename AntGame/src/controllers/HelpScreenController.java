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
public class HelpScreenController extends InputListener {

	private Actor actor;
	private String actorName;
	private AntGame game;
		
	/**
	 * @param game
	 */
	public HelpScreenController(AntGame game) {
		super();
		this.game = game;
	}

	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {		
		return true;
	}

	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		
		actor = event.getTarget();
		actorName = actor.getName();
		
		if(actorName.equals(Config.BACK_TO_MENU)) {
			game.setMainMenuScreen();
		}	
	}	
}