/**
 * 
 */
package widgets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * A simple wrapper around the scene2d ui button. Menu Items are
 * added to a Menu instance to create a game menu
 * 
 * MenuItem.java
 *
 * @author 	Chris B
 * @date	26 Mar 2013
 * @version	1.0
 */
public class MenuItem {

	private TextureRegion up;
	private TextureRegion down;
	private Button button;
	private int spaceBottom;
	
	public MenuItem(TextureRegion up, TextureRegion down, String name) {
		this(up, down, name, 0);
	}
	
	public MenuItem(TextureRegion up, TextureRegion down, String name, int spaceBottom) {
		this(up, down, name, spaceBottom, null);
	}
	
	public MenuItem(TextureRegion up, TextureRegion down, String name, InputListener listener) {
		this(up, down, name, 0, listener);
	}
	
	public MenuItem(TextureRegion up, TextureRegion down, String name, int spaceBottom, InputListener listener) {
		this.up = up;
		this.down = down;
		this.spaceBottom = spaceBottom;
		Drawable drawUp = new TextureRegionDrawable(up);
		Drawable drawDown = new TextureRegionDrawable(down);		
		this.button = new Button(drawUp, drawDown, drawDown);
		this.button.setName(name);
		this.button.addListener(listener);
	}
	
	public float getWidth() {
		return Math.max(this.up.getRegionWidth(), this.down.getRegionWidth());
	}
	
	public float getHeight() {
		return Math.max(this.up.getRegionHeight(), this.down.getRegionHeight());
	}
	
	public Button getButton() {
		return this.button;
	}

	public int getSpaceBottom() {
		return spaceBottom;
	}
	
	public void addListener(InputListener listener) {
		this.button.addListener(listener);
	}
	
}