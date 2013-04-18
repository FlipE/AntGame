/**
 * 
 */
package screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import models.PlayerInfo;
import antgame.AntGame;
import antgame.Assets;
import antgame.Config;
import antgame.MatchManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

import controllers.HelpScreenController;

/**
 * ScoreScreen.java
 * 
 * @date 25 Mar 2013
 * @version 1.0
 */
public class ScoreScreen extends AbstractScreen {

	Table centre;
	private Image backgroundImage;
	private Image logoImage;
	private Table root;
	private Button continueButton;
	private MatchManager matchManager;
	
	/**
	 * @param game
	 */
	public ScoreScreen(AntGame game) {
		super(game);
		this.initialise();
	}

	private void initialise() {

		// initialise the controller for the menu
		HelpScreenController controller = new HelpScreenController(game);
				
		// load the background image
		AtlasRegion backgroundRegion = Assets.textures.findRegion("menu-bg");
		Drawable backgroundDrawable = new TextureRegionDrawable(backgroundRegion);

		// here we create the background image actor; its size is set when the
		backgroundImage = new Image(backgroundDrawable, Scaling.stretch);
		backgroundImage.setFillParent(true);
		
		// add the background image actor to the stage
		stage.addActor(backgroundImage);
		
		// create a table layout to add ui items to the stage
		Skin skin = Assets.skin;
		root = new Table(skin);
		root.setFillParent(true);

		
		// load the logo
		AtlasRegion logoRegion = Assets.textures.findRegion("logo");
		Drawable logoDrawable = new TextureRegionDrawable(logoRegion);

		// here we create the background image actor; its size is set when the
		logoImage = new Image(logoDrawable);
				
		// create a top section for the logo the cell added is aligned bottom and has 100 padding top
		Table top = new Table(skin);
		root.add(top).height(logoRegion.getRegionHeight()).padTop(75).colspan(2);
		root.row();
				
		// add the background image actor to the stage
		top.add(logoImage).size(logoRegion.getRegionWidth(), logoRegion.getRegionHeight());
				
		// load the menu items. the item names allow the controller distinguish between them to 
		// perform different tasks
		// create a center section for the content
		centre = new Table(skin);
		final ScrollPane scroll = new ScrollPane(centre);
		scroll.setSmoothScrolling(true);
		scroll.setFadeScrollBars(false);
		scroll.setScrollingDisabled(true, false);
		root.add(scroll).expand().fill().pad(75).padBottom(10);
		root.row();
				
		// continue button
		Drawable backUp = new TextureRegionDrawable(Assets.textures.findRegion("continue-up"));
		Drawable backDown = new TextureRegionDrawable(Assets.textures.findRegion("continue-down"));
		continueButton = new Button(backUp, backDown, backDown);
		continueButton.setName(Config.BACK_TO_MENU);
		continueButton.addListener(controller);
		root.add(continueButton).padBottom(10);
		
		// add the table to the stage
		stage.addActor(root);
		
		// enable debug draw
		root.debug();
		centre.debug();
	}

	/**
	 * @param p
	 * @return
	 */
	private Table headings() {
		Skin skin = Assets.skin;
		Table scoreTbl = new Table();
		
		Label name = new Label("Player Name", skin);
		Label wins = new Label("Wins", skin);
		Label draws = new Label("Draw", skin);
		Label losses = new Label("Lose", skin);
		
		scoreTbl.add(name).prefWidth(300);
		scoreTbl.add(wins).prefWidth(40);
		scoreTbl.add(draws).prefWidth(40);
		scoreTbl.add(losses).prefWidth(40);
		
		return scoreTbl;
	}
	
	/**
	 * @param p
	 * @return
	 */
	private Table playerScore(PlayerInfo p) {
		Skin skin = Assets.skin;
		Table scoreTbl = new Table();
		
		Label name = new Label(p.getName(), skin);
		Label wins = new Label(p.getWins() + "", skin);
		Label draws = new Label(p.getDraws() + "", skin);
		Label losses = new Label(p.getLosses() + "", skin);
		
		scoreTbl.add(name).prefWidth(300);
		scoreTbl.add(wins).prefWidth(40);
		scoreTbl.add(draws).prefWidth(40);
		scoreTbl.add(losses).prefWidth(40);
		
		return scoreTbl;
	}

	@Override
	public void show() {
		
		// clear the old content
		this.centre.clear();
		this.centre.top();
		// first sort the players
		this.matchManager = MatchManager.getInstance();
		this.matchManager.sortPlayers();
		
		centre.add(this.headings());
		centre.row();
		
		// then add all players
		for(PlayerInfo p : this.matchManager.getPlayers()) {
			centre.add(this.playerScore(p));
			centre.row();
		}
		
		// set the stage as the input processor
		Gdx.input.setInputProcessor(stage);
		
		// set the background and table transparencies to 0 so they can be faded in
		//backgroundImage.getColor().a = 0f;
		root.getColor().a = 0f;
		
		// fade in the menu items
		root.addAction(fadeIn(0.75f));
	}

	@Override
	public void render(float delta) {
		// update the actors
		stage.act(delta);

		// set the down state if mouse is over the buttons and up if it isn't
		this.continueButton.setChecked(this.continueButton.isOver());
		
		// clear the screen with the given RGB color (black)
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		// draw the actors
		stage.draw();
		
		// debug draw table
		//Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
	}

}