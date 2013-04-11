/**
 * 
 */
package screens;

import listeners.MatchListener;
import views.AntWorldView;
import views.View;
import antgame.AntGame;
import antgame.Match;
import antgame.MatchManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.utils.Array;

/**
 * PlayScreen.java
 *
 * @author 	Chris B
 * @date	25 Mar 2013
 * @version	1.0
 * @param
 */
public class PlayScreen extends AbstractScreen implements MatchListener {

	private Match match;
	private View worldView;
	private MatchManager matchManager;
	private Array<Match> matches;
	
	private enum ScreenState {
		GET_READY, PLAYING, MATCH_RESULT, FINISHED
	}
	
	private ScreenState screenState;
	
	/**
	 * @param game
	 */
	public PlayScreen(AntGame game) {
		super(game);
		this.matchManager = MatchManager.getInstance();
		this.matches = new Array<Match>();
		this.initialise();
	}

	private void initialise() {
		
		// initialise world simulation
		//world = new AntWorld(cells, redBrain, blackBrain);
		
		// initialise gui
		
		// initialise controller*/
		
	}
	
	@Override
	public void show() {
		try {
			// set the screen state to get ready so that game doesn;t start immediately
			this.screenState = ScreenState.PLAYING;
			
			// this call make the match manager pair up all teams to play on each world
			this.matchManager.match();
			
			// this grabs all the matches in the manager
			this.matches = this.matchManager.getMatches();
			
			// this sets the current match to 
			match = this.matches.get(0);
			
			// create the new view giving it the world
			worldView = new AntWorldView(match.getCells(), stage);
		}
		catch (Exception e) {
			// if the matche manager can't create any games, return to the main menu
			// this should never happen because the menu screen should verify the
			// data before allowing this screen transition
			game.setMainMenuScreen();
		}
	}
	
	@Override
	public void render(float delta) {
		
		// update the simulation
		this.update(delta);
		
		// clear the screen with the given RGB color (black)
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// render the world
		worldView.draw();
	}

	private void update(float delta) {
		
		// update stage actors, this updates the ui
		stage.act(delta);
		
		switch(this.screenState) {		
			case GET_READY:
				// wait for button press to start the match
			break;
			
			case PLAYING:
				// update the world
				match.update();	
			break;
			
			case MATCH_RESULT:
				// show match results, wait for button press to move to next match			
			break;
				
			case FINISHED:
				// if there are no more matches transition to the next screen
			break;
		}
		
	}

	@Override
	public void resize(int width, int height) {
	}

	/* (non-Javadoc)
	 * @see listeners.MatchListener#isFinished()
	 */
	@Override
	public void isFinished() {
		// play the next match?
		// show a new screen?
		// change state to show scores
		if(this.matchManager.hasNext()) {
			// get the next match
			this.match = this.matchManager.next();			
		}
		else {
			// show final scores
		}
	}

}