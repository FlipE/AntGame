/**
 * 
 */
package screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import models.PlayerInfo;
import views.widgets.PlayerInfoView;
import views.widgets.WorldInfoView;
import antgame.AntGame;
import antgame.Assets;
import antgame.Config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;

import controllers.TwoPlayerMenuController;

/**
 * PlayScreen.java
 * 
 * @author Chris B
 * @date 25 Mar 2013
 * @version 1.0
 */
public class TwoPlayerMenuScreen extends AbstractScreen {

	private Image backgroundImage;
	private Image logoImage;
	private Table root;
	private Button backBtn;
	private Button playBtn;
	private Array<PlayerInfo> players;
	private Array<PlayerInfoView> playerViews;
	
	/**
	 * @param game
	 */
	public TwoPlayerMenuScreen(AntGame game) {
		super(game);
		this.players = new Array<PlayerInfo>();
		this.playerViews = new Array<PlayerInfoView>();
		this.initialise();
	}

	private void initialise() {

		// initialise the controller for the menu
		TwoPlayerMenuController controller = new TwoPlayerMenuController(game);

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

		// load the logo
		AtlasRegion logoRegion = Assets.textures.findRegion("logo");
		Drawable logoDrawable = new TextureRegionDrawable(logoRegion);

		// here we create the background image actor; its size is set when the
		logoImage = new Image(logoDrawable);

		// create a top section for the logo the cell added is aligned bottom and has 100 padding top
		//Table top = new Table(skin);
		//root.add(top).height(logoRegion.getRegionHeight()).padTop(75).colspan(2);
		//root.row();

		// add the logo image actor to the stage
		//top.add(logoImage).size(logoRegion.getRegionWidth(), logoRegion.getRegionHeight());
		
		// back button
		Table left = new Table(skin);
		Drawable backUp = new TextureRegionDrawable(Assets.textures.findRegion("back-up"));
		Drawable backDown = new TextureRegionDrawable(Assets.textures.findRegion("back-down"));
		backBtn = new Button(backUp, backDown, backDown);
		backBtn.setName(Config.BACK_TO_MENU);
		backBtn.addListener(controller);
		left.add(backBtn).size(180, 71);
		root.add(left).expandY().padBottom(75).padTop(150).left().top();
		
		// load the menu items. the item names allow the controller distinguish between them to 
		// perform different tasks
		// create a center section for the content
		Table centre = new Table(skin);
		centre.left().top();
		final ScrollPane scroll = new ScrollPane(centre);
		scroll.setSmoothScrolling(true);
		scroll.setScrollingDisabled(true, false);
		root.add(scroll).expand().fill().padTop(150).padLeft(50);
		root.row();
		
		// players
		centre.add(addWorld());
		centre.row();
		centre.add(addPlayer(1));
		centre.row();
		centre.add(addPlayer(2));
		centre.row();

		// play button
		Drawable playUp = new TextureRegionDrawable(Assets.textures.findRegion("play-up"));
		Drawable playDown = new TextureRegionDrawable(Assets.textures.findRegion("play-down"));
		playBtn = new Button(playUp, playDown, playDown);
		playBtn.setName(Config.BACK_TO_MENU);
		playBtn.addListener(controller);
		root.add(playBtn).size(180, 71).colspan(2).right().padRight(230).padBottom(80);
		
		// add the table to the stage
		stage.addActor(root);
		
		// enable debug draw
		centre.debug();
		root.debug();
		centre.debug();
	}

	private PlayerInfoView addPlayer(int id) {
		// create a new player info and view
		PlayerInfo playerInfo = new PlayerInfo("Player " + id, id);
		PlayerInfoView view = new PlayerInfoView(playerInfo);
		
		// add the info and view to the lists so we can keep track of what players are added
		this.players.add(playerInfo);
		this.playerViews.add(view);
		
		// return the view so it can be added to the screen content
		return view;
	}
	
	private WorldInfoView addWorld() {
		WorldInfoView view = new WorldInfoView();
		return view;
	}
	
	@Override
	public void show() {

		// set the stage as the input processor
		Gdx.input.setInputProcessor(stage);

		// set the table alpha to 0 so it can be faded in
		root.getColor().a = 0f;

		// fade in the menu items
		root.addAction(fadeIn(0.75f));
	}

	@Override
	public void render(float delta) {
		// update the actors
		stage.act(delta);

		// set the down state if mouse is over the buttons and up if it isn't
		this.backBtn.setChecked(this.backBtn.isOver());
		this.playBtn.setChecked(this.playBtn.isOver());
		
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