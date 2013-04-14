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

/**
 * HeadsUpDisplay.java
 *
 * @author 	Chris B
 * @date	13 Apr 2013
 * @version	1.0
 */
public class HeadsUpDisplay implements View {
	
	Stage stage;
	Match match;
	Label roundNum;
	
	public HeadsUpDisplay(Stage stage, Match match) {
		
		this.stage = stage;
		this.match = match;
		Skin skin = Assets.skin;
		
		// hud stuff
		Table hud = new Table();
		hud.setFillParent(true);
		hud.top();
		roundNum = new Label("0", skin);
		hud.add(roundNum);
		stage.addActor(hud);
	}

	
	/* (non-Javadoc)
	 * @see views.View#draw()
	 */
	@Override
	public void draw() {
		
		roundNum.setText("" + this.match.getRoundNum());
		stage.draw();
	}

}