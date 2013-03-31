/**
 * 
 */
package screens;

import models.AntWorld;
import views.AntWorldTextView;
import ai.AntBrain;
import antgame.AntGame;
import cells.Cell;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

import exceptions.InvalidInputException;
import fileio.AntBrainLoader;
import fileio.SimpleWorldLoader;

/**
 * PlayScreen.java
 *
 * @author 	Chris B
 * @date	25 Mar 2013
 * @version	1.0
 * @param <T>
 */
public class PlayScreen extends AbstractScreen {

	private AntWorld world;
	private AntWorldTextView worldView;
	
	/**
	 * @param game
	 */
	public PlayScreen(AntGame game) {
		super(game);
		this.initialise();
	}

	private void initialise() {
		
		// load assets
		Cell[][] cells = SimpleWorldLoader.load(Gdx.files.internal("data/workingworld.world"));
		// Gdx.files.internal("data/libgdx.png")
		
		AntBrain redBrain = null;
		AntBrain blackBrain = null;
		
		try {
			redBrain = AntBrainLoader.load("C:/University/Dropbox/Git2/AntGame/AntGame-android/assets/brains/horseshoe.brain");
			blackBrain = AntBrainLoader.load("C:/University/Dropbox/Git2/AntGame/AntGame-android/assets/brains/cleverbrain1.brain");
		}
		catch(InvalidInputException e) {
			System.out.println("invalid brains. This will crash the game at some point. best not to continue");
			System.exit(0);
		}
		
		// initialise world simulation
		world = new AntWorld(cells, redBrain, blackBrain);
		
		// initialise gui
		worldView = new AntWorldTextView(world, stage);
		
		// initialise controller
		
	}
	
	@Override
	public void render(float delta) {
		
		// update the world
		world.update();
		stage.act(delta);
		
		// clear the screen with the given RGB color (black)
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// render the world
		worldView.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

}