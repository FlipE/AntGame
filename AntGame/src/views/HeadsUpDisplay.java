/**
 * 
 */
package views;

import antgame.Assets;
import antgame.Match;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * The heads up display give feedback to the user about the state of the game.
 * The current round
 * The current score for each team
 * How many ants are left for each team 
 * 
 * HeadsUpDisplay.java
 *
 * @author 	24233
 * @date	13 Apr 2013
 * @version	1.0
 */
public class HeadsUpDisplay extends Table {
	
	/** string buffer to stop garbage collecting strings */
	private StringBuffer buffer;
	
	/** the match being played */
	private Match match;
	
	/** the round number display */
	private Label roundNum;
	
	/** the red score */
	private Label redScore;
	
	/** the black score */
	private Label blackScore;
	
	/**
	 * The heads up display give feedback to the user about the state of the game.
	 * The current round
	 * The current score for each team
	 * How many ants are left for each team
	 * 
	 * @param stage the stage onto which everything should be placed.
	 * @param match the match to display information from.
	 */
	public HeadsUpDisplay(Match match) {
		
		this.buffer = new StringBuffer();
		this.match = match;
		Skin skin = Assets.skin;
		
		this.setWidth(902);
		this.setHeight(108);
		//this.setPosition(61, 658);
		
		// add background
		this.setBackground(new TextureRegionDrawable(Assets.textures.findRegion("hud-bg")));
		
		// label to display the round number
		this.roundNum = new Label("0", skin);
		this.blackScore = new Label("0", skin);
		
		this.add(this.redPlayer());
		this.add(this.roundNum).prefWidth(75).padLeft(40);
		this.add(this.blackPlayer()).padLeft(20);
		
		this.debug();
	}

	private Table redPlayer() {
		Skin skin = Assets.skin;
		Table player = new Table();
		
		Label nameLbl = new Label("Name: ", skin);
		Label name = new Label(this.match.getRedPlayerName(), skin);
		
		Label scoreLbl = new Label("Score:", skin);
		this.redScore = new Label("0", skin);
		
		player.add(nameLbl).right().prefWidth(60);
		player.add(name).left().prefWidth(300);
		player.row();
		
		player.add(scoreLbl).right().prefWidth(60);
		player.add(this.redScore).left();
		
		player.debug();
		
		return player;
	}
	
	private Table blackPlayer() {
		Skin skin = Assets.skin;
		Table player = new Table();
		
		Label nameLbl = new Label("Name: ", skin);
		Label name = new Label(this.match.getBlackPlayerName(), skin);
		
		Label scoreLbl = new Label("Score:", skin);
		this.blackScore = new Label("0", skin);
		
		player.add(nameLbl).right().prefWidth(60);
		player.add(name).left().prefWidth(300);
		player.row();
		
		player.add(scoreLbl).right().prefWidth(60);
		player.add(this.blackScore).left();
		
		player.debug();
		
		return player;
	}
	
	
	/**
	 * update the info for each label and draw the contents of the stage
	 */
	public void act() {
		// reset buffer, append the value, set the label txt
		buffer.setLength(0);
		buffer.append(this.match.getRoundNum());
		this.roundNum.setText(buffer);
		
		// reset buffer, append the value, set the label txt
		buffer.setLength(0);
		buffer.append(this.match.getRedScore());
		this.redScore.setText(buffer);
		
		// reset buffer, append the value, set the label txt
		buffer.setLength(0);
		buffer.append(this.match.getBlackScore());
		this.blackScore.setText(buffer);
	}

}