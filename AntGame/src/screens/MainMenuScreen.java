/**
 * 
 */
package screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import views.widgets.Menu;
import views.widgets.MenuItem;
import antgame.AntGame;
import antgame.Assets;
import antgame.Config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

import controllers.MainMenuController;

/**
 * PlayScreen.java
 * 
 * @author Chris B
 * @date 25 Mar 2013
 * @version 1.0
 */
public class MainMenuScreen extends AbstractScreen {

	Image backgroundImage;
	Image logoImage;
	Table root;
	
	/**
	 * @param game
	 */
	public MainMenuScreen(AntGame game) {
		super(game);
		this.initialise();
	}

	private void initialise() {
		
		// load the background image
		AtlasRegion backgroundRegion = Assets.textures.findRegion("menu-bg");
		Drawable backgroundDrawable = new TextureRegionDrawable(backgroundRegion);

		// here we create the background image actor; its size is set when the
		backgroundImage = new Image(backgroundDrawable, Scaling.stretch);
		backgroundImage.setFillParent(true);
		
		// add the background image actor to the stage
		stage.addActor(backgroundImage);
		
		
		// create a table layout to add ui items to the stage
		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		root = new Table(skin);
		root.setFillParent(true);
		
		
		
		// load the logo from the texture atlas
		AtlasRegion logoRegion = Assets.textures.findRegion("logo");
		Drawable logoDrawable = new TextureRegionDrawable(logoRegion);
		
		// here we create the logo image actor
		logoImage = new Image(logoDrawable);
		
		// create a top section for the logo the cell added is aligned bottom and has 100 padding top
		Table top = new Table(skin);
		root.add(top).height(logoRegion.getRegionHeight()).padTop(75);
		root.row();
		
		// add the background image actor to the stage
		top.add(logoImage).size(logoRegion.getRegionWidth(), logoRegion.getRegionHeight());
		
		
		// create a center section for the content
		Table centre = new Table(skin);
		root.add(centre).expand().padBottom(75);
		
		// load the menu items. the item names allow the controller distinguish between them to 
		// perform different tasks
		Menu menu = new Menu();
		int paddingBottom = 10;
		
		// initialise the controller for the menu
		MainMenuController controller = new MainMenuController(game);
		
		// two player game menu item
		TextureRegion twoPlayerUp = Assets.textures.findRegion("menu-two-player-game-up");
		TextureRegion twoPlayerDown = Assets.textures.findRegion("menu-two-player-game-down");
		MenuItem twoPlayer = new MenuItem(twoPlayerUp, twoPlayerDown, Config.MENU_ITEM_TWO_PLAYER_GAME, paddingBottom, controller);
		menu.addMenuItem(twoPlayer);
		
		// tournament game menu item
		TextureRegion tournamentUp = Assets.textures.findRegion("menu-tournament-game-up");
		TextureRegion tournamentDown = Assets.textures.findRegion("menu-tournament-game-down");
		MenuItem tournament = new MenuItem(tournamentUp, tournamentDown, Config.MENU_ITEM_TOURNAMENT_GAME, paddingBottom, controller);
		menu.addMenuItem(tournament);
		
		// help menu item
		TextureRegion helpUp = Assets.textures.findRegion("menu-help-up");
		TextureRegion helpDown = Assets.textures.findRegion("menu-help-down");
		MenuItem help = new MenuItem(helpUp, helpDown, Config.MENU_ITEM_HELP, paddingBottom, controller);
		menu.addMenuItem(help);
		
		// exit menu item
		TextureRegion exitUp = Assets.textures.findRegion("menu-exit-up");
		TextureRegion exitDown = Assets.textures.findRegion("menu-exit-down");
		MenuItem exit = new MenuItem(exitUp, exitDown, Config.MENU_ITEM_EXIT, paddingBottom, controller);
		menu.addMenuItem(exit);
		
		// add the menu to the table
		centre.add(menu);
		
		// add the table to the stage
		stage.addActor(root);
		
		// enable debug draw
		//root.debug();
	}

	@Override
	public void show() {
		// set the stage as the input processor here and not in initialise to avoid
		// clashing with other screens which also accept input
		Gdx.input.setInputProcessor(stage);		
		
		// set the background and table transparencies to 0 so they can be faded in
		//backgroundImage.getColor().a = 0f;
		root.getColor().a = 0f;
		
		// fade in the background
		//backgroundImage.addAction(fadeIn(0.75f));
				
		// fade in the menu items
		root.addAction(fadeIn(0.75f));
	}

	@Override
	public void render(float delta) {
		// update the actors
		stage.act(delta);
		
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