/**
 * 
 */
package views.widgets;

import listeners.WorldInfoListener;
import models.WorldInfo;
import antgame.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import controllers.WorldInfoController;

/**
 * AddPlayer.java
 *
 * @author 	Chris B
 * @date	2 Apr 2013
 * @version	1.0
 */
public class WorldInfoView extends Table implements WorldInfoListener {

	private WorldInfo worldInfo;
	private Label title;
	private Label worldLbl;
	private TextField worldTxt;
	private Button browseBtn;
	private FormValidationFeedback worldFeedback; 
	
	public WorldInfoView(WorldInfo worldInfo) {
		super();
		this.worldInfo = worldInfo;
		this.initialise();
	}

	private void initialise() {
		
		this.worldInfo.addListener(this);
		
		// load the skin
		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		// initialise the controller for the menu
		WorldInfoController controller = new WorldInfoController(this.worldInfo);
		
		// Title
		title = new Label("World", skin);

		// choose brain
		Drawable browseUp = new TextureRegionDrawable(Assets.textures.findRegion("browse-up"));
		Drawable browseDown = new TextureRegionDrawable(Assets.textures.findRegion("browse-down"));
		worldLbl = new Label("World:", skin);
		worldTxt = new TextField("Not Set", skin);
		browseBtn = new Button(browseUp, browseDown);
		browseBtn.addListener(controller);
		
		// loader feedback display
		worldFeedback = new FormValidationFeedback();
		
		// table defaults and alignment
		this.defaults().padRight(10).padBottom(3);
		this.left().top();
		
		// add background
		this.setBackground(new TextureRegionDrawable(Assets.textures.findRegion("world-info-bg")));
		
		// add title
		this.add(title).padBottom(30).padTop(5).left().padLeft(35);
		this.row();
		
		// add world
		this.add(worldLbl).right().padLeft(55);
		this.add(worldTxt).left().expandX().prefWidth(300).padRight(5);
		this.add(browseBtn);
		this.add(worldFeedback).padRight(34);
		this.row();
	}

	@Override
	public void updateWorldPath(String path, boolean isValid) {
		worldFeedback.setValid(isValid);
		this.worldTxt.setText(path);
	}
}