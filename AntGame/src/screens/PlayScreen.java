/**
 * 
 */
package screens;

import listeners.MatchListener;
import views.AntWorldView;
import views.HeadsUpDisplay;
import views.View;
import antgame.AntGame;
import antgame.Assets;
import antgame.Match;
import antgame.MatchManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import controllers.PlayScreenController;

/**
 * PlayScreen.java
 * 
 * @author Chris B
 * @date 25 Mar 2013
 * @version 1.0
 * @param
 */
public class PlayScreen extends AbstractScreen implements MatchListener {

	private Table root;
	private Table matchResults;
	private Button playBtn;
	private Button continueBtn;
	
	private Match match;
	private View worldView;
	private HeadsUpDisplay hud;
	private MatchManager matchManager;
	private PlayScreenController controller;

	private enum ScreenState {
		GET_READY, PLAYING, PAUSED, MATCH_RESULT, FINISHED
	}

	private ScreenState screenState;

	/**
	 * @param game
	 */
	public PlayScreen(AntGame game) {
		super(game);
		this.matchManager = MatchManager.getInstance();
		this.controller = new PlayScreenController(game, this);
	}

	public void reset() {
		if(this.root != null) {
			this.root.reset();
		}
		super.stage = new Stage();
	}

	@Override
	public void show() {
		try {
			
			// continue button
			Drawable backUp = new TextureRegionDrawable(Assets.textures.findRegion("continue-up"));
			Drawable backDown = new TextureRegionDrawable(Assets.textures.findRegion("continue-down"));
			continueBtn = new Button(backUp, backDown, backDown);
			continueBtn.setName("continue");
			continueBtn.addListener(controller);
			
			this.matchResults = new Table();
			
			// this call make the match manager pair up all teams to play on each world
			this.matchManager.match();

			// this sets the current match to 
			match = this.matchManager.next();

			// register this screen as an observer of the match
			match.addListener(this);

			// setup the ui
			
			// create a table layout to add ui items to the stage
			Skin skin = Assets.skin;
			root = new Table(skin);
			root.setFillParent(true);
			this.root.debug();
			stage.addActor(root);

			// set the screen state to get ready so that game doesn;t start immediately
			this.getReady();
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
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
		this.stage.act();
		this.hud.act();
		
		
		// clear the screen with the given RGB color (black)
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// render the world
		worldView.draw();

		// render the ui
		this.stage.draw();

		// debug draw
		//Table.drawDebug(stage);
	}

	private void update(float delta) {

		// update stage actors, this updates the ui
		stage.act(delta);

		switch (this.screenState) {
		case GET_READY:
			// wait for button press to start the match
			// set hover state on play button
			this.playBtn.setChecked(this.playBtn.isOver());
			break;

		case PLAYING:
			// update the world
			match.update();
			break;

		case PAUSED:
			// do not update ants
			break;

		case MATCH_RESULT:
			// show match results, wait for button press to move to next match
			this.continueBtn.setChecked(this.continueBtn.isOver());
			break;

		case FINISHED:
			// if there are no more matches transition to the next screen
			break;
		}

	}

	@Override
	public void resize(int width, int height) {
	}

	/**
	 * When the match is finished the match calls this function.
	 * The state is changed to display the results
	 */
	@Override
	public void isFinished() {
		System.out.println("finished");
		this.matchResultState();
	}

	/**
	 * 
	 */
	public void getReady() {

		// add the hud at the top of the table
		this.reset();
		root.setFillParent(true);
		
		// new hud and renderer for this match
		this.hud = new HeadsUpDisplay(match);
		this.worldView = new AntWorldView(match.getCells(), stage);
		
		this.root.add(this.hud).top().padTop(10);
		this.root.row();
		
		// show a button in center of screen saying get ready
		// when it is clicked transition to playing
		Drawable backUp = new TextureRegionDrawable(Assets.textures.findRegion("play-small-up"));
		Drawable backDown = new TextureRegionDrawable(Assets.textures.findRegion("play-small-down"));
		playBtn = new Button(backUp, backDown, backDown);
		playBtn.setName("play");
		playBtn.addListener(controller);
		root.add(playBtn).expand();
		
		this.stage.addActor(root);
		
		// set the stage as the input processor
		Gdx.input.setInputProcessor(stage);
		
		// set the state
		this.screenState = ScreenState.GET_READY;
	}

	/**
	 * change the game to the play state
	 */
	public void playState() {
		
		// remove the play button from root by clearing it and re-adding the hud
		//this.root.add(this.hud).top().expand().padTop(10);
		//this.root.row();
		
		this.root.removeActor(this.playBtn);
		
		// set the state
		this.screenState = ScreenState.PLAYING;
	}

	/**
	 * 
	 */
	public void pauseState() {
		this.screenState = ScreenState.PAUSED;
	}

	public void matchResultState() {
		
		// set the state
		this.screenState = ScreenState.MATCH_RESULT;
		System.out.println("results");
		
		// clear the table and re-add the hud
		this.root.reset();
		this.root.layout();
		root.setFillParent(true);
		this.root.add(this.hud).top().padTop(10);;
		this.root.row();
		this.root.removeActor(this.matchResults);
		
		// show the match result table
		Skin skin = Assets.skin;
		this.matchResults.clear();
		
		// player name and score labels
		Label redName = new Label(this.match.getRedPlayerName(), skin);
		Label redScore = new Label(this.match.getRedScore() + "", skin);
		Label blackName = new Label(this.match.getBlackPlayerName(), skin);
		Label blackScore = new Label(this.match.getBlackScore() + "", skin);
		
		this.matchResults.add(redName).prefWidth(200);
		this.matchResults.add(redScore).left();
		this.matchResults.row();
		this.matchResults.add(blackName).prefWidth(200);
		this.matchResults.add(blackScore).left();
		this.matchResults.row();
		this.matchResults.add(continueBtn);
		
		this.root.add(this.matchResults).expand();
		
	}
	
	/**
	 * if there is another match in the queue play that else
	 * go to the score screen
	 */
	public void nextMatch() {
		System.out.println(this.matchManager.hasNext());
		if (this.matchManager.hasNext()) {
			this.match = this.matchManager.next();
			// register this screen as an observer of the match
			this.match.addListener(this);
			this.getReady();
		}
		else {
			game.setScoreScreen();
		}
	}

}