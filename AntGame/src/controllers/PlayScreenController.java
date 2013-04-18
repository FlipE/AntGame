/**
 * 
 */
package controllers;

import screens.PlayScreen;
import antgame.AntGame;

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
public class PlayScreenController extends InputListener {

	private Actor actor;
	private String actorName;
	private PlayScreen screen;
	private AntGame game;
		
	/**
	 * @param game
	 */
	public PlayScreenController(AntGame game, PlayScreen screen) {
		super();
		this.game = game;
		this.screen = screen;
	}

	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {		
		return true;
	}

	public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		
		actor = event.getTarget();
		actorName = actor.getName();
		
		// get ready button
		if(actorName.equals("play")) {
			screen.playState();
		}
		else if(actorName.equals("pause")) {
			screen.pauseState();
		}
		else if(actorName.equals("continue")) {
			screen.nextMatch();
		}
		else if(actorName.equals("menu")) {
			game.setMainMenuScreen();
		}
	}	
}