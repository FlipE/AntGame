/**
 * 
 */
package views.widgets;

import listeners.PlayerInfoListener;
import models.PlayerInfo;
import antgame.Assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import controllers.PlayerInfoController;

/**
 * AddPlayer.java
 *
 * @author 	Chris B
 * @date	2 Apr 2013
 * @version	1.0
 */
public class PlayerInfoView extends Table implements PlayerInfoListener {

	private PlayerInfo playerInfo;
	private Label title;
	private Label nameLbl;
	private TextField nameTxt;
	private Label brainLbl;
	private TextField brainTxt;
	private Button browseBtn;
	private FormValidationFeedback nameFeedback;
	private FormValidationFeedback brainFeedback;
	
	public PlayerInfoView(PlayerInfo playerInfo) {
		super();
		this.playerInfo = playerInfo;
		this.initialise();
	}

	private void initialise() {
		
		// register this view as a listener to the player info
		this.playerInfo.addListener(this);
		
		// load the skin
		Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		// initialise the controller for the menu
		PlayerInfoController controller = new PlayerInfoController(this.playerInfo);
		
		// Title
		title = new Label("Player " + playerInfo.getId(), skin);

		// choose name
		nameLbl = new Label("Name:", skin);
		nameTxt = new TextField(playerInfo.getName(), skin);
		nameTxt.setTextFieldListener(controller);

		// choose brain
		Drawable browseUp = new TextureRegionDrawable(Assets.textures.findRegion("browse-up"));
		Drawable browseDown = new TextureRegionDrawable(Assets.textures.findRegion("browse-down"));
		brainLbl = new Label("Brain:", skin);
		brainTxt = new TextField("Not Set", skin);
		//brainTxt.setDisabled(true);
		browseBtn = new Button(browseUp, browseDown);
		browseBtn.addListener(controller);
		
		// form feedback
		nameFeedback = new FormValidationFeedback();
		nameFeedback.setValid(true);
		brainFeedback = new FormValidationFeedback();
		
		// table defaults and alignment
		this.defaults().padRight(10).padBottom(3);
		this.left().top();
		
		// add background
		this.setBackground(new TextureRegionDrawable(Assets.textures.findRegion("add-player-bg")));
		
		// add title
		this.add(title).padBottom(30).padTop(5).left().padLeft(35);
		this.row();
		
		// add name
		this.add(nameLbl).right().padLeft(55);
		this.add(nameTxt).left().colspan(2).expandX().prefWidth(380);
		this.add(nameFeedback).padRight(34);
		this.row();
		
		// add brain
		this.add(brainLbl).right().padLeft(20);
		this.add(brainTxt).left().expandX().prefWidth(300).padRight(5);
		this.add(browseBtn);
		this.add(brainFeedback).padRight(34);
		this.row();
	}
	
	@Override
	public void updateBrainPath(String path, boolean isValid) {
		this.brainFeedback.setValid(isValid);
		this.brainTxt.setText(path);
	}

	@Override
	public void updateName(String name, boolean isValid) {
		// update the form feedback widget to show whether the new name is valid
		this.nameFeedback.setValid(isValid);
		
		// get the cursor position before setting the text
		int cursorPosition = nameTxt.getCursorPosition();
		
		// set the new name in the text box, this resets the cursor position
		this.nameTxt.setText(name);
		
		// set the cursor to its previous position 
		this.nameTxt.setCursorPosition(cursorPosition);
	}
	
	

	/* (non-Javadoc)
	 * @see listeners.PlayerInfoListener#updateId(int)
	 */
	@Override
	public void updateId(int id) {
		this.title.setText("Player " + id);
	}	
}