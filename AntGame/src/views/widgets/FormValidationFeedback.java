/**
 * 
 */
package views.widgets;

import antgame.Assets;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Simple visual feedback for use on forms. upon instantiation no deedback is shown.
 * When set to valid a tick is shown. When set to invalid a cross is shown.
 * 
 * FormValidationFeedback.java
 *
 * @author 	Chris B
 * @date	4 Apr 2013
 * @version	1.0
 */
public class FormValidationFeedback extends Table {

	private Image valid;
	private Image invalid;
	private Image blank;
	
	public FormValidationFeedback() {		
		valid = new Image(new TextureRegionDrawable(Assets.textures.findRegion("tick")));
		invalid = new Image(new TextureRegionDrawable(Assets.textures.findRegion("cross")));
		blank = new Image(new TextureRegionDrawable(Assets.textures.findRegion("blank")));
		this.add(blank);
	}
	
	public void setValid(boolean isValid) {
		this.clear();
		if(isValid) {
			this.add(valid);
		}
		else {
			this.add(invalid);
		}
	}
}