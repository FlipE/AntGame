/**
 * 
 */
package views.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

/**
 * Menu.java
 *
 * @author 	Chris B
 * @date	26 Mar 2013
 * @version	1.0
 */
public class Menu extends Table {

	private Array<Button> buttons;
	
	public Menu() {
		this(null);
	}
	
	public Menu(Skin skin) {
		super(skin);
		this.buttons = new Array<Button>();
	}
	
	public void addMenuItem(MenuItem item) {
		buttons.add(item.getButton());
		this.add(item.getButton()).size(item.getWidth(), item.getHeight()).uniform().spaceBottom(item.getSpaceBottom());
		this.row();
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		// set the button's down image as active onMouseOver
		for(Button b : buttons) {
			b.setChecked(b.isOver());
		}
		
	}
	
}